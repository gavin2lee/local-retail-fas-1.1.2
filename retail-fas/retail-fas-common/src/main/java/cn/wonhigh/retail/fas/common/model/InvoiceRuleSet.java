package cn.wonhigh.retail.fas.common.model;


/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-14 10:48:39
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
public class InvoiceRuleSet extends FasBaseModel implements SequenceStrId{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3433587902093853425L;

    /**
     * 申请编号
     */
    private String invoiceRuleNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 开票名称
     */
    private String invoiceName;

    /**
     * 税务登记号
     */
    private String taxRegistryNo;

    /**
     * 客户-地址
     */
    private String buyerAddress;

    /**
     * 客户-电话
     */
    private String buyerTel;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 银行账户名
     */
    private String bankAccountName;

    /**
     * 邮寄地址
     */
    private String mailingAddress;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 开票方式
     */
    private String billingMethod;

    /**
     * 店铺分组
     */
    private String shopGroupNo;

    /**
     * 店铺分组
     */
    private String shopGroupName;
    
    /**
     * 发票模板编号
     */
    private String invoiceTempNo;
    
    /**
     * 模板名称
     */
    private String invoiceTempName;

    /**
     * 备注
     */
    private String remark;


    /**
     * 
     * {@linkplain #invoiceRuleNo}
     *
     * @return the value of invoice_rule_set.invoice_rule_no
     */
    public String getInvoiceRuleNo() {
        return invoiceRuleNo;
    }

    /**
     * 
     * {@linkplain #invoiceRuleNo}
     * @param invoiceRuleNo the value for invoice_rule_set.invoice_rule_no
     */
    public void setInvoiceRuleNo(String invoiceRuleNo) {
        this.invoiceRuleNo = invoiceRuleNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of invoice_rule_set.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for invoice_rule_set.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of invoice_rule_set.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for invoice_rule_set.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of invoice_rule_set.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for invoice_rule_set.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of invoice_rule_set.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for invoice_rule_set.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of invoice_rule_set.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for invoice_rule_set.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of invoice_rule_set.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for invoice_rule_set.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of invoice_rule_set.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for invoice_rule_set.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #invoiceName}
     *
     * @return the value of invoice_rule_set.invoice_name
     */
    public String getInvoiceName() {
        return invoiceName;
    }

    /**
     * 
     * {@linkplain #invoiceName}
     * @param invoiceName the value for invoice_rule_set.invoice_name
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     *
     * @return the value of invoice_rule_set.tax_registry_no
     */
    public String getTaxRegistryNo() {
        return taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     * @param taxRegistryNo the value for invoice_rule_set.tax_registry_no
     */
    public void setTaxRegistryNo(String taxRegistryNo) {
        this.taxRegistryNo = taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #buyerAddress}
     *
     * @return the value of invoice_rule_set.buyer_address
     */
    public String getBuyerAddress() {
        return buyerAddress;
    }

    /**
     * 
     * {@linkplain #buyerAddress}
     * @param buyerAddress the value for invoice_rule_set.buyer_address
     */
    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    /**
     * 
     * {@linkplain #buyerTel}
     *
     * @return the value of invoice_rule_set.buyer_tel
     */
    public String getBuyerTel() {
        return buyerTel;
    }

    /**
     * 
     * {@linkplain #buyerTel}
     * @param buyerTel the value for invoice_rule_set.buyer_tel
     */
    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of invoice_rule_set.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for invoice_rule_set.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of invoice_rule_set.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for invoice_rule_set.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     *
     * @return the value of invoice_rule_set.bank_account_name
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     * @param bankAccountName the value for invoice_rule_set.bank_account_name
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     *
     * @return the value of invoice_rule_set.mailing_address
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     * @param mailingAddress the value for invoice_rule_set.mailing_address
     */
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of invoice_rule_set.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for invoice_rule_set.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of invoice_rule_set.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for invoice_rule_set.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #billingMethod}
     *
     * @return the value of invoice_rule_set.billing_method
     */
    public String getBillingMethod() {
        return billingMethod;
    }

    /**
     * 
     * {@linkplain #billingMethod}
     * @param billingMethod the value for invoice_rule_set.billing_method
     */
    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     *
     * @return the value of invoice_rule_set.shop_group_no
     */
    public String getShopGroupNo() {
        return shopGroupNo;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     * @param shopGroupNo the value for invoice_rule_set.shop_group_no
     */
    public void setShopGroupNo(String shopGroupNo) {
        this.shopGroupNo = shopGroupNo;
    }

    public String getShopGroupName() {
		return shopGroupName;
	}

	public void setShopGroupName(String shopGroupName) {
		this.shopGroupName = shopGroupName;
	}

	/**
     * 
     * {@linkplain #invoiceTempNo}
     *
     * @return the value of invoice_rule_set.invoice_temp_no
     */
    public String getInvoiceTempNo() {
        return invoiceTempNo;
    }

    /**
     * 
     * {@linkplain #invoiceTempNo}
     * @param invoiceTempNo the value for invoice_rule_set.invoice_temp_no
     */
    public void setInvoiceTempNo(String invoiceTempNo) {
        this.invoiceTempNo = invoiceTempNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of invoice_rule_set.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for invoice_rule_set.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #invoiceTempName}
     *
     * @return the value of invoice_rule_set.invoiceTempName
     */
	public String getInvoiceTempName() {
		return invoiceTempName;
	}
	
	/**
     * 
     * {@linkplain #invoiceTempName}
     * @param remark the value for invoice_rule_set.invoiceTempName
     */
	public void setInvoiceTempName(String invoiceTempName) {
		this.invoiceTempName = invoiceTempName;
	}
    
    
}