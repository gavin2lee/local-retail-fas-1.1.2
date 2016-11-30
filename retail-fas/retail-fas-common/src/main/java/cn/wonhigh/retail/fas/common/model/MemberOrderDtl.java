package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2015-01-20 16:15:46
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
public class MemberOrderDtl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 销售日期
     */
    private Date outStartDate;

    /**
     * 销售日期
     */
    private Date outEndDate;

    /**
     * 公司编号
     */
    private String companyNo;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of member_order_dtl.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for member_order_dtl.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

     /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of member_order_dtl.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for member_order_dtl.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of member_order_dtl.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for member_order_dtl.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of member_order_dtl.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for member_order_dtl.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of member_order_dtl.invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param invoiceAmount the value for member_order_dtl.invoice_amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of member_order_dtl.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for member_order_dtl.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of member_order_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for member_order_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #outStartDate}
     *
     * @return the value of member_order_dtl.out_start_date
     */
    public Date getOutStartDate() {
        return outStartDate;
    }

    /**
     * 
     * {@linkplain #outStartDate}
     * @param outStartDate the value for member_order_dtl.out_start_date
     */
    public void setOutStartDate(Date outStartDate) {
        this.outStartDate = outStartDate;
    }

    /**
     * 
     * {@linkplain #outEndDate}
     *
     * @return the value of member_order_dtl.out_end_date
     */
    public Date getOutEndDate() {
        return outEndDate;
    }

    /**
     * 
     * {@linkplain #outEndDate}
     * @param outEndDate the value for member_order_dtl.out_end_date
     */
    public void setOutEndDate(Date outEndDate) {
        this.outEndDate = outEndDate;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of member_order_dtl.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for member_order_dtl.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }
}