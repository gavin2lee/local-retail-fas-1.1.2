package cn.wonhigh.retail.fas.core;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.FasLogoutStatusEnum;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 财务辅助系统基类model
 * 
 * @author 杨勇
 * @date 2014-6-23 上午10:15:41
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public  class BaseEntry<T> implements Serializable {

	private static final long serialVersionUID = -8025893432901705532L;
	
	
	/** 建档人 */
	private String createUser;
	
	/** 建档时间 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date createTime;
	
	/** 修改人 */
	private String updateUser;
	
	/** 修改时间 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date updateTime;
	
	/** 审核状态 */
    private Integer auditStatus;
    
    /** 审核状态名称 */
    private String auditStatusName;

    /** 注销状态 */
    private Integer status;
    
    /** 审核人 */
    private String auditor;
    
    /** 审核时间 */
//    @JsonSerialize(using = JsonDateSerializer$10.class)
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date auditTime;
    
    /** 单据状态名称 */
	private String statusName;
	
	/** 单据编码 */
	private String billNo;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getAuditStatusName() {
		for(FasAduitStatusEnum s : FasAduitStatusEnum.values()) {
			if(getAuditStatus() != null && 
					s.getValue().intValue() == getAuditStatus().intValue()) {
				return s.getText();
			}
		}
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public String getStatusName() {
		for(FasLogoutStatusEnum s : FasLogoutStatusEnum.values()) {
			if(getStatus() != null && 
					s.getValue().intValue() == getStatus().intValue()) {
				return s.getText();
			}
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}
