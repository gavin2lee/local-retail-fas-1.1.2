package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-29 14:26:35
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
public class CustomerReceRelDtl extends FasBaseModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主表关联
     */
    private String refId;

    /**
     * 年份
     */
    private String year;

    /**
     * 返利额度
     */
    private BigDecimal rebateAmount;

    /**
     * 返利额度
     */
    private BigDecimal hasRebateAmount;
    
    /**
     * 备注
     */
    private String remark;

    public BigDecimal getHasRebateAmount() {
		return hasRebateAmount;
	}

	public void setHasRebateAmount(BigDecimal hasRebateAmount) {
		this.hasRebateAmount = hasRebateAmount;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
     * 
     * {@linkplain #year}
     *
     * @return the value of customer_rece_rel_dtl.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for customer_rece_rel_dtl.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     *
     * @return the value of customer_rece_rel_dtl.rebate_amount
     */
    public BigDecimal getRebateAmount() {
        return rebateAmount;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     * @param rebateAmount the value for customer_rece_rel_dtl.rebate_amount
     */
    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of customer_rece_rel_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for customer_rece_rel_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}