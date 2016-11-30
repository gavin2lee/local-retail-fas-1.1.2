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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.FinancialCategoryCommonDto;
import cn.wonhigh.retail.fas.common.dto.FinancialCategoryDto;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;
import cn.wonhigh.retail.fas.common.model.FinancialCategoryDtl;
import cn.wonhigh.retail.fas.manager.FinancialCategoryDtlManager;
import cn.wonhigh.retail.fas.manager.FinancialCategoryManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 财务大类
 * @author yang.y
 * @date  2014-12-23 10:38:39
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
@RequestMapping("/financial_category")
@ModuleVerify("30100014")
public class FinancialCategoryController extends BaseController<FinancialCategoryDto> {
	
    @Resource
    private FinancialCategoryManager financialCategoryManager;
    
    @Resource
    private FinancialCategoryDtlManager financialCategoryDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("financial_category/",financialCategoryManager);
    }
    
    /**
     * 校验数据
     * @param id 主键
     * @param styleNo 编码
     * @return boolean true or false
     * @throws Exception  异常
     */
    @RequestMapping(value="/validate_data", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validateData(@RequestParam("id")String id, 
    		@RequestParam("financialCategoryNo")String financialCategoryNo, HttpServletRequest request)
    		throws Exception {
//    	String financialCategoryName = request.getParameter("name");
    	String companyNo = request.getParameter("companyNo");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("uniqueFinancialCategoryNo", financialCategoryNo);
//		if(StringUtils.isNotEmpty(financialCategoryName)) {
//			params.put("uniqueFinancialCategoryName", financialCategoryName);
//		}
		List<FinancialCategory> list = financialCategoryManager.findByBiz(null, params);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "分类编码不能重复");
			return result;
		}
		JsonUtil<FinancialCategoryDtl> util = new JsonUtil<FinancialCategoryDtl>();
		Map<CommonOperatorEnum, List<FinancialCategoryDtl>> datas = util.convertToMap(request, FinancialCategoryDtl.class);
		if(datas != null && datas.size() > 0) {
			List<FinancialCategoryDtl> lstUpdateDtl = datas.get(CommonOperatorEnum.UPDATED);
			if(null != lstUpdateDtl && lstUpdateDtl.size() > 0) {
				for(FinancialCategoryDtl dtl : lstUpdateDtl) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("categoryNo", dtl.getCategoryNo());
					queryParams.put("companyNo", companyNo);
					List<FinancialCategoryDtl> lstResult = financialCategoryDtlManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getId().equals(dtl.getId())) {
						result.put("success", false);
						result.put("message", "商品大类"+dtl.getName()+"已经存在该公司中了");
						return result;
					}
				}
			}
			List<FinancialCategoryDtl> lstInsertDtl = datas.get(CommonOperatorEnum.INSERTED);
			if(null != lstInsertDtl && lstInsertDtl.size() > 0) {
				for(FinancialCategoryDtl dtl : lstInsertDtl) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("categoryNo", dtl.getCategoryNo());
					queryParams.put("companyNo", companyNo);
					List<FinancialCategoryDtl> lstResult = financialCategoryDtlManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0 
							&& StringUtils.isEmpty(id)) {
						result.put("success", false);
						result.put("message", "商品大类"+dtl.getName()+"已经存在该公司中了");
						return result;
					}
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getFinancialCategoryNo().equals(financialCategoryNo)) {
						result.put("success", false);
						result.put("message", "商品大类"+dtl.getName()+"已经存在该公司中了");
						return result;
					}
				}
			}
		}
		result.put("success", true);
		return result;
    }
    
    /**
     * 校验是否可删除
     * @param request HttpServletRequest
     * @return Map<String, Object>
     * @throws Exception 异常
     */
    @RequestMapping("/check_del")
    @ResponseBody
    public Map<String, Object> checkDel(HttpServletRequest request) throws Exception {
		String deletedList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request
				.getParameter("deleted");
		return this.checkRequestData(request, deletedList, "删除");
    }
    
    /**
     * 校验是否可停用
     * @param request HttpServletRequest
     * @return Map<String, Object>
     * @throws Exception 异常
     */
    @RequestMapping("/check_unable")
    @ResponseBody
    public Map<String, Object> checkUnable(HttpServletRequest request) throws Exception {
    	String unableList = StringUtils.isEmpty(request.getParameter("unabled")) ? "" : request
				.getParameter("unabled");
		return this.checkRequestData(request, unableList, "停用");
    }
    
    /**
     * 校验是否可进行删除或停用操作
     * @param request HttpServletRequest
     * @param datas 请求数据
     * @return Map<String, Object>
     * @throws Exception 异常
     */
    private Map<String, Object> checkRequestData(HttpServletRequest request, String datas, String operateName) 
    	throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
    }
    
    /**
	 * 新增/修改(主表和明细数据)
	 * 
	 * @param model 待新增的主表数据
	 * @param request HttpServletRequest
	 * @return ResponseEntity
     * @throws ManagerException 
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Boolean> saveAll(@ModelAttribute("model") FinancialCategoryDto model, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<FinancialCategoryDtl> util = new JsonUtil<FinancialCategoryDtl>();
			Map<CommonOperatorEnum, List<FinancialCategoryDtl>> params = util.convertToMap(request, 
					FinancialCategoryDtl.class);
			financialCategoryManager.save(model, params);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
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
			JsonUtil<FinancialCategoryDto> util = new JsonUtil<FinancialCategoryDto>();
			List<FinancialCategoryDto> list = util.convertListWithTypeReference(deletedList, request,
					FinancialCategoryDto.class);
			financialCategoryManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return map;
	}
	
	@RequestMapping(value = "/getAllCateInfo")
	@ResponseBody
	public List<FinancialCategoryCommonDto> getAllCateInfo(HttpServletRequest req,Model model)throws ManagerException{
		
		List<FinancialCategoryCommonDto> list = new ArrayList<FinancialCategoryCommonDto>();
		Map<String, Object> params = builderParams(req, model);
		
		if(params == null) {
			return list;
		}
		
//		String companyNo = params.get("companyNo") == null ? "" : params.get("companyNo").toString();
//		if(StringUtils.isEmpty(companyNo)) {
//			return list;
//		}
		return financialCategoryManager.getAllCateInfo(params);
	}
	
	@RequestMapping(value = "/getCateInfoByCondition")
	@ResponseBody
	public List<FinancialCategory> getCateInfoByCondition(HttpServletRequest req,Model model)throws ManagerException{
		
		List<FinancialCategory> list = new ArrayList<FinancialCategory>();
		Map<String, Object> params = builderParams(req, model);
		
		if(params == null) {
			return list;
		}
		
		return financialCategoryManager.findCateInfoByCateNo(params);
	}
}