package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 保证金罚没通知单 
 * @author admin
 * @date  2014-09-22 11:59:28
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
@ExportFormat(className=AbstractExportFormat.class)
public class BillCustFineNt extends FasBaseModel {

	private static final long serialVersionUID = -5161379224996607447L;

	/**
     * 单据编码
     */
    private String billNo;

    /**
     * 结算主体编码
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 保证金金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal marginAmount;

    /**
     * 已收保证金金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal recedMarginAmount;

    /**
     * 罚没金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal fineAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_cust_fine_nt.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_cust_fine_nt.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_cust_fine_nt.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_cust_fine_nt.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_cust_fine_nt.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_cust_fine_nt.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of bill_cust_fine_nt.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for bill_cust_fine_nt.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of bill_cust_fine_nt.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for bill_cust_fine_nt.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_cust_fine_nt.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_cust_fine_nt.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     *
     * @return the value of bill_cust_fine_nt.margin_amount
     */
    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     * @param marginAmount the value for bill_cust_fine_nt.margin_amount
     */
    public void setMarginAmount(BigDecimal marginAmount) {
        this.marginAmount = marginAmount;
    }

    /**
     * 
     * {@linkplain #recedMarginAmount}
     *
     * @return the value of bill_cust_fine_nt.reced_margin_amount
     */
    public BigDecimal getRecedMarginAmount() {
        return recedMarginAmount;
    }

    /**
     * 
     * {@linkplain #recedMarginAmount}
     * @param recedMarginAmount the value for bill_cust_fine_nt.reced_margin_amount
     */
    public void setRecedMarginAmount(BigDecimal recedMarginAmount) {
        this.recedMarginAmount = recedMarginAmount;
    }

    /**
     * 
     * {@linkplain #fineAmount}
     *
     * @return the value of bill_cust_fine_nt.fine_amount
     */
    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    /**
     * 
     * {@linkplain #fineAmount}
     * @param fineAmount the value for bill_cust_fine_nt.fine_amount
     */
    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_cust_fine_nt.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_cust_fine_nt.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}