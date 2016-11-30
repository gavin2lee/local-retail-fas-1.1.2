package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

/**
 * 地区批发结算
 * 
 * @author yang.y
 */
@Controller
@RequestMapping("/bill_balance_zone_detail")
@ModuleVerify("30160011") 
public class BillBalanceZoneDetailListController extends BillBalanceZoneController {}
