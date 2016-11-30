/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaPrivatePurchaseService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-11-7 上午11:02:53  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SelfPurchaseDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.service.BaseCrudService;

public interface AreaPrivatePurchaseService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(Map<String, Object> params);

	/**
	 * 更新验收单价格
	 * @param dto
	 * @return
	 */
	int modifyReceiptBillCost(SelfPurchaseDto dto);

	/**
	 * 更新到货单价格
	 * @param dto
	 */
	void modifyAsnBillCost(SelfPurchaseDto dto);

	/**
	 * 查询原单号
	 * @param dto
	 * @return
	 */
	BillBuyBalance findAsnBill(SelfPurchaseDto dto);

}
