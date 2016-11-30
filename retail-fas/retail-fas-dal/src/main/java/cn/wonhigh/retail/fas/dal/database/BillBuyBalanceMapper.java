package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleUnionBuyBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-17 14:43:51
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
public interface BillBuyBalanceMapper extends BaseCrudMapper {
	int selectSalerCompanyCount(@Param("params")Map<String,Object> params);
	
	int selectBuyerCompanyCount(@Param("params")Map<String,Object> params);
	
	List<BillBuyBalance> selectSalerCompany(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	List<BillBuyBalance> selectBuyerCompany(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	int findSplitCount(@Param("params")Map<String, Object> params);
	
	List<Object> selectEnterByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params) ;

	int selectEnterCount(@Param("params")Map<String,Object> params) ;

	int updateBalanceInfoById(BillBuyBalance bill);

	List<Object> selectEnterFooter(@Param("params") Map<String, Object> params);
	
	int updateBuyBalanceCost(Map<String,Object> obj);
	
	List<BillSaleUnionBuyBalance> selectUnionBalances(Map<String,Object> params);
	
	Integer selectUnionBalancesCount(Map<String,Object> params);

	int selectAreaEnterCount(@Param("params") Map<String, Object> params);

	List<Object> selectAreaEnterByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<Object> selectAreaEnterFooter(@Param("params") Map<String, Object> params);
	
	List<BillBuyBalance> selectPurchaseRegionCost(@Param("params") Map<String, Object> params);
	
	BillBuyBalance selectSpecifyItemCostCount(@Param("params") Map<String, Object> params);
	
	List<BillBuyBalance> selectSpecifyItemCost(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	void updateBuyBalanceNo(BillBalance obj);
	
	void clearBuyBalanceNo(BillBalance obj);

	int deleteByBillNo(String billNo);

}