package cn.wonhigh.retail.fas.manager.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeDtl;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasBillCodeConvert;
import cn.wonhigh.retail.fas.common.vo.ItemPriceCost;
import cn.wonhigh.retail.fas.manager.utils.ItemPriceAndCostCache;
import cn.wonhigh.retail.fas.service.BrandUnitService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.FinancialAccountService;
import cn.wonhigh.retail.fas.service.HeadquarterCostMaintainService;
import cn.wonhigh.retail.fas.service.ItemService;
import cn.wonhigh.retail.fas.service.POSRedundanceService;
import cn.wonhigh.retail.fas.service.PriceQuotationListService;
import cn.wonhigh.retail.fas.service.PurchasePriceService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;

import com.yougou.logistics.base.common.exception.ServiceException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProcessPosBillTask implements Runnable {

	private Logger log = Logger.getLogger(ProcessPosBillTask.class);

	/**
	 * 更新时间筛选单据提前量-分钟数
	 */
	@Value("${pos.update.minute}")
	private Integer posUpdateMinute;

	private String shardingFlag;

	public void sethardingFlag(String val) {
		this.shardingFlag = val;
	}

	private Date startTime;
	private Date endTime;

	@Resource
	private POSRedundanceService posRedundanceService;

	@Resource
	private CompanyService companyService;

	@Resource
	private BrandUnitService brandUnitService;

//	@Resource
//	private ItemCostManager itemCostManager;

	@Resource
	private ItemPriceAndCostCache itemPriceAndCostCache;
	

	public ProcessPosBillTask() {
		super();
	}

	CountDownLatch signal;

	public void run(String shardingFlag, CountDownLatch signal) {
		this.shardingFlag = shardingFlag;
		this.signal = signal;

		System.out.println("task id:" + this.hashCode());
		try {
			endTime = DateUtil.getCurrentDateTime();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 获取起始时间
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(endTime);
		rightNow.add(Calendar.MINUTE, Math.min(posUpdateMinute, -600));
		startTime = rightNow.getTime();

		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			updateOrderCompanyInfo();
			updateExchangeInfo();
		} finally {
			signal.countDown();
		}
	}

	private Integer updateExchangeDetailInfo() {
		Integer returnExchangeDtlCount = 0;
		List<ReturnExchangeDtl> returnExchangeDtlList = null;
		while (true) {
			try {
				returnExchangeDtlList = posRedundanceService.getReturnExchangeDtl(startTime, endTime, shardingFlag);
			} catch (ServiceException e) {
				log.error("查询ReturnExchangeDtl失败");
			}

			if (returnExchangeDtlList == null || returnExchangeDtlList.size() == 0)
				break;

			for (ReturnExchangeDtl returnExchangeDtl : returnExchangeDtlList) {
				String brandUnitNo = "";
				String brandUnitName = "";
				// BigDecimal unitCost = new BigDecimal(0);
				// BigDecimal regionCost = new BigDecimal(0);
				// BigDecimal headquarterCost = new BigDecimal(0);
				// BigDecimal tagPriceNation = new BigDecimal(0);
				// BigDecimal purchasePrice = new BigDecimal(0);
				// BigDecimal materialPrice = new BigDecimal(0);
				// BigDecimal factoryPrice = new BigDecimal(0);

				try {
					// 查询品牌部
					BrandUnit brandUnit = brandUnitService.getBrandUnitByBrand(returnExchangeDtl.getBrandNo());
					if (brandUnit != null && brandUnit.getBrandUnitNo() != null) {
						brandUnitNo = brandUnit.getBrandUnitNo();
					}
					if (brandUnit != null && brandUnit.getName() != null) {
						brandUnitName = brandUnit.getName();
					}

					// 查询主表信息
					String companyNo = null;
					Date outDate = null;
					ReturnExchangeMain returnExchangeMainInfo = posRedundanceService.getReturnExchangeMainByBuinessNo(
							returnExchangeDtl.getBusinessNo(), shardingFlag);

					companyNo = returnExchangeMainInfo.getCompanyNo();
					outDate = returnExchangeMainInfo.getOutDate();
					String itemNo = returnExchangeDtl.getItemNo();

					if (companyNo == null || outDate == null) {
						log.warn("单据结算公司或者outData为空:" + returnExchangeDtl.getBusinessNo());
						continue;
					}

					// // 查询价格大区
					// String priceZoneNo = null;
					// Map<String, Object> financeAccountMap = new
					// HashMap<String, Object>();
					// financeAccountMap.put("companyNo", companyNo);
					// financeAccountMap.put("status", 1);
					// financeAccountMap.put("groupLeadRole", 0);
					//
					// List<FinancialAccount> financialAccounts =
					// financialAccountService.findByBiz(null,
					// financeAccountMap);
					// if (!CollectionUtils.isEmpty(financialAccounts)) {
					// priceZoneNo = financialAccounts.get(0).getPriceZone();
					// }
					// if (StringUtils.isEmpty(priceZoneNo)) {
					// Map<String, Object> companyMap = new HashMap<String,
					// Object>();
					// companyMap.put("companyNo", companyNo);
					// companyMap.put("status", 1);
					//
					// List<Company> companies = companyService.findByBiz(null,
					// companyMap);
					// if (!CollectionUtils.isEmpty(companies)) {
					// priceZoneNo = companies.get(0).getZoneNo();
					// }
					// }
					//
					// // 查询地区价
					// RegionCostMaintain regionCostMaintain =
					// regionCostMaintainService.getRegionCost(priceZoneNo,
					// returnExchangeDtl.getItemNo(), outDate);
					// if (regionCostMaintain != null &&
					// regionCostMaintain.getRegionCost() != null) {
					// regionCost = regionCostMaintain.getRegionCost();
					// }
					//
					// // 查询单位成本
					// ItemCost itemCost =
					// itemCostService.getItemCost(companyNo,
					// returnExchangeDtl.getItemNo(), outDate);
					// if (itemCost != null && itemCost.getUnitCost() != null) {
					// unitCost = itemCost.getUnitCost();
					// } else {
					// unitCost = regionCost;
					// }
					//
					// // 查询总部价
					// HeadquarterCostMaintain headquarterCostMaintain =
					// headquarterCostMaintainService
					// .getHeadquarterCost(returnExchangeDtl.getItemNo(),
					// outDate);
					// if (headquarterCostMaintain != null &&
					// headquarterCostMaintain.getHeadquarterCost() != null) {
					// headquarterCost =
					// headquarterCostMaintain.getHeadquarterCost();
					// }
					//
					// // 查询全国统一牌价
					// PriceQuotationList priceQuotationList =
					// priceQuotationListService.getPriceQuotationList("0000",
					// returnExchangeDtl.getItemNo());
					// if (priceQuotationList != null &&
					// priceQuotationList.getTagPrice() != null) {
					// tagPriceNation = priceQuotationList.getTagPrice();
					// }
					//
					// // 查询采购价、物料价、厂进价
					// Map<String, Object> itemParam = new HashMap<String,
					// Object>();
					// itemParam.put("itemNo", returnExchangeDtl.getItemNo());
					// List<Item> itemList = itemService.findByBiz(null,
					// itemParam);
					// if (!CollectionUtils.isEmpty(itemList)) {
					// PurchasePrice purchasePriceObj =
					// purchasePriceService.findBalancePurchasePrice(
					// returnExchangeDtl.getItemNo(),
					// itemList.get(0).getSupplierNo(), outDate);
					// if (purchasePriceObj != null &&
					// purchasePriceObj.getPurchasePrice() != null) {
					// purchasePrice = purchasePriceObj.getPurchasePrice();
					// }
					// if (purchasePriceObj != null &&
					// purchasePriceObj.getMaterialPrice() != null) {
					// materialPrice = purchasePriceObj.getMaterialPrice();
					// }
					// if (purchasePriceObj != null &&
					// purchasePriceObj.getFactoryPrice() != null) {
					// factoryPrice = purchasePriceObj.getFactoryPrice();
					// }
					// }

					ItemPriceCost priceCost =  itemPriceAndCostCache.getItemPriceAndCost(companyNo, itemNo, outDate);
					
					returnExchangeDtlCount += posRedundanceService.updateReturnExchangeDtl(returnExchangeDtl.getId(),
							brandUnitNo, brandUnitName, priceCost.getUnitCost(), priceCost.getRegionCost(),
							priceCost.getHeadquarterCost(), priceCost.getTagPriceNation(),
							priceCost.getPurchasePrice(), priceCost.getMaterialPrice(), priceCost.getFactoryPrice(),
							shardingFlag);
				} catch (Exception e) {
					log.error("更新" + shardingFlag + "库 order_dtl失败：order_no = " + returnExchangeDtl.getBusinessNo()
							+ "item_no = " + returnExchangeDtl.getItemNo(), e);
				}
			}

		}
		return returnExchangeDtlCount;
	}

	/**
	 * 更新pos退换货订单明细
	 * 
	 * @return
	 */
	private void updateExchangeInfo() {
		Integer returnExchangeMainCount = 0;
		List<ReturnExchangeMain> returnExchangeMainList = null;
		// 分批执行
		while (true) {
			try {
				returnExchangeMainList = posRedundanceService.getReturnExchangeMain(startTime, endTime, shardingFlag);
			} catch (ServiceException e1) {
				log.error("查询ReturnExchangeMain失败");
				break;
			}
			if (returnExchangeMainList == null || returnExchangeMainList.size() == 0)
				break;
			for (ReturnExchangeMain returnExchangeMain : returnExchangeMainList) {
				// 获取FAS单据类型
				String fasBillCode = FasBillCodeConvert.chooseFasBillCode(returnExchangeMain);
				try {
					// 查询公司
					Company company = companyService.getCompanyByShopNoWithDate(returnExchangeMain.getShopNo(),
							returnExchangeMain.getOutDate());
					if (company != null)
						returnExchangeMainCount += posRedundanceService.updateReturnExchangeMain(
								returnExchangeMain.getId(), fasBillCode, company.getCompanyNo(), company.getName(),
								shardingFlag);
					else
						log.warn("单据的公司为空:" + returnExchangeMain.getShopNo());
				} catch (ServiceException e) {
					log.error("更新" + shardingFlag + "库 return_exchange_main失败：business_no = "
							+ returnExchangeMain.getBusinessNo());
				}
			}

		}

		Integer returnExchangeDtlCount = updateExchangeDetailInfo();

		if (returnExchangeMainCount > 0 || returnExchangeDtlCount > 0) {
			log.info("更新" + shardingFlag + "库 return_exchange_main表成功，共更新" + returnExchangeMainCount + "条数据");
			log.info("更新" + shardingFlag + "库 return_exchange_order_dtl表成功，共更新" + returnExchangeDtlCount + "条数据");
		}
	}

	private Integer updateOrderDetailsInfo() {
		Integer orderDtlCount = 0;
		List<OrderDtl> orderDtlList = null;
		while (true) {
			try {
				orderDtlList = posRedundanceService.getOrderDtl(startTime, endTime, shardingFlag);
			} catch (ServiceException e) {
				log.error("查询OrderDtl失败");
			}
			if (orderDtlList == null || orderDtlList.size() == 0)
				break;

			for (OrderDtl orderDtl : orderDtlList) {
				updateOrderDetailInfo(orderDtl);
			}
		}
		return orderDtlCount;
	}

	private Integer updateOrderDetailInfo(OrderDtl orderDtl) {
		String brandUnitNo = "";
		String brandUnitName = ""; 

		try {
			// 查询品牌部
			BrandUnit brandUnit = brandUnitService.getBrandUnitByBrand(orderDtl.getBrandNo());
			if (brandUnit != null && brandUnit.getBrandUnitNo() != null) {
				brandUnitNo = brandUnit.getBrandUnitNo();
			}
			if (brandUnit != null && brandUnit.getName() != null) {
				brandUnitName = brandUnit.getName();
			}

			// 查询主表信息
			String companyNo = null;
			Date outDate = null;
			OrderMain orderMainInfo = posRedundanceService.getOrderMainByOrderNo(orderDtl.getOrderNo(), shardingFlag);
			companyNo = orderMainInfo.getCompanyNo();
			outDate = orderMainInfo.getOutDate();
			String itemNo = orderDtl.getItemNo();

			if (companyNo == null && outDate == null) {
				log.warn("单据结算公司或者outData为空:" + orderMainInfo.getOrderNo());
				return 0;
			}

			// // 查询价格大区
			// String priceZoneNo = null;
			// Map<String, Object> financeAccountMap = new HashMap<String,
			// Object>();
			// financeAccountMap.put("companyNo", companyNo);
			// financeAccountMap.put("status", 1);
			// financeAccountMap.put("groupLeadRole", 0);
			//
			// List<FinancialAccount> financialAccounts =
			// financialAccountService.findByBiz(null, financeAccountMap);
			// if (!CollectionUtils.isEmpty(financialAccounts)) {
			// priceZoneNo = financialAccounts.get(0).getPriceZone();
			// }
			// if (StringUtils.isEmpty(priceZoneNo)) {
			// Map<String, Object> companyMap = new HashMap<String, Object>();
			// companyMap.put("companyNo", companyNo);
			// companyMap.put("status", 1);
			//
			// List<Company> companies = companyService.findByBiz(null,
			// companyMap);
			// if (!CollectionUtils.isEmpty(companies)) {
			// priceZoneNo = companies.get(0).getZoneNo();
			// }
			// }
			// // 查询地区价
			// RegionCostMaintain regionCostMaintain =
			// regionCostMaintainService.getRegionCost(priceZoneNo,
			// orderDtl.getItemNo(), outDate);
			// if (regionCostMaintain != null &&
			// regionCostMaintain.getRegionCost() != null) {
			// regionCost = regionCostMaintain.getRegionCost();
			// }
			//
			// // 查询单位成本
			// ItemCost itemCost = itemCostService.getItemCost(companyNo,
			// orderDtl.getItemNo(), outDate);
			// if (itemCost != null && itemCost.getUnitCost() != null) {
			// unitCost = itemCost.getUnitCost();
			// } else {
			// unitCost = regionCost;
			// }
			//
			// // 查询总部价
			// HeadquarterCostMaintain headquarterCostMaintain =
			// headquarterCostMaintainService.getHeadquarterCost(
			// orderDtl.getItemNo(), outDate);
			// if (headquarterCostMaintain != null &&
			// headquarterCostMaintain.getHeadquarterCost() != null) {
			// headquarterCost = headquarterCostMaintain.getHeadquarterCost();
			// }
			//
			// // 查询全国统一牌价
			// PriceQuotationList priceQuotationList =
			// priceQuotationListService.getPriceQuotationList("0000",
			// orderDtl.getItemNo());
			// if (priceQuotationList != null &&
			// priceQuotationList.getTagPrice() != null) {
			// tagPriceNation = priceQuotationList.getTagPrice();
			// }
			//
			// // 查询采购价、物料价、厂进价
			// Map<String, Object> itemParam = new HashMap<String, Object>();
			// itemParam.put("itemNo", orderDtl.getItemNo());
			// List<Item> itemList = itemService.findByBiz(null, itemParam);
			// if (!CollectionUtils.isEmpty(itemList)) {
			// PurchasePrice purchasePriceObj =
			// purchasePriceService.findBalancePurchasePrice(
			// orderDtl.getItemNo(), itemList.get(0).getSupplierNo(), outDate);
			// if (purchasePriceObj != null &&
			// purchasePriceObj.getPurchasePrice() != null) {
			// purchasePrice = purchasePriceObj.getPurchasePrice();
			// }
			// if (purchasePriceObj != null &&
			// purchasePriceObj.getMaterialPrice() != null) {
			// materialPrice = purchasePriceObj.getMaterialPrice();
			// }
			// if (purchasePriceObj != null &&
			// purchasePriceObj.getFactoryPrice() != null) {
			// factoryPrice = purchasePriceObj.getFactoryPrice();
			// }
			// }

			// }
			ItemPriceCost priceCost = itemPriceAndCostCache.getItemPriceAndCost(companyNo, itemNo, outDate);

			return posRedundanceService.updateOrderDtl(orderDtl.getId(), brandUnitNo, brandUnitName,
					priceCost.getUnitCost(), priceCost.getRegionCost(), priceCost.getHeadquarterCost(),
					priceCost.getTagPriceNation(), priceCost.getPurchasePrice(), priceCost.getMaterialPrice(),
					priceCost.getFactoryPrice(), shardingFlag);

		} catch (Exception e) {
			log.error("更新" + shardingFlag + "库 order_dtl失败：order_no = " + orderDtl.getOrderNo() + "item_no = "
					+ orderDtl.getItemNo());
			return 0;
		}
	}

	/**
	 * 更新pos销售订单
	 */
	private void updateOrderCompanyInfo() {
		Integer orderMainCount = 0;
		List<OrderMain> orderMainList = null;
		// 分批执行
		while (true) {
			try {
				orderMainList = posRedundanceService.getOrderMain(startTime, endTime, shardingFlag);
			} catch (ServiceException e1) {
				log.error("查询OrderMain失败", e1);
				break;
			}
			if (orderMainList == null || orderMainList.size() == 0)
				break;
			for (OrderMain orderMain : orderMainList) {
				// 获取FAS单据类型
				String fasBillCode = FasBillCodeConvert.chooseFasBillCode(orderMain);
				try {
					// 查询公司
					Company company = companyService.getCompanyByShopNoWithDate(orderMain.getShopNo(),
							orderMain.getOutDate());
					if (company != null)
						orderMainCount += posRedundanceService.updateOrderMain(orderMain.getId(), fasBillCode,
								company.getCompanyNo(), company.getName(), shardingFlag);
					else
						log.warn("单据的公司为空:" + orderMain.getShopNo());
				} catch (ServiceException e) {
					log.error("更新" + shardingFlag + "库 order_main失败：order_no = " + orderMain.getOrderNo(), e);
				}
			}

			if (orderMainList.size() < 100)
				break;
		}

		// 更新pos销售订单明细
		Integer orderDtlCount = updateOrderDetailsInfo();
		if (orderMainCount > 0 || orderDtlCount > 0) {
			log.info("更新" + shardingFlag + "库 order_main表成功，共更新" + orderMainCount + "条数据");
			log.info("更新" + shardingFlag + "库 order_dtl表成功，共更新" + orderDtlCount + "条数据");
		}
	}
}
