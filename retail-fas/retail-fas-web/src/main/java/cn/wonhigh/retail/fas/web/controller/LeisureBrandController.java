/**
 * title:LeisureBrandController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:休闲品牌对账报表
 * auther:user
 * date:2016-3-11 下午4:32:55
 */
package cn.wonhigh.retail.fas.web.controller;

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
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.LeisureBrandDto;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.LeisureBrandManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@ModuleVerify("30510415")
@RequestMapping("/leisure_brand_check")
public class LeisureBrandController extends BaseController<LeisureBrandDto> {
	@Resource
    private LeisureBrandManager leisureBrandManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Override
	protected CrudInfo init() {
		 return new CrudInfo("leisure_brand_check/",leisureBrandManager);
	}

	@RequestMapping("/check_report")
	public ModelAndView tocheckReport(HttpServletRequest req){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("/report/leisure_brand_report");
		return mav;
	}
	
	/**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(HttpServletRequest req,Map<String, Object> map){
		String brandNos = "";
		if (map.get("brandNoLike") != null) {
			brandNos = FasUtil.formatInQueryCondition(map.get("brandNoLike").toString());
		}
		map.put("brandNoLike", brandNos);
		return map;
	}
	
	/**
	 * 查询动态列
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
    @ResponseBody
    @RequestMapping("/organ_columns.json")
	public Map<String,Object> payWaySumColumns(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> obj = new HashMap<String, Object>();
    	Map<String, Object> params = builderParams(req, model);
    	setParams(req,params);
    	//过滤承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("hqCompanyData",companyNos);
		}
    	//查询动态列
    	Map<String, Object> headers = new LinkedHashMap<String, Object>();
    	List<Map<String, Object>> columns = leisureBrandManager.findDynColumns(params);
    	for(Map<String, Object> column : columns) {
    		headers.put(column.get("ORDER_UNIT_NAME").toString(), column.get("ORDER_UNIT_NO"));
    	}
    	
    	obj.put("headers", headers);
    	return obj;
    }
	
    /**
     * 查询
     */
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		//过滤承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("hqCompanyData",companyNos);
		}
		//初始赋值
		int total = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();
		Map<String, Object> obj = new HashMap<String, Object>();
		
		total=this.leisureBrandManager.findCount(params);
		if(total>0){
			//查询动态列
			List<Map<String, Object>> dynColumns=this.leisureBrandManager.findDynColumns(params);
			if(dynColumns.size()>0){
				StringBuilder columnStr = new StringBuilder();
	        	for(Map<String, Object> column : dynColumns) {
	        		columnStr.append("SUM(CASE WHEN T.ORDER_UNIT_NO='"+column.get("ORDER_UNIT_NO")+"'AND T.BILL_TYPE='1371' THEN T.SEND_QTY ELSE NULL END) AS '"+column.get("ORDER_UNIT_NO")+"',");
	        	}
	        	columnStr.deleteCharAt(columnStr.length() - 1);
	        	params.put("columns", columnStr);
	        	
	        	StringBuilder sumStr = new StringBuilder();
	        	for(Map<String, Object> column : dynColumns) {
	        		sumStr.append("SUM("+column.get("ORDER_UNIT_NO")+") AS "+column.get("ORDER_UNIT_NO")+",");
	        	}
	        	sumStr.deleteCharAt(sumStr.length() - 1);
	        	params.put("sumColumns", sumStr);
        	}
        	//分页查询
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.leisureBrandManager.findSumByPage(page, sortColumn, sortOrder, params);
			//查询合计行
			totalList=this.leisureBrandManager.findTotalRow(params);
		}
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/list_export")
	public void listExport(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		//过滤承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("hqCompanyData",companyNos);
		}
		//查询动态列
		List<Map<String, Object>> dynColumns=this.leisureBrandManager.findDynColumns(params);
		if(dynColumns.size()>0){
			StringBuilder columnStr = new StringBuilder();
        	for(Map<String, Object> column : dynColumns) {
        		columnStr.append("SUM(CASE WHEN T.ORDER_UNIT_NO='"+column.get("ORDER_UNIT_NO")+"'AND T.BILL_TYPE='1371' THEN T.SEND_QTY ELSE NULL END) AS '"+column.get("ORDER_UNIT_NO")+"',");
        	}
        	columnStr.deleteCharAt(columnStr.length() - 1);
        	params.put("columns", columnStr);
        	
        	StringBuilder sumStr = new StringBuilder();
        	for(Map<String, Object> column : dynColumns) {
        		sumStr.append("SUM("+column.get("ORDER_UNIT_NO")+") AS "+column.get("ORDER_UNIT_NO")+",");
        	}
        	sumStr.deleteCharAt(sumStr.length() - 1);
        	params.put("sumColumns", sumStr);
    	}
		List<Map<String, Object>> dataList = this.leisureBrandManager.findSumByPage(null, sortColumn, sortOrder, params);
		//查询合计行
		List<Map<String, Object>> totalList=this.leisureBrandManager.findTotalRow(params);
		if(totalList.size()>0){
			dataList.addAll(totalList);
		}
		
		this.exportData(params, response, dataList);
	}
	
}
