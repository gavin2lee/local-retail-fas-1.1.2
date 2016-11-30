package cn.wonhigh.retail.fas.api.service;

import java.util.Date;

import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;


/**
 * 公司账套
 * @author yang.y
 * @date  2014-12-05 16:36:16
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
public interface PurchasePriceApiService {
	/**
	 * 查询单据采购价
	 * @param params
	 * @return
	 */
	PurchasePrice findBillPurchasePrice(String itemNo, String supplierNo, Date billDate) throws ServiceException;
	
}