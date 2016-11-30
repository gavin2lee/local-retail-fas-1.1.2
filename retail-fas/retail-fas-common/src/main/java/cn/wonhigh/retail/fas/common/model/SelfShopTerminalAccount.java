package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ShopTerminalAccountExportFormat;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-17 16:59:10
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@ExportFormat(className=ShopTerminalAccountExportFormat.class)
public class SelfShopTerminalAccount extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 44639730312344150L;

	/**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
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
     * 商户编码
     */
    private String merchantsNo;

    /**
     * 终端号
     */
    private String terminalNumber;

    /**
     * 刷卡行
     */
    private String creditCardBank;

    /**
     * 终端绑定账号
     */
    private String creditCardAccount;

    /**
     * 支付方式
     */
    private String creditCardType;

    /**
     * 费率
     */
    private BigDecimal creditCardRate;

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of self_shop_terminal_account.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for self_shop_terminal_account.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of self_shop_terminal_account.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for self_shop_terminal_account.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of self_shop_terminal_account.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for self_shop_terminal_account.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of self_shop_terminal_account.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for self_shop_terminal_account.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of self_shop_terminal_account.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for self_shop_terminal_account.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of self_shop_terminal_account.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for self_shop_terminal_account.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #merchantsNo}
     *
     * @return the value of self_shop_terminal_account.merchants_no
     */
    public String getMerchantsNo() {
        return merchantsNo;
    }

    /**
     * 
     * {@linkplain #merchantsNo}
     * @param merchantsNo the value for self_shop_terminal_account.merchants_no
     */
    public void setMerchantsNo(String merchantsNo) {
        this.merchantsNo = merchantsNo;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     *
     * @return the value of self_shop_terminal_account.terminal_number
     */
    public String getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     * @param terminalNumber the value for self_shop_terminal_account.terminal_number
     */
    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    /**
     * 
     * {@linkplain #creditCardBank}
     *
     * @return the value of self_shop_terminal_account.credit_card_bank
     */
    public String getCreditCardBank() {
        return creditCardBank;
    }

    /**
     * 
     * {@linkplain #creditCardBank}
     * @param creditCardBank the value for self_shop_terminal_account.credit_card_bank
     */
    public void setCreditCardBank(String creditCardBank) {
        this.creditCardBank = creditCardBank;
    }

    /**
     * 
     * {@linkplain #creditCardAccount}
     *
     * @return the value of self_shop_terminal_account.credit_card_account
     */
    public String getCreditCardAccount() {
        return creditCardAccount;
    }

    /**
     * 
     * {@linkplain #creditCardAccount}
     * @param creditCardAccount the value for self_shop_terminal_account.credit_card_account
     */
    public void setCreditCardAccount(String creditCardAccount) {
        this.creditCardAccount = creditCardAccount;
    }

    /**
     * 
     * {@linkplain #creditCardType}
     *
     * @return the value of self_shop_terminal_account.credit_card_type
     */
    public String getCreditCardType() {
        return creditCardType;
    }

    /**
     * 
     * {@linkplain #creditCardType}
     * @param creditCardType the value for self_shop_terminal_account.credit_card_type
     */
    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    /**
     * 
     * {@linkplain #creditCardRate}
     *
     * @return the value of self_shop_terminal_account.credit_card_rate
     */
    public BigDecimal getCreditCardRate() {
        return creditCardRate;
    }

    /**
     * 
     * {@linkplain #creditCardRate}
     * @param creditCardRate the value for self_shop_terminal_account.credit_card_rate
     */
    public void setCreditCardRate(BigDecimal creditCardRate) {
        this.creditCardRate = creditCardRate;
    }

}