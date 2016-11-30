package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-22 10:50:44
 * @version 1.0.3
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class InventoryBook implements Serializable {
    /**
     * 流水号
     */
    private String id;

    /**
     * 发方机构编码
     */
    private String storeNoFrom;

    /**
     * 发放机构名称
     */
    private String storeNameFrom;

    /**
     * 收方机构编码
     */
    private String storeNoTo;

    /**
     * 收方机构名称
     */
    private String storeNameTo;

    /**
     * 发方公司编码
     */
    private String companyNoFrom;

    /**
     * 发方公司名称
     */
    private String companyNameFrom;

    /**
     * 发方订货单位编码
     */
    private String orderUnitNoFrom;

    /**
     * 发方订货单位名称
     */
    private String orderUnitNameFrom;

    /**
     * 收方公司编码
     */
    private String companyNoTo;

    /**
     * 收方公司名称
     */
    private String companyNameTo;

    /**
     * 收方订货单位编码
     */
    private String orderUnitNoTo;

    /**
     * 收方订货单位名称
     */
    private String orderUnitNameTo;

    /**
     * 机构编码
     */
    private String storeNo;

    /**
     * 机构名称
     */
    private String storeName;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 订货单位
     */
    private String orderUnitNo;

    /**
     * 订货单位名称
     */
    private String orderUnitName;

    /**
     * 商品编码
     */
    private String itemNo;

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
     * 类别编码
     */
    private String categoryNo;

    /**
     * sku码
     */
    private String skuNo;

    /**
     * 尺寸编码
     */
    private String sizeNo;

    /**
     * 尺寸分类
     */
    private String sizeKind;

    /**
     * 单据编码
     */
    private String billNo;

    /**
     * 单据状态
     */
    private Byte status;

    /**
     * 单据类型
     */
    private Integer billType;

    /**
     * 出入库标志（-1：出库 1：入库）
     */
    private Byte inOutFlag;

    /**
     * 相关单据编码
     */
    private String refBillNo;

    /**
     * 相关单据类型
     */
    private Integer refBillType;

    /**
     * 单据的业务类型
     */
    private Integer bizType;

    /**
     * 0:记非实物库存、1：记实物库存
     */
    private Byte accountType;

    /**
     * 业务发生日期
     */
    private Date billDate;

    /**
     * 含税单价
     */
    private BigDecimal cost;

    /**
     * 正品库存发生量
     */
    private Integer balanceOffset;

    /**
     * 正品库存结存数量(商品库存账Inventory结存数量)
     */
    private Integer balanceQty;

    /**
     * 厂入库在途变化量
     */
    private Integer factoryInOffset;

    /**
     * 厂入库在途总量
     */
    private Integer factoryInQty;

    /**
     * 厂入库在途差异变化量
     */
    private Integer factoryInDiffOffset;

    /**
     * 厂入库在途差异总量
     */
    private Integer factoryInDiffQty;

    /**
     * 内区入库在途变化量
     */
    private Integer transitInOffset;

    /**
     * 内区入库在途总量
     */
    private Integer transitInQty;

    /**
     * 内区出库在途变化量
     */
    private Integer transitOutOffset;

    /**
     * 内区出库在途总量
     */
    private Integer transitOutQty;

    /**
     * 内区入库差异变化量
     */
    private Integer inDiffOffset;

    /**
     * 内区入库差异总量
     */
    private Integer inDiffQty;

    /**
     * 内区出库差异变化量
     */
    private Integer outDiffOffset;

    /**
     * 内区出库差异量
     */
    private Integer outDiffQty;

    /**
     * 外区入库在途变化量
     */
    private Integer transitInAccountOffset;

    /**
     * 外区入库在途总量
     */
    private Integer transitInAccountQty;

    /**
     * 外区出库在途变化量
     */
    private Integer transitOutAccountOffset;

    /**
     * 外区出库在途总量
     */
    private Integer transitOutAccountQty;

    /**
     * 外区入库差异变化量
     */
    private Integer inDiffAccountOffset;

    /**
     * 外区入库差异总量
     */
    private Integer inDiffAccountQty;

    /**
     * 外区出库差异变化量
     */
    private Integer outDiffAccountOffset;

    /**
     * 外区出库差异量
     */
    private Integer outDiffAccountQty;

    /**
     * 冻结变化量
     */
    private Integer lockOffset;

    /**
     * 冻结数量
     */
    private Integer lockQty;

    /**
     * 预占变化量
     */
    private Integer occupiedOffset;

    /**
     * 预占数量
     */
    private Integer occupiedQty;

    /**
     * 备货变化量
     */
    private Integer backupOffset;

    /**
     * 备货数量
     */
    private Integer backupQty;

    /**
     * 客残变化量
     */
    private Integer guestBadOffset;

    /**
     * 客残数量
     */
    private Integer guestBadQty;

    /**
     * 原残变化量
     */
    private Integer originalBadOffset;

    /**
     * 原残数量
     */
    private Integer originalBadQty;

    /**
     * 次品在途发生量
     */
    private Integer badTransitOffset;

    /**
     * 次品在途量
     */
    private Integer badTransitQty;

    /**
     * 次品在途差异变化
     */
    private Integer badDiffOffset;

    /**
     * 次品在途差异
     */
    private Integer badDiffQty;

    /**
     * 退厂在途变化量（暂时不用）
     */
    private Integer returnOffset;

    /**
     * 退厂在途总量（暂时不用）
     */
    private Integer returnQty;

    /**
     * 借用在途变化量（暂时不用）
     */
    private Integer borrowOffset;

    /**
     * 借用在途总量（暂时不用）
     */
    private Integer borrowQty;

    /**
     * 记账时间
     */
    private Date createTime;

    /**
     * 时间戳
     */
    private Date createTimestamp;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 分库字段：本部+大区
     */
    private String shardingFlag;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of inventory_book.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for inventory_book.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #storeNoFrom}
     *
     * @return the value of inventory_book.store_no_from
     */
    public String getStoreNoFrom() {
        return storeNoFrom;
    }

    /**
     * 
     * {@linkplain #storeNoFrom}
     * @param storeNoFrom the value for inventory_book.store_no_from
     */
    public void setStoreNoFrom(String storeNoFrom) {
        this.storeNoFrom = storeNoFrom;
    }

    /**
     * 
     * {@linkplain #storeNameFrom}
     *
     * @return the value of inventory_book.store_name_from
     */
    public String getStoreNameFrom() {
        return storeNameFrom;
    }

    /**
     * 
     * {@linkplain #storeNameFrom}
     * @param storeNameFrom the value for inventory_book.store_name_from
     */
    public void setStoreNameFrom(String storeNameFrom) {
        this.storeNameFrom = storeNameFrom;
    }

    /**
     * 
     * {@linkplain #storeNoTo}
     *
     * @return the value of inventory_book.store_no_to
     */
    public String getStoreNoTo() {
        return storeNoTo;
    }

    /**
     * 
     * {@linkplain #storeNoTo}
     * @param storeNoTo the value for inventory_book.store_no_to
     */
    public void setStoreNoTo(String storeNoTo) {
        this.storeNoTo = storeNoTo;
    }

    /**
     * 
     * {@linkplain #storeNameTo}
     *
     * @return the value of inventory_book.store_name_to
     */
    public String getStoreNameTo() {
        return storeNameTo;
    }

    /**
     * 
     * {@linkplain #storeNameTo}
     * @param storeNameTo the value for inventory_book.store_name_to
     */
    public void setStoreNameTo(String storeNameTo) {
        this.storeNameTo = storeNameTo;
    }

    /**
     * 
     * {@linkplain #companyNoFrom}
     *
     * @return the value of inventory_book.company_no_from
     */
    public String getCompanyNoFrom() {
        return companyNoFrom;
    }

    /**
     * 
     * {@linkplain #companyNoFrom}
     * @param companyNoFrom the value for inventory_book.company_no_from
     */
    public void setCompanyNoFrom(String companyNoFrom) {
        this.companyNoFrom = companyNoFrom;
    }

    /**
     * 
     * {@linkplain #companyNameFrom}
     *
     * @return the value of inventory_book.company_name_from
     */
    public String getCompanyNameFrom() {
        return companyNameFrom;
    }

    /**
     * 
     * {@linkplain #companyNameFrom}
     * @param companyNameFrom the value for inventory_book.company_name_from
     */
    public void setCompanyNameFrom(String companyNameFrom) {
        this.companyNameFrom = companyNameFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNoFrom}
     *
     * @return the value of inventory_book.order_unit_no_from
     */
    public String getOrderUnitNoFrom() {
        return orderUnitNoFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNoFrom}
     * @param orderUnitNoFrom the value for inventory_book.order_unit_no_from
     */
    public void setOrderUnitNoFrom(String orderUnitNoFrom) {
        this.orderUnitNoFrom = orderUnitNoFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNameFrom}
     *
     * @return the value of inventory_book.order_unit_name_from
     */
    public String getOrderUnitNameFrom() {
        return orderUnitNameFrom;
    }

    /**
     * 
     * {@linkplain #orderUnitNameFrom}
     * @param orderUnitNameFrom the value for inventory_book.order_unit_name_from
     */
    public void setOrderUnitNameFrom(String orderUnitNameFrom) {
        this.orderUnitNameFrom = orderUnitNameFrom;
    }

    /**
     * 
     * {@linkplain #companyNoTo}
     *
     * @return the value of inventory_book.company_no_to
     */
    public String getCompanyNoTo() {
        return companyNoTo;
    }

    /**
     * 
     * {@linkplain #companyNoTo}
     * @param companyNoTo the value for inventory_book.company_no_to
     */
    public void setCompanyNoTo(String companyNoTo) {
        this.companyNoTo = companyNoTo;
    }

    /**
     * 
     * {@linkplain #companyNameTo}
     *
     * @return the value of inventory_book.company_name_to
     */
    public String getCompanyNameTo() {
        return companyNameTo;
    }

    /**
     * 
     * {@linkplain #companyNameTo}
     * @param companyNameTo the value for inventory_book.company_name_to
     */
    public void setCompanyNameTo(String companyNameTo) {
        this.companyNameTo = companyNameTo;
    }

    /**
     * 
     * {@linkplain #orderUnitNoTo}
     *
     * @return the value of inventory_book.order_unit_no_to
     */
    public String getOrderUnitNoTo() {
        return orderUnitNoTo;
    }

    /**
     * 
     * {@linkplain #orderUnitNoTo}
     * @param orderUnitNoTo the value for inventory_book.order_unit_no_to
     */
    public void setOrderUnitNoTo(String orderUnitNoTo) {
        this.orderUnitNoTo = orderUnitNoTo;
    }

    /**
     * 
     * {@linkplain #orderUnitNameTo}
     *
     * @return the value of inventory_book.order_unit_name_to
     */
    public String getOrderUnitNameTo() {
        return orderUnitNameTo;
    }

    /**
     * 
     * {@linkplain #orderUnitNameTo}
     * @param orderUnitNameTo the value for inventory_book.order_unit_name_to
     */
    public void setOrderUnitNameTo(String orderUnitNameTo) {
        this.orderUnitNameTo = orderUnitNameTo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of inventory_book.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for inventory_book.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #storeName}
     *
     * @return the value of inventory_book.store_name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 
     * {@linkplain #storeName}
     * @param storeName the value for inventory_book.store_name
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of inventory_book.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for inventory_book.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of inventory_book.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for inventory_book.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of inventory_book.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for inventory_book.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     *
     * @return the value of inventory_book.order_unit_name
     */
    public String getOrderUnitName() {
        return orderUnitName;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     * @param orderUnitName the value for inventory_book.order_unit_name
     */
    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of inventory_book.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for inventory_book.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of inventory_book.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for inventory_book.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of inventory_book.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for inventory_book.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of inventory_book.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for inventory_book.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of inventory_book.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for inventory_book.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of inventory_book.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for inventory_book.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of inventory_book.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for inventory_book.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of inventory_book.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for inventory_book.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of inventory_book.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for inventory_book.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of inventory_book.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for inventory_book.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     *
     * @return the value of inventory_book.size_kind
     */
    public String getSizeKind() {
        return sizeKind;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     * @param sizeKind the value for inventory_book.size_kind
     */
    public void setSizeKind(String sizeKind) {
        this.sizeKind = sizeKind;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of inventory_book.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for inventory_book.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of inventory_book.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for inventory_book.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of inventory_book.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for inventory_book.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 
     * {@linkplain #inOutFlag}
     *
     * @return the value of inventory_book.in_out_flag
     */
    public Byte getInOutFlag() {
        return inOutFlag;
    }

    /**
     * 
     * {@linkplain #inOutFlag}
     * @param inOutFlag the value for inventory_book.in_out_flag
     */
    public void setInOutFlag(Byte inOutFlag) {
        this.inOutFlag = inOutFlag;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of inventory_book.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for inventory_book.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillType}
     *
     * @return the value of inventory_book.ref_bill_type
     */
    public Integer getRefBillType() {
        return refBillType;
    }

    /**
     * 
     * {@linkplain #refBillType}
     * @param refBillType the value for inventory_book.ref_bill_type
     */
    public void setRefBillType(Integer refBillType) {
        this.refBillType = refBillType;
    }

    /**
     * 
     * {@linkplain #bizType}
     *
     * @return the value of inventory_book.biz_type
     */
    public Integer getBizType() {
        return bizType;
    }

    /**
     * 
     * {@linkplain #bizType}
     * @param bizType the value for inventory_book.biz_type
     */
    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    /**
     * 
     * {@linkplain #accountType}
     *
     * @return the value of inventory_book.account_type
     */
    public Byte getAccountType() {
        return accountType;
    }

    /**
     * 
     * {@linkplain #accountType}
     * @param accountType the value for inventory_book.account_type
     */
    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of inventory_book.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for inventory_book.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of inventory_book.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for inventory_book.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #balanceOffset}
     *
     * @return the value of inventory_book.balance_offset
     */
    public Integer getBalanceOffset() {
        return balanceOffset;
    }

    /**
     * 
     * {@linkplain #balanceOffset}
     * @param balanceOffset the value for inventory_book.balance_offset
     */
    public void setBalanceOffset(Integer balanceOffset) {
        this.balanceOffset = balanceOffset;
    }

    /**
     * 
     * {@linkplain #balanceQty}
     *
     * @return the value of inventory_book.balance_qty
     */
    public Integer getBalanceQty() {
        return balanceQty;
    }

    /**
     * 
     * {@linkplain #balanceQty}
     * @param balanceQty the value for inventory_book.balance_qty
     */
    public void setBalanceQty(Integer balanceQty) {
        this.balanceQty = balanceQty;
    }

    /**
     * 
     * {@linkplain #factoryInOffset}
     *
     * @return the value of inventory_book.factory_in_offset
     */
    public Integer getFactoryInOffset() {
        return factoryInOffset;
    }

    /**
     * 
     * {@linkplain #factoryInOffset}
     * @param factoryInOffset the value for inventory_book.factory_in_offset
     */
    public void setFactoryInOffset(Integer factoryInOffset) {
        this.factoryInOffset = factoryInOffset;
    }

    /**
     * 
     * {@linkplain #factoryInQty}
     *
     * @return the value of inventory_book.factory_in_qty
     */
    public Integer getFactoryInQty() {
        return factoryInQty;
    }

    /**
     * 
     * {@linkplain #factoryInQty}
     * @param factoryInQty the value for inventory_book.factory_in_qty
     */
    public void setFactoryInQty(Integer factoryInQty) {
        this.factoryInQty = factoryInQty;
    }

    /**
     * 
     * {@linkplain #factoryInDiffOffset}
     *
     * @return the value of inventory_book.factory_in_diff_offset
     */
    public Integer getFactoryInDiffOffset() {
        return factoryInDiffOffset;
    }

    /**
     * 
     * {@linkplain #factoryInDiffOffset}
     * @param factoryInDiffOffset the value for inventory_book.factory_in_diff_offset
     */
    public void setFactoryInDiffOffset(Integer factoryInDiffOffset) {
        this.factoryInDiffOffset = factoryInDiffOffset;
    }

    /**
     * 
     * {@linkplain #factoryInDiffQty}
     *
     * @return the value of inventory_book.factory_in_diff_qty
     */
    public Integer getFactoryInDiffQty() {
        return factoryInDiffQty;
    }

    /**
     * 
     * {@linkplain #factoryInDiffQty}
     * @param factoryInDiffQty the value for inventory_book.factory_in_diff_qty
     */
    public void setFactoryInDiffQty(Integer factoryInDiffQty) {
        this.factoryInDiffQty = factoryInDiffQty;
    }

    /**
     * 
     * {@linkplain #transitInOffset}
     *
     * @return the value of inventory_book.transit_in_offset
     */
    public Integer getTransitInOffset() {
        return transitInOffset;
    }

    /**
     * 
     * {@linkplain #transitInOffset}
     * @param transitInOffset the value for inventory_book.transit_in_offset
     */
    public void setTransitInOffset(Integer transitInOffset) {
        this.transitInOffset = transitInOffset;
    }

    /**
     * 
     * {@linkplain #transitInQty}
     *
     * @return the value of inventory_book.transit_in_qty
     */
    public Integer getTransitInQty() {
        return transitInQty;
    }

    /**
     * 
     * {@linkplain #transitInQty}
     * @param transitInQty the value for inventory_book.transit_in_qty
     */
    public void setTransitInQty(Integer transitInQty) {
        this.transitInQty = transitInQty;
    }

    /**
     * 
     * {@linkplain #transitOutOffset}
     *
     * @return the value of inventory_book.transit_out_offset
     */
    public Integer getTransitOutOffset() {
        return transitOutOffset;
    }

    /**
     * 
     * {@linkplain #transitOutOffset}
     * @param transitOutOffset the value for inventory_book.transit_out_offset
     */
    public void setTransitOutOffset(Integer transitOutOffset) {
        this.transitOutOffset = transitOutOffset;
    }

    /**
     * 
     * {@linkplain #transitOutQty}
     *
     * @return the value of inventory_book.transit_out_qty
     */
    public Integer getTransitOutQty() {
        return transitOutQty;
    }

    /**
     * 
     * {@linkplain #transitOutQty}
     * @param transitOutQty the value for inventory_book.transit_out_qty
     */
    public void setTransitOutQty(Integer transitOutQty) {
        this.transitOutQty = transitOutQty;
    }

    /**
     * 
     * {@linkplain #inDiffOffset}
     *
     * @return the value of inventory_book.in_diff_offset
     */
    public Integer getInDiffOffset() {
        return inDiffOffset;
    }

    /**
     * 
     * {@linkplain #inDiffOffset}
     * @param inDiffOffset the value for inventory_book.in_diff_offset
     */
    public void setInDiffOffset(Integer inDiffOffset) {
        this.inDiffOffset = inDiffOffset;
    }

    /**
     * 
     * {@linkplain #inDiffQty}
     *
     * @return the value of inventory_book.in_diff_qty
     */
    public Integer getInDiffQty() {
        return inDiffQty;
    }

    /**
     * 
     * {@linkplain #inDiffQty}
     * @param inDiffQty the value for inventory_book.in_diff_qty
     */
    public void setInDiffQty(Integer inDiffQty) {
        this.inDiffQty = inDiffQty;
    }

    /**
     * 
     * {@linkplain #outDiffOffset}
     *
     * @return the value of inventory_book.out_diff_offset
     */
    public Integer getOutDiffOffset() {
        return outDiffOffset;
    }

    /**
     * 
     * {@linkplain #outDiffOffset}
     * @param outDiffOffset the value for inventory_book.out_diff_offset
     */
    public void setOutDiffOffset(Integer outDiffOffset) {
        this.outDiffOffset = outDiffOffset;
    }

    /**
     * 
     * {@linkplain #outDiffQty}
     *
     * @return the value of inventory_book.out_diff_qty
     */
    public Integer getOutDiffQty() {
        return outDiffQty;
    }

    /**
     * 
     * {@linkplain #outDiffQty}
     * @param outDiffQty the value for inventory_book.out_diff_qty
     */
    public void setOutDiffQty(Integer outDiffQty) {
        this.outDiffQty = outDiffQty;
    }

    /**
     * 
     * {@linkplain #transitInAccountOffset}
     *
     * @return the value of inventory_book.transit_in_account_offset
     */
    public Integer getTransitInAccountOffset() {
        return transitInAccountOffset;
    }

    /**
     * 
     * {@linkplain #transitInAccountOffset}
     * @param transitInAccountOffset the value for inventory_book.transit_in_account_offset
     */
    public void setTransitInAccountOffset(Integer transitInAccountOffset) {
        this.transitInAccountOffset = transitInAccountOffset;
    }

    /**
     * 
     * {@linkplain #transitInAccountQty}
     *
     * @return the value of inventory_book.transit_in_account_qty
     */
    public Integer getTransitInAccountQty() {
        return transitInAccountQty;
    }

    /**
     * 
     * {@linkplain #transitInAccountQty}
     * @param transitInAccountQty the value for inventory_book.transit_in_account_qty
     */
    public void setTransitInAccountQty(Integer transitInAccountQty) {
        this.transitInAccountQty = transitInAccountQty;
    }

    /**
     * 
     * {@linkplain #transitOutAccountOffset}
     *
     * @return the value of inventory_book.transit_out_account_offset
     */
    public Integer getTransitOutAccountOffset() {
        return transitOutAccountOffset;
    }

    /**
     * 
     * {@linkplain #transitOutAccountOffset}
     * @param transitOutAccountOffset the value for inventory_book.transit_out_account_offset
     */
    public void setTransitOutAccountOffset(Integer transitOutAccountOffset) {
        this.transitOutAccountOffset = transitOutAccountOffset;
    }

    /**
     * 
     * {@linkplain #transitOutAccountQty}
     *
     * @return the value of inventory_book.transit_out_account_qty
     */
    public Integer getTransitOutAccountQty() {
        return transitOutAccountQty;
    }

    /**
     * 
     * {@linkplain #transitOutAccountQty}
     * @param transitOutAccountQty the value for inventory_book.transit_out_account_qty
     */
    public void setTransitOutAccountQty(Integer transitOutAccountQty) {
        this.transitOutAccountQty = transitOutAccountQty;
    }

    /**
     * 
     * {@linkplain #inDiffAccountOffset}
     *
     * @return the value of inventory_book.in_diff_account_offset
     */
    public Integer getInDiffAccountOffset() {
        return inDiffAccountOffset;
    }

    /**
     * 
     * {@linkplain #inDiffAccountOffset}
     * @param inDiffAccountOffset the value for inventory_book.in_diff_account_offset
     */
    public void setInDiffAccountOffset(Integer inDiffAccountOffset) {
        this.inDiffAccountOffset = inDiffAccountOffset;
    }

    /**
     * 
     * {@linkplain #inDiffAccountQty}
     *
     * @return the value of inventory_book.in_diff_account_qty
     */
    public Integer getInDiffAccountQty() {
        return inDiffAccountQty;
    }

    /**
     * 
     * {@linkplain #inDiffAccountQty}
     * @param inDiffAccountQty the value for inventory_book.in_diff_account_qty
     */
    public void setInDiffAccountQty(Integer inDiffAccountQty) {
        this.inDiffAccountQty = inDiffAccountQty;
    }

    /**
     * 
     * {@linkplain #outDiffAccountOffset}
     *
     * @return the value of inventory_book.out_diff_account_offset
     */
    public Integer getOutDiffAccountOffset() {
        return outDiffAccountOffset;
    }

    /**
     * 
     * {@linkplain #outDiffAccountOffset}
     * @param outDiffAccountOffset the value for inventory_book.out_diff_account_offset
     */
    public void setOutDiffAccountOffset(Integer outDiffAccountOffset) {
        this.outDiffAccountOffset = outDiffAccountOffset;
    }

    /**
     * 
     * {@linkplain #outDiffAccountQty}
     *
     * @return the value of inventory_book.out_diff_account_qty
     */
    public Integer getOutDiffAccountQty() {
        return outDiffAccountQty;
    }

    /**
     * 
     * {@linkplain #outDiffAccountQty}
     * @param outDiffAccountQty the value for inventory_book.out_diff_account_qty
     */
    public void setOutDiffAccountQty(Integer outDiffAccountQty) {
        this.outDiffAccountQty = outDiffAccountQty;
    }

    /**
     * 
     * {@linkplain #lockOffset}
     *
     * @return the value of inventory_book.lock_offset
     */
    public Integer getLockOffset() {
        return lockOffset;
    }

    /**
     * 
     * {@linkplain #lockOffset}
     * @param lockOffset the value for inventory_book.lock_offset
     */
    public void setLockOffset(Integer lockOffset) {
        this.lockOffset = lockOffset;
    }

    /**
     * 
     * {@linkplain #lockQty}
     *
     * @return the value of inventory_book.lock_qty
     */
    public Integer getLockQty() {
        return lockQty;
    }

    /**
     * 
     * {@linkplain #lockQty}
     * @param lockQty the value for inventory_book.lock_qty
     */
    public void setLockQty(Integer lockQty) {
        this.lockQty = lockQty;
    }

    /**
     * 
     * {@linkplain #occupiedOffset}
     *
     * @return the value of inventory_book.occupied_offset
     */
    public Integer getOccupiedOffset() {
        return occupiedOffset;
    }

    /**
     * 
     * {@linkplain #occupiedOffset}
     * @param occupiedOffset the value for inventory_book.occupied_offset
     */
    public void setOccupiedOffset(Integer occupiedOffset) {
        this.occupiedOffset = occupiedOffset;
    }

    /**
     * 
     * {@linkplain #occupiedQty}
     *
     * @return the value of inventory_book.occupied_qty
     */
    public Integer getOccupiedQty() {
        return occupiedQty;
    }

    /**
     * 
     * {@linkplain #occupiedQty}
     * @param occupiedQty the value for inventory_book.occupied_qty
     */
    public void setOccupiedQty(Integer occupiedQty) {
        this.occupiedQty = occupiedQty;
    }

    /**
     * 
     * {@linkplain #backupOffset}
     *
     * @return the value of inventory_book.backup_offset
     */
    public Integer getBackupOffset() {
        return backupOffset;
    }

    /**
     * 
     * {@linkplain #backupOffset}
     * @param backupOffset the value for inventory_book.backup_offset
     */
    public void setBackupOffset(Integer backupOffset) {
        this.backupOffset = backupOffset;
    }

    /**
     * 
     * {@linkplain #backupQty}
     *
     * @return the value of inventory_book.backup_qty
     */
    public Integer getBackupQty() {
        return backupQty;
    }

    /**
     * 
     * {@linkplain #backupQty}
     * @param backupQty the value for inventory_book.backup_qty
     */
    public void setBackupQty(Integer backupQty) {
        this.backupQty = backupQty;
    }

    /**
     * 
     * {@linkplain #guestBadOffset}
     *
     * @return the value of inventory_book.guest_bad_offset
     */
    public Integer getGuestBadOffset() {
        return guestBadOffset;
    }

    /**
     * 
     * {@linkplain #guestBadOffset}
     * @param guestBadOffset the value for inventory_book.guest_bad_offset
     */
    public void setGuestBadOffset(Integer guestBadOffset) {
        this.guestBadOffset = guestBadOffset;
    }

    /**
     * 
     * {@linkplain #guestBadQty}
     *
     * @return the value of inventory_book.guest_bad_qty
     */
    public Integer getGuestBadQty() {
        return guestBadQty;
    }

    /**
     * 
     * {@linkplain #guestBadQty}
     * @param guestBadQty the value for inventory_book.guest_bad_qty
     */
    public void setGuestBadQty(Integer guestBadQty) {
        this.guestBadQty = guestBadQty;
    }

    /**
     * 
     * {@linkplain #originalBadOffset}
     *
     * @return the value of inventory_book.original_bad_offset
     */
    public Integer getOriginalBadOffset() {
        return originalBadOffset;
    }

    /**
     * 
     * {@linkplain #originalBadOffset}
     * @param originalBadOffset the value for inventory_book.original_bad_offset
     */
    public void setOriginalBadOffset(Integer originalBadOffset) {
        this.originalBadOffset = originalBadOffset;
    }

    /**
     * 
     * {@linkplain #originalBadQty}
     *
     * @return the value of inventory_book.original_bad_qty
     */
    public Integer getOriginalBadQty() {
        return originalBadQty;
    }

    /**
     * 
     * {@linkplain #originalBadQty}
     * @param originalBadQty the value for inventory_book.original_bad_qty
     */
    public void setOriginalBadQty(Integer originalBadQty) {
        this.originalBadQty = originalBadQty;
    }

    /**
     * 
     * {@linkplain #badTransitOffset}
     *
     * @return the value of inventory_book.bad_transit_offset
     */
    public Integer getBadTransitOffset() {
        return badTransitOffset;
    }

    /**
     * 
     * {@linkplain #badTransitOffset}
     * @param badTransitOffset the value for inventory_book.bad_transit_offset
     */
    public void setBadTransitOffset(Integer badTransitOffset) {
        this.badTransitOffset = badTransitOffset;
    }

    /**
     * 
     * {@linkplain #badTransitQty}
     *
     * @return the value of inventory_book.bad_transit_qty
     */
    public Integer getBadTransitQty() {
        return badTransitQty;
    }

    /**
     * 
     * {@linkplain #badTransitQty}
     * @param badTransitQty the value for inventory_book.bad_transit_qty
     */
    public void setBadTransitQty(Integer badTransitQty) {
        this.badTransitQty = badTransitQty;
    }

    /**
     * 
     * {@linkplain #badDiffOffset}
     *
     * @return the value of inventory_book.bad_diff_offset
     */
    public Integer getBadDiffOffset() {
        return badDiffOffset;
    }

    /**
     * 
     * {@linkplain #badDiffOffset}
     * @param badDiffOffset the value for inventory_book.bad_diff_offset
     */
    public void setBadDiffOffset(Integer badDiffOffset) {
        this.badDiffOffset = badDiffOffset;
    }

    /**
     * 
     * {@linkplain #badDiffQty}
     *
     * @return the value of inventory_book.bad_diff_qty
     */
    public Integer getBadDiffQty() {
        return badDiffQty;
    }

    /**
     * 
     * {@linkplain #badDiffQty}
     * @param badDiffQty the value for inventory_book.bad_diff_qty
     */
    public void setBadDiffQty(Integer badDiffQty) {
        this.badDiffQty = badDiffQty;
    }

    /**
     * 
     * {@linkplain #returnOffset}
     *
     * @return the value of inventory_book.return_offset
     */
    public Integer getReturnOffset() {
        return returnOffset;
    }

    /**
     * 
     * {@linkplain #returnOffset}
     * @param returnOffset the value for inventory_book.return_offset
     */
    public void setReturnOffset(Integer returnOffset) {
        this.returnOffset = returnOffset;
    }

    /**
     * 
     * {@linkplain #returnQty}
     *
     * @return the value of inventory_book.return_qty
     */
    public Integer getReturnQty() {
        return returnQty;
    }

    /**
     * 
     * {@linkplain #returnQty}
     * @param returnQty the value for inventory_book.return_qty
     */
    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    /**
     * 
     * {@linkplain #borrowOffset}
     *
     * @return the value of inventory_book.borrow_offset
     */
    public Integer getBorrowOffset() {
        return borrowOffset;
    }

    /**
     * 
     * {@linkplain #borrowOffset}
     * @param borrowOffset the value for inventory_book.borrow_offset
     */
    public void setBorrowOffset(Integer borrowOffset) {
        this.borrowOffset = borrowOffset;
    }

    /**
     * 
     * {@linkplain #borrowQty}
     *
     * @return the value of inventory_book.borrow_qty
     */
    public Integer getBorrowQty() {
        return borrowQty;
    }

    /**
     * 
     * {@linkplain #borrowQty}
     * @param borrowQty the value for inventory_book.borrow_qty
     */
    public void setBorrowQty(Integer borrowQty) {
        this.borrowQty = borrowQty;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of inventory_book.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for inventory_book.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createTimestamp}
     *
     * @return the value of inventory_book.create_timestamp
     */
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * 
     * {@linkplain #createTimestamp}
     * @param createTimestamp the value for inventory_book.create_timestamp
     */
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of inventory_book.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for inventory_book.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of inventory_book.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for inventory_book.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }
}