/**  
*   
* 项目名称：retail-fas-dal  
* 类名称：BillAreaOtherBalanceMapper  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午1:41:23  
* @version       
*/ 
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface BillAreaOtherBalanceMapper extends BaseCrudMapper {

	/**
	 * 查询地区其他出库结算单
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectAreaAmogBalanceBill(BillBalance billBalance);

	/**
	 * 生成回写结算单编号到sale表
	 * @param bill
	 */
	public void updateSaleBalanceNo(BillBalance bill);

	/**
	 * 结算单金额合计
	 * @return
	 */
	public List<BillBalance> selectTotalRow(
			@Param("params") Map<String, Object> params);

	/**
	 * 查询匹配的结算单(批量)
	 * @param map
	 * @param billBalance
	 */
	public List<BillBalance> selectMatchedBalanceBill(@Param("params")Map<String, Object> params);

	/**
	 * 生成回写结算单编号(选单)
	 * @param billNo
	 * @param billNo2
	 */
	public void updateSaleBalanceNoBySelect(@Param("params")Map<String, Object> params);
}
