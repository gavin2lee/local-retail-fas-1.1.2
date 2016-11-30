package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-31 11:51:27
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
public class CostCategorySetting extends FasBaseModel implements SequenceStrId{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     */
    private String costCateNo;

    /**
     * 费用类别编码
     */
    private String code;

    /**
     * 费用类别名称
     */
    private String name;

    /**
     * 会计科目编码
     */
    private String accountsNo;

    /**
     * 会计科目名称
     */
    private String accountsName;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

     /**
     * 备注
     */
    private String remark;

   
    /**
     * 
     * {@linkplain #costCateNo}
     *
     * @return the value of cost_category_setting.cost_cate_no
     */
    public String getCostCateNo() {
        return costCateNo;
    }

    /**
     * 
     * {@linkplain #costCateNo}
     * @param costCateNo the value for cost_category_setting.cost_cate_no
     */
    public void setCostCateNo(String costCateNo) {
        this.costCateNo = costCateNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of cost_category_setting.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for cost_category_setting.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of cost_category_setting.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for cost_category_setting.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #accountsNo}
     *
     * @return the value of cost_category_setting.accounts_no
     */
    public String getAccountsNo() {
        return accountsNo;
    }

    /**
     * 
     * {@linkplain #accountsNo}
     * @param accountsNo the value for cost_category_setting.accounts_no
     */
    public void setAccountsNo(String accountsNo) {
        this.accountsNo = accountsNo;
    }

    /**
     * 
     * {@linkplain #accountsName}
     *
     * @return the value of cost_category_setting.accounts_name
     */
    public String getAccountsName() {
        return accountsName;
    }

    /**
     * 
     * {@linkplain #accountsName}
     * @param accountsName the value for cost_category_setting.accounts_name
     */
    public void setAccountsName(String accountsName) {
        this.accountsName = accountsName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of cost_category_setting.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for cost_category_setting.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of cost_category_setting.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for cost_category_setting.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

  
    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of cost_category_setting.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for cost_category_setting.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}