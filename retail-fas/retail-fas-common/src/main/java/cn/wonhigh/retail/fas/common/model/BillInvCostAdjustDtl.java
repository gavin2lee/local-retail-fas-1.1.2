package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:14
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class BillInvCostAdjustDtl extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3838302774059347187L;

	/**
     * 单据编码
     */
    private String billNo;

    /**
     * 商品系统唯一编码(系统编码,对用户不可见)
     */
    private String itemNo;
    
    /**
     * 商品款号
     */
    private String styleNo;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 商品编码(出厂时的商品编码)
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌部编码
     */
    private String brandUnitNo;

    /**
     * 尺寸分类
     */
    private String sizeKind;

    /**
     * 调整的成本
     */
    private BigDecimal adjustCost;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 期末数量
     */
    private Integer closingQty;
    
    /**
	 * 期末库存成本差异(用于页面展示)
	 */
	private BigDecimal diverAmount;
	
	/**
	 * 系统期末余额(用于页面展示)
	 */
	private BigDecimal closingAmount;
	
	/**
	 * 期末库存成本差异(用于页面展示 )
	 */
	private BigDecimal adjustAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 分库字段：本部+大区
     */
    private String zoneYyyymm;

    /**
     * 分库字段：本部+大区
     */
    private String shardingFlag;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_inv_cost_adjust_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_inv_cost_adjust_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_inv_cost_adjust_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_inv_cost_adjust_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #barcode}
     *
     * @return the value of bill_inv_cost_adjust_dtl.barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 
     * {@linkplain #barcode}
     * @param barcode the value for bill_inv_cost_adjust_dtl.barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of bill_inv_cost_adjust_dtl.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for bill_inv_cost_adjust_dtl.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_inv_cost_adjust_dtl.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_inv_cost_adjust_dtl.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_inv_cost_adjust_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_inv_cost_adjust_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     *
     * @return the value of bill_inv_cost_adjust_dtl.size_kind
     */
    public String getSizeKind() {
        return sizeKind;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     * @param sizeKind the value for bill_inv_cost_adjust_dtl.size_kind
     */
    public void setSizeKind(String sizeKind) {
        this.sizeKind = sizeKind;
    }

    /**
     * 
     * {@linkplain #adjustCost}
     *
     * @return the value of bill_inv_cost_adjust_dtl.adjust_cost
     */
    public BigDecimal getAdjustCost() {
        return adjustCost;
    }

    /**
     * 
     * {@linkplain #adjustCost}
     * @param adjustCost the value for bill_inv_cost_adjust_dtl.adjust_cost
     */
    public void setAdjustCost(BigDecimal adjustCost) {
        this.adjustCost = adjustCost;
    }

    /**
     * 
     * {@linkplain #unitCost}
     *
     * @return the value of bill_inv_cost_adjust_dtl.unit_cost
     */
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    /**
     * 
     * {@linkplain #unitCost}
     * @param unitCost the value for bill_inv_cost_adjust_dtl.unit_cost
     */
    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    /**
     * 
     * {@linkplain #closingQty}
     *
     * @return the value of bill_inv_cost_adjust_dtl.closing_qty
     */
    public Integer getClosingQty() {
        return closingQty;
    }

    /**
     * 
     * {@linkplain #closingQty}
     * @param closingQty the value for bill_inv_cost_adjust_dtl.closing_qty
     */
    public void setClosingQty(Integer closingQty) {
        this.closingQty = closingQty;
    }


    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_inv_cost_adjust_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_inv_cost_adjust_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     *
     * @return the value of bill_inv_cost_adjust_dtl.zone_yyyymm
     */
    public String getZoneYyyymm() {
        return zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     * @param zoneYyyymm the value for bill_inv_cost_adjust_dtl.zone_yyyymm
     */
    public void setZoneYyyymm(String zoneYyyymm) {
        this.zoneYyyymm = zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of bill_inv_cost_adjust_dtl.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for bill_inv_cost_adjust_dtl.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

	public BigDecimal getDiverAmount() {
		return diverAmount;
	}

	public void setDiverAmount(BigDecimal diverAmount) {
		this.diverAmount = diverAmount;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public BigDecimal getclosingAmount() {
		return closingAmount;
	}

	public void setclosingAmount(BigDecimal closingAmount) {
		this.closingAmount = closingAmount;
	}

	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
}