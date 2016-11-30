package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
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
public interface BillBalanceInvoiceApplyService extends BaseCrudService {
	int delete(String[] ids) throws ServiceException;
	
	List<BillSaleBalance> getBillSaleBalanceSum(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);

	List<BillSaleBalance> getBillSaleBalanceDtl(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);

	public int selectBillSaleBalanceCount(Map<String,Object> params) throws ServiceException;

	//BillBalanceInvoiceApply addFetchId(BillBalanceInvoiceApply model) throws ServiceException;
	
	/**
	 * 生成开票申请(从结算单点击按钮生成)
	 * @param model
	 * @return
	 * @throws ServiceException
	 */
	//public BillBalanceInvoiceApply addBillBalanceInvoiceApply(BillBalanceInvoiceApply model)throws ServiceException;
	
	/**
	 * 针对开票登记获取开票申请信息
	 * @param page
	 * @param params
	 * @return
	 */
	List<BillBalanceInvoiceApply> getByPage(SimplePage page, Map<String,Object> params);
	
	int getCount(Map<String,Object> params);
	
	public void updateInvoiceApplyNo(BillBalanceInvoiceApply  invoiceApply);
	
	/**
	 * 发票登记时，根据开票申请号回写发票编号
	 * @param billBalanceInvoiceApply
	 * @author Wangyj
	 */
	public void updateByPrimaryKeySelective(BillBalanceInvoiceApply billBalanceInvoiceApply);
	
	/**
	 * 删除发票登记记录时，根据发票号清空开票申请中的发票号
	 * @param billBalanceInvoiceApply
	 * @author Wangyj
	 */
	public void updateInvoiceRegisterNo(BillBalanceInvoiceApply billBalanceInvoiceApply);
	
	
	/**
	 * 根据结算单获取开票申请
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBalanceInvoiceApply> getByBalanceNo(SimplePage page,String orderByField,String orderBy, Map<String,Object> params);

	/**
	 * 根据结算单获取开票申请数量
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int getCountByBalanceNo(Map<String,Object> params);
	
	/**
	 * 获取地区批发团购订单大类汇总信息
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> getBillSaleOrderSum(Map<String,Object> params);

	List<BillSaleBalance> getBillSaleSummaryByBalanceNos(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 在团购预付款登记里，根据结算公司和客户查询，取得开票申请的信息
	 * @param page
	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
	 * @return
	 * @author wangyj
	 */
	public List<BillCommonInvoiceRegister> findInvoiceApplyForPayment(SimplePage page, Map<String,Object> params) throws ServiceException;
	
	public int findInvoiceApplyCountForPayment(Map<String,Object> params) throws ServiceException;
	
	/**
	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
	 * @param params
	 * @author wangyj
	 */
	public void updateUseFlagByBillNo(Map<String,Object> params) throws ServiceException;

	BigDecimal findDeductionAmountByBalanceNos(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 导出税控系统的模板
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<BillBalanceInvoiceApply> findTaxExportList(Map<String,Object> params) throws ServiceException;
	
	/**
	 * 查询华东税控模板信息
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceApply> findHdTaxExportList(Map<String,Object> params) throws ServiceException;
	
}