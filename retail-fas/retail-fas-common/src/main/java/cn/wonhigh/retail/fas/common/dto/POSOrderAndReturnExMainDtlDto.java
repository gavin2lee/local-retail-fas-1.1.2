package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TODO: 销售明细数据
 * 
 * @author zhang.lh
 * @date 2014-12-2 下午6:18:52
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
@ExportFormat(className=AbstractExportFormat.class)
public class POSOrderAndReturnExMainDtlDto implements Serializable{
 	private static final long serialVersionUID = -3969021101271924735L;
  	//明细表中ID
	private String orderDtlId;
	/*//商场编码
	private String mallNo;*/
	//店铺代码
	private String shopName;
	//店铺名称
	private String shopNo;
	/*//结算公司名称
	private String companyName;*/
	//单据日期
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date outDate;
	//单据编号
	private String orderNo;
	//商品编码
	private String itemCode;
	//商品名称
	private String itemName;
	/*//营业员
	private String assistant;*/
	//主表中的总数量
	private Integer qty;
	//牌价额
	private BigDecimal tagPriceAmount;
	//牌价
	private BigDecimal tagPrice;
	//现价额
	private BigDecimal salePriceAmount;
	//现价
	private BigDecimal salePrice;
	//现价额
	private BigDecimal settleAmount;
	//结算价
	private BigDecimal settlePrice;
	//结算金额
 	private BigDecimal reduceAmount;
 	//减价
	private BigDecimal reducePrice;
	//现金券总金额
	private BigDecimal couponAmount;
	//商品总金额
	private BigDecimal amount;
	//订单总金额
	private BigDecimal allAmount;
	//财务确认标志
	private Integer monthlyFlag;
	//订单业务类型
	private Integer orderBillType;
	//发票号
	private String invoiceNo;
	//发票日期
	private Date invoiceDate;
	//扣率
	private BigDecimal discount;
	//原单号
	private String oldOrderNo;
	//备注
	private String remark;
	//发票金额
	private BigDecimal invoiceAmount;
	//明细中的总数量
	private Integer dtlQty;
 	//品牌
	private String brandName;
	//商品SKU
 	private String skuNo;
 	// 销售类型 
 	private Integer businessType;
 	//折扣价
	private BigDecimal discPrice;
	//折扣额
	private BigDecimal discountRemain;
	//促销优惠单价
	private BigDecimal prefPrice;
	//促销活动编号
	private String proNo;
	//促销活动名称
	private String proName;
	//会员折数
	private BigDecimal vipDiscount;
	//基本积分
	private Integer baseScore;
	//整单分摊积分
	private Integer proScore;
	//商品折数
	  
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal itemDiscount;
	
	private String itemDiscountStr;
	//详细表中的总金额
	private BigDecimal dtlAmount;
	//商品尺码
	private String sizeNo;
	//建档时间
	private Date createTime;
	//建档人
	private String createUser;
	//类别
   	private String categoryNo;
   	//商品编号
  	private String itemNo;
  	//品牌编号
  	private String brandNo;

  	private BigDecimal discountAmount;
	private BigDecimal saleAmount;
	
  	
  	private String  launchType;
	private String  rateCode;
	private String  activityDescribe;
	
	private BigDecimal regionCost;
	private BigDecimal unitCost;
	
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
	public BigDecimal getItemDiscount() {
		return itemDiscount;
	}
	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
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