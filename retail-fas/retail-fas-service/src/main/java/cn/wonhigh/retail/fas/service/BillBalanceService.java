package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillBalanceService extends BaseCrudService {
	
	public int verify(BillBalance billBalance) throws ServiceException;

	public void clearSaleBalanceNo(String balanceNo) throws ServiceException;

	public void clearBuyBalanceNo(String balanceNo) throws ServiceException;
	
	public void resetSaleBalanceActualCost(String balanceNo) throws ServiceException;
	
	public void updateSaleBalanceStatus(BillBalance billBalance) throws ServiceException;

	public void updateSaleBalanceNo(BillBalance dealBalance) throws ServiceException;
	
	
}