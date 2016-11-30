package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途
 * 
 * @author liu.jp
 * @date 2014-09-05 10:33:45
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface BaroqueBillBalanceManager extends BaseCrudManager {

	/**
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectBalanceBill(BillBalance billBalance)
			throws ManagerException;
	
	/**
	 * (根据结算单号)更新结算单状态
	 * 
	 * @param bill
	 * @return
	 */
	public int updateStatus(BillBalance bill) throws ManagerException;

	/**
	 * (根据结算单号)审核结算单
	 * 
	 * @param bill
	 * @return
	 */
	/**
	 * (根据结算单号)审核结算单
	 * 
	 * @param bill
	 * @return
	 */
	public int verify(BillBalance bill) throws ManagerException;
	
	/**
	 * 保存结算单
	 * @param billBalance
	 * @return
	 */
	public BillBalance addBalanceBill(BillBalance billBalance) throws ManagerException;
	
	/**
	 * 删除结算单
	 * @param billBalance
	 * @return
	 */
	public void deleteBalanceBill(BillBalance billBalance) throws ManagerException;
}
