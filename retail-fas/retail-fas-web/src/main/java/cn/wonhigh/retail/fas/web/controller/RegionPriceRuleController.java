package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.manager.RegionPriceRuleManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@RequestMapping("/region_price_rule")
@ModuleVerify("30120014")
public class RegionPriceRuleController extends BaseController<RegionPriceRule> {
    @Resource
    private RegionPriceRuleManager regionPriceRuleManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("region_price_rule/",regionPriceRuleManager);
    }
    
    @RequestMapping(value = "/check_rule_refered.json")
	public ResponseEntity<Integer> checkRuleRefered(HttpServletRequest req,Model model) throws ManagerException {
		
    	Map<String, Object> params = builderParams(req, model);
		
		int total= regionPriceRuleManager.checkIsRuleRefered(params);
		
		return new ResponseEntity<Integer>(total, HttpStatus.OK);
	}
    
    /**
     * 校验加价规则唯一性
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/get_unique_count.json")
	public ResponseEntity<Integer> getCheckUniqueCount(HttpServletRequest req,Model model)throws ManagerException{
		Map<String, Object> params = builderParams(req, model);
		int total= regionPriceRuleManager.findCount(params);
		return new ResponseEntity<Integer>(total, HttpStatus.OK);
	}
    
    /**
     * 增加年份
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 地区价格规则
     * @throws ManagerException 异常
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/add_year")
    @ResponseBody
	public Map<String, Object> addYear(HttpServletRequest req,Model model)throws ManagerException, JsonParseException, JsonMappingException, IOException{
		String yearCode = req.getParameter("yearCode");
		String year = req.getParameter("year");
		if(StringUtils.isNotBlank(req.getParameter("checkedRows"))){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> itemList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<RegionPriceRule> lstItem = convertListWithTypeReference(mapper, itemList, RegionPriceRule.class);
			Date currDate = new Date();
			String updateUser = Authorization.getUser().getUsername();
			for (RegionPriceRule rule : lstItem) {	
				String ruleYear = rule.getYear();
				String ruleYearCode = rule.getYearCode();
				if(StringUtils.isNotBlank(ruleYear) && ruleYear.indexOf(year) == -1){
					rule.setYear(ruleYear.concat(",").concat(year));
					rule.setYearCode(ruleYearCode.concat(",").concat(yearCode));
					String[] yearCodes = rule.getYearCodes();
					ArrayUtils.add(yearCodes, yearCode);
					rule.setYearCodes(yearCodes);
					rule.setUpdateUser(updateUser);
					rule.setUpdateTime(currDate);
					regionPriceRuleManager.modifyById(rule);
				}
				
			} 
 		}
		Map<String, Object> result = new HashMap<String, Object>();
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
}