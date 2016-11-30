package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.HqOtherStockOutBalanceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@Service("hqOtherStockOutBalanceManager")
class HqOtherStockOutBalanceManagerImpl extends BaseCrudManagerImpl implements HqOtherStockOutBalanceManager {
	@Resource
	private HqOtherStockOutBalanceService hqOtherStockOutBalanceService;
	
	@Override
	protected BaseCrudService init() {
		return hqOtherStockOutBalanceService;
	}
	@Override
	public int modifyBillStatus(String[] ids, Map<String,Object> params) {
		return hqOtherStockOutBalanceService.modifyBalanceBillStatus(ids, params);
	}

	@Override
	public int deleteBalanceBill(String[] ids) throws ManagerException {
		try {
			return hqOtherStockOutBalanceService.deleteBalanceBill(ids);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 *  出库金额验证
	 */
	@Override
	public BillBalance findBalanceBill(BillBalance formData) {
		String brandNo = formData.getBrandNo();	// 品牌
		if (!brandNo.equals("")) {
			String brandNos = "'" + brandNo.replaceAll(",", "','") + "'";
			formData.setBrandNo(brandNos);
		}
		// 查询结算信息
		List<BillBalance> lstBillBalance = hqOtherStockOutBalanceService
				.findBalanceBillList(formData);
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			return lstBillBalance.get(0);
		}
		return null;
	}

	/**
	 *  保存结算单(单个操作)
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addBalanceBill(BillBalance formData) throws Exception {
		String brandNo = formData.getBrandNo(); // 品牌
		if (!brandNo.equals("")) {
			formData.setBrandNo(FasUtil.formatInCondition(brandNo));
		}
		// 查询结算信息
		List<BillBalance> lstBillBalance = hqOtherStockOutBalanceService.findBalanceBillList(formData);
		// 保存结算信息
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			formData.setBrandNo(brandNo);
			hqOtherStockOutBalanceService.addBalanceBill(lstBillBalance, formData);
			return lstBillBalance.get(0);
		}
		return null;
	}

	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return hqOtherStockOutBalanceService.findTotalRow(params);
	}

	/**
	 * 批量生成结算单
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillByBatch(BillBalance formData)
			throws Exception {
		formData.setSalerNo(FasUtil.formatInQueryCondition(formData.getSalerNo()));
		formData.setBuyerNo(FasUtil.formatInQueryCondition(formData.getBuyerNo()));
		formData.setBrandNo(FasUtil.formatInQueryCondition(formData.getBrandNo()));
		List<BillBalance> lstItem =hqOtherStockOutBalanceService.findMatchedBalanceBill(formData);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(lstItem!=null){
			resultMap = hqOtherStockOutBalanceService.addBalanceBillBatch(lstItem,formData);
		}else {
			resultMap.put("flag", false)	;
		}
		return resultMap;
	}

	@Override
	public Map<String,Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance billBalance) throws Exception {
		return hqOtherStockOutBalanceService.addBalanceBillBySelect(lstItem,billBalance);
	}
}