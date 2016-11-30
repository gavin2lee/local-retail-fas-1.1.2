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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceApplyManager;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceInfoManager;
import cn.wonhigh.retail.fas.manager.BillCommonRegisterDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
@RequestMapping("/bill_balance_invoice_info")
@Deprecated
public class BillBalanceInvoiceInfoController extends BaseCrudController<BillBalanceInvoiceInfo> {
	
    @Resource
    private BillBalanceInvoiceInfoManager billBalanceInvoiceInfoManager;
    
    @Resource
    private BillBalanceInvoiceApplyManager billBalanceInvoiceApplyManager;
    
    @Resource
    private BillCommonRegisterDtlManager billCommonRegisterDtlManager;
    

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_balance_invoice_info/",billBalanceInvoiceInfoManager);
    }
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		String billNo = req.getParameter("billNo");
		String balanceNo = req.getParameter("balanceNo");
		if(StringUtils.isEmpty(billNo) && StringUtils.isEmpty(balanceNo)) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("total", 0);
			obj.put("rows", new ArrayList<BillBalanceInvoiceInfo>(1));
			return obj;
		}
		return super.queryList(req, model);
	}
	
    /**
     * 根据结算公司和客户编码获取发票信息
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/getBillBalanceInvoiceInfo")
	@ResponseBody
	public  Map<String, Object> getBillBalanceInvoiceInfo(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billBalanceInvoiceInfoManager.getCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBalanceInvoiceInfo> list = this.billBalanceInvoiceInfoManager.findByCustomerNo(page, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
    
    @RequestMapping(value = "/getBillShopBanancleInfo")
	@ResponseBody
	public  Map<String, Object> getBillShopBanancleInfo(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billBalanceInvoiceApplyManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBalanceInvoiceApply> list = this.billBalanceInvoiceApplyManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(list == null || list.size() == 0) {
			obj.put("total", 0);
			obj.put("rows", new ArrayList<BillCommonRegisterDtl>());
			return obj;
		}
		BillCommonRegisterDtl billCommonRegisterDtl = new BillCommonRegisterDtl();
		billCommonRegisterDtl.setBillNo(list.get(0).getInvoiceRegisterNo());
		if(StringUtils.isEmpty(billCommonRegisterDtl.getBillNo())){
			obj.put("total", 0);
			obj.put("rows", new ArrayList<BillCommonRegisterDtl>());
			return obj;
		}
		params.put("billNo", billCommonRegisterDtl.getBillNo());
		int dtltotal = this.billCommonRegisterDtlManager.findCount(params);
		List<BillCommonRegisterDtl> billCommonRegisterDtllist = billCommonRegisterDtlManager.findByPage(page, sortColumn, sortOrder, params);
		
	
		obj.put("total", dtltotal);
		obj.put("rows", billCommonRegisterDtllist);
		return obj;
	}
}