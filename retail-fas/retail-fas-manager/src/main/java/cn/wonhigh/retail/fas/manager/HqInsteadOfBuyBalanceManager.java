/**
 * title:HqInsteadOfBuyBalanceManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:总部代采结算单处理
 * auther:user
 * date:2015-4-11 下午4:31:34
 */
package cn.wonhigh.retail.fas.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface HqInsteadOfBuyBalanceManager extends BaseCrudManager {

	/**
	 * 查询结算单金额合计
	 * @param params
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);
	
	/**
	 * 查询总部代采结算单(选单)
	 * @param map
	 * @param formData
	 * @return
	 */
	BillBalance findBalanceBillBySelect(Map<String, List<Object>> map,
			BillBalance formData);
	
	/**
	 * 查询总部代采结算单
	 * @param formData
	 * @return
	 */
	BillBalance findHqBalanceBill(BillBalance formData);
	
	/**
	 * 保存总部代采结算单
	 * @param billBalance
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	BillBalance addHqBalanceBill(BillBalance billBalance) throws IllegalAccessException, InvocationTargetException, ServiceException, Exception;
	
	/**
	 * 保存结算单(选单)
	 * @param lstItem
	 * @param billBalance
	 * @return
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addHqBalanceBillBySelect(List<Object> lstItem,
			BillBalance billBalance) throws ServiceException, Exception;
	
	/**
	 * 扣项调整总部代采结算
	 * @param lstItem
	 * @param billBalance
	 * @return
	 */
	BillBalance modifyHqDeducation(List<Object> lstItem, BillBalance billBalance);
	
	/**
	 * 保存总部代采结算单(批量)
	 * @param billType
	 * @param fromPage
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addHqBalanceBillByBatch(BillBalance fromPage) throws ServiceException, Exception;
	
	/**
	 * 删除结算单
	 * @param ids
	 * @return
	 */
	int deleteBalanceBill(String[] ids) throws ManagerException;
	
	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBillStatus(String[] ids, Map<String,Object> params);

	/**
	 * 更新单据价格
	 * @param formData
	 * @return
	 */
	int modifyBillCost(BillBalance formData);

}
