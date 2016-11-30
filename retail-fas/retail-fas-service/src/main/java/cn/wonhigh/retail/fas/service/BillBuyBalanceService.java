package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjustDtl;
import cn.wonhigh.retail.fas.common.model.BillSaleUnionBuyBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
public interface BillBuyBalanceService extends BaseCrudService {
	int deleteByBillNo(String billNo) throws ServiceException;

	int selectSalerCompanyCount(Map<String,Object> params);
	
	int selectBuyerCompanyCount(Map<String,Object> params);
	
	List<BillBuyBalance> selectSalerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	List<BillBuyBalance> selectBuyerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);

	int findSplitCount(Map<String, Object> params);
	
	int updateBuyBalanceCost(Map<String,Object> obj) throws ServiceException;
	
	List<BillSaleUnionBuyBalance> selectUnionBalances(Map<String,Object> params) throws ServiceException;
	
	Integer selectUnionBalancesCount(Map<String,Object> params) throws ServiceException;
	
	List<BillBuyBalance> selectPurchaseRegionCost(Map<String,Object> params) throws ServiceException;
	
	BillBuyBalance selectSpecifyItemCostCount(Map<String,Object> params);
	
	List<BillBuyBalance> selectSpecifyItemCost(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	void updateBuyBalanceNo(BillBalance obj);
	
	void clearBuyBalanceNo(BillBalance obj);
}