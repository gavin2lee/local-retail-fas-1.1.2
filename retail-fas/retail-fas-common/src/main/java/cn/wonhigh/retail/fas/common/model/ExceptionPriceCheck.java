package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-18 12:32:47
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
public class ExceptionPriceCheck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9125674252266778814L;
	
	/**
	 * Id
	 */
	private String id;
	
	/**
	 * 单据编号
	 */
	private String billNo;
	
	/**
	 * 单据类型
	 */
	private Integer billType;
	
	/**
	 * 单据类型名称
	 */
	private String billTypeName;
	
	/**
	 * 订货单位编码
	 */
	private String orderUnitNo;
	
	/**
	 * 单据日期
	 */
	@JsonSerialize(using=JsonDateSerializer$10.class)
	private Date billDate;
	
	/**
	 * 商品编码
	 */
	private String itemCode;
	
	/**
	 * 商品名称
	 */
	private String itemName;
	
	/**
	 * 商品编号
	 */
	private String itemNo;
	
	/**
	 * 商品sku
	 */
	private String skuNo;
	
	/**
	 * 商品尺码
	 */
	private String sizeNo;
	
	/**
	 * 商品数量
	 */
	private Integer qty;
	
	/**
	 * 商品价格
	 */
	private BigDecimal cost;
	
	/**
	 * 商品取价来源
	 */
	private Integer costFrom;
	
	/**
	 * 商品取价来源名称
	 */
	private String costFromName;
	
	/**
	 * 商品实际价格
	 */
	private BigDecimal baseCost;
	
	/**
	 * 品牌编码
	 */
	private String brandNo;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 大类编码
	 */
	private String categoryNo;
	
	/**
	 * 大类名称
	 */
	private String categoryName;
	
	/**
	 * 供应商编码
	 */
	private String supplierNo;
	
	/**
	 * 公司编码
	 */
	private String companyNo;
	
	/**
	 * 地区编码
	 */
	private String zoneNo;
	
	/**
	 * 地区编码
	 */
	private String zoneName;
	
	/**
	 * 异常原因
	 */
	private String exceptionReason;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using=JsonDateSerializer$19.class)
	private Date createTime;
	
	/**
	 * 结算类型
	 */
	private Integer balanceType;

	/**
	 * 结算类型名称
	 */
	private String balanceTypeName;
	
	public String getBalanceTypeName() {
		return balanceTypeName;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public String getBillNo() {
		return billNo;
	}

	public Integer getBillType() {
		return billType;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public Date getBillDate() {
		return billDate;
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

	public String getSizeNo() {
		return sizeNo;
	}

	public Integer getQty() {
		return qty;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public Integer getCostFrom() {
		return costFrom;
	}

	public String getCostFromName() {
		return costFromName;
	}

	public BigDecimal getBaseCost() {
		return baseCost;
	}

	public Integer getStatus() {
		return status;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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

	public void setSizeNo(String sizeNo) {
		this.sizeNo = sizeNo;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setCostFrom(Integer costFrom) {
		this.costFrom = costFrom;
	}

	public void setCostFromName(String costFromName) {
		this.costFromName = costFromName;
	}

	public void setBaseCost(BigDecimal baseCost) {
		this.baseCost = baseCost;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
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

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

}
