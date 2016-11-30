package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.ExceptionPriceCheckDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.ExceptionPriceBillService;
import cn.wonhigh.retail.fas.service.PurchasePriceService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;
import cn.wonhigh.retail.fas.service.SettlePathDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-06-05 16:02:08
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("exceptionPriceBillManager")
class ExceptionPriceBillManagerImpl extends BaseCrudManagerImpl implements ExceptionPriceBillManager {
	
    @Resource
    private ExceptionPriceBillService exceptionPriceBillService;

    @Resource
    private FinancialAccountManager financialAccountManager;
    
    @Resource
    private RegionCostMaintainService regionCostMaintainService;
    
    @Resource
    private CompanyService companyService;
    
    @Resource
    private PurchasePriceService purchasePriceService;

    @Resource
    private SettlePathDtlService settlePathDtlService;
    
    @Override
    public BaseCrudService init() {
        return exceptionPriceBillService;
    }

    @Override
	public int findRegionPriceExceptionsCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return exceptionPriceBillService.findRegionPriceExceptionsCount(params);
		} catch (ServiceException e) {
			throw new  ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findRegionPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return exceptionPriceBillService.findRegionPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new  ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findPurchasePriceExceptionsCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return exceptionPriceBillService.findPurchasePriceExceptionsCount(params);
		} catch (ServiceException e) {
			throw new  ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<ExceptionPriceCheckDto> findPurchasePriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return exceptionPriceBillService.findPurchasePriceExceptionsByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new  ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public int findFasAllPriceExceptionsCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return exceptionPriceBillService.findFasAllPriceExceptionsCount(params);
		} catch (ServiceException e) {
			throw new  ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findFasAllPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return exceptionPriceBillService.findFasAllPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new  ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int batchUpdatePriceSchedule() throws ManagerException {
		try {
			return exceptionPriceBillService.batchUpdatePriceSchedule();
			
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findTagPriceExceptionsCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return exceptionPriceBillService.findTagPriceExceptionsCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findTagPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return exceptionPriceBillService.findTagPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 地区价更新，bill_buy_balance, 1301,1304,1333,1372
	 */
	@Override
	public int updateBuyBillRegionPrice(Map<String, Object> params)
			throws ManagerException {
		try {
			
			//总部职能公司列表
			String leadRoleCompanys = financialAccountManager.findLeadRoleCompanyNos();
			List<String> leaderCompanyNos = new ArrayList<String>();
			if (StringUtils.isNotBlank(leadRoleCompanys)) {
				leaderCompanyNos = Arrays.asList(leadRoleCompanys.split(","));
			}
			//到货单检查 1301
			List<BillBuyBalance> buyAsnBalances = exceptionPriceBillService.getBuyAsnRegionPrices(params);
			if (!CollectionUtils.isEmpty(buyAsnBalances)) {
				for (BillBuyBalance buyBalance: buyAsnBalances) {
					BigDecimal cost = setBillRegionCost(buyBalance, leaderCompanyNos);
					if (null == cost) {
						continue;
					}
					buyBalance.setCost(cost);
					buyBalance.setCostChecked(1);
					updateRegionPriceBill(buyBalance);
				}
			}
			
			//验收单检查 1304
			List<BillBuyBalance> buyReceiptBalances = exceptionPriceBillService.getBuyReceiptRegionPrices(params);
			if (!CollectionUtils.isEmpty(buyReceiptBalances)) {
				for (BillBuyBalance buyBalance: buyReceiptBalances) {
					BigDecimal cost = setBillRegionCost(buyBalance, leaderCompanyNos);
					if (null == cost) {
						continue;
					}
					buyBalance.setCost(cost);
					buyBalance.setCostChecked(1);
					updateRegionPriceBill(buyBalance);
				}
			}
			
			//原残退厂单检查 1333
			List<BillBuyBalance> buyReturnBalances = exceptionPriceBillService.getBuyReturnRegionPrices(params);
			if (!CollectionUtils.isEmpty(buyReturnBalances)) {
				for (BillBuyBalance buyBalance: buyReturnBalances) {
					BigDecimal cost = setBillRegionCost(buyBalance, leaderCompanyNos);
					if (null == cost) {
						continue;
					}
					buyBalance.setCost(cost);
					buyBalance.setCostChecked(1);
					updateRegionPriceBill(buyBalance);
				}
			}
			
			//跨区调货入库单检查 1372
			List<BillBuyBalance> buyTransferInBalances = exceptionPriceBillService.getBuyTransferInRegionPrices(params);
			if (!CollectionUtils.isEmpty(buyTransferInBalances)) {
				for (BillBuyBalance buyBalance: buyTransferInBalances) {
					BigDecimal cost = setBillRegionCost(buyBalance, leaderCompanyNos);
					if (null == cost) {
						continue;
					}
					buyBalance.setCost(cost);
					buyBalance.setCostChecked(1);
					updateRegionPriceBill(buyBalance);
				}
			}
			
			return 1;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}

	/**
	 * 获取地区价
	 * @param buyBalance
	 * @param leaderCompanyNos
	 * @return
	 * @throws ServiceException
	 * @throws ManagerException
	 */
	private BigDecimal setBillRegionCost(BillBuyBalance buyBalance, List<String> leaderCompanyNos) throws ServiceException, ManagerException {
		Map<String, Object> paramMap = null;
		//跨区调货单默认是买方的地区价，如果买方是总部公司，取卖方的地区价
		String zoneNo = findPriceZoneByCompanyNo(buyBalance.getBuyerNo());
		//调货入库 1372
		if (buyBalance.getBillType().intValue() == BillTypeEnums.TRANSFER_IN.getRequestId().intValue()) {
			if (leaderCompanyNos.contains(buyBalance.getBuyerNo())) {
				zoneNo = findPriceZoneByCompanyNo(buyBalance.getSalerNo());
			}
		}
		//ASN-1301到货单、RECEIPT-1304验收单，FIRST_ORDER-0订货、REPLENISH_ORDER-1补货
		if ((buyBalance.getBillType().intValue() == BillTypeEnums.ASN.getRequestId().intValue() ||
				buyBalance.getBillType().intValue() == BillTypeEnums.RECEIPT.getRequestId().intValue()) &&
				(buyBalance.getBizType().intValue() == BizTypeEnums.FIRST_ORDER.getStatus().intValue()
				|| buyBalance.getBizType().intValue() == BizTypeEnums.REPLENISH_ORDER.getStatus().intValue())) {
			if (null != buyBalance.getPathNo()) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("companyNo", buyBalance.getBuyerNo());
				paramMap.put("pathNo", buyBalance.getPathNo());
				List<SettlePathDtl> pathDtls = settlePathDtlService.findByBiz(null, paramMap);
				if (CollectionUtils.isEmpty(pathDtls)) {
					return null;
				}
				if ("CGJ".equals(pathDtls.get(0).getFinancialBasis())) {
					return null;
				}
				zoneNo = pathDtls.get(0).getFinancialBasis();
			}
		}
		if (StringUtils.isEmpty(zoneNo)) {
			return null;
		}
		//查询买方的地区价
		BigDecimal regionCost = regionCostMaintainService.findRegionCost(buyBalance.getItemNo(),
				zoneNo, buyBalance.getSendDate());
		if (null == regionCost) {
			return null;
		}
		return regionCost;
	}

	/**
	 * 更新单据的地区价
	 * @param buyBalance
	 * @throws ServiceException
	 * @throws ManagerException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	private void updateRegionPriceBill(BillBuyBalance buyBalance) throws ServiceException, ManagerException {
		try {
			exceptionPriceBillService.updateBillBuyBalanceCost(buyBalance);
			//同步更新bill_sale_balance表
			if (buyBalance.getBillType().intValue() == BillTypeEnums.ASN.getRequestId().intValue()
					|| buyBalance.getBillType().intValue() == BillTypeEnums.TRANSFER_IN.getRequestId().intValue()) {
				exceptionPriceBillService.updateBillSaleBalanceCost(buyBalance);
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据公司编码查询所属的价格大区
	 * @param companyNo
	 * @return zoneNo
	 * @throws ServiceException 
	 * @throws ManagerException 
	 */
	public String findPriceZoneByCompanyNo(String companyNo) throws ServiceException, ManagerException {
		String zoneNo = "";
		Map<String, Object> companyMap = null;
		Map<String, Object> financeAccountMap = new HashMap<String, Object>();
		financeAccountMap.put("companyNo", companyNo);
		financeAccountMap.put("status", 1);
		financeAccountMap.put("groupLeadRole", 0);
		
		List<FinancialAccount> financialAccounts = financialAccountManager.findByBiz(null, financeAccountMap);
		if (!CollectionUtils.isEmpty(financialAccounts)) {
			zoneNo = financialAccounts.get(0).getPriceZone();
		}
		
		//如果没有配置价格特区，就取公司默认大区
		if (StringUtils.isEmpty(zoneNo)) {
			companyMap = new HashMap<String, Object>();
			companyMap.put("companyNo", companyNo);
			companyMap.put("status", 1);
			
			List<Company> companies = companyService.findByBiz(null, companyMap);
			if (!CollectionUtils.isEmpty(companies)) {
				zoneNo = companies.get(0).getZoneNo();
			}
		}
		return zoneNo;
	}
	
	/*@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	private void updateTransferBill(BillBuyBalance buyBalance) throws ServiceException {
		try {
			
			exceptionPriceBillService.updateBillBuyBalanceCost(buyBalance);
			
			//1371更新bill_sale_balance的地区价
			exceptionPriceBillService.updateBillSaleBalanceCost(buyBalance);
			
		} catch (ServiceException e) {
			throw e;
		}
	}*/
	
	/**
	 * 地区价更新，bill_sale_balance, 1301,1371
	 */
	@Override
	public int updateSaleBillRegionPrice(Map<String, Object> params)
			throws ManagerException {
		try {
			//总部职能公司列表
			String leadRoleCompanys = financialAccountManager.findLeadRoleCompanyNos();
			List<String> leaderCompanyNos = new ArrayList<String>();
			if (StringUtils.isNotBlank(leadRoleCompanys)) {
				leaderCompanyNos = Arrays.asList(leadRoleCompanys.split(","));
			}
			
			List<BillSaleBalance> saleAsnBalances = exceptionPriceBillService.getSaleAsnRegionPrices(params);
			if (!CollectionUtils.isEmpty(saleAsnBalances)) {
				for (BillSaleBalance saleBalance : saleAsnBalances) {
					BigDecimal cost = setBillSaleBalanceRegionCost(saleBalance, leaderCompanyNos);
					if (null == cost) {
						continue;
					}
					saleBalance.setCost(cost);
					saleBalance.setCostChecked(1);
					updateSaleBalanceRegionPrice(saleBalance);
				}
			}
			
			List<BillSaleBalance> saleTransferOutBalances = exceptionPriceBillService.getSaleTransferOutRegionPrices(params);
			if (!CollectionUtils.isEmpty(saleTransferOutBalances)) {
				for (BillSaleBalance saleBalance : saleTransferOutBalances) {
					BigDecimal cost = setBillSaleBalanceRegionCost(saleBalance, leaderCompanyNos);
					if (null == cost) {
						continue;
					}
					saleBalance.setCost(cost);
					saleBalance.setCostChecked(1);
					updateSaleBalanceRegionPrice(saleBalance);
				}
			}
			
			return 1;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	private BigDecimal setBillSaleBalanceRegionCost(BillSaleBalance saleBalance, List<String> leaderCompanyNos) throws ServiceException, ManagerException {
		
		//跨区调货单默认是买方的地区价，如果买方是总部公司，取卖方的地区价
		String zoneNo = findPriceZoneByCompanyNo(saleBalance.getBuyerNo());
		if (saleBalance.getBillType().intValue() == BillTypeEnums.TRANSFER_OUT.getRequestId().intValue()) {
			if (leaderCompanyNos.contains(saleBalance.getBuyerNo())) {
				zoneNo = findPriceZoneByCompanyNo(saleBalance.getSalerNo());
			}
		}
		
		if (StringUtils.isEmpty(zoneNo)) {
			return null;
		}
		//查询买方的地区价
		BigDecimal regionCost = regionCostMaintainService.findRegionCost(saleBalance.getItemNo(),
				zoneNo, saleBalance.getSendDate());
		if (null == regionCost) {
			return null;
		}
		return regionCost;
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	private void updateSaleBalanceRegionPrice(BillSaleBalance saleBalance) throws ServiceException {
		try {
			//1301更新bill_sale_balance的地区价
			exceptionPriceBillService.updateBillSaleBalanceCost(saleBalance);
		} catch (ServiceException e) {
			throw e;
		}
	}
	
	/**
	 * 采购价更新,bill_buy_balance, 1301,1304,1333, 
	 * 需要关联更新bill_sale_balance的1301单据
	 */
	@Override
	public int updateBuyBillPurchasePrice(Map<String, Object> params)
			throws ManagerException {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			//1301到货单
			List<BillBuyBalance> buyAsnBalances = exceptionPriceBillService.getBuyAsnPurchasePrices(params);
			if (!CollectionUtils.isEmpty(buyAsnBalances)) {
				for (BillBuyBalance buyBalance: buyAsnBalances) {
					if (null != buyBalance.getPathNo()) {
						paramMap.put("companyNo", buyBalance.getBuyerNo());
						paramMap.put("pathNo", buyBalance.getPathNo());
						List<SettlePathDtl> pathDtls = settlePathDtlService.findByBiz(null, paramMap);
						if (CollectionUtils.isEmpty(pathDtls)) {
							continue;
						}
						if (!("CGJ".equals(pathDtls.get(0).getFinancialBasis()))) {
							continue;
						}
					}
					updatePurchasePriceBill(buyBalance);
				}
			}
			
			//1304验收单
			List<BillBuyBalance> buyReceiptBalances = exceptionPriceBillService.getBuyReceiptPurchasePrices(params);
			if (!CollectionUtils.isEmpty(buyReceiptBalances)) {
				for (BillBuyBalance buyBalance: buyReceiptBalances) {
					if (null != buyBalance.getPathNo()) {
						paramMap.put("companyNo", buyBalance.getBuyerNo());
						paramMap.put("pathNo", buyBalance.getPathNo());
						List<SettlePathDtl> pathDtls = settlePathDtlService.findByBiz(null, paramMap);
						if (CollectionUtils.isEmpty(pathDtls)) {
							continue;
						}
						if (!("CGJ".equals(pathDtls.get(0).getFinancialBasis()))) {
							continue;
						}
					}
					updatePurchasePriceBill(buyBalance);
				}
			}
			
			//1333 原残退厂发货单
			List<BillBuyBalance> buyReturnBalances = exceptionPriceBillService.getBuyReturnPurchasePrices(params);
			if (!CollectionUtils.isEmpty(buyReturnBalances)) {
				for (BillBuyBalance buyBalance: buyReturnBalances) {
					if (null != buyBalance.getPathNo()) {
						paramMap.put("companyNo", buyBalance.getBuyerNo());
						paramMap.put("pathNo", buyBalance.getPathNo());
						List<SettlePathDtl> pathDtls = settlePathDtlService.findByBiz(null, paramMap);
						if (CollectionUtils.isEmpty(pathDtls)) {
							continue;
						}
						if (!("CGJ".equals(pathDtls.get(0).getFinancialBasis()))) {
							continue;
						}
					}
					updatePurchasePriceBill(buyBalance);
				}
			}
			
			return 1;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	private void updatePurchasePriceBill(BillBuyBalance buyBalance) throws ServiceException, ManagerException {
		try {
			
			if (StringUtils.isEmpty(buyBalance.getSupplierNo())) {
				return;
			}
			//调用PMS查询采购价的接口
			PurchasePrice purchasePrice = purchasePriceService.findBalancePurchasePrice(buyBalance.getItemNo(),
					buyBalance.getSupplierNo(), buyBalance.getSendDate());
			
			if (null == purchasePrice) {
				return;
			}
			
			buyBalance.setCost(purchasePrice.getPurchasePrice());
			buyBalance.setCostChecked(1);
			
			exceptionPriceBillService.updateBillBuyBalanceCost(buyBalance);
			//1301，bill_sale_balance只是总部地区的结算单据，是地区价
//			if (buyBalance.getBillType().intValue() == BillTypeEnums.ASN.getRequestId().intValue()) {
//				exceptionPriceBillService.updateBillSaleBalanceCost(buyBalance);
//			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}

}