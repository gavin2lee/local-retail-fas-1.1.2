package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;

public class CurrencyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670239077681562474L;
	
	private int id;
	
	/**
	 * 币种编码
	 */
	private String currencyCode;
	
	/**
	 * 币种名称
	 */
	private String currencyName;
	
	/**
	 * 币种标识
	 */
	private String currencySymbol;

	public int getId() {
		return id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
}
