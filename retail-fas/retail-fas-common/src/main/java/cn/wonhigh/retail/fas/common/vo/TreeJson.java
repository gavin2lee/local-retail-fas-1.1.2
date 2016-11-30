package cn.wonhigh.retail.fas.common.vo;

import java.io.Serializable;
import java.util.List;

public class TreeJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5503265581998971979L;
	
	private String id;
	private String text;
	private String iconCls;
	private String state;
	private String checked;
	private List<TreeJson> children;
	
	public TreeJson() {
		super();
	}

	public TreeJson(String id, String text, String iconCls, String state,
			String checked, List<TreeJson> children) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
		this.state = state;
		this.checked = checked;
		this.children = children;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public List<TreeJson> getChildren() {
		return children;
	}
	public void setChildren(List<TreeJson> children) {
		this.children = children;
	}

}
