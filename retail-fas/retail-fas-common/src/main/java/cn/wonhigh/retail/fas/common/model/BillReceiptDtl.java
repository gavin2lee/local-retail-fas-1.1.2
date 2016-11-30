package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-17 12:00:56
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
public class BillReceiptDtl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1864237706410889004L;

	/**
     * 主键(UUID)
     */
    private String id;

    /**
     * 结算单编号
     */
    private String balanceNo;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 明细序号
     */
    private Integer seqId;

    /**
     * sku编码
     */
    private String skuNo;

    /**
     * 商品编号
     */
    private String itemNo;

    /**
     * 含税单价
     */
    private BigDecimal cost;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 尺寸编号
     */
    private String sizeNo;

    /**
     * 收货数量
     */
    private Integer stockInQty;

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
     * @return the value of bill_receipt_dtl.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_receipt_dtl.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_receipt_dtl.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_receipt_dtl.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_receipt_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_receipt_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #seqId}
     *
     * @return the value of bill_receipt_dtl.seq_id
     */
    public Integer getSeqId() {
        return seqId;
    }

    /**
     * 
     * {@linkplain #seqId}
     * @param seqId the value for bill_receipt_dtl.seq_id
     */
    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_receipt_dtl.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_receipt_dtl.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_receipt_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_receipt_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_receipt_dtl.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_receipt_dtl.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_receipt_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_receipt_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of bill_receipt_dtl.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for bill_receipt_dtl.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #stockInQty}
     *
     * @return the value of bill_receipt_dtl.stock_in_qty
     */
    public Integer getStockInQty() {
        return stockInQty;
    }

    /**
     * 
     * {@linkplain #stockInQty}
     * @param stockInQty the value for bill_receipt_dtl.stock_in_qty
     */
    public void setStockInQty(Integer stockInQty) {
        this.stockInQty = stockInQty;
    }

    /**
     * 
     * {@linkplain #boxNo}
     *
     * @return the value of bill_receipt_dtl.box_no
     */
    public String getBoxNo() {
        return boxNo;
    }

    /**
     * 
     * {@linkplain #boxNo}
     * @param boxNo the value for bill_receipt_dtl.box_no
     */
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_receipt_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_receipt_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}