package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-05 14:55:50
 * @version 1.0.8
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface TransferBalanceDateManager extends BaseCrudManager {

	/**
	 * 批量生成
	 * @param tbd
	 * @return
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String, Object> addBalanceDateByBatch(TransferBalanceDate tbd) throws ServiceException, Exception;
}