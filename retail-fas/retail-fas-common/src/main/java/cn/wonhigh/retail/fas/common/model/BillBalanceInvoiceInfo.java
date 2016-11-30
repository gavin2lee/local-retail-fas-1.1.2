package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途
 * 
 * @author chen.mj
 * @date 2014-09-30 11:19:51
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class BillBalanceInvoiceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088815451522452927L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 发票申请单号
	 */
	private String billNo;

	/**
	 * 结算单据编号
	 */
	private String balanceNo;

	/**
	 * 发票号
	 */
	private String invoiceNo;

	/**
	 * 发票金额
	 */
	private BigDecimal amount;

	/**
	 * 含税金额
	 */
	private BigDecimal taxAmount;

	/**
	 * 发票日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date invoiceDate;

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
	 * 确认人
	 */
	private String auditor;

	/**
	 * 确认日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date auditDate;

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @return the value of bill_balance_invoice_info.id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @param id
	 *            the value for bill_balance_invoice_info.id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_balance_invoice_info.bill_no
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @param billNo
	 *            the value for bill_balance_invoice_info.bill_no
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 
	 * {@linkplain #balanceNo}
	 * 
	 * @return the value of bill_balance_invoice_info.balance_no
	 */
	public String getBalanceNo() {
		return balanceNo;
	}

	/**
	 * 
	 * {@linkplain #balanceNo}
	 * 
	 * @param balanceNo
	 *            the value for bill_balance_invoice_info.balance_no
	 */
	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	/**
	 * 
	 * {@linkplain #invoiceNo}
	 * 
	 * @return the value of bill_balance_invoice_info.invoice_no
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * 
	 * {@linkplain #invoiceNo}
	 * 
	 * @param invoiceNo
	 *            the value for bill_balance_invoice_info.invoice_no
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * 
	 * {@linkplain #amount}
	 * 
	 * @return the value of bill_balance_invoice_info.amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 
	 * {@linkplain #amount}
	 * 
	 * @param amount
	 *            the value for bill_balance_invoice_info.amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 
	 * {@linkplain #taxAmount}
	 * 
	 * @return the value of bill_balance_invoice_info.tax_amount
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 * 
	 * {@linkplain #taxAmount}
	 * 
	 * @param taxAmount
	 *            the value for bill_balance_invoice_info.tax_amount
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * 
	 * {@linkplain #invoiceDate}
	 * 
	 * @return the value of bill_balance_invoice_info.invoice_date
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * 
	 * {@linkplain #invoiceDate}
	 * 
	 * @param invoiceDate
	 *            the value for bill_balance_invoice_info.invoice_date
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * 
	 * {@linkplain #deliveryDate}
	 * 
	 * @return the value of bill_balance_invoice_info.delivery_date
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * 
	 * {@linkplain #deliveryDate}
	 * 
	 * @param deliveryDate
	 *            the value for bill_balance_invoice_info.delivery_date
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * 
	 * {@linkplain #express}
	 * 
	 * @return the value of bill_balance_invoice_info.express
	 */
	public String getExpress() {
		return express;
	}

	/**
	 * 
	 * {@linkplain #express}
	 * 
	 * @param express
	 *            the value for bill_balance_invoice_info.express
	 */
	public void setExpress(String express) {
		this.express = express;
	}

	/**
	 * 
	 * {@linkplain #deliveryNo}
	 * 
	 * @return the value of bill_balance_invoice_info.delivery_no
	 */
	public String getDeliveryNo() {
		return deliveryNo;
	}

	/**
	 * 
	 * {@linkplain #deliveryNo}
	 * 
	 * @param deliveryNo
	 *            the value for bill_balance_invoice_info.delivery_no
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	/**
	 * 
	 * {@linkplain #receiveDate}
	 * 
	 * @return the value of bill_balance_invoice_info.receive_date
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}

	/**
	 * 
	 * {@linkplain #receiveDate}
	 * 
	 * @param receiveDate
	 *            the value for bill_balance_invoice_info.receive_date
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @return the value of bill_balance_invoice_info.remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @param remark
	 *            the value for bill_balance_invoice_info.remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}