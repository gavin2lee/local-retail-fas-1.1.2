/**  
 *   
 * 项目名称：retail-fas-dal  
 * 类名称：BaroqueBillBalanceMapper  
 * 类描述：巴洛克结算单
 * 创建人：liu.jp  
 * 创建时间：2016-5-13 下午1:58:19  
 * @version       
 */

package cn.wonhigh.retail.fas.dal.database;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface BaroqueBillBalanceMapper extends BaseCrudMapper {
	
	/**
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectBalanceBill(BillBalance billBalance);
	
	/**
	 * (根据结算单号)更新结算单状态
	 * @param bill
	 * @return
	 */
	public int updateStatus(BillBalance bill);
	
	/**
	 * (根据结算单号)审核结算单
	 * @param bill
	 * @return
	 */
	public int verify(BillBalance bill);
}
