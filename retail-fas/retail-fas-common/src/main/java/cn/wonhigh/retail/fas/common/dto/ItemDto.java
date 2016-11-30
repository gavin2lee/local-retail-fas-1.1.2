/**  
*   
* 项目名称：retail-fas-common  
* 类名称：ItemDto  
* 类描述：用于查询显示商品的DTO
* 创建人：ouyang.zm  
* 创建时间：2014-9-2 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.model.Item;

public class ItemDto extends Item{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3433732941104973588L;
	
	/** 品牌编码 */
	private String brandNo;
	
	/** 品牌名称 */
	private String brandName;
	
	/** 供应商编码 */
	private String supplierNo;
	
	/** 供应商名称 */
	private String supplierName;

	/** 类别名称 */
	private String categoryName;
	
	/** 年份名称 */
	private String yearsName;
	
	/** 季节名称 */
	private String seasonName;
	
	/** 订货形式名称 */
	private String orderfromName;
	
	/** 性别名称 */
	private String genderName;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getYearsName() {
		return yearsName;
	}

	public void setYearsName(String yearsName) {
		this.yearsName = yearsName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getOrderfromName() {
		return orderfromName;
	}

	public void setOrderfromName(String orderfromName) {
		this.orderfromName = orderfromName;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
