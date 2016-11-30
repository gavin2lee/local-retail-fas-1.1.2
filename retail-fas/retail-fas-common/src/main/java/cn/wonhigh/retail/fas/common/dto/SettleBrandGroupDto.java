package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.SettleBrandGroup;

/**
 * 品牌组与品牌关联后，在页面显示时所需的dto
 * 
 * @author 杨勇
 * @date 2014-8-7 上午10:26:09
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@ExportFormat(className=AbstractExportFormat.class)
public class SettleBrandGroupDto extends SettleBrandGroup {

	private static final long serialVersionUID = 7357693387979471099L;

	/** 品牌部编码 */
	private String brandUnitNo;
	
	/** 品牌部名称 */
	private String brandUnitName;

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
}
