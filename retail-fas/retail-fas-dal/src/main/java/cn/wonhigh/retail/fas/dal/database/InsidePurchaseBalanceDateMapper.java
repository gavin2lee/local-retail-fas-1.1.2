package cn.wonhigh.retail.fas.dal.database;

import cn.wonhigh.retail.fas.common.model.InsidePurchaseBalanceDate;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-02-02 20:00:10
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
public interface InsidePurchaseBalanceDateMapper extends BaseCrudMapper {
	
	/**
	 * 根据结算公司及日期，修改是否已开票标识
	 * @param insidePurchaseBalanceDate
	 */
	public void updateInvoiceFlagByCondition(InsidePurchaseBalanceDate insidePurchaseBalanceDate);
}