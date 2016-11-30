package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.model.SupplierReturnProfit;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-19 10:35:13
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
public interface SupplierReturnProfitManager extends BaseCrudManager {
	
	int addSupplierReturnProfit(PayRelationship payRelationShip,BigDecimal rate,String type,Integer returnProfitType) throws ManagerException;
	
	public void deleteGenerateReturnProfit(PayRelationship payRelationShip,SupplierReturnProfit supplierReturnProfit) throws ManagerException;
	
	public List<PayRelationship> findReturnProfit(Map<String, Object> params) throws ManagerException;
}