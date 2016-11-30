package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * TODO: 员购销售明细
 * 
 * @author xu.j
 * @date 2014-11-11 下午5:47:34
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class MemberOrderDetail extends OrderDtlDto implements Serializable {
	private static final long serialVersionUID = 620437085753654859L;
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;
	private int businessType;
	private String assistantNo;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal tagPriceAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal salePriceAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal discPriceAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal settleAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal reduceAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal prefAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal couponAmount;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal allAmount;

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getAssistantNo() {
		return assistantNo;
	}

	public void setAssistantNo(String assistantNo) {
		this.assistantNo = assistantNo;
	}

	public BigDecimal getTagPriceAmount() {
		return tagPriceAmount;
	}

	public void setTagPriceAmount(BigDecimal tagPriceAmount) {
		this.tagPriceAmount = tagPriceAmount;
	}

	public BigDecimal getSalePriceAmount() {
		return salePriceAmount;
	}

	public void setSalePriceAmount(BigDecimal salePriceAmount) {
		this.salePriceAmount = salePriceAmount;
	}

	public BigDecimal getDiscPriceAmount() {
		return discPriceAmount;
	}

	public void setDiscPriceAmount(BigDecimal discPriceAmount) {
		this.discPriceAmount = discPriceAmount;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getReduceAmount() {
		return reduceAmount;
	}

	public void setReduceAmount(BigDecimal reduceAmount) {
		this.reduceAmount = reduceAmount;
	}

	public BigDecimal getPrefAmount() {
		return prefAmount;
	}

	public void setPrefAmount(BigDecimal prefAmount) {
		this.prefAmount = prefAmount;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}
}
