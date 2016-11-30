package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;


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
public interface PrintManager {
	public Map<String, Object> getPrintListByBalanceNo(String balanceNo)throws ManagerException;

	public Map<String, Object> getBalanceGatherListByBalanceNo(String balanceNo, Date balanceEndDate)throws ManagerException;

	public Map<String, Map<String, Object>> getBalanceDiffData(String companyNo,String brandNo, String year, String month)throws ManagerException;
}