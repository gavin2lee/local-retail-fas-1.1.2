package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-05-26 14:23:33
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
@ExportFormat(className=AbstractExportFormat.class)
public class NcFinishedGoods implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6948506433428800755L;

	/**
     * 
     */
    private Integer id;

    /**
     * 公司编号
     */
    @ExcelCell("A")
    private String companyNo;

    /**
     * 公司名称
     */
    @ExcelCell("B")
    private String companyName;

    /**
     * 品牌部编码
     */
    @ExcelCell("C")
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    @ExcelCell("D")
    private String brandUnitName;

    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    @ExcelCell("E")
    private String categoryName;
    
    /**
     * 年(yyyy)
     */
    @ExcelCell("F")
    private String year;

    /**
     * 月(mm)
     */
    @ExcelCell("G")
    private String month;
    
    /**
     * 方向（1：借，2：贷）
     */
    @ExcelCell("H")
    private String directionName;
    
    /**
     * 方向（1：借，2：贷）
     */
    private Integer direction;
    
    /**
     * 数量
     */
    @ExcelCell("I")
    private Integer qty;

    /**
     * 金额
     */
    @ExcelCell("J")
    private BigDecimal amount;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUser;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of nc_finished_goods.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for nc_finished_goods.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of nc_finished_goods.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for nc_finished_goods.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of nc_finished_goods.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for nc_finished_goods.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of nc_finished_goods.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for nc_finished_goods.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of nc_finished_goods.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for nc_finished_goods.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of nc_finished_goods.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for nc_finished_goods.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of nc_finished_goods.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for nc_finished_goods.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #direction}
     *
     * @return the value of nc_finished_goods.direction
     */
    public Integer getDirection() {
        return direction;
    }

    /**
     * 
     * {@linkplain #direction}
     * @param direction the value for nc_finished_goods.direction
     */
    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of nc_finished_goods.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for nc_finished_goods.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of nc_finished_goods.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for nc_finished_goods.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of nc_finished_goods.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for nc_finished_goods.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of nc_finished_goods.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for nc_finished_goods.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of nc_finished_goods.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for nc_finished_goods.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of nc_finished_goods.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for nc_finished_goods.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of nc_finished_goods.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for nc_finished_goods.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of nc_finished_goods.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for nc_finished_goods.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

	public String getDirectionName() {
		return directionName;
	}

	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
}