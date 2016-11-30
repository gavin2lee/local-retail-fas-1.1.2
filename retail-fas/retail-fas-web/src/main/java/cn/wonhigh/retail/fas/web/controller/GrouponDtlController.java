package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 地区单位团购-团购订单明细
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
@RequestMapping("/groupon_dtl")
@ModuleVerify("30190020")
public class GrouponDtlController extends BaseCrudController<BillBuyBalance> {
	
	@Resource
    private BillBuyBalanceManager billBuyBalanceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("groupon_dtl/",billBuyBalanceManager);
    }
    
    /**
	 * 地区单位团购明细
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/groupon_dtl_list.json")
	@ResponseBody
	public Map<String, Object> queryOtherStockOutList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
//		params.put("queryCondition", " AND bill_type in ("+BillTypeEnums.SALEORDER.getRequestId()+" )");
		params.put("billType", BillTypeEnums.SALEORDER.getRequestId());
		
		int total = billBuyBalanceManager.findCount(params);
		
		List<BillBuyBalance> list = new ArrayList<BillBuyBalance>();
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = billBuyBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
    
}