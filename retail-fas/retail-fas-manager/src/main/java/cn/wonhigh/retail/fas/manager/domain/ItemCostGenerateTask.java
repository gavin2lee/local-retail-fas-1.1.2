package cn.wonhigh.retail.fas.manager.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.cache.RedisStorage;
import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.backend.utils.JsonUtils;
import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.CompanyPeriodBalanceService;
import cn.wonhigh.retail.fas.service.PeriodBalanceAuditService;
import cn.wonhigh.retail.fas.service.PeriodBalanceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Component("itemCostGenerateTask")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemCostGenerateTask implements Runnable,ICostGenerate {

	private static final XLogger logger = XLoggerFactory.getXLogger(ItemCostGenerateTask.class);

	@Resource
	private PeriodBalanceService periodBalanceService;

	@Resource
	private PeriodBalanceAuditService periodBalanceAuditService;

	@Resource
	private CompanyPeriodBalanceService companyPeriodBalanceService;

	@Resource
	private BrandService brandService;

	@Resource
	private BillBuyBalanceService billBuyBalanceService;

	ItemCost itemCost;
	final Map<String, Object> statusMap = new HashMap<>();
	private ItemCostConditionDto dto;
	private Map<String, Object> params;
	String ticket;
	Map<String, Object> auduitParams = new HashMap<String, Object>();
	String preYear;
	String preMonth;
	String userName;
	
	public String start(ItemCost itemCost) {
		this.itemCost = itemCost;
		this.params = new HashMap<String, Object>();
		this.userName= Authorization.getUser().getLoginName();
		try {
			this.dto = combileDtoParams(itemCost, params);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		// 先获取本月所属年月
		Calendar cal = Calendar.getInstance();
		cal.setTime(dto.getStartDate());
		year = String.valueOf(cal.get(Calendar.YEAR));
		month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		startDate = DateUtil.getFirstDayOfMonthStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
		endDate = DateUtil.getLastDayOfMonthStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

		// 先获取上月所属年月
		cal.add(Calendar.MONTH, -1);
		preYear = String.valueOf(cal.get(Calendar.YEAR));
		preMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		auduitParams.put("status", String.valueOf(0));
		auduitParams.put("companyNo", dto.getCompanyNo()); 
		try {
			this.dto = combileDtoParams(itemCost, params);
			List<PeriodBalanceAudit> existsAudits = periodBalanceAuditService.findByBiz(null, auduitParams);
			if (!CollectionUtils.isEmpty(existsAudits)) {
				return "-1";
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		
		auduitParams.put("year", year);
		auduitParams.put("month", month); 
		
		auduitParams.put("brandUnitNo", dto.getBrandUnitNo());		
		Thread thread = new Thread(this);
		thread.start();

		ticket = UUIDGenerator.getUUID();
		return ticket;
	}

	String startDate;
	String endDate;
	String year;
	String month;

	int step = 0;

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		Thread.currentThread().setName(name + "&" + userName);
		
		int isSuccess = 1; // 1表示生成成功，2表示生成失败
		try {
			auduitParams.put("id", -1);
			auduitParams.put("brandUnitName", dto.getBrandUnitName());
			auduitParams.put("createUser", dto.getCreateUser());
			auduitParams.put("createTime", dto.getCreateTime());

			params.put("year", preYear);
			params.put("month", preMonth);
			updateStatus(1, 1, 1, "生成成本开始...");

			generateCostAndPeriodCase();

			updateStatus(step++, 1, 2, "生成成本结束.");
		} catch (Exception e) {
			updateStatus(step, steps, -1, "生成成本错误.");
			logger.error(e.getMessage(), e);
			isSuccess = 2;
		} finally {
			this.updatePeriodBalanceAudit(auduitParams, dto, isSuccess); // 更新生成成本日志状态
		}

	}

	// 更新生成成本日志状态
	private void updatePeriodBalanceAudit(Map<String, Object> auduitParams, ItemCostConditionDto dto, Integer status) {
		try {
			Map<String, Object> sucParams = new HashMap<String, Object>();
			sucParams.put("companyNo", auduitParams.get("companyNo"));
			sucParams.put("year", auduitParams.get("year"));
			sucParams.put("month", auduitParams.get("month"));
			sucParams.put("brandUnitNo", auduitParams.get("brandUnitNo"));
			sucParams.put("oldStatus", Integer.valueOf(0));
			sucParams.put("status", status);
			sucParams.put("updateUser", dto.getCreateUser());
			sucParams.put("updateTime", DateUtil.getCurrentDateTime());
			periodBalanceAuditService.auditPeriodBalance(sucParams, true);
		} catch (Exception e) {
			logger.error("更新生成成本日志失败!", e);
		}
	}

	int steps = 15;

	private void generateCostAndPeriodCase() throws Exception {

		periodBalanceAuditService.auditPeriodBalance(auduitParams, false);

		// int totalCount =
		// companyPeriodBalanceService.selectWeightedCostCount(params);

		List<String> lists = new ArrayList<String>();
		HashMap<String, CompanyPeriodBalance> periodBalanceHashMap = null;
		HashMap<String, BillBuyBalance> billBuyBalanceHashMap = null;

		int pageNo = 1;// 当前页数
		SimplePage page = new SimplePage(pageNo, Integer.MAX_VALUE, Integer.MAX_VALUE);

		updateStatus(step++, steps, 2, "获取上月期末.");
		// 获取上月期末
		List<CompanyPeriodBalance> periodBalances = companyPeriodBalanceService.selectTheFinalCostByPage(page, "", "",
				params, null);
		// 本月买卖
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("isPE", String.valueOf(ShardingUtil.isPEByShardingFlag(dto.getShardingFlag())));

		updateStatus(step++, steps, 1, "获取采购价格.");
		List<BillBuyBalance> billBuyBalances = billBuyBalanceService.selectPurchaseRegionCost(params);

		updateStatus(step++, steps, 1, "获取上期成本.");
		periodBalanceHashMap = companyPeriodBalanceClassTypeHashMap(periodBalances, lists);

		updateStatus(step++, steps, 1, "获取上期成本.");
		billBuyBalanceHashMap = billBuyBalanceClassTypeHashMap(billBuyBalances, lists);

		List<String> list1 = removeRepeat(lists); // 去除重复item_no

		dto.setCurrentYear(year);
		dto.setCurrentMonth(month);
		dto.setLastYear(preYear);
		dto.setLastMonth(preMonth);
		dto.setIsPE(String.valueOf(ShardingUtil.isPEByShardingFlag(dto.getShardingFlag())));

		// 生成成本
		logger.info("################ 生成成本开始   ###################");
		params.put("year", year);
		params.put("month", month);
		if (list1.size() > 0) {
			updateStatus(step++, steps, 1, "生成成本...");
			periodBalanceService.generateItemCost(list1, dto, params, periodBalanceHashMap, billBuyBalanceHashMap);
		} else {
			updateStatus(step++, steps, 1, "上月期末无结存或本月未发生买卖...");
			logger.info("####公司" + dto.getCompanyNo() + "上月期末无结存或本月未发生买卖####");
		}
		this.generatePeriodBalance(dto, params);
		
		updateStatus(step++, steps, 1, "生成公司结存完成");

	}
	
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void generatePeriodBalance(ItemCostConditionDto dto, Map<String, Object> params) throws ServiceException {
		// 按店铺生成结存
		logger.info("################ 生成店铺结存开始   ###################");
		this.shopPeriodBalanceMenthod(dto, params);

		// 按公司生成结存
		logger.info("################ 生成公司结存开始   ###################");
		this.companyPeriodBalanceMenthod(dto, params);
	}

	private void companyPeriodBalanceMenthod(ItemCostConditionDto dto, Map<String, Object> params)
			throws ServiceException {
		updateStatus(step++, steps, 1, "生成本月公司结存...");
		// 生成本月结存
		companyPeriodBalanceService.generateBalancePreData(dto, params);

		updateStatus(step++, steps, 1, "回写地区价到公司期间结存入库类金额...");
		// 回写地区价到公司期间结存入库类金额
		companyPeriodBalanceService.writeBackAmount(dto);

		updateStatus(step++, steps, 1, "回写单位成本到公司结存表的出库类汇总金额...");
		// 回写单位成本到公司结存表的出库类汇总金额
		companyPeriodBalanceService.itemCostFeedbackToPeriodBalance(params);

		updateStatus(step++, steps, 1, "更新库存调整金额...");
		// 重刷库存调整金额
		companyPeriodBalanceService.writeBackCostAdjustmentAmout(params);
	}

	/**
	 * 按照店铺生成结存
	 * 
	 * @throws ServiceException
	 */
	private void shopPeriodBalanceMenthod(ItemCostConditionDto dto, Map<String, Object> params) throws ServiceException {
		updateStatus(step++, steps, 1, "清除店铺本月结存数据...");
		// 先删除本公司,本月的所有结存数据
		periodBalanceService.deleteCompanyMonthPeriodBalance(params);

		updateStatus(step++, steps, 1, "生成店铺本月结存...");
		// 生成本月结存
		periodBalanceService.transferInventoryBookToPeriodBalance(dto, params);

		updateStatus(step++, steps, 1, "回写地区价到店铺结存入库类金额...");
		// 回写地区价到店铺结存入库类金额
		periodBalanceService.writeBackAmount(dto);

		updateStatus(step++, steps, 1, "回写单位成本到店铺结存表的出库类汇总金额...");
		// 回写单位成本到店铺结存表的出库类汇总金额
		periodBalanceService.itemCostFeedbackToPeriodBalance(params);

		// 回写成本到结存表的出库类汇总金额
		/* periodBalanceService.itemCostFeedbackToPeriodBalance(dto, params); */

		// 重刷库存调整金额
		/* periodBalanceService.writeBackCostAdjustmentAmout(params); */
	}

	/**
	 * 去除重复方法
	 * 
	 * @param list
	 * @return
	 */
	private List<String> removeRepeat(List<String> l) {
		HashSet<String> hs = new HashSet<String>(l);
		List<String> list = new ArrayList<String>(hs);
		return list;
	}

	private HashMap<String, BillBuyBalance> billBuyBalanceClassTypeHashMap(List<BillBuyBalance> list, List<String> lists) {
		HashMap<String, BillBuyBalance> billBuyBalanceHashMap = new HashMap<String, BillBuyBalance>();
		for (BillBuyBalance billBuyBalance : list) {
			billBuyBalanceHashMap.put(billBuyBalance.getItemNo(), billBuyBalance);
			lists.add(billBuyBalance.getItemNo());
		}
		return billBuyBalanceHashMap;
	}

	/**
	 * 将上月期末结存数据存入HashMap，并且截取itemNo存入另一个的List
	 * 
	 * @param list
	 * @param lists
	 * @return
	 */
	private HashMap<String, CompanyPeriodBalance> companyPeriodBalanceClassTypeHashMap(List<CompanyPeriodBalance> list,
			List<String> lists) {
		HashMap<String, CompanyPeriodBalance> companyPeriodBalanceHashMap = new HashMap<String, CompanyPeriodBalance>();
		for (CompanyPeriodBalance companyPeriodBalance : list) {
			companyPeriodBalanceHashMap.put(companyPeriodBalance.getItemNo(), companyPeriodBalance);
			lists.add(companyPeriodBalance.getItemNo());
		}
		return companyPeriodBalanceHashMap;
	}

	private ItemCostConditionDto combileDtoParams(ItemCost itemCost, Map<String, Object> params)
			throws ServiceException {

		params.put("companyNo", itemCost.getCompanyNo());
		params.put("year", itemCost.getYear());
		params.put("month", itemCost.getMonth());

		int year = Integer.valueOf(itemCost.getYear());
		int month = Integer.valueOf(itemCost.getMonth());

		ItemCostConditionDto dto = new ItemCostConditionDto();
		dto.setCompanyNo(itemCost.getCompanyNo());
		dto.setStartDate(DateUtil.getFirstDayOfMonth(year, month));
		dto.setEndDate(DateUtil.getLastDayOfMonth(year, month));
		dto.setCreateUser(itemCost.getCreateUser());
		dto.setCreateTime(itemCost.getCreateTime());
		dto.setBrandUnitNo(itemCost.getBrandUnitNo());
		dto.setBrandUnitName(itemCost.getBrandUnitName());

		if (StringUtils.isNotEmpty(itemCost.getBrandUnitNo())) {
			Map<String, Object> brandParam = new HashMap<>();
			brandParam.put("brandUnitNo", itemCost.getBrandUnitNo());
			List<Brand> brands = brandService.findByBiz(null, brandParam);

			List<String> brandNos = new ArrayList<String>();
			for (Brand brand : brands) {
				brandNos.add(brand.getBrandNo());
			}

			dto.setBrandNos(brandNos);
			params.put("brandNos", brandNos);
		}
		if (StringUtils.isNotEmpty(itemCost.getItemNos())) {
			dto.setItemNos(Arrays.asList(itemCost.getItemNos().split(",")));
			params.put("itemNos", Arrays.asList(itemCost.getItemNos().split(",")));
		}

		String shardingFlag = "";
		String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
		if (StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)) {// 集团总部
			shardingFlag = "0_Z";
		} else {
			shardingFlag = organTypeNo + "_" + dto.getCompanyNo().substring(0, 1);
		}
		dto.setShardingFlag(shardingFlag);
		params.put("shardingFlag", shardingFlag);

		return dto;
	}

	static final long TIME_OUT = 1 * 60 * 1000;

	private void updateStatus(int index, int count, int status, String msg) {
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

		logger.info(String.format("生成成本,%s,%d/%d", msg, index, count));
		
		RedisStorage.getCurrent().set(ticket, str, TIME_OUT);
	}
}
