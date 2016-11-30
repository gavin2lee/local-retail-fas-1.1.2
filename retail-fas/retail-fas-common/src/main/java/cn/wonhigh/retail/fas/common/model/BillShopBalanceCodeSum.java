package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-06-26 19:11:14
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
public class BillShopBalanceCodeSum implements Serializable {
	
	private static final long serialVersionUID = 3028253735818532353L;

	/**
     * 分库标识
     */
    private String shardingFlag;
	
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
     * 结算码
     */
    private String billingCode;

    /**
     * 销售金额收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;

    /**
     * 扣率,如17.00代表17.00%
     */
    private BigDecimal discount;

    /**
     * 扣率名称
     */
    private String discountName;

    /**
     * 扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductAmount;

    public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_code_sum.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_code_sum.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_code_sum.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_code_sum.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_code_sum.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_code_sum.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_code_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_code_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_code_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_code_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_code_sum.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_code_sum.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance_code_sum.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance_code_sum.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_code_sum.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_code_sum.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_code_sum.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_code_sum.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance_code_sum.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance_code_sum.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_code_sum.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_code_sum.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_code_sum.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_code_sum.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_code_sum.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_code_sum.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #billingCode}
     *
     * @return the value of bill_shop_balance_code_sum.billing_code
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * 
     * {@linkplain #billingCode}
     * @param billingCode the value for bill_shop_balance_code_sum.billing_code
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of bill_shop_balance_code_sum.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for bill_shop_balance_code_sum.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of bill_shop_balance_code_sum.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for bill_shop_balance_code_sum.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #discountName}
     *
     * @return the value of bill_shop_balance_code_sum.discount_name
     */
    public String getDiscountName() {
        return discountName;
    }

    /**
     * 
     * {@linkplain #discountName}
     * @param discountName the value for bill_shop_balance_code_sum.discount_name
     */
    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     *
     * @return the value of bill_shop_balance_code_sum.deduct_amount
     */
    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     * @param deductAmount the value for bill_shop_balance_code_sum.deduct_amount
     */
    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }
}