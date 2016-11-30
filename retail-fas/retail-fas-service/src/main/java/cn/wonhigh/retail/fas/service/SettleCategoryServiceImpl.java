package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SettleCategoryDto;
import cn.wonhigh.retail.fas.dal.database.SettleCategoryMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
@Service("settleCategoryService")
class SettleCategoryServiceImpl extends BaseServiceImpl implements SettleCategoryService {
    
	@Resource
    private SettleCategoryMapper settleCategoryMapper;

    @Override
    public BaseCrudMapper init() {
        return settleCategoryMapper;
    }
    
    @Override
   	public int findRelationCount(Map<String, Object> params) throws ServiceException {
		return settleCategoryMapper.selectRelationCount(params, null);
   	}

   	@Override
   	public List<SettleCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ServiceException {
		return settleCategoryMapper.selectRelationByPage(page, sortColumn, sortOrder, params, null);
   	}
}