package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.manager.HeadquarterPriceRuleManager;
import cn.wonhigh.retail.fas.manager.RegionPriceRuleManager;
import cn.wonhigh.retail.fas.manager.SettlePathDtlManager;
import cn.wonhigh.retail.fas.manager.SupplierGroupManager;
import cn.wonhigh.retail.fas.manager.SupplierManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
@RequestMapping("/supplier_group")
@ModuleVerify("30120011")
public class SupplierGroupController extends BaseController<SupplierGroup> {
	
    @Resource
    private SupplierGroupManager supplierGroupManager;
    
    @Resource
    private SettlePathDtlManager settlePathDtlManager;
    
    @Resource
    private HeadquarterPriceRuleManager headquarterPriceRuleManager;
    
    @Resource
    private RegionPriceRuleManager regionPriceRuleManager;
    
    @Resource
    private SupplierManager supplierManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("supplier_group/",supplierGroupManager);
    }

    /**
     * 跳转到未分组供应商列表
     * @return
     */
	@RequestMapping(value = "/list_no_group")
	public String toItemBuyGather() {
		return "supplier_group/list_no_group";
	}
	
	/**
	 * 保存未分组供应商
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping({"/importNoGroup"})
    @ResponseBody
    public Map<String, Object> importNoGroup(HttpServletRequest req, Model model) throws Exception{
    	Map<String, Object> obj = new HashMap<String, Object>();
    	obj.put("success", false);
    	String checkedRows = req.getParameter("checkedRows");
    	String groupNo = req.getParameter("groupNo");
    	if(StringUtils.isNotBlank(checkedRows) && StringUtils.isNotBlank(groupNo)){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> itemList = mapper.readValue(checkedRows, new TypeReference<List<Map>>() {});
			List<Object> lstItem = convertListWithTypeReference(mapper, itemList, Supplier.class);
			supplierGroupManager.importNoGroup(groupNo,lstItem);
			obj.put("success", true);
		}
    	return obj;
    }
    
	/**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
	
    @RequestMapping({"/supplierNoGroup.json"})
    @ResponseBody
    public Map<String, Object> supplierNoGroup(HttpServletRequest req, Model model) {
    	//获取未分组的供应商列表（状态为正常）
        int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
        SystemUser currUser = Authorization.getUser();
        String organTypeNo = currUser.getOrganTypeNo();
        Map<String, Object> params = builderParams(req, model);
        params.put("status", 1);//添加参数，查询状态为1的未分组供应商
        params.put("organTypeNo", organTypeNo);
        int total = supplierManager.findByNoGroupCount(params);
        SimplePage page = new SimplePage(pageNo, pageSize, total);
        List<Supplier> list=supplierManager.findByNoGroup(page,params);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", Integer.valueOf(total));
        obj.put("rows", list);
        return obj;
    }
    
    @RequestMapping(value = "/getGroupNoAndName")
    @ResponseBody
	public List<Map<String, Object>> getGroupNoAndName(HttpServletRequest request, SupplierGroup supplierGroup)
			throws ManagerException {
    	
    	return supplierGroupManager.getGroupNoAndName(supplierGroup);
	}
    
    @RequestMapping(value = "/get_unique_module")
	public ResponseEntity<SupplierGroup> getUniqueGroupByNo(HttpServletRequest request,Model model, SupplierGroup supplierGroup)
			throws ManagerException {
    	
    	Map<String, Object> params = builderParams(request, model);
    	
    	List<SupplierGroup> groups = this.supplierGroupManager.findByBiz(null, params);
    	if (CollectionUtils.isEmpty(groups)) {
    		return new ResponseEntity<SupplierGroup>(new SupplierGroup() , HttpStatus.OK);
		}
		return new ResponseEntity<SupplierGroup>(groups.get(0), HttpStatus.OK);
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
	public Map<String, Boolean> saveAll(@ModelAttribute("model") SupplierGroup model, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<SupplierGroupRel> util = new JsonUtil<SupplierGroupRel>();
			Map<CommonOperatorEnum, List<SupplierGroupRel>> params = util.convertToMap(request, SupplierGroupRel.class);
			supplierGroupManager.save(model, params);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
	
	@RequestMapping(value = "/get_audited_group")
	@ResponseBody
	public List<SupplierGroup> getAuditedGroup(SupplierGroup modelType,HttpServletRequest req,Model model)throws ManagerException{
		Map<String,Object> params=builderParams(req, model);
		return supplierGroupManager.findByBiz(modelType, params);
	}
    
	 /**
     * 进入结算大类查询页面
     * 
     * @return String
     */
    @RequestMapping("/list_tabMain")
    public String listTabMain() {
    	return "supplier_group/search_list";
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
    		@RequestParam("groupNo")String groupNo, HttpServletRequest request)
    		throws Exception {
    	String groupName = request.getParameter("groupName");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("uniqueGroupNo", groupNo);
		params.put("uniqueGroupName", groupName);
		
		List<SupplierGroup> list = supplierGroupManager.findByBiz(null, params);
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "单据编码或名称不能重复");
			return result;
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
    @RequestMapping("/check_supplier_group_used")
    @ResponseBody
    public Map<String, Object> checkSupplierGroupUsed(HttpServletRequest request, SupplierGroupRel modelType) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", modelType.getGroupNo());
		// 判断结算路径中是否有引用
		int count = settlePathDtlManager.findCount(params);
		if(count > 0) {
			result.put("success", false);
			result.put("message", "供应商组 [" + modelType.getGroupNo() +
					"] 已经被结算路径引用，不能操作!");
			return result;
		}
		
		Map<String, Object> addRuleParams = new HashMap<String, Object>();
		addRuleParams.put("supplierGroupNoAlial", modelType.getGroupNo());
		// 判断结算路径中是否有引用
		count = headquarterPriceRuleManager.findCount(addRuleParams);
		if(count > 0) {
			result.put("success", false);
			result.put("message", "供应商组 [" + modelType.getGroupNo() +
					"] 已经被总部加价规则引用，不能操作!");
			return result;
		}
		
		// 判断结算路径中是否有引用
		count = regionPriceRuleManager.findCount(addRuleParams);
		if(count > 0) {
			result.put("success", false);
			result.put("message", "供应商组 [" + modelType.getGroupNo() +
					"] 已经被地区加价规则引用，不能操作!");
			return result;
		}
		
		result.put("success", true);
		return result;
    }
    
	
}