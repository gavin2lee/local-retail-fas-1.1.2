package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class UpdateCostApiDto implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -1257778031369670025L;

	/**
	 * 公司编号
	 */
	public String companyNo;

	/**
	 * 起始时间
	 */
	public Date startDate;
	
	/**
	 * 结束时间
	 */
	public Date endDate;

	/**
	 * 品牌编号集
	 */
	public List<String> brandNos;

	/**
	 * 商品编号集
	 */
	public List<String> itemNos;

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getBrandNos() {
		return brandNos;
	}

	public void setBrandNos(List<String> brandNos) {
		this.brandNos = brandNos;
	}

	public List<String> getItemNos() {
		return itemNos;
	}

	public void setItemNos(List<String> itemNos) {
		this.itemNos = itemNos;
	}

}
