package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.SettlePathBillBasisEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 拆单的数据源头
 * 
 * @author yang.y
 */
public class SplitDataSource {

	private String billNo;
	
	private String supplierNo;
	
	private String companyNo;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date sendOutDate;
	
	private BigDecimal taxRate;
	
	private String brandNo;
	
	private String brandName;
	
	private String itemNo;
	
	private String categoryNo;
	
	private String years;
	
	private String season;
	
	private Integer billBasis;
	
	private String billBasisName;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public Date getSendOutDate() {
		return sendOutDate;
	}

	public void setSendOutDate(Date sendOutDate) {
		this.sendOutDate = sendOutDate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
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

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Integer getBillBasis() {
		return billBasis;
	}

	public void setBillBasis(Integer billBasis) {
		this.billBasis = billBasis;
	}

	public String getBillBasisName() {
		if(billBasis != null) {
			for(SettlePathBillBasisEnums e : SettlePathBillBasisEnums.values()) {
				if(e.getValue().equals(billBasis.toString())) {
					return e.getText();
				}
			}
		}
		return billBasisName;
	}

	public void setBillBasisName(String billBasisName) {
		this.billBasisName = billBasisName;
	}
}
