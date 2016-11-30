package cn.wonhigh.retail.fas.dal.database;

import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
public interface InvoiceTemplateSetMapper extends BaseCrudMapper {
	
	/**
	 * 查询当前时间最新单号(用于生成单号)
	 * @param invoiceTemplateSet  查询条件
	 * @return String 最新单号
	 */
	public String selectInvoiceTemplateMaxBillNo(InvoiceTemplateSet  invoiceTemplateSet);
	
	public <InvoiceTemplateSet> int deleteByModel(InvoiceTemplateSet  invoiceTemplateSet);
	
	public  int checkIsUseData(String invoiceTempNo);
}