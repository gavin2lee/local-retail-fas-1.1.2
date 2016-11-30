package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
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
public class BillShopBalanceBack extends FasBaseModel {

	private static final long serialVersionUID = 2508318743412601223L;

	/**
     * 单据编号
     */
    private String billNo;
    
    private String rootDiffId;

    /**
     * 差异编号
     */
    private String diffBillNo;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 回款日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date backDate;

    /**
     * 回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal backAmount;

    /**
     * 备注
     */
    private String remark;
    
    public String getRootDiffId() {
		return rootDiffId;
	}

	public void setRootDiffId(String rootDiffId) {
		this.rootDiffId = rootDiffId;
	}

	/**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_back.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_back.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #diffBillNo}
     *
     * @return the value of bill_shop_balance_back.diff_bill_no
     */
    public String getDiffBillNo() {
        return diffBillNo;
    }

    /**
     * 
     * {@linkplain #diffBillNo}
     * @param diffBillNo the value for bill_shop_balance_back.diff_bill_no
     */
    public void setDiffBillNo(String diffBillNo) {
        this.diffBillNo = diffBillNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_back.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_back.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #backDate}
     *
     * @return the value of bill_shop_balance_back.back_date
     */
    public Date getBackDate() {
        return backDate;
    }

    /**
     * 
     * {@linkplain #backDate}
     * @param backDate the value for bill_shop_balance_back.back_date
     */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    /**
     * 
     * {@linkplain #backAmount}
     *
     * @return the value of bill_shop_balance_back.back_amount
     */
    public BigDecimal getBackAmount() {
        return backAmount;
    }

    /**
     * 
     * {@linkplain #backAmount}
     * @param backAmount the value for bill_shop_balance_back.back_amount
     */
    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_back.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_back.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}