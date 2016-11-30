package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
public class BillShopBalanceInvoiceSource implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6518756566518214652L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 发票申请单号
     */
    private String invoiceapplyNo;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 开票金额
     */
    private BigDecimal billingAmount;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_invoice_source.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_invoice_source.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_invoice_source.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_invoice_source.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #invoiceapplyNo}
     *
     * @return the value of bill_shop_balance_invoice_source.invoiceapply_no
     */
    public String getInvoiceapplyNo() {
        return invoiceapplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceapplyNo}
     * @param invoiceapplyNo the value for bill_shop_balance_invoice_source.invoiceapply_no
     */
    public void setInvoiceapplyNo(String invoiceapplyNo) {
        this.invoiceapplyNo = invoiceapplyNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_invoice_source.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_invoice_source.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_invoice_source.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_invoice_source.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_shop_balance_invoice_source.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_shop_balance_invoice_source.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }
}