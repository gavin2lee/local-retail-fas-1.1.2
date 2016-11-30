/**
 * title:OweTheGuestService.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-7-1 下午5:36:44
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.OweTheGuestInventoryDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 
 */
public interface OweTheGuestService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	List<OweTheGuestInventoryDto> findTotalRow(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findSumCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<OweTheGuestInventoryDto> findSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<OweTheGuestInventoryDto> findSumTotalRow(Map<String, Object> params);

}
