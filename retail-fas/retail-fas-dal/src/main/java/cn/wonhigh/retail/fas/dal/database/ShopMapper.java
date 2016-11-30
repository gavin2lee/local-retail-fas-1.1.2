package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ShopExtensionDto;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBrand;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 14:23:50
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
public interface ShopMapper extends BaseCrudMapper {
	
	List<Shop> selectSubsiInfoList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	Shop selectSubsiInfo(Map<String,Object> params);
	
	ShopBrand selectShopBrandInfo(Map<String,Object> params);
	
	List<Shop> selectShopInfoByPayTypeWithoutDataAuthority(String payType);
	
	List<Shop> selectShopInfoListByShopNos(@Param("params")Map<String,Object> params);
	
	List<String> getShopBySelfCheckin(@Param("params")Map<String,Object> params);
	
	int selectShopAndOrganCount(@Param("params")Map<String,Object> params);
	
	List<Shop> selectShopAndOrganByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	List<ShopExtensionDto> fetchShopRegion();
	
	List<ShopExtensionDto> fetchShopOrgan(); 
	
	List<Shop> findMallInfo(@Param("params")Map<String, Object> params);
	
	List<ShopExtensionDto> findShopExtentionByCondition(@Param("params")Map<String,Object> params);
	
}