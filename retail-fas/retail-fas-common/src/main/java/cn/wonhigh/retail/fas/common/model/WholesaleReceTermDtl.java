package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

/**
 * 地区批发收款条款-表体
 * @author yang.y
 * @date  2014-09-17 18:00:36
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
@ExportFormat(className=AbstractExportFormat.class)
public class WholesaleReceTermDtl extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 8234725950743345241L;

	/**
     * 条款编码
     */
    private String termNo;

    /**
     * 预收类型
     */
    private Integer advanceType;
    
    /**
     * 预收比例
     */
    private BigDecimal advanceScale;

    /**
     * 控制点(0-订货 1-发货)
     */
    private Integer controlPoint;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #termNo}
     *
     * @return the value of wholesale_rece_terms_dtl.term_no
     */
    public String getTermNo() {
        return termNo;
    }

    /**
     * 
     * {@linkplain #termNo}
     * @param termNo the value for wholesale_rece_terms_dtl.term_no
     */
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    public Integer getAdvanceType() {
		return advanceType;
	}

	public void setAdvanceType(Integer advanceType) {
		this.advanceType = advanceType;
	}

	/**
     * 
     * {@linkplain #advanceScale}
     *
     * @return the value of wholesale_rece_terms_dtl.advance_scale
     */
    public BigDecimal getAdvanceScale() {
        return advanceScale;
    }

    /**
     * 
     * {@linkplain #advanceScale}
     * @param advanceScale the value for wholesale_rece_terms_dtl.advance_scale
     */
    public void setAdvanceScale(BigDecimal advanceScale) {
        this.advanceScale = advanceScale;
    }

    /**
     * 
     * {@linkplain #controlPoint}
     *
     * @return the value of wholesale_rece_terms_dtl.control_point
     */
    public Integer getControlPoint() {
        return controlPoint;
    }

    /**
     * 
     * {@linkplain #controlPoint}
     * @param controlPoint the value for wholesale_rece_terms_dtl.control_point
     */
    public void setControlPoint(Integer controlPoint) {
        this.controlPoint = controlPoint;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of wholesale_rece_terms_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for wholesale_rece_terms_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}