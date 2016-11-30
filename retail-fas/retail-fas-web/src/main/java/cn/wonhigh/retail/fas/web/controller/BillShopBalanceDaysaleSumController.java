package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;
import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDaysaleSumManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.manager.ShopDayReportManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@Controller
@RequestMapping("/bill_shop_balance_daysale_sum")
@ModuleVerify("30140018")
public class BillShopBalanceDaysaleSumController extends BaseController<SaleOrderPayway> {
    @Resource
    private BillShopBalanceDaysaleSumManager billShopBalanceDaysaleSumManager;
    
    @Resource
	private ShopDayReportManager shopDayReportManager;

    @Resource
    private OrderMainManager orderMainManager;
    
    @Resource
	private ShopManager shopManager ;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("mallshop_balance/",billShopBalanceDaysaleSumManager);
    }
    
    @Override
    public String list() {
    	return "mallshop_balance/mall_saleDayReport";
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/listType")
    public ModelAndView listTab(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
		String type = req.getParameter("type");
		if(StringUtils.isNotBlank(type)){
			mav.addObject("type", type);
		}
		mav.setViewName("mallshop_balance/mall_saleDayReport");
		return mav;
    }
    
    @RequestMapping("/mall_saleDayReport_order")
   	public String mall_saleDayReport_order() {
   		return "mallshop_balance/mall_saleDayReport_order";
   	}
    
    @RequestMapping("/mall_saleDayReport_day")
   	public String mall_saleDayReport_day() {
   		return "mallshop_balance/mall_saleDayReport_day";
   	}
    
    @RequestMapping("/mall_saleDayReport_month")
   	public String mall_saleDayReport_month() {
   		return "mallshop_balance/mall_saleDayReport_month";
   	}
    
    @RequestMapping("/mall_saleDayReport_order_brand")
   	public String mall_saleDayReport_order_brand() {
   		return "mallshop_balance/mall_saleDayReport_order_brand";
   	}
    
    @RequestMapping("/mall_saleDayReport_discount")
   	public String mall_saleDayReport_discount() {
   		return "mallshop_balance/mall_saleDayReport_discount";
   	}
    
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String,Object> obj = new HashMap<String,Object>();
    	obj.put("rows", new ArrayList<SaleOrderPayway>());
		obj.put("total", 0);
		
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	Map<String, Object> params = builderParams(req, model);
    	
    	String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		//是否小计
//		String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
//			params.put("shopNoLists",  Arrays.asList(shopNo.split(",")));
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}else {
			//根据公司和管理城市查询店铺
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);
			if(StringUtils.isNotEmpty(organNo)){
				shopParamMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
			}
			if(StringUtils.isNotEmpty(brandNos)){
				shopParamMap.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			}
			//需要查询的店铺列表
			List<String> shopNoList = new ArrayList<String>();
			//页面选择了公司和管理城市，根据提交来查询店铺
			List<Shop>  shopList =  shopManager.findByBiz(null, shopParamMap);
			if (!CollectionUtils.isEmpty(shopList)) {
				for(Shop shop : shopList) {
					shopNoList.add(shop.getShopNo());
				 }
			}
			if (!CollectionUtils.isEmpty(shopNoList)) {
				params.put("shopNoList",  shopNoList);
			}else{
				params.put("shopNo", "01");
			}
		}
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	//0-正常销售 1-跨店销售
    	businessTypeList.add(new Integer(0));
		businessTypeList.add(new Integer(1));
		params.put("businessTypeList", businessTypeList);
		
		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(new Integer(30));
		statusList.add(new Integer(41));
		params.put("statusList", statusList);
		
		int total = this.orderMainManager.findSaleOrderPaywayCount(params);
		if (total < 1) {
			return obj;
		}
		
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SaleOrderPayway> dailyReportList = orderMainManager.findSaleOrderPaywayByPage(page, sortColumn, sortOrder, params);
		
		obj.put("rows", dailyReportList);
		obj.put("total", total);
    	
//    	OcOrderMainDto  ocOrderMainDto =  billShopSaleOrderManager.findList4SumOcOrderByParameter(params);
    	//根据条件汇总销售金额、数量
    	SaleOrderPayway saleOrderPayway = orderMainManager.getSumSaleOrderPayway(params);
    	
    	if (null != saleOrderPayway) {
    		// 组装footer
    		SaleOrderPayway payWay = new SaleOrderPayway();
    		payWay.setShopName("合计");
    		payWay.setPayAmount(saleOrderPayway.getPayAmount());
    		payWay.setReturnAmount(saleOrderPayway.getReturnAmount());
    		payWay.setOrderAmount(saleOrderPayway.getOrderAmount());
    		payWay.setP01(saleOrderPayway.getP01());
    		payWay.setP04(saleOrderPayway.getP04());
    		payWay.setP03(saleOrderPayway.getP03());
    		payWay.setP05(saleOrderPayway.getP05());
    		payWay.setP06(saleOrderPayway.getP06());

    		payWay.setP08(saleOrderPayway.getP08());
    		payWay.setP09(saleOrderPayway.getP09());
    		payWay.setP10(saleOrderPayway.getP10());
    		payWay.setP11(saleOrderPayway.getP11());
    		payWay.setP12(saleOrderPayway.getP12());
    		payWay.setP13(saleOrderPayway.getP13());
    		payWay.setP14(saleOrderPayway.getP14());
    		payWay.setP15(saleOrderPayway.getP15());
    		payWay.setP16(saleOrderPayway.getP16());
    		payWay.setP17(saleOrderPayway.getP17());
    		payWay.setP18(saleOrderPayway.getP18());
    		payWay.setP19(saleOrderPayway.getP19());
    		payWay.setP20(saleOrderPayway.getP20());
    		payWay.setP21(saleOrderPayway.getP21());
    		payWay.setP22(saleOrderPayway.getP22());
    		payWay.setP23(saleOrderPayway.getP23());
    		payWay.setP24(saleOrderPayway.getP24());
    		payWay.setP25(saleOrderPayway.getP25());
    		payWay.setP26(saleOrderPayway.getP26());
    		payWay.setP27(saleOrderPayway.getP27());
    		payWay.setP28(saleOrderPayway.getP28());
    		payWay.setP29(saleOrderPayway.getP29());
    		payWay.setP30(saleOrderPayway.getP30());
    		payWay.setP31(saleOrderPayway.getP31());
    		payWay.setP32(saleOrderPayway.getP32());
    		payWay.setP35(saleOrderPayway.getP35());
    		payWay.setP999(saleOrderPayway.getP999());
    		List<SaleOrderPayway> saleOrderPaywaySumTotal = new ArrayList<SaleOrderPayway>();
    		saleOrderPaywaySumTotal.add(payWay);
    		
    		obj.put("footer", saleOrderPaywaySumTotal);
		}
		return obj;
    }
    
//    @Override
    public Map<String, Object> queryListOld(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String,Object> obj = new HashMap<String,Object>();
    	obj.put("rows", new ArrayList<IndependShopDayReport>());
		obj.put("total", 0);
		
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	Map<String, Object> params = builderParams(req, model);
    	
    	String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		//是否小计
		String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoList",  Arrays.asList(shopNo.split(",")));
		}else {
			//根据公司和管理城市查询店铺
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);
			if(StringUtils.isNotEmpty(organNo)){
				shopParamMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
			}
			//需要查询的店铺列表
			List<String> shopNoList = new ArrayList<String>();
			//页面选择了公司和管理城市，根据提交来查询店铺
			List<Shop>  shopList =  shopManager.findByBiz(null, shopParamMap);
			if (!CollectionUtils.isEmpty(shopList)) {
				for(Shop shop : shopList) {
					shopNoList.add(shop.getShopNo());
				 }
			}else{
				params.put("shopNo", null);
			}
			if (!CollectionUtils.isEmpty(shopNoList)) {
				params.put("shopNoList",  shopNoList);
			}
		}
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	//0-正常销售 1-跨店销售
    	businessTypeList.add(new Integer(0));
		businessTypeList.add(new Integer(1));
		params.put("businessTypeList", businessTypeList);
		
		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(new Integer(30));
		statusList.add(new Integer(41));
		params.put("statusList", statusList);
		
		int total = this.shopDayReportManager.findShopDailyReportCount(params);
		if (total < 1) {
			return obj;
		}
		
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<POSOcGroupOrderPayway> dailyReportList = shopDayReportManager.findShopDailyReportByPage(page, sortColumn, sortOrder, params);
		
		obj.put("rows", convertDailyReportList(dailyReportList, isSubTotal));
		obj.put("total", total);
		
		return obj;
    }
    
    /**
     * 对象转换，组装后台竖排数据成横排数据
     * @param dailyReportList
     * @return
     */
    private List<IndependShopDayReport> convertDailyReportList(List<POSOcGroupOrderPayway> dailyReportList, String isSubTotal) {
		List<IndependShopDayReport> resultDayReports = new ArrayList<IndependShopDayReport>();
		if (CollectionUtils.isEmpty(dailyReportList)) {
			return resultDayReports;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String, Map<String, List<POSOcGroupOrderPayway>>> maps = new HashMap<String, Map<String, List<POSOcGroupOrderPayway>>>();
		for(POSOcGroupOrderPayway orderPaywayGroup : dailyReportList){
			String keyShopNo = orderPaywayGroup.getShopNo();
			String keyOutDate = dateFormat.format(orderPaywayGroup.getOutDate());
			if(maps.containsKey(keyShopNo)) {
				Map<String, List<POSOcGroupOrderPayway>> shopOrderPaywayMap = maps.get(keyShopNo);
				if(shopOrderPaywayMap.containsKey(keyOutDate)) {
					shopOrderPaywayMap.get(keyOutDate).add(orderPaywayGroup);
				}else {
					List<POSOcGroupOrderPayway> dataList = new ArrayList<POSOcGroupOrderPayway>();
					dataList.add(orderPaywayGroup);
					maps.get(keyShopNo).put(keyOutDate, dataList);
				}
			} else {
				List<POSOcGroupOrderPayway> orderPaywayDtoList = new ArrayList<POSOcGroupOrderPayway>();
				orderPaywayDtoList.add(orderPaywayGroup);
				
				Map<String, List<POSOcGroupOrderPayway>> shopOrderPaywayMap = new HashMap<String, List<POSOcGroupOrderPayway>>();
				shopOrderPaywayMap.put(keyOutDate, orderPaywayDtoList);
				
				maps.put(keyShopNo, shopOrderPaywayMap);
			}
		}
		
		for (Map.Entry<String, Map<String, List<POSOcGroupOrderPayway>>> listMapTemp : maps.entrySet()) { 
			IndependShopDayReport subShopDayReport = new IndependShopDayReport();
			for (Map.Entry<String, List<POSOcGroupOrderPayway>> paywayDtaoList : listMapTemp.getValue().entrySet()) { 
				IndependShopDayReport shopDayReport = new IndependShopDayReport();
				shopDayReport.setTotalAmount(new BigDecimal(0));
				for(POSOcGroupOrderPayway ocGroupOrderPaywayDtoTemp : paywayDtaoList.getValue()) {
					shopDayReport.setShopNo(ocGroupOrderPaywayDtoTemp.getShopNo());
					shopDayReport.setShopName(ocGroupOrderPaywayDtoTemp.getShopName());
					shopDayReport.setOutDate(ocGroupOrderPaywayDtoTemp.getOutDate());
					fillObject(ocGroupOrderPaywayDtoTemp,shopDayReport,subShopDayReport);
				}
				resultDayReports.add(shopDayReport);
			}
			//增加小计对象
			if("true".equals(isSubTotal)) {
				subShopDayReport.setShopName("小计：");
				resultDayReports.add(subShopDayReport);	
			}
		}
		
		return resultDayReports;
	}

    public void fillObject(POSOcGroupOrderPayway ocGroupOrderPaywayDtoTemp,IndependShopDayReport independShopDayReport,IndependShopDayReport subShopDayReport){
		
		if(PublicConstans.PAYCODE_CASHAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getCashAmount() == null) {
				subShopDayReport.setCashAmount(new BigDecimal(0));
			}
			subShopDayReport.setCashAmount(subShopDayReport.getCashAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()) );
		}else if(PublicConstans.PAYCODE_ADVANCEPAY.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setAdvancePay(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getAdvancePay() == null ) {
				subShopDayReport.setAdvancePay(new BigDecimal(0));
			}
			subShopDayReport.setAdvancePay(subShopDayReport.getAdvancePay().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CASHCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getCashCouponAmount() == null) {
				subShopDayReport.setCashCouponAmount(new BigDecimal(0));
			}
			subShopDayReport.setCashCouponAmount(subShopDayReport.getCashCouponAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CREDITCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCreditCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());	
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getCreditCardAmount() == null) {
				subShopDayReport.setCreditCardAmount(new BigDecimal(0));
			}
			subShopDayReport.setCreditCardAmount(subShopDayReport.getCreditCardAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getMallCardAmount() == null) {
				subShopDayReport.setMallCardAmount(new BigDecimal(0));
			}
			subShopDayReport.setMallCardAmount(subShopDayReport.getMallCardAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_OTHERAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setOtherAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getOtherAmount() == null) {
				subShopDayReport.setOtherAmount(new BigDecimal(0));
			}
			subShopDayReport.setOtherAmount(subShopDayReport.getOtherAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getMallCouponAmount() == null ) {
				subShopDayReport.setMallCouponAmount(new BigDecimal(0));
			}
			subShopDayReport.setMallCouponAmount(subShopDayReport.getMallCouponAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}
	}

	/**
	 * 查询导出数据的方法
	 * @param params 查询参数
	 * @return List<ModelType>
	 * @throws ManagerException 异常
	 */
    @Override
	protected List<SaleOrderPayway> queryExportData(Map<String, Object> params) throws ManagerException {    	
    	String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		//是否小计
		String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
//			params.put("shopNoList",  Arrays.asList(shopNo.split(",")));
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}else {
			//根据公司和管理城市查询店铺
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);
			if(StringUtils.isNotEmpty(organNo)){
				shopParamMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
			}
			//需要查询的店铺列表
			List<String> shopNoList = new ArrayList<String>();
			//页面选择了公司和管理城市，根据提交来查询店铺
			List<Shop>  shopList =  shopManager.findByBiz(null, shopParamMap);
			if (!CollectionUtils.isEmpty(shopList)) {
				for(Shop shop : shopList) {
					shopNoList.add(shop.getShopNo());
				 }
			}
			if (!CollectionUtils.isEmpty(shopNoList)) {
				params.put("shopNoList",  shopNoList);
			}
		}
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	//0-正常销售 1-跨店销售
    	businessTypeList.add(new Integer(0));
		businessTypeList.add(new Integer(1));
		params.put("businessTypeList", businessTypeList);
		
		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(new Integer(30));
		statusList.add(new Integer(41));
		params.put("statusList", statusList);
		
//		int total = this.shopDayReportManager.findShopDailyReportCount(params);
		int total = this.orderMainManager.findSaleOrderPaywayCount(params);
		if (total < 1) {
			return null;
		}
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		
		SimplePage page = new SimplePage(1, total, (int) total);
//		List<POSOcGroupOrderPayway> dailyReportList = shopDayReportManager.findShopDailyReportByPage(page, orderByField, orderBy, params);
		List<SaleOrderPayway> dailyReportList = orderMainManager.findSaleOrderPaywayByPage(page, orderByField, orderBy, params);
		
		SaleOrderPayway saleOrderPayway = orderMainManager.getSumSaleOrderPayway(params);
    	
    	if (null != saleOrderPayway) {
    		// 组装footer
    		SaleOrderPayway payWay = new SaleOrderPayway();
    		payWay.setShopName("合计");
    		payWay.setPayAmount(saleOrderPayway.getPayAmount());
    		payWay.setReturnAmount(saleOrderPayway.getReturnAmount());
    		payWay.setOrderAmount(saleOrderPayway.getOrderAmount());
    		payWay.setP01(saleOrderPayway.getP01());
    		payWay.setP04(saleOrderPayway.getP04());
    		payWay.setP03(saleOrderPayway.getP03());
    		payWay.setP05(saleOrderPayway.getP05());
    		payWay.setP06(saleOrderPayway.getP06());

    		payWay.setP08(saleOrderPayway.getP08());
    		payWay.setP09(saleOrderPayway.getP09());
    		payWay.setP10(saleOrderPayway.getP10());
    		payWay.setP11(saleOrderPayway.getP11());
    		payWay.setP12(saleOrderPayway.getP12());
    		payWay.setP13(saleOrderPayway.getP13());
    		payWay.setP14(saleOrderPayway.getP14());
    		payWay.setP15(saleOrderPayway.getP15());
    		payWay.setP16(saleOrderPayway.getP16());
    		payWay.setP17(saleOrderPayway.getP17());
    		payWay.setP18(saleOrderPayway.getP18());
    		payWay.setP19(saleOrderPayway.getP19());
    		payWay.setP20(saleOrderPayway.getP20());
    		payWay.setP21(saleOrderPayway.getP21());
    		payWay.setP22(saleOrderPayway.getP22());
    		payWay.setP23(saleOrderPayway.getP23());
    		payWay.setP24(saleOrderPayway.getP24());
    		payWay.setP25(saleOrderPayway.getP25());
    		payWay.setP26(saleOrderPayway.getP26());
    		payWay.setP27(saleOrderPayway.getP27());
    		payWay.setP28(saleOrderPayway.getP28());
    		payWay.setP29(saleOrderPayway.getP29());
    		payWay.setP30(saleOrderPayway.getP30());
    		payWay.setP31(saleOrderPayway.getP31());
    		payWay.setP32(saleOrderPayway.getP32());
    		payWay.setP35(saleOrderPayway.getP35());
    		payWay.setP999(saleOrderPayway.getP999());
    		dailyReportList.add(payWay);
    		
		}
//		return convertDailyReportList(dailyReportList, isSubTotal);
		return dailyReportList;
	}
    
}