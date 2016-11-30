package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillSaleOutDtl;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 批发销售订单表页面展示的dto
 * 
 * @author yang.y
 */
@ExportFormat(className=AbstractExportFormat.class)
public class BillSaleOutDtlDto extends BillSaleOutDtl {

	private static final long serialVersionUID = -7265840288904475128L;
	
	/** 业务类型 */
	private Integer bizType;
	
	/** 结算主体 */
	private String companyName;
	
	/** 客户名称 */
	private String customerName;
	
	/** 品牌名称 */
	private String brandName;
	
	/** 批发订单号 */
	private String refBillNo;
	
	/** 商品名称 */
	private String itemName;
	
	/** 含税总金额 */
	private BigDecimal costAmount;
	
	/** 牌价额 */
	private BigDecimal quoteAmount;
	
	/** 单据日期 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date billDate;
	
	/** 单据状态 */
	private Integer billStatus;

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(BigDecimal quoteAmount) {
		this.quoteAmount = quoteAmount;
	}
}
