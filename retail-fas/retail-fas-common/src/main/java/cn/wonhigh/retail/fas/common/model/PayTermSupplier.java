package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.PayTermTypeEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:07
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
public class PayTermSupplier implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 条款编码
     */
    private String termNo;

    /**
     * 条款名称
     */
    private String termName;

    /**
     * 条款类型(0:固定日;1:发货日XX天;2:发票日XX天;3:月结XX天;4:供应商发货日XX天)
     */
    private String termType;

    /**
     * 固定日(1-28)
     */
    private Integer fixedDay;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 0:否;1:是
     */
    private Integer isPrepay;

    /**
     * 期货预付比例
     */
    private BigDecimal forwardProportion;

    /**
     * 现货预付比例
     */
    private BigDecimal spotProportion;

    /**
     * 生效日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date effectiveDate;
    
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /***扩展字段***/
    
    /**
     * 条款类型名称
     */
    private String termTypeName;
    
    public String getTermTypeName() {
		return termTypeName;
	}

	public void setTermTypeName(String termTypeName) {
		this.termTypeName = termTypeName;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of pay_term_supplier.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for pay_term_supplier.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of pay_term_supplier.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for pay_term_supplier.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of pay_term_supplier.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for pay_term_supplier.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of pay_term_supplier.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for pay_term_supplier.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of pay_term_supplier.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for pay_term_supplier.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #termNo}
     *
     * @return the value of pay_term_supplier.term_no
     */
    public String getTermNo() {
        return termNo;
    }

    /**
     * 
     * {@linkplain #termNo}
     * @param termNo the value for pay_term_supplier.term_no
     */
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    /**
     * 
     * {@linkplain #termName}
     *
     * @return the value of pay_term_supplier.term_name
     */
    public String getTermName() {
        return termName;
    }

    /**
     * 
     * {@linkplain #termName}
     * @param termName the value for pay_term_supplier.term_name
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }

    /**
     * 
     * {@linkplain #termType}
     *
     * @return the value of pay_term_supplier.term_type
     */
    public String getTermType() {
        return termType;
    }

    /**
     * 
     * {@linkplain #termType}
     * @param termType the value for pay_term_supplier.term_type
     */
    public void setTermType(String termType) {
        this.termType = termType;
        setTermTypeName(PayTermTypeEnums.getNameByNo(Integer.parseInt(termType)));
    }

    /**
     * 
     * {@linkplain #fixedDay}
     *
     * @return the value of pay_term_supplier.fixed_day
     */
    public Integer getFixedDay() {
        return fixedDay;
    }

    /**
     * 
     * {@linkplain #fixedDay}
     * @param fixedDay the value for pay_term_supplier.fixed_day
     */
    public void setFixedDay(Integer fixedDay) {
        this.fixedDay = fixedDay;
    }

    /**
     * 
     * {@linkplain #days}
     *
     * @return the value of pay_term_supplier.days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 
     * {@linkplain #days}
     * @param days the value for pay_term_supplier.days
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 
     * {@linkplain #isPrepay}
     *
     * @return the value of pay_term_supplier.is_prepay
     */
    public Integer getIsPrepay() {
        return isPrepay;
    }

    /**
     * 
     * {@linkplain #isPrepay}
     * @param isPrepay the value for pay_term_supplier.is_prepay
     */
    public void setIsPrepay(Integer isPrepay) {
        this.isPrepay = isPrepay;
    }

    /**
     * 
     * {@linkplain #forwardProportion}
     *
     * @return the value of pay_term_supplier.forward_proportion
     */
    public BigDecimal getForwardProportion() {
        return forwardProportion;
    }

    /**
     * 
     * {@linkplain #forwardProportion}
     * @param forwardProportion the value for pay_term_supplier.forward_proportion
     */
    public void setForwardProportion(BigDecimal forwardProportion) {
        this.forwardProportion = forwardProportion;
    }

    /**
     * 
     * {@linkplain #spotProportion}
     *
     * @return the value of pay_term_supplier.spot_proportion
     */
    public BigDecimal getSpotProportion() {
        return spotProportion;
    }

    /**
     * 
     * {@linkplain #spotProportion}
     * @param spotProportion the value for pay_term_supplier.spot_proportion
     */
    public void setSpotProportion(BigDecimal spotProportion) {
        this.spotProportion = spotProportion;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of pay_term_supplier.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for pay_term_supplier.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of pay_term_supplier.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for pay_term_supplier.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of pay_term_supplier.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for pay_term_supplier.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of pay_term_supplier.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for pay_term_supplier.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}