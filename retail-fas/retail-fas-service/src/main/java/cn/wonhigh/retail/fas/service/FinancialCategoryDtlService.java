package cn.wonhigh.retail.fas.service;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-23 10:38:39
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface FinancialCategoryDtlService extends BaseCrudService {
	
	/**
	 * 通过财务大类编码删除明细数据
	 * @param financialCategoryNo 财务大类编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
	int deleteByFinancialCategoryNo(String financialCategoryNo) throws ServiceException;
}