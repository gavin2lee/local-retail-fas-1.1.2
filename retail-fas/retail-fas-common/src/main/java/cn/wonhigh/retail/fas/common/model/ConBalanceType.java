package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-24 10:31:04
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
public class ConBalanceType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2567241690937577839L;

	private String id;

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
     * 业务类型(1:采购 2:销售)
     */
    private Byte businessType;

    /**
     * 是否做结算单(0:否 1:是)
     */
    private Byte isBalance;

    /**
     * 状态(0:禁用 1:启用)
     */
    private Byte status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Byte getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    public Byte getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(Byte isBalance) {
        this.isBalance = isBalance;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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