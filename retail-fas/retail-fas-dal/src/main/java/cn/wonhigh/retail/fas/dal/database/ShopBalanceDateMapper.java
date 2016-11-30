package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-15 14:29:23
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
public interface ShopBalanceDateMapper extends BaseCrudMapper {	
	
	int updateBalanceDateSet(ShopBalanceDate shopBalanceDate);
	
	int updateBalanceBillAlready(ShopBalanceDate shopBalanceDate);

	int selectNewShopDateCount(@Param("params") Map<String, Object> params);

	List<ShopBalanceDate> selectNewShopDateByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
	int selectNoSetShopBalanceDateCount(@Param("params") Map<String, Object> params);
	
	List<ShopBalanceDate> selectNoSetShopBalanceDateByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
	int selectShopBalanceDatePartOfRightCount(@Param("params") Map<String, Object> params);

	List<ShopBalanceDate> selectShopBalanceDatePartOfRightByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
}