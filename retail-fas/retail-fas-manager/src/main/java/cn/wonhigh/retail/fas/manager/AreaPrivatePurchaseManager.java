/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaPrivatePurchaseManager  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-11-7 上午11:00:22  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SelfPurchaseDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.manager.BaseCrudManager;

public interface AreaPrivatePurchaseManager extends BaseCrudManager {

	/**
	 * 查询入库合计行
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(Map<String, Object> params);

	/**
	 * @param dto
	 * @return 
	 */
	int modifyCost(SelfPurchaseDto dto);

}
