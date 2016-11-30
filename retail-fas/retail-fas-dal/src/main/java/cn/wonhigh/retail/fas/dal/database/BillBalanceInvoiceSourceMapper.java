package cn.wonhigh.retail.fas.dal.database;


import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceSource;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
public interface BillBalanceInvoiceSourceMapper extends BaseCrudMapper {
	
	void deleteInvoiceSource(String billNo);
	/**
	 * 内购结算类型，修改需要修改开票金额
	 * @param billBalanceInvoiceSource
	 * @return
	 */
	public int updateAmountByBillNo(BillBalanceInvoiceSource billBalanceInvoiceSource);
}