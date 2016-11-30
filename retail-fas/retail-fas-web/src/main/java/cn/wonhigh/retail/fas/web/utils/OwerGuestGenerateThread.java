package cn.wonhigh.retail.fas.web.utils;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.CompanyPeriodBalanceManager;

/** 
* @author 王仕秒
* @version 创建时间：2016-7-11 上午11:07:06 
*/
public class OwerGuestGenerateThread implements Runnable {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(OwerGuestGenerateThread.class);
	
	private CompanyPeriodBalanceManager companyPeriodBalanceManager;
	
	private Map<String, Object> params;
	
	private SystemUser systemUser;
	
	public OwerGuestGenerateThread(CompanyPeriodBalanceManager companyPeriodBalanceManager, Map<String, Object> params, SystemUser systemUser) {
		this.companyPeriodBalanceManager = companyPeriodBalanceManager;
		this.params = params;
		this.systemUser = systemUser;
	}

	@Override
	public void run() {
		try {
			Authorization.setUser(systemUser);//设置主线程登陆信息
			LOGGER.info("###############开始执行生成累计欠客任务###################");
			
			String year = params.get("year").toString();
			String month = params.get("month").toString();
			params.put("lastMonthDate", DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month)));
			params.put("isPE", String.valueOf(ShardingUtil.isPE()));
			
			companyPeriodBalanceManager.generateCompanyOwerGuest(params);

			String shardingFlag = "";
			String organTypeNo = systemUser.getOrganTypeNo();
			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){// 集团总部
				shardingFlag = "0_Z";
			}else{
				shardingFlag = organTypeNo + "_" + params.get("companyNo").toString().substring(0, 1);
			}
			
			String loginUser =systemUser.getLoginName();
			params.put("createUser", loginUser);
			
			Date currDate = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
			params.put("startDate", DateUtil.getFirstDayOfMonth(currDate));
			params.put("endDate", currDate);
			params.put("shardingFlag", shardingFlag);
			
			companyPeriodBalanceManager.generateCompanySalesSum(params);
		} catch (Exception e) {
			LOGGER.error("生成本月累计欠客数据失败!", e);
		} 
		
	}

}
