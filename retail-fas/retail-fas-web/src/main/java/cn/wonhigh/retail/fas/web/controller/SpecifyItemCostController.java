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

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceManager;
import cn.wonhigh.retail.fas.manager.SpecifyItemCostManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

@Controller
@RequestMapping("/specify_item_cost")
@ModuleVerify("34000002")
public class SpecifyItemCostController extends BaseCrudController<BillBuyBalance> {
	
	@Resource
	private SpecifyItemCostManager specifyItemCostManager;
	
	@Resource
	private BillBuyBalanceManager billBuyBalanceManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("specify_item_cost/",specifyItemCostManager);
	}

	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model){
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		BillBuyBalance billBuyBalance = billBuyBalanceManager.selectSpecifyItemCostCount(params);
		List<BillBuyBalance> list = new ArrayList<BillBuyBalance>();
		if(billBuyBalance.getTotal()>0){
			SimplePage page = new SimplePage(pageNo, pageSize, billBuyBalance.getTotal());
			list = billBuyBalanceManager.selectSpecifyItemCost(page, null, null, params);
			list = specifyItemCostManager.setExtendsProperties(list);
		}
		
		obj.put("total", billBuyBalance.getTotal());
		obj.put("rows", list);
		return obj;
		
	}
	
	@RequestMapping(value = "/do_fas_export")
	public void exportFAS(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(1, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<BillBuyBalance>  list = billBuyBalanceManager.selectSpecifyItemCost(page, null, null, params);
		list = specifyItemCostManager.setExtendsProperties(list);
		
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}
}
