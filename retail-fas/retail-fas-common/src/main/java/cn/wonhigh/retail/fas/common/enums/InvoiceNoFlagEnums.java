/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：InvoiceNoFlagEnums  
 * 类描述：调用pos 接口是否需要按发票号或申请号查询的标识
 * 创建人：Wang.yj  
 * 创建时间：2015-01-08 下午20:14:24  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum InvoiceNoFlagEnums {
	SEARCH_INVOICE_NO_NULL(0, "查询发票编号为空"),
	SEARCH_INVOICE_NO_NOT_NULL(1, "查询发票编号不为空"),
	SEARCH__ALL(2, "查询所有");
	
	private Integer typeNO;
	private String typeName;

	public Integer getTypeNO() {
		return typeNO;
	}

	public void setTypeNO(Integer typeNO) {
		this.typeNO = typeNO;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	private InvoiceNoFlagEnums(Integer typeNO, String typeName) {
		this.typeNO = typeNO;
		this.typeName = typeName;
	}

	public static String getTypeName(int typeNo) {
		InvoiceNoFlagEnums enums[] = InvoiceNoFlagEnums.values();
		for (InvoiceNoFlagEnums type : enums) {
			if (type.getTypeNO() == typeNo) {
				return type.getTypeName();
			}
		}
		return "";
	}
}
