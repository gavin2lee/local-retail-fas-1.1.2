package cn.wonhigh.retail.fas.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CashInComeCheck;
import cn.wonhigh.retail.fas.manager.CashInComeCheckManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

@Controller
@RequestMapping("/cash_income_check")
@ModuleVerify("30170022")
public class CashInComeCheckController extends ExcelImportController<CashInComeCheck>{

	@Resource
	private CashInComeCheckManager cashInComeCheckManager;
	
	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<CashInComeCheck> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected CrudInfo init() {
		// TODO Auto-generated method stub
		return new CrudInfo("/IndepShop_management/cash_income_check/",null);
	}

	@RequestMapping("/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model){
		
		Map<String,Object> params = builderParams(req, model);
		
		Map<String, Object> obj = null;
		try {
			obj = cashInComeCheckManager.queryList(params);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);

		}
		return obj;
		
	}
	
	@Override
	protected List<CashInComeCheck> queryExportData(Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		
		params.put("pageSize", Integer.MAX_VALUE);
		params.put("pageNumber", 1);
		Map<String,Object> obj = cashInComeCheckManager.queryList(params);
		
		List<CashInComeCheck> cashInComeCheckList = null;
		if(obj != null){
			cashInComeCheckList = (List<CashInComeCheck>) obj.get("rows");
		}
		return cashInComeCheckList;
	}
	
}
