package cn.wonhigh.retail.fas.api.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dto.HQRegionCostDto;
import cn.wonhigh.retail.fas.api.dto.HeadquarterCostDto;
import cn.wonhigh.retail.fas.api.dto.ItemZonePriceDto;
import cn.wonhigh.retail.fas.api.dto.RegionCostDto;
import cn.wonhigh.retail.fas.api.dto.UpdateCostApiDto;
import cn.wonhigh.retail.fas.api.service.BrandApiService;
import cn.wonhigh.retail.fas.api.service.CompanyAccountingPeriodService;
import cn.wonhigh.retail.fas.api.service.CostAccountingApi;
import cn.wonhigh.retail.fas.api.service.FinancialAccountApiService;
import cn.wonhigh.retail.fas.api.service.HeadquarterCostAccountingService;
import cn.wonhigh.retail.fas.api.service.RegionCostAccountingService;
import cn.wonhigh.retail.fas.api.service.UpdateCostService;
import cn.wonhigh.retail.fas.api.utils.CommonUtils;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.mdm.api.CompanyApi;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("costAccountingManagerImplApi")
public class CostAccountingManagerImpl implements CostAccountingApi {

	@Resource
	private HeadquarterCostAccountingService headquarterCostAccountingService;

	@Resource
	private RegionCostAccountingService regionCostAccountingService;

	@Resource
	private CompanyAccountingPeriodService companyAccountingPeriodService;

	@Resource
	private FinancialAccountApiService financialAccountApiService;
	
	@Resource
	private UpdateCostService updateCostService;
	
	@Resource
	private BrandApiService brandApiService;
	
	@Resource
	private CompanyApi companyApi;
	
	private Logger log = Logger.getLogger(getClass());

	@Override
	public BigDecimal findRegionCost(RegionCostDto registDto)
			throws RpcException {
		try {
			if(registDto == null){
				throw new RpcException("财务辅助dubbo服务", "registDto 参数不合法");
			}
			if (CommonUtils.strIsNull(registDto.getCompanyNo()) 
					&& CommonUtils.strIsNull(registDto.getOrderUnitNo())) {
				throw new RpcException("财务辅助dubbo服务", "companyNo参数和OrderUnitNo不能全为空！");
			}
			if (null == registDto.getItemNo() && CollectionUtils.isEmpty(registDto.getItemNos())) {
				throw new RpcException("财务辅助dubbo服务", "itemNo参数不合法");
			}
			if(registDto.getEffectiveDate() == null){
				throw new RpcException("财务辅助dubbo服务", "effectiveDate参数不合法");
			}
			
			String zoneNo = getZoneNoByRegionCostDto(registDto);
			if (null != registDto.getItemNo()) {
				return regionCostAccountingService.findRegionCost(registDto.getItemNo(), zoneNo,registDto.getEffectiveDate());
			}else {
				return regionCostAccountingService.findRegionCost(registDto.getItemNos().get(0), zoneNo,registDto.getEffectiveDate());
			}
			
		} catch (ServiceException e) {
			log.error("查询商品地区价格失败", e);
			throw new RpcException(10001, "获取商品地区价格失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public BigDecimal findHeadquarterCost(HeadquarterCostDto headquarterCostDto) throws RpcException {
		try {
			if(headquarterCostDto == null){
				throw new RpcException("财务辅助dubbo服务", "headquarterCostDto参数不合法");
			}
			if (CommonUtils.strIsNull(headquarterCostDto.getItemNo())) {
				throw new RpcException("财务辅助dubbo服务", "itemNo参数不合法");
			}
			if(headquarterCostDto.getEffectiveDate() == null){
				throw new RpcException("财务辅助dubbo服务", "effectiveDate参数不合法");
			}
			
			return headquarterCostAccountingService.findHeadquarterCost(headquarterCostDto.getItemNo(),headquarterCostDto.getEffectiveDate());
		} catch (ServiceException e) {
			log.error("查询商品总部价格", e);
			throw new RpcException(10001, "获取商品总部价格", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public BigDecimal findRegionCostByOrderUnitDto(RegionCostDto dto) throws RpcException {
		try {
			if (CommonUtils.strIsNull(dto.getOrderUnitNo())) {
				throw new RpcException("财务辅助dubbo服务", "orderUnitNo参数不合法");
			}
			if (CommonUtils.strIsNull(dto.getItemNo())) {
				throw new RpcException("财务辅助dubbo服务", "itemNo参数不合法");
			}
			if(null == dto.getEffectiveDate()){
				throw new RpcException("财务辅助dubbo服务", "effectiveDate参数不合法");
			}
			//根据订货单位查询公司信息
			Company company = regionCostAccountingService.getCompanyInfoByOrderUnitNo(dto.getOrderUnitNo());
			
			if (null == company) {
				throw new RpcException("财务辅助dubbo服务", "获取公司信息失败！");
			}
			String zoneNo = company.getZoneNo();
			//查询公司帐套维护默认的价格大区
			Map<String, Object> financialAccountMap = new HashMap<String, Object>();
			financialAccountMap.put("companyNo", company.getCompanyNo());
			FinancialAccount financialAccount = regionCostAccountingService.getFinancialAccountByCompanyNo(financialAccountMap);
			if (null != financialAccount && !StringUtils.isEmpty(financialAccount.getPriceZone())) {
				//维护公司帐套,取帐套的价格大区
				zoneNo = financialAccount.getPriceZone();
			}
			return regionCostAccountingService.findRegionCost(
					dto.getItemNo(), zoneNo,dto.getEffectiveDate());
		} catch (ServiceException e) {
			log.error("获取商品地区价格失败", e);
			throw new RpcException(10001, "获取商品地区价格失败", "财务辅助dubbo服务", e);
		}
	}
	
	private CompanyBrandSettlePeriod initMinBrandBalanceDate(String companyNo, String brandNo) {
		CompanyBrandSettlePeriod newObj = new CompanyBrandSettlePeriod();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date minDate = cal.getTime();
		newObj.setCompanyNo(companyNo);
		newObj.setBrandNo(brandNo);
		newObj.setAccountSettleTime(minDate);
		newObj.setSupplierSettleTime(minDate);
		newObj.setTransferSettleTime(minDate);
		newObj.setSaleSettleTime(minDate);
		return newObj;
	} 
	
	private CompanySettlePeriod initMinBalanceDate(String companyNo) {
		CompanySettlePeriod newObj = new CompanySettlePeriod();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date minDate = cal.getTime();
		newObj.setCompanyNo(companyNo);
		newObj.setAccountSettleTime(minDate);
		newObj.setSupplierSettleTime(minDate);
		newObj.setTransferSettleTime(minDate);
		newObj.setSaleSettleTime(minDate);
		return newObj;
	}

	private boolean isWholesale(String companyNo) throws RpcException {
		cn.wonhigh.retail.mdm.common.model.Company c = new cn.wonhigh.retail.mdm.common.model.Company();
		c.setCompanyNo(companyNo);
		c = companyApi.findById(c);
		String companyName = "";
		if(null != c){
			companyName = c.getName();
		}
		if(StringUtils.isNotBlank(companyName)){
			return companyName.indexOf("批发-体育") != - 1;
		}
		return false;
	}
	
	@Override
	public CompanySettlePeriod findBalanceDate(String companyNo)
			throws RpcException {
		try {
			if (CommonUtils.strIsNull(companyNo)) {
				throw new RpcException("财务辅助dubbo服务", "companyNo参数不合法");
			}
			if(isWholesale(companyNo)){// 批发公司判断
				return initMinBalanceDate(companyNo);
			}
			//log.info("获取公司结算期间, 公司编码为： " + companyNo);
			return companyAccountingPeriodService.findBalanceDate(companyNo);
		} catch (ServiceException e) {
			log.error("获取公司结算期间失败", e);
			throw new RpcException(10001, "获取公司结算期间失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public boolean checkUpdateFactoryPrice(
			HeadquarterCostMaintain headqarterCostMaintain) throws RpcException {
		try {
			if (CommonUtils.strIsNull(headqarterCostMaintain.getItemNo())
					|| CommonUtils.strIsNull(headqarterCostMaintain.getSupplierNo())
					|| null == headqarterCostMaintain.getEffectiveTime()) {

				log.error("获取是否可以更新成本的参数失败");
				throw new RpcException("参数验证失败", 10001, "财务辅助dubbo服务");
			}
			
			//根据商品编码和供应商编码查询总部成本，若没有维护则可以修改
			int costCount = headquarterCostAccountingService.qryHeadquarterCostExist(headqarterCostMaintain);
			
			if (costCount > 0) {//已经存在记录
				//最大生效时间小于传入的时间，可以修改
				int count = headquarterCostAccountingService.qryHeadquarterCostIsEffective(headqarterCostMaintain);
				if (count > 0) {
					return true;
				}else {
					return false;
				}
			}else {
				return true;
			}
			
		} catch (ServiceException e) {
			log.error("校验商品厂进价是否可以修改失败", e);
			throw new RpcException(10002, "校验商品厂进价是否可以修改失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public List<ItemZonePriceDto> findRegionListCost(RegionCostDto regionCostDto) throws RpcException {
		try {
			
			if(regionCostDto == null){
				throw new RpcException("财务辅助dubbo服务", "regionCostDto参数不合法");
			}
			if (CommonUtils.strIsNull(regionCostDto.getCompanyNo())
					 && CommonUtils.strIsNull(regionCostDto.getOrderUnitNo())) {
				throw new RpcException("财务辅助dubbo服务", "CompanyNo和OrderUnitNo不能都为空");
			}
			if(CollectionUtils.isEmpty(regionCostDto.getItemNos())){
				throw new RpcException("财务辅助dubbo服务", "itemNo参数不合法");
			}
			if(regionCostDto.getEffectiveDate() == null){
				throw new RpcException("财务辅助dubbo服务", "effectiveDate参数不合法");
			}
			
			String zoneNo = getZoneNoByRegionCostDto(regionCostDto);
			
			List<ItemZonePriceDto> itemZonePriceDtos = new ArrayList<ItemZonePriceDto>();
			for (String itemNo : regionCostDto.getItemNos()) {
				
				BigDecimal curBigDecimal = regionCostAccountingService.findRegionCost(itemNo, zoneNo, regionCostDto.getEffectiveDate());
			
				ItemZonePriceDto itemZonePriceDto = new ItemZonePriceDto();
				itemZonePriceDto.setItemNo(itemNo);
				itemZonePriceDto.setPrice(curBigDecimal);
				itemZonePriceDto.setZoneNo(zoneNo);
				itemZonePriceDtos.add(itemZonePriceDto);
			}
			
			return itemZonePriceDtos;
		} catch (ServiceException e) {
			log.error("批量查询商品地区价格失败", e);
			throw new RpcException(10001, "批量获取商品地区价格失败", "财务辅助dubbo服务", e);
		}
	}

	private String getZoneNoByRegionCostDto(RegionCostDto regionCostDto) throws RpcException {
		try {
			String zoneNo = null;
			Company company = null;
			FinancialAccount financialAccount = null;
			if (!StringUtils.isEmpty(regionCostDto.getCompanyNo())) {
				//查询公司信息
				Map<String, Object> companyMap = new HashMap<String, Object>();
				companyMap.put("companyNo", regionCostDto.getCompanyNo());
				company = regionCostAccountingService.findCompanyModel(companyMap);
				
				if (null == company) {
					throw new RpcException("财务辅助dubbo服务", "查询公司报错");
				}
				zoneNo = company.getZoneNo();
				//查询公司帐套维护默认的价格大区
				Map<String, Object> financialAccountmMap = new HashMap<String, Object>();
				financialAccountmMap.put("companyNo", regionCostDto.getCompanyNo());
				financialAccount = regionCostAccountingService.getFinancialAccountByCompanyNo(financialAccountmMap);
				if (null != financialAccount && !StringUtils.isEmpty(financialAccount.getPriceZone())) {
					//维护公司帐套,取帐套的价格大区
					zoneNo = financialAccount.getPriceZone();
				}
			}else {
				//根据订货单位查询公司信息
				company = regionCostAccountingService.getCompanyInfoByOrderUnitNo(regionCostDto.getOrderUnitNo());
				if (null == company) {
					throw new RpcException("财务辅助dubbo服务", "获取公司信息失败！");
				}
				zoneNo = company.getZoneNo();
				//查询公司帐套维护默认的价格大区
				Map<String, Object> financialAccountmMap = new HashMap<String, Object>();
				financialAccountmMap.put("companyNo", company.getCompanyNo());
				financialAccount = regionCostAccountingService.getFinancialAccountByCompanyNo(financialAccountmMap);
				if (null != financialAccount && !StringUtils.isEmpty(financialAccount.getPriceZone())) {
					//维护公司帐套,取帐套的价格大区
					zoneNo = financialAccount.getPriceZone();
				}
			}
			
			return zoneNo;
		} catch (Exception e) {
			log.error("查询地区失败", e);
			throw new RpcException(10001, "查询地区失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public HQRegionCostDto findHQAndRegionCost(RegionCostDto dto) throws RpcException {
		try {
			HQRegionCostDto hqRegionCostDto = new HQRegionCostDto();
			if(dto == null){
				throw new RpcException("财务辅助dubbo服务", "regionCostDto参数不合法");
			}
			if (CommonUtils.strIsNull(dto.getItemNo())) {
				throw new RpcException("财务辅助dubbo服务", "itemNo参数不合法");
			}
			if(dto.getEffectiveDate() == null ){
				throw new RpcException("财务辅助dubbo服务", "effectiveDate参数不合法");
			}
			
			hqRegionCostDto.setHqPrice(headquarterCostAccountingService.findHeadquarterCost(dto.getItemNo(),dto.getEffectiveDate()));
			
			String zoneNo = getZoneNoByRegionCostDto(dto);
			hqRegionCostDto.setRegionPrice(regionCostAccountingService.findRegionCost(
					dto.getItemNo(), zoneNo,dto.getEffectiveDate()));
			return hqRegionCostDto;
			
		} catch (ServiceException e) {
			log.error("批量查询商品地区价格失败", e);
			throw new RpcException(10001, "批量获取商品地区价格失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public boolean checkHQFunctionCompany(String companyNo) throws RpcException {
		try {
			if(StringUtils.isEmpty(companyNo)){
				throw new RpcException("财务辅助dubbo服务", "companyNo参数不合法");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			params.put("groupLeadRole", 1);
			params.put("status", 1);
			
			List<FinancialAccount> financialAccounts = financialAccountApiService.findByParams(params);
			if (CollectionUtils.isEmpty(financialAccounts)) {
				return false;
			}
			return true;
			
		} catch (ServiceException e) {
			log.error("查询公司帐套维护信息失败", e);
			throw new RpcException(10001, "查询公司帐套维护信息失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public List<String> findAllHQFunctionCompany() throws RpcException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupLeadRole", 1);
			params.put("status", 1);
			
			List<String> companys = new ArrayList<String>();
			List<FinancialAccount> financialAccounts = financialAccountApiService.findByParams(params);
			if (CollectionUtils.isEmpty(financialAccounts)) {
				return null;
			}
			for (FinancialAccount financialAccount : financialAccounts) {
				companys.add(financialAccount.getCompanyNo());
			}
			return companys;
			
		} catch (ServiceException e) {
			log.error("查询公司帐套维护信息失败", e);
			throw new RpcException(10001, "查询公司帐套维护信息失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public CompanyBrandSettlePeriod findBalanceDateByBrandAndCompany(String companyNo, String brandNo)
			throws RpcException {
		try {
			if(isWholesale(companyNo)){// 批发公司判断
				return initMinBrandBalanceDate(companyNo,brandNo);
			}
			if (CommonUtils.strIsNull(companyNo)) {
				throw new RpcException("财务辅助dubbo服务", "companyNo参数不合法");
			}
			if (CommonUtils.strIsNull(brandNo)) {
				throw new RpcException("财务辅助dubbo服务", "brandNo参数不合法");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			
			Brand brand = brandApiService.findByBrandNo(brandNo);
			if (null == brand) {
				throw new RpcException("财务辅助dubbo服务", "品牌获取失败");
			}
			//设置品牌部
			params.put("brandNo", brand.getSysNo());
			
			return companyAccountingPeriodService.getCompanyBrandBalanceDate(params);
			
		} catch (ServiceException e) {
			log.error("获取公司结算期间失败", e);
			throw new RpcException(10001, "获取公司结算期间失败", "财务辅助dubbo服务", e);
		}
	}

	@Override
	public int updateCost(UpdateCostApiDto updateCostApiDto)
			throws RpcException {
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", updateCostApiDto.getCompanyNo());
			params.put("startDate", updateCostApiDto.getStartDate());
			params.put("endDate", updateCostApiDto.getEndDate());
			
			return updateCostService.updateItemCost(params);
			
		} catch (ServiceException e) {
			throw new RpcException("FAS-API", "分配价格到业务单据失败。");
		}
	}

	@Override
	public int updateHQAndRegionCost(UpdateCostApiDto updateCostApiDto)	throws RpcException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", updateCostApiDto.getCompanyNo());
			params.put("startDate", updateCostApiDto.getStartDate());
			params.put("endDate", updateCostApiDto.getEndDate());
			
			updateCostService.updateRegionCost(params);
			updateCostService.updateHeadquaterCost(params);
			
			return 1;
		} catch (ServiceException e) {
			throw new RpcException("FAS-API", "分配价格到业务单据失败。");
		}
	}

	@Override
	public CompanyBrandSettlePeriod findCompanyBrandMaxSettleDate(
			String companyNo, List<String> brandNos) throws RpcException {
		try {
			if(isWholesale(companyNo) && !CollectionUtils.isEmpty(brandNos)){// 批发公司判断
				return initMinBrandBalanceDate(companyNo,brandNos.get(0));
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			
			List<CompanyBrandSettlePeriod> settlePeriods = new ArrayList<CompanyBrandSettlePeriod>();
			for (String brandNo : brandNos) {
				Brand brand = brandApiService.findByBrandNo(brandNo);
				if (null == brand) {
					throw new RpcException("财务辅助dubbo服务", "品牌获取失败,品牌名称：" + brandNo);
				}
				//设置品牌部
				params.put("brandNo", brand.getSysNo());
//				params.put("brandNo", brandNo);
				CompanyBrandSettlePeriod settlePeriod = companyAccountingPeriodService.getCompanyBrandBalanceDate(params);
				
				if (null == settlePeriod) {
					return null;
				} else {
					if (null == settlePeriod.getAccountSettleTime() || null == settlePeriod.getSaleSettleTime()
							|| null == settlePeriod.getTransferSettleTime() || null == settlePeriod.getSupplierSettleTime()) {
						log.info("公司品牌结转日配置错误， 公司编码：" + companyNo + "  品牌编码： " + brandNo);
						return null;
					}
					settlePeriods.add(settlePeriod);
				}
			}
			
			CompanyBrandSettlePeriod maxBrandSettlePeriod = null;
			Date maxDate = null;
			if (!CollectionUtils.isEmpty(settlePeriods)) {
				maxBrandSettlePeriod = settlePeriods.get(0);
				//默然为财务结账日
				maxDate = maxBrandSettlePeriod.getAccountSettleTime();
				
				for (CompanyBrandSettlePeriod brandSettlePeriod : settlePeriods) {
					if (maxDate.before(brandSettlePeriod.getAccountSettleTime())) {
						maxDate = brandSettlePeriod.getAccountSettleTime();
						maxBrandSettlePeriod = brandSettlePeriod;
					}
					if (maxDate.before(brandSettlePeriod.getSaleSettleTime())) {
						maxDate = brandSettlePeriod.getSaleSettleTime();
						maxBrandSettlePeriod = brandSettlePeriod;
					}
				}
			}
			
			return maxBrandSettlePeriod;
		} catch (ServiceException e) {
			throw new RpcException("FAS-API", "分配价格到业务单据失败。");
		}
	}

}

/**
 * 更新成本线程类
 * @author user
 *
 */
/*class UpdateCostThread implements Runnable {
	
	private Map<String, Object> params;
	private UpdateCostService updateCostService;
	private Logger log = Logger.getLogger(getClass());
	public UpdateCostThread(UpdateCostService updateCostService, Map<String, Object> params) {
		this.updateCostService = updateCostService;
		this.params = params;
	}
	@Override
	public void run() {
		int orderCount = updateCostService.updateOrderDtl(params);
		log.info("------------更新order_dtl表成功，共更新数据条数：" + orderCount);
		int returnCount = updateCostService.updateReturnExchangeDtl(params);
		log.info("------------更新return_exchange_dtl表成功，共更新数据条数：" + returnCount);
		int buyCount = updateCostService.updateBillBuyBalance(params);
		log.info("------------更新bill_buy_balance表成功，共更新数据条数：" + buyCount);
		int saleCount = updateCostService.updateBillSaleBalance(params);
		log.info("------------更新updateBillSaleBalance表成功，共更新数据条数：" + saleCount);
		int periodCount = updateCostService.updatePeriodBalance(params);
		log.info("------------更新period_balance表成功，共更新数据条数：" + periodCount);
	}
}*/