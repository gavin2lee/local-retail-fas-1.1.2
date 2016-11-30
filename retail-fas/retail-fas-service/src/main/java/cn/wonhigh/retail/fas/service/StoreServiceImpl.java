package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.StoreMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 11:02:24
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
@Service("storeService")
class StoreServiceImpl extends BaseCrudServiceImpl implements StoreService {
    @Resource
    private StoreMapper storeMapper;

    @Override
    public BaseCrudMapper init() {
        return storeMapper;
    }

	@Override
	public String findStoreNameByNo(String storeNo) throws ServiceException {
		if (StringUtils.isEmpty(storeNo)) {
			return null;
		}
		if ("ALL".equals(storeNo)) {
			return "ALL";
		}
		return storeMapper.findStoreNameByNo(storeNo);
	}
}