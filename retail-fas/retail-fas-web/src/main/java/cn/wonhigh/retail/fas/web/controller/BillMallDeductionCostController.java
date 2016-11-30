package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BillMallDeductionCost;
import cn.wonhigh.retail.fas.manager.BillMallDeductionCostManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-08-28 11:47:41
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
@RequestMapping("/mall_deductcost")
public class BillMallDeductionCostController extends BaseController<BillMallDeductionCost> {
    @Resource
    private BillMallDeductionCostManager billMallDeductionCostManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_mall_deduction_cost/",billMallDeductionCostManager);
    }
    
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/mall_deductcostbill";
   	}
    
}