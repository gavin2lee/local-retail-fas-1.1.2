/**
 * title:HqInsteadOfBuyBalanceService.java
 * package:cn.wonhigh.retail.fas.service
 * description:总部代采结算单
 * auther:user
 * date:2015-4-11 下午4:32:59
 */
package cn.wonhigh.retail.fas.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface HqInsteadOfBuyBalanceService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);
	
	/**
	 * 总部代采查询结算单
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findHqBalanceList(BillBalance billBalance);
	
	/**
	 * 保存总部代采结算单
	 * @param lstBillBalance
	 * @param billBalance
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	void addHqBalanceBill(List<BillBalance> lstBillBalance, BillBalance billBalance) throws IllegalAccessException, InvocationTargetException, ServiceException, Exception;

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
	 * 扣项调整总部代采
	 * @param lstItem
	 * @param billBalance
	 * @return
	 */
	BillBalance modifyHqDeducation(List<Object> lstItem, BillBalance billBalance);
	
	/**
	 * 查询匹配的总部代采结算单
	 * @param billType
	 * @param billBalance
	 * @return
	 */
	List<BillBalance> findMatchedHqBalanceBill(
			BillBalance billBalance);
	
	/**
	 * 保存总部代采结算单(批量)
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addHqBalanceBillByBatch(List<BillBalance> billBalanceList,BillBalance formData) throws ServiceException, Exception;

	/**
	 * 删除结算单
	 * @param ids
	 * @return
	 */
	int deleteBalanceBill(String[] ids) throws ServiceException;
	
	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBillStatus(String[] ids, Map<String, Object> params);

	/**
	 * 更新单价
	 * @param formData
	 * @return
	 */
	int modifyBillCost(BillBalance formData);

}
