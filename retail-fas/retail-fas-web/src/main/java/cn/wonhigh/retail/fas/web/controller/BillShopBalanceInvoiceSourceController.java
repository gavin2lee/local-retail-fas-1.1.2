package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceInvoiceSource;
import cn.wonhigh.retail.fas.manager.BillShopBalanceInvoiceSourceManager;

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
@RequestMapping("/bill_shop_balance_invoice_source")
public class BillShopBalanceInvoiceSourceController extends BaseCrudController<BillShopBalanceInvoiceSource> {
    @Resource
    private BillShopBalanceInvoiceSourceManager billShopBalanceInvoiceSourceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_invoice_source/",billShopBalanceInvoiceSourceManager);
    }
}