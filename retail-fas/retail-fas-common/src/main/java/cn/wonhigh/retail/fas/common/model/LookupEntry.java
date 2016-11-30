package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-22 12:13:41
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
public class LookupEntry implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5574494741197673501L;

	/**
     * 字典项编号
     */
    private String lookupEntryNo;

    /**
     * 字典ID
     */
    private Integer lookupId;

    /**
     * 特征码
     */
    private String opcode;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 序号
     */
    private Integer orderNo;

    /**
     * 字典类型(1 系统;0 普通)
     */
    private String type;

    /**
     * 状态(1正常,0无效)
     */
    private Byte status;

    /**
     * 是否选中
     */
    private String defaultFlag;

    /**
     * 创建人
     */
    private String createUser;

    
    private String organTypeNo;
    
    public String getOrganTypeNo() {
		return organTypeNo;
	}

	public void setOrganTypeNo(String organTypeNo) {
		this.organTypeNo = organTypeNo;
	}

	/**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date updateTime;

    public LookupEntry() {}

	public LookupEntry(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
    /**
     * 
     * {@linkplain #lookupEntryNo}
     *
     * @return the value of lookup_entry.lookup_entry_no
     */
    public String getLookupEntryNo() {
        return lookupEntryNo;
    }

    /**
     * 
     * {@linkplain #lookupEntryNo}
     * @param lookupEntryNo the value for lookup_entry.lookup_entry_no
     */
    public void setLookupEntryNo(String lookupEntryNo) {
        this.lookupEntryNo = lookupEntryNo;
    }

    /**
     * 
     * {@linkplain #lookupId}
     *
     * @return the value of lookup_entry.lookup_id
     */
    public Integer getLookupId() {
        return lookupId;
    }

    /**
     * 
     * {@linkplain #lookupId}
     * @param lookupId the value for lookup_entry.lookup_id
     */
    public void setLookupId(Integer lookupId) {
        this.lookupId = lookupId;
    }

    /**
     * 
     * {@linkplain #opcode}
     *
     * @return the value of lookup_entry.opcode
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 
     * {@linkplain #opcode}
     * @param opcode the value for lookup_entry.opcode
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of lookup_entry.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for lookup_entry.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of lookup_entry.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for lookup_entry.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of lookup_entry.order_no
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for lookup_entry.order_no
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of lookup_entry.type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * {@linkplain #type}
     * @param type the value for lookup_entry.type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of lookup_entry.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for lookup_entry.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #defaultFlag}
     *
     * @return the value of lookup_entry.default_flag
     */
    public String getDefaultFlag() {
        return defaultFlag;
    }

    /**
     * 
     * {@linkplain #defaultFlag}
     * @param defaultFlag the value for lookup_entry.default_flag
     */
    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of lookup_entry.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for lookup_entry.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of lookup_entry.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for lookup_entry.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of lookup_entry.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for lookup_entry.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of lookup_entry.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for lookup_entry.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}