/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaAmongTransferManager  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-10-24 下午3:16:47  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface AreaAmongTransferManager extends BaseCrudManager {
	/**
	 * 查询出库明细合计行
	 * @param params
	 * @return
	 */
	List<TotalDto> findTotalRow(Map<String, Object> params);

	/**
	 * 查询汇总明细
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findGatherListByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * 查询汇总数量
	 * @param params
	 * @return
	 */
	int findGatherCount(Map<String, Object> params);

	/**
	 * 收发汇总数量查询
	 * @param params
	 * @return
	 */
	int findSummaryCount(Map<String, Object> params);

	/**
	 * 收发汇总查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findSummaryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * 收发汇总金额查询
	 * @param params
	 * @return
	 */
	List<TotalDto> findSummaryTotalRow(Map<String, Object> params);

}
