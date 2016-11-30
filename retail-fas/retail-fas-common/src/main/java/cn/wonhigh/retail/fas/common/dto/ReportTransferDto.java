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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

public class ReportTransferDto implements Serializable{

	private static final long serialVersionUID = -3433732941104973588L;
	
	private Integer index;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 公司编码
	 */
	private String buyerNo;
	
	/**
	 * 公司名称
	 */
	private String buyerName;
	
	/**
	 * 公司编码
	 */
	private String salerNo;
	
	/**
	 * 公司名称
	 */
	private String salerName;
	
	/**
	 * 大区编码
	 */
	private String zoneNo;
	
	/**
	 * 大区名称
	 */
	private String zoneName;
	
	/**
	 * 大区编码
	 */
	private String zoneNoFrom;
	
	/**
	 * 大区名称
	 */
	private String zoneNameFrom;
	
	/**
	 * 管理城市
	 */
	private String organNo;
	
	/**
	 * 管理城市
	 */
	private String organName;
	
	/**
	 * 管理城市
	 */
	private String organNoFrom;
	
	/**
	 * 管理城市
	 */
	private String organNameFrom;
	
	/**
	 * 订货单位编号
	 */
	private String orderUnitNo;
	
	/**
	 * 订货单位
	 */
	private String orderUnitName;
	
	/**
	 * 订货单位编号
	 */
	private String orderUnitNoFrom;
	
	/**
	 * 订货单位
	 */
	private String orderUnitNameFrom;
	
	/**
	 * 商品编码
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
	 * 品牌部编码
	 */
	private String brandUnitNo;
	
	/**
	 * 品牌部名称
	 */
	private String brandUnitName;
	
	/**
	 * 大类编码
	 */
	private String categoryNo;
	
	/**
	 * 大类名称
	 */
	private String categoryName;

	/**
	 * 品牌编码
	 */
	private String brandNo;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 单价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal cost;
	
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
	 * 接收数量
	 */
    private Integer receiveQty;
    
	/**
	 * 接收金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal receiveAmount;
    
	/**
	 * 本月发出数量
	 */
    private Integer currSendQty;
    
	/**
	 * 本月发出金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal currSendAmount;
    
	/**
	 * 本月接收数量
	 */
    private Integer currReQty;
    
	/**
	 * 本月接收金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal currReAmount;
    
	/**
	 * 本月差异数量
	 */
    private Integer currDiffQty;
    
	/**
	 * 本月差异金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal currDiffAmount;
    
	/**
	 * 前月发未收数量
	 */
    private Integer preSendNoReQty;
    
	/**
	 * 前月发未收金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preSendNoReAmount;
    
	/**
	 * 前月发本月收数量
	 */
    private Integer preSendCurrReQty;
    
	/**
	 * 前月发本月收金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preSendCurrReAmount;
    
	/**
	 * 前月差异数量
	 */
    private Integer preDiffQty;

	/**
	 * 前月差异金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preDiffAmount;
    
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
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

	public Integer getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Integer receiveQty) {
		this.receiveQty = receiveQty;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Integer getCurrSendQty() {
		return currSendQty;
	}

	public void setCurrSendQty(Integer currSendQty) {
		this.currSendQty = currSendQty;
	}

	public BigDecimal getCurrSendAmount() {
		return currSendAmount;
	}

	public void setCurrSendAmount(BigDecimal currSendAmount) {
		this.currSendAmount = currSendAmount;
	}

	public Integer getCurrReQty() {
		return currReQty;
	}

	public void setCurrReQty(Integer currReQty) {
		this.currReQty = currReQty;
	}

	public BigDecimal getCurrReAmount() {
		return currReAmount;
	}

	public void setCurrReAmount(BigDecimal currReAmount) {
		this.currReAmount = currReAmount;
	}

	public Integer getCurrDiffQty() {
		return currDiffQty;
	}

	public void setCurrDiffQty(Integer currDiffQty) {
		this.currDiffQty = currDiffQty;
	}

	public BigDecimal getCurrDiffAmount() {
		return currDiffAmount;
	}

	public void setCurrDiffAmount(BigDecimal currDiffAmount) {
		this.currDiffAmount = currDiffAmount;
	}

	public Integer getPreSendNoReQty() {
		return preSendNoReQty;
	}

	public void setPreSendNoReQty(Integer preSendNoReQty) {
		this.preSendNoReQty = preSendNoReQty;
	}

	public BigDecimal getPreSendNoReAmount() {
		return preSendNoReAmount;
	}

	public void setPreSendNoReAmount(BigDecimal preSendNoReAmount) {
		this.preSendNoReAmount = preSendNoReAmount;
	}

	public Integer getPreSendCurrReQty() {
		return preSendCurrReQty;
	}

	public void setPreSendCurrReQty(Integer preSendCurrReQty) {
		this.preSendCurrReQty = preSendCurrReQty;
	}

	public BigDecimal getPreSendCurrReAmount() {
		return preSendCurrReAmount;
	}

	public void setPreSendCurrReAmount(BigDecimal preSendCurrReAmount) {
		this.preSendCurrReAmount = preSendCurrReAmount;
	}

	public Integer getPreDiffQty() {
		return preDiffQty;
	}

	public void setPreDiffQty(Integer preDiffQty) {
		this.preDiffQty = preDiffQty;
	}

	public BigDecimal getPreDiffAmount() {
		return preDiffAmount;
	}

	public void setPreDiffAmount(BigDecimal preDiffAmount) {
		this.preDiffAmount = preDiffAmount;
	}
    
    
    
}
