package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 公司财务账套
 * 
 * @author ouyang.zm
 * @date 2015-01-22 10:06:49
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class FinancialAccount extends FasBaseModel implements SequenceStrId {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3136967769263337124L;

	/**
	 * 公司编号
	 */
	private String companyNo;

	/**
	 * 承担总部职能 1、是 0、否
	 */
	private Byte groupLeadRole;

	/**
	 * NC账套编号
	 */
	private String ncId;

	/**
	 * 父项公司编号
	 */
	private String parentCompany;

	/**
	 * 是否属于分公司 1、是 0、否
	 */
	private Byte childCompany;

	/**
	 * 相关供应商
	 */
	private String supplierNo;

	/**
	 * 默认价格区
	 */
	private String priceZone;

	/**
	 * 结转收入凭证类别
	 */
	private String revenueJournalType;

	/**
	 * 结转成本凭证类别
	 */
	private String costJournalType;

	/**
	 * 存贷负债凭证类别
	 */
	private String liabilitiesJournalType;

	/**
	 * 结转对内收入凭证类别
	 */
	private String internalJournalType;

	/**
	 * 辅助核算类别1
	 */
	private String assistProject01;

	/**
	 * 辅助核算类别2
	 */
	private String assistProject02;

	/**
	 * 辅助核算类别3
	 */
	private String assistProject03;

	/**
	 * 辅助核算类别4
	 */
	private String assistProject04;

	/**
	 * 辅助核算类别5
	 */
	private String assistProject05;

	/**
	 * 辅助核算类别6
	 */
	private String assistProject06;

	/**
	 * 辅助核算类别7
	 */
	private String assistProject07;

	/**
	 * 辅助核算类别8
	 */
	private String assistProject08;

	/**
	 * 启用标志 1、已启用 0、已停用
	 */
	private Integer status;

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
	 * 扩展属性
	 */
	// 公司名称
	private String companyName;
	// 供应商名称
	private String supplierName;
	// 父项公司
	private String parentCompanyName;
	// 默认价格区名称
	private String priceZoneName;
	//大区名称
	private String zoneName;
	
	public String getParentCompanyName() {
		return parentCompanyName;
	}

	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * 
	 * {@linkplain #companyNo}
	 * 
	 * @return the value of financial_account.company_no
	 */
	public String getCompanyNo() {
		return companyNo;
	}

	/**
	 * 
	 * {@linkplain #companyNo}
	 * 
	 * @param companyNo
	 *            the value for financial_account.company_no
	 */
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	/**
	 * 
	 * {@linkplain #groupLeadRole}
	 * 
	 * @return the value of financial_account.group_lead_role
	 */
	public Byte getGroupLeadRole() {
		return groupLeadRole;
	}

	public String getPriceZone() {
		return priceZone;
	}

	public void setPriceZone(String priceZone) {
		this.priceZone = priceZone;
	}

	/**
	 * 
	 * {@linkplain #groupLeadRole}
	 * 
	 * @param groupLeadRole
	 *            the value for financial_account.group_lead_role
	 */
	public void setGroupLeadRole(Byte groupLeadRole) {
		this.groupLeadRole = groupLeadRole;
	}

	/**
	 * 
	 * {@linkplain #ncId}
	 * 
	 * @return the value of financial_account.nc_id
	 */
	public String getNcId() {
		return ncId;
	}

	public String getPriceZoneName() {
		return priceZoneName;
	}

	public void setPriceZoneName(String priceZoneName) {
		this.priceZoneName = priceZoneName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * 
	 * {@linkplain #ncId}
	 * 
	 * @param ncId
	 *            the value for financial_account.nc_id
	 */
	public void setNcId(String ncId) {
		this.ncId = ncId;
	}

	/**
	 * 
	 * {@linkplain #parentCompany}
	 * 
	 * @return the value of financial_account.parent_company
	 */
	public String getParentCompany() {
		return parentCompany;
	}

	/**
	 * 
	 * {@linkplain #parentCompany}
	 * 
	 * @param parentCompany
	 *            the value for financial_account.parent_company
	 */
	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}

	/**
	 * 
	 * {@linkplain #childCompany}
	 * 
	 * @return the value of financial_account.child_company
	 */
	public Byte getChildCompany() {
		return childCompany;
	}

	/**
	 * 
	 * {@linkplain #childCompany}
	 * 
	 * @param childCompany
	 *            the value for financial_account.child_company
	 */
	public void setChildCompany(Byte childCompany) {
		this.childCompany = childCompany;
	}

	/**
	 * 
	 * {@linkplain #supplierNo}
	 * 
	 * @return the value of financial_account.supplier_no
	 */
	public String getSupplierNo() {
		return supplierNo;
	}

	/**
	 * 
	 * {@linkplain #supplierNo}
	 * 
	 * @param supplierNo
	 *            the value for financial_account.supplier_no
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	/**
	 * 
	 * {@linkplain #revenueJournalType}
	 * 
	 * @return the value of financial_account.revenue_journal_type
	 */
	public String getRevenueJournalType() {
		return revenueJournalType;
	}

	/**
	 * 
	 * {@linkplain #revenueJournalType}
	 * 
	 * @param revenueJournalType
	 *            the value for financial_account.revenue_journal_type
	 */
	public void setRevenueJournalType(String revenueJournalType) {
		this.revenueJournalType = revenueJournalType;
	}

	/**
	 * 
	 * {@linkplain #costJournalType}
	 * 
	 * @return the value of financial_account.cost_journal_type
	 */
	public String getCostJournalType() {
		return costJournalType;
	}

	/**
	 * 
	 * {@linkplain #costJournalType}
	 * 
	 * @param costJournalType
	 *            the value for financial_account.cost_journal_type
	 */
	public void setCostJournalType(String costJournalType) {
		this.costJournalType = costJournalType;
	}

	/**
	 * 
	 * {@linkplain #liabilitiesJournalType}
	 * 
	 * @return the value of financial_account.liabilities_journal_type
	 */
	public String getLiabilitiesJournalType() {
		return liabilitiesJournalType;
	}

	/**
	 * 
	 * {@linkplain #liabilitiesJournalType}
	 * 
	 * @param liabilitiesJournalType
	 *            the value for financial_account.liabilities_journal_type
	 */
	public void setLiabilitiesJournalType(String liabilitiesJournalType) {
		this.liabilitiesJournalType = liabilitiesJournalType;
	}

	/**
	 * 
	 * {@linkplain #internalJournalType}
	 * 
	 * @return the value of financial_account.internal_journal_type
	 */
	public String getInternalJournalType() {
		return internalJournalType;
	}

	/**
	 * 
	 * {@linkplain #internalJournalType}
	 * 
	 * @param internalJournalType
	 *            the value for financial_account.internal_journal_type
	 */
	public void setInternalJournalType(String internalJournalType) {
		this.internalJournalType = internalJournalType;
	}

	/**
	 * 
	 * {@linkplain #assistProject01}
	 * 
	 * @return the value of financial_account.assist_project_01
	 */
	public String getAssistProject01() {
		return assistProject01;
	}

	/**
	 * 
	 * {@linkplain #assistProject01}
	 * 
	 * @param assistProject01
	 *            the value for financial_account.assist_project_01
	 */
	public void setAssistProject01(String assistProject01) {
		this.assistProject01 = assistProject01;
	}

	/**
	 * 
	 * {@linkplain #assistProject02}
	 * 
	 * @return the value of financial_account.assist_project_02
	 */
	public String getAssistProject02() {
		return assistProject02;
	}

	/**
	 * 
	 * {@linkplain #assistProject02}
	 * 
	 * @param assistProject02
	 *            the value for financial_account.assist_project_02
	 */
	public void setAssistProject02(String assistProject02) {
		this.assistProject02 = assistProject02;
	}

	/**
	 * 
	 * {@linkplain #assistProject03}
	 * 
	 * @return the value of financial_account.assist_project_03
	 */
	public String getAssistProject03() {
		return assistProject03;
	}

	/**
	 * 
	 * {@linkplain #assistProject03}
	 * 
	 * @param assistProject03
	 *            the value for financial_account.assist_project_03
	 */
	public void setAssistProject03(String assistProject03) {
		this.assistProject03 = assistProject03;
	}

	/**
	 * 
	 * {@linkplain #assistProject04}
	 * 
	 * @return the value of financial_account.assist_project_04
	 */
	public String getAssistProject04() {
		return assistProject04;
	}

	/**
	 * 
	 * {@linkplain #assistProject04}
	 * 
	 * @param assistProject04
	 *            the value for financial_account.assist_project_04
	 */
	public void setAssistProject04(String assistProject04) {
		this.assistProject04 = assistProject04;
	}

	/**
	 * 
	 * {@linkplain #assistProject05}
	 * 
	 * @return the value of financial_account.assist_project_05
	 */
	public String getAssistProject05() {
		return assistProject05;
	}

	/**
	 * 
	 * {@linkplain #assistProject05}
	 * 
	 * @param assistProject05
	 *            the value for financial_account.assist_project_05
	 */
	public void setAssistProject05(String assistProject05) {
		this.assistProject05 = assistProject05;
	}

	/**
	 * 
	 * {@linkplain #assistProject06}
	 * 
	 * @return the value of financial_account.assist_project_06
	 */
	public String getAssistProject06() {
		return assistProject06;
	}

	/**
	 * 
	 * {@linkplain #assistProject06}
	 * 
	 * @param assistProject06
	 *            the value for financial_account.assist_project_06
	 */
	public void setAssistProject06(String assistProject06) {
		this.assistProject06 = assistProject06;
	}

	/**
	 * 
	 * {@linkplain #assistProject07}
	 * 
	 * @return the value of financial_account.assist_project_07
	 */
	public String getAssistProject07() {
		return assistProject07;
	}

	/**
	 * 
	 * {@linkplain #assistProject07}
	 * 
	 * @param assistProject07
	 *            the value for financial_account.assist_project_07
	 */
	public void setAssistProject07(String assistProject07) {
		this.assistProject07 = assistProject07;
	}

	/**
	 * 
	 * {@linkplain #assistProject08}
	 * 
	 * @return the value of financial_account.assist_project_08
	 */
	public String getAssistProject08() {
		return assistProject08;
	}

	/**
	 * 
	 * {@linkplain #assistProject08}
	 * 
	 * @param assistProject08
	 *            the value for financial_account.assist_project_08
	 */
	public void setAssistProject08(String assistProject08) {
		this.assistProject08 = assistProject08;
	}

	/**
	 * 
	 * {@linkplain #status}
	 * 
	 * @return the value of financial_account.status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * {@linkplain #status}
	 * 
	 * @param status
	 *            the value for financial_account.status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @return the value of financial_account.create_user
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @param createUser
	 *            the value for financial_account.create_user
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @return the value of financial_account.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @param createTime
	 *            the value for financial_account.create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @return the value of financial_account.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @param updateUser
	 *            the value for financial_account.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @return the value of financial_account.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @param updateTime
	 *            the value for financial_account.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @return the value of financial_account.remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @param remark
	 *            the value for financial_account.remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}