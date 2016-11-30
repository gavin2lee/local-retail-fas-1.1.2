package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
public interface BillBalanceUpdateManager extends BaseCrudManager{
	
	public BillBalance add(BillBalance obj, Map<String, Object> params)throws ManagerException;

	public BillBalance update(BillBalance obj)throws ManagerException;

	public Integer delete(BillBalance obj)throws ManagerException;

	public BillBalance createBalance(BillBalance obj, List<Object> lstItem)throws ManagerException;

	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem)throws ManagerException;

	
}