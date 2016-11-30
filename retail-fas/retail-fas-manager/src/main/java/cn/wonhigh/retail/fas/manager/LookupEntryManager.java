package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:21:01
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
public interface LookupEntryManager extends BaseCrudManager {
	
	String getEntryNameByLookupIdEntryCode(Map<String, Object> params) throws ManagerException;
	
}