package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ShopGroupDto;
import cn.wonhigh.retail.fas.common.model.ShopGroup;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-22 11:41:25
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
public interface ShopGroupManager extends BaseCrudManager {

	Integer delete(List<Object> dtlList) throws ManagerException;

	ShopGroup add(ShopGroup obj, List<Object> insertedList) throws ManagerException;

	ShopGroup update(ShopGroup obj, List<Object> insertedList,
			List<Object> updatedList, List<Object> deletedList) throws ManagerException;

	Integer operate(List<Object> list) throws ManagerException;
	
	/**
	 * 通过店铺编码获取店铺分组信息
	 * @param shopNo
	 * @return
	 */
	public List<ShopGroup> getShopGroupNoByShopNo(String shopNo) throws ManagerException;

	/**
	 * 店铺分组 客户查询精灵
	 * @param shopNo
	 * @return
	 */
	public int findClientQueryCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 店铺分组 客户查询精灵
	 * @param shopNo
	 * @return
	 */
	public List<ShopGroup> findClientQueryByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询店铺分组下的店铺明细
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<ShopGroupDto> findByExport(SimplePage page,String orderByField, 
			String orderBy,Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询店铺分组下的店铺明细总数
	 * @param params
	 * @return
	 */
	public int findByExportCount(Map<String, Object> params) throws ManagerException;
	
}