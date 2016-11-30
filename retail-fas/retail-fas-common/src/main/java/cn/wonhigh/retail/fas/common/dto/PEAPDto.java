/**  
*   
* 项目名称：retail-fas-common  
* 类名称：BillBalanceDto  
* 类描述：用于查询显示结算相关信息的DTO
* 创建人：wang.m 
* 创建时间：2014-10-17 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PEAPDto implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 9106543739413503288L;

	/**
	 * 备注
	 */
	private String remark ;
	
	/**
	 * 地区名称
	 */
	private String zoneName ;
	
	/**
	 * 供应商名称
	 */
	private String salerName ;
	
	/**
	 * 公司名称
	 */
	private String buyerName;
	
	/**
	 * 供应商编码
	 */
	private String salerNo;
	
	/**
	 * 公司编码
	 */
	private String buyerNo;

	/**
	 * 大类编码
	 */
	private String categoryNo;

	/**
	 * 品牌编码
	 */
	private String brandNo;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 品牌部编码
	 */
	private String brandUnitNo;
	
	/**
	 * 品牌部名称
	 */
	private String brandUnitName;
	
	/**
	 * 年月
	 */
	private String yyyyMM;
	
	/**
	 * 鞋金额
	 */
	private BigDecimal shoesAmount;

	/**
	 * 鞋数量
	 */
	private Integer shoesQty;

	/**
	 * 服金额
	 */
	private BigDecimal clothesAmount;

	/**
	 * 服数量
	 */
	private Integer clothesQty;
	
	/**
	 * 配金额
	 */
	private BigDecimal partsAmount;

	/**
	 * 配数量
	 */
	private Integer partsQty;
	
	/**
	 * 厂商额
	 */
	private BigDecimal supplierAmount;
	
	/**
	 * 地区额
	 */
	private BigDecimal zoneAmount;

	/**
	 * 中间结算额
	 */
	private BigDecimal referAmount;
	
	/**
	 * 返利额
	 */
	private BigDecimal diffAmount1;

	/**
	 * 盈亏额
	 */
	private BigDecimal diffAmount2;
	
	/**
	 * 应付数量
	 */
	private Integer allQty;
	
	/**
	 * 应付账款
	 */
	private BigDecimal allAmount;
	
	/**
	 * 未到期账款
	 */
	private BigDecimal noDueAmount;
	
	/**
	 * 0-30天到期账款
	 */
	private BigDecimal due30Amount;
	
	/**
	 * 31-60天到期账款
	 */
	private BigDecimal due60Amount;
	
	/**
	 * 61-90天到期账款
	 */
	private BigDecimal due90Amount;
	
	/**
	 * 90天以上到期账款
	 */
	private BigDecimal due91Amount;

	/**
	 * 到期日为空账款
	 */
	private BigDecimal dueNULLAmount;
	
	/**
	 * 到期日
	 */
	private Date dueDate;
	
	/**
	 * 到期周
	 */
	private String dueWeekDate;
	
	/**
	 * 到期月
	 */
	private String dueMonthDate;
	
	
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

	public String getYyyyMM() {
		return yyyyMM;
	}

	public void setYyyyMM(String yyyyMM) {
		this.yyyyMM = yyyyMM;
	}

	public BigDecimal getShoesAmount() {
		return shoesAmount;
	}

	public void setShoesAmount(BigDecimal shoesAmount) {
		this.shoesAmount = shoesAmount;
	}

	public Integer getShoesQty() {
		return shoesQty;
	}

	public void setShoesQty(Integer shoesQty) {
		this.shoesQty = shoesQty;
	}

	public BigDecimal getClothesAmount() {
		return clothesAmount;
	}

	public void setClothesAmount(BigDecimal clothesAmount) {
		this.clothesAmount = clothesAmount;
	}

	public Integer getClothesQty() {
		return clothesQty;
	}

	public void setClothesQty(Integer clothesQty) {
		this.clothesQty = clothesQty;
	}

	public BigDecimal getPartsAmount() {
		return partsAmount;
	}

	public void setPartsAmount(BigDecimal partsAmount) {
		this.partsAmount = partsAmount;
	}

	public Integer getPartsQty() {
		return partsQty;
	}

	public void setPartsQty(Integer partsQty) {
		this.partsQty = partsQty;
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

	public BigDecimal getReferAmount() {
		return referAmount;
	}

	public void setReferAmount(BigDecimal referAmount) {
		this.referAmount = referAmount;
	}

	public BigDecimal getDiffAmount1() {
		return diffAmount1;
	}

	public void setDiffAmount1(BigDecimal diffAmount1) {
		this.diffAmount1 = diffAmount1;
	}

	public BigDecimal getDiffAmount2() {
		return diffAmount2;
	}

	public void setDiffAmount2(BigDecimal diffAmount2) {
		this.diffAmount2 = diffAmount2;
	}

	public BigDecimal getDueNULLAmount() {
		return dueNULLAmount;
	}

	public void setDueNULLAmount(BigDecimal dueNULLAmount) {
		this.dueNULLAmount = dueNULLAmount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getDueWeekDate() {
		return dueWeekDate;
	}

	public void setDueWeekDate(String dueWeekDate) {
		this.dueWeekDate = dueWeekDate;
	}

	public String getDueMonthDate() {
		return dueMonthDate;
	}

	public void setDueMonthDate(String dueMonthDate) {
		this.dueMonthDate = dueMonthDate;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
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

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public BigDecimal getNoDueAmount() {
		return noDueAmount;
	}

	public void setNoDueAmount(BigDecimal noDueAmount) {
		this.noDueAmount = noDueAmount;
	}

	public BigDecimal getDue30Amount() {
		return due30Amount;
	}

	public void setDue30Amount(BigDecimal due30Amount) {
		this.due30Amount = due30Amount;
	}

	public BigDecimal getDue60Amount() {
		return due60Amount;
	}

	public void setDue60Amount(BigDecimal due60Amount) {
		this.due60Amount = due60Amount;
	}

	public BigDecimal getDue90Amount() {
		return due90Amount;
	}

	public void setDue90Amount(BigDecimal due90Amount) {
		this.due90Amount = due90Amount;
	}

	public BigDecimal getDue91Amount() {
		return due91Amount;
	}

	public void setDue91Amount(BigDecimal due91Amount) {
		this.due91Amount = due91Amount;
	}

	public Integer getAllQty() {
		return allQty;
	}

	public void setAllQty(Integer allQty) {
		this.allQty = allQty;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
