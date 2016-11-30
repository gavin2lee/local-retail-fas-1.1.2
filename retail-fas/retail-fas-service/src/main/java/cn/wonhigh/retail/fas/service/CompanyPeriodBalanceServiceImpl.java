package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.dal.database.CompanyMapper;
import cn.wonhigh.retail.fas.dal.database.CompanyPeriodBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.ItemCostMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-15 16:12:30
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
@Service("companyPeriodBalanceService")
class CompanyPeriodBalanceServiceImpl extends BaseCrudServiceImpl implements CompanyPeriodBalanceService {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(CompanyPeriodBalanceServiceImpl.class);
	
	@Resource
    private CompanyPeriodBalanceMapper companyPeriodBalanceMapper;

	@Resource
	private ItemCostMapper itemCostMapper;
	
	@Resource
	private CompanyMapper companyMapper;
	
	
    @Override
    public BaseCrudMapper init() {
        return companyPeriodBalanceMapper;
    }
    
    @Override
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params)
			throws ServiceException {
    	try {
    		return companyPeriodBalanceMapper.getCompanyPeriodSubTotalCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<CompanyPeriodBalance> findCompanyPeriodBalanceSubTotalPages(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
    		return companyPeriodBalanceMapper.getCompanyPeriodSubTotalByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CompanyPeriodBalance> findCompanyPeriodBalancePages(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
    		return companyPeriodBalanceMapper.getCompanyPeriodByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<CompanyPeriodBalance> selectTotalRow(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		try {
    		return companyPeriodBalanceMapper.selectTotalRow(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public int deleteMonthlyBalance(Map<String, Object> params)
			throws ServiceException {
		try {
			return companyPeriodBalanceMapper.deleteMonthlyBalance(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		} 
	}
	
	@Override
	public void transferLastPeriodBalance(ItemCostConditionDto conditionDto, Map<String, Object> currentMap)
			throws ServiceException {
		
		// 结转公司上期存在的结存，而本期没有发生业务的数据到本月
 		companyPeriodBalanceMapper.transferLastPeriodBalanceToCurrent(conditionDto);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public void generateWeightedCost(ItemCostConditionDto conditionDto, Map<String, Object> currentMap)
			throws ServiceException {
		try {
			LOGGER.info("################ 开始执行生成成本逻辑   ###################");
			// 删除本期的所有成本，成本调整的不用删
			itemCostMapper.deleteCurrentMonthCost(currentMap);
			
			// 计算本期入库类单据的成本
			calculateItemCostFromPeriodBalance(currentMap, conditionDto);
			
			// 结转上期的存在的成本，而本期未重新生成的成本到本期
			itemCostMapper.transferLastItemCostToCurrentMonth(conditionDto);
			
			// 回写成本到结存表的出库类汇总金额
			itemCostFeedbackToPeriodBalance(currentMap);
			
			LOGGER.info("################ 成本生成执行完毕，成本已成功回写到结存   ###################");
		} catch (Exception e) {
			LOGGER.error("生成成本失败", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int deleteCompanyMonthPeriodBalance(Map<String, Object> currentMap) throws ServiceException {
		try {
			//删除本公司,本月的结存数据
			return companyPeriodBalanceMapper.deleteCompanyMonthPeriodBalance(currentMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int summaryInventoryBook(ItemCostConditionDto conditionDto)
			throws ServiceException {
		try {
			// 从流水表汇总出 本期发生的业务结存数据 到结存表
	 		return companyPeriodBalanceMapper.summaryInventoryBook(conditionDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private void calculateItemCostFromPeriodBalance(Map<String, Object> currentMap, ItemCostConditionDto conditionDto) {
		// 分批获取本期结存 已经汇总的入库类单据的数据
		int totalCount = companyPeriodBalanceMapper.selectCount(currentMap, null);
		if (totalCount <= 0) {
			return;
		}
		
		//本月已经有过成本调整
		List<ItemCost> adjustedItemCosts = itemCostMapper.findAdjustedItemCosts(currentMap);
				
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
			List<CompanyPeriodBalance> periodBalances = companyPeriodBalanceMapper.selectByPage(page, "", "", currentMap, null);
			// 某一个地区需要维护的商品进行价格维护
			List<ItemCost> itemCostList = new ArrayList<ItemCost>();
			
			for (CompanyPeriodBalance pBalance : periodBalances) {
				ItemCost itemCost = new ItemCost();
				itemCost.setItemNo(pBalance.getItemNo());
				//本月已经有过成本调整，不需要结转
				if (!checkNeedTransfer(itemCost, adjustedItemCosts)) {
					continue;
				}
				
				itemCost.setShardingFlag(pBalance.getShardingFlag());
				itemCost.setCompanyNo(pBalance.getCompanyNo());
				itemCost.setCompanyName(companyMap.get(pBalance.getCompanyNo()));
				itemCost.setItemCode(pBalance.getItemCode());
				itemCost.setItemName(pBalance.getItemName());
				itemCost.setBrandNo(pBalance.getBrandNo());
				itemCost.setYear((String) (currentMap.get("year")));
				itemCost.setMonth((String) (currentMap.get("month")));
				itemCost.setCreateUser(conditionDto.getCreateUser());
				itemCost.setCreateTime(conditionDto.getCreateTime());
				itemCost.setUpdateUser(conditionDto.getCreateUser());
				itemCost.setUpdateTime(conditionDto.getCreateTime());
				
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
	 * 判断上期成本是否需要结转到本期，经过成本调整单的不需要结转
	 * @param itemCost
	 * @param adjustedItemCosts
	 * @return
	 */
	private boolean checkNeedTransfer(ItemCost itemCost, List<ItemCost> adjustedItemCosts) {
		boolean result = true;
		if (CollectionUtils.isEmpty(adjustedItemCosts)) {
			return true;
		}
		for (ItemCost compareItemCost : adjustedItemCosts) {
			if (compareItemCost.getItemNo().equals(itemCost.getItemNo())){
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 结转上月发生了而本月未发生业务的结存信息
	 * 
	 * @param conditionDto
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void itemCostFeedbackToPeriodBalance(Map<String, Object> currentMap) throws ServiceException {
		try{
			// 分批获取本期结存 已经汇总的入库类单据的数据
			int totalCount = companyPeriodBalanceMapper.queryPeriodBalanceJoinItemCostCount(currentMap);
			if (totalCount <= 0) {
				return;
			}
			int pageSize = 2000;// 每次查询2000条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
	
			while (pageNo <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				companyPeriodBalanceMapper.batchUpdateBalanceItemCostByPage(page, "", "", currentMap);
				pageNo++;
			}
			LOGGER.info("################"+currentMap.get("companyNo")+"公司"+currentMap.get("year")+"年"+currentMap.get("month")+"月"+currentMap.get("brandNos")+"品牌部出库类金额已成功回写到公司结存    ###################");
		}catch(Exception e){
			LOGGER.error("################"+currentMap.get("companyNo")+"公司"+currentMap.get("year")+"年"+currentMap.get("month")+"月"+currentMap.get("brandNos")+"品牌部成本回写公司结存失败。", e);
			throw new ServiceException(e);
		}
	}
	
	public boolean generateBalancePreData(ItemCostConditionDto conditionDto, Map<String, Object> currentMap) 
			throws ServiceException {
		try {
			// 先删除本公司,本月的所有结存数据
	    	companyPeriodBalanceMapper.deleteCompanyMonthPeriodBalance(currentMap);
	    	
			//生成预处理的结存数据
	    	companyPeriodBalanceMapper.ShopSummaryToCompany(conditionDto);
			
			LOGGER.info("################ "+conditionDto.getCompanyNo()+"公司"+conditionDto.getCurrentYear()+"年"+conditionDto.getCurrentMonth()+"月"+conditionDto.getBrandUnitNo()+"品牌部生成公司结存成功。    ###################");
			return true;
		} catch (Exception e) {
			LOGGER.error(conditionDto.getCompanyNo()+"公司"+conditionDto.getCurrentYear()+"年"+conditionDto.getCurrentMonth()+"月"+conditionDto.getBrandUnitNo()+"品牌部生成公司结存失败。", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void writeBackAmount(ItemCostConditionDto invCostGenerateDto) throws ServiceException {
		try{
			companyPeriodBalanceMapper.batchUpdateWriteBackAmount(invCostGenerateDto);
			LOGGER.info("################ "+invCostGenerateDto.getCompanyNo()+"公司"+invCostGenerateDto.getCurrentYear()+"年"+invCostGenerateDto.getCurrentMonth()+"月"+invCostGenerateDto.getBrandUnitNo()+"品牌部入库数据已成功回写到公司期间结存    ###################");
		}catch(Exception e){
			LOGGER.error(invCostGenerateDto.getCompanyNo()+"公司"+invCostGenerateDto.getCurrentYear()+"年"+invCostGenerateDto.getCurrentMonth()+"月"+invCostGenerateDto.getBrandUnitNo()+"品牌部入库数据回写结存失败。", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void writeBackCostAdjustmentAmout(Map<String, Object> params) throws ServiceException {
		try{
			companyPeriodBalanceMapper.batchHandleCostDjustmentAmount(params);
			LOGGER.info("################"+params.get("companyNo")+"公司"+params.get("year")+"年"+params.get("month")+"月"+params.get("brandNos")+"品牌部重刷差异调整金额成功  ###################");
		}catch(Exception e){
			LOGGER.error("################"+params.get("companyNo")+"公司"+params.get("year")+"年"+params.get("month")+"月"+params.get("brandNos")+"品牌部重刷差异调整金额失败。", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public int selectWeightedCostCount(Map<String, Object> params)
			throws ServiceException {
		return companyPeriodBalanceMapper.selectWeightedCostCount(params);
	}
	
	@Override
	public List<CompanyPeriodBalance> selectTheFinalCostByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return companyPeriodBalanceMapper.selectTheFinalCostByPage(page, orderByField, orderBy, params, authorityParams);
	}
	
}