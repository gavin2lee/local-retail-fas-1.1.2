package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-21 10:32:05
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
public class ParamMain extends FasBaseModel implements SequenceStrId {
    /**
	 * 
	 */
	private static final long serialVersionUID = -65609845002661428L;

    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数级别 0=不允许修改，1=允许客户化修改
     */
    private Byte lookupLevel;

    /**
     * 控制级别 0、公用 1、大区 2、公司 3、店铺
     */
    private Byte controlLevel;

    /**
     * 备注
     */
    private String remark;

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
     * 
     * {@linkplain #paramCode}
     *
     * @return the value of param_main.param_code
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * 
     * {@linkplain #paramCode}
     * @param paramCode the value for param_main.param_code
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    /**
     * 
     * {@linkplain #paramName}
     *
     * @return the value of param_main.param_name
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 
     * {@linkplain #paramName}
     * @param paramName the value for param_main.param_name
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 
     * {@linkplain #lookupLevel}
     *
     * @return the value of param_main.lookup_level
     */
    public Byte getLookupLevel() {
        return lookupLevel;
    }

    /**
     * 
     * {@linkplain #lookupLevel}
     * @param lookupLevel the value for param_main.lookup_level
     */
    public void setLookupLevel(Byte lookupLevel) {
        this.lookupLevel = lookupLevel;
    }

    /**
     * 
     * {@linkplain #controlLevel}
     *
     * @return the value of param_main.control_level
     */
    public Byte getControlLevel() {
        return controlLevel;
    }

    /**
     * 
     * {@linkplain #controlLevel}
     * @param controlLevel the value for param_main.control_level
     */
    public void setControlLevel(Byte controlLevel) {
        this.controlLevel = controlLevel;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of param_main.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for param_main.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of param_main.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for param_main.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of param_main.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for param_main.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of param_main.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for param_main.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of param_main.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for param_main.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}