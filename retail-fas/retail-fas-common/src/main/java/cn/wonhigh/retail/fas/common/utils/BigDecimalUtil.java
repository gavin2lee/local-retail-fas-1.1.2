package cn.wonhigh.retail.fas.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang.NullArgumentException;

/**
 * BigDecimal转换的工具类
 * 
 * @author 杨勇
 * @date 2014-6-27 下午4:10:15
 * @version 0.1.0
 * @copyright yougou.com
 */
public class BigDecimalUtil {

	/** 精度 */
	public final static int SCALE = 2;

	/** 默认的小数位数 */
	public final static String POINT_DEFAULT_PATTERN = "0.#######";

	/** 保留2位小数 */
	public final static String POINT_2_PATTERN = "0.##";

	/** 保留3位小数 */
	public final static String POINT_3_PATTERN = "0.###";

	/** 保留4位小数 */
	public final static String POINT_4_PATTERN = "0.####";

	/** 保留5位小数 */
	public final static String POINT_5_PATTERN = "0.#####";

	/** 保留6位小数 */
	public final static String POINT_6_PATTERN = "0.######";

	/** 保留7位小数 */
	public final static String POINT_7_PATTERN = "0.#######";

	/**
	 * 将BigDecimal类型的数据转换为String类型
	 * 
	 * @param value
	 *            BigDecimal
	 * @return 转换后的数据
	 */
	public static String formatToStr(BigDecimal value) {
		return formatToStr(value, POINT_DEFAULT_PATTERN);
	}

	/**
	 * 将BigDecimal类型的数据转换为String类型
	 * 
	 * @param value
	 *            BigDecimal
	 * @param pattern
	 *            保留的小数样式
	 * @return 转换后的数据
	 */
	public static String formatToStr(BigDecimal value, String pattern) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value.doubleValue());
	}

	/**
	 * 将BigDecimal类型的数据转换为保留指定小数位数的BigDecimal对象
	 * 
	 * @param value
	 *            BigDecimal
	 * @return 转换后的数据
	 */
	public static BigDecimal format(BigDecimal value) {
		return format(value, POINT_DEFAULT_PATTERN);
	}

	/**
	 * 将BigDecimal类型的数据转换为保留指定小数位数的BigDecimal对象
	 * 
	 * @param value
	 *            BigDecimal
	 * @param pattern
	 *            保留的小数样式
	 * @return 转换后的数据
	 */
	public static BigDecimal format(BigDecimal value, String pattern) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat(pattern);
		String str = df.format(value.doubleValue());
		return new BigDecimal(str);
	}

	/**
	 * 多个数值相加
	 * 
	 * @param values
	 *            待相加的数据
	 * @return 求和后的数据
	 */
	public static BigDecimal add(BigDecimal... values) {
		if (values.length == 0) {
			return BigDecimal.ZERO;
		}
		BigDecimal initValue = BigDecimal.ZERO;
		for (BigDecimal value : values) {
			initValue = add(initValue, value);
		}
		return initValue;
	}

	/**
	 * 2个数相加
	 * 
	 * @param value
	 * @param value1
	 * @return 相加后的值
	 */
	public static BigDecimal add(BigDecimal value, BigDecimal value1) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		if (value1 == null) {
			value1 = BigDecimal.ZERO;
		}
		BigDecimal returnVal = value.add(value1, MathContext.DECIMAL64);
		returnVal = returnVal.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		return returnVal;
	}
	
	/**
	 * 2个数相加
	 * 
	 * @param value
	 * @param value1
	 * @return 相加后的值
	 */
	public static BigDecimal add(BigDecimal value, BigDecimal value1,Boolean isRoundHalfUp) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		if (value1 == null) {
			value1 = BigDecimal.ZERO;
		}
		BigDecimal returnVal = value.add(value1, MathContext.DECIMAL64);
		if(null!=isRoundHalfUp && isRoundHalfUp.booleanValue())
		{
			returnVal = returnVal.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		}
		return returnVal;
	}

	/**
	 * 2个数相减
	 * 
	 * @param value
	 * @param value1
	 * @return 相减后的值
	 */
	public static BigDecimal subtract(BigDecimal value, BigDecimal value1) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		if (value1 == null) {
			value1 = BigDecimal.ZERO;
		}

		BigDecimal returnVal = value.subtract(value1, MathContext.DECIMAL64);
		returnVal = returnVal.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		return returnVal;
	}

	/**
	 * 2数相乘
	 * 
	 * @param value
	 * @param value1
	 * @return
	 */
	public static BigDecimal multiInt(BigDecimal value, Integer value1) {
		if (value == null || value1 == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal returnVal = value.multiply(new BigDecimal(value1), MathContext.DECIMAL64);
		returnVal = returnVal.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		return returnVal;
	}

	/**
	 * 2数相乘
	 * 
	 * @param value
	 * @param value1
	 * @return
	 */
	public static BigDecimal multi(BigDecimal value, BigDecimal value1) {
		if (value == null || value1 == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal returnVal = value.multiply(value1, MathContext.DECIMAL64);
		returnVal = returnVal.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		return returnVal;
	}

	/**
	 * N数相乘，如果其中有null，返回ZERO
	 * 
	 * @param values
	 * @return
	 */
	public static BigDecimal mutiAll(BigDecimal... values) {
		return mutiAll(true,values);
	}
	
	public static BigDecimal mutiAll(Boolean roundHalfUp,BigDecimal...values){
		if (values == null || (values!=null && values.length==0)) {
			return BigDecimal.ZERO;
		}
		BigDecimal returnVal = values[0];
		if(null == returnVal){
			returnVal  = BigDecimal.ZERO;
		}
		for (Integer i = 1; i < values.length; i++) {
			BigDecimal factor = values[i];
			if(null == factor){
				break;
			}
			returnVal = returnVal.multiply(factor);
		}
		if(roundHalfUp){
			returnVal = returnVal.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		}
		return returnVal;
	}

	/**
	 * 2数相除
	 * 
	 * @param value
	 * @param value1
	 * @return
	 */
	public static BigDecimal division(BigDecimal value, BigDecimal value1) {
		if (value == null || value1 == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal returnVal = value.divide(value1, SCALE, RoundingMode.HALF_UP);
		return returnVal;
	}

	public static BigDecimal divisionScale(BigDecimal value, int scale, BigDecimal value1) {
		if (value == null || value1 == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal returnVal = value.divide(value1, scale, RoundingMode.HALF_UP);
		return returnVal;
	}
}
