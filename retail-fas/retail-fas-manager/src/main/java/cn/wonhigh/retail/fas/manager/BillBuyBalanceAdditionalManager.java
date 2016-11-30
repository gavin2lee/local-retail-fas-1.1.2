package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-06-06 10:02:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface BillBuyBalanceAdditionalManager extends BaseCrudManager {

	List<BillBuyBalanceAdditionDto> selectByParams(Map<String, Object> params) throws ManagerException;

	List<BillBuyBalanceAdditionDto> findBaroqueBillByPage(com.yougou.logistics.base.common.utils.SimplePage arg0,
			java.lang.String arg1, java.lang.String arg2, java.util.Map<String, Object> arg3) throws ManagerException;

	List<BillBuyBalanceAdditionDto> findBaroqueRegionCostBill(com.yougou.logistics.base.common.utils.SimplePage arg0,
			java.lang.String arg1, java.lang.String arg2, java.util.Map<String, Object> arg3) throws ManagerException;

	List<BillBuyBalanceAdditionDto> findGroupBaroqueRegionCostBill(
			com.yougou.logistics.base.common.utils.SimplePage arg0, java.lang.String arg1, java.lang.String arg2,
			java.util.Map<String, Object> arg3) throws ManagerException;

	List<BillBuyBalanceAdditionDto> findBaroqueDistributeCostBill(
			com.yougou.logistics.base.common.utils.SimplePage arg0, java.lang.String arg1, java.lang.String arg2,
			java.util.Map<String, Object> arg3) throws ManagerException;

	Integer getCount(Map<String, Object> params) throws ManagerException;

	Integer findGroupBaroqueDistributeCostBillCount(Map<String, Object> params) throws ManagerException;

	Integer findBaroqueRegionCostBillCount(Map<String, Object> params) throws ManagerException;

	Integer findGroupBaroqueRegionCostBillCount(Map<String, Object> params) throws ManagerException;
}