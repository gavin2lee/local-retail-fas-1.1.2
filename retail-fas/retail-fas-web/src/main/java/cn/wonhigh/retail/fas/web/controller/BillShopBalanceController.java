package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDeductDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDiffDto;
import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.ShopMallEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceBrand;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceCateSumManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceCodeSumManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDeductManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDiffManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceProSumManager;
import cn.wonhigh.retail.fas.manager.BillShopSaleOrderManager;
import cn.wonhigh.retail.fas.manager.ShopBalanceDateManager;
import cn.wonhigh.retail.fas.manager.ShopBrandManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.service.BillBacksectionSplitDtlService;
import cn.wonhigh.retail.fas.service.BillShopBalanceDeductService;
import cn.wonhigh.retail.fas.service.BillShopBalanceDiffService;
import cn.wonhigh.retail.fas.web.controller.BaseController;
import cn.wonhigh.retail.fas.web.utils.AbstactBillDtlHandler;
import cn.wonhigh.retail.fas.web.utils.BalanceHSSFExportComplex;
import cn.wonhigh.retail.fas.web.utils.BillShopDeductHandler;
import cn.wonhigh.retail.fas.web.utils.BillShopDiffHandler;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.fas.web.utils.ShopMallDtlShowTypeEnum;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.fas.web.vo.HeaderFormDataVo;
import cn.wonhigh.retail.fas.web.vo.MutliSheetExportVo;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author chen.mj
 * @date 2014-09-02 14:54:52
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/mall_shopbalance")
@ModuleVerify("30140007")
@SuppressWarnings("rawtypes")
public class BillShopBalanceController extends BaseController<BillShopBalance> {
	
	@Resource
	public BillShopBalanceManager billShopBalanceManager;
	
	@Resource
	private BillShopBalanceDiffManager billShopBalanceDiffManager;
	
	@Resource
	private BillShopBalanceDeductManager billShopBalanceDeductManager;
	
	@Resource
    private BillShopBalanceCateSumManager billShopBalanceCateSumManager;
	
	@Resource
    private BillShopBalanceCodeSumManager  billShopBalanceCodeSumManager;

	@Resource
	private ShopManager shopManager;
	
	@Resource
	private ShopBrandManager shopBrandManager;
	
	@Resource
	private ShopBalanceDateManager shopBalanceDateManager; 
	
	@Resource
	private BillShopBalanceProSumManager billShopBalanceProSumManager;
	
	@Resource
	private BillShopSaleOrderManager billShopSaleOrderManager;
	
	@Resource
	private BillShopBalanceDiffService billShopBalanceDiffService;
	
	@Resource
	private BillShopBalanceDeductService billShopBalanceDeductService;
	
	@Resource
	private BillBacksectionSplitDtlService billBacksectionSplitDtlService;
	
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("mall_shopbalance/", billShopBalanceManager);
	}
	
	@RequestMapping(value = "/check_warn_list.json")
	@ResponseBody
	public  Map<String, Object> checkWarnList(HttpServletRequest req, Model model) {
		int total =0;
		List<BillShopBalance> list = null;
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		String checkType = req.getParameter("checkType");
		if(StringUtils.isNotBlank(checkType)){
			if(checkType.equals("0")){
				total = billShopBalanceManager.checkBackPayTimeoutCount(params);
				if(total > 0){
					SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
					list = billShopBalanceManager.checkBackPayTimeoutList(page, sortColumn, sortOrder, params);
				}
			}else if(checkType.equals("1")){
				total = billShopBalanceManager.checkMakeInvoiceTimeoutCount(params);
				if(total > 0){
					SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
					list = billShopBalanceManager.checkMakeInvoiceTimeoutList(page, sortColumn, sortOrder, params);
				}
			}
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/shop_balance_brand.json")
	@ResponseBody
	public  Map<String, Object> queryShopBalanceBrand(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		List<BillShopBalanceCateSum> list = billShopBalanceCateSumManager.findBalanceShopBrand(params);//.findByBiz(null, cateSumParams);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("rows", list);
		if(list == null || list.size() == 0) {
			List<ShopBrand> shopbrandlist  = shopBrandManager.findByBiz(null, params); 
			obj.put("rows", shopbrandlist);
		}
		return obj;
	}
	

	@RequestMapping(value = "/shopbalance_list_main.json")
	@ResponseBody
	public  Map<String, Object> queryShopBalnceList(HttpServletRequest req, Model model) throws ManagerException {
		try{
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
        String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
        String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
        String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
        String month = params.get("month") == null ? null : params.get("month").toString();
        String retailType = params.get("retailType") == null ? null : params.get("retailType").toString();
        if (StringUtils.isNotEmpty(shopNos)) {
        	params.put("shopNos",Arrays.asList(shopNos.split(",")));
		}
        if (StringUtils.isNotEmpty(companyNos)) {
        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
		}
        if(StringUtils.isNotEmpty(organNos)){
        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//        	params.put("organNos",  null);
		}
        if(StringUtils.isNotEmpty(brandNos)){
	        String brandUnitNo = brandNos.replace(",","|");
//			params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			params.put("brandUnitNo", brandUnitNo); 
		}	
        if (StringUtils.isNotEmpty(retailType)) {
        	params.put("retailType", FasUtil.formatInQueryCondition(retailType));
		}
        if (StringUtils.isNotEmpty(shopNo)) {
        	params.put("shopNo", shopNo);
		}
        if (StringUtils.isNotEmpty(month)) {
        	params.put("month", month);
		}       
		int total = this.billShopBalanceManager.selectBlanceCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalance> list = this.billShopBalanceManager.selectBlanceList(page, sortColumn, sortOrder, params);
		
		for(BillShopBalance shopBalance : list) {
//			BigDecimal value0 = systemBillingAmount.subtract(mallBillingAmount) ;//BigDecimalUtil.subtract(systemBillingAmount, mallBillingAmount);
			
			//单据体结算差异页签的扣费差异金额汇总+票前费用.扣费差异
			Map<String, String> msgMap = new HashMap<String, String>();
			Map<String, Object> msparams = new HashMap<String, Object>();
			msparams.put("balanceNo", shopBalance.getBalanceNo());
			msparams.put("parBalanceNo", "");
			// 查询结算差异汇总数据
			BigDecimal totalDiffSystemAmount = null; //结算差异-系统扣费总额
			BigDecimal totalDiffAmount = null; //结算差异-扣费差异总额
			GatherBillShopBalanceDiffDto diffDto = billShopBalanceDiffService.gatherBalanceDiff(params);
			if(diffDto != null) {
				totalDiffSystemAmount = diffDto.getTotalDeductDiffAmount();
				totalDiffAmount = diffDto.getTotalDiffAmount();
			}
			if(totalDiffSystemAmount == null) {
				totalDiffSystemAmount = BigDecimal.ZERO;
			}
			if(totalDiffAmount == null) {
				totalDiffAmount = BigDecimal.ZERO;
			}
			// 查询票前费用汇总数据
			msparams.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
			BigDecimal totalDeductActualAmount = null; //票前费用-实际扣费总额
			BigDecimal totalDeductDiffAmount = null; //票前费用-扣费差异总额
			GatherBillShopBalanceDeductDto deductDto = billShopBalanceDeductService.gatherBalanceDeduct(params);
			if(deductDto != null) {
				totalDeductActualAmount = deductDto.getTotalActualAmount();
				totalDeductDiffAmount = deductDto.getTotalDiffAmount();
			}
			if(totalDeductActualAmount == null) {
				totalDeductActualAmount = BigDecimal.ZERO;
			}
			if(totalDeductDiffAmount == null) {
				totalDeductDiffAmount = BigDecimal.ZERO;
			}
			BigDecimal value1 = BigDecimalUtil.add(totalDiffAmount, totalDeductDiffAmount);
			BigDecimal  value0 = shopBalance.getBalanceDiffAmount();
//			if(value0.compareTo(value1) != 0) {
//			结算差异额  结算差异总额 - 结算差异.扣费差异 +票前费用.扣费差异 
			BigDecimal diffValue = value0.subtract(value1);
			if(value0.subtract(value1).doubleValue() > 0.02 || value0.subtract(value1).doubleValue() < -0.02) {
				shopBalance.setIsEqureTrue("0");
			}else {
				shopBalance.setIsEqureTrue("1");
			}
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
		}catch(Exception e){
//			e.printStackTrace();
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "/shopbalance_resultlist.json") 
	@ResponseBody
	public  Map<String, Object> queryShopBalanceResultList(HttpServletRequest req, Model model) throws ManagerException {
		try{
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
        String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
        String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
        String retailType = params.get("retailType") == null ? null : params.get("retailType").toString();
        if (StringUtils.isNotEmpty(shopNos)) {
        	params.put("shopNos",Arrays.asList(shopNos.split(",")));
		}
        if (StringUtils.isNotEmpty(companyNos)) {
        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
		}
        if(StringUtils.isNotEmpty(organNos)){
        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//        	params.put("organNos",  null);
		}
        if(StringUtils.isNotEmpty(brandNos)){
	        String brandUnitNo = brandNos.replace(",","|");
//			params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			params.put("brandUnitNo", brandUnitNo); 
		}	
        if (StringUtils.isNotEmpty(retailType)) {
        	params.put("retailType", FasUtil.formatInQueryCondition(retailType));
		}
		int total = this.billShopBalanceManager.selectSalesResultCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalance> list = this.billShopBalanceManager.selectSalesResultList(page, sortColumn, sortOrder, params);
		
		for(BillShopBalance shopBalance : list) {
//			BigDecimal value0 = systemBillingAmount.subtract(mallBillingAmount) ;//BigDecimalUtil.subtract(systemBillingAmount, mallBillingAmount);
			
			//单据体结算差异页签的扣费差异金额汇总+票前费用.扣费差异
			Map<String, String> msgMap = new HashMap<String, String>();
			Map<String, Object> msparams = new HashMap<String, Object>();
			msparams.put("balanceNo", shopBalance.getBalanceNo());
			msparams.put("parBalanceNo", "");
			// 查询结算差异汇总数据
			BigDecimal totalDiffSystemAmount = null; //结算差异-系统扣费总额
			BigDecimal totalDiffAmount = null; //结算差异-扣费差异总额
			GatherBillShopBalanceDiffDto diffDto = billShopBalanceDiffService.gatherBalanceDiff(params);
			if(diffDto != null) {
				totalDiffSystemAmount = diffDto.getTotalDeductDiffAmount();
				totalDiffAmount = diffDto.getTotalDiffAmount();
			}
			if(totalDiffSystemAmount == null) {
				totalDiffSystemAmount = BigDecimal.ZERO;
			}
			if(totalDiffAmount == null) {
				totalDiffAmount = BigDecimal.ZERO;
			}
			// 查询票前费用汇总数据
			msparams.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
			BigDecimal totalDeductActualAmount = null; //票前费用-实际扣费总额
			BigDecimal totalDeductDiffAmount = null; //票前费用-扣费差异总额
			GatherBillShopBalanceDeductDto deductDto = billShopBalanceDeductService.gatherBalanceDeduct(params);
			if(deductDto != null) {
				totalDeductActualAmount = deductDto.getTotalActualAmount();
				totalDeductDiffAmount = deductDto.getTotalDiffAmount();
			}
			if(totalDeductActualAmount == null) {
				totalDeductActualAmount = BigDecimal.ZERO;
			}
			if(totalDeductDiffAmount == null) {
				totalDeductDiffAmount = BigDecimal.ZERO;
			}
			BigDecimal value1 = BigDecimalUtil.add(totalDiffAmount, totalDeductDiffAmount);
			BigDecimal  value0 = shopBalance.getBalanceDiffAmount();
//			if(value0.compareTo(value1) != 0) {
//			结算差异额  结算差异总额 - 结算差异.扣费差异 +票前费用.扣费差异 
			BigDecimal diffValue = value0.subtract(value1);
			if(value0.subtract(value1).doubleValue() > 0.02 || value0.subtract(value1).doubleValue() < -0.02) {
				shopBalance.setIsEqureTrue("0");
			}else {
				shopBalance.setIsEqureTrue("1");
			}
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
		}catch(Exception e){
//			e.printStackTrace();
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "/shopbalance_backsectionsplit_list.json")
	@ResponseBody
	public  Map<String, Object> queryShopBalnceBackSectionSplitList(HttpServletRequest req, Model model) throws ManagerException {
		try{
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
        String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
        String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
        String retailType = params.get("retailType") == null ? null : params.get("retailType").toString();
        if (StringUtils.isNotEmpty(shopNos)) {
        	params.put("shopNos",Arrays.asList(shopNos.split(",")));
		}
        if (StringUtils.isNotEmpty(companyNos)) {
        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
		}
        if(StringUtils.isNotEmpty(organNos)){
        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//        	params.put("organNos",  null);
		}
        if(StringUtils.isNotEmpty(brandNos)){
	        String brandUnitNo = brandNos.replace(",","|");
//			params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			params.put("brandUnitNo", brandUnitNo); 
		}	
        if (StringUtils.isNotEmpty(retailType)) {
        	params.put("retailType", FasUtil.formatInQueryCondition(retailType));
		}
		int total = this.billShopBalanceManager.selectSalesBackSectionSplitCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalance> list = this.billShopBalanceManager.selectSalesBackSectionSplitList(page, sortColumn, sortOrder, params);
		
		for(BillShopBalance shopBalance : list) {
//			BigDecimal value0 = systemBillingAmount.subtract(mallBillingAmount) ;//BigDecimalUtil.subtract(systemBillingAmount, mallBillingAmount);
			
			//单据体结算差异页签的扣费差异金额汇总+票前费用.扣费差异
			Map<String, String> msgMap = new HashMap<String, String>();
			Map<String, Object> msparams = new HashMap<String, Object>();
			msparams.put("balanceNo", shopBalance.getBalanceNo());
			msparams.put("parBalanceNo", "");
			// 查询结算差异汇总数据
			BigDecimal totalDiffSystemAmount = null; //结算差异-系统扣费总额
			BigDecimal totalDiffAmount = null; //结算差异-扣费差异总额
			GatherBillShopBalanceDiffDto diffDto = billShopBalanceDiffService.gatherBalanceDiff(params);
			if(diffDto != null) {
				totalDiffSystemAmount = diffDto.getTotalDeductDiffAmount();
				totalDiffAmount = diffDto.getTotalDiffAmount();
			}
			if(totalDiffSystemAmount == null) {
				totalDiffSystemAmount = BigDecimal.ZERO;
			}
			if(totalDiffAmount == null) {
				totalDiffAmount = BigDecimal.ZERO;
			}
			// 查询票前费用汇总数据
			msparams.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
			BigDecimal totalDeductActualAmount = null; //票前费用-实际扣费总额
			BigDecimal totalDeductDiffAmount = null; //票前费用-扣费差异总额
			GatherBillShopBalanceDeductDto deductDto = billShopBalanceDeductService.gatherBalanceDeduct(params);
			if(deductDto != null) {
				totalDeductActualAmount = deductDto.getTotalActualAmount();
				totalDeductDiffAmount = deductDto.getTotalDiffAmount();
			}
			if(totalDeductActualAmount == null) {
				totalDeductActualAmount = BigDecimal.ZERO;
			}
			if(totalDeductDiffAmount == null) {
				totalDeductDiffAmount = BigDecimal.ZERO;
			}
			BigDecimal value1 = BigDecimalUtil.add(totalDiffAmount, totalDeductDiffAmount);
			BigDecimal  value0 = shopBalance.getBalanceDiffAmount();
//			if(value0.compareTo(value1) != 0) {
//			结算差异额  结算差异总额 - 结算差异.扣费差异 +票前费用.扣费差异 
			BigDecimal diffValue = value0.subtract(value1);
			if(value0.subtract(value1).doubleValue() > 0.02 || value0.subtract(value1).doubleValue() < -0.02) {
				shopBalance.setIsEqureTrue("0");
			}else {
				shopBalance.setIsEqureTrue("1");
			}
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
		}catch(Exception e){
//			e.printStackTrace();
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "/shop_list.json")
	@ResponseBody
	public  Map<String, Object> queryShopList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
        String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
        String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
		
        if (StringUtils.isNotEmpty(shopNos)) {
        	params.put("shopNos",Arrays.asList(shopNos.split(",")) );
		}
        if (StringUtils.isNotEmpty(companyNos)) {
        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
		}
			
		int total = this.billShopBalanceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalance> list = this.billShopBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		
		for(BillShopBalance shopBalance : list) {
//			BigDecimal value0 = systemBillingAmount.subtract(mallBillingAmount) ;//BigDecimalUtil.subtract(systemBillingAmount, mallBillingAmount);
			
			//单据体结算差异页签的扣费差异金额汇总+票前费用.扣费差异
			Map<String, String> msgMap = new HashMap<String, String>();
			Map<String, Object> msparams = new HashMap<String, Object>();
			msparams.put("balanceNo", shopBalance.getBalanceNo());
			msparams.put("parBalanceNo", "");
			// 查询结算差异汇总数据
			BigDecimal totalDiffSystemAmount = null; //结算差异-系统扣费总额
			BigDecimal totalDiffAmount = null; //结算差异-扣费差异总额
			GatherBillShopBalanceDiffDto diffDto = billShopBalanceDiffService.gatherBalanceDiff(params);
			if(diffDto != null) {
				totalDiffSystemAmount = diffDto.getTotalDeductDiffAmount();
				totalDiffAmount = diffDto.getTotalDiffAmount();
			}
			if(totalDiffSystemAmount == null) {
				totalDiffSystemAmount = BigDecimal.ZERO;
			}
			if(totalDiffAmount == null) {
				totalDiffAmount = BigDecimal.ZERO;
			}
			// 查询票前费用汇总数据
			msparams.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
			BigDecimal totalDeductActualAmount = null; //票前费用-实际扣费总额
			BigDecimal totalDeductDiffAmount = null; //票前费用-扣费差异总额
			GatherBillShopBalanceDeductDto deductDto = billShopBalanceDeductService.gatherBalanceDeduct(params);
			if(deductDto != null) {
				totalDeductActualAmount = deductDto.getTotalActualAmount();
				totalDeductDiffAmount = deductDto.getTotalDiffAmount();
			}
			if(totalDeductActualAmount == null) {
				totalDeductActualAmount = BigDecimal.ZERO;
			}
			if(totalDeductDiffAmount == null) {
				totalDeductDiffAmount = BigDecimal.ZERO;
			}
			BigDecimal value1 = BigDecimalUtil.add(totalDiffAmount, totalDeductDiffAmount);
			BigDecimal  value0 = shopBalance.getBalanceDiffAmount();
//			if(value0.compareTo(value1) != 0) {
//			结算差异额  结算差异总额 - 结算差异.扣费差异 +票前费用.扣费差异 
			BigDecimal diffValue = value0.subtract(value1);
			if(value0.subtract(value1).doubleValue() > 0.02 || value0.subtract(value1).doubleValue() < -0.02) {
				shopBalance.setIsEqureTrue("0");
			}else {
				shopBalance.setIsEqureTrue("1");
			}
		}
		
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/shop_backsection_splitlist.json")
	@ResponseBody
	public  Map<String, Object> queryBacksectionSplitDeduct(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billShopBalanceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalance> list = this.billShopBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(list == null || list.size() == 0) {
			obj.put("total", 0);
			obj.put("rows", new ArrayList<BillShopBalance>(0));
			return obj;
		}
		for(BillShopBalance shopBalance : list) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("balanceNo", shopBalance.getBalanceNo());
			paramMap.put("status", 2);
			paramMap.put("costDeductType", 2);
			BillShopBalance billShopBalance = billShopBalanceManager.getBacksectionSplitDeduct(paramMap);
			if(billShopBalance == null) { 
				
			} else {
				shopBalance.setBalanceDeductAmount(billShopBalance.getBalanceDeductAmount());
			}
			BillShopBalance billShopBalance1= new BillShopBalance();
			billShopBalance1.setShopNo(billShopBalance.getShopNo());
			billShopBalance1.setMonth(billShopBalance.getMonth());
			BigDecimal  paymentAmount = billShopBalanceManager.getPaymentAmount(billShopBalance1);
			if(paymentAmount == null) {
				paymentAmount=BigDecimal.valueOf(0); 
			}
			shopBalance.setPaymentAmount(paymentAmount);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
		
		
	}
	
	/**
	 * 查询店铺结算单并用于收款回款拆分
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/shop_balance_deduct_after.json")
	@ResponseBody
	public  Map<String, Object> queryShopBalanceDeductAfter(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		List<BillShopBalance> list = this.billShopBalanceManager.findShopBalanceDeductAfter(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 查询统计公司-回款方应回金额
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/shop_balance_receive_amount.json")
	@ResponseBody
	public  BigDecimal queryShopBalanceReceiveAmount(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		List<BillShopBalance> list = this.billShopBalanceManager.findShopBalanceDeductAfter(params);
		
		BigDecimal receiveAmount = new BigDecimal("0"); 
		for(BillShopBalance billShopBalance : list){
			receiveAmount = receiveAmount.add(billShopBalance.getMallBillingAmount());
			receiveAmount = receiveAmount.subtract(billShopBalance.getBalanceDeductAfterAmount());
			receiveAmount = receiveAmount.subtract(billShopBalance.getPaymentAmount());
		}
		return receiveAmount;
	}
	
	@RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("mallshop_balance/shopbalance_bill");
		return mav;
	}

	@RequestMapping("/shopbalance_tbMain")
	public String shopbalance_tbMain() {
		return "mallshop_balance/shopbalance_tbMain";
	}

//	@RequestMapping("/shop_Result")
//	public String shop_ResultList() {
//		return "mallshop_balance/shopbalance_ResultList";
//	}
//	
//	@RequestMapping("/shop_BackSectionSplit")
//	public String shop_BackSectionSplitList() {
//		return "mallshop_balance/shopbalance_BackSectionSplitList";
//	}
	
	  @RequestMapping(method = RequestMethod.GET ,value = "/shop_Result")
		public ModelAndView shop_ResultList(HttpServletRequest req) {
			ModelAndView mav = new ModelAndView();
			String warnPostUrl = req.getParameter("warnPostUrl");
			if(StringUtils.isNotBlank(warnPostUrl)){
				mav.addObject("warnPostUrl", warnPostUrl);
			}
			mav.setViewName("mallshop_balance/shopbalance_ResultList");
			return mav;
		}
	  
	  @RequestMapping(method = RequestMethod.GET ,value = "/shop_BackSectionSplit")
		public ModelAndView shop_BackSectionSplitList(HttpServletRequest req) {
			ModelAndView mav = new ModelAndView();
			String warnPostUrl = req.getParameter("warnPostUrl");
			if(StringUtils.isNotBlank(warnPostUrl)){
				mav.addObject("warnPostUrl", warnPostUrl);
			}
			mav.setViewName("mallshop_balance/shopbalance_BackSectionSplitList");
			return mav;
		}
	@RequestMapping("/shopbalance_dtlbill")
	public String shopbalance_dtlbill() {
		return "mallshop_balance/shopbalance_dtlbill";
	}
	
	@RequestMapping("/shopbalance_advancebill")
	public String shopbalance_advancebill() {
		return "/mallshop_balance/shopbalance_advancebill";
	}
	
	@RequestMapping("/shopbalance_vipbill")
	public String shopbalance_vipbill() {
		return "mallshop_balance/shopbalance_vipbill";
	}
	
	@RequestMapping("/shopbalance_deductbill")
	public String shopbalance_deductbill() {
		return "mallshop_balance/shopbalance_deductbill";
	}
	
	@RequestMapping("/shopbalance_diffbill")
	public String shopbalance_diffbill() {
		return "mallshop_balance/shopbalance_diffbill";
	}
	
	
	@RequestMapping("/shopbalance_batchadd_resultlist")
	public String shopbalance_batchadd_resultlist() {
		return "mallshop_balance/shopBalance_BatchAdd_ResultList";
	}
	
    @RequestMapping("/shopbalance_invoicebill")
   	public ModelAndView shopbalance_invoicebill(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("mallshop_balance/shopbalance_invoicebill");
    	String billNo = request.getParameter("billNo");
    	if(StringUtils.isNotEmpty(billNo)) {
    		mav.addObject("billNo", billNo);
    	}
    	return mav;
   	}
	
	@RequestMapping("/shopbalance_accountbill")
	public String shopbalance_accountbill() {
		return "mallshop_balance/shopbalance_accountbill";
	}

	/**
   	 * 保存
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping("/saveShopBalanceBill")
   	@ResponseBody
   	public BillShopBalance saveBalanceBalance(@ModelAttribute("billShopBalance")BillShopBalance billShopBalance, 
   			HttpServletRequest request) throws Exception {
   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
   		billShopBalance.setId(null);
   		billShopBalance.setCreateUser(loginUser.getUsername());
   		billShopBalance.setCreateTime(DateUtil.getCurrentDateTime());
   		billShopBalance.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
   		billShopBalance.setStatus(ShopMallEnums.BalanceStatus.NO_VERIFY.getRequestId());
   		
   		String diffInsertedList = StringUtils.isEmpty(request.getParameter("diffInserted")) ? "" : request
   				.getParameter("diffInserted");
   		JsonUtil<BillShopBalanceDiff> util = new JsonUtil<BillShopBalanceDiff>();
   		
   		List<BillShopBalanceDiff> lstDiffInserted = util.convertListWithTypeReference(diffInsertedList, request,
   				BillShopBalanceDiff.class);
   		
   		String deductInsertedList = StringUtils.isEmpty(request.getParameter("dudectInserted")) ? "" : request
   				.getParameter("dudectInserted");
   		JsonUtil<BillShopBalanceDeduct> deductUtil = new JsonUtil<BillShopBalanceDeduct>();
   		
   		List<BillShopBalanceDeduct> lstDeductInserted = deductUtil.convertListWithTypeReference(deductInsertedList, request,
   				BillShopBalanceDeduct.class);
   		
   		String brandInsertedList = StringUtils.isEmpty(request.getParameter("brandInserted")) ? "" : request
   				.getParameter("brandInserted");
   		JsonUtil<BillShopBalanceBrand> brandUtil = new JsonUtil<BillShopBalanceBrand>();
   		
   		List<BillShopBalanceBrand> lstBrandInserted = brandUtil.convertListWithTypeReference(brandInsertedList, request,
   				BillShopBalanceBrand.class);
   		
   		
   		String deductAfterInsertedList = StringUtils.isEmpty(request.getParameter("deductAfterInserted")) ? "" : request
   				.getParameter("deductAfterInserted");
   		JsonUtil<BillShopBalanceDeduct> deductAfterUtil = new JsonUtil<BillShopBalanceDeduct>();
   		
   		List<BillShopBalanceDeduct> lstDeductAfterInserted = deductUtil.convertListWithTypeReference(deductAfterInsertedList, request,
   				BillShopBalanceDeduct.class);
   		
   		BillShopBalance result = null;
   		try {
   			result = billShopBalanceManager.save(billShopBalance, lstDiffInserted, lstDeductInserted,lstBrandInserted,lstDeductAfterInserted);
   		} catch(ManagerException e) {
   			result = new BillShopBalance();
   			result.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
   			result.setErrorInfo(e.getMessage());
   		}
   		return result;
   	}
	
   	/**
   	 * 批量保存
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping("/batchAdd")
   	@ResponseBody
   	public List<Map<String, String>> batchAdd(@ModelAttribute("billShopBalance")BillShopBalance billShopBalance, HttpServletRequest request) 
   			throws Exception{
//   		var shopBalanceObj = companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";"+balanceType+";
//   		"+organNo+";"+bsgroupsNo+";"+mallNo+";"+payType+";";
		
//   		String[] shopBalances = strshopBalanceObjs.split(";");
//   		BillShopBalance billShopBalance = new BillShopBalance();
   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
   		
//   		String companyNo = shopBalances[0];
//		String organNo = shopBalances[6];
//		String bsgroupsNo = null;
//		 if(shopBalances[7] != null && shopBalances[7].length() > 0) {
//			 bsgroupsNo= shopBalances[7]; 
//		 }
//		String mallNo = null;
//		 if(shopBalances[8] != null && shopBalances[8].length() > 0) {
//			 mallNo= shopBalances[8]; 
//		 }
//		 String payType =null;
//		 if(shopBalances[9] != null && shopBalances[9].length() > 0) {
//			 payType= shopBalances[9]; 
//		 }
		 
//		 根据管理城市获取经营城市
//		 Map<String, Object> organParams = new HashMap<String, Object>();
//		 organParams.put("parentNo", organNo);
//		 organParams.put("organLevel", 2);
//		 List<Organ> organList=  organManager.findByBiz(null, organParams);
//		 if(organList != null && organList.size() > 0) {
//				for(Organ organData : organList) {
//					shop.setOrganNo(organData.getOrganNo());  //一个管理城市对于多个经营城市
//				}
//		 }
		
		Map<String, Object> params = new HashMap<String, Object>();
		 params.put("companyNo", billShopBalance.getCompanyNo());
		 params.put("parentOrganNo", billShopBalance.getOrganNo());//sql特殊处理   根据管理城市来获取经营城市
		 params.put("bsgroupsNo", billShopBalance.getBsgroupsNo());
		 params.put("mallNo", billShopBalance.getMallNo());
//		 params.put("payType", payType);
//		 String[] shopnos = billShopBalance.getShopNo().split(",");//{"CA01BS","CA02BS","CA01BL"};
		if(billShopBalance.getShopNo() != null && !"".equals(billShopBalance.getShopNo())){
			params.put("shopNos", Arrays.asList(billShopBalance.getShopNo().split(",")));
		}
		 List<Shop> shopList= shopManager.findByBiz(null, params);
		 List<BillShopBalance> billShopBalanceList  = new ArrayList<BillShopBalance>();
		 BillShopBalance bill = null;
		 if(shopList != null && shopList.size() > 0) {
			 for(Shop shop : shopList) {
				 bill = new BillShopBalance();
				 bill.setCompanyNo(billShopBalance.getCompanyNo());
				 bill.setCompanyName(billShopBalance.getCompanyName());
				 bill.setMonth(billShopBalance.getMonth());
				 bill.setShopNo(shop.getShopNo());
				 bill.setShortName(shop.getShortName());
				 
				 bill.setId(null);
				 bill.setCreateUser(loginUser.getUsername());
				 bill.setCreateTime(DateUtil.getCurrentDateTime());
				 bill.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
				 bill.setStatus(ShopMallEnums.BalanceStatus.NO_VERIFY.getRequestId());
				 bill.setBalanceType(billShopBalance.getBalanceType());
				 billShopBalanceList.add(bill);
			 }
		 }
		List<Map<String, String>> mapResult = billShopBalanceManager.batchAdd(billShopBalanceList);
   		return mapResult;
   	}
   	/**
   	 * 获取实际扣费金额  票前费用合计
   	 * @param billShopBalance
   	 * @return
   	 * @throws ServiceException
   	 */
   	@RequestMapping("/getBalanceDeductAmount")
   	@ResponseBody
   	protected BillShopBalance getBalanceDeductAmount(@RequestParam("shopBalanceObj")String strshopBalanceObjs,BillShopBalance billShopBalance) throws Exception {
   		 String[] shopBalances = strshopBalanceObjs.split(";");
         String companyNo = shopBalances[0];
	   	 String shopNo = shopBalances[1];
	   	 String month = shopBalances[2];
	   	 String balanceNo = shopBalances[3];
		 billShopBalance.setBalanceNo(balanceNo);
		 billShopBalance.setCompanyNo(companyNo);
		 billShopBalance.setShopNo(shopNo);
		 billShopBalance.setMonth(month);
		 BigDecimal balanceDeductAmount=billShopBalanceManager.getBalanceDeductAmount(billShopBalance);
		 billShopBalance.setBalanceDeductAmount(balanceDeductAmount);
		 
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("shopNo", shopNo);
		 params.put("month", month);
		 params.put("balanceNo", balanceNo);
		 List<BillShopBalance> billShopBalanceList= billShopBalanceManager.findByBiz(billShopBalance, params);
			
		 billShopBalance.setId(billShopBalanceList.get(0).getId());
		 billShopBalance.setSystemSalesAmount(billShopBalanceList.get(0).getSystemSalesAmount());
		 billShopBalance.setBalanceStartDate(billShopBalanceList.get(0).getBalanceStartDate());
		 billShopBalance.setBalanceEndDate(billShopBalanceList.get(0).getBalanceEndDate());
		 billShopBalanceManager.getNumDataCalc(billShopBalance);
		 billShopBalanceManager.modifyById(billShopBalance);
		 
   		return billShopBalance;
   	}
   	
   	/**
   	 * 获取结算差异金额  结算差异金额  tab页保存后计算该方法
   	 * @param strshopBalanceObjs
   	 * @param billShopBalance
   	 * @return
   	 * @throws Exception
   	 */
   	@RequestMapping("/getBalanceDiffAmountBill")
   	@ResponseBody
   	protected BillShopBalance getBalanceDiffAmountBill(@RequestParam("shopBalanceObj")String strshopBalanceObjs,BillShopBalance billShopBalance) throws Exception {
		String[] shopBalances = strshopBalanceObjs.split(";");
		String companyNo = shopBalances[0];
		String shopNo = shopBalances[1];
		String month = shopBalances[2];
		String balanceNo = shopBalances[3];
		billShopBalance.setBalanceNo(balanceNo);
		billShopBalance.setCompanyNo(companyNo);
		billShopBalance.setShopNo(shopNo);
		billShopBalance.setMonth(month);
		BigDecimal balanceDiffAmount = billShopBalanceManager.getBalanceDiffAmount(billShopBalance);
		billShopBalance.setBalanceDiffAmount(balanceDiffAmount);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", shopNo);
		params.put("month", month);
		params.put("balanceNo", balanceNo);
		List<BillShopBalance> billShopBalanceList = billShopBalanceManager.findByBiz(billShopBalance, params);
		if (!CollectionUtils.isEmpty(billShopBalanceList)) {
			billShopBalance.setId(billShopBalanceList.get(0).getId());
			billShopBalance.setSystemSalesAmount(billShopBalanceList.get(0).getSystemSalesAmount());
			billShopBalance.setBalanceStartDate(billShopBalanceList.get(0).getBalanceStartDate());
			billShopBalance.setBalanceEndDate(billShopBalanceList.get(0).getBalanceEndDate());
			billShopBalanceManager.getNumDataCalc(billShopBalance);
			billShopBalanceManager.modifyById(billShopBalance);
		}
		return billShopBalance;
   	}
   	
   	/**
   	 * 获取结算差异金额合计   结算差异金额
   	 * @param billShopBalance
   	 * @return
   	 * @throws ServiceException
   	 */
   	@RequestMapping("/getBalanceDiffAmount")
   	@ResponseBody
   	protected BigDecimal getBalanceDiffAmount(@RequestParam("shopBalanceObj")String strshopBalanceObjs,BillShopBalance billShopBalance) throws Exception {
   		return billShopBalanceManager.getBalanceDiffAmount(billShopBalance);
   	}
   	
   	/**
   	 * 删除
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value = "/deleteShopBalance")
   	@ResponseBody
   	public Integer remove(@RequestParam("idList")String strIds,HttpServletRequest request) throws Exception {
   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
   		String createUser= loginUser.getUsername();
   		Date createTime = DateUtil.getCurrentDateTime();
   		String[] ids = strIds.split(";");
   		return  billShopBalanceManager.remove(ids,createUser,createTime);
   	} 
   	
   	@RequestMapping(value = "/getPaymentMethodSum")
	@ResponseBody
	public  Map<String, Object> getPaymentMethodSum(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billShopBalanceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalanceDaysaleSum> list = this.billShopBalanceManager.findPaymentMethodSum(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list == null ? new ArrayList<Object>() : list);
		
		BigDecimal sumAmount = null;
        sumAmount =  billShopBalanceManager.getSumAmount(params);
		// 组装footer
		if (list.size() >= 0) {
			BillShopBalanceDaysaleSum billShopBalanceDaysaleSum = new BillShopBalanceDaysaleSum();
			billShopBalanceDaysaleSum.setPayName("合计");
			billShopBalanceDaysaleSum.setAmount(sumAmount);
			List<BillShopBalanceDaysaleSum> billShopBalanceDaysaleSumTotal = new ArrayList<BillShopBalanceDaysaleSum>();
			billShopBalanceDaysaleSumTotal.add(billShopBalanceDaysaleSum);
			obj.put("footer", billShopBalanceDaysaleSumTotal);
		}
		return obj;
	}
   	
	@RequestMapping(value = "/getBillShopBalance.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billShopBalanceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalance> list = this.billShopBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		
/*		if(null != list && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if(StringUtils.isNotEmpty(list.get(i).getShopNo())){
					map.put("shopNo", list.get(i).getShopNo());
					InvoiceRuleSet invoiceRuleSet = invoiceRuleSetManager.selectInvoiceNameByShopNo(map);
					
					if(null != invoiceRuleSet){
						list.get(i).setInvoiceName(invoiceRuleSet.getInvoiceName());
					}
				}
			}
		}*/
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
   	
	/**
	 * 通过店铺和结算单中的终止结算日期,查询是否已生成下期结算单
	 * @param request HttpServletRequest
	 * @return 是否已生成下期结算单的标志
	 * @throws ManagerException 
	 */
	@RequestMapping("/hasNextBillBalance1")
 	public ResponseEntity<String> hasNextBillBalance1(HttpServletRequest request) throws ManagerException {
 		String strFlag = "success";
 		try {
 			String shopNo = request.getParameter("shopNo");
 			String balanceEndDate = request.getParameter("balanceEndDate");
 			String month = request.getParameter("month");
 			if(StringUtils.isEmpty(balanceEndDate)) {
 				Map<String, Object> balanceDateParams = new HashMap<String, Object>();
 				balanceDateParams.put("shopNo", shopNo);
 				balanceDateParams.put("month", month);
 				List<ShopBalanceDate> list = shopBalanceDateManager.findByBiz(null, balanceDateParams);
 				if(list != null && list.size() > 0) {
 					balanceEndDate = DateUtil.format1(list.get(0).getBalanceEndDate());
 				}
 			}
 			if(StringUtils.isNotEmpty(shopNo) && StringUtils.isNotEmpty(balanceEndDate)) { 				
 				Map<String, Object> params = new HashMap<String, Object>();
 				params.put("shopNo", shopNo);
 				params.put("balanceEndDate", balanceEndDate);
 				int iCount = billShopBalanceManager.hasNextBalanceDate(params);
 				if(iCount > 0) {
 					strFlag = "exist";
 				}
 			}
		} catch(Exception e) {
			strFlag = "exist";
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
 		return new ResponseEntity<String>(strFlag,HttpStatus.OK);
 	}
 	
	/**
	 * 通过店铺和结算单中的终止结算日期,查询是否已生成下期结算单
	 * @param request HttpServletRequest
	 * @return 是否已生成下期结算单的标志
	 * @throws ManagerException 
	 */
 	public  String   hasNextBillBalance(HttpServletRequest request) throws ManagerException {
 		String strFlag = "success";
 		try {
 			String shopNo = request.getParameter("shopNo");
 			String balanceEndDate = request.getParameter("balanceEndDate");
 			String month = request.getParameter("month");
 			if(StringUtils.isEmpty(balanceEndDate)) {
 				Map<String, Object> balanceDateParams = new HashMap<String, Object>();
 				balanceDateParams.put("shopNo", shopNo);
 				balanceDateParams.put("month", month);
 				List<ShopBalanceDate> list = shopBalanceDateManager.findByBiz(null, balanceDateParams);
 				if(list != null && list.size() > 0) {
 					balanceEndDate = DateUtil.format1(list.get(0).getBalanceEndDate());
 				}
 			}
 			if(StringUtils.isNotEmpty(shopNo) && StringUtils.isNotEmpty(balanceEndDate)) { 				
 				Map<String, Object> params = new HashMap<String, Object>();
 				params.put("shopNo", shopNo);
 				params.put("balanceEndDate", balanceEndDate);
 				int iCount = billShopBalanceManager.hasNextBalanceDate(params);
 				if(iCount > 0) {
 					strFlag = "exist";
 				}
 			}
		} catch(Exception e) {
			strFlag = "exist";
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
 		return strFlag;
 	}
	
	/**
	 * 通过系统参数控制，结算单审核后是否可以修改  按公司
	 * @param request HttpServletRequest
	 * @return 是否已生成下期结算单的标志
	 * @throws ManagerException 
	 */
	@RequestMapping("/hasNextBillBalance")
 	public ResponseEntity<String> checkSystemUpdateSet(HttpServletRequest request) throws ManagerException {
 		String strFlag = "success";
 		try {
 			String shopNo = request.getParameter("shopNo");
 			String companyNo = request.getParameter("companyNo");
 			String status = request.getParameter("status");// '单据状态(1-制单、2-确认、3-作废、4、已开票)',
 			
// 			处理  是否生成下期单据不可以修改,默认控制，默认
 			Map<String, Object> checkNextBillParams = new HashMap<String, Object>();
 			checkNextBillParams.put("businessOrganNo", companyNo);
 			checkNextBillParams.put("paramCode", "MS_BalanceCheck_NextBill");
 			checkNextBillParams.put("status", 1);
			String checkNextvalue = billShopBalanceManager.getSystemParamSetValue(checkNextBillParams);
			int checkNextvalueSet = 0;
			if(checkNextvalue != null && !"".equals(checkNextvalue)){
				checkNextvalueSet= Integer.valueOf(checkNextvalue).intValue();
			}
			
 			Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
			systemParamSetParams.put("businessOrganNo", companyNo);
			systemParamSetParams.put("paramCode", "MS_BalanceCheck_StatusUp");
			systemParamSetParams.put("status", 1);
			String statusUpValue = billShopBalanceManager.getSystemParamSetValue(systemParamSetParams);
			int statusUpValueSet = 2;
			if(statusUpValue != null && !"".equals(statusUpValue)){
				statusUpValueSet= Integer.valueOf(statusUpValue).intValue();
			}
			
			int statusBill= Integer.valueOf(status).intValue();
//			0-不控制     1-控制状态:制单      2-控制状态：确认      4-控制状态：开票申请    
//			首先检查单据是否生成下期
			if(checkNextvalueSet == 0){
			   strFlag = hasNextBillBalance(request);
			   if("success".equals(strFlag)){
				if(0 == statusUpValueSet){//不控制
					strFlag = "success";
				} if(0 != statusUpValueSet){
						if(statusUpValueSet <= statusBill){
							//控制 不可修改
							strFlag = "statusexist"+status+"/"+statusUpValueSet;
						}
//						else {
//							//没有配置参数，按2
//							strFlag = hasNextBillBalance(request);
//						}
				}
			}
			}else{
//				strFlag = "success";
				if(0 == statusUpValueSet){//不控制
					strFlag = "success";
				} if(0 != statusUpValueSet){
						if(statusUpValueSet <= statusBill){
							//控制 不可修改
							strFlag = "statusexist"+status+"/"+statusUpValueSet;
						}
//						else {
//							//没有配置参数，按2
////							strFlag = hasNextBillBalance(request);
//						}
				}
			}
		} catch(Exception e) {
			strFlag = "exist";
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
 		return new ResponseEntity<String>(strFlag,HttpStatus.OK);
 	}
	
	/**
	 * 新增页面：修改操作
	 * @param model 待确认的实体对象
	 * @param request HttpServletRequest
	 * @return BillShopBalance
	 */
	@RequestMapping(value = "/updateBill")
	@ResponseBody
	public BillShopBalance updateBill(BillShopBalance billShopBalance, HttpServletRequest request) 
		throws ManagerException {
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		try {
			// 获取票前费用页签修改的数据
			String deduceDataInserted = StringUtils.isEmpty(request.getParameter("deduceDataInserted")) ? "" 
					: request.getParameter("deduceDataInserted");
			String deduceDataUpdated = StringUtils.isEmpty(request.getParameter("deduceDataUpdated")) ? "" 
					: request.getParameter("deduceDataUpdated");
			String deduceDataDeleted = StringUtils.isEmpty(request.getParameter("deduceDataDeleted")) ? "" 
					: request.getParameter("deduceDataDeleted");
			JsonUtil<BillShopBalanceDeduct> deduceUtil = new JsonUtil<BillShopBalanceDeduct>();
			List<BillShopBalanceDeduct> lstDeductInsert = deduceUtil.convertListWithTypeReference(deduceDataInserted, 
					request, BillShopBalanceDeduct.class);
			List<BillShopBalanceDeduct> lstDeductUpdate = deduceUtil.convertListWithTypeReference(deduceDataUpdated, 
					request, BillShopBalanceDeduct.class);
			List<BillShopBalanceDeduct> lstDeductDel = deduceUtil.convertListWithTypeReference(deduceDataDeleted, 
					request, BillShopBalanceDeduct.class);
			Map<CommonOperatorEnum, List<BillShopBalanceDeduct>> deductDataMap = new HashMap<CommonOperatorEnum, List<BillShopBalanceDeduct>>();
			deductDataMap.put(CommonOperatorEnum.INSERTED, lstDeductInsert);
			deductDataMap.put(CommonOperatorEnum.UPDATED, lstDeductUpdate);
			deductDataMap.put(CommonOperatorEnum.DELETED, lstDeductDel);
			
			// 获取结算差异页签修改的数据
			String insertedList = StringUtils.isEmpty(request.getParameter("balanceDiffDataInserted")) 
					? "" : request.getParameter("balanceDiffDataInserted");
			String upadtedList = StringUtils.isEmpty(request.getParameter("balanceDiffDataUpdated")) 
					? "" : request.getParameter("balanceDiffDataUpdated");
			String deletedList = StringUtils.isEmpty(request.getParameter("balanceDiffDataDeleted")) 
					? "" : request.getParameter("balanceDiffDataDeleted");
			JsonUtil<BillShopBalanceDiff> diffUtil = new JsonUtil<BillShopBalanceDiff>();
			List<BillShopBalanceDiff> lstInsert = diffUtil.convertListWithTypeReference(insertedList, 
					request, BillShopBalanceDiff.class);
			List<BillShopBalanceDiff> lstUpdate = diffUtil.convertListWithTypeReference(upadtedList, 
					request, BillShopBalanceDiff.class);
			List<BillShopBalanceDiff> lstDelete = diffUtil.convertListWithTypeReference(deletedList, 
					request, BillShopBalanceDiff.class);
			Map<CommonOperatorEnum, List<BillShopBalanceDiff>> diffDataMap = new HashMap<CommonOperatorEnum, List<BillShopBalanceDiff>>();
			diffDataMap.put(CommonOperatorEnum.INSERTED, lstInsert);
			diffDataMap.put(CommonOperatorEnum.UPDATED, lstUpdate);
			diffDataMap.put(CommonOperatorEnum.DELETED, lstDelete);
			
			// 获取多品牌结算页签修改的数据
			String brandDataInserted = StringUtils.isEmpty(request.getParameter("brandDataInserted")) ? "" 
					: request.getParameter("brandDataInserted");
			String brandDataUpdated = StringUtils.isEmpty(request.getParameter("brandDataUpdated")) ? "" 
					: request.getParameter("brandDataUpdated");
			String brandDataDeleted = StringUtils.isEmpty(request.getParameter("brandDataDeleted")) ? "" 
					: request.getParameter("brandDataDeleted");
			JsonUtil<BillShopBalanceBrand> brandUtil = new JsonUtil<BillShopBalanceBrand>();
			List<BillShopBalanceBrand> lstBrandInsert = brandUtil.convertListWithTypeReference(brandDataInserted, 
					request, BillShopBalanceBrand.class);
			List<BillShopBalanceBrand> lstBrandUpdate = brandUtil.convertListWithTypeReference(brandDataUpdated, 
					request, BillShopBalanceBrand.class);
			List<BillShopBalanceBrand> lstBrandDel = brandUtil.convertListWithTypeReference(brandDataDeleted, 
					request, BillShopBalanceBrand.class);
			Map<CommonOperatorEnum, List<BillShopBalanceBrand>> brandDataMap = new HashMap<CommonOperatorEnum, List<BillShopBalanceBrand>>();
			brandDataMap.put(CommonOperatorEnum.INSERTED, lstBrandInsert);
			brandDataMap.put(CommonOperatorEnum.UPDATED, lstBrandUpdate);
			brandDataMap.put(CommonOperatorEnum.DELETED, lstBrandDel);
				
			
			// 获取票后费用页签修改的数据
			String deduceAfterDataInserted = StringUtils.isEmpty(request.getParameter("deduceAfterDataInserted")) ? "" 
					: request.getParameter("deduceAfterDataInserted");
			String deduceAfterDataUpdated = StringUtils.isEmpty(request.getParameter("deduceAfterDataUpdated")) ? "" 
					: request.getParameter("deduceAfterDataUpdated");
			String deduceAfterDataDeleted = StringUtils.isEmpty(request.getParameter("deduceAfterDataDeleted")) ? "" 
					: request.getParameter("deduceAfterDataDeleted");
			JsonUtil<BillShopBalanceDeduct> deduceAfterUtil = new JsonUtil<BillShopBalanceDeduct>();
			List<BillShopBalanceDeduct> lstDeductAfterInsert = deduceAfterUtil.convertListWithTypeReference(deduceAfterDataInserted, 
					request, BillShopBalanceDeduct.class);
			List<BillShopBalanceDeduct> lstDeductAfterUpdate = deduceAfterUtil.convertListWithTypeReference(deduceAfterDataUpdated, 
					request, BillShopBalanceDeduct.class);
			List<BillShopBalanceDeduct> lstDeductAfterDel = deduceAfterUtil.convertListWithTypeReference(deduceAfterDataDeleted, 
					request, BillShopBalanceDeduct.class);
			Map<CommonOperatorEnum, List<BillShopBalanceDeduct>> deductAfterDataMap = new HashMap<CommonOperatorEnum, List<BillShopBalanceDeduct>>();
			deductAfterDataMap.put(CommonOperatorEnum.INSERTED, lstDeductAfterInsert);
			deductAfterDataMap.put(CommonOperatorEnum.UPDATED, lstDeductAfterUpdate);
			deductAfterDataMap.put(CommonOperatorEnum.DELETED, lstDeductAfterDel);
			
			// 获取大类页签修改的数据
						String balanceCategoryDataInserted = StringUtils.isEmpty(request.getParameter("balanceCategoryDataInserted")) ? "" 
								: request.getParameter("balanceCategoryDataInserted");
						String balanceCategoryDataUpdated = StringUtils.isEmpty(request.getParameter("balanceCategoryDataUpdated")) ? "" 
								: request.getParameter("balanceCategoryDataUpdated");
						String balanceCategoryDataDeleted = StringUtils.isEmpty(request.getParameter("balanceCategoryDataDeleted")) ? "" 
								: request.getParameter("balanceCategoryDataDeleted");
						JsonUtil<BillShopBalanceCateSum> categoryUtil = new JsonUtil<BillShopBalanceCateSum>();
						List<BillShopBalanceCateSum> lstCategoryInsert = categoryUtil.convertListWithTypeReference(balanceCategoryDataInserted, 
								request, BillShopBalanceCateSum.class);
						List<BillShopBalanceCateSum> lstCategoryUpdate = categoryUtil.convertListWithTypeReference(balanceCategoryDataUpdated, 
								request, BillShopBalanceCateSum.class);
						List<BillShopBalanceCateSum> lstCategoryDel = categoryUtil.convertListWithTypeReference(balanceCategoryDataDeleted, 
								request, BillShopBalanceCateSum.class);
						Map<CommonOperatorEnum, List<BillShopBalanceCateSum>> categoryDataMap = new HashMap<CommonOperatorEnum, List<BillShopBalanceCateSum>>();
						categoryDataMap.put(CommonOperatorEnum.INSERTED, lstCategoryInsert);
						categoryDataMap.put(CommonOperatorEnum.UPDATED, lstCategoryUpdate);
						categoryDataMap.put(CommonOperatorEnum.DELETED, lstCategoryDel);
						
			BillShopBalance result = null;
			billShopBalance.setUpdateUser(loginUser.getLoginName());
			billShopBalance.setUpdateTime(DateUtil.getCurrentDateTime());
			String month = request.getParameter("month");
			billShopBalance.setMonth(month);  
			BigDecimal mallBillingAmount = new BigDecimal("0");
			BigDecimal mallNumberAmount =  new BigDecimal("0");
			String mallBillingAmountStr =request.getParameter("mallBillingAmount");
			String mallNumberAmountStr = request.getParameter("mallNumberAmount");
//			if((mallBillingAmount != null) && (mallNumberAmount != null)) {
			if(StringUtils.isNotEmpty(mallBillingAmountStr) && StringUtils.isNotEmpty(mallNumberAmountStr)) { 	
				 mallBillingAmount =new BigDecimal(mallBillingAmountStr); 
				 mallNumberAmount = new BigDecimal(mallNumberAmountStr);
			}
			billShopBalance.setMallBillingAmount(mallBillingAmount);
			billShopBalance.setMallNumberAmount(mallNumberAmount);
			billShopBalance.setStatus(null);
	   		try {
	   			result = billShopBalanceManager.updateBill(billShopBalance, deductDataMap, diffDataMap,brandDataMap,deductAfterDataMap,categoryDataMap);
	   		} catch(ManagerException e) {
	   			result = billShopBalanceManager.findById(billShopBalance);
	   			result.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
	   			result.setErrorInfo(e.getMessage());
	   			logger.error(e.getMessage(),e);
	   		}
	   		return result;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 新增页面：审核/反审核操作
	 * @param model 待确认的实体对象
	 * @param request HttpServletRequest
	 * @return BillShopBalance
	 */
	@RequestMapping(value = "/confirm")
	@ResponseBody
	public BillShopBalance confirm(BillShopBalance billShopBalance, HttpServletRequest request) 
		throws ManagerException {
		int iCount = billShopBalanceManager.confirm(billShopBalance);
		if(iCount > 0) {
			billShopBalance = billShopBalanceManager.findById(billShopBalance);
		}
		return billShopBalance;
	}
	
	/**
	 * 列表页面：审核/反审核操作
	 * @param model 待确认的实体对象
	 * @param request HttpServletRequest
	 * @return BillShopBalance
	 */
	@RequestMapping(value = "/batchConfirm")
	public ResponseEntity<Map<String, Boolean>> batchConfirm(HttpServletRequest request) 
		throws ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		try {
			String confirmList = StringUtils.isEmpty(request.getParameter("confirmList")) ? "" : request.getParameter("confirmList");
			JsonUtil<BillShopBalance> util = new JsonUtil<BillShopBalance>();
			List<BillShopBalance> list = util.convertListWithTypeReference(confirmList, request, BillShopBalance.class);
			if(list != null && list.size() > 0) {
				billShopBalanceManager.batchConfirm(list);
			}
			flag.put("success", true);
			return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 进入维护差异回款页面
	 * @param request
	 * @return ModelAndView
	 * @throws ManagerException 异常
	 */
	@RequestMapping("/selectShopBalanceDiffBack")
	public ModelAndView selectShopBalanceDiffBack(HttpServletRequest request) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("diffBillNo", request.getParameter("diffBillNo"));
		obj.addObject("balanceNo", request.getParameter("balanceNo"));
		obj.addObject("diffDtlId", request.getParameter("diffDtlId"));
		obj.setViewName("mallshop_balance/selectShopBalanceDiffBack");
		return obj;
	}
	
	
	/**
	 * 导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/do_dtl_export")
	public void doDtlExport(HttpServletRequest request, Model model,
			HttpServletResponse response) throws ManagerException {
		try {
			SimpleDateFormat simdf=new SimpleDateFormat("yyyyMMdd");//yyyy-MM-dd HH:mm:ss
			Map<String,Object> params = builderParams(request, model);
			String fileName = (String)params.get( "fileName")+" "+simdf.format(new Date());
			String dtlShowType = request.getParameter("dtlShowType");
			ObjectMapper mapper = new ObjectMapper();
			List<MutliSheetExportVo> lstSheetVo = new ArrayList<MutliSheetExportVo>();
	
			//处理表头数据
			String headerFormData = request.getParameter("headerFormData");
			List<HeaderFormDataVo> lstHeaderFormDataVo = null;
			if(StringUtils.isNotEmpty(headerFormData)) {
				headerFormData = headerFormData.replace( "[" ,"" );
				headerFormData = headerFormData.replace( "]" ,"" );
				headerFormData= "[" +headerFormData+"]" ;
				lstHeaderFormDataVo = mapper.readValue(headerFormData, new TypeReference<List<HeaderFormDataVo>>(){});
			}
			
			// 处理大类汇总数据
			String saleDtlExportColumns = (String) params.get("saleDtlExportColumns");
			if(StringUtils.isNotEmpty(saleDtlExportColumns)) {
				MutliSheetExportVo exportVo = new MutliSheetExportVo();
				// 设置不包含表头
				exportVo.setContainHeaderForm(false);
				exportVo.setSheetName(params.get("dtlDataGridName")+"");
				saleDtlExportColumns = saleDtlExportColumns.replace( "[" ,"" );
				saleDtlExportColumns = saleDtlExportColumns.replace( "]" ,"" );
				saleDtlExportColumns= "[" +saleDtlExportColumns+"]" ;
	        	//处理销售明细列表数据
	        	List<Map> columnsList = mapper.readValue(saleDtlExportColumns, new TypeReference<List<Map>>(){});
	        	exportVo.setColumnsMapList(columnsList);
	        	AbstactBillDtlHandler handler = ShopMallDtlShowTypeEnum.getHandler(dtlShowType);
	        	List<Map> dataMapList = null;
	        	if(ShopMallDtlShowTypeEnum.CATEGORY.getShowType().equals(dtlShowType)) {
	        		dataMapList = handler.buildManager(billShopBalanceCateSumManager)
	        			.buildColumnsList(columnsList)
	        			.buildParams(params)
	        			.buildBillShopBalanceManager(billShopBalanceManager)
	        			.bulidExportData();
	        	} else if(ShopMallDtlShowTypeEnum.BALANCE_DTL.getShowType().equals(dtlShowType)) {
	        		dataMapList = handler.buildManager(billShopSaleOrderManager)
		        			.buildColumnsList(columnsList)
		        			.buildParams(params)
		        			.buildBillShopBalanceManager(billShopBalanceManager)
		        			.bulidExportData();
	        	} else if(ShopMallDtlShowTypeEnum.PROMOTIONS.getShowType().endsWith(dtlShowType)) {
	        		dataMapList = handler.buildManager(billShopBalanceProSumManager)
	        				.buildColumnsList(columnsList)
	        				.buildParams(params)
	        				.bulidExportData();
	        	} else if(ShopMallDtlShowTypeEnum.PAYMENT_METHOD.getShowType().endsWith(dtlShowType)) {
	        		dataMapList = handler.buildManager(billShopBalanceManager)
	        				.buildColumnsList(columnsList)
	        				.buildParams(params)
	        				.bulidExportData();
	        	}  else if(ShopMallDtlShowTypeEnum.BILLING_CODE.getShowType().endsWith(dtlShowType)) {
	        		dataMapList = handler.buildManager(billShopBalanceCodeSumManager)
	        				.buildColumnsList(columnsList)
	        				.buildParams(params)
	        				.bulidExportData();
	        	}
	        	
	        	exportVo.setDataMapList(dataMapList);
	            lstSheetVo.add(exportVo);
			}
			
			// 处理结算差异页签
			String diffExportColumns = (String) params.get("diffExportColumns");
			MutliSheetExportVo diffExportVo = null;
			if(StringUtils.isNotEmpty(diffExportColumns)) {
				diffExportVo = new MutliSheetExportVo();
				diffExportVo.setContainHeaderForm(true);
				diffExportVo.setLstHeaderFormDataVo(lstHeaderFormDataVo);
				diffExportVo.setSheetName(params.get("diffFileName")+"");
	        	diffExportColumns=diffExportColumns.replace( "[" ,"" );
	        	diffExportColumns=diffExportColumns.replace( "]" ,"" );
	        	diffExportColumns= "[" +diffExportColumns+"]" ;
	        	//字段名列表
	        	List<Map> columnsList = mapper.readValue(diffExportColumns, new TypeReference<List<Map>>(){});
	        	diffExportVo.setColumnsMapList(columnsList);
	        	
	        	AbstactBillDtlHandler handler = new BillShopDiffHandler();
	        	List<Map> dataMapList = handler.buildManager(billShopBalanceDiffManager)
	        		.buildColumnsList(columnsList)
	        		.buildParams(params)
	        		.bulidExportData();
	        	diffExportVo.setDataMapList(dataMapList);
	            // 处理票前费用页签
	            String deductExportColumns = (String) params.get("deductExportColumns");
	            if(StringUtils.isNotEmpty(deductExportColumns)) {
	            	MutliSheetExportVo exportVo = new MutliSheetExportVo();
	            	exportVo.setLstHeaderFormDataVo(lstHeaderFormDataVo);
	            	exportVo.setSheetName(params.get("deductFileName")+"");
	            	deductExportColumns = deductExportColumns.replace( "[" ,"" );
	            	deductExportColumns = deductExportColumns.replace( "]" ,"" );
	            	deductExportColumns = "[" +deductExportColumns+"]";
	            	//字段名列表
	            	List<Map> deductColumnsList = mapper.readValue(deductExportColumns, new TypeReference<List<Map>>(){});
	            	exportVo.setColumnsMapList(deductColumnsList);
	            	
	            	AbstactBillDtlHandler deductHandler = new BillShopDeductHandler();
	            	List<Map> deductDataMapList = deductHandler.buildManager(billShopBalanceDeductManager)
	            			.buildColumnsList(deductColumnsList)
	            			.buildParams(params)
	            			.bulidExportData();
	            	exportVo.setDataMapList(deductDataMapList);
	            	List<MutliSheetExportVo> children = new ArrayList<MutliSheetExportVo>(1);
	            	children.add(exportVo);
	            	diffExportVo.setChildren(children);
	            }
	            lstSheetVo.add(diffExportVo);
			}
			
			HSSFExportComplex.multiSheetExportData(fileName, lstSheetVo, response, null);
		} catch(Exception e) {
			logger.info(">>>>>>门店结算单导出报错.",e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	

	/**
	 * 批量导出明细
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/do_dtl_batchexport")
	public void doDtlBatchExport(HttpServletRequest request, Model model,
			HttpServletResponse response) throws ManagerException {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat simdf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat simdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String shopBalanceDataGrid=request.getParameter("shopBalanceDataGrid");
			List<Object> billShopBalanceList = convertListWithTypeReference(shopBalanceDataGrid, BillShopBalance.class);
			Map<String,Object> params = builderParams(request, model);
			String fileName = (String)params.get( "fileName")+""+sdf.format(new Date());
			String dtlShowType = request.getParameter("dtlShowType");
			ObjectMapper mapper = new ObjectMapper();
			List<MutliSheetExportVo> lstSheetVo = new ArrayList<MutliSheetExportVo>();
			for(Object list:billShopBalanceList){
				String balanceNo=((BillShopBalance)list).getBalanceNo();
				params.put("balanceNo",balanceNo);
				//处理表头数据
				String headerFormData = request.getParameter("headerFormData");
				List<HeaderFormDataVo> lstHeaderFormDataVo = null;
				if(StringUtils.isNotEmpty(headerFormData)) {
					headerFormData = headerFormData.replace( "[" ,"" );
					headerFormData = headerFormData.replace( "]" ,"" );
					headerFormData= "[" +headerFormData+"]" ;
					lstHeaderFormDataVo = mapper.readValue(headerFormData, new TypeReference<List<HeaderFormDataVo>>(){});
				}
				
				for(HeaderFormDataVo hv:lstHeaderFormDataVo){
					if(hv.getField().equals("balanceType")){
						if(((BillShopBalance)list).getBalanceTypeName() != null){
							hv.setValue(((BillShopBalance)list).getBalanceTypeName());
						}
					}else if(hv.getField().equals("billDate")){
						if(((BillShopBalance)list).getBillDate() != null){
							hv.setValue(simdf.format(((BillShopBalance)list).getBillDate()));
						}
					}else if(hv.getField().equals("billStatusName")){
						if(((BillShopBalance)list).getBillStatusName() != null){
							hv.setValue(((BillShopBalance)list).getBillStatusName());
						}
					}else if(hv.getField().equals("balanceNo")){
						if(((BillShopBalance)list).getBalanceNo() != null){
							hv.setValue(((BillShopBalance)list).getBalanceNo());
						}
					}else if(hv.getField().equals("shortName")){
						if(((BillShopBalance)list).getShortName() != null){
							hv.setValue(((BillShopBalance)list).getShortName());
						}
					}else if(hv.getField().equals("actualRateName")){
						if(((BillShopBalance)list).getActualRateName() != null){
							hv.setValue(((BillShopBalance)list).getActualRateName());
						}
					}else if(hv.getField().equals("contractRateName")){
						if(((BillShopBalance)list).getContractRateName() != null){
							hv.setValue(((BillShopBalance)list).getContractRateName());
						}
					}else if(hv.getField().equals("mallDeductAmount")){
						if(((BillShopBalance)list).getMallDeductAmount() != null){
							hv.setValue(((BillShopBalance)list).getMallDeductAmount().toString());
						}
					}else if(hv.getField().equals("companyName")){
						if(((BillShopBalance)list).getCompanyName() != null){
							hv.setValue(((BillShopBalance)list).getCompanyName());
						}
					}else if(hv.getField().equals("mallNumberAmount")){
						if(((BillShopBalance)list).getMallNumberAmount() != null){
							hv.setValue(((BillShopBalance)list).getMallNumberAmount().toString());
						}
					}else if(hv.getField().equals("systemSalesAmount")){
						if(((BillShopBalance)list).getSystemSalesAmount() != null){
							hv.setValue(((BillShopBalance)list).getSystemSalesAmount().toString());
						}
					}else if(hv.getField().equals("salesDiffamount")){
						if(((BillShopBalance)list).getSalesDiffamount() != null){
							hv.setValue(((BillShopBalance)list).getSalesDiffamount().toString());
						}
					}else if(hv.getField().equals("month")){
						if(((BillShopBalance)list).getMonth() != null){
							hv.setValue(((BillShopBalance)list).getMonth());
						}
					}else if(hv.getField().equals("mallBillingAmount")){
						if(((BillShopBalance)list).getMallBillingAmount() != null){
							hv.setValue(((BillShopBalance)list).getMallBillingAmount().toString());
						}
					}else if(hv.getField().equals("systemBillingAmount")){
						if(((BillShopBalance)list).getSystemBillingAmount() != null){
							hv.setValue(((BillShopBalance)list).getSystemBillingAmount().toString());
						}
					}else if(hv.getField().equals("balanceDiffAmount")){
						if(((BillShopBalance)list).getBalanceDiffAmount() != null){
							hv.setValue(((BillShopBalance)list).getBalanceDiffAmount().toString());
						}
					}else if(hv.getField().equals("startEndDate")){
						hv.setValue(sdf.format(((BillShopBalance)list).getBalanceStartDate())+"-"+sdf.format(((BillShopBalance)list).getBalanceEndDate()));
					}else if(hv.getField().equals("differenceAmount")){
						if(((BillShopBalance)list).getDifferenceAmount() != null){
							hv.setValue(((BillShopBalance)list).getDifferenceAmount().toString());
						}
					}else if(hv.getField().equals("estimateAmount")){
						if(((BillShopBalance)list).getEstimateAmount() != null){
							hv.setValue(((BillShopBalance)list).getEstimateAmount().toString());
						}
					}else if(hv.getField().equals("prepaymentAmount")){
						if(((BillShopBalance)list).getPrepaymentAmount() != null){
							hv.setValue(((BillShopBalance)list).getPrepaymentAmount().toString());
						}
					}else if(hv.getField().equals("balanceDesc")){
						if(((BillShopBalance)list).getBalanceDesc() != null){
							hv.setValue(((BillShopBalance)list).getBalanceDesc().toString());
						}
					}else if(hv.getField().equals("usedPrepaymentAmount")){
						if(((BillShopBalance)list).getUsedPrepaymentAmount() != null){
							hv.setValue(((BillShopBalance)list).getUsedPrepaymentAmount().toString());
						}
					}
				}
				
				// 处理结算差异页签
				String diffExportColumns = (String) params.get("diffExportColumns");
				MutliSheetExportVo diffExportVo = null;
				List<MutliSheetExportVo> children = new ArrayList<MutliSheetExportVo>(2);
				if(StringUtils.isNotEmpty(diffExportColumns)) {
					diffExportVo = new MutliSheetExportVo();
					diffExportVo.setContainHeaderForm(true);
					diffExportVo.setLstHeaderFormDataVo(lstHeaderFormDataVo);
					diffExportVo.setSheetName(params.get("diffFileName")+"");
		        	diffExportColumns=diffExportColumns.replace( "[" ,"" );
		        	diffExportColumns=diffExportColumns.replace( "]" ,"" );
		        	diffExportColumns= "[" +diffExportColumns+"]" ;
		        	//字段名列表
		        	List<Map> columnsList = mapper.readValue(diffExportColumns, new TypeReference<List<Map>>(){});
		        	diffExportVo.setColumnsMapList(columnsList);
		        	
		        	AbstactBillDtlHandler handler = new BillShopDiffHandler();
		        	List<Map> dataMapList = handler.buildManager(billShopBalanceDiffManager)
		        		.buildColumnsList(columnsList)
		        		.buildParams(params)
		        		.bulidExportData();
		        	diffExportVo.setDataMapList(dataMapList);
		            // 处理票前费用页签
		            String deductExportColumns = (String) params.get("deductExportColumns");
		            if(StringUtils.isNotEmpty(deductExportColumns)) {
		            	MutliSheetExportVo exportVo = new MutliSheetExportVo();
		            	exportVo.setLstHeaderFormDataVo(lstHeaderFormDataVo);
		            	exportVo.setSheetName(params.get("deductFileName")+"");
		            	deductExportColumns = deductExportColumns.replace( "[" ,"" );
		            	deductExportColumns = deductExportColumns.replace( "]" ,"" );
		            	deductExportColumns = "[" +deductExportColumns+"]";
		            	//字段名列表
		            	List<Map> deductColumnsList = mapper.readValue(deductExportColumns, new TypeReference<List<Map>>(){});
		            	exportVo.setColumnsMapList(deductColumnsList);
		            	
		            	AbstactBillDtlHandler deductHandler = new BillShopDeductHandler();
		            	List<Map> deductDataMapList = deductHandler.buildManager(billShopBalanceDeductManager)
		            			.buildColumnsList(deductColumnsList)
		            			.buildParams(params)
		            			.bulidExportData();
		            	exportVo.setDataMapList(deductDataMapList);
		            	children.add(exportVo);
		            }
		         // 处理活动汇总数据
					String saleDtlExportColumns = (String) params.get("saleDtlExportColumns");
					if(StringUtils.isNotEmpty(saleDtlExportColumns)) {
						MutliSheetExportVo exportVo = new MutliSheetExportVo();
						// 设置不包含表头
						exportVo.setSheetName(params.get("dtlDataGridName")+"");
						exportVo.setLstHeaderFormDataVo(lstHeaderFormDataVo);
						saleDtlExportColumns = saleDtlExportColumns.replace( "[" ,"" );
						saleDtlExportColumns = saleDtlExportColumns.replace( "]" ,"" );
						saleDtlExportColumns= "[" +saleDtlExportColumns+"]" ;
			        	//处理销售明细列表数据
			        	List<Map> promotionsColumnsList = mapper.readValue(saleDtlExportColumns, new TypeReference<List<Map>>(){});
			        	exportVo.setColumnsMapList(promotionsColumnsList);
			        	AbstactBillDtlHandler promotionsHandler = ShopMallDtlShowTypeEnum.getHandler(dtlShowType);
			        	List<Map> promotionsDataMapList = null;
			        	
			        	if(ShopMallDtlShowTypeEnum.PROMOTIONS.getShowType().endsWith(dtlShowType)) {
			        		promotionsDataMapList = promotionsHandler.buildManager(billShopBalanceProSumManager)
			        				.buildColumnsList(columnsList)
			        				.buildParams(params)
			        				.bulidExportData();
			        	}
			        	exportVo.setDataMapList(promotionsDataMapList);
		            	children.add(exportVo);
		            	diffExportVo.setChildren(children);
					}
				}
				lstSheetVo.add(diffExportVo);
			}
			 BalanceHSSFExportComplex.multiSheetExportData(fileName, lstSheetVo, response, null);
		} catch(Exception e) {
			logger.info(">>>>>>门店结算单导出明细报错.",e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	

	/**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private List<Object> convertListWithTypeReference(String rows, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(rows, new TypeReference<List<Map>>() {});
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
	
	/**
	 * 校验数据是否已平
	 * @param billShopBalance
	 * @param request
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/validateDataBalance")
	@ResponseBody
	public Map<String, String> validateDataBalance(@ModelAttribute("billShopBalance")BillShopBalance billShopBalance,
			HttpServletRequest request) throws ManagerException {
		BillShopBalance resultModel = billShopBalanceManager.findById(billShopBalance);
		if(resultModel == null) {
			return null;
		}
		
//		// 设置收款差异金额
//					BigDecimal thePaymentAmount = billBacksectionSplitDtlService.sumPaymentAmount(billShopBalance.getBalanceNo());
//					BillShopBalanceDeduct shopBalanceDeduct = null;
//					try {
//						shopBalanceDeduct = billShopBalanceManager.getCostDeductTypeAmount(billShopBalance,null);
//					} catch (ServiceException e) {
//						e.printStackTrace();
//					}
//					BigDecimal deductAmount  = new BigDecimal("0");
//					BigDecimal actualAmount  = new BigDecimal("0");
//					if(shopBalanceDeduct != null) {
//						deductAmount =shopBalanceDeduct.getDeductAmount(); 
//						actualAmount = shopBalanceDeduct.getActualAmount();
//					}
////					 收款差异=商场开票金额-票后账扣-回款金额
//					BigDecimal differenceAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), thePaymentAmount).subtract(actualAmount);
//					resultModel.setDifferenceAmount(differenceAmount);
//					
////					 应返款金额 =商场开票金额-票后账扣
//					BigDecimal returnedAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), actualAmount);
//					resultModel.setReturnedAmount(returnedAmount);
		
		Map<String, String> msgMap = billShopBalanceManager.validateDataBalance(resultModel);
		return msgMap;
	}
	
	/**
	 * 校验结算期以及查询是否存在预估结算单
	 * @param model
	 * @param request
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/validateAndGetEstimateBill")
	@ResponseBody
	public Map<String, Object> validateAndGetEstimateBill(@ModelAttribute("model")BillShopBalance model,
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 处理结算期
		ShopBalanceDate shopBalanceDate = shopBalanceDateManager.findBalanceDate(model.getShopNo(), model.getMonth());
		// 没有维护该月对应的结算期
		if(shopBalanceDate == null) {
			map.put("flag", 1);
			map.put("msg", "请先设置店铺[" + model.getShopNo() + "]对应的[" + model.getMonth() + "]结算期");
			return map;
		}
		// 如果结算期都已生成数据
		if(shopBalanceDate.getBalanceFlag() == 2) {
			map.put("flag", 1);
			map.put("msg", "店铺[" + model.getShopNo() + "]对应的[" + model.getMonth() + "]结算期已生成结算单，不能重复生成");
			return map;
		}
		// 如果是生成正式结算单，查询是否存在店铺在结算期中存在预估结算单，若存在，则返回预估结算单，否则直接返回
		if(model.getBalanceType() == ShopMallEnums.BalanceType.FORMAL_BALANCE.getRequestId().intValue()) {
			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("companyNo", model.getCompanyNo());
			String companyNo = model.getCompanyNo() == null ? null : model.getCompanyNo().toString();
			if(companyNo != null && !"".equals(companyNo)){
			   params.put("companyNos", FasUtil.formatInQueryCondition(companyNo));
			}
			params.put("shopNo", model.getShopNo());
			params.put("startDate", DateUtil.format1(shopBalanceDate.getBalanceStartDate()));
			params.put("endDate", DateUtil.format1(shopBalanceDate.getBalanceEndDate()));
			params.put("balanceType", ShopMallEnums.BalanceType.ESTIMATE_BALANCE.getRequestId().intValue());
			List<BillShopBalance> lstBill = billShopBalanceManager.findByBiz(null, params);
			if(lstBill != null && lstBill.size() > 0) {
				map.put("flag", 2);
				map.put("bill", lstBill.get(0));
				return map;
			}
		}
		map.put("flag", 0);
		return map;
	}
	
	/**
	 * 复制结算单
	 * @param model
	 * @param request
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/copyBill")
	@ResponseBody
	public BillShopBalance copyBill(@ModelAttribute("model")BillShopBalance model, 
			HttpServletRequest request) throws ManagerException{
		BillShopBalance result = null;
		try {
			SystemUser systemUser = CurrentUser.getCurrentUser(request);
			
			Map<String, Object> map = new HashMap<String, Object>();
			// 处理结算期
			ShopBalanceDate shopBalanceDate = shopBalanceDateManager.findBalanceDate(model.getShopNo(), model.getMonth());
			// 没有维护该月对应的结算期
			if(shopBalanceDate == null) {				
				result.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
	   			result.setErrorInfo("请先设置店铺[" + model.getShopNo() + "]对应的[" + model.getMonth() + "]结算期");
			}
			// 如果结算期都已生成数据
			if(shopBalanceDate.getBalanceFlag() == 2) {
				result.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
	   			result.setErrorInfo( "店铺[" + model.getShopNo() + "]对应的[" + model.getMonth() + "]结算期已生成结算单，不能重复操作");
			}
			result = billShopBalanceManager.copyBill(model, systemUser);
   		} catch(ManagerException e) {
   			result = billShopBalanceManager.findById(model);
   			result.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
   			result.setErrorInfo(e.getMessage());
   		}
   		return result;
	}
	
	/**
	 * 导出
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/do_exports")
	public void doFasExport(HttpServletRequest req, Model model,HttpServletResponse response) throws ManagerException {
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String)params.get("firstHeaderColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String)params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if(StringUtils.isNotEmpty(exportColumns)) {
			try {
				//字段名列表
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {});

				List<Map> subColumnsList = new ArrayList<Map>();
				if(StringUtils.isNotEmpty(subExportColumns)) {
					subColumnsList = mapper.readValue(subExportColumns, new TypeReference<List<Map>>() {});
					
					//如果是混合表头，则将subColumnsList加入columnsList集合
					if(ExportTypeEnum.FIX_HEADER.getType().equalsIgnoreCase(exportType)) {
						columnsList.addAll(subColumnsList);
						subColumnsList = new ArrayList<Map>(1);
					}
				}
				columnsList = this.sortExportColumns(columnsList);
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(columnsList);
				exportVo.setSubColumnsMapList(subColumnsList);
				
				if(StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}
				 String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		        String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
		        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
		        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		        String retailType = params.get("retailType") == null ? null : params.get("retailType").toString();
		        if (StringUtils.isNotEmpty(shopNos)) {
		        	params.put("shopNos",Arrays.asList(shopNos.split(",")));
				}
		        if (StringUtils.isNotEmpty(companyNos)) {
		        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
				}
		        if(StringUtils.isNotEmpty(organNos)){
		        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
				}
		        if(StringUtils.isNotEmpty(brandNos)){
					params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
				}	
		        if (StringUtils.isNotEmpty(retailType)) {
		        	params.put("retailType", FasUtil.formatInQueryCondition(retailType));
				}
		        if(StringUtils.isNotEmpty(brandNos)){
			        String brandUnitNo = brandNos.replace(",","|");
//					params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
					params.put("brandUnitNo", brandUnitNo); 
				}	
				/*
				if (StringUtils.isNotEmpty(shopNos)) {
			        params.put("shopNos",Arrays.asList(shopNos.split(",")));
				}
		        if (StringUtils.isNotEmpty(companyNos)) {
		        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
				}
		        if(StringUtils.isNotEmpty(organNos)){
		        	params.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNos));
		        	params.put("organNos",  null);
				}
		        if(StringUtils.isNotEmpty(brandNos)){
					params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
				}	*/
				int total = this.billShopBalanceManager.selectBlanceCount(params);
				SimplePage page = new SimplePage(0, total, total);
				List<BillShopBalance> list = this.billShopBalanceManager.selectBlanceList(page, sortColumn, sortOrder, params);
				List<Map> listArrayList = new ArrayList<Map>();
				if(list != null && list.size() > 0) {
					List<String> fields = new ArrayList<String>();
					for(Map map : columnsList) {
						fields.add(map.get("field").toString());
					}
					boolean flag = true;
					ExportFormat formatAnnotation = null;
					AbstractExportFormat<BillShopBalance> format = null;
					for(BillShopBalance vo : list) {
						Map map = null;
						if(flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if(formatAnnotation != null) {
							format = (AbstractExportFormat<BillShopBalance>) formatAnnotation.className().newInstance();
							map = format.format(fields, vo);
						} else {
							map = new HashMap();
							BeanUtilsCommon.object2MapWithoutNull(vo, map);
						}
						if(subColumnsList != null && subColumnsList.size() > 0) {
							List<Map> subExportData = this.findComplexHeaderData(vo);
							map.put("subExportData", subExportData);
						} else {
							map.put("subExportData", new ArrayList<Map>(1));
						}
						listArrayList.add(map);
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(listArrayList);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportData(exportVo, response);
				}
			} catch(Exception e) {
				logger.error(e.getMessage(),e);
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	
	}
}