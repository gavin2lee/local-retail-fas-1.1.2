package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ItemCostConditionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593163213914056139L;

	private String companyNo;
	
	private String companyName;

	private String createUser;

	private Date createTime;
	
	private Date startDate;
	
	private Date endDate;

	private List<String> brandNos;
	
	private List<String> brandUnitNos;

	private List<String> itemNos;

	private String shardingFlag;
	
	private String currentYear;
	
	private String currentMonth;
	
	private String lastYear;
	
	private String lastMonth;
	
	private String brandNo;
	
	private String brandUnitNo;
	
	private String brandUnitName;
	
	private String orderUnitNo;
	
	private List<String> orderUnitNos;
	
	private String isPE;

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getBrandNos() {
		return brandNos;
	}

	public void setBrandNos(List<String> brandNos) {
		this.brandNos = brandNos;
	}

	public List<String> getBrandUnitNos() {
		return brandUnitNos;
	}

	public void setBrandUnitNos(List<String> brandUnitNos) {
		this.brandUnitNos = brandUnitNos;
	}

	public List<String> getItemNos() {
		return itemNos;
	}

	public void setItemNos(List<String> itemNos) {
		this.itemNos = itemNos;
	}

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public String getLastYear() {
		return lastYear;
	}

	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public List<String> getOrderUnitNos() {
		return orderUnitNos;
	}

	public void setOrderUnitNos(List<String> orderUnitNos) {
		this.orderUnitNos = orderUnitNos;
	}

	public String getIsPE() {
		return isPE;
	}

	public void setIsPE(String isPE) {
		this.isPE = isPE;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
