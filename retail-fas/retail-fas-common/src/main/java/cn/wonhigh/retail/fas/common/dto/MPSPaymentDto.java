package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MPSPaymentDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -251718955681838574L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 支付编码
     */
    private String payCode;

    /**
     * 支付名称
     */
    private String payName;

    /**
     * 英文名称
     */
    private String payEname;

    /**
     * 默认币别,默认人民币
     */
    private String currencyCode;

    /**
     * 是否有卡标识,0-无卡 1-有卡 默认为0
     */
    private Byte cardFlag;

    /**
     * 是否积分标识,0-不计积分 1-计积分
     */
    private Byte scoreFlag;

    /**
     * 换算率,默认为1
     */
    private BigDecimal ratio;

    /**
     * 提成率,默认为1
     */
    private BigDecimal salesRatio;

    /**
     * 是否结算标识,0-结算 1-不结算
     */
    private Byte countFlag;

    /**
     * 是否找零标识,0-找零 1-不找零 默认找0
     */
    private Byte changeFlag;

    /**
     * 是否虚收款,0-是 1-否
     */
    private Byte virtualFlag;

    /**
     * 是否通用,0-是，1-否
     */
    private Byte commonFlag;

    /**
     * 顺序号
     */
    private Short orderNo;
    
    /**
     * 状态,0-有效 1-无效 默认为0
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

	public Integer getId() {
		return id;
	}

	public String getPayCode() {
		return payCode;
	}

	public String getPayName() {
		return payName;
	}

	public String getPayEname() {
		return payEname;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public Byte getCardFlag() {
		return cardFlag;
	}

	public Byte getScoreFlag() {
		return scoreFlag;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public BigDecimal getSalesRatio() {
		return salesRatio;
	}

	public Byte getCountFlag() {
		return countFlag;
	}

	public Byte getChangeFlag() {
		return changeFlag;
	}

	public Byte getVirtualFlag() {
		return virtualFlag;
	}

	public Byte getCommonFlag() {
		return commonFlag;
	}

	public Short getOrderNo() {
		return orderNo;
	}

	public Byte getStatus() {
		return status;
	}

	public String getRemark() {
		return remark;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public void setPayEname(String payEname) {
		this.payEname = payEname;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCardFlag(Byte cardFlag) {
		this.cardFlag = cardFlag;
	}

	public void setScoreFlag(Byte scoreFlag) {
		this.scoreFlag = scoreFlag;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public void setSalesRatio(BigDecimal salesRatio) {
		this.salesRatio = salesRatio;
	}

	public void setCountFlag(Byte countFlag) {
		this.countFlag = countFlag;
	}

	public void setChangeFlag(Byte changeFlag) {
		this.changeFlag = changeFlag;
	}

	public void setVirtualFlag(Byte virtualFlag) {
		this.virtualFlag = virtualFlag;
	}

	public void setCommonFlag(Byte commonFlag) {
		this.commonFlag = commonFlag;
	}

	public void setOrderNo(Short orderNo) {
		this.orderNo = orderNo;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
