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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.ParamControlTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.ParamMain;
import cn.wonhigh.retail.fas.common.model.SystemParamSet;
import cn.wonhigh.retail.fas.manager.ParamMainManager;
import cn.wonhigh.retail.fas.manager.SystemParamSetManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 系统参数设置
 * @author user
 * @date  2015-10-22 10:32:22
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
@RequestMapping("/base_setting/system_param_set")
@ModuleVerify("30100018")
public class SystemParamSetController extends BaseController<SystemParamSet> {
    @Resource
    private SystemParamSetManager systemParamSetManager;
    
    @Resource
    private ParamMainManager paramMainManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/system_param_set/",systemParamSetManager);
    }
    
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String paramFlag = StringUtils.isEmpty(req.getParameter("paramFlag")) ? "" : String.valueOf(req.getParameter("paramFlag"));
		Map<String, Object> params = builderParams(req, model);
		if (StringUtils.isNotEmpty(paramFlag) && paramFlag.equals("include")) {
			params.put("queryCondition", "AND PARAM_CODE IN ('Effective_Date')");
		} else if (StringUtils.isNotEmpty(paramFlag) && paramFlag.equals("exclusive")) {
			params.put("queryCondition", "AND PARAM_CODE NOT IN ('Effective_Date')");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.systemParamSetManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillSaleBalance> list = this.systemParamSetManager.findByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
    /**
	 * 控制级别
	 * @return
	 */
	@RequestMapping(value = "/getControlType")
	@ResponseBody
	public List<Map<String, String>> getTransferOutBillStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		ParamControlTypeEnums[] enumData = ParamControlTypeEnums.values();
		for (ParamControlTypeEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("typeNo", x.getTypeNo().toString());
			map.put("typeName", x.getTypeName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据key 及公司编号,获取系统参数值
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value = "/findSystemParamByParma")
	@ResponseBody
	public String findSystemParamByParma(HttpServletRequest req, Model model) {
		String paramValue = "1";
		try{
			Map<String, Object> params = builderParams(req, model);
			params.put("status", 1);
			paramValue = systemParamSetManager.findSystemParamByParma(null, params);
		}catch(ManagerException e){
		}
		return paramValue;
	}
	
	/**
	 * 根据key 及公司编号,获取系统参数值
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value = "/findSystemParamListByParma")
	@ResponseBody
	public List<SystemParamSet> findSystemParamListByParma(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> params = builderParams(req, model);
		List<SystemParamSet> lstItem = systemParamSetManager.findSystemParamSetByParma(null, params);
		return lstItem;
	}

    /**
	 * 控制级别
	 * @return
     * @throws ManagerException 
	 */
	@RequestMapping(value = "/getSystemParamList")
	@ResponseBody
	public List<Map<String, String>> getSystemParamList() throws ManagerException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		List<ParamMain> lstItem = paramMainManager.findByBiz(null, null);
		for (ParamMain x : lstItem) {
			map = new HashMap<String, String>();
			map.put("paramCode", x.getParamCode().toString());
			map.put("paramName", x.getParamName());
			list.add(map);
		}
		return list;
	}
}