package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public interface SettlePathDtlManager extends BaseCrudManager {
	
	/**
	 * 查询可选择的结算公司数量
	 * @param params 查询参数
	 * @return 数量
	 * @throws ManagerException 异常
	 */
	int findCompanyCount(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 分页查询可选择的结算公司
	 * @param page 分页对象
	 * @param orderByField 排序字段
	 * @param orderBy 排序
	 * @param params 查询参数
	 * @return 结算公司集合
	 * @throws ManagerException 异常
	 */
	List<Object> findCompanyPage(SimplePage page, String orderByField, String orderBy, Map<String,Object> params)
			throws ManagerException;
}