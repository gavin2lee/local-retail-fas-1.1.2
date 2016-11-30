package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-11-10 10:19:15
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
public class BackOrderSales implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8050333859691100067L;

	/**
     * 主键
     */
    private String id;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 商品编码
     */
    private String itemNo;

    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;

    /**
     * 累计欠客数量
     */
    private Integer sumOweQty;

    /**
     * 累计欠客成本
     */
    private BigDecimal sumOweCost;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of back_order_sales.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for back_order_sales.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of back_order_sales.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for back_order_sales.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of back_order_sales.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for back_order_sales.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of back_order_sales.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for back_order_sales.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of back_order_sales.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for back_order_sales.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of back_order_sales.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for back_order_sales.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of back_order_sales.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for back_order_sales.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #sumOweQty}
     *
     * @return the value of back_order_sales.sum_owe_qty
     */
    public Integer getSumOweQty() {
        return sumOweQty;
    }

    /**
     * 
     * {@linkplain #sumOweQty}
     * @param sumOweQty the value for back_order_sales.sum_owe_qty
     */
    public void setSumOweQty(Integer sumOweQty) {
        this.sumOweQty = sumOweQty;
    }

    /**
     * 
     * {@linkplain #sumOweCost}
     *
     * @return the value of back_order_sales.sum_owe_cost
     */
    public BigDecimal getSumOweCost() {
        return sumOweCost;
    }

    /**
     * 
     * {@linkplain #sumOweCost}
     * @param sumOweCost the value for back_order_sales.sum_owe_cost
     */
    public void setSumOweCost(BigDecimal sumOweCost) {
        this.sumOweCost = sumOweCost;
    }
}