package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SystemParamSet;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-22 10:32:22
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
public interface SystemParamSetManager extends BaseCrudManager {
	/**
	 * 根据系统参数key查询参数值集合
	 * @param systemParamSet
	 * @param params
	 * @return
	 */
	public List<SystemParamSet> findSystemParamSetByParma(SystemParamSet systemParamSet,Map<String, Object> params)throws ManagerException;
	
	/**
	 * 根据系统参数key查询参数值
	 * @param systemParamSet
	 * @param params
	 * @return
	 */
	public String findSystemParamByParma(SystemParamSet systemParamSet,Map<String, Object> params)throws ManagerException;
	
}