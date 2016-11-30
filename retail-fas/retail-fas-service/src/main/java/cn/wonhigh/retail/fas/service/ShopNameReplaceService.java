package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.ShopNameReplace;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-01-06 17:24:59
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
public interface ShopNameReplaceService extends BaseCrudService {
	
	/**
	 * 查询店铺替换名称
	 * @param shopNo
	 * @param brandUnitNo
	 * @return
	 * @throws ServiceException
	 */
	ShopNameReplace selectReplaceName(String shopNo, String brandUnitNo) throws ServiceException;
}