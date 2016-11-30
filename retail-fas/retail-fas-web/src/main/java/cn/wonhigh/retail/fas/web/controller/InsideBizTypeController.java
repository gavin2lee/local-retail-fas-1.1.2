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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.InsideBizType;
import cn.wonhigh.retail.fas.common.model.InsideBizTypeDetail;
import cn.wonhigh.retail.fas.manager.InsideBizTypeManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 基础数据设置-内购业务类型Controller
 * @author wangyj
 * @date  2015-04-02 13:45:59
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
@RequestMapping("/inside_biz_type")
@ModuleVerify("30100016")
public class InsideBizTypeController extends BaseController<InsideBizType> {
    @Resource
    private InsideBizTypeManager insideBizTypeManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("inside_biz_type/",insideBizTypeManager);
    }
    
    /**
     * 校验是否包含重复数据
     * @param financialAccount
     * @return
     * @throws ManagerException 
     */
    @ResponseBody
    @RequestMapping(value="/check_Repect", method=RequestMethod.POST)
    public boolean checkRepeatData(@ModelAttribute("model") InsideBizType model) throws ManagerException{
    	Map<String,Object> params=new HashMap<String, Object>();
    	//根据公司及业务类型编号校验唯一性
    	params.put("companyNo", model.getCompanyNo());
    	params.put("bizTypeCode", model.getBizTypeCode());
    	List<InsideBizType> list=insideBizTypeManager.findByBiz(model, params);
    	if (list != null && list.size() > 0) {
			for (InsideBizType m : list) {
				if (!m.getId().equals(model.getId())) {
					return true;
				}
			}
		}
		return false;
    }
    
    @RequestMapping(value = "/add")
	@ResponseBody
	public InsideBizType add(InsideBizType obj,Model model, HttpServletRequest req)throws Exception{
    	Map<String, Object> params = builderParams(req, model);
		String insertedRows = req.getParameter("insertedRows");
		String insertedClientRows = req.getParameter("insertedClientRows");
		if(obj.getId()!=null&&!obj.getId().equals("")){
			obj.setInsideId(Integer.valueOf(obj.getId()));
		}else{
			obj.setInsideId(null);
		}
		obj.setStatus(1);
		InsideBizType insideBizType = new InsideBizType();
		List<Object> insertedList = convertListWithTypeReference(insertedRows, InsideBizTypeDetail.class);
		List<Object> clientRows = convertListWithTypeReference(insertedClientRows, InsideBizTypeDetail.class);
		insideBizType=insideBizTypeManager.add(obj, insertedList,clientRows,params);
		insideBizType.setBillNo("success");
		return insideBizType;
	}
    
    @RequestMapping(value = "/updateKey")
	@ResponseBody
	public InsideBizType updateInside(InsideBizType obj, HttpServletRequest req)throws Exception{
    	InsideBizType insideBizType = new InsideBizType();
		String insertedRows = req.getParameter("insertedRows");
//		String updatedRows = req.getParameter("updatedRows");
		String deletedRows = req.getParameter("deletedRows");
		// 客户信息
		String clientInsRows = req.getParameter("clientInsRows");
		String clientUpRows = req.getParameter("clientUpRows");
		String clientDelRows = req.getParameter("clientDelRows");
		List<Object> insertedList = convertListWithTypeReference(insertedRows, InsideBizTypeDetail.class);
//		List<Object> updatedList = convertListWithTypeReference(updatedRows, InsideBizTypeDetail.class);
		List<Object> deletedList = convertListWithTypeReference(deletedRows, InsideBizTypeDetail.class);
		
		List<Object> clientInsList = convertListWithTypeReference(clientInsRows, InsideBizTypeDetail.class);
		List<Object> clientUpList = convertListWithTypeReference(clientUpRows, InsideBizTypeDetail.class);
		List<Object> clientDelList = convertListWithTypeReference(clientDelRows, InsideBizTypeDetail.class);
		deletedList.addAll(clientDelList);
		insideBizType=insideBizTypeManager.update(obj, insertedList, clientUpList, deletedList,clientInsList);
		insideBizType.setBillNo("success");
		return insideBizType;
	}
    
    @RequestMapping(value = "/del")
	@ResponseBody
	public Integer del(InsideBizType obj, HttpServletRequest req)throws Exception{
		String checkedRows = req.getParameter("checkedRows");
		if(StringUtils.isNotBlank(checkedRows)){
			List<Object> list = convertListWithTypeReference(checkedRows, InsideBizType.class);
			return insideBizTypeManager.delete(list);
		}
		return 0;
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
	private List<Object> convertListWithTypeReference(String rows, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(rows, new TypeReference<List<Map>>() {});
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
}