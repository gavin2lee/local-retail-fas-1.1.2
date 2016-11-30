package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillBalanceInvoiceApplyStatusEnums;
import cn.wonhigh.retail.fas.common.enums.CurrencyTypeEnums;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author ning.ly
 * @date  2015-06-02 16:17
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
public class BillBalanceInvoiceDto extends FasBaseModel {

	private static final long serialVersionUID = 6008079654456642086L;
	
	public void setBalanceType(String balanceType) {
		if(StringUtils.isNotEmpty(balanceType)){
			this.balanceTypeStr = BalanceTypeEnums.getTypeNameByNo(Integer.parseInt(balanceType));
		}
		this.balanceType = balanceType;
	}

	public void setBalanceTypeStr(String balanceType) {
		this.balanceTypeStr = balanceType;
	}

	/**
     * 开票申请单号
     */
    private String billNo;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date balanceEndDate;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 大类编号
     */
    private String categoryNo;

    /**
     * 大类
     */
    private String categoryName;

	/**
     * 管理城市编号
     */
    private String organNo;

    /**
     * 管理城市名称
     */
    private String organName;


    /**
     * 数量
     */
    private Integer qty;

    /**
     * 金额
     */
    private BigDecimal sendAmount;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 不含税金额
     */
    private BigDecimal noTaxAmount;

    /**
     * 预估成本
     */
    private BigDecimal estimatedAmount;

    /**
     * 终端收入金额
     */
    private BigDecimal posEarningAmount;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 店铺编码
     */
    private String shopNo;

	 /**
     * 店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 
	 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 合同扣率
     */
    private BigDecimal contractRate;

    /**
     * 实际扣率
     */
    private BigDecimal actualRate;
    

	 /**
     * 销售明细成本汇总
     */
    private BigDecimal costTotal;

    
    /**
     * 一级大类编码
     */
    private String cateNo;
    
    /**
     * 一级大类名称
     */
    private String cateName;
    
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

    public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}


    
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

    public String getCurrencyName() {
    	for(CurrencyTypeEnums s : CurrencyTypeEnums.values()) {
			if(getCurrency() != null && getCurrency().equals(s.getTypeNo()+"")) {
				return s.getTypeName();
			}
		}
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

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_balance_invoice_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_balance_invoice_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_balance_invoice_dtl.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_balance_invoice_dtl.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_balance_invoice_dtl.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_balance_invoice_dtl.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_balance_invoice_dtl.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_balance_invoice_dtl.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_balance_invoice_dtl.brand_Name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_balance_invoice_dtl.brand_Name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_balance_invoice_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_balance_invoice_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_balance_invoice_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_balance_invoice_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_balance_invoice_dtl.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_balance_invoice_dtl.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

	 /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_balance_invoice_dtl.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_balance_invoice_dtl.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_balance_invoice_dtl.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_balance_invoice_dtl.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_balance_invoice_dtl.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_balance_invoice_dtl.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of bill_balance_invoice_dtl.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for bill_balance_invoice_dtl.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #sendAmount}
     *
     * @return the value of bill_balance_invoice_dtl.send_amount
     */
    public BigDecimal getSendAmount() {
        return sendAmount;
    }

    /**
     * 
     * {@linkplain #sendAmount}
     * @param sendAmount the value for bill_balance_invoice_dtl.send_amount
     */
    public void setSendAmount(BigDecimal sendAmount) {
        this.sendAmount = sendAmount;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_balance_invoice_dtl.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_balance_invoice_dtl.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     *
     * @return the value of bill_balance_invoice_dtl.tax_amount
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     * @param taxAmount the value for bill_balance_invoice_dtl.tax_amount
     */
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * 
     * {@linkplain #noTaxAmount}
     *
     * @return the value of bill_balance_invoice_dtl.no_tax_amount
     */
    public BigDecimal getNoTaxAmount() {
        return noTaxAmount;
    }

    /**
     * 
     * {@linkplain #noTaxAmount}
     * @param noTaxAmount the value for bill_balance_invoice_dtl.no_tax_amount
     */
    public void setNoTaxAmount(BigDecimal noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    /**
     * 
     * {@linkplain #estimatedAmount}
     *
     * @return the value of bill_balance_invoice_dtl.estimated_amount
     */
    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    /**
     * 
     * {@linkplain #estimatedAmount}
     * @param estimatedAmount the value for bill_balance_invoice_dtl.estimated_amount
     */
    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    /**
     * 
     * {@linkplain #posEarningAmount}
     *
     * @return the value of bill_balance_invoice_dtl.pos_earning_amount
     */
    public BigDecimal getPosEarningAmount() {
        return posEarningAmount;
    }

    /**
     * 
     * {@linkplain #posEarningAmount}
     * @param posEarningAmount the value for bill_balance_invoice_dtl.pos_earning_amount
     */
    public void setPosEarningAmount(BigDecimal posEarningAmount) {
        this.posEarningAmount = posEarningAmount;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_balance_invoice_dtl.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_balance_invoice_dtl.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_balance_invoice_dtl.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_balance_invoice_dtl.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_balance_invoice_dtl.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_balance_invoice_dtl.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

	/**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of bill_balance_invoice_dtl.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for bill_balance_invoice_dtl.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #contractRate}
     *
     * @return the value of bill_balance_invoice_dtl.contract_rate
     */
    public BigDecimal getContractRate() {
        return contractRate;
    }

    /**
     * 
     * {@linkplain #contractRate}
     * @param contractRate the value for bill_balance_invoice_dtl.contract_rate
     */
    public void setContractRate(BigDecimal contractRate) {
        this.contractRate = contractRate;
    }

    /**
     * 
     * {@linkplain #actualRate}
     *
     * @return the value of bill_balance_invoice_dtl.actual_rate
     */
    public BigDecimal getActualRate() {
        return actualRate;
    }

    /**
     * 
     * {@linkplain #actualRate}
     * @param actualRate the value for bill_balance_invoice_dtl.actual_rate
     */
    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

	 /**
     * 
     * {@linkplain #costTotal}
     *
     * @return the value of bill_balance_invoice_dtl.cost_total
     */
    public BigDecimal getCostTotal() {
        return costTotal;
    }

    /**
     * 
     * {@linkplain #costTotal}
     * @param costTotal the value for bill_balance_invoice_dtl.cost_total
     */
    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_balance_invoice_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_balance_invoice_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getBalanceType() {
		return balanceType;
	}


	public String getCateNo() {
		return cateNo;
	}

	public void setCateNo(String cateNo) {
		this.cateNo = cateNo;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
}