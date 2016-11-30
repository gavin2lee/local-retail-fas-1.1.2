package cn.wonhigh.retail.fas.dal.database;

import cn.wonhigh.retail.fas.common.model.InvoiceApplyConfirmDtl;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-06-03 16:57:11
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
public interface InvoiceApplyConfirmDtlMapper extends BaseCrudMapper {
	
	/**
	 * 根据单据号及产品编号，修改状态或开票申请号
	 * @param invoiceApplyConfirmDtl
	 * @return
	 */
	public int updateByOrderNo(InvoiceApplyConfirmDtl invoiceApplyConfirmDtl);
	
	/**
	 * 开票申请删除时,需要根据开票申请清空关联表的开票申请信息
	 * @param invoiceApplyNo
	 * @return
	 */
	public int updateByInvoiceApplyNo(String invoiceApplyNo);
	
	/**
	 * 根据开票申请号信息开票日期及金额
	 * @param invoiceApplyConfirmDtl
	 * @return
	 */
	public int updateConfirmDtlByApplyNo(InvoiceApplyConfirmDtl invoiceApplyConfirmDtl);
}