package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Organ;

import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-13 14:01:15
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
public interface OrganManager extends BaseCrudManager {
	
	/**
	 * 根据店铺编码获取管理城市信息
	 * @param params
	 * @return
	 */
	Organ getOrganByShopNo(Map<String,Object> params);
}