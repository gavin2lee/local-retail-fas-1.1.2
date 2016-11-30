package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.vo.LookupVo;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface BillCommonInvoiceRegisterMapper extends BaseCrudMapper {
	
	public String getBalanceType(@Param("billNo")String billNo);
	
	/**
	 * 反写开票申请单号
	 * @param BillBalance 
	 */
	public void updateBillBalanceByBillNo(BillCommonInvoiceRegister  invoiceRegister);
	
	public  List<LookupVo> selectByAllCustomer(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);
	
	  public int selectAllCustomerCount(@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);

	public int selectCountByBalanceNo(@Param("params")Map<String, Object> params);

	public List<BillCommonInvoiceRegister> selectListByBalanceNo(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
    /**
     * 根据单据查询开票申请
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillCommonInvoiceRegister> getByInvoice(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

    public int getCountByInvoice(@Param("params")Map<String, Object> params);
    
    //到票确认
    public void updateConfirm(@Param("invoiceRegisterNo")String invoiceRegisterNo,@Param("dtlId")String id,@Param("user")String user);
    
    //获取店铺和品牌
    public List<BillCommonInvoiceRegister> getShopAndBrand(@Param("params")Map<String, Object> params);

//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
//	 * @return
//	 */
//	public List<BillCommonInvoiceRegister> findInvoiceRegisterForPayment(@Param("page") SimplePage page, @Param("params")Map<String,Object> params);
//	
//	public int findInvoiceregisterCountForPayment(@Param("params")Map<String,Object> params);
//	
//	/**
//	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
//	 * @param params
//	 */
//	public void updateUseFlagByBillNo(@Param("params")Map<String,Object> params);
}