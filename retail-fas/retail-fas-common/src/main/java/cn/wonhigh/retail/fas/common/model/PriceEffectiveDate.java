package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;


/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-10 14:12:09
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
public class PriceEffectiveDate extends FasBaseModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8084938326094863599L;

	/**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 采购价生效日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date purPriceEffDate;

    /**
     * 地区价生效日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date areaPriceEffDate;

    /**
     * 品牌部编码
     */
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 状态 1、已启用 0、已停用
     */
    private Integer status;

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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of price_effective_date.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for price_effective_date.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of price_effective_date.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for price_effective_date.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of price_effective_date.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for price_effective_date.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of price_effective_date.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for price_effective_date.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #purPriceEffDate}
     *
     * @return the value of price_effective_date.pur_price_eff_date
     */
    public Date getPurPriceEffDate() {
        return purPriceEffDate;
    }

    /**
     * 
     * {@linkplain #purPriceEffDate}
     * @param purPriceEffDate the value for price_effective_date.pur_price_eff_date
     */
    public void setPurPriceEffDate(Date purPriceEffDate) {
        this.purPriceEffDate = purPriceEffDate;
    }

    /**
     * 
     * {@linkplain #areaPriceEffDate}
     *
     * @return the value of price_effective_date.area_price_eff_date
     */
    public Date getAreaPriceEffDate() {
        return areaPriceEffDate;
    }

    /**
     * 
     * {@linkplain #areaPriceEffDate}
     * @param areaPriceEffDate the value for price_effective_date.area_price_eff_date
     */
    public void setAreaPriceEffDate(Date areaPriceEffDate) {
        this.areaPriceEffDate = areaPriceEffDate;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of price_effective_date.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for price_effective_date.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of price_effective_date.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for price_effective_date.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of price_effective_date.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for price_effective_date.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of price_effective_date.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for price_effective_date.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of price_effective_date.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for price_effective_date.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of price_effective_date.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for price_effective_date.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of price_effective_date.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for price_effective_date.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of price_effective_date.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for price_effective_date.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}