package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.RuleMatchFail;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-15 17:42:50
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
public interface RuleMatchFailService extends BaseCrudService {

	public RuleMatchFail findHQItemUnmatched(String itemNo) throws ServiceException;
	
	public RuleMatchFail findRegionItemUnmatched(String itemNo, String zoneNo) throws ServiceException;
	
}