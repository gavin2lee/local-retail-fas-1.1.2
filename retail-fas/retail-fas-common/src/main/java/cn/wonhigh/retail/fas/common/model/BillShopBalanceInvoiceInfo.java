package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
public class BillShopBalanceInvoiceInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2485840026153909914L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String invoiceinfoNo;

    /**
     * 发票申请单号
     */
    private String invoiceapplyNo;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceDate;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 含税金额
     */
    private BigDecimal taxAmount;

    /**
     * 快递日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date deliveryDate;

    /**
     * 快递公司
     */
    private String express;

    /**
     * 快递单号
     */
    private String deliveryNo;

    /**
     * 接收日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date receiveDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_invoice_info.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_invoice_info.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #invoiceinfoNo}
     *
     * @return the value of bill_shop_balance_invoice_info.invoiceinfo_no
     */
    public String getInvoiceinfoNo() {
        return invoiceinfoNo;
    }

    /**
     * 
     * {@linkplain #invoiceinfoNo}
     * @param invoiceinfoNo the value for bill_shop_balance_invoice_info.invoiceinfo_no
     */
    public void setInvoiceinfoNo(String invoiceinfoNo) {
        this.invoiceinfoNo = invoiceinfoNo;
    }

    /**
     * 
     * {@linkplain #invoiceapplyNo}
     *
     * @return the value of bill_shop_balance_invoice_info.invoiceapply_no
     */
    public String getInvoiceapplyNo() {
        return invoiceapplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceapplyNo}
     * @param invoiceapplyNo the value for bill_shop_balance_invoice_info.invoiceapply_no
     */
    public void setInvoiceapplyNo(String invoiceapplyNo) {
        this.invoiceapplyNo = invoiceapplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_shop_balance_invoice_info.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_shop_balance_invoice_info.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of bill_shop_balance_invoice_info.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for bill_shop_balance_invoice_info.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of bill_shop_balance_invoice_info.invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param invoiceAmount the value for bill_shop_balance_invoice_info.invoice_amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     *
     * @return the value of bill_shop_balance_invoice_info.tax_amount
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     * @param taxAmount the value for bill_shop_balance_invoice_info.tax_amount
     */
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * 
     * {@linkplain #deliveryDate}
     *
     * @return the value of bill_shop_balance_invoice_info.delivery_date
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * 
     * {@linkplain #deliveryDate}
     * @param deliveryDate the value for bill_shop_balance_invoice_info.delivery_date
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * 
     * {@linkplain #express}
     *
     * @return the value of bill_shop_balance_invoice_info.express
     */
    public String getExpress() {
        return express;
    }

    /**
     * 
     * {@linkplain #express}
     * @param express the value for bill_shop_balance_invoice_info.express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * 
     * {@linkplain #deliveryNo}
     *
     * @return the value of bill_shop_balance_invoice_info.delivery_no
     */
    public String getDeliveryNo() {
        return deliveryNo;
    }

    /**
     * 
     * {@linkplain #deliveryNo}
     * @param deliveryNo the value for bill_shop_balance_invoice_info.delivery_no
     */
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of bill_shop_balance_invoice_info.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     * @param receiveDate the value for bill_shop_balance_invoice_info.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_invoice_info.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_invoice_info.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}