package cn.wonhigh.retail.fas.service;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
public interface SettleCategoryDtlService extends BaseCrudService {
	
	/**
	 * 通过结算大类编码删除明细数据
	 * @param settleCategoryNo 结算大类编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
	int deleteBySettleCategoryNo(String settleCategoryNo) throws ServiceException;
}