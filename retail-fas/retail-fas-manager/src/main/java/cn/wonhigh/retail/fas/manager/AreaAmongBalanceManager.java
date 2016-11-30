/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaAmongBalanceManager  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-9-24 下午12:08:06  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface AreaAmongBalanceManager extends BaseCrudManager {

	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBillStatus(String[] ids, Map<String,Object> map);

	/**
	 * 删除结算单
	 * @param ids
	 * @throws ManagerException 
	 */
	int deleteBalanceBill(String[] ids) throws ManagerException;

	/**
	 * 保存结算单
	 * @param billBalance
	 * @return
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	BillBalance addBalanceBill(BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 查询结算单合计行
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
	 * 批量生成结算单
	 * @param list
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String, Object> addBalanceBillByBatch(BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 保存结算单(选单)
	 * @param lstItem
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String, Object> addBalanceBillBySelect(List<Object> lstItem, BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 查询出库明细
	 * @param formData
	 * @return
	 */
	BillBalance findOutList(BillBalance formData);

	/**
	 * 扣项调整
	 * @param lstItem
	 * @param billBalance
	 * @return
	 */
	BillBalance modifyDeducation(List<Object> lstItem, BillBalance billBalance);
}
