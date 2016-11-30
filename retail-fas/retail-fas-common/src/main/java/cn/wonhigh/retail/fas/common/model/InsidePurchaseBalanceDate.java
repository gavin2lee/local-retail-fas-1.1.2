package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-02-02 20:00:10
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
@ExportFormat(className=AbstractExportFormat.class)
public class InsidePurchaseBalanceDate extends FasBaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2155842546423314049L;

    /**
     * 结算主体编号
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

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
     * 否生成开票申请(0-未生成，1-已生成)
     */
    private Integer invoiceFlag;
    
    /**
     * 否生成开票申请名称
     */
    private String invoiceFlagName;

    /**
     * 备注
     */
    private String remark;
    /**
     * 单据类型
     */
    private Integer billType;
    
    public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of inside_purchase_balance_date.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for inside_purchase_balance_date.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of inside_purchase_balance_date.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for inside_purchase_balance_date.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #balanceMonth}
     *
     * @return the value of inside_purchase_balance_date.balance_month
     */
    public String getBalanceMonth() {
        return balanceMonth;
    }

    /**
     * 
     * {@linkplain #balanceMonth}
     * @param balanceMonth the value for inside_purchase_balance_date.balance_month
     */
    public void setBalanceMonth(String balanceMonth) {
        this.balanceMonth = balanceMonth;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of inside_purchase_balance_date.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for inside_purchase_balance_date.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of inside_purchase_balance_date.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for inside_purchase_balance_date.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #invoiceFlag}
     *
     * @return the value of inside_purchase_balance_date.invoice_flag
     */
    public Integer getInvoiceFlag() {
        return invoiceFlag;
    }

    /**
     * 
     * {@linkplain #invoiceFlag}
     * @param invoiceFlag the value for inside_purchase_balance_date.invoice_flag
     */
    public void setInvoiceFlag(Integer invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
        if (null != invoiceFlag && invoiceFlag == 1) {
			setInvoiceFlagName("已开票");
		}else{
			setInvoiceFlagName("未开票");
		}
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of inside_purchase_balance_date.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for inside_purchase_balance_date.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getInvoiceFlagName() {
		return invoiceFlagName;
	}

	public void setInvoiceFlagName(String invoiceFlagName) {
		this.invoiceFlagName = invoiceFlagName;
	}

}