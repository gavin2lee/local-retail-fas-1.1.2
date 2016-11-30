package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CashBalance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4630126969736906102L;

	/**
	 * 店铺编码
	 */
	private String shopNo;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 期初金额
	 */
	private BigDecimal startAmount;
	
	/**
	 * 本期销售金额 
	 */
	private BigDecimal currentAmount;
	
	/**
	 * 本期存入金额
	 */
	private BigDecimal currentDepositAmount;
	
	/**
	 * 期末金额
	 */
	private BigDecimal endAmount;
	
	/**
	 * 备用金
	 */
	private BigDecimal prepareCash;

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}

	public BigDecimal getCurrentDepositAmount() {
		return currentDepositAmount;
	}

	public void setCurrentDepositAmount(BigDecimal currentDepositAmount) {
		this.currentDepositAmount = currentDepositAmount;
	}

	public BigDecimal getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	public BigDecimal getPrepareCash() {
		return prepareCash;
	}

	public void setPrepareCash(BigDecimal prepareCash) {
		this.prepareCash = prepareCash;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}
	
}
