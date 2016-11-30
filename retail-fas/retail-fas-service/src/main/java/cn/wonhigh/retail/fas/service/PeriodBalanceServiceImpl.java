package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.OrgUnitBrandRel;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.dal.database.BillPurchaseAdjustMapper;
import cn.wonhigh.retail.fas.dal.database.CompanyMapper;
import cn.wonhigh.retail.fas.dal.database.ItemCostMapper;
import cn.wonhigh.retail.fas.dal.database.OrgUnitBrandRelMapper;
import cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-06 19:05:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("periodBalanceService")
class PeriodBalanceServiceImpl extends BaseCrudServiceImpl implements PeriodBalanceService {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(PeriodBalanceServiceImpl.class);
	
	@Resource
	private PeriodBalanceMapper periodBalanceMapper;

	@Resource
	private ItemCostMapper itemCostMapper;
	
	@Resource
	private CompanyMapper companyMapper;
	
	@Resource
	private OrgUnitBrandRelMapper orgUnitBrandRelMapper;
	
	@Resource
	private BillPurchaseAdjustMapper billPurchaseAdjustMapper;
	
    @Override
    public BaseCrudMapper init() {
        return periodBalanceMapper;
    }

	@Override
	public int findCompanyPeriodBalanceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return periodBalanceMapper.getCompanyPeriodCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<PeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		try {
			return periodBalanceMapper.getCompanyPeriodByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params)
			throws ServiceException {
		return periodBalanceMapper.getCompanyPeriodSubTotalCount(params);
	}

	@Override
	public List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		return periodBalanceMapper.getCompanyPeriodSubTotalByPage(page, orderByField, orderBy, params);
	}
	@Override
	public int findCompanyPeriodBalanceSubTotalCount1(Map<String, Object> params)
			throws ServiceException {
		return periodBalanceMapper.getCompanyPeriodByPageCount(params);
	}

	@Override
	public List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages1(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		return periodBalanceMapper.getCompanyPeriodByPageNo(page, orderByField, orderBy, params);
	}

	@Override
	public List<PeriodBalance> selectTotalRow(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return periodBalanceMapper.selectTotalRow(page, orderByField, orderBy, params);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = ServiceException.class)
	public int deleteCompanyMonthBalance(Map<String, Object> params)
			throws ServiceException {
		try {
			return periodBalanceMapper.deleteCompanyMonthBalance(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		} 
	}
	
	
	/**
	 * 汇总本月结存数量
	 * 
	 * @param invCostGenerateDto
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public void accountMonthAllData(ItemCostConditionDto itemCostConditionDto, Map<String, Object> currentMap) throws ServiceException {
		try {
			LOGGER.info("################ 开始执行店铺结存生产逻辑   ###################");
			// 先删除本公司,本月的所有结存数据
			periodBalanceMapper.deleteCompanyMonthPeriodBalance(currentMap);
						
			//查询公司下的所有订货单位
			HashMap<String,Object> orderUnitMap=new HashMap<String,Object>();
			orderUnitMap.put("companyNo", itemCostConditionDto.getCompanyNo());
			orderUnitMap.put("brandNos", itemCostConditionDto.getBrandNos());
			List<OrgUnitBrandRel> list=orgUnitBrandRelMapper.selectByOrderUnitNoParams(orderUnitMap);
			List<String> orderUnitNos = new ArrayList<String>();
			for(OrgUnitBrandRel orgUnitBrandRel:list){
				orderUnitNos.add(orgUnitBrandRel.getOrderUnitNo());
			}
			
			itemCostConditionDto.setOrderUnitNo(FasUtil.formatInQueryCondition(StringUtils.join(orderUnitNos,",")));
			periodBalanceMapper.transferInventoryBookToPeriodBalance(itemCostConditionDto);// 从流水表汇总出 本期发生的业务结存数据 到结存表
			// 结转公司上期存在的结存，而本期没有发生业务的数据到本月
			//periodBalanceMapper.transferLastPeriodBalanceToCurrent(itemCostConditionDto);
			LOGGER.info("################ 执行完成店铺结存生成逻辑   ###################");
		} catch (Exception e) {
			LOGGER.error("生成结存失败", e);
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void generateMonthEndWeightedCost(ItemCostConditionDto invCostGenerateDto, Map<String, Object> currentMap) throws ServiceException {
		try {
			LOGGER.info("################ 开始执行生成成本逻辑   ###################");
			// 删除本期的所有成本，成本调整的不用删
			itemCostMapper.deleteCurrentMonthCost(currentMap);
			
			// 计算本期入库类单据的成本
			calculateItemCostFromPeriodBalance(currentMap, invCostGenerateDto);
			
			// 结转上期的存在的成本，而本期未重新生成的成本到本期
			itemCostMapper.transferLastItemCostToCurrentMonth(invCostGenerateDto);
			
			currentMap.put("createUser", invCostGenerateDto.getCreateUser());
			currentMap.put("createTime", new Date() );
			itemCostMapper.updateItemCostInfo(currentMap);
			
			// 回写成本到结存表的出库类汇总金额
			itemCostFeedbackToPeriodBalance(currentMap);
			
			LOGGER.info("################ 成本生成执行完毕，成本已成功回写到结存   ###################");
		} catch (Exception e) {
			LOGGER.error("生成店铺结存失败。", e);
			throw new ServiceException(e);
		}
		
	}

	private void calculateItemCostFromPeriodBalance(Map<String, Object> currentMap, ItemCostConditionDto invCostGenerateDto) {
		// 分批获取本期结存 已经汇总的入库类单据的数据
		int totalCount = periodBalanceMapper.selectWeightedCostCount(currentMap);
		if (totalCount <= 0) {
			return;
		}
		List<String> itemNoList = this.findAdjustedItemCosts(currentMap);//本月已经有过成本调整itemNo列表
				
		int pageSize = 2000;// 每次查询100条
		int pageNo = 1;// 当前页数
		int totalPage = totalCount / pageSize;// 总页数
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}

		List<Company> companies = companyMapper.findAllCompanyWithoutPermission();
		Map<String, String> companyMap = new HashMap<String, String>();
		
		for (Company company : companies) {
			companyMap.put(company.getCompanyNo(), company.getName());
		}
		
		while (pageNo <= totalPage) {
			SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
			// 按公司和商品汇总的各种出入库数量和金额
			List<PeriodBalance> periodBalances = periodBalanceMapper.selectWeightedCostByPage(page, "", "", currentMap, null);
			// 某一个地区需要维护的商品进行价格维护
			List<ItemCost> itemCostList = new ArrayList<ItemCost>();
			
			for (PeriodBalance pBalance : periodBalances) {
				ItemCost itemCost = new ItemCost();
				itemCost.setItemNo(pBalance.getItemNo());
				//本月已经有过成本调整，不需要结转
				if(itemNoList.contains(pBalance.getItemNo())){
					continue;
				}
				
				itemCost.setShardingFlag(invCostGenerateDto.getShardingFlag());
				itemCost.setCompanyNo(pBalance.getCompanyNo());
				itemCost.setCompanyName(companyMap.get(pBalance.getCompanyNo()));
				itemCost.setItemCode(pBalance.getItemCode());
				itemCost.setItemName(pBalance.getItemName());
				itemCost.setBrandNo(pBalance.getBrandNo());
				itemCost.setYear((String) (currentMap.get("year")));
				itemCost.setMonth((String) (currentMap.get("month")));
				itemCost.setCreateUser(invCostGenerateDto.getCreateUser());
				itemCost.setCreateTime(invCostGenerateDto.getCreateTime());
				itemCost.setUpdateUser(invCostGenerateDto.getCreateUser());
				itemCost.setUpdateTime(invCostGenerateDto.getCreateTime());
				
				// 期初金额
				BigDecimal openingBalance = new BigDecimal(0);
				if (null != pBalance.getOpeningBalance()) {
					openingBalance = pBalance.getOpeningBalance();
				}
				// 采购入库金额
				BigDecimal purchaseInAmount = new BigDecimal(0);
				if (null != pBalance.getPurchaseInAmount()) {
					purchaseInAmount = pBalance.getPurchaseInAmount();
				}
				// 跨区调货入库
				BigDecimal outerTransferInAmount = new BigDecimal(0);
				if (null != pBalance.getOuterTransferInAmount()) {
					outerTransferInAmount = pBalance.getOuterTransferInAmount();
				}
				// 采购退厂
				BigDecimal purchaseReturnAmount = new BigDecimal(0);
				if (null != pBalance.getPurchaseReturnAmount()) {
					purchaseReturnAmount = pBalance.getPurchaseReturnAmount();
				}
				// 库存成本调整金额
				BigDecimal costAdjustmentAmount = new BigDecimal(0);
				if (null != pBalance.getCostAdjustmentAmount()) {
					costAdjustmentAmount = pBalance.getCostAdjustmentAmount();
				}
				
				BigDecimal totalAmountDecimal = openingBalance.add(purchaseInAmount).add(outerTransferInAmount)
						.add(purchaseReturnAmount).add(costAdjustmentAmount);

				Integer totalQty = (null == pBalance.getOpeningQty() ? 0 : pBalance.getOpeningQty())
						+ (null == pBalance.getPurchaseInQty() ? 0 : pBalance.getPurchaseInQty())
						+ (null == pBalance.getOuterTransferInQty() ? 0 : pBalance.getOuterTransferInQty())
						+ (null == pBalance.getPurchaseReturnQty() ? 0 : pBalance.getPurchaseReturnQty());

				BigDecimal totalQtyDecimal = new BigDecimal(totalQty);

				if (totalQty != 0 && totalAmountDecimal.compareTo(new BigDecimal(0)) != 0) {
					//单位成本=总金额/总数量
					itemCost.setUnitCost(totalAmountDecimal.divide(totalQtyDecimal, 2, RoundingMode.HALF_UP));
					itemCostList.add(itemCost);
				}
			}
			if (CollectionUtils.isEmpty(itemCostList)) {
				pageNo++;
				continue;
			}
			itemCostMapper.batchInsertItemCost(itemCostList);
			pageNo++;
		}
	}

	/**
	 * 结转上月发生了而本月未发生业务的结存信息
	 * 
	 * @param invCostGenerateDto
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void itemCostFeedbackToPeriodBalance(Map<String, Object> currentMap)
			throws ServiceException {
		try{
			// 分批获取本期结存 已经汇总的出库类单据的数据
			int totalCount = periodBalanceMapper.queryPeriodBalanceJoinItemCostCount(currentMap);
			if (totalCount <= 0) {
				LOGGER.info("################"+currentMap.get("companyNo")+"公司"+currentMap.get("year")+"年"+currentMap.get("month")+"月"+currentMap.get("brandNos")+"品牌部店铺期间结存查询为0    ###################");
				return;
			}
			int pageSize = 2000;// 每次查询100条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}

			while (pageNo <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				periodBalanceMapper.batchUpdateBalanceItemCostByPage(page, "", "", currentMap);
				pageNo++;
			}
			
			LOGGER.info("################ "+currentMap.get("companyNo")+"公司"+currentMap.get("year")+"年"+currentMap.get("month")+"月"+currentMap.get("brandNos")+"品牌部出库类金额已成功回写到店铺结存    ###################");
		}catch(Exception e){
			LOGGER.error(currentMap.get("companyNo")+"公司"+currentMap.get("year")+"年"+currentMap.get("month")+"月"+currentMap.get("brandNos")+"品牌部成本回写店铺结存失败。", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void storeBalanceExport(SimplePage page,
			Map<String, Object> params, Function<Object, Boolean> handler)
			throws ServiceException {
		String isSubTotal = params.get("isSubTotal") == null ? "" : (String)params.get("isSubTotal");
		String statement = null;
		if (isSubTotal.equals("true")) {
			statement = "cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper.getCompanyPeriodByPageNo";
		}else {
			statement = "cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper.findStoreBalanceList";
		}
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}

	@Override
	public int findSdBalanceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return periodBalanceMapper.selectSdBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<PeriodBalance> findSdBalanceByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		try {
			return periodBalanceMapper.selectSdBalanceByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void storeSdBalanceExport(SimplePage page,
			Map<String, Object> params, Function<Object, Boolean> handler)
			throws ServiceException {
		String statement = "cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper.selectSdBalanceByPage";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}
	
	@Override
	public List<PeriodBalance> selectWeightedCostByPage( SimplePage page,String orderByField,
			String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return periodBalanceMapper.selectWeightedCostByPage(page, orderByField, orderBy, params, authorityParams);
	}

	@Override
	public int selectWeightedCostCount(Map<String, Object> params)
			throws ServiceException {
		return periodBalanceMapper.selectWeightedCostCount(params);
	}

	@Override
	public List<PeriodBalance> selectTheFinalCostByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return periodBalanceMapper.selectTheFinalCostByPage(page, orderByField, orderBy, params, authorityParams);
	}
	
	@Override
	public void writeBackAmount(ItemCostConditionDto invCostGenerateDto)
			throws ServiceException {
		try{
			periodBalanceMapper.batchUpdateWriteBackAmount(invCostGenerateDto);
			LOGGER.info("################ "+invCostGenerateDto.getCompanyNo()+"公司"+invCostGenerateDto.getCurrentYear()+"年"+invCostGenerateDto.getCurrentMonth()+"月"+invCostGenerateDto.getBrandUnitNo()+"品牌部入库数据已成功回写到结存    ###################");
		}catch(Exception e){
			LOGGER.error(invCostGenerateDto.getCompanyNo()+"公司"+invCostGenerateDto.getCurrentYear()+"年"+invCostGenerateDto.getCurrentMonth()+"月"+invCostGenerateDto.getBrandUnitNo()+"品牌部入库数据回写结存失败。", e);
			throw new ServiceException(e);
		}
	}

	
	@Override
	public void generateItemCost(List<String> list, ItemCostConditionDto dto,Map<String, Object> params,
			HashMap<String, CompanyPeriodBalance> periodBalanceHashMap,HashMap<String, BillBuyBalance> billBuyBalanceHashMap)throws ServiceException {
		try{
			//先删除本地的item_cost数据
			itemCostMapper.deleteCompanyMonthCost(dto);
			//本月已经有过成本调整item_no列表 
			List<String> itemNoList = this.findAdjustedItemCosts(params);
			
			List<ItemCost> listCost=new ArrayList<ItemCost>();
			for(String str:list){
				CompanyPeriodBalance periodBalance=periodBalanceHashMap.get(str);
				BillBuyBalance billBuyBalance=billBuyBalanceHashMap.get(str);
				if(itemNoList.contains(str)){
					continue;
				}
				ItemCost itemCost=this.initItemCost(dto,periodBalance,billBuyBalance);//初始化itemCost
				itemCost.setItemNo(str);
				Integer totalQty = Integer.valueOf(0);
				BigDecimal totalAmount = BigDecimal.valueOf(0d);
				if(null != periodBalance && null != periodBalance.getClosingQty()){
					totalQty = totalQty + periodBalance.getClosingQty();
				}
				
				if(null != periodBalance && null != periodBalance.getClosingBalance()){
					totalAmount = totalAmount.add(periodBalance.getClosingBalance());
				}
				
				if(null != billBuyBalance && null != billBuyBalance.getBuyQty()){
					totalQty = totalQty + billBuyBalance.getBuyQty();
				}
				
				if(null != billBuyBalance && null != billBuyBalance.getBuyAmount()){
					totalAmount = totalAmount.add(billBuyBalance.getBuyAmount());
				}
				
				//体类采购入库调整单
				if(ShardingUtil.isPEByShardingFlag(itemCost.getShardingFlag())){
					BigDecimal purchaseInCostAdjustment = billPurchaseAdjustMapper.queryCostAdjustment(itemCost);
					if(purchaseInCostAdjustment!=null){
						totalAmount = totalAmount.add(purchaseInCostAdjustment);
					}
				}
				
				if (totalQty != 0 && totalAmount.compareTo(BigDecimal.ZERO) != 0) {
					//单位成本=总金额/总数量
					itemCost.setUnitCost(totalAmount.divide(BigDecimal.valueOf(totalQty), ShardingUtil.isPEByShardingFlag(itemCost.getShardingFlag())?4:2, RoundingMode.HALF_UP));
					if(billBuyBalance==null){
						itemCost.setRegionCost(periodBalance.getUnitCost());//该商品本月没有买卖
					}else{
						itemCost.setRegionCost(billBuyBalance.getRegionCost());
					}
					listCost.add(itemCost);
				}else{
					LOGGER.info("################ "+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部"+itemCost.getItemCode()+"商品总数量为0或者总金额为0  ###################");
				}
			}
			if(listCost.size()>0){
				itemCostMapper.batchInsertItemCost(listCost);
			}
			// 结转上期的存在的成本，而本期没有重新生成的成本到本期
			itemCostMapper.transferLastItemCostToCurrentMonth(dto);
			
			LOGGER.info("################ "+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部单位成本生成执行完毕  ###################");
		} catch (Exception e) {
			LOGGER.error(dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部生成单位成本失败!", e);
			throw new ServiceException(e);
		}
	
	}

	private ItemCost initItemCost(ItemCostConditionDto dto,
			CompanyPeriodBalance periodBalance, BillBuyBalance billBuyBalance) {
		List<Company> companies = companyMapper.findAllCompanyWithoutPermission();
		Map<String, String> companyMap = new HashMap<String, String>();
		for (Company company : companies) {
			companyMap.put(company.getCompanyNo(), company.getName());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dto.getStartDate());
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
		String currentMonth = String.valueOf(cal.get(Calendar.MONTH)+1);
		
//		ItemCost itemCost = SpringContext.getBean(ItemCost.class);
		ItemCost itemCost = new ItemCost();
		itemCost.setYear(currentYear);
		itemCost.setMonth(currentMonth);
		itemCost.setShardingFlag(dto.getShardingFlag());
		itemCost.setCompanyNo(dto.getCompanyNo());
		itemCost.setCompanyName(companyMap.get(dto.getCompanyNo()));
		if(periodBalance!=null){
			itemCost.setItemCode(periodBalance.getItemCode());
			itemCost.setItemName(periodBalance.getItemName());
			itemCost.setBrandNo(periodBalance.getBrandNo());
		}else{
			itemCost.setItemCode(billBuyBalance.getItemCode());
			itemCost.setItemName(billBuyBalance.getItemName());
			itemCost.setBrandNo(billBuyBalance.getBrandNo());
		}
		itemCost.setCreateUser(dto.getCreateUser());
		itemCost.setCreateTime(dto.getCreateTime());
		itemCost.setUpdateUser(dto.getCreateUser());
		itemCost.setUpdateTime(dto.getCreateTime());
		return itemCost;
	}

	private List<String> findAdjustedItemCosts(Map<String, Object> params) {
		List<String> itemNoList = new ArrayList<String>();
		List<ItemCost> adjustedItemCosts = itemCostMapper.findAdjustedItemCosts(params);
		for (ItemCost itemCost : adjustedItemCosts) {
			itemNoList.add(itemCost.getItemNo());
		}
		return itemNoList;
	}

	@Override
	public int deleteCompanyMonthPeriodBalance(Map<String, Object> currentMap) throws ServiceException {
		try {
			return periodBalanceMapper.deleteCompanyMonthPeriodBalance(currentMap);
		} catch (Exception e) {
			LOGGER.error(currentMap.get("companyNo")+"公司"+currentMap.get("year")+"年"+currentMap.get("month")+"月"+currentMap.get("brandNos")+"品牌部删除店铺当月结存失败", e);
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public int findStoreBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.findStoreBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> findStoreBalanceList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.findStoreBalanceList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void transferInventoryBookToPeriodBalance(ItemCostConditionDto itemCostConditionDto, Map<String, Object> currentMap) throws ServiceException {
		try {
			HashMap<String,Object> orderUnitMap=new HashMap<String,Object>();
			orderUnitMap.put("companyNo", itemCostConditionDto.getCompanyNo());
			orderUnitMap.put("brandNos", itemCostConditionDto.getBrandNos());
			List<OrgUnitBrandRel> list=orgUnitBrandRelMapper.selectByOrderUnitNoParams(orderUnitMap);//查询公司下的所有订货单位
			List<String> orderUnitNos = new ArrayList<String>();
			for(OrgUnitBrandRel orgUnitBrandRel:list){
				orderUnitNos.add(orgUnitBrandRel.getOrderUnitNo());
			}
			
			itemCostConditionDto.setOrderUnitNos(orderUnitNos);
			periodBalanceMapper.transferInventoryBookToPeriodBalance(itemCostConditionDto);// 从流水表汇总出 本期发生的业务结存数据 到结存表
			// 结转公司上期存在的结存，而本期没有发生业务的数据到本月
			periodBalanceMapper.transferLastPeriodBalanceToCurrent(itemCostConditionDto);
			LOGGER.info("################ "+itemCostConditionDto.getCompanyNo()+"公司"+itemCostConditionDto.getCurrentYear()+"年"+itemCostConditionDto.getCurrentMonth()+"月"+itemCostConditionDto.getBrandUnitNo()+"品牌部执行完成店铺结存生成逻辑   ###################");
		} catch (Exception e) {
			LOGGER.error(itemCostConditionDto.getCompanyNo()+"公司"+itemCostConditionDto.getCurrentYear()+"年"+itemCostConditionDto.getCurrentMonth()+"月"+itemCostConditionDto.getBrandUnitNo()+"品牌部按店铺汇总结存失败", e);
			throw new ServiceException(e.getMessage(),e);
		}		
	}

	@Override
	public int queryStoreBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.queryStoreBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<PeriodBalance> queryStoreBalanceList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.queryStoreBalanceList(page,orderByField,orderBy,params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<String> getAllItemNos(Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.getAllItemNos(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public void queryStoreBalanceList(SimplePage page,Map<String, Object> params, Function<Object, Boolean> handler)
			throws ServiceException {
		String statement = "cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper.queryStoreBalanceList";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}
	
	@Override
	public void queryStoreBalanceListAll(Map<String, Object> params, Function<Object, Boolean> handler) throws ServiceException {
		String statement = "cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper.queryStoreBalanceAll";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}

	@Override
	public PeriodBalance queryStoreBalanceAll(Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.queryStoreBalanceAll(params);
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}

	@Override
	public int checkPeriodBalanceData(Map<String, Object> params) throws ServiceException {
		try {
			return periodBalanceMapper.checkPeriodBalanceData(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}