package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.core.BaseEntry;

/**
 * 财务辅助系统基类model
 * 
 * @author 杨勇
 * @date 2014-6-23 上午10:15:41
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public  class FasBaseModel extends BaseEntry<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920547469855441250L;

	/** 主键 */
	private String id;

	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
