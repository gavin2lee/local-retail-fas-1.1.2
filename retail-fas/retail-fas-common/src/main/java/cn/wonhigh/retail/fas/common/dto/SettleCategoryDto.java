package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.SettleCategory;


/**
 * 结算大类页面展示相关的dto
 * 
 * @author 杨勇
 * @date 2014-8-6 上午10:46:07
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@ExportFormat(className=AbstractExportFormat.class)
public class SettleCategoryDto extends SettleCategory {

	private static final long serialVersionUID = -1726119437079164122L;

	/** 大类编码 */
	private String categoryNo;
	
	/** 大类名称 */
	private String categoryName;

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
