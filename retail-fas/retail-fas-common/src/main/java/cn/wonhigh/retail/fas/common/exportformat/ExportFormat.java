package cn.wonhigh.retail.fas.common.exportformat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出相关的注解类
 * 
 * @author 杨勇
 * @date 2014-7-8 下午5:32:25
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExportFormat {
	
	Class<?> className();
}
