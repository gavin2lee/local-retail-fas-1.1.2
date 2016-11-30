package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.model.MallDeductionSet;
import cn.wonhigh.retail.fas.manager.MallDeductionSetManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 扣费类别
 * @author chen.mj
 * @date  2014-10-30 09:49:24
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
@RequestMapping("/mall_deduction_set")
@ModuleVerify("30140002")
public class MallDeductionSetController extends BaseController<MallDeductionSet> {
    @Resource
    private MallDeductionSetManager mallDeductionSetManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("mall_deduction_set/",mallDeductionSetManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/mall_deduction_set";
   	}
    
    @RequestMapping("/toSearchCost")
   	public String toSearchCost() {
    	return "base_setting/mall/searchCostCate";
   	}
    
    
    /**
	 * 保存
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/validate_data")
	@ResponseBody
    public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String messageStr="";
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		List<MallDeductionSet> checkList = new ArrayList<MallDeductionSet>();
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>(){});
			checkList.addAll(convertInvoiceRuleSet(mapper,list));
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
			checkList.addAll(convertInvoiceRuleSet(mapper,list));
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		Boolean isExist =false;
		//1.判断结算公司与是场地经营费用的唯一性。
		isExist=validateDataExist(checkList,returnMap,1);
		if(isExist){
			messageStr = "结算公司【"+returnMap.get("duplicateCostCompany")+"】为【场地经营费用】的已经存在！";
			result.put("success", false);
			result.put("message", messageStr);
			return result;
		}else{
			//2.验证结算公司，扣费名称，总账费用类别名称是否同时存在
			isExist=validateDataExist(checkList,returnMap,2);
			if (isExist) {
				messageStr = "扣费名称  【" + returnMap.get("duplicateCostName") + "】" +
							 "，结算公司【"+returnMap.get("duplicateCostCompany")+"】" +
							 "，总账费用类别名称【"+returnMap.get("duplicateCostTypeName")+"】已经存在！";
				result.put("success", false);
				result.put("message", messageStr);
				return result;
			}
		}
		result.put("success", true);
		return result;
    }
	
	private boolean validateDataExist(List<MallDeductionSet> checkList,Map<String, String> returnMap,int type) throws ManagerException{
		int debited_rental = 1; //是否是场地经营费用（0=不是 1=是）
		boolean duplicate = false;
		if (!CollectionUtils.isEmpty(checkList)) {
			Map<String, Object> checkParams = null;
			List<MallDeductionSet> countList = null;
			String duplicateCostCompany = "";
			String duplicateCostName = "";  
			String duplicateCostTypeName ="";
			for (MallDeductionSet costCategory : checkList) {
				checkParams = new HashMap<String, Object>();
				if(type == 1){
					if(costCategory.getDebitedRental()!=debited_rental){
						return duplicate;
					}
					checkParams.put("debitedRental",debited_rental); //是否场地经营费用
				}else{
					checkParams.put("costCode", costCategory.getCostCode());   //总账扣费类别
					checkParams.put("name", costCategory.getName()); 			//扣费名称
				}
				checkParams.put("companyNo", costCategory.getCompanyNo()); //结算公司
				//1.判断结算公司与是场地经营费用的唯一性。
				countList = this.mallDeductionSetManager.findByBiz(null, checkParams);
				if (StringUtils.isEmpty(costCategory.getCode())) {//新增
					if (!CollectionUtils.isEmpty(countList)) {
						duplicate = true;
						duplicateCostCompany= countList.get(0).getCompanyName();
						duplicateCostName = countList.get(0).getName();
						duplicateCostTypeName=countList.get(0).getCostName();
						returnMap.put("duplicateCostTypeName", duplicateCostTypeName);
						returnMap.put("duplicateCostName", duplicateCostName);
						returnMap.put("duplicateCostCompany", duplicateCostCompany);
						break;
					}
				}else {
					if (!CollectionUtils.isEmpty(countList) &&  !(countList.get(0).getCode().equals(costCategory.getCode()))) {
						duplicate = true;
						duplicateCostCompany= countList.get(0).getCompanyName();
						duplicateCostName = countList.get(0).getName();
						duplicateCostTypeName=countList.get(0).getCostName();
						returnMap.put("duplicateCostTypeName", duplicateCostTypeName);
						returnMap.put("duplicateCostName", duplicateCostName);
						returnMap.put("duplicateCostCompany", duplicateCostCompany);
						break;
					}
				}
			}
		}
		return duplicate;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<MallDeductionSet> convertInvoiceRuleSet(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<MallDeductionSet> entityClass = (Class<MallDeductionSet>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<MallDeductionSet> tl=new ArrayList<MallDeductionSet>(list.size());
		for (int i = 0; i < list.size(); i++) {
			MallDeductionSet type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
	
    
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<MallDeductionSet> dataList = mallDeductionSetManager.findByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	  /**
     * 查询批发订单-2015 02 02
     * @param req HttpServletRequest
     * @return Map<String, Object>
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/select_deduct_cost")
	@ResponseBody
    public Map<String, Object> selectCostByParams(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = mallDeductionSetManager.selectCostByCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<MallDeductionSet> list = mallDeductionSetManager.selectCostByParams(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
}