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
public class Mall implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7439758303029435763L;

	/**
     * 商场ID
     */
    private Integer id;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String name;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

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
     * @return the value of mall.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for mall.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of mall.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for mall.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of mall.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for mall.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of mall.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for mall.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of mall.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for mall.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of mall.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for mall.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of mall.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for mall.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of mall.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for mall.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of mall.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for mall.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of mall.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for mall.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}