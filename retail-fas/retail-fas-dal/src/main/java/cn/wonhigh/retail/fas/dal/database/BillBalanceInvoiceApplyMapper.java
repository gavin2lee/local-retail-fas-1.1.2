package cn.wonhigh.retail.fas.dal.database;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-08 14:57:03
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
public interface BillBalanceInvoiceApplyMapper extends BaseCrudMapper {
	
	/**
	 * 查询当前时间最新结算单号(用于生成结算单号)
	 * @param invoiceApply  查询条件
	 * @return String 最新结算单号
	 */
	public String selectInvoiceApplyMaxBillNo(BillBalanceInvoiceApply  invoiceApply);

//	public String getBalanceType(@Param("billNo")String billNo);
	
	/**
	 * 反写开票申请单号
	 * @param BillBalance 
	 */
	public void updateInvoiceApplyNo(BillBalanceInvoiceApply  invoiceApply);	
	
	
	List<BillSaleBalance> getBillSaleBalanceSum(@Param("page")SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params") Map<String,Object> params);
	
	List<BillSaleBalance> getBillSaleBalanceDtl(@Param("page")SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params") Map<String,Object> params);
	
	public int selectBillSaleBalanceCount(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 针对发票登记的开票申请列表查询
	 * @param page
	 * @param params
	 * @return
	 */
	List<BillBalanceInvoiceApply> getByPage(@Param("page")SimplePage page, @Param("params") Map<String,Object> params);
	
	int getCount(@Param("params") Map<String,Object> params);
	
	/**
	 * 删除发票登记时清空发票登记号
	 * @param invoiceApply
	 * @return
	 */
	void updateInvoiceRegisterNo(BillBalanceInvoiceApply invoiceApply);
	
	/**
	 * 根据结算单号查询开票申请
	 * @param params
	 * @return
	 */
    public int getCountByBalanceNo(@Param("params")Map<String,Object> params);
    
    /**
     * 根据结算单号查询开票申请
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillBalanceInvoiceApply> getByBalanceNo(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
    
    /**
     * 获取地区批发团购的大类汇总信息
     * @param params
     * @return
     */
    List<BillSaleBalance> getBillSaleOrderSum(@Param("params") Map<String,Object> params);

	public List<BillSaleBalance> getBillSaleSummaryByBalanceNos(@Param("params") Map<String,Object> params);

	public BigDecimal getDeductionAmountByBalanceNos(@Param("params") Map<String,Object> params);
	
	/**
	 * 在团购预付款登记里，根据结算公司和客户查询，取得开票申请的信息
	 * @param page
	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
	 * @return
	 * @author wangyj
	 */
	public List<BillCommonInvoiceRegister> findInvoiceApplyForPayment(@Param("page") SimplePage page, @Param("params")Map<String,Object> params);
	
	public int findInvoiceApplyCountForPayment(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
	 * @param params
	 * @author wangyj
	 */
	public void updateUseFlagByBillNo(@Param("params")Map<String,Object> params);
	
	/**
	 * 导出税控系统的模板
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<BillBalanceInvoiceApply> findTaxExportList(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询华东税控模板信息
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceApply> findHdTaxExportList(@Param("params")Map<String,Object> params);
	
	
	
}