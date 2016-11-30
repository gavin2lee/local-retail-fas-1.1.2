package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-20 11:53:39
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
public class OperateLog implements Serializable {
 
	private static final long serialVersionUID = 6058062506039373479L;

	/**
     * 主键ID
     */
    private String id;

    /**
     * 数据编码
     */
    private String dataNo;

    /**
     * 模块编码(枚举类表示)
     */
    private Integer moduleNo;
    
    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 操作编码(枚举表示:新增、修改、删除...)
     */
    private Integer operateNo;

    /**
     * 操作名称
     */
    private String operateName;
    
    /**
     * 操作状态
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 操作人
     */
    private String createUser;

    /**
     * 操作时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date createTime;
    
    /**
     *  操作状态(打回、通过)
     */
    private Integer operateStatus;
    
    /**
     * 操作状态名称
     */
    private String operateStatusName;

    public String getOperateStatusName() {
		return operateStatusName;
	}

	public void setOperateStatusName(String operateStatusName) {
		this.operateStatusName = operateStatusName;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getModuleName() {
    	for(OperateLogEnums.OperateModule s : OperateLogEnums.OperateModule.values()) {
			if(getModuleNo() != null && 
					s.getModuleNo() == getModuleNo().intValue()) {
				return s.getModuleName();
			}
		}
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperateName() {
		for(OperateLogEnums.OperateAction s : OperateLogEnums.OperateAction.values()) {
			if(getOperateNo() != null && 
					s.getOperateNo() == getOperateNo().intValue()) {
				return s.getOperateName();
			}
		}
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of operate_log.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for operate_log.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #dataNo}
     *
     * @return the value of operate_log.data_no
     */
    public String getDataNo() {
        return dataNo;
    }

    /**
     * 
     * {@linkplain #dataNo}
     * @param dataNo the value for operate_log.data_no
     */
    public void setDataNo(String dataNo) {
        this.dataNo = dataNo;
    }

    /**
     * 
     * {@linkplain #moduleNo}
     *
     * @return the value of operate_log.module_no
     */
    public Integer getModuleNo() {
        return moduleNo;
    }

    /**
     * 
     * {@linkplain #moduleNo}
     * @param moduleNo the value for operate_log.module_no
     */
    public void setModuleNo(Integer moduleNo) {
        this.moduleNo = moduleNo;
    }

    /**
     * 
     * {@linkplain #operateNo}
     *
     * @return the value of operate_log.operate_no
     */
    public Integer getOperateNo() {
        return operateNo;
    }

    /**
     * 
     * {@linkplain #operateNo}
     * @param operateNo the value for operate_log.operate_no
     */
    public void setOperateNo(Integer operateNo) {
        this.operateNo = operateNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of operate_log.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for operate_log.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of operate_log.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for operate_log.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of operate_log.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for operate_log.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}