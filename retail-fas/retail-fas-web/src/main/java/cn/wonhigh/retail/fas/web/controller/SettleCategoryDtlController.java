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

import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.manager.SettleCategoryDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 结算大类明细
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
@RequestMapping("/settle_category_dtl")
public class SettleCategoryDtlController extends BaseController<SettleCategoryDtl> {
	
    @Resource
    private SettleCategoryDtlManager settleCategoryDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_category_dtl/",settleCategoryDtlManager);
    }
    
    /**
     * 通过结算大类编码，查询该结算大类的明细数据
     * 
     * @param settleCategoryNo 结算路径编码
     * @param request HttpServletRequest
     * @return 结算路径明细列表
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_settle_category_dtl")
	@ResponseBody
	public List<SettleCategoryDtl> querySettleCategoryDtl(@RequestParam("settleCategoryNo")String settleCategoryNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("settleCategoryNo", settleCategoryNo);
		List<SettleCategoryDtl> list = this.settleCategoryDtlManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping(value = "/get_biz")
	@ResponseBody
	public List<SettleCategoryDtl> getBiz(SettleCategoryDtl modelType, HttpServletRequest req,Model model)
			throws ManagerException {
		String settleCategoryNo = req.getParameter("settleCategoryNo");
    	if(StringUtils.isNotEmpty(settleCategoryNo)) {
    		return super.getBiz(modelType, req, model);
    	}
    	return new ArrayList<SettleCategoryDtl>();
	}
}