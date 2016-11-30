/**
 * title:SettledBillsAuditController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:已结算单据审核
 * auther:user
 * date:2016-4-7 上午11:12:10
 */
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.PhysicalBrandManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/settledBills_audit")
@ModuleVerify("40001008")
public class SettledBillsAuditController extends BaseCrudController<SettledBillsDto> {
	@Resource
	private PhysicalBrandManager physicalBrandManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Override
	public CrudInfo init() {
		return new CrudInfo("top_sports/settledBills_audit/", physicalBrandManager);
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
		//查询承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.BUYER_NO IN ("+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.physicalBrandManager.findSettledBillsCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SettledBillsDto> list = this.physicalBrandManager.findSettledBillsByPage(page, sortColumn, sortOrder, params);
		List<SettledBillsDto> totalList = physicalBrandManager.findSettledBillsListTotalRow(params);//查询合计行
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/sum_list.json")
	@ResponseBody
	public Map<String, Object> querySumList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		//查询承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.BUYER_NO IN ("+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.physicalBrandManager.findSettledBillsSumCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SettledBillsDto> list = this.physicalBrandManager.findSettledBillsSumByPage(page, sortColumn, sortOrder, params);
		List<SettledBillsDto> totalList = physicalBrandManager.findSettledBillsSumTotalRow(params);//查询合计行
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 审核
	 * @param req
	 * @param balanceNo
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/bills_audit")
	@ResponseBody
	public Integer settledBillsAudit(HttpServletRequest req, @RequestParam("billNos") String[] balanceNo)
			throws ManagerException {
		String billNoLists = req.getParameter("billNos");
		String[] ids=billNoLists.split(",");
		int i=this.physicalBrandManager.modifyBillsAuditStatus(ids);//根据结算单号审核结算单
		return i;
	}
	
	/**
	 * 反审核
	 * @param req
	 * @param balanceNo
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/bills_unAudit")
	@ResponseBody
	public Integer settledBillsUnAudit(HttpServletRequest req, @RequestParam("billNos") String balanceNo)
			throws ManagerException {
		String billNoLists = req.getParameter("billNos");
		String[] ids=billNoLists.split(",");
		int i=this.physicalBrandManager.modifyBillsUnAuditStatus(ids);
		return i;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		//查询承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND A.BUYER_NO IN ("+ companyNos + ")");
		}
		//判断导出哪个表
		String flag = StringUtils.isEmpty(req.getParameter("flag")) ? "" : String.valueOf(req.getParameter("flag"));
		List<SettledBillsDto> dataList=new ArrayList<SettledBillsDto>();
		if(flag.equals("dtlList")){
			int total = this.physicalBrandManager.findSettledBillsCount(params);
			SimplePage page = new SimplePage(pageNo, total, (int) total);
			dataList = this.physicalBrandManager.findSettledBillsByPage(page, sortColumn, sortOrder, params);
		}else if(flag.equals("sumList")){
			int total = this.physicalBrandManager.findSettledBillsSumCount(params);
			SimplePage page = new SimplePage(pageNo, total, (int) total);
			 dataList = this.physicalBrandManager.findSettledBillsSumByPage(page, sortColumn, sortOrder, params);
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
