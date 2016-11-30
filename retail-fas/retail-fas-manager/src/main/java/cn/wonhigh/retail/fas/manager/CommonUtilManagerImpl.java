package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.enums.AskPaymentStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.LookupEnum;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.SettleMethod;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ShopVo;
import cn.wonhigh.retail.fas.service.CommonUtilService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@Service("CommonUtilManager")
class CommonUtilManagerImpl  implements CommonUtilManager {
	
	@Resource
	private CommonUtilService commonUtilService;
	
	@Resource
	private LookupEntryManager lookupEntryManager;
	
	@Resource
	private CurrencyManagementManager currencyManagementManager;
	
	@Resource
	private SettleMethodManager settleMethodManager;
	
	@Override
	public List<String> queryHeadquarterMaintainedItems() throws ManagerException {
		 try{
		    	return this.commonUtilService.queryHeadquarterMaintainedItems();
		    }catch(ServiceException e){
		    	throw new ManagerException(e.getMessage(), e);
		    }
	}

	@Override
	public List<String> queryRegionMaintainedItems(String zoneNo) throws ManagerException {
		 try{
		    	return this.commonUtilService.queryRegionMaintainedItems(zoneNo);
		    }catch(ServiceException e){
		    	throw new ManagerException(e.getMessage(), e);
		    }
	}


	@Override
	public String getSettleName(String settleMentMethodNo)
			throws ManagerException {
		if (StringUtils.isEmpty(settleMentMethodNo)) {
			return "";
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("settleCode", settleMentMethodNo);
			List<SettleMethod> list = settleMethodManager.findByBiz(null,
					params);
			for (SettleMethod x : list) {
				if (settleMentMethodNo.equals(x.getSettleCode())) {
					return x.getSettleName();
				}
			}
		}
		return "";
	}

	@Override
	public String getCurrencyName(String currencyCode) throws ManagerException {
		if (StringUtils.isEmpty(currencyCode)) {
			return "";
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currencyCode", currencyCode);
			List<CurrencyManagement> CurrencyList = currencyManagementManager
					.findByBiz(null, params);
			for (CurrencyManagement x : CurrencyList) {
				if (currencyCode.equals(x.getCurrencyCode())) {
					return x.getCurrencyName();
				}
			}
		}
		return "";
	}

	@Override
	public String getStatusName(String statusNo) throws ManagerException {
		if (StringUtils.isEmpty(statusNo)) {
			return "";
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("lookupId", LookupEnum.BILL_STATUS.getCode());
			List<LookupEntry> statusList = lookupEntryManager.findByBiz(null,
					params);
			for (LookupEntry x : statusList) {
				if (statusNo.equals(x.getCode())) {
					return x.getName();
				}
			}
		}
		return "";
	}


	@Override
	public String getAskPaymentStatusName(String statusNo)
			throws ManagerException {
		if (StringUtils.isEmpty(statusNo)) {
			return "";
		} else {
			AskPaymentStatusEnums[] statusList = AskPaymentStatusEnums.values();
			for (AskPaymentStatusEnums x : statusList) {
				if (statusNo.equals(x.getStatusNo())) {
					return x.getStatusName();
				}
			}
		}
		return "";
	}

	@Override
	public int countHQNeedRuleMatchItems(Map<String, Object> params) throws ManagerException {
		try{
	    	return this.commonUtilService.countHQNeedRuleMatchItems(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public int countRegionNeedRuleMatchItems(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.countRegionNeedRuleMatchItems(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<HeadquarterCostMaintain> queryHQNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try{
	    	return this.commonUtilService.queryHQNeedRuleMatchItems(page,sortColumn,sortOrder,params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<RegionCostMaintain> queryRegionNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.queryRegionNeedRuleMatchItems(page,sortColumn,sortOrder,params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public String getBillTypeName(String typeNo) throws ManagerException {
		if (StringUtils.isEmpty(typeNo)) {
			return "";
		} else {
			BillTypeEnums[] statusList = BillTypeEnums.values();
			for (BillTypeEnums x : statusList) {
				if (typeNo.equals(x.getRequestId().toString())) {
					return x.getDesc();
				}
			}
		}
		return "";
	}

	@Override
	public String getBillStatusName(String statusNo) throws ManagerException {
		if (StringUtils.isEmpty(statusNo)) {
			return "";
		} else {
			GmsBillStatusEnums[] statusList = GmsBillStatusEnums.values();
			for (GmsBillStatusEnums x : statusList) {
				if (statusNo.equals(x.getStatus().toString())) {
					return x.getText();
				}
			}
		}
		return "";
	}


	@Override
	public String generateBillNo(int typeNo) throws ManagerException {
		try{
	    	return this.commonUtilService.generateBillNo(typeNo);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public int findShopCount(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.findShopCount(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<ShopVo> findShopByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.findShopByPage(page, sortColumn, sortOrder, params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}
	
	@Override
	public int selectPageOrganizationCount(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.selectPageOrganizationCount(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<LookupVo> selectPageOrganizationList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.selectPageOrganizationList(page, sortColumn, sortOrder, params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public int selectItemExtendsCount(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.selectItemExtendsCount(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<ItemDto> selectItemExtendsList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.selectItemExtendsList(page, sortColumn, sortOrder, params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public int countHQFirstNewNeedRuleMatchItems(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.countHQFirstNewNeedRuleMatchItems(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}


	@Override
	public List<HeadquarterCostMaintain> queryHQFirstNewNeedRuleMatchItems(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try{
	    	return this.commonUtilService.queryHQFirstNewNeedRuleMatchItems(page,sortColumn,sortOrder,params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public int countQuotationRuleMatchItems(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.countQuotationRuleMatchItems(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<HeadquarterCostMaintain> queryQuotationRuleMatchItems(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try{
	    	return this.commonUtilService.queryQuotationRuleMatchItems(page,sortColumn,sortOrder,params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<ZoneInfo> findPriceZones(Map<String, Object> params)
			throws ManagerException {
		try{
	    	return this.commonUtilService.findPriceZones(params);
	    }catch(ServiceException e){
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}
	
}
