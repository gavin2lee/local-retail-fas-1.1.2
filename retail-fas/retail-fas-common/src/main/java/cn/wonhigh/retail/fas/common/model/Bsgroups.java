package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 14:23:50
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
public class Bsgroups implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1694739628811973401L;

	/**
     * 商业集团ID
     */
    private Integer id;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String name;

    /**
     * 建档人
     */
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
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bsgroups.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bsgroups.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bsgroups.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bsgroups.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of bsgroups.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for bsgroups.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bsgroups.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bsgroups.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bsgroups.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bsgroups.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bsgroups.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bsgroups.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bsgroups.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bsgroups.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bsgroups.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bsgroups.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}