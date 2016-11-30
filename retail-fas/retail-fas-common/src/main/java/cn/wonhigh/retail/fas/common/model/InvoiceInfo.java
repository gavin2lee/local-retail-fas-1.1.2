package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.InvoiceInfoExportFormat;


/**
 * 开票信息维护
 * @author ouyang.zm
 * @date  2015-02-03 11:06:43
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@ExportFormat(className=InvoiceInfoExportFormat.class)
public class InvoiceInfo extends FasBaseModel implements SequenceStrId{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2391269784996605884L;
	
    /**
     * 公司编码
     */
	@ExcelCell("A")
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 客户类型 1、公司 2、客户 3、商场 4、商业集团 5、供应商
     */
    private Byte clientType;

    @ExcelCell("B")
    private String clientTypeStr;
    
	/**
     * 客户编码
     */
    @ExcelCell("C")
    private String clientNo;

    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 客户简称
     */
    private String clientShortName;

    /**
     * 开票名称
     */
    @ExcelCell("D")
    private String invoiceName;

    /**
     * 发票类型 0、普通发票 1、增值票
     */
    private Byte invoiceType;

    @ExcelCell("E")
    private String invoiceTypeStr;
    
    /**
     * nc客户编码
     */
    @ExcelCell("F")
    private String ncClientNo;

    /**
     * NC客户档案
     */
    @ExcelCell("G")
    private String ncClientName;

    /**
     * 纳税人识别号
     */
    @ExcelCell("H")
    private String taxpayerNo;

    /**
     * 地址
     */
    @ExcelCell("I")
    private String address;

    /**
     * 电话号码
     */
    @ExcelCell("J")
    private String telephoneNumber;

    /**
     * 开户行
     */
    @ExcelCell("K")
    private String bankName;

    /**
     * 账号
     */
    @ExcelCell("L")
    private String accountNo;

    /**
     * 收票邮寄地址
     */
    @ExcelCell("M")
    private String postAddress;

    /**
     * 收票联系人
     */
    @ExcelCell("N")
    private String contactPerson;

    /**
     * 收票联系电话
     */
    @ExcelCell("O")
    private String contactNumber;
    
    private String month;
    
    public String getClientTypeStr() {
		return clientTypeStr;
	}

	public void setClientTypeStr(String clientTypeStr) {
		if (null != clientTypeStr) {
			Byte i = 0 ;
			if(clientTypeStr.startsWith("公司")){
				i = 1;
			}else if(clientTypeStr.startsWith("客户")){
				i = 2;
			}else if(clientTypeStr.startsWith("商场")){
				i = 3;
			}else if(clientTypeStr.startsWith("商业集团")){
				i = 4;
			}else if(clientTypeStr.startsWith("供应商")){
				i = 5;
			}
			this.clientType = i;
		}
		this.clientTypeStr = clientTypeStr;
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		if (null != invoiceTypeStr) {
			Byte i = 0 ;
			if(invoiceTypeStr.startsWith("普通发票")){
				i = 1;
			}else if(invoiceTypeStr.startsWith("增值发票")){
				i = 2;
			}
			this.invoiceType = i;
		}
		this.invoiceTypeStr = invoiceTypeStr;
	}
	
   
	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of invoice_info.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for invoice_info.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of invoice_info.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for invoice_info.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #clientType}
     *
     * @return the value of invoice_info.client_type
     */
    public Byte getClientType() {
        return clientType;
    }

    /**
     * 
     * {@linkplain #clientType}
     * @param clientType the value for invoice_info.client_type
     */
    public void setClientType(Byte clientType) {
        this.clientType = clientType;
    }

    /**
     * 
     * {@linkplain #clientNo}
     *
     * @return the value of invoice_info.client_no
     */
    public String getClientNo() {
        return clientNo;
    }

    /**
     * 
     * {@linkplain #clientNo}
     * @param clientNo the value for invoice_info.client_no
     */
    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    /**
     * 
     * {@linkplain #clientName}
     *
     * @return the value of invoice_info.client_name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 
     * {@linkplain #clientName}
     * @param clientName the value for invoice_info.client_name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 
     * {@linkplain #invoiceName}
     *
     * @return the value of invoice_info.invoice_name
     */
    public String getInvoiceName() {
        return invoiceName;
    }

    /**
     * 
     * {@linkplain #invoiceName}
     * @param invoiceName the value for invoice_info.invoice_name
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     *
     * @return the value of invoice_info.invoice_type
     */
    public Byte getInvoiceType() {
        return invoiceType;
    }

    /**
     * 
     * {@linkplain #invoiceType}
     * @param invoiceType the value for invoice_info.invoice_type
     */
    public void setInvoiceType(Byte invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 
     * {@linkplain #ncClientNo}
     *
     * @return the value of invoice_info.nc_client_no
     */
    public String getNcClientNo() {
        return ncClientNo;
    }

    /**
     * 
     * {@linkplain #ncClientNo}
     * @param ncClientNo the value for invoice_info.nc_client_no
     */
    public void setNcClientNo(String ncClientNo) {
        this.ncClientNo = ncClientNo;
    }

    /**
     * 
     * {@linkplain #ncClientName}
     *
     * @return the value of invoice_info.nc_client_name
     */
    public String getNcClientName() {
        return ncClientName;
    }

    /**
     * 
     * {@linkplain #ncClientName}
     * @param ncClientName the value for invoice_info.nc_client_name
     */
    public void setNcClientName(String ncClientName) {
        this.ncClientName = ncClientName;
    }

    /**
     * 
     * {@linkplain #taxpayerNo}
     *
     * @return the value of invoice_info.taxpayer_no
     */
    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    /**
     * 
     * {@linkplain #taxpayerNo}
     * @param taxpayerNo the value for invoice_info.taxpayer_no
     */
    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }

    /**
     * 
     * {@linkplain #address}
     *
     * @return the value of invoice_info.address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * {@linkplain #address}
     * @param address the value for invoice_info.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * {@linkplain #telephoneNumber}
     *
     * @return the value of invoice_info.telephone_number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * 
     * {@linkplain #telephoneNumber}
     * @param telephoneNumber the value for invoice_info.telephone_number
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * 
     * {@linkplain #bankName}
     *
     * @return the value of invoice_info.bank_name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 
     * {@linkplain #bankName}
     * @param bankName the value for invoice_info.bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 
     * {@linkplain #accountNo}
     *
     * @return the value of invoice_info.account_no
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 
     * {@linkplain #accountNo}
     * @param accountNo the value for invoice_info.account_no
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 
     * {@linkplain #postAddress}
     *
     * @return the value of invoice_info.post_address
     */
    public String getPostAddress() {
        return postAddress;
    }

    /**
     * 
     * {@linkplain #postAddress}
     * @param postAddress the value for invoice_info.post_address
     */
    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    /**
     * 
     * {@linkplain #contactPerson}
     *
     * @return the value of invoice_info.contact_person
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * 
     * {@linkplain #contactPerson}
     * @param contactPerson the value for invoice_info.contact_person
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * 
     * {@linkplain #contactNumber}
     *
     * @return the value of invoice_info.contact_number
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 
     * {@linkplain #contactNumber}
     * @param contactNumber the value for invoice_info.contact_number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getClientShortName() {
		return clientShortName;
	}

	public void setClientShortName(String clientShortName) {
		this.clientShortName = clientShortName;
	}
	
}