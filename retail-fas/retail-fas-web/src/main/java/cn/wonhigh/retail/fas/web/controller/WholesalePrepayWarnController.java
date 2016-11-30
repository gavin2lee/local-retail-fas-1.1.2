package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerOrderRemainDto;
import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.CustomerOrderRemainManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.WholesalePrepayWarnManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 客户保证金预收款预警
 * @author admin
 * @date  2014-09-23 11:02:19
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
@RequestMapping("/wholesale_prepay_warn")
@ModuleVerify("30160007")
public class WholesalePrepayWarnController extends BaseController<WholesalePrepayWarn> {
    
	@Resource
    private WholesalePrepayWarnManager wholesalePrepayWarnManager;
	
    @Resource
    private BillSaleBalanceManager billSaleBalanceManager;
    
    @Resource
    private CustomerOrderRemainManager customerOrderRemainManager;
    
    @Resource
    private FinancialAccountManager financialAccountManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_prepay_warn/",wholesalePrepayWarnManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("wholesale_prepay_warn/list");
		return mav;
	}
    
    @RequestMapping(value = "/list.json")
	@ResponseBody
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<Object> list = new ArrayList<Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND COMPANY_NO IN (" + companyNos + ")");
		}
		total = wholesalePrepayWarnManager.findCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = wholesalePrepayWarnManager.findByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
    
    @RequestMapping("/select_margin_amount")
    @ResponseBody
    public Map<String, BigDecimal> selectMarginAmount(HttpServletRequest request)
			throws ManagerException {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("companyNo", request.getParameter("companyNo"));
    	params.put("customerNo", request.getParameter("customerNo"));
    	String preOrderNo = request.getParameter("preOrderNo");
    	if(StringUtils.isNotEmpty(preOrderNo)) {
    		params.put("preOrderNo", preOrderNo);
    	}
    	Map<String, BigDecimal> result = wholesalePrepayWarnManager.selectMarginAmount(params);
    	return result;
	}
    
    @RequestMapping(value = "/select_sale_order")
	@ResponseBody
    public Map<String, Object> selectSaleOrder(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND company_no IN (" + companyNos + ")");
		}
		if(params.get("multiBuyerNo")!=null && !"".equals(params.get("multiBuyerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiBuyerNo")).split(","));
		}
		int total = customerOrderRemainManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<WholesaleCustomerOrderRemainDto> list = customerOrderRemainManager.queryWholesaleCustomerOrderRemain(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
    @RequestMapping(value = "/export_warn")
	public void exportWarn(HttpServletRequest req,Model model,HttpServletResponse response)throws Exception{
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(1, Integer.MAX_VALUE, Integer.MAX_VALUE);
		String companyNos = null;
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND company_no IN (" + companyNos + ")");
		}
		if(params.get("multiBuyerNo")!=null && !"".equals(params.get("multiBuyerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiBuyerNo")).split(","));
		}
		List lstItem = customerOrderRemainManager.queryWholesaleCustomerOrderRemain(page, "", "", params);
		List<Map> dataMapList = ExportUtils.getDataList(lstItem);
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}
    
}