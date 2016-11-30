package cn.wonhigh.retail.fas.common.utils;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

@Component  
public class JsonDateDeserialize$19 extends JsonDeserializer<Date> {  
      
	private final static String pattern = "yyyy-MM-dd HH:mm:ss";

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) 
			throws IOException, JsonProcessingException {
		
		return DateUtil.parseToDate(jp.getText(), pattern);
	}  
}
