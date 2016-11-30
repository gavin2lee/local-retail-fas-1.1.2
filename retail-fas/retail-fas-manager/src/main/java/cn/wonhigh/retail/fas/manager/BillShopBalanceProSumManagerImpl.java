package cn.wonhigh.retail.fas.manager;

import java.util.List;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.service.BillShopBalanceProSumService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@Service("billShopBalanceProSumManager")
class BillShopBalanceProSumManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceProSumManager {
	
    @Resource
    private BillShopBalanceProSumService billShopBalanceProSumService;

    @Override
    public BaseCrudService init() {
        return billShopBalanceProSumService;
    }

	@Override
	public List<BillShopBalanceProSum> getSumAmount(Map<String, Object> params) throws ManagerException {
		try {
			return billShopBalanceProSumService.getSumAmount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}