package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.ShopCheck;
import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.ShopCheckService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("shopCheckManager")
class ShopCheckManagerImpl extends BaseCrudManagerImpl implements ShopCheckManager {
    @Resource
    private ShopCheckService shopCheckService;

    @Override
    public BaseCrudService init() {
        return shopCheckService;
    }

	@Override
	public List<ShopCheck> findShopCheckByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return shopCheckService.findShopCheckByPage(page, sortColumn, sortOrder, params);
	}

	@Override
	public Integer findShopCheckByPageCount(Map<String, Object> params) {
		return shopCheckService.findShopCheckByPageCount(params);
	}

	@Override
	public List<ShopCheckSet> findShopCheckNos(Map<String, Object> params) {
		return shopCheckService.findShopCheckNos(params);
	}

	@Override
	public int updateData(ShopCheck shopCheck) {
		SystemUser user = CurrentUser.getCurrentUser();
		shopCheck.setUpdateUser(user.getLoginName());
		shopCheck.setUpdateTime(new Date(new Date().getTime()));
		return shopCheckService.updateData(shopCheck);
	}

	@Override
	public List<String> findShops(Map<String, Object> params) {
		return shopCheckService.findShops(params);
	}

	@Override
	public ShopCheck findShopCheck(Map<String, Object> params) {
		return shopCheckService.findShopCheck(params);
	}

	@Override
	public boolean addShopCheck(List<ShopCheck> list) throws ManagerException {
		try {
			if(list!=null &&list.size()>0){
				for (ShopCheck shopCheck:list) {
					shopCheck.setId(UUIDGenerator.getUUID());
					this.add(shopCheck);
				}
			}
		} catch (ManagerException e) {
			throw new ManagerException(e.getMessage(), e);

		}
		return true;
	}

}