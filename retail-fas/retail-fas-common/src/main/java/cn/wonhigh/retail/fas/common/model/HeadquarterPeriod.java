package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-08 10:17:18
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
public class HeadquarterPeriod extends FasBaseModel implements SequenceStrId {

    /**
	 * ID
	 */
	private static final long serialVersionUID = 1L;
	 /**
     * 公司编码
     */
	private String companyNo;
	 /**
     * 公司名称
     */
    private String companyName;
    /**
     * 结账品牌部编码
     */
    private String brandUnitNo;
    /**
     * 结账品牌部名称
     */
    private String brandUnitName;
    /**
     * 结账日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date supplierSettleTime;


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

    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    public String getBrandUnitName() {
        return brandUnitName;
    }

    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    public Date getSupplierSettleTime() {
        return supplierSettleTime;
    }

    public void setSupplierSettleTime(Date supplierSettleTime) {
        this.supplierSettleTime = supplierSettleTime;
    }

}