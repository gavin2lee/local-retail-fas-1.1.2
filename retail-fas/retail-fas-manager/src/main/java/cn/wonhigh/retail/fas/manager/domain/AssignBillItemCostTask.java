package cn.wonhigh.retail.fas.manager.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import cn.wonhigh.retail.backend.cache.RedisStorage;
import cn.wonhigh.retail.backend.core.SpringContext;
import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.backend.utils.JsonUtils;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.ItemCostMapper;
import cn.wonhigh.retail.fas.dal.database.cost.ItemCostMapperExt;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.utils.SimplePage;

public class AssignBillItemCostTask implements Runnable {
	private static final XLogger logger = XLoggerFactory.getXLogger(AssignBillItemCostTask.class);
	
	@Resource
	private ItemCostMapper itemCostMapper;

	ItemCostMapperExt itemCostMapperExt;

	Company company;
	String brandUnitNo;
	Date start;
	Date end;
	final Map<String, Object> params;
	final Map<String, Object> statusMap = new HashMap<>();
	String ticket;
	String userName;
	String lookKey;
	public AssignBillItemCostTask(Company company, String brandUnitNo, Date start, Date end) {
		this.company = company;
		this.brandUnitNo = brandUnitNo;
		this.start = start;
		this.end = end;
		this.params = getUpdateCostParams();
		itemCostMapperExt = SpringContext.getBean(ItemCostMapperExt.class);
		this.userName = Authorization.getUser().getLoginName();
		lookKey = "itemcost:assign:" + company.getCompanyNo();
	}

	public Map<String, Object> start() {

		
		String val = RedisStorage.getCurrent().get(lookKey);
		if (!StringUtils.isEmpty(val)) {
			updateStatus(0, 1, -1, String.format("已经在【%s】执行的分配任务，请勿重复执行.", val));
			return statusMap;
		}
		ticket = UUIDGenerator.getUUID();
		statusMap.put("status", 1);
		statusMap.put("ticket", ticket);
		updateStatus(0, 1, 1, "开始执行");

		String d = DateUtil.getDateTime(new Date());

		RedisStorage.getCurrent().set(lookKey, d, TIME_OUT);
		doing();
		return statusMap;
	}

	private void doing() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try {
			itemCostMapper = SpringContext.getBean(ItemCostMapper.class);
			String name = Thread.currentThread().getName();
			Thread.currentThread().setName(name + "&" + userName);

			assignOrderItemCost();

			assignExchangeItemCost();

			updateBalanceItemCost();

			updateStatus(1, 1, 2, "分配成本完成");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			updateStatus(1, 1, -1, "分配成本出现错误");			
		}
		finally{
			RedisStorage.getCurrent().set(lookKey,"",5); //清除锁
		} 

	}
	
	private void assignOrderItemCost() {
		
		updateStatus(0, 1, 1, "分配销售单据单位成本...");
		
		int POCount = itemCostMapper.updatePosOrderItemCost(params);  

		updateStatus(POCount, POCount, 1, "分配销售单据单位成本完成.");
		
		logger.info(String.format("分配销售单据单位成本完成:company:%s,count:%d", company.getCompanyNo(), POCount));
	}

	private void assignExchangeItemCost() {
		
		updateStatus(0, 1, 1, "分配退货单据单位成本...");
		
		// 更新POS退换货表单位成本价
		int PRCount = itemCostMapper.updatePosReturnExchangeItemCost(params);
		
		logger.info("共更新了 RETURN_EXCHANGE_DTL 成本记录" + PRCount);
		
		updateStatus(PRCount, PRCount, 1, "分配退货单据单位成本完成.");
		
		logger.info(String.format("分配退货单据单位成本完成:company:%s,count:%d", company.getCompanyNo(), PRCount));

	}

	/**
	 * 更新买卖表单位成本
	 * 
	 * @param params
	 */
	private void updateBalanceItemCost() {
		
		updateStatus(1, 1, 1, "开始分配买卖表单位成本");
		
		int count = itemCostMapper.getSaleBalanceItemCostUnmatchCount(params);
		
		updateStatus(1, count, 1, "分配买卖表单位成本...");

		int pageNo = 1;
		int pageSize = 5000;
		SimplePage page = new SimplePage(pageNo, pageSize, 0);
		int index = 0;
		while (true) {
			List<BillSaleBalance> saleBalances = itemCostMapper.getSaleBalanceItemCostUnmatchByPage(page, "", "",
					params);
			if (!CollectionUtils.isEmpty(saleBalances)) {
				params.put("lastId", saleBalances.get(saleBalances.size() - 1).getId());
				for (BillSaleBalance billSaleBalance : saleBalances) {
					itemCostMapper.updateSaleCostById(billSaleBalance);
					if (index++ % 100 == 0)
						updateStatus(index, count, 1, "分配买卖表单位成本...");
				}
			}
			if (saleBalances.size() < pageSize) {
				break;
			}
		}
		logger.info(String.format("分配买卖表单位成本完成:company:%s,count:%d", company.getCompanyNo(), count));
	}

	static long TIME_OUT = 30 * 60 * 1000;

	private void updateStatus(int index, int count, int status, String msg) {
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
		logger.info(String.format("分配成本,%s,%d/%d", msg, index, count));

		RedisStorage.getCurrent().set(ticket, str, TIME_OUT);
	}

	private Map<String, Object> getUpdateCostParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		String organTypeNo = company.getOrganTypeNo();

		if (StringUtils.isEmpty(organTypeNo) || "0".equals(organTypeNo)) {
			params.put("shardingFlag", "0_Z");
		} else {
			params.put("shardingFlag", organTypeNo + "_" + company.getZoneNo());
		}
		params.put("startDate", start);
		params.put("endDate", end);
		params.put("companyNo", company.getCompanyNo());
		String year = String.format("%tY", start);
		String month = String.format("%tm", start);
		params.put("year", year);
		params.put("month", Integer.parseInt(month));
		if (StringUtils.isNotBlank(brandUnitNo)) {
			params.put("multiBrandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
		}
		return params;
	}

}
