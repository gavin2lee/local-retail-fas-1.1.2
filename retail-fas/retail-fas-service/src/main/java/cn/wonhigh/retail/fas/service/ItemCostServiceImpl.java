package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.dal.database.BillBuyBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillSaleBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.ItemCostMapper;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途
 * 
 * @author wangyj
 * @date 2015-03-04 10:33:05
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("itemCostService")
class ItemCostServiceImpl extends BaseCrudServiceImpl implements ItemCostService {

	private Logger log = Logger.getLogger(getClass());

	@Resource
	private ItemCostMapper itemCostMapper;

	@Resource
	private BillBuyBalanceMapper billBuyBalanceMapper;

	@Resource
	private BillSaleBalanceMapper billSaleBalanceMapper;

	// @Resource
	// private SqlSessionFactory sqlSessionFactoryForLogistics;

	@Override
	public BaseCrudMapper init() {
		return itemCostMapper;
	}

	@Override
	public int findItemCostUnmatchRegionPriceCount(Map<String, Object> params) throws ServiceException {
		try {
			return itemCostMapper.getItemCostUnmatchRegionPriceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ItemCost> findItemCostUnmatchRegionPriceByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return itemCostMapper.getItemCostUnmatchRegionPriceByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int deleteCompanyMonthCost(ItemCostConditionDto invCostGenerateDto) throws ServiceException {
		try {
			return itemCostMapper.deleteCompanyMonthCost(invCostGenerateDto);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateRegionCost(Company company, String brandUnitNo,Date start, Date end) throws ServiceException {
		Map<String, Object> params = getUpdateCostParams(company, brandUnitNo, start, end);
		List<String> ids = null;
		try {
			// 更新POS订单表地区成本价
			ids = itemCostMapper.findPosOrderRegionCostId(params);
			if (!CollectionUtils.isEmpty(ids)) {
				params.put("ids", ids);
				itemCostMapper.updatePosOrderRegionCost(params);
			}

			// 更新POS退换货表地区成本价
			ids = itemCostMapper.findPosReturnExchangeRegionCostId(params);
			if (!CollectionUtils.isEmpty(ids)) {
				params.put("ids", ids);
				itemCostMapper.updatePosReturnExchangeRegionCost(params);
			}

			// 更新买卖表地区价
			this.updateBalanceRegionCost(params);
			return 1;
		} catch (Exception e) {
			throw new ServiceException("定时任务更新地区价失败。", e);
		}
	}
	
	/**
	 * 更新买卖表地区价
	 * @param params
	 */
	private void updateBalanceRegionCost(Map<String, Object> params) {
		int pageNo = 1;
		int pageSize = 5000;
		SimplePage page = new SimplePage(pageNo, pageSize, 0);
		while (true) {
			List<BillSaleBalance> saleBalances = itemCostMapper.getSaleBalanceRegionCostUnmatchByPage(page, "", "", params);
			if(!CollectionUtils.isEmpty(saleBalances)){
				params.put("lastId", saleBalances.get(saleBalances.size()-1).getId());
				for (BillSaleBalance billSaleBalance : saleBalances) {  
					itemCostMapper.updateSaleCostById(billSaleBalance);
				}
			}
			if (saleBalances.size() < pageSize) {
				break;
			}
		}
	}
	
	
	@Override
	public int updateHeadquaterCost(Company company, String brandUnitNo,Date start, Date end) throws ServiceException {

		Map<String, Object> params = getUpdateCostParams(company, brandUnitNo, start, end);

		try {
			// 更新POS订单表总部成本价
			itemCostMapper.updatePosOrderHeadquaterCost(params);
			// 更新POS退换货表总部成本价
			itemCostMapper.updatePosReturnExchangeHeadquaterCost(params);
			// 更新买卖表总部价
			this.updateBalanceHeadquaterCost(params);
			return 1;
		} catch (Exception e) {
			throw new ServiceException("定时任务更新总部价失败。", e);
		}
	}

	/**
	 * 更新买卖表总部价
	 * @param params
	 */
	private void updateBalanceHeadquaterCost(Map<String, Object> params) {
		int pageNo = 1;
		int pageSize = 5000;
		SimplePage page = new SimplePage(pageNo, pageSize, 0);
		while (true) {
			List<BillSaleBalance> saleBalances = itemCostMapper.getSaleBalanceHeadquaterCostUnmatchByPage(page, "", "", params);
			if(!CollectionUtils.isEmpty(saleBalances)){
				params.put("lastId", saleBalances.get(saleBalances.size()-1).getId());
				for (BillSaleBalance billSaleBalance : saleBalances) {  
					itemCostMapper.updateSaleCostById(billSaleBalance);
				}
			}
			if (saleBalances.size() < pageSize) {
				break;
			}
		}
	}

	@Override
	public int updateItemCost(Company company, String brandUnitNo,Date start, Date end) throws ServiceException {
		try {
			Map<String, Object> params = getUpdateCostParams(company, brandUnitNo, start, end);
			// 更新POS订单表单位成本价
			int POCount = itemCostMapper.updatePosOrderItemCost(params); 
			log.info("共更新了 ORDER_DTL 成本记录" + POCount);
			// 更新POS退换货表单位成本价
			int PRCount = itemCostMapper.updatePosReturnExchangeItemCost(params);
			log.info("共更新了 RETURN_EXCHANGE_DTL 成本记录" + PRCount);
			// 更新买卖表单位成本
			this.updateBalanceItemCost(params);
			
			return 1;
		} catch (Exception e) {
			log.error("定时任务更新成本价失败。", e);
			throw new ServiceException("定时任务更新成本价失败。", e);
		}
	}

	/**
	 * 更新买卖表单位成本
	 * @param params
	 */
	private void updateBalanceItemCost(Map<String, Object> params) {
		int pageNo = 1;
		int pageSize = 5000;
		SimplePage page = new SimplePage(pageNo, pageSize, 0);
		while (true) {
			List<BillSaleBalance> saleBalances = itemCostMapper.getSaleBalanceItemCostUnmatchByPage(page, "", "",params);
			if(!CollectionUtils.isEmpty(saleBalances)){
				params.put("lastId", saleBalances.get(saleBalances.size()-1).getId());
				for (BillSaleBalance billSaleBalance : saleBalances) {
					itemCostMapper.updateSaleCostById(billSaleBalance);
				}
			}
			if (saleBalances.size() < pageSize) {
				break;
			}
		}

	}
	/**
	 * 拼装查询条件
	 * @param company
	 * @param brandUnitNo
	 * @param start
	 * @param end
	 * @return
	 */
	private Map<String, Object> getUpdateCostParams(Company company, String brandUnitNo, Date start, Date end) {
		Map<String, Object> params = new HashMap<String, Object>();
		String organTypeNo = company.getOrganTypeNo();

		if (StringUtils.isEmpty(organTypeNo) || "0".equals(organTypeNo)) {
			params.put("shardingFlag", "0_Z");
		} else {
			params.put("shardingFlag", organTypeNo + "_" + company.getZoneNo());
		}
		params.put("startDate", start);
		params.put("endDate", end);
		params.put("companyNo", company.getCompanyNo());
		String year = String.format("%tY", start);
		String month = String.format("%tm", start);
		params.put("year", year);
		params.put("month", Integer.parseInt(month));
		if(StringUtils.isNotBlank(brandUnitNo)){
			params.put("multiBrandUnitNo",FasUtil.formatInQueryCondition(brandUnitNo));
		}
		return params;
	}

	@Override
	public int updateRegionCostSchedule(Map<String, Object> params) throws ServiceException {
		try {
			// 更新POS订单表地区成本价
			itemCostMapper.updatePosOrderRegionCostSchedule(params);
			// 更新POS退换货表地区成本价
			itemCostMapper.updatePosReturnExchangeRegionCostSchedule(params);
			// 更新BILL_BUY_BALANCE表地区成本价
			itemCostMapper.updateBuyBalanceRegionCost(params);
			// 更新BILL_SALE_BALANCE表地区成本价
			itemCostMapper.updateSaleBalanceRegionCost(params);
			return 1;
		} catch (Exception e) {
			throw new ServiceException("定时任务更新地区价失败。", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	@Override
	public int updatePOSOrderHQSchedule(Map<String, Object> params) throws ServiceException {
		try {
			// 更新POS订单表总部成本价
			itemCostMapper.updatePOSOrderHQSchedule(params);

			return 1;
		} catch (Exception e) {
			throw new ServiceException("定时任务更新总部价失败。", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateReturnExchangeHQSchedule(Map<String, Object> params) throws ServiceException {
		try {

			// 更新POS退换货表总部成本价
			itemCostMapper.updateReturnExchangeHQSchedule(params);

			return 1;
		} catch (Exception e) {
			throw new ServiceException("更新总部价失败。", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateBuyBalanceHeadquaterCost(Map<String, Object> params) throws ServiceException {
		try {
			// 更新BILL_BUY_BALANCE表总部成本价
			itemCostMapper.updateBuyBalanceHeadquaterCost(params);

			return 1;
		} catch (Exception e) {
			throw new ServiceException("更新总部价失败。", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateSaleBalanceHeadquaterCost(Map<String, Object> params) throws ServiceException {
		try {
			// 更新BILL_SALE_BALANCE表总部成本价
			itemCostMapper.updateSaleBalanceHeadquaterCost(params);
			return 1;
		} catch (Exception e) {
			throw new ServiceException("更新总部价失败。", e);
		}
	}

	// @Override
	// public int updateHeadquaterCostSchedule() throws ServiceException {
	// try {
	// // 更新POS订单表总部成本价
	// itemCostMapper.updatePOSOrderHQSchedule(params);
	// // 更新POS退换货表总部成本价
	// itemCostMapper.updateReturnExchangeHQSchedule(params);
	// // 更新BILL_BUY_BALANCE表总部成本价
	// itemCostMapper.updateBuyBalanceHeadquaterCost(params);
	// // 更新BILL_SALE_BALANCE表总部成本价
	// itemCostMapper.updateSaleBalanceHeadquaterCost(params);
	// return 1;
	// } catch (Exception e) {
	// throw new ServiceException("定时任务更新总部价失败。", e);
	// }
	// }

	@Override
	public int updateItemCostSchedule(Map<String, Object> params) throws ServiceException {
		try {
			// 更新POS订单表单位成本价
			int POCount = itemCostMapper.updatePosOrderItemCostSchedule(params);
			log.info("共更新了 ORDER_DTL 成本记录" + POCount);
			// 更新POS退换货表单位成本价
			int PRCount = itemCostMapper.updateReturnExchangeItemCostSchedule(params);
			log.info("共更新了 RETURN_EXCHANGE_DTL 成本记录" + PRCount);

			// 更新BILL_BUY_BALANCE表单位成本价
			updateBuyBalanceItemCostSchedule(params);
			updateSaleBalanceItemCostSchedule(params);

			return 1;
		} catch (Exception e) {
			throw new ServiceException("定时任务更新成本价失败。", e);
		}
	}

	private void updateBuyBalanceItemCostSchedule(Map<String, Object> params) {
		// 直接查询inventory_book表数据来判断地区价
		int totalCount = itemCostMapper.getBBICUnmatchCountSchedule(params);
		if (totalCount <= 0) {
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
			// 查询出满足条件的记录
			List<BillBuyBalance> buyBalances = itemCostMapper.getBBICUnmatchByPageSchedule(page, "", "", params);

			BillBuyBalance buyBalance = new BillBuyBalance();
			for (BillBuyBalance billBuyBalance : buyBalances) {
				buyBalance.setId(billBuyBalance.getId());
				buyBalance.setUnitCost(billBuyBalance.getUnitCost());
				billBuyBalanceMapper.updateByPrimaryKeySelective(buyBalance);
			}
			pageNo++;
		}

	}

	private void updateSaleBalanceItemCostSchedule(Map<String, Object> params) {
		// 直接查询inventory_book表数据来判断地区价
		int totalCount = itemCostMapper.getSBICUnmatchCountSchedule(params);
		if (totalCount <= 0) {
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
			// 查询出满足条件的记录
			List<BillSaleBalance> saleBalances = itemCostMapper.getSBICUnmatchByPageSchedule(page, "", "", params);

			BillSaleBalance saleBalance = new BillSaleBalance();
			for (BillSaleBalance billSaleBalance : saleBalances) {
				saleBalance.setId(billSaleBalance.getId());
				saleBalance.setUnitCost(billSaleBalance.getUnitCost());
				billSaleBalanceMapper.updateByPrimaryKeySelective(saleBalance);
			}
			pageNo++;
		}
	}

	@Override
	public ItemCost getItemCost(String companyNo, String itemNo, Date date) throws ServiceException {

		if (companyNo == null || itemNo == null || date == null)
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", companyNo);
		params.put("itemNo", itemNo);
		params.put("date", date);
		return itemCostMapper.getItemCost(params);
	}

	@Override
	public List<ItemCost> findItemCostForCompanyPeriodBalance(Map<String, Object> params) throws ServiceException {
		try {
			return itemCostMapper.findItemCostForCompanyPeriodBalance(params);
		} catch (Exception e) {
			log.error("查询当月成本失败!", e);
			throw new ServiceException("查询当月成本失败!", e);
		}
	}

	@Override
	public int batchInsertItemCost(List<ItemCost> itemCostList) {
		return itemCostMapper.batchInsertItemCost(itemCostList);
	}

	@Override
	public int findBLKItemCostCount(Map<String, Object> params) throws ServiceException {
		try {
			return itemCostMapper.findBLKItemCostCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ItemCost> findBLKItemCostList(SimplePage page, String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return itemCostMapper.findBLKItemCostList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void runSql(Map<String, Object> params, String type) {
		if("d".equals(type)){//删除
			itemCostMapper.runSqlForD(params);
		}else if("u".equals(type)){//更新
			itemCostMapper.runSqlForU(params);
		}else if("c".equals(type)){//新增
			itemCostMapper.runSqlForC(params);
		}
	}

}