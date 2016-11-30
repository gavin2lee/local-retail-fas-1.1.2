package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.Map;

import cn.wonhigh.retail.fas.manager.domain.TagPriceArgument;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface BaroquePurchaseRegionCostManager extends BaseCrudManager {

	boolean updateRegionCost(String originalNos, String itemNo) throws ManagerException;
	
	Map<String, Integer> updateRegionCost(String ids) throws ManagerException;
	
	boolean updateTagPrice(TagPriceArgument args) throws ManagerException;
	
	boolean updateBuyAdl(BigDecimal exchange,BigDecimal tariffRate,BigDecimal vatRate,String id) throws ManagerException;
}
