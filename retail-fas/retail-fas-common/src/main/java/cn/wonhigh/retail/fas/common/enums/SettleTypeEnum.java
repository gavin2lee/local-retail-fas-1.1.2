/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：SettleTypeEnum  
 * 类描述：结算方式类别
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-1 下午3:31:23  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum SettleTypeEnum {
	SettleType1("1", "现金"), SettleType2("2", "支票"), SettleType3("3", "汇兑"), SettleType4(
			"4", "汇票"), SettleType5("5", "信用证"), SettleType6("6", "银行呈兑汇票");

	private String no;
	private String name;

	private SettleTypeEnum(String no, String name) {
		this.no = no;
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
