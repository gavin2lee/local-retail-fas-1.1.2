package cn.wonhigh.retail.fas.common.utils;

import org.apache.commons.lang.StringUtils;

public class FasUtil {

	/**
	 * A,B,C - ('A','B','C')/A;B;C - ('A','B','C')
	 * @param str
	 * @return
	 */
	public static String formatInQueryConditionByChar(String str,String strChar){
		if(StringUtils.isNotBlank(str) && str.indexOf("(")!= 0){
			str =str.replaceAll(strChar, "','");
			str = "('".concat(str).concat("')");
		}
		return str;
	}
	
	/**
	 * A,B,C - ('A','B','C')
	 * @param str
	 * @return
	 */
	public static String formatInQueryCondition(String str){
		if(StringUtils.isNotBlank(str) && str.indexOf("(")!= 0){
			str =str.replaceAll(",", "','");
			str = "('".concat(str).concat("')");
		}
		return str;
	}
	
	/**
	 * A,B,C - 'A','B','C'
	 * @param str
	 * @return
	 */
	public static String formatInCondition(String str){
		if(StringUtils.isNotBlank(str) && str.indexOf("(")!= 0){
			str =str.replaceAll(",", "','");
			str = "'".concat(str).concat("'");
		}
		return str;
	}
	
	/**
	 * ('A','B','C')- A,B,C 
	 * @param str
	 * @return
	 */
	public static String parseInQueryCondition(String str){
		if(StringUtils.isNotBlank(str) && str.indexOf("(")== 0){
			str = str.replaceAll("'", "");
			str = str.substring(1, str.length() - 1);
		}
		return str;
	}
	
	/**
	 * 'A','B','C'- A,B,C 
	 * @param str
	 * @return
	 */
	public static String splitStr(String str){
		String resultStr = "";
		if(StringUtils.isNotBlank(str)){
			resultStr = str.replaceAll("'", "");
			resultStr = resultStr.substring(0, resultStr.length());
		}
		return resultStr;
	}
	
	/**
	 * 'A','B','C'- ('A','B','C') 
	 * @param str
	 * @return
	 */
	public static String splitToInStr(String str){
		String resultStr = "";
		if(StringUtils.isNotBlank(str)){
			resultStr = "(".concat(str).concat(")");
		}
		return resultStr;
	}
	
	/**
	 * ('A','B','C')- 'A','B','C'
	 * @param str
	 * @return
	 */
	public static String parseInCondition(String str){
		String resultStr = "";
		if(StringUtils.isNotBlank(str)){
			resultStr = str.substring(1, str.length() - 1);
		}
		return resultStr;
	}
}
