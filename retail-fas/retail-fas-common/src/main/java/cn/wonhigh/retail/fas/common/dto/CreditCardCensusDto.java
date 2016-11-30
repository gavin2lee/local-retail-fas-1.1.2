package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

public class CreditCardCensusDto {
	
	private String terminalNumber;
	
	private String cardNumber;
	
	private int times;
	
	private String companyName;
	
	private String shopNo;
	
	private String shopName;
	
	private BigDecimal totalAmount;

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public int getTimes() {
		return times;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getShopNo() {
		return shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
