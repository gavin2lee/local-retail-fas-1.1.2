package cn.wonhigh.retail.fas.common.vo;

import java.io.Serializable;

/**
 * @异常返回结构实体类
 * @author wei.hj
 * @Date 2013-7-29
 * @version 0.1.0
 * @copyright yougou.com
 */
public class ValidateVo  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6796611509182820958L;
	
	/**
	 * 校验类型
	 */
    private  int validateType;
    
	/**
	 * 是否为空
	 */
	private  boolean emptyValidate;
	
	/**
	 * 属性编码
	 */
	private  String propertyName;
	
	/**
	 * 属性描述
	 */
	private  String propertyDesc;
	
	/**
	 * 属性名称
	 */
	private  String cnPropertyName;

	
	public int getValidateType() {
		return validateType;
	}
	public void setValidateType(int validateType) {
		this.validateType = validateType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public boolean isEmptyValidate() {
		return emptyValidate;
	}
	public void setEmptyValidate(boolean emptyValidate) {
		this.emptyValidate = emptyValidate;
	}
	
	public String getCnPropertyName() {
		return cnPropertyName;
	}
	
	public void setCnPropertyName(String cnPropertyName) {
		this.cnPropertyName = cnPropertyName;
	}
	public String getPropertyDesc() {
		return propertyDesc;
	}
	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}
	public ValidateVo(int validateType, String propertyName, String cnPropertyName, String propertyDesc, boolean emptyValidate) {
		super();
		this.validateType = validateType;
		this.emptyValidate = emptyValidate;
		this.propertyName = propertyName;
		this.propertyDesc = propertyDesc;
		this.cnPropertyName = cnPropertyName;
	}
	
}
