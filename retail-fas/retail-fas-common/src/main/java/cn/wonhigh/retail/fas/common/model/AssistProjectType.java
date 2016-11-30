package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 辅助项目类型
 * 
 * @author ouyang.zm
 * @date 2014-10-29 14:45:59
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class AssistProjectType extends FasBaseModel implements SequenceStrId {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537090005093196695L;

	/**
	 * 辅助项目类型编码
	 */
	private String typeCode;

	/**
	 * 辅助项目类型名称
	 */
	private String typeName;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
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
	 * {@linkplain #typeCode}
	 * 
	 * @return the value of assist_project_type.type_code
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * 
	 * {@linkplain #typeCode}
	 * 
	 * @param typeCode
	 *            the value for assist_project_type.type_code
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 
	 * {@linkplain #typeName}
	 * 
	 * @return the value of assist_project_type.type_name
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 
	 * {@linkplain #typeName}
	 * 
	 * @param typeName
	 *            the value for assist_project_type.type_name
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @return the value of assist_project_type.create_user
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @param createUser
	 *            the value for assist_project_type.create_user
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @return the value of assist_project_type.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @param createTime
	 *            the value for assist_project_type.create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @return the value of assist_project_type.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @param updateUser
	 *            the value for assist_project_type.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @return the value of assist_project_type.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @param updateTime
	 *            the value for assist_project_type.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}