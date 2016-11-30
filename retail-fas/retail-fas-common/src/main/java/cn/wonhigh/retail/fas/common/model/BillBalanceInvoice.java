package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-25 16:31:54
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
public class BillBalanceInvoice implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4967377549993963571L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 快递公司
     */
    private String express;

    /**
     * 快递日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date expressDate;

    /**
     * 快递单号
     */
    private String expressBillNo;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 结算类型(1-应收,2-应付)
     */
    private Byte balanceType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 发票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date invoiceDate;

    /**
     * 接受日期
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
     * @return the value of bill_balance_invoice.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_balance_invoice.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #express}
     *
     * @return the value of bill_balance_invoice.express
     */
    public String getExpress() {
        return express;
    }

    /**
     * 
     * {@linkplain #express}
     * @param express the value for bill_balance_invoice.express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * 
     * {@linkplain #expressDate}
     *
     * @return the value of bill_balance_invoice.express_date
     */
    public Date getExpressDate() {
        return expressDate;
    }

    /**
     * 
     * {@linkplain #expressDate}
     * @param expressDate the value for bill_balance_invoice.express_date
     */
    public void setExpressDate(Date expressDate) {
        this.expressDate = expressDate;
    }

    /**
     * 
     * {@linkplain #expressBillNo}
     *
     * @return the value of bill_balance_invoice.express_bill_no
     */
    public String getExpressBillNo() {
        return expressBillNo;
    }

    /**
     * 
     * {@linkplain #expressBillNo}
     * @param expressBillNo the value for bill_balance_invoice.express_bill_no
     */
    public void setExpressBillNo(String expressBillNo) {
        this.expressBillNo = expressBillNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_balance_invoice.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_balance_invoice.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_balance_invoice.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_balance_invoice.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_balance_invoice.balance_type
     */
    public Byte getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_balance_invoice.balance_type
     */
    public void setBalanceType(Byte balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_balance_invoice.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_balance_invoice.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of bill_balance_invoice.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for bill_balance_invoice.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of bill_balance_invoice.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     * @param receiveDate the value for bill_balance_invoice.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_balance_invoice.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_balance_invoice.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}