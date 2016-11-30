package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-21 15:51:55
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class InvoiceApplyConfirmDtl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
	/**
     * 主键
     */
    private String id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 
     */
    private String orderDtlId;

    /**
     * 商品编号
     */
    private String itemNo;

    /**
     * 结算类型
     */
    private Byte balanceType;

    /**
     * 单据类型，如：索赔、报废等
     */
    private String billType;

    /**
     * 公司编号
     */
    private String companyNo;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 开票申请单据号
     */
    private String invoiceApplyNo;

    /**
     * 开票申请日期
     */
    private Date invoiceApplyDate;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 销售日期
     */
    private Date outStartDate;

    /**
     * 销售日期
     */
    private Date outEndDate;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    
    public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of invoice_apply_confirm_dtl.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for invoice_apply_confirm_dtl.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of invoice_apply_confirm_dtl.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for invoice_apply_confirm_dtl.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     *
     * @return the value of invoice_apply_confirm_dtl.order_dtl_id
     */
    public String getOrderDtlId() {
        return orderDtlId;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     * @param orderDtlId the value for invoice_apply_confirm_dtl.order_dtl_id
     */
    public void setOrderDtlId(String orderDtlId) {
        this.orderDtlId = orderDtlId;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of invoice_apply_confirm_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for invoice_apply_confirm_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of invoice_apply_confirm_dtl.balance_type
     */
    public Byte getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for invoice_apply_confirm_dtl.balance_type
     */
    public void setBalanceType(Byte balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of invoice_apply_confirm_dtl.bill_type
     */
    public String getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for invoice_apply_confirm_dtl.bill_type
     */
    public void setBillType(String billType) {
        this.billType = billType;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of invoice_apply_confirm_dtl.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for invoice_apply_confirm_dtl.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of invoice_apply_confirm_dtl.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for invoice_apply_confirm_dtl.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of invoice_apply_confirm_dtl.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for invoice_apply_confirm_dtl.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     *
     * @return the value of invoice_apply_confirm_dtl.invoice_apply_no
     */
    public String getInvoiceApplyNo() {
        return invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     * @param invoiceApplyNo the value for invoice_apply_confirm_dtl.invoice_apply_no
     */
    public void setInvoiceApplyNo(String invoiceApplyNo) {
        this.invoiceApplyNo = invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of invoice_apply_confirm_dtl.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for invoice_apply_confirm_dtl.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of invoice_apply_confirm_dtl.invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param invoiceAmount the value for invoice_apply_confirm_dtl.invoice_amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #outStartDate}
     *
     * @return the value of invoice_apply_confirm_dtl.out_start_date
     */
    public Date getOutStartDate() {
        return outStartDate;
    }

    /**
     * 
     * {@linkplain #outStartDate}
     * @param outStartDate the value for invoice_apply_confirm_dtl.out_start_date
     */
    public void setOutStartDate(Date outStartDate) {
        this.outStartDate = outStartDate;
    }

    /**
     * 
     * {@linkplain #outEndDate}
     *
     * @return the value of invoice_apply_confirm_dtl.out_end_date
     */
    public Date getOutEndDate() {
        return outEndDate;
    }

    /**
     * 
     * {@linkplain #outEndDate}
     * @param outEndDate the value for invoice_apply_confirm_dtl.out_end_date
     */
    public void setOutEndDate(Date outEndDate) {
        this.outEndDate = outEndDate;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of invoice_apply_confirm_dtl.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for invoice_apply_confirm_dtl.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of invoice_apply_confirm_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for invoice_apply_confirm_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of invoice_apply_confirm_dtl.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for invoice_apply_confirm_dtl.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of invoice_apply_confirm_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for invoice_apply_confirm_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}