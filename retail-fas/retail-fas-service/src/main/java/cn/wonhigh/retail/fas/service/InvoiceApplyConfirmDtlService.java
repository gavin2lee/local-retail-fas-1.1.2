package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.InvoiceApplyConfirmDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface InvoiceApplyConfirmDtlService extends BaseCrudService {

	public int updateByOrderNo(InvoiceApplyConfirmDtl invoiceApplyConfirmDtl) throws ServiceException;
	
	public int updateByInvoiceApplyNo(String invoiceApplyNo) throws ServiceException;
	
	/**
	 * 根据开票申请号信息开票日期及金额
	 * @param invoiceApplyConfirmDtl
	 * @return
	 */
	public int updateConfirmDtlByApplyNo(InvoiceApplyConfirmDtl invoiceApplyConfirmDtl)throws ServiceException ;
}