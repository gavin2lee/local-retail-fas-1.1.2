package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.SettleCategoryDto;
import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;
import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.common.model.SettleCategory;
import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.common.model.SettlePath;
import cn.wonhigh.retail.fas.manager.HeadquarterPriceRuleManager;
import cn.wonhigh.retail.fas.manager.RegionPriceRuleManager;
import cn.wonhigh.retail.fas.manager.SettleCategoryDtlManager;
import cn.wonhigh.retail.fas.manager.SettleCategoryManager;
import cn.wonhigh.retail.fas.manager.SettlePathManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;

/**
 * 结算大类 
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
@RequestMapping("/settle_category")
@ModuleVerify("30111001")
public class SettleCategoryController extends BaseController<SettleCategoryDto> {
	
    @Resource
    private SettleCategoryManager settleCategoryManager;
    
    @Resource
    private SettleCategoryDtlManager settleCategoryDtlManager;
    
    @Resource
    private SettlePathManager settlePathManager;
    
    @Resource
    private HeadquarterPriceRuleManager headquarterPriceRuleManager;
    
    @Resource
    private RegionPriceRuleManager regionPriceRuleManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_category/",settleCategoryManager);
    }
    
    @RequestMapping("/settle_category_dtl/{settleCategoryNo}")
    public ModelAndView settleCategoryDtl(@PathVariable("settleCategoryNo")String settleCategoryNo, 
    		HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("settleCategoryNo", settleCategoryNo);
    	mav.setViewName("settle_category/settle_category_dtl");
    	return mav;
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
    		@RequestParam("settleCategoryNo")String settleCategoryNo, HttpServletRequest request)
    		throws Exception {
    	String settleCategoryName = request.getParameter("name");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("uniqueSettleCategoryNo", settleCategoryNo);
		if(StringUtils.isNotEmpty(settleCategoryName)) {
			params.put("uniqueSettleCategoryName", settleCategoryName);
		}
		List<SettleCategory> list = settleCategoryManager.findByBiz(null, params);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "单据编码或名称不能重复");
			return result;
		}
		JsonUtil<SettleCategoryDtl> util = new JsonUtil<SettleCategoryDtl>();
		Map<CommonOperatorEnum, List<SettleCategoryDtl>> datas = util.convertToMap(request, SettleCategoryDtl.class);
		if(datas != null && datas.size() > 0) {
			List<SettleCategoryDtl> lstUpdateDtl = datas.get(CommonOperatorEnum.UPDATED);
			if(null != lstUpdateDtl && lstUpdateDtl.size() > 0) {
				for(SettleCategoryDtl dtl : lstUpdateDtl) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("queryCategoryNo", dtl.getCategoryNo());
					List<SettleCategoryDtl> lstResult = settleCategoryDtlManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getId().equals(dtl.getId())) {
						result.put("success", false);
						result.put("message", "同一个大类不能出现在多条数据中");
						return result;
					}
				}
			}
			List<SettleCategoryDtl> lstInsertDtl = datas.get(CommonOperatorEnum.INSERTED);
			if(null != lstInsertDtl && lstInsertDtl.size() > 0) {
				for(SettleCategoryDtl dtl : lstInsertDtl) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("queryCategoryNo", dtl.getCategoryNo());
					List<SettleCategoryDtl> lstResult = settleCategoryDtlManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0 
							&& StringUtils.isEmpty(id)) {
						result.put("success", false);
						result.put("message", "同一个大类不能出现在多条数据中");
						return result;
					}
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getSettleCategoryNo().equals(settleCategoryNo)) {
						result.put("success", false);
						result.put("message", "同一个大类不能出现在多条数据中");
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
    	JsonUtil<SettleCategoryDto> util = new JsonUtil<SettleCategoryDto>();
		List<SettleCategoryDto> list = util.convertListWithTypeReference(datas, request,
				SettleCategoryDto.class);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list == null || list.size() == 0) {
			result.put("success", true);
			return result;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		for(SettleCategoryDto dto : list) {
			params.put("settleCategoryNo", dto.getSettleCategoryNo());
			// 判断结算路径中是否有引用
			List<SettlePath> lstPath = settlePathManager.findByBiz(null, params);
			if(lstPath != null && lstPath.size() > 0) {
				result.put("success", false);
				result.put("message", "大类[" + dto.getSettleCategoryNo() + "]在结算路径中有引用，不能" + operateName);
				return result;
			}
			params.put("categoryNo", dto.getSettleCategoryNo());
			//判断总部加价规则中是否有引用
			List<HeadquarterPriceRule> lstRule = headquarterPriceRuleManager.findByBiz(null, params);
			if(lstRule != null && lstRule.size() > 0) {
				result.put("success", false);
				result.put("message", "大类[" + dto.getSettleCategoryNo() + "]在总部加价规则中有引用，不能" + operateName);
				return result;
			}
			//判断在地区加价规则中是否有引用
			List<RegionPriceRule> lstRegionRule = regionPriceRuleManager.findByBiz(null, params);
			if(lstRegionRule != null && lstRegionRule.size() > 0) {
				result.put("success", false);
				result.put("message", "大类[" + dto.getCategoryNo() + "]在地区加价规则中有引用，不能" + operateName);
				return result;
			}
		}
		result.put("success", true);
		return result;
    }
    
    /**
	 * 新增/修改(主表和明细数据)
	 * 
	 * @param model 待新增的主表数据
	 * @param request HttpServletRequest
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Boolean> saveAll(@ModelAttribute("model") SettleCategoryDto model, 
			HttpServletRequest request) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<SettleCategoryDtl> util = new JsonUtil<SettleCategoryDtl>();
			Map<CommonOperatorEnum, List<SettleCategoryDtl>> params = util.convertToMap(request, 
					SettleCategoryDtl.class);
			settleCategoryManager.save(model, params);
			map.put("success", true);
		} catch(Exception e) {
			logger.error("保存结算大类错误。。");
			map.put("success", false);
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
			JsonUtil<SettleCategoryDto> util = new JsonUtil<SettleCategoryDto>();
			List<SettleCategoryDto> list = util.convertListWithTypeReference(deletedList, request,
					SettleCategoryDto.class);
			settleCategoryManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			logger.error("删除结算大类错误。。");
			map.put("success", false);
		}
		return map;
	}
	
    /**
     * 重写导出时的查询数据方法
     * 
     * @param params 请求参数
     * @return List<BrandGroupDto>
     * @throws ManagerException 异常
     */
// 	@Override
// 	protected List<SettleCategoryDto> queryExportData(Map<String, Object> params) throws ManagerException {
// 		int total = settleCategoryManager.findRelationCount(params);
// 		SimplePage page = new SimplePage(1, total, (int) total);
// 		List<SettleCategoryDto> list = settleCategoryManager.findRelationByPage(page, "", "", params);
// 		return list;
// 	} 
}