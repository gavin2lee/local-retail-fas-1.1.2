package cn.wonhigh.retail.fas.web.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.fas.manager.PeriodBalanceAuditManager;
import cn.wonhigh.retail.gms.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.gms.common.utils.DateUtil;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 批量匹配地区加价规则，生成地区价
 * 
 * @author wang.xy
 * @date 2014-10-9 上午10:31:42
 * @version 0.1.0
 * @copyright yougou.com
 */
public class ItemCostGenerateThread implements Runnable {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ItemCostGenerateThread.class);
	
	private ItemCostManager itemCostManager;

	private ItemCostConditionDto conditionDto;
	
	private boolean storeDimension;

	private PeriodBalanceAuditManager periodBalanceAuditManager;
	
	public ItemCostGenerateThread(ItemCostManager itemCostManager, ItemCostConditionDto dto,
			PeriodBalanceAuditManager periodBalanceAuditManager, boolean storeDimension) {
		this.itemCostManager = itemCostManager;
		this.conditionDto = dto;
		this.storeDimension = storeDimension;
		this.periodBalanceAuditManager = periodBalanceAuditManager;
	}

	@Override
	public void run() {
		
		Map<String, Object> auduitParams = new HashMap<String, Object>();
		Map<String, Object> failedParams = new HashMap<String, Object>();
		try {
			LOGGER.info("###############开始执行生成成本任务###################");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(conditionDto.getStartDate());
			String currentYear = String.valueOf(cal.get(Calendar.YEAR));
			String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
			auduitParams.put("companyNo", conditionDto.getCompanyNo());
			auduitParams.put("status", 0);
			List<PeriodBalanceAudit> existsAudits = periodBalanceAuditManager.findByBiz(null, auduitParams);
			if (!CollectionUtils.isEmpty(existsAudits)) {
				return;
			}
			auduitParams.put("id", -1);
			auduitParams.put("year", currentYear);
			auduitParams.put("month", currentMonth);
			auduitParams.put("createUser", conditionDto.getCreateUser());
			auduitParams.put("createTime", conditionDto.getCreateTime());
			periodBalanceAuditManager.auditPeriodBalance(auduitParams, false);
			
			if (this.storeDimension) {
				//机构结存
				itemCostManager.generateStoreItemCost(this.conditionDto);
			}else {
				//公司结存
				itemCostManager.generateCompanyItemCost(this.conditionDto);
			}
			
			Map<String, Object> sucParams = new HashMap<String, Object>();
			sucParams.put("id", auduitParams.get("id"));
			sucParams.put("status", 1);
			sucParams.put("updateUser", conditionDto.getCreateUser());
			sucParams.put("updateTime", DateUtil.getCurrentDateTime());
			periodBalanceAuditManager.auditPeriodBalance(sucParams, true);
			
			LOGGER.info("###############生成成本成功###################");
		} catch (Exception e) {
			LOGGER.error("生成成本失败。", e);
			try {
				if (-1 != (int) auduitParams.get("id")) {
					failedParams.put("id", auduitParams.get("id"));
					failedParams.put("status", 2);
					failedParams.put("updateUser", conditionDto.getCreateUser());
					failedParams.put("updateTime", DateUtil.getCurrentDateTime());
					periodBalanceAuditManager.auditPeriodBalance(failedParams, true);
				}
			} catch (ManagerException e1) {
			}
		}
	}

}
