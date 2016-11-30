package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemZonePriceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4411920859538561873L;

	/**
	 * 地区编号
	 */
	private String zoneNo;
	
	/**
	 * 商品编号
	 */
    private String itemNo;
    
    /**
     * 商品价格
     */
    private BigDecimal price;

	public String getZoneNo() {
		return zoneNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
