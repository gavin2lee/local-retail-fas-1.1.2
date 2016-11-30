/**
 * title:SelfPurchaseDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:地区自购Dto
 * auther:user
 * date:2016-7-14 上午11:03:40
 */
package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;


public class SelfPurchaseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6315171409430796711L;
	/**
	 * 单据编码
	 */
	@ExcelCell("A")
	private String originalBillNo;
	/**
	 * 商品编码
	 */
	@ExcelCell("B")
	private String itemCode;
	/**
	 * 价格
	 */
	@ExcelCell("C")
	private BigDecimal cost;
	
	/**
	 * 关联单号
	 */
	private String refBillNo;
	
	public String getOriginalBillNo() {
		return originalBillNo;
	}
	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getRefBillNo() {
		return refBillNo;
	}
	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}
	
}
