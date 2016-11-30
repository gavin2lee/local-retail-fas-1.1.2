package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
@RequestMapping("/bill_buy_balance")
public class BillBuyBalanceController extends BaseCrudController<BillBuyBalance> {
    @Resource
    private BillBuyBalanceManager billBuyBalanceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_buy_balance/",billBuyBalanceManager);
    }
    
	@RequestMapping(value = "/selectBuyerCompany")
	@ResponseBody
	public  Map<String, Object>  selectBuyerCompany(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int iTotal = billBuyBalanceManager.selectBuyerCompanyCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) iTotal);
		List<BillBuyBalance> buyListTemp = billBuyBalanceManager.selectBuyerCompany(page, sortColumn, sortOrder, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", buyListTemp);
		map.put("total",iTotal);
   		return map;
	}
	
	@RequestMapping(value = "/selectSalerCompany")
	@ResponseBody
	public  Map<String, Object>  selectSalerCompany(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int iTotal = billBuyBalanceManager.selectSalerCompanyCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) iTotal);
		List<BillBuyBalance> buyListTemp = billBuyBalanceManager.selectSalerCompany(page, sortColumn, sortOrder, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", buyListTemp);
		map.put("total",iTotal);
   		return map;
	}
}