/**
 * title:HqInsteadOfBuyBalanceManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-4-11 下午4:32:14
 */
package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.BrandUnitService;
import cn.wonhigh.retail.fas.service.HqInsteadOfBuyBalanceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("hqInsteadOfBuyBalanceManager")
public class HqInsteadOfBuyBalanceManagerImpl extends BaseCrudManagerImpl
		implements HqInsteadOfBuyBalanceManager {
	@Resource
	private HqInsteadOfBuyBalanceService hqInsteadOfBuyBalanceService;
	@Resource
	private BrandUnitService brandUnitService;

	@Override
	protected BaseCrudService init() {
		return hqInsteadOfBuyBalanceService;
	}

	/**
	 * 查询结算单金额合计
	 * @param params
	 * @return
	 */
	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return hqInsteadOfBuyBalanceService.findTotalRow(params);
	}

	/**
	 * 结算单金额验证(选单)
	 */
	@Override
	public BillBalance findBalanceBillBySelect(Map<String, List<Object>> map,
			BillBalance formData) {
		BigDecimal entryAmount = new BigDecimal(0);
		List<Object> entryList = map.get("entry");

		if (entryList != null) {
			for (Object object : entryList) {
				BillBuyBalance sale = (BillBuyBalance) object;
				// 累加入库金额
				entryAmount = entryAmount.add(sale.getCost().multiply(
						new BigDecimal(sale.getReceiveQty())));
			}
		}
		if (entryAmount != null) {
			formData.setEntryAmount(entryAmount);
			return formData;
		}
		return null;
	}

	/**
	 * 总部代采结算单金额验证
	 */
	@Override
	public BillBalance findHqBalanceBill(BillBalance formData) {
		String brandUnitNo = formData.getBrandUnitNo();// 获取品牌部
		Map<String, Object> params=new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			String brandNosWith = brandUnitService.findBrands(params);	// 查询有权限的品牌部下有权限的品牌
			formData.setBrandNo(brandNosWith);
		}else{
			params.put("brandUnitNo", null);
			String brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下的品牌
			formData.setBrandNo(brandNos);
		}
		// 查询结算信息
		List<BillBalance> lstBillBalance = hqInsteadOfBuyBalanceService
				.findHqBalanceList(formData);
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			return lstBillBalance.get(0);
		}
		return null;
	}

	/**
	 * 保存结算单(单个操作)
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addHqBalanceBill(BillBalance billBalance)
			throws Exception {
		String brandUnitNo = billBalance.getBrandUnitNo(); // 品牌部
		String brandNos = "";
		Map<String, Object> params=new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下有权限的品牌
			billBalance.setBrandNo(brandNos);
		}else{
			params.put("brandUnitNo", null);
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下的品牌
			billBalance.setBrandNo(brandNos);
		}
		// 查询结算信息
		List<BillBalance> lstBillBalance = hqInsteadOfBuyBalanceService.findHqBalanceList(billBalance);
		// 保存结算信息
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			billBalance.setBrandUnitNo(brandUnitNo);
			billBalance.setBrandNo(brandNos);
			hqInsteadOfBuyBalanceService.addHqBalanceBill(lstBillBalance,billBalance);
			return lstBillBalance.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> addHqBalanceBillBySelect(List<Object> map,
			BillBalance billBalance) throws Exception {
		return hqInsteadOfBuyBalanceService.addHqBalanceBillBySelect(map,
				billBalance);
	}

	@Override
	public BillBalance modifyHqDeducation(List<Object> lstItem,
			BillBalance billBalance) {
		return hqInsteadOfBuyBalanceService.modifyHqDeducation(lstItem,
				billBalance);
	}

	/**
	 * 批量生成总部代采结算单
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public Map<String, Object> addHqBalanceBillByBatch(BillBalance formData)
			throws Exception {
		formData.setSalerNo(FasUtil.formatInQueryCondition(formData.getSalerNo()));
		formData.setBuyerNo(FasUtil.formatInQueryCondition(formData.getBuyerNo()));
		formData.setBrandUnitNo(FasUtil.formatInQueryCondition(formData.getBrandUnitNo()));
		List<BillBalance> lstItem = hqInsteadOfBuyBalanceService.findMatchedHqBalanceBill(formData);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (lstItem != null) {
			resultMap = hqInsteadOfBuyBalanceService.addHqBalanceBillByBatch(lstItem, formData);
		} else {
			resultMap.put("flag", false);
		}
		return resultMap;
	}

	@Override
	public int deleteBalanceBill(String[] ids) throws ManagerException {
		try {
			return hqInsteadOfBuyBalanceService.deleteBalanceBill(ids);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int modifyBillStatus(String[] ids, Map<String, Object> params) {
		return hqInsteadOfBuyBalanceService.modifyBillStatus(ids, params);
	}

	@Override
	public int modifyBillCost(BillBalance formData) {
		//设置参数
		BillBalance bill=new BillBalance();
		bill.setSalerNo(FasUtil.formatInQueryCondition(formData.getSalerNo()));
		bill.setBuyerNo(FasUtil.formatInQueryCondition(formData.getBuyerNo()));
		bill.setBrandUnitNo(FasUtil.formatInQueryCondition(formData.getBrandUnitNo()));
		bill.setBalanceStartDate(formData.getBalanceStartDate());
		bill.setBalanceEndDate(formData.getBalanceEndDate());
		return hqInsteadOfBuyBalanceService.modifyBillCost(bill);
	}

}
