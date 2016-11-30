package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillBuyBalanceManager extends BaseCrudManager {
	int selectSalerCompanyCount(Map<String,Object> params);
	
	int selectBuyerCompanyCount(Map<String,Object> params);
	
	List<BillBuyBalance> selectSalerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	List<BillBuyBalance> selectBuyerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	BillBuyBalance selectSpecifyItemCostCount(Map<String, Object> params);
	
	List<BillBuyBalance> selectSpecifyItemCost(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);

}