package cn.wonhigh.retail.fas.common.dto;

public class ShopExtensionDto {
	/**
	 * 店铺编码
	 */
	private String shopNo;

	/**
	 * 品牌编码
	 */
	private String brandNo;

	/**
	 * 属性编码
	 */
	private String attributeNo;

	public String getShopNo() {
		return shopNo;
	}


	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}


	public String getBrandNo() {
		return brandNo;
	}


	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}


	public String getAttributeNo() {
		return attributeNo;
	}


	public void setAttributeNo(String attributeNo) {
		this.attributeNo = attributeNo;
	}


	public String getAttributeDetailNo() {
		return attributeDetailNo;
	}


	public void setAttributeDetailNo(String attributeDetailNo) {
		this.attributeDetailNo = attributeDetailNo;
	}


	public String getAttributeDetailName() {
		return attributeDetailName;
	}


	public void setAttributeDetailName(String attributeDetailName) {
		this.attributeDetailName = attributeDetailName;
	}


	/**
	 * 属性明细编码
	 */
	private String attributeDetailNo;
	
	
	/**
	 * 
	 */
	private String attributeDetailName;
}
