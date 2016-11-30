package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface PurchasePriceMapper extends BaseCrudMapper {

	/**
	 * 查询单据采购价
	 * @param params
	 * @return
	 */
	PurchasePrice getBalancePurchasePrice(@Param("params") Map<String, Object> params);

	/**
	 * 根据货号查询采购价
	 * @param params
	 * @return
	 */
	PurchasePrice getBalancePurchasePriceByItemNo(@Param("params") Map<String, Object> params);
	
}