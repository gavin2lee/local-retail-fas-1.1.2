package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.web.controller.BaseCrudController.CrudInfo;

import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.CompanyPeriodBalanceManager;
import cn.wonhigh.retail.fas.web.utils.OwerGuestGenerateThread;

@Controller
@RequestMapping("/blk_company_period_balance")
@ModuleVerify("30120304")
public class BLkCompanyPeriodBalanceController extends BaseController<CompanyPeriodBalance>  {
	
	@Resource
	private CompanyPeriodBalanceManager companyPeriodBalanceManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("blk_company_period_balance/",companyPeriodBalanceManager);
	}
	
	@RequestMapping({"/list"})
    public String list() {
    	return "company_period_balance/blk_list";
    }
	
    @RequestMapping(value = "/generate_ower_guest.json")
	@ResponseBody
	public  Map<String, Object> generateCompanyOwerGuest(HttpServletRequest req, Model model){
    	Map<String, Object> obj = new HashMap<String, Object>();
    	obj.put("success", true);
    	
    	Map<String, Object> params = builderParams(req, model);
    	SystemUser systemUser = CurrentUser.getCurrentUser();
		new Thread(new OwerGuestGenerateThread(companyPeriodBalanceManager, params, systemUser)).start();
		
    	return obj;
    }

}
