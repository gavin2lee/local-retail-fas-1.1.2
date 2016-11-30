package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-26 15:40:54
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
public class BillDifferences implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6678443890693001170L;

	/**
     * UUID
     */
    private String id;

    /**
     * 单据编号,来源单据的单据编号
     */
    private String billNo;

    /**
     * 处理单号
     */
    private String handleNo;

    /**
     * 单据类型
     */
    private Integer billType;

    /**
     * 到货单单据编号
     */
    private String asnBillNo;

    /**
     * 单据状态 (0-制单,1-出货,2-收货,98-删除,99-作废,100-完结)
     */
    private Byte status;

    /**
     * 调整类型:1.数据出错,2:实物丢失
     */
    private Byte adjustmentType;

    /**
     * 制单机构
     */
    private String sysNo;

    /**
     * 发货订货单位
     */
    private String orderUnitNoFrom;

    /**
     * 发货方订货单位名称
     */
    private String orderUnitNameFrom;

    /**
     * 收货订货单位
     */
    private String orderUnitNo;

    /**
     * 收货方订货单位名称
     */
    private String orderUnitName;

    /**
     * 机构编码,调出机构
     */
    private String storeNoFrom;

    /**
     * 发货方机构名称(如果是店铺则保存店铺名称其他则保存仓库名称)
     */
    private String storeNameFrom;

    /**
     * 店铺,调出店铺
     */
    private String shopNoFrom;

    /**
     * 调入机构编码
     */
    private String storeNo;

    /**
     * 收货方机构名称(如果是店铺则保存店铺名称其他则保存仓库名称)
     */
    private String storeName;

    /**
     * 调入店铺编码
     */
    private String shopNo;

    /**
     * 供应商
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 发出时间
     */
    private Date sendDate;

    /**
     * 接受时间
     */
    private Date receiptDate;

    /**
     * 商品内码
     */
    private String itemNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 颜色编码
     */
    private String colorNo;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌编码
     */
    private String brandName;

    /**
     * sku编码
     */
    private String skuNo;

    /**
     * 尺寸编号
     */
    private String sizeNo;

    /**
     * 发出数量
     */
    private Integer sendOutQty;

    /**
     * 接收数量
     */
    private Integer stockInQty;

    /**
     * 差异数量
     */
    private Integer differencesQty;

    /**
     * 调整数量
     */
    private Integer adjustmentQty;

    /**
     * 调整方向:1.收货方,2:发货方
     */
    private Integer adjustmentDirection;

    /**
     * 含税单价
     */
    private BigDecimal cost;

    /**
     * 调整金额
     */
    private BigDecimal adjustmentCost;

    /**
     * 业务员
     */
    private String merchandiser;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 箱号
     */
    private String boxNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_differences.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_differences.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_differences.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_differences.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #handleNo}
     *
     * @return the value of bill_differences.handle_no
     */
    public String getHandleNo() {
        return handleNo;
    }

    /**
     * 
     * {@linkplain #handleNo}
     * @param handleNo the value for bill_differences.handle_no
     */
    public void setHandleNo(String handleNo) {
        this.handleNo = handleNo;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of bill_differences.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for bill_differences.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 
     * {@linkplain #asnBillNo}
     *
     * @return the value of bill_differences.asn_bill_no
     */
    public String getAsnBillNo() {
        return asnBillNo;
    }

    /**
     * 
     * {@linkplain #asnBillNo}
     * @param asnBillNo the value for bill_differences.asn_bill_no
     */
    public void setAsnBillNo(String asnBillNo) {
        this.asnBillNo = asnBillNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_differences.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_differences.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #adjustmentType}
     *
     * @return the value of bill_differences.adjustment_type
     */
    public Byte getAdjustmentType() {
        return adjustmentType;
    }

    /**
     * 
     * {@linkplain #adjustmentType}
     * @param adjustmentType the value for bill_differences.adjustment_type
     */
    public void setAdjustmentType(Byte adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of bill_differences.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for bill_differences.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNoFrom}
     *
     * @return the value of bill_differences.order_unit_no_from
     */
    public String getOrderUnitNoFrom() {
        return orderUnitNoFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNoFrom}
     * @param orderUnitNoFrom the value for bill_differences.order_unit_no_from
     */
    public void setOrderUnitNoFrom(String orderUnitNoFrom) {
        this.orderUnitNoFrom = orderUnitNoFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNameFrom}
     *
     * @return the value of bill_differences.order_unit_name_from
     */
    public String getOrderUnitNameFrom() {
        return orderUnitNameFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNameFrom}
     * @param orderUnitNameFrom the value for bill_differences.order_unit_name_from
     */
    public void setOrderUnitNameFrom(String orderUnitNameFrom) {
        this.orderUnitNameFrom = orderUnitNameFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of bill_differences.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for bill_differences.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     *
     * @return the value of bill_differences.order_unit_name
     */
    public String getOrderUnitName() {
        return orderUnitName;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     * @param orderUnitName the value for bill_differences.order_unit_name
     */
    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    /**
     * 
     * {@linkplain #storeNoFrom}
     *
     * @return the value of bill_differences.store_no_from
     */
    public String getStoreNoFrom() {
        return storeNoFrom;
    }

    /**
     * 
     * {@linkplain #storeNoFrom}
     * @param storeNoFrom the value for bill_differences.store_no_from
     */
    public void setStoreNoFrom(String storeNoFrom) {
        this.storeNoFrom = storeNoFrom;
    }

    /**
     * 
     * {@linkplain #storeNameFrom}
     *
     * @return the value of bill_differences.store_name_from
     */
    public String getStoreNameFrom() {
        return storeNameFrom;
    }

    /**
     * 
     * {@linkplain #storeNameFrom}
     * @param storeNameFrom the value for bill_differences.store_name_from
     */
    public void setStoreNameFrom(String storeNameFrom) {
        this.storeNameFrom = storeNameFrom;
    }

    /**
     * 
     * {@linkplain #shopNoFrom}
     *
     * @return the value of bill_differences.shop_no_from
     */
    public String getShopNoFrom() {
        return shopNoFrom;
    }

    /**
     * 
     * {@linkplain #shopNoFrom}
     * @param shopNoFrom the value for bill_differences.shop_no_from
     */
    public void setShopNoFrom(String shopNoFrom) {
        this.shopNoFrom = shopNoFrom;
    }

    /**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of bill_differences.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for bill_differences.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #storeName}
     *
     * @return the value of bill_differences.store_name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 
     * {@linkplain #storeName}
     * @param storeName the value for bill_differences.store_name
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_differences.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_differences.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of bill_differences.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for bill_differences.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of bill_differences.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for bill_differences.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #sendDate}
     *
     * @return the value of bill_differences.send_date
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 
     * {@linkplain #sendDate}
     * @param sendDate the value for bill_differences.send_date
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 
     * {@linkplain #receiptDate}
     *
     * @return the value of bill_differences.receipt_date
     */
    public Date getReceiptDate() {
        return receiptDate;
    }

    /**
     * 
     * {@linkplain #receiptDate}
     * @param receiptDate the value for bill_differences.receipt_date
     */
    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_differences.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_differences.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of bill_differences.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for bill_differences.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_differences.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_differences.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #colorNo}
     *
     * @return the value of bill_differences.color_no
     */
    public String getColorNo() {
        return colorNo;
    }

    /**
     * 
     * {@linkplain #colorNo}
     * @param colorNo the value for bill_differences.color_no
     */
    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    /**
     * 
     * {@linkplain #colorName}
     *
     * @return the value of bill_differences.color_name
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * 
     * {@linkplain #colorName}
     * @param colorName the value for bill_differences.color_name
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_differences.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_differences.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_differences.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_differences.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_differences.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_differences.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of bill_differences.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for bill_differences.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #sendOutQty}
     *
     * @return the value of bill_differences.send_out_qty
     */
    public Integer getSendOutQty() {
        return sendOutQty;
    }

    /**
     * 
     * {@linkplain #sendOutQty}
     * @param sendOutQty the value for bill_differences.send_out_qty
     */
    public void setSendOutQty(Integer sendOutQty) {
        this.sendOutQty = sendOutQty;
    }

    /**
     * 
     * {@linkplain #stockInQty}
     *
     * @return the value of bill_differences.stock_in_qty
     */
    public Integer getStockInQty() {
        return stockInQty;
    }

    /**
     * 
     * {@linkplain #stockInQty}
     * @param stockInQty the value for bill_differences.stock_in_qty
     */
    public void setStockInQty(Integer stockInQty) {
        this.stockInQty = stockInQty;
    }

    /**
     * 
     * {@linkplain #differencesQty}
     *
     * @return the value of bill_differences.differences_qty
     */
    public Integer getDifferencesQty() {
        return differencesQty;
    }

    /**
     * 
     * {@linkplain #differencesQty}
     * @param differencesQty the value for bill_differences.differences_qty
     */
    public void setDifferencesQty(Integer differencesQty) {
        this.differencesQty = differencesQty;
    }

    /**
     * 
     * {@linkplain #adjustmentQty}
     *
     * @return the value of bill_differences.adjustment_qty
     */
    public Integer getAdjustmentQty() {
        return adjustmentQty;
    }

    /**
     * 
     * {@linkplain #adjustmentQty}
     * @param adjustmentQty the value for bill_differences.adjustment_qty
     */
    public void setAdjustmentQty(Integer adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    /**
     * 
     * {@linkplain #adjustmentDirection}
     *
     * @return the value of bill_differences.adjustment_direction
     */
    public Integer getAdjustmentDirection() {
        return adjustmentDirection;
    }

    /**
     * 
     * {@linkplain #adjustmentDirection}
     * @param adjustmentDirection the value for bill_differences.adjustment_direction
     */
    public void setAdjustmentDirection(Integer adjustmentDirection) {
        this.adjustmentDirection = adjustmentDirection;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_differences.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_differences.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #adjustmentCost}
     *
     * @return the value of bill_differences.adjustment_cost
     */
    public BigDecimal getAdjustmentCost() {
        return adjustmentCost;
    }

    /**
     * 
     * {@linkplain #adjustmentCost}
     * @param adjustmentCost the value for bill_differences.adjustment_cost
     */
    public void setAdjustmentCost(BigDecimal adjustmentCost) {
        this.adjustmentCost = adjustmentCost;
    }

    /**
     * 
     * {@linkplain #merchandiser}
     *
     * @return the value of bill_differences.merchandiser
     */
    public String getMerchandiser() {
        return merchandiser;
    }

    /**
     * 
     * {@linkplain #merchandiser}
     * @param merchandiser the value for bill_differences.merchandiser
     */
    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_differences.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_differences.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_differences.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_differences.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of bill_differences.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for bill_differences.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of bill_differences.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for bill_differences.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #boxNo}
     *
     * @return the value of bill_differences.box_no
     */
    public String getBoxNo() {
        return boxNo;
    }

    /**
     * 
     * {@linkplain #boxNo}
     * @param boxNo the value for bill_differences.box_no
     */
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_differences.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_differences.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}