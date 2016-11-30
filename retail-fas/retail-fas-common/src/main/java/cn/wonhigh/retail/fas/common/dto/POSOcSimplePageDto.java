package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;

public class POSOcSimplePageDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前页码
	 */
	private int pageNo = 1;
	/**
	 * 每页显示记录数
	 */
	private int pageSize = 10;
	/**
	 * 排序字段
	 */
	private String sortColumn;
	/**
	 * 排序规则
	 */
	private String sortOrder;
	
	
	/**
	 * 构造器
	 */
	public POSOcSimplePageDto(){
		
	}
	
	public POSOcSimplePageDto(int pageNo,int pageSize,String sortColumn,String sortOrder){
 		if(pageNo > 0){
			this.pageNo = pageNo;
		}
		if(pageSize > 0){
			this.pageSize = pageSize;
		}
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
	}
	

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
}
