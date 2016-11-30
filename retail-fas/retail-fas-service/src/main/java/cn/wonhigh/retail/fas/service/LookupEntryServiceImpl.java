package cn.wonhigh.retail.fas.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.LookupEntryMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:21:01
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
@Service("lookupEntryService")
class LookupEntryServiceImpl extends BaseCrudServiceImpl implements LookupEntryService {
    @Resource
    private LookupEntryMapper lookupEntryMapper;

    @Override
    public BaseCrudMapper init() {
        return lookupEntryMapper;
    }

	@Override
	public String getEntryNameByLookupIdEntryCode(Map<String, Object> params)
			throws ServiceException {
		try {
			return lookupEntryMapper.getEntryNameByLookupIdEntryCode(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}