package cn.wonhigh.retail.fas.service;

import java.util.Map;


import cn.wonhigh.retail.fas.common.model.Category;

import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface CategoryService extends BaseCrudService {
	
	/**
	 * 根据商品编码获取大类信息
	 * @param params
	 * @return
	 */
	Category getCategoryByItemCode(Map<String,Object> params);
}