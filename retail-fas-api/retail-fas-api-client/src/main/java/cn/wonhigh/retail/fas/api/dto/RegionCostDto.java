package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RegionCostDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8093777614210238231L;
	
	public RegionCostDto(){}

	/**
	 * 订货单位编码
	 */
	private String orderUnitNo;
	
	/**
	 * 公司编码
	 */
	private String companyNo;
	
	/**
	 * 商品编码
	 */
	private String itemNo;
	
	/**
	 * 货号
	 */
	private List<String> itemNos;
	
	private Date effectiveDate;

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public List<String> getItemNos() {
		return itemNos;
	}

	public void setItemNos(List<String> itemNos) {
		this.itemNos = itemNos;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
