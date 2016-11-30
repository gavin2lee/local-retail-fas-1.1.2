/**  
*   
* 项目名称：retail-fas-dal  
* 类名称：BillAreaBalanceMapper  
* 类描述：地区结算
* 创建人：ouyang.zm  
* 创建时间：2014-10-20 下午1:58:19  
* @version       
*/ 
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface BillAreaBalanceMapper extends BaseCrudMapper {
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
	int updateSaleBalanceNoToNull(@Param("billNo")String billNo);

	/**
	 * 更新buy表的balance_no为null
	 * @param string
	 * @return 
	 */
	int updateBuyBalanceNoToNull(@Param("billNo")String billNo);
	
	/**
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectBalanceBill(BillBalance billBalance);

	/**
	 * 回写balance_no到sale表
	 * @param bill
	 */
	public void updateSaleBalanceNo(BillBalance bill);
	/**
	 * 回写balance_no到buy表
	 * @param bill
	 */
	public void updateBuyBalanceNo(BillBalance bill);

	/**
	 * 结算单合计行
	 * @return
	 */
	public List<BillBalance> selectTotalRow(
			@Param("params") Map<String, Object> params);

	/**
	 * 查询收款金额合计
	 * @param params
	 * @return
	 */
	public List<TotalDto> selectBalancePaymentTotal(
			@Param("params") Map<String, Object> params);

	/**
	 * 查询匹配的结算单(批量)
	 * @param map
	 * @param billBalance
	 */
	public List<BillBalance> selectMatchedBalanceBill(@Param("params")Map<String, Object> params);

	/**
	 * 回写结算单号到sale表(选单)
	 * @param billNo
	 * @param billNo2
	 */
	public void updateSaleBalanceNoBySelect(@Param("params")Map<String, Object> params);
	
	/**
	 * 回写结算单号到buy表(选单)
	 * @param billNo
	 * @param billNo2
	 */
	public void updateBuyBalanceNoBySelect(@Param("params")Map<String, Object> params);
	
}
