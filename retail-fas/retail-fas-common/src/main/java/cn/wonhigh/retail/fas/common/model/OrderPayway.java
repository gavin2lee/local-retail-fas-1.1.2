package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-11-02 15:28:11
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
public class OrderPayway implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9134445551077627166L;

	private String id;

    private String orderNo;

    private String payCode;

    private String payName;

    private String paywayNum;

    private BigDecimal amount;

    private BigDecimal poundage;
    
    private BigDecimal rate;

    private String remark;

    private Byte status;

    private Byte paywayFlag;

    private String paywayTicketNo;

    private Date paywayDatetime;

    private Date outDate;

    private Byte orderType;

    private String zoneYyyymm;

    private String auditor;

    private Date auditorTime;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String shardingFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPaywayNum() {
        return paywayNum;
    }

    public void setPaywayNum(String paywayNum) {
        this.paywayNum = paywayNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getPaywayFlag() {
        return paywayFlag;
    }

    public void setPaywayFlag(Byte paywayFlag) {
        this.paywayFlag = paywayFlag;
    }

    public String getPaywayTicketNo() {
        return paywayTicketNo;
    }

    public void setPaywayTicketNo(String paywayTicketNo) {
        this.paywayTicketNo = paywayTicketNo;
    }

    public Date getPaywayDatetime() {
        return paywayDatetime;
    }

    public void setPaywayDatetime(Date paywayDatetime) {
        this.paywayDatetime = paywayDatetime;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public String getZoneYyyymm() {
        return zoneYyyymm;
    }

    public void setZoneYyyymm(String zoneYyyymm) {
        this.zoneYyyymm = zoneYyyymm;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
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

    public String getShardingFlag() {
        return shardingFlag;
    }

    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}