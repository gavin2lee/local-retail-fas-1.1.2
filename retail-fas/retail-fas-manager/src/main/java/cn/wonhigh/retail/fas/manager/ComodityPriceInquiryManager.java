package cn.wonhigh.retail.fas.manager;



import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface ComodityPriceInquiryManager extends BaseCrudManager {
	PurchasePrice findBalancePurchasePrice(Map<String,Object> params) throws ServiceException;
	
}
