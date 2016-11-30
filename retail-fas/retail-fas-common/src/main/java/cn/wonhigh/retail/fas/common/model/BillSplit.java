package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
public class BillSplit implements Serializable {
	
	private static final long serialVersionUID = 3043705345195990778L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 拆分后的单据编码
     */
    private String billNo;

    /**
     * 原单据编码
     */
    private String refBillNo;

    /**
     * 卖方编码
     */
    private String salerNo;

    /**
     * 买方编码
     */
    private String buyerNo;

    /**
     * 单据标志(0：应收， 1：应付)
     */
    private Integer billFlag;

    /**
     * 发货总数量
     */
    private Integer totalQty;

    /**
     * 发货总金额(不含税)
     */
    private BigDecimal totalAmount;

    /**
     * 税收总额
     */
    private BigDecimal totalTaxAmount;

    /**
     * 发货日期
     */
    private Date sendOutDate;

    /**
     * 对账标志
     */
    private Integer reconFlag;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_split.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_split.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_split.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_split.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_split.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_split.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_split.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_split.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_split.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_split.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public Integer getBillFlag() {
		return billFlag;
	}

	public void setBillFlag(Integer billFlag) {
		this.billFlag = billFlag;
	}

	/**
     * 
     * {@linkplain #totalQty}
     *
     * @return the value of bill_split.total_qty
     */
    public Integer getTotalQty() {
        return totalQty;
    }

    /**
     * 
     * {@linkplain #totalQty}
     * @param totalQty the value for bill_split.total_qty
     */
    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    /**
     * 
     * {@linkplain #totalAmount}
     *
     * @return the value of bill_split.total_amount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 
     * {@linkplain #totalAmount}
     * @param totalAmount the value for bill_split.total_amount
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 
     * {@linkplain #totalTaxAmount}
     *
     * @return the value of bill_split.total_tax_amount
     */
    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    /**
     * 
     * {@linkplain #totalTaxAmount}
     * @param totalTaxAmount the value for bill_split.total_tax_amount
     */
    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    /**
     * 
     * {@linkplain #sendOutDate}
     *
     * @return the value of bill_split.send_out_date
     */
    public Date getSendOutDate() {
        return sendOutDate;
    }

    /**
     * 
     * {@linkplain #sendOutDate}
     * @param sendOutDate the value for bill_split.send_out_date
     */
    public void setSendOutDate(Date sendOutDate) {
        this.sendOutDate = sendOutDate;
    }

    /**
     * 
     * {@linkplain #reconFlag}
     *
     * @return the value of bill_split.recon_flag
     */
    public Integer getReconFlag() {
        return reconFlag;
    }

    /**
     * 
     * {@linkplain #reconFlag}
     * @param reconFlag the value for bill_split.recon_flag
     */
    public void setReconFlag(Integer reconFlag) {
        this.reconFlag = reconFlag;
    }
}