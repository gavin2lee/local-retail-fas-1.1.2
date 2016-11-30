package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SupplierRateSet;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface SupplierRateSetManager extends BaseCrudManager {
	
	int saveAll(Map<CommonOperatorEnum, List<SupplierRateSet>> dataMap) throws ManagerException;
	
	List<SupplierRateSet> selectByParams(Map<String, Object> params) throws ManagerException;
	
	Integer findCount()throws ManagerException;
	
	boolean exist(String supplierNo) throws ManagerException;
	
	List<SupplierRateSet> findSupplierByPage(com.yougou.logistics.base.common.utils.SimplePage arg0, java.lang.String arg1, java.lang.String arg2, java.util.Map<String,Object> arg3) throws ManagerException;
}