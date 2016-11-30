package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SupplierRateSet;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-05-05 09:10:13
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
public interface SupplierRateSetService extends BaseCrudService {
	
	List<SupplierRateSet> selectByParams(Map<String, Object> params) throws ServiceException;
	
	Integer findCount()throws ServiceException;
	
	List<SupplierRateSet> findSupplierByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String,Object> params)throws ServiceException;

	boolean exist(String supplierNo) throws ServiceException;
}