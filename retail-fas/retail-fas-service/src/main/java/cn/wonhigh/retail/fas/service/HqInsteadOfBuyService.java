/**
 * title:HqInsteadOfBuyService.java
 * package:cn.wonhigh.retail.fas.service
 * description:总部代采入库明细业务处理
 * auther:user
 * date:2015-4-11 下午4:19:18
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface HqInsteadOfBuyService extends BaseCrudService {

	/**
	 * 查询明细合计行
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findEntryCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findEntryByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findEntryTotalRow(Map<String, Object> params);

}
