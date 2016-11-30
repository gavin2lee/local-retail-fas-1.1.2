package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.manager.OrderDtlManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 15:21:34
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
@RequestMapping("/order_dtl")
public class OrderDtlController extends BaseCrudController<OrderDtl> {
	
    @Resource
    private OrderDtlManager orderDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("order_dtl/",orderDtlManager);
    }
}