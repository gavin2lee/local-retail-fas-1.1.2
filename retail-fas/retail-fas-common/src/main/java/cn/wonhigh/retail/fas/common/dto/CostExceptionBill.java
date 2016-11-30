package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

public class CostExceptionBill implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3951982488038800291L;

	/**
	 * 流水号
	 */
	private String id;

	/**
	 * sku码
	 */
	private String skuNo;

	/**
	 * 尺码
	 */
	private String sizeNo;
	
	/**
	 * 商品编码
	 */
	private String itemNo;
	/**
	 * 商品编码
	 */
	private String itemCode;

	/**
	 * 单据编号
	 */
	private String billNo;

	/**
	 * 单据类型
	 */
	private Integer billType;

	/**
	 * 单据类型
	 */
	private String billTypeName;
	
	/**
	 * 订货单位
	 */
	private String orderUnitNo;

	/**
	 * 业务发生日期(审核日期、销售日期)
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date billDate;

	/**
	 * 数量
	 */
	private Integer qty;

	/**
	 * 成本价（单价）
	 */
	private BigDecimal cost;

	/**
	 * 1:单据金额为0或1 2:单据价格与地区价不相等
	 */
	private Integer flag;

	/** 建档人 */
	private String createUser;
	
	/** 建档时间 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date createTime;
	
	/**
	 * 
	 * {@linkplain #id}
	 *
	 * @return the value of exception_bill.id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * @param id the value for exception_bill.id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * {@linkplain #skuNo}
	 *
	 * @return the value of exception_bill.sku_no
	 */
	public String getSkuNo() {
		return skuNo;
	}

	/**
	 * 
	 * {@linkplain #skuNo}
	 * @param skuNo the value for exception_bill.sku_no
	 */
	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSizeNo() {
		return sizeNo;
	}

	public void setSizeNo(String sizeNo) {
		this.sizeNo = sizeNo;
	}

	/**
	 * 
	 * {@linkplain #itemNo}
	 *
	 * @return the value of exception_bill.item_no
	 */
	public String getItemNo() {
		return itemNo;
	}

	/**
	 * 
	 * {@linkplain #itemNo}
	 * @param itemNo the value for exception_bill.item_no
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 *
	 * @return the value of exception_bill.bill_no
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * @param billNo the value for exception_bill.bill_no
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 
	 * {@linkplain #billType}
	 *
	 * @return the value of exception_bill.bill_type
	 */
	public Integer getBillType() {
		return billType;
	}

	/**
	 * 
	 * {@linkplain #billType}
	 * @param billType the value for exception_bill.bill_type
	 */
	public void setBillType(Integer billType) {
		this.billType = billType;
		if(null!=billType){
			setBillTypeName(BillTypeEnums.getNameByNo(billType));
        }
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	/**
	 * 
	 * {@linkplain #orderUnitNo}
	 *
	 * @return the value of exception_bill.order_unit_no
	 */
	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	/**
	 * 
	 * {@linkplain #orderUnitNo}
	 * @param orderUnitNo the value for exception_bill.order_unit_no
	 */
	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	/**
	 * 
	 * {@linkplain #billDate}
	 *
	 * @return the value of exception_bill.bill_date
	 */
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * 
	 * {@linkplain #billDate}
	 * @param billDate the value for exception_bill.bill_date
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
	 * 
	 * {@linkplain #qty}
	 *
	 * @return the value of exception_bill.qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * 
	 * {@linkplain #qty}
	 * @param qty the value for exception_bill.qty
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * 
	 * {@linkplain #cost}
	 *
	 * @return the value of exception_bill.cost
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * 
	 * {@linkplain #cost}
	 * @param cost the value for exception_bill.cost
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * 
	 * {@linkplain #flag}
	 *
	 * @return the value of exception_bill.flag
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * 
	 * {@linkplain #flag}
	 * @param flag the value for exception_bill.flag
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
}
