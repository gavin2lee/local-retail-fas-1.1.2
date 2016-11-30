package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;


public class BillSaleUnionBuyBalance extends FasBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1645150813556335321L;
	
	private Integer billType;
	
	private Integer bizType;
	
	private String orderUnitNo;
	
	private String orderUnitName;
	
	private String supplierNo;
	
	private String supplierName;
	
	private String companyNo;
	
	private String buyerNo;
	
	private String salerNo;
	
	private Date billDate;
	
	private String itemNo;
	
	private String itemCode;
	
	private String itemName;
	
	private String skuNo;
	
	private Integer qty;
	
	private BigDecimal cost;
	
	private BigDecimal billCost;
	
	private String brandNo;
	
	private String brandName;
	
	private String categoryNo;
	
	private String categoryName;

	public Integer getBillType() {
		return billType;
	}

	public Date getBillDate() {
		return billDate;
	}

	public String getItemNo() {
		return itemNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public Integer getQty() {
		return qty;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public BigDecimal getBillCost() {
		return billCost;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setBillCost(BigDecimal billCost) {
		this.billCost = billCost;
	}

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public String getOrderUnitName() {
		return orderUnitName;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public String getSalerNo() {
		return salerNo;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	
}
