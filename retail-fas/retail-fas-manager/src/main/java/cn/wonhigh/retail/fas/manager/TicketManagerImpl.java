package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.TicketService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2014-11-13 14:45:01
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
@Service("ticketManager")
class TicketManagerImpl extends BaseCrudManagerImpl implements TicketManager {
    @Resource
    private TicketService ticketService;

    @Override
    public BaseCrudService init() {
        return ticketService;
    }
}