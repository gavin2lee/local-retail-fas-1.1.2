package cn.wonhigh.retail.fas.service;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-04 15:25:06
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
public interface OfficialItemService extends BaseCrudService {

	int deleteByItemCodeAndQuotoNo(String itemCode, String quoteNo) throws ServiceException;

	int deleteAll() throws ServiceException;
}