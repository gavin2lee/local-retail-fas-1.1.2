package cn.wonhigh.retail.fas.common.model;

/**
 * 财务大类明细
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
public class FinancialCategoryDtl extends FasBaseModel {

	private static final long serialVersionUID = 8385389347270129498L;

	/**
     * 财务大类编码
     */
    private String financialCategoryNo;

    /**
     * 大类编码
     */
    private String categoryNo;
    
    /**
     * 大类名称
     */
    private String name;
    
    /**
     * 数量控制标志
     */
    private Integer qtyControlFlag;
    
    public Integer getQtyControlFlag() {
		return qtyControlFlag;
	}

	public void setQtyControlFlag(Integer qtyControlFlag) {
		this.qtyControlFlag = qtyControlFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 
     * {@linkplain #financialCategoryNo}
     *
     * @return the value of financial_category_dtl.financial_category_no
     */
    public String getFinancialCategoryNo() {
        return financialCategoryNo;
    }

    /**
     * 
     * {@linkplain #financialCategoryNo}
     * @param financialCategoryNo the value for financial_category_dtl.financial_category_no
     */
    public void setFinancialCategoryNo(String financialCategoryNo) {
        this.financialCategoryNo = financialCategoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of financial_category_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for financial_category_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
}