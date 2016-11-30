package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.WholesaleReceTermDtlDto;
import cn.wonhigh.retail.fas.common.enums.FasLogoutStatusEnum;
import cn.wonhigh.retail.fas.common.model.WholesaleReceTerm;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.WholesaleReceTermManager;
import cn.wonhigh.retail.fas.service.HqOtherStockOutBalanceServiceImpl;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 地区批发收款条款-表頭
 * @author yang.y
 * @date  2014-09-17 18:00:36
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
@RequestMapping("/wholesale_rece_term")
@ModuleVerify("30160001")
public class WholesaleReceTermController extends BaseController<WholesaleReceTerm> {
   
	@Resource
    private WholesaleReceTermManager wholesaleReceTermManager;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	private Logger logger = Logger.getLogger(WholesaleReceTermController.class);

    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_rece_term/",wholesaleReceTermManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("wholesale_rece_term/list");
		return mav;
	}
    
    @RequestMapping(value = "/list.json")
	@ResponseBody
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<Object> list = new ArrayList<Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND COMPANY_NO IN (" + companyNos + ")");
		}
		total = wholesaleReceTermManager.findCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = wholesaleReceTermManager.findByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
    
    /**
	 * 新增/修改(主表和明细数据)
	 * 
	 * @param model 待新增的主表数据
	 * @param request HttpServletRequest
	 * @return Map<String, Boolean>
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Boolean> saveAll(@ModelAttribute("model") WholesaleReceTerm model, 
			HttpServletRequest request) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<WholesaleReceTermDtlDto> util = new JsonUtil<WholesaleReceTermDtlDto>();
			Map<CommonOperatorEnum, List<WholesaleReceTermDtlDto>> params = util.convertToMap(request, 
					WholesaleReceTermDtlDto.class);
			wholesaleReceTermManager.save(model, params);
			map.put("success", true);
		} catch(Exception e) {
			logger.debug(e.getMessage(), e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping(value = "/select_byCompanyNoAndCustomerNo")
	@ResponseBody
	public Map<String, Object> selectByCompanyNoAndCustomerNo (HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", request.getParameter("companyNo"));
		params.put("customerNo", request.getParameter("customerNo"));
		List<WholesaleReceTerm> list = wholesaleReceTermManager.selectByCompanyNoAndCustomerNo(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("rows", list);
		return obj;
	}
	
    /**
	 * 校验条款是否重复
	 * 
	 * @param request HttpServletRequest
	 * @return iCount
     * @throws ManagerException 
	 */
	@RequestMapping(value = "/check_add")
	@ResponseBody
	public Integer checkAdd(HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		//params.put("companyNo", request.getParameter("companyNo"));
		params.put("termNo", request.getParameter("termNo"));
		params.put("name", request.getParameter("name"));
		int iCount = wholesaleReceTermManager.checkOnlyByTermNoOrTermName(params);
		return iCount;
	}
	
	/**
	 * 进入选择收款条款页面
	 * @return ModelAndView
	 */
	@RequestMapping("/select")
	public ModelAndView select(HttpServletRequest request) {
		//默认值查询已启用的数据
		String status = request.getParameter("status") != null ? request.getParameter("status") 
				: FasLogoutStatusEnum.ENABLE_STATUS.getValue().toString();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("wholesale_rece_term/select");
		mav.addObject("status", status);
		return mav;
	}
	@RequestMapping(value = "/get_biz_view")
	@ResponseBody
	public List<ModelAndView> getBizView(ModelAndView modelType,HttpServletRequest req,Model model)throws ManagerException{
		Map<String,Object> params=builderParams(req, model);
		return wholesaleReceTermManager.findByBiz(modelType, params);
	}
	
	@Override
	protected List<WholesaleReceTerm> queryExportData(Map<String, Object> params) throws ManagerException {
		int total = wholesaleReceTermManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		String isHq = StringUtils.isEmpty(params.get("isHq")+"") ? "" : String.valueOf(params.get("isHq"));
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND COMPANY_NO IN (" + companyNos + ")");
		}
		List<WholesaleReceTerm> list = wholesaleReceTermManager.findByPage(page, orderByField, orderBy, params);
		return list;
	}
	
}