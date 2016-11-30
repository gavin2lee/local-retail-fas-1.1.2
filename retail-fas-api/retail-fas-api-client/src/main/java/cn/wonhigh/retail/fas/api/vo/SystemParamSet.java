package cn.wonhigh.retail.fas.api.vo;

import java.util.Date;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-06-13 14:53:25
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
public class SystemParamSet {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务组织控制级别类型 1、大区 2、公司 3、店铺
     */
    private Byte businessOrganType;

    /**
     * 业务组织编码
     */
    private String businessOrganNo;

    /**
     * 业务组织名称
     */
    private String businessOrganName;

    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数值
     */
    private String dtlValue;

    /**
     * 参数名称
     */
    private String dtlName;

    /**
     * 参数对象编码(备用)
     */
    private String paramObjNo;

    /**
     * 参数描述
     */
    private String paramDescription;

    /**
     * 控制类型 0、不控制 1、全部控制 2、局部控制
     */
    private Byte controlType;

    /**
     * 状态 1、启用 0、停用
     */
    private Byte status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of system_param_set.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for system_param_set.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #businessOrganType}
     *
     * @return the value of system_param_set.business_organ_type
     */
    public Byte getBusinessOrganType() {
        return businessOrganType;
    }

    /**
     * 
     * {@linkplain #businessOrganType}
     * @param businessOrganType the value for system_param_set.business_organ_type
     */
    public void setBusinessOrganType(Byte businessOrganType) {
        this.businessOrganType = businessOrganType;
    }

    /**
     * 
     * {@linkplain #businessOrganNo}
     *
     * @return the value of system_param_set.business_organ_no
     */
    public String getBusinessOrganNo() {
        return businessOrganNo;
    }

    /**
     * 
     * {@linkplain #businessOrganNo}
     * @param businessOrganNo the value for system_param_set.business_organ_no
     */
    public void setBusinessOrganNo(String businessOrganNo) {
        this.businessOrganNo = businessOrganNo;
    }

    /**
     * 
     * {@linkplain #businessOrganName}
     *
     * @return the value of system_param_set.business_organ_name
     */
    public String getBusinessOrganName() {
        return businessOrganName;
    }

    /**
     * 
     * {@linkplain #businessOrganName}
     * @param businessOrganName the value for system_param_set.business_organ_name
     */
    public void setBusinessOrganName(String businessOrganName) {
        this.businessOrganName = businessOrganName;
    }

    /**
     * 
     * {@linkplain #paramCode}
     *
     * @return the value of system_param_set.param_code
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * 
     * {@linkplain #paramCode}
     * @param paramCode the value for system_param_set.param_code
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    /**
     * 
     * {@linkplain #paramName}
     *
     * @return the value of system_param_set.param_name
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 
     * {@linkplain #paramName}
     * @param paramName the value for system_param_set.param_name
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 
     * {@linkplain #dtlValue}
     *
     * @return the value of system_param_set.dtl_value
     */
    public String getDtlValue() {
        return dtlValue;
    }

    /**
     * 
     * {@linkplain #dtlValue}
     * @param dtlValue the value for system_param_set.dtl_value
     */
    public void setDtlValue(String dtlValue) {
        this.dtlValue = dtlValue;
    }

    /**
     * 
     * {@linkplain #dtlName}
     *
     * @return the value of system_param_set.dtl_name
     */
    public String getDtlName() {
        return dtlName;
    }

    /**
     * 
     * {@linkplain #dtlName}
     * @param dtlName the value for system_param_set.dtl_name
     */
    public void setDtlName(String dtlName) {
        this.dtlName = dtlName;
    }

    /**
     * 
     * {@linkplain #paramObjNo}
     *
     * @return the value of system_param_set.param_obj_no
     */
    public String getParamObjNo() {
        return paramObjNo;
    }

    /**
     * 
     * {@linkplain #paramObjNo}
     * @param paramObjNo the value for system_param_set.param_obj_no
     */
    public void setParamObjNo(String paramObjNo) {
        this.paramObjNo = paramObjNo;
    }

    /**
     * 
     * {@linkplain #paramDescription}
     *
     * @return the value of system_param_set.param_description
     */
    public String getParamDescription() {
        return paramDescription;
    }

    /**
     * 
     * {@linkplain #paramDescription}
     * @param paramDescription the value for system_param_set.param_description
     */
    public void setParamDescription(String paramDescription) {
        this.paramDescription = paramDescription;
    }

    /**
     * 
     * {@linkplain #controlType}
     *
     * @return the value of system_param_set.control_type
     */
    public Byte getControlType() {
        return controlType;
    }

    /**
     * 
     * {@linkplain #controlType}
     * @param controlType the value for system_param_set.control_type
     */
    public void setControlType(Byte controlType) {
        this.controlType = controlType;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of system_param_set.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for system_param_set.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of system_param_set.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for system_param_set.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of system_param_set.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for system_param_set.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of system_param_set.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for system_param_set.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of system_param_set.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for system_param_set.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of system_param_set.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for system_param_set.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}