package cn.wonhigh.retail.fas.manager.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.CommonConstants;
import cn.wonhigh.retail.fas.manager.PreWarnTemplateManager;

import com.yougou.logistics.base.common.enums.JobBizStatusEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.interfaces.RemoteJobServiceExtWithParams;
import com.yougou.logistics.base.common.model.JobBizLog;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

/****
 * 首页预警消息
 * @author Administrator
 *
 */
@Deprecated
@Service
@ManagedResource(objectName = CommonConstants.SYS_NAME + "PreWarnMessageCheck", description = StringUtils.EMPTY)
public class PreWarnMessageCheckScheduler implements RemoteJobServiceExtWithParams {
	
	private static final Logger log = Logger.getLogger(PreWarnMessageCheckScheduler.class);
	
	@Resource
	private PreWarnTemplateManager preWarnTemplateManager;
	
	/**
	 * 调度错误日志
	 */
	private static final List<JobBizLog> JOB_BIZ_LOG = new ArrayList<JobBizLog>();

	/**
	 * 调度执行状态
	 */
	private static JobBizStatusEnum jobBizStatusEnum;

	@Override
	public void executeJobWithParams(String jobId, String triggerName, String groupName,
			RemoteJobInvokeParamsDto remoteJobInvokeParamsDto) {
		
		try {
			preWarnTemplateManager.check(null);
		} catch (ManagerException e) {
			log.info(e.getMessage() + "   " + e);
			System.out.println(e.getMessage() + "   " + e);
		}
	}

	@Override
	public JobBizStatusEnum getJobStatus(String jobId, String arg0, String arg1) {

		return jobBizStatusEnum;
	}

	@Override
	public String getLogs(String jobId, String triggerName, String groupName, long lastDate) {

		String listStr = "[]";
		List<JobBizLog> list = JOB_BIZ_LOG;
		JobBizLog jobBizLog = new JobBizLog();
		jobBizLog.setGroupName(groupName);
		jobBizLog.setTriggerName(triggerName);
		jobBizLog.setRemark("拉远程日志48");
		jobBizLog.setType("INTERRUPTED");
		// GMT时间
		jobBizLog.setGmtDate(new Date().getTime());
		JOB_BIZ_LOG.add(jobBizLog);
		
		if (list.size() == 0) {
			return listStr;
		}
		Iterator<JobBizLog> it = list.iterator();
		while (it.hasNext()) {
			JobBizLog log = it.next();
			if (null != log.getGmtDate() && log.getGmtDate() <= lastDate) {
				it.remove();
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			listStr = mapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
			log.error("给调度框架传数据报错！", e);
		} catch (JsonMappingException e) {
			log.error("给调度框架传数据报错！", e);
		} catch (IOException e) {
			log.error("给调度框架传数据报错！", e);
		}
		return listStr;

	}

	@Override
	public void initializeJob(String jobId, String arg0, String arg1) {

	}

	@Override
	public void pauseJob(String jobId, String arg0, String arg1) {

	}

	@Override
	public void restartJob(String jobId, String arg0, String arg1) {

	}

	@Override
	public void resumeJob(String jobId, String arg0, String arg1) {

	}

	@Override
	public void stopJob(String jobId, String arg0, String arg1) {

	}


}
