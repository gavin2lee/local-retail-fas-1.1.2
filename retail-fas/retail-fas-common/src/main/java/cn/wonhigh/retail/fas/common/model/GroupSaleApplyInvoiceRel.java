package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-29 11:56:15
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
public class GroupSaleApplyInvoiceRel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2582095235489176286L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 开票申请单据号
     */
    private String invoiceApplyNo;

    /**
     * 开票申请日期
     */
    private Date invoiceApplyDate;

    /**
     * 发票单据号
     */
    private String invoiceNo;

    /**
     * 发票登记日期
     */
    private Date invoiceDate;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of group_sale_apply_invoice_rel.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for group_sale_apply_invoice_rel.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of group_sale_apply_invoice_rel.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for group_sale_apply_invoice_rel.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     *
     * @return the value of group_sale_apply_invoice_rel.invoice_apply_no
     */
    public String getInvoiceApplyNo() {
        return invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     * @param invoiceApplyNo the value for group_sale_apply_invoice_rel.invoice_apply_no
     */
    public void setInvoiceApplyNo(String invoiceApplyNo) {
        this.invoiceApplyNo = invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of group_sale_apply_invoice_rel.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for group_sale_apply_invoice_rel.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of group_sale_apply_invoice_rel.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for group_sale_apply_invoice_rel.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of group_sale_apply_invoice_rel.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for group_sale_apply_invoice_rel.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of group_sale_apply_invoice_rel.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for group_sale_apply_invoice_rel.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of group_sale_apply_invoice_rel.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for group_sale_apply_invoice_rel.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of group_sale_apply_invoice_rel.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for group_sale_apply_invoice_rel.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of group_sale_apply_invoice_rel.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for group_sale_apply_invoice_rel.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}