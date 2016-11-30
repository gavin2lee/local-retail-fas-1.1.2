package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

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
public class BillShopBalanceDaysaleSum implements Serializable {

	private static final long serialVersionUID = -4497989284303189817L;

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
     * 销售日期
     */
    private Date outDate;

    /**
     * 销售总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;

    /**
     * 支付方式代号
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
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal amount;
    
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
     * @return the value of bill_shop_balance_daysale_sum.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_daysale_sum.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_daysale_sum.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_daysale_sum.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_daysale_sum.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_daysale_sum.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_daysale_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_daysale_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_daysale_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_daysale_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_daysale_sum.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_daysale_sum.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance_daysale_sum.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance_daysale_sum.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_daysale_sum.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_daysale_sum.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_daysale_sum.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_daysale_sum.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance_daysale_sum.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance_daysale_sum.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #outDate}
     *
     * @return the value of bill_shop_balance_daysale_sum.out_date
     */
    public Date getOutDate() {
        return outDate;
    }

    /**
     * 
     * {@linkplain #outDate}
     * @param outDate the value for bill_shop_balance_daysale_sum.out_date
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of bill_shop_balance_daysale_sum.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for bill_shop_balance_daysale_sum.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #payCode}
     *
     * @return the value of bill_shop_balance_daysale_sum.pay_code
     */
    public String getPayCode() {
        return payCode;
    }

    /**
     * 
     * {@linkplain #payCode}
     * @param payCode the value for bill_shop_balance_daysale_sum.pay_code
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    /**
     * 
     * {@linkplain #payName}
     *
     * @return the value of bill_shop_balance_daysale_sum.pay_name
     */
    public String getPayName() {
        return payName;
    }

    /**
     * 
     * {@linkplain #payName}
     * @param payName the value for bill_shop_balance_daysale_sum.pay_name
     */
    public void setPayName(String payName) {
        this.payName = payName;
    }

    /**
     * 
     * {@linkplain #paywayNum}
     *
     * @return the value of bill_shop_balance_daysale_sum.payway_num
     */
    public String getPaywayNum() {
        return paywayNum;
    }

    /**
     * 
     * {@linkplain #paywayNum}
     * @param paywayNum the value for bill_shop_balance_daysale_sum.payway_num
     */
    public void setPaywayNum(String paywayNum) {
        this.paywayNum = paywayNum;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_shop_balance_daysale_sum.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_shop_balance_daysale_sum.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}