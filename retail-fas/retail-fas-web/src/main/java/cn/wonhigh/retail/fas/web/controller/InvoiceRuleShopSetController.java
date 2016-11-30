package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.InvoiceRuleShopSet;
import cn.wonhigh.retail.fas.manager.InvoiceRuleShopSetManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-15 14:29:23
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
@RequestMapping("/invoice_rule_shop_set")
public class InvoiceRuleShopSetController extends BaseController<InvoiceRuleShopSet> {
    @Resource
    private InvoiceRuleShopSetManager invoiceRuleShopSetManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("invoice_rule_shop_set/",invoiceRuleShopSetManager);
    }
}