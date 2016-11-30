/**
 * title:HqOtherEntryService.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-5-8 上午10:49:22
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 
 */
public interface AreaOtherStockInEntryService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findTotalRow(Map<String, Object> params);
	
	int selectDtlCount(Map<String, Object> params);

	List<BillBuyBalance> selectDtlByPage(SimplePage page, String orderByField, String orderBy, Map<String,Object> params);

	List<BillBuyBalance> selectDtlTotalRow(Map<String, Object> params);

}
