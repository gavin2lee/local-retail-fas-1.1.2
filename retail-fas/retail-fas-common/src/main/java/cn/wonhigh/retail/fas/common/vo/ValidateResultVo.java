package cn.wonhigh.retail.fas.common.vo;

import java.io.Serializable;

/**
 * @异常返回结构实体类
 * @author wei.hj
 * @Date 2013-7-29
 * @version 0.1.0
 * @copyright yougou.com
 */
public class ValidateResultVo  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6796611509182820958L;
	
	/**
	 * 是否通过 0-通过 ; 1-不通过
	 */
	private int pass;
	
	private int index;
	
	private String errorInfo = "";
	
	private String errorCode = "";
	
	private String emptyCode = "";
	
	private Object validateObj;

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Object getValidateObj() {
		return validateObj;
	}

	public void setValidateObj(Object validateObj) {
		this.validateObj = validateObj;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getEmptyCode() {
		return emptyCode;
	}

	public void setEmptyCode(String emptyCode) {
		this.emptyCode = emptyCode;
	}
	
}
