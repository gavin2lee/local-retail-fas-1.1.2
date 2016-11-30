package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;

/**
 * 结算路径明细
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public class SettlePathDtlModel implements Serializable {

	private static final long serialVersionUID = 997135491927431165L;

	/**
     * 结算路径编码
     */
    private String pathNo;

    /**
     * 结算主体编码
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

    /**
     * 结算依据
     */
    private String financialBasis;

    /**
     * 结算依据文本
     */
    private String financialBasisText;

    /**
     * 结算次序
     */
    private Integer pathOrder;

    public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
	}

	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of settle_path_dtl.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for settle_path_dtl.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of settle_path_dtl.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for settle_path_dtl.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #financialBasis}
     *
     * @return the value of settle_path_dtl.financial_basis
     */
    public String getFinancialBasis() {
        return financialBasis;
    }

    /**
     * 
     * {@linkplain #financialBasis}
     * @param financialBasis the value for settle_path_dtl.financial_basis
     */
    public void setFinancialBasis(String financialBasis) {
        this.financialBasis = financialBasis;
    }

    /**
     * 
     * {@linkplain #financialBasisText}
     *
     * @return the value of settle_path_dtl.financial_basis_text
     */
    public String getFinancialBasisText() {
        return financialBasisText;
    }

    /**
     * 
     * {@linkplain #financialBasisText}
     * @param financialBasisText the value for settle_path_dtl.financial_basis_text
     */
    public void setFinancialBasisText(String financialBasisText) {
        this.financialBasisText = financialBasisText;
    }

    /**
     * 
     * {@linkplain #pathOrder}
     *
     * @return the value of settle_path_dtl.path_order
     */
    public Integer getPathOrder() {
        return pathOrder;
    }

    /**
     * 
     * {@linkplain #pathOrder}
     * @param pathOrder the value for settle_path_dtl.path_order
     */
    public void setPathOrder(Integer pathOrder) {
        this.pathOrder = pathOrder;
    }
}