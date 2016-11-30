package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

public class IndependentShopMonthReport extends FasBaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3442349742827658009L;

	/**
	 * 店铺编码
	 */
	private String shopNo;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	
	/**
	 * 销售月
	 */
	private String saleMonth;
	
	/**
	 * 现金金额
	 */
	private BigDecimal cashAmount;
	
	/**
	 * 刷卡金额
	 */
	private BigDecimal creditCardAmount;
	
	/**
	 * 现金券金额
	 */
	private BigDecimal cashCouponAmount;
	
	/**
	 * 商场卡金额
	 */
	private BigDecimal mallCardAmount;
	
	/**
	 * 商场券金额
	 */
	private BigDecimal mallCouponAmount;
	
	/**
	 * 预付款金额
	 */
	private BigDecimal advancePayAmount;
	
	/**
	 * 其他金额
	 */
	private BigDecimal othersAmount;
	
	/**
	 * 总销售金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 总回扣费金额
	 */
	private BigDecimal feeAmount;
	
	/**
	 * 店铺基础费用金额
	 */
	private BigDecimal basicSpendAmount;
	
	/**
	 * 店铺月实际收益金额 = (totalAmount - (feeAmount+basicSpendAmount))
	 */
	private BigDecimal actualIncomeAmount;

	public String getShopNo() {
		return shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public String getSaleMonth() {
		return saleMonth;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public BigDecimal getCreditCardAmount() {
		return creditCardAmount;
	}

	public BigDecimal getCashCouponAmount() {
		return cashCouponAmount;
	}

	public BigDecimal getMallCardAmount() {
		return mallCardAmount;
	}

	public BigDecimal getMallCouponAmount() {
		return mallCouponAmount;
	}

	public BigDecimal getAdvancePayAmount() {
		return advancePayAmount;
	}

	public BigDecimal getOthersAmount() {
		return othersAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public BigDecimal getBasicSpendAmount() {
		return basicSpendAmount;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setSaleMonth(String saleMonth) {
		this.saleMonth = saleMonth;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public void setCreditCardAmount(BigDecimal creditCardAmount) {
		this.creditCardAmount = creditCardAmount;
	}

	public void setCashCouponAmount(BigDecimal cashCouponAmount) {
		this.cashCouponAmount = cashCouponAmount;
	}

	public void setMallCardAmount(BigDecimal mallCardAmount) {
		this.mallCardAmount = mallCardAmount;
	}

	public void setMallCouponAmount(BigDecimal mallCouponAmount) {
		this.mallCouponAmount = mallCouponAmount;
	}

	public void setAdvancePayAmount(BigDecimal advancePayAmount) {
		this.advancePayAmount = advancePayAmount;
	}

	public void setOthersAmount(BigDecimal othersAmount) {
		this.othersAmount = othersAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public void setBasicSpendAmount(BigDecimal basicSpendAmount) {
		this.basicSpendAmount = basicSpendAmount;
	}

	public BigDecimal getActualIncomeAmount() {
		return actualIncomeAmount;
	}

	public void setActualIncomeAmount(BigDecimal actualIncomeAmount) {
		this.actualIncomeAmount = actualIncomeAmount;
	}
}
