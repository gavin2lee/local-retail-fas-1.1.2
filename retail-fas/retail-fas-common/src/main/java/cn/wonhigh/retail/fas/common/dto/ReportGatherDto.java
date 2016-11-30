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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class ReportGatherDto implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 852256698072049855L;
	
	
	/**
	 * 订货形式
	 */
	private String orderfrom;
	
	/**
	 * 订货形式
	 */
	private String orderfromName;
	
	/**
	 * 买方编码
	 */
	private String buyerNo;
	
	/**
	 * 买方名称
	 */
	private String buyerName;
	
	/**
	 * 卖方编码
	 */
	private String salerNo;
	
	/**
	 * 卖方名称
	 */
	private String salerName;
	
	/**
	 * 供应商编码
	 */
	private String supplierNo;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 品牌部编码
	 */
	private String brandUnitNo;
	
	/**
	 * 品牌部名称
	 */
	private String brandUnitName;
	
	/**
	 * 品牌编码
	 */
	private String brandNo;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 大类编码
	 */
	private String categoryNo;
	
	/**
	 * 大类名称
	 */
	private String categoryName;

	/**
	 * 发出数量
	 */
	private Integer sendQty;
	
	/**
	 * 发出金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal sendAmount;
	
	/**
	 * 原残退货数量
	 */
	private Integer returnQty;
	
	/**
	 * 原残退货金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal returnAmount;
	
	
	/**
	 * 客残退货数量
	 */
	private Integer customReturnQty;
	
	/**
	 * 客残退货金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal customReturnAmount;
	
	/**
	 * 其他扣项
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal deductionAmount;
	
	/**
	 * 应付数量
	 */
	private Integer balanceQty;
	
	/**
	 * 应付金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal balanceAmount;
	
	/**
	 * 原残发出数量(今年累计)
	 */
	private Integer totalSendQty;
	
	/**
	 * 原残发出金额(今年累计)
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalSendAmount;
	
	/**
	 * 客残退货数量(今年累计)
	 */
	private Integer totalCustomerReturnQty;
	
	/**
	 * 客残退货金额(今年累计)
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalCustomerReturnAmount;
	
	/**
	 * 退货数量(今年累计)
	 */
	private Integer totalReturnQty;
	
	/**
	 * 退货金额(今年累计)
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalReturnAmount;
	
	/**
	 * 其他扣项(今年累计)
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalDeductionAmount;
	
	/**
	 * 应付数量(今年累计)
	 */
	private Integer totalBalanceQty;
	
	/**
	 * 应付金额(今年累计)
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalBalanceAmount;
	
	/**
	 * 管理城市编号
	 */
	private String organNo;
	
	/**
	 * 管理城市名称
	 */
	private String organName;
	
	/**
	 * 大区编号
	 */
	private String zoneNo;
	
	/**
	 * 大区名称
	 */
	private String zoneName;
	
	/**
	 * 小计标识
	 */
	private String flag;
	
	/**
	 * 上月未结余数
	 */
	private Integer beforeMonthBalanceQty; 
	
	/**
	 * 上月未结余额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal beforeMonthBalanceAmount;
	
	/**
	 *  本月付款数
	 */
	private Integer currentMonthPaymentQty;
	
	/**
	 *  本月付款额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal currentMonthPaymentAmount;
	
	/**
	 *  本月进货数
	 */
	private Integer currentMonthBalanceQty; 
	
	/**
	 *  本月进货款
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal currentMonthBalanceAmount;
	
	/**
	 *  本月初余数
	 */
	private Integer currentMonthStartQty; 
	
	/**
	 *  本月初余额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal currentMonthStartAmount; 
	
	/**
	 *  本月末余数
	 */
	private Integer currentMonthEndQty; 
	
	/**
	 *  本月末余额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal currentMonthEndAmount; 
	
	/**
	 *  供应商组类型
	 */
	private String supplierGroupNo;
	
	/**
	 *  供应商组名称
	 */
	private String supplierGroupName;
	
	/**
	 * 一级大类编码
	 */
	private String oneLevelCategoryNo;
	
	/**
	 * 一级大类
	 */
	private String oneLevelCategoryName;
	
	/**
	 * 二级大类编码
	 */
	private String twoLevelCategoryNo;
	
	/**
	 * 二级大类
	 */ 
	private String twoLevelCategoryName;
	
	/**
	 * 发出日期
	 */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
	private Date sendDateStart;
	
	/**
	 * 结束日期
	 */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
	private Date sendDateEnd;
	
	public Date getSendDateStart() {
		return sendDateStart;
	}

	public void setSendDateStart(Date sendDateStart) {
		this.sendDateStart = sendDateStart;
	}

	public Date getSendDateEnd() {
		return sendDateEnd;
	}

	public void setSendDateEnd(Date sendDateEnd) {
		this.sendDateEnd = sendDateEnd;
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

	public String getSupplierGroupNo() {
		return supplierGroupNo;
	}

	public void setSupplierGroupNo(String supplierGroupNo) {
		this.supplierGroupNo = supplierGroupNo;
	}

	public String getSupplierGroupName() {
		return supplierGroupName;
	}

	public void setSupplierGroupName(String supplierGroupName) {
		this.supplierGroupName = supplierGroupName;
	}

	public Integer getBeforeMonthBalanceQty() {
		return beforeMonthBalanceQty;
	}

	public void setBeforeMonthBalanceQty(Integer beforeMonthBalanceQty) {
		this.beforeMonthBalanceQty = beforeMonthBalanceQty;
	}

	public BigDecimal getBeforeMonthBalanceAmount() {
		return beforeMonthBalanceAmount;
	}

	public void setBeforeMonthBalanceAmount(BigDecimal beforeMonthBalanceAmount) {
		this.beforeMonthBalanceAmount = beforeMonthBalanceAmount;
	}

	public Integer getCurrentMonthPaymentQty() {
		return currentMonthPaymentQty;
	}

	public void setCurrentMonthPaymentQty(Integer currentMonthPaymentQty) {
		this.currentMonthPaymentQty = currentMonthPaymentQty;
	}

	public BigDecimal getCurrentMonthPaymentAmount() {
		return currentMonthPaymentAmount;
	}

	public void setCurrentMonthPaymentAmount(BigDecimal currentMonthPaymentAmount) {
		this.currentMonthPaymentAmount = currentMonthPaymentAmount;
	}

	public Integer getCurrentMonthBalanceQty() {
		return currentMonthBalanceQty;
	}

	public void setCurrentMonthBalanceQty(Integer currentMonthBalanceQty) {
		this.currentMonthBalanceQty = currentMonthBalanceQty;
	}

	public BigDecimal getCurrentMonthBalanceAmount() {
		return currentMonthBalanceAmount;
	}

	public void setCurrentMonthBalanceAmount(BigDecimal currentMonthBalanceAmount) {
		this.currentMonthBalanceAmount = currentMonthBalanceAmount;
	}

	public Integer getCurrentMonthStartQty() {
		return currentMonthStartQty;
	}

	public void setCurrentMonthStartQty(Integer currentMonthStartQty) {
		this.currentMonthStartQty = currentMonthStartQty;
	}

	public BigDecimal getCurrentMonthStartAmount() {
		return currentMonthStartAmount;
	}

	public void setCurrentMonthStartAmount(BigDecimal currentMonthStartAmount) {
		this.currentMonthStartAmount = currentMonthStartAmount;
	}

	public Integer getCurrentMonthEndQty() {
		return currentMonthEndQty;
	}

	public void setCurrentMonthEndQty(Integer currentMonthEndQty) {
		this.currentMonthEndQty = currentMonthEndQty;
	}

	public BigDecimal getCurrentMonthEndAmount() {
		return currentMonthEndAmount;
	}

	public void setCurrentMonthEndAmount(BigDecimal currentMonthEndAmount) {
		this.currentMonthEndAmount = currentMonthEndAmount;
	}

	public Integer getCustomReturnQty() {
		return customReturnQty;
	}

	public void setCustomReturnQty(Integer customReturnQty) {
		this.customReturnQty = customReturnQty;
	}

	public BigDecimal getCustomReturnAmount() {
		return customReturnAmount;
	}

	public void setCustomReturnAmount(BigDecimal customReturnAmount) {
		this.customReturnAmount = customReturnAmount;
	}

	public Integer getTotalCustomerReturnQty() {
		return totalCustomerReturnQty;
	}

	public void setTotalCustomerReturnQty(Integer totalCustomerReturnQty) {
		this.totalCustomerReturnQty = totalCustomerReturnQty;
	}

	public BigDecimal getTotalCustomerReturnAmount() {
		return totalCustomerReturnAmount;
	}

	public void setTotalCustomerReturnAmount(BigDecimal totalCustomerReturnAmount) {
		this.totalCustomerReturnAmount = totalCustomerReturnAmount;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


	public Integer getTotalSendQty() {
		return totalSendQty;
	}

	public void setTotalSendQty(Integer totalSendQty) {
		this.totalSendQty = totalSendQty;
	}

	public BigDecimal getTotalSendAmount() {
		return totalSendAmount;
	}

	public void setTotalSendAmount(BigDecimal totalSendAmount) {
		this.totalSendAmount = totalSendAmount;
	}

	public Integer getTotalReturnQty() {
		return totalReturnQty;
	}

	public void setTotalReturnQty(Integer totalReturnQty) {
		this.totalReturnQty = totalReturnQty;
	}

	public BigDecimal getTotalReturnAmount() {
		return totalReturnAmount;
	}

	public void setTotalReturnAmount(BigDecimal totalReturnAmount) {
		this.totalReturnAmount = totalReturnAmount;
	}

	public BigDecimal getTotalDeductionAmount() {
		return totalDeductionAmount;
	}

	public void setTotalDeductionAmount(BigDecimal totalDeductionAmount) {
		this.totalDeductionAmount = totalDeductionAmount;
	}

	public Integer getTotalBalanceQty() {
		return totalBalanceQty;
	}

	public void setTotalBalanceQty(Integer totalBalanceQty) {
		this.totalBalanceQty = totalBalanceQty;
	}

	public BigDecimal getTotalBalanceAmount() {
		return totalBalanceAmount;
	}

	public void setTotalBalanceAmount(BigDecimal totalBalanceAmount) {
		this.totalBalanceAmount = totalBalanceAmount;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public Integer getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public Integer getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
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

	public String getOrderfrom() {
		return orderfrom;
	}

	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}

	public String getOrderfromName() {
		return orderfromName;
	}

	public void setOrderfromName(String orderfromName) {
		this.orderfromName = orderfromName;
	}

}
