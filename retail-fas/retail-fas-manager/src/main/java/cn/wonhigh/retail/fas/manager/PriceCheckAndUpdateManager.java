/**
 * title:PriceCheckAndUpdateManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2016-8-2 下午3:06:03
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 
 */
public interface PriceCheckAndUpdateManager extends BaseCrudManager {

	/**
	 * @param params
	 * @return
	 */
	int findHqSaleBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findHqSaleBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaSaleBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaSaleBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaBuyBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaBuyBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * 更新出库单据单价
	 * @param params
	 * @return
	 * @throws ManagerException 
	 * @throws ServiceException 
	 */
	int modifySaleBillCost(Map<String, Object> params) throws ServiceException, ManagerException;

	/**
	 * 更新出库单据单价
	 * @param params
	 * @return
	 * @throws ManagerException 
	 * @throws ServiceException 
	 */
	int modifyBuyBillCost(Map<String, Object> params) throws ServiceException, ManagerException;

	/**
	 * @param params
	 * @return
	 */
	int findHqBuyBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqBuyBillCostIsZeroByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findHqBuyBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqBuyBillCostIsDiffByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findHqSaleBillCostIsNullCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqSaleBillCostIsNullByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaBuyDiffOutBillCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyDiffOutBillByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

}
