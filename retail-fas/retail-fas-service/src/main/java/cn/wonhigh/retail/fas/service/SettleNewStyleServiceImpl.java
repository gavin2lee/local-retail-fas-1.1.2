package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SettleNewStyleDto;
import cn.wonhigh.retail.fas.dal.database.SettleNewStyleMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
@Service("settleNewStyleService")
class SettleNewStyleServiceImpl extends BaseServiceImpl implements SettleNewStyleService {
	
    @Resource
    private SettleNewStyleMapper settleNewStyleMapper;

    @Override
    public BaseCrudMapper init() {
        return settleNewStyleMapper;
    }
    
    @Override
	public int findRelationCount(Map<String, Object> params) throws ServiceException {
		try {
			return settleNewStyleMapper.selectRelationCount(params, null);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<SettleNewStyleDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return settleNewStyleMapper.selectRelationByPage(page, sortColumn, sortOrder, params, null);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}