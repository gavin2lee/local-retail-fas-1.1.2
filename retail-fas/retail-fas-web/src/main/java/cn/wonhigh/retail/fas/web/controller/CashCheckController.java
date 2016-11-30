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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CashCheck;
import cn.wonhigh.retail.fas.common.model.CreditCardCheck;
import cn.wonhigh.retail.fas.manager.CashCheckManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
@RequestMapping("/cash_check")
@ModuleVerify("30170007")
public class CashCheckController extends ExcelImportController<CashCheck> {
    @Resource
    private CashCheckManager cashCheckManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("/IndepShop_management/cash_check/",cashCheckManager);
    }
    
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model)throws ManagerException {
    	int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
	    Map<String, Object> params = this.getQueryParams(req);
	    CashCheck cashCheck = cashCheckManager.findShopSaleDetailCount(params);
	    List<CashCheck> list = new ArrayList<CashCheck>();
	    if(cashCheck.getTotal() > 0){
	    	SimplePage page = new SimplePage(pageNo, pageSize, cashCheck.getTotal());
	    	list = cashCheckManager.findShopSaleDetailList(page, null, null, params);
	    	list = cashCheckManager.setCashCheckProperties(list,params);
	    }
		Map<String, Object> obj = new HashMap<String, Object>();
		obj .put("total", cashCheck.getTotal());
		obj.put("rows", list);
		return obj;
    }
    
    @Override
	protected List<CashCheck> queryExportData(Map<String, Object> params) throws ManagerException {
    	List<Integer> businessTypeList = new ArrayList<Integer>();
    	
    	businessTypeList.add(new Integer(0));//0-正常销售 1-跨店销售
		businessTypeList.add(new Integer(1));
		businessTypeList.add(new Integer(2));
		businessTypeList.add(new Integer(6));
		
		params.put("businessTypeList", businessTypeList);
	    CashCheck cashCheck = cashCheckManager.findShopSaleDetailCount(params);
	    List<CashCheck> list = new ArrayList<CashCheck>();
	    if(cashCheck.getTotal() > 0){
	    	SimplePage page = new SimplePage(1, cashCheck.getTotal(), cashCheck.getTotal());
	    	list = cashCheckManager.findShopSaleDetailList(page, null, null, params);
	    	list = cashCheckManager.setCashCheckProperties(list,params);
	    }
		return list;
	}
    
	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<CashCheck> list) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@RequestMapping("/lookForChildList")
	@ResponseBody
	public List<CashCheck> queryChildList(@RequestParam("depositAccount")String depositAccount,@RequestParam("depositDate")String depositDate) throws ManagerException{
		if(StringUtils.isEmpty(depositAccount) || StringUtils.isEmpty(depositDate.toString())){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> depositAccounts = new ArrayList<String>();
		if(depositAccount.indexOf(",")!=0){
			String[] accounts = depositAccount.split(",");
			for(String account:accounts){
				if(!depositAccounts.contains(account)){
					depositAccounts.add(account);
				}
			}
		}else{
			depositAccounts.add(depositAccount);
		}
		params.put("depositAccounts", depositAccounts);
		params.put("startDate", depositDate);
		params.put("endDate", depositDate);
		return cashCheckManager.queryCashCheckDetail(params);
	}
	
	private Map<String, Object> getQueryParams(HttpServletRequest req) {
		Map<String, Object> params = new HashMap<String, Object>();
		String companyNo = req.getParameter("companyNo");
		String shopNo = req.getParameter("shopNo");
		String startOutDate = req.getParameter("startOutDate");
		String endOutDate = req.getParameter("endOutDate");
		String depositAccount = req.getParameter("account");
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	
    	businessTypeList.add(new Integer(0));//0-正常销售 1-跨店销售
		businessTypeList.add(new Integer(1));
		businessTypeList.add(new Integer(2));
		businessTypeList.add(new Integer(6));
		
		params.put("companyNo", companyNo);
		params.put("shopNo", shopNo);
		params.put("startOutDate", startOutDate);
		params.put("endOutDate", endOutDate);
		params.put("account", depositAccount);
		params.put("businessTypeList", businessTypeList);
		
		return params;
	}
	
}