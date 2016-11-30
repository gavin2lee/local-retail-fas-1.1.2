package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PreWarnTemplate;
import cn.wonhigh.retail.fas.manager.PreWarnTemplateManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:49:31
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
@RequestMapping("/pre_warn_template")
public class PreWarnTemplateController extends BaseCrudController<PreWarnTemplate> {
    @Resource
    private PreWarnTemplateManager preWarnTemplateManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("pre_warn_template/",preWarnTemplateManager);
    }
    
    @RequestMapping("/check")
    @ResponseBody
    public Map<String, Object> checkMessage(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String checkType = req.getParameter("checkType");
    	preWarnTemplateManager.check(checkType);
    	map.put("status", true);
    	return map;
    }
     
    
}