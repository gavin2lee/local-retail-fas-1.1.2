package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

public interface WholesaleRemaingManager {

	public abstract <ModelType> int saveWholesaleCustomerRemainingInfo(ModelType model, int num)throws ManagerException, NumberFormatException, Exception;
	
	public abstract <ModelType> void saveWholesaleCustomerDtl(WholesaleCustomerRemainingSum remainingSum, ModelType model, int num)throws ServiceException, NumberFormatException, Exception;
	
}
