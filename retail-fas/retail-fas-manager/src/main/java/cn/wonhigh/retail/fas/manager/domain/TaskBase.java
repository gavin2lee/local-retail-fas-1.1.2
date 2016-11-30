package cn.wonhigh.retail.fas.manager.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import cn.wonhigh.retail.backend.cache.RedisStorage;
import cn.wonhigh.retail.backend.utils.JsonUtils;

public abstract class TaskBase {
	
	protected static final XLogger logger = XLoggerFactory.getXLogger(TaskBase.class);
	protected String ticket;
	protected final Map<String, Object> statusMap = new HashMap<>();
	protected long TIME_OUT = 1 * 60 * 1000;
	
	final Integer START = 1;
	final Integer SUCCESS = 2;
	final Integer FAILED = -1;
	
	protected void updateStatus(Integer index, Integer count, Integer status, String msg) {
		if(ticket == null)
			return;
		String str = "";
		statusMap.put("index", index);
		statusMap.put("status", status);
		statusMap.put("ticket", ticket);
		statusMap.put("count", count);
		statusMap.put("info", msg);

		try {
			str = JsonUtils.toJson(statusMap);
		} catch (IOException e) {

		}
//		logger.info(String.format("生成成本,%s,%d/%d", msg, index, count));	
		RedisStorage.getCurrent().set(ticket, str, TIME_OUT);
	}
}
