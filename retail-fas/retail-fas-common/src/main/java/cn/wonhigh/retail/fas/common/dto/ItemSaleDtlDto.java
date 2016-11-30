package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

@ExportFormat(className=AbstractExportFormat.class)
public class ItemSaleDtlDto extends FasBaseModel {
	
	private static final long serialVersionUID = 786724356194399721L;

	/**
	 * mainDto.getOrderNo();
			mainDto.getShopName();
			mainDto.getShopNo();

			List<OrderDtlDto> orderDtls = mainDto.getOrderDtls();
			for(OrderDtlDto orderDtlDto : orderDtls){
				
				orderDtlDto.getItemCode();
				orderDtlDto.getItemName();
				orderDtlDto.getTagPrice();
				orderDtlDto.getSalePrice();
				orderDtlDto.getQty();
				orderDtlDto.getSettlePrice();
				orderDtlDto.getAmount();
				
			}
	 */
	
	private int total;
	private String shopNo;
	
	private String shopName;
	
	private String itemName;
	
	private String assistantName;
	
	 
	/**
	 * 牌价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;
	
	/**
	 * 牌价额 = (tagPrice * qty)
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPriceAmount;
	
	/**
	 * 现价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal salePrice;
	
	/**
	 * 现价额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal salePriceAmount;
	
	/**
	 * 数量
	 */
	private Integer qty;
	
	/**
	 * 商品结算价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal settlePrice;
	
	/**
	 * 商品总金额,(结算价-减价)*数量   (整单结算金额)
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal amount;
	
	/**
	 * 明细结算总金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal dealAmount;
	
	/**
	 * 销售日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;
	
	/**
	 * 订单类型 0-正常销售  1-换货 2-退货
	 */
	private String orderBillType;
	
	/**
	 * 原订单号
	 */
	private String oldOrderNo;
	
	private String remark;
	 private String discountCode;
	
	/**
     * 订单编号
     */
    private String orderNo;

    /**
     * 销售类型,0-正常 1-换货 2-退货 默认为0
     */
    private Integer orderType;

    /**
     * 结算公司编码
     */
    private String companyNo;
    
    private String companyName;

    /**
     * 经营区域编号
     */
    private String zoneNo;
    
    private String zoneName;

    /**
     * 经营城市编号
     */
    private String bizCityNo;

    private String bizName;
    
    /**
     * 商业集团编码
     */
    private String bsgroupsNo;
    
    private String bsgroupsName;
    
    /**
     * 片区编码
     */
    private String regionNo;

    /**
     * 商场编码
     */
    private String mallNo;
    
    private String mallName;

//    public String getShopName() {
//		return shopName;
//	}
//
//	public void setShopName(String shopName) {
//		this.shopName = shopName;
//	}
	/**
     * 店铺编码
     */
//    private String shopNo;
    
//    private String shopName;

    /**
     * (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 销售日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date saleDate;
    
    /**
     * 总部成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal headquarterCost;
    
    /**
     * 地区成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionCost;
    
    /**
     * 单位成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal unitCost;  
       
  	private BigDecimal discountAmount;
	
    private String  launchType;
    private String  launchTypeName;
	private String  rateCode;
	private String  activityDescribe;
    private String billingCode;
    private String categoryName;
    
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date proStartDate;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date proEndDate;
	
	
	private String brandName;
	private String brandUnitNo;
	private String brandUnitName;
	private String organNo;
	private String organName;
	

    public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	private String discountName;
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
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date startDate;
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getBizCityNo() {
		return bizCityNo;
	}

	public void setBizCityNo(String bizCityNo) {
		this.bizCityNo = bizCityNo;
	}

	public String getBsgroupsNo() {
		return bsgroupsNo;
	}

	public void setBsgroupsNo(String bsgroupsNo) {
		this.bsgroupsNo = bsgroupsNo;
	}

	public String getRegionNo() {
		return regionNo;
	}

	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
	}

	public String getMallNo() {
		return mallNo;
	}

	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	public String getRetailType() {
		return retailType;
	}

	public void setRetailType(String retailType) {
		this.retailType = retailType;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getAssistantNo() {
		return assistantNo;
	}

	public void setAssistantNo(String assistantNo) {
		this.assistantNo = assistantNo;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSizeNo() {
		return sizeNo;
	}

	public void setSizeNo(String sizeNo) {
		this.sizeNo = sizeNo;
	}

	public Integer getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(Integer itemFlag) {
		this.itemFlag = itemFlag;
	}

	public BigDecimal getDiscPrice() {
		return discPrice;
	}

	public void setDiscPrice(BigDecimal discPrice) {
		this.discPrice = discPrice;
	}

	public BigDecimal getReducePrice() {
		return reducePrice;
	}

	public void setReducePrice(BigDecimal reducePrice) {
		this.reducePrice = reducePrice;
	}

	public BigDecimal getTagAmount() {
		return tagAmount;
	}

	public void setTagAmount(BigDecimal tagAmount) {
		this.tagAmount = tagAmount;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
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

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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

	public Integer getAffectFlag() {
		return affectFlag;
	}

	public void setAffectFlag(Integer affectFlag) {
		this.affectFlag = affectFlag;
	}

	public String getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public BigDecimal getMallDeductAmount() {
		return mallDeductAmount;
	}

	public void setMallDeductAmount(BigDecimal mallDeductAmount) {
		this.mallDeductAmount = mallDeductAmount;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public BigDecimal getReduceAmount() {
		return reduceAmount;
	}

	public void setReduceAmount(BigDecimal reduceAmount) {
		this.reduceAmount = reduceAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
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

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getOrderBillTypeName() {
		return orderBillTypeName;
	}

	public void setOrderBillTypeName(String orderBillTypeName) {
		this.orderBillTypeName = orderBillTypeName;
	}
	@JsonSerialize(using = JsonDateSerializer$10.class)
    private Date endDate;

    /**
     * 订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 9-其他 默认为0
     */
    private Integer businessType;

    /**
     * 订单状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银 40-待配货41-已发货 50-已提货 99-已完结
     */

    /**
     * 营业员工号,与HR工号代码一致
     */
    private String assistantNo;

    /**
     * 营业员姓名
     */
//    private String assistantName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 商品SKU
     */
    private String skuNo;

    /**
     * 商品内码
     */
    private String itemNo;

    /**
     * 商品尺码
     */
    private String sizeNo;

    /**
     * 商品数量
     */
//    private Integer qty;

    /**
     * 商品类型,0-正常 1-赠品 促销标识
     */
    private Integer itemFlag;

    /**
     * 商品牌价
     */
//    private BigDecimal tagPrice;

    /**
     * 商品现价
     */
//    private BigDecimal salePrice;

    /**
     * 商品折扣价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discPrice;

    /**
     * 商品结算价
     */
//    private BigDecimal settlePrice;

    /**
     * 减价,单价减价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal reducePrice;

    /**
     * 商品总金额,(结算价-减价)*数量
     */
//    private BigDecimal amount;

    /**
     * 牌价额,牌价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagAmount;

    /**
     * 现价额,现价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;

    /**
     * 结算额,结算价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal settleAmount;

    /**
     * 折扣额,商品折扣价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discAmount;

    /**
     * 促销优惠单价,促销优惠单价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prefPrice;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    /**
     * 扣率,如0.17
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discount;
    /**
     * 扣率%
     */
    private String discountStr;

    /**
     * 会员折数
     */
    private BigDecimal vipDiscount;

    /**
     * 基本积分
     */
    private Integer baseScore;

    /**
     * 整单分摊积分
     */
    private Integer proScore;

    /**
     * 是否影响销售,0-正常 1-不可销售 默认为0
     */
    private Integer affectFlag;

    /**
     * 商品折数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal itemDiscount;

    private String itemDiscountStr;
    
    /**
     * 是否已结算(1-未结算，2-已结算)
     */
    private String isBalance;

    /**
     * 结算单号码
     */
    private String balanceNo;

    /**
     * 备注
     */
//    private String remark;

    /**
     * 商场扣额(结算额*扣率)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallDeductAmount;

	/*//结算公司名称
	private String companyName;*/
	//单据日期
//	@JsonSerialize(using = JsonDateSerializer$10.class)
//	private Date outDate;
	//商品编码
	private String itemCode;
	//商品名称
//	private String itemName;
	/*//营业员
	private String assistant;*/
	//牌价额
//	private BigDecimal tagPriceAmount;
	
	//现价额
//		private BigDecimal salePriceAmount;
		//结算金额
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	 	private BigDecimal reduceAmount;
		//现金券总金额
	@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal couponAmount;
		//订单总金额
	@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal allAmount;
		//财务确认标志
		private Integer monthlyFlag;
		//订单业务类型
//		private Integer orderBillType;
		//发票号
		private String invoiceNo;
		//发票日期
		private Date invoiceDate;
		//原单号
//		private String oldOrderNo;
		//发票金额
		private BigDecimal invoiceAmount;
	
		private String orderBillTypeName;
//		

	public String getOrderNo() {
		return orderNo;
	}

	public String getShopNo() {
		return shopNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public BigDecimal getTagPriceAmount() {
		return tagPriceAmount;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public BigDecimal getSalePriceAmount() {
		return salePriceAmount;
	}

	public Integer getQty() {
		return qty;
	}

	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public void setTagPriceAmount(BigDecimal tagPriceAmount) {
		this.tagPriceAmount = tagPriceAmount;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public void setSalePriceAmount(BigDecimal salePriceAmount) {
		this.salePriceAmount = salePriceAmount;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public Date getOutDate() {
		return outDate;
	}

	public String getOrderBillType() {
		return orderBillType;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public void setOrderBillType(String orderBillType) {
		this.orderBillType = orderBillType;
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

	public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}

	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
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

	public BigDecimal getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(BigDecimal dealAmount) {
		this.dealAmount = dealAmount;
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

	public String getLaunchTypeName() {
		return launchTypeName;
	}

	public void setLaunchTypeName(String launchTypeName) {
		this.launchTypeName = launchTypeName;
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

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getBsgroupsName() {
		return bsgroupsName;
	}

	public void setBsgroupsName(String bsgroupsName) {
		this.bsgroupsName = bsgroupsName;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getDiscountStr() {
		return discountStr;
	}

	public void setDiscountStr(String discountStr) {
		this.discountStr = discountStr;
	}
	
	
}
