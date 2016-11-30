package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
public interface BillAskPaymentManager extends BaseCrudManager {

	int generateBillBybalance(List<BillBalance> list, String loginName) throws ManagerException;

	int verify(BillAskPayment obj)throws ManagerException;

	List<BillAskPayment> selectFooter(Map<String, Object> params) throws ManagerException;

	List<BillAskPayment> setTargetCurencyPropertites(List<BillAskPayment> list,Map<String, String> currencyInfo) throws ManagerException;

	/**
	 * @param bill
	 * @return
	 * @throws ServiceException 
	 */
	BillAskPayment addMainForm(BillAskPayment bill) throws ServiceException;
}