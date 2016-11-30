package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
public class BillPaymentDtl implements Serializable,SequenceId {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5974973868278231349L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 结算方式编码
     */
    private String settleMethodNo;

    /**
     * 结算方式名称
     */
    private String settleMethodName;

    /**
     * 付款用途
     */
    private String paymentApplication;

    /**
     * 预付订单号
     */
    private String advancePaymentOrderNo;

    /**
     * 应付数量
     */
    private Integer payQty;
    
    /**
     * 应付金额
     */
    private BigDecimal payAmount;

    /**
     * 现金折扣
     */
    private BigDecimal moneyDiscount;

    /**
     * 折后金额
     */
    private BigDecimal discountAmount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 实付金额
     */
    private BigDecimal balanceAmount;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 银行结算号
     */
    private String bankBalanceNo;

    /**
     * 备注
     */
    private String remark;

    public Integer getPayQty() {
		return payQty;
	}

	public void setPayQty(Integer payQty) {
		this.payQty = payQty;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_payment_dtl.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_payment_dtl.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_payment_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_payment_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #settleMethodNo}
     *
     * @return the value of bill_payment_dtl.settle_method_no
     */
    public String getSettleMethodNo() {
        return settleMethodNo;
    }

    /**
     * 
     * {@linkplain #settleMethodNo}
     * @param settleMethodNo the value for bill_payment_dtl.settle_method_no
     */
    public void setSettleMethodNo(String settleMethodNo) {
        this.settleMethodNo = settleMethodNo;
    }

    /**
     * 
     * {@linkplain #settleMethodName}
     *
     * @return the value of bill_payment_dtl.settle_method_name
     */
    public String getSettleMethodName() {
        return settleMethodName;
    }

    /**
     * 
     * {@linkplain #settleMethodName}
     * @param settleMethodName the value for bill_payment_dtl.settle_method_name
     */
    public void setSettleMethodName(String settleMethodName) {
        this.settleMethodName = settleMethodName;
    }

    /**
     * 
     * {@linkplain #paymentApplication}
     *
     * @return the value of bill_payment_dtl.payment_application
     */
    public String getPaymentApplication() {
        return paymentApplication;
    }

    /**
     * 
     * {@linkplain #paymentApplication}
     * @param paymentApplication the value for bill_payment_dtl.payment_application
     */
    public void setPaymentApplication(String paymentApplication) {
        this.paymentApplication = paymentApplication;
    }

    /**
     * 
     * {@linkplain #advancePaymentOrderNo}
     *
     * @return the value of bill_payment_dtl.advance_payment_order_no
     */
    public String getAdvancePaymentOrderNo() {
        return advancePaymentOrderNo;
    }

    /**
     * 
     * {@linkplain #advancePaymentOrderNo}
     * @param advancePaymentOrderNo the value for bill_payment_dtl.advance_payment_order_no
     */
    public void setAdvancePaymentOrderNo(String advancePaymentOrderNo) {
        this.advancePaymentOrderNo = advancePaymentOrderNo;
    }

    /**
     * 
     * {@linkplain #payAmount}
     *
     * @return the value of bill_payment_dtl.pay_amount
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 
     * {@linkplain #payAmount}
     * @param payAmount the value for bill_payment_dtl.pay_amount
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 
     * {@linkplain #moneyDiscount}
     *
     * @return the value of bill_payment_dtl.money_discount
     */
    public BigDecimal getMoneyDiscount() {
        return moneyDiscount;
    }

    /**
     * 
     * {@linkplain #moneyDiscount}
     * @param moneyDiscount the value for bill_payment_dtl.money_discount
     */
    public void setMoneyDiscount(BigDecimal moneyDiscount) {
        this.moneyDiscount = moneyDiscount;
    }

    /**
     * 
     * {@linkplain #discountAmount}
     *
     * @return the value of bill_payment_dtl.discount_amount
     */
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    /**
     * 
     * {@linkplain #discountAmount}
     * @param discountAmount the value for bill_payment_dtl.discount_amount
     */
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * 
     * {@linkplain #fee}
     *
     * @return the value of bill_payment_dtl.fee
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 
     * {@linkplain #fee}
     * @param fee the value for bill_payment_dtl.fee
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 
     * {@linkplain #balanceAmount}
     *
     * @return the value of bill_payment_dtl.balance_amount
     */
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * 
     * {@linkplain #balanceAmount}
     * @param balanceAmount the value for bill_payment_dtl.balance_amount
     */
    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of bill_payment_dtl.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for bill_payment_dtl.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #bankBalanceNo}
     *
     * @return the value of bill_payment_dtl.bank_balance_no
     */
    public String getBankBalanceNo() {
        return bankBalanceNo;
    }

    /**
     * 
     * {@linkplain #bankBalanceNo}
     * @param bankBalanceNo the value for bill_payment_dtl.bank_balance_no
     */
    public void setBankBalanceNo(String bankBalanceNo) {
        this.bankBalanceNo = bankBalanceNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_payment_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_payment_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}