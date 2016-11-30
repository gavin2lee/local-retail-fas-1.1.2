package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.vo.TreeJson;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BrandManager extends BaseCrudManager {
	
	/**
	 * 查询品牌及其品牌部 用于树形品牌查询控件
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<TreeJson> selectBrandWithBrandUnit(Map<String, Object> params) throws ManagerException;
}