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

import cn.wonhigh.retail.fas.common.model.PaymentTerm;
import cn.wonhigh.retail.fas.manager.PaymentTermManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;


/**
 * 付款条款
 * @author ouyang.zm
 * @date  2014-09-02 09:26:51
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
@RequestMapping("/base_setting/payment_term")
@ModuleVerify("30100009")
public class PaymentTermController extends BaseController<PaymentTerm> {
    @Resource
    private PaymentTermManager paymentTermManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/payment_term/",paymentTermManager);
    }
    
    /**
  	* 校验是否包含重复数据
  	* @param financialAccount
  	* @return
  	* @throws ManagerException 
  	 * @throws IOException 
  	 * @throws JsonMappingException 
  	 * @throws JsonParseException 
  	*/
  	@SuppressWarnings("rawtypes")
  	@ResponseBody
  	@RequestMapping(value = "/validate_data")
  	public Map<String, Object> checkRepeatData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {
  		Map<String, Object> result = new HashMap<String, Object>();
  		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
  		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
  		ObjectMapper mapper = new ObjectMapper();
  		List<PaymentTerm> checkList = new ArrayList<PaymentTerm>();
  		if (StringUtils.isNotEmpty(upadtedList)) {
  			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>(){});
  			checkList.addAll(convertInvoiceInfo(mapper,list));
  		}
  		if (StringUtils.isNotEmpty(insertedList)) {
  			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
  			checkList.addAll(convertInvoiceInfo(mapper,list));
  		}
  		if (!CollectionUtils.isEmpty(checkList)) {
  			Map<String, Object> checkParams = null;
  			boolean duplicate = false;
  			String repeatedData = "";
  			for (PaymentTerm paymentTerm : checkList) {
  				checkParams = new HashMap<String, Object>();
  				checkParams.put("paymentNo", paymentTerm.getPaymentNo());
  				List<PaymentTerm> list =paymentTermManager.findByBiz(null, checkParams);
  				if (list != null && list.size() > 0) {
  					duplicate = true;
  					repeatedData =paymentTerm.getPaymentNo();
  					break;
  				}
  			}
  			if (duplicate) {
  				result.put("success", false);
  				result.put("message", "编码:"+repeatedData+" 已存在");
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
  	private List<PaymentTerm> convertInvoiceInfo(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
  		Class<PaymentTerm> entityClass = (Class<PaymentTerm>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
  		List<PaymentTerm> tl=new ArrayList<PaymentTerm>(list.size());
  		for (int i = 0; i < list.size(); i++) {
  			PaymentTerm type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
  			tl.add(type);
  		}
  		return tl;
  	}
}