package cn.wonhigh.retail.fas.service;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
public interface BillBacksectionSplitService extends BaseCrudService {
	
	int deleteById(int id);
	
	public <ModelType> int deleteByPrimarayKeyForModel(ModelType record);
	
	/**
	 * 统计晚于此回款单创建的回款单条数
	 * @param id
	 * @return
	 */
	public int findAfterCount(String id);

	public <ModelType>  int batchAdd(ModelType model) throws ServiceException;
}