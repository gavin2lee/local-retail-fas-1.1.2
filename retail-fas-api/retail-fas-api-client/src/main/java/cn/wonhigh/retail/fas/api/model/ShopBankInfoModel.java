package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;

public class ShopBankInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -462279373949559927L;
	
	/**
	 * 店铺编号
	 */
	private String shopNo;
	
	/**
	 * 终端号
	 */
	private String terminalNo;
	
	/**
	 * 卡号
	 */
	private String cardNo;

	public String getShopNo() {
		return shopNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
