package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 客户保证金预收款预警
 * @author admin
 * @date  2014-09-23 11:02:19
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
public class WholesalePrepayWarn extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 4305180914380180803L;

	/**
     * 结算主体编码
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 保证金金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal marginAmount;

    /**
     * 已收保证金金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal recedMarginAmount;

    /**
     * 保证金是否足额（0 = 是 1 = 否）
     */
    private Integer marginFull;

    /**
     * 预收款
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal prePayment;

    /**
     * 预收订单号
     */
    private String preOrderNo;

    /**
     * 订单金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal orderAmount;

    /**
     * 出库金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal sendOutAmount;

    /**
     * 订货预收是否足额（0 = 是 1 = 否）
     */
    private Integer preOrderFull;

    /**
     * 发货预收是否足额（0 = 是 1 = 否）
     */
    private Integer preSendOutFull;

    /**
     * 预收款盈余
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal prePaymentProfit;

    /**
     * 已冲销金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal reversalAmount;
    
    /**
     * 可冲销金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal reversalOverAmount;

    /**
     * 预收款余额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal prePaymentOver;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of wholesale_prepay_warn.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for wholesale_prepay_warn.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of wholesale_prepay_warn.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for wholesale_prepay_warn.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of wholesale_prepay_warn.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for wholesale_prepay_warn.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of wholesale_prepay_warn.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for wholesale_prepay_warn.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     *
     * @return the value of wholesale_prepay_warn.margin_amount
     */
    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     * @param marginAmount the value for wholesale_prepay_warn.margin_amount
     */
    public void setMarginAmount(BigDecimal marginAmount) {
        this.marginAmount = marginAmount;
    }

    /**
     * 
     * {@linkplain #recedMarginAmount}
     *
     * @return the value of wholesale_prepay_warn.reced_margin_amount
     */
    public BigDecimal getRecedMarginAmount() {
        return recedMarginAmount;
    }

    /**
     * 
     * {@linkplain #recedMarginAmount}
     * @param recedMarginAmount the value for wholesale_prepay_warn.reced_margin_amount
     */
    public void setRecedMarginAmount(BigDecimal recedMarginAmount) {
        this.recedMarginAmount = recedMarginAmount;
    }

    /**
     * 
     * {@linkplain #marginFull}
     *
     * @return the value of wholesale_prepay_warn.margin_full
     */
    public Integer getMarginFull() {
        return marginFull;
    }

    /**
     * 
     * {@linkplain #marginFull}
     * @param marginFull the value for wholesale_prepay_warn.margin_full
     */
    public void setMarginFull(Integer marginFull) {
        this.marginFull = marginFull;
    }

    /**
     * 
     * {@linkplain #prePayment}
     *
     * @return the value of wholesale_prepay_warn.pre_payment
     */
    public BigDecimal getPrePayment() {
        return prePayment;
    }

    /**
     * 
     * {@linkplain #prePayment}
     * @param prePayment the value for wholesale_prepay_warn.pre_payment
     */
    public void setPrePayment(BigDecimal prePayment) {
        this.prePayment = prePayment;
    }

    /**
     * 
     * {@linkplain #preOrderNo}
     *
     * @return the value of wholesale_prepay_warn.pre_order_no
     */
    public String getPreOrderNo() {
        return preOrderNo;
    }

    /**
     * 
     * {@linkplain #preOrderNo}
     * @param preOrderNo the value for wholesale_prepay_warn.pre_order_no
     */
    public void setPreOrderNo(String preOrderNo) {
        this.preOrderNo = preOrderNo;
    }

    /**
     * 
     * {@linkplain #orderAmount}
     *
     * @return the value of wholesale_prepay_warn.order_amount
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 
     * {@linkplain #orderAmount}
     * @param orderAmount the value for wholesale_prepay_warn.order_amount
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 
     * {@linkplain #sendOutAmount}
     *
     * @return the value of wholesale_prepay_warn.send_out_amount
     */
    public BigDecimal getSendOutAmount() {
        return sendOutAmount;
    }

    /**
     * 
     * {@linkplain #sendOutAmount}
     * @param sendOutAmount the value for wholesale_prepay_warn.send_out_amount
     */
    public void setSendOutAmount(BigDecimal sendOutAmount) {
        this.sendOutAmount = sendOutAmount;
    }

    /**
     * 
     * {@linkplain #preOrderFull}
     *
     * @return the value of wholesale_prepay_warn.pre_order_full
     */
    public Integer getPreOrderFull() {
        return preOrderFull;
    }

    /**
     * 
     * {@linkplain #preOrderFull}
     * @param preOrderFull the value for wholesale_prepay_warn.pre_order_full
     */
    public void setPreOrderFull(Integer preOrderFull) {
        this.preOrderFull = preOrderFull;
    }

    /**
     * 
     * {@linkplain #preSendOutFull}
     *
     * @return the value of wholesale_prepay_warn.pre_send_out_full
     */
    public Integer getPreSendOutFull() {
        return preSendOutFull;
    }

    /**
     * 
     * {@linkplain #preSendOutFull}
     * @param preSendOutFull the value for wholesale_prepay_warn.pre_send_out_full
     */
    public void setPreSendOutFull(Integer preSendOutFull) {
        this.preSendOutFull = preSendOutFull;
    }

    /**
     * 
     * {@linkplain #prePaymentProfit}
     *
     * @return the value of wholesale_prepay_warn.pre_payment_profit
     */
    public BigDecimal getPrePaymentProfit() {
        return prePaymentProfit;
    }

    /**
     * 
     * {@linkplain #prePaymentProfit}
     * @param prePaymentProfit the value for wholesale_prepay_warn.pre_payment_profit
     */
    public void setPrePaymentProfit(BigDecimal prePaymentProfit) {
        this.prePaymentProfit = prePaymentProfit;
    }

    /**
     * 
     * {@linkplain #reversalAmount}
     *
     * @return the value of wholesale_prepay_warn.reversal_amount
     */
    public BigDecimal getReversalAmount() {
        return reversalAmount;
    }

    /**
     * 
     * {@linkplain #reversalAmount}
     * @param reversalAmount the value for wholesale_prepay_warn.reversal_amount
     */
    public void setReversalAmount(BigDecimal reversalAmount) {
        this.reversalAmount = reversalAmount;
    }

    public BigDecimal getReversalOverAmount() {
		return reversalOverAmount;
	}

	public void setReversalOverAmount(BigDecimal reversalOverAmount) {
		this.reversalOverAmount = reversalOverAmount;
	}

	/**
     * 
     * {@linkplain #prePaymentOver}
     *
     * @return the value of wholesale_prepay_warn.pre_payment_over
     */
    public BigDecimal getPrePaymentOver() {
        return prePaymentOver;
    }

    /**
     * 
     * {@linkplain #prePaymentOver}
     * @param prePaymentOver the value for wholesale_prepay_warn.pre_payment_over
     */
    public void setPrePaymentOver(BigDecimal prePaymentOver) {
        this.prePaymentOver = prePaymentOver;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of wholesale_prepay_warn.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for wholesale_prepay_warn.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}