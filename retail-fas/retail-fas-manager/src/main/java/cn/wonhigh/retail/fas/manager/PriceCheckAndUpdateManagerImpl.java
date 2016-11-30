/**
 * title:PriceCheckAndUpdateManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:地区价检查更新处理
 * auther:user
 * date:2016-8-2 下午3:07:22
 */
package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.PriceCheckAndUpdateService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;
import cn.wonhigh.retail.fas.service.SettlePathDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("priceCheckAndUpdateManagerImpl")
public class PriceCheckAndUpdateManagerImpl extends BaseCrudManagerImpl
		implements PriceCheckAndUpdateManager {

	@Resource
	private  PriceCheckAndUpdateService priceCheckAndUpdateService;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Resource
	private CompanyService companyService;
	@Resource
	private SettlePathDtlService settlePathDtlService;
	@Resource
	private RegionCostMaintainService regionCostMaintainService;
	
	@Override
	protected BaseCrudService init() {
		return  priceCheckAndUpdateService;
	}

	@Override
	public int findHqSaleBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqSaleBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqSaleBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findHqSaleBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqSaleBillCostIsDiffCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqSaleBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findAreaSaleBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaSaleBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaSaleBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaSaleBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findAreaSaleBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaSaleBillCostIsDiffCount(params);
	}

	@Override
	public int findHqBuyBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqBuyBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqBuyBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqBuyBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findHqBuyBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqBuyBillCostIsDiffCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqBuyBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqBuyBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}
	
	@Override
	public int findAreaBuyBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaBuyBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaBuyBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findAreaBuyBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaBuyBillCostIsDiffCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaBuyBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}
	
	@Override
	public int findHqSaleBillCostIsNullCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqSaleBillCostIsNullCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqSaleBillCostIsNullByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findHqSaleBillCostIsNullByPage(page,sortColumn,sortOrder,params);
	}
	
	@Override
	public int findAreaBuyDiffOutBillCount(Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaBuyDiffOutBillCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyDiffOutBillByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateService.findAreaBuyDiffOutBillByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int modifySaleBillCost(Map<String, Object> params) throws ServiceException, ManagerException {
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		
		String companyNo=params.get("saleCompanyNo").toString();
		String exceptionType=params.get("exceptionType").toString();
	
		if(hqCompanyNos.contains(companyNo) && exceptionType.equals("1")){	//总部出库单与维护的地区价不一致
			List<PriceCheckAndUpdateDto> hqSaleIsDiff = priceCheckAndUpdateService.findHqSaleBillCostIsDiffByPage(null, null, null, params);
			if (!CollectionUtils.isEmpty(hqSaleIsDiff)) {
				for (PriceCheckAndUpdateDto dto : hqSaleIsDiff) {
					BigDecimal cost = setBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(0);
					updateRegionPriceBill(dto);
				}
			}
		}else if(regionCompanyNos.contains(companyNo) && exceptionType.equals("1")){ //地区出库单与维护的地区价不一致
			List<PriceCheckAndUpdateDto> areaSaleIsDiff = priceCheckAndUpdateService.findAreaSaleBillCostIsDiffByPage(null, null, null, params);
			if (!CollectionUtils.isEmpty(areaSaleIsDiff)) {
				for (PriceCheckAndUpdateDto dto : areaSaleIsDiff) {
					BigDecimal cost = setBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(-1);
					updateRegionPriceBill(dto);
				}
			}
		}
		return 0;
	}
	
	@Override
	public int modifyBuyBillCost(Map<String, Object> params) throws ServiceException, ManagerException {
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		
		String companyNo=params.get("saleCompanyNo").toString();
		String exceptionType=params.get("exceptionType").toString();
		
	   if(regionCompanyNos.contains(companyNo) && exceptionType.equals("1")){ //地区入库单与维护的地区价不一致
			List<PriceCheckAndUpdateDto> areaBuyIsDiff = priceCheckAndUpdateService.findAreaBuyBillCostIsDiffByPage(null, null, null, params);
			if (!CollectionUtils.isEmpty(areaBuyIsDiff)) {
				for (PriceCheckAndUpdateDto dto : areaBuyIsDiff) {
					BigDecimal cost = setBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(1);
					updateRegionPriceBill(dto);
				}
			}
		}else if(hqCompanyNos.contains(companyNo) && exceptionType.equals("1")){ //总部入库单与维护的地区价不一致
			List<PriceCheckAndUpdateDto> hqBuyIsDiff = priceCheckAndUpdateService.findHqBuyBillCostIsDiffByPage(null, null, null, params);
			if (!CollectionUtils.isEmpty(hqBuyIsDiff)) {
				for (PriceCheckAndUpdateDto dto : hqBuyIsDiff) {
					BigDecimal cost = setBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(-1);
					updateRegionPriceBill(dto);
				}
			}
		}else if(regionCompanyNos.contains(companyNo) && exceptionType.equals("2")){ //地区入库单与原出库单的地区价不一致
			//查询验收单
			List<PriceCheckAndUpdateDto> areaBuyEntryOne = priceCheckAndUpdateService.findAreaBuyBillOneByPage(params);
			if (!CollectionUtils.isEmpty(areaBuyEntryOne)) {
				for (PriceCheckAndUpdateDto dto : areaBuyEntryOne) {
					BigDecimal cost = setEntryBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(1);
					updateRegionPriceBill(dto);
				}
			}
			//查询调货入库单和原残退厂发货单
			List<PriceCheckAndUpdateDto> areaBuyEntryTwo = priceCheckAndUpdateService.findAreaBuyBillTwoByPage(params);
			if (!CollectionUtils.isEmpty(areaBuyEntryTwo)) {
				for (PriceCheckAndUpdateDto dto : areaBuyEntryTwo) {
					BigDecimal cost = setEntryBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(1);
					updateRegionPriceBill(dto);
				}
			}
			//查询到货单,同步更新bill_buy_balance表的到货单
			List<PriceCheckAndUpdateDto> areaBuyEntryThree = priceCheckAndUpdateService.findAreaBuyBillThreeByPage(params);
			if (!CollectionUtils.isEmpty(areaBuyEntryThree)) {
				for (PriceCheckAndUpdateDto dto : areaBuyEntryThree) {
					BigDecimal cost = setEntryBillRegionCost(dto, hqCompanyNos);
					if (null == cost) {
						continue;
					}
					dto.setCost(cost);
					dto.setUpdateType(1);
					updateRegionPriceBill(dto);
				}
			}
		}
		return 0;
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
	
	/**
	 * 获取地区价
	 * @param pcauDto
	 * @param leaderCompanyNos
	 * @return
	 * @throws ServiceException
	 * @throws ManagerException
	 */
	private BigDecimal setBillRegionCost(PriceCheckAndUpdateDto pcauDto, String leaderCompanyNos) throws ServiceException, ManagerException {
		Map<String, Object> paramMap = null;
		//跨区调货单默认是买方的地区价，如果买方是总部公司，取卖方的地区价
		String zoneNo = findPriceZoneByCompanyNo(pcauDto.getBuyerNo());
		//1371 或者 1372
		if (pcauDto.getBillType().intValue() == BillTypeEnums.TRANSFER_IN.getRequestId().intValue()
				|| pcauDto.getBillType().intValue() == BillTypeEnums.TRANSFER_OUT.getRequestId().intValue()) {
			if (leaderCompanyNos.contains(pcauDto.getBuyerNo())) {
				zoneNo = findPriceZoneByCompanyNo(pcauDto.getSalerNo());
			}
		}
		//1301到货单、1304验收单，-0订货、-1补货
		if ((pcauDto.getBillType().intValue() == BillTypeEnums.ASN.getRequestId().intValue() ||
				pcauDto.getBillType().intValue() == BillTypeEnums.RECEIPT.getRequestId().intValue()) &&
				(pcauDto.getBizType().intValue() == BizTypeEnums.FIRST_ORDER.getStatus().intValue()
				|| pcauDto.getBizType().intValue() == BizTypeEnums.REPLENISH_ORDER.getStatus().intValue())) {
			if (null != pcauDto.getPathNo()) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("companyNo", pcauDto.getBuyerNo());
				paramMap.put("pathNo", pcauDto.getPathNo());
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
		BigDecimal regionCost = regionCostMaintainService.findRegionCost(pcauDto.getItemNo(),zoneNo, pcauDto.getSendDate());
		if (null == regionCost) {
			return null;
		}
		return regionCost;
	}
	
	/**
	 * 获取验收单、调货入库单、原残退厂发货单地区价
	 * @param pcauDto
	 * @param leaderCompanyNos
	 * @return
	 * @throws ServiceException
	 * @throws ManagerException
	 */
	private BigDecimal setEntryBillRegionCost(PriceCheckAndUpdateDto pcauDto, String leaderCompanyNos) throws ServiceException, ManagerException {
		Map<String, Object> paramMap = null;
		//跨区调货单默认是买方的地区价，如果买方是总部公司，取卖方的地区价
		String zoneNo = findPriceZoneByCompanyNo(pcauDto.getBuyerNo());
		//1371 或者 1372
		if (pcauDto.getBillType().intValue() == BillTypeEnums.TRANSFER_IN.getRequestId().intValue()
				|| pcauDto.getBillType().intValue() == BillTypeEnums.TRANSFER_OUT.getRequestId().intValue()) {
			if (leaderCompanyNos.contains(pcauDto.getBuyerNo())) {
				zoneNo = findPriceZoneByCompanyNo(pcauDto.getSalerNo());
			}
		}
		//1301到货单、1304验收单，-0订货、-1补货
		if ((pcauDto.getBillType().intValue() == BillTypeEnums.ASN.getRequestId().intValue() ||
				pcauDto.getBillType().intValue() == BillTypeEnums.RECEIPT.getRequestId().intValue()) &&
				(pcauDto.getBizType().intValue() == BizTypeEnums.FIRST_ORDER.getStatus().intValue()
				|| pcauDto.getBizType().intValue() == BizTypeEnums.REPLENISH_ORDER.getStatus().intValue())) {
			if (null != pcauDto.getPathNo()) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("companyNo", pcauDto.getBuyerNo());
				paramMap.put("pathNo", pcauDto.getPathNo());
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
		//根据价格大区查询对应货号的地区价
		BigDecimal regionCost=null;
		//查询对应出库单的价格
		if(pcauDto.getBillType()==BillTypeEnums.RECEIPT.getRequestId().intValue()){
			regionCost = priceCheckAndUpdateService.findAsnBillCost(pcauDto);
		} else if (pcauDto.getBillType() == BillTypeEnums.TRANSFER_IN.getRequestId().intValue()
				|| pcauDto.getBillType() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
			regionCost = priceCheckAndUpdateService.findTransferAndReturnBillCost(pcauDto);
		} else if(pcauDto.getBillType()==BillTypeEnums.ASN.getRequestId().intValue()){
			regionCost = priceCheckAndUpdateService.findSaleAsnBillCost(pcauDto);
		}
		//差异验收 则取维护的地区价
		if (null == regionCost) {
			regionCost = regionCostMaintainService.findRegionCost(pcauDto.getItemNo(),zoneNo, pcauDto.getSendDate());
		}
		if (null == regionCost) {
			return null;
		}
		return regionCost;
	}
	
	/**
	 * 更新单据的地区价
	 * @param pcauDto
	 * @throws ServiceException
	 * @throws ManagerException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	private void updateRegionPriceBill(PriceCheckAndUpdateDto pcauDto) throws ServiceException, ManagerException {
		if (pcauDto.getUpdateType() == 0) {
			priceCheckAndUpdateService.modifyBillBuyBalanceCost(pcauDto);
			priceCheckAndUpdateService.modifyBillSaleBalanceCost(pcauDto);
		} else if (pcauDto.getUpdateType() == -1) {
			priceCheckAndUpdateService.modifyBillSaleBalanceCost(pcauDto);
		} else if (pcauDto.getUpdateType() == 1) {
			priceCheckAndUpdateService.modifyBillBuyBalanceCost(pcauDto);
		}
	}

	
}
