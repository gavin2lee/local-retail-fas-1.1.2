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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillInvoiceApplyDtl;
import cn.wonhigh.retail.fas.manager.BillInvoiceApplyDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 地区批发-开票申请单明细
 * @author admin
 * @date  2014-09-16 15:35:20
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
@RequestMapping("/bill_invoice_apply_dtl")
public class BillInvoiceApplyDtlController extends BaseController<BillInvoiceApplyDtl> {
	
    @Resource
    private BillInvoiceApplyDtlManager billInvoiceApplyDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_invoice_apply_dtl/",billInvoiceApplyDtlManager);
    }
    
    /**
     * 通过新旧款编码，查询该新旧款的明细数据
     * 
     * @param styleNo 新旧款编码
     * @param request HttpServletRequest
     * @return 新旧款明细列表
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_invoice_apply_dtl")
	@ResponseBody
	public List<BillInvoiceApplyDtl> queryInvoiceApplyDtl(@RequestParam("billNo")String billNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		List<BillInvoiceApplyDtl> list = this.billInvoiceApplyDtlManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping(value = "/list.json")
   	@ResponseBody
   	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
       	String billNo = req.getParameter("billNo");
       	if(StringUtils.isEmpty(billNo)) {
       		Map<String, Object> obj = new HashMap<String, Object>();
       		obj.put("total", 0);
       		obj.put("rows", new ArrayList<BillInvoiceApplyDtl>(1));
       		return obj;
       	}
   		return super.queryList(req, model);
   	}
}