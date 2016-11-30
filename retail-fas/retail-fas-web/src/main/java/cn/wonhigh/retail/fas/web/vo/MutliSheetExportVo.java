package cn.wonhigh.retail.fas.web.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 多个sheet格式导出相关的vo类
 * 
 * @author 杨勇
 * @date 2014-7-8 上午10:05:26
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings("rawtypes")
public class MutliSheetExportVo implements Serializable {

	private static final long serialVersionUID = -4086875191604854091L;

	/** sheet名称 */
	private String sheetName;
	
	/** 表格列名集合 */
	private List<Map> ColumnsMapList;
	
	/** 表格数据集合 */
	private List<Map> dataMapList;

	/** 是否需要包含表头数据 */
	private boolean containHeaderForm;
	
	/** 表头对象 */
	private List<HeaderFormDataVo> lstHeaderFormDataVo;
	
	/** 该sheet中的子数据 */
	private List<MutliSheetExportVo> children;
	
	public List<MutliSheetExportVo> getChildren() {
		return children;
	}

	public void setChildren(List<MutliSheetExportVo> children) {
		this.children = children;
	}

	public boolean isContainHeaderForm() {
		return containHeaderForm;
	}

	public void setContainHeaderForm(boolean containHeaderForm) {
		this.containHeaderForm = containHeaderForm;
	}

	public List<HeaderFormDataVo> getLstHeaderFormDataVo() {
		return lstHeaderFormDataVo;
	}

	public void setLstHeaderFormDataVo(List<HeaderFormDataVo> lstHeaderFormDataVo) {
		this.lstHeaderFormDataVo = lstHeaderFormDataVo;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<Map> getColumnsMapList() {
		return ColumnsMapList;
	}

	public void setColumnsMapList(List<Map> columnsMapList) {
		ColumnsMapList = columnsMapList;
	}

	public List<Map> getDataMapList() {
		return dataMapList;
	}

	public void setDataMapList(List<Map> dataMapList) {
		this.dataMapList = dataMapList;
	}
}
