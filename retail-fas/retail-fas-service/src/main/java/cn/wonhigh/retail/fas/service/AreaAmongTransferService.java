/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaAmongTransferService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-10-24 下午3:20:12  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface AreaAmongTransferService extends BaseCrudService {

	/**
	 * 查询出库明细金额合计
	 * @param params
	 * @return
	 */
	List<TotalDto> findTotalRow(Map<String, Object> params);

	/**
	 * 查询出库汇总明细
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findGatherListByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findGatherCount(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findSummaryCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findSummaryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<TotalDto> findSummaryTotalRow(Map<String, Object> params);

}
