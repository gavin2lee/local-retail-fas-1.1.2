package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.DepositCheck;
import cn.wonhigh.retail.fas.common.model.DepositSet;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.DepositCashManager;
import cn.wonhigh.retail.fas.manager.DepositSetManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-13 10:07:17
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/deposit_check")
@ModuleVerify("30170028")
public class DepositCheckController extends ExcelImportController<DepositCheck> {
	@Resource
	private ShopManager shopManager;
	@Resource
	private DepositCashManager depositCashManager;
	@Resource
	private DepositSetManager depositSetManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("IndepShop_management/deposit_set/", depositCashManager);
	}

	@RequestMapping(value = "/list")
	public String list() {
		return "IndepShop_management/deposit_set/deposit_check";
	}

	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		Map<String, Object> params = builderParams(req, model);

		params = setParams(req, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if (params == null) {
			obj.put("total", 0);
			obj.put("rows", new ArrayList<DepositSet>());
			return obj;
		}

		//根据参数查询各店铺最近一次存现时间
		List<DepositCash> depositDateList = depositCashManager.findLastDepositDate(params);
		List<DepositCheck> list = new ArrayList<>();
		for (DepositCash depositCash : depositDateList) {
			DepositCheck check = new DepositCheck();
			Date date = depositCash.getDepositDate();
			String shopNo = depositCash.getShopNo();
			String shardingFlag = depositCash.getShardingFlag();
			Map<String, Object> params1 = this.buildSelfParams(date, shopNo, shardingFlag);
			DepositCheck depositCheck = depositCashManager.findBeyondDates(params1);
			Integer beyondLastDepositDate = 0;
			BigDecimal saleAmount = BigDecimal.valueOf(0d);
			check.setShopNo(shopNo);
			check.setShopName(depositCash.getShopName());
			check.setCheckDate(new Date());
			if (null != depositCheck) {
				beyondLastDepositDate = depositCheck.getBeyondLastDepositDate();
				saleAmount = depositCheck.getSaleAmount();
			}
			check.setBeyondLastDepositDate(beyondLastDepositDate);
			check.setDepositDiff(saleAmount);System.out.println(saleAmount);
			list.add(check);
		}

		list = getDepositCheckList(list);

		List<DepositCheck> result = new ArrayList<>();

		for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
			if (i > (list.size() - 1))
				break;
			result.add(list.get(i));
		}

		obj.put("total", list.size());
		obj.put("rows", result);

		return obj;
	}

	private Map<String, Object> buildSelfParams(Date date, String shopNo, String shardingFlag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date current = c.getTime();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);//增加一天
		date = c.getTime();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("date", sdf.format(date));//最后存现日增加一天的日期
		params.put("shopNo", shopNo);
		params.put("shardingFlag", shardingFlag);

		StringBuilder sb = new StringBuilder();
		long diffs = (current.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
		for (int i = 0; i < diffs - 1; i++) {
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, 1);//增加一天
			date = c.getTime();
			sb.append(" UNION ALL SELECT '"
					+ shopNo
					+ "' shop_no,'"
					+ sdf.format(date)
					+ "' out_date,IFNULL((SELECT SUM(main.amount) amount FROM (SELECT shop_no,out_date,order_no,amount FROM order_main WHERE shop_no = '"
					+ shopNo
					+ "' AND out_date = '"
					+ sdf.format(date)
					+ "' AND sharding_flag = '"
					+ shardingFlag
					+ "' UNION ALL SELECT shop_no,out_date,business_no,amount FROM return_exchange_main WHERE shop_no = '"
					+ shopNo
					+ "' AND out_date = '"
					+ sdf.format(date)
					+ "' AND sharding_flag = '"
					+ shardingFlag
					+ "') main LEFT JOIN order_payway op ON main.order_no = op.order_no WHERE 1=1 AND pay_code = '01' GROUP BY main.shop_no),0) sale_amount");
		}
		params.put("queryCondition", new String(sb));
		return params;
	}

	private List<DepositCheck> getDepositCheckList(List<DepositCheck> list) {
		List<DepositCheck> depositCheckList = new ArrayList<DepositCheck>();
		Map<String, Object> params = new HashMap<String, Object>();
		for (DepositCheck depositCheck : list) {
			params.put("shopNo", depositCheck.getShopNo());
			DepositSet temp = depositSetManager.getDepositSet(params);
			if (null != temp) {
				if (temp.getBeyondLastDepositDate() == null)
					temp.setBeyondLastDepositDate(Integer.MAX_VALUE);
				if (temp.getDepositDiff() == null)
					temp.setDepositDiff(BigDecimal.valueOf(Integer.MAX_VALUE));
				if ((depositCheck.getBeyondLastDepositDate() > temp.getBeyondLastDepositDate())
						|| (depositCheck.getDepositDiff().compareTo(temp.getDepositDiff()) == 1)) {
					depositCheck.setAmount(temp.getAmount());
					depositCheckList.add(depositCheck);
				}
			}
		}
		return depositCheckList;
	}

	private Map<String, Object> setParams(HttpServletRequest req, Map<String, Object> params) {
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String bizCityNo = params.get("bizCityNo") == null ? null : params.get("bizCityNo").toString();
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		Map<String, Object> shopParamMap = new HashMap<String, Object>();

		shopParamMap.put("companyNo", companyNo);//根据公司查询店铺
		shopParamMap.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		shopParamMap.put("bizCityNoLists", FasUtil.formatInQueryCondition(bizCityNo));

		List<String> shopList = shopManager.getShopBySelfCheckin(shopParamMap);
		if (shopList.size() <= 0) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNoList", shopList);
		map.put("pageSize", params.get("pageSize"));
		map.put("pageIndex", params.get("pageIndex"));
		map.put("startOutDate", params.get("startOutDate"));
		map.put("rows", params.get("rows"));
		map.put("endOutDate", params.get("endOutDate"));
		map.put("page", params.get("page"));
		map.put("pageNumber", params.get("pageNumber"));
		return map;
	}

	@Override
	protected List<DepositCheck> queryExportData(Map<String, Object> params) throws ManagerException {
		params = setParams(null, params);
		if (params == null) {
			return null;
		}
		//根据参数查询各店铺最近一次存现时间
		List<DepositCash> depositDateList = depositCashManager.findLastDepositDate(params);
		List<DepositCheck> list = new ArrayList<>();
		for (DepositCash depositCash : depositDateList) {
			DepositCheck check = new DepositCheck();
			Date date = depositCash.getDepositDate();
			String shopNo = depositCash.getShopNo();
			String shardingFlag = depositCash.getShardingFlag();
			Map<String, Object> params1 = this.buildSelfParams(date, shopNo, shardingFlag);
			DepositCheck depositCheck = depositCashManager.findBeyondDates(params1);
			Integer beyondLastDepositDate = 0;
			BigDecimal saleAmount = BigDecimal.valueOf(0d);
			check.setShopNo(shopNo);
			check.setShopName(depositCash.getShopName());
			check.setCheckDate(new Date());
			if (null != depositCheck) {
				beyondLastDepositDate = depositCheck.getBeyondLastDepositDate();
				saleAmount = depositCheck.getSaleAmount();
			}
			check.setBeyondLastDepositDate(beyondLastDepositDate);
			check.setDepositDiff(saleAmount);
			list.add(check);
		}

		list = getDepositCheckList(list);

		return list;
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<DepositCheck> list) {
		// TODO Auto-generated method stub
		return false;
	}
}