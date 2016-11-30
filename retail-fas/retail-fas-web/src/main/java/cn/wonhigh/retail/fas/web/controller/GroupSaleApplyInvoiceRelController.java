package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.GroupSaleApplyInvoiceRel;
import cn.wonhigh.retail.fas.manager.GroupSaleApplyInvoiceRelManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-29 11:56:15
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/group_sale_apply_invoice_rel")
public class GroupSaleApplyInvoiceRelController extends BaseCrudController<GroupSaleApplyInvoiceRel> {
    @Resource
    private GroupSaleApplyInvoiceRelManager groupSaleApplyInvoiceRelManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("group_sale_apply_invoice_rel/",groupSaleApplyInvoiceRelManager);
    }
}