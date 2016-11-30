package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

/**
 * 财务大类
 * @author yang.y
 * @date  2014-12-23 10:38:39
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
@ExportFormat(className=AbstractExportFormat.class)
public class FinancialCategory extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 6100909961268845149L;

	/**
     * 财务大类编码
     */
    private String financialCategoryNo;

    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 结算公司编码
     */
    private String companyNo;
    
    /**
     * 结算公司名称
     */
    private String companyName;
    
    /**
     * 是否属于系统默认
     */
    private Integer isDefault;

    /**
     * 备注
     */
    private String remark;

    public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
     * 
     * {@linkplain #financialCategoryNo}
     *
     * @return the value of financial_category.financial_category_no
     */
    public String getFinancialCategoryNo() {
        return financialCategoryNo;
    }

    /**
     * 
     * {@linkplain #financialCategoryNo}
     * @param financialCategoryNo the value for financial_category.financial_category_no
     */
    public void setFinancialCategoryNo(String financialCategoryNo) {
        this.financialCategoryNo = financialCategoryNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of financial_category.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for financial_category.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of financial_category.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for financial_category.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}