package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;


/**
 * 地区批发收款条款-表頭
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
public class WholesaleReceTerm extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = -8482512272018530412L;

	/**
     * 条款编码
     */
    private String termNo;

    /**
     * 条款名称
     */
    private String name;

    /**
     * 结算主体编码
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;
    
    /**
     * 控制类型  0：批发订单   1：客户余额
     */
    private Integer controlType;
    
    private String controlTypeName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #termNo}
     *
     * @return the value of wholesale_rece_terms.term_no
     */
    public String getTermNo() {
        return termNo;
    }

    /**
     * 
     * {@linkplain #termNo}
     * @param termNo the value for wholesale_rece_terms.term_no
     */
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of wholesale_rece_terms.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for wholesale_rece_terms.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of wholesale_rece_terms.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for wholesale_rece_terms.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of wholesale_rece_terms.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for wholesale_rece_terms.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public Integer getControlType() {
		return controlType;
	}

	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}

	public String getControlTypeName() {
		if(this.controlType == 0) {
			this.controlTypeName = "批发订单";
		}else {
			this.controlTypeName = "客户余额";
		}
		return controlTypeName;
	}

	public void setControlTypeName(String controlTypeName) {
		this.controlTypeName = controlTypeName;
	}

	/**
     * 
     * {@linkplain #remark}
     *
     * @return the value of wholesale_rece_terms.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for wholesale_rece_terms.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}