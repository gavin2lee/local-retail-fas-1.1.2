/**  
 *   
 * 项目名称：retail-fas-service  
 * 类名称：HqAreaReconciliationDtlService  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2015-3-9 下午12:08:37  
 * @version       
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.HqAreaReconciliationDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface HqAreaReconciliationDtlService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	int findShipmentCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> findShipmentByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> findTotalRow(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> findShipmentTotalRow(
			Map<String, Object> params);

}
