package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BaroqueBillBalanceService extends BaseCrudService {

	/**
	 * @param billBalance
	 * @return
	 */
	public List<BillBalance> selectBalanceBill(BillBalance billBalance)
			throws ServiceException;

	/**
	 * (根据结算单号)更新结算单状态
	 * 
	 * @param bill
	 * @return
	 */
	public int updateStatus(BillBalance bill) throws ServiceException;

	/**
	 * (根据结算单号)审核结算单
	 * 
	 * @param bill
	 * @return
	 */
	public int verify(BillBalance bill) throws ServiceException;

}