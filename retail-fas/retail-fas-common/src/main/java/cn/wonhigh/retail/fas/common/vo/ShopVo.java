package cn.wonhigh.retail.fas.common.vo;

import java.io.Serializable;

/**
 * @字典对象
 * @author wang.m
 * @Date 2014-9-23
 * @version 0.1.0
 * @copyright yougou.com
 */
public class ShopVo  implements Serializable {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -6796611509182820958L;
	private  String shopNo;
	private  String shopName;
    private  String companyNo;
    private  String companyName;
    private  String zoneNo;
    private  String zoneName;
    private  String mallNo;
    private  String mallName;
    private  String address;
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
	public String getMallNo() {
		return mallNo;
	}
	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}
	public String getMallName() {
		return mallName;
	}
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
}
