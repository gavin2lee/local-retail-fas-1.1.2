package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
public class OrderAssistant implements Serializable {
    /**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单明细主键ID
     */
    private String orderDtlId;

    /**
     * 营业员主键ID
     */
    private String assistantId;

    /**
     * 营业员工号
     */
    private String assistantNo;

    /**
     * 营业员姓名
     */
    private String assistantName;

    /**
     * 分摊金额,按营业员分摊金额
     */
    private BigDecimal shareAmount;

    /**
     * 营业员人数,单条营业员人数
     */
    private Boolean counts;

    /**
     * 订单类型，0-销售，1-换货，2-退货
     */
    private Boolean orderType;

    /**
     * 建档人姓名
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 最后修改人姓名
     */
    private String updateUser;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of order_assistant.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for order_assistant.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of order_assistant.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for order_assistant.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     *
     * @return the value of order_assistant.order_dtl_id
     */
    public String getOrderDtlId() {
        return orderDtlId;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     * @param orderDtlId the value for order_assistant.order_dtl_id
     */
    public void setOrderDtlId(String orderDtlId) {
        this.orderDtlId = orderDtlId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     *
     * @return the value of order_assistant.assistant_id
     */
    public String getAssistantId() {
        return assistantId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     * @param assistantId the value for order_assistant.assistant_id
     */
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of order_assistant.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for order_assistant.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of order_assistant.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for order_assistant.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #shareAmount}
     *
     * @return the value of order_assistant.share_amount
     */
    public BigDecimal getShareAmount() {
        return shareAmount;
    }

    /**
     * 
     * {@linkplain #shareAmount}
     * @param shareAmount the value for order_assistant.share_amount
     */
    public void setShareAmount(BigDecimal shareAmount) {
        this.shareAmount = shareAmount;
    }

    /**
     * 
     * {@linkplain #counts}
     *
     * @return the value of order_assistant.counts
     */
    public Boolean getCounts() {
        return counts;
    }

    /**
     * 
     * {@linkplain #counts}
     * @param counts the value for order_assistant.counts
     */
    public void setCounts(Boolean counts) {
        this.counts = counts;
    }

    /**
     * 
     * {@linkplain #orderType}
     *
     * @return the value of order_assistant.order_type
     */
    public Boolean getOrderType() {
        return orderType;
    }

    /**
     * 
     * {@linkplain #orderType}
     * @param orderType the value for order_assistant.order_type
     */
    public void setOrderType(Boolean orderType) {
        this.orderType = orderType;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of order_assistant.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for order_assistant.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of order_assistant.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for order_assistant.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of order_assistant.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for order_assistant.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of order_assistant.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for order_assistant.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}