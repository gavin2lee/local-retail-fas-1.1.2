package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeDtl;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.BillSaleBalanceService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.HeadquarterCostMaintainService;
import cn.wonhigh.retail.fas.service.ItemCostService;
import cn.wonhigh.retail.fas.service.OrderDtlService;
import cn.wonhigh.retail.fas.service.PeriodBalanceService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;
import cn.wonhigh.retail.fas.service.ReturnExchangeDtlService;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 更新单据冗余成本字段Manager实现类 
 * @author xia.j
 * @date  2015-05-11 15:04:50
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("redundancyCostManager")
class RedundancyCostManagerImpl implements RedundancyCostManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(RedundancyCostManagerImpl.class);
	
	/**
	 * 操作者列表
	 */
	private static List<String> operateUserList = new ArrayList<String>();
	
	@Resource
	private BillBuyBalanceService billBuyBalanceService;
	
	@Resource
	private BillSaleBalanceService billSaleBalanceService;
	
	@Resource
	private OrderDtlService orderDtlService;
	
	@Resource
	private ReturnExchangeDtlService returnExchangeDtlService;
	
	@Resource
	private PeriodBalanceService periodBalanceService;
	
	@Resource
	private ItemCostService itemCostService;
	
	@Resource
	private CompanyService companyService;
	
	@Resource
	private RegionCostMaintainService regionCostMaintainService;
	
	@Resource
	private HeadquarterCostMaintainService headquarterCostMaintainService;
	
	@Override
	public Map<String, Object> updateCost(Map<String, Object> params) throws ManagerException {
		
		Map<String, Object> obj = new HashMap<String,Object>();
		
		String operateUser = params.get("operateUser").toString();
		//操作者控制，同一个操作者不可点击多次
		if(operateUserList.contains(operateUser)) {
			obj.put("success", false);
			return obj;
		}
		
		operateUserList.add(operateUser);
		
		int count = 0;
		
		LOGGER.info("============开始更新buy表成本==========");
		int buyCount = this.updateBuyCost(params);
		LOGGER.info("============更新buy表成本成功，共更新" + buyCount + "条数据，operateUser：" + operateUser + " params：" + params);
		
		LOGGER.info("============开始更新sale表成本==========");
		int saleCount = this.updateSaleCost(params);
		LOGGER.info("============更新sale表成本成功，共更新" + saleCount + "条数据，operateUser：" + operateUser + " params：" + params);
		
		LOGGER.info("============开始更新order表成本==========");
		int orderCount = this.updateOrderCost(params);
		LOGGER.info("============更新order表成本成功，共更新" + orderCount + "条数据，operateUser：" + operateUser + " params：" + params);
		
		LOGGER.info("============开始更新return表成本==========");
		int returnCount = this.updateReturnCost(params);
		LOGGER.info("============更新return表成本成功，共更新" + returnCount + "条数据，operateUser：" + operateUser + " params：" + params);
		
		LOGGER.info("============开始更新period表成本==========");
		int periodCount = this.updatePeriodCost(params);
		LOGGER.info("============更新period表成本成功，共更新" + periodCount + "条数据，operateUser：" + operateUser + " params：" + params);
		
		count = buyCount + saleCount + saleCount + orderCount + returnCount + periodCount;
		
		operateUserList.remove(operateUser);
		
		obj.put("success", true);
		obj.put("count", count);
		return obj;
	}

	@Override
	public int updateBuyCost(Map<String, Object> params)
			throws ManagerException {
		
		int updateCount = 0;
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			
			// 优先根据receive_date筛选
			queryParams.put("queryCondition",
					"AND ((receive_date BETWEEN '" + params.get("startDate") + "' AND '" + params.get("endDate") + "') " 
					+ "OR (send_date BETWEEN '" + params.get("startDate") + "' AND '" + params.get("endDate") + "' AND receive_date IS NULL))");
			queryParams.put("buyerNo", params.get("companyNo"));
			// 分批更新
			SimplePage page = new SimplePage();
			int totalCount = billBuyBalanceService.findCount(queryParams);
			int pageSize = 100;
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);

			for (int i = 1; i <= page.getTotalPage(); i++) {
				//查询单据
				page.setPageNo(i);
				List<BillBuyBalance> list = billBuyBalanceService.findByPage(page, null, null, queryParams);
				
				for (BillBuyBalance billBalance : list) {
					
					BigDecimal unitCost = null;
					BigDecimal regionCost = null;
					BigDecimal headquarterCost = null;

					String companyNo = billBalance.getBuyerNo();
					String itemNo = billBalance.getItemNo();
					Date saleDate = billBalance.getReceiveDate();
					if(saleDate == null) {
						saleDate = billBalance.getSendDate();
					}
					
					if(null == companyNo || null == itemNo || null == saleDate) continue;
					
					// 查询加权成本
					unitCost = this.selectUnitCost(companyNo, itemNo, saleDate);
					// 查询地区成本
					regionCost = this.selectRegionCost(companyNo, itemNo, saleDate);
					// 查询总部成本
					headquarterCost = this.selectHeadquarterCost(itemNo, saleDate);
					//更新单据成本
					if(null != unitCost || null != regionCost || null != headquarterCost) {
						BillBuyBalance updateEntity = new BillBuyBalance();
						updateEntity.setId(billBalance.getId());
						updateEntity.setUnitCost(unitCost);
						updateEntity.setRegionCost(regionCost);
						updateEntity.setHeadquarterCost(headquarterCost);
						updateCount += billBuyBalanceService.modifyById(updateEntity);
					}
				}
				
			}
		} catch (ServiceException e) {
			LOGGER.error("更新buy表成本失败, params:" + params);
			throw new ManagerException(e.getMessage(), e);
		}
		
		return updateCount;
	}

	@Override
	public int updateSaleCost(Map<String, Object> params)
			throws ManagerException {
		
		int updateCount = 0;
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			
			// 优先根据receive_date筛选
			queryParams.put("queryCondition",
					"AND ((receive_date BETWEEN '" + params.get("startDate") + "' AND '" + params.get("endDate") + "') " 
					+ "OR (send_date BETWEEN '" + params.get("startDate") + "' AND '" + params.get("endDate") + "' AND receive_date IS NULL))");
			queryParams.put("salerNo", params.get("companyNo"));
			// 分批更新
			SimplePage page = new SimplePage();
			int totalCount = billSaleBalanceService.findCount(queryParams);
			int pageSize = 100;
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);

			for (int i = 1; i <= page.getTotalPage(); i++) {
				// 查询单据
				page.setPageNo(i);
				List<BillSaleBalance> list = billSaleBalanceService.findByPage(page, null, null, queryParams);
				
				for (BillSaleBalance billBalance : list) {
					
					BigDecimal unitCost = null;
					BigDecimal regionCost = null;
					BigDecimal headquarterCost = null;

					String companyNo = billBalance.getSalerNo();
					String itemNo = billBalance.getItemNo();
					Date saleDate = billBalance.getReceiveDate();
					if(saleDate == null) {
						saleDate = billBalance.getSendDate();
					}
					
					if(null == companyNo || null == itemNo || null == saleDate) continue;
					
					// 查询加权成本
					unitCost = this.selectUnitCost(companyNo, itemNo, saleDate);
					// 查询地区成本
					regionCost = this.selectRegionCost(companyNo, itemNo, saleDate);
					// 查询总部成本
					headquarterCost = this.selectHeadquarterCost(itemNo, saleDate);
					
					// 更新单据成本
					if(null != unitCost || null != regionCost || null != headquarterCost) {
						BillSaleBalance updateEntity = new BillSaleBalance();
						updateEntity.setId(billBalance.getId());
						updateEntity.setUnitCost(unitCost);
						updateEntity.setRegionCost(regionCost);
						updateEntity.setHeadquarterCost(headquarterCost);
						updateCount += billSaleBalanceService.modifyById(updateEntity);
					}
				}
				
			}
		} catch (ServiceException e) {
			LOGGER.error("更新sale表成本失败, params:" + params);
			throw new ManagerException(e.getMessage(), e);
		}
		
		return updateCount;
	}

	@Override
	public int updateOrderCost(Map<String, Object> params)
			throws ManagerException {
		
		int updateCount = 0;
		try {
			// 设置查询参数
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("startDate", params.get("startDate"));
			queryParams.put("endDate", params.get("endDate"));
			queryParams.put("companyNo", params.get("companyNo"));
			
			// 分批更新
			SimplePage page = new SimplePage();
			int totalCount = orderDtlService.findCount(queryParams);
			int pageSize = 100;
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);

			for (int i = 1; i <= page.getTotalPage(); i++) {
				// 查询单据
				page.setPageNo(i);
				List<OrderDtlDto> list = orderDtlService.findByPage(page, null, null, queryParams);
				
				for (OrderDtlDto orderDtlDto : list) {
					
					BigDecimal unitCost = null;
					BigDecimal regionCost = null;
					BigDecimal headquarterCost = null;
					
					String companyNo = orderDtlDto.getCompanyNo();
					String itemNo = orderDtlDto.getItemNo();
					Date saleDate = orderDtlDto.getOutDate();
					
					if(null == companyNo || null == itemNo || null == saleDate) continue;
					
					// 查询加权成本
					unitCost = this.selectUnitCost(companyNo, itemNo, saleDate);
					// 查询地区成本
					regionCost = this.selectRegionCost(companyNo, itemNo, saleDate);
					// 查询总部成本
					headquarterCost = this.selectHeadquarterCost(itemNo, saleDate);
					
					// 更新单据成本
					if(null != unitCost || null != regionCost || null != headquarterCost) {
						OrderDtl updateEntity = new OrderDtl();
						updateEntity.setId(orderDtlDto.getOrderDtlId());
						updateEntity.setUnitCost(unitCost);
						updateEntity.setRegionCost(regionCost);
						updateEntity.setHeadquarterCost(headquarterCost);
						updateCount += orderDtlService.modifyById(updateEntity);
					}
				}
				
			}
		} catch (ServiceException e) {
			LOGGER.error("更新order表成本失败, params:" + params);
			throw new ManagerException(e.getMessage(), e);
		}
		
		return updateCount;
	}

	@Override
	public int updateReturnCost(Map<String, Object> params)
			throws ManagerException {
		
		int updateCount = 0;
		try {
			// 设置查询参数
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("startDate", params.get("startDate"));
			queryParams.put("endDate", params.get("endDate"));
			queryParams.put("companyNo", params.get("companyNo"));
			
			// 分批更新
			SimplePage page = new SimplePage();
			int totalCount = returnExchangeDtlService.findCount(queryParams);
			int pageSize = 100;
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);

			for (int i = 1; i <= page.getTotalPage(); i++) {
				// 查询单据
				page.setPageNo(i);
				List<ReturnExchangeDtl> list = returnExchangeDtlService.findByPage(page, null, null, queryParams);
				
				for (ReturnExchangeDtl returnExchangeDtl : list) {
					
					BigDecimal unitCost = null;
					BigDecimal regionCost = null;
					BigDecimal headquarterCost = null;
					
					String companyNo = returnExchangeDtl.getCompanyNo();
					String itemNo = returnExchangeDtl.getItemNo();
					Date saleDate = returnExchangeDtl.getOutDate();
					
					if(null == companyNo || null == itemNo || null == saleDate) continue;
					
					// 查询加权成本
					unitCost = this.selectUnitCost(companyNo, itemNo, saleDate);
					// 查询地区成本
					regionCost = this.selectRegionCost(companyNo, itemNo, saleDate);
					// 查询总部成本
					headquarterCost = this.selectHeadquarterCost(itemNo, saleDate);
					
					// 更新单据成本
					if(null != unitCost || null != regionCost || null != headquarterCost) {
						ReturnExchangeDtl updateEntity = new ReturnExchangeDtl();
						updateEntity.setId(returnExchangeDtl.getId());
						updateEntity.setUnitCost(unitCost);
						updateEntity.setRegionCost(regionCost);
						updateEntity.setHeadquarterCost(headquarterCost);
						updateCount += returnExchangeDtlService.modifyById(updateEntity);
					}
				}
				
			}
		} catch (ServiceException e) {
			LOGGER.error("更新return表成本失败, params:" + params);
			throw new ManagerException(e.getMessage(), e);
		}
		
		return updateCount;
	}
	
	@Override
	public int updatePeriodCost(Map<String, Object> params)
			throws ManagerException {
		
		int updateCount = 0;
		try {
			// 设置查询参数
			Map<String, Object> queryParams = new HashMap<String, Object>();
			//以起始日期的年份月份查询
			Date date = DateUtil.parseToDateWithThrowException(params.get("startDate").toString(), "yyyy-MM-dd");
			queryParams.put("year", DateUtil.getYear(date));
			queryParams.put("month", DateUtil.getMonth(date));
			queryParams.put("companyNo", params.get("companyNo"));
			
			// 分批更新
			SimplePage page = new SimplePage();
			int totalCount = periodBalanceService.findCount(queryParams);
			int pageSize = 100;
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);
			
			for (int i = 1; i <= page.getTotalPage(); i++) {
				// 查询单据
				page.setPageNo(i);
				List<PeriodBalance> list = periodBalanceService.findByPage(page, null, null, queryParams);
				
				for (PeriodBalance periodBalance : list) {
					
					BigDecimal regionCost = null;
					BigDecimal headquarterCost = null;
					
					String companyNo = periodBalance.getCompanyNo();
					String itemNo = periodBalance.getItemNo();
					Date saleDate = DateUtil.getLastDayOfMonth(date);
					
					if(null == companyNo || null == itemNo || null == saleDate) continue;
					
					// 查询地区成本
					regionCost = this.selectRegionCost(companyNo, itemNo, saleDate);
					// 查询总部成本
					headquarterCost = this.selectHeadquarterCost(itemNo, saleDate);
					
					// 更新单据成本
					if(null != regionCost || null != headquarterCost) {
						PeriodBalance updateEntity = new PeriodBalance();
						updateEntity.setId(periodBalance.getId());
						updateEntity.setRegionCost(regionCost);
						updateEntity.setHeadquarterCost(headquarterCost);
						updateCount += periodBalanceService.modifyById(updateEntity);
					}
				}
				
			}
			
		} catch (ServiceException e) {
			LOGGER.error("更新period表成本失败, params:" + params);
			throw new ManagerException(e.getMessage(), e);
		} catch (ParseException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		
		return updateCount;
	}

	/**
	 * 查询加权成本
	 * @param companyNo
	 * @param itemNo
	 * @param saleDate
	 * @return
	 * @throws ServiceException 
	 */
	private BigDecimal selectUnitCost(String companyNo, String itemNo, Date saleDate) throws ServiceException {
		
		BigDecimal unitCost = null;
		Map<String, Object> itemCostParams = new HashMap<String, Object>();
		itemCostParams.put("companyNo", companyNo);
		itemCostParams.put("itemNo", itemNo);
		itemCostParams.put("saleDate", DateUtil.format1(saleDate));
		List<ItemCost> list = itemCostService.findByBiz(null, itemCostParams);
		
		if(CollectionUtils.isNotEmpty(list)) {
			unitCost = list.get(0).getUnitCost();
		}
		
		return unitCost;
	}
	
	/**
	 * 查询地区成本
	 * @param companyNo
	 * @param itemNo
	 * @param saleDate
	 * @return
	 * @throws ServiceException 
	 */
	private BigDecimal selectRegionCost(String companyNo, String itemNo, Date saleDate) throws ServiceException {
		
		BigDecimal regionCost = null;
		
		//查询大区编号
		String zoneNo = null;
		if(companyNo != null){
			Company company = new Company();
			company.setCompanyNo(companyNo);
			company = companyService.findById(company);
			if(company == null){
				return regionCost;
			}
			zoneNo = company.getZoneNo();
		}
		
		regionCost = regionCostMaintainService.findRegionCost(itemNo, zoneNo, saleDate);
		
		return regionCost;
	}
	
	/**
	 * 查询总部成本
	 * @param itemNo
	 * @param saleDate
	 * @return
	 * @throws ServiceException 
	 */
	private BigDecimal selectHeadquarterCost(String itemNo, Date saleDate) throws ServiceException {
		
		BigDecimal headquarterCost = null;
		Map<String, Object> headquarterCostParams = new HashMap<String, Object>();
		headquarterCostParams.put("itemNo", itemNo);
		headquarterCostParams.put("effectiveTime", DateUtil.format1(saleDate));
		HeadquarterCostMaintain headquarterCostMaintain = headquarterCostMaintainService.getLastEffectiveModel(headquarterCostParams);
		
		if(null != headquarterCostMaintain) {
			headquarterCost = headquarterCostMaintain.getHeadquarterCost();
		}
		
		return headquarterCost;
	}

	@Override
	public Map<String, Object> allocateBillItemCost(Map<String, Object> params) throws ManagerException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		return resultMap;
	}

}