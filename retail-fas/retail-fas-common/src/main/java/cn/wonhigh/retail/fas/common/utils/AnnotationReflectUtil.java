package cn.wonhigh.retail.fas.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import cn.wonhigh.retail.fas.common.annotation.ClassReflect;
import cn.wonhigh.retail.fas.common.annotation.FieldReflect;

public class AnnotationReflectUtil {

	/**
	 * 复制对象
	 * @param dest 目标对象
	 * @param ori 源对象
	 * @throws Exception 
	 */
	public static void copyProperties(Object dest, Object ori) throws Exception {
		if(dest == null || ori == null) {
			return;
		}
		ClassReflect classReflect = ori.getClass().getAnnotation(ClassReflect.class);
		if(classReflect == null) {
			return ;
		}
		Class<?> destclazz = classReflect.reflectClass();
		// 判断是否是同一个类型
		if(!dest.getClass().getName().equals(destclazz.getName())) {
			return ;
		}
		Class<?> oriClazz = ori.getClass();
		Field []fields = oriClazz.getDeclaredFields();
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			FieldReflect fieldReflect = field.getAnnotation(FieldReflect.class);
			if(fieldReflect == null) {
				continue;
			}
			String reflectFieldName = fieldReflect.name();
			if(StringUtils.isEmpty(reflectFieldName)) {
				continue;
			}
			// 获取ori源对象中该field属性的值
			try {
				Method getMethod = oriClazz.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() 
						+ fieldName.substring(1));
				Object value = getMethod.invoke(ori);
				
				// 设置属性值至反射对象中
				Class<?> propertyType = field.getType(); // 获取属性的类型
				Method reflectSetMethod = destclazz.getDeclaredMethod("set" + reflectFieldName.substring(0, 1).toUpperCase() 
						+ reflectFieldName.substring(1), propertyType);
				reflectSetMethod.invoke(dest, value);
			} catch (Exception e) {
				throw new Exception(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 复制对象
	 * @param destClass 目标对象的Class
	 * @param ori 源对象
	 * @return 目标对象
	 * @throws Exception 
	 */
	public static Object createAndCopyProperties(Class<?> destClass, Object ori) throws Exception {
		Object dest = null;
		try {
			dest = destClass.newInstance();
			copyProperties(dest, ori);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return dest;
	}
}
