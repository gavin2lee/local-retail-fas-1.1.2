/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaAmongBalanceService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-9-24 下午12:12:02  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface AreaAmongBalanceService extends BaseCrudService {

	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBillStatus(String[] ids, Map<String,Object> params);

	/**
	 * 删除结算单
	 * @param ids
	 * @return
	 * @throws ServiceException 
	 */
	int deleteBalanceBill(String[] ids) throws ServiceException;

	/**
	 * 查询结算单
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findBalanceBillList(BillBalance billBalance);

	/**
	 * 保存结算单
	 * @param lstBillBalance
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	void addBalanceBill(List<BillBalance> lstBillBalance, BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 查询结算单金额合计
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);

	/**
	 * 查询收款金额合计
	 * @param params
	 * @return
	 */
	List<TotalDto> findReceivableAmountTotal(Map<String, Object> params);

	/**
	 * 查询匹配的结算单(批量)
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findMatchedBalanceList(BillBalance billBalance);

	/**
	 * 保存结算单(批量)
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillBatch(List<BillBalance> billBalanceList,BillBalance formData) throws ServiceException, Exception;

	/**
	 * 保存结算单(选单)
	 * @param lstItem
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String, Object> addBalanceBillBySelect(List<Object> lstItem, BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 扣项调整
	 * @param lstItem
	 * @param billBalance
	 * @return
	 */
	BillBalance modifyDeducation(List<Object> lstItem, BillBalance billBalance);

}
