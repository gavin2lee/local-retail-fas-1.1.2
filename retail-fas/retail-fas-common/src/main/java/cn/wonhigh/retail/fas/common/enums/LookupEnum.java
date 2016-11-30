package cn.wonhigh.retail.fas.common.enums;

/**
 * 数据字段相关的枚举类
 * 
 * @author 杨勇
 * @date 2014-8-26 下午3:02:53
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum LookupEnum {

	YEAR("YEAR"), //年份
	SEASON("SEASON"), //采购季节
	SELL_SEASON("SELL_SEASON"),//产品季节
	BILL_STATUS("36"),//季节
	ORDER_STYLE("ORDER_STYLE"),
	GENDER("GENDER");
	private LookupEnum(String code) {
		this.code = code;
	}

	private String code;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
