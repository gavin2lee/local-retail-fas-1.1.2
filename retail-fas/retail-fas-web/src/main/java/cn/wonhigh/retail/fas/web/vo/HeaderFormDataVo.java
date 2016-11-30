package cn.wonhigh.retail.fas.web.vo;

import java.io.Serializable;

/**
 * excel导出时，表头对象
 * 
 * @author yang.y
 */
public class HeaderFormDataVo implements Serializable {

	private static final long serialVersionUID = 5379171942225913248L;

	private String field;
	
	private String name;
	
	private String value;
	
	private int colspan;
	
	private int rowspan;

	/** 所占的行数 */
	private int rowSize = 4;
	
	public HeaderFormDataVo() {}
	
	public HeaderFormDataVo(String field, String name, String value) {
		super();
		this.field = field;
		this.name = name;
		this.value = value;
	}
	
	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
