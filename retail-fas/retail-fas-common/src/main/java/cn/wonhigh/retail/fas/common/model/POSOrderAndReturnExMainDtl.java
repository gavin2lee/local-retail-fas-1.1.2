package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author chen.sn
 * @date  2014-09-10 10:34:23
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class POSOrderAndReturnExMainDtl extends FasBaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -834938552267093060L;
	private String orderDtlId;
	private String shopName;
	private String shopNo;
	private Date outDate;
	private String orderNo;
	private String itemCode;
	private String itemName;
	private Integer qty;
	private BigDecimal tagPriceAmount;
	private BigDecimal tagPrice;
	private BigDecimal salePriceAmount;
	private BigDecimal salePrice;
	private BigDecimal settleAmount;
	private BigDecimal settlePrice;
	private BigDecimal reduceAmount;
	private BigDecimal reducePrice;
	private BigDecimal couponAmount;
    private BigDecimal remainAmount;
	private BigDecimal amount;
	private BigDecimal allAmount;
	private Integer monthlyFlag;
	private Integer orderBillType;
	private String invoiceNo;
	private Date invoiceDate;
	private BigDecimal discount;
	private String oldOrderNo;
	private String remark;
	private Integer dtlQty;
	//发票金额
	private BigDecimal invoiceAmount;
 
	private String brandName;
 	private String skuNo;
 	private Integer businessType;
	private BigDecimal discPrice;
	private BigDecimal discountRemain;
	private BigDecimal prefPrice;
	private String proNo;
	private String proName;
	private BigDecimal vipDiscount;
	private Integer baseScore;
	private Integer proScore;
	private BigDecimal itemDiscount;
	private String itemDiscountStr;
	private BigDecimal dtlAmount;
	private String sizeNo;
    /** 外卡折让金额 */
	private BigDecimal rebateAmount;
	private Date createTime;
	private String createUser;
	private String categoryNo;
	private String itemNo;
	private String brandNo;
  
	private String  launchType;
	private String  rateCode;
	private String  activityDescribe;
	
	private BigDecimal regionCost;
	private BigDecimal unitCost;
	
	private BigDecimal discountAmount;
	private BigDecimal saleAmount;
	 private String billingCode;
	 private String categoryName;
	 private String discountCode;
	 
	 private String  assistantNo;
	 private String  assistantName; 
	 
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date proStartDate;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date proEndDate;
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getTagPriceAmount() {
		return tagPriceAmount;
	}

	public void setTagPriceAmount(BigDecimal tagPriceAmount) {
		this.tagPriceAmount = tagPriceAmount;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getSalePriceAmount() {
		return salePriceAmount;
	}

	public void setSalePriceAmount(BigDecimal salePriceAmount) {
		this.salePriceAmount = salePriceAmount;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public BigDecimal getReduceAmount() {
		return reduceAmount;
	}

	public void setReduceAmount(BigDecimal reduceAmount) {
		this.reduceAmount = reduceAmount;
	}

	public BigDecimal getReducePrice() {
		return reducePrice;
	}

	public void setReducePrice(BigDecimal reducePrice) {
		this.reducePrice = reducePrice;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
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

	public Integer getMonthlyFlag() {
		return monthlyFlag;
	}

	public void setMonthlyFlag(Integer monthlyFlag) {
		this.monthlyFlag = monthlyFlag;
	}

	public Integer getOrderBillType() {
		return orderBillType;
	}

	public void setOrderBillType(Integer orderBillType) {
		this.orderBillType = orderBillType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getOrderDtlId() {
		return orderDtlId;
	}

	public void setOrderDtlId(String orderDtlId) {
		this.orderDtlId = orderDtlId;
	}

	/**
	 * @return the dtlQty
	 */
	public Integer getDtlQty() {
		return dtlQty;
	}

	/**
	 * @param dtlQty the dtlQty to set
	 */
	public void setDtlQty(Integer dtlQty) {
		this.dtlQty = dtlQty;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public BigDecimal getDiscPrice() {
		return discPrice;
	}

	public void setDiscPrice(BigDecimal discPrice) {
		this.discPrice = discPrice;
	}

	public BigDecimal getDiscountRemain() {
		return discountRemain;
	}

	public void setDiscountRemain(BigDecimal discountRemain) {
		this.discountRemain = discountRemain;
	}

	public BigDecimal getPrefPrice() {
		return prefPrice;
	}

	public void setPrefPrice(BigDecimal prefPrice) {
		this.prefPrice = prefPrice;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public BigDecimal getVipDiscount() {
		return vipDiscount;
	}

	public void setVipDiscount(BigDecimal vipDiscount) {
		this.vipDiscount = vipDiscount;
	}

	public Integer getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(Integer baseScore) {
		this.baseScore = baseScore;
	}

	public Integer getProScore() {
		return proScore;
	}

	public void setProScore(Integer proScore) {
		this.proScore = proScore;
	}

	public BigDecimal getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public BigDecimal getDtlAmount() {
		return dtlAmount;
	}

	public void setDtlAmount(BigDecimal dtlAmount) {
		this.dtlAmount = dtlAmount;
	}
 	/**
	 * @return the sizeNo
	 */
	public String getSizeNo() {
		return sizeNo;
	}
 	/**
	 * @param sizeNo the sizeNo to set
	 */
	public void setSizeNo(String sizeNo) {
		this.sizeNo = sizeNo;
	}
 
	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the categoryNo
	 */
	public String getCategoryNo() {
		return categoryNo;
	}

	/**
	 * @param categoryNo the categoryNo to set
	 */
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * @return the itemNo
	 */
	public String getItemNo() {
		return itemNo;
	}

	/**
	 * @param itemNo the itemNo to set
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @return the brandNo
	 */
	public String getBrandNo() {
		return brandNo;
	}

	/**
	 * @param brandNo the brandNo to set
	 */
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getLaunchType() {
		return launchType;
	}

	public void setLaunchType(String launchType) {
		this.launchType = launchType;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getActivityDescribe() {
		return activityDescribe;
	}

	public void setActivityDescribe(String activityDescribe) {
		this.activityDescribe = activityDescribe;
	}

	public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public Date getProStartDate() {
		return proStartDate;
	}

	public void setProStartDate(Date proStartDate) {
		this.proStartDate = proStartDate;
	}

	public Date getProEndDate() {
		return proEndDate;
	}

	public void setProEndDate(Date proEndDate) {
		this.proEndDate = proEndDate;
	}

	public String getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getItemDiscountStr() {
		return itemDiscountStr;
	}

	public void setItemDiscountStr(String itemDiscountStr) {
		this.itemDiscountStr = itemDiscountStr;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getAssistantNo() {
		return assistantNo;
	}

	public void setAssistantNo(String assistantNo) {
		this.assistantNo = assistantNo;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	
}