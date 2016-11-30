/**  
*   
* 项目名称：retail-fas-common  
* 类名称：GmsBillStatusEnums  
* 类描述：货品单据状态
* 创建人：ouyang.zm  
* 创建时间：2014-10-17 下午4:45:24  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.enums;

public enum GmsBillStatusEnums {
	MAKEBILL(0, "制单"),
	//	EDIT(1, "出库"), 
	//	SUBMIT(2, "提交"), 
	VERIFY(3, "审核"),
	CONFIRM(5, "确认"), 
	SEND(1, "已发未收"), 
	RECEIVED(2, "已收货"), 
	FROZEN(9, "冻结"), 
	UNFROZEN(11, "已解冻"), 
	CANCEL(99, "作废"), 
	OVER(100, "完结"), 
	PARTRECEIPT( 50, "未完结"), 
	DELETE(98, "逻辑删除"),
	HANDLING(6,"处理中"),
	HANDLED(7,"已处理");

	//  0=制单 1-已发未收  2-已收货 99-作废  100-完结

	private Integer status;
	private String text;

	private GmsBillStatusEnums(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getText() {
		return this.text;
	}

	public static String getTextByStatus(Integer status) {
		GmsBillStatusEnums[] statusArr = values();
		for (GmsBillStatusEnums billStatusEnums : statusArr) {
			if (billStatusEnums.getStatus().equals(status)) {
				return billStatusEnums.getText();
			}
		}
		return null;
	}
}
