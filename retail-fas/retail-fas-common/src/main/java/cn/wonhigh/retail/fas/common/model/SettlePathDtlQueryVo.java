package cn.wonhigh.retail.fas.common.model;

/**
 * 查询结算路径明细的vo对象
 * 
 * @author 杨勇
 * @date 2014-8-29 下午12:09:11
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class SettlePathDtlQueryVo {

	private Integer billType;
	
	private Integer billBasis;
	
	/** 是否原残退厂的标志 */
	private Integer returnOwnFlag;
	
	private String categoryNo;
	
	private String brandNo;
	
	private String years;
	
	private String season;
	
	/** 发出方编码 */
	private String supplierNo;

	/** 收货方编码 */
	private String companyNo;
	
	/**
	 * 结算路径编码
	 */
	private String pathNo;
  
	@Override
	public String toString() {
		 return String.format("billType=%d,billBasis=%d,returnOwnFlag=%d,categoryNo=%s," 
				 			+ "brandNo=%s,years=%s,season=%s,supplierNo=%s,companyNo=%s,pathNo=%s)",
				 billType,billBasis,returnOwnFlag,categoryNo,brandNo,years,season,supplierNo,companyNo,pathNo);
	}

	public Integer getReturnOwnFlag() {
		return returnOwnFlag;
	}

	public void setReturnOwnFlag(Integer returnOwnFlag) {
		this.returnOwnFlag = returnOwnFlag;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getBillBasis() {
		return billBasis;
	}

	public void setBillBasis(Integer billBasis) {
		this.billBasis = billBasis;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
	}
}
