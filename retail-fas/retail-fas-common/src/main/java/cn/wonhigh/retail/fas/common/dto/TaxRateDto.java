package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TaxRateDto {
	private BigDecimal tariffRate;
	private BigDecimal vatRate;
	private String currencyCode;
	private String currencyName;
	private String standardCurrencyCode;
	private String standardCurrencyName;

	public String getStandardCurrencyCode() {
		return standardCurrencyCode;
	}

	public void setStandardCurrencyCode(String standardCurrencyCode) {
		this.standardCurrencyCode = standardCurrencyCode;
	}

	public String getStandardCurrencyName() {
		return standardCurrencyName;
	}

	public void setStandardCurrencyName(String standardCurrencyName) {
		this.standardCurrencyName = standardCurrencyName;
	}

	private String supplierCode;
	private String supplierName;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	private String itemNo;
	private String styleNo;
	private Date effectiveDate;

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getTariffRate() {
		return tariffRate;
	}

	public void setTariffRate(BigDecimal tariffRate) {
		this.tariffRate = tariffRate;
	}

	public BigDecimal getVatRate() {
		return vatRate;
	}

	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
