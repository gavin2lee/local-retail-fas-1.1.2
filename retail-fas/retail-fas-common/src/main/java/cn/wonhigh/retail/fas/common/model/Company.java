package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 结算公司
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
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3783878089048897487L;

	/**
     * 结算公司ID
     */
    private Integer id;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String name;

    /**
     * 状态(0 = 撤消 1 = 正常)
     */
    private Byte status;

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
     * 经营区域编号
     */
    private String zoneNo;
    
    /**
     * 经营地区名称
     */
    private String zoneName;

    /**
     * 建档人
     */
    private String createUser;
    
    
    private String organTypeNo;

    public String getOrganTypeNo() {
		return organTypeNo;
	}

	public void setOrganTypeNo(String organTypeNo) {
		this.organTypeNo = organTypeNo;
	}

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
     * 时间序列
     */
    private Long timeSeq;
    
    /**
     * 检索码
     */
    private String searchCode;
    
    /**
     * 结算公司地址
     */
    private String address;
    
    /**
     * 扩展属性(等同于 companyNo,用于公司多选common_util.js)
     */
    private String code;
    

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of company.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for company.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of company.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for company.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of company.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for company.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of company.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for company.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of company.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for company.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of company.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for company.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     *
     * @return the value of company.bank_account_name
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     * @param bankAccountName the value for company.bank_account_name
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of company.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for company.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of company.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for company.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     *
     * @return the value of company.tax_registry_no
     */
    public String getTaxRegistryNo() {
        return taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     * @param taxRegistryNo the value for company.tax_registry_no
     */
    public void setTaxRegistryNo(String taxRegistryNo) {
        this.taxRegistryNo = taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxLevel}
     *
     * @return the value of company.tax_level
     */
    public String getTaxLevel() {
        return taxLevel;
    }

    /**
     * 
     * {@linkplain #taxLevel}
     * @param taxLevel the value for company.tax_level
     */
    public void setTaxLevel(String taxLevel) {
        this.taxLevel = taxLevel;
    }

    /**
     * 
     * {@linkplain #legalPerson}
     *
     * @return the value of company.legal_person
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 
     * {@linkplain #legalPerson}
     * @param legalPerson the value for company.legal_person
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * 
     * {@linkplain #identityCard}
     *
     * @return the value of company.identity_card
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * 
     * {@linkplain #identityCard}
     * @param identityCard the value for company.identity_card
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /**
     * 
     * {@linkplain #fax}
     *
     * @return the value of company.fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 
     * {@linkplain #fax}
     * @param fax the value for company.fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 
     * {@linkplain #email}
     *
     * @return the value of company.email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * {@linkplain #email}
     * @param email the value for company.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of company.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for company.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of company.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for company.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of company.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for company.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of company.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for company.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of company.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for company.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of company.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for company.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Long getTimeSeq() {
		return timeSeq;
	}

	public void setTimeSeq(Long timeSeq) {
		this.timeSeq = timeSeq;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}