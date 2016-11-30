package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ShopGroupDto;
import cn.wonhigh.retail.fas.common.model.ShopGroup;
import cn.wonhigh.retail.fas.dal.database.ShopGroupMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-22 11:41:25
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
@Service("shopGroupService")
class ShopGroupServiceImpl extends BaseServiceImpl implements ShopGroupService {
    @Resource
    private ShopGroupMapper shopGroupMapper;

    @Override
    public BaseCrudMapper init() {
        return shopGroupMapper;
    }

	@Override
	public List<ShopGroup> getShopGroupNoByShopNo(Map<String, Object> params) {
		return shopGroupMapper.getShopGroupNoByShopNo(params);
	}

	@Override
	public List<ShopGroup> findBizByDtlShopNo(Map<String, Object> groupMap)
			throws ServiceException {
		return shopGroupMapper.findBizByDtlShopNo(groupMap);
	}

	@Override
	public List<ShopGroup> findClientQueryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		return shopGroupMapper.findClientQueryByPage(page, sortColumn, sortOrder, params);
	}

	@Override
	public int findClientQueryCount(Map<String, Object> params)
			throws ServiceException {
		return shopGroupMapper.findClientQueryCount(params);
	}

	@Override
	public List<ShopGroupDto> findByExport(SimplePage page,String orderByField, String orderBy,
			Map<String, Object> params)throws ServiceException {
		return shopGroupMapper.selectByExport(page,orderByField, orderBy, params);
	}

	@Override
	public int findByExportCount(Map<String, Object> params)
			throws ServiceException {
		return shopGroupMapper.selectByExportCount(params);
	}
}