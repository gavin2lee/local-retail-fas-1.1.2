/**
 * title:PayableAccountDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:应付账款表
 * auther:user
 * date:2016-4-12 下午12:15:25
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class PayableAccountDto {
	private String buyerNo;
	
	private String buyerName;
	
	private String supplierNo;
	
	private String supplierName;
	
    @JsonSerialize(using = JsonDateSerializer$10.class)  
	private Date billDate ;
	
    @JsonSerialize(using = JsonDateSerializer$10.class)  
	private Date sendDate;
	
	private Integer billType;
	
	private String billTypeName;
	
	private String billNo;
	
	/**
	 * 期初应付账款
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal earlyPayableAmount;
	
	/**
	 * 本期应付金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal currentPayableAmount;
	
	/**
	 * 本期付款
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal currentPayment;
	
	/**
	 * 余额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal balance;

   

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}


	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public BigDecimal getEarlyPayableAmount() {
		return earlyPayableAmount;
	}

	public void setEarlyPayableAmount(BigDecimal earlyPayableAmount) {
		this.earlyPayableAmount = earlyPayableAmount;
	}

	public BigDecimal getCurrentPayableAmount() {
		return currentPayableAmount;
	}

	public void setCurrentPayableAmount(BigDecimal currentPayableAmount) {
		this.currentPayableAmount = currentPayableAmount;
	}

	public BigDecimal getCurrentPayment() {
		return currentPayment;
	}

	public void setCurrentPayment(BigDecimal currentPayment) {
		this.currentPayment = currentPayment;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
