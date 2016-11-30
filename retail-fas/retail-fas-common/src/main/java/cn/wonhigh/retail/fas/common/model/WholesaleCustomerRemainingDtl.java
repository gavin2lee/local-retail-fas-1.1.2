package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 客户余额明细
 * @author user
 * @date  2016-06-06 16:23:31
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class WholesaleCustomerRemainingDtl implements Serializable , SequenceId{
    
    private static final long serialVersionUID = -5634200638831836579L;
    
    /**
     * 主键
     */
    private Integer id;

    /**
     * 客户余额主表ID
     */
    private Integer mainId;

    /**
     * 预收款ID （0：支出 无预收款ID）
     */
    private Integer prePaymentId;

    /**
     * 收支类型 （0：收入  1：支出）
     */
    private Integer type;

    /**
     * 金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal money;

    /**
     * 剩余总金额金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal remainingAmount;

    /**
     * 相关单号
     */
    private String refNo;

    /**
     * 交易日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date billDate;
    
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
    @JsonDeserialize(using = JsonDateDeserialize$19.class) 
    private Date createTime;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 排序，用于处理历史数据排序问题
     */
    private Long position;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class) 
    private Date updateTime;

    /**
     * 单据类型
     */
    private Integer billType;

    /**
     * 业务类型
     */
    private Integer bizType;
    
    /**
     * 单据类型
     */
    private String billTypeName;
    
    /**
     * 业务类型
     */
    private String bizTypeName;

    /**
     * 单据编码
     */
    private String billNo;

    /**
     * 返利金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal rebateAmount;
    /**
     * 其他费用
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal otherPrice;

    /**
     * 客户冻结余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal frozenAmount;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of wholesale_customer_remaining_dtl.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for wholesale_customer_remaining_dtl.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #mainId}
     *
     * @return the value of wholesale_customer_remaining_dtl.main_id
     */
    public Integer getMainId() {
        return mainId;
    }

    /**
     * 
     * {@linkplain #mainId}
     * @param mainId the value for wholesale_customer_remaining_dtl.main_id
     */
    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    /**
     * 
     * {@linkplain #prePaymentId}
     *
     * @return the value of wholesale_customer_remaining_dtl.pre_payment_id
     */
    public Integer getPrePaymentId() {
        return prePaymentId;
    }

    /**
     * 
     * {@linkplain #prePaymentId}
     * @param prePaymentId the value for wholesale_customer_remaining_dtl.pre_payment_id
     */
    public void setPrePaymentId(Integer prePaymentId) {
        this.prePaymentId = prePaymentId;
    }

    /**
     * 
     * {@linkplain #money}
     *
     * @return the value of wholesale_customer_remaining_dtl.money
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 
     * {@linkplain #money}
     * @param money the value for wholesale_customer_remaining_dtl.money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 
     * {@linkplain #remainingAmount}
     *
     * @return the value of wholesale_customer_remaining_dtl.remaining_amount
     */
    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    /**
     * 
     * {@linkplain #remainingAmount}
     * @param remainingAmount the value for wholesale_customer_remaining_dtl.remaining_amount
     */
    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    /**
     * 
     * {@linkplain #refNo}
     *
     * @return the value of wholesale_customer_remaining_dtl.ref_no
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * 
     * {@linkplain #refNo}
     * @param refNo the value for wholesale_customer_remaining_dtl.ref_no
     */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of wholesale_customer_remaining_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for wholesale_customer_remaining_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of wholesale_customer_remaining_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for wholesale_customer_remaining_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #position}
     *
     * @return the value of wholesale_customer_remaining_dtl.position
     */
    public Long getPosition() {
        return position;
    }

    /**
     * 
     * {@linkplain #position}
     * @param position the value for wholesale_customer_remaining_dtl.position
     */
    public void setPosition(Long position) {
        this.position = position;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of wholesale_customer_remaining_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for wholesale_customer_remaining_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of wholesale_customer_remaining_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for wholesale_customer_remaining_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     *
     * @return the value of wholesale_customer_remaining_dtl.rebate_amount
     */
    public BigDecimal getRebateAmount() {
        return rebateAmount;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     * @param rebateAmount the value for wholesale_customer_remaining_dtl.rebate_amount
     */
    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    /**
     * 
     * {@linkplain #frozenAmount}
     *
     * @return the value of wholesale_customer_remaining_dtl.frozen_amount
     */
    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    /**
     * 
     * {@linkplain #frozenAmount}
     * @param frozenAmount the value for wholesale_customer_remaining_dtl.frozen_amount
     */
    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
		if (null != billType) {
			setBillTypeName(BillTypeEnums.getNameByNo(billType));
		}
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
		if (null != bizType) {			
			setBizTypeName(WholesaleRemainingTypeEnums.getTypeNameByNo(bizType));
			if (bizType == 2 && getType() == 2) {
				setBizTypeName(WholesaleRemainingTypeEnums.CUSTOMER_PAYMENT.getTypeName());
			}
			if (bizType == 2 && getType() == 0) {
				setBizTypeName(WholesaleRemainingTypeEnums.ORDER_PAYMENT.getTypeName());
			}
		}
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

    
}