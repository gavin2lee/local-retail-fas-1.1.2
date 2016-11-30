package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.model.Organ;

/**
 * 组织机构dto
 * 
 * @author yang.y
 */
public class OrganDto extends Organ {

	private static final long serialVersionUID = 1986652294455320004L;
	
	/** 大区名称 */
	private String zoneName;

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
}
