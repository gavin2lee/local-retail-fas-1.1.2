package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.vo.LookupVo;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
public interface BillCommonInvoiceRegisterService extends BaseCrudService {
	
//	int delete(String[] ids) throws ServiceException;
	
	public  List<LookupVo> findByAllCustomer(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
	public int findAllCustomerCount(Map<String,Object> params)throws ServiceException;

	int selectCountByBalanceNo(Map<String, Object> params)throws ServiceException;

	List<BillCommonInvoiceRegister> selectListByBalanceNo(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;
	
	/**
	 * 根据单据获取开票申请
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<BillCommonInvoiceRegister> getByInvoice(SimplePage page,String orderByField,String orderBy,@Param("params") Map<String, Object> params);
	
	/**
	 * 根据单据获取开票申请数量
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int getCountByInvoice(Map<String,Object> params);
	
	public void updateInvoice(String invoiceRegisterNo,String id,String user);
	
	public List<BillCommonInvoiceRegister> getShopAndBrand(Map<String, Object> params);
	
	
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
//	 * @return
//	 */
//	public List<BillCommonInvoiceRegister> findInvoiceRegisterForPayment(SimplePage page, Map<String,Object> params) throws ServiceException;
//	
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息记录数
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录数
//	 * @return
//	 */
//	public int findInvoiceregisterCountForPayment(Map<String,Object> params) throws ServiceException;
//	
//	/**
//	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
//	 * @param params
//	 */
//	public void updateUseFlagByBillNo(Map<String,Object> params) throws ServiceException;
}