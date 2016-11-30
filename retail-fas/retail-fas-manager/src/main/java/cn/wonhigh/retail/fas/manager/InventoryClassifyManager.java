package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.yj
 * @date  2016-08-28 09:02:52
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
public interface InventoryClassifyManager extends BaseCrudManager {
	
	public int findInventoryClassInicationCount(Map<String, Object> params) throws ManagerException;
	
	public List<Map<String, Object>> findInventoryClassInicationByPage(SimplePage page, Map<String, Object> params) throws ManagerException;
	
	public List<PeriodBalance> findCateGoryCloumn(String sortColumn,String sortOrder,Map<String, Object> params) throws ManagerException;
	
	public List<Map<String, Object>> findInventoryClassInicationTotal(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询体育的存货分类汇总记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findPeInventoryClassInicationCount(Map<String, Object> params)throws ManagerException;
	
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
			SimplePage page, Map<String, Object> params)throws ManagerException;
	
	/**
	 * 查询体育的存货分类汇总记底部合计
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<Map<String, Object>> findPeInventoryClassInicationTotal(Map<String, Object> params)throws ManagerException;
	

}