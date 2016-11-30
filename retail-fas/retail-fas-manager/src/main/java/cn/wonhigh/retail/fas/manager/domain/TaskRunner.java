package cn.wonhigh.retail.fas.manager.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import cn.wonhigh.retail.backend.cache.RedisStorage;
import cn.wonhigh.retail.backend.utils.JsonUtils;

public class TaskRunner {
//  容我想想	
//	private static final XLogger logger = XLoggerFactory.getXLogger(ItemCostGenerateTask.class);
//	protected String ticket;
//	protected final Map<String, Object> statusMap = new HashMap<>();
//	protected long TIME_OUT = 1 * 60 * 1000;
//	
//	private ITask task;
//	private ITaskArgs taskArgs;
//	private String runnerName;
//	
//	public TaskRunner(ITask task,ITaskArgs args,String name){
//		this(task, args);
//		this.runnerName = name;
//	}
//	
//	public TaskRunner(ITask task,ITaskArgs args){
//		this.task = task;
//		this.taskArgs = args;
//	}
//	
//	public ITaskResult run(){
//		try {
//			return this.task.start(this.taskArgs);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return null;
//		}
//	}
//	
//	protected void updateStatus(int index, int count, int status, String msg) {
//		if(ticket == null)
//			return;
//		String str = "";
//		statusMap.put("index", index);
//		statusMap.put("status", status);
//		statusMap.put("ticket", ticket);
//		statusMap.put("count", count);
//		statusMap.put("info", msg);
//
//		try {
//			str = JsonUtils.toJson(statusMap);
//		} catch (IOException e) {
//
//		}
//		logger.info(String.format("生成成本,%s,%d/%d", msg, index, count));	
//		RedisStorage.getCurrent().set(ticket, str, TIME_OUT);
//	}
}
