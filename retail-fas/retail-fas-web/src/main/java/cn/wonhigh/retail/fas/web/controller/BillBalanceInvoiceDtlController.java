package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceDtlManager;
import cn.wonhigh.retail.fas.manager.LookupEntryManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途
 * 
 * @author wangyj
 * @date 2015-01-07 16:30:58
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
@Controller
@RequestMapping("/bill_balance_invoice_dtl")
public class BillBalanceInvoiceDtlController extends BaseController<BillBalanceInvoiceDtl> {

	@Resource
	private BillBalanceInvoiceDtlManager billBalanceInvoiceDtlManager;

	// @Resource
	// private ItemCostManager itemCostManagOe;

	// @Resource
	// private BillSaleBalanceManager billSaleBalanceManage;
	//
	// @Resource
	// private BillShopBalanceManager billShopBalanceManager;
	//
	// @Resource
	// private BillShopSaleOrderManager billShopSaleOrderManager;
	//
	// @Resource
	// private OrderMainManager orderMainManager;

	@Resource
	private LookupEntryManager lookupEntryManager;

	//
	// @Resource
	// private OrganManager organManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_balance_invoice_dtl/", billBalanceInvoiceDtlManager);
	}

	/**
	 * 新增/修改
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Boolean>
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Object> saveAll(HttpServletRequest request) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JsonUtil<BillBalanceInvoiceDtl> util = new JsonUtil<BillBalanceInvoiceDtl>();
			Map<CommonOperatorEnum, List<BillBalanceInvoiceDtl>> dataMap = util.convertToMap(request,
					BillBalanceInvoiceDtl.class);
			billBalanceInvoiceDtlManager.saveAll(dataMap);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 通过开票申请单编码查询大类明细数据集合
	 * 
	 * @param billNo
	 *            开票申请单编码
	 * @param request
	 *            HttpServletRequest
	 * @return List集合
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping("/query_by_billNo")
	@ResponseBody
	public Map<String, Object> queryByBillNo(@RequestParam("billNo") String billNo, HttpServletRequest request)
			throws ManagerException {

		List<BillBalanceInvoiceDtl> list = new ArrayList<BillBalanceInvoiceDtl>();

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(billNo)) {
			map.put("rows", list);
			map.put("total", 0);
			return map;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billNo);
			list = billBalanceInvoiceDtlManager.findByBiz(null, params);

			// String companyNo = request.getParameter("companyNo");
			// String balanceNos = request.getParameter("balanceNos") == null
			// ?"":request.getParameter("balanceNos");
			// String balanceType = request.getParameter("balanceType");

			// //根据源单号获取销售明细
			// List<Item> itemList = getItemList(billNo,balanceNos,balanceType);
			// params.put("companyNo", companyNo);
			//
			// if(CollectionUtils.isNotEmpty(list) &&
			// CollectionUtils.isNotEmpty(itemList)){
			// for (BillBalanceInvoiceDtl dtl : list) {
			// BigDecimal costTotal = new BigDecimal(0);
			// String categoryNo = "";
			// if(StringUtils.isNotBlank(dtl.getCategoryNo())){
			// categoryNo = dtl.getCategoryNo().substring(0,2);
			// }
			// String brandNo = dtl.getBrandNo();
			// String organNo = dtl.getOrganNo();
			// String dtlKey = categoryNo +"|" +brandNo +"|"+organNo;
			// for (Item item : itemList) {
			// params.put("itemNo", item.getItemNo());
			// params.put("saleDate", item.getSaleDate());
			// String itemCategoryNo = "";
			// if(StringUtils.isNotBlank(item.getCategoryNo())){
			// itemCategoryNo = item.getCategoryNo().substring(0,2);
			// }
			// String itemBrandNo = item.getBrandNo();
			// String itemOrganNo = item.getOrganNo();
			// String itemKey = itemCategoryNo +"|" +itemBrandNo
			// +"|"+itemOrganNo;
			// if(balanceType.equals(BalanceTypeEnums.AREA_MALL.getTypeNo()+"")){
			// String shopNo = dtl.getShopNo();
			// String itemShopNo = item.getShopNo();
			// dtlKey = dtlKey +"|"+shopNo;
			// itemKey = itemKey +"|" +itemShopNo;
			// // 如按大类汇总,大类相同,则汇总成本金额
			// if(dtlKey.equals(itemKey)){
			// //取得成本金额
			// List<ItemCost> itmeCostList = itemCostManage.findByBiz(null,
			// params);
			// if(CollectionUtils.isNotEmpty(itmeCostList)){
			// for (ItemCost itemCost : itmeCostList) {
			// costTotal = costTotal.add(itemCost.getUnitCost());//汇总成本
			// }
			// }
			// }
			// }else{
			// // 如按大类汇总,大类相同,则汇总成本金额
			// if(dtlKey.equals(itemKey)){
			// //取得成本金额
			// List<ItemCost> itmeCostList = itemCostManage.findByBiz(null,
			// params);
			// if(CollectionUtils.isNotEmpty(itmeCostList)){
			// for (ItemCost itemCost : itmeCostList) {
			// costTotal = costTotal.add(itemCost.getUnitCost());//汇总成本
			// }
			// }
			// }
			// }
			// }
			// dtl.setCostTotal(costTotal);
			// }
			// }

			BillBalanceInvoiceDtl dtl = new BillBalanceInvoiceDtl();

			List<BillBalanceInvoiceDtl> total = new ArrayList<BillBalanceInvoiceDtl>();

			BigDecimal amount = new BigDecimal(0);
			int qty = 0;
			if (null != list && list.size() > 0) {
				params.put("lookupCode", "SHOP_CATEGORY_B");
				for (BillBalanceInvoiceDtl billBalanceInvoiceDtl : list) {
					params.put("entryCode", billBalanceInvoiceDtl.getRetailType());
					// 查询店铺小类名称
					String name = lookupEntryManager.getEntryNameByLookupIdEntryCode(params);
					billBalanceInvoiceDtl.setRetailTypeName(name);
					if (billBalanceInvoiceDtl.getQty() != null) {
						qty += billBalanceInvoiceDtl.getQty();
					}
					amount = amount.add(null == billBalanceInvoiceDtl.getSendAmount() ? new BigDecimal(0)
							: billBalanceInvoiceDtl.getSendAmount());
				}
				dtl.setSalerName("合计");
				dtl.setSendAmount(amount);
				dtl.setQty(qty);
				total.add(dtl);
			}

			map.put("footer", total);
			map.put("rows", list);
			map.put("total", list.size());
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 根据分组获取开票明细信息
	 * 
	 * @param billNo
	 * @param request
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/queryInvoiceDtlGroupByParams")
	@ResponseBody
	public Map<String, Object> queryInvoiceDtlGroupByParams(HttpServletRequest request) throws ManagerException {

		List<BillBalanceInvoiceDtl> list = new ArrayList<BillBalanceInvoiceDtl>();

		String groupBy = request.getParameter("groupBy");
		String billNo = request.getParameter("billNo");
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(billNo)) {
			map.put("rows", list);
			return map;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		params.put("groupBy", groupBy);
		list = billBalanceInvoiceDtlManager.selectByGroupByParams(params);

		// //根据源单号获取销售明细
		// List<Item> itemList = getItemList(billNo,balanceNos,balanceType);
		// params.put("companyNo", companyNo);
		// for (BillBalanceInvoiceDtl dtl : list) {
		// BigDecimal costTotal = new BigDecimal(0);
		// for (Item item : itemList) {
		// params.put("itemNo", item.getItemNo());
		// params.put("saleDate", item.getSaleDate());
		// // 如按大类汇总,大类相同,则汇总成本金额
		// if("category_no".equals(groupBy) &&
		// StringUtils.isNotBlank(item.getCategoryNo()) &&
		// StringUtils.isNotBlank(dtl.getCategoryNo())
		// && item.getCategoryNo().substring(0,
		// 2).equals(dtl.getCategoryNo().substring(0, 2))){
		// //取得成本金额
		// List<ItemCost> itmeCostList = itemCostManage.findByBiz(null, params);
		// if(CollectionUtils.isNotEmpty(itmeCostList)){
		// for (ItemCost itemCost : itmeCostList) {
		// costTotal = costTotal.add(itemCost.getUnitCost());//汇总成本
		// }
		// }
		// }else if("brand_no".equals(groupBy) && null != item.getBrandNo() &&
		// item.getBrandNo().equals(dtl.getBrandNo())){
		// //取得成本金额
		// List<ItemCost> itmeCostList = itemCostManage.findByBiz(null, params);
		// if(CollectionUtils.isNotEmpty(itmeCostList)){
		// for (ItemCost itemCost : itmeCostList) {
		// costTotal = costTotal.add(itemCost.getUnitCost());//汇总成本
		// }
		// }
		// }else if("brand_no,category_no".equals(groupBy) && null !=
		// item.getBrandNo() && item.getBrandNo().equals(dtl.getBrandNo())
		// && StringUtils.isNotBlank(dtl.getCategoryNo()) &&
		// StringUtils.isNotBlank(item.getCategoryNo())
		// && item.getCategoryNo().substring(0,
		// 2).equals(dtl.getCategoryNo().substring(0, 2))){
		// //取得成本金额
		// List<ItemCost> itmeCostList = itemCostManage.findByBiz(null, params);
		// if(CollectionUtils.isNotEmpty(itmeCostList)){
		// for (ItemCost itemCost : itmeCostList) {
		// costTotal = costTotal.add(itemCost.getUnitCost());//汇总成本
		// }
		// }
		// }
		// }
		// dtl.setCostTotal(costTotal);
		// }
		//
		BillBalanceInvoiceDtl dtl = new BillBalanceInvoiceDtl();

		List<BillBalanceInvoiceDtl> total = new ArrayList<BillBalanceInvoiceDtl>();

		// double amount = 0;
		BigDecimal amount = new BigDecimal(0);
		int qty = 0;
		if (null != list && list.size() > 0) {
			params.put("lookupCode", "SHOP_CATEGORY_B");
			for (BillBalanceInvoiceDtl billBalanceInvoiceDtl : list) {
				params.put("entryCode", billBalanceInvoiceDtl.getRetailType());
				// 查询店铺小类名称
				String name = lookupEntryManager.getEntryNameByLookupIdEntryCode(params);
				billBalanceInvoiceDtl.setRetailType(name);
				if (billBalanceInvoiceDtl.getQty() != null) {
					qty += billBalanceInvoiceDtl.getQty();
				}
				amount = amount.add(null == billBalanceInvoiceDtl.getSendAmount() ? new BigDecimal(0)
						: billBalanceInvoiceDtl.getSendAmount());
				amount = amount.add(null == billBalanceInvoiceDtl.getSendAmount() ? new BigDecimal(0)
						: billBalanceInvoiceDtl.getSendAmount());
			}
			dtl.setSalerName("合计");
			// dtl.setSendAmount(new BigDecimal(amount));
			dtl.setSendAmount(amount);
			dtl.setQty(qty);
			total.add(dtl);
		}

		map.put("footer", total);
		map.put("rows", list);
		return map;
	}

	// private List<Item> getItemList(String billNo,String balanceNos,String
	// balanceType) throws ManagerException{
	// List<Item> itemList = new ArrayList<Item>();
	// if (StringUtils.isNotBlank(balanceNos)) {
	// Map<String,Object> params = new HashMap<String,Object>();
	// String[] balanceNoList = balanceNos.split(",");
	// // 首先根据单据号获取销售明细
	// if (balanceType.equals(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()+""))
	// {//查pos 的销售明细，根据开票申请号查询销售明细
	// for (String balanceNo : balanceNoList) {
	// params.put("order", balanceNo);
	// SimplePage page = new SimplePage(0, Integer.MAX_VALUE,
	// Integer.MAX_VALUE);
	// List<OrderDtlDto> orderList =
	// orderMainManager.findPosInnerBuyDetailList(page,params);
	// if(CollectionUtils.isNotEmpty(orderList)){
	// for (OrderDtlDto orderDtlDto : orderList) {
	// Item item = new Item();
	// item.setItemNo(orderDtlDto.getItemNo());
	// item.setBrandNo(orderDtlDto.getBrandNo());
	// item.setCategoryNo(orderDtlDto.getCategoryNo());
	// item.setOrganNo(orderDtlDto.getOrganNo());
	// item.setSaleDate(orderDtlDto.getOutDate());
	// itemList.add(item);
	// }
	// }
	// }
	// } else if
	// (balanceType.equals(BalanceTypeEnums.AREA_MALL.getTypeNo()+"")){
	// for (String balanceNo : balanceNoList) {
	// params.put("balanceNo", balanceNo);
	// BillShopBalance billShopBalance = new BillShopBalance();
	// billShopBalance.setBalanceNo(balanceNo);
	// BillShopBalance obillShopBalance =
	// billShopBalanceManager.findById(billShopBalance);
	// if(null != obillShopBalance){
	// params.put("pageSize", 1);
	// params.put("pageNumber", 999999999);
	// params.put("shopNo", obillShopBalance.getShopNo());
	// params.put("startDate", obillShopBalance.getBalanceStartDate());
	// params.put("endDate", obillShopBalance.getBalanceEndDate());
	// // params.put("organNo", obillShopBalance.getOrganNo());
	// // Organ organ = organManager.getManagerOrganByOrganNo(params);
	// // String organNo = "";
	// // if(null != organ){
	// // organNo = organ.getOrganNo();
	// // }
	// POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderDtl =
	// billShopSaleOrderManager.queryShopSaleDetailListRemote(params);
	// if(null != orderDtl && CollectionUtils.isNotEmpty(orderDtl.getResult())){
	// List<POSOrderAndReturnExMainDtlDto> orderDtlList= orderDtl.getResult();
	// for (POSOrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto :
	// orderDtlList) {
	// Item item = new Item();
	// item.setItemNo(orderAndReturnExMainDtlDto.getItemNo());
	// item.setBrandNo(orderAndReturnExMainDtlDto.getBrandNo());
	// item.setCategoryNo(orderAndReturnExMainDtlDto.getCategoryNo());
	// item.setSaleDate(orderAndReturnExMainDtlDto.getOutDate());
	// // item.setOrganNo(organNo);
	// item.setOrganNo(obillShopBalance.getOrganNo());
	// item.setShopNo(obillShopBalance.getShopNo());
	// itemList.add(item);
	// }
	// }
	// }
	// }
	// }else {// 查bill_sale_balance
	// for (String balanceNo : balanceNoList) {
	// params.put("balanceNo", balanceNo);
	// List<BillSaleBalance> billSaleBalanceList =
	// billSaleBalanceManage.findByBiz(null, params);
	// if(CollectionUtils.isNotEmpty(billSaleBalanceList)){
	// for (BillSaleBalance billSaleBalance : billSaleBalanceList) {
	// Item item = new Item();
	// item.setItemNo(billSaleBalance.getItemNo());
	// item.setBrandNo(billSaleBalance.getBrandNo());
	// item.setCategoryNo(billSaleBalance.getCategoryNo());
	// item.setSaleDate(billSaleBalance.getSendDate());
	// item.setOrganNo(billSaleBalance.getOrganNoFrom());
	// itemList.add(item);
	// }
	// }
	// }
	// }
	// }
	// return itemList;
	// }
}