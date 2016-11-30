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

import cn.wonhigh.retail.fas.common.model.FinancialCategoryDtl;
import cn.wonhigh.retail.fas.manager.FinancialCategoryDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 财务大类明细
 * @author Administrator
 * @date  2014-12-23 10:38:39
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/financial_category_dtl")
public class FinancialCategoryDtlController extends BaseCrudController<FinancialCategoryDtl> {
	
    @Resource
    private FinancialCategoryDtlManager financialCategoryDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("financial_category_dtl/",financialCategoryDtlManager);
    }
    
    /**
     * 通过结算大类编码，查询该结算大类的明细数据
     * 
     * @param financialCategoryNo 财务大类编码
     * @param request HttpServletRequest
     * @return List<FinancialCategoryDtl>
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_financial_category_dtl")
	@ResponseBody
	public List<FinancialCategoryDtl> querySettleCategoryDtl(@RequestParam("financialCategoryNo")String financialCategoryNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("financialCategoryNo", financialCategoryNo);
		List<FinancialCategoryDtl> list = this.financialCategoryDtlManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping(value = "/get_biz")
	@ResponseBody
	public List<FinancialCategoryDtl> getBiz(FinancialCategoryDtl modelType, HttpServletRequest req,Model model)
			throws ManagerException {
		String financialCategoryNo = req.getParameter("financialCategoryNo");
    	if(StringUtils.isNotEmpty(financialCategoryNo)) {
    		return super.getBiz(modelType, req, model);
    	}
    	return new ArrayList<FinancialCategoryDtl>();
	}
}