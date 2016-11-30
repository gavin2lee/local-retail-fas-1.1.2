package cn.wonhigh.retail.fas.service;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 11:02:24
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
public interface StoreService extends BaseCrudService {

	/**
	 * 根据编码查询名称
	 * @param orderUnitNo
	 * @return
	 * @throws ServiceException
	 */
	String findStoreNameByNo(String storeNo) throws ServiceException;
	
}