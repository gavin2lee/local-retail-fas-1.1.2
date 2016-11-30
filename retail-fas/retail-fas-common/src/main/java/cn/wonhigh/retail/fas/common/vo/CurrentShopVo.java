package cn.wonhigh.retail.fas.common.vo;

import java.util.Date;


/**
 * TODO: 增加描述
 * 
 * @author chen.sn
 * @date 2014-8-14 下午4:45:42
 * @version 0.9.1 
 * @copyright yougou.com 
 */
public class CurrentShopVo {
	private String shopNo;
	private String shopName;
	private String shopCode;
	private String bizCityNo;
	private String storeNo;
	private String storeName;
	private String organNo;
	private String address;
	private String companyNo;
	private String companyName;
	private String retailType;
	private String zoneNo;
	/**
	 * 货品关账日期
	 */
	private Date goodsDate;
	
	/**
	 * 正常销售关账日期
	 */
	private Date saleDate;
	
	/**
	 * 内购销售日期
	 */
	private Date grouponDate;
	
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
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getOrganNo() {
		return organNo;
	}
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
	public String getBizCityNo() {
		return bizCityNo;
	}
	public void setBizCityNo(String bizCityNo) {
		this.bizCityNo = bizCityNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getRetailType() {
		return retailType;
	}
	public void setRetailType(String retailType) {
		this.retailType = retailType;
	}
	public Date getGoodsDate() {
		return goodsDate;
	}
	public void setGoodsDate(Date goodsDate) {
		this.goodsDate = goodsDate;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public Date getGrouponDate() {
		return grouponDate;
	}
	public void setGrouponDate(Date grouponDate) {
		this.grouponDate = grouponDate;
	}
	public String getZoneNo() {
		return zoneNo;
	}
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	
}
