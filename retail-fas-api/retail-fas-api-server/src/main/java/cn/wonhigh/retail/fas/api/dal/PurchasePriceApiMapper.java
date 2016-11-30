package cn.wonhigh.retail.fas.api.dal;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;

/**
 * 请写出类的用途 
 * @author wangxy
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

public interface PurchasePriceApiMapper {
	/**
	 * 查询单据采购价
	 * @param params
	 * @return
	 */
	PurchasePrice getBillPurchasePrice(@Param("params") Map<String, Object> params);
	
}