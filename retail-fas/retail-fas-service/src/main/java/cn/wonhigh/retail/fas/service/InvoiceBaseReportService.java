package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @date  2015-11-23 10:01:20
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
public interface InvoiceBaseReportService extends BaseCrudService {

	/**
	 * 查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
}