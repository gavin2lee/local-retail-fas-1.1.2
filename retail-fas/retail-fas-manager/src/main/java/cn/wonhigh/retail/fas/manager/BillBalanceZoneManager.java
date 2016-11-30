package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 地区批发销售出库单
 * 
 * @author yang.y
 */
public interface BillBalanceZoneManager extends BaseCrudManager {

	/**
	 * 新增地区批发结算单
	 * @param model 结算单
	 * @return 新增的对象
	 * @throws ManagerException 异常
	 */
	BillBalance addBill(BillBalance bill) throws ManagerException;
	
	/**
	 * 批量删除数据
	 * @param lstBill 待删除的数据集合
	 * @return 删除的数量
	 * @throws ManagerException 异常
	 */
	int delete(List<BillBalance> lstBill) throws ManagerException;
	
	/**
	 * 业务确认操作
	 * @param oList 待确认的数据集合(单个或批量确认)
	 * @return 单据对象(批量操作时，返回第一个对象)
	 * @throws ManagerException 异常
	 */
	BillBalance confirm(List<BillBalance> lstBill) throws ManagerException;
	
	/**
	 * 批量新增地区批发结算单
	 * @param params 数据
	 * @return 新增的数量
	 * @throws Exception 
	 */
	int batchAddBill(Map<String, Object> params) throws ManagerException, Exception;

	Map<String, BigDecimal> selectAmount(Map<String, Object> params) throws ManagerException;

	BillBalance createBalance(BillBalance obj, List<Object> lstItem)throws ManagerException;

	BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem)throws ManagerException;

	BillBalance updateBill(BillBalance model)throws ManagerException;
	
	List<BillSaleBalance> setExtendsBillSaleBalanceProperties(List<Object> list);
	
	/**
	 * 解冻退货金额
	 * @param list
	 * @return
	 * @throws ManagerException
	 */
	Boolean unfrozenAmount(List<BillSaleBalance> list) throws ManagerException;
	
}
