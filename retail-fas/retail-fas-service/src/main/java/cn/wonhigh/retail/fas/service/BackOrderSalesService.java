package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BackOrderSales;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-09-01 18:15:52
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface BackOrderSalesService extends BaseCrudService {

	int selectCompanyOwerGuestCount(Map<String, Object> params) throws ServiceException;

	List<BackOrderSales> selectCompanyOwerGuestByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;

	int batchInsertCompanyOwerGuest(List<BackOrderSales> backOrderSales) throws ServiceException;

	int deleteCompanyOwerGuestCount(Map<String, Object> params) throws ServiceException;
	
}