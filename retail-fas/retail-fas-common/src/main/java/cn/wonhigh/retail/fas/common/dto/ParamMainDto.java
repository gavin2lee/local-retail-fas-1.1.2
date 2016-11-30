/**
 * title:ParamMainDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:参数和参数值
 * auther:user
 * date:2015-10-21 下午5:20:13
 */
package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.model.ParamMain;

public class ParamMainDto extends ParamMain{

	private static final long serialVersionUID = 3595839211679842939L;
	
	private String dtlName;
	private String dtlValue;
	private int isvalid;
	
	public String getDtlName() {
		return dtlName;
	}
	public void setDtlName(String dtlName) {
		this.dtlName = dtlName;
	}
	public String getDtlValue() {
		return dtlValue;
	}
	public void setDtlValue(String dtlValue) {
		this.dtlValue = dtlValue;
	}
	public int getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}
	
	

}
