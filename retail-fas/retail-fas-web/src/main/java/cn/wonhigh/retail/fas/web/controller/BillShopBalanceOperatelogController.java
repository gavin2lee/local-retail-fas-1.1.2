package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceOperatelog;
import cn.wonhigh.retail.fas.manager.BillShopBalanceOperatelogManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2016-02-19 09:52:49
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
@RequestMapping("/bill_shop_balance_operatelog")
public class BillShopBalanceOperatelogController extends BaseCrudController<BillShopBalanceOperatelog> {
    @Resource
    private BillShopBalanceOperatelogManager billShopBalanceOperatelogManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_operatelog/",billShopBalanceOperatelogManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("mallshop_balance/shopbalance_operatelog");
		return mav;
	}
}