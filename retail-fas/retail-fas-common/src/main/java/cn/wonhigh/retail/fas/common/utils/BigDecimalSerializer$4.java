package cn.wonhigh.retail.fas.common.utils;

import java.io.IOException;
import java.math.BigDecimal;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

@Component  
public class BigDecimalSerializer$4 extends JsonSerializer<BigDecimal> {  
      
    @Override  
    public void serialize(BigDecimal value, JsonGenerator jgen,  
            SerializerProvider provider) throws IOException,  
            JsonProcessingException { 
        String str = BigDecimalUtil.formatToStr(value, "0.0000");  
        jgen.writeString(str);  
    }  
}
