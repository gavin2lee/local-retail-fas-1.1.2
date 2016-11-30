package cn.wonhigh.retail.fas.manager;

import java.util.Date;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-29 18:03:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface PurchasePriceManager extends BaseCrudManager {
	/**
	 * 查询单据的采购价
	 * @param itemNo
	 * @param supplierNo
	 * @param billDate
	 * @return
	 * @throws ServiceException
	 */
	PurchasePrice findBalancePurchasePrice(String itemNo, String supplierNo,
			Date billDate) throws ManagerException;
	
	/**
	 * 根据货号查询采购价
	 * @param itemNo
	 * @param supplierNo
	 * @param billDate
	 * @return
	 * @throws ServiceException
	 */
	PurchasePrice findBalancePurchasePriceByItemNo(String itemNo,
			Date billDate) throws ManagerException;
}