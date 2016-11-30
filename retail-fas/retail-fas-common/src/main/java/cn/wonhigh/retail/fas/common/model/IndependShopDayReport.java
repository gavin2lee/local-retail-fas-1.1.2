package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class IndependShopDayReport extends FasBaseModel implements Comparable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -165273518157564139L;

	private String rowId;//主键
	/**
	* 店铺代码
	*/
	private String shopNo;

	/**
	* 店铺名称
	*/
	private String companyName;
	
	/**
	* 店铺代码
	*/
	private String companyNo;

	/**
	* 店铺名称
	*/
	private String shopName;
	
	/**
	* 销售日期
	*/
	@JsonSerialize(using=JsonDateSerializer$10.class)
	private Date outDate;

	/**
	* 订单编号
	*/
	private String orderNo;

	/**
	 * 支付方式代号
	 */
	private String payCode;

	/**
	 * 支付方式名称
	 */
	private String payName;

//	/**
//	 * 汇总金额
//	 */
//	private BigDecimal groupAmount;
	
	/**
	 * 现金
	 */
	private BigDecimal cashAmount;
	
	/**
	 * 银行卡
	 */
	private BigDecimal creditCardAmount;
	
	/**
	 * 现金券
	 */
	private BigDecimal cashCouponAmount;
	
	/**
	 * 商场券
	 */
	private BigDecimal mallCouponAmount;
	
	/**
	 * IC卡
	 */
	private BigDecimal iCardAmount;
	
	/**
	 * 商场卡
	 */
	private BigDecimal mallCardAmount;
	
	/**
	 * 其他
	 */
	private BigDecimal otherAmount;
	
	/**
	 * 公司折扣券
	 */
	private BigDecimal companyOnSaleAmount;
	
	/**
	 * 预付款
	 */
	private BigDecimal advancePay;
	
	/**
	 * 销售总额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 现金实收金额
	 */
	private BigDecimal cashAmount_;
	
	/**
	 * 实收金额（暂时约定：除现金支付方式外，实收与销售金额相等）
	 */
	private BigDecimal totalAmount_;
	
	/**
	 * 现金差异
	 */
	private BigDecimal cashAmountDiff;
	private BigDecimal cashCouponAmountDiff;
	private BigDecimal creditCardAmountDiff;
	private BigDecimal mallCardAmountDiff;
	private BigDecimal mallCouponAmountDiff;
	private BigDecimal AdvancePayDiff;
	private BigDecimal otherAmountDiff;
	
	/**
	 * 差异合计
	 */
	private BigDecimal totalAmountDiff;
	
	/**
	 * 当天总存现金额
	 */
	private BigDecimal depositAmount;

	/**
	 * 当前存现差异金额
	 */
	private BigDecimal currentDepositCashDefferenceAmount;
	
	/**
	 * 累计存现差异金额
	 */
	private BigDecimal cumulativeDifferenceAmount;
	
	private BigDecimal saleAmount;//销售
	
    private BigDecimal amount;//实收
    
    private BigDecimal poundage;//手续费
	
	public String getShopNo() {
		return shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public Date getOutDate() {
		return outDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getPayCode() {
		return payCode;
	}

	public String getPayName() {
		return payName;
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

	public BigDecimal getMallCouponAmount() {
		return mallCouponAmount;
	}

	public BigDecimal getiCardAmount() {
		return iCardAmount;
	}

	public BigDecimal getMallCardAmount() {
		return mallCardAmount;
	}

	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	public BigDecimal getCompanyOnSaleAmount() {
		return companyOnSaleAmount;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public void setPayName(String payName) {
		this.payName = payName;
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

	public void setMallCouponAmount(BigDecimal mallCouponAmount) {
		this.mallCouponAmount = mallCouponAmount;
	}

	public void setiCardAmount(BigDecimal iCardAmount) {
		this.iCardAmount = iCardAmount;
	}

	public void setMallCardAmount(BigDecimal mallCardAmount) {
		this.mallCardAmount = mallCardAmount;
	}

	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public void setCompanyOnSaleAmount(BigDecimal companyOnSaleAmount) {
		this.companyOnSaleAmount = companyOnSaleAmount;
	}

	public BigDecimal getAdvancePay() {
		return advancePay;
	}

	public void setAdvancePay(BigDecimal advancePay) {
		this.advancePay = advancePay;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return (int) (this.outDate.getTime() - ((IndependShopDayReport)o).getOutDate().getTime());
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public BigDecimal getCumulativeDifferenceAmount() {
		return cumulativeDifferenceAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public void setCumulativeDifferenceAmount(BigDecimal cumulativeDifferenceAmount) {
		this.cumulativeDifferenceAmount = cumulativeDifferenceAmount;
	}

	public BigDecimal getCurrentDepositCashDefferenceAmount() {
		return currentDepositCashDefferenceAmount;
	}

	public void setCurrentDepositCashDefferenceAmount(
			BigDecimal currentDepositCashDefferenceAmount) {
		this.currentDepositCashDefferenceAmount = currentDepositCashDefferenceAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public BigDecimal getCashAmount_() {
		return cashAmount_;
	}

	public void setCashAmount_(BigDecimal cashAmount_) {
		this.cashAmount_ = cashAmount_;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getCashAmountDiff() {
		return cashAmountDiff;
	}

	public void setCashAmountDiff(BigDecimal cashAmountDiff) {
		this.cashAmountDiff = cashAmountDiff;
	}

	public BigDecimal getTotalAmount_() {
		return totalAmount_;
	}

	public void setTotalAmount_(BigDecimal totalAmount_) {
		this.totalAmount_ = totalAmount_;
	}

	public BigDecimal getCashCouponAmountDiff() {
		return cashCouponAmountDiff;
	}

	public void setCashCouponAmountDiff(BigDecimal cashCouponAmountDiff) {
		this.cashCouponAmountDiff = cashCouponAmountDiff;
	}

	public BigDecimal getCreditCardAmountDiff() {
		return creditCardAmountDiff;
	}

	public void setCreditCardAmountDiff(BigDecimal creditCardAmountDiff) {
		this.creditCardAmountDiff = creditCardAmountDiff;
	}

	public BigDecimal getMallCardAmountDiff() {
		return mallCardAmountDiff;
	}

	public void setMallCardAmountDiff(BigDecimal mallCardAmountDiff) {
		this.mallCardAmountDiff = mallCardAmountDiff;
	}

	public BigDecimal getAdvancePayDiff() {
		return AdvancePayDiff;
	}

	public void setAdvancePayDiff(BigDecimal advancePayDiff) {
		AdvancePayDiff = advancePayDiff;
	}

	public BigDecimal getOtherAmountDiff() {
		return otherAmountDiff;
	}

	public void setOtherAmountDiff(BigDecimal otherAmountDiff) {
		this.otherAmountDiff = otherAmountDiff;
	}

	public BigDecimal getTotalAmountDiff() {
		return totalAmountDiff;
	}

	public void setTotalAmountDiff(BigDecimal totalAmountDiff) {
		this.totalAmountDiff = totalAmountDiff;
	}

	public BigDecimal getMallCouponAmountDiff() {
		return mallCouponAmountDiff;
	}

	public void setMallCouponAmountDiff(BigDecimal mallCouponAmountDiff) {
		this.mallCouponAmountDiff = mallCouponAmountDiff;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}
}
