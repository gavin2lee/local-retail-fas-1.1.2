package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.ShopBalanceDateMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-15 14:29:23
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
@Service("shopBalanceDateService")
class ShopBalanceDateServiceImpl extends BaseCrudServiceImpl implements ShopBalanceDateService {
	
    @Resource
    private ShopBalanceDateMapper shopBalanceDateMapper;

    @Override
    public BaseCrudMapper init() {
        return shopBalanceDateMapper;
    }

	@Override
	public int updateBalanceDateSet(
			ShopBalanceDate shopBalanceDate) throws ServiceException {
		return shopBalanceDateMapper.updateBalanceDateSet(shopBalanceDate);
	}

	@Override
	public int updateBalanceBillAlready(ShopBalanceDate shopBalanceDate)
			throws ServiceException {
		return shopBalanceDateMapper.updateBalanceBillAlready(shopBalanceDate);
	}

	@Override
	public int findNewShopDateCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return shopBalanceDateMapper.selectNewShopDateCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> findNewShopDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return shopBalanceDateMapper.selectNewShopDateByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectNoSetShopBalanceDateCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return shopBalanceDateMapper.selectNoSetShopBalanceDateCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> selectNoSetShopBalanceDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return shopBalanceDateMapper.selectNoSetShopBalanceDateByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectShopBalanceDatePartOfRightCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return shopBalanceDateMapper.selectShopBalanceDatePartOfRightCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<ShopBalanceDate> selectShopBalanceDatePartOfRightByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return shopBalanceDateMapper.selectShopBalanceDatePartOfRightByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			ShopBalanceDate obj = (ShopBalanceDate)modelType;
			obj.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(obj.getCompanyNo()));
			obj.setId(UUIDGenerator.getUUID());
			return super.add(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}