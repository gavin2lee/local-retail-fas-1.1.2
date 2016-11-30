package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.WholesaleCustomerOrderRemaining;
import cn.wonhigh.retail.fas.manager.WholesaleCustomerOrderRemainingManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-05-24 14:04:10
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
@RequestMapping("/wholesale_customer_order_remaining")
public class WholesaleCustomerOrderRemainingController extends BaseCrudController<WholesaleCustomerOrderRemaining> {
    @Resource
    private WholesaleCustomerOrderRemainingManager wholesaleCustomerOrderRemainingManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_customer_order_remaining/",wholesaleCustomerOrderRemainingManager);
    }
}