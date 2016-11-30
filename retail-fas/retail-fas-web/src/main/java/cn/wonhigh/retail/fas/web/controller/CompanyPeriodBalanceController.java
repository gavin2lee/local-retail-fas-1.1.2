package cn.wonhigh.retail.fas.web.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.CompanyPeriodBalanceManager;
import cn.wonhigh.retail.fas.web.utils.OwerGuestGenerateThread;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController.CrudInfo;

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
@RequestMapping("/company_period_balance")
@ModuleVerify("30120006")
public class CompanyPeriodBalanceController extends BaseController<CompanyPeriodBalance> {

	private static final XLogger logger = XLoggerFactory.getXLogger(CompanyPeriodBalanceController.class);
	
    @Resource
    private CompanyPeriodBalanceManager companyPeriodBalanceManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("company_period_balance/",companyPeriodBalanceManager);
    }
    
    @RequestMapping(value = "/generate_ower_guest.json")
	@ResponseBody
	public  Map<String, Object> generateCompanyOwerGuest(HttpServletRequest req, Model model){
    	Map<String, Object> obj = new HashMap<String, Object>();
    	obj.put("success", true);
    	
    	Map<String, Object> params = builderParams(req, model);
    	SystemUser systemUser = CurrentUser.getCurrentUser();
		new Thread(new OwerGuestGenerateThread(companyPeriodBalanceManager, params, systemUser)).start();
		
    	return obj;
    }
  
    @RequestMapping(value = "/company_period_balance.json")
	@ResponseBody
	public  Map<String, Object> queryCompanyPeriodBalances(HttpServletRequest req, Model model) throws ManagerException {
    	int total = 0;
		SimplePage page = null;
		List<CompanyPeriodBalance> list = null;
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		String year = params.get("year").toString();
		String month = params.get("month").toString();
		Date currDate = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		
		params.put("preYear", lastYear);
		params.put("preMonth", lastMonth);
		params.put("startDate", DateUtil.getFirstDayOfMonth(currDate));
		params.put("endDate", currDate);
		
		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("brandNos", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		String categoryNos = req.getParameter("categoryNos");
		if (StringUtils.isNotEmpty(categoryNos)) {
			params.put("multiCategorys", Arrays.asList(categoryNos.substring(1).split(",")));
		}
		
		params.put("isPE", String.valueOf(ShardingUtil.isPE()));
		
		//按照小计方式查询
		if("true".equals(req.getParameter("isSubTotal")) || "true".equals(req.getParameter("onlySubTotal"))){
			total = companyPeriodBalanceManager.findCompanyPeriodBalanceSubTotalCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = companyPeriodBalanceManager.findCompanyPeriodBalanceSubTotalPages(page, sortColumn, sortOrder, params);
		}else {
			total = companyPeriodBalanceManager.findCount(params, null);
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = companyPeriodBalanceManager.findCompanyPeriodBalancePages(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", companyPeriodBalanceManager.setExtendsPeriodBalanceProperties(list));
		List<CompanyPeriodBalance> pList = companyPeriodBalanceManager.selectTotalRow(page, sortColumn, sortOrder, params);
		obj.put("footer", pList);
		return obj;
	}
	
    /**
	 * 查询导出数据的方法
	 * @param params 查询参数
	 * @return List<ModelType>
	 * @throws ManagerException 异常
	 */
    @Override
	protected List<CompanyPeriodBalance> queryExportData(Map<String, Object> params) throws ManagerException {
    	int total = 0;
    	SimplePage page = null;
    	List<CompanyPeriodBalance> list = null;
    	String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String isSubTotal = params.get("isSubTotal") == null ? "" : (String)params.get("isSubTotal");
		String onlySubTotal = params.get("onlySubTotal") == null ? "" : (String)params.get("onlySubTotal");
    	
		String year = params.get("year").toString();
		String month = params.get("month").toString();
		Date currDate = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		
		params.put("preYear", lastYear);
		params.put("preMonth", lastMonth);
		params.put("startDate", DateUtil.getFirstDayOfMonth(currDate));
		params.put("endDate", currDate);
		
		if (null != params.get("brandNos") && StringUtils.isNotEmpty((String)params.get("brandNos"))) {
			params.put("brandNos", Arrays.asList(((String)params.get("brandNos")).split(",")));
		}
		if (null != params.get("categoryNos") && StringUtils.isNotEmpty((String)params.get("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(((String)params.get("categoryNos")).split(",")));
		}
		
		params.put("isPE", String.valueOf(ShardingUtil.isPE()));
		
		//小计方式导出
		if (isSubTotal.equals("true") || "true".equals(onlySubTotal)) {
			total = companyPeriodBalanceManager.findCompanyPeriodBalanceSubTotalCount(params);
			page = new SimplePage(1, total, (int) total);
			list = companyPeriodBalanceManager.findCompanyPeriodBalanceSubTotalPages(page, orderByField, orderBy, params);
		}else {
			total = companyPeriodBalanceManager.findCount(params, null);
			page = new SimplePage(1, total, (int) total);
			list = companyPeriodBalanceManager.findCompanyPeriodBalancePages(page, orderByField, orderBy, params);
		}
		
		list = companyPeriodBalanceManager.setExtendsPeriodBalanceProperties(list);
		List<CompanyPeriodBalance> pList = companyPeriodBalanceManager.selectTotalRow(page, orderByField, orderBy, params);
		list.add(pList.get(0));
		return list;
	}
}