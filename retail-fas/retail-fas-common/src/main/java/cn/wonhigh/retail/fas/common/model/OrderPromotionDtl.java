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
public class OrderPromotionDtl implements Serializable {
    /**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 顺序号
     */
    private Integer seqId;

    /**
     * 业务编号,订单号或退换货号
     */
    private String orderNo;

    /**
     * 业务明细ID
     */
    private String orderDtlId;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    /**
     * 扣率,如17.00代表17.00%
     */
    private BigDecimal discount;

    /**
     * 扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)
     */
    private Boolean discountType;

    /**
     * 扣率来源编号
     */
    private String discountSourceId;

    /**
     * 订单类型，0-销售，1-换货，2-退货
     */
    private Boolean orderType;

    /**
     * 创建人编号
     */
    private String createUserNo;

    /**
     * 创建人姓名
     */
    private String createUser;

    /**
     * 创建时间
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
     * @return the value of order_promotion_dtl.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for order_promotion_dtl.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #seqId}
     *
     * @return the value of order_promotion_dtl.seq_id
     */
    public Integer getSeqId() {
        return seqId;
    }

    /**
     * 
     * {@linkplain #seqId}
     * @param seqId the value for order_promotion_dtl.seq_id
     */
    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of order_promotion_dtl.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for order_promotion_dtl.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     *
     * @return the value of order_promotion_dtl.order_dtl_id
     */
    public String getOrderDtlId() {
        return orderDtlId;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     * @param orderDtlId the value for order_promotion_dtl.order_dtl_id
     */
    public void setOrderDtlId(String orderDtlId) {
        this.orderDtlId = orderDtlId;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of order_promotion_dtl.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for order_promotion_dtl.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of order_promotion_dtl.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for order_promotion_dtl.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of order_promotion_dtl.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for order_promotion_dtl.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #discountType}
     *
     * @return the value of order_promotion_dtl.discount_type
     */
    public Boolean getDiscountType() {
        return discountType;
    }

    /**
     * 
     * {@linkplain #discountType}
     * @param discountType the value for order_promotion_dtl.discount_type
     */
    public void setDiscountType(Boolean discountType) {
        this.discountType = discountType;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     *
     * @return the value of order_promotion_dtl.discount_source_id
     */
    public String getDiscountSourceId() {
        return discountSourceId;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     * @param discountSourceId the value for order_promotion_dtl.discount_source_id
     */
    public void setDiscountSourceId(String discountSourceId) {
        this.discountSourceId = discountSourceId;
    }

    /**
     * 
     * {@linkplain #orderType}
     *
     * @return the value of order_promotion_dtl.order_type
     */
    public Boolean getOrderType() {
        return orderType;
    }

    /**
     * 
     * {@linkplain #orderType}
     * @param orderType the value for order_promotion_dtl.order_type
     */
    public void setOrderType(Boolean orderType) {
        this.orderType = orderType;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of order_promotion_dtl.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for order_promotion_dtl.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of order_promotion_dtl.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for order_promotion_dtl.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of order_promotion_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for order_promotion_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of order_promotion_dtl.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for order_promotion_dtl.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of order_promotion_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for order_promotion_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}