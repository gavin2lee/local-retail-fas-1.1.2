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

import cn.wonhigh.retail.fas.common.model.ItemReturnRecord;
import cn.wonhigh.retail.fas.manager.ItemReturnManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/item_return")
@ModuleVerify("30170018")
public class ItemReturnController extends ExcelImportController<ItemReturnRecord>{
	
	@Resource
	private ItemReturnManager itemReturnManager;
	
	@Override
	protected CrudInfo init() {
		return new CrudInfo("IndepShop_management/item_return/",itemReturnManager);
	}
	
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		
		ItemReturnRecord itemReturnRecord = itemReturnManager.findItemReturnCount(params);
		List<ItemReturnRecord> list = new ArrayList<ItemReturnRecord>();
		if(itemReturnRecord.getTotal()>0){
			SimplePage page = new SimplePage(pageNo, pageSize, itemReturnRecord.getTotal());
			list = itemReturnManager.findItemReturnList(page, null, null, params);
			String companyNo = (String) params.get("companyNo");
			list = itemReturnManager.setItemReturnProperties(list,companyNo);
		}
		obj.put("total", itemReturnRecord.getTotal());
		obj.put("rows", list);
		return obj;
	}

	@Override
	protected String[] getImportProperties() {
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<ItemReturnRecord> list) {
		return false;
	}
	
	@Override
	protected List<ItemReturnRecord> queryExportData(Map<String, Object> params) throws ManagerException {
		SimplePage page = new SimplePage(1, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<ItemReturnRecord> list = itemReturnManager.findItemReturnList(page, null, null, params);
		String companyNo = (String) params.get("companyNo");
		list = itemReturnManager.setItemReturnProperties(list,companyNo);
		return list;
	}
	
}
