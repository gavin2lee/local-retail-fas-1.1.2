package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-13 16:29:02
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class SupplierGroupRelModel implements Serializable {

	private static final long serialVersionUID = -784166290594621397L;

	private String id;
	
	/** 供应商编码 */
    private String supplierNo;

    /** 供应商名称 */
    private String supplierName;
    
    /** 供应商组编码 */
    private String groupNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
}