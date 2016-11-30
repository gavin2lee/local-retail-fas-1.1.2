package cn.wonhigh.retail.fas.common.model;


/**
 * 请写出类的用途 
 * @author user
 * @date  2015-09-22 14:01:10
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
public class ShopCheck extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6183957380705498061L;

	/**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 检查年
     */
    private String year;

    /**
     * 检查月
     */
    private String month;

    /**
     * 检查项编码
     */
    private String checkNo;

    /**
     * 结果登记
     */
    private Integer checkResult;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of shop_check.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for shop_check.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of shop_check.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for shop_check.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of shop_check.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for shop_check.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of shop_check.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for shop_check.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of shop_check.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for shop_check.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #checkNo}
     *
     * @return the value of shop_check.check_no
     */
    public String getCheckNo() {
        return checkNo;
    }

    /**
     * 
     * {@linkplain #checkNo}
     * @param checkNo the value for shop_check.check_no
     */
    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of shop_check.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for shop_check.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
}