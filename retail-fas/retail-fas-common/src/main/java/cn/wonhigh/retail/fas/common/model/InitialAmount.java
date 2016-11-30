package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-28 17:43:32
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
public class InitialAmount extends FasBaseModel implements Serializable {
    /**
	 * 序列号ID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 结算主体编号
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

    /**
     * 供应商编号
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 内部客户编号
     */
    private String customerNo;

    /**
     * 内部客户名称
     */
    private String customerName;

    /**
     * 初始日期
     */
     @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date initialDate;

    /**
     * 商品编号
     */
    private String itemNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 币别
     */
    private String currency;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 期初牌价额
     */
    private BigDecimal cost;

    /**
     * 期初应付金额
     */
    private BigDecimal amount;

    
    /**
     * 备注
     */
    private String remark;


	/**
     * 结算类型
     */
    private Integer balanceType;
    
    /**
     * 到期日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date dueDate;
    
    /**
     * 付款状态
     */
    private int payStatus;
    
    /**
     * 付款状态
     */
    private String payStatusStr;
    
    /**
     * 付款日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date payDate;


    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of initial_amount.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for initial_amount.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of initial_amount.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for initial_amount.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of initial_amount.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for initial_amount.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of initial_amount.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for initial_amount.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of initial_amount.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for initial_amount.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of initial_amount.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for initial_amount.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #initialDate}
     *
     * @return the value of initial_amount.initial_date
     */
    public Date getInitialDate() {
        return initialDate;
    }

    /**
     * 
     * {@linkplain #initialDate}
     * @param initialDate the value for initial_amount.initial_date
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of initial_amount.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for initial_amount.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of initial_amount.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for initial_amount.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of initial_amount.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for initial_amount.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #currency}
     *
     * @return the value of initial_amount.currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * {@linkplain #currency}
     * @param currency the value for initial_amount.currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of initial_amount.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for initial_amount.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of initial_amount.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for initial_amount.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of initial_amount.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for initial_amount.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of initial_amount.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for initial_amount.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of initial_amount.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for initial_amount.balance_type
     */
    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusStr() {
		if(getPayStatus() == 1){
			return "已付款";
		}else if(getPayStatus() == 0){
			return "未付款";
		}
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}