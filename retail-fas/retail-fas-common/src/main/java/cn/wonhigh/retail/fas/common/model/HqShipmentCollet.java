package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
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
public class HqShipmentCollet extends FasBaseModel implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 开始日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class) 
	private Date startDate;
	
	/**
	 * 结束日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class) 
	private Date endDate;
	
	
	private Integer total;
	
	 /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 经营区域名称
     */
    private String zoneName;

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
     * 供应商编号
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 鞋-金额(退残金额)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount01;
    
    /**
     * 服-金额(不确认转销金额)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount02;
    
    /**
     * 配-金额(未转销金额)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount03;
    
    /**
     * 其他-金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount04;
    
    /**
     * 鞋-数量(退残数量)
     */
    private Integer totalQty01;
    
    /**
     * 服-数量(不确认转销数量)
     */
    private Integer totalQty02;
    
    /**
     * 配-数量(未转销数量)
     */
    private Integer totalQty03;
    /**
     * 其他-数量
     */
    private Integer totalQty04;
    
    /**
     * 数量合计
     */
    private Integer qty;
    
    /**
     * 厂商额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal supplierAmount;
    
    /**
     * 地区额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal zoneAmount;
    
    /**
     * 加价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal zoneAddAmount;
    
    /**
     * 中间计算金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal amount;
    
    /**
     * 亏损额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lossAmount;
    
    /**
     * 年月
     */
    private String yearMonths;
    

    /**
     * 单据编号
     */
    private String billNo;
    
    /**
     * 退货编号
     */
    private String returnNo;

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
     * 收货地编号
     */
    private String receiveStoreNo;

    /**
     * 收货地名称
     */
    private String receiveStoreName;

    
    /**
     * 商品内码
     */
    private String itemNo;

    /**
     * 商品编号
     */
    private String itemCode;

    /**
     * 商场名称
     */
    private String itemName;

    /**
     * 所属品牌
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
     * 牌价金额(牌价差异)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagAmount;
    
    /** 发出方订货单位编码 */
    private String orderUnitNoFrom;
    
    /** 发出方订货单位名称 */
    private String orderUnitNameFrom;
    
    /** 发出方管理城市编号 */
	private String organNoFrom;

	/** 发出方管理城市名称 */
	private String organNameFrom;
	
	/**
	 * 退货日期
	 */
	private String reDate;
    
    /**
	 * 未确认日期
	 */
	private String noAffirmDate;
	
    /**
	 * 开票日期
	 */
	private String applyDate;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 召回属性
	 */
	private String rcAttribute;
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getTotalAmount01() {
		return totalAmount01;
	}

	public void setTotalAmount01(BigDecimal totalAmount01) {
		this.totalAmount01 = totalAmount01;
	}

	public BigDecimal getTotalAmount02() {
		return totalAmount02;
	}

	public void setTotalAmount02(BigDecimal totalAmount02) {
		this.totalAmount02 = totalAmount02;
	}

	public BigDecimal getTotalAmount03() {
		return totalAmount03;
	}

	public void setTotalAmount03(BigDecimal totalAmount03) {
		this.totalAmount03 = totalAmount03;
	}

	public Integer getTotalQty01() {
		return totalQty01;
	}

	public void setTotalQty01(Integer totalQty01) {
		this.totalQty01 = totalQty01;
	}

	public Integer getTotalQty02() {
		return totalQty02;
	}

	public void setTotalQty02(Integer totalQty02) {
		this.totalQty02 = totalQty02;
	}

	public Integer getTotalQty03() {
		return totalQty03;
	}

	public void setTotalQty03(Integer totalQty03) {
		this.totalQty03 = totalQty03;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getSupplierAmount() {
		return supplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		this.supplierAmount = supplierAmount;
	}

	public BigDecimal getZoneAmount() {
		return zoneAmount;
	}

	public void setZoneAmount(BigDecimal zoneAmount) {
		this.zoneAmount = zoneAmount;
	}

	public BigDecimal getZoneAddAmount() {
		return zoneAddAmount;
	}

	public void setZoneAddAmount(BigDecimal zoneAddAmount) {
		this.zoneAddAmount = zoneAddAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public String getYearMonths() {
		return yearMonths;
	}

	public void setYearMonths(String yearMonths) {
		this.yearMonths = yearMonths;
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

	public String getReceiveStoreNo() {
		return receiveStoreNo;
	}

	public void setReceiveStoreNo(String receiveStoreNo) {
		this.receiveStoreNo = receiveStoreNo;
	}

	public String getReceiveStoreName() {
		return receiveStoreName;
	}

	public void setReceiveStoreName(String receiveStoreName) {
		this.receiveStoreName = receiveStoreName;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public BigDecimal getTagAmount() {
		return tagAmount;
	}

	public void setTagAmount(BigDecimal tagAmount) {
		this.tagAmount = tagAmount;
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

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public BigDecimal getTotalAmount04() {
		return totalAmount04;
	}

	public void setTotalAmount04(BigDecimal totalAmount04) {
		this.totalAmount04 = totalAmount04;
	}

	public Integer getTotalQty04() {
		return totalQty04;
	}

	public void setTotalQty04(Integer totalQty04) {
		this.totalQty04 = totalQty04;
	}

	public String getReDate() {
		return reDate;
	}

	public void setReDate(String reDate) {
		this.reDate = reDate;
	}

	public String getNoAffirmDate() {
		return noAffirmDate;
	}

	public void setNoAffirmDate(String noAffirmDate) {
		this.noAffirmDate = noAffirmDate;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRcAttribute() {
		return rcAttribute;
	}

	public void setRcAttribute(String rcAttribute) {
		this.rcAttribute = rcAttribute;
	}

	public BigDecimal getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(BigDecimal lossAmount) {
		this.lossAmount = lossAmount;
	}
	
}