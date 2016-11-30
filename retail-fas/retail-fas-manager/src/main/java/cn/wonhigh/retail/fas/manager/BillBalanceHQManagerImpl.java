package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.PricingRange;
import cn.wonhigh.retail.fas.service.BillBalanceHQService;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author wang.m1
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
@Service("billBalanceHQManager")
class BillBalanceHQManagerImpl extends BaseCrudManagerImpl implements BillBalanceHQManager {
	@Resource
	private BillBalanceHQService billHQBalanceService;

	@Resource
	private PricingRangeManager pricingRangeManager;
	
	@Override
	public BaseCrudService init() {
		return billHQBalanceService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addBill(BillBalance billBalance, Map<String, Object> params) throws ManagerException {
		try {
			// 查询结算信息
			List<BillBalance> lstBillBalance = billHQBalanceService.queryBalanceList(params);

			// 批量保存结算信息
			if (!CollectionUtils.isEmpty(lstBillBalance)) {
				billHQBalanceService.saveBalanceList(lstBillBalance, billBalance);
				return lstBillBalance.get(0); // 默认返回的结算单
			}
			return null;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem) throws ManagerException {
		try {
			return billHQBalanceService.createBalance(obj, lstItem);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem) throws ManagerException {
		try {
			return billHQBalanceService.balanceAdjust(obj, lstItem);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance balanceAdjust(BillBalance obj, List<OtherDeduction> lstDeduction,
			List<CustomImperfect> lstImperfect) throws ManagerException {
		try {
			return billHQBalanceService.balanceAdjust(obj, lstDeduction, lstImperfect);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectBalanceGatherCount(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectBalanceGatherCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalanceDto> selectBalanceGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectBalanceGatherList(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalanceDto> selectBalanceGatherFooter(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectBalanceGatherFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectBalanceCount(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectBalanceByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceFooter(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectBalanceFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectEnterCount(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectEnterCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectEnterList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billHQBalanceService.selectEnterList(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectEnterFooter(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectEnterFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectItemGatherCount(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectItemGatherCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectItemGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectItemGatherList(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectItemGatherFooter(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.selectItemGatherFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance getBalanceAmount(Map<String, Object> params) throws ManagerException {
		try {
			List<BillBalance> lstBalance = billHQBalanceService.queryBalanceList(params);
			if (lstBalance.size() > 0) {
				return lstBalance.get(0);
			}
			return null;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectHqCount() throws ManagerException {
		try {
			return billHQBalanceService.selectHqCount();
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}

	}

	@Override
	public Map<String, Object> handleBeforeBalance(Map<String, Object> params) throws ManagerException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 查询结算信息
			List<BillBalance> lstBillBalance = billHQBalanceService.queryBalanceList(params);
			if (CollectionUtils.isEmpty(lstBillBalance)) {
				resultMap.put("errorMessage", "当前期间不存在未结算业务！");
				return resultMap;
			}
			// 查询为0单价
			int count = billHQBalanceService.queryZeroPriceBill(params);
			if (count > 0) {
				resultMap.put("errorMessage", "单据存在为0单价，请先维护商品价格！");
				return resultMap;
			}
			// 检查OA超额状态
			String balanceType = String.valueOf(params.get("balanceType"));
			int iBalanceType = Integer.parseInt(balanceType);
			if (iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {// 总部厂商
				List<PricingRange> lstItem = pricingRangeManager.findByBiz(null, null);
				if (!CollectionUtils.isEmpty(lstItem)) {
					PricingRange obj = lstItem.get(0);
					if(obj.getIsStart() == 1){
						params.put("priceRangeCondition", obj.getQueryCondition());
						int excessCount = billHQBalanceService.queryExcessPriceBillCount(params);
						if (excessCount > 0) {
							resultMap.put("priceRangeCondition", obj.getQueryCondition());
						}
					}
				}
			}
			return resultMap;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int updateExceptionPrice(Map<String, Object> params) throws ManagerException {
		try {
			return billHQBalanceService.updateBalancePrice(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public void findExportList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
		try {
			billHQBalanceService.findExportList(params, handler);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceForBaroque(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			return this.billHQBalanceService.selectBalanceForBaroque(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}