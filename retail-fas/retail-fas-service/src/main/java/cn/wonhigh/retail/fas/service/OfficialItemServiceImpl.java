package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.OfficialItemMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-04 15:25:06
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
@Service("officialItemService")
class OfficialItemServiceImpl extends BaseCrudServiceImpl implements OfficialItemService {
    @Resource
    private OfficialItemMapper officialItemMapper;

    @Override
    public BaseCrudMapper init() {
        return officialItemMapper;
    }

	@Override
	public int deleteByItemCodeAndQuotoNo(String itemCode, String quoteNo) throws ServiceException{
		try {
			return officialItemMapper.deleteByItemCodeAndQuotoNo(itemCode,quoteNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int deleteAll() throws ServiceException {
		try {
			return officialItemMapper.deleteAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}