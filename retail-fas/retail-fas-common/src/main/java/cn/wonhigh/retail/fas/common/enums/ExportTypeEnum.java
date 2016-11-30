package cn.wonhigh.retail.fas.common.enums;

/**
 * 导出格式的枚举类
 * 
 * @author 杨勇
 * @date 2014-7-16 下午3:57:39
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum ExportTypeEnum {

	COMMON("common"), //普通方式
	FIX_HEADER("fix"), //混合表头,如品牌组导出
	COMPLEX_HEADER("complex"); //复合表头,如结算路径导出
	
	private String type;
	
	private ExportTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
