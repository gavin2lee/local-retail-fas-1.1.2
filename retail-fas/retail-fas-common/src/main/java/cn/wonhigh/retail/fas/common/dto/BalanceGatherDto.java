/**  
* 项目名称：retail-fas-common  
* 类名称：BalanceDto  
* 类描述：用于查询显示结算相关信息的DTO
* 创建人：wang.m 
* 创建时间：2014-10-17 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

public class BalanceGatherDto{
	
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
     * 品牌编号
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 发出数量
     */
    private Integer outQty;

    /**
     * 接收数量
     */
    private Integer entryQty;

    /**
     * 退残数量 
     */
    private Integer returnQty;

    /**
     * 扣项数量 
     */
    private Integer deductionQty;

    /**
     * 结算数量 
     */
    private Integer balanceQty;
    
    /**
     * 发出金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal outAmount;

    /**
     * 接收金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal entryAmount;

    /**
     * 退残金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal returnAmount;

    /**
     * 其他扣项金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductionAmount;

    /**
     * 结算金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceAmount;

    /**
     * 出库数量(send_qty + return_qty)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal outBalanceQty;
    
    /**
     * 入库数量(receive_qty + return_qty)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal entryBalanceQty;
    
    /**
     * 出库金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal outBalanceAmount;
    
    /**
     * 入库金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal entryBalanceAmount;
    
	public BigDecimal getOutBalanceQty() {
		return outBalanceQty;
	}

	public void setOutBalanceQty(BigDecimal outBalanceQty) {
		this.outBalanceQty = outBalanceQty;
	}

	public BigDecimal getEntryBalanceQty() {
		return entryBalanceQty;
	}

	public void setEntryBalanceQty(BigDecimal entryBalanceQty) {
		this.entryBalanceQty = entryBalanceQty;
	}

	public BigDecimal getOutBalanceAmount() {
		return outBalanceAmount;
	}

	public void setOutBalanceAmount(BigDecimal outBalanceAmount) {
		this.outBalanceAmount = outBalanceAmount;
	}

	public BigDecimal getEntryBalanceAmount() {
		return entryBalanceAmount;
	}

	public void setEntryBalanceAmount(BigDecimal entryBalanceAmount) {
		this.entryBalanceAmount = entryBalanceAmount;
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

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getOutQty() {
		return outQty;
	}

	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	public Integer getEntryQty() {
		return entryQty;
	}

	public void setEntryQty(Integer entryQty) {
		this.entryQty = entryQty;
	}

	public Integer getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	public Integer getDeductionQty() {
		return deductionQty;
	}

	public void setDeductionQty(Integer deductionQty) {
		this.deductionQty = deductionQty;
	}

	public Integer getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public BigDecimal getEntryAmount() {
		return entryAmount;
	}

	public void setEntryAmount(BigDecimal entryAmount) {
		this.entryAmount = entryAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
    
}
