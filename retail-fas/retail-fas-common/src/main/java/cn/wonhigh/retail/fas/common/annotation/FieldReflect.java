package cn.wonhigh.retail.fas.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段映射：属性级别、运行时的注解
 * 
 * @author yang.y
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldReflect {
	
	/** 映射的属性名 */
	String name();
}
