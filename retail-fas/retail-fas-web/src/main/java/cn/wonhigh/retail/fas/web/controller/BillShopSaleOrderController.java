package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.model.BillShopSaleOrder;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BillShopSaleOrderManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-02 10:30:30
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
@RequestMapping("/mall_shopsorderd")
@ModuleVerify("30140004")
public class BillShopSaleOrderController extends BaseController<ItemSaleDtlDto> {
    @Resource
    private BillShopSaleOrderManager billShopSaleOrderManager;
    
    @Resource
    private OrderMainManager orderMainManager;
    
//    @Resource
//    private OrderMainInfoDataSet orderMainInfoDataSet;
//
//    @Resource
//	private ShopManager shopManager ;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_sale_order/",billShopSaleOrderManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/mall_shopsorderd";
   	}
    
    @RequestMapping("/saleproxybalancelist")
  	public String saleproxybalancelist() {
  		return "saleproxy_balance/saleproxy_balance_googlists";
  	}
    
    
    @Override
    @ResponseBody
	public Map<String,Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	obj.put("total", 0);
    	obj.put("rows", new ArrayList<POSOrderAndReturnExMainDtl>());
    	obj.put("footer", new ArrayList<BillShopSaleOrder>());
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
//    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
//    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	Map<String, Object> params = builderParams(req, model);
    	//设置参数
    	setCommonsParams(params);
		List<ItemSaleDtlDto> list = new ArrayList<ItemSaleDtlDto>();

		ItemSaleDtlDto itemSaleDtlDto = null;
		itemSaleDtlDto = this.orderMainManager.findShopSaleDetailCount(params);
		if (null != itemSaleDtlDto) {
			SimplePage page = new SimplePage(pageNo, pageSize, itemSaleDtlDto.getTotal());
			list = this.orderMainManager.findShopSaleDetailList(page, params);
		}
		List<ItemSaleDtlDto> footer = new ArrayList<ItemSaleDtlDto>();
		if (null != itemSaleDtlDto) {
			itemSaleDtlDto.setOrderNo("合计");
			footer.add(itemSaleDtlDto);
		}
		obj.put("total", itemSaleDtlDto.getTotal());
		obj.put("rows", list);
		obj.put("footer", footer);
		return obj;
	}
    
    @Override
	public void setCommonsParams(Map<String, Object> params){
    	String orderBillType = params.get("orderBillType") ==null ? "" : params.get("orderBillType").toString();
    	
    	String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		String categoryNo = params.get("categoryNo") == null ? null : params.get("categoryNo").toString();
		String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String qty = params.get("qty") == null ? null : params.get("qty").toString();
		String saleAmount = params.get("saleAmount") == null ? null : params.get("saleAmount").toString();
		
		if(StringUtils.isNotEmpty(brandNos)){// modify By wang.m 06/11
    		params.put("multiBrandNo", FasUtil.formatInQueryCondition(brandNos));
    	}
    	
    	params.put("companyNo",FasUtil.formatInQueryCondition(companyNos));
    	//如果选择了特定的店铺生成
    	if(StringUtils.isNotEmpty(shopNos)){// modify By wang.m 06/11
    		params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNos));
    		params.put("shopNos", null);
		}
    	
    	if(StringUtils.isNotEmpty(brandNos)){
    		params.put("multiBrandNo",  FasUtil.formatInQueryCondition(brandNos));
		}
		
    	if(StringUtils.isNotEmpty(organNo)){
			params.put("organNos",  FasUtil.formatInQueryCondition(organNo));
		}
    	if(StringUtils.isNotEmpty(categoryNo)){// modify By wang.m 06/11
    		params.put("categoryNo", FasUtil.formatInQueryCondition(categoryNo));
    	}
    	
//    	if(params.get("categoryNo") != null && !"".equals(params.get("categoryNo").toString())) {
//			params.put("categoryNos", FasUtil.formatInQueryCondition(params.get("categoryNo").toString()));
//		}
    	
		List<Integer> businessTypeList = new ArrayList<Integer>();
		businessTypeList.add(0);
		businessTypeList.add(1);
		businessTypeList.add(2);
		businessTypeList.add(6);
		params.put("businessTypeList",businessTypeList);
		
		if(!orderBillType.equals("-1")){
    		params.put("orderBillTypes",orderBillType);
    	}
		
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(30);
		statusList.add(41);
		params.put("statusList",statusList);
		
		if (StringUtils.isNotEmpty(qty)) {
			if("0".equals(qty)){
				params.put("noQty",qty);
			}else if("1".equals(qty)){
				params.put("isQty","qty");
			}
		}
		
		if (StringUtils.isNotEmpty(saleAmount)) {
			if("0".equals(saleAmount)){
				params.put("noSaleAmount",saleAmount);
			}else if("1".equals(saleAmount)){
				params.put("isSaleAmount","saleAmount");
			}
		}
	}
    
    
    @Override
	protected void selectManagerMenthod(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
    	this.orderMainManager.findShopSaleDetailList(params, handler);
	}

	/**
     * 销售明细按扣率汇总（扣率横排）——查询动态列
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/discount_columns.json")
	public Map<String,Object> discountColumns(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	Map<String, Object> params = builderParams(req, model);
    	
    	//格式化参数
		params = this.formatParams(params);
    	
    	//查询动态列
    	Map<String, Object> headers = new LinkedHashMap<String, Object>();
    	List<String> columns = billShopSaleOrderManager.selectDiscountSumColumn(params);
    	for(String column : columns) {
    		if("NULL".equals(column)) {
    			headers.put("空扣率", column);
    		}
    		else {
    			//去除小数点，解决数据映射问题
    			headers.put(column, column.replace(".", ""));
    		}
    	}
    	
    	obj.put("headers", headers);
    	return obj;
    }
    	
	/**
     * 销售明细按扣率汇总（扣率横排）——查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/discount_sum.json")
	public Map<String,Object> discountSum(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		params = this.formatParams(params);
    	
    	//总条数
    	int total = 0;
    	//结果集
    	List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
    	//合计
    	List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();
    	
    	// 查询总条数
    	total = billShopSaleOrderManager.selectDiscountSumCount(params);
    	if(total > 0) {
    		// 查询动态列
        	List<String> columns = billShopSaleOrderManager.selectDiscountSumColumn(params);
        	StringBuilder columnStr = new StringBuilder();
        	for(String column : columns) {
        		columnStr.append("CAST(IFNULL(SUM(IF(IFNULL(a.discount,'NULL')='" + column + "',IF(a.balance_base = 1,a.tag_price * a.qty,a.amount),null)),0.00) AS CHAR) AS '" + column.replace(".", "") + "',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	params.put("columns", columnStr);
        	
        	// 查询合计
        	Map<String, Object> totalMap = billShopSaleOrderManager.selectDiscountSumTotal(params);
        	totalMap.put("shop_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	rows = billShopSaleOrderManager.selectDiscountSumData(page, sortColumn, sortOrder, params);
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
    /**
	 * 销售明细按扣率汇总（扣率横排）——导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/discount_sum_export")
	public void doFasExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.formatParams(params);
		
		// 查询动态列
    	List<String> columns = billShopSaleOrderManager.selectDiscountSumColumn(params);
    	StringBuilder columnStr = new StringBuilder();
    	for(String column : columns) {
    		columnStr.append("CAST(IFNULL(SUM(IF(IFNULL(a.discount,'NULL')='" + column + "',IF(a.balance_base = 1,a.tag_price * a.qty,a.amount),null)),0.00) AS CHAR) AS '" + column.replace(".", "") + "',");
    	}
    	columnStr.deleteCharAt(columnStr.length() - 1);
    	params.put("columns", columnStr);
		
    	//查询数据
		List<Map<String, Object>> dataList = billShopSaleOrderManager.selectDiscountSumData(null, sortColumn, sortOrder, params);
		
		// 查询合计
    	Map<String, Object> totalMap = billShopSaleOrderManager.selectDiscountSumTotal(params);
    	totalMap.put("shop_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
    
    /**
	 * 销售明细按扣率汇总（扣率横排）——格式化参数
	 * @param params
	 * @return
	 */
	private Map<String, Object> formatParams(Map<String, Object> params) {
		
		if(params.get("companyNos") != null && !"".equals(params.get("companyNos").toString())) {
			params.put("companyNos", FasUtil.formatInQueryCondition(params.get("companyNos").toString()));
		}
		
		if(params.get("shopNos") != null && !"".equals(params.get("shopNos").toString())) {
			params.put("shopNos", FasUtil.formatInQueryCondition(params.get("shopNos").toString()));
		}
		
		if(params.get("brandNos") != null && !"".equals(params.get("brandNos").toString())) {
			params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
		}
		
		if(params.get("categoryNo") != null && !"".equals(params.get("categoryNo").toString())) {
			params.put("categoryNos", FasUtil.formatInQueryCondition(params.get("categoryNo").toString()));
		}
		
		if(params.get("organNoTemp") != null && !"".equals(params.get("organNoTemp").toString())) {
			params.put("parentOrganNos", FasUtil.formatInQueryCondition(params.get("organNoTemp").toString()));
		}
		return params;
	}
	
	/**
     * 支付金额按扣率汇总（支付类型横排）——查询动态列
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/payWaySum_columns.json")
	public Map<String,Object> payWaySumColumns(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	Map<String, Object> params = builderParams(req, model);
    	
    	//格式化参数
		params = this.payWaySumFormatParams(params);
    	
    	//查询动态列
    	Map<String, Object> headers = new LinkedHashMap<String, Object>();
    	List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
    	for(Map<String, Object> column : columns) {
			headers.put(column.get("pay_name").toString(), column.get("pay_code"));
    	}
    	
    	obj.put("headers", headers);
    	return obj;
    }
    
    /**
     * 支付金额按扣率汇总（支付类型横排）——查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/payWaySum_sum.json")
	public Map<String,Object> payWaySumSum(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		params = this.payWaySumFormatParams(params);
    	
    	//总条数
    	int total = 0;
    	//结果集
    	List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
    	//合计
    	List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();
    	
    	// 查询总条数
    	total = billShopSaleOrderManager.selectPayWaySumCount(params);
    	if(total > 0) {
    		// 查询动态列
    		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
    		StringBuilder columnStr = new StringBuilder();
    		StringBuilder columnStr2 = new StringBuilder();
    		
    		for(Map<String, Object> column : columns) {
    			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
    			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	columnStr2.deleteCharAt(columnStr2.length() - 1);
        	
        	params.put("columns", columnStr);
        	params.put("columns2", columnStr2);
        	
        	// 查询合计
        	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWaySumTotal(params);
        	totalMap.put("shop_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWaySumData(page, sortColumn, sortOrder, params);
        	
        	// 是否小计
        	if("true".equals(params.get("isCount"))) {
        		List<String> countColumns = new ArrayList<String>();
            	countColumns.add("amount");
            	countColumns.add("tag_price");
            	countColumns.add("total");
            	for(Map<String, Object> column : columns) {
            		countColumns.add(column.get("pay_code").toString());
            	}
            	dataList = payWaylittleCount(dataList, countColumns);
        	}
        	
        	rows = dataList;
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
    /**
	 * 支付金额按扣率汇总（支付类型横排）——导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/payWaySum_sum_export")
	public void doPayWaySumExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.payWaySumFormatParams(params);
		
		// 查询动态列
		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
		StringBuilder columnStr = new StringBuilder();
		StringBuilder columnStr2 = new StringBuilder();
		
		for(Map<String, Object> column : columns) {
			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
    	}
    	columnStr.deleteCharAt(columnStr.length() - 1);
    	columnStr2.deleteCharAt(columnStr2.length() - 1);
    	
    	params.put("columns", columnStr);
    	params.put("columns2", columnStr2);
		
    	//查询数据
		List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWaySumData(null, sortColumn, sortOrder, params);
		
		// 是否小计
    	if("true".equals(params.get("isCount"))) {
    		List<String> countColumns = new ArrayList<String>();
        	countColumns.add("amount");
        	countColumns.add("tag_price");
        	countColumns.add("total");
        	for(Map<String, Object> column : columns) {
        		countColumns.add(column.get("pay_code").toString());
        	}
        	dataList = payWaylittleCount(dataList, countColumns);
    	}
		
		// 查询合计
    	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWaySumTotal(params);
    	totalMap.put("shop_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
	
	/**
     * 支付金额按销售订单查询（支付类型横排）——查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/payWayOrder.json")
	public Map<String,Object> payWayOrder(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		params = this.payWaySumFormatParams(params);
    	
    	//总条数
    	int total = 0;
    	//结果集
    	List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
    	//合计
    	List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();
    	
    	// 查询总条数
    	total = billShopSaleOrderManager.selectPayWayOrderCount(params);
    	if(total > 0) {
    		// 查询动态列
    		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
    		StringBuilder columnStr = new StringBuilder();
    		
    		for(Map<String, Object> column : columns) {
    			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	
        	params.put("columns", columnStr);
        	
        	// 查询合计
        	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayOrderTotal(params);
        	totalMap.put("shop_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	rows = billShopSaleOrderManager.selectPayWayOrderData(page, sortColumn, sortOrder, params);
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
    /**
	 * 支付金额按销售订单查询（支付类型横排）——导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/payWayOrder_export")
	public void doPayWayOrderExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.payWaySumFormatParams(params);
		
		// 查询动态列
		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
		StringBuilder columnStr = new StringBuilder();
		
		for(Map<String, Object> column : columns) {
			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
    	}
    	columnStr.deleteCharAt(columnStr.length() - 1);
    	
    	params.put("columns", columnStr);
		
    	//查询数据
		List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayOrderData(null, sortColumn, sortOrder, params);
		
		// 查询合计
    	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayOrderTotal(params);
    	totalMap.put("shop_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
	
	/**
     * 支付金额按销售订单查询-明细分摊，品牌汇总（支付类型横排）——查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/payWayOrderBrand.json")
	public Map<String,Object> payWayOrderBrand(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		params = this.payWaySumFormatParams(params);
    	
    	//总条数
    	int total = 0;
    	//结果集
    	List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
    	//合计
    	List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();
    	
    	// 查询总条数
    	total = billShopSaleOrderManager.selectPayWayOrderBrandCount(params);
    	if(total > 0) {
        	
        	// 查询动态列
    		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
    		StringBuilder columnStr = new StringBuilder();
    		StringBuilder columnStr2 = new StringBuilder();
    		
    		for(Map<String, Object> column : columns) {
    			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
    			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	columnStr2.deleteCharAt(columnStr2.length() - 1);
        	
        	params.put("columns", columnStr);
        	params.put("columns2", columnStr2);
        	
        	// 查询合计
        	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayOrderBrandTotal(params);
        	totalMap.put("shop_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayOrderBrandData(page, sortColumn, sortOrder, params);
        	
        	// 是否小计
        	if("true".equals(params.get("isCount"))) {
        		List<String> countColumns = new ArrayList<String>();
            	countColumns.add("amount");
            	countColumns.add("tag_price");
            	for(Map<String, Object> column : columns) {
            		countColumns.add(column.get("pay_code").toString());
            	}
            	dataList = payWaylittleCount(dataList, countColumns);
        	}
        	
        	rows = dataList;
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
    /**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总（支付类型横排）——导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/payWayOrderBrand_export")
	public void doPayWayOrderBrandExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.payWaySumFormatParams(params);
		
		// 查询动态列
		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
		StringBuilder columnStr = new StringBuilder();
		StringBuilder columnStr2 = new StringBuilder();
		
		for(Map<String, Object> column : columns) {
			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
    	}
    	columnStr.deleteCharAt(columnStr.length() - 1);
    	columnStr2.deleteCharAt(columnStr2.length() - 1);
    	
    	params.put("columns", columnStr);
    	params.put("columns2", columnStr2);
		
    	//查询数据
		List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayOrderBrandData(null, sortColumn, sortOrder, params);
		
		// 是否小计
    	if("true".equals(params.get("isCount"))) {
    		List<String> countColumns = new ArrayList<String>();
        	countColumns.add("amount");
        	countColumns.add("tag_price");
        	for(Map<String, Object> column : columns) {
        		countColumns.add(column.get("pay_code").toString());
        	}
        	dataList = payWaylittleCount(dataList, countColumns);
    	}
    	
		// 查询合计
    	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayOrderBrandTotal(params);
    	totalMap.put("shop_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
	
	/**
     * 支付金额按明细分摊，日统计（支付类型横排）——查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/payWayDay.json")
	public Map<String,Object> payWayDay(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		params = this.payWaySumFormatParams(params);
    	
    	//总条数
    	int total = 0;
    	//结果集
    	List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
    	//合计
    	List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();
    	
    	// 查询总条数
    	total = billShopSaleOrderManager.selectPayWayDayCount(params);
    	if(total > 0) {
        	
        	// 查询动态列
    		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
    		StringBuilder columnStr = new StringBuilder();
    		StringBuilder columnStr2 = new StringBuilder();
    		
    		for(Map<String, Object> column : columns) {
    			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
    			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	columnStr2.deleteCharAt(columnStr2.length() - 1);
        	
        	params.put("columns", columnStr);
        	params.put("columns2", columnStr2);
        	
        	// 查询合计
        	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayDayTotal(params);
        	totalMap.put("shop_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayDayData(page, sortColumn, sortOrder, params);
        	
        	// 是否小计
        	if("true".equals(params.get("isCount"))) {
        		List<String> countColumns = new ArrayList<String>();
            	countColumns.add("amount");
            	countColumns.add("order_amount");
            	countColumns.add("return_amount");
            	countColumns.add("tag_price");
            	for(Map<String, Object> column : columns) {
            		countColumns.add(column.get("pay_code").toString());
            	}
            	dataList = payWaylittleCount(dataList, countColumns);
        	}
        	
        	rows = dataList;
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
    /**
	 * 支付金额按明细分摊，日统计（支付类型横排）——导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/payWayDay_export")
	public void doPayWayDayExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.payWaySumFormatParams(params);
		
		// 查询动态列
		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
		StringBuilder columnStr = new StringBuilder();
		StringBuilder columnStr2 = new StringBuilder();
		
		for(Map<String, Object> column : columns) {
			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
    	}
    	columnStr.deleteCharAt(columnStr.length() - 1);
    	columnStr2.deleteCharAt(columnStr2.length() - 1);
    	
    	params.put("columns", columnStr);
    	params.put("columns2", columnStr2);
		
    	//查询数据
		List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayDayData(null, sortColumn, sortOrder, params);
		
		// 是否小计
    	if("true".equals(params.get("isCount"))) {
    		List<String> countColumns = new ArrayList<String>();
        	countColumns.add("amount");
        	countColumns.add("order_amount");
        	countColumns.add("return_amount");
        	countColumns.add("tag_price");
        	for(Map<String, Object> column : columns) {
        		countColumns.add(column.get("pay_code").toString());
        	}
        	dataList = payWaylittleCount(dataList, countColumns);
    	}
    	
		// 查询合计
    	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayDayTotal(params);
    	totalMap.put("shop_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
	
	/**
     * 支付金额按明细分摊，月统计（支付类型横排）——查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/payWayMonth.json")
	public Map<String,Object> payWayMonth(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		params = this.payWaySumFormatParams(params);
    	
    	//总条数
    	int total = 0;
    	//结果集
    	List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
    	//合计
    	List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();
    	
    	// 查询总条数
    	total = billShopSaleOrderManager.selectPayWayMonthCount(params);
    	if(total > 0) {
        	
        	// 查询动态列
    		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
    		StringBuilder columnStr = new StringBuilder();
    		StringBuilder columnStr2 = new StringBuilder();
    		StringBuilder columnStr3 = new StringBuilder();
    		
    		for(Map<String, Object> column : columns) {
    			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
    			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
    			columnStr3.append("ROUND(SUM(AAA."+column.get("pay_code")+"),2) AS '"+ column.get("pay_code")+"',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	columnStr2.deleteCharAt(columnStr2.length() - 1);
        	columnStr3.deleteCharAt(columnStr3.length() - 1);
        	
        	params.put("columns", columnStr);
        	params.put("columns2", columnStr2);
        	params.put("columns3", columnStr3);
        	
        	// 查询合计
        	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayMonthTotal(params);
        	totalMap.put("shop_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayMonthData(page, sortColumn, sortOrder, params);
        	
        	// 是否小计
        	if("true".equals(params.get("isCount"))) {
        		List<String> countColumns = new ArrayList<String>();
            	countColumns.add("amount");
            	countColumns.add("order_amount");
            	countColumns.add("return_amount");
            	countColumns.add("tag_price");
            	countColumns.add("total");
            	for(Map<String, Object> column : columns) {
            		countColumns.add(column.get("pay_code").toString());
            	}
            	dataList = payWaylittleCount(dataList, countColumns);
        	}
        	
        	rows = this.calculateError(dataList,columns);//处理误差
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
	/**
	 * 支付金额按明细分摊，月统计（支付类型横排）——导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/payWayMonth_export")
	public void doPayWayMonthExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.payWaySumFormatParams(params);
		
		// 查询动态列
		List<Map<String, Object>> columns = billShopSaleOrderManager.selectPayWaySumColumn(params);
		StringBuilder columnStr = new StringBuilder();
		StringBuilder columnStr2 = new StringBuilder();
		StringBuilder columnStr3 = new StringBuilder();
		
		for(Map<String, Object> column : columns) {
			columnStr.append("IFNULL(SUM(IF(pay.pay_code = '" + column.get("pay_code") +  "',pay.amount,NULL)),0.00) AS '" + column.get("pay_code") + "',");
			columnStr2.append("ROUND(SUM((IFNULL(a1.amount/a1.all_amount,0))*a1." + column.get("pay_code") + "),2) AS '" + column.get("pay_code") + "',");
			columnStr3.append("ROUND(SUM(AAA."+column.get("pay_code")+"),2) AS '"+ column.get("pay_code")+"',");
    	}
    	columnStr.deleteCharAt(columnStr.length() - 1);
    	columnStr2.deleteCharAt(columnStr2.length() - 1);
    	columnStr3.deleteCharAt(columnStr3.length() - 1);
    	
    	params.put("columns", columnStr);
    	params.put("columns2", columnStr2);
    	params.put("columns3", columnStr3);
		
    	//查询数据
		List<Map<String, Object>> dataList = billShopSaleOrderManager.selectPayWayMonthData(null, sortColumn, sortOrder, params);
		
		// 是否小计
    	if("true".equals(params.get("isCount"))) {
    		List<String> countColumns = new ArrayList<String>();
        	countColumns.add("amount");
        	countColumns.add("order_amount");
        	countColumns.add("return_amount");
        	countColumns.add("tag_price");
        	countColumns.add("total");
        	for(Map<String, Object> column : columns) {
        		countColumns.add(column.get("pay_code").toString());
        	}
        	dataList = payWaylittleCount(dataList, countColumns);
    	}
    	dataList = this.calculateError(dataList, columns);
    	
		// 查询合计
    	Map<String, Object> totalMap = billShopSaleOrderManager.selectPayWayMonthTotal(params);
    	totalMap.put("shop_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
	
	/**
	 * 支付金额统计--小计
	 * @param dataList 原数据列表
	 * @param countColumns 需要小计的列
	 * @return
	 */
	private List<Map<String, Object>> payWaylittleCount(List<Map<String, Object>> dataList, List<String> countColumns) {
		
		String shopNoTemp = "";
		Map<String, Object> groupTemp = null;
		
		for(int i = 0; i < dataList.size(); i++) {
			
			Map<String, Object> data = dataList.get(i);
			
			String shopNoCompare = data.get("shop_no").toString();
			
			//小计维度根据条件判断分组情况
			boolean isSameGroup = true;
			if(!shopNoTemp.equals(shopNoCompare)){
				isSameGroup = false;
			}
			
			//分组不同则新初始化一条小计
			if(!isSameGroup) {
				if(groupTemp != null) {
					dataList.add(i, groupTemp);
					i++;
				}
				groupTemp = new HashMap<String, Object>();
				groupTemp.put("shop_name", "小计：");
				
				shopNoTemp = shopNoCompare;
			}
			
			// 累加
			for(String column : countColumns) {
				BigDecimal bdTemp = null;
				if(groupTemp.get(column) != null) {
					bdTemp = new BigDecimal(groupTemp.get(column).toString()) ;
				}
				else {
					bdTemp = new BigDecimal("0");
				}
				
				groupTemp.put(column, BigDecimalUtil.add(bdTemp, new BigDecimal(data.get(column).toString())));
			}
			
			if(i == dataList.size()-1) {
				dataList.add(i + 1, groupTemp);
				i++;
			}
		}
		
		return dataList;
	}
	
	/**
	 * 支付金额——格式化参数
	 * @param params
	 * @return
	 */
	private Map<String, Object> payWaySumFormatParams(Map<String, Object> params) {
		
		if(params.get("companyNo") != null && !"".equals(params.get("companyNo").toString())) {
			params.put("companyNos", FasUtil.formatInQueryCondition(params.get("companyNo").toString()));
		}
		
		if(params.get("shopNoTemp") != null && !"".equals(params.get("shopNoTemp").toString())) {
			params.put("shopNos", FasUtil.formatInQueryCondition(params.get("shopNoTemp").toString()));
		}
		
		if(params.get("brandNos") != null && !"".equals(params.get("brandNos").toString())) {
			params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
		}
		
		if(params.get("organNoTemp") != null && !"".equals(params.get("organNoTemp").toString())) {
			params.put("parentOrganNos", FasUtil.formatInQueryCondition(params.get("organNoTemp").toString()));
		}
		
		//IP-内购
		if(params.get("type") != null && "ip".equals(params.get("type").toString())) {
			params.put("businessTypes", "('3')");
		}
		//MS-门店
		else {
			params.put("businessTypes", "('0', '1', '2', '6')");
		}
		return params;
	}
	
    /**
     * 误差处理
     * @param dataList
     * @return
     */
    private List<Map<String, Object>> calculateError(List<Map<String, Object>> dataList,List<Map<String, Object>> columns) {
		for (Map<String, Object> map : dataList) {
			BigDecimal total = (BigDecimal) map.get("total");
			BigDecimal sub = BigDecimal.valueOf(0d);
			for(int i=1;i<columns.size();i++){
				sub = sub.add((BigDecimal) map.get(columns.get(i).get("pay_code")));
			}
			map.put((String) columns.get(0).get("pay_code"), total.subtract(sub));
		}
		return dataList;
	}
}