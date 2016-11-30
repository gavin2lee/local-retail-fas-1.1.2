/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：AreaTransferStatusEnums  
 * 类描述：调拨出库单状态
 * 创建人：ouyang.zm  
 * 创建时间：2014-11-24 下午3:55:52  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum BillBalanceInvoiceApplyStatusEnums {
	NO_CONFIRM(1, "制单"), CONFIRM(2, "确认"), CONFIRMED(3, "已开票");;

	private Integer status;
	private String text;

	private BillBalanceInvoiceApplyStatusEnums(Integer status, String text) {
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
		BillBalanceInvoiceApplyStatusEnums[] statusArr = values();
		for (BillBalanceInvoiceApplyStatusEnums billStatusEnums : statusArr) {
			if (billStatusEnums.getStatus().equals(status)) {
				return billStatusEnums.getText();
			}
		}
		return null;
	}
}
