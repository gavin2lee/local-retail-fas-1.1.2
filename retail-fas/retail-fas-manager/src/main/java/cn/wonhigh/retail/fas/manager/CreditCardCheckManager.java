package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.model.CreditCardCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
public interface CreditCardCheckManager extends BaseCrudManager {
		
	public List<CreditCardCheck> getShopSaleDetail(String terminalNumber,String outDate) throws ManagerException;
	
	public CreditCardCheck findShopSaleDetailCount(Map<String,Object> params) throws ManagerException;
	
	public List<CreditCardCheck> findShopSaleDetailList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ManagerException;

	public List<CreditCardCheck> setCreditCardCheckProperties(List<CreditCardCheck> list, String companyNo) throws ManagerException;
}