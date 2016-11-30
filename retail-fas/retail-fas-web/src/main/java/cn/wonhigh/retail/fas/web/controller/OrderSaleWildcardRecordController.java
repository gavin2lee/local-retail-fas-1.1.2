package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.OrderSaleWildcardRecord;
import cn.wonhigh.retail.fas.manager.OrderSaleWildcardRecordManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
@RequestMapping("/order_sale_wildcard_record")
public class OrderSaleWildcardRecordController extends BaseCrudController<OrderSaleWildcardRecord> {
    @Resource
    private OrderSaleWildcardRecordManager orderSaleWildcardRecordManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("order_sale_wildcard_record/",orderSaleWildcardRecordManager);
    }
}