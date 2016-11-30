package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SettleBrandGroupDto;
import cn.wonhigh.retail.fas.dal.database.SettleBrandGroupMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 品牌组
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
@Service("settleBrandGroupService")
class SettleBrandGroupServiceImpl extends BaseServiceImpl implements SettleBrandGroupService {
   
	@Resource
    private SettleBrandGroupMapper settleBrandGroupMapper;

    @Override
    public BaseCrudMapper init() {
        return settleBrandGroupMapper;
    }
    
    @Override
	public int findRelationCount(Map<String, Object> params) throws ServiceException {
		return settleBrandGroupMapper.selectRelationCount(params, null);
	}

	@Override
	public List<SettleBrandGroupDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		return settleBrandGroupMapper.selectRelationByPage(page, sortColumn, sortOrder, params, null);
	}
}