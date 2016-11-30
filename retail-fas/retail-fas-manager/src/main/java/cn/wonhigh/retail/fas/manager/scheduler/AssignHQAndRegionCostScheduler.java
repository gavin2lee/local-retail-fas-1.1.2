package cn.wonhigh.retail.fas.manager.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.CommonConstants;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

/**
 *  分配成本定时任务
 * @author wang.xy1
 *
 */
@Service
@ManagedResource(objectName = CommonConstants.SYS_NAME + "AssignHQAndRegionCost", description = StringUtils.EMPTY)
public class AssignHQAndRegionCostScheduler extends RedundanceSchedule {

 
	@Resource
	private ItemCostManager itemCostManager;
	

	
    @Resource
	private FinancialAccountManager financialAccountManager;
 

	@Override
	protected void run(RemoteJobInvokeParamsDto params) {
		try {
			//定时任务执行的时间是凌晨，需要更新上一天的成本
			Date todayDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(todayDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			todayDate = cal.getTime();
			
			List<Company> companies = getCompanies(params);
			//承担总部职能公司
			String financialAccounts = financialAccountManager.findLeadRoleCompanyNos(); 
			
			Date start = DateUtil.getFirstDayOfMonth(todayDate);
			Date end = DateUtil.getLastDayOfMonth(todayDate);
			for (Company company : companies) {
				//承担总部职能公司跳过
				if (financialAccounts.contains(company.getCompanyNo())) {
					continue;
				}
			 
				//调用GMS接口，检验是否存在单据异常
				log.info("#####加权成本定时任务执行######  companyNo:  " + company.getCompanyNo());
				try {
					itemCostManager.assignBillRegionCost(company,null,start,end); 
				} catch (ManagerException e) {
					log.error("分配地区价格失败。公司编码为： " + company.getCompanyNo(), e);
				}
				
				try { 
					itemCostManager.assignBillHQCost(company,null,start,end);
					log.info("#####加权成本定时任务执行完成######  companyNo:  " + company.getCompanyNo());
				} catch (ManagerException e) {
					log.error("分配总部价格失败。公司编码为： " + company.getCompanyNo(), e);
				}
				
				
			}
		} catch (Exception e) {
			log.error("查询后台数据公司信息、财务帐套信息出现异常。。。。", e);
		}
	}

	 
}
