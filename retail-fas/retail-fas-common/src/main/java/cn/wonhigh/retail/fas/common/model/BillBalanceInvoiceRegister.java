package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-28 13:44:50
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
public class BillBalanceInvoiceRegister  extends FasBaseModel  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 发票单号
     */
    private String billNo;

    /**
     * 公司编码(开票方)-卖方
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 客户编码(收票方)-买方
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 开票名称
     */
    private String name;

    /**
     * 发票类型(0 = 普通发票 1 = 增值票)
     */
    private Byte invoiceType;

    /**
     * 申请开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyDate;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceDate;

    /**
     * 开票金额
     */
    private BigDecimal amount;

    /**
     * 源单类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)
     */
    private Integer balanceType;

    /**
     * 源单编码
     */
    private String balanceNo;

    /**
     * 是否预开票(0 = 是 1 = 否)
     */
    private Byte preInvoice;

    /**
     * 预估成本
     */
    private BigDecimal estimatedAmount;


    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
  	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
  	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
  	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date auditTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_balance_invoice_register.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_balance_invoice_register.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_balance_invoice_register.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_balance_invoice_register.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_balance_invoice_register.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_balance_invoice_register.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_balance_invoice_register.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_balance_invoice_register.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_balance_invoice_register.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_balance_invoice_register.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of bill_balance_invoice_register.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for bill_balance_invoice_register.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     *
     * @return the value of bill_balance_invoice_register.invoice_type
     */
    public Byte getInvoiceType() {
        return invoiceType;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     * @param invoiceType the value for bill_balance_invoice_register.invoice_type
     */
    public void setInvoiceType(Byte invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of bill_balance_invoice_register.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for bill_balance_invoice_register.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_balance_invoice_register.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_balance_invoice_register.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of bill_balance_invoice_register.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for bill_balance_invoice_register.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_balance_invoice_register.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_balance_invoice_register.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_balance_invoice_register.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_balance_invoice_register.balance_type
     */
    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_balance_invoice_register.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_balance_invoice_register.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     *
     * @return the value of bill_balance_invoice_register.pre_invoice
     */
    public Byte getPreInvoice() {
        return preInvoice;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     * @param preInvoice the value for bill_balance_invoice_register.pre_invoice
     */
    public void setPreInvoice(Byte preInvoice) {
        this.preInvoice = preInvoice;
    }

    /**
     * 
     * {@linkplain #estimatedAmount}
     *
     * @return the value of bill_balance_invoice_register.estimated_amount
     */
    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    /**
     * 
     * {@linkplain #estimatedAmount}
     * @param estimatedAmount the value for bill_balance_invoice_register.estimated_amount
     */
    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_balance_invoice_register.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_balance_invoice_register.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_balance_invoice_register.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_balance_invoice_register.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bill_balance_invoice_register.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bill_balance_invoice_register.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_balance_invoice_register.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_balance_invoice_register.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of bill_balance_invoice_register.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for bill_balance_invoice_register.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of bill_balance_invoice_register.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for bill_balance_invoice_register.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_balance_invoice_register.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_balance_invoice_register.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}