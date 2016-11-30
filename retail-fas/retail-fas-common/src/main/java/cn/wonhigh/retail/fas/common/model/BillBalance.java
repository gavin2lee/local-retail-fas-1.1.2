package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillBalanceZoneStatusEnums;
import cn.wonhigh.retail.fas.common.enums.PeBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途
 * 
 * @author wang.m1
 * @date 2014-09-05 14:50:55
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class BillBalance implements Serializable {
	private String currencyCode;
	private String currencyName;
	private String standardCurrencyCode;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getStandardCurrencyCode() {
		return standardCurrencyCode;
	}

	public void setStandardCurrencyCode(String standardCurrencyCode) {
		this.standardCurrencyCode = standardCurrencyCode;
	}

	public String getStandardCurrencyName() {
		return standardCurrencyName;
	}

	public void setStandardCurrencyName(String standardCurrencyName) {
		this.standardCurrencyName = standardCurrencyName;
	}

	public BigDecimal getStandardAmount() {
		return standardAmount;
	}

	public void setStandardAmount(BigDecimal standardAmount) {
		this.standardAmount = standardAmount;
	}

	private String standardCurrencyName;
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal standardAmount;

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 6581753197254666678L;

	/**
	 * 主键(uuid)
	 */
	private String id;

	/**
	 * 单据编号(结算单号)
	 */
	private String billNo;

	/**
	 * 单据名称
	 */
	private String billName;

	/**
	 * 单据状态
	 */
	private Integer status;

	/**
	 * 体育单据状态
	 */
	private String peStatusName;

	/**
	 * 扩展单据状态
	 */
	private Integer extendStatus;

	/**
	 * 结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算
	 * 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)
	 */
	private Integer balanceType;

	/**
	 * 结算单买方编号
	 */
	private String buyerNo;

	/**
	 * 结算单买方名称
	 */
	private String buyerName;

	/**
	 * 结算单卖方编号
	 */
	private String salerNo;

	/**
	 * 结算单卖方名称
	 */
	private String salerName;

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
	 * 大类编码
	 */
	private String categoryNo;

	/**
	 * 大类名称
	 */
	private String categoryName;

	/**
	 * 结算开始时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date balanceDate;

	/**
	 * 结算开始时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date balanceStartDate;
	
	/**
	 * 结算结束日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date balanceEndDate;
	
	/**
	 * 结算开始时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date returnStartDate;
	
	/**
	 * 结算结束日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date returnEndDate;
	
	/**
	 * 结算开始时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date unfrozenStartDate;
	
	/**
	 * 结算结束日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date unfrozenEndDate;
	
	/**
	 * 选单结算开始时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date balanceStartDate1;
	
	/**
	 * 选单结算结束日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
	private Date balanceEndDate1;

	/**
	 * 币别
	 */
	private String currency;

	/**
	 * 发出金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outAmount;

	/**
	 * 接收金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal entryAmount;

	/**
	 * 退残金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal returnAmount;

	/**
	 * 客残金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal customReturnAmount;

	/**
	 * 其他扣项金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal deductionAmount;
	/**
	 * 其他费用
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal otherPrice;

	/**
	 * 结算金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal balanceAmount;

	/**
	 * 返利总额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal sumRebateAmount;

	/**
	 * 发票金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal invoiceAmount;

	/**
	 * 已结算金额(付款金额/收款金额)
	 */
	private BigDecimal hasBalanceAmount;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 修改时间
	 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date updateTime;

	/**
	 * 审核人
	 */
	private String auditor;

	/**
	 * 审核时间
	 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
	private Date auditTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 发出数(备用)
	 */
	private Integer outQty;

	/**
	 * 接收数(备用)
	 */
	private Integer entryQty;

	/**
	 * 退残数(备用)
	 */
	private Integer returnQty;

	/**
	 * 退残数(备用)
	 */
	private Integer customReturnQty;

	/**
	 * 扣项数(备用)
	 */
	private Integer deductionQty;

	/**
	 * 结算数(备用)
	 */
	private Integer balanceQty;

	/**
	 * 请款单号
	 */
	private String askPaymentNo;

	/**
	 * 开票申请单号
	 */
	private String invoiceApplyNo;

	/**
	 * 发票号
	 */
	private String invoiceNo;

	/**
	 * 是否自定义(1-是,0-否)
	 */
	private int isUserDefined;

	/**
	 * 票前返利金额
	 */
	private BigDecimal rebateAmount;

	/**
	 * 票后返利金额
	 */
	private BigDecimal invoiceRebateAmount;

	/**
	 * 接受方管理城市编号
	 */
	private String organNo;

	/**
	 * 接受方管理城市名称
	 */
	private String organName;

	/**
	 * 发出方管理城市编号
	 */
	private String organNoFrom;

	/**
	 * 发出方管理城市名称
	 */
	private String organNameFrom;

	/**
	 * 是否已分摊票前费用(0-否 ,1-是)
	 */
	private int isApportionRebate;

	/******************** 扩展字段START **********************/

	/**
	 * 未结算金额
	 */
	private BigDecimal noBalanceAmount;

	/**
	 * 单据状态名称
	 */
	private String statusName;

	/**
	 * 扩展单据状态名称
	 */
	private String extendStatusName;

	/**
	 * 查询条件
	 */
	private String queryCondition;

	private String brandMultiNo;

	private String categoryMultiNo;

	private boolean isBatch;

	private Integer saleOutQty;

	private String balanceTypeName;

	private Integer billType;

	private Integer bizType;
	
	private Integer unfrozenStatus;

	private String bizMultiType;

	private String errorInfo;

	private Integer billSaleBalanceCount;

	private String years;

	private BigDecimal money;

	/**
	 * 供应商编码
	 */
	private String supplierNo;

	/**
	 * 多选品牌
	 */
	private String multiBrandNo;

	/**
	 * 多选品牌名称
	 */
	private String multiBrandName;

	/**
	 * 多选大类
	 */
	private String multiCategoryNo;

	/**
	 * 多选大类名称
	 */
	private String multiCategoryName;

	/**
	 * 多品牌或者多品牌部合并标志
	 */
	private Integer mergeFlag;

	/**
	 * 多品牌部
	 */
	private String multiBrandUnitNo;

	/**
	 * 结算表名
	 */
	private String tableName;
	/**
	 * 结算单类型名称
	 */
	private String balanceName;
	/**
	 * 结算单状态
	 */
	private String balanceStatus;

	/**
	 * 扩展分类编码
	 */
	private String extendCategoryNo;

	/**
	 * 扩展分类名称
	 */
	private String extendCategoryName;

	/**
	 * 扩展分类查询条件
	 */
	private String extendCategoryCondition;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 供应商类型
	 */
	private String supplierGroupNo;

	/**
	 * 二级大类
	 */
	private String twoLevelCategoryNo;

	/**
	 * 核价范围
	 */
	private String priceRangeCondition;

	/**
	 * 系统参数公司编码
	 */
	private String systemCompanyNo;

	/**
	 * 厂商额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal supplierAmount;

	public String getPriceRangeCondition() {
		return priceRangeCondition;
	}

	public void setPriceRangeCondition(String priceRangeCondition) {
		this.priceRangeCondition = priceRangeCondition;
	}

	public String getPeStatusName() {
		return peStatusName;
	}

	public void setPeStatusName(String peStatusName) {
		this.peStatusName = peStatusName;
	}

	public BigDecimal getSupplierAmount() {
		return supplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		this.supplierAmount = supplierAmount;
	}

	public String getSystemCompanyNo() {
		return systemCompanyNo;
	}

	public void setSystemCompanyNo(String systemCompanyNo) {
		this.systemCompanyNo = systemCompanyNo;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSupplierGroupNo() {
		return supplierGroupNo;
	}

	public void setSupplierGroupNo(String supplierGroupNo) {
		this.supplierGroupNo = supplierGroupNo;
	}

	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getExtendCategoryCondition() {
		return extendCategoryCondition;
	}

	public void setExtendCategoryCondition(String extendCategoryCondition) {
		this.extendCategoryCondition = extendCategoryCondition;
	}

	public String getExtendCategoryNo() {
		return extendCategoryNo;
	}

	public void setExtendCategoryNo(String extendCategoryNo) {
		this.extendCategoryNo = extendCategoryNo;
	}

	public String getExtendCategoryName() {
		return extendCategoryName;
	}

	public void setExtendCategoryName(String extendCategoryName) {
		this.extendCategoryName = extendCategoryName;
	}

	public BigDecimal getCustomReturnAmount() {
		return customReturnAmount;
	}

	public void setCustomReturnAmount(BigDecimal customReturnAmount) {
		this.customReturnAmount = customReturnAmount;
	}

	public Integer getCustomReturnQty() {
		return customReturnQty;
	}

	public void setCustomReturnQty(Integer customReturnQty) {
		this.customReturnQty = customReturnQty;
	}

	public String getBalanceName() {
		return balanceName;
	}

	public void setBalanceName(String balanceName) {
		this.balanceName = balanceName;
	}

	public String getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getMultiBrandNo() {
		return multiBrandNo;
	}

	public void setMultiBrandNo(String multiBrandNo) {
		this.multiBrandNo = multiBrandNo;
	}

	public String getMultiBrandName() {
		return multiBrandName;
	}

	public void setMultiBrandName(String multiBrandName) {
		this.multiBrandName = multiBrandName;
	}

	public String getMultiCategoryNo() {
		return multiCategoryNo;
	}

	public void setMultiCategoryNo(String multiCategoryNo) {
		this.multiCategoryNo = multiCategoryNo;
	}

	public String getMultiCategoryName() {
		return multiCategoryName;
	}

	public void setMultiCategoryName(String multiCategoryName) {
		this.multiCategoryName = multiCategoryName;
	}

	public BigDecimal getInvoiceRebateAmount() {
		return invoiceRebateAmount;
	}

	public void setInvoiceRebateAmount(BigDecimal invoiceRebateAmount) {
		this.invoiceRebateAmount = invoiceRebateAmount;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public String getExtendStatusName() {
		return extendStatusName;
	}

	public void setExtendStatusName(String extendStatusName) {
		this.extendStatusName = extendStatusName;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getMultiBrandUnitNo() {
		return multiBrandUnitNo;
	}

	public void setMultiBrandUnitNo(String multiBrandUnitNo) {
		this.multiBrandUnitNo = multiBrandUnitNo;
	}

	public String getBizMultiType() {
		return bizMultiType;
	}

	public void setBizMultiType(String bizMultiType) {
		this.bizMultiType = bizMultiType;
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

	public String getBalanceTypeName() {
		return balanceTypeName;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	public String getCategoryMultiNo() {
		return categoryMultiNo;
	}

	public void setCategoryMultiNo(String categoryMultiNo) {
		this.categoryMultiNo = categoryMultiNo;
	}

	public Integer getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}

	public String getBrandMultiNo() {
		return brandMultiNo;
	}

	public void setBrandMultiNo(String brandMultiNo) {
		this.brandMultiNo = brandMultiNo;
	}

	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

	public Integer getSaleOutQty() {
		return saleOutQty;
	}

	public void setSaleOutQty(Integer saleOutQty) {
		this.saleOutQty = saleOutQty;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getStatusName() {
		if (status != null && balanceType == BalanceTypeEnums.AREA_WHOLESALE.getTypeNo()) {
			statusName = BillBalanceZoneStatusEnums.getTypeNameByNo(status);
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/********************** 扩展字段END *************************/

	public Integer getExtendStatus() {
		return extendStatus;
	}

	public void setExtendStatus(Integer extendStatus) {
		this.extendStatus = extendStatus;
		if (extendStatus != null) {
			setExtendStatusName(BalanceStatusEnums.getTypeNameByNo(extendStatus));
		}

	}

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @return the value of bill_balance_bak.id
	 */
	public String getId() {
		return id;
	}

	public Integer getDeductionQty() {
		return deductionQty;
	}

	public void setDeductionQty(Integer deductionQty) {
		this.deductionQty = deductionQty;
	}

	public int getIsUserDefined() {
		return isUserDefined;
	}

	public void setIsUserDefined(int isUserDefined) {
		this.isUserDefined = isUserDefined;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
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

	public BigDecimal getNoBalanceAmount() {
		return noBalanceAmount;
	}

	public void setNoBalanceAmount(BigDecimal noBalanceAmount) {
		this.noBalanceAmount = noBalanceAmount;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * 
	 * @param id
	 *            the value for bill_balance_bak.id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @return the value of bill_balance_bak.bill_no
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 
	 * {@linkplain #billNo}
	 * 
	 * @param billNo
	 *            the value for bill_balance_bak.bill_no
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 
	 * {@linkplain #billName}
	 * 
	 * @return the value of bill_balance_bak.bill_name
	 */
	public String getBillName() {
		return billName;
	}

	/**
	 * 
	 * {@linkplain #billName}
	 * 
	 * @param billName
	 *            the value for bill_balance_bak.bill_name
	 */
	public void setBillName(String billName) {
		this.billName = billName;
	}

	/**
	 * 
	 * {@linkplain #status}
	 * 
	 * @return the value of bill_balance_bak.status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * {@linkplain #status}
	 * 
	 * @param status
	 *            the value for bill_balance_bak.status
	 */
	public void setStatus(Integer status) {
		this.status = status;
		if (status != null) {
			setStatusName(BalanceStatusEnums.getTypeNameByNo(status));
			setPeStatusName(PeBalanceStatusEnums.getNameByNo(status));
		}
	}

	/**
	 * 
	 * {@linkplain #balanceType}
	 * 
	 * @return the value of bill_balance_bak.balance_type
	 */
	public Integer getBalanceType() {
		return balanceType;
	}

	/**
	 * 
	 * {@linkplain #balanceType}
	 * 
	 * @param balanceType
	 *            the value for bill_balance_bak.balance_type
	 */
	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
		if (balanceType != null) {
			this.setBalanceTypeName(BalanceTypeEnums.getTypeNameByNo(balanceType));
		}
	}

	/**
	 * 
	 * {@linkplain #buyerNo}
	 * 
	 * @return the value of bill_balance_bak.buyer_no
	 */
	public String getBuyerNo() {
		return buyerNo;
	}

	/**
	 * 
	 * {@linkplain #buyerNo}
	 * 
	 * @param buyerNo
	 *            the value for bill_balance_bak.buyer_no
	 */
	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	/**
	 * 
	 * {@linkplain #buyerName}
	 * 
	 * @return the value of bill_balance_bak.buyer_name
	 */
	public String getBuyerName() {
		return buyerName;
	}

	/**
	 * 
	 * {@linkplain #buyerName}
	 * 
	 * @param buyerName
	 *            the value for bill_balance_bak.buyer_name
	 */
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	/**
	 * 
	 * {@linkplain #salerNo}
	 * 
	 * @return the value of bill_balance_bak.saler_no
	 */
	public String getSalerNo() {
		return salerNo;
	}

	/**
	 * 
	 * {@linkplain #salerNo}
	 * 
	 * @param salerNo
	 *            the value for bill_balance_bak.saler_no
	 */
	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	/**
	 * 
	 * {@linkplain #salerName}
	 * 
	 * @return the value of bill_balance_bak.saler_name
	 */
	public String getSalerName() {
		return salerName;
	}

	/**
	 * 
	 * {@linkplain #salerName}
	 * 
	 * @param salerName
	 *            the value for bill_balance_bak.saler_name
	 */
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @return the value of bill_balance_bak.brand_no
	 */
	public String getBrandNo() {
		return brandNo;
	}

	/**
	 * 
	 * {@linkplain #brandNo}
	 * 
	 * @param brandNo
	 *            the value for bill_balance_bak.brand_no
	 */
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	/**
	 * 
	 * {@linkplain #brandName}
	 * 
	 * @return the value of bill_balance_bak.brand_name
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 
	 * {@linkplain #brandName}
	 * 
	 * @param brandName
	 *            the value for bill_balance_bak.brand_name
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 
	 * {@linkplain #balanceStartDate}
	 * 
	 * @return the value of bill_balance_bak.balance_start_date
	 */
	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	/**
	 * 
	 * {@linkplain #balanceStartDate}
	 * 
	 * @param balanceStartDate
	 *            the value for bill_balance_bak.balance_start_date
	 */
	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	/**
	 * 
	 * {@linkplain #balanceEndDate}
	 * 
	 * @return the value of bill_balance_bak.balance_end_date
	 */
	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	/**
	 * 
	 * {@linkplain #balanceEndDate}
	 * 
	 * @param balanceEndDate
	 *            the value for bill_balance_bak.balance_end_date
	 */
	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}

	/**
	 * 
	 * {@linkplain #currency}
	 * 
	 * @return the value of bill_balance_bak.currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 
	 * {@linkplain #currency}
	 * 
	 * @param currency
	 *            the value for bill_balance_bak.currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 
	 * {@linkplain #outAmount}
	 * 
	 * @return the value of bill_balance_bak.out_amount
	 */
	public BigDecimal getOutAmount() {
		return outAmount;
	}

	/**
	 * 
	 * {@linkplain #outAmount}
	 * 
	 * @param outAmount
	 *            the value for bill_balance_bak.out_amount
	 */
	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	/**
	 * 
	 * {@linkplain #entryAmount}
	 * 
	 * @return the value of bill_balance_bak.entry_amount
	 */
	public BigDecimal getEntryAmount() {
		return entryAmount;
	}

	/**
	 * 
	 * {@linkplain #entryAmount}
	 * 
	 * @param entryAmount
	 *            the value for bill_balance_bak.entry_amount
	 */
	public void setEntryAmount(BigDecimal entryAmount) {
		this.entryAmount = entryAmount;
	}

	/**
	 * 
	 * {@linkplain #returnAmount}
	 * 
	 * @return the value of bill_balance_bak.return_amount
	 */
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	/**
	 * 
	 * {@linkplain #returnAmount}
	 * 
	 * @param returnAmount
	 *            the value for bill_balance_bak.return_amount
	 */
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	/**
	 * 
	 * {@linkplain #deductionAmount}
	 * 
	 * @return the value of bill_balance_bak.deduction_amount
	 */
	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	/**
	 * 
	 * {@linkplain #deductionAmount}
	 * 
	 * @param deductionAmount
	 *            the value for bill_balance_bak.deduction_amount
	 */
	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	/**
	 * 
	 * {@linkplain #balanceAmount}
	 * 
	 * @return the value of bill_balance_bak.balance_amount
	 */
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	/**
	 * 
	 * {@linkplain #balanceAmount}
	 * 
	 * @param balanceAmount
	 *            the value for bill_balance_bak.balance_amount
	 */
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	/**
	 * 
	 * {@linkplain #invoiceAmount}
	 * 
	 * @return the value of bill_balance_bak.invoice_amount
	 */
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	/**
	 * 
	 * {@linkplain #invoiceAmount}
	 * 
	 * @param invoiceAmount
	 *            the value for bill_balance_bak.invoice_amount
	 */
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	/**
	 * 
	 * {@linkplain #hasBalanceAmount}
	 * 
	 * @return the value of bill_balance_bak.has_balance_amount
	 */
	public BigDecimal getHasBalanceAmount() {
		return hasBalanceAmount;
	}

	/**
	 * 
	 * {@linkplain #hasBalanceAmount}
	 * 
	 * @param hasBalanceAmount
	 *            the value for bill_balance_bak.has_balance_amount
	 */
	public void setHasBalanceAmount(BigDecimal hasBalanceAmount) {
		this.hasBalanceAmount = hasBalanceAmount;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @return the value of bill_balance_bak.create_user
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @param createUser
	 *            the value for bill_balance_bak.create_user
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @return the value of bill_balance_bak.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @param createTime
	 *            the value for bill_balance_bak.create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @return the value of bill_balance_bak.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @param updateUser
	 *            the value for bill_balance_bak.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @return the value of bill_balance_bak.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @param updateTime
	 *            the value for bill_balance_bak.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 
	 * {@linkplain #auditor}
	 * 
	 * @return the value of bill_balance_bak.auditor
	 */
	public String getAuditor() {
		return auditor;
	}

	/**
	 * 
	 * {@linkplain #auditor}
	 * 
	 * @param auditor
	 *            the value for bill_balance_bak.auditor
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * 
	 * {@linkplain #auditTime}
	 * 
	 * @return the value of bill_balance_bak.audit_time
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 
	 * {@linkplain #auditTime}
	 * 
	 * @param auditTime
	 *            the value for bill_balance_bak.audit_time
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @return the value of bill_balance_bak.remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @param remark
	 *            the value for bill_balance_bak.remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 
	 * {@linkplain #outQty}
	 * 
	 * @return the value of bill_balance_bak.out_qty
	 */
	public Integer getOutQty() {
		return outQty;
	}

	/**
	 * 
	 * {@linkplain #outQty}
	 * 
	 * @param outQty
	 *            the value for bill_balance_bak.out_qty
	 */
	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	/**
	 * 
	 * {@linkplain #entryQty}
	 * 
	 * @return the value of bill_balance_bak.entry_qty
	 */
	public Integer getEntryQty() {
		return entryQty;
	}

	/**
	 * 
	 * {@linkplain #entryQty}
	 * 
	 * @param entryQty
	 *            the value for bill_balance_bak.entry_qty
	 */
	public void setEntryQty(Integer entryQty) {
		this.entryQty = entryQty;
	}

	/**
	 * 
	 * {@linkplain #returnQty}
	 * 
	 * @return the value of bill_balance_bak.return_qty
	 */
	public Integer getReturnQty() {
		return returnQty;
	}

	/**
	 * 
	 * {@linkplain #returnQty}
	 * 
	 * @param returnQty
	 *            the value for bill_balance_bak.return_qty
	 */
	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	/**
	 * 
	 * {@linkplain #askPaymentNo}
	 * 
	 * @return the value of bill_balance_bak.ask_payment_no
	 */
	public String getAskPaymentNo() {
		return askPaymentNo;
	}

	/**
	 * 
	 * {@linkplain #askPaymentNo}
	 * 
	 * @param askPaymentNo
	 *            the value for bill_balance_bak.ask_payment_no
	 */
	public void setAskPaymentNo(String askPaymentNo) {
		this.askPaymentNo = askPaymentNo;
	}

	/**
	 * 
	 * {@linkplain #invoiceApplyNo}
	 * 
	 * @return the value of bill_balance_bak.invoice_apply_no
	 */
	public String getInvoiceApplyNo() {
		return invoiceApplyNo;
	}

	/**
	 * 
	 * {@linkplain #invoiceApplyNo}
	 * 
	 * @param invoiceApplyNo
	 *            the value for bill_balance_bak.invoice_apply_no
	 */
	public void setInvoiceApplyNo(String invoiceApplyNo) {
		this.invoiceApplyNo = invoiceApplyNo;
	}

	/**
	 * 
	 * {@linkplain #invoiceNo}
	 * 
	 * @return the value of bill_balance_bak.invoice_no
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * 
	 * {@linkplain #invoiceNo}
	 * 
	 * @param invoiceNo
	 *            the value for bill_balance_bak.invoice_no
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Integer getMergeFlag() {
		return mergeFlag;
	}

	public void setMergeFlag(Integer mergeFlag) {
		this.mergeFlag = mergeFlag;
	}

	public Integer getBillSaleBalanceCount() {
		return billSaleBalanceCount;
	}

	public void setBillSaleBalanceCount(Integer billSaleBalanceCount) {
		this.billSaleBalanceCount = billSaleBalanceCount;
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

	public int getIsApportionRebate() {
		return isApportionRebate;
	}

	public void setIsApportionRebate(int isApportionRebate) {
		this.isApportionRebate = isApportionRebate;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getSumRebateAmount() {
		return sumRebateAmount;
	}

	public void setSumRebateAmount(BigDecimal sumRebateAmount) {
		this.sumRebateAmount = sumRebateAmount;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public Date getBalanceStartDate1() {
		return balanceStartDate1;
	}

	public void setBalanceStartDate1(Date balanceStartDate1) {
		this.balanceStartDate1 = balanceStartDate1;
	}

	public Date getBalanceEndDate1() {
		return balanceEndDate1;
	}

	public void setBalanceEndDate1(Date balanceEndDate1) {
		this.balanceEndDate1 = balanceEndDate1;
	}

	public Date getReturnStartDate() {
		return returnStartDate;
	}

	public void setReturnStartDate(Date returnStartDate) {
		this.returnStartDate = returnStartDate;
	}

	public Date getReturnEndDate() {
		return returnEndDate;
	}

	public void setReturnEndDate(Date returnEndDate) {
		this.returnEndDate = returnEndDate;
	}

	public Date getUnfrozenStartDate() {
		return unfrozenStartDate;
	}

	public void setUnfrozenStartDate(Date unfrozenStartDate) {
		this.unfrozenStartDate = unfrozenStartDate;
	}

	public Date getUnfrozenEndDate() {
		return unfrozenEndDate;
	}

	public void setUnfrozenEndDate(Date unfrozenEndDate) {
		this.unfrozenEndDate = unfrozenEndDate;
	}

	public Integer getUnfrozenStatus() {
		return unfrozenStatus;
	}

	public void setUnfrozenStatus(Integer unfrozenStatus) {
		this.unfrozenStatus = unfrozenStatus;
	}

}