package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-06-06 10:02:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class BillBuyBalanceAdditional implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1180707600474125132L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 关联单据单号
	 */
	private String billNo;

	/**
	 * 币别编号
	 */
	private String currencyCode;

	/**
	 * 币别名称
	 */
	private String currencyName;

	/**
	 * 汇率
	 */
	private BigDecimal exchangeRate;

	/**
	 * 关税率
	 */
	private BigDecimal tariffRate;

	/**
	 * 增值税率
	 */
	private BigDecimal vatRate;

	/**
	 * 采购费用
	 */
	private BigDecimal purchaseFee;

	/**
	 * 本位币编码
	 */
	private String standardCurrencyCode;

	/**
	 * 本位币名称
	 */
	private String standardCurrencyName;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 地区加价
	 */
	private BigDecimal regionPriceGap;

	/**
	 * 地区金额
	 */
	private BigDecimal regionAmount;

	/**
	 * 地区价
	 */
	private BigDecimal regionCost;

	/**
	 * 二级大类
	 */
	private String categorySecondary;

	/**
	 * 三级大类
	 */
	private String categoryTertiary;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	private String itemCode;

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @param id
	 *            the value for bill_buy_balance_additional.id
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_buy_balance_additional.bill_no
	 */
	public String getItemCode() {
		return itemCode;
	}

	private String itemName;

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @param id
	 *            the value for bill_buy_balance_additional.id
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_buy_balance_additional.bill_no
	 */
	public String getItemName() {
		return itemName;
	}

	private String originalBillNo;

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @param id
	 *            the value for bill_buy_balance_additional.id
	 */
	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_buy_balance_additional.bill_no
	 */
	public String getOriginalBillNo() {
		return originalBillNo;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @return the value of bill_buy_balance_additional.id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @param id
	 *            the value for bill_buy_balance_additional.id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_buy_balance_additional.bill_no
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.bill_no
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 
	 * {@linkplain #currencyCode}
	 * 
	 * @return the value of bill_buy_balance_additional.currency_code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 
	 * {@linkplain #currencyCode}
	 * 
	 * @param currencyCode
	 *            the value for bill_buy_balance_additional.currency_code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 
	 * {@linkplain #currencyName}
	 * 
	 * @return the value of bill_buy_balance_additional.currency_name
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * 
	 * {@linkplain #currencyName}
	 * 
	 * @param currencyName
	 *            the value for bill_buy_balance_additional.currency_name
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * 
	 * {@linkplain #exchangeRate}
	 * 
	 * @return the value of bill_buy_balance_additional.exchange_rate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * 
	 * {@linkplain #exchangeRate}
	 * 
	 * @param exchangeRate
	 *            the value for bill_buy_balance_additional.exchange_rate
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 
	 * {@linkplain #tariffRate}
	 * 
	 * @return the value of bill_buy_balance_additional.tariff_rate
	 */
	public BigDecimal getTariffRate() {
		return tariffRate;
	}

	/**
	 * 
	 * {@linkplain #tariffRate}
	 * 
	 * @param tariffRate
	 *            the value for bill_buy_balance_additional.tariff_rate
	 */
	public void setTariffRate(BigDecimal tariffRate) {
		this.tariffRate = tariffRate;
	}

	/**
	 * 
	 * {@linkplain #vatRate}
	 * 
	 * @return the value of bill_buy_balance_additional.vat_rate
	 */
	public BigDecimal getVatRate() {
		return vatRate;
	}

	/**
	 * 
	 * {@linkplain #vatRate}
	 * 
	 * @param vatRate
	 *            the value for bill_buy_balance_additional.vat_rate
	 */
	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	/**
	 * 
	 * {@linkplain #purchaseFee}
	 * 
	 * @return the value of bill_buy_balance_additional.purchase_fee
	 */
	public BigDecimal getPurchaseFee() {
		return purchaseFee;
	}

	/**
	 * 
	 * {@linkplain #purchaseFee}
	 * 
	 * @param purchaseFee
	 *            the value for bill_buy_balance_additional.purchase_fee
	 */
	public void setPurchaseFee(BigDecimal purchaseFee) {
		this.purchaseFee = purchaseFee;
	}

	/**
	 * 
	 * {@linkplain #standardCurrenyCode}
	 * 
	 * @return the value of bill_buy_balance_additional.standard_curreny_code
	 */
	public String getStandardCurrencyCode() {
		return standardCurrencyCode;
	}

	/**
	 * 
	 * {@linkplain #standardCurrenyCode}
	 * 
	 * @param standardCurrenyCode
	 *            the value for
	 *            bill_buy_balance_additional.standard_curreny_code
	 */
	public void setStandardCurrencyCode(String standardCurrencyCode) {
		this.standardCurrencyCode = standardCurrencyCode;
	}

	/**
	 * 
	 * {@linkplain #standardCurrenyName}
	 * 
	 * @return the value of bill_buy_balance_additional.standard_curreny_name
	 */
	public String getStandardCurrencyName() {
		return standardCurrencyName;
	}

	/**
	 * 
	 * {@linkplain #standardCurrenyName}
	 * 
	 * @param standardCurrenyName
	 *            the value for
	 *            bill_buy_balance_additional.standard_curreny_name
	 */
	public void setStandardCurrencyName(String standardCurrencyName) {
		this.standardCurrencyName = standardCurrencyName;
	}

	/**
	 * 
	 * {@linkplain #totalAmount}
	 * 
	 * @return the value of bill_buy_balance_additional.total_amount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 
	 * {@linkplain #totalAmount}
	 * 
	 * @param totalAmount
	 *            the value for bill_buy_balance_additional.total_amount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 
	 * {@linkplain #regionPriceGap}
	 * 
	 * @return the value of bill_buy_balance_additional.region_price_gap
	 */
	public BigDecimal getRegionPriceGap() {
		return regionPriceGap;
	}

	/**
	 * 
	 * {@linkplain #regionPriceGap}
	 * 
	 * @param regionPriceGap
	 *            the value for bill_buy_balance_additional.region_price_gap
	 */
	public void setRegionPriceGap(BigDecimal regionPriceGap) {
		this.regionPriceGap = regionPriceGap;
	}

	/**
	 * 
	 * {@linkplain #regionAmount}
	 * 
	 * @return the value of bill_buy_balance_additional.region_amount
	 */
	public BigDecimal getRegionAmount() {
		return regionAmount;
	}

	/**
	 * 
	 * {@linkplain #regionAmount}
	 * 
	 * @param regionAmount
	 *            the value for bill_buy_balance_additional.region_amount
	 */
	public void setRegionAmount(BigDecimal regionAmount) {
		this.regionAmount = regionAmount;
	}

	/**
	 * 
	 * {@linkplain #regionCost}
	 * 
	 * @return the value of bill_buy_balance_additional.region_cost
	 */
	public BigDecimal getRegionCost() {
		return regionCost;
	}

	/**
	 * 
	 * {@linkplain #regionCost}
	 * 
	 * @param regionCost
	 *            the value for bill_buy_balance_additional.region_cost
	 */
	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	/**
	 * 
	 * {@linkplain #categorySecondary}
	 * 
	 * @return the value of bill_buy_balance_additional.category_secondary
	 */
	public String getCategorySecondary() {
		return categorySecondary;
	}

	/**
	 * 
	 * {@linkplain #categorySecondary}
	 * 
	 * @param categorySecondary
	 *            the value for bill_buy_balance_additional.category_secondary
	 */
	public void setCategorySecondary(String categorySecondary) {
		this.categorySecondary = categorySecondary;
	}

	/**
	 * 
	 * {@linkplain #categoryTertiary}
	 * 
	 * @return the value of bill_buy_balance_additional.category_tertiary
	 */
	public String getCategoryTertiary() {
		return categoryTertiary;
	}

	/**
	 * 
	 * {@linkplain #categoryTertiary}
	 * 
	 * @param categoryTertiary
	 *            the value for bill_buy_balance_additional.category_tertiary
	 */
	public void setCategoryTertiary(String categoryTertiary) {
		this.categoryTertiary = categoryTertiary;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @return the value of bill_buy_balance_additional.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @param createTime
	 *            the value for bill_buy_balance_additional.create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @return the value of bill_buy_balance_additional.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @param updateUser
	 *            the value for bill_buy_balance_additional.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @return the value of bill_buy_balance_additional.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @param updateTime
	 *            the value for bill_buy_balance_additional.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}