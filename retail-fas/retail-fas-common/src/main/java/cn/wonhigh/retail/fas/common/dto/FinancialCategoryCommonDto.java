package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

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
public class FinancialCategoryCommonDto implements Serializable{

	private static final long serialVersionUID = -2467004742217171102L;

	/** 大类编码 */
	private String financialCategoryNo;
	
	/** 大类名称 */
	private String name;

	public String getFinancialCategoryNo() {
		return financialCategoryNo;
	}

	public void setFinancialCategoryNo(String financialCategoryNo) {
		this.financialCategoryNo = financialCategoryNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
