package cn.wonhigh.retail.fas.dal.database;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-12 14:37:52
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
public interface BillCommonRegisterDtlMapper extends BaseCrudMapper {
	
	/**
	 * 删除
	 * 
	 * @param params
	 * @return
	 */
	public int deleteByBillNo(@Param("billNo") String billNo);
	
	/**
	 * 根据发票号获取发票明细信息
	 * @param invoiceNo
	 * @return
	 */
	public BillCommonRegisterDtl getInvoiceAmount(String invoiceNo);
	
}