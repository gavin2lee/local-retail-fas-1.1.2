/**  
*   
* 项目名称：retail-fas-service  
* 类名称：HqOtherStockOutDtlService  
* 类描述：
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.service.BaseCrudService;

public interface HqOtherStockOutDtlService extends BaseCrudService {
	/**
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findTotalRow(Map<String, Object> params);

}
