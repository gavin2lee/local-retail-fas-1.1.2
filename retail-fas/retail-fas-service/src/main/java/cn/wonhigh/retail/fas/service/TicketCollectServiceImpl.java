package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.TicketCollect;
import cn.wonhigh.retail.fas.dal.database.TicketCollectMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
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
@Service("ticketCollectService")
class TicketCollectServiceImpl extends BaseCrudServiceImpl implements TicketCollectService {
    @Resource
    private TicketCollectMapper ticketCollectMapper;

    @Override
    public BaseCrudMapper init() {
        return ticketCollectMapper;
    }

	@Override
	public TicketCollect selectTicketCollectByTicketNo(Map<String, Object> params) throws ServiceException {
		return ticketCollectMapper.selectTicketCollectByTicketNo(params);
	}
}