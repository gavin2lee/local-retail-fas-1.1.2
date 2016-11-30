package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.common.model.PriceQuotationList;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.common.vo.ItemPriceCost;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.CompanyPeriodBalanceService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.FinancialAccountService;
import cn.wonhigh.retail.fas.service.HeadquarterCostMaintainService;
import cn.wonhigh.retail.fas.service.ItemCostService;
import cn.wonhigh.retail.fas.service.ItemService;
import cn.wonhigh.retail.fas.service.PeriodBalanceAuditService;
import cn.wonhigh.retail.fas.service.PeriodBalanceService;
import cn.wonhigh.retail.fas.service.PriceQuotationListService;
import cn.wonhigh.retail.fas.service.PurchasePriceService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;
import cn.wonhigh.retail.gms.api.service.CalculateWeightedCostApi;
import cn.wonhigh.retail.gms.api.service.CloseAccountApi;
import cn.wonhigh.retail.gms.common.dto.PeriodBalanceManagerAuditDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author wang.xy1
 * @date 2014-08-25 13:45:42
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("itemCostManager")
class ItemCostManagerImpl extends BaseCrudManagerImpl implements ItemCostManager {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ItemCostManagerImpl.class);

	@Resource
	private PeriodBalanceService periodBalanceService;

	@Resource
	private PeriodBalanceAuditService periodBalanceAuditService;

	@Resource
	private CompanyPeriodBalanceService companyPeriodBalanceService;

	@Resource
	private ItemCostService itemCostService;

	@Resource
	private CalculateWeightedCostApi calculateWeightedCostApi;

	@Resource
	private CloseAccountApi closeAccountApi;

	@Resource
	private BrandService brandService;
	
	@Resource
	private BillBuyBalanceService billBuyBalanceService;
	
	@Override
	protected BaseCrudService init() {
		return itemCostService;
	}

    //成本新方案
    public boolean checkHasBillException(ItemCost itemCost) throws ManagerException{
    	try {
    		if (StringUtils.isEmpty(itemCost.getCompanyNo()) || StringUtils.isEmpty(itemCost.getYear())
    				|| StringUtils.isEmpty(itemCost.getMonth())) {
    			LOGGER.error("必要参数为空！！");
				return false;
			}
    		Map<String, Object> params = new HashMap<String, Object>();
    		ItemCostConditionDto dto = combileDtoParams(itemCost, params);
    		new Thread(new GenerateCostAndPeriodCaseThread(this, dto, params, itemCost)).start();
			
			return true;
		} catch (Exception e) {
			LOGGER.error("生成成本异常", e);
			throw new ManagerException(e);
		}
    }
	
    @Override
	public void generateCostAndPeriodCase(ItemCostConditionDto dto,Map<String, Object> params,ItemCost itemCost) throws ManagerException{
		Map<String, Object> auduitParams = new HashMap<String, Object>();
		int isSuccess = 1; //1表示生成成功，2表示生成失败
		try {
			// 先获取本月所属年月
			Calendar cal = Calendar.getInstance();
			cal.setTime(dto.getStartDate());
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
			String startDate = DateUtil.getFirstDayOfMonthStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
			String endDate = DateUtil.getLastDayOfMonthStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

			// 先获取上月所属年月
			cal.add(Calendar.MONTH, -1);
			String preYear = String.valueOf(cal.get(Calendar.YEAR));
			String preMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

			auduitParams.put("companyNo", dto.getCompanyNo());
			auduitParams.put("year", year);
			auduitParams.put("month", month);
			auduitParams.put("brandUnitNo", dto.getBrandUnitNo());
			auduitParams.put("status", String.valueOf(0));
			List<PeriodBalanceAudit> existsAudits = periodBalanceAuditService.findByBiz(null, auduitParams);
			if (!CollectionUtils.isEmpty(existsAudits)) {
				return;
			}
			auduitParams.put("id", -1);
			auduitParams.put("brandUnitName", dto.getBrandUnitName());
			auduitParams.put("createUser", dto.getCreateUser());
			auduitParams.put("createTime", dto.getCreateTime());

			periodBalanceAuditService.auditPeriodBalance(auduitParams, false);

			params.put("year", preYear);
			params.put("month", preMonth);
			int totalCount = companyPeriodBalanceService.selectWeightedCostCount(params);
			List<String> lists=new ArrayList<String>();
			HashMap<String,CompanyPeriodBalance> periodBalanceHashMap=null;
			HashMap<String,BillBuyBalance> billBuyBalanceHashMap=null;
			int pageSize = totalCount; //查询总数
			int pageNo = 1;// 当前页数
			SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
			//获取上月期末
			List<CompanyPeriodBalance> periodBalances =companyPeriodBalanceService.selectTheFinalCostByPage(page, "", "", params, null);
			//本月买卖
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("isPE", String.valueOf(ShardingUtil.isPEByShardingFlag(dto.getShardingFlag())));
			List<BillBuyBalance> billBuyBalances=billBuyBalanceService.selectPurchaseRegionCost(params);
			periodBalanceHashMap = companyPeriodBalanceClassTypeHashMap(periodBalances,lists);
			billBuyBalanceHashMap = billBuyBalanceClassTypeHashMap(billBuyBalances,lists);
			List<String> list1=this.removeRepeat(lists); //去除重复item_no
			
			dto.setCurrentYear(year);
			dto.setCurrentMonth(month);
			dto.setLastYear(preYear);
			dto.setLastMonth(preMonth);
			dto.setIsPE(String.valueOf(ShardingUtil.isPEByShardingFlag(dto.getShardingFlag())));
			
			//生成成本
			LOGGER.info("################ "+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部生成成本开始   ###################");
			params.put("year", year);
			params.put("month", month);
			if(list1.size()>0){
				periodBalanceService.generateItemCost(list1,dto,params,periodBalanceHashMap,billBuyBalanceHashMap);
			}else{
				LOGGER.info("####公司"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部上月期末无结存或本月未发生买卖####");
			}
			
			this.generatePeriodBalance(dto,params);//生成店铺结存和公司结存
		
		} catch (ServiceException e) {
			isSuccess =2;
			throw new ManagerException(e.getMessage(), e);
		} finally{
			this.updatePeriodBalanceAudit(auduitParams, dto, isSuccess); //更新生成成本日志状态
		}

	}
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void generatePeriodBalance(ItemCostConditionDto dto, Map<String, Object> params) throws ServiceException {
		
    	this.shopPeriodBalanceMenthod(dto,params);//按店铺生成结存

		this.companyPeriodBalanceMenthod(dto,params);//按公司生成结存
	}

	/**
	 * 按照店铺生成结存
	 * @throws ServiceException 
	 */
	public void shopPeriodBalanceMenthod(ItemCostConditionDto dto,Map<String, Object> params) throws ServiceException{
		// 先删除本公司,本月的所有结存数据
		periodBalanceService.deleteCompanyMonthPeriodBalance(params);
		
		//生成本月结存
		periodBalanceService.transferInventoryBookToPeriodBalance(dto, params);
		
		//回写地区价到店铺结存入库类金额
		periodBalanceService.writeBackAmount(dto);
		
		// 回写单位成本到店铺结存表的出库类汇总金额
		periodBalanceService.itemCostFeedbackToPeriodBalance(params);
	}
	
	/**
	 * 按照公司生成结存
	 * @throws ServiceException 
	 */
	public void companyPeriodBalanceMenthod(ItemCostConditionDto dto,Map<String, Object> params) throws ServiceException{
		//生成本月结存
		companyPeriodBalanceService.generateBalancePreData(dto, params);
		
		//回写地区价到公司期间结存入库类金额
		companyPeriodBalanceService.writeBackAmount(dto);
		
		// 回写单位成本到公司结存表的出库类汇总金额
		companyPeriodBalanceService.itemCostFeedbackToPeriodBalance(params);
		
		//重刷库存调整金额
		companyPeriodBalanceService.writeBackCostAdjustmentAmout(params);
	}
	
    
	
	/**
	 * 将上月期末结存数据存入HashMap，并且截取itemNo存入另一个的List
	 * 
	 * @param list
	 * @param lists
	 * @return
	 */
	public HashMap<String,CompanyPeriodBalance> companyPeriodBalanceClassTypeHashMap(List<CompanyPeriodBalance> list,List<String> lists){
		HashMap<String,CompanyPeriodBalance> companyPeriodBalanceHashMap=new HashMap<String,CompanyPeriodBalance>();
		for(CompanyPeriodBalance companyPeriodBalance:list){
			companyPeriodBalanceHashMap.put(companyPeriodBalance.getItemNo(), companyPeriodBalance);
			lists.add(companyPeriodBalance.getItemNo());
		}
		return companyPeriodBalanceHashMap;
	}

	/**
	 * 将本月采购、调货数据存入HashMap，并且截取itemNo存入另一个的List
	 * 
	 * @param list
	 * @param lists
	 * @return
	 */
	public HashMap<String,BillBuyBalance> billBuyBalanceClassTypeHashMap(List<BillBuyBalance> list,List<String> lists){
		HashMap<String,BillBuyBalance>  billBuyBalanceHashMap=new HashMap<String,BillBuyBalance>();
		for(BillBuyBalance billBuyBalance:list){
			billBuyBalanceHashMap.put(billBuyBalance.getItemNo(), billBuyBalance);
			lists.add(billBuyBalance.getItemNo());
		}
		return billBuyBalanceHashMap;
	}

	/**
	 * 去除重复方法
	 * 
	 * @param list
	 * @return
	 */
	private List<String> removeRepeat(List<String> l){
		HashSet<String> hs = new HashSet<String>(l);
		List<String> list = new ArrayList<String>(hs); 
		return list;
	}
	
	//新方案类型转换
	private ItemCostConditionDto combileDtoParams(ItemCost itemCost, Map<String, Object> params) throws ServiceException{
		
		params.put("companyNo", itemCost.getCompanyNo());
		params.put("year", itemCost.getYear());
		params.put("month", itemCost.getMonth());

		int year = Integer.valueOf(itemCost.getYear());
		int month = Integer.valueOf(itemCost.getMonth());

		ItemCostConditionDto dto = new ItemCostConditionDto();
		dto.setCompanyNo(itemCost.getCompanyNo());
		dto.setStartDate(DateUtil.getFirstDayOfMonth(year, month));
		dto.setEndDate(DateUtil.getLastDayOfMonth(year, month));
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
			params.put("brandNos", brandNos);
		}
		if (StringUtils.isNotEmpty(itemCost.getItemNos())) {
			dto.setItemNos(Arrays.asList(itemCost.getItemNos().split(",")));
			params.put("itemNos", Arrays.asList(itemCost.getItemNos().split(",")));
		}
		
		String shardingFlag = "";
		String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
		if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){// 集团总部
			shardingFlag = "0_Z";
		}else{
			shardingFlag = organTypeNo + "_" + dto.getCompanyNo().substring(0, 1);
		}
		dto.setShardingFlag(shardingFlag);
		params.put("shardingFlag", shardingFlag);

		return dto;
	}
	
	

	/*
	 * @Override public boolean checkGenerateCondition(ItemCost itemCost) throws
	 * ManagerException { try { boolean canDo = true; PeriodBalanceDTO
	 * periodBalanceDTO = new PeriodBalanceDTO();
	 * periodBalanceDTO.setCompanyNo(itemCost.getCompanyNo()); Calendar calendar
	 * = new GregorianCalendar(); DateFormat df = new
	 * SimpleDateFormat("yyyy-MM-dd"); Date date = df.parse(itemCost.getYear() +
	 * "-" + itemCost.getMonth() + "-" + "1"); calendar.setTime(date);
	 * calendar.add(Calendar.MONTH, -1); String lastYear =
	 * String.valueOf(calendar.get(Calendar.YEAR)); String lastMonth =
	 * String.valueOf(calendar.get(Calendar.MONTH) + 1);
	 * //查询该公司是否有结存数据，判断是否为库存初始化 int count =
	 * calculateWeightedCostApi.queryPeriodBalanceCountByParams
	 * (periodBalanceDTO); if (count > 0) { //判断该公司上月是否已经有结存数据
	 * periodBalanceDTO.setYear(lastYear); periodBalanceDTO.setMonth(lastMonth);
	 * count =
	 * calculateWeightedCostApi.queryPeriodBalanceCountByParams(periodBalanceDTO
	 * ); if (count <= 0) { canDo = false; } } return canDo; }catch
	 * (ParseException e) { e.printStackTrace(); throw new ManagerException(); }
	 * catch (RpcException e) { e.printStackTrace();
	 * LOGGER.error("调用GMS分页查询单据异常接口失败"); throw new
	 * ManagerException("调用GMS分页查询单据异常接口失败"); } }
	 */

	@Override
	public int findItemCostUnmatchRegionPriceCount(Map<String, Object> params) throws ManagerException {
		try {
			return itemCostService.findItemCostUnmatchRegionPriceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ItemCost> findItemCostUnmatchRegionPriceByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return itemCostService.findItemCostUnmatchRegionPriceByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findItemsCostAuditCount(PeriodBalanceManagerAuditDto params) throws ManagerException {
		try {
			return calculateWeightedCostApi.countPeriodBalanceManagerAudit(params);
		} catch (RpcException e) {
			LOGGER.error("调用GMS检验是否存在单据异常接口");
			throw new ManagerException("调用GMS检验是否存在单据异常接口");
		}
	}

	@Override
	public List<PeriodBalanceManagerAuditDto> findItemsCostAudit(SimplePage page, PeriodBalanceManagerAuditDto params)
			throws ManagerException {
		try {
			return calculateWeightedCostApi.queryPeriodBalanceManagerAudit(page, params);
		} catch (RpcException e) {
			LOGGER.error("调用GMS检验是否存在单据异常接口");
			throw new ManagerException("调用GMS检验是否存在单据异常接口");
		}
	}

	
//	@Override
//	public int assignBillItemCostSchedule(Company company, Date start, Date end) throws ManagerException {
//		Map<String, Object> params = getUpdateCostParams(company, start, end);
//
//		Date todayDate = new Date();
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(todayDate);
//		cal.add(Calendar.DAY_OF_MONTH, -1);
//		todayDate = cal.getTime();
//
//		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
//		String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
//		params.put("year", currentYear);
//		params.put("month", currentMonth);
//		try {
//			itemCostService.updateItemCostSchedule(params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//		return 1;
//	}
//
//	@Override
//	public int assignBillRegionCostSchedule(Company company, Date start, Date end) throws ManagerException {
//		Map<String, Object> params = getUpdateCostParams(company, start, end);
//		try {
//			itemCostService.updateRegionCostSchedule(params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//		return 1;
//	}
//
//	@Override
//	public int assignBillHQCostSchedule(Company company, Date start, Date end) throws ManagerException {
//		Map<String, Object> params = getUpdateCostParams(company, start, end);
//		try {
//			LOGGER.info("更新POS订单表总部成本价");
//			itemCostService.updatePOSOrderHQSchedule(params);
//			LOGGER.info("更新POS退换货表总部成本价");
//			itemCostService.updateReturnExchangeHQSchedule(params);
//			LOGGER.info("更新BILL_BUY_BALANCE表总部成本价");
//			itemCostService.updateBuyBalanceHeadquaterCost(params);
//			LOGGER.info("更新BILL_SALE_BALANCE表总部成本价");
//			itemCostService.updateSaleBalanceHeadquaterCost(params);
//
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//		return 1;
//	}

	@Override
	public boolean checkGMSClosingDate(ItemCost itemCost) throws ManagerException {
		boolean result = false;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(itemCost.getYear() + "-" + itemCost.getMonth() + "-" + "1");
			result = closeAccountApi.checkCloseResultByCompany(itemCost.getCompanyNo(), date);
		} catch (Exception e) {
			LOGGER.error("检查GMS机构的关账日失败。", e);
			throw new ManagerException(e);
		}
		return result;
	}

	/**
	 * 生成机构维度的结存
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void generateStoreItemCost(ItemCostConditionDto conditionDto) throws ManagerException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(conditionDto.getStartDate());
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
		String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

		Map<String, Object> currentMap = new HashMap<String, Object>();
		currentMap.put("shardingFlag", conditionDto.getShardingFlag());
		currentMap.put("companyNo", conditionDto.getCompanyNo());
		currentMap.put("year", currentYear);
		currentMap.put("month", currentMonth);

		if (!CollectionUtils.isEmpty(conditionDto.getBrandNos())) {
			currentMap.put("brandNos", conditionDto.getBrandNos());
		}
		if (!CollectionUtils.isEmpty(conditionDto.getItemNos())) {
			currentMap.put("itemNos", conditionDto.getItemNos());
		}

		// 获取本公司上月的所有结存数据
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

		conditionDto.setCurrentYear(currentYear);
		conditionDto.setCurrentMonth(currentMonth);
		conditionDto.setLastYear(lastYear);
		conditionDto.setLastMonth(lastMonth);

		try {

			periodBalanceService.accountMonthAllData(conditionDto, currentMap);

			periodBalanceService.generateMonthEndWeightedCost(conditionDto, currentMap);
			// periodBalanceService.itemCostFeedbackToPeriodBalance(conditionDto,
			// currentMap);

		} catch (ServiceException e) {
			LOGGER.error("生成期间结存失败", e);
			throw new ManagerException(e);
		}
	}

	/**
	 * 生成公司维度的结存
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void generateCompanyItemCost(ItemCostConditionDto conditionDto) throws ManagerException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(conditionDto.getStartDate());
			String currentYear = String.valueOf(cal.get(Calendar.YEAR));
			String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

			Map<String, Object> currentMap = new HashMap<String, Object>();
			currentMap.put("shardingFlag", conditionDto.getShardingFlag());
			currentMap.put("companyNo", conditionDto.getCompanyNo());
			currentMap.put("year", currentYear);
			currentMap.put("month", currentMonth);

			if (!CollectionUtils.isEmpty(conditionDto.getBrandNos())) {
				currentMap.put("brandNos", conditionDto.getBrandNos());
			}
			if (!CollectionUtils.isEmpty(conditionDto.getItemNos())) {
				currentMap.put("itemNos", conditionDto.getItemNos());
			}

			// 获取本公司上月的所有结存数据
			cal.add(Calendar.MONTH, -1);
			String lastYear = String.valueOf(cal.get(Calendar.YEAR));
			String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

			conditionDto.setCurrentYear(currentYear);
			conditionDto.setCurrentMonth(currentMonth);
			conditionDto.setLastYear(lastYear);
			conditionDto.setLastMonth(lastMonth);

			preGenerateBalanceData(conditionDto, currentMap);

			companyPeriodBalanceService.transferLastPeriodBalance(conditionDto, currentMap);

			companyPeriodBalanceService.generateWeightedCost(conditionDto, currentMap);

		} catch (ServiceException e) {
			LOGGER.error("生成期间结存失败", e);
			throw new ManagerException(e);
		}
	}

	private void preGenerateBalanceData(ItemCostConditionDto conditionDto, Map<String, Object> currentMap)
			throws ManagerException {
		try {

			companyPeriodBalanceService.deleteCompanyMonthPeriodBalance(currentMap);

			companyPeriodBalanceService.summaryInventoryBook(conditionDto);

		} catch (ServiceException e) {
			LOGGER.error("生成期间结存失败", e);
			throw new ManagerException(e);
		}
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
	
	public class GenerateCostAndPeriodCaseThread implements Runnable {
		private final XLogger LOGGER = XLoggerFactory.getXLogger(GenerateCostAndPeriodCaseThread.class);
		
		private ItemCostManager itemCostManager;
		
		private ItemCostConditionDto dto;
		
		private Map<String, Object> params;
		
		private ItemCost itemCost;
		private String userName;
		
		public GenerateCostAndPeriodCaseThread(ItemCostManager itemCostManager,ItemCostConditionDto dto,Map<String, Object> params,ItemCost itemCost){
			this.itemCostManager = itemCostManager;
			this.dto = dto;
			this.params = params;
			this.itemCost = itemCost;
			this.userName =  Authorization.getUser().getLoginName();
		}
		
		@Override
		public void run() {
			try {
				String name = Thread.currentThread().getName();
				Thread.currentThread().setName(name +"&" + userName);
				LOGGER.info("###################### 开始批量生成公司"+dto.getCompanyNo()+"品牌部"+dto.getBrandUnitNo()+"的加权成本：  ##############################");
				long start = System.currentTimeMillis();
				itemCostManager.generateCostAndPeriodCase(dto, params, itemCost);
				long end = System.currentTimeMillis();
				System.out.println("##################生成成本消耗的时间############################");
				System.out.println(end - start);
				LOGGER.info("###################### success： 批量生成公司"+dto.getCompanyNo()+"品牌部"+dto.getBrandUnitNo()+"的加权成本成本！！！  ##############################");
			} catch (ManagerException e) {
				LOGGER.error(dto.getCompanyNo()+"品牌部"+dto.getBrandUnitNo()+"生成结存数据、加权成本失败",e);
			} 
		}
	}
	
	@Resource
	private RegionCostMaintainService regionCostMaintainService;

	@Resource
	private HeadquarterCostMaintainService headquarterCostMaintainService;

	@Resource
	private PriceQuotationListService priceQuotationListService;

	@Resource
	private PurchasePriceService purchasePriceService;

	@Resource
	private ItemService itemService;

	@Resource
	private FinancialAccountService financialAccountService;
	
	@Resource
	private CompanyService companyService;

	@Override
	public ItemPriceCost getItemPriceAndCost(String companyNo, String itemNo, Date outDate) throws ManagerException {
		try {
			String priceZoneNo = getPriceZone(companyNo);
			ItemPriceCost result = getItemAllPrice(priceZoneNo, itemNo, outDate);

			// 查询单位成本
			BigDecimal unitCost = getItemCost(companyNo, itemNo, outDate);
			result.setUnitCost(unitCost);
			return result;

		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public BigDecimal getItemCost(String companyNo, String itemNo, Date date) throws ManagerException {
		try {
			ItemCost itemCost = itemCostService.getItemCost(companyNo, itemNo, date);
			if (itemCost != null && itemCost.getUnitCost() != null)
				return itemCost.getUnitCost();
			else
				return null;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	public ItemPriceCost getItemAllPrice(String zoneNo, String itemNo, Date outDate) throws ManagerException {
		try {
			return getItemPrice(zoneNo, itemNo, outDate);
		} catch (Exception e) {
			throw new ManagerException(e);
		}

	}

	private ItemPriceCost getItemPrice(String zoneNo, String itemNo, Date outDate) throws ServiceException {
		ItemPriceCost result = new ItemPriceCost();

		// 查询地区价
		RegionCostMaintain regionCostMaintain = regionCostMaintainService.getRegionCost(zoneNo, itemNo, outDate);
		if (regionCostMaintain != null && regionCostMaintain.getRegionCost() != null) {
			BigDecimal val = regionCostMaintain.getRegionCost();
			result.setRegionCost(val);
			result.setUnitCost(val);
		}
		// 查询总部价
		HeadquarterCostMaintain headquarterCostMaintain = headquarterCostMaintainService.getHeadquarterCost(itemNo,
				outDate);
		if (headquarterCostMaintain != null && headquarterCostMaintain.getHeadquarterCost() != null) {
			BigDecimal headquarterCost = headquarterCostMaintain.getHeadquarterCost();
			result.setHeadquarterCost(headquarterCost);
		}

		// 查询全国统一牌价
		PriceQuotationList priceQuotationList = priceQuotationListService.getPriceQuotationList("0000", itemNo);
		if (priceQuotationList != null && priceQuotationList.getTagPrice() != null) {
			BigDecimal tagPriceNation = priceQuotationList.getTagPrice();
			result.setTagPriceNation(tagPriceNation);
		}

		// 查询采购价、物料价、厂进价
		Map<String, Object> itemParam = new HashMap<String, Object>();
		itemParam.put("itemNo", itemNo);
		List<Item> itemList = itemService.findByBiz(null, itemParam);
		if (!CollectionUtils.isEmpty(itemList)) {
			PurchasePrice purchasePriceObj = purchasePriceService.findBalancePurchasePrice(itemNo, itemList.get(0)
					.getSupplierNo(), outDate);
			if (purchasePriceObj != null && purchasePriceObj.getPurchasePrice() != null) {
				BigDecimal purchasePrice = purchasePriceObj.getPurchasePrice();
				result.setPurchasePrice(purchasePrice);
			}
			if (purchasePriceObj != null && purchasePriceObj.getMaterialPrice() != null) {
				BigDecimal materialPrice = purchasePriceObj.getMaterialPrice();
				result.setMaterialPrice(materialPrice);
			}
			if (purchasePriceObj != null && purchasePriceObj.getFactoryPrice() != null) {
				BigDecimal factoryPrice = purchasePriceObj.getFactoryPrice();
				result.setFactoryPrice(factoryPrice);
			}
		}
		return result;
	}

	private String getPriceZone(String companyNo) throws ServiceException {
		String priceZoneNo = null;
		Map<String, Object> financeAccountMap = new HashMap<String, Object>();
		financeAccountMap.put("companyNo", companyNo);
		financeAccountMap.put("status", 1);
		financeAccountMap.put("groupLeadRole", 0);

		List<FinancialAccount> financialAccounts = financialAccountService.findByBiz(null, financeAccountMap);
		if (!CollectionUtils.isEmpty(financialAccounts)) {
			priceZoneNo = financialAccounts.get(0).getPriceZone();
		}
		if (StringUtils.isEmpty(priceZoneNo)) {
			Map<String, Object> companyMap = new HashMap<String, Object>();
			companyMap.put("companyNo", companyNo);
			companyMap.put("status", 1);

			List<Company> companies = companyService.findByBiz(null, companyMap);
			if (!CollectionUtils.isEmpty(companies)) {
				priceZoneNo = companies.get(0).getZoneNo();
			}
		}
		return priceZoneNo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void assignBillItemCost(Company company,String brandUnitNo,Date start ,Date end)
			throws ManagerException {
		try {
			itemCostService.updateItemCost(company,brandUnitNo,start,end);
		} catch (ServiceException e) { 
			throw new ManagerException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void assignBillRegionCost(Company company,String brandUnitNo,Date start ,Date end) throws ManagerException {
	 
		try {
			itemCostService.updateRegionCost(company,brandUnitNo,start,end);
		} catch (ServiceException e) { 
			throw new ManagerException(e);
		}
	 
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void assignBillHQCost(Company company,String brandUnitNo,Date start ,Date end) throws ManagerException {
	 
		try {
			itemCostService.updateHeadquaterCost(company,brandUnitNo,start,end);
		} catch (ServiceException e) { 
			throw new ManagerException(e);
		}
	 
	}

	@Override
	public boolean generateBLKItemCost(ItemCost itemCost)
			throws ManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void genearateSelfUnitCost(ItemCost itemCost) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			ItemCostConditionDto dto = combileDtoParams(itemCost, params);
			// 先获取本月所属年月
			Calendar cal = Calendar.getInstance();
			cal.setTime(dto.getStartDate());
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
			String startDate = DateUtil.getFirstDayOfMonthStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
			String endDate = DateUtil.getLastDayOfMonthStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

			// 先获取上月所属年月
			cal.add(Calendar.MONTH, -1);
			String preYear = String.valueOf(cal.get(Calendar.YEAR));
			String preMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);

			params.put("year", preYear);
			params.put("month", preMonth);
			int totalCount = companyPeriodBalanceService.selectWeightedCostCount(params);
			List<String> lists=new ArrayList<String>();
			HashMap<String,CompanyPeriodBalance> periodBalanceHashMap=null;
			HashMap<String,BillBuyBalance> billBuyBalanceHashMap=null;
			int pageSize = totalCount; //查询总数
			int pageNo = 1;// 当前页数
			SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
			//获取上月期末
			List<CompanyPeriodBalance> periodBalances =companyPeriodBalanceService.selectTheFinalCostByPage(page, "", "", params, null);
			//本月买卖
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("isPE", String.valueOf(ShardingUtil.isPEByShardingFlag(dto.getShardingFlag())));
			List<BillBuyBalance> billBuyBalances=billBuyBalanceService.selectPurchaseRegionCost(params);
			periodBalanceHashMap = companyPeriodBalanceClassTypeHashMap(periodBalances,lists);
			billBuyBalanceHashMap = billBuyBalanceClassTypeHashMap(billBuyBalances,lists);
			List<String> list1=this.removeRepeat(lists); //去除重复item_no
			
			dto.setCurrentYear(year);
			dto.setCurrentMonth(month);
			dto.setLastYear(preYear);
			dto.setLastMonth(preMonth);
			dto.setIsPE(String.valueOf(ShardingUtil.isPEByShardingFlag(dto.getShardingFlag())));
			
			//生成成本
			LOGGER.info("################"+dto.getCompanyNo()+"公司"+year+"年"+month+"月"+dto.getBrandUnitNo()+"品牌部生成成本开始   ###################");
			params.put("year", year);
			params.put("month", month);
			if(list1.size()>0){
				periodBalanceService.generateItemCost(list1,dto,params,periodBalanceHashMap,billBuyBalanceHashMap);
			}else{
				LOGGER.info("####公司"+ dto.getCompanyNo() +"上月期末无结存或本月未发生买卖####");
			}
			
			//按店铺生成结存
			LOGGER.info("################ 生成店铺结存开始   ###################");
			this.shopPeriodBalanceMenthod(dto,params);

			//按公司生成结存
			LOGGER.info("################ 生成公司结存开始   ###################");
			this.companyPeriodBalanceMenthod(dto,params);
			
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		} 
	}

	@Override
	public void runSql(Map<String, Object> params, String type) {
		itemCostService.runSql(params,type);
	}


}