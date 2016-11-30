package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-06 15:45:01
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
public class Store implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3524767598343788186L;

	/**
     * 
     */
    private Integer id;

    /**
     * 仓库编码
     */
    private String storeNo;

    /**
     * 所属主仓库
     */
    private String parentNo;

    /**
     * 机构代号
     */
    private String storeCode;

    /**
     * 机构简称
     */
    private String shortName;

    /**
     * 机构全称
     */
    private String fullName;

    /**
     * 所属业务单元
     */
    private String sysNo;

    /**
     * 机构状态( 0:冻结,1:正常,9:撤销)
     */
    private Byte status;

    /**
     * 仓库面积
     */
    private BigDecimal area;

    /**
     * 行政省编码
     */
    private String provinceNo;

    /**
     * 行政市编码
     */
    private String cityNo;

    /**
     * 行政县编码
     */
    private String countyNo;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 地址(填写时不用包含省、市、县)
     */
    private String address;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 传真号
     */
    private String fax;

    /**
     * 机构类型(21:店仓  22:仓库)
     */
    private Byte storeType;

    /**
     * 仓库类别:1城市厂、2中转仓、3工厂仓
     */
    private String storageType;

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
     * 时间序列
     */
    private Long timeSeq;

    /**
     * 检索码
     */
    private String searchCode;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of store.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for store.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of store.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for store.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #parentNo}
     *
     * @return the value of store.parent_no
     */
    public String getParentNo() {
        return parentNo;
    }

    /**
     * 
     * {@linkplain #parentNo}
     * @param parentNo the value for store.parent_no
     */
    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    /**
     * 
     * {@linkplain #storeCode}
     *
     * @return the value of store.store_code
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     * 
     * {@linkplain #storeCode}
     * @param storeCode the value for store.store_code
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of store.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for store.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of store.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for store.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of store.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for store.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of store.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for store.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #area}
     *
     * @return the value of store.area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * 
     * {@linkplain #area}
     * @param area the value for store.area
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     *
     * @return the value of store.province_no
     */
    public String getProvinceNo() {
        return provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     * @param provinceNo the value for store.province_no
     */
    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     *
     * @return the value of store.city_no
     */
    public String getCityNo() {
        return cityNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     * @param cityNo the value for store.city_no
     */
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     *
     * @return the value of store.county_no
     */
    public String getCountyNo() {
        return countyNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     * @param countyNo the value for store.county_no
     */
    public void setCountyNo(String countyNo) {
        this.countyNo = countyNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of store.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for store.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #address}
     *
     * @return the value of store.address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * {@linkplain #address}
     * @param address the value for store.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * {@linkplain #zipCode}
     *
     * @return the value of store.zip_code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 
     * {@linkplain #zipCode}
     * @param zipCode the value for store.zip_code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of store.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for store.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of store.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for store.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #fax}
     *
     * @return the value of store.fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 
     * {@linkplain #fax}
     * @param fax the value for store.fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 
     * {@linkplain #storeType}
     *
     * @return the value of store.store_type
     */
    public Byte getStoreType() {
        return storeType;
    }

    /**
     * 
     * {@linkplain #storeType}
     * @param storeType the value for store.store_type
     */
    public void setStoreType(Byte storeType) {
        this.storeType = storeType;
    }

    /**
     * 
     * {@linkplain #storageType}
     *
     * @return the value of store.storage_type
     */
    public String getStorageType() {
        return storageType;
    }

    /**
     * 
     * {@linkplain #storageType}
     * @param storageType the value for store.storage_type
     */
    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of store.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for store.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of store.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for store.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of store.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for store.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of store.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for store.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of store.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for store.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #timeSeq}
     *
     * @return the value of store.time_seq
     */
    public Long getTimeSeq() {
        return timeSeq;
    }

    /**
     * 
     * {@linkplain #timeSeq}
     * @param timeSeq the value for store.time_seq
     */
    public void setTimeSeq(Long timeSeq) {
        this.timeSeq = timeSeq;
    }

    /**
     * 
     * {@linkplain #searchCode}
     *
     * @return the value of store.search_code
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * 
     * {@linkplain #searchCode}
     * @param searchCode the value for store.search_code
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }
}