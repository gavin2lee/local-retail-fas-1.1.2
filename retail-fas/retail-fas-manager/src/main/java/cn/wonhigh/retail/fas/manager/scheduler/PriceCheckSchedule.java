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
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.ExceptionPriceBillManager;

import com.yougou.logistics.base.common.enums.JobBizStatusEnum;
import com.yougou.logistics.base.common.interfaces.RemoteJobServiceExtWithParams;
import com.yougou.logistics.base.common.model.JobBizLog;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

/**
 * 异常价格检查
 * @author wang.xy1
 *
 */
@Service
@ManagedResource(objectName = CommonConstants.SYS_NAME + "ExceptionPriceCheck", description = StringUtils.EMPTY)
public class PriceCheckSchedule extends RedundanceSchedule { 

    @Resource
	private ExceptionPriceBillManager exceptionPriceBillManager;
 

	@Override
	public void executeJobWithParams(String jobId, String triggerName, String groupName,
			RemoteJobInvokeParamsDto remoteJobInvokeParamsDto) {
		
	}

	 

	/**
	 * 定时任务执行的时间是凌晨，需要更新上一天的成本
	 */
	@Override
	protected void run(RemoteJobInvokeParamsDto params) {
		try { 
			log.info("异常价格定时任务执行开始");
			Date todayDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(todayDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			todayDate = cal.getTime();
			
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("startDate", DateUtil.getFirstDayOfMonth(todayDate));
			paramsMap.put("endDate", DateUtil.getLastDayOfMonth(todayDate));
			
			List<Company> companies = getCompanies(params);
			for (Company company : companies) {
				//体育不需要
				if(!"U010102".equals(company.getOrganTypeNo())){  
					paramsMap.put("companyNos", FasUtil.formatInQueryCondition(company.getCompanyNo()));
					
					log.info(" BUY 异常价格检查：" + company.getCompanyNo());
					exceptionPriceBillManager.updateBuyBillRegionPrice(paramsMap);
					
					log.info(" SALE 异常价格检查：" + company.getCompanyNo());
					exceptionPriceBillManager.updateSaleBillRegionPrice(paramsMap);
					
					log.info(" Purchase 异常价格检查：" + company.getCompanyNo());
					//采购价更新,bill_buy_balance, 1301,1304,1333, 关联更新的bill_sale_balance的1301单据
					exceptionPriceBillManager.updateBuyBillPurchasePrice(paramsMap);
				}
			}
			
			log.info("异常价格定时任务执行结束");
		} catch (Exception e) {
			// 如果异常传JobBizLog对象到Scheduler
			log.error("异常价格定时任务执行错误", e);
		}
	}


}
