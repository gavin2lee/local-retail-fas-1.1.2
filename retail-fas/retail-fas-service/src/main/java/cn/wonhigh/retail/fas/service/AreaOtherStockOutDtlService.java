/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaOtherStockOutDtlService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午2:17:18  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.service.BaseCrudService;

public interface AreaOtherStockOutDtlService extends BaseCrudService {
	/**
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findTotalRow(Map<String, Object> params);

}
