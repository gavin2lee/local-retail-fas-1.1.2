package cn.wonhigh.retail.fas.common.enums;

/**
 * 跨区调货业务类型
 * 
 * @author yu.y
 * @date 2014-9-1 上午10:07:33
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum BizTypeTransferEnums {
	AREA_TO_AREA_NORMAL(14, "地区到地区正常调货"), AREA_TO_AREA_DIFF(15, "地区到地区差异处理"),
	AREA_TO_HQ_NORMAL(24, "地区到总部正常调货"), AREA_TO_HQ_DIFF(25, "地区到总部差异处理"), 
	HQ_TO_AREA_NORMAL(34, "总部到地区正常调货"), HQ_TO_AREA_DIFF(35, "总部到地区差异处理"), 
	HQ_TO_HQ_NORMAL(44, "总部到总部正常调货"), HQ_TO_HQ_DIFF(45, "总部到总部差异处理");

	private Integer status;
	private String text;

	private BizTypeTransferEnums(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getText() {
		return this.text;
	}
	
	public static String getNameByNo(int no){
		for (BizTypeTransferEnums item : BizTypeTransferEnums.values()) {
			if(item.getStatus().intValue() == no){
				return item.getText();
			}
		}
		return "";
	}

}
