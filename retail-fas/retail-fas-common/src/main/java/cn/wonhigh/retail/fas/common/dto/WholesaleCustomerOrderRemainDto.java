package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 批发客户订单余额查询结果属性
 * @author user
 * @date  2016-06-06 18:10:49
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

public class WholesaleCustomerOrderRemainDto extends CustomerOrderRemain{

	private static final long serialVersionUID = -8751312016946234737L;

	 /**
     * 订金比例
     */
    private Integer advanceScale;
    
    /**
     * 应收订金
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal preOrderAmount;
    
    /**
     * 实收货款
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal paidAmount;
    
    /**
     * 客户订单账面余额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal orderRemainingBookAmount;
    
    /**
     * 保证金金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal marginAmount;
    
    /**
     * 保证金余额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal marginRemainderAmount;
    
    /**
     * 信贷额度
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal creditAmount;
    
    /**
     * 条款编码
     */
    private String termNo;

    /**
     * 条款名称
     */
    private String termName;
    

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Integer getAdvanceScale() {
		return advanceScale;
	}

	public void setAdvanceScale(Integer advanceScale) {
		this.advanceScale = advanceScale;
	}

	public BigDecimal getPreOrderAmount() {
		return preOrderAmount;
	}

	public void setPreOrderAmount(BigDecimal preOrderAmount) {
		this.preOrderAmount = preOrderAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getOrderRemainingBookAmount() {
		return orderRemainingBookAmount;
	}

	public void setOrderRemainingBookAmount(BigDecimal orderRemainingBookAmount) {
		this.orderRemainingBookAmount = orderRemainingBookAmount;
	}

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	public BigDecimal getMarginRemainderAmount() {
		return marginRemainderAmount;
	}

	public void setMarginRemainderAmount(BigDecimal marginRemainderAmount) {
		this.marginRemainderAmount = marginRemainderAmount;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}
    

}
