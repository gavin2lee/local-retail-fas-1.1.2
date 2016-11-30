package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
public interface BillSplitManager extends BaseCrudManager {
	
	/**
	 * 查询需要拆单的单据总数
	 * @param params
	 * @return
	 */
	int selectSplitBillCount(Map<String,Object> params);
	
	/**
	 * 查询需要拆单的单据明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectSplitBill(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	/**
	 * 查询结算路径
	 * @param params
	 * @return
	 */
	List<SettlePathDto> selectSettlePath(Map<String,Object> params);
	
	/**
	 * 调整结算主体（重新拆单）
	 * @param billList
	 * @param pathNo
	 * @return
	 * @throws ManagerException 
	 * @throws RpcException 
	 */
	Map<String,Object> splitData(List<Object> billList, String pathNo) throws ManagerException;
}