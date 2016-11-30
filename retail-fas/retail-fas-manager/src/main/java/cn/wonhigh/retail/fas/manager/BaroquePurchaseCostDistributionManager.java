package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface BaroquePurchaseCostDistributionManager extends BaseCrudManager {

	// boolean distributePurchaseCost(List<RegionCostCaculatorDto>
	// regionCostCaculatorDtoList, BigDecimal purchaseFee)
	// throws ManagerException;

	boolean distributePurchaseCost(String originalNos, BigDecimal purchaseFee) throws ManagerException;
}
