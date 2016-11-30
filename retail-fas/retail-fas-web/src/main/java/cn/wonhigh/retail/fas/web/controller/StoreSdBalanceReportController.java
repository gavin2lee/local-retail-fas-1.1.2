package cn.wonhigh.retail.fas.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.manager.PeriodBalanceManager;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-28 09:02:52
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
@RequestMapping("/store_sd_balance_report")
@ModuleVerify("30120017")
public class StoreSdBalanceReportController extends BaseController<PeriodBalance> {
	
    @Resource
    private PeriodBalanceManager periodBalanceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("store_balance_report/",periodBalanceManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/sd_list")
    public ModelAndView listTab(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("store_balance_report/sd_list");
		return mav;
    }
    
    @RequestMapping(value = "/store_sd_balance_report.json")
   	@ResponseBody
   	public  Map<String, Object> queryGMSBalanceSdList(HttpServletRequest req, Model model) throws ManagerException {
    	int total = 0;
    	SimplePage page = null;
		List<PeriodBalance> list = null;
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String startYearMonth = StringUtils.isBlank(req.getParameter("startYearMonth")) ? "" : req.getParameter("startYearMonth");
    	String endYearMonth = StringUtils.isBlank(req.getParameter("endYearMonth")) ? "" : req.getParameter("endYearMonth");
		Map<String, Object> params = builderParams(req, model);
		params = convertYearMoth(startYearMonth, endYearMonth, params);
		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("multiBrands", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(req.getParameter("organNos").split(",")));
		}
		total = periodBalanceManager.findSdBalanceCount(params);
		page = new SimplePage(pageNo, pageSize, (int) total);
		list = periodBalanceManager.findSdBalanceByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);									
		obj.put("rows", list);
		return obj;
   	}
    
    @Override
    protected List<PeriodBalance> queryExportData(Map<String, Object> params) throws ManagerException {
    	int total = 0;
    	SimplePage page = null;
    	List<PeriodBalance> list = null;
    	String isSubTotal = params.get("isSubTotal") == null ? "" : (String)params.get("isSubTotal");
    	String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
    	if (null != params.get("brandNos") && StringUtils.isNotEmpty((String)params.get("brandNos"))) {
			params.put("multiBrands", Arrays.asList(((String)params.get("brandNos")).split(",")));
		}
		if (null != params.get("categoryNos") && StringUtils.isNotEmpty((String)params.get("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(((String)params.get("categoryNos")).split(",")));
		}
		if (null != params.get("organNos") && StringUtils.isNotEmpty((String)params.get("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(((String)params.get("organNos")).split(",")));
		}
		//小计方式导出
		if (isSubTotal.equals("true")) {
			total = periodBalanceManager.findCompanyPeriodBalanceSubTotalCount1(params);
			page = new SimplePage(1, total, (int) total);
			list = periodBalanceManager.findCompanyPeriodBalanceSubTotalPages1(page, orderByField, orderBy, params);
			for (PeriodBalance pBalance : list) {
    			//小计格式化
    			if (null != pBalance.getCategoryNo() && pBalance.getCategoryNo().indexOf("zzzzz") != -1) {
    				pBalance.setCompanyNo("小计：");
    				pBalance.setItemCode("");
    				pBalance.setItemName("");
    				pBalance.setCategoryName("");
    				pBalance.setYearsName("");
    				pBalance.setSeasonName("");
    				pBalance.setOrderfrom("");
    				pBalance.setGender("");
    			}
			}
		}else {
			 total = periodBalanceManager.findCount(params);
			 page = new SimplePage(1, total, (int) total);
			 list = periodBalanceManager.findByPage(page, orderByField, orderBy, params);
    }
		return periodBalanceManager.setExtendsPeriodBalanceProperties(list);
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void queryResultMap(Map vals) {
    	
    	//是否是小计行
    	boolean isCount = false;
    	if (null != vals.get("categoryNo") && vals.get("categoryNo").toString().indexOf("zzzzz") != -1) {
    		isCount = true;
    	}
    	
    	if(isCount) {
    		vals.put("companyName", "小计：");
    		vals.put("itemCode", "");
    		vals.put("itemName", "");
    		vals.put("categoryName", "");
    		vals.put("yearsName", "");
    		vals.put("seasonName", "");
    		vals.put("orderfrom", "");
    		vals.put("gender", "");
    	}
    	
    }
    
    @Override
	protected void selectManagerMenthod(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
    	
    	String startYearMonth = null;
    	String endYearMonth = null;
    	//地区类型多选
		if (null != params.get("brandNos") && StringUtils.isNotEmpty((String)params.get("brandNos"))) {
			params.put("multiBrands", Arrays.asList(((String)params.get("brandNos")).split(",")));
		}
		if (null != params.get("categoryNos") && StringUtils.isNotEmpty((String)params.get("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(((String)params.get("categoryNos")).split(",")));
		}
		if (null != params.get("organNos") && StringUtils.isNotEmpty((String)params.get("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(((String)params.get("organNos")).split(",")));
		}
    	if(null != params.get("startYearMonth") && StringUtils.isNotBlank((String)params.get("startYearMonth"))) {
    		startYearMonth = params.get("startYearMonth").toString();
    	}
    	if(null != params.get("endYearMonth") && StringUtils.isNotBlank((String)params.get("endYearMonth"))) {
    		endYearMonth = params.get("endYearMonth").toString();
    	}
    	params = convertYearMoth(startYearMonth, endYearMonth, params);
    	this.periodBalanceManager.storeSdBalanceExport(page, params, handler);
    }
    
    
    private Map<String, Object> convertYearMoth(String startYearMonth, String endYearMonth, Map<String,Object> params){
    	List<PeriodBalance> list = new ArrayList<PeriodBalance>();
    	if(startYearMonth.equals("") || endYearMonth.equals("") || startYearMonth.length()<6 || endYearMonth.length()<6){
    		return params;
    	}
    	int startYear = Integer.parseInt(startYearMonth.substring(0,4));
    	int startMonth = Integer.parseInt(startYearMonth.substring(4,6));
    	int endYear = Integer.parseInt(endYearMonth.substring(0,4));
    	int endMonth = Integer.parseInt(endYearMonth.substring(4,6));
    	int tempYear = endYear - startYear;
    	PeriodBalance perBalance;
    	if( tempYear == 0){
    		for(int i = startMonth; i<= endMonth; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(startYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    	}else if(tempYear >= 1){
    		for(int i= startMonth; i<=12; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(startYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    		for(int i = 1; i< endYear-startYear; i++){
    			for (int j = 1; j <= 12; j++) {
    				perBalance = new PeriodBalance();
        			perBalance.setYear(startYear+i+"");
        			perBalance.setMonth(j+"");
        			list.add(perBalance);
				}
    		}
    		for(int i = 1; i<= endMonth; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(endYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    	}
    	params.put("multiYearAndMonth", list);
    	return params;
    }
    
}