package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.ParamMainDto;
import cn.wonhigh.retail.fas.common.model.ParamDtl;
import cn.wonhigh.retail.fas.common.model.ParamMain;
import cn.wonhigh.retail.fas.manager.ParamMainManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 数据字典参数定义
 * @author user
 * @date  2015-10-21 10:32:05
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
@RequestMapping("/base_setting/digital_dict")
@ModuleVerify("30100020")
public class ParamMainController extends BaseController<ParamMain> {
	
    @Resource
    private ParamMainManager paramMainManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/digital_dict/",paramMainManager);
    }
    
    /**
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public String mainTab() {
		return "base_setting/digital_dict/query_page";
	}
    
    /**
	 * 新增/修改(主表和明细数据)
	 * @param model 待新增的主表数据
	 * @param request HttpServletRequest
	 * @return ResponseEntity
     * @throws ManagerException 
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Boolean> saveAll(@ModelAttribute("model") ParamMain model, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<ParamDtl> util = new JsonUtil<ParamDtl>();
			Map<CommonOperatorEnum, List<ParamDtl>> params = util.convertToMap(request, 
					ParamDtl.class);
			paramMainManager.save(model, params);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
     * @param model Model
     * @param request HttpServletRequest
     * @return 存放total和row数据的map
     */
    @RequestMapping("/query.json")
    @ResponseBody
    public Map<String, Object> query(Model model, HttpServletRequest request) {
    	int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
		Map<String, Object> params = builderParams(request, model);
		int total = paramMainManager.findRelationCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<ParamMainDto> list = paramMainManager.findRelationByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
	/**
	 * 删除操作
	 * @param request HttpServletRequest
	 * @return  Map<String, Boolean>
	 * @throws Exception 异常
	 */
	@RequestMapping("/do_delete")
	@ResponseBody
	public Map<String, Boolean> doDelete(HttpServletRequest request) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String deletedList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request
					.getParameter("deleted");
			JsonUtil<ParamMainDto> util = new JsonUtil<ParamMainDto>();
			List<ParamMainDto> list = util.convertListWithTypeReference(deletedList, request,
					ParamMainDto.class);
			paramMainManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			logger.error("删除错误。。");
			map.put("success", false);
		}
		return map;
	}
}