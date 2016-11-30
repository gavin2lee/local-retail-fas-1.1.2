/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaPrivatePurchaseManager  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-9-29 下午4:18:52  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface AreaPrivatePurchaseBalanceManager extends BaseCrudManager {
	/**
	 * 查询结算单合计行
	 * @param params
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);
	
	/**
	 * 查询结算单
	 * @param formData
	 * @return
	 */
	BillBalance findBalanceBill(BillBalance formData);
	
	/**
	 * 保存结算单
	 * @param billBalance
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	BillBalance addBalanceBill(BillBalance billBalance) throws IllegalAccessException, InvocationTargetException, ServiceException, Exception;
	
	/**
	 * 保存结算单(选单)
	 * @param lstItem
	 * @param billBalance
	 * @return
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance billBalance) throws ServiceException, Exception;
	
	/**
	 * 扣项调整自购结算
	 * @param lstItem
	 * @param billBalance
	 * @return
	 */
	BillBalance modifyDeducation(List<Object> lstItem, BillBalance billBalance);
	
	/**
	 * 保存地区自购结算单(批量)
	 * @param billType
	 * @param fromPage
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillByBatch(BillBalance fromPage) throws ServiceException, Exception;
	
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
	
}
