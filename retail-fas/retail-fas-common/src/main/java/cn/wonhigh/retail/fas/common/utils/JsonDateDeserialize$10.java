/**  
*   
* 项目名称：retail-fas-common  
* 类名称：JsonDateDeserialize$10  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-11-19 下午6:08:14  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.utils;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonDateDeserialize$10 extends JsonDeserializer<Date> {  
    
	private final static String pattern = "yyyy-MM-dd";

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) 
			throws IOException, JsonProcessingException {
		
		return DateUtil.parseToDate(jp.getText(), pattern);
	}  
}
