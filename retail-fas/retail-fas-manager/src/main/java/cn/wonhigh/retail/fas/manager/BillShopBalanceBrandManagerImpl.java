package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceBrand;
import cn.wonhigh.retail.fas.service.BillShopBalanceBrandService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-10-21 15:01:41
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
@Service("billShopBalanceBrandManager")
class BillShopBalanceBrandManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceBrandManager {
    @Resource
    private BillShopBalanceBrandService billShopBalanceBrandService;

    @Override
    public BaseCrudService init() {
        return billShopBalanceBrandService;
    }

	@Override
	public BillShopBalanceBrand getSumBalanceBrand(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceBrandService.getSumBalanceBrand(params);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}