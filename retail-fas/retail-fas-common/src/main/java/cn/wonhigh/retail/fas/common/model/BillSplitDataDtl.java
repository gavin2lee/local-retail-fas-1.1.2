package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拆单过程的数据源明细model
 * 
 * @author 杨勇
 * @date 2014-8-29 上午10:50:20
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class BillSplitDataDtl implements Serializable {

	private static final long serialVersionUID = -8618718734536050591L;

	/** 单据编码 */
	private String billNo;
	
	/** 商品编码 */
	private String itemNo;
	
	private String itemCode;
	
	/** 商品名称 */
	private String itemName;

	/** 品牌编码 */
	private String brandNo;
	
	/** 品牌名称 */
	private String brandName;
	
	/** 发货数量 */
	private Integer sendOutQty;
	
	/** 大类编码 */
	private String categoryNo;

	/** 大类名称 */
	private String categoryName;
	
	/** 年份 */
	private String years;
	
	/** 季节 */
	private String season;
	
	/** 含税单价 */
	private BigDecimal supplierCost;
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getSupplierCost() {
		return supplierCost;
	}

	public void setSupplierCost(BigDecimal supplierCost) {
		this.supplierCost = supplierCost;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public Integer getSendOutQty() {
		return sendOutQty;
	}

	public void setSendOutQty(Integer sendOutQty) {
		this.sendOutQty = sendOutQty;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
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
}
