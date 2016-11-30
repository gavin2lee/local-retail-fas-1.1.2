package cn.wonhigh.retail.fas.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 拆单操作的结算路径明细对象
 * 
 * @author yang.y
 */
public class BillSplitSettlePathDtlModel implements Serializable {

	private static final long serialVersionUID = -6813143031821097804L;

	/** 结算路径编码 */
	private String pathNo;
	
	/** 供应商组编码 */
	private String supplierGroupNo;
	
	/** 错误编码 */
	private String errorCode;
	
	/** 错误消息 */
	private String errorMsg;
	
	/** 结算路径明细集合 */
	private List<SettlePathDtlModel> list;

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

	public List<SettlePathDtlModel> getList() {
		return list;
	}

	public void setList(List<SettlePathDtlModel> list) {
		this.list = list;
	}

	public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
	}

	public String getSupplierGroupNo() {
		return supplierGroupNo;
	}

	public void setSupplierGroupNo(String supplierGroupNo) {
		this.supplierGroupNo = supplierGroupNo;
	}
}
