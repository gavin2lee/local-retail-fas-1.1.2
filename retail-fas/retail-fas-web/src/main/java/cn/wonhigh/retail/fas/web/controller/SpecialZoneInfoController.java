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
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.SpecialZoneInfo;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.manager.SpecialZoneInfoManager;
import cn.wonhigh.retail.fas.manager.ZoneInfoManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.annotation.ModuleVerify;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-04-07 18:18:17
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
@RequestMapping("/special_zone_info")
@ModuleVerify("30120023")
public class SpecialZoneInfoController extends BaseController<SpecialZoneInfo> {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(SpecialZoneInfoController.class); 
	
    @Resource
    private SpecialZoneInfoManager specialZoneInfoManager;
    @Resource
    private ZoneInfoManager zoneInfoManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("special_zone_info/",specialZoneInfoManager);
    }
    
    /**
	 * 根据公司编号及结算月，判断是否已存在记录，如果存在，刚不能再新增
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/validate_data", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIsExist(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		try {
			String insertedList = StringUtils.isEmpty(request.getParameter("inserted")) ? "" : request.getParameter("inserted");
//			String updatedList = StringUtils.isEmpty(request.getParameter("updated")) ? "" : request.getParameter("updated");
			ObjectMapper mapper = new ObjectMapper();
			if (StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
				List<SpecialZoneInfo> oList= convertListWithTypeReference(mapper,list);
				if(CollectionUtils.isNotEmpty(oList)){
					Map<String, Object> params = null;
					List<SpecialZoneInfo> specialZoneInfos = null;
					List<ZoneInfo> zoneInfos = null;
					
					//本次新增大区重复控制
					Map<String, Object> zoneNos = new HashMap<String, Object>() ;
					Map<String, Object> zoneNames = new HashMap<String, Object>() ;
					
					for (SpecialZoneInfo model : oList) {
						if (zoneNos.containsKey(model.getZoneNo())) {
							map.put("success", false);
							map.put("message", "大区编号："+model.getZoneNo()+"已存在，不能再新增。");
							break;
						}
						if (zoneNames.containsKey(model.getName())) {
							map.put("success", false);
							map.put("message", "大区名称："+model.getName()+"已存在，不能再新增。");
							break;
						}
						zoneNos.put("zoneNo", model.getZoneNo());
						zoneNames.put("zoneName", model.getName());
						
						params = new HashMap<String, Object>();
						params.put("zoneNo", model.getZoneNo());
						specialZoneInfos = specialZoneInfoManager.findByBiz(null, params);
						if(CollectionUtils.isNotEmpty(specialZoneInfos)){
							map.put("success", false);
							map.put("message", "大区编号："+model.getZoneNo()+"已存在，不能再新增。");
							break;
						}
						zoneInfos = zoneInfoManager.findByBiz(null, params);
						if(CollectionUtils.isNotEmpty(zoneInfos)){
							map.put("success", false);
							map.put("message", "大区编号："+model.getZoneNo()+"已存在，不能再新增。");
							break;
						}
						params.remove("zoneNo");
						params.put("name", model.getName());
						specialZoneInfos = specialZoneInfoManager.findByBiz(null, params);
						if(CollectionUtils.isNotEmpty(specialZoneInfos)){
							map.put("success", false);
							map.put("message", "大区名称："+model.getZoneNo()+"已存在，不能再新增。");
							break;
						}
						zoneInfos = zoneInfoManager.findByBiz(null, params);
						if(CollectionUtils.isNotEmpty(zoneInfos)){
							map.put("success", false);
							map.put("message", "大区名称："+model.getZoneNo()+"已存在，不能再新增。");
							break;
						}
					}
				}
			}
		} catch(Exception e) {
			logger.info("校验大区错误，校验失败！");
			map.put("success", false);
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
	private List<SpecialZoneInfo> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<SpecialZoneInfo> entityClass = (Class<SpecialZoneInfo>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<SpecialZoneInfo> tl=new ArrayList<SpecialZoneInfo>(list.size());
		for (int i = 0; i < list.size(); i++) {
			SpecialZoneInfo type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
}