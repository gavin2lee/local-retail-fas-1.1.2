package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.TicketCollect;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface TicketCollectService extends BaseCrudService {
	
	/**
	 * 根据参数获取收款等级信息
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public TicketCollect selectTicketCollectByTicketNo(Map<String, Object> params) throws ServiceException;
}