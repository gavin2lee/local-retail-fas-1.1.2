package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;

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
public interface BillInvoiceApplyGeneratorManager extends BaseCrudManager {


	public Map<String, Object> generateBalanceInvoiceApply(
			List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators) throws ManagerException;
	
	public Map<String,Object> addInvoiceApplyMainInfo(List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyTemp,String createUser,String invoiceInfoStatus) throws ManagerException ;
}