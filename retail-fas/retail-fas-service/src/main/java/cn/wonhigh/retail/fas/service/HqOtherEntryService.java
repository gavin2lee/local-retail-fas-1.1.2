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

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 
 */
public interface HqOtherEntryService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findTotalRow(Map<String, Object> params);

}
