package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TODO: 销售订单按大类汇总Dto
 * 
 * @author zhang.lh
 * @date 2014-11-25 下午4:45:11
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */
public class POSOcGroupRootCategoryDto implements Serializable{
	
 	private static final long serialVersionUID = -309785808587278961L;
 	 //门店编号
  	private String shopNo;
 	 //门店名称	 
  	private String shopName;
	//品牌编号		
	private String brandNo;
	//品牌名称	 	
	private String brandName;
	//大类编号	 
	private String rootCategoryNo;
	//大类名称	 	
	private String rootCategoryName;
	//汇总的数量	
	private Integer groupQty;
	//汇总的销售金额	
	private BigDecimal groupAmount;
	
	private BigDecimal  deductAmount;
	
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public String getRootCategoryNo() {
		return rootCategoryNo;
	}
	public void setRootCategoryNo(String rootCategoryNo) {
		this.rootCategoryNo = rootCategoryNo;
	}
	public String getRootCategoryName() {
		return rootCategoryName;
	}
	public void setRootCategoryName(String rootCategoryName) {
		this.rootCategoryName = rootCategoryName;
	}
	public Integer getGroupQty() {
		return groupQty;
	}
	public void setGroupQty(Integer groupQty) {
		this.groupQty = groupQty;
	}
	public BigDecimal getGroupAmount() {
		return groupAmount;
	}
	public void setGroupAmount(BigDecimal groupAmount) {
		this.groupAmount = groupAmount;
	}
	public BigDecimal getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}
 
	
}
