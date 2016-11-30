package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 付款条款 
 * @author ouyang.zm
 * @date  2014-10-29 16:18:05
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
public class PaymentTerm extends FasBaseModel implements SequenceStrId {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2734687047085138143L;

	/**
     * 付款条款编码
     */
    private String paymentNo;

    /**
     * 付款条款描述
     */
    private String expressed;

    /**
     * 信用天数
     */
    private Integer creditDay;

    /**
     * 折率、扣率
     */
    private BigDecimal discount;

    /**
     * 启用标志 1、启用 0、未启用
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
     * 
     * {@linkplain #paymentNo}
     *
     * @return the value of payment_term.payment_no
     */
    public String getPaymentNo() {
        return paymentNo;
    }

    /**
     * 
     * {@linkplain #paymentNo}
     * @param paymentNo the value for payment_term.payment_no
     */
    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    /**
     * 
     * {@linkplain #expressed}
     *
     * @return the value of payment_term.expressed
     */
    public String getExpressed() {
        return expressed;
    }

    /**
     * 
     * {@linkplain #expressed}
     * @param expressed the value for payment_term.expressed
     */
    public void setExpressed(String expressed) {
        this.expressed = expressed;
    }

    /**
     * 
     * {@linkplain #creditDay}
     *
     * @return the value of payment_term.credit_day
     */
    public Integer getCreditDay() {
        return creditDay;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
     * 
     * {@linkplain #creditDay}
     * @param creditDay the value for payment_term.credit_day
     */
    public void setCreditDay(Integer creditDay) {
        this.creditDay = creditDay;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of payment_term.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for payment_term.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }


    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of payment_term.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for payment_term.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of payment_term.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for payment_term.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of payment_term.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for payment_term.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of payment_term.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for payment_term.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}