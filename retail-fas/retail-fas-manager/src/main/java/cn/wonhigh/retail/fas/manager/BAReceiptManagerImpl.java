package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.BAReceiptService;
import cn.wonhigh.retail.fas.service.BillBalanceService;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.CommonUtilService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;

/**
 * 请写出类的用途
 * 
 * @author ouyang.zm
 * @date 2014-08-25 12:10:54
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
@Service("baReceiptManager")
class BAReceiptManagerImpl implements BAReceiptManager {

	@Resource
	private BAReceiptService baReceiptService;

	@Resource
	private BillBalanceService billBalanceService;

	@Resource
	private CommonUtilService commonUtilService;

	@Resource
	private BillBuyBalanceService billBuyBalanceService;

	@Override
	public int findReceiptCount(Map<String, Object> params) throws ManagerException {
		try {
			return baReceiptService.findReceiptCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BAReceiptDto> findReceiptList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return baReceiptService.findReceiptList(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BAReceiptDto> findReceiptFooter(Map<String, Object> params) throws ManagerException {
		try {
			return baReceiptService.findReceiptFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	private List<RegionCostCaculatorDto> getUpdateList(BAReceiptDto baReceiptDto) throws ServiceException {
		List<RegionCostCaculatorDto> newList = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("originalBillNo", baReceiptDto.getOriginalBillNo());
		params.put("salerNo", baReceiptDto.getSalerNo());
		List<BillBuyBalance> lstBuy = billBuyBalanceService.findByBiz(null, params);
		for (BillBuyBalance balance : lstBuy) {
			RegionCostCaculatorDto dto = new RegionCostCaculatorDto();
			dto.setOriginalBillNo(balance.getOriginalBillNo());
			dto.setItemNo(balance.getItemNo());
			newList.add(dto);
		}
		return newList;
	}

	@Override
	public int generateBalance(List<BAReceiptDto> lstChecked, Map<String, Object> params) throws ManagerException {
		Map<String, List<BAReceiptDto>> map = new HashMap<>();
		try {
			for (BAReceiptDto dto : lstChecked) {
				if (StringUtils.isBlank(dto.getBalanceNo())) {
					String key = dto.getSalerNo().concat("_").concat(dto.getBuyerNo());
					if (null == map.get(key)) {
						List<BAReceiptDto> lstDto = new ArrayList<>();
						lstDto.add(dto);
						map.put(key, lstDto);
					} else {
						List<BAReceiptDto> lstDto = map.get(key);
						lstDto.add(dto);
					}
				}
			}// 合并相同买卖方的单
			for (List<BAReceiptDto> lstDto : map.values()) {
				BillBalance balance = this.initNewBalance(lstDto, params);
				billBalanceService.add(balance);
				this.updateBuyBalanceNoByItem(lstDto, balance);// 按item和originalBillNo更新buy表数据
			}
			return 0;
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	private void updateBalanceNo(List<BAReceiptDto> lstDto, BillBalance balance) throws Exception {
		String billNo = "";
		for (BAReceiptDto dto : lstDto) {
			billNo = billNo.concat(dto.getOriginalBillNo()).concat(",");
		}
		billNo = billNo.substring(0, billNo.length() - 1);
		Map<String, Object> params = new HashMap<>();
		params.put("multiOriginalBillNo", FasUtil.formatInQueryCondition(billNo));
		params.put("balanceNo", balance.getBillNo());
		params.put("salerNo", lstDto.get(0).getSalerNo());
		baReceiptService.updateBuyBalanceNo(params);
	}

	private void updateBuyBalanceNoByItem(List<BAReceiptDto> lstDto, BillBalance balance) throws Exception {
		for (BAReceiptDto dto : lstDto) {
			Map<String, Object> params = new HashMap<>();
			params.put("originalBillNo", dto.getOriginalBillNo());
			params.put("balanceNo", balance.getBillNo());
			params.put("salerNo", dto.getSalerNo());
			params.put("itemNo", dto.getItemNo());
			baReceiptService.updateBuyBalanceNoByItem(params);
		}
	}

	private BillBalance initNewBalance(List<BAReceiptDto> lstDto, Map<String, Object> params) throws Exception {
		BillBalance balance = new BillBalance();
		String receiveDateStart = String.valueOf(params.get("receiveDateStart"));
		String receiveDateEnd = String.valueOf(params.get("receiveDateEnd"));

		BAReceiptDto firstBill = lstDto.get(0);
		balance.setId(UUIDHexGenerator.generate());
		balance.setBrandUnitName(firstBill.getBrandUnitName());
		balance.setBrandUnitNo(firstBill.getBrandUnitNo());
		balance.setCategoryName(firstBill.getCategoryName());
		balance.setCategoryNo(firstBill.getCategoryNo());
		balance.setBillNo(commonUtilService.getNewBillNoCompanyNo(firstBill.getBuyerNo(),
				FasBillTypeEnums.BA.getRequestId()));
		balance.setBalanceType(BalanceTypeEnums.BAROQUE_RECEIPT.getTypeNo());
		balance.setSalerNo(firstBill.getSupplierNo());
		balance.setSalerName(firstBill.getSupplierName());
		balance.setBuyerNo(firstBill.getBuyerNo());
		balance.setBuyerName(firstBill.getBuyerName());
		balance.setBalanceStartDate(DateUtil.parseToDate(receiveDateStart, "yyyy-MM-dd"));
		balance.setBalanceEndDate(DateUtil.parseToDate(receiveDateEnd, "yyyy-MM-dd"));
		balance.setBalanceDate(new Date());
		balance.setCreateUser(Authorization.getUser().getUsername());
		balance.setCreateTime(new Date());
		int outQty = 0;
		BigDecimal outAmount = new BigDecimal(0);

		for (BAReceiptDto dto : lstDto) {
			outQty += dto.getReceiveQty();
			outAmount = outAmount.add(dto.getPurchaseAmount());
		}
		balance.setOutQty(outQty);
		balance.setOutAmount(outAmount);
		balance.setBalanceQty(outQty);
		balance.setBalanceAmount(outAmount);
		return balance;
	}

	@Override
	public String[] getItemNos(String originalBillNos) throws ManagerException {
		try {
			return this.baReceiptService.getItemNos(originalBillNos);
		} catch (Exception ex) {
			throw new ManagerException(ex);
		}
	}

}