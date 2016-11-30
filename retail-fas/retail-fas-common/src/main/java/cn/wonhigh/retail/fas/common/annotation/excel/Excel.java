package cn.wonhigh.retail.fas.common.annotation.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel和 model对应
 * 
 * @author dong.j
 * @date 2014-1-7 下午4:49:29
 * @version 0.1.0
 * @copyright yougou.com
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

	public enum Struct {
		ANNOTATION, XML
	}

	/**
	 * 从多少行开始解析数据
	 * 
	 * @return
	 */
	int start() default 2;

	/**
	 * 解析到多少行结束（暂时没实现）
	 * 
	 * @return
	 */
	int end() default Integer.MAX_VALUE;

	/**
	 * 默认使用注解配置
	 * 
	 * @return
	 */
	Struct struct() default Struct.ANNOTATION;

}
