package cn.wonhigh.retail.fas.common.exportformat;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * TODO: 增加描述
 * 
 * @author 杨勇
 * @date 2014-7-8 下午5:20:06
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings("rawtypes")
public class AbstractExportFormat<T> {

	public Map format(List<String> fields, T obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(obj);
		Map map = mapper.readValue(json, Map.class);
		return map;
	}

	protected Object getFieldVal(String field, T obj) throws Exception {
		Method getMethod = obj.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() 
				+ field.substring(1));
		return getMethod.invoke(obj);
	}
}
