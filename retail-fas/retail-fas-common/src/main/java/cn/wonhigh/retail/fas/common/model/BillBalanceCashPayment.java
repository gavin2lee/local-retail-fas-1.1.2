package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 结算单收款/付款信息 
 * @author user
 * @date  2015-05-14 10:19:25
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
public class BillBalanceCashPayment extends FasBaseModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7904979554462111452L;

    /**
     * 收款单号、付款单号
     */
    private String billNo;
    
    /**
     * 平衡标志 1、相等 0、不相等
     */
    private Integer status;

    /**
     * 结算类型
     */
    private Byte balanceType;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 应收金额、应付金额(结算金额)
     */
    private BigDecimal balanceAmount;

    /**
     * 收款金额
     */
    private BigDecimal receivableAmount;

    /**
     * 付款金额
     */
    private BigDecimal paymentAmount;

    /**
     * 余额=结算金额-收/付款金额
     */
    private BigDecimal overageAmount;

    /**
     * 单据日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date billDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_balance_cash_payment.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_balance_cash_payment.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_balance_cash_payment.balance_type
     */
    public Byte getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_balance_cash_payment.balance_type
     */
    public void setBalanceType(Byte balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_balance_cash_payment.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_balance_cash_payment.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceAmount}
     *
     * @return the value of bill_balance_cash_payment.balance_amount
     */
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * 
     * {@linkplain #balanceAmount}
     * @param balanceAmount the value for bill_balance_cash_payment.balance_amount
     */
    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * 
     * {@linkplain #receivableAmount}
     *
     * @return the value of bill_balance_cash_payment.receivable_amount
     */
    public BigDecimal getReceivableAmount() {
        return receivableAmount;
    }

    /**
     * 
     * {@linkplain #receivableAmount}
     * @param receivableAmount the value for bill_balance_cash_payment.receivable_amount
     */
    public void setReceivableAmount(BigDecimal receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    /**
     * 
     * {@linkplain #paymentAmount}
     *
     * @return the value of bill_balance_cash_payment.payment_amount
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 
     * {@linkplain #paymentAmount}
     * @param paymentAmount the value for bill_balance_cash_payment.payment_amount
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 
     * {@linkplain #overageAmount}
     *
     * @return the value of bill_balance_cash_payment.overage_amount
     */
    public BigDecimal getOverageAmount() {
        return overageAmount;
    }

    /**
     * 
     * {@linkplain #overageAmount}
     * @param overageAmount the value for bill_balance_cash_payment.overage_amount
     */
    public void setOverageAmount(BigDecimal overageAmount) {
        this.overageAmount = overageAmount;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_balance_cash_payment.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_balance_cash_payment.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_balance_cash_payment.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_balance_cash_payment.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_balance_cash_payment.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_balance_cash_payment.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bill_balance_cash_payment.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bill_balance_cash_payment.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_balance_cash_payment.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_balance_cash_payment.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_balance_cash_payment.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_balance_cash_payment.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}