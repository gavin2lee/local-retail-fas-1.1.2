package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

import cn.wonhigh.retail.fas.common.model.BillPrePaymentNt;
import cn.wonhigh.retail.fas.manager.BillPrePaymentNtManager;


/**
 * 团购预收款控制层
 * @author admin
 * @date  2014-09-22 12:14:38
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
@RequestMapping("/groupon_prepayment")
@ModuleVerify("30190010")
public class GrouponPrePaymentController extends BaseController<BillPrePaymentNt> {
    
	@Resource
    private BillPrePaymentNtManager billPrePaymentNtManager;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("groupon_prepayment/",billPrePaymentNtManager);
    }
    
    @RequestMapping("/list_main")
    public String billTab() {
    	return "groupon_prepayment/list_tabMain";
    }
    
	/**
	 * 新增
	 * 
	 * @param model 待新增的实体对象
	 * @param request HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException 
	 */
    @Override
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> insert(@ModelAttribute("model") BillPrePaymentNt model,
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			billPrePaymentNtManager.addGroupPrePayment(model);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
}