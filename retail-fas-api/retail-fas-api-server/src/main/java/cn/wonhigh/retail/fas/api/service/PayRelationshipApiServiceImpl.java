package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dal.PayRelationshipApiMapper;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.model.ContractDiscount;
import cn.wonhigh.retail.fas.common.model.PayRelationship;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:06
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
@Service("payRelationshipApiService")
class PayRelationshipApiServiceImpl  implements PayRelationshipApiService {
	
    @Resource
    private PayRelationshipApiMapper payRelationshipApiMapper;
    
    private boolean isNormalBiz(int bizType){
    	return bizType == BizTypeEnums.FIRST_ORDER.getStatus().intValue()
				|| bizType == BizTypeEnums.REPLENISH_ORDER.getStatus().intValue()
				|| bizType == BizTypeEnums.DIRECT.getStatus().intValue()
				|| bizType == BizTypeEnums.PURCHASE.getStatus().intValue()
				|| bizType == 60;// 总部收购
    			
    }
    
	@Override
	public void handerPEValence(BillBalanceApiDto dto)
			throws ServiceException {
		int billType = dto.getBillType();
		boolean isSplit = null!= dto.getIsSplit() && 1 == dto.getIsSplit().intValue();
		if(billType == BillTypeEnums.ASN.getRequestId().intValue()){// 到货单
			payRelationshipApiMapper.deleteByBillNo(dto.getOriginalBillNo());
			// 初始化关系表单据信息
			PayRelationship ship = this.initPayRelationship(dto, isSplit);
			if(this.isNormalBiz(ship.getBusinessBizType())){// 正常结算单据
				if(ship.getBusinessBizType().intValue() != 60){// 总部收购价格改为前台导入
					// 初始化关系表折扣信息
					this.initBalanceDiscount(ship, isSplit);
					// 价格更新
					this.updateBalanceCost(ship, isSplit);
				}else{
					Map<String, Object> params = new HashMap<String, Object>();
					 params.put("businessBillNo", ship.getBusinessBillNo());
					 params.put("buyerNo", ship.getSalerNo());// 总公司
					 PayRelationship buyShip = payRelationshipApiMapper.selectSumBuyBalanceAmount(params);
					 if(buyShip!= null){
							ship.setAmount(buyShip.getAmount());
							ship.setQty(buyShip.getQty());
							ship.setTagAmount(buyShip.getTagAmount());
					 }
				}
			}else{// 差异单据
				PayRelationship refShip = payRelationshipApiMapper.selectRefShip(ship.getBusinessBillNo());
				if(refShip != null){
					ship.setRefAsnBillNo(refShip.getBusinessBillNo());
					this.initDiffBillDiscount(refShip, ship);
					this.initDiffBillCost(refShip, ship);
				}
			}
			// 初始化到期付款信息
			this.initDuePayment(ship);
			// 插入关系表记录
			payRelationshipApiMapper.insertSelective(ship);
		}else{// 验收单
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(dto.getRefBillNo())){
				params.put("businessBillNo", dto.getRefBillNo());
				List<PayRelationship>  lstShip = payRelationshipApiMapper.selectByBiz(params);
				if(lstShip.size() >0 ){
					PayRelationship refShip = lstShip.get(0);
					if(billType == BillTypeEnums.RECEIPT.getRequestId().intValue()){
						this.updateBuyReceiptCost(refShip);
					}
				}
			}
		}
	}

	/**
	 * 初始化差异单据折扣
	 * @param refShip
	 * @param ship
	 */
	private void initDiffBillDiscount(PayRelationship refShip,PayRelationship ship) {
		ship.setAlgorithm(refShip.getAlgorithm());
		ship.setNotTaxAlgorithm(refShip.getNotTaxAlgorithm());
		ship.setSupplierDiscount1(refShip.getSupplierDiscount1());
		ship.setSupplierDiscount2(refShip.getSupplierDiscount2());
		ship.setBalanceDiscount1(refShip.getBalanceDiscount1());
		ship.setBalanceDiscount2(refShip.getBalanceDiscount2());
		ship.setSupplierContractDiscountNo(refShip.getSupplierContractDiscountNo());
		ship.setZoneAlgorithm(refShip.getZoneAlgorithm());
		ship.setZonePriceBasis(refShip.getZonePriceBasis());
		ship.setZoneOriginalDiscount1(refShip.getZoneOriginalDiscount1());
		ship.setZoneOriginalDiscount2(refShip.getZoneOriginalDiscount2());
		ship.setZoneAddPrice(refShip.getZoneAddPrice());
		ship.setCompanyContractDiscountNo(refShip.getCompanyContractDiscountNo());
	}
	
	/**
	 * 初始化差异单据价格
	 * @param refShip
	 * @param ship
	 */
	private void initDiffBillCost(PayRelationship refShip, PayRelationship ship) {
		List<PayRelationship> lstBuy = payRelationshipApiMapper.selectBuyAsnDiffList(ship);
		List<PayRelationship> lstSale = payRelationshipApiMapper.selectSaleAsnDiffList(ship);
		for (PayRelationship buyShip : lstBuy) {
			if(StringUtils.isBlank(buyShip.getId())){
				ship.setNotTaxAmount(buyShip.getNotTaxAmount());
				continue;
			}
			payRelationshipApiMapper.updateBuyCostById(buyShip.getId(),buyShip.getCost(),buyShip.getTagPrice());
		}
		for (PayRelationship saleShip : lstSale) {
			payRelationshipApiMapper.updateSaleCostById(saleShip.getId(),saleShip.getCost(),saleShip.getTagPrice());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("businessBillNo", ship.getBusinessBillNo());
		params.put("buyerNo", ship.getSalerNo());// 总公司
		PayRelationship buyShip = payRelationshipApiMapper.selectSumBuyBalanceAmount(params);
		PayRelationship saleShip = payRelationshipApiMapper.selectSumSaleBalanceAmount(ship.getBusinessBillNo());
		if(buyShip!= null){
			ship.setAmount(buyShip.getAmount());
			ship.setQty(buyShip.getQty());
			ship.setTagAmount(buyShip.getTagAmount());
		}
		if(saleShip!= null){
			ship.setZoneAmount(saleShip.getAmount());
			BigDecimal tagAmount = ship.getTagAmount();
			BigDecimal zoneAmount = ship.getZoneAmount();
			if(null != tagAmount && tagAmount.doubleValue() != 0 
					&& null != zoneAmount && zoneAmount.doubleValue() != 0){
				ship.setZoneDiscount(zoneAmount.divide(tagAmount,5,BigDecimal.ROUND_HALF_UP));
			}	
		}
	}

	private void initDuePayment(PayRelationship ship) {
		ship.setPayStatus("0");
		ship.setPayAmount(new BigDecimal(0));
		Date dueDate = payRelationshipApiMapper.selectAsnDueDateByBillNo(ship);
		ship.setDueDate(dueDate);
	}

	/**
	 * 初始化关系表单据信息
	 * @param buyBalance
	 * @return
	 */
	private PayRelationship initPayRelationship(BillBalanceApiDto dto, boolean isSplit){
		PayRelationship payRelationship = new PayRelationship();
		payRelationship.setId(UUIDHexGenerator.generate());
		payRelationship.setSupplierNo(dto.getSupplierNo());
		payRelationship.setSupplierName(dto.getSupplierName());
		if(isSplit){
			payRelationship.setSalerNo(dto.getSalerNo());
			payRelationship.setSalerName(dto.getSalerName());
			payRelationship.setBuyerNo(dto.getBuyerNo());
			payRelationship.setBuyerName(dto.getBuyerName());
		}else{
			payRelationship.setSalerNo(dto.getBuyerNo());
			payRelationship.setSalerName(dto.getBuyerName());
		}
		payRelationship.setBillDate(dto.getSendDate());
		payRelationship.setBrandNo(dto.getBrandNo());
		payRelationship.setBrandName(dto.getBrandName());
		payRelationship.setCategoryNo(dto.getCategoryNo().substring(0, 2));
		payRelationship.setCategoryName(dto.getCategoryName());
		payRelationship.setOrderNo(dto.getOrderNo());
		payRelationship.setBusinessBillNo(dto.getOriginalBillNo());
		payRelationship.setBusinessBillType(dto.getBillType());
		payRelationship.setBusinessBizType(dto.getBizType());
		payRelationship.setStoreNo(dto.getReceiveStoreNo());
		payRelationship.setStoreName(dto.getReceiveStoreName());
		payRelationship.setOrderUnitNo(dto.getOrderUnitNo());
		payRelationship.setOrderUnitName(dto.getOrderUnitName());
		payRelationship.setCreateUser(dto.getCreateUser());
		payRelationship.setCreateTime(dto.getCreateTime());
		payRelationship.setOrderType(String.valueOf(dto.getIsfutures()));
		payRelationship.setSettlementNumber(dto.getInvoiceNo());
		return payRelationship;
	}
	
	/**
	 * 更新buy表价格
	 * @param payRelationship
	 * @return
	 */
	private int updateBuyBalanceCost(PayRelationship payRelationship){
		int count = 0;
		List<PayRelationship> ids = payRelationshipApiMapper.selectBuyBalanceList(payRelationship);
		if(!CollectionUtils.isEmpty(ids)){
			BigDecimal notTaxAmount = new BigDecimal(0);
			for (PayRelationship ship : ids) {
				count +=payRelationshipApiMapper.updateBuyCostById(ship.getId(),ship.getCost(),ship.getTagPrice());
				notTaxAmount = notTaxAmount.add(ship.getNotTaxAmount());
			}
			payRelationship.setNotTaxAmount(notTaxAmount);
		}
		return count;
	}
	
	/**
	 * 更新sale表价格
	 * @param payRelationship
	 * @return
	 */
	private int updateSaleBalanceCost(PayRelationship payRelationship){
		int count = 0;
		List<PayRelationship> ids = payRelationshipApiMapper.selectSaleBalanceList(payRelationship);
		if(!CollectionUtils.isEmpty(ids)){
			for (PayRelationship ship : ids) {
				count +=payRelationshipApiMapper.updateSaleCostById(ship.getId(),ship.getCost(),ship.getTagPrice());
			}
		}
		return count;
	}
	
	/**
	 * 更新buy地区采购价格
	 * @param payRelationship
	 * @return
	 */
	private int updateBuyZoneBalanceCost(PayRelationship payRelationship){
		int count = 0;
		List<PayRelationship> ids = payRelationshipApiMapper.selectBuyZoneList(payRelationship.getBusinessBillNo());
		if(!CollectionUtils.isEmpty(ids)){
			for (PayRelationship ship : ids) {
				count +=payRelationshipApiMapper.updateBuyCostById(ship.getId(),ship.getCost(),ship.getTagPrice());
			}
		}
		return count;
	}
	
	/**
	 * 更新buy验收单价格
	 * @param payRelationship
	 * @return
	 */
	private int updateBuyReceiptCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			int count = 0;
			List<PayRelationship> ids = payRelationshipApiMapper.selectBuyReceiptList(payRelationship.getBusinessBillNo());
			if(!CollectionUtils.isEmpty(ids)){
				for (PayRelationship ship : ids) {
					count +=payRelationshipApiMapper.updateBuyCostById(ship.getId(),ship.getCost(),ship.getTagPrice());
				}
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 更新价格
	 * @param ship
	 * @return
	 */
	private void updateBalanceCost(PayRelationship ship, boolean isSplit) throws ServiceException {
		try {
			PayRelationship buyShip = null;
			PayRelationship saleShip = null;
			// 重新更新厂商结算价
			int buyCount = this.updateBuyBalanceCost(ship);
			if(buyCount >0){
				 Map<String, Object> params = new HashMap<String, Object>();
				 params.put("businessBillNo", ship.getBusinessBillNo());
				 params.put("buyerNo", ship.getSalerNo());// 总公司
				 buyShip = payRelationshipApiMapper.selectSumBuyBalanceAmount(params);
				 if(buyShip!= null){
						ship.setAmount(buyShip.getAmount());
						ship.setQty(buyShip.getQty());
						ship.setTagAmount(buyShip.getTagAmount());
				 }
			}
			if(isSplit){
				// 重新更新地区结算价
				int saleCount = this.updateSaleBalanceCost(ship);
				if(saleCount >0){
					this.updateBuyZoneBalanceCost(ship);
					saleShip = payRelationshipApiMapper.selectSumSaleBalanceAmount(ship.getBusinessBillNo());
				}
				if(saleShip!= null){
					ship.setZoneAmount(saleShip.getAmount());
					BigDecimal tagAmount = ship.getTagAmount();
					BigDecimal zoneAmount = ship.getZoneAmount();
					if(null != tagAmount && tagAmount.doubleValue() != 0 
							&& null != zoneAmount && zoneAmount.doubleValue() != 0){
						ship.setZoneDiscount(zoneAmount.divide(tagAmount,5,BigDecimal.ROUND_HALF_UP));
					}	
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/**
	 * 初始化折扣信息
	 * @param ship
	 * @return
	 */
	private void initBalanceDiscount(PayRelationship ship,boolean isSplit) throws ServiceException {
		try {
			ContractDiscount buyDiscount = this.getBuyDiscount(ship);
			if(null != buyDiscount){
				ship.setAlgorithm(buyDiscount.getAlgorithm());
				ship.setNotTaxAlgorithm(buyDiscount.getNotTaxAlgorithm());
				ship.setSupplierDiscount1(buyDiscount.getDiscount1());
				ship.setSupplierDiscount2(buyDiscount.getDiscount2());
				ship.setBalanceDiscount1(buyDiscount.getDiscount1());
				ship.setBalanceDiscount2(buyDiscount.getDiscount2());
				ship.setSupplierContractDiscountNo(buyDiscount.getContractDiscountNo());
			}
			if(isSplit){
				ContractDiscount saleDiscount = this.getSaleDiscount(ship);
				if(null != saleDiscount){
					ship.setZoneAlgorithm(saleDiscount.getAlgorithm());
					ship.setZonePriceBasis(saleDiscount.getPriceBasis());
					ship.setZoneOriginalDiscount1(saleDiscount.getDiscount1());
					ship.setZoneOriginalDiscount2(saleDiscount.getDiscount2());
					ship.setZoneAddPrice(saleDiscount.getAddPrice());
					ship.setCompanyContractDiscountNo(saleDiscount.getContractDiscountNo());
				}	
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	private ContractDiscount getBuyDiscount(PayRelationship payRelationship)
			throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("salerNo", payRelationship.getSupplierNo());
			params.put("buyerNo", payRelationship.getSalerNo());
			params.put("brandNo", payRelationship.getBrandNo());
			params.put("categoryNo", payRelationship.getCategoryNo());
			params.put("orderType", payRelationship.getOrderType());
			params.put("contractDiscountType", 1);
			params.put("sendDate", payRelationship.getBillDate());
			return payRelationshipApiMapper.selectLastContractDiscount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private ContractDiscount getSaleDiscount(PayRelationship payRelationship)
			throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("salerNo", payRelationship.getSalerNo());
			params.put("buyerNo", payRelationship.getBuyerNo());
			params.put("brandNo", payRelationship.getBrandNo());
			params.put("categoryNo", payRelationship.getCategoryNo());
			params.put("orderType", payRelationship.getOrderType());
			params.put("contractDiscountType", 2);
			params.put("sendDate", payRelationship.getBillDate());
			return payRelationshipApiMapper.selectLastContractDiscount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void invalid(BillHeaderApiDto billHeaderApiDto)
			throws ServiceException {
		try {
			String billNo = billHeaderApiDto.getBillNo();
			if(StringUtils.isNotBlank(billNo)){
				payRelationshipApiMapper.deleteByBillNo(billNo);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void changePE(BillBalanceApiDto dto) throws ServiceException {
		try {
			String billNo = dto.getOriginalBillNo();
			int billType = dto.getBillType();
			if(BillTypeEnums.RETURNOWN.getRequestId().intValue() == billType){
				payRelationshipApiMapper.updateBuyReturnCostByBillNo(billNo);
				payRelationshipApiMapper.updateSaleReturnCostByBillNo(billNo);
			}
			payRelationshipApiMapper.swapBuySaleByBillNo(billNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int selectBalanceCountByBillNo(String billNo)
			throws ServiceException {
		try {
			return payRelationshipApiMapper.selectBalanceCountByBillNo(billNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}