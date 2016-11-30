package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.dal.database.SupplierGroupMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
@Service("supplierGroupService")
class SupplierGroupServiceImpl extends BaseServiceImpl implements SupplierGroupService {
    @Resource
    private SupplierGroupMapper supplierGroupMapper;

    @Override
    public BaseCrudMapper init() {
        return supplierGroupMapper;
    }

	@Override
	public List<Map<String, Object>> getGroupNoAndName(
			SupplierGroup supplierGroup) throws ServiceException {
		return supplierGroupMapper.getGroupNoAndName(supplierGroup);
	}
}