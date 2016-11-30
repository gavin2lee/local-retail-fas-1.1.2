package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Supplier;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
public interface SupplierService extends BaseCrudService {
	/**
	 * 查询未分组的供应商
	 * @author wang.sm
	 * @date  2015-07-30 10:58:20
	 * @version 0.7.6
	 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
	 */
	public List<Supplier> findByNoGroup(SimplePage page,Map<String, Object> params);
	/**
	 * 查询未分组的供应商总数
	 * @author wang.sm
	 * @date  2015-07-30 10:58:20
	 * @version 0.7.6
	 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
	 */
	public int findByNoGroupCount(Map<String, Object> params);
}