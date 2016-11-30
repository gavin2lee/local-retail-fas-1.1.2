package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-30 10:22:59
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
public class Organ implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9217587542781984647L;

	/**
     * 组织ID
     */
    private Integer id;

    /**
     * 组织编号
     */
    private String organNo;

    /**
     * 组织编码
     */
    private String organCode;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 上级组织ID
     */
    private String parentNo;

    /**
     * 1有效，0无效
     */
    private Byte status;

    /**
     * 组织级别
     */
    private Integer organLevel;

    /**
     * 序号
     */
    private Integer orderNo;

    /**
     * 组织路径
     */
    private String path;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
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
     * @return the value of organ.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for organ.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of organ.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for organ.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organCode}
     *
     * @return the value of organ.organ_code
     */
    public String getOrganCode() {
        return organCode;
    }

    /**
     * 
     * {@linkplain #organCode}
     * @param organCode the value for organ.organ_code
     */
    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of organ.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for organ.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #parentNo}
     *
     * @return the value of organ.parent_no
     */
    public String getParentNo() {
        return parentNo;
    }

    /**
     * 
     * {@linkplain #parentNo}
     * @param parentNo the value for organ.parent_no
     */
    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of organ.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for organ.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #organLevel}
     *
     * @return the value of organ.organ_level
     */
    public Integer getOrganLevel() {
        return organLevel;
    }

    /**
     * 
     * {@linkplain #organLevel}
     * @param organLevel the value for organ.organ_level
     */
    public void setOrganLevel(Integer organLevel) {
        this.organLevel = organLevel;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of organ.order_no
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for organ.order_no
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #path}
     *
     * @return the value of organ.path
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * {@linkplain #path}
     * @param path the value for organ.path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of organ.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for organ.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of organ.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for organ.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of organ.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for organ.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of organ.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for organ.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of organ.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for organ.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of organ.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for organ.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}