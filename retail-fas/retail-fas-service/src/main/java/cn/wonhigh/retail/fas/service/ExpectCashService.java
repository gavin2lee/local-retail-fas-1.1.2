package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;



import cn.wonhigh.retail.fas.common.model.ExpectCash;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * ExpectCashService
 * @author tang.yc
 * @date  2014-08-26 16:05:20
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
public interface ExpectCashService extends BaseCrudService {
	/**
	 * @throws ServiceException 
	 * 查询预收款单
	 * @Title: findExpectCashByParams  
	 * @param @param params
	 * @param @return 
	 * @return List<ExpectCash> 
	 * @author: zhou.q1 
	 * @throws
	 */
	public List<ExpectCash> findExpectCashByParams(Map<String, Object> params) throws ServiceException;
			
	public List<ExpectCash> findExpectCashByParams(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params) throws ServiceException;
	
	public int countExpectCashByParams(Map<String, Object> params) throws ServiceException;
	
	public int modifyById(ExpectCash expectCash) throws ServiceException;
	
	public List<ExpectCash> processExpectCashBalanceDiff(Map<String, Object> params) throws ServiceException;
	
	public List<ExpectCash> processUseExpectCashBalanceDiff(Map<String, Object> params) throws ServiceException;
	
	public ExpectCash getExpectCashAmount(Map<String, Object> params) throws ServiceException;
}