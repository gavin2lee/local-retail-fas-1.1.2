package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CashCheck;
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
public interface CashCheckService extends BaseCrudService {
	CashCheck findShopSaleDetailCount(Map<String, Object> params) throws ServiceException;

	List<CashCheck> findShopSaleDetailList(SimplePage page, String orderBy, String orderByField, Map<String, Object> params) throws ServiceException;
	
	List<CashCheck> queryCashCheckDetail(Map<String, Object> params) throws ServiceException;

	BigDecimal queryActualIncomeAmount(Map<String, Object> params) throws ServiceException;
	
	BigDecimal querySystemIncomeAmount(Map<String, Object> params) throws ServiceException;

}