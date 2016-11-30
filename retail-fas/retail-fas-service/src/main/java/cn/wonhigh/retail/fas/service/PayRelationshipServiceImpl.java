package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.ContractDiscount;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.utils.AlgorithmUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.dal.database.PayRelationshipMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("payRelationshipService")
class PayRelationshipServiceImpl extends BaseCrudServiceImpl implements PayRelationshipService {
	
    @Resource
    private PayRelationshipMapper payRelationshipMapper;

    @Resource
    private CommonUtilService commonUtilService; 
    
    @Resource
    private PEUtilService peUtilService;
    
    @Resource
    private BillBalanceService billBalanceService;
    
    @Override
    public BaseCrudMapper init() {
        return payRelationshipMapper;
    }

	@Override
	public int updateBuyBalanceCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			List<PayRelationship> ids = payRelationshipMapper.selectBuyBalanceList(payRelationship);
			if(!CollectionUtils.isEmpty(ids)){
				if(ids.size() >1){
					for (PayRelationship ship : ids) {
						if(StringUtils.isBlank(ship.getId())){
							BigDecimal notTaxAmountNew = ids.get(0).getNotTaxAmount() == null ? BigDecimal.ZERO : ids.get(0).getNotTaxAmount();
							BigDecimal notTaxAmountOld = payRelationship.getNotTaxAmount()== null ? BigDecimal.ZERO : payRelationship.getNotTaxAmount();
							if(notTaxAmountNew.doubleValue() != notTaxAmountOld.doubleValue()){
								payRelationship.setNotTaxAmount(notTaxAmountNew);
							}
							continue;
						}
						payRelationshipMapper.updateBuyCostById(ship.getId(),ship.getCost(),ship.getTagPrice(),ship.getItemName(),ship.getCategoryNo(),ship.getCategoryName());
					}
					return 1;
				}
				BigDecimal notTaxAmountNew = ids.get(0).getNotTaxAmount() == null ? BigDecimal.ZERO : ids.get(0).getNotTaxAmount();
				BigDecimal notTaxAmountOld = payRelationship.getNotTaxAmount()== null ? BigDecimal.ZERO : payRelationship.getNotTaxAmount();
				if(notTaxAmountNew.doubleValue() != notTaxAmountOld.doubleValue()){
					payRelationship.setNotTaxAmount(notTaxAmountNew);
					return 1;
				}
			}
			 
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateSaleBalanceCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			List<PayRelationship> ids = payRelationshipMapper.selectSaleBalanceList(payRelationship);
			if(!CollectionUtils.isEmpty(ids)){
				for (PayRelationship ship : ids) {
					payRelationshipMapper.updateSaleCostById(ship.getId(),ship.getCost(),ship.getTagPrice(),ship.getItemName(),ship.getCategoryNo(),ship.getCategoryName());
				}
				return 1;
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateBuyReceiptBalanceCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			int count = 0;
			List<PayRelationship> ids = payRelationshipMapper.selectBuyReceiptList(payRelationship.getBusinessBillNo());
			if(!CollectionUtils.isEmpty(ids)){
				for (PayRelationship ship : ids) {
					count +=payRelationshipMapper.updateBuyCostById(ship.getId(),ship.getCost(),ship.getTagPrice(),ship.getItemName(),ship.getCategoryNo(),ship.getCategoryName());
				}
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public int updateBuyZoneBalanceCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			int count = 0;
			List<PayRelationship> ids = payRelationshipMapper.selectBuyZoneList(payRelationship.getBusinessBillNo());
			if(!CollectionUtils.isEmpty(ids)){
				for (PayRelationship ship : ids) {
					count +=payRelationshipMapper.updateBuyCostById(ship.getId(),ship.getCost(),ship.getTagPrice(),ship.getItemName(),ship.getCategoryNo(),ship.getCategoryName());
				}
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public int updateAsnDiffBalanceCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			int count = 0;
			String refBillNo = payRelationship.getBusinessBillNo();
			List<PayRelationship> billNos = payRelationshipMapper.selectAsnDiffList(refBillNo);
			if(!CollectionUtils.isEmpty(billNos)){
				for (PayRelationship bill : billNos) {
					bill.setAlgorithm(payRelationship.getAlgorithm());
					bill.setNotTaxAlgorithm(payRelationship.getNotTaxAlgorithm());
					bill.setSupplierDiscount1(payRelationship.getSupplierDiscount1());
					bill.setSupplierDiscount2(payRelationship.getSupplierDiscount2());
					bill.setBalanceDiscount1(payRelationship.getBalanceDiscount1());
					bill.setBalanceDiscount2(payRelationship.getBalanceDiscount2());
					bill.setSupplierContractDiscountNo(payRelationship.getSupplierContractDiscountNo());
					bill.setZoneAlgorithm(payRelationship.getZoneAlgorithm());
					bill.setZonePriceBasis(payRelationship.getZonePriceBasis());
					bill.setZoneOriginalDiscount1(payRelationship.getZoneOriginalDiscount1());
					bill.setZoneOriginalDiscount2(payRelationship.getZoneOriginalDiscount2());
					bill.setZoneAddPrice(payRelationship.getZoneAddPrice());
					bill.setCompanyContractDiscountNo(payRelationship.getCompanyContractDiscountNo()); 
					List<PayRelationship> lstBuy = payRelationshipMapper.selectBuyAsnDiffList(bill);
					List<PayRelationship> lstSale = payRelationshipMapper.selectSaleAsnDiffList(bill);
					for (PayRelationship buyShip : lstBuy) {
						if(StringUtils.isBlank(buyShip.getId())){
							bill.setNotTaxAmount(buyShip.getNotTaxAmount());
							continue;
						}
						payRelationshipMapper.updateBuyCostById(buyShip.getId(),buyShip.getCost(),buyShip.getTagPrice(),buyShip.getItemName(),buyShip.getCategoryNo(),buyShip.getCategoryName());
					}
					for (PayRelationship saleShip : lstSale) {
						payRelationshipMapper.updateSaleCostById(saleShip.getId(),saleShip.getCost(),saleShip.getTagPrice(),saleShip.getItemName(),saleShip.getCategoryNo(),saleShip.getCategoryName());
					}
					payRelationshipMapper.updateBalanceAmount(bill);
					count ++;
				}
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public ContractDiscount getBuyDiscount(PayRelationship payRelationship)
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
			return peUtilService.selectLastContractDiscount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ContractDiscount getSaleDiscount(PayRelationship payRelationship)
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
			return peUtilService.selectLastContractDiscount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int findItemCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return payRelationshipMapper.findItemCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PayRelationship> findFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return payRelationshipMapper.findFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PayRelationship> findItemList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return payRelationshipMapper.findItemList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateDueDate(Map<String, Object> params)
			throws ServiceException {
		try {
			return payRelationshipMapper.updateDueDate(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateDiscount(PayRelationship payRelationship)
			throws ServiceException {
		try {
			return payRelationshipMapper.updateBalanceDiscount(payRelationship);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateCost(PayRelationship payRelationship)
			throws ServiceException {
		try {
			// 更新明细表价格
			int buyCount = this.updateBuyBalanceCost(payRelationship);
			int saleCount = 0;
			if(StringUtils.isNotBlank(payRelationship.getCompanyContractDiscountNo())){
				saleCount = this.updateSaleBalanceCost(payRelationship);
			}
			// 更新关系表金额
			payRelationshipMapper.updateBalanceAmount(payRelationship);
			if(buyCount > 0 || saleCount >0){
				 if(saleCount >0){// 更新地区采购价格
					 this.updateBuyZoneBalanceCost(payRelationship);
					 this.updateBuyReceiptBalanceCost(payRelationship);
				 }
				 // 更新对账标记
				 if(buyCount > 0){
					 payRelationship = payRelationshipMapper.selectByPrimaryKey(payRelationship);
					 this.updateBalanceFlag(payRelationship);
				 }
				 // 差异价格更新
				 this.updateAsnDiffBalanceCost(payRelationship);
				 return 1;
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateBalanceFlag(PayRelationship payRelationship)
			throws ServiceException {
		try {
			if(StringUtils.isBlank(payRelationship.getSettlementNumber()) 
					&& StringUtils.isBlank(payRelationship.getBusinessBillNo())){
				return 0;
			}
			return payRelationshipMapper.updateBalanceFlag(payRelationship);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public PayRelationship findReturnProfitCount(Map<String, Object> params) throws ServiceException {
		try {
			return payRelationshipMapper.findReturnProfitCount(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public List<PayRelationship> findReturnProfitList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return payRelationshipMapper.findReturnProfitList(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public List<PayRelationship> selectUpdateDiscountList(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		try {
			return payRelationshipMapper.selectUpdateDiscountList(page,params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PayRelationship> selectUpdateCostList(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		try {
			return payRelationshipMapper.selectUpdateCostList(page,params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int importTagPrice(PayRelationship ship) throws ServiceException {
		try {
			BigDecimal hqCost = BigDecimal.ZERO;
			if(StringUtils.isNotBlank(ship.getSupplierContractDiscountNo())){
				hqCost = AlgorithmUtil.algorithmAll(ship.getAlgorithm(), ship.getTagPrice(), ship.getBalanceDiscount1(), ship.getBalanceDiscount2());
			}
			BigDecimal zoneCost = BigDecimal.ZERO;
			if(StringUtils.isNotBlank(ship.getCompanyContractDiscountNo())){
				if(StringUtils.isNotBlank(ship.getZoneAlgorithm())){
					zoneCost = AlgorithmUtil.algorithmAll(ship.getZoneAlgorithm(), ship.getTagPrice(), ship.getZoneOriginalDiscount1(), ship.getZoneOriginalDiscount2());		
				}else{
					if(null != ship.getZoneAddPrice()){
						zoneCost = hqCost.add(ship.getZoneAddPrice());
					}else if(null != ship.getZoneOriginalDiscount1()){
						zoneCost = hqCost.multiply(ship.getZoneOriginalDiscount1());
					}
				}
			}
			payRelationshipMapper.updateBuyCostByParams(ship.getBusinessBillNo(),ship.getItemCode(),hqCost,ship.getTagPrice());
			payRelationshipMapper.updateSaleCostByParams(ship.getBusinessBillNo(),ship.getItemCode(),zoneCost,ship.getTagPrice());
			BigDecimal notTaxAmount = payRelationshipMapper.selectNotTaxAmount(ship);
			ship.setNotTaxAmount(notTaxAmount);
			payRelationshipMapper.updateBalanceAmount(ship);
			this.updateBuyZoneBalanceCost(ship);
			this.updateBuyReceiptBalanceCost(ship);
			this.updateAsnDiffBalanceCost(ship);
			ship = payRelationshipMapper.selectByPrimaryKey(ship);
			this.updateBalanceFlag(ship);
			return 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public int generateBalanceNew(Map<String, Object> params)
			throws ServiceException {
		try {
			List<BillBalance> lstBalance = payRelationshipMapper.queryBalanceList(params);
			int count = 0;
			for (BillBalance billBalance : lstBalance) {
				billBalance.setBalanceStartDate(DateUtil.getdate(String.valueOf(params.get("sendDateStart"))));
				billBalance.setBalanceEndDate(DateUtil.getdate(String.valueOf(params.get("sendDateEnd"))));
				this.initBalance(billBalance);
				billBalanceService.add(billBalance);
				String multiBrandNo = params.get("multiBrandNo") == null ? "" : String.valueOf(params.get("multiBrandNo"));
				billBalance.setMultiBrandNo(multiBrandNo);
				params.put("balanceSalerNo", billBalance.getSalerNo());
				params.put("balanceBuyerNo", billBalance.getBuyerNo());
				params.put("balanceBrandNo", billBalance.getBrandNo());
				params.put("balanceBillNo", billBalance.getBillNo());
				payRelationshipMapper.updateShipBalanceNo(params);
				payRelationshipMapper.updateDeductionBalanceNo(billBalance);
				payRelationshipMapper.updateAdjustBalanceNo(billBalance);
				count++;
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 初始化结算单
	 * @param billBalance
	 * @throws ServiceException
	 */
	private void initBalance(BillBalance billBalance) throws ServiceException {
		billBalance.setId(UUIDHexGenerator.generate());
		billBalance.setBillNo(commonUtilService.getNewBillNoCompanyNo(billBalance.getBuyerNo(), FasBillTypeEnums.TS.getRequestId()));
		billBalance.setCreateUser(CurrentUser.getCurrentUser().getUsername());
		billBalance.setCreateTime(new Date());
		billBalance.setBillName(billBalance.getSalerName().concat(DateUtil.format(billBalance.getBalanceStartDate())).concat("-").concat(DateUtil.format(billBalance.getBalanceEndDate())).concat("结算单"));
		billBalance.setBalanceType(BalanceTypeEnums.PE_SUPPLIER.getTypeNo());
		billBalance.setBalanceDate(new Date());
	}

	@Override
	public int updateCostBuy(PayRelationship newShip) throws ServiceException {
		try {
			if(StringUtils.isNotBlank(newShip.getItemCode())){// 按货号导入
				String billNo = newShip.getBusinessBillNo();
				String itemCode = newShip.getItemCode();
				BigDecimal tagPrice = newShip.getTagPrice();
				BigDecimal supplierCost = tagPrice.multiply(newShip.getSupplierDiscount1());
				BigDecimal zoneCost = tagPrice.multiply(newShip.getZoneOriginalDiscount1());
				payRelationshipMapper.updateBuyCostByParams(billNo, itemCode, supplierCost, tagPrice);
				payRelationshipMapper.updateSaleCostByParams(billNo, itemCode, zoneCost, tagPrice);
				payRelationshipMapper.updateBalanceAmount(newShip);
				this.updateBuyZoneBalanceCost(newShip);
				this.updateBuyReceiptBalanceCost(newShip);
				this.updateAsnDiffBalanceCost(newShip);
			}else{// 按单据导入
				int buyCount = this.updateBuyBalanceCost(newShip);
				int saleCount = this.updateSaleBalanceCost(newShip);
				// 更新关系表金额
				payRelationshipMapper.updateBalanceAmount(newShip);
				if(buyCount > 0 || saleCount >0){
					 if(saleCount >0){// 更新地区采购价格
						 this.updateBuyZoneBalanceCost(newShip);
						 this.updateBuyReceiptBalanceCost(newShip);
					 }
					 // 差异价格更新
					 this.updateAsnDiffBalanceCost(newShip);
				}
			}
			return 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}