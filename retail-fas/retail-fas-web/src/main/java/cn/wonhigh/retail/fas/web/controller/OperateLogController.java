package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.manager.OperateLogManager;
import cn.wonhigh.retail.fas.web.utils.OperateLogHelper;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-20 11:53:39
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
@RequestMapping("/operate_log")
public class OperateLogController extends BaseCrudController<OperateLog> {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

    @Resource
    private OperateLogManager operateLogManager;
    
    @Resource
    private OperateLogHelper operateLogHelper;

    @Override
    public CrudInfo init() {
        return new CrudInfo("operate_log/",operateLogManager);
    }
    
    /**
     * 进入操作日志明细页面
     * @param request HttpServletRequest
     * @return ModelAndView
     */
    @RequestMapping("/operate_detail")
    public ModelAndView operateDetail(HttpServletRequest request) {
    	String dataNo = request.getParameter("dataNo");
    	String moduleNo = request.getParameter("moduleNo");
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("operate_log/operate_detail");
    	mav.addObject("dataNo", dataNo);
    	mav.addObject("moduleNo", moduleNo);
    	return mav;
    }
    
    /**
     * 查看日志数据(包括查看该单据group by后或者所有数据)
     * @param request HttpServletRequest
     * @return 数据集合
     * @throws ManagerException 异常
     */
    @RequestMapping("/find_list")
    @ResponseBody
    public List<OperateLog> findList(HttpServletRequest request) throws ManagerException {
    	List<OperateLog> lstLog = null;
    	String showProcessData = request.getParameter("showProcessData");
    	String dataNo = request.getParameter("dataNo");
    	String moduleNo = request.getParameter("moduleNo");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("dataNo", dataNo);
    	params.put("moduleNo", moduleNo);
    	if(StringUtils.isNotEmpty(showProcessData)) {
    		lstLog = operateLogManager.selectProcessData(params);
    	} else {
    		lstLog = operateLogManager.findByBiz(null, params);
    	}
    	return lstLog;
    }
    
    @RequestMapping("/test")
    public String testAdd(HttpServletRequest request) throws ManagerException {
    	try {
    		operateLogHelper.addVerifyPost(request, "test", OperateLogEnums.OperateModule.TEST.getModuleNo(),
    				1, "一级审核", "111");
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
    	}
    	return null;
    }
}