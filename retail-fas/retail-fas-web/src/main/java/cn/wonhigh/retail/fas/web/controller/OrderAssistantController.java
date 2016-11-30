package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.OrderAssistant;
import cn.wonhigh.retail.fas.manager.OrderAssistantManager;
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
@RequestMapping("/order_assistant")
public class OrderAssistantController extends BaseCrudController<OrderAssistant> {
    @Resource
    private OrderAssistantManager orderAssistantManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("order_assistant/",orderAssistantManager);
    }
}