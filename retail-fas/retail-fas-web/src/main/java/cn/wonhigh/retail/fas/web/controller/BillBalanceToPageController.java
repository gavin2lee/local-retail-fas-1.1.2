package cn.wonhigh.retail.fas.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;

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
@RequestMapping("/bill_balance/to_page")
public class BillBalanceToPageController{
	/***************总部厂商结算START****************/

	@RequestMapping(value = "/to_item_buy_gather")
	public String toItemBuyGather() {
		return "bill_balance/hq/item_buy_gather";
	}
	
	@RequestMapping(value = "/to_item_sale_gather")
	public String toItemSaleGather() {
		return "bill_balance/hq/item_sale_gather";
	}
	
	@RequestMapping(value = "/to_create_balance")
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
	
	
	@RequestMapping(value = "/to_adjust_balance")
	public ModelAndView toBalanceAdjust(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("balanceType", req.getParameter("balanceType"));
		mav.setViewName("bill_balance/hq/balance_adjust");
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
		mav.addObject("balanceType", BalanceTypeEnums.HQ_VENDOR.getTypeNo());
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
		obj.addObject("balanceType", BalanceTypeEnums.HQ_WHOLESALE.getTypeNo());
		if(StringUtils.isNotBlank(isArea)){
			obj.addObject("isArea", isArea);
			obj.setViewName("bill_balance/hq/sale_balance_area");
		}else{
			obj.setViewName("bill_balance/hq/sale_balance");
		}
		String billNoMenu = req.getParameter("billNoMenu");
		if(StringUtils.isNotBlank(billNoMenu)){
			obj.addObject("billNoMenu", billNoMenu);
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
	public String saleInvoiceTab() {
		return "bill_balance/hq/sale_tab_invoice";
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

	@RequestMapping(value = "/area_list_gather")
	public String areaGatherList() {
		return "bill_balance/area/area_list_gather";
	}

	/***************总部批发结算END****************/
}