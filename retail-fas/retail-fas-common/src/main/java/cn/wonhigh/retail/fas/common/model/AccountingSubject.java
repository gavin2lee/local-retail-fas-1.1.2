package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 会计科目 
 * @author ouyang.zm
 * @date  2015-01-04 11:46:00
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
public class AccountingSubject extends FasBaseModel  implements SequenceStrId {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6151723681605300142L;

    /**
     * 科目编码
     */
    private String subjectCode;

    /**
     * 科目级次
     */
    private Byte subjectLevel;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 科目类型
     */
    private Byte subjectType;

    /**
     * 科目英文名称
     */
    private String subjectEnName;

    /**
     * 余额方向 1、借 2、贷
     */
    private Byte balanceDirection;

    /**
     * 科目默认币种
     */
    private String currency;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 余额方向控制
     */
    private Byte balanceFlag;

    /**
     * 助记码
     */
    private String mnemonicCode;

    /**
     * 是否核销科目
     */
    private Byte cancelFlag;

    /**
     * 启用期间
     */
    private String beginPeriod;

    /**
     * 启用年度
     */
    private String beginYear;

    /**
     * 帐簿余额双向显示
     */
    private String bothDirection;

    /**
     * 现金银行科目
     */
    private Byte cashBankFlag;

    /**
     * 发生额方向控制
     */
    private Byte incurFlag;

    /**
     * 创建会计主体账簿
     */
    private String glorgBookCreation;

    /**
     * 创建年度
     */
    private String createYear;

    /**
     * 创建月份
     */
    private String createPeriod;

    /**
     * 控制系统
     */
    private String controlSystem;

    /**
     * 删除标志
     */
    private Byte delFlag;

    /**
     * 末级标志
     */
    private Byte endFlag;

    /**
     * 终止期间
     */
    private String endPeriod;

    /**
     * 终止年度
     */
    private String endYear;

    /**
     * 内部交易信息是否必录
     */
    private Byte recordedFlag;

    /**
     * 内部交易科目
     */
    private String innerSubject;

    /**
     * 表外科目
     */
    private Byte outFlag;

    /**
     * 会计主体账簿
     */
    private String glorgBook;

    /**
     * 对应集团科目
     */
    private String groupSubject;

    /**
     * 科目方案
     */
    private String subjectScheme;

    /**
     * 封存标志
     */
    private Byte sealFlag;

    /**
     * 状态 1、已启用 0、已停用
     */
    private Integer status;

    /**
     * 汇总打印级次
     */
    private Byte printLevel;

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
	 * 扩展
	 */
	private String companyName;
	
    /**
     * 
     * {@linkplain #subjectCode}
     *
     * @return the value of accounting_subject.subject_code
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
     * 
     * {@linkplain #subjectCode}
     * @param subjectCode the value for accounting_subject.subject_code
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * 
     * {@linkplain #subjectLevel}
     *
     * @return the value of accounting_subject.subject_level
     */
    public Byte getSubjectLevel() {
        return subjectLevel;
    }

    /**
     * 
     * {@linkplain #subjectLevel}
     * @param subjectLevel the value for accounting_subject.subject_level
     */
    public void setSubjectLevel(Byte subjectLevel) {
        this.subjectLevel = subjectLevel;
    }

    /**
     * 
     * {@linkplain #subjectName}
     *
     * @return the value of accounting_subject.subject_name
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 
     * {@linkplain #subjectName}
     * @param subjectName the value for accounting_subject.subject_name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * 
     * {@linkplain #subjectType}
     *
     * @return the value of accounting_subject.subject_type
     */
    public Byte getSubjectType() {
        return subjectType;
    }

    /**
     * 
     * {@linkplain #subjectType}
     * @param subjectType the value for accounting_subject.subject_type
     */
    public void setSubjectType(Byte subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * 
     * {@linkplain #subjectEnName}
     *
     * @return the value of accounting_subject.subject_en_name
     */
    public String getSubjectEnName() {
        return subjectEnName;
    }

    /**
     * 
     * {@linkplain #subjectEnName}
     * @param subjectEnName the value for accounting_subject.subject_en_name
     */
    public void setSubjectEnName(String subjectEnName) {
        this.subjectEnName = subjectEnName;
    }

    /**
     * 
     * {@linkplain #balanceDirection}
     *
     * @return the value of accounting_subject.balance_direction
     */
    public Byte getBalanceDirection() {
        return balanceDirection;
    }

    /**
     * 
     * {@linkplain #balanceDirection}
     * @param balanceDirection the value for accounting_subject.balance_direction
     */
    public void setBalanceDirection(Byte balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    /**
     * 
     * {@linkplain #currency}
     *
     * @return the value of accounting_subject.currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * {@linkplain #currency}
     * @param currency the value for accounting_subject.currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * {@linkplain #displayName}
     *
     * @return the value of accounting_subject.display_name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * {@linkplain #displayName}
     * @param displayName the value for accounting_subject.display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     * {@linkplain #unit}
     *
     * @return the value of accounting_subject.unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * {@linkplain #unit}
     * @param unit the value for accounting_subject.unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of accounting_subject.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for accounting_subject.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #balanceFlag}
     *
     * @return the value of accounting_subject.balance_flag
     */
    public Byte getBalanceFlag() {
        return balanceFlag;
    }

    /**
     * 
     * {@linkplain #balanceFlag}
     * @param balanceFlag the value for accounting_subject.balance_flag
     */
    public void setBalanceFlag(Byte balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    /**
     * 
     * {@linkplain #mnemonicCode}
     *
     * @return the value of accounting_subject.mnemonic_code
     */
    public String getMnemonicCode() {
        return mnemonicCode;
    }

    /**
     * 
     * {@linkplain #mnemonicCode}
     * @param mnemonicCode the value for accounting_subject.mnemonic_code
     */
    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    /**
     * 
     * {@linkplain #cancelFlag}
     *
     * @return the value of accounting_subject.cancel_flag
     */
    public Byte getCancelFlag() {
        return cancelFlag;
    }

    /**
     * 
     * {@linkplain #cancelFlag}
     * @param cancelFlag the value for accounting_subject.cancel_flag
     */
    public void setCancelFlag(Byte cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    /**
     * 
     * {@linkplain #beginPeriod}
     *
     * @return the value of accounting_subject.begin_period
     */
    public String getBeginPeriod() {
        return beginPeriod;
    }

    /**
     * 
     * {@linkplain #beginPeriod}
     * @param beginPeriod the value for accounting_subject.begin_period
     */
    public void setBeginPeriod(String beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    /**
     * 
     * {@linkplain #beginYear}
     *
     * @return the value of accounting_subject.begin_year
     */
    public String getBeginYear() {
        return beginYear;
    }

    /**
     * 
     * {@linkplain #beginYear}
     * @param beginYear the value for accounting_subject.begin_year
     */
    public void setBeginYear(String beginYear) {
        this.beginYear = beginYear;
    }

    /**
     * 
     * {@linkplain #bothDirection}
     *
     * @return the value of accounting_subject.both_direction
     */
    public String getBothDirection() {
        return bothDirection;
    }

    /**
     * 
     * {@linkplain #bothDirection}
     * @param bothDirection the value for accounting_subject.both_direction
     */
    public void setBothDirection(String bothDirection) {
        this.bothDirection = bothDirection;
    }

    /**
     * 
     * {@linkplain #cashBankFlag}
     *
     * @return the value of accounting_subject.cash_bank_flag
     */
    public Byte getCashBankFlag() {
        return cashBankFlag;
    }

    /**
     * 
     * {@linkplain #cashBankFlag}
     * @param cashBankFlag the value for accounting_subject.cash_bank_flag
     */
    public void setCashBankFlag(Byte cashBankFlag) {
        this.cashBankFlag = cashBankFlag;
    }

    /**
     * 
     * {@linkplain #incurFlag}
     *
     * @return the value of accounting_subject.incur_flag
     */
    public Byte getIncurFlag() {
        return incurFlag;
    }

    /**
     * 
     * {@linkplain #incurFlag}
     * @param incurFlag the value for accounting_subject.incur_flag
     */
    public void setIncurFlag(Byte incurFlag) {
        this.incurFlag = incurFlag;
    }

    /**
     * 
     * {@linkplain #glorgBookCreation}
     *
     * @return the value of accounting_subject.glorg_book_creation
     */
    public String getGlorgBookCreation() {
        return glorgBookCreation;
    }

    /**
     * 
     * {@linkplain #glorgBookCreation}
     * @param glorgBookCreation the value for accounting_subject.glorg_book_creation
     */
    public void setGlorgBookCreation(String glorgBookCreation) {
        this.glorgBookCreation = glorgBookCreation;
    }

    /**
     * 
     * {@linkplain #createYear}
     *
     * @return the value of accounting_subject.create_year
     */
    public String getCreateYear() {
        return createYear;
    }

    /**
     * 
     * {@linkplain #createYear}
     * @param createYear the value for accounting_subject.create_year
     */
    public void setCreateYear(String createYear) {
        this.createYear = createYear;
    }

    /**
     * 
     * {@linkplain #createPeriod}
     *
     * @return the value of accounting_subject.create_period
     */
    public String getCreatePeriod() {
        return createPeriod;
    }

    /**
     * 
     * {@linkplain #createPeriod}
     * @param createPeriod the value for accounting_subject.create_period
     */
    public void setCreatePeriod(String createPeriod) {
        this.createPeriod = createPeriod;
    }

    /**
     * 
     * {@linkplain #controlSystem}
     *
     * @return the value of accounting_subject.control_system
     */
    public String getControlSystem() {
        return controlSystem;
    }

    /**
     * 
     * {@linkplain #controlSystem}
     * @param controlSystem the value for accounting_subject.control_system
     */
    public void setControlSystem(String controlSystem) {
        this.controlSystem = controlSystem;
    }

    /**
     * 
     * {@linkplain #delFlag}
     *
     * @return the value of accounting_subject.del_flag
     */
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 
     * {@linkplain #delFlag}
     * @param delFlag the value for accounting_subject.del_flag
     */
    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 
     * {@linkplain #endFlag}
     *
     * @return the value of accounting_subject.end_flag
     */
    public Byte getEndFlag() {
        return endFlag;
    }

    /**
     * 
     * {@linkplain #endFlag}
     * @param endFlag the value for accounting_subject.end_flag
     */
    public void setEndFlag(Byte endFlag) {
        this.endFlag = endFlag;
    }

    /**
     * 
     * {@linkplain #endPeriod}
     *
     * @return the value of accounting_subject.end_period
     */
    public String getEndPeriod() {
        return endPeriod;
    }

    /**
     * 
     * {@linkplain #endPeriod}
     * @param endPeriod the value for accounting_subject.end_period
     */
    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    /**
     * 
     * {@linkplain #endYear}
     *
     * @return the value of accounting_subject.end_year
     */
    public String getEndYear() {
        return endYear;
    }

    /**
     * 
     * {@linkplain #endYear}
     * @param endYear the value for accounting_subject.end_year
     */
    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    /**
     * 
     * {@linkplain #recordedFlag}
     *
     * @return the value of accounting_subject.recorded_flag
     */
    public Byte getRecordedFlag() {
        return recordedFlag;
    }

    /**
     * 
     * {@linkplain #recordedFlag}
     * @param recordedFlag the value for accounting_subject.recorded_flag
     */
    public void setRecordedFlag(Byte recordedFlag) {
        this.recordedFlag = recordedFlag;
    }

    /**
     * 
     * {@linkplain #innerSubject}
     *
     * @return the value of accounting_subject.inner_subject
     */
    public String getInnerSubject() {
        return innerSubject;
    }

    /**
     * 
     * {@linkplain #innerSubject}
     * @param innerSubject the value for accounting_subject.inner_subject
     */
    public void setInnerSubject(String innerSubject) {
        this.innerSubject = innerSubject;
    }

    /**
     * 
     * {@linkplain #outFlag}
     *
     * @return the value of accounting_subject.out_flag
     */
    public Byte getOutFlag() {
        return outFlag;
    }

    /**
     * 
     * {@linkplain #outFlag}
     * @param outFlag the value for accounting_subject.out_flag
     */
    public void setOutFlag(Byte outFlag) {
        this.outFlag = outFlag;
    }

    /**
     * 
     * {@linkplain #glorgBook}
     *
     * @return the value of accounting_subject.glorg_book
     */
    public String getGlorgBook() {
        return glorgBook;
    }

    /**
     * 
     * {@linkplain #glorgBook}
     * @param glorgBook the value for accounting_subject.glorg_book
     */
    public void setGlorgBook(String glorgBook) {
        this.glorgBook = glorgBook;
    }

    /**
     * 
     * {@linkplain #groupSubject}
     *
     * @return the value of accounting_subject.group_subject
     */
    public String getGroupSubject() {
        return groupSubject;
    }

    /**
     * 
     * {@linkplain #groupSubject}
     * @param groupSubject the value for accounting_subject.group_subject
     */
    public void setGroupSubject(String groupSubject) {
        this.groupSubject = groupSubject;
    }

    /**
     * 
     * {@linkplain #subjectScheme}
     *
     * @return the value of accounting_subject.subject_scheme
     */
    public String getSubjectScheme() {
        return subjectScheme;
    }

    /**
     * 
     * {@linkplain #subjectScheme}
     * @param subjectScheme the value for accounting_subject.subject_scheme
     */
    public void setSubjectScheme(String subjectScheme) {
        this.subjectScheme = subjectScheme;
    }

    /**
     * 
     * {@linkplain #sealFlag}
     *
     * @return the value of accounting_subject.seal_flag
     */
    public Byte getSealFlag() {
        return sealFlag;
    }

    /**
     * 
     * {@linkplain #sealFlag}
     * @param sealFlag the value for accounting_subject.seal_flag
     */
    public void setSealFlag(Byte sealFlag) {
        this.sealFlag = sealFlag;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of accounting_subject.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for accounting_subject.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #printLevel}
     *
     * @return the value of accounting_subject.print_level
     */
    public Byte getPrintLevel() {
        return printLevel;
    }

    /**
     * 
     * {@linkplain #printLevel}
     * @param printLevel the value for accounting_subject.print_level
     */
    public void setPrintLevel(Byte printLevel) {
        this.printLevel = printLevel;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of accounting_subject.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for accounting_subject.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of accounting_subject.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for accounting_subject.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of accounting_subject.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for accounting_subject.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of accounting_subject.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for accounting_subject.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}