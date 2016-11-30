package cn.wonhigh.retail.fas.web.controller;


import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 结算公司
 * 
 * @author ouyang.zm
 * @date 2014-08-25 13:52:36
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/base_setting/company")
@ModuleVerify("30100008")
public class CompanyController extends BaseCrudController<Company> {
	@Resource
	private CompanyManager companyManager;
	@Resource
	private FinancialAccountManager financialAccountManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("base_setting/company/", companyManager);
	}

	/**
	 * 转到查询结算公司
	 * @return
	 */
	@RequestMapping("/toSearchCompany")
	public ModelAndView toSearchCompany(HttpServletRequest req) {
		String params = StringUtils.isEmpty(req.getParameter("params")) ? "" : String.valueOf(req.getParameter("params"));
		ModelAndView view=new ModelAndView();
		view.setViewName("base_setting/company/searchCompany");
		view.addObject("params", params);
		return view;
	}
	
	/**
	 * 转到查询结算公司(多选)
	 * @return
	 */
	@RequestMapping("/toMultiSearch")
	public ModelAndView toMultiSearch(HttpServletRequest req) {
		String params = StringUtils.isEmpty(req.getParameter("params")) ? "" : String.valueOf(req.getParameter("params"));
		ModelAndView view=new ModelAndView();
		view.setViewName("base_setting/company/multiSearch");
		view.addObject("params", params);
		return view;
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String p = StringUtils.isEmpty(req.getParameter("params")) ? "" : String.valueOf(req.getParameter("params"));
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		//过滤承担总部职能的结算公司
		if(("groupLeadRole").equals(p)){
			if(StringUtils.isNotEmpty(companyNos)){
				params.put("queryCondition", "AND company_no NOT IN ("+companyNos +")");
			}
		}
		if(("notGroupLeadRole").equals(p)){
				params.put("queryCondition", "AND company_no IN ("+companyNos +")");
		}
		int total = companyManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Company> list = companyManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 根据店铺编码获取结算公司
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getCompanyByShopNo")
	@ResponseBody
	public Company getCompanyByShopNo(HttpServletRequest req)throws ManagerException{
		List<Company> list = new ArrayList<Company>();
		
		Company dto = new Company();
		String shopNo = req.getParameter("shopNo");
		
		if(StringUtils.isEmpty(shopNo)){
			return dto;
		}
		list  = companyManager.getCompanyByShopNo(shopNo);
		if(CollectionUtils.isEmpty(list)){
			return dto;
		}else{
			dto = list.get(0);
		}
		return dto;
	}
	
}