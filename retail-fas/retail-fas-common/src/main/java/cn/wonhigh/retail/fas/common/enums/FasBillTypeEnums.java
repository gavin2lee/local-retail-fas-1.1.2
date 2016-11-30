/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：BalanceTypeEnums  
 * 类描述：结算类型
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-12 下午12:05:57  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;


public enum FasBillTypeEnums {
	RR(1, "地区间结算单","RR",5),
	RP(2, "地区自购结算单","RP",6),	
	RO(3, "地区其他出库结算单","RO",11),	
	GA(4, "总部代采购结算单 ","GA",13),
	GP(5, "总部厂商结算单","GP",1),
	GS(6, "总部地区结算单","GS",2),	
	RS(7, "地区批发结算单","RS",7),	
	PR(8, "请款单","PR"),	
	PC(9, "付款单 ","PC"),	
	PI(10, "采购发票","PI"),	
	AI(11, "开票申请 ","AI"),
	RI(12, "销售发票","RI"),	
	PA(13, "预收款单","PA"),
	RC(14, "收款条款","RC"),
	SG(15, "店铺组","SG"),
	TS(16, "体总厂商结算单","TS",16),
	BA(17, "巴罗克厂商结算单","BA",16);
	
	private FasBillTypeEnums(Integer typeNo, String typeName, String requestId) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.requestId = requestId;
	}
	
	private FasBillTypeEnums(Integer typeNo, String typeName, String requestId, Integer balanceType) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.requestId = requestId;
		this.balanceType = balanceType;
	}
	
	private Integer typeNo; // 代号
	private String typeName; // 模块名称
	private String requestId; // 单据编码标识
	private Integer balanceType; // 结算类型
	
	
	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public Integer getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(Integer typeNo) {
		this.typeNo = typeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public static String getRequestIdByBalanceType(int balanceType){
		for (FasBillTypeEnums billType : FasBillTypeEnums.values()) {
			if(billType.getBalanceType().intValue() == balanceType){
				return billType.getRequestId();
			}
		}
		return "";
	}
	
	
}
