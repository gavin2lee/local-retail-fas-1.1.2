package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-13 16:29:02
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
public class SupplierGroupRel extends FasBaseModel implements SequenceStrId {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6964955939144067086L;

    /**
     * 供应商代号
     */
	@ExcelCell("B")
    private String supplierNo;

    /**
     * 供应商名称
     */
	@ExcelCell("C")
    private String supplierName;

    /**
     * 组代号
     */
    @ExcelCell("A")
    private String groupNo;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 启用日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date enableTime;

    /**
     * 终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date disableTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #groupNo}
     *
     * @return the value of supplier_group.group_no
     */
    public String getGroupNo() {
        return groupNo;
    }

    /**
     * 
     * {@linkplain #groupNo}
     * @param groupNo the value for supplier_group.group_no
     */
    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    /**
     * 
     * {@linkplain #groupName}
     *
     * @return the value of supplier_group.group_name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 
     * {@linkplain #groupName}
     * @param groupName the value for supplier_group.group_name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 
     * {@linkplain #enableTime}
     *
     * @return the value of supplier_group.enable_time
     */
    public Date getEnableTime() {
        return enableTime;
    }

    /**
     * 
     * {@linkplain #enableTime}
     * @param enableTime the value for supplier_group.enable_time
     */
    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    /**
     * 
     * {@linkplain #disableTime}
     *
     * @return the value of supplier_group.disable_time
     */
    public Date getDisableTime() {
        return disableTime;
    }

    /**
     * 
     * {@linkplain #disableTime}
     * @param disableTime the value for supplier_group.disable_time
     */
    public void setDisableTime(Date disableTime) {
        this.disableTime = disableTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of supplier_group.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for supplier_group.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of supplier_group_rel.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for supplier_group_rel.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of supplier_group_rel.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for supplier_group_rel.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

}