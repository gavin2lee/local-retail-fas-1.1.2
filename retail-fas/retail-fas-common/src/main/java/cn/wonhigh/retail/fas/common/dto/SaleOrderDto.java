package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 批发销售订单
 * 
 * @author yang.y
 */
public class SaleOrderDto {

	/** 单据编码 */
	private String billNo;
	
	/** 相关单号 */
	private String refBillNo;
	
	/** 单据日期 */
	@JsonSerialize(using = JsonDateSerializer$10.class)  
	private Date billDate;
	
	/** 单据数量 */
	private Integer sendQty;
	
	/** 单据金额 */
	private BigDecimal amount;

	/**
     * 结算买方编号
     */
    private String buyerNo;

    /**
     * 结算买方名称
     */
    private String buyerName;

    /**
     * 结算卖方编号
     */
    private String salerNo;

    /**
     * 结算卖方名称
     */
    private String salerName;
    
    /**
     * 收款条款编码
     */
    private String termNo;
    
    /**
     * 收款条款
     */
    private String termName;
    
    /**
     * 返利额度
     */
    private BigDecimal rebateAmount;
    
    /**
     * 实际收款
     */
    private BigDecimal paidAmount;
    
    /**
     * 订货预收款
     */
    private BigDecimal preOrderAmount;
    
    /**
     * 发货预收款
     */
    private BigDecimal preSendAmount;
    
    /**
     * 启用保证金控制(0 : 未启用， 1 ： 已启用)
     */
    private Integer marginControlFlag;
    
    /**
     * 保证金额度
     */
    private BigDecimal marginAmount;

    /**
     * 保证金余额
     */
    private BigDecimal marginRemainderAmount;
    
    /**
     * 只在页面显示,不保存进数据库
     */
    private String marginControlFlagText;
    
    /**
     * 保证金是否足额
     */
    private Integer isMarginFull;
    
    /**
     * 订货预收款是否足额
     */
    private Integer isOrderFull;
    
    /**
     * 发货预收款是否足额
     */
    private Integer isSendFull;
    
    /**
     * 保证金是否足额
     */
    private String isMarginFullText;
    
    /**
     * 订货预收款是否足额
     */
    private String isOrderFullText;
    
    /**
     * 发货预收款是否足额
     */
    private String isSendFullText;
    
    /**
     * 发货金额
     */
    private BigDecimal sendAmount;
    
    /**
     * 预收款余额
     */
    private BigDecimal remainderAmount;
    
    /**
     * 发货控制点 0 -按比例 1 -无固定比例
     */
    private Integer preSendControl;
    
    /**
     * 订货控制点 0 -按比例 1 -无固定比例
     */
    private Integer preOrderControl;
    
    /**
     * 客户余额
     */
    private BigDecimal remainingAmount;
    
    /**订金比例        */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal  advanceScale;
    /**批发退货金额	   */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal  periodWholesaleReturn;
    /**批发退货出库金额*/ 
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal  periodOutWholesaleReturn;
    /**扣项金额	       */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal  deductionAmount;
    /**客户订单余额	   */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal  billRemainingAmount;
    /**信贷额度        */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class) 
    private BigDecimal  creditAmount;
    
    
    
	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public Integer getPreOrderControl() {
		return preOrderControl;
	}

	public void setPreOrderControl(Integer preOrderControl) {
		this.preOrderControl = preOrderControl;
	}

	public Integer getPreSendControl() {
		return preSendControl;
	}

	public void setPreSendControl(Integer preSendControl) {
		this.preSendControl = preSendControl;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public BigDecimal getRemainderAmount() {
		if(remainderAmount == null){
			return new BigDecimal(0);
		}
		return remainderAmount;
	}

	public void setRemainderAmount(BigDecimal remainderAmount) {
		this.remainderAmount = remainderAmount;
	}

	public Integer getIsMarginFull() {
		return isMarginFull;
	}

	public void setIsMarginFull(Integer isMarginFull) {
		this.isMarginFull = isMarginFull;
	}

	public Integer getIsOrderFull() {
		return isOrderFull;
	}

	public void setIsOrderFull(Integer isOrderFull) {
		this.isOrderFull = isOrderFull;
	}

	public Integer getIsSendFull() {
		return isSendFull;
	}

	public void setIsSendFull(Integer isSendFull) {
		this.isSendFull = isSendFull;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getIsMarginFullText() {
		return isMarginFullText;
	}

	public void setIsMarginFullText(String isMarginFullText) {
		if(null == marginControlFlag || marginControlFlag.intValue() == YesNoEnum.NO.getValue()){// 不启用保证金控制
			this.isMarginFullText = YesNoEnum.YES.getText();
		}else{// 启用保证金控制
			if(marginAmount != null){
				if(marginRemainderAmount != null && marginAmount.subtract(marginRemainderAmount).doubleValue() <= 0){
					this.isMarginFullText = YesNoEnum.YES.getText();
					setIsMarginFull(YesNoEnum.YES.getValue());
				}else{
					this.isMarginFullText = YesNoEnum.NO.getText();
					setIsMarginFull(YesNoEnum.NO.getValue());
				}
	
			}
		}
	}

	public String getIsOrderFullText() {
		return isOrderFullText;
	}

	public void setIsOrderFullText(String isOrderFullText) {
		if(preOrderAmount != null){
			if(this.getRemainderAmount().subtract(preOrderAmount).doubleValue() >= 0){
				setIsOrderFull(YesNoEnum.YES.getValue());
				this.isOrderFullText = YesNoEnum.YES.getText();
			}else{
				setIsOrderFull(YesNoEnum.NO.getValue());
				this.isOrderFullText = YesNoEnum.NO.getText();
			}
		}else{
			if(this.getRemainderAmount().subtract(amount).doubleValue() >= 0){
				setIsOrderFull(YesNoEnum.YES.getValue());
				this.isOrderFullText = YesNoEnum.YES.getText();
			}
		}
	}

	public String getIsSendFullText() {
		return isSendFullText;
	}

	public void setIsSendFullText(String isSendFullText) {
		if(preSendAmount != null){
			if(this.getRemainderAmount().subtract(preSendAmount).doubleValue() >= 0){
				setIsSendFull(YesNoEnum.YES.getValue());
				this.isSendFullText = YesNoEnum.YES.getText();
			}else{
				setIsSendFull(YesNoEnum.NO.getValue());
				this.isSendFullText = YesNoEnum.NO.getText();
			}
		}else{
			if(this.getRemainderAmount().subtract(amount).doubleValue() >= 0){
				setIsSendFull(YesNoEnum.YES.getValue());
				this.isSendFullText = YesNoEnum.YES.getText();
			}
		}
	}

	public String getMarginControlFlagText() {
		return marginControlFlagText;
	}

	public void setMarginControlFlagText(String marginControlFlagText) {
		this.marginControlFlagText = marginControlFlagText;
	}

	public Integer getMarginControlFlag() {
		return marginControlFlag;
	}

	public void setMarginControlFlag(Integer marginControlFlag) {
		this.marginControlFlag = marginControlFlag;
		if(marginControlFlag == null || marginControlFlag.intValue() == YesNoEnum.NO.getValue()){
			setMarginControlFlagText(YesNoEnum.NO.getText());
		}else{
			setMarginControlFlagText(YesNoEnum.YES.getText());
		}
	}

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
		setIsMarginFullText("");
	}

	public BigDecimal getMarginRemainderAmount() {
		return marginRemainderAmount;
	}

	public void setMarginRemainderAmount(BigDecimal marginRemainderAmount) {
		this.marginRemainderAmount = marginRemainderAmount;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSalerNo() {
		return salerNo;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getPreOrderAmount() {
		return preOrderAmount;
	}

	public void setPreOrderAmount(BigDecimal preOrderAmount) {
		this.preOrderAmount = preOrderAmount;
		setIsOrderFullText("");
	}

	public BigDecimal getPreSendAmount() {
		return preSendAmount;
	}

	public void setPreSendAmount(BigDecimal preSendAmount) {
		this.preSendAmount = preSendAmount;
		setIsSendFullText("");
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public BigDecimal getAdvanceScale() {
		return advanceScale;
	}

	public void setAdvanceScale(BigDecimal advanceScale) {
		this.advanceScale = advanceScale;
	}

	public BigDecimal getPeriodWholesaleReturn() {
		return periodWholesaleReturn;
	}

	public void setPeriodWholesaleReturn(BigDecimal periodWholesaleReturn) {
		this.periodWholesaleReturn = periodWholesaleReturn;
	}

	public BigDecimal getPeriodOutWholesaleReturn() {
		return periodOutWholesaleReturn;
	}

	public void setPeriodOutWholesaleReturn(BigDecimal periodOutWholesaleReturn) {
		this.periodOutWholesaleReturn = periodOutWholesaleReturn;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public BigDecimal getBillRemainingAmount() {
		return billRemainingAmount;
	}

	public void setBillRemainingAmount(BigDecimal billRemainingAmount) {
		this.billRemainingAmount = billRemainingAmount;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	
}
