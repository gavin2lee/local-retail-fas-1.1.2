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
public class ParamDtl extends FasBaseModel implements SequenceStrId {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7912334805282267080L;


    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String dtlName;

    /**
     * 参数值
     */
    private String dtlValue;

    /**
     * 是否有效 0-有效 1-无效
     */
    private Byte isvalid;

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
     * @return the value of param_dtl.param_code
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * 
     * {@linkplain #paramCode}
     * @param paramCode the value for param_dtl.param_code
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    /**
     * 
     * {@linkplain #dtlName}
     *
     * @return the value of param_dtl.dtl_name
     */
    public String getDtlName() {
        return dtlName;
    }

    /**
     * 
     * {@linkplain #dtlName}
     * @param dtlName the value for param_dtl.dtl_name
     */
    public void setDtlName(String dtlName) {
        this.dtlName = dtlName;
    }

    /**
     * 
     * {@linkplain #dtlValue}
     *
     * @return the value of param_dtl.dtl_value
     */
    public String getDtlValue() {
        return dtlValue;
    }

    /**
     * 
     * {@linkplain #dtlValue}
     * @param dtlValue the value for param_dtl.dtl_value
     */
    public void setDtlValue(String dtlValue) {
        this.dtlValue = dtlValue;
    }

    /**
     * 
     * {@linkplain #isvalid}
     *
     * @return the value of param_dtl.isvalid
     */
    public Byte getIsvalid() {
        return isvalid;
    }

    /**
     * 
     * {@linkplain #isvalid}
     * @param isvalid the value for param_dtl.isvalid
     */
    public void setIsvalid(Byte isvalid) {
        this.isvalid = isvalid;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of param_dtl.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for param_dtl.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of param_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for param_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of param_dtl.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for param_dtl.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of param_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for param_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}