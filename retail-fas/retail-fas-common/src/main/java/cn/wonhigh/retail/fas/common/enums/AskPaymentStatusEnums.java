/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：AskPaymentStatusEnums  
 * 类描述：请款单状态
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-26 下午3:29:12  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum AskPaymentStatusEnums {
	TOUCHING(0, "制单"), NO_AUDITING(1, "未审核"), AUDITING(2, "已审核"), CANCEL(3,
			"作废");

	private Integer statusNo;
	private String statusName;

	public Integer getStatusNo() {
		return statusNo;
	}

	public void setStatusNo(Integer statusNo) {
		this.statusNo = statusNo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private AskPaymentStatusEnums(Integer statusNo, String statusName) {
		this.statusNo = statusNo;
		this.statusName = statusName;
	}

}
