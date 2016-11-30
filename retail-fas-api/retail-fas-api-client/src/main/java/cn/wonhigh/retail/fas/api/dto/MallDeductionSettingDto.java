package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

public class MallDeductionSettingDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2309351521465099816L;
	/**
     * 单据编号
     */
    private String deductionNo;

    /**
     * 扣费编码
     */
    private String code;

    /**
     * 扣费名称
     */
    private String name;

    /**
     * 总账费用类别编码
     */
    private String costCode;

    /**
     * 总账费用类别名称
     */
    private String costName;

    /**
     * 扣费类型
     */
    private Byte type;


    /**
     * 建档人
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private String createUser;

    /**
     * 建档时间
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
    @JsonSerialize(using = JsonDateSerializer$19.class)  
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private String remark;

    

    /**
     * 
     * {@linkplain #deductionNo}
     *
     * @return the value of mall_deduction_setting.deduction_no
     */
    public String getDeductionNo() {
        return deductionNo;
    }

    /**
     * 
     * {@linkplain #deductionNo}
     * @param deductionNo the value for mall_deduction_setting.deduction_no
     */
    public void setDeductionNo(String deductionNo) {
        this.deductionNo = deductionNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of mall_deduction_setting.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for mall_deduction_setting.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of mall_deduction_setting.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for mall_deduction_setting.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #costCode}
     *
     * @return the value of mall_deduction_setting.cost_code
     */
    public String getCostCode() {
        return costCode;
    }

    /**
     * 
     * {@linkplain #costCode}
     * @param costCode the value for mall_deduction_setting.cost_code
     */
    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    /**
     * 
     * {@linkplain #costName}
     *
     * @return the value of mall_deduction_setting.cost_name
     */
    public String getCostName() {
        return costName;
    }

    /**
     * 
     * {@linkplain #costName}
     * @param costName the value for mall_deduction_setting.cost_name
     */
    public void setCostName(String costName) {
        this.costName = costName;
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of mall_deduction_setting.type
     */
    public Byte getType() {
        return type;
    }

    /**
     * 
     * {@linkplain #type}
     * @param type the value for mall_deduction_setting.type
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of mall_deduction_setting.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for mall_deduction_setting.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of mall_deduction_setting.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for mall_deduction_setting.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of mall_deduction_setting.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for mall_deduction_setting.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of mall_deduction_setting.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for mall_deduction_setting.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of mall_deduction_setting.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for mall_deduction_setting.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
