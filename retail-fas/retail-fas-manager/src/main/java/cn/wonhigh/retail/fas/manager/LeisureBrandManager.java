/**
 * title:LeisureBrandManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2016-3-15 上午11:04:17
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 
 */
public interface LeisureBrandManager extends BaseCrudManager {

	/**
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findDynColumns(Map<String, Object> params);
	
	
	List<Map<String, Object>>  findSumByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params);

	List<Map<String, Object>> findTotalRow(Map<String, Object> params);
}
