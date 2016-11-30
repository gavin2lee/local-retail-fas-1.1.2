package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceSet;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 门店结算差异生成方式配置表
 * @author yang.y
 * @date  2015-01-05 14:11:37
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
public interface BillShopBalanceSetManager extends BaseCrudManager {

	int saveAll(Map<CommonOperatorEnum, List<BillShopBalanceSet>> dataMap) throws ManagerException;
	
	List<BillShopBalanceSet> selectByParams(Map<String, Object> params) throws ManagerException;
}