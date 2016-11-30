package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BillInvoiceDtl;
import cn.wonhigh.retail.fas.manager.BillInvoiceDtlManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
@RequestMapping("/bill_invoice_dtl")
public class BillInvoiceDtlController extends BaseCrudController<BillInvoiceDtl> {
    @Resource
    private BillInvoiceDtlManager billInvoiceDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_invoice_dtl/",billInvoiceDtlManager);
    }

}