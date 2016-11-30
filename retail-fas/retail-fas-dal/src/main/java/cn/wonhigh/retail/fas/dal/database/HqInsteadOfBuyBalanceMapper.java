/**
 * title:HqInsteadOfBuyBalanceMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:总部代采结算单处理
 * auther:user
 * date:2015-4-11 下午4:35:53
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface HqInsteadOfBuyBalanceMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<BillBalance> selectTotalRow(@Param("params")Map<String, Object> params);

	/**
	 * 查询总部代采结算单
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectHqBuyBalanceBill(BillBalance billBalance);
	
	/**
	 * @param bill
	 */
	public void updateHqBuyBalanceNo(BillBalance bill);
	
	/**
	 * 回写balance_no到代采入库明细(选单)
	 * @param params
	 */
	public void updateBuyHqBalanceNoBySelect(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询匹配到的总部代采结算单
	 * @param params
	 * @return
	 */
	public List<BillBalance> selectMatchedHqBalanceBill(@Param("params")Map<String, Object> params);
	
	/**
	 * 更新sale表的balance_no为null
	 * @param string
	 * @return 
	 */
	int updateBuyBalanceNoToNull(@Param("billNo")String billNo);
	
	/**
	 * 删除结算单
	 * @param params
	 * @return
	 */
	public int deleteBalanceBill(@Param("billNo") String billNo);
	
	/**
	 * 修改结算单状态
	 * @param params
	 * @return
	 */
	public int updateBillStatus(@Param("params") Map<String, Object> params);

	/**
	 * 更新单价
	 * @param formData
	 * @return
	 */
	public int updateBillCost(BillBalance formData);
}
