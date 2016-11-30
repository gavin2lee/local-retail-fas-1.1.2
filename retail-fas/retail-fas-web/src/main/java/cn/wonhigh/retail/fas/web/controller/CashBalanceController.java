package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CashBalance;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.CashBalanceManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/cash_balance")
@ModuleVerify("30170030")
public class CashBalanceController extends BaseCrudController<CashBalance> {
	
	@Resource
	private CashBalanceManager cashBalanceManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("IndepShop_management/cash_balance/", cashBalanceManager);
	}
	
	@RequestMapping({ "/list.json" })
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String,Object>();
		Map<String, Object> params = builderParams(req, model);
		int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
		params.put("isCashRegister", String.valueOf(ShardingUtil.isPE()));//modify by wang.m 添加自收银店铺过滤条件
		
		int total = cashBalanceManager.findCashBalanceCount(params);
		List<CashBalance> list = new ArrayList<CashBalance>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = cashBalanceManager.findCashBalanceList(page,null,null,params);
		}
		
		obj.put("total", total);
		obj.put("rows", list);
		
		return obj;
	}
	
	@RequestMapping(value = "/do_exports")
	public void doExport(HttpServletRequest req, Model model,HttpServletResponse response) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		params.put("isCashRegister", String.valueOf(ShardingUtil.isPE()));//modify by wang.m 添加自收银店铺过滤条件
		int total = cashBalanceManager.findCashBalanceCount(params);
		List<CashBalance> list = new ArrayList<CashBalance>();
		if(total > 0){
			SimplePage page = new SimplePage(1, total, total);
			list = cashBalanceManager.findCashBalanceList(page,null,null,params);
		}
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}

}
