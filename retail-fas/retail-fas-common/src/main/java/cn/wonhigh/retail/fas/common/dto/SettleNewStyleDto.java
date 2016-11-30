package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.SettleNewStyle;


/**
 * 新旧款页面展示相关的dto
 * 
 * @author 杨勇
 * @date 2014-8-6 上午10:46:07
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@ExportFormat(className=AbstractExportFormat.class)
public class SettleNewStyleDto extends SettleNewStyle {

	private static final long serialVersionUID = -7450168015763493903L;

	/** 季节编号 */
	private String seasonNo;
	
	/** 季节名称 */
	private String seasonName;
	
	/** 年份 */
	private String year;

	public String getSeasonNo() {
		return seasonNo;
	}

	public void setSeasonNo(String seasonNo) {
		this.seasonNo = seasonNo;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
