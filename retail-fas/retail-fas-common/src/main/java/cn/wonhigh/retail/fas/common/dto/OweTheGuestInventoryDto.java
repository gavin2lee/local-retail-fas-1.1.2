/**
 * title:OweTheGuestInventoryDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:欠客存货调节表
 * auther:user
 * date:2015-7-1 下午4:53:00
 */
package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


public class OweTheGuestInventoryDto implements Serializable {

	private static final long serialVersionUID = 6986965252714663115L;
	
	private String companyNo;
	
	private String companyName;

	private String orderNo; // 销售单号
	
	private String refundNo; //退货、退款单号

	private String shopNo;

	private String shopName;

	private String organNo;// 管理城市编码

	private String organName;// 管理城市名称

	private String busiCityNo;// 经营城市编码

	private String busiCityName;// 经营城市名称

	private String itemNo;
	
	private String skuNo;
	
	private String sizeNo;

	private String itemCode;

	private String itemName;

	private String categoryNo;

	private String categoryName;

	private String brandNo;

	private String brandName;
	
	private String brandUnitName;

	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal unitCost;// 成本
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal unitCostSum; //成本合计

	private int saleQty;// 销售数量

	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date saleDate; // 销售日期

	private int sendQty; // 发出数量
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date sendDate; // 发出日期

	private int currPeriodOweQty; // 本期销售欠客数量

	private int earlyOweCurrSendQty; // 前期欠客本期发出数量

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

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

	public String getBusiCityNo() {
		return busiCityNo;
	}

	public void setBusiCityNo(String busiCityNo) {
		this.busiCityNo = busiCityNo;
	}

	public String getBusiCityName() {
		return busiCityName;
	}

	public void setBusiCityName(String busiCityName) {
		this.busiCityName = busiCityName;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public int getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(int saleQty) {
		this.saleQty = saleQty;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public int getSendQty() {
		return sendQty;
	}

	public void setSendQty(int sendQty) {
		this.sendQty = sendQty;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getCurrPeriodOweQty() {
		return currPeriodOweQty;
	}

	public void setCurrPeriodOweQty(int currPeriodOweQty) {
		this.currPeriodOweQty = currPeriodOweQty;
	}

	public int getEarlyOweCurrSendQty() {
		return earlyOweCurrSendQty;
	}

	public void setEarlyOweCurrSendQty(int earlyOweCurrSendQty) {
		this.earlyOweCurrSendQty = earlyOweCurrSendQty;
	}

	public BigDecimal getUnitCostSum() {
		return unitCostSum;
	}

	public void setUnitCostSum(BigDecimal unitCostSum) {
		this.unitCostSum = unitCostSum;
	}
	
}
