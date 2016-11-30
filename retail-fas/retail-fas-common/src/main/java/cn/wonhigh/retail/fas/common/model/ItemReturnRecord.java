package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


public class ItemReturnRecord extends FasBaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9091590768845443674L;
	
	/**
	 * 店铺编号
	 */
	private String shopNo;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 商场编号
	 */
	private String mallNo;
	/**
	 * 商场名称
	 */
	private String mallName;
	/**
	 * 品牌部编号
	 */
	private String brandUnitNo;
	/**
	 * 品牌部名称
	 */
	private String brandUnitName;
	/**
	 * 原单号
	 */
	private String oldOrderNo;
	/**
	 * 消费日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date oldOutDate;
	/**
	 * 退款日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;
	/**
	 * 客户账户
	 */
	private String creditCardAccount;
	/**
	 * 全部/部分
	 */
	private String fullOpen;
	/**
	 * 原单总金额
	 */
	private BigDecimal oldAllAmount;
	/**
	 * 总退款金额
	 */
	private BigDecimal allAmount;
	/**
	 * 退款金额
	 */
	private BigDecimal amount;
	/**
	 * 支付方式编码
	 */
	private String payCode;
	/**
	 * 支付方式
	 */
	private String payName;
	/**
	 * 实际退款金额
	 */
	private BigDecimal actualReturnAmount;
	/**
	 * 实际退款日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date actualReturnTime;
	
	private Integer total;//总记录数
	
	
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
	public String getMallNo() {
		return mallNo;
	}
	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}
	public String getMallName() {
		return mallName;
	}
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}
	public String getBrandUnitNo() {
		return brandUnitNo;
	}
	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}
	public String getBrandUnitName() {
		return brandUnitName;
	}
	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}
	public Date getOldOutDate() {
		return oldOutDate;
	}
	public void setOldOutDate(Date oldOutDate) {
		this.oldOutDate = oldOutDate;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getFullOpen() {
		return fullOpen;
	}
	public void setFullOpen(String fullOpen) {
		this.fullOpen = fullOpen;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
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
	public BigDecimal getActualReturnAmount() {
		return actualReturnAmount;
	}
	public void setActualReturnAmount(BigDecimal actualReturnAmount) {
		this.actualReturnAmount = actualReturnAmount;
	}
	public Date getActualReturnTime() {
		return actualReturnTime;
	}
	public void setActualReturnTime(Date actualReturnTime) {
		this.actualReturnTime = actualReturnTime;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getOldOrderNo() {
		return oldOrderNo;
	}
	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	public BigDecimal getOldAllAmount() {
		return oldAllAmount;
	}
	public void setOldAllAmount(BigDecimal oldAllAmount) {
		this.oldAllAmount = oldAllAmount;
	}
	public String getCreditCardAccount() {
		return creditCardAccount;
	}
	public void setCreditCardAccount(String creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}
	
	
}
