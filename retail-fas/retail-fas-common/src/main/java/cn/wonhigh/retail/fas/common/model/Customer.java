package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 客户
 * @author ouyang.zm
 * @date  2014-10-22 15:09:15
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
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5457016780060007143L;

	/**
     * 顾客ID
     */
    private Integer id;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 检索码
     */
    private String code;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 客户简称
     */
    private String shortName;

    /**
     * 客户全称
     */
    private String fullName;

    /**
     * 客户英文名称
     */
    private String enName;

    /**
     * 客户状态(0 = 撤消 1 = 正常)
     */
    private Byte status;

    /**
     * 客户性质(1:加盟 2:代销 3:批发)
     */
    private String category;

    /**
     * 客户类型(1:个人 2:公司)
     */
    private String type;

    /**
     * 客户等级(A B C D四种)
     */
    private String level;

    /**
     * 信用额度
     */
    private BigDecimal creditlines;

    /**
     * 批发折扣(1-100之间,可以为小数,用于计算时除以100)
     */
    private BigDecimal discount;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 银行账户名
     */
    private String bankAccountName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 税务登记号
     */
    private String taxRegistryNo;

    /**
     * 纳税级别(0:一般纳税人 1:小规模纳税人)
     */
    private String taxLevel;

    /**
     * 法人代表
     */
    private String legalPerson;

    /**
     * 营业证号/身份证号
     */
    private String identityCard;

    /**
     * 传真号
     */
    private String fax;

    /**
     * 电子邮箱
     */
    private String email;

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
     * 联系地址
     */
    private String address;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
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
     * 收款条款编码
     */
    private String termNo;

    /**
     * 保证金额度
     */
    private BigDecimal marginAmount;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of customer.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for customer.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of customer.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for customer.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of customer.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for customer.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of customer.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for customer.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of customer.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for customer.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of customer.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for customer.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #enName}
     *
     * @return the value of customer.en_name
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 
     * {@linkplain #enName}
     * @param enName the value for customer.en_name
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of customer.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for customer.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #category}
     *
     * @return the value of customer.category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * {@linkplain #category}
     * @param category the value for customer.category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of customer.type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * {@linkplain #type}
     * @param type the value for customer.type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * {@linkplain #level}
     *
     * @return the value of customer.level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     * {@linkplain #level}
     * @param level the value for customer.level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 
     * {@linkplain #creditlines}
     *
     * @return the value of customer.creditlines
     */
    public BigDecimal getCreditlines() {
        return creditlines;
    }

    /**
     * 
     * {@linkplain #creditlines}
     * @param creditlines the value for customer.creditlines
     */
    public void setCreditlines(BigDecimal creditlines) {
        this.creditlines = creditlines;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of customer.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for customer.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of customer.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for customer.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of customer.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for customer.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     *
     * @return the value of customer.bank_account_name
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     * @param bankAccountName the value for customer.bank_account_name
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of customer.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for customer.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of customer.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for customer.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     *
     * @return the value of customer.tax_registry_no
     */
    public String getTaxRegistryNo() {
        return taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     * @param taxRegistryNo the value for customer.tax_registry_no
     */
    public void setTaxRegistryNo(String taxRegistryNo) {
        this.taxRegistryNo = taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxLevel}
     *
     * @return the value of customer.tax_level
     */
    public String getTaxLevel() {
        return taxLevel;
    }

    /**
     * 
     * {@linkplain #taxLevel}
     * @param taxLevel the value for customer.tax_level
     */
    public void setTaxLevel(String taxLevel) {
        this.taxLevel = taxLevel;
    }

    /**
     * 
     * {@linkplain #legalPerson}
     *
     * @return the value of customer.legal_person
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 
     * {@linkplain #legalPerson}
     * @param legalPerson the value for customer.legal_person
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * 
     * {@linkplain #identityCard}
     *
     * @return the value of customer.identity_card
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * 
     * {@linkplain #identityCard}
     * @param identityCard the value for customer.identity_card
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /**
     * 
     * {@linkplain #fax}
     *
     * @return the value of customer.fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 
     * {@linkplain #fax}
     * @param fax the value for customer.fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 
     * {@linkplain #email}
     *
     * @return the value of customer.email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * {@linkplain #email}
     * @param email the value for customer.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     *
     * @return the value of customer.province_no
     */
    public String getProvinceNo() {
        return provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     * @param provinceNo the value for customer.province_no
     */
    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     *
     * @return the value of customer.city_no
     */
    public String getCityNo() {
        return cityNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     * @param cityNo the value for customer.city_no
     */
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     *
     * @return the value of customer.county_no
     */
    public String getCountyNo() {
        return countyNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     * @param countyNo the value for customer.county_no
     */
    public void setCountyNo(String countyNo) {
        this.countyNo = countyNo;
    }

    /**
     * 
     * {@linkplain #address}
     *
     * @return the value of customer.address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * {@linkplain #address}
     * @param address the value for customer.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of customer.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for customer.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of customer.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for customer.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of customer.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for customer.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of customer.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for customer.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of customer.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for customer.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #termNo}
     *
     * @return the value of customer.term_no
     */
    public String getTermNo() {
        return termNo;
    }

    /**
     * 
     * {@linkplain #termNo}
     * @param termNo the value for customer.term_no
     */
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     *
     * @return the value of customer.margin_amount
     */
    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     * @param marginAmount the value for customer.margin_amount
     */
    public void setMarginAmount(BigDecimal marginAmount) {
        this.marginAmount = marginAmount;
    }
}