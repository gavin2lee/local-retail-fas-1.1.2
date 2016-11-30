/**  
*   
* 项目名称：retail-fas-common  
* 类名称：BillBalanceDto  
* 类描述：用于查询显示结算相关信息的DTO
* 创建人：wang.m 
* 创建时间：2014-10-17 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

public class ReportFinanceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5109226833925942317L;
	
	private String supplierNo;
	
    private String itemSupplierName;
    
    private String supplierName;
    
    private String hqCompanyNo;
    
    private String hqCompanyName;
    
    private String areaCompanyNo;
    
    private String areaCompanyName;
    
    private String itemNo;
    
    private String itemCode;
    
    private String itemName;
    
    private String brandNo;
    
    private String brandName;
    
    private String brandUnitName;
    
    private String categoryNo;
    
    private String categoryName;
   
    private String twoLevelCategoryNo;
    
    private String twoLevelCategoryName;
    
    private String threeLevelCategoryNo;
    
    private String threeLevelCategoryName;
    
    private String organNo;
    
    private String organName;
    
    private String yearsName;
    
    private String seasonName;
    
    private String genderName;
    
    private String supplierGroupName;
    
    private String orderFlag;
    
    private String orderFlagName;
    
    private Integer sendQty;
    
    private Integer purchaseQty;
    
    private Integer salesQty;
    
	@JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagPrice;
    
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discountTagPrice;
    
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal roundTagPrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal addPrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchaseCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchaseBalanceAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesBalanceAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal materialPrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchasePrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal factoryPrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal materialAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchaseAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal factoryAmount;   
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionCostFrom;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal headquarterAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionFromAmount;

    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal differenceAmount1;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal differenceAmount2;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discountTagAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal roundTagAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagDiscountRate;
   
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal headquarterCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal ppAddPrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal ppAddDiscount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal hcmAddDiscount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;
    
    public String getItemSupplierName() {
		return itemSupplierName;
	}

	public void setItemSupplierName(String itemSupplierName) {
		this.itemSupplierName = itemSupplierName;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getOrderFlagName() {
		return orderFlagName;
	}

	public void setOrderFlagName(String orderFlagName) {
		this.orderFlagName = orderFlagName;
	}

	public BigDecimal getDifferenceAmount1() {
		return differenceAmount1;
	}

	public void setDifferenceAmount1(BigDecimal differenceAmount1) {
		this.differenceAmount1 = differenceAmount1;
	}

	public BigDecimal getDifferenceAmount2() {
		return differenceAmount2;
	}

	public void setDifferenceAmount2(BigDecimal differenceAmount2) {
		this.differenceAmount2 = differenceAmount2;
	}

	public BigDecimal getDiscountTagAmount() {
		return discountTagAmount;
	}

	public void setDiscountTagAmount(BigDecimal discountTagAmount) {
		this.discountTagAmount = discountTagAmount;
	}

	public BigDecimal getRoundTagAmount() {
		return roundTagAmount;
	}

	public void setRoundTagAmount(BigDecimal roundTagAmount) {
		this.roundTagAmount = roundTagAmount;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		if(orderFlag.equals("1") || orderFlag.equals("2")){
			this.orderFlag = "统采";
		}else if(orderFlag.equals("3") || orderFlag.equals("4")){
			this.orderFlag = "调货";
		}else if(orderFlag.equals("5")){
			this.orderFlag = "代采";
		}else{
			this.orderFlag = "";
		}
	}
	
	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getDiscountTagPrice() {
		return discountTagPrice;
	}

	public void setDiscountTagPrice(BigDecimal discountTagPrice) {
		this.discountTagPrice = discountTagPrice;
	}

	public BigDecimal getRoundTagPrice() {
		return roundTagPrice;
	}

	public void setRoundTagPrice(BigDecimal roundTagPrice) {
		this.roundTagPrice = roundTagPrice;
	}

	public BigDecimal getAddPrice() {
		return addPrice;
	}

	public void setAddPrice(BigDecimal addPrice) {
		this.addPrice = addPrice;
	}

	public BigDecimal getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public BigDecimal getSalesCost() {
		return salesCost;
	}

	public void setSalesCost(BigDecimal salesCost) {
		this.salesCost = salesCost;
	}

	public Integer getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(Integer purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public Integer getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(Integer salesQty) {
		this.salesQty = salesQty;
	}

	public BigDecimal getPurchaseBalanceAmount() {
		return purchaseBalanceAmount;
	}

	public void setPurchaseBalanceAmount(BigDecimal purchaseBalanceAmount) {
		this.purchaseBalanceAmount = purchaseBalanceAmount;
	}

	public BigDecimal getSalesBalanceAmount() {
		return salesBalanceAmount;
	}

	public void setSalesBalanceAmount(BigDecimal salesBalanceAmount) {
		this.salesBalanceAmount = salesBalanceAmount;
	}

	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}

	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}

	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getThreeLevelCategoryNo() {
		return threeLevelCategoryNo;
	}

	public void setThreeLevelCategoryNo(String threeLevelCategoryNo) {
		this.threeLevelCategoryNo = threeLevelCategoryNo;
	}

	public String getThreeLevelCategoryName() {
		return threeLevelCategoryName;
	}

	public void setThreeLevelCategoryName(String threeLevelCategoryName) {
		this.threeLevelCategoryName = threeLevelCategoryName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getHqCompanyNo() {
		return hqCompanyNo;
	}

	public void setHqCompanyNo(String hqCompanyNo) {
		this.hqCompanyNo = hqCompanyNo;
	}

	public String getHqCompanyName() {
		return hqCompanyName;
	}

	public void setHqCompanyName(String hqCompanyName) {
		this.hqCompanyName = hqCompanyName;
	}

	public String getAreaCompanyNo() {
		return areaCompanyNo;
	}

	public void setAreaCompanyNo(String areaCompanyNo) {
		this.areaCompanyNo = areaCompanyNo;
	}

	public String getAreaCompanyName() {
		return areaCompanyName;
	}

	public void setAreaCompanyName(String areaCompanyName) {
		this.areaCompanyName = areaCompanyName;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getYearsName() {
		return yearsName;
	}

	public void setYearsName(String yearsName) {
		this.yearsName = yearsName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getSupplierGroupName() {
		return supplierGroupName;
	}

	public void setSupplierGroupName(String supplierGroupName) {
		this.supplierGroupName = supplierGroupName;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public BigDecimal getMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(BigDecimal materialAmount) {
		this.materialAmount = materialAmount;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getFactoryAmount() {
		return factoryAmount;
	}

	public void setFactoryAmount(BigDecimal factoryAmount) {
		this.factoryAmount = factoryAmount;
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

	public BigDecimal getRegionCostFrom() {
		return regionCostFrom;
	}

	public void setRegionCostFrom(BigDecimal regionCostFrom) {
		this.regionCostFrom = regionCostFrom;
	}

	public BigDecimal getHeadquarterAmount() {
		return headquarterAmount;
	}

	public void setHeadquarterAmount(BigDecimal headquarterAmount) {
		this.headquarterAmount = headquarterAmount;
	}

	public BigDecimal getRegionAmount() {
		return regionAmount;
	}

	public void setRegionAmount(BigDecimal regionAmount) {
		this.regionAmount = regionAmount;
	}

	public BigDecimal getRegionFromAmount() {
		return regionFromAmount;
	}

	public void setRegionFromAmount(BigDecimal regionFromAmount) {
		this.regionFromAmount = regionFromAmount;
	}

	public BigDecimal getTagDiscountRate() {
		return tagDiscountRate;
	}

	public void setTagDiscountRate(BigDecimal tagDiscountRate) {
		this.tagDiscountRate = tagDiscountRate;
	}

	public BigDecimal getPpAddPrice() {
		return ppAddPrice;
	}

	public void setPpAddPrice(BigDecimal ppAddPrice) {
		this.ppAddPrice = ppAddPrice;
	}

	public BigDecimal getPpAddDiscount() {
		return ppAddDiscount;
	}

	public void setPpAddDiscount(BigDecimal ppAddDiscount) {
		this.ppAddDiscount = ppAddDiscount;
	}

	public BigDecimal getHcmAddDiscount() {
		return hcmAddDiscount;
	}

	public void setHcmAddDiscount(BigDecimal hcmAddDiscount) {
		this.hcmAddDiscount = hcmAddDiscount;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}
	
	
}
