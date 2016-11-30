/**  
 *   
 * 项目名称：retail-fas-dal  
 * 类名称：AreaPrivatePurchaseMapper  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2014-11-7 上午11:05:01  
 * @version       
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.SelfPurchaseDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface AreaPrivatePurchaseMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectTotalRow(
			@Param("params") Map<String, Object> params);

	/**
	 * @param dto
	 * @return
	 */
	int updateReceiptBillCost(SelfPurchaseDto dto);

	/**
	 * @param dto
	 */
	void updateAsnBillCost(SelfPurchaseDto dto);

	/**
	 * @param dto
	 * @return
	 */
	BillBuyBalance selectAsnBill(SelfPurchaseDto dto);

}
