package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.CheckTolerExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 对账容差设置属性类
 * @author user
 * @date  2016-05-30 13:51:26
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
@ExportFormat(className=CheckTolerExportFormat.class) 
public class CheckToler extends FasBaseModel implements SequenceStrId{

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
     * 供应商编码
     */
    @ExcelCell("B")
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 税前容差金额>=
     */
    @ExcelCell("C")
    private BigDecimal pretaxTolerLow;

    /**
     * 税前容差金额<=
     */
     @ExcelCell("D")
    private BigDecimal pretaxTolerUp;

    /**
     * 税后容差金额>=
     */
    @ExcelCell("E")
    private BigDecimal aftertaxTolerLow;
    

    /**
     * 税后容差金额<=
     */
    @ExcelCell("F")
    private BigDecimal aftertaxTolerUp;
    
    /**
     * 不含税*1.17与含税容差(厂商)>=
     */
    @ExcelCell("G")
    private BigDecimal notaxTolerLow;
    
    /**
     * 不含税*1.17与含税容差(厂商)<=
     */
    @ExcelCell("H")
    private BigDecimal notaxTolerUp;
     
     /**
      * 生效日期
      */
    @ExcelCell("I")
    @JsonSerialize(using = JsonDateSerializer$10.class)   
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date effectiveDate;
     
     


    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of check_toler.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for check_toler.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of check_toler.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for check_toler.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of check_toler.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for check_toler.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of check_toler.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for check_toler.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #pretaxTolerLow}
     *
     * @return the value of check_toler.pretax_toler_low
     */
    public BigDecimal getPretaxTolerLow() {
        return pretaxTolerLow;
    }

    /**
     * 
     * {@linkplain #pretaxTolerLow}
     * @param pretaxTolerLow the value for check_toler.pretax_toler_low
     */
    public void setPretaxTolerLow(BigDecimal pretaxTolerLow) {
        this.pretaxTolerLow = pretaxTolerLow;
    }

    /**
     * 
     * {@linkplain #pretaxTolerUp}
     *
     * @return the value of check_toler.pretax_toler_up
     */
    public BigDecimal getPretaxTolerUp() {
        return pretaxTolerUp;
    }

    /**
     * 
     * {@linkplain #pretaxTolerUp}
     * @param pretaxTolerUp the value for check_toler.pretax_toler_up
     */
    public void setPretaxTolerUp(BigDecimal pretaxTolerUp) {
        this.pretaxTolerUp = pretaxTolerUp;
    }

    /**
     * 
     * {@linkplain #aftertaxTolerLow}
     *
     * @return the value of check_toler.aftertax_toler_low
     */
    public BigDecimal getAftertaxTolerLow() {
        return aftertaxTolerLow;
    }

    /**
     * 
     * {@linkplain #aftertaxTolerLow}
     * @param aftertaxTolerLow the value for check_toler.aftertax_toler_low
     */
    public void setAftertaxTolerLow(BigDecimal aftertaxTolerLow) {
        this.aftertaxTolerLow = aftertaxTolerLow;
    }

    /**
     * 
     * {@linkplain #aftertaxTolerUp}
     *
     * @return the value of check_toler.aftertax_toler_up
     */
    public BigDecimal getAftertaxTolerUp() {
        return aftertaxTolerUp;
    }

    /**
     * 
     * {@linkplain #aftertaxTolerUp}
     * @param aftertaxTolerUp the value for check_toler.aftertax_toler_up
     */
    public void setAftertaxTolerUp(BigDecimal aftertaxTolerUp) {
        this.aftertaxTolerUp = aftertaxTolerUp;
    }

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getNotaxTolerLow() {
		return notaxTolerLow;
	}

	public void setNotaxTolerLow(BigDecimal notaxTolerLow) {
		this.notaxTolerLow = notaxTolerLow;
	}

	public BigDecimal getNotaxTolerUp() {
		return notaxTolerUp;
	}

	public void setNotaxTolerUp(BigDecimal notaxTolerUp) {
		this.notaxTolerUp = notaxTolerUp;
	}


}