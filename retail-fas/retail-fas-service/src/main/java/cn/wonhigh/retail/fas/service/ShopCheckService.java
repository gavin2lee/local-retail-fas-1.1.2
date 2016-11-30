package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ShopCheck;
import cn.wonhigh.retail.fas.common.model.ShopCheckSet;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-09-22 14:01:10
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
public interface ShopCheckService extends BaseCrudService {
	/**
	 * 根据公司查询公司旗下店铺检查项登记情况
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopCheck> findShopCheckByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);
	
	public List<ShopCheckSet> findShopCheckNos(Map<String, Object> params);
	
	/**
	 * 根据公司查询公司旗下店铺检查项登记总计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Integer findShopCheckByPageCount(Map<String, Object> params);
	
	/**
	 * 修改检查编码对应的结果
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int updateData(ShopCheck shopCheck);
	
	/**
	 * 根据组合条件获取店铺列表
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<String> findShops(Map<String, Object> params);
	
	/**
	 * 根据检查编码查询
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public ShopCheck findShopCheck(Map<String, Object> params);
}