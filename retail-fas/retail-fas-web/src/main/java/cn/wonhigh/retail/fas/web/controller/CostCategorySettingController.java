package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CostCategorySetting;
import cn.wonhigh.retail.fas.manager.CostCategorySettingManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-08-28 10:48:25
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
@RequestMapping("/cost_category")
@ModuleVerify("30140001")
public class CostCategorySettingController extends BaseController<CostCategorySetting> {
    @Resource
    private CostCategorySettingManager costCategorySettingManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("cost_category_setting/",costCategorySettingManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/cost_categoryset";
   	}
    

	@SuppressWarnings("rawtypes")
	@RequestMapping("/validate_data")
	@ResponseBody
	public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		List<CostCategorySetting> checkList = new ArrayList<CostCategorySetting>();
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>(){});
			checkList.addAll(convertInvoiceRuleSet(mapper,list));
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
			checkList.addAll(convertInvoiceRuleSet(mapper,list));
		}
		if (!CollectionUtils.isEmpty(checkList)) {
			Map<String, Object> checkParams = null;
			List<CostCategorySetting> countList = null;
			boolean duplicate = false;
			String duplicateCostCategory = "";
			String duplicateCostCompany = "";
			for (CostCategorySetting costCategory : checkList) {
				checkParams = new HashMap<String, Object>();
				checkParams.put("name", costCategory.getName());
				checkParams.put("companyNo", costCategory.getCompanyNo());
				countList = this.costCategorySettingManager.findByBiz(null, checkParams);
				if (StringUtils.isEmpty(costCategory.getCode())) {//新增
					if (!CollectionUtils.isEmpty(countList)) {
						duplicate = true;
						duplicateCostCategory = countList.get(0).getName();
						duplicateCostCompany= countList.get(0).getCompanyName();
						break;
					}
				}else {
					if (!CollectionUtils.isEmpty(countList) &&  !(countList.get(0).getCode().equals(costCategory.getCode()))) {
						duplicate = true;
						duplicateCostCategory = countList.get(0).getName();
						duplicateCostCompany= countList.get(0).getCompanyName();
						break;
					}
				}
			}
			if (duplicate) {
				result.put("success", false);
				result.put("message", "总账费用类别名称【" + duplicateCostCategory + "】," +
						"结算公司为【"+duplicateCostCompany+"】 已经存在！");
				return result;
			}
		}
		
		result.put("success", true);
		return result;
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
	private List<CostCategorySetting> convertInvoiceRuleSet(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<CostCategorySetting> entityClass = (Class<CostCategorySetting>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<CostCategorySetting> tl=new ArrayList<CostCategorySetting>(list.size());
		for (int i = 0; i < list.size(); i++) {
			CostCategorySetting type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
	
}