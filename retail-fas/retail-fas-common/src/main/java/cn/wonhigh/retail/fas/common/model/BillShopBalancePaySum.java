package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
public class BillShopBalancePaySum implements Serializable {
    /**
	 * 序列号ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 结算单据编号
     */
    private String balanceNo;

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
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 月份
     */
    private String month;

    /**
     * 结算起始日期
     */
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    private Date balanceEndDate;

    /**
     * 支付方式代号收款方式
     */
    private String payCode;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 支付方式编号,卡号、券号、结算号
     */
    private String paywayNum;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 开票金额
     */
    private BigDecimal billingAmount;

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_pay_sum.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_pay_sum.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_pay_sum.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_pay_sum.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_pay_sum.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_pay_sum.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_pay_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_pay_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_pay_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_pay_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_pay_sum.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_pay_sum.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance_pay_sum.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance_pay_sum.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_pay_sum.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_pay_sum.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_pay_sum.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_pay_sum.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance_pay_sum.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance_pay_sum.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_pay_sum.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_pay_sum.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_pay_sum.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_pay_sum.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_pay_sum.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_pay_sum.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #payCode}
     *
     * @return the value of bill_shop_balance_pay_sum.pay_code
     */
    public String getPayCode() {
        return payCode;
    }

    /**
     * 
     * {@linkplain #payCode}
     * @param payCode the value for bill_shop_balance_pay_sum.pay_code
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    /**
     * 
     * {@linkplain #payName}
     *
     * @return the value of bill_shop_balance_pay_sum.pay_name
     */
    public String getPayName() {
        return payName;
    }

    /**
     * 
     * {@linkplain #payName}
     * @param payName the value for bill_shop_balance_pay_sum.pay_name
     */
    public void setPayName(String payName) {
        this.payName = payName;
    }

    /**
     * 
     * {@linkplain #paywayNum}
     *
     * @return the value of bill_shop_balance_pay_sum.payway_num
     */
    public String getPaywayNum() {
        return paywayNum;
    }

    /**
     * 
     * {@linkplain #paywayNum}
     * @param paywayNum the value for bill_shop_balance_pay_sum.payway_num
     */
    public void setPaywayNum(String paywayNum) {
        this.paywayNum = paywayNum;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_shop_balance_pay_sum.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_shop_balance_pay_sum.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_shop_balance_pay_sum.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_shop_balance_pay_sum.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }
}