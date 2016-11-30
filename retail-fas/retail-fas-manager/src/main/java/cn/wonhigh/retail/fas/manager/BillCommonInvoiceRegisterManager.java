package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderInvoiceParameterDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillCommonInvoiceRegisterManager extends BaseCrudManager {
	
	int remove(String[] ids) throws ManagerException;
	
	public void updateConfirm(List<BillCommonInvoiceRegister> invoiceRegisterList,String user) throws ManagerException;
	
	public  List<LookupVo> findByAllCustomer(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)throws ManagerException;
	
	public int selectAllCustomerCount(Map<String,Object> params)throws ManagerException;

	public int selectCountByBalanceNo(Map<String, Object> params)throws ManagerException;

	public List<BillCommonInvoiceRegister> selectListByBalanceNo(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;
	
	public int addBillCommonInvoiceRegister(BillCommonInvoiceRegister billCommonInvoiceRegister,List<OcOrderInvoiceParameterDto> orderDtlDtoList) throws ManagerException;
	
	 /**
     * 发票登记页面保存处理(从销售明细页面直接生成发票登记信息)
     * orderDtlDtoList：包括订单号及销售类型（正常销售、退换货）
     */
    public int addByGroupOrMemberBuyer(BillCommonInvoiceRegister invoiceRegister,List<OcOrderInvoiceParameterDto> orderDtlDtoList) throws ManagerException ;
	
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
//	 * @return
//	 */
//	public List<BillCommonInvoiceRegister> findInvoiceRegisterForPayment(SimplePage page, Map<String,Object> params) throws ManagerException;
//	
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息记录数
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录数
//	 * @return
//	 */
//	public int findInvoiceregisterCountForPayment(Map<String,Object> params) throws ManagerException;
//	
//	/**
//	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
//	 * @param params
//	 */
//	public void updateUseFlagByBillNo(Map<String,Object> params) throws ManagerException ;
	
	/**
	 * 导入发票明细，包括发票表头主体信息（可能存在多笔一样的主体信息，例如一张发票有多张明细情况时）
	 * @param dtlList 发票明细集合,包括开票申请号及公司、客户等信息
	 * @param createUser 登陆用户名称
	 * @throws ManagerException
	 */
	public void doImportInvoiceRegsiter(List<BillCommonRegisterDtl> dtlList,String createUser) throws ManagerException;
	
	/**
	 * 根据开票申请号查询开票申请信息,并设置发票登记的表头基本信息;并且根据开票申请号回写发票号
	 * @param billCommonInvoiceRegister 发票登记对象
	 * @return
	 * @throws ServiceException
	 */
	public String addInvoiceRegsiter(BillCommonInvoiceRegister billCommonInvoiceRegister) throws ServiceException ;
	
	/**
	 * 开票申请列表选中开票申请记录，直接点击“生成发票”按钮，根据选择的开票申请信息，按开票方+收票方+结算类型+发票类型+是否预开票 属性区分，
	 * 是否开成一笔发票信息还是多笔发票信息
	 * @param list 选中的开票申请记录
	 * @param userName 操作人名称，用于作创建人
	 * @return 生成的发票信息集合
	 * @throws ManagerException
	 */
	public List<BillCommonInvoiceRegister> addInoviceRegisters(List<Object> list,String userName) throws ManagerException;
	
    /**
     * 查询开票申请
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillCommonInvoiceRegister> getByInvoice(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
    
    public int getByInvoiceCount(Map<String, Object> params)throws ManagerException;
}