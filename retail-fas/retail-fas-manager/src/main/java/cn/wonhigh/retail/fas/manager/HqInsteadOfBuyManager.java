/**
 * title:HqInsteadOfBuyManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:总部代采入库明细处理
 * auther:user
 * date:2015-4-11 下午4:14:53
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface HqInsteadOfBuyManager extends BaseCrudManager {

	/**
	 * 查询明细合计行
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(
			Map<String, Object> params);

	/**
	 * 查询入库明细数量
	 * @param params
	 * @return
	 */
	int findEntryCount(Map<String, Object> params);

	/**
	 * 查询入库明细
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findEntryByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params);

	/**
	 * 查询入库明细合计行
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findEntryTotalRow(Map<String, Object> params);

}
