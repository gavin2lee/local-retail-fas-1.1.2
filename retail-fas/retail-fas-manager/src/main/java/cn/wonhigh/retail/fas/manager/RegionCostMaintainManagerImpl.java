package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.RuleMatchFailReasonEnums;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.common.model.RuleMatchFail;
import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.common.model.SettleNewStyleDtl;
import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.FinancialAccountService;
import cn.wonhigh.retail.fas.service.HeadquarterCostMaintainService;
import cn.wonhigh.retail.fas.service.PurchasePriceService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;
import cn.wonhigh.retail.fas.service.RegionPriceRuleService;
import cn.wonhigh.retail.fas.service.RuleMatchFailService;
import cn.wonhigh.retail.fas.service.SettleCategoryDtlService;
import cn.wonhigh.retail.fas.service.SettleNewStyleDtlService;
import cn.wonhigh.retail.fas.service.SupplierGroupRelService;
import cn.wonhigh.retail.fas.service.ZoneInfoService;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@Service("regionCostMaintainManager")
class RegionCostMaintainManagerImpl extends BaseCrudManagerImpl implements RegionCostMaintainManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(RegionCostMaintainManagerImpl.class);
	
    @Resource
    private RegionCostMaintainService regionCostMaintainService;

    private static final int RULE_MATCHED = 0;//规则匹配成功
    
    @Resource
    private SupplierGroupRelService supplierGroupRelService;
    
    @Resource
    private SettleCategoryDtlService settleCategoryDtlService;
    
    @Resource
    private SettleNewStyleDtlService settleNewStyleDtlService;

    @Resource
    private BrandService brandService;
    
    @Resource
    private RegionPriceRuleService regionPriceRuleService;
    
    @Resource
    private HeadquarterCostMaintainService headquarterCostMaintainService;
    
    @Resource
    private RuleMatchFailService ruleMatchFailService;
    
    @Resource
    private FinancialAccountService financialAccountService;
    
    @Resource
    private CompanyService companyService;
    
    @Resource
    private PurchasePriceService purchasePriceService;
    
    @Resource
    private CommonUtilService commonUtilService;
    
    @Resource
    private ZoneInfoService zoneInfoService;
    
    @Override
    public BaseCrudService init() {
        return regionCostMaintainService;
    }

    /**
   	 * 记录匹配加价规则失败记录
   	 * @param params
   	 * @return
     * @throws ManagerException 
   	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    private void doMatchRuleErrorLog(RegionCostMaintain regionCostMaintain, 
		RuleMatchFailReasonEnums unmatchReason) throws ManagerException {
    	try {
    		//检查是否已经存在错误的记录，有则更新，没有才插入
    		RuleMatchFail ruleMatchExist = this.ruleMatchFailService.findRegionItemUnmatched(regionCostMaintain.getItemNo(), regionCostMaintain.getZoneNo());
    		
    		RuleMatchFail failReason = null;
    		if (null == ruleMatchExist) {
    			failReason = new RuleMatchFail();
        		failReason.setItemNo(regionCostMaintain.getItemNo());
        		failReason.setItemCode(regionCostMaintain.getItemCode());
        		failReason.setZoneNo(regionCostMaintain.getZoneNo());
        		failReason.setBrandNo(regionCostMaintain.getBrandNo());
        		failReason.setBrandName(regionCostMaintain.getBrandName());
        		failReason.setMatchType(1);//地区匹配失败
        		failReason.setFailReason(unmatchReason.getReasonNo());
        		failReason.setStatus(0);
    			if (RULE_MATCHED == unmatchReason.getReasonNo()) {
    				failReason.setFailReason(RuleMatchFailReasonEnums.MATCH_SUCCESS.getReasonNo());
    				failReason.setStatus(1);
				}
        		failReason.setCreateTime(new Date());
        		this.ruleMatchFailService.add(failReason);
			}else {
				ruleMatchExist.setCreateTime(new Date());
				ruleMatchExist.setFailReason(unmatchReason.getReasonNo());
				ruleMatchExist.setStatus(0);
    			if (RULE_MATCHED == unmatchReason.getReasonNo()) {
    				ruleMatchExist.setFailReason(RuleMatchFailReasonEnums.MATCH_SUCCESS.getReasonNo());
    				ruleMatchExist.setStatus(1);
				}
				this.ruleMatchFailService.modifyById(ruleMatchExist);
			}
    		
		} catch (ServiceException e) {
			LOGGER.info(".....更新规则匹配异常失败.....");
			throw new ManagerException(e.getMessage(), e);
		}
    }
    
    @Override
	public void genetareRegionCost(RegionCostMaintain regionCost) throws ManagerException {
		try {
			regionCost.setAddRuleNo("");
			regionCost.setFailMessage("");
			regionCost.setMatchedRegionPriceRule(null);
			//加价规则参数
			Map<String, Object> priceRuleParams = new HashMap<String, Object>();
			// 设置区域编码
			priceRuleParams.put("zoneNoAlial", regionCost.getZoneNo());
			
			//商品供应商组匹配
			Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
			supplierGroupParams.put("supplierNo", regionCost.getSupplierNo());
			List<SupplierGroupRel> groupRelList = this.supplierGroupRelService.findByBiz(null, supplierGroupParams);
			if (CollectionUtils.isEmpty(groupRelList)) {
				regionCost.setFailMessage("供应商组匹配失败！");
				return;
			}
			priceRuleParams.put("supplierGroupNoAlial", groupRelList.get(0).getGroupNo());
			
			//结算大类匹配
			Map<String, Object> categoryParams = new HashMap<String, Object>();
			categoryParams.put("categoryNo", regionCost.getCategoryNo());
			List<SettleCategoryDtl> settleCategoryList = this.settleCategoryDtlService.findByBiz(null, categoryParams);
			if (CollectionUtils.isEmpty(settleCategoryList)) {
				regionCost.setFailMessage("结算大类匹配失败！");
				return;
			}
			priceRuleParams.put("categoryNoAlial", settleCategoryList.get(0).getSettleCategoryNo());
			
			//品牌匹配品牌部
			Map<String, Object> brandUnitParams = new HashMap<String, Object>();
			brandUnitParams.put("brandNo", regionCost.getBrandNo());
			List<Brand> brandList = this.brandService.findByBiz(null, brandUnitParams);
			if (!CollectionUtils.isEmpty(brandList)) {
				priceRuleParams.put("brandUnitNoAlial", brandList.get(0).getSysNo());
			}
			priceRuleParams.put("currentTime", new Date());
			
			//新旧款匹配
			Map<String, Object> newStyleParams = new HashMap<String, Object>();
			newStyleParams.put("yearCode", regionCost.getYear());
			newStyleParams.put("seasonNo", regionCost.getSeason());
			List<SettleNewStyleDtl> newStyleList = this.settleNewStyleDtlService.findByBiz(null, newStyleParams);
			//根据新旧款查询的加价规则
			List<RegionPriceRule> regionNewStylePriceRuleList = null;
			//根据年份季节查询的加价规则
			List<RegionPriceRule> regionYearSeasonPriceRuleList = null;
			
			if (!CollectionUtils.isEmpty(newStyleList)) {
				priceRuleParams.put("styleType", newStyleList.get(0).getStyleNo());
				priceRuleParams.put("newStyleFlag", 1);
				//根据新旧款匹配加价规则
				regionNewStylePriceRuleList = this.regionPriceRuleService.findByBiz(null, priceRuleParams);
			}	
			//根据年份季节匹配
			priceRuleParams.put("yearCode", regionCost.getYear());
			priceRuleParams.put("season", regionCost.getSeason());
			priceRuleParams.put("styleType", "");
			priceRuleParams.put("newStyleFlag", 0);
			regionYearSeasonPriceRuleList = this.regionPriceRuleService.findByBiz(null, priceRuleParams);

			List<RegionPriceRule> regionPriceRuleList = new ArrayList<RegionPriceRule>();
			if (!CollectionUtils.isEmpty(regionNewStylePriceRuleList)) {
				regionPriceRuleList.addAll(regionNewStylePriceRuleList);
			}
			if (!CollectionUtils.isEmpty(regionYearSeasonPriceRuleList)) {
				regionPriceRuleList.addAll(regionYearSeasonPriceRuleList);
			}
			
			if (CollectionUtils.isEmpty(regionPriceRuleList)) {
				regionCost.setFailMessage("新旧款、年份季节、品牌部、地区或生效日期匹配失败！");
				return;
			}
			
			// add by wang.m 二级大类匹配
			List<RegionPriceRule> newRuleList = new ArrayList<RegionPriceRule>();
			String twoLevelCategoryNo = regionCost.getTwoLevelCategoryNo();
			for (RegionPriceRule rule : regionPriceRuleList) {
				if(StringUtils.isBlank(rule.getTwoLevelCategoryNo())){
					newRuleList.add(rule);
				}else{
					if(rule.getTwoLevelCategoryNo().indexOf(twoLevelCategoryNo) != -1){
						newRuleList.add(rule);
					}
				}
			}				
			regionPriceRuleList = newRuleList;
			if(CollectionUtils.isEmpty(regionPriceRuleList)){
				regionCost.setFailMessage("新旧款、年份季节、品牌部、地区或生效日期匹配失败！");
				return;
			}
			//匹配的一个规则对象
			RegionPriceRule regoinRule = regionPriceRuleList.get(0);
			regionCost.setAddRuleNo(regoinRule.getAddRuleNo());
			regionCost.setMatchedRegionPriceRule(regionPriceRuleList);
			
			regionCost.setAddBasis(regoinRule.getAddBasis());
			regionCost.setAddBasisName(regoinRule.getAddBasis().equals("3") ? "总部成本" : "厂进价");
			regionCost.setAddPrice(regoinRule.getAddPrice());
			regionCost.setAddDiscount(regoinRule.getAddDiscount());
			regionCost.setDiscountRate(regoinRule.getDiscountRate());
			
			if (regoinRule.getAddBasis().equals("2")) {
				//加价依据是牌价,直接获取商品的建议牌价
				BigDecimal tagPrice = regionCost.getSuggestTagPrice();
				regionCost.setRegionCost(tagPrice.multiply(regoinRule.getDiscountRate()));
				regionCost.setEffectiveTime(new Date());
				regionCost.setTagPrice(tagPrice);
			}else if (regoinRule.getAddBasis().equals("3")) {
				//加价依据总部价
				Map<String, Object> headPriceParam = new HashMap<String, Object>();
				headPriceParam.put("itemNo", regionCost.getItemNo());
				headPriceParam.put("effectiveTime", new Date());
				
				HeadquarterCostMaintain headCostMaintains = this.headquarterCostMaintainService.getLastEffectiveModel(headPriceParam);
				
				if (null != headCostMaintains) {
					//地区价就等于总部价+加价或者总部价乘以加折
					BigDecimal headPrice = headCostMaintains.getHeadquarterCost();
					if (regoinRule.getAddPrice() != null) {
						regionCost.setRegionCost(headPrice.add(regoinRule.getAddPrice()));
					}else if (regoinRule.getAddDiscount() != null) {
						regionCost.setRegionCost(headPrice.multiply(regoinRule.getAddDiscount()));
					}
					regionCost.setHeadquarterCost(headPrice);
					regionCost.setEffectiveTime(headCostMaintains.getEffectiveTime());
				}else {
					//未匹配到总部价
					regionCost.setFailMessage("获取总部成本价失败！");
					return;
				}
			}else if (regoinRule.getAddBasis().equals("1")) {
				// 加价依据是厂进价
				PurchasePrice purchasePrice = purchasePriceService.findBalancePurchasePrice(regionCost.getItemNo(), regionCost.getSupplierNo(), new Date());
				if (null != purchasePrice) {
					//地区价就等于厂进价+加价或者厂进价乘以加折
					BigDecimal factoryPrice = purchasePrice.getFactoryPrice();
					if (regoinRule.getAddPrice() != null) {
						regionCost.setRegionCost(factoryPrice.add(regoinRule.getAddPrice()));
					}else if (regoinRule.getAddDiscount() != null) {
						regionCost.setRegionCost(factoryPrice.multiply(regoinRule.getAddDiscount()));
					}
					regionCost.setFactoryPrice(factoryPrice);
					regionCost.setEffectiveTime(purchasePrice.getEffectiveDate());
				}else {
					//未匹配到厂进价
					regionCost.setFailMessage("获取厂进价失败！");
					return;
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException("匹配加价规则失败");
		}
	}
    
    @Override
	public Map<String, Object> getPriceRuleData(
			RegionCostMaintain regionCostMaintain) throws ManagerException {
    	try {
	    	Map<String, Object> returnMap = new HashMap<String, Object>();
	    	returnMap.put("failMessage", "");
	    	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("addRuleNo", regionCostMaintain.getAddRuleNo());
			
			List<RegionPriceRule> regionPriceRuleList = regionPriceRuleService.findByBiz(null, params);
			
			if (CollectionUtils.isEmpty(regionPriceRuleList)) {
				return null;
			}
			RegionPriceRule regionPriceRule = regionPriceRuleList.get(0);
			
			returnMap.put("addBasis",regionPriceRule.getAddBasis());
			returnMap.put("addBasisName",regionPriceRule.getAddBasis().equals("3") ? "总部成本" : "厂进价");
			returnMap.put("addPrice",regionPriceRule.getAddPrice());
			returnMap.put("addDiscount",regionPriceRule.getAddDiscount());
			returnMap.put("discountRate",regionPriceRule.getDiscountRate());
			
			if (regionPriceRule.getAddBasis().equals("2") && null != regionPriceRule.getDiscountRate()) {
				//加价依据是牌价,直接获取商品的建议牌价
				BigDecimal tagPrice = regionCostMaintain.getSuggestTagPrice() == null ? new BigDecimal(0) : regionCostMaintain.getSuggestTagPrice();
				returnMap.put("regionCost", tagPrice.multiply(regionPriceRule.getDiscountRate()));
				returnMap.put("effectiveTime", new Date());
				returnMap.put("tagPrice", tagPrice);
			}else if (regionPriceRule.getAddBasis().equals("3")) {
				//加价依据总部价
				Map<String, Object> headPriceParam = new HashMap<String, Object>();
				headPriceParam.put("itemNo", regionCostMaintain.getItemNo());
				headPriceParam.put("effectiveTime", new Date());
				
				HeadquarterCostMaintain headCostMaintains = this.headquarterCostMaintainService.getLastEffectiveModel(headPriceParam);
				
				if (null != headCostMaintains) {
					//地区价就等于总部价+加价或者总部价乘以加折
					BigDecimal headPrice = headCostMaintains.getHeadquarterCost() == null ? new BigDecimal(0) : headCostMaintains.getHeadquarterCost();
					if (regionPriceRule.getAddPrice() != null) {
						returnMap.put("regionCost", headPrice.add(regionPriceRule.getAddPrice()));
					}else if (regionPriceRule.getAddDiscount() != null) {
						returnMap.put("regionCost", headPrice.multiply(regionPriceRule.getAddDiscount()));
					}
					returnMap.put("headquarterCost", headPrice);
					returnMap.put("effectiveTime", headCostMaintains.getEffectiveTime());
				}else {
					returnMap.put("failMessage", "获取总部成本价失败！");
				}
			}else if (regionPriceRule.getAddBasis().equals("1")) {
				// 加价依据是厂进价
				PurchasePrice purchasePrice = purchasePriceService.findBalancePurchasePrice(regionCostMaintain.getItemNo(), regionCostMaintain.getSupplierNo(), new Date());
				if (null != purchasePrice) {
					//地区价就等于厂进价+加价或者总部价乘以加折
					BigDecimal factoryPrice = purchasePrice.getFactoryPrice();
					if (regionPriceRule.getAddPrice() != null) {
						returnMap.put("regionCost",factoryPrice.add(regionPriceRule.getAddPrice()));
					}else if (regionPriceRule.getAddDiscount() != null) {
						returnMap.put("regionCost",factoryPrice.multiply(regionPriceRule.getAddDiscount()));
					}
					returnMap.put("factoryPrice", factoryPrice);
					returnMap.put("effectiveTime",purchasePrice.getEffectiveDate());
				}else {
					//未匹配到厂进价
					returnMap.put("failMessage", "获取厂进价失败！");
				}
			}
		
			return returnMap;
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public RegionCostMaintain batchGenetareRegionCost(RegionCostMaintain regionCost, 
			Map<String, ZoneInfo> zoneMap, Map<String, Brand> brandMap) throws ManagerException{
		try {
			//加价规则参数
			Map<String, Object> priceRuleParams = new HashMap<String, Object>();
			// 设置区域编码
			priceRuleParams.put("zoneNoAlial", regionCost.getZoneNo());
			
			regionCost.setZoneName(zoneMap.get(regionCost.getZoneNo()).getName());
			
			//商品供应商组匹配
			Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
			supplierGroupParams.put("supplierNo", regionCost.getSupplierNo());
			List<SupplierGroupRel> groupRelList = this.supplierGroupRelService.findByBiz(null, supplierGroupParams);
			if (CollectionUtils.isEmpty(groupRelList)) {
				//记录匹配加价规则失败日志
				doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.SUPPLIER_GROUP_UNMATCH);
				regionCost.setMatchResult(false);
				return regionCost;
			}
			priceRuleParams.put("supplierGroupNoAlial", groupRelList.get(0).getGroupNo());
			
			//结算大类匹配
			Map<String, Object> categoryParams = new HashMap<String, Object>();
			categoryParams.put("categoryNo", regionCost.getCategoryNo());
			List<SettleCategoryDtl> settleCategoryList = this.settleCategoryDtlService.findByBiz(null, categoryParams);
			if (CollectionUtils.isEmpty(settleCategoryList)) {
				//记录匹配加价规则失败日志
				doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.SETTLE_CATEGORY_UNMATCH);
				regionCost.setMatchResult(false);
				return regionCost;
			}
			priceRuleParams.put("categoryNoAlial", settleCategoryList.get(0).getSettleCategoryNo());
			
			//品牌匹配品牌部
//			Map<String, Object> brandUnitParams = new HashMap<String, Object>();
//			brandUnitParams.put("brandNo", regionCost.getBrandNo());
//			List<Brand> brandList = this.brandService.findByBiz(null, brandUnitParams);
//			if (!CollectionUtils.isEmpty(brandList)) {
//			}
			priceRuleParams.put("brandUnitNoAlial", brandMap.get(regionCost.getBrandNo()).getSysNo());
			priceRuleParams.put("currentTime", new Date());
			
			//新旧款匹配
			Map<String, Object> newStyleParams = new HashMap<String, Object>();
			newStyleParams.put("yearCode", regionCost.getYear());
			newStyleParams.put("seasonNo", regionCost.getSeason());
			List<SettleNewStyleDtl> newStyleList = this.settleNewStyleDtlService.findByBiz(null, newStyleParams);
			//根据新旧款查询的加价规则
			List<RegionPriceRule> regionNewStylePriceRuleList = null;
			//根据年份季节查询的加价规则
			List<RegionPriceRule> regionYearSeasonPriceRuleList = null;
			
			if (!CollectionUtils.isEmpty(newStyleList)) {
				priceRuleParams.put("styleType", newStyleList.get(0).getStyleNo());
				priceRuleParams.put("newStyleFlag", 1);
				regionNewStylePriceRuleList = this.regionPriceRuleService.findByBiz(null, priceRuleParams);
			}	
			//根据年份季节匹配
			priceRuleParams.put("yearCode", regionCost.getYear());
			priceRuleParams.put("season", regionCost.getSeason());
			priceRuleParams.put("styleType", "");
			priceRuleParams.put("newStyleFlag", 0);
			regionYearSeasonPriceRuleList = this.regionPriceRuleService.findByBiz(null, priceRuleParams);

			List<RegionPriceRule> regionPriceRuleList = new ArrayList<RegionPriceRule>();
			if (!CollectionUtils.isEmpty(regionNewStylePriceRuleList)) {
				regionPriceRuleList.addAll(regionNewStylePriceRuleList);
			}
			if (!CollectionUtils.isEmpty(regionYearSeasonPriceRuleList)) {
				regionPriceRuleList.addAll(regionYearSeasonPriceRuleList);
			}
			
			//匹配到多个加价规则
			if (regionPriceRuleList.size() > 1) {
				// add by wang.m 二级大类匹配
				List<RegionPriceRule> newRuleList = new ArrayList<RegionPriceRule>();
				boolean errorFlag = false;
				String twoLevelCategoryNo = regionCost.getTwoLevelCategoryNo();
				for (RegionPriceRule rule : regionPriceRuleList) {
					if(StringUtils.isBlank(rule.getTwoLevelCategoryNo())){
						errorFlag = true;
						break ;
					}
					if(rule.getTwoLevelCategoryNo().indexOf(twoLevelCategoryNo) != -1){
						newRuleList.add(rule);
					}
				}				
				if(newRuleList.size() == 1 && !errorFlag){
					regionPriceRuleList = newRuleList;
				}else{
					doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.MATCH_ONEMORE_ERROR);
					regionCost.setMatchResult(false);
					return regionCost;
				}
			}
			//未匹配到加价规则，需要记录日志
			if (CollectionUtils.isEmpty(regionPriceRuleList)){
				//未匹配到加价规则，需要记录日志
				doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
				regionCost.setMatchResult(false);
				return regionCost;
			}
			//匹配的一个规则对象
			RegionPriceRule regoinRule = regionPriceRuleList.get(0);
			// add by wang.m 校验二级大类匹配
			if(StringUtils.isNotBlank(regoinRule.getTwoLevelCategoryNo()) 
					&& regoinRule.getTwoLevelCategoryNo().indexOf(regionCost.getTwoLevelCategoryNo()) == -1){
				//未匹配到加价规则，需要记录日志
				doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
				regionCost.setMatchResult(false);
				return regionCost;
			}
			regionCost.setAddRuleNo(regoinRule.getAddRuleNo());
			regionCost.setAddBasis(regoinRule.getAddBasis());
			regionCost.setAddPrice(regoinRule.getAddPrice());
			regionCost.setAddDiscount(regoinRule.getAddDiscount());
			regionCost.setDiscountRate(regoinRule.getDiscountRate());
			
			if (regoinRule.getAddBasis().equals("2")) {
				//加价依据是牌价,直接获取商品的建议牌价
				BigDecimal tagPrice = regionCost.getSuggestTagPrice();
				regionCost.setRegionCost(tagPrice.multiply(regoinRule.getDiscountRate()));
				regionCost.setEffectiveTime(new Date());
				regionCost.setMatchResult(true);
			}else if (regoinRule.getAddBasis().equals("3")) {
				
				if (null != regionCost.getHeadquarterCost()) {
					//地区价就等于总部价+加价或者总部价乘以加折
					BigDecimal headPrice = regionCost.getHeadquarterCost();
					if (regoinRule.getAddPrice() != null) {
						regionCost.setRegionCost(headPrice.add(regoinRule.getAddPrice()));
					}else if (regoinRule.getAddDiscount() != null) {
						regionCost.setRegionCost(headPrice.multiply(regoinRule.getAddDiscount()));
					}
					
					regionCost.setMatchResult(true);
				}else {
					//未匹配到总部价
					doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.GET_HQ_PRICE_ERROR);
					regionCost.setMatchResult(false);
				}
			}
			
			//记录加价规则匹配成功
			doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.MATCH_SUCCESS);
			
			// 匹配上了需要插入记录,没有匹配上的已经记录了日志
			if (regionCost.getMatchResult()) {
				this.regionCostMaintainService.add(regionCost);
			}
			return regionCost;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<RegionPriceRule> findRegionAddRulesByBiz(
			RegionCostMaintain regionCost) throws ManagerException {
		try {
			//加价规则参数
			Map<String, Object> priceRuleParams = new HashMap<String, Object>();
			// 设置区域编码
			priceRuleParams.put("zoneNoAlial", regionCost.getZoneNo());
			
			//商品供应商组匹配
			Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
			supplierGroupParams.put("supplierNo", regionCost.getSupplierNo());
			List<SupplierGroupRel> groupRelList = this.supplierGroupRelService.findByBiz(null, supplierGroupParams);
			if (CollectionUtils.isEmpty(groupRelList)) {
				return null;
			}
			priceRuleParams.put("supplierGroupNoAlial", groupRelList.get(0).getGroupNo());
			
			//结算大类匹配
			Map<String, Object> categoryParams = new HashMap<String, Object>();
			categoryParams.put("categoryNo", regionCost.getCategoryNo());
			List<SettleCategoryDtl> settleCategoryList = this.settleCategoryDtlService.findByBiz(null, categoryParams);
			if (CollectionUtils.isEmpty(settleCategoryList)) {
				return null;
			}
			priceRuleParams.put("categoryNoAlial", settleCategoryList.get(0).getSettleCategoryNo());
			
			//品牌匹配品牌部
			Map<String, Object> brandUnitParams = new HashMap<String, Object>();
			brandUnitParams.put("brandNo", regionCost.getBrandNo());
			List<Brand> brandList = this.brandService.findByBiz(null, brandUnitParams);
			if (!CollectionUtils.isEmpty(brandList)) {
				priceRuleParams.put("brandUnitNoAlial", brandList.get(0).getSysNo());
			}
			priceRuleParams.put("currentTime", new Date());
			
			//新旧款匹配
			Map<String, Object> newStyleParams = new HashMap<String, Object>();
			newStyleParams.put("yearCode", regionCost.getYear());
			newStyleParams.put("seasonNo", regionCost.getSeason());
			List<SettleNewStyleDtl> newStyleList = this.settleNewStyleDtlService.findByBiz(null, newStyleParams);
			//根据新旧款查询的加价规则
			List<RegionPriceRule> regionNewStylePriceRuleList = null;
			//根据年份季节查询的加价规则
			List<RegionPriceRule> regionYearSeasonPriceRuleList = null;
			
			if (!CollectionUtils.isEmpty(newStyleList)) {
				priceRuleParams.put("styleType", newStyleList.get(0).getStyleNo());
				priceRuleParams.put("newStyleFlag", 1);
				//根据新旧款匹配加价规则
				regionNewStylePriceRuleList = this.regionPriceRuleService.findByBiz(null, priceRuleParams);
			}	
			//根据年份季节匹配
			priceRuleParams.put("yearCode", regionCost.getYear());
			priceRuleParams.put("season", regionCost.getSeason());
			priceRuleParams.put("styleType", "");
			priceRuleParams.put("newStyleFlag", 0);
			regionYearSeasonPriceRuleList = this.regionPriceRuleService.findByBiz(null, priceRuleParams);

			List<RegionPriceRule> regionPriceRuleList = new ArrayList<RegionPriceRule>();
			if (!CollectionUtils.isEmpty(regionNewStylePriceRuleList)) {
				regionPriceRuleList.addAll(regionNewStylePriceRuleList);
			}
			if (!CollectionUtils.isEmpty(regionYearSeasonPriceRuleList)) {
				regionPriceRuleList.addAll(regionYearSeasonPriceRuleList);
			}
			
			return regionPriceRuleList;
			
		} catch (ServiceException e) {
			throw new ManagerException("查询加价规则失败");
		}
	}
	
	/**
	 * 根据公司编码，商品，单据日期查询地区价
	 * @param companyNo,itemNo,effectiveTime
	 */
	@Override
	public BigDecimal findRegionCostForBalanceBill(RegionCostMaintain regionCost)
			throws ManagerException {
		try {
			if (StringUtils.isEmpty(regionCost.getItemNo()) || null == regionCost.getEffectiveTime()) {
				return null;
			}
			String zoneNo = "";
			if (StringUtils.isNotEmpty(regionCost.getCompanyNo())) {
				zoneNo = findPriceZoneByCompanyNo(regionCost.getCompanyNo());
			}else {
				if (StringUtils.isEmpty(regionCost.getZoneNo())) {
					return null;
				}
				zoneNo = regionCost.getZoneNo();
			}
			return regionCostMaintainService.findRegionCost(regionCost.getItemNo(),zoneNo,regionCost.getEffectiveTime());
			
		} catch (ServiceException e) {
			return null;
		}
	}
	
	/**
	 * 根据公司编码查询所属的价格大区
	 * @param companyNo
	 * @return zoneNo
	 */
	public String findPriceZoneByCompanyNo(String companyNo) throws ManagerException {
		try {
			String zoneNo = "";
			Map<String, Object> companyMap = null;
			Map<String, Object> financeAccountMap = new HashMap<String, Object>();
			financeAccountMap.put("companyNo", companyNo);
			financeAccountMap.put("status", 1);
			financeAccountMap.put("groupLeadRole", 0);
			
			List<FinancialAccount> financialAccounts = financialAccountService.findByBiz(null, financeAccountMap);
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
		} catch (ServiceException e) {
			throw new ManagerException("查询价格大区失败");
		}
	}

	@Override
	public List<RegionCostMaintain> findZoneRegionCost(
			Map<String, Object> params) throws ManagerException {
		try {
			return regionCostMaintainService.findZoneRegionCost(params);
		} catch (ServiceException e) {
			throw new ManagerException("查询各地区的地区价失败");
		}
	}
	
    public RegionCostMaintain addRegionCost(RegionCostMaintain regionCost) throws ManagerException {
    	try {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("itemNo", regionCost.getItemNo());
    		params.put("zoneNo", regionCost.getZoneNo());
    		params.put("addBasis", regionCost.getAddBasis());
    		// 根据货号获取加价规则
    		List<RegionPriceRule> ruleList = regionPriceRuleService.findRuleByParams(params);
    		if(CollectionUtils.isEmpty(ruleList)){// 没有匹配到规则
    			doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
    			regionCost.setMatchResult(false);
    			return regionCost;
    		}
    		if(ruleList.size() >1){// 匹配到多个规则
    			if(ruleList.get(0).getEffectiveTime().getTime() == ruleList.get(1).getEffectiveTime().getTime()){
    				doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.MATCH_ONEMORE_ERROR);
        			regionCost.setMatchResult(false);
        			return regionCost;
    			}
    		}
    		if(!setRegionCostInfo(regionCost,ruleList.get(0))){
    			doMatchRuleErrorLog(regionCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
    			regionCost.setMatchResult(false);
    			return regionCost;
    		}
    		regionCost.setMatchResult(true);
    		regionCostMaintainService.add(regionCost);
			return regionCost;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
    }
    
    private boolean setRegionCostInfo(RegionCostMaintain regionCost,
			RegionPriceRule regoinRule){
    	regionCost.setAddRuleNo(regoinRule.getAddRuleNo());
		regionCost.setAddBasis(regoinRule.getAddBasis());
		regionCost.setAddPrice(regoinRule.getAddPrice());
		regionCost.setAddDiscount(regoinRule.getAddDiscount());
		regionCost.setDiscountRate(regoinRule.getDiscountRate());
		if (regoinRule.getAddBasis().equals("2")) {
			//加价依据是牌价,直接获取商品的建议牌价
			BigDecimal tagPrice = regionCost.getSuggestTagPrice();
			regionCost.setRegionCost(tagPrice.multiply(regoinRule.getDiscountRate()));
			regionCost.setEffectiveTime(new Date());
			regionCost.setTagPrice(tagPrice);
			return true;
		}else if (regoinRule.getAddBasis().equals("3")) {
			if (null != regionCost.getHeadquarterCost()) {
				//地区价就等于总部价+加价或者总部价乘以加折
				BigDecimal headPrice = regionCost.getHeadquarterCost();
				if (regoinRule.getAddPrice() != null) {
					regionCost.setRegionCost(headPrice.add(regoinRule.getAddPrice()));
				}else if (regoinRule.getAddDiscount() != null) {
					regionCost.setRegionCost(headPrice.multiply(regoinRule.getAddDiscount()));
				}
				return true;
			}else {
				//未匹配到总部价
				return false;
			}
		}else if (regoinRule.getAddBasis().equals("1")) {
			if (null != regionCost.getFactoryPrice()) {
				//地区价就等于厂进价+加价或者厂进价乘以加折
				BigDecimal factoryPrice = regionCost.getFactoryPrice();
				if (regoinRule.getAddPrice() != null) {
					regionCost.setRegionCost(factoryPrice.add(regoinRule.getAddPrice()));
				}else if (regoinRule.getAddDiscount() != null) {
					regionCost.setRegionCost(factoryPrice.multiply(regoinRule.getAddDiscount()));
				}
				return true;
			}else {
				//未匹配到厂进价
				return false;
			}
		}
		return false;
	}

	@Override
	public void findAreaPriceExport(SimplePage page,
			Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
		try {
			regionCostMaintainService.findAreaPriceExport(page,params,handler);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<RegionCostMaintain> findRegionCostReport(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) 
			 throws ManagerException{
		try {
			return regionCostMaintainService.findRegionCostReport(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int batchGenetareCostByRule(Map<String, Object> params)
			throws ManagerException {
		try {
			return regionCostMaintainService.batchGenetareCostByRule(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int batchGenetareCostNew(Map<String, Object> params)
			throws Exception {
			String[] zoneNos = FasUtil.parseInQueryCondition(String.valueOf(params.get("multiPriceZoneNo"))).split(",");
			SystemUser user = CurrentUser.getCurrentUser();
			Map<String, Object> brandParams = new HashMap<String, Object>();
			brandParams.put("multiBrandUnitNo", FasUtil.formatInQueryCondition(params.get("brandUnitNos").toString()));
			List<Brand> brands = brandService.findByBiz(null, brandParams);
			ExecutorService exe = Executors.newFixedThreadPool(30);
			for (String zoneNo : zoneNos) {
				ZoneInfo zone = zoneInfoService.getPriceZoneByNo(zoneNo);
				for (Brand brand : brands) {
					exe.execute(new GenereateCostThread(zone, brand,user, params));
				}
			}
			exe.shutdown();
	        exe.awaitTermination(2, TimeUnit.HOURS);
	        return 1;
	}
	
	class GenereateCostThread implements Runnable {
		
		private ZoneInfo zone;
		
		private Brand brand;
		
		private SystemUser user; 
		
		private Map<String, Object> params;
		
		public GenereateCostThread(ZoneInfo zone, Brand brand, SystemUser user, Map<String, Object> params) {
			this.params = params;
			this.zone = zone;
			this.brand = brand;
			this.user = user;
		}
		@Override
		public void run() {
			try {
				String categoryNos = String.valueOf(params.get("multiCategoryNo"));
				String effectiveTime = String.valueOf(params.get("effectiveTime1"));
				String addBasis = String.valueOf(params.get("HQQuotationFlag"));
				String username = user.getUsername();
				String organTypeNo = user.getOrganTypeNo();
				Map<String, Object> queryParams = new HashMap<>();
				queryParams.put("zoneNo", zone.getZoneNo());
				queryParams.put("brandNo", brand.getBrandNo());
				queryParams.put("multiCategoryNo", categoryNos);
				queryParams.put("effectiveTime", effectiveTime);
				queryParams.put("addBasis", addBasis);
				int pageNo = 1;
				int pageSize = 20000;
				SimplePage page = new SimplePage(pageNo, pageSize, 0);
				while(true){
					List<RegionCostMaintain> lstRegionCost = commonUtilService.getRegionNeedRuleMatchItems(page,queryParams);
					for (RegionCostMaintain regionCost: lstRegionCost) {
						regionCost.setZoneNo(zone.getZoneNo());
						regionCost.setZoneName(zone.getName());
						regionCost.setEffectiveTime(DateUtil.parseToDate(effectiveTime, "yyyy-MM-dd"));
						regionCost.setShardingFlag(organTypeNo.concat("_").concat(zone.getZoneNo()));
						regionCost.setCreateTime(new Date());
						regionCost.setCreateUser(username);
						regionCostMaintainService.add(regionCost);
					}
					if(lstRegionCost.size() < pageSize){
						break;
					} 
				}
			} catch (Exception e) {
				LOGGER.error("地区价生成异常!", e);
			}
		}
	}

}