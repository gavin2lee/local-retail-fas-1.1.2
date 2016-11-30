package cn.wonhigh.retail.fas.web.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.ExchangeRateSetup;
import cn.wonhigh.retail.fas.manager.ExchangeRateSetupManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 汇率设置
 * @author ouyang.zm
 * @date  2014-09-02 10:01:57
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
@RequestMapping("/base_setting/exchange_rate_setup")
public class ExchangeRateSetupController extends BaseController<ExchangeRateSetup> {
    @Resource
    private ExchangeRateSetupManager exchangeRateSetupManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/exchange_rate_setup/",exchangeRateSetupManager);
    }
    
    /**
     * 校验是否包含重复数据
     * @param financialAccount
     * @return
     * @throws ManagerException 
     */
    @ResponseBody
    @RequestMapping(value="/check_Repect", method=RequestMethod.POST)
    public boolean checkrepeatData(@ModelAttribute("model") ExchangeRateSetup model) throws ManagerException{
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String effectDate=null;
    	String queryCondition="";
		if (model.getEffectiveDate() != null) {
			effectDate = formatter.format(model.getEffectiveDate());
			queryCondition = "AND ( EXCHANGE_RATE_CODE = '" + model.getExchangeRateCode() + "' OR (SOURCE_CURRENCY = '"
					+ model.getSourceCurrency() + "'AND TARGET_CURRENCY='" + model.getTargetCurrency()
					+ "' AND EFFECTIVE_DATE='" + effectDate + "' AND COMPANY_NO='" + model.getCompanyNo() + "'))";
		} else {
			queryCondition = "AND ( EXCHANGE_RATE_CODE = '" + model.getExchangeRateCode() + "' OR (SOURCE_CURRENCY = '"
					+ model.getSourceCurrency() + "'AND TARGET_CURRENCY='" + model.getTargetCurrency()
					+ "' AND EFFECTIVE_DATE IS NULL AND COMPANY_NO='" + model.getCompanyNo() + "'))";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("queryCondition", queryCondition);
		List<ExchangeRateSetup> list = exchangeRateSetupManager.findByBiz(
				model, params);
		if (list != null && list.size() > 0) {
			for (ExchangeRateSetup m : list) {
				if (!m.getId().equals(model.getId())) {
					return true;
				}
			}
		}
		return false;
    }
}