package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * TODO: 支付方式分组
 * 
 * @author zhang.lh
 * @date 2014-12-3 下午4:33:45
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
public class POSOcGroupOrderPayway extends FasBaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2849558182855119260L;

	/**
	* 店铺代码
	*/
	private String shopNo;

	/**
	* 店铺名称
	*/
	private String shopName;
	
	/**
	* 结算公司编码
	*/
	private String companyNo;

	/**
	* 销售日期
	*/
	@JsonSerialize(using = JsonDateSerializer$10.class)
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
	 * 汇总金额
	 */
	private BigDecimal groupAmount;

	/**
	* 支付方式编号 卡号、券号、结算号
	*/
	private String paywayNum;

	/**
	 * 总金额
	 */
	private BigDecimal allAmount;
	
	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public BigDecimal getGroupAmount() {
		return groupAmount;
	}

	public void setGroupAmount(BigDecimal groupAmount) {
		this.groupAmount = groupAmount;
	}

	public String getPaywayNum() {
		return paywayNum;
	}

	public void setPaywayNum(String paywayNum) {
		this.paywayNum = paywayNum;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getCreditCardAmount() {
		return creditCardAmount;
	}

	public void setCreditCardAmount(BigDecimal creditCardAmount) {
		this.creditCardAmount = creditCardAmount;
	}

	public BigDecimal getCashCouponAmount() {
		return cashCouponAmount;
	}

	public void setCashCouponAmount(BigDecimal cashCouponAmount) {
		this.cashCouponAmount = cashCouponAmount;
	}

	public BigDecimal getMallCouponAmount() {
		return mallCouponAmount;
	}

	public void setMallCouponAmount(BigDecimal mallCouponAmount) {
		this.mallCouponAmount = mallCouponAmount;
	}

	public BigDecimal getiCardAmount() {
		return iCardAmount;
	}

	public void setiCardAmount(BigDecimal iCardAmount) {
		this.iCardAmount = iCardAmount;
	}

	public BigDecimal getMallCardAmount() {
		return mallCardAmount;
	}

	public void setMallCardAmount(BigDecimal mallCardAmount) {
		this.mallCardAmount = mallCardAmount;
	}

	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public BigDecimal getCompanyOnSaleAmount() {
		return companyOnSaleAmount;
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
	
}