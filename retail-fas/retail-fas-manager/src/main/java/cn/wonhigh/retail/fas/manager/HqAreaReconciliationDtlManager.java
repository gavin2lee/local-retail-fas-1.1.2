/**  
 *   
 * 项目名称：retail-fas-manager  
 * 类名称：HqAreaReconciliationDtlManager  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2015-3-9 上午11:59:31  
 * @version       
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.HqAreaReconciliationDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface HqAreaReconciliationDtlManager extends BaseCrudManager {

	/**
	 * 出货明细表数量
	 * @param params
	 * @return
	 */
	int findShipmentCount(Map<String, Object> params);

	/**
	 * 分页查询出货明细表
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> findShipmentByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * 查询合计行
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> findTotalRow(Map<String, Object> params);

	/**
	 * 查询出货明细合计行
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> findShipmentTotalRow(
			Map<String, Object> params);

}
