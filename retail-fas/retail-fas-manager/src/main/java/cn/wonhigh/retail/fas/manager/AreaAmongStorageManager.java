/**
 * title:AreaAmongStorageManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-5-6 下午4:52:37
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.manager.BaseCrudManager;

public interface AreaAmongStorageManager extends BaseCrudManager {

	/**
	 * 查询合计行
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(Map<String, Object> params);

}
