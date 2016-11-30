package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillBalanceInvoiceInfoManager extends BaseCrudManager {
	
	/**
	 * 根据结算公司和客户编码查询发票信息
	 * @param page
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalanceInvoiceInfo> findByCustomerNo(SimplePage page, Map<String,Object> params)throws ManagerException;
	
	/**
	 * 根据结算公司和客户编码查询发票数量
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int getCount(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 根据发票号获取发票信息
	 * @param invoiceNo
	 * @return
	 * @throws ManagerException
	 */
	public BillBalanceInvoiceInfo getInvoiceAmount(String invoiceNo)throws ManagerException;
}