package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.RateExpenseRemind;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.manager.RateExpenseRemindManager;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 11:56:41
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
@RequestMapping("/rate_expense_remind")
public class RateExpenseRemindController extends BaseCrudController<RateExpenseRemind> {
    @Resource
    private RateExpenseRemindManager rateExpenseRemindManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("rate_expense_remind/",rateExpenseRemindManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/warn_concract_show")
	public ModelAndView warnConcractShow(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("rate_expense_remind/warn_concract_show");
		return mav;
	}
    /**
	 * 查询两个月内到期的合同
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/two_months_invalid_concract.json")
	@ResponseBody
	public Map<String, Object> selectTwoMonthsInvalid(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<ShopBalanceDate> list = null;
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);

		total = rateExpenseRemindManager.selectTwoMonthsInvalidCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = rateExpenseRemindManager.selectTwoMonthsInvalidByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
    
}