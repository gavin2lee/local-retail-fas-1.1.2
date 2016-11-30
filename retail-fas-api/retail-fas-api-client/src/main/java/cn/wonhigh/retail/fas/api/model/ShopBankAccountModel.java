package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;

public class ShopBankAccountModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public String getCreditCardAccount() {
		return creditCardAccount;
	}

	public String getDepositAccount() {
		return depositAccount;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public void setCreditCardAccount(String creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
}
