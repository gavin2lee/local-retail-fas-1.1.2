package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.service.BillBuyBalanceAdditionalService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-06-06 10:02:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("billBuyBalanceAdditionalManager")
class BillBuyBalanceAdditionalManagerImpl extends BaseCrudManagerImpl implements BillBuyBalanceAdditionalManager {
	@Resource
	private BillBuyBalanceAdditionalService billBuyBalanceAdditionalService;

	@Override
	public BaseCrudService init() {
		return billBuyBalanceAdditionalService;
	}

	@Override
	public List<BillBuyBalanceAdditionDto> selectByParams(Map<String, Object> params) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.selectByParams(params);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findBaroqueBillByPage(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findBillBuyBalanceAdditionalByPage(arg0, arg1, arg2, arg3);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public Integer getCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.getCount(params);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findBaroqueRegionCostBill(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findBaroqueRegionCostBill(arg0, arg1, arg2, arg3);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findGroupBaroqueRegionCostBill(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findGroupBaroqueRegionCostBill(arg0, arg1, arg2, arg3);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findBaroqueDistributeCostBill(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findBaroqueDistributeCostBill(arg0, arg1, arg2, arg3);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public Integer findGroupBaroqueDistributeCostBillCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findGroupBaroqueDistributeCostBillCount(params);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public Integer findBaroqueRegionCostBillCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findBaroqueRegionCostBillCount(params);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}

	@Override
	public Integer findGroupBaroqueRegionCostBillCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.findGroupBaroqueRegionCostBillCount(params);
		} catch (ServiceException ex) {
			throw new ManagerException(ex);
		}
	}
}