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

import cn.wonhigh.retail.fas.common.model.CardReturnCheck;
import cn.wonhigh.retail.fas.common.model.CreditCardCheck;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.CreditCardCheckManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
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
@RequestMapping("/credit_card_check")
@ModuleVerify("30170008")
public class CreditCardCheckController extends ExcelImportController<CreditCardCheck> {
    @Resource
    private CreditCardCheckManager creditCardCheckManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("/IndepShop_management/self_shop_check/",creditCardCheckManager);
    }
    
    @RequestMapping("/credit_card_check_list")
	public String creditCardCheck() {
		return "IndepShop_management/self_shop_check/credit_card_check_list";
	}

	@RequestMapping("/cash_check_list")
	public String cashCheck() {
		return "IndepShop_management/self_shop_check/cash_check_list";
	}
    
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
	    Map<String, Object> params = this.getQueryParams(req);
	    String companyNo = req.getParameter("companyNo");
	    CreditCardCheck creditCardCheck = creditCardCheckManager.findShopSaleDetailCount(params);
	    List<CreditCardCheck> list = new ArrayList<CreditCardCheck>();
	    if(creditCardCheck.getTotal() > 0){
	    	SimplePage page = new SimplePage(pageNo, pageSize, creditCardCheck.getTotal());
	    	list = creditCardCheckManager.findShopSaleDetailList(page, null, null, params);
	    	list = creditCardCheckManager.setCreditCardCheckProperties(list,companyNo);
	    }
		Map<String, Object> obj = new HashMap<String, Object>();
		obj .put("total", creditCardCheck.getTotal());
		obj.put("rows", list);
		return obj;
    	
    }
    
    @Override
	protected List<CreditCardCheck> queryExportData(Map<String, Object> params) throws ManagerException {
    	List<Integer> businessTypeList = new ArrayList<Integer>();
    	
    	businessTypeList.add(new Integer(0));//0-正常销售 1-跨店销售
		businessTypeList.add(new Integer(1));
		businessTypeList.add(new Integer(2));
		businessTypeList.add(new Integer(6));
		
		params.put("businessTypeList", businessTypeList);
	    String companyNo = (String) params.get("companyNo");
	    CreditCardCheck creditCardCheck = creditCardCheckManager.findShopSaleDetailCount(params);
	    List<CreditCardCheck> list = new ArrayList<CreditCardCheck>();
	    if(creditCardCheck.getTotal() > 0){
	    	SimplePage page = new SimplePage(1, creditCardCheck.getTotal(), creditCardCheck.getTotal());
	    	list = creditCardCheckManager.findShopSaleDetailList(page, null, null, params);
	    	list = creditCardCheckManager.setCreditCardCheckProperties(list,companyNo);
	    }
		return list;
	}

	private Map<String, Object> getQueryParams(HttpServletRequest req) {
		Map<String, Object> params = new HashMap<String, Object>();
		String companyNo = req.getParameter("companyNo");
		String shopNo = req.getParameter("shopNo");
		String startOutDate = req.getParameter("startOutDate");
		String endOutDate = req.getParameter("endOutDate");
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	
    	businessTypeList.add(new Integer(0));//0-正常销售 1-跨店销售
		businessTypeList.add(new Integer(1));
		businessTypeList.add(new Integer(2));
		businessTypeList.add(new Integer(6));
		
		params.put("companyNo", companyNo);
		params.put("shopNo", shopNo);
		params.put("startOutDate", startOutDate);
		params.put("endOutDate", endOutDate);
		params.put("businessTypeList", businessTypeList);
		
		return params;
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<CreditCardCheck> list) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@RequestMapping("/list_child")
	@ResponseBody
	public List<CreditCardCheck> queryShopSaleDetail(@RequestParam("terminalNumber")String terminalNumber,@RequestParam("outDate")String outDate) throws ManagerException{
		if(StringUtils.isEmpty(terminalNumber) || StringUtils.isEmpty(outDate.toString())){
			return null;
		}
		List<CreditCardCheck> CreditCardCheckList = creditCardCheckManager.getShopSaleDetail(terminalNumber,outDate);
		return CreditCardCheckList;
	}
}