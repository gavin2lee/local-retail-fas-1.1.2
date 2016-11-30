package cn.wonhigh.retail.fas.api.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 反射相关的工具类
 * 
 * @author yang.y
 */
public class ReflectUtil {

	/**
	 * 检验字段是否为空
	 * @param obj 待校验的对象
	 * @param fieldNames 待校验的字段数组
	 * @return boolean
	 * @throws ManagerException 
	 */
	public static boolean validateRequired(Object obj, String []fieldNames) throws ManagerException {
		if(obj == null || (fieldNames == null || fieldNames.length == 0)) {
			return false;
		}
		try {
			Class<?> clazz = obj.getClass();
			for(String fieldName : fieldNames) {
				Field field = clazz.getDeclaredField(fieldName);
				Class<?> fieldClazz = field.getType();
				Method method = clazz.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() 
						+ fieldName.substring(1));
				Object value = method.invoke(obj);
				if(fieldClazz.getSimpleName().equals(String.class.getSimpleName())) {
					String fieldValue = value.toString();
					if(StringUtils.isEmpty(fieldValue)) {
						return false;
					}
				} else {
					if(value == null) {
						return false;
					}
				}
			}
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
	}
	
	public static void copyProperties(Object dest, Object ori) throws Exception {
		try {
			Class<?> destClazz = dest.getClass();
			Class<?> oriClazz = ori.getClass();
			Field []destFields = destClazz.getDeclaredFields();
			for(Field field : destFields) {
				String fieldName = field.getName();
				Class<?> fieldClazz = field.getType();
				if("serialVersionUID".equalsIgnoreCase(fieldName)) {
					continue;
				}
				Method oriGetMethod = oriClazz.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() 
						+ fieldName.substring(1));
				Object oriValue = oriGetMethod.invoke(ori);
				if(oriValue != null) {
					Method destSetMethod = destClazz.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() 
							+ fieldName.substring(1), fieldClazz);
					destSetMethod.invoke(dest, oriValue);
				}
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);

		}
	}
}
