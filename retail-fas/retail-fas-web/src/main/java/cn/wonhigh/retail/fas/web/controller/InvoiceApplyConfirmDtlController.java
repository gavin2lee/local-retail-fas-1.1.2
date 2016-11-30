package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.InvoiceApplyConfirmDtl;
import cn.wonhigh.retail.fas.manager.InvoiceApplyConfirmDtlManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-06-03 16:57:11
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
@RequestMapping("/invoice_apply_confirm_dtl")
public class InvoiceApplyConfirmDtlController extends BaseCrudController<InvoiceApplyConfirmDtl> {
    @Resource
    private InvoiceApplyConfirmDtlManager invoiceApplyConfirmDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("invoice_apply_confirm_dtl/",invoiceApplyConfirmDtlManager);
    }
}