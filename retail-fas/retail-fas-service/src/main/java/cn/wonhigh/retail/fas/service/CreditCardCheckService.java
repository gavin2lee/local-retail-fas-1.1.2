package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CreditCardCheck;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface CreditCardCheckService extends BaseCrudService {
	
	public List<CreditCardCheck> getShopSaleDetail(Map<String,Object> params) throws ServiceException;
	
	public CreditCardCheck findCreditCardCheckCount(Map<String,Object> params) throws ServiceException;
	
	public List<CreditCardCheck> findCreditCardCheckList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ServiceException;

	public BigDecimal getOnlieIncomeAmount(Map<String, Object> params) throws ServiceException;
}