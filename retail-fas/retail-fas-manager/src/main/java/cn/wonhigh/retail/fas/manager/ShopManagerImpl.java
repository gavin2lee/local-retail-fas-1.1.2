package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ShopExtensionDto;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.service.ShopService;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("shopManager")
class ShopManagerImpl extends BaseCrudManagerImpl implements ShopManager {
    @Resource
    private ShopService shopService;
    Cache<Integer, List<ShopExtensionDto>> cache;
    @Override
    public BaseCrudService init() {
    	cache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
        return shopService;
    }

	@Override
	public  Shop initSubsiInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shopService.selectSubsiInfo(params);
	}

	@Override
	public ShopBrand selectShopBrandInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shopService.selectShopBrandInfo(params);
	}

	@Override
	public List<Shop> initSubsiInfoList(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return shopService.selectSubsiInfoList(page, orderByField, orderBy, params);
	}

	@Override
	public List<Shop> selectShopInfoListByShopNos(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return shopService.selectShopInfoListByShopNos(params);
	}

	@Override
	public List<String> getShopBySelfCheckin(Map<String, Object> params) {
		return shopService.getShopBySelfCheckin(params);
	}

	@Override
	public int findShopAndOrganCount(Map<String, Object> params) {
		try {
			return shopService.findShopAndOrganCount(params);
		} catch (ServiceException e) { 
			return -1;
		}
	}

	@Override
	public List<Shop> findShopAndOrganByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		try {
			return shopService.findShopAndOrganByPage(page, orderByField, orderBy, params);
		} catch (ServiceException e) { 
			return null;
		}
	}

	
	@Override
	public List<ShopExtensionDto> fetchShopExtention(String attributeNo)throws ManagerException{
		try {
			return shopService.fetchShopExtention(attributeNo);	
		} catch (Exception e) {
			throw new ManagerException(e);
		}
		
	}
	
	/**
	 * 根据条件查询店铺属性的管理城市及片区信息 
	 */
	@Override
	public List<ShopExtensionDto> findShopExtentionByCondition(Map<String, Object> params) {
		List<ShopExtensionDto> result;
		Integer key = params.toString().intern().hashCode();
		result =  (List<ShopExtensionDto>) cache.getIfPresent(key);
		if (result != null)
			return result;
		result = shopService.findShopExtentionByCondition(params);
		cache.put(key, result);
		return result;
	}
}