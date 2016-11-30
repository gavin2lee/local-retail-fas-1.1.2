package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 客户保证金及预收款初始化
 * @author admin
 * @date  2014-09-19 15:00:52
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
public class WholesaleMarginInit extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = -8444400061963729793L;

	/**
     * 结算主体编码
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 保证金额度
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal marginAmount;

    /**
     * 初始保证金额度
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal initMarginAmount;

    /**
     * 初始预收款
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal initPrePayment;

    /**
     * 预收订单号
     */
    private String preOrderNo;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 初始化完成标志（0：未完成 1：已完成）
     */
    private Integer finishFlag;
    
    /**
     * 初始化完成标志(页面显示,不存放到数据库)
     */
    private String finishFlagText;

    public String getFinishFlagText() {
    	for(YesNoEnum s : YesNoEnum.values()) {
			if(getFinishFlag() != null && 
					s.getValue() == getFinishFlag().intValue()) {
				return s.getText();
			}
		}
		return finishFlagText;
	}

	public void setFinishFlagText(String finishFlagText) {
		this.finishFlagText = finishFlagText;
	}

	public Integer getFinishFlag() {
		return finishFlag;
	}

	public void setFinishFlag(Integer finishFlag) {
		this.finishFlag = finishFlag;
	}

	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of wholesale_margin_init.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for wholesale_margin_init.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of wholesale_margin_init.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for wholesale_margin_init.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of wholesale_margin_init.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for wholesale_margin_init.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of wholesale_margin_init.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for wholesale_margin_init.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     *
     * @return the value of wholesale_margin_init.margin_amount
     */
    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    /**
     * 
     * {@linkplain #marginAmount}
     * @param marginAmount the value for wholesale_margin_init.margin_amount
     */
    public void setMarginAmount(BigDecimal marginAmount) {
        this.marginAmount = marginAmount;
    }

    /**
     * 
     * {@linkplain #initMarginAmount}
     *
     * @return the value of wholesale_margin_init.init_margin_amount
     */
    public BigDecimal getInitMarginAmount() {
        return initMarginAmount;
    }

    /**
     * 
     * {@linkplain #initMarginAmount}
     * @param initMarginAmount the value for wholesale_margin_init.init_margin_amount
     */
    public void setInitMarginAmount(BigDecimal initMarginAmount) {
        this.initMarginAmount = initMarginAmount;
    }

    /**
     * 
     * {@linkplain #initPrePayment}
     *
     * @return the value of wholesale_margin_init.init_pre_payment
     */
    public BigDecimal getInitPrePayment() {
        return initPrePayment;
    }

    /**
     * 
     * {@linkplain #initPrePayment}
     * @param initPrePayment the value for wholesale_margin_init.init_pre_payment
     */
    public void setInitPrePayment(BigDecimal initPrePayment) {
        this.initPrePayment = initPrePayment;
    }

    /**
     * 
     * {@linkplain #preOrderNo}
     *
     * @return the value of wholesale_margin_init.pre_order_no
     */
    public String getPreOrderNo() {
        return preOrderNo;
    }

    /**
     * 
     * {@linkplain #preOrderNo}
     * @param preOrderNo the value for wholesale_margin_init.pre_order_no
     */
    public void setPreOrderNo(String preOrderNo) {
        this.preOrderNo = preOrderNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of wholesale_margin_init.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for wholesale_margin_init.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}