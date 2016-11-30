package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.wonhigh.retail.fas.common.model.ShopNameReplace;

import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.ShopNameReplaceMapper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-01-06 17:24:59
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("shopNameReplaceService")
class ShopNameReplaceServiceImpl extends BaseCrudServiceImpl implements ShopNameReplaceService {
	@Resource
	private ShopNameReplaceMapper shopNameReplaceMapper;
	Cache<String, ShopNameReplace> cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS).build();;

	@Override
	public BaseCrudMapper init() {
		return shopNameReplaceMapper;
	}

	@Override
	public <ModelType> int add(ModelType model) throws ServiceException {
		ShopNameReplace shopNameReplace = (ShopNameReplace) model;
		shopNameReplace.setId(UUIDGenerator.getUUID());
		int count = shopNameReplaceMapper.insertSelective(shopNameReplace);
		return count;
	}

	private static boolean loaded = false;
	private ShopNameReplace shopNameReplace = new ShopNameReplace();
	@Override
	public ShopNameReplace selectReplaceName(String shopNo, String brandUnitNo) throws ServiceException {
		if (shopNo == null || brandUnitNo == null || "9999".equalsIgnoreCase(shopNo))
			return null;
		String key = shopNo + "&" + brandUnitNo;
		ShopNameReplace item = cache.getIfPresent(key);
		if (item == null) {
			if (!loaded) {
				loadData();
			}
			item = cache.getIfPresent(key);
			if (item == null) {
				item = shopNameReplaceMapper.selectReplaceName(shopNo, brandUnitNo);
				if( item == null )
					item = shopNameReplace;
				cache.put(key, item);
			}

			item = cache.getIfPresent(key);
		}
		if( shopNameReplace.equals(item))
			return null;
		return item;
	}

	private synchronized void loadData() {
		List<ShopNameReplace> list = shopNameReplaceMapper.selectReplaceNames();
		for (ShopNameReplace item : list) {
			String key = item.getShopNo() + "&" + item.getBrandUnitNo();
			cache.put(key, item);
		}
		loaded = true;
	}

}