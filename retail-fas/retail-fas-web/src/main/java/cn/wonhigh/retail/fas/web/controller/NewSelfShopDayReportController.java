package cn.wonhigh.retail.fas.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.OrderPayway;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.DepositCashManager;
import cn.wonhigh.retail.fas.manager.OrderPaywayManager;
import cn.wonhigh.retail.fas.manager.ShopDayReportManager;
import cn.wonhigh.retail.fas.web.utils.DayReportControllerHelper;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;


@Controller
@RequestMapping("/self_shop_day_report_new")
@ModuleVerify("30170000")
public class NewSelfShopDayReportController extends BaseController<SaleOrderPayway> {
	
	protected static final XLogger logger = XLoggerFactory.getXLogger(NewSelfShopDayReportController.class);
	@Resource
	private OrderPaywayManager orderPaywayManager;
	
	@Resource
	private ShopDayReportManager shopDayReportManager;
	
	@Resource
	private DepositCashManager depositCashManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("IndepShop_management/day_report/", null);
	}
	
	@RequestMapping("/day_report_all")
	public String self_shop_day_report_all() {
		return "IndepShop_management/day_report/day_report_all";
	}

	@RequestMapping("/day_report_brand")
	public String self_shop_day_report_brand() {
		return "IndepShop_management/day_report/day_report_brand";
	}
	
	public Map<String,Object> getParams(HttpServletRequest req, Model model){
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		
		String shopNo = (String) params.get("shopNo");
		List<String> shopNoList = new ArrayList<String>();
		if(shopNo.indexOf(",")!=-1){
			String[] shopNos = shopNo.split(",");
			for (String s : shopNos) {
				shopNoList.add(s);
			}
		}else{
			if(StringUtils.isNotBlank(shopNo)){
				shopNoList.add(shopNo);
			}
		}
		String brandNo = params.get("brandNo") == null ? null : params.get("brandNo").toString();
		params.put("brandNoLists", FasUtil.formatInQueryCondition(brandNo));
		
		params.remove("shopNo");
		params.put("shopNoList", shopNoList);		
		//查询支付方式
		List<OrderPayway> payWays = orderPaywayManager.queryAllPayWays();
		params = this.getQueryParams(params,payWays);
		obj.put("params", params);
		obj.put("pageNo", pageNo);
		obj.put("pageSize", pageSize);
		obj.put("payWays", payWays);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getResult(Map<String, Object> param,DayReportControllerHelper helper) throws ManagerException{
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> params = (Map<String, Object>) param.get("params");
		if (params == null) {
			obj.put("rows", new ArrayList<SaleOrderPayway>());
			obj.put("total", 0);
			return obj;
		}
		
		List<Map<String, Object>> list = helper.getList(param);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		int pageNo = (int) param.get("pageNo");
		int pageSize = (int) param.get("pageSize");
		for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
			if (i > (list.size() - 1))
				break;
			resultList.add(list.get(i));
		}
		

		obj.put("total", list.size());
		obj.put("rows", resultList);
		obj.put("footer", helper.getFooterList(param));
		return obj;
	}
	
	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = getParams(req, model);
		DayReportControllerHelper helper = new DayReportControllerHelper() {
			
			@SuppressWarnings("unchecked")
			@Override
			public List<Map<String, Object>> getList(Map<String, Object> params) throws ManagerException {
				Map<String, Object> param = (Map<String, Object>) params.get("params");
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = deepCopy(shopDayReportManager.findList(param));
				list = getPaidinAmountByOutDate(list, param);
				return list;
			}

			@Override
			public List<Map<String, Object>> getExportList(Map<String, Object> params) throws ManagerException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Map<String, Object>> getFooterList(Map<String, Object> params) throws ManagerException {
				return this.getFooter(params);
			}
		};
		
		return getResult(params,helper);
		
	}
	
	/**
	 * 所有品牌导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_all_export")
	public void doAllExport(HttpServletRequest req, Model model,HttpServletResponse response) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
		// 增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(exportColumns)) {
			// 字段名列表
			List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
			});
			
			columnsList = this.sortExportColumns(columnsList);
			ExportComplexVo exportVo = new ExportComplexVo();
			exportVo.setColumnsMapList(columnsList);

			if (StringUtils.isNotEmpty(firstHeaderColumns)) {
				List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
				});
				exportVo.setHeaderColumnsMapList(headerColumnsList);
			}
			List<Map> list = new ArrayList<Map>(this.queryExportList(params));
			for(Map<String, Object> vo:list){
				vo.put("subExportData", new ArrayList<Map>(1));
			}
			if(list.size() > 0){
				Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
				exportVo.setRowAccessWindowSize(rowAccessWindowSize);
				exportVo.setDataMapList(list);
				exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
				HSSFExportComplex.commonExportData(exportVo, response);
			}
		}
		
	}
	
	private List<Map<String, Object>> queryExportList(Map<String, Object> params) throws ManagerException {
		List<OrderPayway> payWays = orderPaywayManager.queryAllPayWays();
		String shopNo = (String) params.get("shopNo");
		List<String> shopNoList = new ArrayList<String>();
		if(shopNo.indexOf(",")!=-1){
			String[] shopNos = shopNo.split(",");
			for (String s : shopNos) {
				shopNoList.add(s);
			}
		}else{
			if(StringUtils.isNotBlank(shopNo)){
				shopNoList.add(shopNo);
			}
		}
		
		params.remove("shopNo");
		params.put("shopNoList", shopNoList);		
		
		params = this.getQueryParams(params,payWays);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = deepCopy(shopDayReportManager.findList(params));
		list = getPaidinAmountByOutDate(list, params);
		return list;
	}

	@RequestMapping("/list_brand")
	@ResponseBody
	public Map<String, Object> queryListForBrand(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = getParams(req, model);
		DayReportControllerHelper helper = new DayReportControllerHelper() {
			
			@SuppressWarnings("unchecked")
			@Override
			public List<Map<String, Object>> getList(Map<String, Object> params) throws ManagerException {
				Map<String, Object> param = (Map<String, Object>) params.get("params");
				List<OrderPayway> payWays = (List<OrderPayway>) params.get("payWays");
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = deepCopy(shopDayReportManager.findDayReportForBrandList(param));
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(param);
				list = this.getPaidinAmountForBrandByOutDate(list, map);//分品牌获取实收金额
				list = this.dailyReportListByShopNo(list,payWays);//销售统计小计
				list = this.getBrandReportListBySaleError(list,payWays);//销售保留2位小数，及误差处理
				list = this.dailyReportListByBrand(list);//分品牌计算实收，差异,银行卡退款、实际退款
				return list;
			}
			
			@Override
			public List<Map<String, Object>> getExportList(Map<String, Object> params) throws ManagerException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Map<String, Object>> getFooterList(Map<String, Object> params) throws ManagerException {
				return this.getFooter(params);
			}
		};
		return getResult(params, helper);
	}
	
	@RequestMapping(value = "/do_brand_export")
	public void doBrandExport(HttpServletRequest req, Model model,HttpServletResponse response) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
		// 增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(exportColumns)) {
			// 字段名列表
			List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
			});
			
			columnsList = this.sortExportColumns(columnsList);
			ExportComplexVo exportVo = new ExportComplexVo();
			exportVo.setColumnsMapList(columnsList);

			if (StringUtils.isNotEmpty(firstHeaderColumns)) {
				List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
				});
				exportVo.setHeaderColumnsMapList(headerColumnsList);
			}
			List<Map> list = new ArrayList<Map>(this.queryBrandExportList(params));
			for(Map<String, Object> vo:list){
				vo.put("subExportData", new ArrayList<Map>(1));
			}
			if(list.size() > 0){
				Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
				exportVo.setRowAccessWindowSize(rowAccessWindowSize);
				exportVo.setDataMapList(list);
				exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
				HSSFExportComplex.commonExportData(exportVo, response);
			}
		}
	}
	
	private List<Map<String, Object>> queryBrandExportList(Map<String, Object> params) throws ManagerException {
		List<OrderPayway> payWays = orderPaywayManager.queryAllPayWays();
		String shopNo = (String) params.get("shopNo");
		List<String> shopNoList = new ArrayList<String>();
		if(shopNo.indexOf(",")!=-1){
			String[] shopNos = shopNo.split(",");
			for (String s : shopNos) {
				shopNoList.add(s);
			}
		}else{
			if(StringUtils.isNotBlank(shopNo)){
				shopNoList.add(shopNo);
			}
		}
		
		params.remove("shopNo");
		params.put("shopNoList", shopNoList);		
		params = this.getQueryParams(params,payWays);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("params", params);
		map.put("payWays", payWays);
		DayReportControllerHelper helper = new DayReportControllerHelper() {
			
			@SuppressWarnings("unchecked")
			@Override
			public List<Map<String, Object>> getList(Map<String, Object> params) throws ManagerException {
				Map<String, Object> param = (Map<String, Object>) params.get("params");
				List<OrderPayway> payWays = (List<OrderPayway>) params.get("payWays");
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list = deepCopy(shopDayReportManager.findDayReportForBrandList(param));
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(param);
				list = this.getPaidinAmountForBrandByOutDate(list, map);//分品牌获取实收金额
				list = this.dailyReportListByShopNo(list,payWays);//销售统计小计
				list = this.getBrandReportListBySaleError(list,payWays);//销售保留2位小数，及误差处理
				list = this.dailyReportListByBrand(list);//分品牌计算实收，差异
				return list;
			}
			
			@Override
			public List<Map<String, Object>> getExportList(Map<String, Object> params) throws ManagerException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Map<String, Object>> getFooterList(Map<String, Object> params) throws ManagerException {
				return this.getFooter(params);
			}
		};
		List<Map<String, Object>> list = helper.getList(map);
		List<Map<String, Object>> footer = helper.getFooterList(map);
		list.addAll(footer);
		return list;
	}

	@RequestMapping("/query_columns")
	@ResponseBody
	public Map<String,Object> queryColumns(HttpServletRequest req, Model model) throws ManagerException{
		//根据动态列和查询条件查询所有明细对象
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> headers = new LinkedHashMap<String, Object>();
		List<OrderPayway> list = orderPaywayManager.queryAllPayWays();
		Map<String,Object> show = this.getShow(req, model, list);
		
		for(OrderPayway orderPayway : list) {
			String payCode = orderPayway.getPayCode();
			String payName = orderPayway.getPayName();
			for (String key:show.keySet()) {
				BigDecimal val = (BigDecimal) show.get(key);
				if(val.compareTo(BigDecimal.valueOf(0d))!=0){
					if(key.equals("P"+payCode)){
						headers.put("P"+payCode, payName);
					}else if(key.equals("totalAmount")){
						headers.put("totalAmount", "销售总计");
					}else if(key.equals("S"+payCode)){
						headers.put("S"+payCode, payName);
					}else if(key.equals("amount")){
						headers.put("amount", "实收总计");
					}else if(key.equals("D"+payCode)){
						headers.put("D"+payCode, payName);
					}else if(key.equals("returnAmount")){
						headers.put("returnAmount", "退款金额");
					}else if(key.equals("actualReturnAmount")){
						headers.put("actualReturnAmount", "实际退款金额");
					}else if(key.equals("diffAmount")){
						headers.put("diffAmount", "差异总计");
					}else if(key.equals("poundage")){
						headers.put("poundage", "手续费");
					}else if(key.equals("sum")){
						headers.put("sum", "进账总计");
					}
				}
			}
		}
			
		obj.put("headers", this.sortShowHeaders(headers));
		obj.put("show", this.getShowColumns(show));
		return obj;
	}
	
	private Map<String, Object> sortShowHeaders(Map<String, Object> headers) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		for(String key:headers.keySet()){
			if(key.matches("P\\d+")){
				result.put(key, headers.get(key));
			}
		}
		String totalAmount = (String) headers.get("totalAmount");
		if(totalAmount != null){
			result.put("totalAmount", headers.get("totalAmount"));
		}
		for(String key:headers.keySet()){
			if(key.matches("S\\d+")){
				result.put(key, headers.get(key));
			}
		}
		String amount = (String) headers.get("amount");
		if(amount != null){
			result.put("amount", headers.get("amount"));
		}
		for(String key:headers.keySet()){
			if(key.matches("D\\d+")){
				result.put(key, headers.get(key));
			}
		}
		String returnAmount = (String) headers.get("returnAmount");
		if(returnAmount != null){
			result.put("returnAmount", headers.get("returnAmount"));
		}
		String actualReturnAmount = (String) headers.get("actualReturnAmount");
		if(actualReturnAmount != null){
			result.put("actualReturnAmount", headers.get("actualReturnAmount"));
		}
		String diffAmount = (String) headers.get("diffAmount");
		if(diffAmount != null){
			result.put("diffAmount", headers.get("diffAmount"));
		}
		String poundage = (String) headers.get("poundage");
		if(poundage != null){
			result.put("poundage", headers.get("poundage"));
		}
		String sum = (String) headers.get("sum");
		if(sum != null){
			result.put("sum", headers.get("sum"));
		}
		return result;
	}

	private Map<String, Object> getShow(HttpServletRequest req, Model model, List<OrderPayway> payWays) throws ManagerException {
		try {
			Map<String, Object> params = builderParams(req, model);
			
			String shopNo = (String) params.get("shopNo");
			List<String> shopNoList = new ArrayList<String>();
			if(shopNo.indexOf(",")!=-1){
				String[] shopNos = shopNo.split(",");
				for (String s : shopNos) {
					shopNoList.add(s);
				}
			}else{
				if(StringUtils.isNotBlank(shopNo)){
					shopNoList.add(shopNo);
				}
			}
			String brandNo = params.get("brandNo") == null ? null : params.get("brandNo").toString();
			params.put("brandNoLists", FasUtil.formatInQueryCondition(brandNo));
			
			params.remove("shopNo");
			params.put("shopNoList", shopNoList);	
			
			String flag = req.getParameter("flag");
			List<Map<String, Object>> list = null;
			params = this.getQueryParams(params,payWays);
			if (params == null) {
				return null;
			}
			
			if("all".equals(flag)){
				list = this.deepCopy(shopDayReportManager.findList(params));//所有品牌查询
				list = getPaidinAmountByOutDate(list, params);
			}else if("brand".equals(flag)){
				DayReportControllerHelper helper = new DayReportControllerHelper() {
					
					@Override
					public List<Map<String, Object>> getList(Map<String, Object> params) throws ManagerException {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public List<Map<String, Object>> getExportList(Map<String, Object> params) throws ManagerException {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public List<Map<String, Object>> getFooterList(Map<String, Object> params) throws ManagerException {
						// TODO Auto-generated method stub
						return null;
					}
				};
				list = deepCopy(shopDayReportManager.findDayReportForBrandList(params));
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(params);
				list = helper.getPaidinAmountForBrandByOutDate(list, map);//获取现金实收金额
				list = helper.dailyReportListByBrand(list);//分品牌计算实收，差异
			}
			
			Map<String, Object> show = new HashMap<String, Object>();
			Map<String, Object> temp = null;
			
			int i = 0, len = list.size();
			while (i < len) {
				temp = list.get(i);
				for(String key:temp.keySet()){
					if(key.matches("P\\d+") || key.equals("totalAmount")
						|| key.matches("S\\d+") || key.equals("amount")
						|| key.matches("D\\d+") || key.equals("diffAmount")
						|| key.equals("returnAmount") || key.equals("actualReturnAmount")
						|| key.equals("poundage") || key.equals("sum")){
						BigDecimal val = (BigDecimal) temp.get(key);
						if(val!=null&&val.compareTo(BigDecimal.ZERO)!=0){
							show.put(key, val);
						}
					}
				}
				
				i++;
			}
			
			return show;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		} 
	}

	private Map<String, Object> getQueryParams(Map<String, Object> params, List<OrderPayway> payWays) {
		StringBuffer ppColumns = new StringBuffer();
		StringBuffer ssColumns = new StringBuffer();
		StringBuffer ddColumns = new StringBuffer();
		StringBuffer ptColumns = new StringBuffer();
		StringBuffer pt01Columns = new StringBuffer();
		StringBuffer pt02Columns = new StringBuffer();
		StringBuffer stColumns = new StringBuffer();
		StringBuffer st01Columns = new StringBuffer();
		StringBuffer st02Columns = new StringBuffer();
		StringBuffer brandColumns = new StringBuffer();
		StringBuffer brandPPColumns = new StringBuffer();
		StringBuffer brandAPColumns = new StringBuffer();
		StringBuffer brandAAColumns = new StringBuffer();
		StringBuffer brandAA01Columns = new StringBuffer();
		StringBuffer brandAA02Columns = new StringBuffer();
		for (OrderPayway orderPayway : payWays) {
			ppColumns.append(" IFNULL(PP.P"+orderPayway.getPayCode()+",0) P"+orderPayway.getPayCode()+",");
			ptColumns.append(" SUM(P"+orderPayway.getPayCode()+") P"+orderPayway.getPayCode()+",");
			pt01Columns.append(" IF(TRIM(op.pay_code)='"+orderPayway.getPayCode()+"',op.amount,0) P"+orderPayway.getPayCode()+",");
			pt02Columns.append(" IF(TRIM(op.pay_code)='"+orderPayway.getPayCode()+"',op.amount,0) P"+orderPayway.getPayCode()+",");
			
			if(!"01".equals(orderPayway.getPayCode())){
				ssColumns.append(" IFNULL(SS.S"+orderPayway.getPayCode()+",0) S"+orderPayway.getPayCode()+",");
				ddColumns.append(" IFNULL(SS.S"+orderPayway.getPayCode()+",0) - IFNULL(PP.P"+orderPayway.getPayCode()+",0) D"+orderPayway.getPayCode()+",");
				stColumns.append(" SUM(S"+orderPayway.getPayCode()+") S"+orderPayway.getPayCode()+",");
				st01Columns.append(" IF((TRIM(op.pay_code)='"+orderPayway.getPayCode()+"' AND bs. STATUS = 1),op.amount,0) S"+orderPayway.getPayCode()+",");
				if("04".equals(orderPayway.getPayCode())){
					st02Columns.append(" IF((TRIM(op.pay_code)='"+orderPayway.getPayCode()+"' AND bs. STATUS = 1 AND (rem.old_out_date IS NULL OR rem.old_out_date <> rem.out_date) AND rem.amount > 0) OR (TRIM(op.pay_code)='"+orderPayway.getPayCode()+"' AND rem.old_out_date = rem.out_date AND bs. STATUS = 1),op.amount,0) S"+orderPayway.getPayCode()+",");
				}else{
					st02Columns.append(" IF((TRIM(op.pay_code)='"+orderPayway.getPayCode()+"' AND bs. STATUS = 1),op.amount,0) S"+orderPayway.getPayCode()+",");
				}
			}
			
			//分品牌特殊处理
			if(!"01".equals(orderPayway.getPayCode())){
				brandColumns.append(" IFNULL(SS.S"+orderPayway.getPayCode()+",0) S"+orderPayway.getPayCode()+",");
			}
			
			brandPPColumns.append(" SUM(IFNULL(P"+orderPayway.getPayCode()+",0)) P"+orderPayway.getPayCode()+",");
			brandAPColumns.append(" PT.P"+orderPayway.getPayCode()+" +");
			brandAAColumns.append(" IF(a.pay_code = '"+orderPayway.getPayCode()+"',IF(a.all_amount=0,dtl.amount,(dtl.amount / a.all_amount) * a.P"+orderPayway.getPayCode()+"),0) P"+orderPayway.getPayCode()+",");
			brandAA01Columns.append(" IFNULL(SUM(IF(op.pay_code='"+orderPayway.getPayCode()+"',op.amount,0)),0) P"+orderPayway.getPayCode()+",");
			brandAA02Columns.append(" IFNULL(SUM(IF(op.pay_code='"+orderPayway.getPayCode()+"',op.amount,0)),0) P"+orderPayway.getPayCode()+",");
		}
		params.put("ppColumns", ppColumns.deleteCharAt(ppColumns.length()-1));
		params.put("ssColumns", ssColumns.deleteCharAt(ssColumns.length()-1));
		params.put("ddColumns", ddColumns.deleteCharAt(ddColumns.length()-1));
		params.put("ptColumns", ptColumns.deleteCharAt(ptColumns.length()-1));
		params.put("pt01Columns", pt01Columns.deleteCharAt(pt01Columns.length()-1));
		params.put("pt02Columns", pt02Columns.deleteCharAt(pt02Columns.length()-1));
		params.put("stColumns", stColumns.deleteCharAt(stColumns.length()-1));
		params.put("st01Columns", st01Columns.deleteCharAt(st01Columns.length()-1));
		params.put("st02Columns", st02Columns.deleteCharAt(st02Columns.length()-1));
		params.put("brandColumns", brandColumns.deleteCharAt(brandColumns.length()-1));
		params.put("brandPPColumns", brandPPColumns.deleteCharAt(brandPPColumns.length()-1));
		params.put("brandAPColumns", brandAPColumns.deleteCharAt(brandAPColumns.length()-1));
		params.put("brandAAColumns", brandAAColumns.deleteCharAt(brandAAColumns.length()-1));
		params.put("brandAA01Columns", brandAA01Columns.deleteCharAt(brandAA01Columns.length()-1));
		params.put("brandAA02Columns", brandAA02Columns.deleteCharAt(brandAA02Columns.length()-1));
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
		//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(Integer.valueOf(30));
		statusList.add(Integer.valueOf(41));
		params.put("statusList", statusList);
		return params;
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> deepCopy(List<Map<String, Object>> srcList) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(srcList);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(
					byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			List<Map<String, Object>> destList = (List<Map<String, Object>>) in
					.readObject();
			return destList;
		} catch (ClassNotFoundException e) {
			logger.error("未找到指定类", e);
		} catch (IOException e) {
			logger.error("list深拷贝IO流异常", e);
		}
		return srcList;
	}
	
	private List<Map<String, Object>> getPaidinAmountByOutDate(
			List<Map<String, Object>> list, Map<String, Object> params) {
		Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(params);
		for (Map<String, Object> p : list) {
			String shopNo = (String) p.get("shop_no");
			Date outDate = (Date) p.get("out_date");
			BigDecimal bg = map.get(shopNo+outDate);
			BigDecimal p01 = (BigDecimal) p.get("P01");
			BigDecimal amount = (BigDecimal) p.get("amount");
			BigDecimal diffAmount = (BigDecimal) p.get("diffAmount");
			BigDecimal sum = (BigDecimal) p.get("sum");
			
			if(bg != null){
				p.put("S01", bg);
				p.put("D01", bg.subtract(p01));
				p.put("amount", amount.add(bg));
				p.put("diffAmount", diffAmount.add(bg));
				p.put("sum", sum.add(bg));
			}else{
				p.put("S01", BigDecimal.valueOf(0d));
				p.put("D01", p01.negate());
			}
			
		}
		
		return list;
	}
	
	private Map<String, Integer> getShowColumns(Map<String,Object> map) throws ManagerException {
		Integer[] cols = {0,0,0,0};//销售、实收、差异、总计
		for(String key:map.keySet()){
			BigDecimal val = (BigDecimal) map.get(key);
			if(val.compareTo(BigDecimal.ZERO)!=0){
				if(key.matches("P\\d+") || key.equals("totalAmount")){
					cols[0] = cols[0] + 1;
				}else if(key.matches("S\\d+") || key.equals("amount")){
					cols[1] = cols[1] + 1;
				}else if(key.matches("D\\d+") || key.equals("returnAmount") || key.equals("actualReturnAmount") || key.equals("diffAmount")){
					cols[2] = cols[2] + 1;
				}else if(key.equals("poundage") || key.equals("sum")){
					cols[3] = cols[3] + 1;
				}
			}
			
		}

		Map<String, Integer> maps = new HashMap<String, Integer>();
		maps.put("sales", cols[0]);
		maps.put("paidins", cols[1]);
		maps.put("diffs", cols[2]);
		maps.put("sum", cols[3]);

		return maps;
	}
	
	
}
