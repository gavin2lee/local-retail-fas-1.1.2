package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 收发货在途对账报表
 * @author ning.ly
 * @date  2015-04-24 10:10:28
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
public class TransferingCheckDto extends FasBaseModel {
  
	private static final long serialVersionUID = 963481518226510851L;
	
	private String yearsName;
	private String seasonName;
	private String orderfromName;
	private String genderName;

    /**
     * 单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)
     */
    private Integer billType;
    
    /**
     * 单据编号
     */
    private String billNo;
    
    /**
     * 业务类型(0：订货 ，1：补货  ，2直接)
     */
    private Integer bizType;

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
     * 单据状态名称
     */
    private String statusName;

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
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date sendDate;

    /**
     * 接收日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
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
     * 供应商名称
     */
    private String supplierName;

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
     * 含税单价(结算价格、默认原单据价格)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal price;


	/**
     * 单据价格(原单据价格)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billCost;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税单价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal exclusiveCost;

    /**
     * 发出数量
     */
    private Integer sendQty;

    /**
     * 接受数量
     */
    private Integer receiveQty;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 结算单号
     */
    private String balanceNo;



    /**
     * 单据类型显示名称
     */
    private String billTypeName;

    
    /**
     * 结算类型
     */
    private Integer balanceType;
    
    /**
     * 结算类型名称
     */
    private String balanceTypeName;
    
    /**
     * 商品编码
     */
    private String itemCode;
    
    /**
     * 发出金额
     */
    private BigDecimal sendAmount;
    
    /**
     * 接收金额
     */
    private BigDecimal receiveAmount;
   
    
    /**
     * 差异金额
     */
    private BigDecimal differenceAmount;
    
    /**
     * 结算状态
     */
    private Integer balanceStatus;
    
    /**
     * 差异数量
     */
    private Integer differenceQty;

    /**
     * 原单编号
     */
    private String originalBillNo;
    
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
     * 品牌名称
     */
    private String brandUnitName;
    
    
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
    
    /** 年份 */
	private String years;
	
	/** 季节 */
	private String season;
	
	/** 订货形式 */
	private String orderfrom;
	
	/** 性别 */
	private String gender;
	
	/** 牌价 */
	private BigDecimal tagPrice;
	
    /**
     * 备注
     */
    private String remark;
    
	/**本月发出数量*/
	private int currMonSenNum;
	/**本月发出金额*/
	private BigDecimal currMonSenRMB;
	/**本月发本月收数量*/
	private int currMonRecNum;
	/**本月发本月收金额*/
	private BigDecimal currMonRecRMB;
	/**本月发下月收数量*/
	private int nextMonRecNum;
	/**本月发下月收金额*/
	private BigDecimal nextMonRecRMB;
	/**本月发未收数量*/
	private int currMonYetRecNum;
	/**本月发未收金额*/
	private BigDecimal currMonYetRecRMB;
	/**本月差异数量*/
	private int currMonDiffNum;
	/**本月差异金额*/
	private BigDecimal currMonDiffRMB;
	/**上月发出数量*/
	private int lastMonSenNum;
	/**上月发出金额*/
	private BigDecimal lastMonSenRMB;
	/**上月发上月收数量*/
	private int lastMonLastRecNum;
	/**上月发上月收金额*/
	private BigDecimal lastMonLastRecRMB;
	/**上月发本月收数量*/
	private int lastMonRecNum;
	/**上月发本月收金额*/
	private BigDecimal lastMonRecRMB;
	/**上月发未收数量*/
	private int lastMonYetRecNum;
	/**上月发未收金额*/
	private BigDecimal lastMonYetRecRMB;
	/**上月差异数量*/
	private int lastMonDiffNum;
	/**上月差异金额*/
	private BigDecimal lastMonDiffRMB;
	
	public int getCurrMonSenNum() {
		return currMonSenNum;
	}

	public void setCurrMonSenNum(int currMonSenNum) {
		this.currMonSenNum = currMonSenNum;
	}

	public BigDecimal getCurrMonSenRMB() {
		if(this.currMonSenRMB == null){
			return new BigDecimal(0);
		}
		return currMonSenRMB;
	}

	public void setCurrMonSenRMB(BigDecimal currMonSenRMB) {
		this.currMonSenRMB = currMonSenRMB;
	}

	public int getCurrMonRecNum() {
		return currMonRecNum;
	}

	public void setCurrMonRecNum(int currMonRecNum) {
		
		this.currMonRecNum = currMonRecNum;
	}

	public BigDecimal getCurrMonRecRMB() {
		if(this.currMonRecRMB == null){
			return new BigDecimal(0);
		}
		return currMonRecRMB;
	}

	public void setCurrMonRecRMB(BigDecimal currMonRecRMB) {
		this.currMonRecRMB = currMonRecRMB;
	}

	public int getNextMonRecNum() {
		return nextMonRecNum;
	}

	public void setNextMonRecNum(int nextMonRecNum) {
		this.nextMonRecNum = nextMonRecNum;
	}

	public BigDecimal getNextMonRecRMB() {
		if(this.nextMonRecRMB == null){
			return new BigDecimal(0);
		}
		return nextMonRecRMB;
	}

	public void setNextMonRecRMB(BigDecimal nextMonRecRMB) {
		this.nextMonRecRMB = nextMonRecRMB;
	}

	public int getCurrMonYetRecNum() {
		return currMonYetRecNum;
	}

	public void setCurrMonYetRecNum(int currMonYetRecNum) {
		this.currMonYetRecNum = currMonYetRecNum;
	}

	public BigDecimal getCurrMonYetRecRMB() {
		if(this.currMonYetRecRMB == null){
			return new BigDecimal(0);
		}
		return currMonYetRecRMB;
	}

	public void setCurrMonYetRecRMB(BigDecimal currMonYetRecRMB) {
		this.currMonYetRecRMB = currMonYetRecRMB;
	}

	public int getCurrMonDiffNum() {
		return currMonDiffNum;
	}

	public void setCurrMonDiffNum(int currMonDiffNum) {
		this.currMonDiffNum = currMonDiffNum;
	}

	public BigDecimal getCurrMonDiffRMB() {
		if(this.currMonDiffRMB == null){
			return new BigDecimal(0);
		}
		return currMonDiffRMB;
	}

	public void setCurrMonDiffRMB(BigDecimal currMonDiffRMB) {
		this.currMonDiffRMB = currMonDiffRMB;
	}

	public int getLastMonRecNum() {
		return lastMonRecNum;
	}

	public void setLastMonRecNum(int lastMonRecNum) {
		this.lastMonRecNum = lastMonRecNum;
	}

	public BigDecimal getLastMonRecRMB() {
		if(this.lastMonRecRMB == null){
			return new BigDecimal(0);
		}
		return lastMonRecRMB;
	}

	public void setLastMonRecRMB(BigDecimal lastMonRecRMB) {
		this.lastMonRecRMB = lastMonRecRMB;
	}

	public int getLastMonYetRecNum() {
		return lastMonYetRecNum;
	}

	public void setLastMonYetRecNum(int lastMonYetRecNum) {
		this.lastMonYetRecNum = lastMonYetRecNum;
	}

	public BigDecimal getLastMonYetRecRMB() {
		if(this.lastMonYetRecRMB == null){
			return new BigDecimal(0);
		}
		return lastMonYetRecRMB;
	}

	public void setLastMonYetRecRMB(BigDecimal lastMonYetRecRMB) {
	
		this.lastMonYetRecRMB = lastMonYetRecRMB;
	}

	public int getLastMonDiffNum() {
		return lastMonDiffNum;
	}

	public void setLastMonDiffNum(int lastMonDiffNum) {
		this.lastMonDiffNum = lastMonDiffNum;
	}

	public BigDecimal getLastMonDiffRMB() {
		if(this.lastMonDiffRMB == null){
			return new BigDecimal(0);
		}
		return lastMonDiffRMB;
	}

	public void setLastMonDiffRMB(BigDecimal lastMonDiffRMB) {
		this.lastMonDiffRMB = lastMonDiffRMB;
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

	public Integer getDifferenceQty() {
		return differenceQty;
	}

	public void setDifferenceQty(Integer differenceQty) {
		this.differenceQty = differenceQty;
	}

	public Integer getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(Integer balanceStatus) {
		this.balanceStatus = balanceStatus;
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

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}
	
	public String getBalanceTypeName() {
		return balanceTypeName;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
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
	

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of bill_buy_balance.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for bill_buy_balance.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
		if(null !=billType){
			setBillTypeName(BillTypeEnums.getNameByNo(billType));
		}
    }

    public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	/**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_buy_balance.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_buy_balance.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillType}
     *
     * @return the value of bill_buy_balance.ref_bill_type
     */
    public Integer getRefBillType() {
        return refBillType;
    }

    /**
     * 
     * {@linkplain #refBillType}
     * @param refBillType the value for bill_buy_balance.ref_bill_type
     */
    public void setRefBillType(Integer refBillType) {
        this.refBillType = refBillType;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_buy_balance.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_buy_balance.status
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
     * @return the value of bill_buy_balance.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_buy_balance.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_buy_balance.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_buy_balance.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_buy_balance.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_buy_balance.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_buy_balance.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_buy_balance.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #sendDate}
     *
     * @return the value of bill_buy_balance.send_date
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 
     * {@linkplain #sendDate}
     * @param sendDate the value for bill_buy_balance.send_date
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of bill_buy_balance.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     * @param receiveDate the value for bill_buy_balance.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #sendStoreNo}
     *
     * @return the value of bill_buy_balance.send_store_no
     */
    public String getSendStoreNo() {
        return sendStoreNo;
    }

    /**
     * 
     * {@linkplain #sendStoreNo}
     * @param sendStoreNo the value for bill_buy_balance.send_store_no
     */
    public void setSendStoreNo(String sendStoreNo) {
        this.sendStoreNo = sendStoreNo;
    }

    /**
     * 
     * {@linkplain #sendStoreName}
     *
     * @return the value of bill_buy_balance.send_store_name
     */
    public String getSendStoreName() {
        return sendStoreName;
    }

    /**
     * 
     * {@linkplain #sendStoreName}
     * @param sendStoreName the value for bill_buy_balance.send_store_name
     */
    public void setSendStoreName(String sendStoreName) {
        this.sendStoreName = sendStoreName;
    }

    /**
     * 
     * {@linkplain #receiveStoreNo}
     *
     * @return the value of bill_buy_balance.receive_store_no
     */
    public String getReceiveStoreNo() {
        return receiveStoreNo;
    }

    /**
     * 
     * {@linkplain #receiveStoreNo}
     * @param receiveStoreNo the value for bill_buy_balance.receive_store_no
     */
    public void setReceiveStoreNo(String receiveStoreNo) {
        this.receiveStoreNo = receiveStoreNo;
    }

    /**
     * 
     * {@linkplain #receiveStoreName}
     *
     * @return the value of bill_buy_balance.receive_store_name
     */
    public String getReceiveStoreName() {
        return receiveStoreName;
    }

    /**
     * 
     * {@linkplain #receiveStoreName}
     * @param receiveStoreName the value for bill_buy_balance.receive_store_name
     */
    public void setReceiveStoreName(String receiveStoreName) {
        this.receiveStoreName = receiveStoreName;
    }

    /**
     * 
     * {@linkplain #skuId}
     *
     * @return the value of bill_buy_balance.sku_id
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * 
     * {@linkplain #skuId}
     * @param skuId the value for bill_buy_balance.sku_id
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_buy_balance.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_buy_balance.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of bill_buy_balance.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for bill_buy_balance.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_buy_balance.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_buy_balance.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_buy_balance.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_buy_balance.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_buy_balance.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_buy_balance.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_buy_balance.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_buy_balance.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_buy_balance.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_buy_balance.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_buy_balance.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_buy_balance.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_buy_balance.cost
     */
    public BigDecimal getCost() {
    	if(null == cost){
    		return new BigDecimal(0);
    	}
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_buy_balance.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
//        if(null !=cost){
//        	if(cost.intValue() == 0 ){
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
     * @return the value of bill_buy_balance.bill_cost
     */
    public BigDecimal getBillCost() {
        return billCost;
    }

    /**
     * 
     * {@linkplain #billCost}
     * @param billCost the value for bill_buy_balance.bill_cost
     */
    public void setBillCost(BigDecimal billCost) {
        this.billCost = billCost;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_buy_balance.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_buy_balance.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     *
     * @return the value of bill_buy_balance.exclusive_cost
     */
    public BigDecimal getExclusiveCost() {
        return exclusiveCost;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     * @param exclusiveCost the value for bill_buy_balance.exclusive_cost
     */
    public void setExclusiveCost(BigDecimal exclusiveCost) {
        this.exclusiveCost = exclusiveCost;
    }

    /**
     * 
     * {@linkplain #sendQty}
     *
     * @return the value of bill_buy_balance.send_qty
     */
    public Integer getSendQty() {
        return sendQty;
    }

    /**
     * 
     * {@linkplain #sendQty}
     * @param sendQty the value for bill_buy_balance.send_qty
     */
    public void setSendQty(Integer sendQty) {
    	if(sendQty == null){
    		sendQty = new Integer(0);
    	}
        this.sendQty = sendQty;
    }

    public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    /**
     * 
     * {@linkplain #receiveQty}
     *
     * @return the value of bill_buy_balance.receive_qty
     */
    public Integer getReceiveQty() {
        return receiveQty;
    }

    /**
     * 
     * {@linkplain #receiveQty}
     * @param receiveQty the value for bill_buy_balance.receive_qty
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
     * @return the value of bill_buy_balance.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for bill_buy_balance.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_buy_balance.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_buy_balance.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public String getYearsName() {
		return yearsName;
	}

	public void setYearsName(String yearsName) {
		this.yearsName = yearsName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getOrderfromName() {
		return orderfromName;
	}

	public void setOrderfromName(String orderfromName) {
		this.orderfromName = orderfromName;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getLastMonSenNum() {
		return lastMonSenNum;
	}

	public void setLastMonSenNum(int lastMonSenNum) {
		this.lastMonSenNum = lastMonSenNum;
	}

	public BigDecimal getLastMonSenRMB() {
		return lastMonSenRMB;
	}

	public void setLastMonSenRMB(BigDecimal lastMonSenRMB) {
		this.lastMonSenRMB = lastMonSenRMB;
	}

	public int getLastMonLastRecNum() {
		return lastMonLastRecNum;
	}

	public void setLastMonLastRecNum(int lastMonLastRecNum) {
		this.lastMonLastRecNum = lastMonLastRecNum;
	}

	public BigDecimal getLastMonLastRecRMB() {
		return lastMonLastRecRMB;
	}

	public void setLastMonLastRecRMB(BigDecimal lastMonLastRecRMB) {
		this.lastMonLastRecRMB = lastMonLastRecRMB;
	}

	
}