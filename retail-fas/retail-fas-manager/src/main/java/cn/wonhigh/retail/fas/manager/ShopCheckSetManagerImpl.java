package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
import cn.wonhigh.retail.fas.service.ShopCheckSetService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-21 15:14:36
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
@Service("shopCheckSetManager")
class ShopCheckSetManagerImpl extends BaseCrudManagerImpl implements ShopCheckSetManager {
    @Resource
    private ShopCheckSetService shopCheckSetService;

    @Override
    public BaseCrudService init() {
        return shopCheckSetService;
    }

	@Override
	public List<ShopCheckSet> getSerialNo() {
		return shopCheckSetService.getSerialNo();
	}

	@Override
	public List<String> getCheckNo(String shopNo) {
		return shopCheckSetService.getCheckNo(shopNo);
	}
}