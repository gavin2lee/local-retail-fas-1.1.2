package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-19 10:48:20
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
public class OrgUnitBrandRel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9217587542781984L;
	
    private Integer id;

    private String storeNo;

    private String storeCode;

    private String storeName;

    private String orgSearchCode;

    private String orderUnitNo;

    private String orderUnitCode;

    private String orderUnitName;

    private String orderSearchCode;

    private String companyNo;

    private String shopStoreNo;

    private String shopStoreCode;

    private String shopStoreName;

    private String storeSearchCode;

    private Long orgType;

    private String zoneNo;

    private String zoneName;

    private String brandNo;

    private Byte status;

    private Byte storeType;

    private Byte relStatus;

    private Date updateTime;

    private String organNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrgSearchCode() {
        return orgSearchCode;
    }

    public void setOrgSearchCode(String orgSearchCode) {
        this.orgSearchCode = orgSearchCode;
    }

    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    public String getOrderUnitCode() {
        return orderUnitCode;
    }

    public void setOrderUnitCode(String orderUnitCode) {
        this.orderUnitCode = orderUnitCode;
    }

    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public String getOrderSearchCode() {
        return orderSearchCode;
    }

    public void setOrderSearchCode(String orderSearchCode) {
        this.orderSearchCode = orderSearchCode;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getShopStoreNo() {
        return shopStoreNo;
    }

    public void setShopStoreNo(String shopStoreNo) {
        this.shopStoreNo = shopStoreNo;
    }

    public String getShopStoreCode() {
        return shopStoreCode;
    }

    public void setShopStoreCode(String shopStoreCode) {
        this.shopStoreCode = shopStoreCode;
    }

    public String getShopStoreName() {
        return shopStoreName;
    }

    public void setShopStoreName(String shopStoreName) {
        this.shopStoreName = shopStoreName;
    }

    public String getStoreSearchCode() {
        return storeSearchCode;
    }

    public void setStoreSearchCode(String storeSearchCode) {
        this.storeSearchCode = storeSearchCode;
    }

    public Long getOrgType() {
        return orgType;
    }

    public void setOrgType(Long orgType) {
        this.orgType = orgType;
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

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getStoreType() {
        return storeType;
    }

    public void setStoreType(Byte storeType) {
        this.storeType = storeType;
    }

    public Byte getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(Byte relStatus) {
        this.relStatus = relStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrganNo() {
        return organNo;
    }

    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }
}