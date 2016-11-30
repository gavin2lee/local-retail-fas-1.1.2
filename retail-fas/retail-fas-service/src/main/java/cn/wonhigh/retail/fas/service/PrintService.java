package cn.wonhigh.retail.fas.service;


import java.util.Date;
import java.util.Map;

import com.yougou.logistics.base.common.exception.ServiceException;


/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public interface PrintService{
	public Map<String, Object> getPrintListByBalanceNo(String balanceNo)throws ServiceException;

	public Map<String, Object> getBalanceGatherListByBalanceNo(String balanceNo, Date balanceEndDate)throws ServiceException;

	public Map<String, Map<String, Object>> getBalanceDiffData(String companyNo,String brandNo, String year, String month)throws ServiceException;
}