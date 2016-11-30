package cn.wonhigh.retail.fas.manager.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.manager.CompanyManager;

import com.yougou.logistics.base.common.enums.JobBizStatusEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.interfaces.RemoteJobServiceExtWithParams;
import com.yougou.logistics.base.common.model.JobBizLog;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

public abstract class RedundanceSchedule implements RemoteJobServiceExtWithParams {

	/**
	 * 调度错误日志
	 */
	private final List<JobBizLog> JOB_BIZ_LOG = new ArrayList<JobBizLog>();

	@Resource
	private CompanyManager companyManager;

	protected List<Company> getCompanies(RemoteJobInvokeParamsDto params) throws ManagerException {
		if (params == null) {
			return companyManager.findAllCompanyWithoutPermission();
		}
		String companyNo = params.getParam("companyNo");
		if (StringUtils.isEmpty(companyNo))
			return companyManager.findAllCompanyWithoutPermission();
		HashMap<String, Object> vals = new HashMap<>();
		vals.put("companyNo", companyNo);
		return companyManager.findByBiz(new Company(), vals);
	}

	/**
	 * 调度执行状态
	 */
	private JobBizStatusEnum jobBizStatusEnum = JobBizStatusEnum.INITIALIZED;

	protected static final XLogger log = XLoggerFactory.getXLogger(RedundanceSchedule.class);

	@Override
	public void initializeJob(String s, String s1, String s2) {

	}

	@Override
	public void executeJobWithParams(String jobId, String triggerName, String groupName,
			RemoteJobInvokeParamsDto remoteJobInvokeParamsDto) {
		try {

			saveJobBizLog("开始", triggerName, groupName);
			jobBizStatusEnum = JobBizStatusEnum.RUNNING;
			run(remoteJobInvokeParamsDto);
			jobBizStatusEnum = JobBizStatusEnum.FINISHED;
			saveJobBizLog("结束", triggerName, groupName);
			return;

		} catch (Exception e) {
			jobBizStatusEnum = JobBizStatusEnum.INTERRUPTED;
			saveJobBizLog("异常" + e.getMessage(), triggerName, groupName);
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void pauseJob(String s, String s1, String s2) {

	}

	@Override
	public void resumeJob(String s, String s1, String s2) {

	}

	@Override
	public void stopJob(String s, String s1, String s2) {

	}

	@Override
	public void restartJob(String s, String s1, String s2) {

	}

	@Override
	public JobBizStatusEnum getJobStatus(String s, String s1, String s2) {
		return jobBizStatusEnum;
	}

	@Override
	public String getLogs(String jobId, String triggerName, String groupName, long lastDate) {
		synchronized (this) {
			String listStr = "[]";
			if (JOB_BIZ_LOG.size() == 0) {
				return listStr;
			}
			List<JobBizLog> list = JOB_BIZ_LOG;
			JobBizLog jobBizLog = new JobBizLog();
			jobBizLog.setGroupName(groupName);
			jobBizLog.setTriggerName(triggerName);
			jobBizLog.setRemark("拉远程日志:" + this.getClass().getName());
			jobBizLog.setType(jobBizStatusEnum.name());
			// GMT时间
			jobBizLog.setGmtDate(new Date().getTime());
			JOB_BIZ_LOG.add(jobBizLog);
			ObjectMapper mapper = new ObjectMapper();
			try {
				listStr = mapper.writeValueAsString(list);
				list.clear();
			} catch (JsonGenerationException e) {
				log.error("给调度框架传数据报错！", e);
			} catch (JsonMappingException e) {
				log.error("给调度框架传数据报错！", e);
			} catch (IOException e) {
				log.error("给调度框架传数据报错！", e);
			}
			return listStr;
		}
	}

	private synchronized void saveJobBizLog(String errorMsg, String triggerName, String groupName) {
		JobBizLog jobBizLog = new JobBizLog();
		jobBizLog.setTriggerName(triggerName);
		jobBizLog.setGroupName(groupName);
		jobBizLog.setType(jobBizStatusEnum.name());
		jobBizLog.setGmtDate(System.currentTimeMillis());
		jobBizLog.setRemark(errorMsg);
		JOB_BIZ_LOG.add(jobBizLog);
	}

	protected abstract void run(RemoteJobInvokeParamsDto params);

}
