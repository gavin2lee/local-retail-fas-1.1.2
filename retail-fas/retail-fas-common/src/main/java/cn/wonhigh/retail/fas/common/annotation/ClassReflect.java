package cn.wonhigh.retail.fas.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * class类映射：类级别、运行时的注解
 * 
 * @author yang.y
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassReflect {

	/** 反射的Clas类 */
	Class<?> reflectClass();
}
