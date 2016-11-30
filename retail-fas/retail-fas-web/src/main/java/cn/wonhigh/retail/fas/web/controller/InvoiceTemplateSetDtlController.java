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

import cn.wonhigh.retail.fas.common.dto.InvoiceCategoryCommonDto;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;
import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.manager.InvoiceTemplateSetDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
@RequestMapping("/invoice_template_set_dtl")
public class InvoiceTemplateSetDtlController extends BaseCrudController<InvoiceTemplateSetDtl> {
    @Resource
    private InvoiceTemplateSetDtlManager invoiceTemplateSetDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("invoice_template_set_dtl/",invoiceTemplateSetDtlManager);
    }
    
    /**
     * 通过模版编码，查询该模版下的明细数据
     * 
     * @param invoicetempNo 模版编码
     * @param request HttpServletRequest
     * @return 设置明细列表
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_invoice_template_set_dtl")
	@ResponseBody
	public List<InvoiceTemplateSetDtl> queryInvoiceTemplateSetDtl(@RequestParam("invoiceTempNo")String invoiceTempNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoiceTempNo", invoiceTempNo);
		List<InvoiceTemplateSetDtl> list = this.invoiceTemplateSetDtlManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	String invoiceTempNo = req.getParameter("invoiceTempNo");
    	if(StringUtils.isEmpty(invoiceTempNo)) {
    		Map<String, Object> obj = new HashMap<String, Object>();
    		obj.put("total", 0);
    		obj.put("rows", new ArrayList<SettleCategoryDtl>(1));
    		return obj;
    	}
		return super.queryList(req, model);
	}
    
    /**
     * 根据店铺编码获取大类信息
     * @param req
     * @return
     */
    @RequestMapping(value = "/categoryList.json")
	@ResponseBody
    public List<InvoiceCategoryCommonDto> selectCateInfo(HttpServletRequest req,Model model){
    	
    	List<InvoiceCategoryCommonDto> list = new ArrayList<InvoiceCategoryCommonDto>();
    	
    	Map<String, Object> params = builderParams(req, model);
		
		if(params == null) {
			return list;
		}
    	return invoiceTemplateSetDtlManager.selectCateInfo(params);
    }
}