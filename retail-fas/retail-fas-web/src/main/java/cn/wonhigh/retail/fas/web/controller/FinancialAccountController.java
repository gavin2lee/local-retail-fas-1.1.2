package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
/**
 *  公司账套维护
 * @author ouyang.zm
 * @date  2014-08-28 10:14:44
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
@RequestMapping("/base_setting/financial_account")
@ModuleVerify("30100004")
public class FinancialAccountController extends BaseController<FinancialAccount>  {
    @Resource
    private FinancialAccountManager financialAccountManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/financial_account/",financialAccountManager);
    }
    
    /**
     * 校验是否包含重复数据
     * @param financialAccount
     * @return
     * @throws ManagerException 
     */
    @ResponseBody
    @RequestMapping(value="/check_Repect", method=RequestMethod.POST)
    public boolean checkrepeatData(@ModelAttribute("model") FinancialAccount model) throws ManagerException{
    	Map<String,Object> params=new HashMap<String, Object>();
    	String queryCondition = "AND ( C.COMPANY_NO = '"+model.getCompanyNo()+"'  OR A.NC_ID = '"+model.getNcId()+"')";
    	params.put("queryCondition", queryCondition);
    	List<FinancialAccount> list=financialAccountManager.findByBiz(model, params);
		if (list != null && list.size() > 0) {
			for (FinancialAccount m : list) {
				if (!m.getId().equals(model.getId())) {
					return true;
				}
			}
		}
		return false;
    }
    
    /**
     * 获取基本信息
     * @param request HttpServletRequest
     * @param model Model
     * @return FinancialAccount对象
     */
    @ResponseBody
    @RequestMapping("/getBaseInfo")
    public FinancialAccount getBaseInfo(HttpServletRequest request, Model model) {
    	Map<String, Object> params = builderParams(request, model);
    	FinancialAccount financialAccount = financialAccountManager.getBaseInfo(params);
    	// 需要返回一个新对象，否则页面会报错
    	if(financialAccount == null) {
    		financialAccount = new FinancialAccount();
    	}
		return financialAccount;
    }
}