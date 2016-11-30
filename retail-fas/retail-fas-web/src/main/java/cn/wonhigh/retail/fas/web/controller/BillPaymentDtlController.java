package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillPaymentDtl;
import cn.wonhigh.retail.fas.manager.BillPaymentDtlManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/bill_payment_dtl")
public class BillPaymentDtlController extends BaseCrudController<BillPaymentDtl> {
    @Resource
    private BillPaymentDtlManager billPaymentDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_payment_dtl/",billPaymentDtlManager);
    }
}