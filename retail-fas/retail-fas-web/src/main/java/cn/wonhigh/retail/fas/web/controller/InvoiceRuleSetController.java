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

import cn.wonhigh.retail.fas.common.model.InvoiceRuleSet;
import cn.wonhigh.retail.fas.manager.InvoiceRuleSetManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
@RequestMapping("/invoice_rule_set")
public class InvoiceRuleSetController extends BaseController<InvoiceRuleSet> {
	
    @Resource
    private InvoiceRuleSetManager invoiceRuleSetManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("invoice_rule_set/",invoiceRuleSetManager);
    }
    
    private static final int RULESTATUS = 1;
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/invoice_rule_set";
   	}
    
    /**
     * 根据店铺分组获取开票规则信息
     * @param req
     * @return
     * @throws ManagerException
     */
	@RequestMapping(value = "/getInvoiceRuleByShopGroupNo")
	@ResponseBody
	public InvoiceRuleSet getInvoiceRuleByShopGroupNo(HttpServletRequest req)throws ManagerException{
		String shopGroupNo = req.getParameter("shopGroupNo");
		
		List<InvoiceRuleSet> list = new ArrayList<InvoiceRuleSet>();
		
		InvoiceRuleSet dtl = new InvoiceRuleSet();
		if(StringUtils.isEmpty(shopGroupNo)){
			return dtl;
		}
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("status", RULESTATUS);
		params.put("shopGroupNo", shopGroupNo);
		list =  invoiceRuleSetManager.findByBiz(null, params);
		
		if(CollectionUtils.isEmpty(list)){
			return dtl;
		}else{
			dtl = list.get(0);
		}
		
		return dtl;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/validate_data")
	@ResponseBody
	public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {

		Map<String, Object> result = new HashMap<String, Object>();
		
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		List<InvoiceRuleSet> checkList = new ArrayList<InvoiceRuleSet>();

		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>(){});
			checkList.addAll(convertInvoiceRuleSet(mapper,list));
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
			checkList.addAll(convertInvoiceRuleSet(mapper,list));
		}
		if (!CollectionUtils.isEmpty(checkList)) {
			
			Map<String, Integer> duplicateMap = new HashMap<String, Integer>();
			Map<String, Object> checkParams = null;
			int count = 0;
			
			boolean duplicate = false;
			String duplicateShopGroup = "";
			
			for (InvoiceRuleSet ruleSet : checkList) {
				if (ruleSet.getStatus() == 0) {
					continue;
				}
				
				checkParams = new HashMap<String, Object>();
				checkParams.put("shopGroupNo", ruleSet.getShopGroupNo());
				checkParams.put("status", 1);//启用状态
				count = this.invoiceRuleSetManager.findCount(checkParams);
				if (count > 0) {
					duplicate = true;
					duplicateShopGroup = ruleSet.getShopGroupName();
					break;
				}
				
				if (duplicateMap.containsKey(ruleSet.getShopGroupNo())) {
					duplicate = true;
					duplicateShopGroup = ruleSet.getShopGroupName();
					break;
				}else {
					duplicateMap.put(ruleSet.getShopGroupNo(), ruleSet.getStatus());
				}
			}
			
			if (duplicate) {
				result.put("success", false);
				result.put("message", "店铺分组[" + duplicateShopGroup + "]已经存在启用状态的规则！");
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
	private List<InvoiceRuleSet> convertInvoiceRuleSet(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<InvoiceRuleSet> entityClass = (Class<InvoiceRuleSet>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<InvoiceRuleSet> tl=new ArrayList<InvoiceRuleSet>(list.size());
		for (int i = 0; i < list.size(); i++) {
			InvoiceRuleSet type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
	
}