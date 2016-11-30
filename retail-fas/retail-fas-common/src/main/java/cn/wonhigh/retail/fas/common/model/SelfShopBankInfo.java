package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-10 11:30:36
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
public class SelfShopBankInfo extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6532562873024165259L;

    /**
     * 结算公司编码
     */
    private String companyNo;
    
    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shopName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 店铺账号
     */
    private String shopAccount;

    /**
     * 存现账号
     */
    private String depositAccount;

    /**
     * 终端号
     */
    private String terminalNumber;

    /**
     * 刷卡行
     */
    private String creditCardBank;

    /**
     * 刷卡账号
     */
    private String creditCardAccount;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of self_shop_bank_info.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for self_shop_bank_info.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of self_shop_bank_info.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for self_shop_bank_info.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of self_shop_bank_info.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for self_shop_bank_info.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of self_shop_bank_info.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for self_shop_bank_info.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of self_shop_bank_info.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for self_shop_bank_info.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #shopAccount}
     *
     * @return the value of self_shop_bank_info.shop_account
     */
    public String getShopAccount() {
        return shopAccount;
    }

    /**
     * 
     * {@linkplain #shopAccount}
     * @param shopAccount the value for self_shop_bank_info.shop_account
     */
    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    /**
     * 
     * {@linkplain #depositAccount}
     *
     * @return the value of self_shop_bank_info.deposit_account
     */
    public String getDepositAccount() {
        return depositAccount;
    }

    /**
     * 
     * {@linkplain #depositAccount}
     * @param depositAccount the value for self_shop_bank_info.deposit_account
     */
    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     *
     * @return the value of self_shop_bank_info.terminal_number
     */
    public String getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     * @param terminalNumber the value for self_shop_bank_info.terminal_number
     */
    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    /**
     * 
     * {@linkplain #creditCardBank}
     *
     * @return the value of self_shop_bank_info.credit_card_bank
     */
    public String getCreditCardBank() {
        return creditCardBank;
    }

    /**
     * 
     * {@linkplain #creditCardBank}
     * @param creditCardBank the value for self_shop_bank_info.credit_card_bank
     */
    public void setCreditCardBank(String creditCardBank) {
        this.creditCardBank = creditCardBank;
    }

    /**
     * 
     * {@linkplain #creditCardAccount}
     *
     * @return the value of self_shop_bank_info.credit_card_account
     */
    public String getCreditCardAccount() {
        return creditCardAccount;
    }

    /**
     * 
     * {@linkplain #creditCardAccount}
     * @param creditCardAccount the value for self_shop_bank_info.credit_card_account
     */
    public void setCreditCardAccount(String creditCardAccount) {
        this.creditCardAccount = creditCardAccount;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of self_shop_bank_info.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for self_shop_bank_info.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of self_shop_bank_info.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for self_shop_bank_info.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
}