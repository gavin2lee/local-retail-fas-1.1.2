package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-05 14:55:50
 * @version 1.0.8
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class TransferBalanceDate extends FasBaseModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 752283816679929173L;


    /**
     * 调出公司编码
     */
    private String salerNo;

    /**
     * 调出公司名称
     */
    private String salerName;

    /**
     * 调入公司编码
     */
    private String buyerNo;

    /**
     * 调入公司名称
     */
    private String buyerName;

    /**
     * 结算月份
     */
    private String balanceMonth;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;

    /**
     * 开票标志 (0-未开票，1-已开票)
     */
    private Byte invoiceFlag;

    /**
     * 结算标志 (0-未生成，1-已生成)
     */
    private Byte balanceFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of transfer_balance_date.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for transfer_balance_date.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of transfer_balance_date.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for transfer_balance_date.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of transfer_balance_date.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for transfer_balance_date.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of transfer_balance_date.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for transfer_balance_date.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #balanceMonth}
     *
     * @return the value of transfer_balance_date.balance_month
     */
    public String getBalanceMonth() {
        return balanceMonth;
    }

    /**
     * 
     * {@linkplain #balanceMonth}
     * @param balanceMonth the value for transfer_balance_date.balance_month
     */
    public void setBalanceMonth(String balanceMonth) {
        this.balanceMonth = balanceMonth;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of transfer_balance_date.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for transfer_balance_date.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of transfer_balance_date.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for transfer_balance_date.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #invoiceFlag}
     *
     * @return the value of transfer_balance_date.invoice_flag
     */
    public Byte getInvoiceFlag() {
        return invoiceFlag;
    }

    /**
     * 
     * {@linkplain #invoiceFlag}
     * @param invoiceFlag the value for transfer_balance_date.invoice_flag
     */
    public void setInvoiceFlag(Byte invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
    }

    /**
     * 
     * {@linkplain #balanceFlag}
     *
     * @return the value of transfer_balance_date.balance_flag
     */
    public Byte getBalanceFlag() {
        return balanceFlag;
    }

    /**
     * 
     * {@linkplain #balanceFlag}
     * @param balanceFlag the value for transfer_balance_date.balance_flag
     */
    public void setBalanceFlag(Byte balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of transfer_balance_date.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for transfer_balance_date.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}