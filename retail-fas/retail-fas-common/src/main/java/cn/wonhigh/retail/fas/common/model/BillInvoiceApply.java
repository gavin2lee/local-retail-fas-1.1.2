package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 地区批发-开票申请单
 * @author yang.y
 * @date  2014-09-16 14:05:12
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
@ExportFormat(className=AbstractExportFormat.class)
public class BillInvoiceApply extends FasBaseModel {

	private static final long serialVersionUID = 4339678629816081831L;

	/**
     * 单据编码
     */
    private String billNo;

    /**
     * 结算单编码
     */
    private String balanceNo;

    /**
     * 发票类型(0 = 普通发票 1 = 增值票)
     */
    private Integer invoiceType;

    /**
     * 结算公司编码(开票方编码)
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 收票方
     */
    private String payUnit;

    /**
     * 税务登记号
     */
    private String taxRegistryNo;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 申请开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyTime;

    /**
     * 客户交票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date payTime;

    /**
     * 是否预开票(0 = 是 1 = 否)
     */
    private Integer preInvoice;

    /**
     * 邮寄地址
     */
    private String mailingAddress;

    /**
     * 快递日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date deliveryTime;

    /**
     * 快递单号
     */
    private String deliveryNo;

    /**
     * 快递公司
     */
    private String express;

    /**
     * 接收日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date receiveTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_invoice_apply.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_invoice_apply.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_invoice_apply.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_invoice_apply.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     *
     * @return the value of bill_invoice_apply.invoice_type
     */
    public Integer getInvoiceType() {
        return invoiceType;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     * @param invoiceType the value for bill_invoice_apply.invoice_type
     */
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_invoice_apply.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_invoice_apply.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_invoice_apply.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_invoice_apply.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of bill_invoice_apply.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for bill_invoice_apply.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of bill_invoice_apply.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for bill_invoice_apply.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of bill_invoice_apply.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for bill_invoice_apply.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of bill_invoice_apply.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for bill_invoice_apply.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #address}
     *
     * @return the value of bill_invoice_apply.address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * {@linkplain #address}
     * @param address the value for bill_invoice_apply.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * {@linkplain #payUnit}
     *
     * @return the value of bill_invoice_apply.pay_unit
     */
    public String getPayUnit() {
        return payUnit;
    }

    /**
     * 
     * {@linkplain #payUnit}
     * @param payUnit the value for bill_invoice_apply.pay_unit
     */
    public void setPayUnit(String payUnit) {
        this.payUnit = payUnit;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     *
     * @return the value of bill_invoice_apply.tax_registry_no
     */
    public String getTaxRegistryNo() {
        return taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     * @param taxRegistryNo the value for bill_invoice_apply.tax_registry_no
     */
    public void setTaxRegistryNo(String taxRegistryNo) {
        this.taxRegistryNo = taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of bill_invoice_apply.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for bill_invoice_apply.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of bill_invoice_apply.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for bill_invoice_apply.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_invoice_apply.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_invoice_apply.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyTime}
     *
     * @return the value of bill_invoice_apply.invoice_apply_time
     */
    public Date getInvoiceApplyTime() {
        return invoiceApplyTime;
    }

    /**
     * 
     * {@linkplain #invoiceApplyTime}
     * @param invoiceApplyTime the value for bill_invoice_apply.invoice_apply_time
     */
    public void setInvoiceApplyTime(Date invoiceApplyTime) {
        this.invoiceApplyTime = invoiceApplyTime;
    }

    /**
     * 
     * {@linkplain #payTime}
     *
     * @return the value of bill_invoice_apply.pay_time
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 
     * {@linkplain #payTime}
     * @param payTime the value for bill_invoice_apply.pay_time
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     *
     * @return the value of bill_invoice_apply.pre_invoice
     */
    public Integer getPreInvoice() {
        return preInvoice;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     * @param preInvoice the value for bill_invoice_apply.pre_invoice
     */
    public void setPreInvoice(Integer preInvoice) {
        this.preInvoice = preInvoice;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     *
     * @return the value of bill_invoice_apply.mailing_address
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     * @param mailingAddress the value for bill_invoice_apply.mailing_address
     */
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * 
     * {@linkplain #deliveryTime}
     *
     * @return the value of bill_invoice_apply.delivery_time
     */
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * 
     * {@linkplain #deliveryTime}
     * @param deliveryTime the value for bill_invoice_apply.delivery_time
     */
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 
     * {@linkplain #deliveryNo}
     *
     * @return the value of bill_invoice_apply.delivery_no
     */
    public String getDeliveryNo() {
        return deliveryNo;
    }

    /**
     * 
     * {@linkplain #deliveryNo}
     * @param deliveryNo the value for bill_invoice_apply.delivery_no
     */
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    /**
     * 
     * {@linkplain #express}
     *
     * @return the value of bill_invoice_apply.express
     */
    public String getExpress() {
        return express;
    }

    /**
     * 
     * {@linkplain #express}
     * @param express the value for bill_invoice_apply.express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * 
     * {@linkplain #receiveTime}
     *
     * @return the value of bill_invoice_apply.receive_time
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 
     * {@linkplain #receiveTime}
     * @param receiveTime the value for bill_invoice_apply.receive_time
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_invoice_apply.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_invoice_apply.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}