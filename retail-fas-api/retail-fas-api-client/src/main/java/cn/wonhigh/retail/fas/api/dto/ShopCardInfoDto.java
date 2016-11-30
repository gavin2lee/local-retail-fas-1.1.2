package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;

public class ShopCardInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8307369557724130431L;
	/**
	 * 店铺编号
	 */
	private String shopNo;
	
	/**
	 * 终端号
	 */
	private String terminalNo;
	
	/**
	 * 刷卡账号
	 */
	private String creditCardAccount;
	
	/**
	 * 存现账号
	 */
	private String depositAccount;

	public String getShopNo() {
		return shopNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getCreditCardAccount() {
		return creditCardAccount;
	}

	public String getDepositAccount() {
		return depositAccount;
	}

	public void setCreditCardAccount(String creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
}
