/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：ReflectUtil  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2014-10-31 下午5:10:32  
 * @version       
 */
package cn.wonhigh.retail.fas.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReflectUtil {

	/**
	 * 获取对象指定属性的set方法
	 * 
	 * @param clazz
	 *            Class对象
	 * @param fieldName
	 *            字段名称
	 * @param paramClazz
	 *            参数类型
	 * @return set方法
	 */
	public static Method bulidSetFieldMethod(Class<?> clazz, String fieldName,
			Class<?> paramClazz) {
		Method setMethod = null;
		try {
			if (!clazz.getSimpleName().equals("Object")) {
				setMethod = clazz.getDeclaredMethod(
						"set" + fieldName.substring(0, 1).toUpperCase()
								+ fieldName.substring(1), paramClazz);
				if (setMethod != null) {
					return setMethod;
				}
			}
		} catch (Exception e) {
			setMethod = bulidSetFieldMethod(clazz.getSuperclass(), fieldName,
					paramClazz);
			return setMethod;
		}
		return setMethod;
	}

	private static List<Field> getObjectFields(Object obj) {
		List<Field> lst = new ArrayList<>();
		getObjectFields(obj.getClass(), lst);
		return lst;
	}

	private static void getObjectFields(Class clazz, List<Field> lst) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			lst.add(field);
		}
		if (clazz.getSuperclass() == Object.class)
			return;
		getObjectFields(clazz.getSuperclass(), lst);
	}

	static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static void object2MapWithoutNull(Object obj, Map map)
			throws IllegalArgumentException, IllegalAccessException {
		List<Field> fields = getObjectFields(obj);// .getClass().getDeclaredFields();
		for (int j = 0; j < fields.size(); j++) {
			Field field = fields.get(j);
			field.setAccessible(true);

			if (field.get(obj) != null) {
				if ((field.get(obj) instanceof Date)) {
					SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
					map.put(field.getName(), sdf.format(field.get(obj)));
				} else {
					map.put(field.getName(), field.get(obj));
				}
			} else {
				map.put(field.getName(), "");
			}
		}

	}

	public static void copyProperties(Object dest, Object ori) throws Exception {
		try {
			Class<?> destClazz = dest.getClass();
			Class<?> oriClazz = ori.getClass();
			Field[] destFields = destClazz.getDeclaredFields();
			for (Field field : destFields) {
				String fieldName = field.getName();
				Class<?> fieldClazz = field.getType();
				if ("serialVersionUID".equalsIgnoreCase(fieldName)) {
					continue;
				}
				Method oriGetMethod = oriClazz.getDeclaredMethod("get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1));
				Object oriValue = oriGetMethod.invoke(ori);
				if (oriValue != null) {
					Method destSetMethod = destClazz.getDeclaredMethod("set"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1), fieldClazz);
					destSetMethod.invoke(dest, oriValue);
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
}
