package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceRegister;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceRegisterManager;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-17 12:13:49
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
@RequestMapping("/bill_balance_invoice_register_old")
@Deprecated
public class BillBalanceInvoiceRegisterController extends BaseController<BillBalanceInvoiceRegister> {
    @Resource
    private BillBalanceInvoiceRegisterManager billBalanceInvoiceRegisterManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_balance_invoice_register/",billBalanceInvoiceRegisterManager);
    }
    
    
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(@RequestParam("balanceType")String balanceType, HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	int iBalanceType = Integer.parseInt(balanceType);
    	mav.setViewName("bill_balance_invoice_register/list"); 	
    	mav.addObject("balanceType", iBalanceType);    	
    	return mav;
    }
}