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

import cn.wonhigh.retail.fas.common.model.SettleNewStyleDtl;
import cn.wonhigh.retail.fas.manager.SettleNewStyleDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 新旧款明细
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
@RequestMapping("/settle_new_style_dtl")
public class SettleNewStyleDtlController extends BaseController<SettleNewStyleDtl> {
    
	@Resource
    private SettleNewStyleDtlManager settleNewStyleDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_new_style_dtl/",settleNewStyleDtlManager);
    }
    
    /**
     * 通过新旧款编码，查询该新旧款的明细数据
     * 
     * @param styleNo 新旧款编码
     * @param request HttpServletRequest
     * @return 新旧款明细列表
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_settle_new_style")
	@ResponseBody
	public List<SettleNewStyleDtl> querySettleCategoryDtl(@RequestParam("styleNo")String styleNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("styleNo", styleNo);
		List<SettleNewStyleDtl> list = this.settleNewStyleDtlManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping(value = "/get_biz")
	@ResponseBody
	public List<SettleNewStyleDtl> getBiz(SettleNewStyleDtl modelType, HttpServletRequest req,Model model)
			throws ManagerException {
    	String styleNo = req.getParameter("styleNo");
    	if(StringUtils.isNotEmpty(styleNo)) {
    		return super.getBiz(modelType, req, model);
    	}
    	return new ArrayList<SettleNewStyleDtl>();
	}
}