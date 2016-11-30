/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：RuleMatchFailReasonEnums  
 * 类描述：加价规则匹配失败原因
 * 创建人：wang.xy 
 * 创建时间：2014-9-15 下午16:05:57  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum RuleMatchFailReasonEnums {
	MATCH_SUCCESS(0, "加价规则匹配成功"),
	SUPPLIER_GROUP_UNMATCH(1, "供应商组匹配失败"), SETTLE_CATEGORY_UNMATCH(2, "结算大类匹配失败"),
	OTHER_UNMATCH(3, "新旧款、年份季节、品牌部或生效日期匹配失败"),GET_FACTORY_PRICE_ERROR(4, "获取厂进价失败"),
	GET_HQ_PRICE_ERROR(5, "获取总部价失败"),MATCH_ONEMORE_ERROR(6, "匹配到多个加价规则");

	private RuleMatchFailReasonEnums(int reasonNo, String reasonName) {
		this.reasonNo = reasonNo;
		this.reasonName = reasonName;
	}

	private int reasonNo;
	private String reasonName;
	public int getReasonNo() {
		return reasonNo;
	}
	public void setReasonNo(int reasonNo) {
		this.reasonNo = reasonNo;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	

}
