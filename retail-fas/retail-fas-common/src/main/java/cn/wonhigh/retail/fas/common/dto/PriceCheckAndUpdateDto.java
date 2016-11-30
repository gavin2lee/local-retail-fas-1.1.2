/**
 * title:PriceCheckAndUpdateDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:地区价检查更新
 * auther:user
 * date:2016-8-2 下午3:02:37
 */
package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 
 */
public class PriceCheckAndUpdateDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5331694672644145566L;
	
	/**
	 * 主键ID
	 */
	private String id;
	
	/**
	 * 单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)
	 */
	private Integer billType;
	
	private String billTypeName;
	
	/**
	 * 业务类型(0：订货 ，1：补货 ，2直接)
	 */
	private Integer bizType;

	/**
	 * 原单编号
	 */
	private String originalBillNo;
	

	/**
	 * 相关单据编号
	 */
	private String refBillNo;

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
	private Date sendDate;
	
	/**
	 * 接收日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date receiveDate;

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
     * 单据价格
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal cost;
	
	private String PathNo;
	
	/**
	 * 0、更新bill_sale_balance和bill_buy_balance 1、更新bill_buy_balance -1、更新bill_sale_balance
	 */
	private Integer updateType; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
		if (null != billType) {
			setBillTypeName(BillTypeEnums.getNameByNo(billType));
		}
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getOriginalBillNo() {
		return originalBillNo;
	}

	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getPathNo() {
		return PathNo;
	}

	public void setPathNo(String pathNo) {
		PathNo = pathNo;
	}

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}
	
}
