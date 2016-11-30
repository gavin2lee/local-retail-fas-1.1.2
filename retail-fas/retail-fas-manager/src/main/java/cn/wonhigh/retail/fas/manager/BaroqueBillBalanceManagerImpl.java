package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BaroqueBillBalanceService;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.BrandUnitService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.OtherDeductionService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
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
@Service("BaroqueBillBalanceManager")
class BaroqueBillBalanceManagerImpl extends BaseCrudManagerImpl implements BaroqueBillBalanceManager {

	@Resource
	private BaroqueBillBalanceService baroqueBillBalanceService;

	@Resource
	private BrandUnitService brandUnitService;

	@Resource
	private BillBuyBalanceService billBuyBalanceService;

	@Resource
	private OtherDeductionService otherDeductionService;

	@Resource
	private CommonUtilService commonUtilService;

	@Override
	public BaseCrudService init() {
		return baroqueBillBalanceService;
	}

	@Override
	public List<BillBalance> selectBalanceBill(BillBalance billBalance) throws ManagerException {
		try {
			return baroqueBillBalanceService.selectBalanceBill(billBalance);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int updateStatus(BillBalance bill) throws ManagerException {
		try {
			return baroqueBillBalanceService.updateStatus(bill);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	public int verify(BillBalance bill) throws ManagerException {
		try {
			return baroqueBillBalanceService.verify(bill);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 单个保存结算单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addBalanceBill(BillBalance formData) throws ManagerException {
		String brandUnitNo = formData.getBrandUnitNo(); // 品牌部
		String brandNos = "";
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			// 查询结算信息
			List<BillBalance> lstBillBalance = this.baroqueBillBalanceService.selectBalanceBill(formData);
			// 保存结算信息
			if (lstBillBalance != null && lstBillBalance.size() > 0) {
				for (BillBalance bill : lstBillBalance) {
					formData.setBrandUnitNo(brandUnitNo);
					formData.setBrandNo(brandNos);
					this.initBillInfo(bill, formData);// 设置结算单信息
					this.baroqueBillBalanceService.add(bill);
					this.billBuyBalanceService.updateBuyBalanceNo(bill);
					this.otherDeductionService.updateBalanceNo(bill);
					return lstBillBalance.get(0);
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 保存结算单(单个操作)
	 * 
	 * @param bill
	 * @param billBalance
	 * @throws Exception
	 */
	private void initBillInfo(BillBalance bill, BillBalance fromPage) throws Exception {
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.BAROQUE_RECEIPT.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getSalerNo(), null, "BA"));// 生成结算单编码
		bill.setBillName(fromPage.getBillName());
		bill.setBuyerNo(fromPage.getBuyerNo());// 调入公司
		bill.setBuyerName(fromPage.getBuyerName());
		bill.setSalerNo(fromPage.getSalerNo());// 调出公司
		bill.setSalerName(fromPage.getSalerName());
		bill.setStatus(fromPage.getStatus()); // 单据状态
		// bill.setBrandNo(FasUtil.splitStr(fromPage.getBrandNo()));
		// bill.setBrandName(fromPage.getBrandName());
		bill.setBrandUnitNo(fromPage.getBrandUnitNo());
		if ("".equals(fromPage.getBrandUnitNo())) {
			bill.setBrandUnitName("");
		} else {
			bill.setBrandUnitName(fromPage.getBrandUnitName());
		}
		bill.setBalanceStartDate(fromPage.getBalanceStartDate());
		bill.setBalanceEndDate(fromPage.getBalanceEndDate());
		bill.setBalanceDate(fromPage.getBalanceDate());
		bill.setCurrency(fromPage.getCurrency());
		bill.setCreateUser(fromPage.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
		bill.setRemark(fromPage.getRemark());
	}

	/**
	 * 删除结算单
	 * 
	 * @param billBalance
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void deleteBalanceBill(BillBalance billBalance) throws ManagerException {
		try {
			this.baroqueBillBalanceService.deleteById(billBalance.getId());
			this.billBuyBalanceService.clearBuyBalanceNo(billBalance);
//			this.otherDeductionService.updateBalanceNo(billBalance);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}