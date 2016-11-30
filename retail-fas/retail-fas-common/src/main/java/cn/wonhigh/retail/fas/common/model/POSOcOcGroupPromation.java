package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * TODO: 销售订单按活动方式汇总Dto
 * 
 * @author zhang.lh
 * @date 2014-11-25 下午4:45:11
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */
public class POSOcOcGroupPromation implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7958423357912925355L;
	//门店NO
 	private String shopNo;
   	//门店名称		 
 	private String shopName;
  	//活动编码
 	private String proNo;
   	//活动名称		 
 	private String proName;
  	//销售金额		 
 	private BigDecimal amount;
  	//扣率名称			 
 	private String discountName;	
	//扣率			 
 	private BigDecimal discount;
  	//扣费			 
 	private BigDecimal discountAmount;
 	
 	private String discountType;
 	
 	private String launchType;
 	
 	 @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date startDate;
 	
 	 @JsonSerialize(using = JsonDateSerializer$10.class)
     private Date endDate;
 	 
 	private String  rateName;
 	
 	private String discountCode;
 	
 	/**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌中文名称
     */
    private String brandName;
    
    /**
     * 结算码
     */
    private String billingCode;
	/**
	 * @return the proNo
	 */
	public String getProNo() {
		return proNo;
	}
	/**
	 * @param proNo the proNo to set
	 */
	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	/**
	 * @return the proName
	 */
	public String getProName() {
		return proName;
	}
	/**
	 * @param proName the proName to set
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}
 
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @return the discountName
	 */
	public String getDiscountName() {
		return discountName;
	}
	/**
	 * @param discountName the discountName to set
	 */
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	/**
	 * @return the shopNo
	 */
	public String getShopNo() {
		return shopNo;
	}
	/**
	 * @param shopNo the shopNo to set
	 */
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getLaunchType() {
		return launchType;
	}
	public void setLaunchType(String launchType) {
		this.launchType = launchType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBillingCode() {
		return billingCode;
	}
	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}
  	
}
