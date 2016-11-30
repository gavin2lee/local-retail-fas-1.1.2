package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.SettleBrandGroupRel;
import cn.wonhigh.retail.fas.manager.SettleBrandGroupRelManager;


/**
 * 品牌组与品牌关联
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
@RequestMapping("/settle_brand_group_rel")
public class SettleBrandGroupRelController extends BaseController<SettleBrandGroupRel> {
	
	@Resource
    private SettleBrandGroupRelManager settleBrandGroupRelManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_brand_group_rel/",settleBrandGroupRelManager);
    }
    
    @RequestMapping("/query")
    @ResponseBody
    public List<SettleBrandGroupRel> queryByGroupNo(@RequestParam("groupNo")String groupNo, 
    		HttpServletRequest request) throws Exception {
    	if(StringUtils.isEmpty(groupNo)) {
    		return new ArrayList<SettleBrandGroupRel>(1);
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupNo", groupNo);
		List<SettleBrandGroupRel> list = settleBrandGroupRelManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping("/mulit_group_no")
    @ResponseBody
    public List<SettleBrandGroupRel> queryByMulitGroupNo(@RequestParam("mulitGroupNo")String mulitGroupNo, 
    		HttpServletRequest request) throws Exception {
		if(StringUtils.isNotBlank(mulitGroupNo)) {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("mulitGroupNo", ","+mulitGroupNo);
    		List<SettleBrandGroupRel> list = settleBrandGroupRelManager.findByBiz(null, params);
    		return list;
		}
		return null;
    }
}