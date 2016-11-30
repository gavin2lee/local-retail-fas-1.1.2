package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegisterInvoiceDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5107894337194071767L;

	private String salerNo;
	
	private String salerName;
	
	private String buyerNo;
	
	private String buyerName;
	
	private String billNo;
	
	private BigDecimal amount;
	
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date billDate;

	public String getSalerNo() {
		return salerNo;
	}

	public String getSalerName() {
		return salerName;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public String getBillNo() {
		return billNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	@Override
	public String toString() {
		return "RegisterInvoiceDto [salerNo=" + salerNo + ", salerName="
				+ salerName + ", buyerNo=" + buyerNo + ", buyerName="
				+ buyerName + ", billNo=" + billNo + ", amount=" + amount
				+ ", billDate=" + billDate + "]";
	}

}
