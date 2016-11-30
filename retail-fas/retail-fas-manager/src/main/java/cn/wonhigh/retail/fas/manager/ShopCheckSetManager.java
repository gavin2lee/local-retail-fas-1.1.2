package cn.wonhigh.retail.fas.manager;

import java.util.List;
import cn.wonhigh.retail.fas.common.model.ShopCheckSet;

import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-21 15:14:36
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
public interface ShopCheckSetManager extends BaseCrudManager {
	/**
	 * 获取店铺编码流水号
	 */
	public List<ShopCheckSet> getSerialNo();
	
	/**
	 * 根据店铺编码获取公司检查项列表
	 * 
	 */
	public List<String> getCheckNo(String shopNo);

}