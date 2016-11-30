package cn.wonhigh.retail.fas.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import cn.wonhigh.retail.fas.common.vo.Column;


/**
 * 网站联盟核心处理通用util工具类
 * 
 * @ClassName: CommonUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhu.b
 * @date 2011-5-23 下午09:51:47
 */
public class CommonUtil {

	public static String getRound(double d) {
		if (d == 0) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}

	/**
	 * 判断该字符串是否为中文
	 * 
	 * @param str
	 *            String 输入字符
	 * @return boolean
	 */
	public static boolean IsChinese(String str) {
		if (str.matches("[\u4e00-\u9fa5]+")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isGB(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim() == "") {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isBig5(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim() == "") {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param plainText
	 *            String
	 * @return String
	 * @throws Exception 
	 */
	public static String md5(String plainText) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage(), e);

		}
	}

	/** 将输入字符串转化为utf8编码,并返回该编码的字符串 */
	public static String changeEncode(String in) {
		String s = null;
		byte temp[];
		if (in == null) {
			System.out.println("Warn:Chinese null founded!");
			return new String("");
		}
		try {
			temp = in.getBytes("utf8");
			s = new String(temp);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.toString());
		}
		return s;
	}

	/** 根据时间生成唯一编号 */
	public static String buildNumber(int length) {
		long time = System.currentTimeMillis();

		Random random = new Random();
		StringBuffer buffer = new StringBuffer().append(time);

		String result = "";

		if (buffer.length() >= length) {
			buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buffer.append(random.nextInt(9));
			}
		} else {
			for (int i = 0; i < (length - buffer.length()); i++) {
				buffer.append(random.nextInt(9));
			}
		}

		result = buffer.toString();
		return result;
	}

	/**
	 * 获取国际化资源文件中的键所对应的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getLocaleBundleResourceValue(String key) {
		// 获取系统默认的国家/语言环境
		Locale myLocale = Locale.getDefault();
		// 根据指定的国家/语言环境加载资源文件
		ResourceBundle bundle = ResourceBundle.getBundle("yitianplatform", myLocale);
		// 获取资源文件中的key为hello的value值
		return bundle.getString(key);

	}

	/**
	 * 判断list是否有值
	 * */
	@SuppressWarnings("rawtypes")
	public static boolean hasValue(List list) {
		return ((null != list) && (list.size() > 0));
	}

	public static boolean hasValue(String s) {
		return (s != null) && (s.trim().length() > 0);
	}

	/**
	 * 验证是否为Double
	 * @param value
	 * @return
	 */
	public static boolean validateDouble(String value) {
		if (value == null || value.equals(""))
			return false;
		try {
			new Double(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证是否为Long
	 * @param value
	 * @return
	 */
	public static boolean validateLong(String value) {
		if (value == null || value.equals(""))
			return false;
		try {
			new Long(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	

	/**
	 * 验证是否为Date类型
	 * @param value
	 * @return
	 */
	public static boolean validateDate(String value) {
		if (value == null || value.equals(""))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			sdf.parse(value);
			System.out.println(sdf.parse(value));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 从字符串转换为Double
	 * 
	 * @param str
	 * @return
	 */
	public static double stringToDouble(String str) {
		NumberFormat formater = NumberFormat.getInstance();
		double value = 0;
		try {
			if (str == null || str.trim().length() == 0 || !validateDouble(str)) {
				str = "0";
			}
			value = formater.parse(str).doubleValue();
		} catch (Exception e) {
			// SuperContext.showExceptionMsg(e);
		}
		return value;
	}

	public static Map<String, String> conditionExpMap() {
		Map<String, String> conditionExpMap = new HashMap<String, String>();
		conditionExpMap.put("等于", "=");
		conditionExpMap.put("不等于", "<>");
		conditionExpMap.put("大于", ">");
		conditionExpMap.put("大于等于", ">=");
		conditionExpMap.put("小于", "<");
		conditionExpMap.put("小于等于", "<=");
		conditionExpMap.put("包含", "like"); // like '%XXX%'
		conditionExpMap.put("不包含", "not like"); // not like '%XXX%'
		conditionExpMap.put("以开头", "like"); // like  '%XXX'
		conditionExpMap.put("以结尾", "like"); // like 'XXX%'
		return conditionExpMap;
	}

	public static String getConditionExpMap(String conditionExp) {
		return (conditionExpMap().get(conditionExp) != null) ? (String.valueOf(conditionExpMap().get(conditionExp)))
				: "=";
	}

	/**
	 * 处理查询条件公用方法，返回拼装好的SQL
	 * 参数：
	 * queryCondition [  {"columnRelation":"并且","columnName":"factoryname#String","columnCondition":"包含","columnValue":"53"},
	 *                   {"columnRelation":"并且","columnName":"createdate#Date","columnCondition":"大于等于","columnValue":"2012-07-01"},
	 *                   {"columnRelation":"并且","columnName":"counts#double","columnCondition":"大于","columnValue":"100"}
	 *                ]
	 * @param queryConditionParam
	 * @return
	 * and factoryname like '%53%' and to_char(createdate,'yyyy-MM-dd') >= '2013-07-01' or counts > 100.0
	 * @throws Exception 
	 */
	public static HashMap<String, String> getConditionSQL(String queryConditionParam) throws Exception {
		StringBuffer sql = new StringBuffer(""); //返回拼装好的SQL
		StringBuffer errorMsg = new StringBuffer("");//返回验证错误信息
		HashMap<String, String> returnMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (CommonUtil.hasValue(queryConditionParam) && !"[]".equals(queryConditionParam)) {
				//1. JSON转对象
				LinkedList<Column> list = mapper.readValue(queryConditionParam,
						new TypeReference<LinkedList<Column>>() {
						});
				if (CommonUtil.hasValue(list)) {
					int i = 1;
					for (Column col : list) {
						if (col != null && CommonUtil.hasValue(col.getColumnName())) {
							String[] columnNameArr = col.getColumnName().split("#");
							String columnName = "";
							String columnType = "";
							if (columnNameArr != null && columnNameArr.length > 1) {
								columnName = columnNameArr[0];
								columnType = columnNameArr[1];
								//2013-09-16 修改为表配置的形式
								if(CommonUtil.hasValue(columnType)){
									if("2".equals(columnType)){ // 数字
										columnType="double";
									}else if("3".equals(columnType)){ //日期
										columnType="Date";
									}else{
										columnType="string";
									}
								}else {
									columnType="string";
								}
								
								//条件关系
								String columnRelation = "and";
								if (hasValue(col.getColumnRelation()) && "或者".equals(col.getColumnRelation())) {
									columnRelation = "or";
								}

								// like \not like \ left like \right like 要单独判断
								if ("String".equals(columnType) || "string".equals(columnType)
										|| "STRING".equals(columnType)) {
									//字符串类型  
									sql.append(" ").append(columnRelation);
									sql.append(" ").append(columnName);
									String tempExpString = col.getColumnCondition().toLowerCase();
									sql.append(" ").append(CommonUtil.getConditionExpMap(tempExpString));
									if ("包含".equals(tempExpString) || "不包含".equals(tempExpString)) {
										sql.append("  '%").append(col.getColumnValue()).append("%' ");
									} else if ("以开头".equals(tempExpString)) {
										sql.append("  '").append(col.getColumnValue()).append("%' ");
									} else if ("以结尾".equals(tempExpString)) {
										sql.append("  '%").append(col.getColumnValue()).append("' ");
									} else {
										sql.append("  '").append(col.getColumnValue()).append("' ");
									}

								} else if ("double".equals(columnType) || "Double".equals(columnType)
										|| "DOUBLE".equals(columnType) || "short".equals(columnType)
										|| "int".equals(columnType) || "long".equals(columnType)) {

									if (validateDouble(col.getColumnValue())) {
										//数字类型 
										sql.append(" ").append(columnRelation);
										sql.append(" ").append(columnName);
										String tempExpString = col.getColumnCondition().toLowerCase();
										sql.append(" ").append(CommonUtil.getConditionExpMap(tempExpString));
										if ("包含".equals(tempExpString) || "不包含".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("%' ");
										} else if ("以开头".equals(tempExpString)) {
											sql.append("  '").append(col.getColumnValue()).append("%' ");
										} else if ("以结尾".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("' ");
										} else {
											sql.append(" ").append(Double.valueOf(col.getColumnValue()));
										}
									} else {
										errorMsg.append("第" + i + "行输入值格式有误，只能为数字类型; \r\n");
									}

								} else if ("Date".equals(columnType)) {
									//日期类型 全部转成按 按日期比较 目前只到天

									if (validateDate(col.getColumnValue())) {
										sql.append(" ").append(columnRelation);
										sql.append(" ").append("to_char(" + columnName + ",'yyyy-MM-dd')"); // to_char(k.createdate,'yyyy-MM-dd')
										String tempExpString = col.getColumnCondition().toLowerCase();
										sql.append(" ").append(CommonUtil.getConditionExpMap(tempExpString));
										if ("包含".equals(tempExpString) || "不包含".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("%' ");
										} else if ("以开头".equals(tempExpString)) {
											sql.append("  '").append(col.getColumnValue()).append("%' ");
										} else if ("以结尾".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("' ");
										} else {
											sql.append("  '").append(col.getColumnValue()).append("' ");
										}
									} else {
										errorMsg.append("第" + i + "行输入值格式有误，只能为日期类型; \r\n");
									}

								}
							}
						}
						i++;
					}

				}
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);

		}
		returnMap.put("sql", sql.toString());
		returnMap.put("errorMsg", errorMsg.toString());
		return returnMap;
	}

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());

	}

	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
    
	 /**
	  * 验证权限位
	  * @param power_value  存在 角色模块相关表中
	  * @param index  1-增加 2-修改   操作编号
	  * @return
	  */
	public static boolean checkPower(int power_value,int index){
		boolean flag=false;
		int temp=(int)Math.pow(2,index);
		int result = power_value&temp;
		if(result==temp){
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 
	 * @param powerStr 1,2,3,4
	 * @return
	 */
	public  static int getPower(String powerStr){
		int result=0;
		if(CommonUtil.hasValue(powerStr)){
			String[] temp=powerStr.split(",");
			if(temp!=null&&temp.length>0){
				for(String tempV:temp){
					if(hasValue(tempV)){
						result+=(int)Math.pow(2,Integer.valueOf(tempV));
					}
				}
			}
		}
		return  result;
	}
	  
	/**
	 * 价格显示
	 * @param big
	 * @return
	 */
	public static String setPrice(int big){
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
		return fmt.format(big);
	}




	public static void main(String[] args) {
//		 Integer a=2;
//		 Integer b=6;
//		 System.out.println(Integer.toBinaryString(a));
//		 System.out.println(Integer.toBinaryString(b));
//		 System.out.println(a|b|8);
		System.out.println(validateLong("12.8"));
	}
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String temp = str.replaceAll("-", "");
		// 去掉"-"符号
		return temp;
	}

}
