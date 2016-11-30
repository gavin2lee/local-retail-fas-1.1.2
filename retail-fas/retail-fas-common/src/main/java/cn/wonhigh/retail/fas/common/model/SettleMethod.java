package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 结算方式
 * @author ouyang.zm
 * @date  2014-10-29 16:28:22
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
public class SettleMethod extends FasBaseModel implements SequenceStrId {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3762910914617732142L;

	/**
     * 结算方式编码
     */
    private String settleCode;

    /**
     * 结算方式名称
     */
    private String settleName;

    /**
     * 结算方式类别 1、现金 2、支票 3、汇兑 4、汇票 5、信用证 6、银行呈兑汇票
     */
    private Byte settleType;

    /**
     * 业务类型 1、现金业务 2、银行业务 3、票据业务 
     */
    private Byte businessType;

    /**
     * 是否支付手续费 1、是 2、否
     */
    private Byte payFeesFlag;

    /**
     * 承担方
     */
    private String bearer;

    /**
     * 支付方式
     */
    private String paymentMode;

    /**
     * 启用标志 1、启用 0、未启用
     */
    private Integer status;

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
     * 
     * {@linkplain #settleCode}
     *
     * @return the value of settle_method.settle_code
     */
    public String getSettleCode() {
        return settleCode;
    }

    /**
     * 
     * {@linkplain #settleCode}
     * @param settleCode the value for settle_method.settle_code
     */
    public void setSettleCode(String settleCode) {
        this.settleCode = settleCode;
    }

    /**
     * 
     * {@linkplain #settleName}
     *
     * @return the value of settle_method.settle_name
     */
    public String getSettleName() {
        return settleName;
    }

    /**
     * 
     * {@linkplain #settleName}
     * @param settleName the value for settle_method.settle_name
     */
    public void setSettleName(String settleName) {
        this.settleName = settleName;
    }

    /**
     * 
     * {@linkplain #settleType}
     *
     * @return the value of settle_method.settle_type
     */
    public Byte getSettleType() {
        return settleType;
    }

    /**
     * 
     * {@linkplain #settleType}
     * @param settleType the value for settle_method.settle_type
     */
    public void setSettleType(Byte settleType) {
        this.settleType = settleType;
    }

    /**
     * 
     * {@linkplain #businessType}
     *
     * @return the value of settle_method.business_type
     */
    public Byte getBusinessType() {
        return businessType;
    }

    /**
     * 
     * {@linkplain #businessType}
     * @param businessType the value for settle_method.business_type
     */
    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    /**
     * 
     * {@linkplain #payFeesFlag}
     *
     * @return the value of settle_method.pay_fees_flag
     */
    public Byte getPayFeesFlag() {
        return payFeesFlag;
    }

    /**
     * 
     * {@linkplain #payFeesFlag}
     * @param payFeesFlag the value for settle_method.pay_fees_flag
     */
    public void setPayFeesFlag(Byte payFeesFlag) {
        this.payFeesFlag = payFeesFlag;
    }

    /**
     * 
     * {@linkplain #bearer}
     *
     * @return the value of settle_method.bearer
     */
    public String getBearer() {
        return bearer;
    }

    /**
     * 
     * {@linkplain #bearer}
     * @param bearer the value for settle_method.bearer
     */
    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     *
     * @return the value of settle_method.payment_mode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     * @param paymentMode the value for settle_method.payment_mode
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * 
     * {@linkplain #stopFlag}
     *
     * @return the value of settle_method.stop_flag
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #stopFlag}
     * @param stopFlag the value for settle_method.stop_flag
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of settle_method.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for settle_method.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of settle_method.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for settle_method.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of settle_method.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for settle_method.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of settle_method.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for settle_method.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}