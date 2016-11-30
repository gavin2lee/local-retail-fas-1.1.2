package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
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
public class Supplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7191132522187214990L;

	/**
     * 供应商ID
     */
    private Integer id;

    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商特征码(必须输入且只能输入2位)
     */
    private String opcode;

    /**
     * 供应商简称
     */
    private String shortName;

    /**
     * 供应商全称
     */
    private String fullName;

    /**
     * 所属业务单元
     */
    private String sysNo;

    /**
     * 供应商状态(0 = 撤消 1 = 正常,2待确认,3确认)
     */
    private Byte status;

    /**
     * 经营性质(0:本厂 1:加工 2:外厂 3:样品)
     */
    private String bizType;

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
     * 供应商外码
     */
    private String code;

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
     * 邮编
     */
    private String zipCode;

    /**
     * 进项税率
     */
    private BigDecimal costtaxrate;

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
     * 检索码
     */
    private String searchCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of supplier.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for supplier.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of supplier.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for supplier.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #opcode}
     *
     * @return the value of supplier.opcode
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 
     * {@linkplain #opcode}
     * @param opcode the value for supplier.opcode
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of supplier.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for supplier.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of supplier.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for supplier.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of supplier.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for supplier.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of supplier.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for supplier.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #bizType}
     *
     * @return the value of supplier.biz_type
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 
     * {@linkplain #bizType}
     * @param bizType the value for supplier.biz_type
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of supplier.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for supplier.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of supplier.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for supplier.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     *
     * @return the value of supplier.bank_account_name
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     * @param bankAccountName the value for supplier.bank_account_name
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of supplier.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for supplier.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of supplier.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for supplier.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     *
     * @return the value of supplier.tax_registry_no
     */
    public String getTaxRegistryNo() {
        return taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     * @param taxRegistryNo the value for supplier.tax_registry_no
     */
    public void setTaxRegistryNo(String taxRegistryNo) {
        this.taxRegistryNo = taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxLevel}
     *
     * @return the value of supplier.tax_level
     */
    public String getTaxLevel() {
        return taxLevel;
    }

    /**
     * 
     * {@linkplain #taxLevel}
     * @param taxLevel the value for supplier.tax_level
     */
    public void setTaxLevel(String taxLevel) {
        this.taxLevel = taxLevel;
    }

    /**
     * 
     * {@linkplain #legalPerson}
     *
     * @return the value of supplier.legal_person
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 
     * {@linkplain #legalPerson}
     * @param legalPerson the value for supplier.legal_person
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * 
     * {@linkplain #identityCard}
     *
     * @return the value of supplier.identity_card
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * 
     * {@linkplain #identityCard}
     * @param identityCard the value for supplier.identity_card
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of supplier.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for supplier.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #fax}
     *
     * @return the value of supplier.fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 
     * {@linkplain #fax}
     * @param fax the value for supplier.fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 
     * {@linkplain #email}
     *
     * @return the value of supplier.email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * {@linkplain #email}
     * @param email the value for supplier.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     *
     * @return the value of supplier.province_no
     */
    public String getProvinceNo() {
        return provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     * @param provinceNo the value for supplier.province_no
     */
    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     *
     * @return the value of supplier.city_no
     */
    public String getCityNo() {
        return cityNo;
    }

    /**
     * 
     * {@linkplain #cityNo}
     * @param cityNo the value for supplier.city_no
     */
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     *
     * @return the value of supplier.county_no
     */
    public String getCountyNo() {
        return countyNo;
    }

    /**
     * 
     * {@linkplain #countyNo}
     * @param countyNo the value for supplier.county_no
     */
    public void setCountyNo(String countyNo) {
        this.countyNo = countyNo;
    }

    /**
     * 
     * {@linkplain #address}
     *
     * @return the value of supplier.address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * {@linkplain #address}
     * @param address the value for supplier.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * {@linkplain #zipCode}
     *
     * @return the value of supplier.zip_code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 
     * {@linkplain #zipCode}
     * @param zipCode the value for supplier.zip_code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 
     * {@linkplain #costtaxrate}
     *
     * @return the value of supplier.costtaxrate
     */
    public BigDecimal getCosttaxrate() {
        return costtaxrate;
    }

    /**
     * 
     * {@linkplain #costtaxrate}
     * @param costtaxrate the value for supplier.costtaxrate
     */
    public void setCosttaxrate(BigDecimal costtaxrate) {
        this.costtaxrate = costtaxrate;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of supplier.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for supplier.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of supplier.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for supplier.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of supplier.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for supplier.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of supplier.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for supplier.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #searchCode}
     *
     * @return the value of supplier.search_code
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * 
     * {@linkplain #searchCode}
     * @param searchCode the value for supplier.search_code
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of supplier.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for supplier.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}