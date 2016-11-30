package cn.wonhigh.retail.fas.web.utils;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * Freemarker模板页面函数方法
 * @author wei.b
 * @date Jun 19, 2014 3:14:05 PM
 * @version 0.1.0 
 * @copyright yougou.com
 */
public class EncodeURLMethod implements TemplateMethodModel {

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public Object exec(List args) throws TemplateModelException {
		boolean flag = false;

		if (args != null && args.size() > 1) {
			String s1 = (String) args.get(0);
			String s2 = (String) args.get(1);
			/*	if((CommonUtil.hasValue(s1)&&CommonUtil.validateLong(s1))&&(CommonUtil.hasValue(s2)&&CommonUtil.validateLong(s2))){
					flag=CommonUtil.checkPower(Integer.valueOf(s1), Integer.valueOf(s2));
				}*/
		}

		return flag;
	}

}
