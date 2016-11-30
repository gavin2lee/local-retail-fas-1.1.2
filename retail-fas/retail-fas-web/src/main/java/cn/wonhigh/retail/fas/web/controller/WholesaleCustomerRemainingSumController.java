package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingOccurDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSendaDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSumDto;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.WholesaleCustomerRemainingSumManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 批发客户余额
 * @author Administrator
 * @date  2015-07-06 15:41:34
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
@RequestMapping("/wholesale_customer_remaining_sum")
@ModuleVerify("30270119")
public class WholesaleCustomerRemainingSumController extends BaseCrudController<WholesaleCustomerRemainingSum> {
	
	private Logger logger = Logger.getLogger(WholesaleCustomerRemainingSumController.class);
	
    @Resource
    private WholesaleCustomerRemainingSumManager wholesaleCustomerRemainingSumManager;

    @Resource
    private FinancialAccountManager financialAccountManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_customer_remaining/",wholesaleCustomerRemainingSumManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value="/listTab")
    public ModelAndView forwardListTab(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
    	String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
    	mav.setViewName("wholesale_customer_remaining/wholesale_customer_remaining_tab");
		return mav;
    }
    
    @RequestMapping(method = RequestMethod.GET ,value="/selectSumListTab")
    public ModelAndView forwardSelectListTab(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
    	String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
    	mav.setViewName("wholesale_customer_remaining/select_sum_tab");
		return mav;
    }
    
    @RequestMapping(method = RequestMethod.GET ,value="/selectSendaList")
    public ModelAndView forwardSelectSendaList(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
    	String isHq = req.getParameter("isHq");
    	if(StringUtils.isNotBlank(isHq)){
    		mav.addObject("isHq", isHq);
    	}
    	mav.setViewName("wholesale_customer_remaining/select_senda_list");
    	return mav;
    }
    
    @RequestMapping("/to_dtl")
    public String dtlTab(){
    	return "wholesale_customer_remaining/list_dtl";
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("wholesale_customer_remaining/list");
		return mav;
	}

    /**
     * 查询客户余额
     */
	@RequestMapping({"/list.json"})
    @ResponseBody
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
      int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
      int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
      String sortColumn = (StringUtils.isEmpty(req.getParameter("sort"))) ? "" : String.valueOf(req.getParameter("sort"));
      String sortOrder = (StringUtils.isEmpty(req.getParameter("order"))) ? "" : String.valueOf(req.getParameter("order"));
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
      int total = wholesaleCustomerRemainingSumManager.findCount(params);
      SimplePage page = new SimplePage(pageNo, pageSize, total);
	  if (StringUtils.isNotEmpty(companyNos)) {
    	  params.put("queryCondition", "AND w.COMPANY_NO IN (" + companyNos + ")");
      }
	  List<WholesaleCustomerRemainingSumDto> list = wholesaleCustomerRemainingSumManager.findCustomerRemainningByPage(page, sortColumn, sortOrder, params);
      Map<String, Object> obj = new HashMap<String, Object>();
      obj.put("total", Integer.valueOf(total));
      obj.put("rows", list);
      return obj;
    }
	
	/**
	 * 查询客户余额发生
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping({"/select_sum_list.json"})
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest req, Model model) throws ManagerException {
      int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
      int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
      String sortColumn = (StringUtils.isEmpty(req.getParameter("sort"))) ? "" : String.valueOf(req.getParameter("sort"));
      String sortOrder = (StringUtils.isEmpty(req.getParameter("order"))) ? "" : String.valueOf(req.getParameter("order"));
      String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
      Map<String, Object> params = builderParams(req, model);
      //结束时间加上23:59:59
      try {
    	  String endDate = (String)params.get("endDate");
    	  params.put("endDate", DateUtil.getMaxTimeByStringDate(endDate));
	  } catch (Exception e) {
		  logger.error("时间转换失败", e);
	  }
      
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
      int total = wholesaleCustomerRemainingSumManager.findCount(params);
      SimplePage page = new SimplePage(pageNo, pageSize, total);
	  if (StringUtils.isNotEmpty(companyNos)) {
    	  params.put("queryCondition", "AND ws.company_no in (" + companyNos + ")");
      }
	  List<WholesaleCustomerRemainingOccurDto> list = wholesaleCustomerRemainingSumManager.findCustomerRemainningOccurByPage(page, sortColumn, sortOrder, params);
      Map<String, Object> obj = new HashMap<String, Object>();
      obj.put("total", Integer.valueOf(total));
      obj.put("rows", list);
      return obj;
    }
	
	
	/**
	 * 查询森达客户余额明细
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping({"/select_senda_list.json"})
	@ResponseBody
	public Map<String, Object> selectSendaList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = (StringUtils.isEmpty(req.getParameter("sort"))) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = (StringUtils.isEmpty(req.getParameter("order"))) ? "" : String.valueOf(req.getParameter("order"));
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND a.company_no in (" + companyNos + ")");
		}
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		int total = wholesaleCustomerRemainingSumManager.findSendaCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<WholesaleCustomerRemainingSendaDto> list = wholesaleCustomerRemainingSumManager.findSendaListByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", Integer.valueOf(total));
		obj.put("rows", list);
		return obj;
	}
	

    
    @RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model,HttpServletResponse response)throws Exception{
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND w.COMPANY_NO IN (" + companyNos + ")");
		}
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		SimplePage page = new SimplePage(0,Integer.MAX_VALUE, Integer.MAX_VALUE);
		List lstItem = wholesaleCustomerRemainingSumManager.findCustomerRemainningByPage(page,"","", params);
		List<Map> dataMapList = ExportUtils.getDataList(lstItem);
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}
    
    @RequestMapping(value = "/select_sum_export")
	public void selectSumExport(HttpServletRequest req,Model model,HttpServletResponse response)throws Exception{
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND ws.COMPANY_NO IN (" + companyNos + ")");
		}
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		SimplePage page = new SimplePage(0,Integer.MAX_VALUE, Integer.MAX_VALUE);
		List lstItem = wholesaleCustomerRemainingSumManager.findCustomerRemainningOccurByPage(page,"","", params);
		List<Map> dataMapList = ExportUtils.getDataList(lstItem);
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}
    
    
    @RequestMapping(value = "/select_senda_export")
    public void selectSendaExport(HttpServletRequest req,Model model,HttpServletResponse response)throws Exception{
    	List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
    	Map<String, Object> params = builderParams(req, model);
    	String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
    	String companyNos = null;
    	if(isHq.equals("true")){
    		companyNos = financialAccountManager.findLeadRoleCompanyNos();
    	}else{
    		companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
    	}
    	if (StringUtils.isNotEmpty(companyNos)) {
    		params.put("queryCondition", "AND a.company_no in (" + companyNos + ")");
    	}
    	if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
    		params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
    	}
    	SimplePage page = new SimplePage(0,Integer.MAX_VALUE, Integer.MAX_VALUE);
    	List lstItem = wholesaleCustomerRemainingSumManager.findSendaListByPage(page,"","", params);
    	List<Map> dataMapList = ExportUtils.getDataList(lstItem);
    	ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
    }
    
}