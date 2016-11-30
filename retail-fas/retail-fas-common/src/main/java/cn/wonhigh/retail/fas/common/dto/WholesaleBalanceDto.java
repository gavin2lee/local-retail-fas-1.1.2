package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 地区批发出库明细汇总的dto
 * 
 * @author yang.y
 */
public class WholesaleBalanceDto implements Serializable {

	private static final long serialVersionUID = -3529526020692298614L;

	private String buyerNo;
	
	private String buyerName;
	
	private String salerNo;
	
	private String salerName;
	
	private String brandUnitNo;
	
	private String brandUnitName;
	
	private Integer sendQty;
	
	private BigDecimal saleOutAmount;
	
	private BigDecimal rebateAmount;

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
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

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getSaleOutAmount() {
		return saleOutAmount;
	}

	public void setSaleOutAmount(BigDecimal saleOutAmount) {
		this.saleOutAmount = saleOutAmount;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	
}
