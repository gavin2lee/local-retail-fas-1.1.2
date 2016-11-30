package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.Date;

public class HeadquarterCostDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -309674133601115085L;

	/**
	 * 商品编号
	 */
	private String itemNo;
	
	private Date effectiveDate;

	public String getItemNo() {
		return itemNo;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
}
