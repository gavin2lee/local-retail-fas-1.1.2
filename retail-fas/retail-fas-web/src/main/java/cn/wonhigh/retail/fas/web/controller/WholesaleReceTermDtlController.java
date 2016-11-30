package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.WholesaleReceTermDtlDto;
import cn.wonhigh.retail.fas.manager.WholesaleReceTermDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 地区批发收款条款-表体
 * @author yang.y
 * @date  2014-09-17 18:00:36
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
@RequestMapping("/wholesale_rece_term_dtl")
public class WholesaleReceTermDtlController extends BaseController<WholesaleReceTermDtlDto> {
   
	@Resource
    private WholesaleReceTermDtlManager wholesaleReceTermDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_rece_term_dtl/",wholesaleReceTermDtlManager);
    }
    
    @RequestMapping(value = "/get_biz")
   	@ResponseBody
   	public List<WholesaleReceTermDtlDto> getBiz(WholesaleReceTermDtlDto modelType, HttpServletRequest req, Model model)
   			throws ManagerException {
   		String termNo = req.getParameter("termNo");
       	if(StringUtils.isNotEmpty(termNo)) {
       		return super.getBiz(modelType, req, model);
       	}
       	return new ArrayList<WholesaleReceTermDtlDto>();
   	}
    @RequestMapping(value = "/get_biz_view")
   	@ResponseBody
   	public List<WholesaleReceTermDtlDto> getBizView(WholesaleReceTermDtlDto modelType, HttpServletRequest req, Model model)
   			throws ManagerException {
   		String termNo = req.getParameter("termNo");
       	if(StringUtils.isNotEmpty(termNo)) {
       		return super.getBiz(modelType, req, model);
       	}
       	return new ArrayList<WholesaleReceTermDtlDto>();
   	}
}