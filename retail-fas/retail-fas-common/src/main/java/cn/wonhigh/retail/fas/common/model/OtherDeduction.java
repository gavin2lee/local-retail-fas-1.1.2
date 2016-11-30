package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.OtherDeductionTypeEnum;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 其他扣项 
 * @author wang.m1
 * @date  2014-11-24 12:07:35
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
public class OtherDeduction extends FasBaseModel implements SequenceStrId {
    /**
	 * 序列号ID
	 */
	private static final long serialVersionUID = 7115057557670329991L;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 出库单号
     */
    private String billNo;
    
    /**
     * 更新余额状态
     */
    private Integer status;
    /**
     * 更新余额状态
     */
    private String statusName;
    
    /**
     * 扣项类型
     */
    private Integer type;
    
    /**
     * 扣项类型
     */
    private String typeName;

    /**
     * 买方编号
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 卖方编号
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 结算单类型
     */
    private Integer balanceType;

    /**
     * 结算单状态
     */
    private Integer balanceStatus;
    
    /**
     * 结算单状态
     */
    private Integer bizType;
    /**
     * 返利
     */
	private BigDecimal rebateAmount;
	/**
	 * 其他费用
	 */
	private BigDecimal otherPrice;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 产品大类编号
     */
    private String categoryNo;

    /**
     * 产品大类名称
     */
    private String categoryName;

    /**
     * 币别
     */
    private String currencyNo;

    /**
     * 币别名称
     */
    private String currencyName;
    
    /**
     * 本位币
     */
    private String targetCurrencyNo;
    
    /**
     * 本位币名称
     */
    private String targetCurrencyName;
    
    /**
     * 本位币金额
     */
    private BigDecimal targetCurrencyAmount;
    
    /**
     * 汇率
     */
    private BigDecimal conversionFactor;

    /**
     * 扣项分类
     */
    private String deductionCategory;

    /**
     * 扣项数量
     */
    private int deductionQty;
    
    /**
     * 扣项日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date deductionDate;


    /**
     * 罚款金额
     */
    private BigDecimal fineAmount;
    
    /**
     * 残鞋金额
     */
    private BigDecimal returnAmount;
    
    /**
     * 扣项金额
     */
    private BigDecimal deductionAmount;


    /**
     * 备注
     */
    private String remark;
    
    /**
     * 地区公司 （体育）
     */
    private String areaBuyerNo;
    private String areaBuyerName;
    
    /**
     * 到期日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date dueDate;
    
    private BigDecimal noPayAmount;
    
    private BigDecimal payAmount;
    
    private Integer payStatus;
    
    private String deductionName;

    public BigDecimal getFineAmount() {
    	if(fineAmount == null){
    		return new BigDecimal(0);
    	}
		return fineAmount;
	}

	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}

	public BigDecimal getReturnAmount() {
    	if(returnAmount == null){
    		return new BigDecimal(0);
    	}
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getDeductionCategory() {
		return deductionCategory;
	}

	public void setDeductionCategory(String deductionCategory) {
		this.deductionCategory = deductionCategory;
	}

	public int getDeductionQty() {
		return deductionQty;
	}

	public void setDeductionQty(int deductionQty) {
		this.deductionQty = deductionQty;
	}

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of other_deduction.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for other_deduction.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of other_deduction.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for other_deduction.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of other_deduction.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for other_deduction.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of other_deduction.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for other_deduction.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of other_deduction.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for other_deduction.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of other_deduction.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for other_deduction.balance_type
     */
    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     *
     * @return the value of other_deduction.balance_status
     */
    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     * @param balanceStatus the value for other_deduction.balance_status
     */
    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of other_deduction.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for other_deduction.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of other_deduction.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for other_deduction.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of other_deduction.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for other_deduction.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of other_deduction.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for other_deduction.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #currencyNo}
     *
     * @return the value of other_deduction.currency_no
     */
    public String getCurrencyNo() {
        return currencyNo;
    }

    /**
     * 
     * {@linkplain #currencyNo}
     * @param currencyNo the value for other_deduction.currency_no
     */
    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo;
    }

    /**
     * 
     * {@linkplain #currencyName}
     *
     * @return the value of other_deduction.currency_name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 
     * {@linkplain #currencyName}
     * @param currencyName the value for other_deduction.currency_name
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 
     * {@linkplain #deductionDate}
     *
     * @return the value of other_deduction.deduction_date
     */
    public Date getDeductionDate() {
        return deductionDate;
    }

    /**
     * 
     * {@linkplain #deductionDate}
     * @param deductionDate the value for other_deduction.deduction_date
     */
    public void setDeductionDate(Date deductionDate) {
        this.deductionDate = deductionDate;
    }

    /**
     * 
     * {@linkplain #deductionAmount}
     *
     * @return the value of other_deduction.deduction_amount
     */
    public BigDecimal getDeductionAmount() {
    	if(deductionAmount == null){
    		return new BigDecimal(0);
    	}
        return deductionAmount;
    }

    /**
     * 
     * {@linkplain #deductionAmount}
     * @param deductionAmount the value for other_deduction.deduction_amount
     */
    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of other_deduction.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for other_deduction.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getAreaBuyerNo() {
		return areaBuyerNo;
	}

	public void setAreaBuyerNo(String areaBuyerNo) {
		this.areaBuyerNo = areaBuyerNo;
	}

	public String getAreaBuyerName() {
		return areaBuyerName;
	}

	public void setAreaBuyerName(String areaBuyerName) {
		this.areaBuyerName = areaBuyerName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(BigDecimal noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getDeductionName() {
		return deductionName;
	}

	public void setDeductionName(String deductionName) {
		this.deductionName = deductionName;
	}

	public String getTargetCurrencyNo() {
		return targetCurrencyNo;
	}

	public void setTargetCurrencyNo(String targetCurrencyNo) {
		this.targetCurrencyNo = targetCurrencyNo;
	}

	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public BigDecimal getTargetCurrencyAmount() {
		return targetCurrencyAmount;
	}

	public void setTargetCurrencyAmount(BigDecimal targetCurrencyAmount) {
		this.targetCurrencyAmount = targetCurrencyAmount;
	}

	public String getTargetCurrencyName() {
		return targetCurrencyName;
	}

	public void setTargetCurrencyName(String targetCurrencyName) {
		this.targetCurrencyName = targetCurrencyName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		setStatusName(YesNoEnum.getTextByValue(status));
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
		setTypeName(OtherDeductionTypeEnum.getTextByValue(type));
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}