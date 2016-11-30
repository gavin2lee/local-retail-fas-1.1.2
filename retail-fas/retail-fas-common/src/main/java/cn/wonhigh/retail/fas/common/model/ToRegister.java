package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-31 14:45:27
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
public class ToRegister extends FasBaseModel{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7229670594080513984L;
	/**
     * 公司编号
     */
	@ExcelCell("A")
    private String companyNo;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 供应商编号
     */
	@ExcelCell("B")
    private String supplierNo;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 拒付时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@ExcelCell("C")
    private Date paymentDate;
    /**
     * 拒付金额
     */
	@ExcelCell("D")
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal paymentAmount;
    /**
     * 备注
     */
    private String remark;

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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}