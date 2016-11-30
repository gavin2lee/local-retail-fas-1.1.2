package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.HeadquarterPeriod;
import cn.wonhigh.retail.fas.common.model.SupplierTariffSet;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.HeadquarterPeriodManager;
import cn.wonhigh.retail.mdm.api.util.CacheContext;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-08 10:17:18
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
@RequestMapping("/headquarter_period")
@ModuleVerify("30120030")
public class HeadquarterPeriodController extends  BaseController<HeadquarterPeriod> {
    @Resource
    private HeadquarterPeriodManager headquarterPeriodManager;
    
    @Resource
    private BrandUnitManager brandUnitManager;
    @Override
    public CrudInfo init() {
        return new CrudInfo("headquarter_period/",headquarterPeriodManager);
    }
    @SuppressWarnings("rawtypes")
   	@RequestMapping("/validate_data")
   	@ResponseBody
   	public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {

   		Map<String, Object> result = new HashMap<String, Object>();
   		
   		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
   		ObjectMapper mapper = new ObjectMapper();
   		List<HeadquarterPeriod> checkList = new ArrayList<HeadquarterPeriod>();

   		if (StringUtils.isNotEmpty(insertedList)) {
   			if(!insertedList.equals("[]")){
   			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
   			checkList.addAll(convertHeadquarterPeriod(mapper,list));
   			}
   		}
   		if (!CollectionUtils.isEmpty(checkList)) {
   			
   			Map<String, String> duplicateMap = new HashMap<String, String>();
   			Map<String, Object> checkParams = null;
   			int count = 0;
   			
   			boolean duplicate = false;
   			String duplicateShopGroup = "";
   			String duplicateBrand="";
   			for (HeadquarterPeriod settlePeriod : checkList) {
   				
   				checkParams = new HashMap<String, Object>();
   				checkParams.put("companyNo", settlePeriod.getCompanyNo());
   				checkParams.put("brandUnitNo", settlePeriod.getBrandUnitNo());
   				count = this.headquarterPeriodManager.findCount(checkParams);
   				if (count > 0) {
   					duplicate = true;
   					duplicateShopGroup = settlePeriod.getCompanyName();
   					duplicateBrand=settlePeriod.getBrandUnitName();
   					break;
   				}
   				
   				if (duplicateMap.containsKey(settlePeriod.getCompanyNo())) {
   					if (duplicateMap.get(settlePeriod.getCompanyNo()).contains(settlePeriod.getCompanyNo())) {
						duplicate = true;
						duplicateShopGroup = settlePeriod.getCompanyNo();
						duplicateBrand=settlePeriod.getBrandUnitNo();
						break;
					}else {
						duplicateMap.get(settlePeriod.getCompanyNo());
					}
				}else {
					List<String> sompanyNos = new ArrayList<String>();
					sompanyNos.add(settlePeriod.getCompanyNo());
				}
			}
   			if (duplicate) {
   				result.put("success", false);
   				result.put("message", "[" + duplicateShopGroup +"的"+duplicateBrand+ "]品牌部已经维护了结账日！");
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
   	private List<HeadquarterPeriod> convertHeadquarterPeriod(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
   		Class<HeadquarterPeriod> entityClass = (Class<HeadquarterPeriod>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
   		List<HeadquarterPeriod> tl=new ArrayList<HeadquarterPeriod>(list.size());
   		for (int i = 0; i < list.size(); i++) {
   			HeadquarterPeriod type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
   			tl.add(type);
   		}
   		return tl;
   	}
   	
   	
    /**
   	 * 批量新增
   	 * @param request HttpServletRequest
   	 * @return Map<String, Boolean>
     * @throws Exception 
   	 */
   	@RequestMapping(value = "/batch_add")
   	@ResponseBody
   	public Map<String,Object> batchGenetareCostByRule(HttpServletRequest req,Model model) throws Exception {
   		Map<String, Object> result = new HashMap<String, Object>();
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
   		Map<String, Object> params = builderParams(req, model);
   		List<String> companyNoList =null;
   		List<String> brandUnitNoList =null;
   		List<String> companyNameList =null;
   		List<String> brandUnitNameList =null;
   		boolean duplicate = false;
   		String duplicateShopGroup = "";
		String duplicateBrand="";
   		if(params.get("companyNo") != null && !"".equals(params.get("companyNo").toString())) {
   			String[] temps1 = params.get("companyNo").toString().split(",");
   			 companyNoList = Arrays.asList(temps1);
			params.put("companyNo", companyNoList);
		}
   		if(params.get("brandUnitNo") != null && !"".equals(params.get("brandUnitNo").toString())) {
   			String[] temps2 = params.get("brandUnitNo").toString().split(",");
			 brandUnitNoList = Arrays.asList(temps2);
			params.put("brandUnitNo", brandUnitNoList);
		}
   		if(params.get("companyName") != null && !"".equals(params.get("companyName").toString())) {
   			String[] temps1 = params.get("companyName").toString().split(",");
   			 companyNameList = Arrays.asList(temps1);
			params.put("companyName", companyNameList);
		}
   		if(params.get("brandUnitName") != null && !"".equals(params.get("brandUnitName").toString())) {
   			String[] temps2 = params.get("brandUnitName").toString().split(",");
   			brandUnitNameList = Arrays.asList(temps2);
			params.put("brandUnitName", brandUnitNameList);
		}
   		for (String companyNo : companyNoList) {
   			params.put("companyNo", companyNo);
   			CacheContext context = CacheContext.current();
   			params.put("companyName", context.getCompanyName(companyNo));
   			for (String brandUnitNo : brandUnitNoList) {
   	   			params.put("brandUnitNo", brandUnitNo);
   	   			SimplePage page = new SimplePage(0, 10,0);
   	   			params.put("brandUnitName",null);
   	   			List<BrandUnit> list=brandUnitManager.findByPage(page, null, null, params);
   	   			for (BrandUnit brandUnit : list) {
   	   				params.put("brandUnitName",brandUnit.getName() );
   	   			}
			int count = this.headquarterPeriodManager.findCount(params);
			if (count > 0) {
				duplicate = true;
				duplicateShopGroup = companyNo;
				duplicateBrand=brandUnitNo;
				break;
			}
			params.put("createUser", loginUser.getUsername());
			params.put("createTime", DateUtil.getCurrentDateTime());
			params.put("updateTime", DateUtil.getCurrentDateTime());
			this.headquarterPeriodManager.add(params);
		}
	}
   		if (duplicate) {
			result.put("success", false);
			result.put("message", "[" + duplicateShopGroup +"的"+duplicateBrand+ "]品牌部已经维护了结账日！");
			return result;
		}
   		result.put("success", true);
   		return result;

    }
}