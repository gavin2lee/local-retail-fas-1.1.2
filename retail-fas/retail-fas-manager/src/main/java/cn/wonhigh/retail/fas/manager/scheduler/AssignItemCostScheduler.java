package cn.wonhigh.retail.fas.manager.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.CommonConstants;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;

import com.yougou.logistics.base.common.enums.JobBizStatusEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.interfaces.RemoteJobServiceExtWithParams;
import com.yougou.logistics.base.common.model.JobBizLog;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

/**
 * 分配成本定时任务
 * 
 * @author wang.xy1
 * 
 */
@Service
@ManagedResource(objectName = CommonConstants.SYS_NAME + "AssignItemCost", description = StringUtils.EMPTY)
public class AssignItemCostScheduler extends RedundanceSchedule {

	private static final Logger log = Logger.getLogger(AssignItemCostScheduler.class);
 

	@Resource
	private FinancialAccountManager financialAccountManager;

	@Resource
	private ItemCostManager itemCostManager;
 
	@Override
	protected void run(RemoteJobInvokeParamsDto params) {
		try {
			// 定时任务执行的时间是凌晨，需要更新上一天的成本
			Date todayDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(todayDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			todayDate = cal.getTime();

			List<Company> companies = getCompanies(params);//companyManager.findAllCompanyWithoutPermission();
			// 承担总部职能公司
			String financialAccounts = financialAccountManager.findLeadRoleCompanyNos();

			Date start = DateUtil.getFirstDayOfMonth(todayDate);
			Date end = DateUtil.getLastDayOfMonth(todayDate);

			for (Company company : companies) {
				// 承担总部职能公司跳过
				if (financialAccounts.contains(company.getCompanyNo())) {
					continue;
				}
 
				log.info("#####加权成本定时任务执行######  companyNo:" + company.getCompanyNo());
				try {
					itemCostManager.assignBillItemCost(company, null, start, end);
				} catch (ManagerException e) {
					log.error("分配成本价异常，公司编码： " + company.getCompanyNo(), e);
				}
				log.info("#####加权成本定时任务执行完成######  companyNo:" + company.getCompanyNo());
			}
		} catch (Exception e) {
			log.error("查询后台数据公司信息、财务帐套信息出现异常。。。。", e);
		}
	}

}
