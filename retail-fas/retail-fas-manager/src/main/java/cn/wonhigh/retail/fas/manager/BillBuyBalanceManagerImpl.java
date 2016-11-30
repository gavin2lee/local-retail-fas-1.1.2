package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
@Service("billBuyBalanceManager")
class BillBuyBalanceManagerImpl extends BaseCrudManagerImpl implements BillBuyBalanceManager {
    @Resource
    private BillBuyBalanceService billBuyBalanceService;

    @Override
    public BaseCrudService init() {
        return billBuyBalanceService;
    }

	@Override
	public List<BillBuyBalance> selectSalerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
		return billBuyBalanceService.selectSalerCompany(page, orderByField, orderBy, params);
	}

	@Override
	public List<BillBuyBalance> selectBuyerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
		return billBuyBalanceService.selectBuyerCompany(page, orderByField, orderBy, params);
	}

	@Override
	public int selectSalerCompanyCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return billBuyBalanceService.selectSalerCompanyCount(params);
	}

	@Override
	public int selectBuyerCompanyCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return billBuyBalanceService.selectBuyerCompanyCount(params);
	}

	@Override
	public List<BillBuyBalance> selectSpecifyItemCost(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billBuyBalanceService.selectSpecifyItemCost(page, orderByField, orderBy, params);
	}

	@Override
	public BillBuyBalance selectSpecifyItemCostCount(Map<String, Object> params) {
		return billBuyBalanceService.selectSpecifyItemCostCount(params);
	}
}