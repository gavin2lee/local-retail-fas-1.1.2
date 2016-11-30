package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.FasLogoutStatusEnum;
import cn.wonhigh.retail.fas.common.enums.SettlePathBillBasisEnums;
import cn.wonhigh.retail.fas.common.enums.SettlePathBillTypeEnums;
import cn.wonhigh.retail.fas.common.model.SettlePath;
import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceManager;
import cn.wonhigh.retail.fas.manager.SettlePathBrandRelManager;
import cn.wonhigh.retail.fas.manager.SettlePathDtlManager;
import cn.wonhigh.retail.fas.manager.SettlePathManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 结算路径 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@SuppressWarnings({"rawtypes"})
@Controller
@RequestMapping("/settle_path")
@ModuleVerify("30114001")
public class SettlePathController extends BaseController<SettlePathDto> {
	
    @Resource
    private SettlePathManager settlePathManager;
    
	@Resource
	private SettlePathDtlManager settlePathDtlManager;
	
	@Resource
	private SettlePathBrandRelManager settlePathBrandRelManager;
	
	@Resource
	private BillBuyBalanceManager billBuyBalanceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_path/",settlePathManager);
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
    		@RequestParam("pathNo")String pathNo, HttpServletRequest request)
    		throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("pathNo", pathNo);
		List<SettlePath> list = settlePathManager.findByBiz(null, params);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0
				&& !list.get(0).getId().equals(id)) {
			result.put("success", false);
			result.put("message", "结算路径编码不能重复");
			return result;
		}
		result.put("success", true);
		return result;
    }
    
    /**
     * 校验数据
     * @param request HttpServletRequest
     * @return Map<String, Object>
     * @throws Exception  异常
     */
    @RequestMapping(value="/check_audit", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkAudit(HttpServletRequest request)
    		throws Exception {
    	String checkedList = StringUtils.isEmpty(request.getParameter("checkedList")) ? "" : request
				.getParameter("checkedList");
		JsonUtil<SettlePath> util = new JsonUtil<SettlePath>();
		List<SettlePath> list = util.convertListWithTypeReference(checkedList, request,
				SettlePath.class);
		Map<String, Object> result = new HashMap<String, Object>();
		// 校验结算路径是否有关联品牌
		Map<String, Object> params = null;
		if(list != null && list.size() > 0) {
			for(SettlePath model : list) {
				params = new HashMap<String, Object>();
				params.put("pathNo", model.getPathNo());
				List<SettlePathBrandRel> lstBrandRel = settlePathBrandRelManager.findByBiz(null, params);
				if(lstBrandRel == null || lstBrandRel.size() == 0) {
					result.put("success", false);
					result.put("message", "结算路径[" + model.getPathNo() + "]没有关联品牌部!");
					return result;
				}
			}
		}
		result.put("success", true);
		return result;
    }
    
    /**
     * 获取单据依据
     * @return List<Map<String, String>>
     */
    @RequestMapping("/get_bill_basis")
    @ResponseBody
    public List<Map<String, String>> getBillBasis() {
    	List<Map<String, String>> list = new ArrayList<Map<String,String>>();
    	for(SettlePathBillBasisEnums e : SettlePathBillBasisEnums.values()) {
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("value", e.getValue());
    		map.put("text", e.getText());
    		list.add(map);
    	}
    	return list;
    }
    
    /**
     * 获取单据类型
     * @param request HttpServletRequest
     * @return List<Map<String, String>>
     */
    @RequestMapping("/get_bill_type")
    @ResponseBody
    public List<Map<String, String>> getBillType(HttpServletRequest request) {
    	List<Map<String, String>> list = new ArrayList<Map<String,String>>();
    	Map<String, String> map = null;
//    	if(SettlePathBillBasisEnums.ORDERING.getValue().equals(billBasis)) {
		map = new HashMap<String, String>();
		map.put("value", SettlePathBillTypeEnums.ORDERING.getValue());
		map.put("text", SettlePathBillTypeEnums.ORDERING.getText());
		list.add(map);
		map = new HashMap<String, String>();
		map.put("value", SettlePathBillTypeEnums.RESTOCK.getValue());
		map.put("text", SettlePathBillTypeEnums.RESTOCK.getText());
		list.add(map);
		map = new HashMap<String, String>();
		map.put("value", SettlePathBillTypeEnums.HQ_BUY.getValue());
		map.put("text", SettlePathBillTypeEnums.HQ_BUY.getText());
		list.add(map);
//    	} else if(SettlePathBillBasisEnums.RESTOCK.getValue().equals(billBasis)) {
//    		map = new HashMap<String, String>();
//    		map.put("value", SettlePathBillTypeEnums.ORIGINAL_RESIDUES.getValue());
//    		map.put("text", SettlePathBillTypeEnums.ORIGINAL_RESIDUES.getText());
//    		list.add(map);
//    		map = new HashMap<String, String>();
//    		map.put("value", SettlePathBillTypeEnums.OFF_RESIDUAL.getValue());
//    		map.put("text", SettlePathBillTypeEnums.OFF_RESIDUAL.getText());
//    		list.add(map);
//    	}
    	return list;
    }
    
    @RequestMapping("/update_status")
    @ResponseBody
    public Map<String, Boolean> updateStatus() throws ManagerException {
    	Map<String, Boolean> map = new HashMap<String, Boolean>();
    	try {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("queryDate", DateUtil.format1(new Date()));
    		params.put("status", FasLogoutStatusEnum.UNABLE_STATUS.getValue());
    		settlePathManager.updateStatus(params);
    		map.put("success", true);
    	} catch(Exception e) {
    		map.put("success", false);
    		logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
    	return map;
    }
    
    /**
     * 选择结算主体/供应商组
     * @param request HttpServletRequest
     * @return 结算主体或供应商组的集合
     * @throws Exception
     */
    @RequestMapping("/search_company")
    @ResponseBody
    public Map<String, Object> searchCompany(HttpServletRequest request, Model model) throws Exception {
    	int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : 
    		Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : 
			Integer.parseInt(request.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : 
			String.valueOf(request.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : 
			String.valueOf(request.getParameter("order"));
    	Map<String, Object> obj = new HashMap<String, Object>();
    	int totalCount = 0;
    	Map<String, Object> params = new HashMap<String, Object>();
		String existCompanyNos = request.getParameter("existCompanyNos");
		String combogridParams = request.getParameter("q");
		if(StringUtils.isNotEmpty(existCompanyNos)) {
			params.put("existCompanyNos", existCompanyNos.split(","));
		}
		if(StringUtils.isNotEmpty(combogridParams)) {
			params.put("q", combogridParams);
		}
		totalCount = settlePathDtlManager.findCompanyCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) totalCount);
		List<Object> list = settlePathDtlManager.findCompanyPage(page, sortColumn, sortOrder, params);
    	obj.put("total", totalCount);
		obj.put("rows", list);
		return obj;
    }
    
    /**
     * 关联结算主体
     * @param pathNo 结算主体编码
     * @param request HttpServletRequest
     * @return ModelAndView
     */
    @RequestMapping("/company_rel")
	public ModelAndView companyRel(@RequestParam("pathNo")String pathNo, 
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pathNo", pathNo);
		mav.setViewName("/settle_path/company_rel");
		return mav;
	}
    
    /**
     * 关联品牌：修改
     * @param pathNo 结算主体编码
     * @param request HttpServletRequest
     * @return ModelAndView
     */
    @RequestMapping("/brand_rel")
    public ModelAndView brandRel(@RequestParam("pathNo")String pathNo, HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("settle_path/brand_rel");
    	mav.addObject("pathNo", pathNo);
    	return mav;
    }
    
    /**
     * 关联品牌：查看
     * @param pathNo 结算主体编码
     * @param request HttpServletRequest
     * @return ModelAndView
     */
    @RequestMapping("/brand_rel_view")
    public ModelAndView brandRelView(@RequestParam("pathNo")String pathNo, HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("settle_path/brand_rel_view");
    	mav.addObject("pathNo", pathNo);
    	return mav;
    }
    
    /**
     * 从关联品牌页面中，点击"品牌组"按钮时触发的请求
     * @param request HttpServletRequest
     * @return String
     */
    @RequestMapping("/brand_group_rel")
    public String brandGroupRel(HttpServletRequest request) {
    	return "settle_path/brand_group_rel";
    }
	
    /**
     * 结算主体复制
     * @param request HttpServletRequest
     * @return String
     */
	@RequestMapping("/company_copy")
    public ModelAndView companyCopy(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("existCompanyNos", request.getParameter("existCompanyNos"));
		mav.setViewName("settle_path/company_copy");
    	return mav;
    }
	
	/**
	 * 查询符合表头的数据
	 * @param vo 查询参数
	 * @return List<Map>
	 */
//	@Override
//	protected List<Map> findComplexHeaderData(SettlePathDto vo) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pathNo", vo.getPathNo());
//		List<SettlePathDtl> list = null;
//		try {
//			list = settlePathDtlManager.findByBiz(null, params);
//			if(list != null && list.size() > 0) {
//				SupplierGroup supplierGroup = new SupplierGroup();
//				supplierGroup.setGroupNo(list.get(0).getCompanyNo());
//				SupplierGroup result = supplierGroupManager.findById(supplierGroup);
//				if(result != null) {
//					list.get(0).setCompanyName(result.getGroupName());
//				}
//			}
//			ObjectMapper mapper = new ObjectMapper();
//			String json = mapper.writeValueAsString(list);
//			List<Map> map = mapper.readValue(json, List.class);
//			return map;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ArrayList<Map>(1);
//	}
	
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
	public Map<String, Boolean> saveAll(@ModelAttribute("model") SettlePathDto model, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<SettlePathDtl> util = new JsonUtil<SettlePathDtl>();
			Map<CommonOperatorEnum, List<SettlePathDtl>> params = util.convertToMap(request, SettlePathDtl.class);
			String brandListVal = StringUtils.isEmpty(request.getParameter("brandList")) ? "" 
					: request.getParameter("brandList");
			JsonUtil<SettlePathBrandRel> brandUtil = new JsonUtil<SettlePathBrandRel>();
			List<SettlePathBrandRel> brandList = brandUtil.convertListWithTypeReference(brandListVal, 
					request, SettlePathBrandRel.class);
			settlePathManager.save(model, params, brandList);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 审核
	 * @param request HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException 异常
	 */
	@RequestMapping("/do_audit")
	@ResponseBody
	public Boolean doAduit(HttpServletRequest request) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {});
			List<SettlePathDto> oList = convertListWithTypeReference(mapper, list, request);
			int auditVal = StringUtils.isEmpty(request.getParameter("auditVal")) 
					? FasAduitStatusEnum.ADUIT_STATUS.getValue() 
					: Integer.parseInt(request.getParameter("auditVal"));
			if(oList != null && oList.size() > 0) {
				for(SettlePathDto model : oList) {
					model.setAuditStatus(auditVal);
					model.setAuditor(model.getUpdateUser());
					model.setAuditTime(model.getUpdateTime());
				}
				settlePathManager.doAudit(oList);
			}
			return true;
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@RequestMapping("/do_enable")
	@ResponseBody
	public Boolean doEnable(HttpServletRequest request) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" 
					: request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {});
			List<SettlePathDto> oList = convertListWithTypeReference(mapper, list, request);
			if(oList != null && oList.size() > 0) {
				for(SettlePathDto model : oList) {
					model.setStatus(FasLogoutStatusEnum.ENABLE_STATUS.getValue());
					this.setDefaulValues(model, request);
				}
				settlePathManager.doStatus(oList);
			}
			return true;
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 停用
	 * @param request HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException 异常
	 */
	@RequestMapping("/do_unable")
	@ResponseBody
	public Boolean doUnable(HttpServletRequest request) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" 
					: request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {});
			List<SettlePathDto> oList = convertListWithTypeReference(mapper, list, request);
			if(oList != null && oList.size() > 0) {
				for(SettlePathDto model : oList) {
					model.setStatus(FasLogoutStatusEnum.UNABLE_STATUS.getValue());
					this.setDefaulValues(model, request);
				}
				settlePathManager.doStatus(oList);
			}
			return true;
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
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
		String deletedList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
		JsonUtil<SettlePathDto> util = new JsonUtil<SettlePathDto>();
		List<SettlePathDto> list = util.convertListWithTypeReference(deletedList, request,
				SettlePathDto.class);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list == null || list.size() == 0) {
			result.put("success", true);
			return result;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		for(SettlePathDto dto : list) {
			params.put("pathNo", dto.getPathNo());
			// 判断结算路径中是否有引用
			int iCount = billBuyBalanceManager.findCount(params);
			if(iCount > 0) {
				result.put("success", false);
				result.put("message", "结算路径[" + dto.getPathNo() + "]已经被使用，不能删除!");
				return result;
			}
		}
		result.put("success", true);
		return result;
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
			JsonUtil<SettlePathDto> util = new JsonUtil<SettlePathDto>();
			List<SettlePathDto> list = util.convertListWithTypeReference(deletedList, request,
					SettlePathDto.class);
			settlePathManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
}