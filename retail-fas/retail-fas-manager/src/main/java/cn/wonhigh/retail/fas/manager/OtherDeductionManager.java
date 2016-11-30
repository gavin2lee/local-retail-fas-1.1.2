package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-21 14:30:24
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Lad 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface OtherDeductionManager extends BaseCrudManager {

	List<OtherDeduction> findFooter(Map<String, Object> params)throws ManagerException;

	void saveReturnList(String username, String parentId, List<Object> list)throws ManagerException;
	
	List<OtherDeduction> setOtherDedutionProperies(List<OtherDeduction> list,Map<String, String> currencyInfo) throws ManagerException;
	
	/**
	 * 分配扣项
	 * @param list
	 * @return
	 * @throws ManagerException
	 */
	Boolean distributeDeduction(List<OtherDeduction> list) throws ManagerException;

}