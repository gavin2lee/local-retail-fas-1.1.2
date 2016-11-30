package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillBalanceInvoiceApplyStatusEnums;
import cn.wonhigh.retail.fas.common.exportformat.BillBalanceInvoiceApplyExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-05 15:30:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@ExportFormat(className=BillBalanceInvoiceApplyExportFormat.class)
public class BillBalanceInvoiceApply extends FasBaseModel {
	
	private static final long serialVersionUID = -7162189341303776741L;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getInvoiceApplyNo() {
		return invoiceApplyNo;
	}

	public void setInvoiceApplyNo(String invoiceApplyNo) {
		this.invoiceApplyNo = invoiceApplyNo;
	}

	private String invoiceApplyNo;
	
	private String balanceNo;

    /**
     * 发票申请单号
     */
//    private String billNo;

    /**
     * 公司编码(开票方)-卖方
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 客户编码(收票方)-买方
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 开票名称
     */
    private String name;

    /**
     * 申请开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyDate;

    /**
     * 交票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date postPayDate;

    /**
     * 税务登记号
     */
    private String taxRegistryNo;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 银行账户名
     */
    private String bankAccountName;

    /**
     * 币别
     */
    private String currency;
    
    /** 币别名称 */
    private String currencyName;

    /**
     * 开票金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal amount;
    
    /**
     * 订单金额
     */
    private BigDecimal balanceAmount;

    /**
     * 发票类型(0 = 普通发票 1 = 增值票)
     */
    private Byte invoiceType;

    /**
     * 是否预开票(2 = 是 1 = 否)
     */
    private Byte preInvoice;


    /**
     * 引出次数
     */
    private String exportNum;

    /**
     * 客户-买方地址
     */
    private String buyerAddress;

    /**
     * 客户-买方电话
     */
    private String buyerTel;

    /**
     * 邮寄地址
     */
    private String mailingAddress;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 错误信息
     */
    private String errorInfo;

	private String organNo;

    private String organName;

    private String month;
    
    /**
     * 开票信息状态，用于查询
     */
    private Integer invoiceInfoStatus;
    
    /**
     * 店铺编号
     */
    private String shopNo;
    
    /**
     * 店铺名称
     */
    private String shopName;

//    /**
//     * 建档人
//     */
//    private String createUser;
//
//    /**
//     * 建档时间
//     */
//    @JsonSerialize(using = JsonDateSerializer$19.class)  
//  	@JsonDeserialize(using = JsonDateDeserialize$19.class)
//    private Date createTime;
//
//    /**
//     * 修改人
//     */
//    private String updateUser;
//
//    /**
//     * 修改时间
//     */
//    @JsonSerialize(using = JsonDateSerializer$19.class)  
//  	@JsonDeserialize(using = JsonDateDeserialize$19.class)
//    private Date updateTime;
//
//    /**
//     * 审核人
//     */
//    private String auditor;

    public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public void setBalanceTypeStr(String balanceTypeStr) {
		this.balanceTypeStr = balanceTypeStr;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

//    /**
//     * 审核时间
//     */
//    @JsonSerialize(using = JsonDateSerializer$19.class)  
//  	@JsonDeserialize(using = JsonDateDeserialize$19.class)
//    private Date auditTime;
    
    private String currentUser;
    
    /**
     * 结算单类型
     */
    private String balanceType;
    
    /**
     * 
     */
    private String balanceTypeStr;
    
    /**
     * 发票登记申请单号
     */
    private String invoiceRegisterNo;
    
    /**
     * 单据状态
     */
    private Integer status;
    
    /**
     * 单据名称
     */
    private String statusName;
    
    /**
     * 商场编码
     */
    private String mallNo;
    
    /**
     * 商场名称
     */
    private String mallName;
    
    /**
     * 系统收入
     */
    private BigDecimal systemSalesAmount;
    
    /**
     * 开票名称
     */
    private String invoiceName;

	 /**
     * 发票是否已使用：0 未使用，1 已使用
     */
    private Byte useFlag;
    
    private String brandName;

    private String brandNo;
    
    
    /*************************************** 以下为税票模板导出使用到的属性 ***************************************************/
    /**
     * 发票类型名称
     */
    private String invoiceTypeStr;

    /**
     * 结算期间
     */
    private String balanceDate;
    
    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 规格型号
     */
    private String typeModel;
    
    /**
     * 单位
     */
    private String unit;

    /**
     * 发票到达地区期限
     */
    private String arriveTime;
    
    /**
     * 结账单领取方式
     */
    private String getWay;

    /**
     * 商场下结账单日期
     */
    private String billDate;
    
    /**
     * NNC客户名称
     */
    private String ncClientName;

    /**
     * NC客户编码
     */
    private String ncClientNo;
    
    /**
     * 大区
     */
    private String zoneName;
    
    /**
     * 数量
     */
    private Integer qty;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 税额
     */
    private BigDecimal taxAmount;
    
    /**
     * 含税金额
     */
    private BigDecimal containTaxAmount;

    /**
     * 不含税金额
     */
    private BigDecimal noTaxAmount;
    
    /**
     * 金额
     */
    private BigDecimal sendAmount;
    
    /**
     * 店铺小类
     */
    private String retailType;

    public String getCurrencyName() {
//    	for(CurrencyTypeEnums s : CurrencyTypeEnums.values()) {
//			if(getCurrency() != null && getCurrency().equals(s.getTypeNo()+"")) {
//				return s.getTypeName();
//			}
//		}
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

//	/**
//     * 
//     * {@linkplain #billNo}
//     *
//     * @return the value of bill_balance_invoice_apply.bill_no
//     */
//    public String getBillNo() {
//        return billNo;
//    }
//
//    /**
//     * 
//     * {@linkplain #billNo}
//     * @param billNo the value for bill_balance_invoice_apply.bill_no
//     */
//    public void setBillNo(String billNo) {
//        this.billNo = billNo;
//    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_balance_invoice_apply.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_balance_invoice_apply.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(salerNo));
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_balance_invoice_apply.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_balance_invoice_apply.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_balance_invoice_apply.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_balance_invoice_apply.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_balance_invoice_apply.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_balance_invoice_apply.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of bill_balance_invoice_apply.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for bill_balance_invoice_apply.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of bill_balance_invoice_apply.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for bill_balance_invoice_apply.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #postPayDate}
     *
     * @return the value of bill_balance_invoice_apply.post_pay_date
     */
    public Date getPostPayDate() {
        return postPayDate;
    }

    /**
     * 
     * {@linkplain #postPayDate}
     * @param postPayDate the value for bill_balance_invoice_apply.post_pay_date
     */
    public void setPostPayDate(Date postPayDate) {
        this.postPayDate = postPayDate;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     *
     * @return the value of bill_balance_invoice_apply.tax_registry_no
     */
    public String getTaxRegistryNo() {
        return taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #taxRegistryNo}
     * @param taxRegistryNo the value for bill_balance_invoice_apply.tax_registry_no
     */
    public void setTaxRegistryNo(String taxRegistryNo) {
        this.taxRegistryNo = taxRegistryNo;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of bill_balance_invoice_apply.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for bill_balance_invoice_apply.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     *
     * @return the value of bill_balance_invoice_apply.bank_account
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccount}
     * @param bankAccount the value for bill_balance_invoice_apply.bank_account
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     *
     * @return the value of bill_balance_invoice_apply.bank_account_name
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * 
     * {@linkplain #bankAccountName}
     * @param bankAccountName the value for bill_balance_invoice_apply.bank_account_name
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 
     * {@linkplain #currency}
     *
     * @return the value of bill_balance_invoice_apply.currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * {@linkplain #currency}
     * @param currency the value for bill_balance_invoice_apply.currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_balance_invoice_apply.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_balance_invoice_apply.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     *
     * @return the value of bill_balance_invoice_apply.invoice_type
     */
    public Byte getInvoiceType() {
        return invoiceType;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     * @param invoiceType the value for bill_balance_invoice_apply.invoice_type
     */
    public void setInvoiceType(Byte invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     *
     * @return the value of bill_balance_invoice_apply.pre_invoice
     */
    public Byte getPreInvoice() {
        return preInvoice;
    }

    /**
     * 
     * {@linkplain #preInvoice}
     * @param preInvoice the value for bill_balance_invoice_apply.pre_invoice
     */
    public void setPreInvoice(Byte preInvoice) {
        this.preInvoice = preInvoice;
    }



    /**
     * 
     * {@linkplain #exportNum}
     *
     * @return the value of bill_balance_invoice_apply.export_num
     */
    public String getExportNum() {
        return exportNum;
    }

    /**
     * 
     * {@linkplain #exportNum}
     * @param exportNum the value for bill_balance_invoice_apply.export_num
     */
    public void setExportNum(String exportNum) {
        this.exportNum = exportNum;
    }

    /**
     * 
     * {@linkplain #buyerAddress}
     *
     * @return the value of bill_balance_invoice_apply.buyer_address
     */
    public String getBuyerAddress() {
        return buyerAddress;
    }

    /**
     * 
     * {@linkplain #buyerAddress}
     * @param buyerAddress the value for bill_balance_invoice_apply.buyer_address
     */
    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    /**
     * 
     * {@linkplain #buyerTel}
     *
     * @return the value of bill_balance_invoice_apply.buyer_tel
     */
    public String getBuyerTel() {
        return buyerTel;
    }

    /**
     * 
     * {@linkplain #buyerTel}
     * @param buyerTel the value for bill_balance_invoice_apply.buyer_tel
     */
    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     *
     * @return the value of bill_balance_invoice_apply.mailing_address
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * 
     * {@linkplain #mailingAddress}
     * @param mailingAddress the value for bill_balance_invoice_apply.mailing_address
     */
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of bill_balance_invoice_apply.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for bill_balance_invoice_apply.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of bill_balance_invoice_apply.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for bill_balance_invoice_apply.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_balance_invoice_apply.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_balance_invoice_apply.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

//    /**
//     * 
//     * {@linkplain #createUser}
//     *
//     * @return the value of bill_balance_invoice_apply.create_user
//     */
//    public String getCreateUser() {
//        return createUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #createUser}
//     * @param createUser the value for bill_balance_invoice_apply.create_user
//     */
//    public void setCreateUser(String createUser) {
//        this.createUser = createUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #createTime}
//     *
//     * @return the value of bill_balance_invoice_apply.create_time
//     */
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #createTime}
//     * @param createTime the value for bill_balance_invoice_apply.create_time
//     */
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     *
//     * @return the value of bill_balance_invoice_apply.update_user
//     */
//    public String getUpdateUser() {
//        return updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     * @param updateUser the value for bill_balance_invoice_apply.update_user
//     */
//    public void setUpdateUser(String updateUser) {
//        this.updateUser = updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     *
//     * @return the value of bill_balance_invoice_apply.update_time
//     */
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     * @param updateTime the value for bill_balance_invoice_apply.update_time
//     */
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditor}
//     *
//     * @return the value of bill_balance_invoice_apply.auditor
//     */
//    public String getAuditor() {
//        return auditor;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditor}
//     * @param auditor the value for bill_balance_invoice_apply.auditor
//     */
//    public void setAuditor(String auditor) {
//        this.auditor = auditor;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditTime}
//     *
//     * @return the value of bill_balance_invoice_apply.audit_time
//     */
//    public Date getAuditTime() {
//        return auditTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditTime}
//     * @param auditTime the value for bill_balance_invoice_apply.audit_time
//     */
//    public void setAuditTime(Date auditTime) {
//        this.auditTime = auditTime;
//    }

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		if(StringUtils.isNotEmpty(balanceType)){
			this.balanceTypeStr = BalanceTypeEnums.getTypeNameByNo(Integer.parseInt(balanceType));
		}
		this.balanceType = balanceType;
	}

	public String getBalanceTypeStr() {
		return balanceTypeStr;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getInvoiceRegisterNo() {
		return invoiceRegisterNo;
	}

	public void setInvoiceRegisterNo(String invoiceRegisterNo) {
		this.invoiceRegisterNo = invoiceRegisterNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
        if(status != null){
        	setStatusName(BillBalanceInvoiceApplyStatusEnums.getTextByStatus(status));
        	super.setStatusName(BillBalanceInvoiceApplyStatusEnums.getTextByStatus(status));
        }
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMallNo() {
		return mallNo;
	}

	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public BigDecimal getSystemSalesAmount() {
		return systemSalesAmount;
	}

	public void setSystemSalesAmount(BigDecimal systemSalesAmount) {
		this.systemSalesAmount = systemSalesAmount;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	/**
     * 
     * {@linkplain #useFlag}
     *
     * @return the value of bill_balance_invoice_apply.use_flag
     */
    public Byte getUseFlag() {
        return useFlag;
    }

    /**
     * 
     * {@linkplain #useFlag}
     * @param useFlag the value for bill_balance_invoice_apply.use_flag
     */
    public void setUseFlag(Byte useFlag) {
        this.useFlag = useFlag;
    }

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

	public Integer getInvoiceInfoStatus() {
		return invoiceInfoStatus;
	}

	public void setInvoiceInfoStatus(Integer invoiceInfoStatus) {
		this.invoiceInfoStatus = invoiceInfoStatus;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
	}

	public String getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTypeModel() {
		return typeModel;
	}

	public void setTypeModel(String typeModel) {
		this.typeModel = typeModel;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getGetWay() {
		return getWay;
	}

	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getNcClientName() {
		return ncClientName;
	}

	public void setNcClientName(String ncClientName) {
		this.ncClientName = ncClientName;
	}

	public String getNcClientNo() {
		return ncClientNo;
	}

	public void setNcClientNo(String ncClientNo) {
		this.ncClientNo = ncClientNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getContainTaxAmount() {
		return containTaxAmount;
	}

	public void setContainTaxAmount(BigDecimal containTaxAmount) {
		this.containTaxAmount = containTaxAmount;
	}

	public BigDecimal getNoTaxAmount() {
		return noTaxAmount;
	}

	public void setNoTaxAmount(BigDecimal noTaxAmount) {
		this.noTaxAmount = noTaxAmount;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public String getRetailType() {
		return retailType;
	}

	public void setRetailType(String retailType) {
		this.retailType = retailType;
	}
}