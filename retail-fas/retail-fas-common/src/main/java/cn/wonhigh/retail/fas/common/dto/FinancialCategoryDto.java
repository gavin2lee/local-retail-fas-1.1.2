package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;

/**
 * 财务大类页面展示相关的dto
 * @author yang.y
 * @date  2014-12-23 10:38:39
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
public class FinancialCategoryDto extends FinancialCategory {

	private static final long serialVersionUID = -2467004742217171102L;

	/** 大类编码 */
	private String categoryNo;
	
	/** 大类名称 */
	private String categoryName;
	
	 /** 是否启用数量控制标志 */
    private Integer qtyControlFlag;
    
    private String qtyControlFlagName;
    
	public String getQtyControlFlagName() {
		if(this.getQtyControlFlag() == null || this.getQtyControlFlag().intValue() == 0) {
			return "否";
		}
		if(this.getQtyControlFlag() != null && this.getQtyControlFlag().intValue() == 1) {
			return "是";
		}
		return qtyControlFlagName;
	}

	public void setQtyControlFlagName(String qtyControlFlagName) {
		this.qtyControlFlagName = qtyControlFlagName;
	}

	public Integer getQtyControlFlag() {
		return qtyControlFlag;
	}

	public void setQtyControlFlag(Integer qtyControlFlag) {
		this.qtyControlFlag = qtyControlFlag;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
