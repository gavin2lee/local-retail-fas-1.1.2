package cn.wonhigh.retail.fas.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.CategoryManager;
import cn.wonhigh.retail.fas.manager.InvoiceBaseReportManager;

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
@RequestMapping("/invoice_base_report")
@ModuleVerify("30510412")
public class InvoiceBaseReportController extends BaseController<FasBaseModel> {
	
    @Resource
    private InvoiceBaseReportManager invoiceBaseReportManager;
    
    @Resource
    private CategoryManager categoryManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("invoice_base_report/",invoiceBaseReportManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "report/invoice_base_report";
   	}
    
    /**
     * 查询数据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @ResponseBody
    @RequestMapping("/list.json")
	public Map<String,Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	
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
    	total = invoiceBaseReportManager.selectCount(params);
    	if(total > 0) {
    		
    		// 查询一级大类，用于循环拼SQL
    		Map<String, Object> categoryCondition = new HashMap<String, Object>();
    		categoryCondition.put("levelid", 1);
    		List<Category> categoryList = categoryManager.findByBiz(null, categoryCondition);
    		params.put("categoryList", categoryList);
        	
        	// 查询合计
        	Map<String, Object> totalMap = invoiceBaseReportManager.selectTotal(params);
        	totalMap.put("short_name", "合计：");
        	totalList.add(totalMap);
        	
        	// 分页查询
        	SimplePage page = new SimplePage(pageNo, pageSize, total);
        	rows = invoiceBaseReportManager.selectData(page, sortColumn, sortOrder, params);
    	}
    	
    	obj.put("total", total);
    	obj.put("rows", rows);
    	obj.put("footer", totalList);
    	
		return obj;
	}
    
    /**
	 * 导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/export")
	public void doFasExport(HttpServletRequest req, Model model,
			HttpServletResponse response) throws ManagerException {
		
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    	
		Map<String, Object> params = builderParams(req, model);
		//格式化参数
		params = this.formatParams(params);
		
		// 查询一级大类，用于循环拼SQL
		Map<String, Object> categoryCondition = new HashMap<String, Object>();
		categoryCondition.put("levelid", 1);
		List<Category> categoryList = categoryManager.findByBiz(null, categoryCondition);
		params.put("categoryList", categoryList);
		
    	//查询数据
		List<Map<String, Object>> dataList = invoiceBaseReportManager.selectData(null, sortColumn, sortOrder, params);
		
		// 查询合计
    	Map<String, Object> totalMap = invoiceBaseReportManager.selectTotal(params);
    	totalMap.put("short_name", "合计：");
    	dataList.add(totalMap);
		
		this.exportData(params, response, dataList);
		
	}
    
    /**
	 * 格式化参数
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
		
		if(params.get("organNos") != null && !"".equals(params.get("organNos").toString())) {
			params.put("organNos", FasUtil.formatInQueryCondition(params.get("organNos").toString()));
		}
		return params;
	}
	
}