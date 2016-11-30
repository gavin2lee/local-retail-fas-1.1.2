/**
 * title:OweTheGuestManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-7-1 下午5:34:18
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.OweTheGuestInventoryDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 
 */
public interface OweTheGuestManager extends BaseCrudManager {

	/**
	 * 合计行
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
