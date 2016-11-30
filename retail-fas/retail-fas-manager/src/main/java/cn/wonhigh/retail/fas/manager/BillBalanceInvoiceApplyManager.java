package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
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
public interface BillBalanceInvoiceApplyManager extends BaseCrudManager {
	
	int remove(String[] ids) throws ManagerException;
	
	int deleteOn(String id, String billNo, String balanceType) throws ManagerException;
	
	List<BillSaleBalance> getBillSaleBalanceSum(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	List<BillSaleBalance> getBillSaleBalanceDtl(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	public int selectBillSaleBalanceCount(Map<String,Object> params)throws ManagerException;

	BillBalanceInvoiceApply addFetchId(BillBalanceInvoiceApply model,List<Object> lstItem) throws ManagerException;
	
	/**
	 * 新增发票申请(从结算单点击发票申请生成发票)
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	public BillBalanceInvoiceApply addBillBalanceInvoiceApply(BillBalanceInvoiceApply model) throws ManagerException;
	
	/**
	 * 针对发票登记查询开票申请信息
	 * @param page
	 * @param params
	 * @return
	 */
	List<BillBalanceInvoiceApply> getByPage(SimplePage page, Map<String,Object> params);
	
	int getCount(Map<String,Object> params);
	
//	/**
//	 * 根据单号获取pos订单号
//	 * @param billNo
//	 * @param balanceType
//	 * @return
//	 */
//	public String getOrderBillNo(String billNo, String balanceType, String billType);
//	
//	/**
//	 * 根据单号获取团购/员购明细信息
//	 * @param balanceType 结算类型
//	 * @param invoiceNo 发票号
//	 * @param applyNo 开票申请号
//	 * @return
//	 */
//	public List<POSOrderAndReturnExMainDtlDto> getOrderInfo(String balanceType,String invoiceNo,String applyNo,POSOcOrderParameterParentDto ocOrderParameterParentNoDto);
	
	/**
	 * 根据结算单号查询开票申请
	 * @param params
	 * @return
	 */
    public int getCountByBalanceNo(Map<String,Object> params);
    
    /**
     * 根据结算单号查询开票申请
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillBalanceInvoiceApply> getByBalanceNo(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) ;
    
    /**
	 * 在团购预付款登记里，根据结算公司和客户查询，取得开票申请的信息
	 * @param page
	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
	 * @return
	 * @author wangyj
	 */
	public List<BillCommonInvoiceRegister> findInvoiceApplyForPayment(SimplePage page, Map<String,Object> params) throws ManagerException;
	
	public int findInvoiceApplyCountForPayment(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 批量导入发票信息，根据开票申请号，获取发票表头信息及明细信息，导入的内容只包括发票编码、发票号、快递信息等，其他信息都由开票申请里取
	 * @param dtlList 开票申请号及发票信息，快递信息
	 * @param createUser 登陆用户名称
	 * @throws ManagerException
	 */
	public void doImportInvoiceRegsiter(List<BillCommonRegisterDtl> dtlList,String createUser) throws ManagerException ;
	
	/**
	 * 手动新增开票申请信息的保存处理
	 * @param invoiceApply
	 * @param lstItem 选中的内购结算单据集合
	 * @return
	 * @throws ManagerException
	 */
	public BillBalanceInvoiceApply addInvoiceApply(BillBalanceInvoiceApply invoiceApply,List<Object> lstItem) throws ManagerException;
	
	/**
	 * 结算单直接生成开票申请操作的业务处理
	 * @param balanceInvoiceApplyGenerators
	 * @throws ServiceException
	 * @author wangxj
	 */
	public BillBalanceInvoiceApply addBalanceInvoiceApply(BillBalanceInvoiceApply invoiceApply) throws ManagerException ;
	
	/**
	 * 导出税控系统的模板
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<BillBalanceInvoiceApply> findTaxExportList(Map<String,Object> params) throws ManagerException ;
	
	/**
	 * 查询华东税控模板信息
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceApply> findHdTaxExportList(Map<String,Object> params) throws ManagerException ;
	
}