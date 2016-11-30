package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
public class BillInvoiceDtl implements Serializable,SequenceId {
    /**
	 * 序列ID
	 */
	private static final long serialVersionUID = -9102602087564891916L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票编码
     */
    private String invoiceCode;
    
    /**
     * 发票日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class) 
    private Date invoiceDate;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税税额
     */
    private BigDecimal noTaxAmount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 价格
     */
    private BigDecimal price;

    private Date billDate;
    
    public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_invoice_dtl.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_invoice_dtl.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_invoice_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_invoice_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_invoice_dtl.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_invoice_dtl.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of bill_invoice_dtl.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for bill_invoice_dtl.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of bill_invoice_dtl.invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param invoiceAmount the value for bill_invoice_dtl.invoice_amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_invoice_dtl.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_invoice_dtl.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #noTaxAmount}
     *
     * @return the value of bill_invoice_dtl.no_tax_amount
     */
    public BigDecimal getNoTaxAmount() {
        return noTaxAmount;
    }

    /**
     * 
     * {@linkplain #noTaxAmount}
     * @param noTaxAmount the value for bill_invoice_dtl.no_tax_amount
     */
    public void setNoTaxAmount(BigDecimal noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     *
     * @return the value of bill_invoice_dtl.tax_amount
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     * @param taxAmount the value for bill_invoice_dtl.tax_amount
     */
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_invoice_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_invoice_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_invoice_dtl.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_invoice_dtl.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_invoice_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_invoice_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_invoice_dtl.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_invoice_dtl.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of bill_invoice_dtl.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for bill_invoice_dtl.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #price}
     *
     * @return the value of bill_invoice_dtl.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 
     * {@linkplain #price}
     * @param price the value for bill_invoice_dtl.price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}