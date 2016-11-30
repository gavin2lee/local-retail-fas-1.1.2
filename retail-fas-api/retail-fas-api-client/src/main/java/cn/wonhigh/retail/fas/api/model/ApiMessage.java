package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;

/**
 * 接口消息对象
 * 
 * @author yang.y
 */
public class ApiMessage implements Serializable {

	private static final long serialVersionUID = -3889934326718981786L;

	private String projectName = "fas";
	
	private String errorCode;
	
	private String errorMsg;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
