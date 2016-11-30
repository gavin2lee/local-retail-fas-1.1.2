package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

public class RegionCostCaculatorDto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 420244435162847789L;

	/**
	 * buy表Id
	 */
	private String id;
	/**
	 * 关联单据单号
	 */
	private String billNo;

	/**
	 * 关联单据单号
	 */
	private String originalBillNo;

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
	 * 本位币编码
	 */
	private String standardCurrencyCode;

	/**
	 * 折扣
	 */
	private BigDecimal contractDiscount;
	/**
	 * 本位币名称
	 */

	private String currencyName;

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	private String standardCurrencyName;

	public String getStandardCurrencyName() {
		return standardCurrencyName;
	}

	public void setStandardCurrencyName(String standardCurrencyName) {
		this.standardCurrencyName = standardCurrencyName;
	}

	/**
	 * 总金额
	 */
	private BigDecimal standardTotalAmount;

	public BigDecimal getStandardTotalAmount() {
		return standardTotalAmount;
	}

	public void setStandardTotalAmount(BigDecimal standardTotalAmount) {
		this.standardTotalAmount = standardTotalAmount;
	}

	/**
	 * 本位币总部价
	 */
	private BigDecimal standardBasePrice;

	/**
	 * 本位币采购价
	 */
	private BigDecimal standardPurchasePrice;

	/**
	 * 地区金额
	 */
	private BigDecimal regionAmount;

	public String getStandardCurrencyCode() {
		return standardCurrencyCode;
	}

	public void setStandardCurrencyCode(String standardCurrencyCode) {
		this.standardCurrencyCode = standardCurrencyCode;
	}

	public BigDecimal getStandardBasePrice() {
		return standardBasePrice;
	}

	public void setStandardBasePrice(BigDecimal standardBasePrice) {
		this.standardBasePrice = standardBasePrice;
	}

	public BigDecimal getStandardPurchasePrice() {
		return standardPurchasePrice;
	}

	public void setStandardPurchasePrice(BigDecimal standardPurchasePrice) {
		this.standardPurchasePrice = standardPurchasePrice;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	/**
	 * 地区价
	 */
	private BigDecimal regionCost;

	private String currencyCode;

	private String itemNo;

	private String brandNo;

	private String categoryNo;

	private String buyerNo;

	private String salerNo;

	private BigDecimal purchasePrice;

	private Integer qty;

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public BigDecimal getContractDiscount() {
		return contractDiscount;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setContractDiscount(BigDecimal contractDiscount) {
		this.contractDiscount = contractDiscount;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public String getOriginalBillNo() {
		return originalBillNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public String getBuyerNo() {
		return buyerNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public String getSalerNo() {
		return salerNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public String getCategoryNo() {
		return categoryNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_buy_balance_additional.brandNo
	 */
	public String getBrandNo() {
		return brandNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.brandNo
	 */
	public void setbrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_buy_balance_additional.bill_no
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.bill_no
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_buy_balance_additional.bill_no
	 */
	public String getItemNo() {
		return itemNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @param billNo
	 *            the value for bill_buy_balance_additional.bill_no
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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
	 * {@linkplain #standardCurrenyCode}
	 * 
	 * @return the value of bill_buy_balance_additional.standard_curreny_code
	 */
	public String getStandardCurrenyCode() {
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
	public void setStandardCurrenyCode(String standardCurrencyCode) {
		this.standardCurrencyCode = standardCurrencyCode;
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
}