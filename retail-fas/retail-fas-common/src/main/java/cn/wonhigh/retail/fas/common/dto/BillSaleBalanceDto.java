package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillCodeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 单据类
 * @author wang.m1
 * @date  2014-10-18 12:32:47
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@ExportFormat(className=AbstractExportFormat.class)
public class BillSaleBalanceDto extends FasBaseModel implements SequenceStrId {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -90364152870460702L;
    
	/**
	 * 是否对调
	 */
	private Boolean isSwap;
	
    /**
	 * FAS单据类型
	 */
	private String fasBillCode;
	
	/**
	 * FAS单据类型名称
	 */
	private String fasBillName;

    /**
     * 单据编号
     */
	@ExcelCell("B")
    private String billNo;

    /**
     * 单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)
     */
    private Integer billType;

    /**
     * 业务类型
     */
    private Integer bizType;
    
    /**
     * 业务类型名
     */
    private String bizTypeName;
    
    /**
     * 原单编号
     */
    private String originalBillNo;
    
    /**
     * 相关单号
     */
    private String refBillNo;

    /**
     * 相关单据类型
     */
    private Integer refBillType;

    /**
     * 单据状态
     */
    private Integer status;

    /**
     * 买方编号
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 卖方编号
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 发出日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date sendDate;

    /**
     * 接收日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date receiveDate;

    /**
     * 发出地编号
     */
    private String sendStoreNo;

    /**
     * 发出地名称
     */
    private String sendStoreName;

    /**
     * 收货地编号
     */
    private String receiveStoreNo;

    /**
     * 收货地名称
     */
    private String receiveStoreName;

    /**
     * 明细id
     */
    private String skuId;

    /**
     * SKU编号
     */
    private String skuNo;

    /**
     * 供应商编号
     */
    private String supplierNo;

    /**
     * 商品编号
     */
    private String itemNo;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;
    
    /**
     * 品牌部编号
     */
    private String brandUnitNo;
    
    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 大类编号
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 含税单价(结算价格、默认原单据价格)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal cost;

    /**
     * 单据价格(原单据价格)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billCost;
    
    /**
     * 实际出库价格(费用分摊后的价格)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal actualCost;
    
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal otherDeductCost;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税单价
     */
    private BigDecimal exclusiveCost;
    
    /**
	 * 总部成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal headquarterCost;
	
	/**
	 * 地区成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionCost;
	
	/**
	 * 买方地区价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionCostSecond;
	
	/**
	 * 单位成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal unitCost;
	
	/**
	 * 返利金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal billRebateAmount;
	
	/**
	 * 返利后折扣
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal billRebateDiscount;
	
	/**
	 * 返利后折扣
	 */
	private String billRebateDiscountStr;
	
	/**
	 * 其它费用
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal otherPrice;
	
	/**
	 * 买方单位成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal unitCostSecond;
	
	/**
	 * 采购价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchasePrice;
	
	/**
	 * 物料价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal materialPrice;
	
	/**
	 * 厂进价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal factoryPrice;

	/**
	 * 采购额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchaseAmount;
	
    /**
     * 发出数量
     */
    private Integer sendQty;
    
    /**
     * 解冻日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date unfrozenDate;

    /**
     * 接受数量
     */
    private Integer receiveQty;
    
    /**
     * 解冻数量
     */
    @ExcelCell("D") 
    private Integer unfrozenQty;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 是否拆分单据
     */
    private Integer isSplit;
    
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 商品编码
     */
    @ExcelCell("C") 
    private String itemCode;
	
	/**
     * 结算类型
     */
    private Integer balanceType;
    
    /**
     * 结算状态
     */
    private Integer balanceStatus;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 结算路径编码
     */
    private String pathNo;
    
    /** 发出方订货单位编码 */
    private String orderUnitNoFrom;
    
    /** 发出方订货单位名称 */
    private String orderUnitNameFrom;
    
    /** 发出方管理城市编号 */
	private String organNoFrom;

	/** 发出方管理城市名称 */
	private String organNameFrom;
	
	/** 发出方所属大区编码 */
    private String zoneNoFrom;
    
    /** 发出方所属大区名称 */
    private String zoneNameFrom;
    
	/** 接受方订货单位编码 */
	private String orderUnitNo;
	
	/** 接受方订货单位名称 */
	private String orderUnitName;
	
	/** 接受方管理城市编号 */
	private String organNo;
	
	/** 接受方管理城市名称 */
	private String organName;
	
	/** 接收方所属大区编码 */
    private String zoneNo;
    
    /** 接收方所属大区名称 */
    private String zoneName;
    
    /**
     * 是否生成开票申请
     */
    private Integer isBillInvoice;
    
    /**
     * 开票申请编号
     */
    private String invoiceApplyNo;
    
    /**
     * 开票申请日期
     */
    private Date invoiceApplyDate;
    
    /** 年份 */
	private String years;
	
	/** 季节 */
	private String season;
	
	/** 订货形式 */
	private String orderfrom;
    
	/** 性别 */
	private String gender;
	
	/** 牌价 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;
	
	/** 全国统一牌价 */
	private BigDecimal tagPriceNation;
	
	/** 扣率*/
	@JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discount;
	
	/** 扣率str*/
	private String discountStr;
	
	/**
	 * 异常价格是否已更新
	 */
	private Integer costChecked;
    
    /**
     * 索赔编号
     */
    private String claimNo;
    
    /**
     * 退货编号
     */
    @ExcelCell("A")
    private String returnNo;
	
	/*############################扩展属性############################*/
	
    /**
     * 结算开始时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date balanceStartDate;

    /**
     * 结算结束日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date balanceEndDate;
    /**
     * 结算开始时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date balanceStartDateN;
    
    /**
     * 结算结束日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date balanceEndDateN;
    
    /**
     * 单据类型显示名称
     */
    private String billTypeName;
    
    /**
     * 单据状态名称
     */
    private String statusName;
    
    /**
     * 异常原因
     */
    private String exceptionName;
    
    /**
     * 发出金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sendAmount;
    
    /**
     * 接收金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal receiveAmount;
    
    /**
     * 差异金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal differenceAmount;
    
    /**
     * 一级大类
     */
    private String oneLevelCategoryNo;
    
    /**
     * 一级大类名称
     */
    private String oneLevelCategoryName;
    
    /**
     * 二级大类
     */
    private String twoLevelCategoryNo;
    
    /**
     * 二级大类名称
     */
    private String twoLevelCategoryName;
    
    /**
     * 差异数量
     */
    private Integer differenceQty;
	
	/**
	 *  结算公司 
	 */
	private String companyNo;

	/**
	 *  结算公司 
	 */
	private String companyName;
	
	/**
	 *  性别名称
	 */
	private String genderName;
	
	/**
	 * 扣项金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductionAmount; 
	
	/**
	 * 扣项描述
	 */
	private String deductionRemark;
	
	/**
	 *  供应商组名称
	 */
	private String supplierGroupName;
	
	/**
	 * 商品颜色
	 */
	private String mainColor;
	
	/**
	 * 票前返利
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal rebateAmount;
	
	/**
	 * 开票金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal invoiceAmount;
	
	private String groupName;
	
	/**
	 * 核定价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal aprice;
	
	/**
	 * OA超额状态
	 */
	private String excessStatusName;
	
	
	/**
	 * OA超额状态
	 */
	private String excessStatus;
	
	/**
	 * 订货类型(期货;现货)
	 */
	private String orderType;
	
	/**
	 * 结算号
	 */
	private String settlementNumber;
	
	/**
     * 返利金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal rebatePrice;
    
    /**
     * 折扣
     */
	@JsonSerialize(using = BigDecimalSerializer$4.class)
	private BigDecimal tsRebate;
	
	/**
     * 牌价金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPriceAmount;
	
	/**
     * 成本金额
     */
	@JsonSerialize(using = BigDecimalSerializer$4.class)
	private BigDecimal costAmount;

	public String getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(String settlementNumber) {
		this.settlementNumber = settlementNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getExcessStatus() {
		return excessStatus;
	}

	public void setExcessStatus(String excessStatus) {
		this.excessStatus = excessStatus;
	}

	public BigDecimal getAprice() {
		return aprice;
	}

	public void setAprice(BigDecimal aprice) {
		this.aprice = aprice;
	}

	public String getExcessStatusName() {
		return excessStatusName;
	}

	public void setExcessStatusName(String excessStatusName) {
		this.excessStatusName = excessStatusName;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public String getSupplierGroupName() {
		return supplierGroupName;
	}

	public void setSupplierGroupName(String supplierGroupName) {
		this.supplierGroupName = supplierGroupName;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getDeductionRemark() {
		return deductionRemark;
	}

	public void setDeductionRemark(String deductionRemark) {
		this.deductionRemark = deductionRemark;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getOrderfrom() {
		return orderfrom;
	}

	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}

	public String getZoneNoFrom() {
		return zoneNoFrom;
	}

	public void setZoneNoFrom(String zoneNoFrom) {
		this.zoneNoFrom = zoneNoFrom;
	}

	public String getZoneNameFrom() {
		return zoneNameFrom;
	}

	public void setZoneNameFrom(String zoneNameFrom) {
		this.zoneNameFrom = zoneNameFrom;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getOrderUnitNoFrom() {
		return orderUnitNoFrom;
	}

	public void setOrderUnitNoFrom(String orderUnitNoFrom) {
		this.orderUnitNoFrom = orderUnitNoFrom;
	}

	public String getOrderUnitNameFrom() {
		return orderUnitNameFrom;
	}

	public void setOrderUnitNameFrom(String orderUnitNameFrom) {
		this.orderUnitNameFrom = orderUnitNameFrom;
	}

	public String getOrganNoFrom() {
		return organNoFrom;
	}

	public void setOrganNoFrom(String organNoFrom) {
		this.organNoFrom = organNoFrom;
	}

	public String getOrganNameFrom() {
		return organNameFrom;
	}

	public void setOrganNameFrom(String organNameFrom) {
		this.organNameFrom = organNameFrom;
	}

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public String getOrderUnitName() {
		return orderUnitName;
	}

	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
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

	public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
	}
    
	public String getOneLevelCategoryNo() {
		return oneLevelCategoryNo;
	}

	public void setOneLevelCategoryNo(String oneLevelCategoryNo) {
		this.oneLevelCategoryNo = oneLevelCategoryNo;
	}

	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}

	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
	}

	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}

	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getOriginalBillNo() {
		return originalBillNo;
	}

	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}
	
    public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}
    
	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getDifferenceQty() {
		return differenceQty;
	}

	public void setDifferenceQty(Integer differenceQty) {
		this.differenceQty = differenceQty;
	}
	
	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public Integer getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(Integer balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	
    public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStatusName() {
    	if(null !=status){
			return GmsBillStatusEnums.getTextByStatus(status);
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}	

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_sale_balance.bill_no
     */
    @Override
	public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_sale_balance.bill_no
     */
    @Override
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of bill_sale_balance.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for bill_sale_balance.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
		if(null !=billType){
			setBillTypeName(BillTypeEnums.getNameByNo(billType));
		}

    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_sale_balance.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_sale_balance.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillType}
     *
     * @return the value of bill_sale_balance.ref_bill_type
     */
    public Integer getRefBillType() {
        return refBillType;
    }

    /**
     * 
     * {@linkplain #refBillType}
     * @param refBillType the value for bill_sale_balance.ref_bill_type
     */
    public void setRefBillType(Integer refBillType) {
        this.refBillType = refBillType;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_sale_balance.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_sale_balance.status
     */
    public void setStatus(Integer status) {
        this.status = status;
        if(null !=status){
			this.statusName=GmsBillStatusEnums.getTextByStatus(status);
		}
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_sale_balance.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_sale_balance.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_sale_balance.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_sale_balance.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_sale_balance.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_sale_balance.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_sale_balance.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_sale_balance.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #sendDate}
     *
     * @return the value of bill_sale_balance.send_date
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 
     * {@linkplain #sendDate}
     * @param sendDate the value for bill_sale_balance.send_date
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of bill_sale_balance.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     * @param receiveDate the value for bill_sale_balance.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #sendStoreNo}
     *
     * @return the value of bill_sale_balance.send_store_no
     */
    public String getSendStoreNo() {
        return sendStoreNo;
    }

    /**
     * 
     * {@linkplain #sendStoreNo}
     * @param sendStoreNo the value for bill_sale_balance.send_store_no
     */
    public void setSendStoreNo(String sendStoreNo) {
        this.sendStoreNo = sendStoreNo;
    }

    /**
     * 
     * {@linkplain #sendStoreName}
     *
     * @return the value of bill_sale_balance.send_store_name
     */
    public String getSendStoreName() {
        return sendStoreName;
    }

    /**
     * 
     * {@linkplain #sendStoreName}
     * @param sendStoreName the value for bill_sale_balance.send_store_name
     */
    public void setSendStoreName(String sendStoreName) {
        this.sendStoreName = sendStoreName;
    }

    /**
     * 
     * {@linkplain #receiveStoreNo}
     *
     * @return the value of bill_sale_balance.receive_store_no
     */
    public String getReceiveStoreNo() {
        return receiveStoreNo;
    }

    /**
     * 
     * {@linkplain #receiveStoreNo}
     * @param receiveStoreNo the value for bill_sale_balance.receive_store_no
     */
    public void setReceiveStoreNo(String receiveStoreNo) {
        this.receiveStoreNo = receiveStoreNo;
    }

    /**
     * 
     * {@linkplain #receiveStoreName}
     *
     * @return the value of bill_sale_balance.receive_store_name
     */
    public String getReceiveStoreName() {
        return receiveStoreName;
    }

    /**
     * 
     * {@linkplain #receiveStoreName}
     * @param receiveStoreName the value for bill_sale_balance.receive_store_name
     */
    public void setReceiveStoreName(String receiveStoreName) {
        this.receiveStoreName = receiveStoreName;
    }

    /**
     * 
     * {@linkplain #skuId}
     *
     * @return the value of bill_sale_balance.sku_id
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * 
     * {@linkplain #skuId}
     * @param skuId the value for bill_sale_balance.sku_id
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_sale_balance.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_sale_balance.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of bill_sale_balance.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for bill_sale_balance.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_sale_balance.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_sale_balance.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_sale_balance.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_sale_balance.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_sale_balance.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_sale_balance.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_sale_balance.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_sale_balance.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_sale_balance.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_sale_balance.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_sale_balance.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_sale_balance.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_sale_balance.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_sale_balance.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
//        if(null !=cost){
//			if(cost.intValue() == 0 ){
//				setExceptionName(ExceptionTypeEnums.COST_IS_ZERO.getTypeName());
//			}else if(cost.intValue() == 1){
//				setExceptionName(ExceptionTypeEnums.COST_IS_ONE.getTypeName());
//			}
//		}
    }

    /**
     * 
     * {@linkplain #billCost}
     *
     * @return the value of bill_sale_balance.bill_cost
     */
    public BigDecimal getBillCost() {
        return billCost;
    }

    /**
     * 
     * {@linkplain #billCost}
     * @param billCost the value for bill_sale_balance.bill_cost
     */
    public void setBillCost(BigDecimal billCost) {
        this.billCost = billCost;
//        if(null !=billCost){
//			if(billCost.intValue() == 0 ){
//				setExceptionName(ExceptionTypeEnums.COST_IS_ZERO.getTypeName());
//			}else if(billCost.intValue() == 1){
//				setExceptionName(ExceptionTypeEnums.COST_IS_ONE.getTypeName());
//			}
//		}
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_sale_balance.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_sale_balance.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     *
     * @return the value of bill_sale_balance.exclusive_cost
     */
    public BigDecimal getExclusiveCost() {
        return exclusiveCost;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     * @param exclusiveCost the value for bill_sale_balance.exclusive_cost
     */
    public void setExclusiveCost(BigDecimal exclusiveCost) {
        this.exclusiveCost = exclusiveCost;
    }

    /**
     * 
     * {@linkplain #sendQty}
     *
     * @return the value of bill_sale_balance.send_qty
     */
    public Integer getSendQty() {
        return sendQty;
    }

    /**
     * 
     * {@linkplain #sendQty}
     * @param sendQty the value for bill_sale_balance.send_qty
     */
    public void setSendQty(Integer sendQty) {
    	if(sendQty == null){
    		sendQty = new Integer(0);
    	}
        this.sendQty = sendQty;
    }

    /**
     * 
     * {@linkplain #receiveQty}
     *
     * @return the value of bill_sale_balance.receive_qty
     */
    public Integer getReceiveQty() {
        return receiveQty;
    }

    /**
     * 
     * {@linkplain #receiveQty}
     * @param receiveQty the value for bill_sale_balance.receive_qty
     */
    public void setReceiveQty(Integer receiveQty) {
    	if(receiveQty == null){
    		receiveQty = new Integer(0);
    	}
        this.receiveQty = receiveQty;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of bill_sale_balance.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for bill_sale_balance.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_sale_balance.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_sale_balance.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #isSplit}
     *
     * @return the value of bill_sale_balance.is_split
     */
    public Integer getIsSplit() {
        return isSplit;
    }

    /**
     * 
     * {@linkplain #isSplit}
     * @param isSplit the value for bill_sale_balance.is_split
     */
    public void setIsSplit(Integer isSplit) {
        this.isSplit = isSplit;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerNo == null) ? 0 : buyerNo.hashCode());
		result = prime * result
				+ ((refBillNo == null) ? 0 : refBillNo.hashCode());
		result = prime * result + ((salerNo == null) ? 0 : salerNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillSaleBalanceDto other = (BillSaleBalanceDto) obj;
		if (buyerNo == null) {
			if (other.buyerNo != null)
				return false;
		} else if (!buyerNo.equals(other.buyerNo))
			return false;
		if (refBillNo == null) {
			if (other.refBillNo != null)
				return false;
		} else if (!refBillNo.equals(other.refBillNo))
			return false;
		if (salerNo == null) {
			if (other.salerNo != null)
				return false;
		} else if (!salerNo.equals(other.salerNo))
			return false;
		if (billType == null) {
			if(other.billType != null)
				return false;
		}else if (!billType.equals(other.billType))
			return false;
		if (bizType == null) {
			if(other.bizType != null)
				return false;
		}else if (!bizType.equals(other.bizType))
			return false;
		if (itemNo == null) {
			if(other.itemNo != null)
				return false;
		}else if (!itemNo.equals(other.itemNo))
			return false;
		return true;
	}

	public Integer getIsBillInvoice() {
		return isBillInvoice;
	}

	public String getInvoiceApplyNo() {
		return invoiceApplyNo;
	}

	public Date getInvoiceApplyDate() {
		return invoiceApplyDate;
	}

	public void setIsBillInvoice(Integer isBillInvoice) {
		this.isBillInvoice = isBillInvoice;
	}

	public void setInvoiceApplyNo(String invoiceApplyNo) {
		this.invoiceApplyNo = invoiceApplyNo;
	}

	public void setInvoiceApplyDate(Date invoiceApplyDate) {
		this.invoiceApplyDate = invoiceApplyDate;
	}

	public Integer getCostChecked() {
		return costChecked;
	}

	public void setCostChecked(Integer costChecked) {
		this.costChecked = costChecked;
	}
	
	public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}

	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
	}

	public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getDiscountStr() {
		return discountStr;
	}

	public void setDiscountStr(String discountStr) {
		this.discountStr = discountStr;
	}

	public BigDecimal getRegionCostSecond() {
		return regionCostSecond;
	}

	public void setRegionCostSecond(BigDecimal regionCostSecond) {
		this.regionCostSecond = regionCostSecond;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public BigDecimal getTagPriceNation() {
		return tagPriceNation;
	}

	public void setTagPriceNation(BigDecimal tagPriceNation) {
		this.tagPriceNation = tagPriceNation;
	}

	public BigDecimal getUnitCostSecond() {
		return unitCostSecond;
	}

	public void setUnitCostSecond(BigDecimal unitCostSecond) {
		this.unitCostSecond = unitCostSecond;
	}

	public String getFasBillCode() {
		return fasBillCode;
	}

	public void setFasBillCode(String fasBillCode) {
		this.fasBillCode = fasBillCode;
		if (null != fasBillCode) {
			setFasBillName(FasBillCodeEnums.getNameByCode(fasBillCode));
		}
	}

	public String getFasBillName() {
		return fasBillName;
	}

	public void setFasBillName(String fasBillName) {
		this.fasBillName = fasBillName;
	}

	public BigDecimal getActualCost() {
		return actualCost;
	}

	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public BigDecimal getOtherDeductCost() {
		return otherDeductCost;
	}

	public void setOtherDeductCost(BigDecimal otherDeductCost) {
		this.otherDeductCost = otherDeductCost;
	}

	public BigDecimal getBillRebateAmount() {
		return billRebateAmount;
	}

	public void setBillRebateAmount(BigDecimal billRebateAmount) {
		this.billRebateAmount = billRebateAmount;
	}

	public BigDecimal getBillRebateDiscount() {
		return billRebateDiscount;
	}

	public void setBillRebateDiscount(BigDecimal billRebateDiscount) {
		this.billRebateDiscount = billRebateDiscount;
	}

	public BigDecimal getRebatePrice() {
		return rebatePrice;
	}

	public void setRebatePrice(BigDecimal rebatePrice) {
		this.rebatePrice = rebatePrice;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public Integer getUnfrozenQty() {
		return unfrozenQty;
	}

	public void setUnfrozenQty(Integer unfrozenQty) {
		this.unfrozenQty = unfrozenQty;
	}

	public BigDecimal getTsRebate() {
		return tsRebate;
	}

	public void setTsRebate(BigDecimal tsRebate) {
		this.tsRebate = tsRebate;
	}

	public BigDecimal getTagPriceAmount() {
		return tagPriceAmount;
	}

	public void setTagPriceAmount(BigDecimal tagPriceAmount) {
		this.tagPriceAmount = tagPriceAmount;
	}

	public Date getUnfrozenDate() {
		return unfrozenDate;
	}

	public void setUnfrozenDate(Date unfrozenDate) {
		this.unfrozenDate = unfrozenDate;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public Boolean getIsSwap() {
		return isSwap;
	}

	public void setIsSwap(Boolean isSwap) {
		this.isSwap = isSwap;
	}

	public String getBillRebateDiscountStr() {
		return billRebateDiscountStr;
	}

	public void setBillRebateDiscountStr(String billRebateDiscountStr) {
		this.billRebateDiscountStr = billRebateDiscountStr;
	}

	public Date getBalanceStartDateN() {
		return balanceStartDateN;
	}

	public void setBalanceStartDateN(Date balanceStartDateN) {
		this.balanceStartDateN = balanceStartDateN;
	}

	public Date getBalanceEndDateN() {
		return balanceEndDateN;
	}

	public void setBalanceEndDateN(Date balanceEndDateN) {
		this.balanceEndDateN = balanceEndDateN;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}
	
}