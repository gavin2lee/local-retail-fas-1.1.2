package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-08 15:43:31
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
public class SupplierTariffSet  extends FasBaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3317634245548396295L;
	/**
	 * 供应商编号
	 */
	@ExcelCell("A")
    private String supplierNo;
	/**
	 * 供应商名称
	 */
    private String supplierName;
	/**
	 * 关税率
	 */
	@ExcelCell("B")
    private BigDecimal tariffRate;
	/**
	 * 款号
	 */
	@ExcelCell("C")
    private String styleNo;
	/**
	 * 生效日
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@ExcelCell("D")
	private Date effectiveDate;
	

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getTariffRate() {
        return tariffRate;
    }

    public void setTariffRate(BigDecimal tariffRate) {
        this.tariffRate = tariffRate;
    }

    public String getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

}