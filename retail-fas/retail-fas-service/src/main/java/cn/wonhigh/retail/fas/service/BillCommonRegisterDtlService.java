package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
public interface BillCommonRegisterDtlService extends BaseCrudService {
	
	/**
	 * 根据发票号获取具体发票信息
	 * @param invoiceNo
	 * @return
	 * @throws ServiceException
	 */
	public BillCommonRegisterDtl getInvoiceAmount(String invoiceNo)throws ServiceException;
	
	/**
	 * 根据发票号删除发票明细
	 * 
	 * @param params
	 * @return
	 */
	public int deleteByBillNo(String billNo);
}