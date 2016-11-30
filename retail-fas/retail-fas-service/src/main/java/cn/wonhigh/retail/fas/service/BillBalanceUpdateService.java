package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

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
public interface BillBalanceUpdateService extends BaseCrudService{

	BillBalance add(BillBalance obj, Map<String, Object> params)throws ServiceException;

	BillBalance update(BillBalance obj)throws ServiceException;

	Integer delete(BillBalance obj)throws ServiceException;

	BillBalance createBalance(BillBalance obj, List<Object> lstItem)throws ServiceException;

	BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem)throws ServiceException;

}