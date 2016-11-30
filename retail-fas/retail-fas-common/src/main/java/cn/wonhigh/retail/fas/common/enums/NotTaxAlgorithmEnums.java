package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 算法枚举
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum NotTaxAlgorithmEnums {
	
	A("A", "1：牌价*折扣1四舍五入取2位小数保留到分，2：/1.17 四舍五入取2位小数保留到分，3：*折扣2四舍五入取2位小数保留到分，4： *数量四舍五入取2位小数保留到分"), 
	B("B","1：牌价*折扣1四舍五入保留到元 ，2：/1.17四舍五入取2位小数保留到分，3：*数量*折扣2  四舍五入取2位小数保留到分"),
	C("C","1：牌价*折扣1*折扣2*/1.17*数量四舍五入取2位小数保留到分");  
	

	/**
	 * SERIALNO_CONFIG 表 REQUESTID 的值
	 */
	private String typeNo;
	/**
	 * 模块描述
	 */
	private String typeName;


	
	public String getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	private NotTaxAlgorithmEnums(String typeNo) {
		this.typeNo = typeNo;
	}
	
	private NotTaxAlgorithmEnums(String typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(String id){
		NotTaxAlgorithmEnums enums[] = NotTaxAlgorithmEnums.values();
		for (NotTaxAlgorithmEnums type : enums) {
			if(type.getTypeNo().equals(id)){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
