package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class HQRegionCostDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -309674133601115085L;

	/**
	 * 商品总部价
	 */
	private BigDecimal hqPrice;
	
	/**
	 * 商品地区价
	 */
	private BigDecimal regionPrice;

	public BigDecimal getHqPrice() {
		return hqPrice;
	}

	public void setHqPrice(BigDecimal hqPrice) {
		this.hqPrice = hqPrice;
	}

	public BigDecimal getRegionPrice() {
		return regionPrice;
	}

	public void setRegionPrice(BigDecimal regionPrice) {
		this.regionPrice = regionPrice;
	}

}
