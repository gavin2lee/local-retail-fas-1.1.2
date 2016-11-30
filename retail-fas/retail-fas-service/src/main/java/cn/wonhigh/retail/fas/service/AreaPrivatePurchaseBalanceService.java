/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaPrivatePurchaseService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-9-29 下午4:24:12  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface AreaPrivatePurchaseBalanceService extends BaseCrudService {

	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBillStatus(String[] ids, Map<String, Object> params);

	/**
	 * 删除结算单
	 * @param ids
	 * @return
	 */
	int deleteBalanceBill(String[] ids) throws ServiceException;

	/**
	 * 查询结算单
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findBalanceList(BillBalance billBalance);
	
	/**
	 * 保存结算单
	 * @param lstBillBalance
	 * @param billBalance
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	void addBalanceBill(List<BillBalance> lstBillBalance, BillBalance billBalance) throws IllegalAccessException, InvocationTargetException, ServiceException, Exception;
	

	/**
	 * 查询结算单金额合计
	 * @param params
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);

	/**
	 * 查询匹配的结算单
	 * @param billType
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findMatchedBalanceBill(
			BillBalance billBalance);
	

	/**
	 * 保存地区自购结算单(批量)
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillByBatch(List<BillBalance> billBalanceList,BillBalance formData) throws ServiceException, Exception;
	

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
	 * 扣项调整地区自购
	 * @param lstItem
	 * @param billBalance
	 * @return
	 */
	BillBalance modifyDeducation(List<Object> lstItem, BillBalance billBalance);
	

}
