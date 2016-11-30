package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.TicketCollect;
import cn.wonhigh.retail.fas.service.TicketCollectService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
@Service("ticketCollectManager")
class TicketCollectManagerImpl extends BaseCrudManagerImpl implements TicketCollectManager {
    @Resource
    private TicketCollectService ticketCollectService;

    @Override
    public BaseCrudService init() {
        return ticketCollectService;
    }

	@Override
	public TicketCollect selectTicketCollectByTicketNo(Map<String, Object> params) throws ManagerException{
		try {
			return ticketCollectService.selectTicketCollectByTicketNo(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
}