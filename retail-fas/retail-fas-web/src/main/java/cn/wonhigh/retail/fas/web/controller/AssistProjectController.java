package cn.wonhigh.retail.fas.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

import cn.wonhigh.retail.fas.common.model.AssistProject;
import cn.wonhigh.retail.fas.manager.AssistProjectManager;


/**
 * 辅助项目设置 
 * @author ouyang.zm
 * @date  2014-08-27 09:17:07
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
@RequestMapping("/base_setting/assist_project")
@ModuleVerify("30100003")
public class AssistProjectController extends BaseController<AssistProject> {
    @Resource
    private AssistProjectManager assistProjectManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/assist_project/",assistProjectManager);
    }
    
    /**
     * 校验编码是否重复
     * @param financialAccount
     * @return
     * @throws ManagerException 
     */
    @ResponseBody
    @RequestMapping(value="/check_Repect", method=RequestMethod.POST)
    public boolean checkrepeatData(@ModelAttribute("model") AssistProject model) throws ManagerException{
    	Map<String,Object> params=new HashMap<String, Object>();
    	String queryCondition = "AND ( A.CODE = '"+model.getCode()+"'  OR A.NAME = '"+model.getName()+"')";
    	params.put("queryCondition", queryCondition);
    	List<AssistProject> list=assistProjectManager.findByBiz(model, params);
		if (list != null && list.size() > 0) {
			for (AssistProject m : list) {
				if (!m.getId().equals(model.getId())) {
					return true;
				}
			}
		}
		return false;
    }
}