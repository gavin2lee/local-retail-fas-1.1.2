package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
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

import cn.wonhigh.retail.fas.common.dto.SettleNewStyleDto;
import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;
import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.common.model.SettleNewStyle;
import cn.wonhigh.retail.fas.common.model.SettleNewStyleDtl;
import cn.wonhigh.retail.fas.common.model.SettlePath;
import cn.wonhigh.retail.fas.manager.HeadquarterPriceRuleManager;
import cn.wonhigh.retail.fas.manager.RegionPriceRuleManager;
import cn.wonhigh.retail.fas.manager.SettleNewStyleDtlManager;
import cn.wonhigh.retail.fas.manager.SettleNewStyleManager;
import cn.wonhigh.retail.fas.manager.SettlePathManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 新旧款
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
@RequestMapping("/settle_new_style")
@ModuleVerify("30112001")
public class SettleNewStyleController extends BaseController<SettleNewStyleDto> {
   
	@Resource
    private SettleNewStyleManager settleNewStyleManager;
	
	@Resource
	private SettleNewStyleDtlManager settleNewStyleDtlManager;
	
    @Resource
    private SettlePathManager settlePathManager;
    
    @Resource
    private HeadquarterPriceRuleManager headquarterPriceRuleManager;
    
    @Resource
    private RegionPriceRuleManager regionPriceRuleManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_new_style/",settleNewStyleManager);
    }
    
    @RequestMapping("/settle_new_style_dtl/{styleNo}")
    public ModelAndView settleNewStyleDtl(@PathVariable("styleNo")String styleNo, 
    		HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("styleNo", styleNo);
    	mav.setViewName("settle_new_style/settle_new_style_dtl");
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
    		@RequestParam("styleNo")String styleNo, HttpServletRequest request)
    		throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uniqueSettleNewStyleNo", styleNo);
		String settleNewStyleName = request.getParameter("name");
		if(StringUtils.isNotEmpty(settleNewStyleName)) {
			params.put("uniqueSettleNewStyleName", settleNewStyleName);
		}
		List<SettleNewStyle> list = settleNewStyleManager.findByBiz(null, params);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "分类编码或名称不能重复");
			return result;
		}
		if("ALL".equalsIgnoreCase(styleNo.trim()) || "全部".equals(settleNewStyleName.trim())) {
			result.put("success", false);
			result.put("message", "不允许条件编码为ALL或名称为全部的数据");
			return result;
		}
		JsonUtil<SettleNewStyleDtl> util = new JsonUtil<SettleNewStyleDtl>();
		Map<CommonOperatorEnum, List<SettleNewStyleDtl>> datas = util.convertToMap(request, SettleNewStyleDtl.class);
		if(datas != null && datas.size() > 0) {
			List<SettleNewStyleDtl> lstUpdateDtl = datas.get(CommonOperatorEnum.UPDATED);
			if(null != lstUpdateDtl && lstUpdateDtl.size() > 0) {
				for(SettleNewStyleDtl dtl : lstUpdateDtl) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					if(StringUtils.isNotEmpty(dtl.getSeasonNo()) && !"ALL".equalsIgnoreCase(dtl.getSeasonNo())) {
						queryParams.put("seasonNo", dtl.getSeasonNo());
					}
					queryParams.put("yearCode", dtl.getYearCode());
					List<SettleNewStyleDtl> lstResult = settleNewStyleDtlManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getId().equals(dtl.getId())) {
						result.put("success", false);
						result.put("message", "同一个季节和年份不能出现在多个新旧款数据中");
						return result;
					}
				}
			}
			List<SettleNewStyleDtl> lstInsertDtl = datas.get(CommonOperatorEnum.INSERTED);
			if(null != lstInsertDtl && lstInsertDtl.size() > 0) {
				for(SettleNewStyleDtl dtl : lstInsertDtl) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					if(StringUtils.isNotEmpty(dtl.getSeasonNo()) && !"ALL".equalsIgnoreCase(dtl.getSeasonNo())) {
						queryParams.put("seasonNo", dtl.getSeasonNo());
					}
					queryParams.put("yearCode", dtl.getYearCode());
					List<SettleNewStyleDtl> lstResult = settleNewStyleDtlManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0
							&& StringUtils.isEmpty(id)) {
						result.put("success", false);
						result.put("message", "同一个季节和年份不能出现在多个新旧款数据中");
						return result;
					}
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getStyleNo().equals(styleNo)) {
						result.put("success", false);
						result.put("message", "同一个季节和年份不能出现在多个新旧款数据中");
						return result;
					}
					//判断同一条新旧款数据中是否存在重复的明细数据 如季节：ALL， 年份：2000 和季节：春，年份：2000
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getId().equals(dtl.getId())) {
						result.put("success", false);
						result.put("message", "季节和年份数据有重复,请核对");
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
    	JsonUtil<SettleNewStyleDto> util = new JsonUtil<SettleNewStyleDto>();
		List<SettleNewStyleDto> list = util.convertListWithTypeReference(datas, request,
				SettleNewStyleDto.class);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list == null || list.size() == 0) {
			result.put("success", true);
			return result;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		for(SettleNewStyleDto dto : list) {
			params.put("styleNo", dto.getStyleNo());
			// 判断结算路径中是否有引用
			List<SettlePath> lstPath = settlePathManager.findByBiz(null, params);
			if(lstPath != null && lstPath.size() > 0) {
				result.put("success", false);
				result.put("message", "新旧款[" + dto.getStyleNo() + "]在结算路径中有引用，不能" + operateName);
				return result;
			}
			params.put("styleType", dto.getStyleNo());
			//判断总部加价规则中是否有引用
			List<HeadquarterPriceRule> lstRule = headquarterPriceRuleManager.findByBiz(null, params);
			if(lstRule != null && lstRule.size() > 0) {
				result.put("success", false);
				result.put("message", "新旧款[" + dto.getStyleNo() + "]在总部加价规则中有引用，不能" + operateName);
				return result;
			}
			//判断在地区加价规则中是否有引用
			List<RegionPriceRule> lstRegionRule = regionPriceRuleManager.findByBiz(null, params);
			if(lstRegionRule != null && lstRegionRule.size() > 0) {
				result.put("success", false);
				result.put("message", "新旧款[" + dto.getStyleNo() + "]在地区加价规则中有引用，不能" + operateName);
				return result;
			}
		}
		result.put("success", true);
		return result;
    }
    
    @RequestMapping("/contain_all_item")
    @ResponseBody
    public List<SettleNewStyle> containAllItem(HttpServletRequest request) throws Exception {
    	List<SettleNewStyle> list = new ArrayList<SettleNewStyle>();
    	list.add(new SettleNewStyle("ALL", "全部"));
    	
    	//组装查询参数
    	Map<String, Object> params = new HashMap<String, Object>();
    	String status = request.getParameter("status");
    	if(StringUtils.isNotEmpty(status)) {
    		params.put("status", status);
    	}
    	List<SettleNewStyle> styles = settleNewStyleManager.findByBiz(null, params);
    	if(styles != null && styles.size() > 0) {
    		list.addAll(styles);
    	}
    	return list;
    }
    
    /**
     * 重写导出时的查询数据方法
     * 
     * @param params 请求参数
     * @return List<BrandGroupDto>
     * @throws ManagerException 异常
     */
// 	@Override
// 	protected List<SettleNewStyleDto> queryExportData(Map<String, Object> params) 
// 			throws ManagerException {
// 		int total = settleNewStyleManager.findRelationCount(params);
// 		SimplePage page = new SimplePage(1, total, (int) total);
// 		List<SettleNewStyleDto> list = settleNewStyleManager.findRelationByPage(page, "", "", params);
// 		return list;
// 	} 
 	
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
	public Map<String, Boolean> saveAll(@ModelAttribute("model") SettleNewStyleDto model, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<SettleNewStyleDtl> util = new JsonUtil<SettleNewStyleDtl>();
			Map<CommonOperatorEnum, List<SettleNewStyleDtl>> params = util.convertToMap(request, SettleNewStyleDtl.class);
			settleNewStyleManager.save(model, params);
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
			JsonUtil<SettleNewStyleDto> util = new JsonUtil<SettleNewStyleDto>();
			List<SettleNewStyleDto> list = util.convertListWithTypeReference(deletedList, request,
					SettleNewStyleDto.class);
			settleNewStyleManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
}