package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class BaroqueItemCostDto {
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String month;
	private String year;
	private String itemNo;
	private String itemCode;
	private String itemName;
	private String supplierName;
	private String supplierNo;
	private String styleNo;
	private String brandNo;
	private String brandName;
	private String brandUnitName;
	private String brandUnitNo;
	private String categoryNo; 
	private String categoryName;
	private String oneLevelCategoryName;
	private String twoLevelCategoryName;
	private String brandSeason;
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal factoryPrice;
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date effectiveTime;
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal headquarterCost;
	
	public String getBrandNo() {
		return brandNo;
	}
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
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
	
	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}
	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getBrandSeason() {
		return brandSeason;
	}
	public void setBrandSeason(String brandSeason) {
		this.brandSeason = brandSeason;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
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
	public String getStyleNo() {
		return styleNo;
	}
	public String getBrandUnitName() {
		return brandUnitName;
	}
	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}
	public String getBrandUnitNo() {
		return brandUnitNo;
	}
	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}
	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
	}
	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}
	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}
	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
	}
}
