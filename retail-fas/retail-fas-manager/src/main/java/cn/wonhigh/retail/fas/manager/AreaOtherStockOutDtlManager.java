/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaOtherStockOutDtlManager  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午2:14:30  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.manager.BaseCrudManager;

public interface AreaOtherStockOutDtlManager extends BaseCrudManager {
	/**
	 * 查询出库合计行
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> findTotalRow(Map<String, Object> params);

}
