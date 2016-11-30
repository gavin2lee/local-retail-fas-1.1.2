package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 10:58:55
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
public class BizCity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4910128924310965892L;

	/**
     * ID
     */
    private Integer id;

    /**
     * 经营城市编号
     */
    private String bizCityNo;

    /**
     * 经营城市名称
     */
    private String name;

    /**
     * 片区状态(0 = 撤消 1 = 正常)
     */
    private Byte status;

    /**
     * 经营区域编号
     */
    private String zoneNo;

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
     * @return the value of biz_city.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for biz_city.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #bizCityNo}
     *
     * @return the value of biz_city.biz_city_no
     */
    public String getBizCityNo() {
        return bizCityNo;
    }

    /**
     * 
     * {@linkplain #bizCityNo}
     * @param bizCityNo the value for biz_city.biz_city_no
     */
    public void setBizCityNo(String bizCityNo) {
        this.bizCityNo = bizCityNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of biz_city.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for biz_city.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of biz_city.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for biz_city.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of biz_city.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for biz_city.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of biz_city.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for biz_city.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of biz_city.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for biz_city.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of biz_city.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for biz_city.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of biz_city.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for biz_city.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of biz_city.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for biz_city.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}