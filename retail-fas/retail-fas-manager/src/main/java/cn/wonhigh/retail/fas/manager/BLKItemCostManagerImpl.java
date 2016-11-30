package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BLKCompanyPeriodBalanceService;
import cn.wonhigh.retail.fas.service.BLKPeriodBalanceService;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.ItemCostService;
import cn.wonhigh.retail.fas.service.ItemService;
import cn.wonhigh.retail.fas.service.PeriodBalanceAuditService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("blkItemCostManager")
public class BLKItemCostManagerImpl extends BaseCrudManagerImpl implements BLKItemCostManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BLKItemCostManagerImpl.class);
	
	@Resource
	private PeriodBalanceAuditService periodBalanceAuditService;
	
	@Resource
	private BLKPeriodBalanceService blkPeriodBalanceService;
	
	@Resource
	private BLKCompanyPeriodBalanceService blkCompanyPeriodBalanceService;
	
	@Resource
	private ItemCostService itemCostService;
	
	@Resource
	private BrandService brandService;
	
	@Resource
	private ItemService itemService;

	@Override
	protected BaseCrudService init() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean generateBLKItemCost(ItemCost itemCost) throws ManagerException {
		try {
    		ItemCostConditionDto dto = combileDtoParams(itemCost);
    		new Thread(new GenerateCostAndPeriodCaseThread(this, dto, CurrentUser.getCurrentUser(), itemCost)).start();
			
			return true;
		} catch (Exception e) {
			LOGGER.error("################"+itemCost.getCompanyNo()+"公司"+itemCost.getYear()+"年"+itemCost.getMonth()+"月"+itemCost.getBrandUnitNo()+"品牌部生成成本异常", e);
			throw new ManagerException(e);
		}
	}
	
	@Override
	public void generateCostAndPeriodCase(ItemCostConditionDto dto, ItemCost itemCost)throws ManagerException {
		Map<String, Object> auduitParams = new HashMap<String, Object>();
		int isSuccess = 1; //1表示生成成功，2表示生成失败
		try {
			auduitParams = this.initAuduitParams(dto);
			List<PeriodBalanceAudit> existsAudits = periodBalanceAuditService.findByBiz(null, auduitParams);
			if (!CollectionUtils.isEmpty(existsAudits)) {
				LOGGER.error(dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部正在生成成本中!");
				return;
			}
			periodBalanceAuditService.auditPeriodBalance(auduitParams, false);//新增日志
			
			//生成店铺和公司期间结存
			this.generatePeriodBalance(dto);
			
			//公司期间结存回写入库类金额
			blkCompanyPeriodBalanceService.writeBackAmount(dto);
			
			//巴洛克特有的业务
			this.taransferStyleNoCostToCompanyPeriodBalanceAndItemCost(dto);
			
			//款号单位成本回写到 company_period_balance出库类金额
			blkCompanyPeriodBalanceService.itemCostFeedbackToPeriodBalance(dto);
			
			//重刷库存调整金额
			blkCompanyPeriodBalanceService.writeBackCostAdjustmentAmout(dto);
			
		} catch (ServiceException e) {
			isSuccess =2;
			throw new ManagerException(e.getMessage(), e);
		} finally {
			this.updatePeriodBalanceAudit(auduitParams, dto, isSuccess);//更新生成成本日志状态
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	private void generatePeriodBalance(ItemCostConditionDto dto) throws ServiceException {
		// 先删除本公司,本月的所有店铺结存数据
		blkPeriodBalanceService.deleteCompanyMonthPeriodBalance(dto);
		
		//生成本月店铺期间结存
		blkPeriodBalanceService.transferInventoryBookToPeriodBalance(dto);
		
		//删除公司期间结存
		blkCompanyPeriodBalanceService.deleteCompanyMonthPeriodBalance(dto);
		
		//店铺结存汇总公司期间结存
		blkCompanyPeriodBalanceService.generateCompanyPeriodBalance(dto);
	}

	/**
	 * 巴洛克特有的业务
	 * @param dto
	 * @throws ServiceException 
	 */
	private void taransferStyleNoCostToCompanyPeriodBalanceAndItemCost(ItemCostConditionDto dto) throws ServiceException {
		List<String> itemNos = blkCompanyPeriodBalanceService.findCompanyPeriodBalanceList(dto);//从company_period_balance表汇总当前商品编号
		
		Map<String, BigDecimal> styleInfo = blkCompanyPeriodBalanceService.selectCompanyPeriodBalanceGroupByStyleNo(dto,itemNos);
		
		this.updateItemCostByStyleNoCost(styleInfo,dto);
		
	}
	
	private void updateItemCostByStyleNoCost(Map<String, BigDecimal> styleInfo,ItemCostConditionDto dto) throws ServiceException {
		try {
			itemCostService.deleteCompanyMonthCost(dto);			 
			List<ItemCost> list = this.initBLKItemCost(styleInfo,dto);
			int totalCount = list.size();
			if(list.size() <= 0){
				return;
			}
			
			int pageSize = 500;// 每次插入500条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
			
			while (pageNo <= totalPage) {
				List<ItemCost> updateList = new ArrayList<ItemCost>();
				for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
					if (i > (list.size() - 1))
						break;
					updateList.add(list.get(i));
				}
				itemCostService.batchInsertItemCost(updateList);
				
				pageNo++;
			}
		} catch (Exception e) {
			LOGGER.error("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部回写单位成本失败",e);
			throw new ServiceException(e);
		}

	}
	
	private List<ItemCost> initBLKItemCost(Map<String, BigDecimal> styleInfo,ItemCostConditionDto dto) throws ServiceException {
		List<Item> list = itemService.findBLKItemInfo(dto);//根据巴洛克款号对应所有商品信息
		List<ItemCost> result = new ArrayList<ItemCost>();
		for(Item item:list){
			BigDecimal zero = BigDecimal.ZERO;
			ItemCost itemCost = new ItemCost();
			itemCost.setYear(dto.getCurrentYear());
			itemCost.setMonth(dto.getCurrentMonth());
			itemCost.setShardingFlag(dto.getShardingFlag());
			itemCost.setCompanyNo(dto.getCompanyNo());
			itemCost.setCompanyName(dto.getCompanyName());
			itemCost.setItemNo(item.getItemNo());
			itemCost.setItemCode(item.getCode());
			itemCost.setItemName(item.getName());
			itemCost.setBrandNo(item.getBrandNo());
			itemCost.setCreateUser(dto.getCreateUser());
			itemCost.setCreateTime(dto.getCreateTime());
			itemCost.setUpdateUser(dto.getCreateUser());
			itemCost.setUpdateTime(dto.getCreateTime());
			if(null != styleInfo.get(item.getStyleNo())){
				zero = styleInfo.get(item.getStyleNo());
			}
			itemCost.setUnitCost(zero);
			result.add(itemCost);
		}
		
		return result;
	}


	/**
	 * 初始化生成成本日志
	 * @param dto
	 * @return
	 */
	private Map<String, Object> initAuduitParams(ItemCostConditionDto dto) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("companyNo", dto.getCompanyNo());
		obj.put("year", dto.getCurrentYear());
		obj.put("month", dto.getCurrentMonth());
		obj.put("brandUnitNo", dto.getBrandUnitNo());
		obj.put("brandUnitName", dto.getBrandUnitName());
		obj.put("status", String.valueOf(0));
		obj.put("createUser", dto.getCreateUser());
		obj.put("createTime", dto.getCreateTime());
		return obj;
	}

	//更新生成成本日志状态
    private void updatePeriodBalanceAudit(Map<String, Object> auduitParams,ItemCostConditionDto dto,Integer status){
    	try {
			Map<String, Object> sucParams = new HashMap<String, Object>();
			sucParams.put("companyNo", auduitParams.get("companyNo"));
			sucParams.put("year", auduitParams.get("year"));
			sucParams.put("month", auduitParams.get("month"));
			sucParams.put("brandUnitNo", auduitParams.get("brandUnitNo"));
			sucParams.put("oldStatus", Integer.valueOf(0));
			sucParams.put("status", status);
			sucParams.put("updateUser", dto.getCreateUser());
			sucParams.put("updateTime", DateUtil.getCurrentDateTime());
			periodBalanceAuditService.auditPeriodBalance(sucParams, true);
			} catch (Exception e) {
			LOGGER.error("更新生成成本日志失败!", e);
		}
    }
	
	private ItemCostConditionDto combileDtoParams(ItemCost itemCost) throws ServiceException{
		int year = Integer.valueOf(itemCost.getYear());
		int month = Integer.valueOf(itemCost.getMonth());
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getFirstDayOfMonth(year, month));
		// 先获取上月所属年月
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		
		ItemCostConditionDto dto = new ItemCostConditionDto();
		dto.setCompanyNo(itemCost.getCompanyNo());
		dto.setCompanyName(itemCost.getCompanyName());
		dto.setStartDate(DateUtil.getFirstDayOfMonth(year, month));
		dto.setEndDate(DateUtil.getLastDayOfMonth(year, month));
		dto.setCurrentYear(itemCost.getYear());
		dto.setCurrentMonth(itemCost.getMonth());
		dto.setLastYear(lastYear);
		dto.setLastMonth(lastMonth);
		dto.setCreateUser(itemCost.getCreateUser());
		dto.setCreateTime(itemCost.getCreateTime());
		dto.setBrandUnitNo(itemCost.getBrandUnitNo());
		dto.setBrandUnitName(itemCost.getBrandUnitName());

		if (StringUtils.isNotEmpty(itemCost.getBrandUnitNo())) {
			Map<String, Object> brandParam = new HashMap<>();
			brandParam.put("brandUnitNo", itemCost.getBrandUnitNo());
			List<Brand> brands = brandService.findByBiz(null, brandParam);

			List<String> brandNos = new ArrayList<String>();
			for (Brand brand : brands) {
				brandNos.add(brand.getBrandNo());
			}

			dto.setBrandNos(brandNos);
		}
		
		List<String> styleNos = new ArrayList<String>();
		if(StringUtils.isNotEmpty(itemCost.getStyleNos())){
			styleNos = Arrays.asList(itemCost.getStyleNos().split(","));
		}
		
		if(styleNos != null && styleNos.size()>0){
			StringBuffer sb = new StringBuffer(" AND style_no IN (");
			for (String styleNo : styleNos) {
				sb.append("'"+styleNo+"',");
			}
			sb = sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("queryCondition", String.valueOf(sb));
			List<Item> items = itemService.findByBiz(null, temp);
			List<String> itemNos = new ArrayList<String>();
			for (Item item : items) {
				if(!itemNos.contains(item.getItemNo())){
					itemNos.add(item.getItemNo());
				}
			}
			dto.setItemNos(itemNos);
		}
		
		String shardingFlag = "";
		String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
		if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){// 集团总部
			shardingFlag = "0_Z";
		}else{
			shardingFlag = organTypeNo + "_" + dto.getCompanyNo().substring(0, 1);
		}
		dto.setShardingFlag(shardingFlag);
		
		String isPE = String.valueOf(ShardingUtil.isPEByShardingFlag(shardingFlag));
		dto.setIsPE(isPE);
		
		return dto;
	}	
	
	public class GenerateCostAndPeriodCaseThread implements Runnable {
		private final XLogger LOGGER = XLoggerFactory.getXLogger(GenerateCostAndPeriodCaseThread.class);
		
		private BLKItemCostManager blkItemCostManager;
		
		private ItemCostConditionDto dto;
		
		private SystemUser systemUser;
		
		private ItemCost itemCost;
		
		public GenerateCostAndPeriodCaseThread(BLKItemCostManager blkItemCostManager,ItemCostConditionDto dto,SystemUser systemUser,ItemCost itemCost){
			this.blkItemCostManager = blkItemCostManager;
			this.dto = dto;
			this.systemUser = systemUser;
			this.itemCost = itemCost;
		}
		
		@Override
		public void run() {
			try {
				Authorization.setUser(systemUser);
				LOGGER.info("###################### 开始批量生成公司"+dto.getCompanyNo()+"品牌部"+dto.getBrandUnitNo()+"的加权成本：  ##############################");
				long start = System.currentTimeMillis();
				blkItemCostManager.generateCostAndPeriodCase(dto, itemCost);
				long end = System.currentTimeMillis();
				System.out.println("##################生成成本消耗的时间############################");
				System.out.println(end - start);
				LOGGER.info("###################### success： 批量生成公司"+dto.getCompanyNo()+"品牌部"+dto.getBrandUnitNo()+"的加权成本成本！！！  ##############################");
			} catch (ManagerException e) {
				LOGGER.error(dto.getCompanyNo()+"品牌部"+dto.getBrandUnitNo()+"生成结存数据、加权成本失败",e);
			} 
		}
	}

	@Override
	public int findBLKItemCostCount(Map<String, Object> params) throws ManagerException {
		try {
			return itemCostService.findBLKItemCostCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ItemCost> findBLKItemCostList(SimplePage page, String orderBy,
			String orderByField, Map<String, Object> params) throws ManagerException {
		try {
			return itemCostService.findBLKItemCostList(page, orderBy, orderByField, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}
