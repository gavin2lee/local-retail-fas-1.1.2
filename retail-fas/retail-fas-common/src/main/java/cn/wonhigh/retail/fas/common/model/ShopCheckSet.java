package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ShopCheckSetExportFormat;


/**
 * 请写出类的用途 
 * @author user
 * @date  2015-09-21 16:04:46
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
@ExportFormat(className=ShopCheckSetExportFormat.class)
public class ShopCheckSet extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2246952515904313813L;

	/**
     * 主键
     */
    private Integer rowId;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 店铺检查项编码
     */
    private String checkNo;

    /**
     * 店铺检查项名称
     */
    private String checkName;

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of shop_check_set.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for shop_check_set.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of shop_check_set.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for shop_check_set.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #checkNo}
     *
     * @return the value of shop_check_set.check_no
     */
    public String getCheckNo() {
        return checkNo;
    }

    /**
     * 
     * {@linkplain #checkNo}
     * @param checkNo the value for shop_check_set.check_no
     */
    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    /**
     * 
     * {@linkplain #checkName}
     *
     * @return the value of shop_check_set.check_name
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * 
     * {@linkplain #checkName}
     * @param checkName the value for shop_check_set.check_name
     */
    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
}