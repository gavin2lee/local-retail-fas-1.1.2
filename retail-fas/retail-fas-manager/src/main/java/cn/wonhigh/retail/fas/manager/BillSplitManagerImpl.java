package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApi;
import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.utils.BalanceTypeConvert;
import cn.wonhigh.retail.fas.service.BillSplitService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import com.yougou.logistics.base.service.log.Log;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
@Service("billSplitManager")
class BillSplitManagerImpl extends BaseCrudManagerImpl implements BillSplitManager {
	@Log
	protected Logger log; 
	
	
    @Resource
    private BillSplitService billSplitService;
    
    @Resource
    private BillBuyBalanceApi billBuyBalanceApi;

    @Override
    public BaseCrudService init() {
        return billSplitService;
    }

	@Override
	public int selectSplitBillCount(Map<String, Object> params) {
		return billSplitService.selectSplitBillCount(params);
	}

	@Override
	public List<BillBuyBalance> selectSplitBill(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billSplitService.selectSplitBill(page, orderByField, orderBy, params);
	}

	@Override
	public List<SettlePathDto> selectSettlePath(Map<String, Object> params) {
		return billSplitService.selectSettlePath(params);
	}

	@Override
	public Map<String, Object> splitData(List<Object> billList, String pathNo) throws ManagerException {
		
		List<BillBalanceApiDto> lstBill = new ArrayList<BillBalanceApiDto>();
		List<BillBalanceApiDto> lstReceiptBill = new ArrayList<BillBalanceApiDto>();
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		for(Object obj : billList) {
			BillBuyBalance billBuyBalance = (BillBuyBalance) obj;
			
			// 数据校验，是否已结算
			Map<String, Object> paramsSettleCount = new HashMap<String, Object>();
			paramsSettleCount.put("originalBillNo", billBuyBalance.getOriginalBillNo());
			paramsSettleCount.put("itemNo", billBuyBalance.getItemNo());
			int buySettleCount = billSplitService.selectBuySettleCount(paramsSettleCount);
			int saleSettleCount = billSplitService.selectSaleSettleCount(paramsSettleCount);
			
			//查询到已结算的数据则不处理
			if(buySettleCount > 0 || saleSettleCount > 0) continue;
			
			BillBalanceApiDto billBalanceApiDto = new BillBalanceApiDto();
			billBalanceApiDto = this.copyData(billBalanceApiDto, billBuyBalance);
			billBalanceApiDto.setPathNo(pathNo);
			lstBill.add(billBalanceApiDto);
			
			//如果是到货单，查询出对应的验收单
			if(BillTypeEnums.ASN.getRequestId().equals(billBuyBalance.getBillType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("refBillNo", billBuyBalance.getOriginalBillNo());
				params.put("itemNo", billBuyBalance.getItemNo());
				List<BillBuyBalance> receiptList = billSplitService.selectSplitBill(null, null, null, params);
				for(BillBuyBalance receiptBalance : receiptList) {
					
					BillBalanceApiDto receiptDto = new BillBalanceApiDto();
					receiptDto = this.copyData(receiptDto, receiptBalance);
					receiptDto.setPathNo(pathNo);
					lstReceiptBill.add(receiptDto);
				}
			}
			
		}
		
		try {
			if(lstBill.size() > 0) {
				boolean isSuccess = billBuyBalanceApi.insert(lstBill);
				if(lstReceiptBill.size() > 0) {
					billBuyBalanceApi.insert(lstReceiptBill);
				}
				reMap.put("isSuccess", isSuccess);
				reMap.put("message", "操作成功！");
			}
			else {
				reMap.put("isSuccess", true);
				reMap.put("message", "选择的单据已经结算，不能调整！");
			}
		} catch (RpcException e) {
			reMap.put("isSuccess", false);
			reMap.put("message", e.getMessage());
			log.info(e.getMessage());
			//throw new ManagerException(e.getMessage(), e);
		}
		
		return reMap;
	}
	
	private BillBalanceApiDto copyData(BillBalanceApiDto billBalanceApiDto, BillBuyBalance billBuyBalance) {
		billBalanceApiDto.setBillNo(billBuyBalance.getOriginalBillNo());
		billBalanceApiDto.setBillType(billBuyBalance.getBillType());
		billBalanceApiDto.setBizType(billBuyBalance.getBizType());
		billBalanceApiDto.setRefBillNo(billBuyBalance.getRefBillNo());
		billBalanceApiDto.setRefBillType(billBuyBalance.getRefBillType());
		billBalanceApiDto.setStatus(billBuyBalance.getStatus());
		billBalanceApiDto.setBuyerNo(billBuyBalance.getBuyerNo());
		billBalanceApiDto.setBuyerName(billBuyBalance.getBuyerName());
		//billBalanceApiDto.setSalerNo(billBuyBalance.getSalerNo());
		//billBalanceApiDto.setSalerName(billBuyBalance.getSalerName());
		billBalanceApiDto.setSendDate(billBuyBalance.getSendDate());
		billBalanceApiDto.setReceiveDate(billBuyBalance.getReceiveDate());
		billBalanceApiDto.setSendStoreNo(billBuyBalance.getSendStoreNo());
		billBalanceApiDto.setSendStoreName(billBuyBalance.getSendStoreName());
		billBalanceApiDto.setReceiveStoreNo(billBuyBalance.getReceiveStoreNo());
		billBalanceApiDto.setReceiveStoreName(billBuyBalance.getReceiveStoreName());
		billBalanceApiDto.setSupplierNo(billBuyBalance.getSupplierNo());
		billBalanceApiDto.setSupplierName(billBuyBalance.getSupplierName());
		billBalanceApiDto.setItemNo(billBuyBalance.getItemNo());
		billBalanceApiDto.setItemCode(billBuyBalance.getItemCode());
		billBalanceApiDto.setItemName(billBuyBalance.getItemName());
		billBalanceApiDto.setBrandNo(billBuyBalance.getBrandNo());
		billBalanceApiDto.setBrandName(billBuyBalance.getBrandName());
		billBalanceApiDto.setCategoryNo(billBuyBalance.getCategoryNo());
		billBalanceApiDto.setCategoryName(billBuyBalance.getCategoryName());
		billBalanceApiDto.setCost(billBuyBalance.getCost());
		billBalanceApiDto.setBillCost(billBuyBalance.getBillCost());
		billBalanceApiDto.setTaxRate(billBuyBalance.getTaxRate());
		billBalanceApiDto.setExclusiveCost(billBuyBalance.getExclusiveCost());
		
		//反冲单数量取反(还原)
		if(BalanceTypeConvert.isReturnBill(billBuyBalance.getBillType(), billBuyBalance.getBizType())) {
			billBalanceApiDto.setSendQty(-billBuyBalance.getSendQty());
			billBalanceApiDto.setReceiveQty(-billBuyBalance.getReceiveQty());
		} else {
			billBalanceApiDto.setSendQty(billBuyBalance.getSendQty());
			billBalanceApiDto.setReceiveQty(billBuyBalance.getReceiveQty());
		}
		
		billBalanceApiDto.setOrderNo(billBuyBalance.getOrderNo());
		billBalanceApiDto.setYears(billBuyBalance.getYears());
		billBalanceApiDto.setSeason(billBuyBalance.getSeason());
		billBalanceApiDto.setOrderUnitNoFrom(billBuyBalance.getOrderUnitNoFrom());
		billBalanceApiDto.setOrderUnitNameFrom(billBuyBalance.getOrderUnitNameFrom());
		billBalanceApiDto.setOrganNoFrom(billBuyBalance.getOrganNoFrom());
		billBalanceApiDto.setOrganNameFrom(billBuyBalance.getOrganNameFrom());
		billBalanceApiDto.setOrderUnitNo(billBuyBalance.getOrderUnitNo());
		billBalanceApiDto.setOrderUnitName(billBuyBalance.getOrderUnitName());
		billBalanceApiDto.setOrganNo(billBuyBalance.getOrganNo());
		billBalanceApiDto.setOrganName(billBuyBalance.getOrganName());
		billBalanceApiDto.setCreateUser(billBuyBalance.getCreateUser());
		billBalanceApiDto.setCreateTime(billBuyBalance.getCreateTime());
		return billBalanceApiDto;
	}
}