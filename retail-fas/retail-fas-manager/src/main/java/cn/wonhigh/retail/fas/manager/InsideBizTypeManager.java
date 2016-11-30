package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.InsideBizType;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-04-02 13:45:59
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
public interface InsideBizTypeManager extends BaseCrudManager {
	
	public InsideBizType add(InsideBizType obj, List<Object> insertedList,List<Object> clientList,Map params) throws ManagerException;
	
	public InsideBizType update(InsideBizType obj, List<Object> insertedList,
			List<Object> updatedList, List<Object> deletedList,List<Object> clientInsList)throws ManagerException ;
	
	public Integer delete(List<Object> dtlList) throws ManagerException;
}