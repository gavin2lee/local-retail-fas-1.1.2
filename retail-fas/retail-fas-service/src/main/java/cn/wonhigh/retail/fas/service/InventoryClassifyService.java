package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author wangxy
 * @date 2015-05-06 19:05:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface InventoryClassifyService extends BaseCrudService {

	int findInventoryClassInicationCount(Map<String, Object> params) throws ServiceException;
	
	List<Map<String, Object>> findInventoryClassInicationByPage(SimplePage page, Map<String, Object> params) throws ServiceException;
	
	List<PeriodBalance> findCateGoryCloumn(String orderByField,String orderBy,Map<String, Object> params)throws ServiceException;
	
	List<Map<String, Object>> findInventoryClassInicationTotal(Map<String, Object> params)throws ServiceException;
	
	/**
	 * 查询体育的存货分类汇总记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findPeInventoryClassInicationCount(Map<String, Object> params)throws ServiceException;
	
	/**
	 * 查询体育的存货分类汇总记信息
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<Map<String, Object>> findPeInventoryClassInicationByPage(
			SimplePage page, Map<String, Object> params)throws ServiceException;
	
	/**
	 * 查询体育的存货分类汇总记底部合计
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<Map<String, Object>> findPeInventoryClassInicationTotal(Map<String, Object> params)throws ServiceException;
	
}