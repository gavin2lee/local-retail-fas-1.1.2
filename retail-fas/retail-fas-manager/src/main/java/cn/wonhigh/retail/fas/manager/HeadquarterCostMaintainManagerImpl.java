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
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;
import cn.wonhigh.retail.fas.common.model.PriceQuotationList;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.RuleMatchFail;
import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.common.model.SettleNewStyleDtl;
import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.HeadquarterCostMaintainService;
import cn.wonhigh.retail.fas.service.HeadquarterPriceRuleService;
import cn.wonhigh.retail.fas.service.PriceQuotationListService;
import cn.wonhigh.retail.fas.service.PurchasePriceService;
import cn.wonhigh.retail.fas.service.RuleMatchFailService;
import cn.wonhigh.retail.fas.service.SettleCategoryDtlService;
import cn.wonhigh.retail.fas.service.SettleNewStyleDtlService;
import cn.wonhigh.retail.fas.service.SupplierGroupRelService;

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
@Service("headquarterCostMaintainManager")
class HeadquarterCostMaintainManagerImpl extends BaseCrudManagerImpl implements HeadquarterCostMaintainManager {
    
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(HeadquarterCostMaintainManagerImpl.class);
	
	private static final int RULE_MATCHED = 0;//规则匹配成功
	
	@Resource
    private HeadquarterCostMaintainService headquarterCostMaintainService;

    @Resource
    private SupplierGroupRelService supplierGroupRelService;
    
    @Resource
    private SettleCategoryDtlService settleCategoryDtlService;
    
    @Resource
    private SettleNewStyleDtlService settleNewStyleDtlService;

    @Resource
    private BrandService brandService;
    
    @Resource
    private HeadquarterPriceRuleService headquarterPriceRuleService;

    @Resource
    private RuleMatchFailService ruleMatchFailService;
    
    @Resource
    private PurchasePriceService purchasePriceService;
   
    @Resource
    private PriceQuotationListService priceQuotationListService;
 
    @Resource
    private CommonUtilService commonUtilService;
    
    @Override
    public BaseCrudService init() {
        return headquarterCostMaintainService;
    }

    /**
   	 * 记录匹配加价规则失败记录
   	 * @param params
   	 * @return
   	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    private void doMatchRuleErrorLog(HeadquarterCostMaintain headquarterCost, 
    		RuleMatchFailReasonEnums unmatchReason) throws ManagerException {
    	try {
    		//检查是否已经存在错误的记录，有则更新，没有才插入
    		RuleMatchFail ruleMatchExist = this.ruleMatchFailService.findHQItemUnmatched(headquarterCost.getItemNo());
    		
    		RuleMatchFail failReason = null;
    		if (null == ruleMatchExist) {
    			failReason = new RuleMatchFail();
    			failReason.setItemNo(headquarterCost.getItemNo());
    			failReason.setItemCode(headquarterCost.getItemCode());
    			failReason.setBrandNo(headquarterCost.getBrandNo());
    			failReason.setBrandName(headquarterCost.getBrandName());
    			failReason.setMatchType(0);//总部匹配失败
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
			throw new ManagerException(e.getMessage(), e);
		}
    }
    
    /**
	 * 根据商品属性信息和总部加价规则设置来生成总部价
	 * @param params
	 * @return
	 */
	@Override
	public void generateHeadquarterCost(
			HeadquarterCostMaintain headquarterCost) throws ManagerException {
		try {
			headquarterCost.setAddRuleNo("");
			headquarterCost.setFailMessage("");
			headquarterCost.setMatchedHeadquarterPriceRule(null);
			//加价规则参数
			Map<String, Object> priceRuleParams = new HashMap<String, Object>();
			//商品供应商组匹配
			Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
			supplierGroupParams.put("supplierNo", headquarterCost.getSupplierNo());
			List<SupplierGroupRel> groupRelList = this.supplierGroupRelService.findByBiz(null, supplierGroupParams);
			if (CollectionUtils.isEmpty(groupRelList)) {
				headquarterCost.setFailMessage("供应商组匹配失败！");
				return;
			}
			priceRuleParams.put("supplierGroupNoAlial", groupRelList.get(0).getGroupNo());
			
			//结算大类匹配
			Map<String, Object> categoryParams = new HashMap<String, Object>();
			categoryParams.put("categoryNo", headquarterCost.getCategoryNo());
			List<SettleCategoryDtl> settleCategoryList = this.settleCategoryDtlService.findByBiz(null, categoryParams);
			if (CollectionUtils.isEmpty(settleCategoryList)) {
				headquarterCost.setFailMessage("结算大类匹配失败！");
				return;
			}
			priceRuleParams.put("categoryNoAlial", settleCategoryList.get(0).getSettleCategoryNo());
			
			//品牌匹配品牌部
			Map<String, Object> brandUnitParams = new HashMap<String, Object>();
			brandUnitParams.put("brandNo", headquarterCost.getBrandNo());
			List<Brand> brandList = this.brandService.findByBiz(null, brandUnitParams);
			if (!CollectionUtils.isEmpty(brandList)) {
				priceRuleParams.put("brandUnitNoAlial", brandList.get(0).getSysNo());
			}
			
			priceRuleParams.put("currentTime", new Date());
			
			//新旧款匹配
			Map<String, Object> newStyleParams = new HashMap<String, Object>();
			newStyleParams.put("yearCode", headquarterCost.getYear());
			newStyleParams.put("seasonNo", headquarterCost.getSeason());
			List<SettleNewStyleDtl> newStyleList = this.settleNewStyleDtlService.findByBiz(null, newStyleParams);
			//根据新旧款来查询加价规则
			List<HeadquarterPriceRule> hqNewStylePriceRuleList = null;
			//根据年份季节来查询加价规则
			List<HeadquarterPriceRule> hqYearSeasonPriceRuleList = null;
			
			if (!CollectionUtils.isEmpty(newStyleList)) {
				priceRuleParams.put("styleType", newStyleList.get(0).getStyleNo());
				priceRuleParams.put("newStyleFlag", 1);
				//根据新旧款匹配加价规则
				hqNewStylePriceRuleList = this.headquarterPriceRuleService.findByBiz(null, priceRuleParams);
			}	
			
			//根据新旧款没有匹配到加价规则，再使用年份季节匹配
			priceRuleParams.put("yearCode", headquarterCost.getYear());
			priceRuleParams.put("season", headquarterCost.getSeason());
			priceRuleParams.put("styleType", "");
			priceRuleParams.put("newStyleFlag", 0);
			//根据年份季节匹配加价规则
			hqYearSeasonPriceRuleList = this.headquarterPriceRuleService.findByBiz(null, priceRuleParams);
			
			if (CollectionUtils.isEmpty(hqNewStylePriceRuleList) && CollectionUtils.isEmpty(hqYearSeasonPriceRuleList)) {
				headquarterCost.setFailMessage("新旧款、年份季节、品牌部或生效日期匹配失败！");
				return;
			}
			
			List<HeadquarterPriceRule> headquarterPriceRuleList = new ArrayList<HeadquarterPriceRule>();
			if (!CollectionUtils.isEmpty(hqNewStylePriceRuleList)) {
				headquarterPriceRuleList.addAll(hqNewStylePriceRuleList);
			}
			if (!CollectionUtils.isEmpty(hqYearSeasonPriceRuleList)) {
				headquarterPriceRuleList.addAll(hqYearSeasonPriceRuleList);
			}
			// add by wang.m 二级大类匹配
			List<HeadquarterPriceRule> newRuleList = new ArrayList<HeadquarterPriceRule>();
			String twoLevelCategoryNo = headquarterCost.getTwoLevelCategoryNo();
			for (HeadquarterPriceRule rule : headquarterPriceRuleList) {
				if(StringUtils.isBlank(rule.getTwoLevelCategoryNo())){
					newRuleList.add(rule);
				}else{
					if(rule.getTwoLevelCategoryNo().indexOf(twoLevelCategoryNo) != -1){
						newRuleList.add(rule);
					}
				}
			}				
			headquarterPriceRuleList = newRuleList;
			if(CollectionUtils.isEmpty(headquarterPriceRuleList)){
				headquarterCost.setFailMessage("新旧款、年份季节、品牌部、地区或生效日期匹配失败！");
				return;
			}
			//匹配到一个规则
			HeadquarterPriceRule headquarterRule = headquarterPriceRuleList.get(0);
			headquarterCost.setAddRuleNo(headquarterRule.getAddRuleNo());
			headquarterCost.setMatchedHeadquarterPriceRule(headquarterPriceRuleList);
			headquarterCost.setAddBasis(headquarterRule.getAddBasis());
			headquarterCost.setAddPrice(headquarterRule.getAddPrice());
			headquarterCost.setAddDiscount(headquarterRule.getAddDiscount());
			headquarterCost.setDiscountRate(headquarterRule.getDiscountRate());
			
			if ("1".equals(headquarterRule.getAddBasis())) {
				//加价依据是厂进价
				PurchasePrice purchasePrice = purchasePriceService.findBalancePurchasePrice(
						headquarterCost.getItemNo(), headquarterCost.getSupplierNo(), new Date());
				if (null == purchasePrice || null ==purchasePrice.getFactoryPrice()) {
					//获取厂进价失败
					headquarterCost.setFailMessage("获取厂进价失败！");
					return;
				}
				//总部价就等于厂进价+加价或者厂进价乘以加折
				BigDecimal facPrice = purchasePrice.getFactoryPrice();
				if(null != facPrice && null != headquarterRule.getAddPrice()) {
					headquarterCost.setHeadquarterCost(facPrice.add(headquarterRule.getAddPrice()));
				} else if(null != facPrice && null !=  headquarterRule.getAddDiscount()) {
					headquarterCost.setHeadquarterCost(facPrice.multiply(headquarterRule.getAddDiscount()));
				}
				headquarterCost.setFactoryPrice(facPrice);
				headquarterCost.setEffectiveTime(purchasePrice.getEffectiveDate());
				
			}else if ("2".equals(headquarterRule.getAddBasis())) {
				//加价依据是牌价,直接取自商品的牌价
				Map<String, Object> qPriceMap = new HashMap<String, Object>();
				qPriceMap.put("itemNo", headquarterCost.getItemNo());
				qPriceMap.put("organId", "0000");
				qPriceMap.put("quotationTactics", 0);
				
				List<PriceQuotationList> quotationLists = priceQuotationListService.findByBiz(null, qPriceMap);
				if (CollectionUtils.isEmpty(quotationLists)) {
					//获取牌价失败
					headquarterCost.setFailMessage("获取牌价失败！");
					return;
				}
				BigDecimal tagPrice = quotationLists.get(0).getTagPrice();
				if (null != tagPrice && null != headquarterRule.getDiscountRate() ) {
					headquarterCost.setHeadquarterCost(tagPrice.multiply(headquarterRule.getDiscountRate()));
					// add by wang.m 牌价生成需要四舍五入
					headquarterCost.setHeadquarterCost(headquarterCost.getHeadquarterCost().setScale(0, BigDecimal.ROUND_HALF_UP));
				}
				headquarterCost.setTagPrice(tagPrice);
				headquarterCost.setEffectiveTime(new Date());
			}	
			
		} catch (ServiceException e) {
			throw new ManagerException("匹配加价规则失败!!!");
		}
	}

	/**
	 * param batchType 批量类型，1-采购价批量， 2-牌价批量
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public HeadquarterCostMaintain batchGenerateHeadquarterCost(HeadquarterCostMaintain headquarterCost, String batchType)
			throws ManagerException{
		try {
			//加价规则参数
			Map<String, Object> priceRuleParams = new HashMap<String, Object>();
			
			//商品供应商组匹配
			Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
			supplierGroupParams.put("supplierNo", headquarterCost.getSupplierNo());
			List<SupplierGroupRel> groupRelList = this.supplierGroupRelService.findByBiz(null, supplierGroupParams);
			if (CollectionUtils.isEmpty(groupRelList)) {
				//记录匹配加价规则失败日志
				doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.SUPPLIER_GROUP_UNMATCH);
				headquarterCost.setMatchResult(false);
				return headquarterCost;
			}
			priceRuleParams.put("supplierGroupNoAlial", groupRelList.get(0).getGroupNo());
			
			//结算大类匹配
			Map<String, Object> categoryParams = new HashMap<String, Object>();
			categoryParams.put("categoryNo", headquarterCost.getCategoryNo());
			List<SettleCategoryDtl> settleCategoryList = this.settleCategoryDtlService.findByBiz(null, categoryParams);
			if (CollectionUtils.isEmpty(settleCategoryList)) {
				//记录匹配加价规则失败日志
				doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.SETTLE_CATEGORY_UNMATCH);
				headquarterCost.setMatchResult(false);
				return headquarterCost;
			}
			priceRuleParams.put("categoryNoAlial", settleCategoryList.get(0).getSettleCategoryNo());
			
			//品牌匹配品牌部
			Map<String, Object> brandUnitParams = new HashMap<String, Object>();
			brandUnitParams.put("brandNo", headquarterCost.getBrandNo());
			List<Brand> brandList = this.brandService.findByBiz(null, brandUnitParams);
			if (!CollectionUtils.isEmpty(brandList)) {
				priceRuleParams.put("brandUnitNoAlial", brandList.get(0).getSysNo());
			}
			priceRuleParams.put("currentTime", new Date());
			
			//新旧款匹配
			Map<String, Object> newStyleParams = new HashMap<String, Object>();
			newStyleParams.put("yearCode", headquarterCost.getYear());
			newStyleParams.put("seasonNo", headquarterCost.getSeason());
			
			List<SettleNewStyleDtl> newStyleList = this.settleNewStyleDtlService.findByBiz(null, newStyleParams);
			//根据新旧款来查询加价规则
			List<HeadquarterPriceRule> hqNewStylePriceRuleList = null;
			//根据年份季节来查询加价规则
			List<HeadquarterPriceRule> hqYearSeasonPriceRuleList = null;
			
			if (!CollectionUtils.isEmpty(newStyleList)) {
				priceRuleParams.put("styleType", newStyleList.get(0).getStyleNo());
				priceRuleParams.put("newStyleFlag", 1);
				
				hqNewStylePriceRuleList = this.headquarterPriceRuleService.findByBiz(null, priceRuleParams);
			}	
			//根据新旧款没有匹配到加价规则，再使用年份季节匹配
			priceRuleParams.put("yearCode", headquarterCost.getYear());
			priceRuleParams.put("season", headquarterCost.getSeason());
			priceRuleParams.put("styleType", "");
			priceRuleParams.put("newStyleFlag", 0);
			
			hqYearSeasonPriceRuleList = this.headquarterPriceRuleService.findByBiz(null, priceRuleParams);
			
			List<HeadquarterPriceRule> headquarterPriceRuleList = new ArrayList<HeadquarterPriceRule>();
			if (!CollectionUtils.isEmpty(hqNewStylePriceRuleList)) {
				headquarterPriceRuleList.addAll(hqNewStylePriceRuleList);
			}
			if (!CollectionUtils.isEmpty(hqYearSeasonPriceRuleList)) {
				headquarterPriceRuleList.addAll(hqYearSeasonPriceRuleList);
			}
			
			//匹配到多个加价规则
			if (headquarterPriceRuleList.size() > 1) {
				// add by wang.m 二级大类匹配
				List<HeadquarterPriceRule> newRuleList = new ArrayList<HeadquarterPriceRule>();
				boolean errorFlag = false;
				String twoLevelCategoryNo = headquarterCost.getTwoLevelCategoryNo();
				for (HeadquarterPriceRule rule : headquarterPriceRuleList) {
					if(StringUtils.isBlank(rule.getTwoLevelCategoryNo())){
						errorFlag = true;
						break ;
					}
					if(rule.getTwoLevelCategoryNo().indexOf(twoLevelCategoryNo) != -1){
						newRuleList.add(rule);
					}
				}				
				if(newRuleList.size() == 1 && !errorFlag){
					headquarterPriceRuleList = newRuleList;
				}else{
					//获取厂进价失败
					doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.MATCH_ONEMORE_ERROR);
					headquarterCost.setMatchResult(false);
					return headquarterCost;
				}
			}
			//未匹配到加价规则，需要记录日志
			if (CollectionUtils.isEmpty(headquarterPriceRuleList)){
				//未匹配到加价规则，需要记录日志
				doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
				headquarterCost.setMatchResult(false);
				return headquarterCost;
			}
			
			//匹配到一个规则
			HeadquarterPriceRule headquarterRule = headquarterPriceRuleList.get(0);
			// add by wang.m 校验二级大类匹配
			if(StringUtils.isNotBlank(headquarterRule.getTwoLevelCategoryNo()) 
					&& headquarterRule.getTwoLevelCategoryNo().indexOf(headquarterCost.getTwoLevelCategoryNo()) == -1){
				//未匹配到加价规则，需要记录日志
				doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
				headquarterCost.setMatchResult(false);
				return headquarterCost;
			}
			headquarterCost.setAddRuleNo(headquarterRule.getAddRuleNo());
			headquarterCost.setAddBasis(headquarterRule.getAddBasis());
			headquarterCost.setAddPrice(headquarterRule.getAddPrice());
			headquarterCost.setAddDiscount(headquarterRule.getAddDiscount());
			headquarterCost.setDiscountRate(headquarterRule.getDiscountRate());
			
			if ("1".equals(headquarterRule.getAddBasis())) {
				//加价依据是厂进价
				if (false == "1".equals(batchType)) {
					//未匹配到加价规则，需要记录日志
					doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
					headquarterCost.setMatchResult(false);
					return headquarterCost;
				}
				//总部价就等于厂进价+加价或者厂进价乘以加折
				BigDecimal facPrice = headquarterCost.getFactoryPrice();
				if(null != facPrice && null != headquarterRule.getAddPrice()) {
					headquarterCost.setHeadquarterCost(facPrice.add(headquarterRule.getAddPrice()));
				} else if(null != facPrice && null !=  headquarterRule.getAddDiscount()) {
					headquarterCost.setHeadquarterCost(facPrice.multiply(headquarterRule.getAddDiscount()));
				}
				headquarterCost.setMatchResult(true);
				
			}else if ("2".equals(headquarterRule.getAddBasis())) {
				//加价依据是牌价
				if (false == "2".equals(batchType)) {
					//未匹配到加价规则，需要记录日志
					doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
					headquarterCost.setMatchResult(false);
					return headquarterCost;
				}
				headquarterCost.setHeadquarterCost(headquarterCost.getTagPrice().multiply(headquarterRule.getDiscountRate()));
				// add by wang.m 牌价生成需要四舍五入
				headquarterCost.setHeadquarterCost(headquarterCost.getHeadquarterCost().setScale(0, BigDecimal.ROUND_HALF_UP));
				headquarterCost.setMatchResult(true);
			}	
			//记录加价规则匹配成功
			doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.MATCH_SUCCESS);
			// 匹配上了需要插入记录,没有匹配上的已经记录了日志
			if (headquarterCost.getMatchResult()) {
				this.headquarterCostMaintainService.add(headquarterCost);
			}
			
			return headquarterCost;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findRegionPriceCount(Map<String, Object> params)
			throws ManagerException {
		try {
    		//检查是否已经存在错误的记录，有则更新，没有才插入
    		
			return headquarterCostMaintainService.findRegionPriceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public Map<String, Object> getPriceRuleData(HeadquarterCostMaintain headquarterCostMaintain)
			throws ManagerException {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("failMessage", "");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("addRuleNo", headquarterCostMaintain.getAddRuleNo());
			
			List<HeadquarterPriceRule> headquarterPriceRuleList = headquarterPriceRuleService.findByBiz(null, params);
			
			if (CollectionUtils.isEmpty(headquarterPriceRuleList)) {
				return null;
			}
			HeadquarterPriceRule headquarterRule = headquarterPriceRuleList.get(0);

			returnMap.put("addBasis",headquarterRule.getAddBasis());
			returnMap.put("addBasisName",headquarterRule.getAddBasis().equals("1") ? "厂进价" : "牌价");
			returnMap.put("addPrice",headquarterRule.getAddPrice());
			returnMap.put("addDiscount",headquarterRule.getAddDiscount());
			returnMap.put("discountRate",headquarterRule.getDiscountRate());
			
			if ("1".equals(headquarterRule.getAddBasis())) {
				
				//加价依据是厂进价
				PurchasePrice purchasePrice = purchasePriceService.findBalancePurchasePrice(
						headquarterCostMaintain.getItemNo(), headquarterCostMaintain.getSupplierNo(), new Date());
				if (null == purchasePrice || null ==purchasePrice.getFactoryPrice()) {
					returnMap.put("failMessage", "厂进价获取失败");
					return returnMap;
				}
				//总部价就等于厂进价+加价或者厂进价乘以加折
				BigDecimal facPrice = purchasePrice.getFactoryPrice();
				if(null != headquarterRule.getAddPrice()) {
					returnMap.put("headquarterCost", facPrice.add(headquarterRule.getAddPrice()));
				} else if(null !=  headquarterRule.getAddDiscount()) {
					returnMap.put("headquarterCost", facPrice.multiply(headquarterRule.getAddDiscount()));
				}else {
					returnMap.put("headquarterCost", facPrice);
				}
				returnMap.put("factoryPrice", facPrice);
				returnMap.put("effectiveTime", purchasePrice.getEffectiveDate());
			}else if ("2".equals(headquarterRule.getAddBasis())) {
				//加价依据是牌价,直接取自商品的牌价
				Map<String, Object> qPriceMap = new HashMap<String, Object>();
				qPriceMap.put("itemNo", headquarterCostMaintain.getItemNo());
				qPriceMap.put("organId", "0000");
				qPriceMap.put("quotationTactics", 0);
				
				List<PriceQuotationList> quotationLists = priceQuotationListService.findByBiz(null, qPriceMap);
				if (CollectionUtils.isEmpty(quotationLists)) {
					returnMap.put("failMessage", "牌价价获取失败");
					return returnMap;
				}
				BigDecimal tagPrice = quotationLists.get(0).getTagPrice();
				
				if (null != tagPrice && null != headquarterRule.getDiscountRate() ) {
					returnMap.put("headquarterCost", tagPrice.multiply(headquarterRule.getDiscountRate()));
				}else {
					returnMap.put("headquarterCost", tagPrice ==null ? 0 : tagPrice);
				}
				returnMap.put("tagPrice", tagPrice);
				returnMap.put("effectiveTime", new Date());
			}	
			
			return returnMap;
			
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public List<HeadquarterPriceRule> findRegionAddRulesByBiz(
			HeadquarterCostMaintain headquarterCost) throws ManagerException {
		try {
			//加价规则参数
			Map<String, Object> priceRuleParams = new HashMap<String, Object>();
			//商品供应商组匹配
			Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
			supplierGroupParams.put("supplierNo", headquarterCost.getSupplierNo());
			List<SupplierGroupRel> groupRelList = this.supplierGroupRelService.findByBiz(null, supplierGroupParams);
			if (CollectionUtils.isEmpty(groupRelList)) {
				return null;
			}
			priceRuleParams.put("supplierGroupNoAlial", groupRelList.get(0).getGroupNo());
			
			//结算大类匹配
			Map<String, Object> categoryParams = new HashMap<String, Object>();
			categoryParams.put("categoryNo", headquarterCost.getCategoryNo());
			List<SettleCategoryDtl> settleCategoryList = this.settleCategoryDtlService.findByBiz(null, categoryParams);
			if (CollectionUtils.isEmpty(settleCategoryList)) {
				return null;
			}
			priceRuleParams.put("categoryNoAlial", settleCategoryList.get(0).getSettleCategoryNo());
			
			//品牌匹配品牌部
			Map<String, Object> brandUnitParams = new HashMap<String, Object>();
			brandUnitParams.put("brandNo", headquarterCost.getBrandNo());
			List<Brand> brandList = this.brandService.findByBiz(null, brandUnitParams);
			if (!CollectionUtils.isEmpty(brandList)) {
				priceRuleParams.put("brandUnitNoAlial", brandList.get(0).getSysNo());
			}
			priceRuleParams.put("currentTime", new Date());
			
			//新旧款匹配
			Map<String, Object> newStyleParams = new HashMap<String, Object>();
			newStyleParams.put("yearCode", headquarterCost.getYear());
			newStyleParams.put("seasonNo", headquarterCost.getSeason());
			List<SettleNewStyleDtl> newStyleList = this.settleNewStyleDtlService.findByBiz(null, newStyleParams);
			//根据年份季节来查询加价规则
			List<HeadquarterPriceRule> hqNewStylePriceRuleList = null;
			//根据新旧款来查询加价规则
			List<HeadquarterPriceRule> hqYearSeasonPriceRuleList = null;
			
			if (!CollectionUtils.isEmpty(newStyleList)) {
				priceRuleParams.put("styleType", newStyleList.get(0).getStyleNo());
				priceRuleParams.put("newStyleFlag", 1);
				//根据新旧款匹配加价规则
				hqNewStylePriceRuleList = this.headquarterPriceRuleService.findByBiz(null, priceRuleParams);
			}	
			
			//根据年份季节匹配加价规则
			priceRuleParams.put("yearCode", headquarterCost.getYear());
			priceRuleParams.put("season", headquarterCost.getSeason());
			priceRuleParams.put("styleType", "");
			priceRuleParams.put("newStyleFlag", 0);
			hqYearSeasonPriceRuleList = this.headquarterPriceRuleService.findByBiz(null, priceRuleParams);
			
			List<HeadquarterPriceRule> headquarterPriceRuleList = new ArrayList<HeadquarterPriceRule>();
			if (!CollectionUtils.isEmpty(hqNewStylePriceRuleList)) {
				headquarterPriceRuleList.addAll(hqNewStylePriceRuleList);
			}
			if (!CollectionUtils.isEmpty(hqYearSeasonPriceRuleList)) {
				headquarterPriceRuleList.addAll(hqYearSeasonPriceRuleList);
			}
			
			return headquarterPriceRuleList;
			
		} catch (ServiceException e) {
			throw new ManagerException("查询加价规则失败");
		} 
	}
	
	/**
	 * 根据单据日期和商品编码查询总部成本价
	 * @param itemNo
	 * @param billDate
	 * @return
	 */
	@Override
	public BigDecimal findHqCostForBalanceBill(String itemNo, Date billDate){
		
		try {
			if (StringUtils.isEmpty(itemNo) || null == billDate) {
				return null;
			}
			//总部价查询条件
			Map<String, Object> headPriceParam = new HashMap<String, Object>();
			headPriceParam.put("itemNo", itemNo);
			headPriceParam.put("effectiveTime", billDate);
			
			HeadquarterCostMaintain headCostMaintain = this.headquarterCostMaintainService.getLastEffectiveModel(headPriceParam);
		
			if (null != headCostMaintain) {
				return headCostMaintain.getHeadquarterCost();
			}else {
				return null;
			}
		} catch (ServiceException e) {
			return null;
		}
		
	}

	@Override
	public void batchAdd(List<HeadquarterCostMaintain> batchInsert)
			throws ManagerException {
		try {
			headquarterCostMaintainService.batchAdd(batchInsert);
		} catch (ServiceException e) {
			throw new ManagerException("批量添加总部价失败");
		}
	}
	

	@Override
	public void batchAddNUpdate(List<HeadquarterCostMaintain> batchInsert)
			throws ManagerException {
		try {
			headquarterCostMaintainService.batchAddNUpdate(batchInsert);
		} catch (ServiceException e) {
			throw new ManagerException("批量添加总部价失败");
		}
	}
	
	@Override
    public HeadquarterCostMaintain addHeadquarterCost(HeadquarterCostMaintain headquarterCost, String batchType) throws ManagerException {
    	try {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("itemNo", headquarterCost.getItemNo());
    		params.put("addBasis", batchType);
    		// 根据货号获取加价规则
    		List<HeadquarterPriceRule> ruleList = headquarterPriceRuleService.findRuleByParams(params);
    		if(CollectionUtils.isEmpty(ruleList)){// 没有匹配到规则
				doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
				headquarterCost.setMatchResult(false);
    			return headquarterCost;
    		}
    		if(ruleList.size() >1){// 匹配到多个规则
    			if(ruleList.get(0).getEffectiveTime().getTime() == ruleList.get(1).getEffectiveTime().getTime()){
    				doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.MATCH_ONEMORE_ERROR);
    				headquarterCost.setMatchResult(false);
        			return headquarterCost;
    			}
    		}
    		if(!setHeadquarterCostInfo(headquarterCost,ruleList.get(0),batchType)){
    			doMatchRuleErrorLog(headquarterCost, RuleMatchFailReasonEnums.OTHER_UNMATCH);
    			headquarterCost.setMatchResult(false);
    			return headquarterCost;
    		}
    		headquarterCost.setMatchResult(true);
    		headquarterCostMaintainService.add(headquarterCost);
			return headquarterCost;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
    }

	private boolean setHeadquarterCostInfo(HeadquarterCostMaintain headquarterCost,HeadquarterPriceRule headquarterRule, String batchType) {
		headquarterCost.setAddRuleNo(headquarterRule.getAddRuleNo());
		headquarterCost.setAddBasis(headquarterRule.getAddBasis());
		headquarterCost.setAddPrice(headquarterRule.getAddPrice());
		headquarterCost.setAddDiscount(headquarterRule.getAddDiscount());
		headquarterCost.setDiscountRate(headquarterRule.getDiscountRate());
		
		if ("1".equals(batchType)) {//加价依据是厂进价
			BigDecimal facPrice = headquarterCost.getFactoryPrice();
			if(null != facPrice && null != headquarterRule.getAddPrice()) {
				headquarterCost.setHeadquarterCost(facPrice.add(headquarterRule.getAddPrice()));
				return true;
			} else if(null != facPrice && null !=  headquarterRule.getAddDiscount()) {
				headquarterCost.setHeadquarterCost(facPrice.multiply(headquarterRule.getAddDiscount()));
				return true;
			}
		}else if ("2".equals(batchType)) {//加价依据是牌价
			headquarterCost.setHeadquarterCost(headquarterCost.getTagPrice().multiply(headquarterRule.getDiscountRate()));
			headquarterCost.setHeadquarterCost(headquarterCost.getHeadquarterCost().setScale(0, BigDecimal.ROUND_HALF_UP));
			return true;
		}	
		return false;
	}

	@Override
	public int batchGenetareCostByRule(Map<String, Object> params)
			throws ManagerException {
		try {
			return headquarterCostMaintainService.batchGenetareCostByRule(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int batchGenetareCostNew(Map<String, Object> params)
			throws Exception {
			SystemUser user = CurrentUser.getCurrentUser();
			Map<String, Object> brandParams = new HashMap<String, Object>();
			brandParams.put("multiBrandUnitNo", FasUtil.formatInQueryCondition(params.get("brandUnitNos").toString()));
			List<Brand> brands = brandService.findByBiz(null, brandParams);
			ExecutorService exe = Executors.newFixedThreadPool(30);
			for (Brand brand : brands) {
				exe.execute(new GenereateCostThread(brand, user, params));
			}
			exe.shutdown();
	        exe.awaitTermination(2, TimeUnit.HOURS);
	        return 1;
	}
    
	class GenereateCostThread implements Runnable {
		
		private Brand brand;
		
		private SystemUser user; 
		
		private Map<String, Object> params;
		
		public GenereateCostThread(Brand brand, SystemUser user, Map<String, Object> params) {
			this.params = params;
			this.brand = brand;
			this.user = user;
		}
		@Override
		public void run() {
			try {
				String categoryNos = String.valueOf(params.get("multiCategoryNo"));
				String effectiveTime = String.valueOf(params.get("effectiveTime1"));
				String addBasis = String.valueOf(params.get("HQQuotationFlag"));
				String isFirst = String.valueOf(params.get("firstNew1"));
				String username = user.getUsername();
				Map<String, Object> queryParams = new HashMap<>();
				queryParams.put("brandNo", brand.getBrandNo());
				queryParams.put("multiCategoryNo", categoryNos);
				queryParams.put("effectiveTime", effectiveTime);
				queryParams.put("addBasis", addBasis);
				queryParams.put("isFirst", isFirst);
				int pageNo = 1;
				int pageSize = 20000;
				SimplePage page = new SimplePage(pageNo, pageSize, 0);
				while(true){
					List<HeadquarterCostMaintain> lstHeadquarterCost = commonUtilService.getHQNeedRuleMatchItems(page,queryParams);
					for (HeadquarterCostMaintain headquarterCost: lstHeadquarterCost) {
						headquarterCost.setEffectiveTime(DateUtil.parseToDate(effectiveTime, "yyyy-MM-dd"));
						headquarterCost.setCreateTime(new Date());
						headquarterCost.setCreateUser(username);
						headquarterCostMaintainService.add(headquarterCost);
					}
					if(lstHeadquarterCost.size() < pageSize){
						break;
					}
				}
			} catch (Exception e) {
				LOGGER.error("总部价生成异常!", e);
			}
		}
	}
}