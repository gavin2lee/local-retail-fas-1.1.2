package cn.wonhigh.retail.fas.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.dal.database.CategoryMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("categoryService")
class CategoryServiceImpl extends BaseCrudServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public BaseCrudMapper init() {
        return categoryMapper;
    }

	@Override
	public Category getCategoryByItemCode(Map<String, Object> params) {
		return categoryMapper.getCategoryByItemCode(params);
	}
}