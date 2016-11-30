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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.InsidePurchaseBalanceDate;
import cn.wonhigh.retail.fas.manager.InsidePurchaseBalanceDateManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-02-02 20:00:10
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/inside_purchase_balance_date")
@ModuleVerify("30200010")
public class InsidePurchaseBalanceDateController extends BaseController<InsidePurchaseBalanceDate> {
    @Resource
    private InsidePurchaseBalanceDateManager insidePurchaseBalanceDateManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("inside_purchase_balance_date/",insidePurchaseBalanceDateManager);
    }
    
    /**
	 * 根据公司编号及结算月，判断是否已存在记录，如果存在，刚不能再新增
	 * @param request
	 * @return
     * @throws ManagerException 
	 */
	@RequestMapping(value = "/validate_data", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIsExist(HttpServletRequest request) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String insertedList = StringUtils.isEmpty(request.getParameter("inserted")) ? "" : request.getParameter("inserted");
			ObjectMapper mapper = new ObjectMapper();
			if (StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
				List<InsidePurchaseBalanceDate> oList= convertListWithTypeReference(mapper,list);
				if(CollectionUtils.isNotEmpty(oList)){
					InsidePurchaseBalanceDate model = oList.get(0);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("companyNo", model.getCompanyNo());
					params.put("balanceMonth", model.getBalanceMonth());
					List<InsidePurchaseBalanceDate> ipbdlist = insidePurchaseBalanceDateManager.findByBiz(null, params);
					if(CollectionUtils.isNotEmpty(ipbdlist)){
						map.put("success", true);
						map.put("message", "公司编号："+model.getCompanyNo()+"的"+model.getBalanceMonth()+"结算月的记录已存在，不能再新增。");
					}
				}else{
					map.put("success", false);
				}
			}else{
				map.put("success", false);
			}
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
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
	private List<InsidePurchaseBalanceDate> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<InsidePurchaseBalanceDate> entityClass = (Class<InsidePurchaseBalanceDate>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<InsidePurchaseBalanceDate> tl=new ArrayList<InsidePurchaseBalanceDate>(list.size());
		for (int i = 0; i < list.size(); i++) {
			InsidePurchaseBalanceDate type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
}