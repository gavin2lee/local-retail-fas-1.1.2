package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface BillShopBalanceBackManager extends BaseCrudManager {

	/**
     * 通过结算单号、结算差异编号汇总差异回款金额
     * @param balanceNo 结算单号
     * @param diffBillNo 结算差异编号
     * @param diffDtlId 结算差异ID
     * @return 汇总的金额
     * @throws ManagerException 异常
     */
	BigDecimal sumBackAmount(String balanceNo, String diffBillNo, String diffDtlId) throws ManagerException;
}