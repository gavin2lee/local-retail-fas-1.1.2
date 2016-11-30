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

import cn.wonhigh.retail.fas.common.dto.SettleBrandGroupDto;
import cn.wonhigh.retail.fas.common.model.SettleBrandGroup;
import cn.wonhigh.retail.fas.common.model.SettleBrandGroupRel;
import cn.wonhigh.retail.fas.manager.SettleBrandGroupManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 品牌组
 * 
 * @author yang.y
 * @date 2014-08-26 10:36:46
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/settle_brand_group")
@ModuleVerify("30113001")
public class SettleBrandGroupController extends
		BaseController<SettleBrandGroupDto> {

	@Resource
	private SettleBrandGroupManager settleBrandGroupManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("settle_brand_group/", settleBrandGroupManager);
	}

	@RequestMapping("/settle_brand_group_rel/{groupNo}")
	public ModelAndView relationBrand(@PathVariable("groupNo") String groupNo,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("settle_brand_group/settle_brand_group_rel");
		mav.addObject("groupNo", groupNo);
		return mav;
	}

	/**
	 * 检查数据是否已存在
	 * 
	 * @param id
	 *            主键
	 * @param groupNo
	 *            编码
	 * @return boolean
	 */
	@RequestMapping(value = "/validate_data", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validateData(@RequestParam("id") String id,
			@RequestParam("groupNo") String groupNo, HttpServletRequest request)
			throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uniqueBrandGroupNo", groupNo);
		String brandGroupName = request.getParameter("name");
		if (StringUtils.isNotEmpty(brandGroupName)) {
			params.put("uniqueBrandGroupName", brandGroupName);
		}
		List<SettleBrandGroup> list = settleBrandGroupManager.findByBiz(null,
				params);
		Map<String, Object> result = new HashMap<String, Object>();
		if (list != null && list.size() > 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "品牌组编码或名称不能重复");
			return result;
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 重写导出时的查询数据方法
	 * 
	 * @param params
	 *            请求参数
	 * @return List<BrandGroupDto>
	 * @throws ManagerException
	 *             异常
	 */
	// @Override
	// protected List<SettleBrandGroupDto> queryExportData(Map<String, Object>
	// params) throws ManagerException {
	// int total = settleBrandGroupManager.findRelationCount(params);
	// SimplePage page = new SimplePage(1, total, (int) total);
	// List<SettleBrandGroupDto> list =
	// settleBrandGroupManager.findRelationByPage(page, "", "", params);
	// return list;
	// }

	/**
	 * 新增/修改(主表和明细数据)
	 * 
	 * @param model
	 *            待新增的主表数据
	 * @param request
	 *            HttpServletRequest
	 * @return ResponseEntity
	 * @throws ManagerException 
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Boolean> saveAll(
			@ModelAttribute("model") SettleBrandGroupDto model,
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<SettleBrandGroupRel> util = new JsonUtil<SettleBrandGroupRel>();
			Map<CommonOperatorEnum, List<SettleBrandGroupRel>> params = util
					.convertToMap(request, SettleBrandGroupRel.class);
			settleBrandGroupManager.save(model, params);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);		
		}
		return map;
	}

	/**
	 * 删除操作
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Boolean>
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping("/do_delete")
	@ResponseBody
	public Map<String, Boolean> doDelete(HttpServletRequest request)
			throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String deletedList = StringUtils.isEmpty(request
					.getParameter("deleted")) ? "" : request
					.getParameter("deleted");
			JsonUtil<SettleBrandGroupDto> util = new JsonUtil<SettleBrandGroupDto>();
			List<SettleBrandGroupDto> list = util.convertListWithTypeReference(
					deletedList, request, SettleBrandGroupDto.class);
			settleBrandGroupManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return map;
	}
}