package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
public interface SupplierGroupManager extends BaseCrudManager {

	/**
	 * 选择厂商组列表
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getGroupNoAndName(SupplierGroup supplierGroup) throws ManagerException;

	public int save(SupplierGroup dto, Map<CommonOperatorEnum, List<SupplierGroupRel>> params) 
			throws ManagerException;

	public void importNoGroup(String groupNo, List<Object> lstItem) throws ManagerException;
	
}