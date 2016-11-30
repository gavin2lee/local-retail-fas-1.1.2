package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * TODO: 订单及明细数据导出对象
 * 
 * @author xu.j
 * @date 2014-12-3 下午4:25:55
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@ExportFormat(className=AbstractExportFormat.class)
public class OrderMainExport implements Serializable {
	private static final long serialVersionUID = 1652783339564301421L;
	//订单编号 
	private String billNo;
	
	//订单类型
	private String billTypeName;

	//店铺编号 
	private String shopNo;

	//店铺名称 
	private String shopName;

	private String companyName;

	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;

	private Integer totalQty;

	private BigDecimal tagPriceAmount;

	private BigDecimal salePriceAmount;

	private BigDecimal reducePriceAmount;

	private BigDecimal settleAmount;

	private BigDecimal allAmount;

	private BigDecimal couponAmount;

	private BigDecimal cashAmount;

	private int financeConfirmFlag;

	private String invoiceNo;

	private BigDecimal invoiceAmount;

	private String itemCode;

	private String itemName;

	private String assistantName;

	private int qty;

	private BigDecimal tagPrice;

	private BigDecimal salePrice;

	private BigDecimal settlePrice;

	private BigDecimal reducePrice;

	private BigDecimal amount;

	//发票号
	private String invoiceApplyNo;
	
	//团购券金额
	private BigDecimal ticketPriceAmount;
	
	//团购券发票号
	private String ticketInvoiceNo;
	
	//团购券发票日期
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date ticketInvoiceDate;
	
	//其他收款方式金额
	private BigDecimal otherAmount;
	
	//其他收款方式发票日期
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date invoiceDate;
	
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
	 * 
	 */
	private String financeConfirmFlagStr;
	
	//建档时间
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date createTime;
	//建档人
	private String createUser;
	
	/**
	 * 明细的现金
	 */
	private BigDecimal prefPrice;
	
	/**
	 * 明细的现金券
	 */
	private BigDecimal couponPrice;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 单据类型名称
	 */
	private String bizTypeName;
	
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
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

	public BigDecimal getReducePriceAmount() {
		return reducePriceAmount;
	}

	public void setReducePriceAmount(BigDecimal reducePriceAmount) {
		this.reducePriceAmount = reducePriceAmount;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public int getFinanceConfirmFlag() {
		return financeConfirmFlag;
	}

	public void setFinanceConfirmFlag(int financeConfirmFlag) {
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public BigDecimal getReducePrice() {
		return reducePrice;
	}

	public void setReducePrice(BigDecimal reducePrice) {
		this.reducePrice = reducePrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getInvoiceApplyNo() {
		return invoiceApplyNo;
	}

	public void setInvoiceApplyNo(String invoiceApplyNo) {
		this.invoiceApplyNo = invoiceApplyNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public BigDecimal getTicketPriceAmount() {
		return ticketPriceAmount;
	}

	public void setTicketPriceAmount(BigDecimal ticketPriceAmount) {
		this.ticketPriceAmount = ticketPriceAmount;
	}

	public String getTicketInvoiceNo() {
		return ticketInvoiceNo;
	}

	public void setTicketInvoiceNo(String ticketInvoiceNo) {
		this.ticketInvoiceNo = ticketInvoiceNo;
	}

	public Date getTicketInvoiceDate() {
		return ticketInvoiceDate;
	}

	public void setTicketInvoiceDate(Date ticketInvoiceDate) {
		this.ticketInvoiceDate = ticketInvoiceDate;
	}

	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public String getFinanceConfirmFlagStr() {
		return financeConfirmFlagStr;
	}

	public void setFinanceConfirmFlagStr(String financeConfirmFlagStr) {
		this.financeConfirmFlagStr = financeConfirmFlagStr;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getPrefPrice() {
		return prefPrice;
	}

	public void setPrefPrice(BigDecimal prefPrice) {
		this.prefPrice = prefPrice;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

}
