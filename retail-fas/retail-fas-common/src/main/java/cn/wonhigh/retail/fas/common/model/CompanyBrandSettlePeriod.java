package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-13 12:04:21
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
public class CompanyBrandSettlePeriod extends FasBaseModel implements SequenceStrId {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7585989717952350701L;
	/**
     * 结账公司编码
     */
    private String companyNo;
    /**
     * 结账公司编码
     */
    private String companyName;
    /**
     * 结账品牌编码
     */
    private String brandNo;
    /**
     * 结账品牌编码
     */
    private String brandName;
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

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getSupplierSettleTime() {
        return supplierSettleTime;
    }

    public void setSupplierSettleTime(Date supplierSettleTime) {
        this.supplierSettleTime = supplierSettleTime;
    }

    public Date getAccountSettleTime() {
        return accountSettleTime;
    }

    public void setAccountSettleTime(Date accountSettleTime) {
        this.accountSettleTime = accountSettleTime;
    }

    public Date getSaleSettleTime() {
        return saleSettleTime;
    }

    public void setSaleSettleTime(Date saleSettleTime) {
        this.saleSettleTime = saleSettleTime;
    }

    public Date getTransferSettleTime() {
        return transferSettleTime;
    }

    public void setTransferSettleTime(Date transferSettleTime) {
        this.transferSettleTime = transferSettleTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}