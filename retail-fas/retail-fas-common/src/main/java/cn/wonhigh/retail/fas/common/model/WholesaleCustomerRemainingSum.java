package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 客户余额
 * @author user
 * @date  2016-06-08 10:03:06
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
public class WholesaleCustomerRemainingSum implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户余额
     */
    private BigDecimal remainingAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 冻结客户金额
     */
    private BigDecimal frozenCustomerAmount;
    
    /**
     * 本期收款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal paidAmount;
    
    /**  
     * 本期出库
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sendAmount;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of wholesale_customer_remaining_sum.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for wholesale_customer_remaining_sum.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of wholesale_customer_remaining_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for wholesale_customer_remaining_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of wholesale_customer_remaining_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for wholesale_customer_remaining_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of wholesale_customer_remaining_sum.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for wholesale_customer_remaining_sum.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of wholesale_customer_remaining_sum.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for wholesale_customer_remaining_sum.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #remainingAmount}
     *
     * @return the value of wholesale_customer_remaining_sum.remaining_amount
     */
    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    /**
     * 
     * {@linkplain #remainingAmount}
     * @param remainingAmount the value for wholesale_customer_remaining_sum.remaining_amount
     */
    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of wholesale_customer_remaining_sum.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for wholesale_customer_remaining_sum.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of wholesale_customer_remaining_sum.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for wholesale_customer_remaining_sum.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #frozenCustomerAmount}
     *
     * @return the value of wholesale_customer_remaining_sum.frozen_customer_amount
     */
    public BigDecimal getFrozenCustomerAmount() {
        return frozenCustomerAmount;
    }

    /**
     * 
     * {@linkplain #frozenCustomerAmount}
     * @param frozenCustomerAmount the value for wholesale_customer_remaining_sum.frozen_customer_amount
     */
    public void setFrozenCustomerAmount(BigDecimal frozenCustomerAmount) {
        this.frozenCustomerAmount = frozenCustomerAmount;
    }

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}
    
    
}