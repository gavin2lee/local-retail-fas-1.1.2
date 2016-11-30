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

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.manager.BillBalanceHQManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@RequestMapping("/pe_balance")
public class PeBalanceController extends BaseCrudController<BillBalance> {
	@Resource
	private BillBalanceHQManager billBalanceHQManager;

	@Resource
	private OtherDeductionController otherDeductionController;
	
	@Resource
	private BillAskPaymentController billAskPaymentController;
	
	@Resource
	private BillInvoiceController billInvoiceController;
	
	@Resource
	private BillPurchaseAdjustController billPurchaseAdjustController;
	
	@Resource
	private PayRelationshipController payRelationshipController;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("pe_balance/", billBalanceHQManager);
	}

	/**
	 * 跳转结算单明细
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list() {
		return "pe_balance/pe_balance";
	}
	
	/**
	 * 跳转结算单列表 
	 * @return
	 */
	@RequestMapping(value = "/pe_balance_tabMain")
	public String tabMainTab() {
		return "pe_balance/pe_balance_tabMain";
	}

	/**
	 * 跳转结算单入库明细TAB列表 
	 * @return
	 */
	@RequestMapping(value = "/pe_tab_enter")
	public String enterTab() {
		return "pe_balance/pe_tab_enter";
	}

	/**
	 * 跳转结算单入库明细TAB列表 
	 * @return
	 */
	@RequestMapping(value = "/pe_tab_return")
	public String returnTab() {
		return "pe_balance/pe_tab_return";
	}
	
	/**
	 * 跳转结算单扣项明细TAB列表 
	 * @return
	 */
	@RequestMapping(value = "/pe_tab_deduction")
	public String deductionTab() {
		return "pe_balance/pe_tab_deduction";
	}

	/**
	 * 跳转结算单发票明细TAB列表 
	 * @return
	 */
	@RequestMapping(value = "/pe_tab_invoice")
	public String invoiceTab() {
		return "pe_balance/pe_tab_invoice";
	}

	/**
	 * 跳转结算单请款明细TAB列表 
	 * @return
	 */
	@RequestMapping(value = "/pe_tab_ask_payment")
	public String askPaymentTab() {
		return "pe_balance/pe_tab_ask_payment";
	}
	
	/**
	 * 到货单Tab列表
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/pe_tab_enter.json")
	@ResponseBody
	public Map<String, Object> queryPeEnterList(HttpServletRequest req, Model model) throws Exception {
		return payRelationshipController.queryList(req, model);
	}
	
	/**
	 * 采购调整单Tab列表
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/pe_tab_return.json")
	@ResponseBody
	public Map<String, Object> queryPeReturnList(HttpServletRequest req, Model model) throws Exception {
		return billPurchaseAdjustController.queryList(req, model);
	}
	
	/**
	 * 其他扣项Tab列表
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/pe_tab_deduction.json")
	@ResponseBody
	public Map<String, Object> queryPeDeductionList(HttpServletRequest req, Model model) throws Exception {
		return otherDeductionController.queryList(req, model);
	}

	/**
	 * 请款明细Tab列表 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/pe_tab_ask_payment.json")
	@ResponseBody
	public Map<String, Object> queryAskPaymentList(HttpServletRequest req, Model model) throws ManagerException {	
		return billAskPaymentController.queryListByBalanceNo(req, model);
	}
	
	/**
	 * 发票明细Tab列表
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/pe_tab_invoice.json")
	@ResponseBody
	public Map<String, Object> queryBuyInvoiceList(HttpServletRequest req, Model model) throws ManagerException {	
		return billInvoiceController.queryListByBalanceNo(req, model);
	}
	
	/**
	 * 结算单列表查询
	 */
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billBalanceHQManager.selectBalanceCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBalance> list = new ArrayList<BillBalance>();
		List<BillBalance> footerList = new ArrayList<BillBalance>();
		if(total > 0 ){
			list = billBalanceHQManager.selectBalanceByPage(page, sortColumn, sortOrder, params);
			footerList = billBalanceHQManager.selectBalanceFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}
	
	/**
	 * 删除体育结算单
	 */
	@RequestMapping(value = "/delete_pe_balance")
	@ResponseBody
	public Map<String, Object> deletePeBalance(HttpServletRequest req, Model model) throws ManagerException {
		BillBalance balance = new BillBalance();
		balance.setId(req.getParameter("id"));
		balance.setBalanceType(BalanceTypeEnums.PE_SUPPLIER.getTypeNo());
		int count = billBalanceHQManager.deleteById(balance);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("count", count);
		obj.put("success", true);
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dateList = billBalanceHQManager.selectBalanceByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dateList, response);
	}
	
}