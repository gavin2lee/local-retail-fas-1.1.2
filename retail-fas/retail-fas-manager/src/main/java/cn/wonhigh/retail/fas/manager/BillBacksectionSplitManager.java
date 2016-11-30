package cn.wonhigh.retail.fas.manager;

import java.rmi.ServerException;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillBacksectionSplitManager extends BaseCrudManager {
	
	public int remove(String[] ids) throws ManagerException,ServerException;
	
	/**
	 * 校验回款单是否能被修改、删除
	 * @param id
	 * @return
	 */
	public boolean checkIsUpdate(String id);
}