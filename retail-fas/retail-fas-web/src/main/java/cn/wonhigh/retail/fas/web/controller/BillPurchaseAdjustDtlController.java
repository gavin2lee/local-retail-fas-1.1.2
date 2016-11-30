package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjustDtl;
import cn.wonhigh.retail.fas.manager.BillPurchaseAdjustDtlManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
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
@RequestMapping("/bill_purchase_adjust_dtl")
public class BillPurchaseAdjustDtlController extends BaseCrudController<BillPurchaseAdjustDtl> {
	
    @Resource
    private BillPurchaseAdjustDtlManager billPurchaseAdjustDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_purchase_adjust_dtl/",billPurchaseAdjustDtlManager);
    }
    
    
}