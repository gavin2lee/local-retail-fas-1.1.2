package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.BillBuyBalanceAdditionalService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("baroquePurchaseCostDistributionManager")
class BaroquePurchaseCostDistributionManagerImpl extends BaseCrudManagerImpl implements
		BaroquePurchaseCostDistributionManager {
	@Resource
	private BillBuyBalanceAdditionalService billBuyBalanceAdditionalService;

	@Override
	public BaseCrudService init() {
		return billBuyBalanceAdditionalService;
	}

	@Override
	public boolean distributePurchaseCost(String originalNos, BigDecimal purchaseFee) throws ManagerException {
		try {
			return true;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}
