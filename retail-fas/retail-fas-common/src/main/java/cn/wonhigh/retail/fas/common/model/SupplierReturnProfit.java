package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-19 10:35:13
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
public class SupplierReturnProfit extends FasBaseModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5762689609160896655L;

	/**
     * 主键
     */
    private String id;

    /**
     * 公司编号
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 供应商编号
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 单据编号
     */
    private String billNo;
    
    /**
     * 商品编号
     */
    private String itemNo;
    
    /**
     * 商品编码
     */
    private String itemCode;
    
    /**
     * 品牌
     */
    private String brandNo;
    
    /**
     * 发货日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date sendDate;

    /**
     * 返利日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date generateDate;

    /**
     * 返利金额
     */
    private BigDecimal amount;

    /**
     * 返利原因
     */
    private String remark;

    /**
     * 是否开票(0-已开票 1-未开票)
     */
    private Integer isInvoiced;
    
    /**
     * 开票状态
     */
    private String invoiceStatus;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date invoiceDate;

    /**
     * 是否系统生成(0-系统生成 1-手动新增 2-批量导入)
     */
    private Integer returnType;
    
    /**
     * 类型名(0-系统生成 1-手动新增 2-批量导入)
     */
    private String typeName;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of supplier_return_profit.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for supplier_return_profit.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of supplier_return_profit.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for supplier_return_profit.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of supplier_return_profit.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for supplier_return_profit.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of supplier_return_profit.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for supplier_return_profit.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of supplier_return_profit.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for supplier_return_profit.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #generateDate}
     *
     * @return the value of supplier_return_profit.generate_date
     */
    public Date getGenerateDate() {
        return generateDate;
    }

    /**
     * 
     * {@linkplain #generateDate}
     * @param generateDate the value for supplier_return_profit.generate_date
     */
    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of supplier_return_profit.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for supplier_return_profit.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of supplier_return_profit.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for supplier_return_profit.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #isInvoiced}
     *
     * @return the value of supplier_return_profit.is_invoiced
     */
    public Integer getIsInvoiced() {
        return isInvoiced;
    }

    /**
     * 
     * {@linkplain #isInvoiced}
     * @param isInvoiced the value for supplier_return_profit.is_invoiced
     */
    public void setIsInvoiced(Integer isInvoiced) {
        this.isInvoiced = isInvoiced;
        if(null != this.isInvoiced){
			if(this.isInvoiced.intValue() == 0){
				this.invoiceStatus = "已开票";
			}else if(this.isInvoiced.intValue() == 1){
				this.invoiceStatus = "未开票";
			}
		}
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of supplier_return_profit.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for supplier_return_profit.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of supplier_return_profit.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for supplier_return_profit.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

//    /**
//     * 
//     * {@linkplain #updateUser}
//     *
//     * @return the value of supplier_return_profit.update_user
//     */
//    public String getUpdateUser() {
//        return updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     * @param updateUser the value for supplier_return_profit.update_user
//     */
//    public void setUpdateUser(String updateUser) {
//        this.updateUser = updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     *
//     * @return the value of supplier_return_profit.update_time
//     */
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     * @param updateTime the value for supplier_return_profit.update_time
//     */
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    /**
     * 
     * {@linkplain #returnType}
     *
     * @return the value of supplier_return_profit.type
     */
    public Integer getReturnType() {
        return returnType;
    }

    /**
     * 
     * {@linkplain #returnTtype}
     * @param type the value for supplier_return_profit.type
     */
    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
        if(returnType != null){
        	if(returnType.intValue() == 0){
        		this.typeName = "是";
        	}else if(returnType.intValue() == 1){
        		this.typeName = "是";
        	}else{
        		this.typeName = "否";
        	}
        }
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of supplier_return_profit.item_no
     */
	public String getItemNo() {
		return itemNo;
	}

	/**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for supplier_return_profit.item_no
     */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of supplier_return_profit.brand_no
     */
	public String getBrandNo() {
		return brandNo;
	}

	/**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for supplier_return_profit.brand_no
     */
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	/**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of supplier_return_profit.bill_no
     */
	public String getBillNo() {
		return billNo;
	}

	/**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for supplier_return_profit.bill_no
     */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
}