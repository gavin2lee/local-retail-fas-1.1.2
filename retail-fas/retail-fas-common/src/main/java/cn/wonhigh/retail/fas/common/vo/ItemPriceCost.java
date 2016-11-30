package cn.wonhigh.retail.fas.common.vo;

import java.math.BigDecimal;

public class ItemPriceCost {
	BigDecimal unitCost = BigDecimal.ZERO;
	BigDecimal regionCost = BigDecimal.ZERO;
	BigDecimal headquarterCost = BigDecimal.ZERO;
	BigDecimal tagPriceNation = BigDecimal.ZERO;
	BigDecimal purchasePrice = BigDecimal.ZERO;
	
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	public BigDecimal getRegionCost() {
		return regionCost;
	}
	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}
	public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}
	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
	}
	public BigDecimal getTagPriceNation() {
		return tagPriceNation;
	}
	public void setTagPriceNation(BigDecimal tagPriceNation) {
		this.tagPriceNation = tagPriceNation;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}
	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}
	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}
	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}
	BigDecimal materialPrice = BigDecimal.ZERO;
	BigDecimal factoryPrice = BigDecimal.ZERO;
}
