package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

/**
 * 客户-收款条款关联关系
 * @author admin
 * @date  2014-11-04 13:40:23
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
public class CustomerReceRel extends FasBaseModel implements Cloneable {

	private static final long serialVersionUID = -7666887875800455632L;

	/**
     * 条款编码
     */
    private String termNo;

    /**
     * 条款名称
     */
    private String termName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 结算主体编码
     */
    private String companyNo;
    
    /**
     * 结算主体名称
     */
    private String companyName;

    /**
     * 启用保证金控制(0 : 未启用， 1 ： 已启用)
     */
    private Integer marginControlFlag;
    
    /**
     * 只在页面显示,不保存进数据库
     */
    private String marginControlFlagText;

    /**
     * 保证金额度
     */
    private BigDecimal marginAmount;

    /**
     * 保证金余额
     */
    private BigDecimal marginRemainderAmount;
    
    public BigDecimal getMarginRemainderAmount() {
		return marginRemainderAmount;
	}

	public void setMarginRemainderAmount(BigDecimal marginRemainderAmount) {
		this.marginRemainderAmount = marginRemainderAmount;
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

	public String getMarginControlFlagText() {
    	for(YesNoEnum s : YesNoEnum.values()) {
			if(getMarginControlFlag() != null && 
					s.getValue() == getMarginControlFlag().intValue()) {
				return s.getText();
			}
		}
		return marginControlFlagText;
	}

	public void setMarginControlFlagText(String marginControlFlagText) {
		this.marginControlFlagText = marginControlFlagText;
	}

	/**
     * 
     * {@linkplain #termNo}
     *
     * @return the value of customer_rece_rel.term_no
     */
    public String getTermNo() {
        return termNo;
    }

    /**
     * 
     * {@linkplain #termNo}
     * @param termNo the value for customer_rece_rel.term_no
     */
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    /**
     * 
     * {@linkplain #termName}
     *
     * @return the value of customer_rece_rel.term_name
     */
    public String getTermName() {
        return termName;
    }

    /**
     * 
     * {@linkplain #termName}
     * @param termName the value for customer_rece_rel.term_name
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of customer_rece_rel.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for customer_rece_rel.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of customer_rece_rel.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for customer_rece_rel.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #marginControlFlag}
     *
     * @return the value of customer_rece_rel.margin_control_flag
     */
    public Integer getMarginControlFlag() {
        return marginControlFlag;
    }

    /**
     * 
     * {@linkplain #marginControlFlag}
     * @param marginControlFlag the value for customer_rece_rel.margin_control_flag
     */
    public void setMarginControlFlag(Integer marginControlFlag) {
        this.marginControlFlag = marginControlFlag;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     *
     * @return the value of customer_rece_rel.margin_amount
     */
    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     * @param marginAmount the value for customer_rece_rel.margin_amount
     */
    public void setMarginAmount(BigDecimal marginAmount) {
        this.marginAmount = marginAmount;
    }
    
    public Object clone() {   
        try {   
            return super.clone();   
        } catch (CloneNotSupportedException e) {   
            return null;   
        }   
    }   
}