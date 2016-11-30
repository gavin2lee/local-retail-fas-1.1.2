package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillCommonInvoiceRegisterEnums;
import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 发票登记
 * @author chen.mj
 * @date 2014-11-10 14:40:44
 * @version 1.0.0
 */

public class BillCommonInvoiceRegister extends FasBaseModel implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
	/**
	 * 开票申请单据号或销售单号
	 */
	private String sourceNo;

	// /**
	// * 发票单号
	// */
	// private String billNo;

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	/**
	 * 公司编码(开票方)-卖方
	 */
	private String salerNo;

	/**
	 * 卖方名称
	 */
	private String salerName;
	private String dtlId;
	private String invoiceCode;
	private String invoiceNo;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date invoiceDate;
	private String express;//快递公司
	private String deliveryNo;//快递单号
	
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date deliveryDate;//快递日期
	private String brandName;
	private String invoiceRegisterNo;
	private Integer qty;//商品数量

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
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

	private String buyerNo;
	private String buyerName;

	public Integer getStatus() {
		return status;
	}

	private Integer status;
	
	/**
	 * 确认人
	 */
	private String confirmUser;
	
	/**
	 * 确认时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date confirmTime;

	/**
	 * 单据日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	// @JsonSerialize(using = JsonDateSerializer$19.class)
	// @JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date billDate;

	/**
	 * 开票总金额
	 */
	private BigDecimal amount;

	// /**
	// * 审核人
	// */
	// private String auditor;
	//
	// /**
	// * 审核时间
	// */
	// @JsonSerialize(using = JsonDateSerializer$19.class)
	// @JsonDeserialize(using = JsonDateDeserialize$19.class)
	// private Date auditTime;

	// /**
	// * 建档人
	// */
	// private String createUser;
	//
	// /**
	// * 建档时间
	// */
	// @JsonSerialize(using = JsonDateSerializer$19.class)
	// @JsonDeserialize(using = JsonDateDeserialize$19.class)
	// private Date createTime;
	//
	// /**
	// * 修改人
	// */
	// private String updateUser;
	//
	// /**
	// * 修改时间
	// */
	// @JsonSerialize(using = JsonDateSerializer$19.class)
	// @JsonDeserialize(using = JsonDateDeserialize$19.class)
	// private Date updateTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 发票类型（0 = 普通发票 1 = 增值票）
	 */
	private Integer invoiceType;

	/**
	 * 是否预开票(2 = 是 1 = 否)
	 */
	private Byte preInvoice;

	/**
	 * 结算单据编号
	 */
	private String balanceNo;

	/**
	 * 结算单金额
	 */
	private BigDecimal balanceAmount;

	/**
	 * 结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算
	 * 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)
	 */
	private Integer balanceType;
	/**
	 * 结算类型名称
	 */
	private String balanceTypeName;
	/**
	 * 单据状态名称
	 */
	private String statusName;

	/**
	 * 发票是否已使用：0 未使用，1 已使用
	 */
	private Byte useFlag;
	
	/**
	 * 确认状态名称
	 */
	private String useFlagName;
	
	

	public String getUseFlagName() {
		for(BillCommonInvoiceRegisterEnums billCommonInvoiceRegister:BillCommonInvoiceRegisterEnums.values()){
			if(getUseFlag()!=null && getUseFlag().intValue()== billCommonInvoiceRegister.getUseFlag()){
				return billCommonInvoiceRegister.getText();
			}
		}
		return useFlagName;
	}

	public void setUseFlagName(String useFlagName) {
		this.useFlagName = getUseFlagName();
	}

	/**
	 * 采购发票号标志
	 */
	private String invoiceNoFlag;

	/**
	 * 店铺编号
	 */
	private String shopNo;

	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 
	 */
	private String shortName;

	/**
	 * 商场编码
	 */
	private String mallNo;

	/**
	 * 商场名称
	 */
	private String mallName;

	/**
	 * 结算起始日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date balanceStartDate;

	/**
	 * 结算终止日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date balanceEndDate;

	 private String organNo;

    private String organName;

    private String month;


	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setStatus(Integer status) {
		this.status = status;
		if (null != status) {
			setStatusName(BillStatusEnums.getNameByNo(status));
			super.setStatusName(BillStatusEnums.getNameByNo(status));
		}
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
		if (null != balanceType) {
			setBalanceTypeName(BalanceTypeEnums.getTypeNameByNo(balanceType));
		}
	}

	// /**
	// *
	// * {@linkplain #billNo}
	// *
	// * @return the value of bill_common_invoice_register.bill_no
	// */
	// public String getBillNo() {
	// return billNo;
	// }
	//
	// /**
	// *
	// * {@linkplain #billNo}
	// * @param billNo the value for bill_common_invoice_register.bill_no
	// */
	// public void setBillNo(String billNo) {
	// this.billNo = billNo;
	// }

	/**
	 * 
	 * {@linkplain #salerNo}
	 * 
	 * @return the value of bill_common_invoice_register.saler_no
	 */
	public String getSalerNo() {
		return salerNo;
	}

	/**
	 * 
	 * {@linkplain #salerNo}
	 * 
	 * @param salerNo
	 *            the value for bill_common_invoice_register.saler_no
	 */
	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
		setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(salerNo));
	}

	/**
	 * 
	 * {@linkplain #salerName}
	 * 
	 * @return the value of bill_common_invoice_register.saler_name
	 */
	public String getSalerName() {
		return salerName;
	}

	/**
	 * 
	 * {@linkplain #salerName}
	 * 
	 * @param salerName
	 *            the value for bill_common_invoice_register.saler_name
	 */
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	/**
	 * 
	 * {@linkplain #billDate}
	 * 
	 * @return the value of bill_common_invoice_register.bill_date
	 */
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * 
	 * {@linkplain #billDate}
	 * 
	 * @param billDate
	 *            the value for bill_common_invoice_register.bill_date
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
	 * 
	 * {@linkplain #amount}
	 * 
	 * @return the value of bill_common_invoice_register.amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 
	 * {@linkplain #amount}
	 * 
	 * @param amount
	 *            the value for bill_common_invoice_register.amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @return the value of bill_common_invoice_register.remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @param remark
	 *            the value for bill_common_invoice_register.remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void getStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 
	 * {@linkplain #invoiceType}
	 * 
	 * @return the value of bill_common_invoice_register.invoice_type
	 */
	public Integer getInvoiceType() {
		return invoiceType;
	}

	/**
	 * 
	 * {@linkplain #invoiceType}
	 * 
	 * @param invoiceType
	 *            the value for bill_common_invoice_register.invoice_type
	 */
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * 
	 * {@linkplain #preInvoice}
	 * 
	 * @return the value of bill_common_invoice_register.pre_invoice
	 */
	public Byte getPreInvoice() {
		return preInvoice;
	}

	/**
	 * 
	 * {@linkplain #preInvoice}
	 * 
	 * @param preInvoice
	 *            the value for bill_common_invoice_register.pre_invoice
	 */
	public void setPreInvoice(Byte preInvoice) {
		this.preInvoice = preInvoice;
	}

	public String getBalanceTypeName() {
		return balanceTypeName;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	/**
	 * 
	 * {@linkplain #useFlag}
	 * 
	 * @return the value of bill_common_register_dtl.use_flag
	 */
	public Byte getUseFlag() {
		return useFlag;
	}

	/**
	 * 
	 * {@linkplain #useFlag}
	 * 
	 * @param useFlag
	 *            the value for bill_common_register_dtl.use_flag
	 */
	public void setUseFlag(Byte useFlag) {
		this.useFlag = useFlag;
	}

	/**
	 * 
	 * {@linkplain #shopNo}
	 * 
	 * @return the value of bill_common_invoice_register.shop_no
	 */
	public String getShopNo() {
		return shopNo;
	}

	/**
	 * 
	 * {@linkplain #shopNo}
	 * 
	 * @param shopNo
	 *            the value for bill_common_invoice_register.shop_no
	 */
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	/**
	 * 
	 * {@linkplain #shopName}
	 * 
	 * @return the value of bill_common_invoice_register.shop_name
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * 
	 * {@linkplain #shopName}
	 * 
	 * @param shopName
	 *            the value for bill_common_invoice_register.shop_name
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * 
	 * {@linkplain #mallNo}
	 * 
	 * @return the value of bill_common_invoice_register.mall_no
	 */
	public String getMallNo() {
		return mallNo;
	}

	/**
	 * 
	 * {@linkplain #mallNo}
	 * 
	 * @param mallNo
	 *            the value for bill_common_invoice_register.mall_no
	 */
	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	/**
	 * 
	 * {@linkplain #mallName}
	 * 
	 * @return the value of bill_common_invoice_register.mall_name
	 */
	public String getMallName() {
		return mallName;
	}

	/**
	 * 
	 * {@linkplain #mallName}
	 * 
	 * @param mallName
	 *            the value for bill_common_invoice_register.mall_name
	 */
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	/**
	 * 
	 * {@linkplain #balanceStartDate}
	 * 
	 * @return the value of bill_common_invoice_register.balance_start_date
	 */
	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	/**
	 * 
	 * {@linkplain #balanceStartDate}
	 * 
	 * @param balanceStartDate
	 *            the value for bill_common_invoice_register.balance_start_date
	 */
	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	/**
	 * 
	 * {@linkplain #balanceEndDate}
	 * 
	 * @return the value of bill_common_invoice_register.balance_end_date
	 */
	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	/**
	 * 
	 * {@linkplain #balanceEndDate}
	 * 
	 * @param balanceEndDate
	 *            the value for bill_common_invoice_register.balance_end_date
	 */
	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}

	public String getInvoiceNoFlag() {
		return invoiceNoFlag;
	}

	public void setInvoiceNoFlag(String invoiceNoFlag) {
		this.invoiceNoFlag = invoiceNoFlag;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	 public String getOrganNo() {
        return organNo;
    }

    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getInvoiceRegisterNo() {
		return invoiceRegisterNo;
	}

	public void setInvoiceRegisterNo(String invoiceRegisterNo) {
		this.invoiceRegisterNo = invoiceRegisterNo;
	}

	public String getDtlId() {
		return dtlId;
	}

	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
}