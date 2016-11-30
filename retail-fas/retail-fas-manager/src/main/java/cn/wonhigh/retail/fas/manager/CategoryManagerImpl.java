package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.service.CategoryService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
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
@Service("categoryManager")
class CategoryManagerImpl extends BaseCrudManagerImpl implements CategoryManager {
    @Resource
    private CategoryService categoryService;

    @Override
    public BaseCrudService init() {
        return categoryService;
    }

	@Override
	public Category getCategoryByItemCode(Map<String, Object> params) {
		return categoryService.getCategoryByItemCode(params);
	}
}