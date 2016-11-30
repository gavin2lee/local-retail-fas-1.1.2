/**
 * title:AreaAmongStorageService.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-5-6 下午4:55:43
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.service.BaseCrudService;

public interface AreaAmongStorageService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(Map<String, Object> params);

}
