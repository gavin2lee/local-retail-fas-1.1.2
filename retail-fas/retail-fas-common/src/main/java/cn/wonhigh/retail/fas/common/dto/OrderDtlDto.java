package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 销售订单明细页面展现的dto
 * 
 * @author yang.y
 */
public class OrderDtlDto extends OrderDtl {

	private static final long serialVersionUID = 4180763066674134091L;

	/** 店铺编码 */
	private String shopNo;

	/** 店铺名称 */
	private String shopName;
	
	/**
     * 销售日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
    private Date outDate;

	/** 营业员工号,与HR工号代码一致 */
	private String assistantNo;

	/** 营业员姓名 */
	private String assistantName;

	//订单现金券
	private BigDecimal couponPrice;

	//	/** 结算额 */
	//	@JsonSerialize(using=BigDecimalSerializer.class)
	//	private BigDecimal settleAmount;
	//	
	//	/** 现价额 */
	//	@JsonSerialize(using=BigDecimalSerializer.class)
	//	private BigDecimal saleAmount;
	//	
	//	/** 牌价额 */
	//	@JsonSerialize(using=BigDecimalSerializer.class)
	//	private BigDecimal tagAmount;

	/** 单据日期 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date billDate;

	/**
	 * 发票日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date invoiceDate;

//	/**
//	 * 发票号
//	 */
//	private String invoiceNo;

	/**
	 * 结算公司名称
	 */
	private String companyName;

	/**
	 * 结算公司编码
	 */
	private String companyNo;
	
	/**
	 * 大类名称
	 */
	private String categoryName;
	
	/**
	 * 大类编号
	 */
	private String categoryNo;
	
	/**
	 * 管理城市名称
	 */
	private String organName;
	
	/**
	 * 管理东城市编号
	 */
	private String organNo;
	
	//建档时间
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date createTime;
	//建档人
	private String createUser;
	
	//确认时间
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date updateTime;
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	//确认人
	private String updateUser;
	
	/**
     * 业务类型编号
     */
    private String bizTypeCode;

    /**
     * 业务类型名称
     */
    private String bizTypeName;
    
    /**
     * 优惠券优惠金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal discAmount;
    
    /**
     * 现金券面额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal ticketAmount;
    
    
    /**
     * 现金券价值
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal ticketPrice;
    
    /**
     * 补差金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;
    
    /**
     * 实际销售金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal sellAmount;
    
    /**
     * 顾客姓名
     */
    private String receivingName;
    
    /**
     * 手机号码
     */
    private String receivingTel;
    
    /**
     * 收货地址
     */
    private String receivingAddress;
    
    /**
     * 优惠券开票申请单号
     */
    private String discApplyNo;
    
    /**
     * 现金券开票申请单号
     */
    private String ticketApplyNo;
    
    /**
     * 总记录数
     */
    private int total ;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 品牌部名称
     */
    private String brandUnitName;
    /**
     * 品牌部编号
     */
    private String brandUnitNo;
    
    
    /**
     * 客户编号
     */
    private String clientNo;
    /**
     * 客户名称
     */
    private String clientName;
    
    
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

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

	public String getAssistantNo() {
		return assistantNo;
	}

	public void setAssistantNo(String assistantNo) {
		this.assistantNo = assistantNo;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	//	public BigDecimal getSettleAmount() {
	//		return settleAmount;
	//	}
	//
	//	public void setSettleAmount(BigDecimal settleAmount) {
	//		this.settleAmount = settleAmount;
	//	}
	//
	//	public BigDecimal getSaleAmount() {
	//		return saleAmount;
	//	}
	//
	//	public void setSaleAmount(BigDecimal saleAmount) {
	//		this.saleAmount = saleAmount;
	//	}
	//
	//	public BigDecimal getTagAmount() {
	//		return tagAmount;
	//	}
	//
	//	public void setTagAmount(BigDecimal tagAmount) {
	//		this.tagAmount = tagAmount;
	//	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

//	public String getInvoiceNo() {
//		return invoiceNo;
//	}
//
//	public void setInvoiceNo(String invoiceNo) {
//		this.invoiceNo = invoiceNo;
//	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
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

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getBizTypeCode() {
		return bizTypeCode;
	}

	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}

	public BigDecimal getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(BigDecimal ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public BigDecimal getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
	}

	public String getReceivingName() {
		return receivingName;
	}

	public void setReceivingName(String receivingName) {
		this.receivingName = receivingName;
	}

	public String getReceivingTel() {
		return receivingTel;
	}

	public void setReceivingTel(String receivingTel) {
		this.receivingTel = receivingTel;
	}

	public String getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

	public String getDiscApplyNo() {
		return discApplyNo;
	}

	public void setDiscApplyNo(String discApplyNo) {
		this.discApplyNo = discApplyNo;
	}

	public String getTicketApplyNo() {
		return ticketApplyNo;
	}

	public void setTicketApplyNo(String ticketApplyNo) {
		this.ticketApplyNo = ticketApplyNo;
	}

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
