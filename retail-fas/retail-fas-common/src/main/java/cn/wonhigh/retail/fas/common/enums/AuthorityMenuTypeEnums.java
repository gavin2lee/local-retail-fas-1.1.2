package cn.wonhigh.retail.fas.common.enums;

/**
 * 菜单类型
 * 
 * @author wei.b
 * @date 2013-8-23 下午3:09:28
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum AuthorityMenuTypeEnums{
	MENU_TYPE("0"),
	MODULE_TYPE("1");
	
	public String type;
	
	AuthorityMenuTypeEnums (String type){
		this.type=type;
	}
}
