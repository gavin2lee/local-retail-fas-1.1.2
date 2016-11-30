package cn.wonhigh.retail.fas.common.model;


/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
public class InvoiceTemplateSet extends FasBaseModel implements SequenceStrId{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 发票模版编号
     */
    private String invoiceTempNo;

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 结算公司编码
     */
    private String companyNo;
    
    private String  companyName;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #invoiceTempNo}
     *
     * @return the value of invoice_template_set.invoice_temp_no
     */
    public String getInvoiceTempNo() {
        return invoiceTempNo;
    }

    /**
     * 
     * {@linkplain #invoiceTempNo}
     * @param invoiceTempNo the value for invoice_template_set.invoice_temp_no
     */
    public void setInvoiceTempNo(String invoiceTempNo) {
        this.invoiceTempNo = invoiceTempNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of invoice_template_set.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for invoice_template_set.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of invoice_template_set.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for invoice_template_set.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of invoice_template_set.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for invoice_template_set.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of invoice_template_set.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for invoice_template_set.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of invoice_template_set.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for invoice_template_set.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }


    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of invoice_template_set.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for invoice_template_set.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}