package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillPrePaymentNt;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-22 12:14:38
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
public interface BillPrePaymentNtManager extends BaseCrudManager {

	int doAudit(List<BillPrePaymentNt> oList) throws ManagerException;
	
	public int addGroupPrePayment(BillPrePaymentNt billPrePaymentNt) throws ManagerException;
	
//	public void saveWholesaleCustomerRemainingInfo(BillPrePaymentNt model)throws ManagerException;
	
	public BillPrePaymentNt calcPrePaymentTotal(Map<String, Object> params)throws ManagerException;
	
}