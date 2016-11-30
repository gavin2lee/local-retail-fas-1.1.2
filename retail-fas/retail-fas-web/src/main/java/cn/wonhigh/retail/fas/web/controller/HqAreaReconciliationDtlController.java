/**  
*   
* 项目名称：retail-fas-web  
* 类名称：HqAreaReconciliationDtlController  
* 类描述：总部地区明细对账表
* 创建人：ouyang.zm  
* 创建时间：2015-3-9 上午10:34:29  
* @version       
*/ 
package cn.wonhigh.retail.fas.web.controller;

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

import cn.wonhigh.retail.fas.common.dto.HqAreaReconciliationDto;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.HqAreaReconciliationDtlManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/hq_area_reconciliation_dtl")
public class HqAreaReconciliationDtlController extends BaseCrudController<HqAreaReconciliationDto> {
	@Resource
	private HqAreaReconciliationDtlManager hqAreaReconciliationDtlManager;
	@Resource
    private FinancialAccountManager financialAccountManager;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("hq_area_reconciliation_dtl/", hqAreaReconciliationDtlManager);
	}

	@RequestMapping(value = "/reconciliation_list")
	public String toHqAreaReconciliation() {
		return "hq_area_report_form/reconciliation_list";
	}
	
	@RequestMapping(value = "/shipment_dtl")
	public String toShipmentDtl() {
		return "hq_area_report_form/shipmentDtl_list";
	}
	
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
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.SALER_NO  IN (" + companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.hqAreaReconciliationDtlManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<HqAreaReconciliationDto> list = this.hqAreaReconciliationDtlManager.findByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		List<HqAreaReconciliationDto> totalList=hqAreaReconciliationDtlManager.findTotalRow(params);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
//		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
//		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
//		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.SALER_NO  IN ("+companyNos +")");
		}
		int total = this.hqAreaReconciliationDtlManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<HqAreaReconciliationDto> dataList = this.hqAreaReconciliationDtlManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	@RequestMapping(value = "/shipment_list.json")
	@ResponseBody
	public Map<String, Object> queryShipmentList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.SALER_NO  IN (" + companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.hqAreaReconciliationDtlManager.findShipmentCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<HqAreaReconciliationDto> list = this.hqAreaReconciliationDtlManager.findShipmentByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		List<HqAreaReconciliationDto> totalList=hqAreaReconciliationDtlManager.findShipmentTotalRow(params);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	
	@RequestMapping(value = "/export_list")
	public void do_export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
//		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
//		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
//		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.SALER_NO  IN ("+companyNos +")");
		}
		int total = this.hqAreaReconciliationDtlManager.findShipmentCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<HqAreaReconciliationDto> dataList = this.hqAreaReconciliationDtlManager.findShipmentByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
