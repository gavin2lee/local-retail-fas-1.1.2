package cn.wonhigh.retail.fas.common.annotation.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel cell 和 model field对应
 * 
 * @author dong.j
 * @date 2014-1-7 下午4:49:29
 * @version 0.1.0
 * @copyright yougou.com
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCell {
	String value();
}
