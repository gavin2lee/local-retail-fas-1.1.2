package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.TicketCollect;
import cn.wonhigh.retail.fas.manager.TicketCollectManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2014-11-13 15:44:38
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
@RequestMapping("/ticket_collect")
public class TicketCollectController extends BaseCrudController<TicketCollect> {
    @Resource
    private TicketCollectManager ticketCollectManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("ticket_collect/",ticketCollectManager);
    }
}