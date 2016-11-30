/**  
*   
* 项目名称：retail-fas-common  
* 类名称：InvoiceConfirmEnums  
* 类描述：到票确认结算类型
* 创建人：ouyang.zm  
* 创建时间：2014-12-11 下午12:14:24  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.enums;

public enum InvoiceConfirmEnums {
//		HQ_VENDOR(1, "总部厂商结算"),
		HQ_WHOLESALE(2, "总部地区结算"), 
//		HQ_OTHER(3, "总部其他结算"),  
//		AREA_PURCHASE(4, "地区采购结算"),  
		AREA_AMONG(5, "地区间结算");
//		AREA_BUY(6, "地区自购结算"), 
//		AREA_WHOLESALE(7, "地区批发结算"),
//		AREA_GROUP_BUY(8, "地区单位团购结算"), 
//		AREA_MEMBERS_PURCHASE(9,"地区员购结算"), 
//		AREA_MALL(10, "地区商场结算"),
//		AREA_OTHER(11, "总部其他入库结算");
		
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


		private InvoiceConfirmEnums(Integer typeNO, String typeName) {
			this.typeNO = typeNO;
			this.typeName = typeName;
		}


		public static String getTypeName(int typeNo){
			InvoiceConfirmEnums enums[] = InvoiceConfirmEnums.values();
			for (InvoiceConfirmEnums type : enums) {
				if(type.getTypeNO() == typeNo){
					return type.getTypeName();
				}
			}
			return "";
		}
}
