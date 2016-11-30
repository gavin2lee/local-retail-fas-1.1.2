package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 拆单操作的结算路径明细对象
 * 
 * @author yang.y
 */
public class BillSplitSettlePathDtl implements Serializable {

	private static final long serialVersionUID = 179020064605947549L;

	/** 结算路径编码 */
	private String pathNo;
	
	/** 供应商组编码 */
	private String supplierGroupNo;
	
	/** 错误编码 */
	private String errorCode;
	
	/** 错误消息 */
	private String errorMsg;
	
	/** 结算路径明细集合 */
	private List<SettlePathDtl> list;

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

	public List<SettlePathDtl> getList() {
		return list;
	}

	public void setList(List<SettlePathDtl> list) {
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
