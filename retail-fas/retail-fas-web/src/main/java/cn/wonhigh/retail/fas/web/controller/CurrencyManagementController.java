package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.manager.CurrencyManagementManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 币种管理
 * 
 * @author ouyang.zm
 * @date 2014-09-01 17:18:41
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/base_setting/currency_management")
@ModuleVerify("30100006")
public class CurrencyManagementController extends BaseController<CurrencyManagement> {
	@Resource
	private CurrencyManagementManager currencyManagementManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("base_setting/currency_management/", currencyManagementManager);
	}

	/**
	 * 校验是否包含重复数据
	 * 
	 * @param financialAccount
	 * @return
	 * @throws ManagerException
	 */
	@ResponseBody
	@RequestMapping(value = "/check_Repect", method = RequestMethod.POST)
	public Map<String, Object> checkRepeatData(@ModelAttribute("model") CurrencyManagement model)
			throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		String queryCondition = null;
		String msg = "";
		int standard = 0;
		if (model.getStandardMoney() == null || model.getStandardMoney() == 0) {
			standard = 0;
		} else {
			standard = 1;
		}
		if (standard == 1) {
			queryCondition = "AND ( CURRENCY_CODE = '" + model.getCurrencyCode() + "'  OR CURRENCY_NAME = '"
					+ model.getCurrencyName() + "' OR STANDARD_MONEY='" + model.getStandardMoney() + "')";
			msg = "币种编码或名称重复，且只能有一个本位币，请重新输入!";
		} else {
			queryCondition = "AND ( CURRENCY_CODE = '" + model.getCurrencyCode() + "'  OR CURRENCY_NAME = '"
					+ model.getCurrencyName() + "')";
			msg = "币种编码或名称重复，请重新输入!";
		}
		params.put("queryCondition", queryCondition);
		List<CurrencyManagement> list = currencyManagementManager.findByBiz(model, params);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (list != null && list.size() > 0) {
			for (CurrencyManagement m : list) {
				if (!m.getId().equals(model.getId())) {
					resultMap.put("flag", true);
					resultMap.put("msg", msg);
				}
			}
		} else {
			resultMap.put("flag", false);
		}
		return resultMap;
	}

	/**
	 * 转到查询币种
	 */
	@RequestMapping("/toSearchCurrency")
	public String toSearchCurrency() {
		return "base_setting/currency_management/searchCurrency";
	}

	@RequestMapping("/getCurrency")
	@ResponseBody
	public List<CurrencyManagement> getJsonData(HttpServletRequest req) throws ManagerException {
		return currencyManagementManager.findByBiz(null, null);
	}

	/**
	 * 转到汇率设置
	 * 
	 * @return
	 */
	@RequestMapping("/list_exchange_rate")
	public String toSearchCompany() {
		return "base_setting/currency_management/list_exchange_rate";
	}
}