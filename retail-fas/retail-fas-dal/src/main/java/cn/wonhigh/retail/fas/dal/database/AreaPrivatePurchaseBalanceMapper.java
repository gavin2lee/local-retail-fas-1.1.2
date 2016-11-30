/**  
*   
* 项目名称：retail-fas-dal  
* 类名称：AreaPrivatePurchaseBalanceMapper  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-11-7 上午10:54:42  
* @version       
*/ 
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface AreaPrivatePurchaseBalanceMapper extends BaseCrudMapper {
	/**
	 * 修改结算单状态
	 * @param params
	 * @return
	 */
	public int updateBillStatus(@Param("params") Map<String, Object> params);

	/**
	 * 删除结算单
	 * @param params
	 * @return
	 */
	public int deleteBalanceBill(@Param("billNo") String billNo);

	/**
	 * 更新sale表的balance_no为null
	 * @param string
	 * @return 
	 */
	int updateBuyBalanceNoToNull(@Param("billNo")String billNo);

	/**
	 * 查询地区自购结算单
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectAreaPrivatePurchaseBalanceBill(
			BillBalance billBalance);

	/**
	 * 回写balance_no到自购入库明细
	 * @param bill
	 */
	public void updateBuyBalanceNo(BillBalance bill);

	/**
	 * 查询结算单金额合计
	 * @return
	 */
	public List<BillBalance> selectTotalRow(
			@Param("params") Map<String, Object> params);

	/**
	 * 查询匹配到的结算单
	 * @param params
	 * @return
	 */
	public List<BillBalance> selectMatchedBalanceBill(@Param("params")Map<String, Object> params);
	

	/**
	 * 回写balance_no到自购入库明细(选单)
	 * @param params
	 */
	public void updateBuyBalanceNoBySelect(@Param("params")Map<String, Object> params);

}
