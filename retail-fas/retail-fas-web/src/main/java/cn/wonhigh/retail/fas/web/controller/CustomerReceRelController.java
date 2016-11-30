package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.common.model.CustomerReceRelDtl;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BillPrePaymentNtManager;
import cn.wonhigh.retail.fas.manager.CustomerReceRelManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-04 13:40:23
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
@RequestMapping("/customer_rece_rel")
@ModuleVerify("301600020") 
public class CustomerReceRelController extends BaseController<CustomerReceRel> {
	
	private Logger logger = Logger.getLogger(CustomerReceRelController.class);
	
    @Resource
    private CustomerReceRelManager customerReceRelManager;

    @Resource
    private BillPrePaymentNtManager billPrePaymentNtManager;
    
    @Resource
    private FinancialAccountManager financialAccountManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("customer_rece_rel/",customerReceRelManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("customer_rece_rel/list");
		return mav;
	}
    
    @RequestMapping("/get_by_customer_no")
    @ResponseBody
    public CustomerReceRel getByCustomerNo(HttpServletRequest request) throws ManagerException {
    	String customerNo = request.getParameter("customerNo");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("customerNo", customerNo);
    	List<CustomerReceRel> list = customerReceRelManager.findByBiz(null, params);
    	if(list != null && list.size() > 0) {
    		return list.get(0);
    	}
    	return null;
    }
    
    @RequestMapping("/select_customer")
    public ModelAndView toSelectCustomer(HttpServletRequest request) {
    	String marginControlFlag = request.getParameter("marginControlFlag");
    	if(StringUtils.isEmpty(marginControlFlag)) {
    		marginControlFlag = YesNoEnum.YES.getValue() + "";
    	}
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("marginControlFlag", marginControlFlag);
    	mav.setViewName("customer_rece_rel/select_customer");
    	return mav;
    }
    
    @RequestMapping(value = "/list_customer")
	@ResponseBody
	public Map<String, Object> listCustomer(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.customerReceRelManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<CustomerReceRel> list = this.customerReceRelManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	} 
    
    @RequestMapping("/validate_data")
    @ResponseBody
    public Map<String, Object> validateData(HttpServletRequest request) throws ManagerException {
    	String id = request.getParameter("id");
    	String companyNo = request.getParameter("companyNo");
    	String customerNo = request.getParameter("customerNo");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", companyNo);
		params.put("customerNo", customerNo);
		List<CustomerReceRel> list = customerReceRelManager.findByBiz(null, params);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0
				&& !list.get(0).getId().equals(id)) {
			result.put("success", false);
			result.put("message", "同一个公司和客户只能有一条数据");
			return result;
		}
		result.put("success", true);
		return result;
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
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		total = customerReceRelManager.findCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = customerReceRelManager.findByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
    
	/**
	 * 新增
	 * 
	 * @param model 待新增的实体对象
	 * @param request HttpServletRequest
	 * @return Boolean
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> insert(CustomerReceRel model,
			HttpServletRequest request) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			model.setCreateTime(new Date());
			model.setUpdateTime(new Date());
			model.setCreateUser(CurrentUser.getCurrentUser().getUsername());
			JsonUtil<CustomerReceRelDtl> util = new JsonUtil<CustomerReceRelDtl>();
			Map<CommonOperatorEnum, List<CustomerReceRelDtl>> params = util.convertToMap(request, 
					CustomerReceRelDtl.class);
			customerReceRelManager.add(model, params);
			map.put("success", true);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping(value = "/insertByTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertByTemplate(CustomerReceRel model,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String message = "";
			Map<String, Object> params = new HashMap<String, Object>();
			List<CustomerReceRel> list = new ArrayList<CustomerReceRel>();
			//初始化数据
			this.setDefaulValues(model, request);
			String[] customerInfos = (request.getParameter("customerInfoStr") + "").split("\\|");;
			if(customerInfos != null){
				for (int i = 0; i < customerInfos.length; i++) {
					CustomerReceRel receRel = (CustomerReceRel)model.clone();
					String[] customerInfo = customerInfos[i].split(",");
					receRel.setStatus(1);
					receRel.setCustomerNo(customerInfo[0]);
					receRel.setCustomerName(customerInfo[1]);
					receRel.setUpdateTime(new Date());
					receRel.setUpdateUser(CurrentUser.getCurrentUser().getUsername());
					list.add(receRel);
				}
			}
			//添加
			for(CustomerReceRel rel : list){
				rel.setId(UUIDHexGenerator.generate());
				params.put("companyNo", rel.getCompanyNo());
				params.put("customerNo", rel.getCustomerNo());
				int num = customerReceRelManager.findCount(params);
				if(num < 1){
					customerReceRelManager.add(rel);
				}else {
					message += rel.getCustomerName() + "、";
				}
			}
			if(message != "") {
				message = message.substring(0,message.length()-1);
				map.put("message", "客户：" + message + "</br>已存在本公司的收款条款。");
			}
			map.put("success", true);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 修改
	 * 
	 * @param model 待修改的实体对象
	 * @param request HttpServletRequest
	 * @return Boolean
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> update(CustomerReceRel model, 
			HttpServletRequest request) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			model.setUpdateTime(new Date());
			model.setUpdateUser(CurrentUser.getCurrentUser().getUsername());
			JsonUtil<CustomerReceRelDtl> util = new JsonUtil<CustomerReceRelDtl>();
			Map<CommonOperatorEnum, List<CustomerReceRelDtl>> params = util.convertToMap(request, 
					CustomerReceRelDtl.class);
			customerReceRelManager.update(model, params);
			map.put("success", true);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			map.put("success", false);
		}
		return map;
	}

    @RequestMapping(value = "/check_update")
	@ResponseBody
	public Integer checkUpdate(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("termNo", req.getParameter("termNo"));
		params.put("companyNo", req.getParameter("companyNo"));
		params.put("customerNo", req.getParameter("customerNo"));
		return billPrePaymentNtManager.findCount(params);
	} 
    
    @Override
	protected List<CustomerReceRel> queryExportData(Map<String, Object> params) throws ManagerException {
		int total = customerReceRelManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		String isHq = StringUtils.isEmpty(params.get("isHq")+"") ? "" : String.valueOf(params.get("isHq"));
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND COMPANY_NO IN (" + companyNos + ")");
		}
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		List<CustomerReceRel> list = customerReceRelManager.findByPage(page, orderByField, orderBy, params);
		return list;
	}
	
}