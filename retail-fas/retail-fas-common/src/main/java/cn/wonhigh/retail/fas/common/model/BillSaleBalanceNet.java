package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-07-25 11:17:18
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
public class BillSaleBalanceNet implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * FAS单据类型编号
     */
    private String fasBillCode;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)
     */
    private Integer billType;

    /**
     * 业务类型(0：订货 ，1：补货  ，2直接 4、跨区调货 5、差异处理)
     */
    private Integer bizType;

    /**
     * 相关单号，多个已，隔开
     */
    private String refBillNo;

    /**
     * 相关单据类型
     */
    private Integer refBillType;

    /**
     * 原单据编号
     */
    private String originalBillNo;

    /**
     * 单据状态 1、已发未收 2、已收货 3、审核 5、确认
     */
    private Byte status;

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
    private Date sendDate;

    /**
     * 接收日期
     */
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
     * 供应商编码
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
     * 商品编码
     */
    private String itemCode;

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
    private BigDecimal cost;

    /**
     * 单据价格(原单据价格)
     */
    private BigDecimal billCost;

    /**
     * 实际出库价格(费用分摊后的价格)
     */
    private BigDecimal actualCost;

    /**
     * 票前返利与其他扣项分摊金额字段
     */
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
    private BigDecimal headquarterCost;

    /**
     * 地区成本
     */
    private BigDecimal regionCost;

    /**
     * 副地区价 buy表为卖方sale表为买方
     */
    private BigDecimal regionCostSecond;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 副单位成本 buy表为卖方sale表为买方
     */
    private BigDecimal unitCostSecond;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 物料价
     */
    private BigDecimal materialPrice;

    /**
     * 厂进价
     */
    private BigDecimal factoryPrice;

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
     * 结算单类型(1，总部厂商；2总部地区)
     */
    private Byte balanceType;

    /**
     * 结算单状态（0，制单；1，确认）
     */
    private Byte balanceStatus;

    /**
     * 是否拆分单据（0，不拆分；1，拆分）
     */
    private Byte isSplit;

    /**
     * 结算路径编码
     */
    private String pathNo;

    /**
     * 收货方订货单位编号
     */
    private String orderUnitNo;

    /**
     * 收货方订货单位名称
     */
    private String orderUnitName;

    /**
     * 收货方管理城市编号
     */
    private String organNo;

    /**
     * 收货方管理城市名称
     */
    private String organName;

    /**
     * 发货方订货单位编号
     */
    private String orderUnitNoFrom;

    /**
     * 发货方订货单位名称
     */
    private String orderUnitNameFrom;

    /**
     * 发货方管理城市编号
     */
    private String organNoFrom;

    /**
     * 发货方管理城市名称
     */
    private String organNameFrom;

    /**
     * 收货方所属大区编码
     */
    private String zoneNo;

    /**
     * 收货方所属大区名称
     */
    private String zoneName;

    /**
     * 发货方所属大区编码
     */
    private String zoneNoFrom;

    /**
     * 发货方所属大区名称
     */
    private String zoneNameFrom;

    /**
     * 是否已开票(0:已生成 1:未生成)
     */
    private Byte isBillInvoice;

    /**
     * 开票申请编号
     */
    private String invoiceApplyNo;

    /**
     * 开票申请日期
     */
    private Date invoiceApplyDate;

    /**
     * 年份(指上市的年份,下拉框选择,值: 2006~2026,默认当年)
     */
    private String years;

    /**
     * 季节(下拉框选择,A:春 B:夏 C:秋 D:冬)
     */
    private String season;

    /**
     * 订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)
     */
    private String orderfrom;

    /**
     * 性别(下拉框选择,值: 男, 女, 童, 无性别)
     */
    private String gender;

    /**
     * 牌价
     */
    private BigDecimal tagPrice;

    /**
     * 全国统一牌价
     */
    private BigDecimal tagPriceNation;

    /**
     * 大区年月
     */
    private String zoneYyyymm;

    /**
     * 异常价格更新 (0:未更新 1:正常，2异常)
     */
    private Byte costChecked;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 分库字段(本部加大区)
     */
    private String shardingFlag;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 索赔编号
     */
    private String claimNo;

    /**
     * 退货编号
     */
    private String returnNo;

    /**
     * 返利金额
     */
    private BigDecimal billRebateAmount;

    /**
     * 返利后折扣
     */
    private BigDecimal billRebateDiscount;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_sale_balance_net.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_sale_balance_net.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #fasBillCode}
     *
     * @return the value of bill_sale_balance_net.fas_bill_code
     */
    public String getFasBillCode() {
        return fasBillCode;
    }

    /**
     * 
     * {@linkplain #fasBillCode}
     * @param fasBillCode the value for bill_sale_balance_net.fas_bill_code
     */
    public void setFasBillCode(String fasBillCode) {
        this.fasBillCode = fasBillCode;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_sale_balance_net.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_sale_balance_net.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of bill_sale_balance_net.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for bill_sale_balance_net.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 
     * {@linkplain #bizType}
     *
     * @return the value of bill_sale_balance_net.biz_type
     */
    public Integer getBizType() {
        return bizType;
    }

    /**
     * 
     * {@linkplain #bizType}
     * @param bizType the value for bill_sale_balance_net.biz_type
     */
    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_sale_balance_net.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_sale_balance_net.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillType}
     *
     * @return the value of bill_sale_balance_net.ref_bill_type
     */
    public Integer getRefBillType() {
        return refBillType;
    }

    /**
     * 
     * {@linkplain #refBillType}
     * @param refBillType the value for bill_sale_balance_net.ref_bill_type
     */
    public void setRefBillType(Integer refBillType) {
        this.refBillType = refBillType;
    }

    /**
     * 
     * {@linkplain #originalBillNo}
     *
     * @return the value of bill_sale_balance_net.original_bill_no
     */
    public String getOriginalBillNo() {
        return originalBillNo;
    }

    /**
     * 
     * {@linkplain #originalBillNo}
     * @param originalBillNo the value for bill_sale_balance_net.original_bill_no
     */
    public void setOriginalBillNo(String originalBillNo) {
        this.originalBillNo = originalBillNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_sale_balance_net.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_sale_balance_net.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_sale_balance_net.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_sale_balance_net.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_sale_balance_net.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_sale_balance_net.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_sale_balance_net.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_sale_balance_net.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_sale_balance_net.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_sale_balance_net.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #sendDate}
     *
     * @return the value of bill_sale_balance_net.send_date
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 
     * {@linkplain #sendDate}
     * @param sendDate the value for bill_sale_balance_net.send_date
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of bill_sale_balance_net.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     * @param receiveDate the value for bill_sale_balance_net.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #sendStoreNo}
     *
     * @return the value of bill_sale_balance_net.send_store_no
     */
    public String getSendStoreNo() {
        return sendStoreNo;
    }

    /**
     * 
     * {@linkplain #sendStoreNo}
     * @param sendStoreNo the value for bill_sale_balance_net.send_store_no
     */
    public void setSendStoreNo(String sendStoreNo) {
        this.sendStoreNo = sendStoreNo;
    }

    /**
     * 
     * {@linkplain #sendStoreName}
     *
     * @return the value of bill_sale_balance_net.send_store_name
     */
    public String getSendStoreName() {
        return sendStoreName;
    }

    /**
     * 
     * {@linkplain #sendStoreName}
     * @param sendStoreName the value for bill_sale_balance_net.send_store_name
     */
    public void setSendStoreName(String sendStoreName) {
        this.sendStoreName = sendStoreName;
    }

    /**
     * 
     * {@linkplain #receiveStoreNo}
     *
     * @return the value of bill_sale_balance_net.receive_store_no
     */
    public String getReceiveStoreNo() {
        return receiveStoreNo;
    }

    /**
     * 
     * {@linkplain #receiveStoreNo}
     * @param receiveStoreNo the value for bill_sale_balance_net.receive_store_no
     */
    public void setReceiveStoreNo(String receiveStoreNo) {
        this.receiveStoreNo = receiveStoreNo;
    }

    /**
     * 
     * {@linkplain #receiveStoreName}
     *
     * @return the value of bill_sale_balance_net.receive_store_name
     */
    public String getReceiveStoreName() {
        return receiveStoreName;
    }

    /**
     * 
     * {@linkplain #receiveStoreName}
     * @param receiveStoreName the value for bill_sale_balance_net.receive_store_name
     */
    public void setReceiveStoreName(String receiveStoreName) {
        this.receiveStoreName = receiveStoreName;
    }

    /**
     * 
     * {@linkplain #skuId}
     *
     * @return the value of bill_sale_balance_net.sku_id
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * 
     * {@linkplain #skuId}
     * @param skuId the value for bill_sale_balance_net.sku_id
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_sale_balance_net.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_sale_balance_net.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of bill_sale_balance_net.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for bill_sale_balance_net.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of bill_sale_balance_net.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for bill_sale_balance_net.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_sale_balance_net.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_sale_balance_net.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of bill_sale_balance_net.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for bill_sale_balance_net.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_sale_balance_net.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_sale_balance_net.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_sale_balance_net.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_sale_balance_net.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_sale_balance_net.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_sale_balance_net.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of bill_sale_balance_net.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for bill_sale_balance_net.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of bill_sale_balance_net.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for bill_sale_balance_net.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_sale_balance_net.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_sale_balance_net.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_sale_balance_net.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_sale_balance_net.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_sale_balance_net.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_sale_balance_net.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #billCost}
     *
     * @return the value of bill_sale_balance_net.bill_cost
     */
    public BigDecimal getBillCost() {
        return billCost;
    }

    /**
     * 
     * {@linkplain #billCost}
     * @param billCost the value for bill_sale_balance_net.bill_cost
     */
    public void setBillCost(BigDecimal billCost) {
        this.billCost = billCost;
    }

    /**
     * 
     * {@linkplain #actualCost}
     *
     * @return the value of bill_sale_balance_net.actual_cost
     */
    public BigDecimal getActualCost() {
        return actualCost;
    }

    /**
     * 
     * {@linkplain #actualCost}
     * @param actualCost the value for bill_sale_balance_net.actual_cost
     */
    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * 
     * {@linkplain #otherDeductCost}
     *
     * @return the value of bill_sale_balance_net.other_deduct_cost
     */
    public BigDecimal getOtherDeductCost() {
        return otherDeductCost;
    }

    /**
     * 
     * {@linkplain #otherDeductCost}
     * @param otherDeductCost the value for bill_sale_balance_net.other_deduct_cost
     */
    public void setOtherDeductCost(BigDecimal otherDeductCost) {
        this.otherDeductCost = otherDeductCost;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_sale_balance_net.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_sale_balance_net.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     *
     * @return the value of bill_sale_balance_net.exclusive_cost
     */
    public BigDecimal getExclusiveCost() {
        return exclusiveCost;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     * @param exclusiveCost the value for bill_sale_balance_net.exclusive_cost
     */
    public void setExclusiveCost(BigDecimal exclusiveCost) {
        this.exclusiveCost = exclusiveCost;
    }

    /**
     * 
     * {@linkplain #headquarterCost}
     *
     * @return the value of bill_sale_balance_net.headquarter_cost
     */
    public BigDecimal getHeadquarterCost() {
        return headquarterCost;
    }

    /**
     * 
     * {@linkplain #headquarterCost}
     * @param headquarterCost the value for bill_sale_balance_net.headquarter_cost
     */
    public void setHeadquarterCost(BigDecimal headquarterCost) {
        this.headquarterCost = headquarterCost;
    }

    /**
     * 
     * {@linkplain #regionCost}
     *
     * @return the value of bill_sale_balance_net.region_cost
     */
    public BigDecimal getRegionCost() {
        return regionCost;
    }

    /**
     * 
     * {@linkplain #regionCost}
     * @param regionCost the value for bill_sale_balance_net.region_cost
     */
    public void setRegionCost(BigDecimal regionCost) {
        this.regionCost = regionCost;
    }

    /**
     * 
     * {@linkplain #regionCostSecond}
     *
     * @return the value of bill_sale_balance_net.region_cost_second
     */
    public BigDecimal getRegionCostSecond() {
        return regionCostSecond;
    }

    /**
     * 
     * {@linkplain #regionCostSecond}
     * @param regionCostSecond the value for bill_sale_balance_net.region_cost_second
     */
    public void setRegionCostSecond(BigDecimal regionCostSecond) {
        this.regionCostSecond = regionCostSecond;
    }

    /**
     * 
     * {@linkplain #unitCost}
     *
     * @return the value of bill_sale_balance_net.unit_cost
     */
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    /**
     * 
     * {@linkplain #unitCost}
     * @param unitCost the value for bill_sale_balance_net.unit_cost
     */
    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    /**
     * 
     * {@linkplain #unitCostSecond}
     *
     * @return the value of bill_sale_balance_net.unit_cost_second
     */
    public BigDecimal getUnitCostSecond() {
        return unitCostSecond;
    }

    /**
     * 
     * {@linkplain #unitCostSecond}
     * @param unitCostSecond the value for bill_sale_balance_net.unit_cost_second
     */
    public void setUnitCostSecond(BigDecimal unitCostSecond) {
        this.unitCostSecond = unitCostSecond;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     *
     * @return the value of bill_sale_balance_net.purchase_price
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     * @param purchasePrice the value for bill_sale_balance_net.purchase_price
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * 
     * {@linkplain #materialPrice}
     *
     * @return the value of bill_sale_balance_net.material_price
     */
    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    /**
     * 
     * {@linkplain #materialPrice}
     * @param materialPrice the value for bill_sale_balance_net.material_price
     */
    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    /**
     * 
     * {@linkplain #factoryPrice}
     *
     * @return the value of bill_sale_balance_net.factory_price
     */
    public BigDecimal getFactoryPrice() {
        return factoryPrice;
    }

    /**
     * 
     * {@linkplain #factoryPrice}
     * @param factoryPrice the value for bill_sale_balance_net.factory_price
     */
    public void setFactoryPrice(BigDecimal factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    /**
     * 
     * {@linkplain #sendQty}
     *
     * @return the value of bill_sale_balance_net.send_qty
     */
    public Integer getSendQty() {
        return sendQty;
    }

    /**
     * 
     * {@linkplain #sendQty}
     * @param sendQty the value for bill_sale_balance_net.send_qty
     */
    public void setSendQty(Integer sendQty) {
        this.sendQty = sendQty;
    }

    /**
     * 
     * {@linkplain #receiveQty}
     *
     * @return the value of bill_sale_balance_net.receive_qty
     */
    public Integer getReceiveQty() {
        return receiveQty;
    }

    /**
     * 
     * {@linkplain #receiveQty}
     * @param receiveQty the value for bill_sale_balance_net.receive_qty
     */
    public void setReceiveQty(Integer receiveQty) {
        this.receiveQty = receiveQty;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of bill_sale_balance_net.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for bill_sale_balance_net.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_sale_balance_net.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_sale_balance_net.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_sale_balance_net.balance_type
     */
    public Byte getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_sale_balance_net.balance_type
     */
    public void setBalanceType(Byte balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     *
     * @return the value of bill_sale_balance_net.balance_status
     */
    public Byte getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     * @param balanceStatus the value for bill_sale_balance_net.balance_status
     */
    public void setBalanceStatus(Byte balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    /**
     * 
     * {@linkplain #isSplit}
     *
     * @return the value of bill_sale_balance_net.is_split
     */
    public Byte getIsSplit() {
        return isSplit;
    }

    /**
     * 
     * {@linkplain #isSplit}
     * @param isSplit the value for bill_sale_balance_net.is_split
     */
    public void setIsSplit(Byte isSplit) {
        this.isSplit = isSplit;
    }

    /**
     * 
     * {@linkplain #pathNo}
     *
     * @return the value of bill_sale_balance_net.path_no
     */
    public String getPathNo() {
        return pathNo;
    }

    /**
     * 
     * {@linkplain #pathNo}
     * @param pathNo the value for bill_sale_balance_net.path_no
     */
    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of bill_sale_balance_net.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for bill_sale_balance_net.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     *
     * @return the value of bill_sale_balance_net.order_unit_name
     */
    public String getOrderUnitName() {
        return orderUnitName;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     * @param orderUnitName the value for bill_sale_balance_net.order_unit_name
     */
    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_sale_balance_net.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_sale_balance_net.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_sale_balance_net.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_sale_balance_net.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #orderUnitNoFrom}
     *
     * @return the value of bill_sale_balance_net.order_unit_no_from
     */
    public String getOrderUnitNoFrom() {
        return orderUnitNoFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNoFrom}
     * @param orderUnitNoFrom the value for bill_sale_balance_net.order_unit_no_from
     */
    public void setOrderUnitNoFrom(String orderUnitNoFrom) {
        this.orderUnitNoFrom = orderUnitNoFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNameFrom}
     *
     * @return the value of bill_sale_balance_net.order_unit_name_from
     */
    public String getOrderUnitNameFrom() {
        return orderUnitNameFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNameFrom}
     * @param orderUnitNameFrom the value for bill_sale_balance_net.order_unit_name_from
     */
    public void setOrderUnitNameFrom(String orderUnitNameFrom) {
        this.orderUnitNameFrom = orderUnitNameFrom;
    }

    /**
     * 
     * {@linkplain #organNoFrom}
     *
     * @return the value of bill_sale_balance_net.organ_no_from
     */
    public String getOrganNoFrom() {
        return organNoFrom;
    }

    /**
     * 
     * {@linkplain #organNoFrom}
     * @param organNoFrom the value for bill_sale_balance_net.organ_no_from
     */
    public void setOrganNoFrom(String organNoFrom) {
        this.organNoFrom = organNoFrom;
    }

    /**
     * 
     * {@linkplain #organNameFrom}
     *
     * @return the value of bill_sale_balance_net.organ_name_from
     */
    public String getOrganNameFrom() {
        return organNameFrom;
    }

    /**
     * 
     * {@linkplain #organNameFrom}
     * @param organNameFrom the value for bill_sale_balance_net.organ_name_from
     */
    public void setOrganNameFrom(String organNameFrom) {
        this.organNameFrom = organNameFrom;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_sale_balance_net.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_sale_balance_net.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_sale_balance_net.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_sale_balance_net.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #zoneNoFrom}
     *
     * @return the value of bill_sale_balance_net.zone_no_from
     */
    public String getZoneNoFrom() {
        return zoneNoFrom;
    }

    /**
     * 
     * {@linkplain #zoneNoFrom}
     * @param zoneNoFrom the value for bill_sale_balance_net.zone_no_from
     */
    public void setZoneNoFrom(String zoneNoFrom) {
        this.zoneNoFrom = zoneNoFrom;
    }

    /**
     * 
     * {@linkplain #zoneNameFrom}
     *
     * @return the value of bill_sale_balance_net.zone_name_from
     */
    public String getZoneNameFrom() {
        return zoneNameFrom;
    }

    /**
     * 
     * {@linkplain #zoneNameFrom}
     * @param zoneNameFrom the value for bill_sale_balance_net.zone_name_from
     */
    public void setZoneNameFrom(String zoneNameFrom) {
        this.zoneNameFrom = zoneNameFrom;
    }

    /**
     * 
     * {@linkplain #isBillInvoice}
     *
     * @return the value of bill_sale_balance_net.is_bill_invoice
     */
    public Byte getIsBillInvoice() {
        return isBillInvoice;
    }

    /**
     * 
     * {@linkplain #isBillInvoice}
     * @param isBillInvoice the value for bill_sale_balance_net.is_bill_invoice
     */
    public void setIsBillInvoice(Byte isBillInvoice) {
        this.isBillInvoice = isBillInvoice;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     *
     * @return the value of bill_sale_balance_net.invoice_apply_no
     */
    public String getInvoiceApplyNo() {
        return invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     * @param invoiceApplyNo the value for bill_sale_balance_net.invoice_apply_no
     */
    public void setInvoiceApplyNo(String invoiceApplyNo) {
        this.invoiceApplyNo = invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of bill_sale_balance_net.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for bill_sale_balance_net.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #years}
     *
     * @return the value of bill_sale_balance_net.years
     */
    public String getYears() {
        return years;
    }

    /**
     * 
     * {@linkplain #years}
     * @param years the value for bill_sale_balance_net.years
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * 
     * {@linkplain #season}
     *
     * @return the value of bill_sale_balance_net.season
     */
    public String getSeason() {
        return season;
    }

    /**
     * 
     * {@linkplain #season}
     * @param season the value for bill_sale_balance_net.season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * 
     * {@linkplain #orderfrom}
     *
     * @return the value of bill_sale_balance_net.orderfrom
     */
    public String getOrderfrom() {
        return orderfrom;
    }

    /**
     * 
     * {@linkplain #orderfrom}
     * @param orderfrom the value for bill_sale_balance_net.orderfrom
     */
    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }

    /**
     * 
     * {@linkplain #gender}
     *
     * @return the value of bill_sale_balance_net.gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * {@linkplain #gender}
     * @param gender the value for bill_sale_balance_net.gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     *
     * @return the value of bill_sale_balance_net.tag_price
     */
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     * @param tagPrice the value for bill_sale_balance_net.tag_price
     */
    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPriceNation}
     *
     * @return the value of bill_sale_balance_net.tag_price_nation
     */
    public BigDecimal getTagPriceNation() {
        return tagPriceNation;
    }

    /**
     * 
     * {@linkplain #tagPriceNation}
     * @param tagPriceNation the value for bill_sale_balance_net.tag_price_nation
     */
    public void setTagPriceNation(BigDecimal tagPriceNation) {
        this.tagPriceNation = tagPriceNation;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     *
     * @return the value of bill_sale_balance_net.zone_yyyymm
     */
    public String getZoneYyyymm() {
        return zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     * @param zoneYyyymm the value for bill_sale_balance_net.zone_yyyymm
     */
    public void setZoneYyyymm(String zoneYyyymm) {
        this.zoneYyyymm = zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #costChecked}
     *
     * @return the value of bill_sale_balance_net.cost_checked
     */
    public Byte getCostChecked() {
        return costChecked;
    }

    /**
     * 
     * {@linkplain #costChecked}
     * @param costChecked the value for bill_sale_balance_net.cost_checked
     */
    public void setCostChecked(Byte costChecked) {
        this.costChecked = costChecked;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_sale_balance_net.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_sale_balance_net.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_sale_balance_net.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_sale_balance_net.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of bill_sale_balance_net.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for bill_sale_balance_net.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_sale_balance_net.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_sale_balance_net.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #claimNo}
     *
     * @return the value of bill_sale_balance_net.claim_no
     */
    public String getClaimNo() {
        return claimNo;
    }

    /**
     * 
     * {@linkplain #claimNo}
     * @param claimNo the value for bill_sale_balance_net.claim_no
     */
    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    /**
     * 
     * {@linkplain #returnNo}
     *
     * @return the value of bill_sale_balance_net.return_no
     */
    public String getReturnNo() {
        return returnNo;
    }

    /**
     * 
     * {@linkplain #returnNo}
     * @param returnNo the value for bill_sale_balance_net.return_no
     */
    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    /**
     * 
     * {@linkplain #billRebateAmount}
     *
     * @return the value of bill_sale_balance_net.bill_rebate_amount
     */
    public BigDecimal getBillRebateAmount() {
        return billRebateAmount;
    }

    /**
     * 
     * {@linkplain #billRebateAmount}
     * @param billRebateAmount the value for bill_sale_balance_net.bill_rebate_amount
     */
    public void setBillRebateAmount(BigDecimal billRebateAmount) {
        this.billRebateAmount = billRebateAmount;
    }

    /**
     * 
     * {@linkplain #billRebateDiscount}
     *
     * @return the value of bill_sale_balance_net.bill_rebate_discount
     */
    public BigDecimal getBillRebateDiscount() {
        return billRebateDiscount;
    }

    /**
     * 
     * {@linkplain #billRebateDiscount}
     * @param billRebateDiscount the value for bill_sale_balance_net.bill_rebate_discount
     */
    public void setBillRebateDiscount(BigDecimal billRebateDiscount) {
        this.billRebateDiscount = billRebateDiscount;
    }
}