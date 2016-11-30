package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2015-04-22 09:29:45
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
public class DepositCashCumulativeDifference implements Serializable {
    /**
     * 流水号
     */
    private String id;

    /**
     * 门店编号
     */
    private String shopNo;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 销售日期
     */
    private Date saleOut;

    /**
     * 现金金额
     */
    private BigDecimal cashAmount;

    /**
     * 现金券金额
     */
    private BigDecimal cashCouponAmount;

    /**
     * 银行卡金额
     */
    private BigDecimal bankCardAmount;

    /**
     * 商场卡金额
     */
    private BigDecimal mallCardAmount;

    /**
     * 商场券金额
     */
    private BigDecimal mallCouponAmount;

    /**
     * 预收款金额
     */
    private BigDecimal expectCashAmount;

    /**
     * 外卡金额
     */
    private BigDecimal outsideCardAmount;

    /**
     * 其他金额
     */
    private BigDecimal otherAmount;

    /**
     * 销售总额
     */
    private BigDecimal saleTotalAmount;

    /**
     * 存现总额
     */
    private BigDecimal depositCashTotalAmount;

    /**
     * 当前存现差异
     */
    private BigDecimal currentDepositCashDifference;
    
    /**
     * 累计存现总差异额
     */
    private BigDecimal depositCashDifference;
    

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of deposit_cash_cumulative_difference.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for deposit_cash_cumulative_difference.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of deposit_cash_cumulative_difference.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for deposit_cash_cumulative_difference.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of deposit_cash_cumulative_difference.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for deposit_cash_cumulative_difference.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #saleOut}
     *
     * @return the value of deposit_cash_cumulative_difference.sale_out
     */
    public Date getSaleOut() {
        return saleOut;
    }

    /**
     * 
     * {@linkplain #saleOut}
     * @param saleOut the value for deposit_cash_cumulative_difference.sale_out
     */
    public void setSaleOut(Date saleOut) {
        this.saleOut = saleOut;
    }

    /**
     * 
     * {@linkplain #cashAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.cash_amount
     */
    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    /**
     * 
     * {@linkplain #cashAmount}
     * @param cashAmount the value for deposit_cash_cumulative_difference.cash_amount
     */
    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
     * 
     * {@linkplain #cashCouponAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.cash_coupon_amount
     */
    public BigDecimal getCashCouponAmount() {
        return cashCouponAmount;
    }

    /**
     * 
     * {@linkplain #cashCouponAmount}
     * @param cashCouponAmount the value for deposit_cash_cumulative_difference.cash_coupon_amount
     */
    public void setCashCouponAmount(BigDecimal cashCouponAmount) {
        this.cashCouponAmount = cashCouponAmount;
    }

    /**
     * 
     * {@linkplain #bankCardAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.bank_card_amount
     */
    public BigDecimal getBankCardAmount() {
        return bankCardAmount;
    }

    /**
     * 
     * {@linkplain #bankCardAmount}
     * @param bankCardAmount the value for deposit_cash_cumulative_difference.bank_card_amount
     */
    public void setBankCardAmount(BigDecimal bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    /**
     * 
     * {@linkplain #mallCardAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.mall_card_amount
     */
    public BigDecimal getMallCardAmount() {
        return mallCardAmount;
    }

    /**
     * 
     * {@linkplain #mallCardAmount}
     * @param mallCardAmount the value for deposit_cash_cumulative_difference.mall_card_amount
     */
    public void setMallCardAmount(BigDecimal mallCardAmount) {
        this.mallCardAmount = mallCardAmount;
    }

    /**
     * 
     * {@linkplain #mallCouponAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.mall_coupon_amount
     */
    public BigDecimal getMallCouponAmount() {
        return mallCouponAmount;
    }

    /**
     * 
     * {@linkplain #mallCouponAmount}
     * @param mallCouponAmount the value for deposit_cash_cumulative_difference.mall_coupon_amount
     */
    public void setMallCouponAmount(BigDecimal mallCouponAmount) {
        this.mallCouponAmount = mallCouponAmount;
    }

    /**
     * 
     * {@linkplain #expectCashAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.expect_cash_amount
     */
    public BigDecimal getExpectCashAmount() {
        return expectCashAmount;
    }

    /**
     * 
     * {@linkplain #expectCashAmount}
     * @param expectCashAmount the value for deposit_cash_cumulative_difference.expect_cash_amount
     */
    public void setExpectCashAmount(BigDecimal expectCashAmount) {
        this.expectCashAmount = expectCashAmount;
    }

    /**
     * 
     * {@linkplain #outsideCardAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.outside_card_amount
     */
    public BigDecimal getOutsideCardAmount() {
        return outsideCardAmount;
    }

    /**
     * 
     * {@linkplain #outsideCardAmount}
     * @param outsideCardAmount the value for deposit_cash_cumulative_difference.outside_card_amount
     */
    public void setOutsideCardAmount(BigDecimal outsideCardAmount) {
        this.outsideCardAmount = outsideCardAmount;
    }

    /**
     * 
     * {@linkplain #otherAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.other_amount
     */
    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    /**
     * 
     * {@linkplain #otherAmount}
     * @param otherAmount the value for deposit_cash_cumulative_difference.other_amount
     */
    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }

    /**
     * 
     * {@linkplain #saleTotalAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.sale_total_amount
     */
    public BigDecimal getSaleTotalAmount() {
        return saleTotalAmount;
    }

    /**
     * 
     * {@linkplain #saleTotalAmount}
     * @param saleTotalAmount the value for deposit_cash_cumulative_difference.sale_total_amount
     */
    public void setSaleTotalAmount(BigDecimal saleTotalAmount) {
        this.saleTotalAmount = saleTotalAmount;
    }

    /**
     * 
     * {@linkplain #depositCashTotalAmount}
     *
     * @return the value of deposit_cash_cumulative_difference.deposit_cash_total_amount
     */
    public BigDecimal getDepositCashTotalAmount() {
        return depositCashTotalAmount;
    }

    /**
     * 
     * {@linkplain #depositCashTotalAmount}
     * @param depositCashTotalAmount the value for deposit_cash_cumulative_difference.deposit_cash_total_amount
     */
    public void setDepositCashTotalAmount(BigDecimal depositCashTotalAmount) {
        this.depositCashTotalAmount = depositCashTotalAmount;
    }

    /**
     * 
     * {@linkplain #depositCashDifference}
     *
     * @return the value of deposit_cash_cumulative_difference.deposit_cash_difference
     */
    public BigDecimal getDepositCashDifference() {
        return depositCashDifference;
    }

    /**
     * 
     * {@linkplain #depositCashDifference}
     * @param depositCashDifference the value for deposit_cash_cumulative_difference.deposit_cash_difference
     */
    public void setDepositCashDifference(BigDecimal depositCashDifference) {
        this.depositCashDifference = depositCashDifference;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of deposit_cash_cumulative_difference.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for deposit_cash_cumulative_difference.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public BigDecimal getCurrentDepositCashDifference() {
		return currentDepositCashDifference;
	}

	public void setCurrentDepositCashDifference(
			BigDecimal currentDepositCashDifference) {
		this.currentDepositCashDifference = currentDepositCashDifference;
	}
}