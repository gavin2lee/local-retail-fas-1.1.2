package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.FinanceConfirmFlagEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 员购销售汇总
 * 
 * @author admin
 */
@ExportFormat(className=AbstractExportFormat.class)
public class MemberOrderSummary extends FasBaseModel {

	private static final long serialVersionUID = 620437085753654859L;

	private String shopNo;

	private String shopName;
	private String buyerNo;
	
	/**
	 * 
	 */
	private String companyNo;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/** 总数量 */
	private Integer totalQty;

	/** 牌价额 */
	private BigDecimal tagPriceAmount;

	/** 现价额 */
	private BigDecimal salePriceAmount;

	/** 减价额 */
	private BigDecimal reducePriceAmount;

	/** 结算金额 */
	private BigDecimal settleAmount;

	/** 现金券 */
	private BigDecimal couponAmount;

	/** 应收总金额
	 * 
	 * 结算金额与减价额之差
	 */
	private BigDecimal allAmount;

	/** 现金 */
	private BigDecimal cashAmount;

	/** 开始时间 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date startDate;

	/** 结算时间 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date endDate;

	/** 财务确认标志(1 : 已确认 0 ： 未确认) */
	private Integer financeConfirmFlag;

	/** 发票号（可填多个） */
	private String invoiceNo;

	/** 发票金额 */
	private BigDecimal invoiceAmount;

	/**
	 * 结算公司名称
	 */
	private String companyName;

	/** 销售日期 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;

	/**
	 * 营业员编号 
	 */
	private String assistantNo;

	/**
	 * 发票日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date invoiceDate;

	/**
	 * 团购券发票日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date ticketInvoiceDate;

	/**
	 * 团购券发票号
	 */
	private String ticketInvoiceNo;
	
	/**
	 * 团购券发票金额
	 */
	private BigDecimal ticketInvoicePriceAmount;

	/**
	 * 团购券金额
	 */
	private BigDecimal ticketPriceAmount;

	/**
	 * 其他收款方式金额
	 */
	private BigDecimal otherAmount;

	/**
	 * 单据类型
	 */
	private String billTypeName;
	/**
	 * 订单类型 0-正常 1-换货
	 */
	private Integer orderType ;
	
	/**
	 * id
	 */
	private Integer orderId;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 大类名称
	 */
	private String categoryName;
	
	/**
	 * 管理城市名称
	 */
	private String organName;
	
	/**
	 * 单据类型
	 */
	private Integer businessType;
	
	/**
	 * 确认标志
	 */
	private String financeConfirmFlagStr;
	
	//建档时间
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date createTime;
	//建档人
	private String createUser;
	/**
	 * 此标识用于区分是POS 内购销售单还是GMS 内购、团购单：1＝GMS，2＝POS；在查询明细时使用
	 */
	private String typeFlag;

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
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

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getFinanceConfirmFlag() {
		return financeConfirmFlag;
	}

	public void setFinanceConfirmFlag(Integer financeConfirmFlag) {
		this.financeConfirmFlagStr = FinanceConfirmFlagEnums.getTextByStatus(financeConfirmFlag);
		this.financeConfirmFlag = financeConfirmFlag;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getAssistantNo() {
		return assistantNo;
	}

	public void setAssistantNo(String assistantNo) {
		this.assistantNo = assistantNo;
	}

	public BigDecimal getReducePriceAmount() {
		return reducePriceAmount;
	}

	public void setReducePriceAmount(BigDecimal reducePriceAmount) {
		this.reducePriceAmount = reducePriceAmount;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getTicketPriceAmount() {
		return ticketPriceAmount;
	}

	public void setTicketPriceAmount(BigDecimal ticketPriceAmount) {
		this.ticketPriceAmount = ticketPriceAmount;
	}

	public Date getTicketInvoiceDate() {
		return ticketInvoiceDate;
	}

	public void setTicketInvoiceDate(Date ticketInvoiceDate) {
		this.ticketInvoiceDate = ticketInvoiceDate;
	}

	public String getTicketInvoiceNo() {
		return ticketInvoiceNo;
	}

	public void setTicketInvoiceNo(String ticketInvoiceNo) {
		this.ticketInvoiceNo = ticketInvoiceNo;
	}

	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
	
	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTicketInvoicePriceAmount() {
		return ticketInvoicePriceAmount;
	}

	public void setTicketInvoicePriceAmount(BigDecimal ticketInvoicePriceAmount) {
		this.ticketInvoicePriceAmount = ticketInvoicePriceAmount;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getFinanceConfirmFlagStr() {
		return financeConfirmFlagStr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}
