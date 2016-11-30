package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ShopDepositAccountExportFormat;

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
@ExportFormat(className=ShopDepositAccountExportFormat.class)
public class SelfShopDepositAccount extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
     * 存现账号
     */
    private String depositAccount;

    /**
     * 发卡行
     */
    private String bank;

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of self_shop_deposit_account.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for self_shop_deposit_account.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of self_shop_deposit_account.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for self_shop_deposit_account.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of self_shop_deposit_account.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for self_shop_deposit_account.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of self_shop_deposit_account.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for self_shop_deposit_account.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of self_shop_deposit_account.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for self_shop_deposit_account.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of self_shop_deposit_account.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for self_shop_deposit_account.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #depositAccount}
     *
     * @return the value of self_shop_deposit_account.deposit_account
     */
    public String getDepositAccount() {
        return depositAccount;
    }

    /**
     * 
     * {@linkplain #depositAccount}
     * @param depositAccount the value for self_shop_deposit_account.deposit_account
     */
    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    /**
     * 
     * {@linkplain #bank}
     *
     * @return the value of self_shop_deposit_account.bank
     */
    public String getBank() {
        return bank;
    }

    /**
     * 
     * {@linkplain #bank}
     * @param bank the value for self_shop_deposit_account.bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

}