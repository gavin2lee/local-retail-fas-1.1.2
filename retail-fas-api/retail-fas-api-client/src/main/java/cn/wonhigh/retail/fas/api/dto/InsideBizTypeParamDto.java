package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;

public class InsideBizTypeParamDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145069414257650242L;

	/**
	 * 公司
	 */
	private String companyNo;
	
	/**
     * 业务类型编号
     */
    private String bizTypeCode;
	
    /**
     * 状态：1 启用, 0 禁用
     */
    private Integer status;
    
    /**
     * 店铺编号
     */
    private String shopNo;
	

	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getBizTypeCode() {
		return bizTypeCode;
	}
	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

	@Override
	public String toString() {
		return "InsideBizTypeParamDto [companyNo=" + companyNo + ", bizTypeCode="
				+ bizTypeCode + ", status=" + status+  "]";
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
}
