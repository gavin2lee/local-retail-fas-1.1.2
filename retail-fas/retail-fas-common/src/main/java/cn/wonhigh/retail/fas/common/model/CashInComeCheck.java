package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class CashInComeCheck extends FasBaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6249856916105344533L;

	/**
	 * 店铺编码
	 */
	private String shopNo;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 存现账号
	 */
	private String depositAccount;
	
	/**
	 * 存现日期
	 */
	@JsonSerialize(using=JsonDateSerializer$10.class)
	private Date depositDate;
	
	/**
	 * 存现金额
	 */
	private BigDecimal depositAmount;
	
	/**
	 * 到账金额
	 */
	private BigDecimal inComeAmount;
	
	/**
	 * 到账差异
	 */
	private BigDecimal inComeDiff;

	public String getDepositAccount() {
		return depositAccount;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public BigDecimal getInComeAmount() {
		return inComeAmount;
	}

	public BigDecimal getInComeDiff() {
		return inComeDiff;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public void setInComeAmount(BigDecimal inComeAmount) {
		this.inComeAmount = inComeAmount;
	}

	public void setInComeDiff(BigDecimal inComeDiff) {
		this.inComeDiff = inComeDiff;
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
