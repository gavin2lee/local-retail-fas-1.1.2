package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillBalanceInvoiceInfoService extends BaseCrudService {
	
	/**
	 * 根据结算公司和客户编码查询发票信息
	 * @param page
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<BillBalanceInvoiceInfo> findByCustomerNo(SimplePage page, Map<String,Object> params)throws ServiceException;
	
	/**
	 * 根据结算公司和客户编码查询发票信息
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int getCount(Map<String,Object> params)throws ServiceException;
	
	/**
	 * 根据发票号获取发票信息
	 * @param invoiceNo
	 * @return
	 * @throws ServiceException
	 */
	public BillBalanceInvoiceInfo getInvoiceAmount(String invoiceNo)throws ServiceException;
	
	void deleteInvoiceInfo(String billNo);
}