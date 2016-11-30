package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.model.WholesaleReceTermDtl;

/**
 * 地区批发收款条款-表体页面展示对象
 * 
 * @author yang.y
 */
public class WholesaleReceTermDtlDto extends WholesaleReceTermDtl {

	private static final long serialVersionUID = -4141527084070813424L;

	/** 控制点名称 */
	private String controlPointName;
	
	/** 预收类型名称 */
	private String advanceTypeName;

	public String getControlPointName() {
		if(getControlPoint() == null) {
			return "";
		}
		if(getControlPoint().intValue() == 0) {
			return "订货";
		}
		if(getControlPoint().intValue() == 1) {
			return "发货";
		}
		if(getControlPoint().intValue() == 2) {
			return "补货";
		}
		return controlPointName;
	}

	public void setControlPointName(String controlPointName) {
		this.controlPointName = controlPointName;
	}

	public String getAdvanceTypeName() {
		if(getAdvanceType() == null) {
			return "";
		}
		if(getAdvanceType().intValue() == 0 || getAdvanceType().intValue() == 2) {
			return "按比例";
		}
		if(getAdvanceType().intValue() == 1) {
			return "按余额发货";
		}
		return advanceTypeName;
	}

	public void setAdvanceTypeName(String advanceTypeName) {
		this.advanceTypeName = advanceTypeName;
	}
}
