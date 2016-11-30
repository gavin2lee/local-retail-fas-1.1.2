package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-13 18:03:57
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
public class BalanceDate implements Serializable {
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 结算类型(1-总部厂商结算,2-总部批发结算,3-其他结算)
     */
    private Integer balanceType;

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
     * 结算日
     */
    private String balanceDate;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of balance_date.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for balance_date.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of balance_date.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for balance_date.balance_type
     */
    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of balance_date.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for balance_date.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of balance_date.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for balance_date.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of balance_date.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for balance_date.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of balance_date.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for balance_date.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of balance_date.inside_store_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for balance_date.inside_store_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of balance_date.inside_store_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for balance_date.inside_store_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #balanceDate}
     *
     * @return the value of balance_date.balance_date
     */
    public String getBalanceDate() {
        return balanceDate;
    }

    /**
     * 
     * {@linkplain #balanceDate}
     * @param balanceDate the value for balance_date.balance_date
     */
    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }
}