package cn.wonhigh.retail.fas.common.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * tms系统缓存
 * 
 * @author wei.hj
 * @date 2013-10-17 下午3:12:47
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class SystemCache {
    
    //系统参数
    public static Map<String,String> systemCurrentParamMap = new LinkedHashMap<String,String>();
    
    public static synchronized void putSystemParam(String paramName,String paramValue){
    	if(StringUtils.isNotEmpty(paramName)){
    		systemCurrentParamMap.put(paramName, paramValue);
    	}
    }
    
    public static synchronized void deleteSystemParam(String paramName){
    	if(StringUtils.isNotEmpty(paramName) && systemCurrentParamMap != null && StringUtils.isNotEmpty(systemCurrentParamMap.get(paramName))){
    		systemCurrentParamMap.remove(paramName);
    	}
    }

}
