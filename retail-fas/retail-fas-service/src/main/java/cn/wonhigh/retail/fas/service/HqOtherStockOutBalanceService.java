/**  
*   
* 项目名称：retail-fas-service  
* 类名称：HqOtherStockOutBalanceService  
* 类描述：
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface HqOtherStockOutBalanceService extends BaseCrudService {

	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBalanceBillStatus(String[] ids, Map<String,Object> params);

	/**
	 * 删除结算单
	 * @param ids
	 * @return
	 * @throws ServiceException 
	 */
	int deleteBalanceBill(String[] ids) throws ServiceException;

	/**
	 * 查询结算单列表
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findBalanceBillList(BillBalance billBalance);

	/**
	 * 保存结算单
	 * @param lstBillBalance
	 * @param billBalance
	 * @throws Exception 
	 */
	void addBalanceBill(List<BillBalance> lstBillBalance, BillBalance billBalance) throws Exception;

	/**
	 * 查询结算单金额合计
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);

	/**
	 * 查询匹配的结算单(批量)
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findMatchedBalanceBill(BillBalance billBalance);

	/**
	 * 保存结算单(批量)
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillBatch(List<BillBalance> billBalanceList,BillBalance b) throws ServiceException, Exception;

	/**
	 * 保存结算单(选单)
	 * @param lstItem
	 * @param billBalance
	 * @return
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillBySelect(List<Object> lstItem, BillBalance billBalance) throws ServiceException, Exception;


}

