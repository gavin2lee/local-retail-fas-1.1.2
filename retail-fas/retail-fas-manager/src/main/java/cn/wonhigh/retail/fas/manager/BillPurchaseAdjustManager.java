package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjust;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
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
public interface BillPurchaseAdjustManager extends BaseCrudManager {
	
	public int verifyBill(BillPurchaseAdjust obj) throws ManagerException;
}