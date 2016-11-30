package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ShopGroupDto;
import cn.wonhigh.retail.fas.common.model.ShopGroup;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-23 15:42:05
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
public interface ShopGroupMapper extends BaseCrudMapper {
	
	/**
	 * 根据店铺编码获取店铺分组信息
	 * @param params
	 * @return
	 */
	public List<ShopGroup> getShopGroupNoByShopNo(@Param("params") Map<String, Object> params);

	public List<ShopGroup> findBizByDtlShopNo(@Param("params") Map<String, Object> groupMap);

	public List<ShopGroup> findClientQueryByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);

	public int findClientQueryCount(@Param("params") Map<String, Object> params);
	
	public int selectByExportCount(@Param("params") Map<String, Object> params);
	
	public List<ShopGroupDto> selectByExport(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
}