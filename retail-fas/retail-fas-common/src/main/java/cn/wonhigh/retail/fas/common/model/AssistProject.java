package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 辅助项目 
 * @author ouyang.zm
 * @date  2014-10-29 15:03:27
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
public class AssistProject extends FasBaseModel implements SequenceStrId {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7919481268236812925L;

	/**
     * 辅助项目编码
     */
    private String code;

    /**
     * 辅助项目名称
     */
    private String name;

    /**
     * 辅助项目类型
     */
    private Byte type;

    /**
     * 启用标志 1、启用 0、未启用
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

	/**
     * 公司编码
     */
    private String companyNo;

    /**
	 * 扩展
	 */
	private String companyName;
	
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

	/**
     * 
     * {@linkplain #code}
     *
     * @return the value of assist_project.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for assist_project.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of assist_project.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for assist_project.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of assist_project.type
     */
    public Byte getType() {
        return type;
    }

    /**
     * 
     * {@linkplain #type}
     * @param type the value for assist_project.type
     */
    public void setType(Byte type) {
        this.type = type;
    }


    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of assist_project.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for assist_project.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of assist_project.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for assist_project.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of assist_project.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for assist_project.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of assist_project.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for assist_project.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}