package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Shop implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -459210383246698842L;

	/**
     * 
     */
    private Integer id;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺外码
     */
    private String code;

    /**
     * 店铺所属仓库编码
     */
    private String storeNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;
    
    /**
     * 集团编码
     */
    private String bsgroupsNo;
    
    /**
     * 集团名称
     */
    private String bsgroupsName;
    
    /**
     * 检索码
     */
    private String searchCode;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 组织编号
     */
    private String organNo;
    
    /**
     * 组织名称
     */
    private String organName;

    /**
     * 区编码
     */
    private String zoneNo;
    
    /**
     * 区名称
     */
    private String zoneName;
    
    /**
     * 经营城市编号
     */
    private String bizCityNo;

    /**
     * 所属业务单元
     */
    private String sysNo;

    /**
     * 成立日期(店铺正式营业的日期)
     */
    private Date openDate;

    /**
     * 撤销日期(店铺停止营运的日期)
     */
    private Date closeDate;

    /**
     * 店铺状态( 0:冻结,1:正常,9:撤销)
     */
    private Byte status;

    /**
     * 卖场面积
     */
    private BigDecimal area;

    /**
     * 仓库面积
     */
    private BigDecimal areaLeft;

    /**
     * 总面积
     */
    private BigDecimal areaTotal;

    /**
     * 面积单位(1:㎡)
     */
    private String areaUnit;

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
     * 电子邮箱
     */
    private String email;

    /**
     * 销售渠道编码
     */
    private String channelNo;

    /**
     * 品柜形式
     */
    private String location;

    /**
     * 店员配备数(门店必填,指标准的店员配备数量)
     */
    private Integer employeAmount;

    /**
     * 结算方式(门店必填, 1:扣费店 2:租金店 3:不结算)
     */
    private String payType;

    /**
     * 收银位数(门店必填, 0:元 1:角 2:分
     */
    private String digits;

    /**
     * 每天营业开始时间
     */
    private String startupTime;

    /**
     * 每天营业关闭时间
     */
    private String shutdownTime;

    /**
     * 门店级别( A、B、C、D、E)
     */
    private String shopLevel;

    /**
     * 主营品类(门店必填, 1:男鞋 2:女鞋 3:童鞋 4:综合)
     */
    private String major;

    /**
     * 单品多品(门店必填,C:多品店 D:单品店)
     */
    private String multi;

    /**
     * 批发零售(门店必填,1:零售；2:批发)
     */
    private String saleMode;

    /**
     * (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场

特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;
    
    /**
     * 片区编码
     */
    private String regionNo;
    
    /**
     * 片区名称
     */
    private String regionName;

    /**
     * 商圈编码
     */
    private String cmcdistNo;

    /**
     * 经营类型（男鞋，女鞋，综合）
     */
    private String categoryCode;

    /**
     * 店铺类别
     */
    private String shopClassify;

    /**
     * 调价级别
     */
    private String priceAdjustLevel;

    /**
     * 在线标志:1在线，0离线
     */
    private Byte onLineFlag;

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

    private String brandNo;
    
    private String brandName;
    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of shop.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for shop.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of shop.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for shop.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of shop.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for shop.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of shop.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for shop.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of shop.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for shop.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #searchCode}
     *
     * @return the value of shop.search_code
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * 
     * {@linkplain #searchCode}
     * @param searchCode the value for shop.search_code
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of shop.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for shop.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of shop.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for shop.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of shop.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for shop.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #bizCityNo}
     *
     * @return the value of shop.biz_city_no
     */
    public String getBizCityNo() {
        return bizCityNo;
    }

    /**
     * 
     * {@linkplain #bizCityNo}
     * @param bizCityNo the value for shop.biz_city_no
     */
    public void setBizCityNo(String bizCityNo) {
        this.bizCityNo = bizCityNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of shop.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for shop.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #openDate}
     *
     * @return the value of shop.open_date
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * 
     * {@linkplain #openDate}
     * @param openDate the value for shop.open_date
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * 
     * {@linkplain #closeDate}
     *
     * @return the value of shop.close_date
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * 
     * {@linkplain #closeDate}
     * @param closeDate the value for shop.close_date
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of shop.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for shop.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #area}
     *
     * @return the value of shop.area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * 
     * {@linkplain #area}
     * @param area the value for shop.area
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * 
     * {@linkplain #areaLeft}
     *
     * @return the value of shop.area_left
     */
    public BigDecimal getAreaLeft() {
        return areaLeft;
    }

    /**
     * 
     * {@linkplain #areaLeft}
     * @param areaLeft the value for shop.area_left
     */
    public void setAreaLeft(BigDecimal areaLeft) {
        this.areaLeft = areaLeft;
    }

    /**
     * 
     * {@linkplain #areaTotal}
     *
     * @return the value of shop.area_total
     */
    public BigDecimal getAreaTotal() {
        return areaTotal;
    }

    /**
     * 
     * {@linkplain #areaTotal}
     * @param areaTotal the value for shop.area_total
     */
    public void setAreaTotal(BigDecimal areaTotal) {
        this.areaTotal = areaTotal;
    }

    /**
     * 
     * {@linkplain #areaUnit}
     *
     * @return the value of shop.area_unit
     */
    public String getAreaUnit() {
        return areaUnit;
    }

    /**
     * 
     * {@linkplain #areaUnit}
     * @param areaUnit the value for shop.area_unit
     */
    public void setAreaUnit(String areaUnit) {
        this.areaUnit = areaUnit;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     *
     * @return the value of shop.province_no
     */
    public String getProvinceNo() {
        return provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     * @param provinceNo the value for shop.province_no
     */
    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     *
     * @return the value of shop.city_no
     */
    public String getCityNo() {
        return cityNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     * @param cityNo the value for shop.city_no
     */
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     *
     * @return the value of shop.county_no
     */
    public String getCountyNo() {
        return countyNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     * @param countyNo the value for shop.county_no
     */
    public void setCountyNo(String countyNo) {
        this.countyNo = countyNo;
    }

    /**
     * 
     * {@linkplain #address}
     *
     * @return the value of shop.address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * {@linkplain #address}
     * @param address the value for shop.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * {@linkplain #zipCode}
     *
     * @return the value of shop.zip_code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 
     * {@linkplain #zipCode}
     * @param zipCode the value for shop.zip_code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of shop.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for shop.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of shop.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for shop.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #fax}
     *
     * @return the value of shop.fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 
     * {@linkplain #fax}
     * @param fax the value for shop.fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 
     * {@linkplain #email}
     *
     * @return the value of shop.email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * {@linkplain #email}
     * @param email the value for shop.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * {@linkplain #channelNo}
     *
     * @return the value of shop.channel_no
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * 
     * {@linkplain #channelNo}
     * @param channelNo the value for shop.channel_no
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    /**
     * 
     * {@linkplain #location}
     *
     * @return the value of shop.location
     */
    public String getLocation() {
        return location;
    }

    /**
     * 
     * {@linkplain #location}
     * @param location the value for shop.location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 
     * {@linkplain #employeAmount}
     *
     * @return the value of shop.employe_amount
     */
    public Integer getEmployeAmount() {
        return employeAmount;
    }

    /**
     * 
     * {@linkplain #employeAmount}
     * @param employeAmount the value for shop.employe_amount
     */
    public void setEmployeAmount(Integer employeAmount) {
        this.employeAmount = employeAmount;
    }

    /**
     * 
     * {@linkplain #payType}
     *
     * @return the value of shop.pay_type
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 
     * {@linkplain #payType}
     * @param payType the value for shop.pay_type
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 
     * {@linkplain #digits}
     *
     * @return the value of shop.digits
     */
    public String getDigits() {
        return digits;
    }

    /**
     * 
     * {@linkplain #digits}
     * @param digits the value for shop.digits
     */
    public void setDigits(String digits) {
        this.digits = digits;
    }

    /**
     * 
     * {@linkplain #startupTime}
     *
     * @return the value of shop.startup_time
     */
    public String getStartupTime() {
        return startupTime;
    }

    /**
     * 
     * {@linkplain #startupTime}
     * @param startupTime the value for shop.startup_time
     */
    public void setStartupTime(String startupTime) {
        this.startupTime = startupTime;
    }

    /**
     * 
     * {@linkplain #shutdownTime}
     *
     * @return the value of shop.shutdown_time
     */
    public String getShutdownTime() {
        return shutdownTime;
    }

    /**
     * 
     * {@linkplain #shutdownTime}
     * @param shutdownTime the value for shop.shutdown_time
     */
    public void setShutdownTime(String shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    /**
     * 
     * {@linkplain #shopLevel}
     *
     * @return the value of shop.shop_level
     */
    public String getShopLevel() {
        return shopLevel;
    }

    /**
     * 
     * {@linkplain #shopLevel}
     * @param shopLevel the value for shop.shop_level
     */
    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    /**
     * 
     * {@linkplain #major}
     *
     * @return the value of shop.major
     */
    public String getMajor() {
        return major;
    }

    /**
     * 
     * {@linkplain #major}
     * @param major the value for shop.major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 
     * {@linkplain #multi}
     *
     * @return the value of shop.multi
     */
    public String getMulti() {
        return multi;
    }

    /**
     * 
     * {@linkplain #multi}
     * @param multi the value for shop.multi
     */
    public void setMulti(String multi) {
        this.multi = multi;
    }

    /**
     * 
     * {@linkplain #saleMode}
     *
     * @return the value of shop.sale_mode
     */
    public String getSaleMode() {
        return saleMode;
    }

    /**
     * 
     * {@linkplain #saleMode}
     * @param saleMode the value for shop.sale_mode
     */
    public void setSaleMode(String saleMode) {
        this.saleMode = saleMode;
    }

    /**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of shop.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for shop.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of shop.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for shop.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     *
     * @return the value of shop.region_no
     */
    public String getRegionNo() {
        return regionNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     * @param regionNo the value for shop.region_no
     */
    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    /**
     * 
     * {@linkplain #cmcdistNo}
     *
     * @return the value of shop.cmcdist_no
     */
    public String getCmcdistNo() {
        return cmcdistNo;
    }

    /**
     * 
     * {@linkplain #cmcdistNo}
     * @param cmcdistNo the value for shop.cmcdist_no
     */
    public void setCmcdistNo(String cmcdistNo) {
        this.cmcdistNo = cmcdistNo;
    }

    /**
     * 
     * {@linkplain #categoryCode}
     *
     * @return the value of shop.category_code
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * 
     * {@linkplain #categoryCode}
     * @param categoryCode the value for shop.category_code
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 
     * {@linkplain #shopClassify}
     *
     * @return the value of shop.shop_classify
     */
    public String getShopClassify() {
        return shopClassify;
    }

    /**
     * 
     * {@linkplain #shopClassify}
     * @param shopClassify the value for shop.shop_classify
     */
    public void setShopClassify(String shopClassify) {
        this.shopClassify = shopClassify;
    }

    /**
     * 
     * {@linkplain #priceAdjustLevel}
     *
     * @return the value of shop.price_adjust_level
     */
    public String getPriceAdjustLevel() {
        return priceAdjustLevel;
    }

    /**
     * 
     * {@linkplain #priceAdjustLevel}
     * @param priceAdjustLevel the value for shop.price_adjust_level
     */
    public void setPriceAdjustLevel(String priceAdjustLevel) {
        this.priceAdjustLevel = priceAdjustLevel;
    }

    /**
     * 
     * {@linkplain #onLineFlag}
     *
     * @return the value of shop.on_line_flag
     */
    public Byte getOnLineFlag() {
        return onLineFlag;
    }

    /**
     * 
     * {@linkplain #onLineFlag}
     * @param onLineFlag the value for shop.on_line_flag
     */
    public void setOnLineFlag(Byte onLineFlag) {
        this.onLineFlag = onLineFlag;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of shop.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for shop.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of shop.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for shop.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of shop.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for shop.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of shop.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for shop.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of shop.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for shop.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getCompanyName() {
		return companyName;
	}

	public String getBsgroupsNo() {
		return bsgroupsNo;
	}

	public String getBsgroupsName() {
		return bsgroupsName;
	}

	public String getOrganName() {
		return organName;
	}

	public String getMallName() {
		return mallName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setBsgroupsNo(String bsgroupsNo) {
		this.bsgroupsNo = bsgroupsNo;
	}

	public void setBsgroupsName(String bsgroupsName) {
		this.bsgroupsName = bsgroupsName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}