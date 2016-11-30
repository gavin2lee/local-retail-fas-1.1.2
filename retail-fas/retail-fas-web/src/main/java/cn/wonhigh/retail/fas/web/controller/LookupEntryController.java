package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.LookupEnum;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.manager.LookupEntryManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 数据字典
 * @author yang.y
 * @date  2014-08-26 15:21:01
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
@RequestMapping("/lookup_entry")
public class LookupEntryController extends BaseCrudController<LookupEntry> {
	
    @Resource
    private LookupEntryManager lookupEntryManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("lookup_entry/",lookupEntryManager);
    }
    
    /**
     * 查询数据字典下的所有季节
     * 
     * @param request HttpServletRequest
     * @return 季节集合
     * @throws Exception 异常
     */
    @RequestMapping("/seasons")
    @ResponseBody
    public List<LookupEntry> listSeason(HttpServletRequest request) throws Exception {
    	List<LookupEntry> list = new ArrayList<LookupEntry>();
    	list.add(new LookupEntry("ALL", "全部"));
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("lookupCode", LookupEnum.SEASON.getCode());
    	List<LookupEntry> seasons = lookupEntryManager.findByBiz(null, params);
    	if(seasons != null && seasons.size() > 0) {
    		list.addAll(seasons);
    	}
    	return list;
    }
    
    /**
     * 查询数据字典下的所有年份
     * 
     * @param request HttpServletRequest
     * @return 年份集合
     * @throws Exception 异常
     */
    @RequestMapping("/years")
    @ResponseBody
    public List<LookupEntry> listYear(HttpServletRequest request) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("lookupCode", LookupEnum.YEAR.getCode());
    	List<LookupEntry> list = lookupEntryManager.findByBiz(null, params);
    	return list;
    }
    
    /**
     * 查询数据字典下的单据状态
     * 
     * @param request HttpServletRequest
     * @return 单据状态集合
     * @throws Exception 异常
     */
    @RequestMapping("/getLookupEntry")
    @ResponseBody
    public List<LookupEntry> listStatus(HttpServletRequest request) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	String lookupId = request.getParameter("lookupId");
    	params.put("lookupId", lookupId);
    	List<LookupEntry> list = lookupEntryManager.findByBiz(null, params);
    	return list;
    }
}