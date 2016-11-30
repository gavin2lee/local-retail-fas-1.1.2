package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
public class BillShopBalanceInvoiceApply implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8510012453926886387L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 发票申请单号
     */
    private String invoiceapplyNo;

    /**
     * 结算公司编码(开票方编码)
     */
    private String companyNo;

    /**
     * 申请开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyDate;

    /**
     * 客户编码-商业集团
     */
    private String customerNo;

    /**
     * 币别
     */
    private String currency;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 纳税人识别号
     */
    private String taxpayerId;

    /**
     * 交票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date postPayDate;

    /**
     * 单据状态
     */
    private Byte status;

    /**
     * 引出次数
     */
    private String exportNum;

    /**
     * 开票名称
     */
    private String name;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 发票类型(0 = 普通发票 1 = 增值票)
     */
    private Byte invoiceType;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 是否预开票(0 = 是 1 = 否)
     */
    private Byte preInvoice;

    /**
     * 商场地址
     */
    private String mallAddress;

    /**
     * 商场电话
     */
    private String mallTel;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 邮寄地址
     */
    private String mailingAddress;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_invoice_apply.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_invoice_apply.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #invoiceapplyNo}
     *
     * @return the value of bill_shop_balance_invoice_apply.invoiceapply_no
     */
    public String getInvoiceapplyNo() {
        return invoiceapplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceapplyNo}
     * @param invoiceapplyNo the value for bill_shop_balance_invoice_apply.invoiceapply_no
     */
    public void setInvoiceapplyNo(String invoiceapplyNo) {
        this.invoiceapplyNo = invoiceapplyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_invoice_apply.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_invoice_apply.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of bill_shop_balance_invoice_apply.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for bill_shop_balance_invoice_apply.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of bill_shop_balance_invoice_apply.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for bill_shop_balance_invoice_apply.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #currency}
     *
     * @return the value of bill_shop_balance_invoice_apply.currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * {@linkplain #currency}
     * @param currency the value for bill_shop_balance_invoice_apply.currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_shop_balance_invoice_apply.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_shop_balance_invoice_apply.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #taxpayerId}
     *
     * @return the value of bill_shop_balance_invoice_apply.taxpayer_id
     */
    public String getTaxpayerId() {
        return taxpayerId;
    }

    /**
     * 
     * {@linkplain #taxpayerId}
     * @param taxpayerId the value for bill_shop_balance_invoice_apply.taxpayer_id
     */
    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    /**
     * 
     * {@linkplain #postPayDate}
     *
     * @return the value of bill_shop_balance_invoice_apply.post_pay_date
     */
    public Date getPostPayDate() {
        return postPayDate;
    }

    /**
     * 
     * {@linkplain #postPayDate}
     * @param postPayDate the value for bill_shop_balance_invoice_apply.post_pay_date
     */
    public void setPostPayDate(Date postPayDate) {
        this.postPayDate = postPayDate;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_shop_balance_invoice_apply.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_shop_balance_invoice_apply.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #exportNum}
     *
     * @return the value of bill_shop_balance_invoice_apply.export_num
     */
    public String getExportNum() {
        return exportNum;
    }

    /**
     * 
     * {@linkplain #exportNum}
     * @param exportNum the value for bill_shop_balance_invoice_apply.export_num
     */
    public void setExportNum(String exportNum) {
        this.exportNum = exportNum;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of bill_shop_balance_invoice_apply.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for bill_shop_balance_invoice_apply.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_shop_balance_invoice_apply.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_shop_balance_invoice_apply.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_invoice_apply.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_invoice_apply.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     *
     * @return the value of bill_shop_balance_invoice_apply.invoice_type
     */
    public Byte getInvoiceType() {
        return invoiceType;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     * @param invoiceType the value for bill_shop_balance_invoice_apply.invoice_type
     */
    public void setInvoiceType(Byte invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of bill_shop_balance_invoice_apply.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for bill_shop_balance_invoice_apply.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of bill_shop_balance_invoice_apply.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for bill_shop_balance_invoice_apply.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     *
     * @return the value of bill_shop_balance_invoice_apply.pre_invoice
     */
    public Byte getPreInvoice() {
        return preInvoice;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     * @param preInvoice the value for bill_shop_balance_invoice_apply.pre_invoice
     */
    public void setPreInvoice(Byte preInvoice) {
        this.preInvoice = preInvoice;
    }

    /**
     * 
     * {@linkplain #mallAddress}
     *
     * @return the value of bill_shop_balance_invoice_apply.mall_address
     */
    public String getMallAddress() {
        return mallAddress;
    }

    /**
     * 
     * {@linkplain #mallAddress}
     * @param mallAddress the value for bill_shop_balance_invoice_apply.mall_address
     */
    public void setMallAddress(String mallAddress) {
        this.mallAddress = mallAddress;
    }

    /**
     * 
     * {@linkplain #mallTel}
     *
     * @return the value of bill_shop_balance_invoice_apply.mall_tel
     */
    public String getMallTel() {
        return mallTel;
    }

    /**
     * 
     * {@linkplain #mallTel}
     * @param mallTel the value for bill_shop_balance_invoice_apply.mall_tel
     */
    public void setMallTel(String mallTel) {
        this.mallTel = mallTel;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of bill_shop_balance_invoice_apply.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for bill_shop_balance_invoice_apply.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of bill_shop_balance_invoice_apply.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for bill_shop_balance_invoice_apply.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     *
     * @return the value of bill_shop_balance_invoice_apply.mailing_address
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     * @param mailingAddress the value for bill_shop_balance_invoice_apply.mailing_address
     */
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_shop_balance_invoice_apply.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_shop_balance_invoice_apply.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_shop_balance_invoice_apply.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_shop_balance_invoice_apply.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bill_shop_balance_invoice_apply.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bill_shop_balance_invoice_apply.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_shop_balance_invoice_apply.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_shop_balance_invoice_apply.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_invoice_apply.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_invoice_apply.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}