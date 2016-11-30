package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ShopGroupDto;
import cn.wonhigh.retail.fas.common.model.ShopGroup;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface ShopGroupService extends BaseCrudService {
	
	/**
	 * 根据店铺编码获取店铺分组信息
	 * @param params
	 * @return
	 */
	public List<ShopGroup> getShopGroupNoByShopNo(Map<String, Object> params) throws ServiceException;

	public List<ShopGroup> findBizByDtlShopNo(Map<String, Object> groupMap) throws ServiceException;

	public List<ShopGroup> findClientQueryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;

	public int findClientQueryCount(Map<String, Object> params) throws ServiceException;
	
	public List<ShopGroupDto> findByExport(SimplePage page,String orderByField, 
			String orderBy,Map<String, Object> params)throws ServiceException ;
	
	public int findByExportCount(Map<String, Object> params)throws ServiceException ;
	
}