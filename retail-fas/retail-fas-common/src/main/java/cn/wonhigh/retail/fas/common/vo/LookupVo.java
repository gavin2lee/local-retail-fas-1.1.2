package cn.wonhigh.retail.fas.common.vo;

import java.io.Serializable;

/**
 * @字典对象
 * @author wang.m
 * @Date 2014-9-23
 * @version 0.1.0
 * @copyright yougou.com
 */
public class LookupVo  implements Serializable {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -6796611509182820958L;
	private  String id;
	private  String code;
    private  String name;
    private  String address;
    private  String bankName;
    private  String bankAccount;
    private  String subCode;
    
    private  String extendField1;
    private  String extendField2;
    private  String extendField3;
    private  String extendField4;
    
	public String getExtendField4() {
		return extendField4;
	}
	public void setExtendField4(String extendField4) {
		this.extendField4 = extendField4;
	}
	public String getExtendField1() {
		return extendField1;
	}
	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}
	public String getExtendField2() {
		return extendField2;
	}
	public void setExtendField2(String extendField2) {
		this.extendField2 = extendField2;
	}
	public String getExtendField3() {
		return extendField3;
	}
	public void setExtendField3(String extendField3) {
		this.extendField3 = extendField3;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
