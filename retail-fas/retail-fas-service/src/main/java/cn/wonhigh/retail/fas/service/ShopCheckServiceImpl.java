package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopCheck;
import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
import cn.wonhigh.retail.fas.dal.database.ShopCheckMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-09-22 14:01:10
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
@Service("shopCheckService")
class ShopCheckServiceImpl extends BaseCrudServiceImpl implements ShopCheckService {
    @Resource
    private ShopCheckMapper shopCheckMapper;

    @Override
    public BaseCrudMapper init() {
        return shopCheckMapper;
    }

	@Override
	public List<ShopCheck> findShopCheckByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return shopCheckMapper.findShopCheckByPage(page, sortColumn, sortOrder, params);
	}

	@Override
	public Integer findShopCheckByPageCount(Map<String, Object> params) {
		return shopCheckMapper.findShopCheckByPageCount(params);
	}

	@Override
	public List<ShopCheckSet> findShopCheckNos(Map<String, Object> params) {
		return shopCheckMapper.findShopCheckNos(params);
	}

	@Override
	public int updateData(ShopCheck shopCheck) {
		return shopCheckMapper.updateData(shopCheck);
	}

	@Override
	public List<String> findShops(Map<String, Object> params) {
		return shopCheckMapper.findShops(params);
	}

	@Override
	public ShopCheck findShopCheck(Map<String, Object> params) {
		return shopCheckMapper.findShopCheck(params);
	}
}