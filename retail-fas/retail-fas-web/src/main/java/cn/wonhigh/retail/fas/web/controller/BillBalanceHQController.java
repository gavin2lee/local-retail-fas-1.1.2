package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.PricingRange;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.BillBalanceHQManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@Controller
@RequestMapping("/bill_balance/hq")
public class BillBalanceHQController extends BaseCrudController<BillBalance> {
	@Resource
	private BillBalanceHQManager billBalanceHQManager;

	@Resource
	private OtherDeductionController otherDeductionController;
	
	@Resource
	private CustomImperfectController customImperfectController;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Resource
	private BillAskPaymentController billAskPaymentController;
	
	@Resource
	private BillInvoiceController billInvoiceController;
	
	@Resource
	private PricingRangeController pricingRangeController;
	
	@Resource
	private BillBalanceInvoiceApplyController billBalanceInvoiceApplyController;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_balance/hq/", billBalanceHQManager);
	}

	/***************总部厂商结算START****************/

	@RequestMapping(value = "/to_item_buy_gather")
	public String toItemBuyGather() {
		return "bill_balance/hq/item_buy_gather";
	}
	
	@RequestMapping(value = "/to_item_sale_gather")
	public String toItemSaleGather() {
		return "bill_balance/hq/item_sale_gather";
	}
	
	@RequestMapping(value = "/to_custom_create_balance")
	public ModelAndView toCustomCreateBalance(HttpServletRequest req) {
		ModelAndView obj = new ModelAndView();
		int iBalanceType = Integer.parseInt(req.getParameter("balanceType"));
		if(BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType){
			obj.setViewName("bill_balance/hq/create_buy_balance");
		}else{
			obj.setViewName("bill_balance/hq/create_sale_balance");
		}
		obj.addObject("balanceType", req.getParameter("balanceType"));
		return obj;
	}
	
	
	@RequestMapping(value = "/to_balance_adjust")
	public ModelAndView toBalanceAdjust(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String balanceType=req.getParameter("balanceType");
		if (StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.AREA_AMONG.getTypeNo()))) {
			mav.addObject("balanceType", balanceType);
			mav.setViewName("area_among_balance/receivable/deduction_adjust");//转到地区间其他扣项调整
		} else if(StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.AREA_BUY.getTypeNo()))){
			mav.addObject("balanceType", balanceType);
			mav.setViewName("area_purchase_balance/deduction_adjust");//转到地区自购其他扣项调整
		}else if(StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo()))){
			mav.addObject("balanceType", balanceType);
			mav.setViewName("hq_insteadBuy_balance/hq_confirm/deduction_adjust");//转到总部代采其他扣项调整
		}else {
			mav.addObject("balanceType", balanceType);
			mav.setViewName("bill_balance/hq/balance_adjust");
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/buy_list_enter")
	public String buyListEnter() {
		return "bill_balance/hq/buy_list_enter";
	}

	/**
	 * 跳转结算单列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_tabMain")
	public String buyListTab() {
		return "bill_balance/hq/buy_tabMain";
	}

	/**
	 * 跳转结算单汇总列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_list_gather")
	public String buyListGather() {
		return "bill_balance/hq/buy_list_gather";
	}

	/**
	 * 跳转结算单(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_balance")
	public ModelAndView buyBalance(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String billNoMenu = req.getParameter("billNoMenu");
		if(StringUtils.isNotBlank(billNoMenu)){
			mav.addObject("billNoMenu", billNoMenu);
		}
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.addObject("balanceType", req.getParameter("balanceType")==null?"":req.getParameter("balanceType"));
		mav.setViewName("bill_balance/hq/buy_balance");
		return mav;
	}

	/**
	 * 跳转结算单入库明细TAB列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_tab_enter")
	public String enterTab() {
		return "bill_balance/hq/buy_tab_enter";
	}

	/**
	 * 跳转结算单退残明细TAB列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_tab_return")
	public String returnTab() {
		return "bill_balance/hq/buy_tab_return";
	}

	/**
	 * 跳转结算单扣项明细TAB列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_tab_deduction")
	public String deductionTab() {
		return "bill_balance/hq/buy_tab_deduction";
	}

	/**
	 * 跳转结算单发票明细TAB列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_tab_invoice")
	public String invoiceTab() {
		return "bill_balance/hq/buy_tab_invoice";
	}

	/**
	 * 跳转结算单付款明细TAB列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/buy_tab_pay")
	public String payTab() {
		return "bill_balance/hq/buy_tab_pay";
	}

	/***************总部厂商结算END****************/

	/***************总部批发结算START******/

	/**
	 * 跳转入库明细列表(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_list_out")
	public String saleListEnter() {
		return "bill_balance/hq/sale_list_out";
	}

	/**
	 * 跳转结算单扣项明细TAB列表(总部厂商结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_tab_deduction")
	public String saleDeductionTab() {
		return "bill_balance/hq/sale_tab_deduction";
	}
	
	/**
	 * 跳转结算单列表(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_tabMain")
	public ModelAndView saleTabMain(HttpServletRequest req) {
		ModelAndView obj = new ModelAndView();
		String isArea = req.getParameter("isArea");
		if(StringUtils.isNotBlank(isArea) && "true".equals(isArea)){
			obj.setViewName("bill_balance/hq/sale_tabMain_area");
		}else{
			obj.setViewName("bill_balance/hq/sale_tabMain");
		}
		return obj;
	}

	/**
	 * 跳转结算单汇总列表(总部厂商结算/总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_list_gather")
	public String saleListGather() {
		return "bill_balance/hq/sale_list_gather";
	}
	
	/**
	 * 跳转结算汇总表(地区采购结算)
	 * @return
	 */
	@RequestMapping(value = "/area_balance_gather")
	public String areaListGather() {
		return "bill_balance/area/area_balance_gather";
	}
	

	/**
	 * 跳转结算单(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_balance")
	public ModelAndView saleBalance(HttpServletRequest req) {
		ModelAndView obj = new ModelAndView();
		String isArea = req.getParameter("isArea");
		obj.addObject("balanceType", req.getParameter("balanceType")==null?"":req.getParameter("balanceType"));
		if(StringUtils.isNotBlank(isArea) && "true".equals(isArea)){
			obj.addObject("isArea", isArea);
			obj.setViewName("bill_balance/hq/sale_balance_area");
		}else{
			obj.setViewName("bill_balance/hq/sale_balance");
		}
		String billNoMenu = req.getParameter("billNoMenu");
		if(StringUtils.isNotBlank(billNoMenu)){
			obj.addObject("billNoMenu", billNoMenu);
		}
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			obj.addObject("warnPostUrl", warnPostUrl);
		}
		return obj;
	}

	/**
	 * 跳转结算单出库明细TAB列表(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_tab_out")
	public String saleOutTab() {
		return "bill_balance/hq/sale_tab_out";
	}

	/**
	 * 跳转结算单退残明细TAB列表(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_tab_return")
	public String saleReturnTab() {
		return "bill_balance/hq/sale_tab_return";
	}
	
	/**
	 * 跳转结算单发票明细TAB列表(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_tab_invoice")
	public ModelAndView saleTabInvoice(HttpServletRequest req) {
		ModelAndView obj = new ModelAndView();
		String isArea = req.getParameter("isArea");
		if(StringUtils.isNotBlank(isArea) && "true".equals(isArea)){
			obj.addObject("isArea", isArea);
			obj.setViewName("bill_balance/hq/sale_tab_invoice_area");
		}else{
			obj.setViewName("bill_balance/hq/sale_tab_invoice");
		}
		return obj;
	}

	/**
	 * 跳转结算单收款明细TAB列表(总部批发结算)
	 * @return
	 */
	@RequestMapping(value = "/sale_tab_receipt")
	public String saleReceiptTab() {
		return "bill_balance/hq/sale_tab_receipt";
	}

	/***************总部批发结算END****************/

	/***************总部其他结算START****************/
	@RequestMapping(value = "/other_list_enter")
	public String otherEnterList() {
		return "bill_balance/hq/other_list_enter";
	}

	/***************总部批发结算END****************/

	/***************地区采购结算start****************/
	@RequestMapping(value = "/area_list_enter")
	public String areaEnterList() {
		return "bill_balance/area/area_list_enter";
	}

	@RequestMapping(value = "/area_list_out")
	public String areaOutList() {
		return "bill_balance/area/area_list_out";
	}
	
	@RequestMapping(value = "/area_list_gather")
	public String areaGatherList() {
		return "bill_balance/area/area_list_gather";
	}

	/***************总部批发结算END****************/

	@RequestMapping(value = "/to_area_tab_out")
	public String toAreaOutTab() {
		return "bill_balance/area/area_tab_out";
	}
	
	@RequestMapping(value = "/to_area_tab_entry")
	public String toAreaEntryTab() {
		return "bill_balance/area/area_tab_entry";
	}
	
	
	/**
	 * 入库明细Tab列表(总部厂商结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/buy_tab_enter.json")
	@ResponseBody
	public Map<String, Object> queryBuyEnterList(HttpServletRequest req, Model model) throws Exception {
		return this.queryEnterList(req, model);
	}

	/**
	 * 退残明细Tab列表(总部厂商结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/buy_tab_return.json")
	@ResponseBody
	public Map<String, Object> queryBuyReturnList(HttpServletRequest req, Model model) throws Exception {
		return this.queryEnterList(req, model);
	}

	/**
	 * 扣项明细Tab列表(总部厂商结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/buy_tab_deduction.json")
	@ResponseBody
	public Map<String, Object> queryDeductionList(HttpServletRequest req, Model model) throws ManagerException {
		return customImperfectController.queryList(req, model);
	}

	/**
	 * 扣项明细Tab列表(总部地区结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/sale_tab_deduction.json")
	@ResponseBody
	public Map<String, Object> querySaleDeductionList(HttpServletRequest req, Model model) throws ManagerException {
		return otherDeductionController.queryList(req, model);
	}
	
	/**
	 * 发票明细Tab列表(总部厂商结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/buy_tab_invoice.json")
	@ResponseBody
	public Map<String, Object> queryBuyInvoiceList(HttpServletRequest req, Model model) throws ManagerException {	
		return billInvoiceController.queryListByBalanceNo(req, model);
	}

	/**
	 * 付款明细Tab列表(总部厂商结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/buy_tab_pay.json")
	@ResponseBody
	public Map<String, Object> queryBuyPayList(HttpServletRequest req, Model model) throws ManagerException {	
		return billAskPaymentController.queryListByBalanceNo(req, model);
	}

	/**
	 * 出库明细Tab列表(总部批发结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/sale_tab_out.json")
	@ResponseBody
	public Map<String, Object> querySaleOutList(HttpServletRequest req, Model model) throws Exception {
		return this.queryEnterList(req, model);
	}

	/**
	 * 退残明细Tab列表(总部批发结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/sale_tab_return.json")
	@ResponseBody
	public Map<String, Object> querySaleReturnList(HttpServletRequest req, Model model) throws Exception {
		return this.queryEnterList(req, model);
	}
	
	/**
	 * 发票明细Tab列表(总部批发结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/sale_tab_invoice.json")
	@ResponseBody
	public Map<String, Object> querySaleInvoiceList(HttpServletRequest req, Model model) throws ManagerException {
		String isArea = req.getParameter("isArea");
		if(StringUtils.isNotBlank(isArea) && "true".equals(isArea)){
			return billInvoiceController.queryListByBalanceNo(req, model);
		}
		return billBalanceInvoiceApplyController.getByBalanceNo(req, model);
	}

	/**
	 * 入库明细列表(总部厂商结算/总部批发结算/总部其他结算/地区采购结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/enter_count")
	@ResponseBody
	public Integer queryEnterCount(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		int count = billBalanceHQManager.selectEnterCount(params);
		return count;
	}
	
	/**
	 * 入库明细列表(总部厂商结算/总部批发结算/总部其他结算/地区采购结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/enter_list.json")
	@ResponseBody
	public Map<String, Object> queryEnterList(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<Object> list = new ArrayList<Object>();
		List<Object> footerList = new ArrayList<Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		String multiOrganNoFrom = null == params.get("organNoFrom") ? "" : params.get("organNoFrom")+"";
		if(null != params.get("pricingStatus") && StringUtils.isNotBlank(String.valueOf(params.get("pricingStatus")))){
			List<PricingRange> lstRange = pricingRangeController.getBiz(null, req, model);
			if(lstRange.size() >0){
				params.put("priceRangeQueryCondition", lstRange.get(0).getQueryCondition());
			}
		}
		if(StringUtils.isNotBlank(multiOrganNoFrom)) {
			params.put("multiOrganNoFrom", FasUtil.formatInQueryCondition(multiOrganNoFrom));
		}
		total = billBalanceHQManager.selectEnterCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = billBalanceHQManager.selectEnterList(page, sortColumn, sortOrder, params);
			footerList = billBalanceHQManager.selectEnterFooter(params);
		}
		map.put("total", total);
		map.put("rows", list);
		map.put("footer", footerList);
		return map;
	}

	/**
	 * 结算单汇总列表(总部厂商结算/总部批发结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/gather_count")
	@ResponseBody
	public Integer queryGatherCount(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		if(StringUtils.isNotBlank(req.getParameter("balanceType")) && BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == Integer.parseInt(req.getParameter("balanceType"))){
			setQueryCondition(params);
		}
		int count = billBalanceHQManager.selectBalanceGatherCount(params);
		return count;
	}
	
	/**
	 * 结算单汇总列表(总部厂商结算/总部批发结算)
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/gather_list.json")
	@ResponseBody
	public Map<String, Object> queryGatherList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		if(StringUtils.isNotBlank(req.getParameter("balanceType")) && BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == Integer.parseInt(req.getParameter("balanceType"))){
			setQueryCondition(params);
		}
		int total = billBalanceHQManager.selectBalanceGatherCount(params); 
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<BillBalanceDto> list = new ArrayList<BillBalanceDto>();
		List<BillBalanceDto> footerList = new ArrayList<BillBalanceDto>();
		if(total > 0){
			list = billBalanceHQManager.selectBalanceGatherList(page, sortColumn, sortOrder, params);
			footerList = billBalanceHQManager.selectBalanceGatherFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billBalanceHQManager.selectBalanceCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBalance> list = new ArrayList<BillBalance>();
		List<BillBalance> footerList = new ArrayList<BillBalance>();
		if(total > 0 ){
			list = billBalanceHQManager.selectBalanceByPage(page, sortColumn, sortOrder, params);
			footerList = billBalanceHQManager.selectBalanceFooter(params);
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}

	/**
	 * 设置结算查询条件
	 * @param params
	 */
	private Map<String, Object> setQueryCondition(Map<String, Object> params) {
		if(null != params.get("balanceType")){
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			String queryCondition = params.get("queryCondition") == null ? "" : String.valueOf(params.get("queryCondition"));
			queryCondition = queryCondition.concat(BalanceTypeEnums.getQueryConditionByNo(iBalanceType));
			String companyNos=financialAccountManager.findLeadRoleCompanyNos();
			if(iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()){
				queryCondition = queryCondition.concat(" AND buyer_no IN ("+companyNos+")");
			}else if(iBalanceType == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()){
				queryCondition = queryCondition.concat(" AND saler_no IN ("+companyNos+")");
			}else if(iBalanceType == BalanceTypeEnums.HQ_OTHER.getTypeNo()){
				queryCondition = queryCondition.concat(" AND buyer_no IN ("+companyNos+")  AND saler_no NOT IN ("+companyNos+")");
			}else if(iBalanceType == BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo()){
				queryCondition = queryCondition.concat(" AND buyer_no NOT IN ("+companyNos+")");
			}
			params.put("zoneCompanyNo", companyNos);
			params.put("queryCondition", queryCondition);
		}
		return params;
	}

	/**
	 * 结算单导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/balance_export")
	public void balanceExport(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String billNo = req.getParameter("billNo");
		if(StringUtils.isNotBlank(billNo)){
			List<Map> ColumnsList =  ExportUtils.getColumnList(req.getParameter("exportColumns"));
			String[] arrNo = billNo.split(",");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("exportFlag", true);
			params.put("balanceType", req.getParameter("balanceType"));
			params.put("isArea", req.getParameter("isArea"));
			SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
			List<Map> dataList = new ArrayList<Map>();
			for (String str : arrNo) {
				params.put("balanceNo", ""); //解决参数循环加载。
				Map<String, Object> map = new HashMap<String, Object>();
				params.put("billNo", str);
				BillBalance bill =  (BillBalance)billBalanceHQManager.selectBalanceByPage(page, "", "", params).get(0);
				params.put("balanceNo", str);
				List dtlList =  billBalanceHQManager.selectItemGatherList(page, "", "", params);
				List footerList =  billBalanceHQManager.selectItemGatherFooter(params);
				if(!CollectionUtils.isEmpty(footerList) && !CollectionUtils.isEmpty(dtlList)){
					dtlList.add(footerList.get(0));
				}
				List<Map> dtlMapList = ExportUtils.getDataList(dtlList);
				String buyerName = bill.getBuyerName();
				String salerName = bill.getSalerName();
				String brandUnitName = StringUtils.isBlank(bill.getBrandUnitName())?"":bill.getBrandUnitName().concat(" 品牌");
				Date startDate = bill.getBalanceStartDate();
				Date endDate = bill.getBalanceEndDate();
				String balanceNo = bill.getBillNo();
				String title = buyerName.concat("-").concat(salerName).concat(" ")
						.concat(brandUnitName).concat(" 结算单 ").concat(balanceNo)
						.concat("(").concat(DateUtil.format(startDate))
						.concat("-").concat(DateUtil.format(endDate)).concat(")");
				map.put("title", title);
				map.put("dtl", dtlMapList);
				dataList.add(map);
			}
			ExportUtils.ExportMainData(req.getParameter("fileName"), ColumnsList, dataList, response);
		}
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		List<Map> ColumnsList =  ExportUtils.getColumnList(req.getParameter("exportColumns"));
		List<Map> dataMapList = ExportUtils.getDataList(this.getDataList(req, model));
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}
	
	/**
	 * 获取导出数据
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getDataList(HttpServletRequest req, Model model) throws Exception{
		List dataList = null;
		List footerList = null;
		String type = req.getParameter("type");	
		if(StringUtils.isNotBlank(type)){
			int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
			Map<String, Object> params = builderParams(req, model);
			SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
			page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
			if(type.equals("enter")){
				if(null != params.get("pricingStatus") && StringUtils.isNotBlank(String.valueOf(params.get("pricingStatus")))){
					List<PricingRange> lstRange = pricingRangeController.getBiz(null, req, model);
					if(lstRange.size() >0){
						params.put("priceRangeQueryCondition", lstRange.get(0).getQueryCondition());
					}
				}
				dataList =   billBalanceHQManager.selectEnterList(page, "", "", setQueryCondition(params));
				footerList = billBalanceHQManager.selectEnterFooter(params);
			}else if(type.equals("gather")){
				if(StringUtils.isNotBlank(req.getParameter("balanceType")) && BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == Integer.parseInt(req.getParameter("balanceType"))){
					setQueryCondition(params);
				}
				dataList =  billBalanceHQManager.selectBalanceGatherList(page, "", "", params);
				footerList = billBalanceHQManager.selectBalanceGatherFooter(params);
			}else if(type.equals("balance")){
				dataList =  billBalanceHQManager.selectBalanceByPage(page, "", "", params);
				footerList = billBalanceHQManager.selectBalanceFooter(params);
			}else if(type.equals("itemGather")){
				params.put("exportFlag", true);
				dataList =  billBalanceHQManager.selectItemGatherList(page, "", "", params);
			}
		}
		if(!CollectionUtils.isEmpty(footerList) && !CollectionUtils.isEmpty(dataList)){
			dataList.add(footerList.get(0));
		}
		return dataList;
	}
	
	/**
	 * 校验结算金额
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getBalanceAmount")
	public BillBalance getBalanceAmount(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		return billBalanceHQManager.getBalanceAmount(params);
	}
	
	/**
	 * 结算前处理
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handle_before_balance")
	@ResponseBody
	public Map<String, Object> handleBeforeBalance(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		return billBalanceHQManager.handleBeforeBalance(params);
	}
	
	/**
	 * 更新异常价格
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update_exception_price")
	@ResponseBody
	public int updateExceptionPrice(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		if(!ShardingUtil.isPE()){
			return billBalanceHQManager.updateExceptionPrice(params);
		}
		return 0;
	}
	
	/**
	 * 新增结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ResponseEntity<BillBalance> addBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		if(StringUtils.isBlank(req.getParameter("isBatch"))){
			 if(StringUtils.isNotBlank(req.getParameter("brandUnitNo"))){
				 params.put("splitBrandUnit", "true");
			 }else{
				 params.put("splitBrandUnit", "false");
			 }
			 if(StringUtils.isNotBlank(req.getParameter("categoryNo"))){
				 params.put("splitCategory", "true");
			 }else{
				 params.put("splitCategory", "false");
			 }
		}
		obj = billBalanceHQManager.addBill(obj, params);
		if(null != obj && StringUtils.isNotBlank(obj.getId())){
			return this.get(obj);
		}
		return null;
	}
	
	/**
	 * 修改结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ResponseEntity<BillBalance> updateBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		billBalanceHQManager.modifyById(obj);
		if(null != obj && StringUtils.isNotBlank(obj.getId())){
			return this.get(obj);
		}
		return null;
	}
	
	/**
	 * 明细汇总列表
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/item_gather.json")
	@ResponseBody
	public Map<String, Object> queryItemGatherList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		List<Object> list = new ArrayList<Object>();
		List<Object> listFooter = new ArrayList<Object>();
		int total = billBalanceHQManager.selectItemGatherCount(params);
		if (total > 0) {
			SimplePage page = null;
			if(params.get("print")!=null && params.get("print").equals("1")){
				page = new SimplePage(pageNo, (int) total, (int) total);
			}else{
				page = new SimplePage(pageNo, pageSize, (int) total);
			}
			list = billBalanceHQManager.selectItemGatherList(page, sortColumn, sortOrder, params);
			listFooter = billBalanceHQManager.selectItemGatherFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", listFooter);
		return obj;
	}
	
	/**
	 * 结算调整
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/balance_adjust")
	public ResponseEntity<BillBalance> balanceAdjust(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(StringUtils.isNotBlank(req.getParameter("deductionRows")) || StringUtils.isNotBlank(req.getParameter("imperfectRows"))){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> deduction = mapper.readValue(req.getParameter("deductionRows"), new TypeReference<List<Map>>() {});
			List lstDeduction =convertListWithTypeReference(mapper, deduction, OtherDeduction.class);
			List<Map> imperfect = mapper.readValue(req.getParameter("imperfectRows"), new TypeReference<List<Map>>() {});
			List lstImperfect =convertListWithTypeReference(mapper, imperfect, CustomImperfect.class);
			obj = billBalanceHQManager.balanceAdjust(obj, lstDeduction, lstImperfect);
		}
		if(null != obj && StringUtils.isNotBlank(obj.getId())){
			return this.get(obj);
		}
		return null;
	}
	
	/**
	 * 自定义结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/custom_create_balance")
	public ResponseEntity<BillBalance> customCreateBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(StringUtils.isNotBlank(req.getParameter("checkedRows"))){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> itemList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<Object> lstItem = null;
			if(BalanceTypeEnums.HQ_VENDOR.getTypeNo() == obj.getBalanceType().intValue()){
				lstItem =convertListWithTypeReference(mapper, itemList, BillBuyBalance.class);
			}else{
				lstItem =convertListWithTypeReference(mapper, itemList, BillSaleBalance.class);
			}
			obj = billBalanceHQManager.createBalance(obj, lstItem);
		}
		if(null != obj && StringUtils.isNotBlank(obj.getId())){
			return this.get(obj);
		}
		return null;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
	
	  //汇总总部结算单状态
    @RequestMapping(value = "/selectHqCount")
	@ResponseBody
	public  List<BillBalance> billConunt(HttpServletRequest req, BillBalance billBalance) throws Exception{
		List<BillBalance> list = new ArrayList<BillBalance>();
		list=billBalanceHQManager.selectHqCount();
		for(int i = 0 ; i < list.size() ; i++){
			BillBalance bill=(BillBalance)list.get(i);
			if(bill.getBalanceType() == BalanceTypeEnums.HQ_VENDOR.getTypeNo()){ 
				bill.setBalanceName(BalanceTypeEnums.HQ_VENDOR.getTypeName());
				bill.setBalanceStatus(BalanceStatusEnums.NO_CONFIRM.getTypeName());
			}
		}
		return list;
	}
    
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/do_enter_export")
	public void doExports(HttpServletRequest request, Model model,HttpServletResponse response)
			throws ManagerException {
			String fileName = request.getParameter("fileName");
			String columns = request.getParameter("exportColumns");
		try {
			ExportComplexVo exportVo = new ExportComplexVo();
			exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
			List<Map> columnsList =  ExportUtils.getColumnList(columns);
			exportVo.setColumnsMapList(columnsList);
			final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo);
			Function<Object, Boolean> handler = new Function<Object, Boolean>() {
				@Override
				public Boolean apply(Object input) {
					Map vals = (Map) input;
					
					exportExcel.write(vals);
					return true;
				}
			};
			SimplePage page = new SimplePage();
			page.setPageSize(Integer.MAX_VALUE);
			Map<String, Object> params = builderParams(request, model);
			setQueryCondition(params);
			this.billBalanceHQManager.findExportList(page, params, handler);
			exportExcel.flush(response);
			exportExcel.close();
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
    @RequestMapping(value = "/get_pricing")
    @ResponseBody
	public Map<String, Object> getPricing(HttpServletRequest request, Model model,HttpServletResponse response) throws Exception{
    	Map<String, Object> map = new HashMap<>();
    	List<PricingRange> lstItem = pricingRangeController.getBiz(null, request, model);
    	if(!CollectionUtils.isEmpty(lstItem)){
    		map.put("obj", lstItem.get(0));
    	}
    	map.put("success", true);
		return map;
	}
    
    @RequestMapping(value = "/save_pricing")
    @ResponseBody
	public Map<String, Object> savePricing(PricingRange obj, HttpServletRequest req, Model model) throws Exception{
		Map<String, Object> map = new HashMap<>();
    	if(null == obj.getId()) {
			 pricingRangeController.add(obj);
		}else{
			pricingRangeController.moditfy(obj);
		}
    	map.put("success", true);
		return map;
	}
    
}