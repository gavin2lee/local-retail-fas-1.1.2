package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.common.model.ShopNameReplace;
import cn.wonhigh.retail.fas.service.ShopNameReplaceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-01-06 17:24:59
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
@Service("shopNameReplaceManager")
class ShopNameReplaceManagerImpl extends BaseCrudManagerImpl implements ShopNameReplaceManager {
    @Resource
    private ShopNameReplaceService shopNameReplaceService;

    @Override
    public BaseCrudService init() {
        return shopNameReplaceService;
    }

	@Override
	public ShopNameReplace selectReplaceName(String shopNo, String brandUnitNo)
			throws ManagerException {
		try {
			return shopNameReplaceService.selectReplaceName(shopNo, brandUnitNo);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
}