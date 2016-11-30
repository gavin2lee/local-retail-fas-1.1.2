package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.manager.BLKPeriodBalanceManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController.CrudInfo;

@Controller
@RequestMapping("/blk_store_balance")
@ModuleVerify("30120007")
public class BLKStoreBalanceController extends BaseController<BLKPeriodBalance> {
	
	@Resource
	private BLKPeriodBalanceManager blkPeriodBalanceManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("store_balance_export/",blkPeriodBalanceManager);
	}
	
	@RequestMapping({"/list"})
    public String list() {
    	return "store_balance_report/blk_list";
    }
	
	@RequestMapping(value = "/store_balance.json")
   	@ResponseBody
   	public  Map<String, Object> queryBLKPeriodBalanceList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		
		String startYearMonth = StringUtils.isBlank(req.getParameter("startYearMonth")) ? "" : req.getParameter("startYearMonth");
    	String endYearMonth = StringUtils.isBlank(req.getParameter("endYearMonth")) ? "" : req.getParameter("endYearMonth");
		Map<String, Object> params = builderParams(req, model);
		params = convertYearMoth(startYearMonth, endYearMonth, params);
		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("multiBrands", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		
		if (StringUtils.isNotEmpty(req.getParameter("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNos").split(",")));
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = blkPeriodBalanceManager.selectStoreBanalceCount(params);
		List<BLKPeriodBalance> list = new ArrayList<BLKPeriodBalance>();
		if(total>0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = blkPeriodBalanceManager.selectStoreBalanceList(page, null, null, params);
			list = blkPeriodBalanceManager.setExtendsProperties(list);
		}
		
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	@Override
    protected List<BLKPeriodBalance> queryExportData(Map<String, Object> params) throws ManagerException {
		String startYearMonth = (String) params.get("startYearMonth");
		String endYearMonth = (String) params.get("endYearMonth");
		params = convertYearMoth(startYearMonth, endYearMonth, params);
		if (StringUtils.isNotEmpty(String.valueOf(params.get("brandNos")))) {
			params.put("multiBrands", Arrays.asList(String.valueOf(params.get("brandNos")).split(",")));
		}
		
		if (StringUtils.isNotEmpty(String.valueOf(params.get("categoryNos")))) {
			params.put("multiCategorys", Arrays.asList(String.valueOf(params.get("categoryNos")).split(",")));
		}
		
		int total = blkPeriodBalanceManager.selectStoreBanalceCount(params);
		List<BLKPeriodBalance> list = new ArrayList<BLKPeriodBalance>();
		if(total>0){
			SimplePage page = new SimplePage(1, total, total);
			list = blkPeriodBalanceManager.selectStoreBalanceList(page, null, null, params);
			list = blkPeriodBalanceManager.setExtendsProperties(list);
		}
		
		return list;
	}
	
	private Map<String, Object> convertYearMoth(String startYearMonth, String endYearMonth, Map<String,Object> params){
    	
    	List<PeriodBalance> list = new ArrayList<PeriodBalance>();
    	if(startYearMonth.equals("") || endYearMonth.equals("") || startYearMonth.length()<6 || endYearMonth.length()<6){
    		return params;
    	}
    	int startYear = Integer.parseInt(startYearMonth.substring(0,4));
    	int startMonth = Integer.parseInt(startYearMonth.substring(4,6));
    	int endYear = Integer.parseInt(endYearMonth.substring(0,4));
    	int endMonth = Integer.parseInt(endYearMonth.substring(4,6));
    	int tempYear = endYear - startYear;
    	PeriodBalance perBalance;
    	if( tempYear == 0){
    		for(int i = startMonth; i<= endMonth; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(startYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    	}else if(tempYear >= 1){
    		for(int i= startMonth; i<=12; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(startYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    		for(int i = 1; i< endYear-startYear; i++){
    			for (int j = 1; j <= 12; j++) {
    				perBalance = new PeriodBalance();
        			perBalance.setYear(startYear+i+"");
        			perBalance.setMonth(j+"");
        			list.add(perBalance);
				}
    		}
    		for(int i = 1; i<= endMonth; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(endYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    	}
    	params.put("multiYearAndMonth", list);
    	return params;
    }
}
