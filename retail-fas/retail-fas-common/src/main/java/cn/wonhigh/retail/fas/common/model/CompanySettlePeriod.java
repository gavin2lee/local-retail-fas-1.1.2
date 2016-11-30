package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2014-11-21 16:27:57
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
public class CompanySettlePeriod extends FasBaseModel implements SequenceStrId {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5457851073818408856L;

	/**
     * 结账公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 厂商结账日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date supplierSettleTime;

    /**
     * 财务结账日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date accountSettleTime;

    /**
     * 销售结账日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date saleSettleTime;

    /**
     * 销售结账日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date transferSettleTime;
    
    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of company_settle_period.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for company_settle_period.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of company_settle_period.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for company_settle_period.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #supplierSettleTime}
     *
     * @return the value of company_settle_period.supplier_settle_time
     */
    public Date getSupplierSettleTime() {
        return supplierSettleTime;
    }

    /**
     * 
     * {@linkplain #supplierSettleTime}
     * @param supplierSettleTime the value for company_settle_period.supplier_settle_time
     */
    public void setSupplierSettleTime(Date supplierSettleTime) {
        this.supplierSettleTime = supplierSettleTime;
    }

    /**
     * 
     * {@linkplain #accountSettleTime}
     *
     * @return the value of company_settle_period.account_settle_time
     */
    public Date getAccountSettleTime() {
        return accountSettleTime;
    }

    /**
     * 
     * {@linkplain #accountSettleTime}
     * @param accountSettleTime the value for company_settle_period.account_settle_time
     */
    public void setAccountSettleTime(Date accountSettleTime) {
        this.accountSettleTime = accountSettleTime;
    }

    /**
     * 
     * {@linkplain #saleSettleTime}
     *
     * @return the value of company_settle_period.sale_settle_time
     */
    public Date getSaleSettleTime() {
        return saleSettleTime;
    }

    /**
     * 
     * {@linkplain #saleSettleTime}
     * @param saleSettleTime the value for company_settle_period.sale_settle_time
     */
    public void setSaleSettleTime(Date saleSettleTime) {
        this.saleSettleTime = saleSettleTime;
    }

    public Date getTransferSettleTime() {
		return transferSettleTime;
	}

	public void setTransferSettleTime(Date transferSettleTime) {
		this.transferSettleTime = transferSettleTime;
	}

	/**
     * 
     * {@linkplain #remark}
     *
     * @return the value of company_settle_period.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for company_settle_period.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}