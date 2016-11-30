package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ParamMainDto;
import cn.wonhigh.retail.fas.dal.database.ParamMainMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-21 10:32:05
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("paramMainService")
class ParamMainServiceImpl extends BaseServiceImpl implements ParamMainService {
    @Resource
    private ParamMainMapper paramMainMapper;

    @Override
    public BaseCrudMapper init() {
        return paramMainMapper;
    }

	@Override
	public int findRelationCount(Map<String, Object> params) {
		return paramMainMapper.selectRelationCount(params);
	}

	@Override
	public List<ParamMainDto> findRelationByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return paramMainMapper.selectRelationByPage(page,sortColumn,sortOrder,params);
	}
}