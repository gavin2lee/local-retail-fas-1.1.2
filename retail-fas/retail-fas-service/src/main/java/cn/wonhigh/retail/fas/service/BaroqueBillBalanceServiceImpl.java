package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.dal.database.BaroqueBillBalanceMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudService;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("BaroqueBillBalanceService")
class BaroqueBillBalanceServiceImpl extends BaseCrudServiceImpl implements
		BaroqueBillBalanceService {

	@Resource
	private BaroqueBillBalanceMapper baroqueBillBalanceMapper;

	@Override
	public List<BillBalance> selectBalanceBill(BillBalance billBalance)
			throws ServiceException {
		try {
			return baroqueBillBalanceMapper.selectBalanceBill(billBalance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateStatus(BillBalance bill) throws ServiceException {
		try {
			return baroqueBillBalanceMapper.updateStatus(bill);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int verify(BillBalance bill) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return baroqueBillBalanceMapper.verify(bill);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public BaseCrudMapper init() {
		return baroqueBillBalanceMapper;
	}

}