package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RegisterInvoiceModel implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8527256118550174888L;
	
	private int id;

	private String salerNo;
	
	private String salerName;
	
	private String buyerNo;
	
	private String buyerName;
	
	private String billNo;
	
	private BigDecimal amount;
	
	private Date billDate;

	public int getId() {
		return id;
	}

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

	public void setId(int id) {
		this.id = id;
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
	
}
