package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-25 10:05:41
 * @version 1.0.1
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class CompanyBalanceBill implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5421854040653235576L;
	
	/**
	 * 单据类型编号
	 */
	private String companyBillCode;
	
	/**
	 * 结算公司编码
	 */
	private String companyNo;
	
	/**
	 * 结算公司名称
	 */
	private String companyName;
	
	/**
	 * 结算公司类型(1:总部 2:地区)
	 */
	private Integer companyType;
	
	/**
	 * 结算公司类型名称
	 */
	private String companyTypeName;
	
	/**
	 * 结算类型编号
	 */
	private Integer balanceNo;
	
	/**
	 * 结算类型编码
	 */
	private String balanceCode;
	
	/**
	 * 结算类型名称
	 */
	private String balanceName;
	
	/**
	 * 主体类型(1:总部 2:地区)
	 */
	private Byte partType;
	
	/**
	 * 主体类型名称
	 */
	private String partTypeName;
	
	/**
	 * 业务类型(1:采购 2:销售)
	 */
	private Byte businessType;
	
	/**
	 * 业务类型名称
	 */
	private String businessTypeName;
	
	/**
	 * 是否做结算单(0:否 1:是)
	 */
	private Byte isBalance;
	
	/**
	 * 单据类型编码
	 */
	private String billCode;
	
	/**
	 * 单据类型名称
	 */
	private String billName;
	
	/**
	 * 出入库类型(1:出库 2:入库)
	 */
	private Byte transferType;
	
	/**
	 * 出入库类型名称
	 */
	private String transferTypeName;
	
	/**
	 * 单据性质(1:发货单 2:收货单)
	 */
	private Byte sendReceiveType;
	
	/**
	 * 单据性质名称
	 */
	private String sendReceiveTypeName;
	
	/**
	 * 是否用于统计(0:否 1:是)
	 */
	private Byte isCount;
	
	/**
	 * 来源系统
	 */
	private String relSys;
	
	/**
	 * 所在表名
	 */
	private String tableName;
	
	/**
	 * 描述说明
	 */
	private String describe;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

	public String getCompanyBillCode() {
		return companyBillCode;
	}

	public void setCompanyBillCode(String companyBillCode) {
		this.companyBillCode = companyBillCode;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getCompanyTypeName() {
		return companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}

	public Integer getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(Integer balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getBalanceCode() {
		return balanceCode;
	}

	public void setBalanceCode(String balanceCode) {
		this.balanceCode = balanceCode;
	}

	public String getBalanceName() {
		return balanceName;
	}

	public void setBalanceName(String balanceName) {
		this.balanceName = balanceName;
	}

	public Byte getPartType() {
		return partType;
	}

	public void setPartType(Byte partType) {
		this.partType = partType;
	}

	public String getPartTypeName() {
		return partTypeName;
	}

	public void setPartTypeName(String partTypeName) {
		this.partTypeName = partTypeName;
	}

	public Byte getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Byte businessType) {
		this.businessType = businessType;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public Byte getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(Byte isBalance) {
		this.isBalance = isBalance;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public Byte getTransferType() {
		return transferType;
	}

	public void setTransferType(Byte transferType) {
		this.transferType = transferType;
	}

	public String getTransferTypeName() {
		return transferTypeName;
	}

	public void setTransferTypeName(String transferTypeName) {
		this.transferTypeName = transferTypeName;
	}

	public Byte getSendReceiveType() {
		return sendReceiveType;
	}

	public void setSendReceiveType(Byte sendReceiveType) {
		this.sendReceiveType = sendReceiveType;
	}

	public String getSendReceiveTypeName() {
		return sendReceiveTypeName;
	}

	public void setSendReceiveTypeName(String sendReceiveTypeName) {
		this.sendReceiveTypeName = sendReceiveTypeName;
	}

	public Byte getIsCount() {
		return isCount;
	}

	public void setIsCount(Byte isCount) {
		this.isCount = isCount;
	}

	public String getRelSys() {
		return relSys;
	}

	public void setRelSys(String relSys) {
		this.relSys = relSys;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}