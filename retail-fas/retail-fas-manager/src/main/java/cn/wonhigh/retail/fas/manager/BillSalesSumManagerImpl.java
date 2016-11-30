package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.core.SpringContext;
import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.common.model.ShopNameReplace;
import cn.wonhigh.retail.fas.common.model.SystemParamSet;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.service.BillSalesSumService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-11 15:51:08
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
@Service("billSalesSumManager")
public class BillSalesSumManagerImpl extends BaseCrudManagerImpl implements BillSalesSumManager {
    @Resource
    private BillSalesSumService billSalesSumService;

    @Override
    public BaseCrudService init() {
        return billSalesSumService;
    }

	@Override
	public List<BillSalesSum> findSalesSum(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billSalesSumService.findSalesSum(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);

		}
	}

	@Override
	public List<BillSalesSum> findSaleGoodsGms(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billSalesSumService.findSaleGoodsGms(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);

		}
	}

	@Override
	public BillSalesSum initSubsiInfo(Map<String, Object> params) {
		return billSalesSumService.selectSubsiInfo(params);
	}

	@Override
	public List<BillSalesSum> initSubsiInfoList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billSalesSumService.selectSubsiInfoList(page, orderByField, orderBy, params);
	}

	@Override
	public BillSalesSum selectSalesSumPosCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		try {
			return billSalesSumService.selectSalesSumPosCount(params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSalesSum> selectSalesSumPos(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		try {
			return billSalesSumService.selectSalesSumPos(page, orderByField, orderBy, params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillSalesSum selectSalesSumGmsCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		try {
			return billSalesSumService.selectSalesSumGmsCount(params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSalesSum> selectSalesSumGms(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		try {
			return billSalesSumService.selectSalesSumGms(page, orderByField, orderBy, params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	
	
	

	@Override
	public BillSalesSum selectSalesSumOtherCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		try {
			return billSalesSumService.selectSalesSumOtherCount(params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<BillSalesSum> selectSalesSumOther(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		try {
			return billSalesSumService.selectSalesSumOther(page, orderByField, orderBy, params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSalesSum> checkShopBalanceDate(Map<String, Object> params)
			throws ManagerException {
		try {
			return billSalesSumService.checkShopBalanceDate(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal getPredictionDeductions(String shopNo,
			Date invoiceApplyDate, String brandUnitNo, String categoryNo)
			throws ManagerException {
		try {
			return billSalesSumService.getPredictionDeductions(shopNo, invoiceApplyDate, brandUnitNo, categoryNo);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 格式化参数
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	public static  Map<String, Object> formatParams(Map<String, Object> params) throws ManagerException {
		//业务类型多选
		if(params.get("businessType") != null && !"".equals(params.get("businessType").toString())) {
			String[] temps = params.get("businessType").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("businessTypes", tempList);
		}
		
		//公司多选
		if(params.get("companyNo") != null && !"".equals(params.get("companyNo").toString())) {
			String[] temps = params.get("companyNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("companyNos", tempList);
		}
		
		//省份多选
		if(params.get("provinceNo") != null && !"".equals(params.get("provinceNo").toString())) {
			String[] temps = params.get("provinceNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("provinceNos", tempList);
		}
		
		//管理城市多选
		if(params.get("organNo") != null && !"".equals(params.get("organNo").toString())) {
			String[] temps = params.get("organNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("organNos", tempList);
		}
		
		//店铺多选
		if(params.get("shopNo") != null && !"".equals(params.get("shopNo").toString())) {
			String[] temps = params.get("shopNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("shopNos", tempList);
		}
		
		//店铺大类多选
		if(params.get("saleMode") != null && !"".equals(params.get("saleMode").toString())) {
			String[] temps = params.get("saleMode").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("saleModes", tempList);
		}
		
		//店铺小类多选
		if(params.get("retailType") != null && !"".equals(params.get("retailType").toString())) {
			String[] temps = params.get("retailType").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("retailTypes", tempList);
		}
		
		//店铺细类多选
		if(params.get("shopMulti") != null && !"".equals(params.get("shopMulti").toString())) {
			String[] temps = params.get("shopMulti").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("shopMultis", tempList);
		}
		
		//店铺级别多选
		if(params.get("shopLevel") != null && !"".equals(params.get("shopLevel").toString())) {
			String[] temps = params.get("shopLevel").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("shopLevels", tempList);
		}
		
		//品牌多选
		if(params.get("brandNo") != null && !"".equals(params.get("brandNo").toString())) {
			String[] temps = params.get("brandNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("brandNos", tempList);
		}
		
		//大类多选
		if(params.get("categoryNo") != null && !"".equals(params.get("categoryNo").toString())) {
			String[] temps = params.get("categoryNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("categoryNos", tempList);
		}
		// 客户
		if(params.get("customerNo") != null && !"".equals(params.get("customerNo").toString())) {
			String[] temps = params.get("customerNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("customerNos", tempList);
		}
		boolean isPos = false;
		boolean isGms = false;
		boolean isOther = false;
		boolean isPe = ShardingUtil.isPE();
		params.put("isPe", isPe);
		//判断是否查询对应业务类型数据
		@SuppressWarnings("unchecked")
		List<String> businessTypes = params.get("businessTypes") == null ? null : (List<String>) params.get("businessTypes");
		//如果没传业务类型，则补全查询全部
		if(CollectionUtils.isEmpty(businessTypes)){
			businessTypes = Arrays.asList("11","12","21","22","23","24","31","41","42","43","44","45","51","52","53","54","55","56","57","58","59","510");
			if(isPe){//体育
				businessTypes = Arrays.asList("11","12","21","22","23","24","25","31","41","42","43","44","45","46","51","52","53","54","55","56","57","58","59","510");
			}
			params.put("businessTypes", businessTypes);
		}
		
		if(businessTypes.contains("11") 
				|| businessTypes.contains("12")) {
			isPos = true;
		}
		if(businessTypes.contains("21") 
				|| businessTypes.contains("22")
				|| businessTypes.contains("23")
				|| businessTypes.contains("24")
				|| businessTypes.contains("25")
				|| businessTypes.contains("31")
				|| businessTypes.contains("41")
				|| businessTypes.contains("42")
				|| businessTypes.contains("43")
				|| businessTypes.contains("44")
				|| businessTypes.contains("45")
				|| businessTypes.contains("46")) {
			isGms = true;
		}
		if(businessTypes.contains("51") 
				|| businessTypes.contains("52")
				|| businessTypes.contains("53")
				|| businessTypes.contains("54")
				|| businessTypes.contains("55")
				|| businessTypes.contains("56")
				|| businessTypes.contains("57")
				|| businessTypes.contains("58")
				|| businessTypes.contains("59")
				|| businessTypes.contains("510")) {
			isOther = true;
		}
		params.put("isPos", isPos);
		params.put("isGms", isGms);
		params.put("isOther", isOther);
		SystemParamSetManager systemParamSetManager = SpringContext.getBean(SystemParamSetManager.class);
		
		//根据大区查询批发业务汇总维度
		Map<String, Object> wholesaleSysParam = new HashMap<String, Object>();
		wholesaleSysParam.put("paramCode", "AR_SalesSum_Wholesale");
		wholesaleSysParam.put("businessOrganNo", params.get("zoneNo").toString());
		wholesaleSysParam.put("status", 1);
		List<SystemParamSet> wholesaleSysParamList = systemParamSetManager.findByBiz(null, wholesaleSysParam);
		if(!CollectionUtils.isEmpty(wholesaleSysParamList)) {
			params.put("countFieldsWholesale", wholesaleSysParamList.get(0).getDtlValue());
		}
		
		//根据大区查询内购业务汇总维度
		Map<String, Object> insidebizSysParam = new HashMap<String, Object>();
		insidebizSysParam.put("paramCode", "AR_SalesSum_Insidebiz");
		insidebizSysParam.put("businessOrganNo", params.get("zoneNo").toString());
		insidebizSysParam.put("status", 1);
		List<SystemParamSet> insidebizSysParamList = systemParamSetManager.findByBiz(null, insidebizSysParam);
		if(!CollectionUtils.isEmpty(insidebizSysParamList)) {
			params.put("countFieldsInsidebiz", insidebizSysParamList.get(0).getDtlValue());
		}
		
		return params;
	}
	
	/**
	 * 字段处理
	 * @param list
	 * @return
	 * @throws ManagerException 
	 */
	public static  List<BillSalesSum> areaTailor(List<BillSalesSum> list) throws ManagerException {
		ShopNameReplaceManager shopNameReplaceManager = SpringContext.getBean(ShopNameReplaceManager.class);
		BillSalesSumManager billSalesSumManager  = SpringContext.getBean(BillSalesSumManager.class);
		for(BillSalesSum billSalesSum : list) {
			
			//门店、内购--添加品牌部编号至店铺名称后
			if("1".equals(billSalesSum.getBizType()) || "4".equals(billSalesSum.getBizType())) {
				ShopNameReplace shopNameReplace = shopNameReplaceManager.selectReplaceName(billSalesSum.getShopNo(), billSalesSum.getBrandUnitNo());
				if(shopNameReplace !=null) {
					billSalesSum.setShortName(shopNameReplace.getReplaceName());
					billSalesSum.setShopNoReplace(shopNameReplace.getReplaceNo());
				}
				
				//门店查询前期少估扣费
				if("1".equals(billSalesSum.getBizType())) {
					billSalesSum.setBiPeriodPredictiondeductions(new BigDecimal(0d));
					
					//如果只有正式结算单，并且已开票
					if(billSalesSum.getIsSearchPrediction() != null && billSalesSum.getIsSearchPrediction().intValue() == 1 && billSalesSum.getInvoiceApplyDate() != null) {
						BigDecimal predictionDeductions = billSalesSumManager.getPredictionDeductions(billSalesSum.getShopNo(), billSalesSum.getInvoiceApplyDate(), billSalesSum.getBrandUnitNo(), billSalesSum.getCategoryNo());
						if(predictionDeductions != null) {
							billSalesSum.setBiPeriodPredictiondeductions(predictionDeductions);
						}
					}
					
					billSalesSum.setBiPeriodMalldeductions(billSalesSum.getBiPeriodSalesdeductions().add(billSalesSum.getBiPeriodPredictiondeductions()));
					billSalesSum.setTmPredictiondeductions(billSalesSum.getBiPeriodPredictiondeductions());
				}
			}
			
			//批发--华北模板显示批发加盟店
			if("2".equals(billSalesSum.getBizType())) {
				billSalesSum.setHb_multi1("批发加盟店");
			} else {
				billSalesSum.setHb_multi1(billSalesSum.getMulti1());
			}
			
			//批发--西北模板拼店名
			if("2".equals(billSalesSum.getBizType())) {
				billSalesSum.setXb_shortName(billSalesSum.getOrganName() + "批发" + billSalesSum.getBrandUnitNo() + "店");
			}
			else if("4".equals(billSalesSum.getBizType())) {
				billSalesSum.setXb_shortName(billSalesSum.getOrganName() + "批发" + billSalesSum.getBrandUnitNo() + "员购店");
			} else {
				billSalesSum.setXb_shortName(billSalesSum.getShortName());
			}
		}
		return list;
	}
	
	/**
	 * 物料是否显示成本
	 * @param list
	 * @return
	 */
	public static  List<BillSalesSum> isShowCost(List<BillSalesSum> list) {
		
		for(BillSalesSum billSalesSum : list) {
			if("物料".equals(billSalesSum.getCategoryName())) {
				if(null != billSalesSum.getLmaPeriodSalescost()) {
					billSalesSum.setLmaPeriodSalescost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getTmiPeriodSalescost()) {
					billSalesSum.setTmiPeriodSalescost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getBiPeriodSalescost()) {
					billSalesSum.setBiPeriodSalescost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getTmaPeriodSalescost()) {
					billSalesSum.setTmaPeriodSalescost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getTmSalescost()) {
					billSalesSum.setTmSalescost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getTaxCost()) {
					billSalesSum.setTaxCost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getNoTaxCosts()) {
					billSalesSum.setNoTaxCosts(new BigDecimal(0d));
				}
				if(null != billSalesSum.getRegionCost()) {
					billSalesSum.setRegionCost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getHeadquarterCost()) {
					billSalesSum.setHeadquarterCost(new BigDecimal(0d));
				}
				if(null != billSalesSum.getPurchasePrice()) {
					billSalesSum.setPurchasePrice(new BigDecimal(0d));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 根据大区、公司、管理城市、店铺/客户分组进行小计
	 * @param list 原数据列表
	 * @param countType 小计维度
	 * @param countlist 小计列表
	 * @return
	 */
	public static  List<BillSalesSum> littleCount(List<BillSalesSum> list, Integer countType, List<BillSalesSum> countlist) {
		
		//分组小计
		String bizTypeGroup = "";
		String companyNoGroup = "";
		String organNoGroup = "";
		String shopNoGroup = "";
		String monthGroup = "";
		String balanceDateGroup = "";
		String brandUnitNoGroup = "";
		List<String> groupList = new ArrayList<String>();
		groupList.add(bizTypeGroup);
		groupList.add(companyNoGroup);
		groupList.add(organNoGroup);
		groupList.add(shopNoGroup);
		groupList.add(monthGroup);
		groupList.add(balanceDateGroup);
		groupList.add(brandUnitNoGroup);
		
		BillSalesSum groupTemp = null;
		
		for(int i = 0; i < list.size(); i++) {
			
			BillSalesSum billSalesSum = list.get(i);
			
			String bizTypeCompare = billSalesSum.getBizType() != null ? billSalesSum.getBizType() : "";
			String companyNoCompare = billSalesSum.getCompanyNo() != null ? billSalesSum.getCompanyNo() : "";
			String organNoCompare = billSalesSum.getOrganNo() != null ? billSalesSum.getOrganNo() : "";
			String shopNoCompare = billSalesSum.getShopNo() != null ? billSalesSum.getShopNo() : "";
			String monthCompare = billSalesSum.getMonth() != null ? billSalesSum.getMonth() : "";
			String balanceDateCompare = billSalesSum.getBalanceStartDate() != null ? DateUtil.format1(billSalesSum.getBalanceStartDate()) : "";
			String brandUnitNoCompare = billSalesSum.getBrandUnitNo() != null ? billSalesSum.getBrandUnitNo() : "";
			List<String> groupCompareList = new ArrayList<String>();
			groupCompareList.add(bizTypeCompare);
			groupCompareList.add(companyNoCompare);
			groupCompareList.add(organNoCompare);
			groupCompareList.add(shopNoCompare);
			groupCompareList.add(monthCompare);
			groupCompareList.add(balanceDateCompare);
			groupCompareList.add(brandUnitNoCompare);
			
			//小计维度根据条件判断分组情况
			boolean isGroupCompare = true;
			for(int j = 0; j < countType; j++) {
				if(!groupList.get(j).equals(groupCompareList.get(j))){
					isGroupCompare = false;
				}
			}
			
			//分组不同则新初始化一条小计
			if(!isGroupCompare) {
				if(groupTemp != null) {
					list.add(i, groupTemp);
					i++;
					countlist.add(groupTemp);
				}
				groupTemp = new BillSalesSum();
				initBillSalesSum(groupTemp);
				groupTemp.setZoneName("小计：");
				switch (countType) {
					case 7:
						groupTemp.setBrandUnitNo(billSalesSum.getBrandUnitNo());
						groupTemp.setBrandUnitName(billSalesSum.getBrandUnitName());
					case 6:
						groupTemp.setBillMonth(billSalesSum.getBillMonth());
						groupTemp.setBalanceStartDate(billSalesSum.getBalanceStartDate());
						groupTemp.setBalanceEndDate(billSalesSum.getBalanceEndDate());
						groupTemp.setSaleMonth(billSalesSum.getSaleMonth());
					case 5:
						groupTemp.setMonth(billSalesSum.getMonth());
					case 4:
						groupTemp.setShopNo(billSalesSum.getShopNo());
						groupTemp.setShortName(billSalesSum.getShortName());
					case 3:
						groupTemp.setOrganNo(billSalesSum.getOrganNo());
						groupTemp.setOrganName(billSalesSum.getOrganName());
					case 2:
						groupTemp.setCompanyNo(billSalesSum.getCompanyNo());
						groupTemp.setCompanyName(billSalesSum.getCompanyName());
					case 1:
						groupTemp.setBizType(billSalesSum.getBizType());
						groupTemp.setBizTypeName(billSalesSum.getBizTypeName());
						break;
	
					default:
						break;
				}
				
				for(int j = 0; j < countType; j++) {
					groupList.set(j, groupCompareList.get(j));
				}
			}
			sumToBillSalesSum(groupTemp, billSalesSum);
			
			if(i == list.size()-1) {
				list.add(i + 1, groupTemp);
				i++;
				countlist.add(groupTemp);
			}
		}
		
		return list;
	}
	
	/**
	 * 初始化统计对象
	 * @param billSalesSum
	 */
	public static  void initBillSalesSum(BillSalesSum billSalesSum) { 
		billSalesSum.setLmaPeriodSalesnum(0);
		billSalesSum.setLmaPeriodRealamount(new BigDecimal(0d));
		billSalesSum.setLmaPeriodSalesamount(new BigDecimal(0d));
		billSalesSum.setLmaPeriodTagamount(new BigDecimal(0d));
		billSalesSum.setLmaPeriodSalescost(new BigDecimal(0d));
		billSalesSum.setLmaPeriodSalesdeductions(new BigDecimal(0d));
		billSalesSum.setLmaPeriodContractdeductions(new BigDecimal(0d));
		billSalesSum.setLmaPeriodCutratedeductions(new BigDecimal(0d));
		billSalesSum.setLmaPeriodPromotiondeductions(new BigDecimal(0d));
		billSalesSum.setLmaPeriodBalanceamount(new BigDecimal(0d));
		
		billSalesSum.setTmiPeriodSalesnum(0);
		billSalesSum.setTmiPeriodRealamount(new BigDecimal(0d));
		billSalesSum.setTmiPeriodSalesamount(new BigDecimal(0d));
		billSalesSum.setTmiPeriodTagamount(new BigDecimal(0d));
		billSalesSum.setTmiPeriodSalescost(new BigDecimal(0d));
		billSalesSum.setTmiPeriodSalesdeductions(new BigDecimal(0d));
		billSalesSum.setTmiPeriodContractdeductions(new BigDecimal(0d));
		billSalesSum.setTmiPeriodCutratedeductions(new BigDecimal(0d));
		billSalesSum.setTmiPeriodPromotiondeductions(new BigDecimal(0d));
		billSalesSum.setTmiPeriodBalanceamount(new BigDecimal(0d));
		
		billSalesSum.setBiPeriodSalesnum(0);
		billSalesSum.setBiPeriodRealamount(new BigDecimal(0d));
		billSalesSum.setBiPeriodSalesamount(new BigDecimal(0d));
		billSalesSum.setBiPeriodTagamount(new BigDecimal(0d));
		billSalesSum.setBiPeriodSalescost(new BigDecimal(0d));
		billSalesSum.setBiPeriodSalesdeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodContractdeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodCutratedeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodPromotiondeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodOtherdeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodPredictiondeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodMalldeductions(new BigDecimal(0d));
		billSalesSum.setBiPeriodBalanceamount(new BigDecimal(0d));
		billSalesSum.setBiPeriodNonTaxBalanceamount(new BigDecimal(0d));
		billSalesSum.setQty(0);
		billSalesSum.setSendAmount(new BigDecimal(0d));
		
		billSalesSum.setTmaPeriodSalesnum(0);
		billSalesSum.setTmaPeriodRealamount(new BigDecimal(0d));
		billSalesSum.setTmaPeriodSalesamount(new BigDecimal(0d));
		billSalesSum.setTmaPeriodTagamount(new BigDecimal(0d));
		billSalesSum.setTmaPeriodSalescost(new BigDecimal(0d));
		billSalesSum.setTmaPeriodSalesdeductions(new BigDecimal(0d));
		billSalesSum.setTmaPeriodContractdeductions(new BigDecimal(0d));
		billSalesSum.setTmaPeriodCutratedeductions(new BigDecimal(0d));
		billSalesSum.setTmaPeriodPromotiondeductions(new BigDecimal(0d));
		billSalesSum.setTmaPeriodBalanceamount(new BigDecimal(0d));
		billSalesSum.setTmaPeriodNonTaxBalanceamount(new BigDecimal(0d));
		
		billSalesSum.setTmSalesnum(0);
		billSalesSum.setTmRealamount(new BigDecimal(0d));
		billSalesSum.setTmSalesamount(new BigDecimal(0d));
		billSalesSum.setTmTagamount(new BigDecimal(0d));
		billSalesSum.setTmSalescost(new BigDecimal(0d));
		billSalesSum.setTmSalesdeductions(new BigDecimal(0d));
		billSalesSum.setTmContractdeductions(new BigDecimal(0d));
		billSalesSum.setTmCutratedeductions(new BigDecimal(0d));
		billSalesSum.setTmPromotiondeductions(new BigDecimal(0d));
		billSalesSum.setTmOtherdeductions(new BigDecimal(0d));
		billSalesSum.setTmPredictiondeductions(new BigDecimal(0d));
		billSalesSum.setTmBalanceamount(new BigDecimal(0d));
		
		billSalesSum.setSumChangebalanceamount(new BigDecimal(0d));
		billSalesSum.setSumSalesdeductions(new BigDecimal(0d));
		billSalesSum.setSystemDeductAmount(new BigDecimal(0d));
		billSalesSum.setBalanceDeductAmount(new BigDecimal(0d));
		billSalesSum.setBalanceDiffAmount(new BigDecimal(0d));
		
		billSalesSum.setNonTaxSalesamount(new BigDecimal(0d));
		billSalesSum.setTaxCost(new BigDecimal(0d));
		billSalesSum.setNoTaxCosts(new BigDecimal(0d));
		billSalesSum.setRegionCost(new BigDecimal(0d));
		billSalesSum.setHeadquarterCost(new BigDecimal(0d));
		billSalesSum.setPurchasePrice(new BigDecimal(0d));
		
		billSalesSum.setBiMallNumberAmount(new BigDecimal(0d));
		billSalesSum.setBiSalesDiffamount(new BigDecimal(0d));
		billSalesSum.setBiExpenseOperateAmount(new BigDecimal(0d));
		billSalesSum.setBiBillingAmount(new BigDecimal(0d));
		billSalesSum.setTmaPromDeductAmount(new BigDecimal(0d));
		
	}
	
	/**
	 * 将第二个对象的值累加至第一个对象中
	 * @param mainBillSalesSum
	 * @param addBillSalesSum
	 */
	public static  void sumToBillSalesSum(BillSalesSum mainBillSalesSum, BillSalesSum addBillSalesSum) {
		
		//上月结算期后
		if(null != mainBillSalesSum.getLmaPeriodSalesnum() && null != addBillSalesSum.getLmaPeriodSalesnum()){
			mainBillSalesSum.setLmaPeriodSalesnum(mainBillSalesSum.getLmaPeriodSalesnum() + addBillSalesSum.getLmaPeriodSalesnum());
		}
		if(null != mainBillSalesSum.getLmaPeriodRealamount() && null != addBillSalesSum.getLmaPeriodRealamount()){
			mainBillSalesSum.setLmaPeriodRealamount(mainBillSalesSum.getLmaPeriodRealamount().add(addBillSalesSum.getLmaPeriodRealamount()));
		}
		if(null != mainBillSalesSum.getLmaPeriodSalesamount() && null != addBillSalesSum.getLmaPeriodSalesamount()){
			mainBillSalesSum.setLmaPeriodSalesamount(mainBillSalesSum.getLmaPeriodSalesamount().add(addBillSalesSum.getLmaPeriodSalesamount()));
		}
		if(null != mainBillSalesSum.getLmaPeriodTagamount() && null != addBillSalesSum.getLmaPeriodTagamount()){
			mainBillSalesSum.setLmaPeriodTagamount(mainBillSalesSum.getLmaPeriodTagamount().add(addBillSalesSum.getLmaPeriodTagamount()));
		}
		if(null != mainBillSalesSum.getLmaPeriodSalescost() && null != addBillSalesSum.getLmaPeriodSalescost()){
			mainBillSalesSum.setLmaPeriodSalescost(mainBillSalesSum.getLmaPeriodSalescost().add(addBillSalesSum.getLmaPeriodSalescost()));
		}
		if(null != mainBillSalesSum.getLmaPeriodSalesdeductions() && null != addBillSalesSum.getLmaPeriodSalesdeductions()){
			mainBillSalesSum.setLmaPeriodSalesdeductions(mainBillSalesSum.getLmaPeriodSalesdeductions().add(addBillSalesSum.getLmaPeriodSalesdeductions()));
		}
		if(null != mainBillSalesSum.getLmaPeriodContractdeductions() && null != addBillSalesSum.getLmaPeriodContractdeductions()){
			mainBillSalesSum.setLmaPeriodContractdeductions(mainBillSalesSum.getLmaPeriodContractdeductions().add(addBillSalesSum.getLmaPeriodContractdeductions()));
		}
		if(null != mainBillSalesSum.getLmaPeriodCutratedeductions() && null != addBillSalesSum.getLmaPeriodCutratedeductions()){
			mainBillSalesSum.setLmaPeriodCutratedeductions(mainBillSalesSum.getLmaPeriodCutratedeductions().add(addBillSalesSum.getLmaPeriodCutratedeductions()));
		}
		if(null != mainBillSalesSum.getLmaPeriodPromotiondeductions() && null != addBillSalesSum.getLmaPeriodPromotiondeductions()){
			mainBillSalesSum.setLmaPeriodPromotiondeductions(mainBillSalesSum.getLmaPeriodPromotiondeductions().add(addBillSalesSum.getLmaPeriodPromotiondeductions()));
		}
		if(null != mainBillSalesSum.getLmaPeriodBalanceamount() && null != addBillSalesSum.getLmaPeriodBalanceamount()){
			mainBillSalesSum.setLmaPeriodBalanceamount(mainBillSalesSum.getLmaPeriodBalanceamount().add(addBillSalesSum.getLmaPeriodBalanceamount()));
		}
		
		//本月结算期内
		if(null != mainBillSalesSum.getTmiPeriodSalesnum() && null != addBillSalesSum.getTmiPeriodSalesnum()){
			mainBillSalesSum.setTmiPeriodSalesnum(mainBillSalesSum.getTmiPeriodSalesnum() + addBillSalesSum.getTmiPeriodSalesnum());
		}
		if(null != mainBillSalesSum.getTmiPeriodRealamount() && null != addBillSalesSum.getTmiPeriodRealamount()){
			mainBillSalesSum.setTmiPeriodRealamount(mainBillSalesSum.getTmiPeriodRealamount().add(addBillSalesSum.getTmiPeriodRealamount()));
		}
		if(null != mainBillSalesSum.getTmiPeriodSalesamount() && null != addBillSalesSum.getTmiPeriodSalesamount()){
			mainBillSalesSum.setTmiPeriodSalesamount(mainBillSalesSum.getTmiPeriodSalesamount().add(addBillSalesSum.getTmiPeriodSalesamount()));
		}
		if(null != mainBillSalesSum.getTmiPeriodTagamount() && null != addBillSalesSum.getTmiPeriodTagamount()){
			mainBillSalesSum.setTmiPeriodTagamount(mainBillSalesSum.getTmiPeriodTagamount().add(addBillSalesSum.getTmiPeriodTagamount()));
		}
		if(null != mainBillSalesSum.getTmiPeriodSalescost() && null != addBillSalesSum.getTmiPeriodSalescost()){
			mainBillSalesSum.setTmiPeriodSalescost(mainBillSalesSum.getTmiPeriodSalescost().add(addBillSalesSum.getTmiPeriodSalescost()));
		}
		if(null != mainBillSalesSum.getTmiPeriodSalesdeductions() && null != addBillSalesSum.getTmiPeriodSalesdeductions()){
			mainBillSalesSum.setTmiPeriodSalesdeductions(mainBillSalesSum.getTmiPeriodSalesdeductions().add(addBillSalesSum.getTmiPeriodSalesdeductions()));
		}
		if(null != mainBillSalesSum.getTmiPeriodContractdeductions() && null != addBillSalesSum.getTmiPeriodContractdeductions()){
			mainBillSalesSum.setTmiPeriodContractdeductions(mainBillSalesSum.getTmiPeriodContractdeductions().add(addBillSalesSum.getTmiPeriodContractdeductions()));
		}
		if(null != mainBillSalesSum.getTmiPeriodCutratedeductions() && null != addBillSalesSum.getTmiPeriodCutratedeductions()){
			mainBillSalesSum.setTmiPeriodCutratedeductions(mainBillSalesSum.getTmiPeriodCutratedeductions().add(addBillSalesSum.getTmiPeriodCutratedeductions()));
		}
		if(null != mainBillSalesSum.getTmiPeriodPromotiondeductions() && null != addBillSalesSum.getTmiPeriodPromotiondeductions()){
			mainBillSalesSum.setTmiPeriodPromotiondeductions(mainBillSalesSum.getTmiPeriodPromotiondeductions().add(addBillSalesSum.getTmiPeriodPromotiondeductions()));
		}
		if(null != mainBillSalesSum.getTmiPeriodBalanceamount() && null != addBillSalesSum.getTmiPeriodBalanceamount()){
			mainBillSalesSum.setTmiPeriodBalanceamount(mainBillSalesSum.getTmiPeriodBalanceamount().add(addBillSalesSum.getTmiPeriodBalanceamount()));
		}
		
		//结算期内合计
		if(null != mainBillSalesSum.getBiPeriodSalesnum() && null != addBillSalesSum.getBiPeriodSalesnum()){
			mainBillSalesSum.setBiPeriodSalesnum(mainBillSalesSum.getBiPeriodSalesnum() + addBillSalesSum.getBiPeriodSalesnum());
		}
		if(null != mainBillSalesSum.getBiPeriodRealamount() && null != addBillSalesSum.getBiPeriodRealamount()){
			mainBillSalesSum.setBiPeriodRealamount(mainBillSalesSum.getBiPeriodRealamount().add(addBillSalesSum.getBiPeriodRealamount()));
		}
		if(null != mainBillSalesSum.getBiPeriodSalesamount() && null != addBillSalesSum.getBiPeriodSalesamount()){
			mainBillSalesSum.setBiPeriodSalesamount(mainBillSalesSum.getBiPeriodSalesamount().add(addBillSalesSum.getBiPeriodSalesamount()));
		}
		if(null != mainBillSalesSum.getBiPeriodTagamount() && null != addBillSalesSum.getBiPeriodTagamount()){
			mainBillSalesSum.setBiPeriodTagamount(mainBillSalesSum.getBiPeriodTagamount().add(addBillSalesSum.getBiPeriodTagamount()));
		}
		if(null != mainBillSalesSum.getBiPeriodSalescost() && null != addBillSalesSum.getBiPeriodSalescost()){
			mainBillSalesSum.setBiPeriodSalescost(mainBillSalesSum.getBiPeriodSalescost().add(addBillSalesSum.getBiPeriodSalescost()));
		}
		if(null != mainBillSalesSum.getBiPeriodSalesdeductions() && null != addBillSalesSum.getBiPeriodSalesdeductions()){
			mainBillSalesSum.setBiPeriodSalesdeductions(mainBillSalesSum.getBiPeriodSalesdeductions().add(addBillSalesSum.getBiPeriodSalesdeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodContractdeductions() && null != addBillSalesSum.getBiPeriodContractdeductions()){
			mainBillSalesSum.setBiPeriodContractdeductions(mainBillSalesSum.getBiPeriodContractdeductions().add(addBillSalesSum.getBiPeriodContractdeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodCutratedeductions() && null != addBillSalesSum.getBiPeriodCutratedeductions()){
			mainBillSalesSum.setBiPeriodCutratedeductions(mainBillSalesSum.getBiPeriodCutratedeductions().add(addBillSalesSum.getBiPeriodCutratedeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodPromotiondeductions() && null != addBillSalesSum.getBiPeriodPromotiondeductions()){
			mainBillSalesSum.setBiPeriodPromotiondeductions(mainBillSalesSum.getBiPeriodPromotiondeductions().add(addBillSalesSum.getBiPeriodPromotiondeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodOtherdeductions() && null != addBillSalesSum.getBiPeriodOtherdeductions()){
			mainBillSalesSum.setBiPeriodOtherdeductions(mainBillSalesSum.getBiPeriodOtherdeductions().add(addBillSalesSum.getBiPeriodOtherdeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodPredictiondeductions() && null != addBillSalesSum.getBiPeriodPredictiondeductions()){
			mainBillSalesSum.setBiPeriodPredictiondeductions(mainBillSalesSum.getBiPeriodPredictiondeductions().add(addBillSalesSum.getBiPeriodPredictiondeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodMalldeductions() && null != addBillSalesSum.getBiPeriodMalldeductions()){
			mainBillSalesSum.setBiPeriodMalldeductions(mainBillSalesSum.getBiPeriodMalldeductions().add(addBillSalesSum.getBiPeriodMalldeductions()));
		}
		if(null != mainBillSalesSum.getBiPeriodBalanceamount() && null != addBillSalesSum.getBiPeriodBalanceamount()){
			mainBillSalesSum.setBiPeriodBalanceamount(mainBillSalesSum.getBiPeriodBalanceamount().add(addBillSalesSum.getBiPeriodBalanceamount()));
		}
		if(null != mainBillSalesSum.getBiPeriodNonTaxBalanceamount() && null != addBillSalesSum.getBiPeriodNonTaxBalanceamount()){
			mainBillSalesSum.setBiPeriodNonTaxBalanceamount(mainBillSalesSum.getBiPeriodNonTaxBalanceamount().add(addBillSalesSum.getBiPeriodNonTaxBalanceamount()));
		}
		if(null != mainBillSalesSum.getQty() && null != addBillSalesSum.getQty()){
			mainBillSalesSum.setQty(mainBillSalesSum.getQty() + addBillSalesSum.getQty());
		}
		if(null != mainBillSalesSum.getSendAmount() && null != addBillSalesSum.getSendAmount()){
			mainBillSalesSum.setSendAmount(mainBillSalesSum.getSendAmount().add(addBillSalesSum.getSendAmount()));
		}
		
		if(null != mainBillSalesSum.getBiMallNumberAmount() && null != addBillSalesSum.getBiMallNumberAmount()){
			mainBillSalesSum.setBiMallNumberAmount(mainBillSalesSum.getBiMallNumberAmount().add(addBillSalesSum.getBiMallNumberAmount()));
		}
		if(null != mainBillSalesSum.getBiSalesDiffamount() && null != addBillSalesSum.getBiSalesDiffamount()){
			mainBillSalesSum.setBiSalesDiffamount(mainBillSalesSum.getBiSalesDiffamount().add(addBillSalesSum.getBiSalesDiffamount()));
		}
		if(null != mainBillSalesSum.getBiExpenseOperateAmount() && null != addBillSalesSum.getBiExpenseOperateAmount()){
			mainBillSalesSum.setBiExpenseOperateAmount(mainBillSalesSum.getBiExpenseOperateAmount().add(addBillSalesSum.getBiExpenseOperateAmount()));
		}
		if(null != mainBillSalesSum.getBiBillingAmount() && null != addBillSalesSum.getBiBillingAmount()){
			mainBillSalesSum.setBiBillingAmount(mainBillSalesSum.getBiBillingAmount().add(addBillSalesSum.getBiBillingAmount()));
		}
		if(null != mainBillSalesSum.getTmaPromDeductAmount() && null != addBillSalesSum.getTmaPromDeductAmount()){
			mainBillSalesSum.setTmaPromDeductAmount(mainBillSalesSum.getTmaPromDeductAmount().add(addBillSalesSum.getTmaPromDeductAmount()));
		}
		
		//本月结算期后
		if(null != mainBillSalesSum.getTmaPeriodSalesnum() && null != addBillSalesSum.getTmaPeriodSalesnum()){
			mainBillSalesSum.setTmaPeriodSalesnum(mainBillSalesSum.getTmaPeriodSalesnum() + addBillSalesSum.getTmaPeriodSalesnum());
		}
		if(null != mainBillSalesSum.getTmaPeriodRealamount() && null != addBillSalesSum.getTmaPeriodRealamount()){
			mainBillSalesSum.setTmaPeriodRealamount(mainBillSalesSum.getTmaPeriodRealamount().add(addBillSalesSum.getTmaPeriodRealamount()));
		}
		if(null != mainBillSalesSum.getTmaPeriodSalesamount() && null != addBillSalesSum.getTmaPeriodSalesamount()){
			mainBillSalesSum.setTmaPeriodSalesamount(mainBillSalesSum.getTmaPeriodSalesamount().add(addBillSalesSum.getTmaPeriodSalesamount()));
		}
		if(null != mainBillSalesSum.getTmaPeriodTagamount() && null != addBillSalesSum.getTmaPeriodTagamount()){
			mainBillSalesSum.setTmaPeriodTagamount(mainBillSalesSum.getTmaPeriodTagamount().add(addBillSalesSum.getTmaPeriodTagamount()));
		}
		if(null != mainBillSalesSum.getTmaPeriodSalescost() && null != addBillSalesSum.getTmaPeriodSalescost()){
			mainBillSalesSum.setTmaPeriodSalescost(mainBillSalesSum.getTmaPeriodSalescost().add(addBillSalesSum.getTmaPeriodSalescost()));
		}
		if(null != mainBillSalesSum.getTmaPeriodSalesdeductions() && null != addBillSalesSum.getTmaPeriodSalesdeductions()){
			mainBillSalesSum.setTmaPeriodSalesdeductions(mainBillSalesSum.getTmaPeriodSalesdeductions().add(addBillSalesSum.getTmaPeriodSalesdeductions()));
		}
		if(null != mainBillSalesSum.getTmaPeriodContractdeductions() && null != addBillSalesSum.getTmaPeriodContractdeductions()){
			mainBillSalesSum.setTmaPeriodContractdeductions(mainBillSalesSum.getTmaPeriodContractdeductions().add(addBillSalesSum.getTmaPeriodContractdeductions()));
		}
		if(null != mainBillSalesSum.getTmaPeriodCutratedeductions() && null != addBillSalesSum.getTmaPeriodCutratedeductions()){
			mainBillSalesSum.setTmaPeriodCutratedeductions(mainBillSalesSum.getTmaPeriodCutratedeductions().add(addBillSalesSum.getTmaPeriodCutratedeductions()));
		}
		if(null != mainBillSalesSum.getTmaPeriodPromotiondeductions() && null != addBillSalesSum.getTmaPeriodPromotiondeductions()){
			mainBillSalesSum.setTmaPeriodPromotiondeductions(mainBillSalesSum.getTmaPeriodPromotiondeductions().add(addBillSalesSum.getTmaPeriodPromotiondeductions()));
		}
		if(null != mainBillSalesSum.getTmaPeriodBalanceamount() && null != addBillSalesSum.getTmaPeriodBalanceamount()){
			mainBillSalesSum.setTmaPeriodBalanceamount(mainBillSalesSum.getTmaPeriodBalanceamount().add(addBillSalesSum.getTmaPeriodBalanceamount()));
		}
		if(null != mainBillSalesSum.getTmaPeriodNonTaxBalanceamount() && null != addBillSalesSum.getTmaPeriodNonTaxBalanceamount()){
			mainBillSalesSum.setTmaPeriodNonTaxBalanceamount(mainBillSalesSum.getTmaPeriodNonTaxBalanceamount().add(addBillSalesSum.getTmaPeriodNonTaxBalanceamount()));
		}
		
		//本月合计
		if(null != mainBillSalesSum.getTmSalesnum() && null != addBillSalesSum.getTmSalesnum()){
			mainBillSalesSum.setTmSalesnum(mainBillSalesSum.getTmSalesnum() + addBillSalesSum.getTmSalesnum());
		}
		if(null != mainBillSalesSum.getTmRealamount() && null != addBillSalesSum.getTmRealamount()){
			mainBillSalesSum.setTmRealamount(mainBillSalesSum.getTmRealamount().add(addBillSalesSum.getTmRealamount()));
		}
		if(null != mainBillSalesSum.getTmSalesamount() && null != addBillSalesSum.getTmSalesamount()){
			mainBillSalesSum.setTmSalesamount(mainBillSalesSum.getTmSalesamount().add(addBillSalesSum.getTmSalesamount()));
		}
		if(null != mainBillSalesSum.getTmTagamount() && null != addBillSalesSum.getTmTagamount()){
			mainBillSalesSum.setTmTagamount(mainBillSalesSum.getTmTagamount().add(addBillSalesSum.getTmTagamount()));
		}
		if(null != mainBillSalesSum.getTmSalescost() && null != addBillSalesSum.getTmSalescost()){
			mainBillSalesSum.setTmSalescost(mainBillSalesSum.getTmSalescost().add(addBillSalesSum.getTmSalescost()));
		}
		if(null != mainBillSalesSum.getTmSalesdeductions() && null != addBillSalesSum.getTmSalesdeductions()){
			mainBillSalesSum.setTmSalesdeductions(mainBillSalesSum.getTmSalesdeductions().add(addBillSalesSum.getTmSalesdeductions()));
		}
		if(null != mainBillSalesSum.getTmContractdeductions() && null != addBillSalesSum.getTmContractdeductions()){
			mainBillSalesSum.setTmContractdeductions(mainBillSalesSum.getTmContractdeductions().add(addBillSalesSum.getTmContractdeductions()));
		}
		if(null != mainBillSalesSum.getTmCutratedeductions() && null != addBillSalesSum.getTmCutratedeductions()){
			mainBillSalesSum.setTmCutratedeductions(mainBillSalesSum.getTmCutratedeductions().add(addBillSalesSum.getTmCutratedeductions()));
		}
		if(null != mainBillSalesSum.getTmPromotiondeductions() && null != addBillSalesSum.getTmPromotiondeductions()){
			mainBillSalesSum.setTmPromotiondeductions(mainBillSalesSum.getTmPromotiondeductions().add(addBillSalesSum.getTmPromotiondeductions()));
		}
		if(null != mainBillSalesSum.getTmOtherdeductions() && null != addBillSalesSum.getTmOtherdeductions()){
			mainBillSalesSum.setTmOtherdeductions(mainBillSalesSum.getTmOtherdeductions().add(addBillSalesSum.getTmOtherdeductions()));
		}
		if(null != mainBillSalesSum.getTmPredictiondeductions() && null != addBillSalesSum.getTmPredictiondeductions()){
			mainBillSalesSum.setTmPredictiondeductions(mainBillSalesSum.getTmPredictiondeductions().add(addBillSalesSum.getTmPredictiondeductions()));
		}
		if(null != mainBillSalesSum.getTmBalanceamount() && null != addBillSalesSum.getTmBalanceamount()){
			mainBillSalesSum.setTmBalanceamount(mainBillSalesSum.getTmBalanceamount().add(addBillSalesSum.getTmBalanceamount()));
		}
		
		if(null != mainBillSalesSum.getSumChangebalanceamount() && null != addBillSalesSum.getSumChangebalanceamount()){
			mainBillSalesSum.setSumChangebalanceamount(mainBillSalesSum.getSumChangebalanceamount().add(addBillSalesSum.getSumChangebalanceamount()));
		}
		if(null != mainBillSalesSum.getSumSalesdeductions() && null != addBillSalesSum.getSumSalesdeductions()){
			mainBillSalesSum.setSumSalesdeductions(mainBillSalesSum.getSumSalesdeductions().add(addBillSalesSum.getSumSalesdeductions()));
		}
		if(null != mainBillSalesSum.getSystemDeductAmount() && null != addBillSalesSum.getSystemDeductAmount()){
			mainBillSalesSum.setSystemDeductAmount(mainBillSalesSum.getSystemDeductAmount().add(addBillSalesSum.getSystemDeductAmount()));
		}
		if(null != mainBillSalesSum.getBalanceDeductAmount() && null != addBillSalesSum.getBalanceDeductAmount()){
			mainBillSalesSum.setBalanceDeductAmount(mainBillSalesSum.getBalanceDeductAmount().add(addBillSalesSum.getBalanceDeductAmount()));
		}
		if(null != mainBillSalesSum.getBalanceDiffAmount() && null != addBillSalesSum.getBalanceDiffAmount()){
			mainBillSalesSum.setBalanceDiffAmount(mainBillSalesSum.getBalanceDiffAmount().add(addBillSalesSum.getBalanceDiffAmount()));
		}
		
		if(null != mainBillSalesSum.getNonTaxSalesamount() && null != addBillSalesSum.getNonTaxSalesamount()){
			mainBillSalesSum.setNonTaxSalesamount(mainBillSalesSum.getNonTaxSalesamount().add(addBillSalesSum.getNonTaxSalesamount()));
		}
		if(null != mainBillSalesSum.getTaxCost() && null != addBillSalesSum.getTaxCost()){
			mainBillSalesSum.setTaxCost(mainBillSalesSum.getTaxCost().add(addBillSalesSum.getTaxCost()));
		}
		if(null != mainBillSalesSum.getNoTaxCosts() && null != addBillSalesSum.getNoTaxCosts()){
			mainBillSalesSum.setNoTaxCosts(mainBillSalesSum.getNoTaxCosts().add(addBillSalesSum.getNoTaxCosts()));
		}
		if(null != mainBillSalesSum.getRegionCost() && null != addBillSalesSum.getRegionCost()){
			mainBillSalesSum.setRegionCost(mainBillSalesSum.getRegionCost().add(addBillSalesSum.getRegionCost()));
		}
		if(null != mainBillSalesSum.getHeadquarterCost() && null != addBillSalesSum.getHeadquarterCost()){
			mainBillSalesSum.setHeadquarterCost(mainBillSalesSum.getHeadquarterCost().add(addBillSalesSum.getHeadquarterCost()));
		}
		if(null != mainBillSalesSum.getPurchasePrice() && null != addBillSalesSum.getPurchasePrice()){
			mainBillSalesSum.setPurchasePrice(mainBillSalesSum.getPurchasePrice().add(addBillSalesSum.getPurchasePrice()));
		}
	}

	/**
  	 * 检查有内购销售但未设置内购结算期的公司
  	 * 
  	 * @param params
  	 * @return
  	 * @author wangyj
  	 */
	@Override
  	public List<BillSalesSum> checkInnerBuyBalanceDate( Map<String, Object> params) throws ManagerException{
		try {
			return billSalesSumService.checkInnerBuyBalanceDate(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
  	}
	
	
}