package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.core.SpringContext;
import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CompanyBrandSettlePeriodManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.CompanySettlePeriodManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.manager.PeriodBalanceAuditManager;
import cn.wonhigh.retail.fas.manager.domain.AssignBillItemCostTask;
import cn.wonhigh.retail.fas.manager.domain.ICostGenerate;
import cn.wonhigh.retail.fas.manager.domain.ItemCostGenerateTask;
import cn.wonhigh.retail.fas.web.utils.ItemCostGenerateThread;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author wang.xy1
 * @date 2014-08-25 13:45:42
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/item_cost")
@ModuleVerify("30120001")
public class ItemCostController extends BaseController<ItemCost> {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ItemCostController.class);

	@Resource
	private ItemCostManager itemCostManager;

	@Resource
	private ItemManager itemManager;

	@Resource
	private BrandManager brandManager;

	@Resource
	private CompanySettlePeriodManager companySettlePeriodManager;

	@Resource
	private CompanyBrandSettlePeriodManager companyBrandSettlePeriodManager;

	@Resource
	private PeriodBalanceAuditManager periodBalanceAuditManager;

	@Resource
	private CompanyManager companyManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("item_cost/", itemCostManager);
	}

	@RequestMapping(value = "/item_cost_list.json")
	@ResponseBody
	public Map<String, Object> queryGMSBalanceList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);

		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("brandNos", FasUtil.formatInQueryCondition(req.getParameter("brandNos")));
		}

		if ("true".equals(getResquestStr(req, "zeroAmount"))) {
			params.put("queryCondition", " AND unit_cost = 0");
		}

		int total = 0;
		List<ItemCost> list = new ArrayList<ItemCost>();
		// 按照小计方式查询
		if ("true".equals(getResquestStr(req, "regionCostUnmatch"))) {
			params.put("zoneNo", Authorization.getCurrentZone());
			params.put(
					"effectiveTime",
					DateUtil.getLastDayOfMonthStr(Integer.valueOf(getResquestStr(req, "year")),
							Integer.valueOf(getResquestStr(req, "month"))));

			total = itemCostManager.findItemCostUnmatchRegionPriceCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = itemCostManager.findItemCostUnmatchRegionPriceByPage(page, null, null, params);
		} else {
			total = itemCostManager.findCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = itemCostManager.findByPage(page, null, null, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	/**
	 * 查询导出数据的方法
	 * 
	 * @param params
	 *            查询参数
	 * @return List<ModelType>
	 * @throws ManagerException
	 *             异常
	 */
	protected List<ItemCost> queryExportData(Map<String, Object> params) throws ManagerException {
		int total = 0;
		List<ItemCost> list = new ArrayList<ItemCost>();
		if (StringUtils.isNotEmpty(getRequestExportStr(params, "brandNos"))) {
			params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
		}
		if ("true".equals(getRequestExportStr(params, "zeroAmount"))) {
			params.put("queryCondition", " AND unit_cost = 0");
		}

		// 按照小计方式查询
		if ("true".equals(getRequestExportStr(params, "regionCostUnmatch"))) {
			params.put("zoneNo", Authorization.getCurrentZone());
			params.put(
					"effectiveTime",
					DateUtil.getLastDayOfMonthStr(Integer.valueOf(getRequestExportStr(params, "year")),
							Integer.valueOf(getRequestExportStr(params, "month"))));

			total = itemCostManager.findItemCostUnmatchRegionPriceCount(params);
			SimplePage page = new SimplePage(1, total, (int) total);
			list = itemCostManager.findItemCostUnmatchRegionPriceByPage(page, null, null, params);
		} else {
			total = itemCostManager.findCount(params);
			SimplePage page = new SimplePage(1, total, (int) total);
			list = itemCostManager.findByPage(page, null, null, params);
		}
		return list;
	}

	/**
	 * 批量生成前的单据校验
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/generate_item_cost")
	@ResponseBody
	public Map<String, Object> generateItemCost(HttpServletRequest request, ItemCost itemCost) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("success", true);

		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);

		ItemCostConditionDto dto = new ItemCostConditionDto();
		dto.setCompanyNo(itemCost.getCompanyNo());
		dto.setStartDate(DateUtil.getFirstDayOfMonth(Integer.valueOf(itemCost.getYear()),
				Integer.valueOf(itemCost.getMonth())));
		dto.setEndDate(DateUtil.getLastDayOfMonth(Integer.valueOf(itemCost.getYear()),
				Integer.valueOf(itemCost.getMonth())));
		dto.setCreateUser(loginUser.getUsername());
		try {
			dto.setCreateTime(DateUtil.getCurrentDateTime());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		if (StringUtils.isNotEmpty(itemCost.getBrandNos())) {
			dto.setBrandNos(Arrays.asList(itemCost.getBrandNos().split(",")));
		}
		if (StringUtils.isNotEmpty(itemCost.getItemNos())) {
			dto.setItemNos(Arrays.asList(itemCost.getItemNos().split(",")));
		}

		String shardingFlag = "";
		String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
		if (StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)) {// 集团总部
			shardingFlag = "0_Z";
		} else {
			shardingFlag = organTypeNo + "_" + dto.getCompanyNo().substring(0, 1);
		}
		dto.setShardingFlag(shardingFlag);

		// 按机构生成结存、成本
		if (itemCost.getOnlyCompanyDM()) {
			new Thread(new ItemCostGenerateThread(itemCostManager, dto, periodBalanceAuditManager, true)).start();
		} else {
			new Thread(new ItemCostGenerateThread(itemCostManager, dto, periodBalanceAuditManager, false)).start();
		}

		return params;
	}

	/**
	 * 批量生成前的单据校验
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/check_generate_condition")
	@ResponseBody
	public Map<String, Object> checkHasZeroAmountBill(HttpServletRequest request, ItemCost itemCost) {
		Map<String, Object> params = new HashMap<String, Object>();
		String async = request.getParameter("async");
		
		try {
			
			SystemUser loginUser =  Authorization.getUser();
			itemCost.setCreateUser(loginUser.getUsername());
			itemCost.setCreateTime(DateUtil.getCurrentDateTime());
			if(StringUtils.isEmpty(async)){
				params.put("success", true);
				// 按机构生成成本
				itemCostManager.checkHasBillException(itemCost);// 鞋类、体育
			}
			else{
				ICostGenerate task = SpringContext.getBean("itemCostGenerateTask");
				String ticket = task.start(itemCost);
				params.put("ticket", ticket);
				if( "-1".equals(ticket)){
					params.put("success",true);
					params.put("info", "成本正在生成,请勿重复。");
					params.put("status", "2");
				}
				else{
					params.put("success",true);
					params.put("info", "成本正在生成");
					params.put("status", "1");
				}
				 
			}

		} catch (Exception e) {
			params.put("success", false);
			params.put("message", "生成成本异常。");
			params.put("info", "生成成本异常。");
			params.put("status", "-1");
			LOGGER.error(e.getMessage());
		}
		return params;
	}

	@RequestMapping(value = "/check_generate_condition_self")
	@ResponseBody
	public void genearateUnitCostForSelf(HttpServletRequest request, ItemCost itemCost) throws ManagerException {
		try {
			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
			itemCost.setCreateUser(loginUser.getUsername());
			itemCost.setCreateTime(DateUtil.getCurrentDateTime());

			// 按机构生成成本
			itemCostManager.genearateSelfUnitCost(itemCost);// 鞋类、体育
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 单位成本为0的，重新生成成本
	 * 
	 * @param request
	 * @param itemCost
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "/genearate_zero_item_cost")
	@ResponseBody
	public Map<String, Object> gennearateZeroItemCost(HttpServletRequest req) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("success", true);

		String checkedList = StringUtils.isEmpty(req.getParameter("checked")) ? "" : req.getParameter("checked");
		ObjectMapper mapper = new ObjectMapper();
		List<ItemCost> list = new ArrayList<ItemCost>();
		if (StringUtils.isNotEmpty(checkedList)) {
			@SuppressWarnings("rawtypes")
			List<Map> l = mapper.readValue(checkedList, new TypeReference<List<Map>>() {
			});
			list = convertListWithTypeReference(mapper, l, req);
		}

		// 按照品牌部进行分组
		Map<String, ItemCost> map = new HashMap<String, ItemCost>();
		for (ItemCost itemCost : list) {
			String brandUnitNo = itemCost.getBrandUnitNo();
			if (map.containsKey(brandUnitNo)) {
				ItemCost cost = map.get(brandUnitNo);
				String brandUnitNo1 = cost.getBrandNo();
				cost.setBrandUnitNo(brandUnitNo1 + "," + brandUnitNo);
			} else {
				map.put(itemCost.getBrandUnitNo(), itemCost);
			}
		}

		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);

		try {
			// 按照品牌部不同分别生成成本
			for (String key : map.keySet()) {
				ItemCost itemCost = map.get(key);
				itemCost.setCreateUser(loginUser.getUsername());
				itemCost.setCreateTime(DateUtil.getCurrentDateTime());
				itemCostManager.checkHasBillException(itemCost);
			}
		} catch (Exception e) {
			params.put("success", false);
			params.put("message", "生成成本异常。");
			LOGGER.error(e.getMessage());
		}
		return params;
	}

	/**
	 * 判断公司是否已关账
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/check_settle_date")
	@ResponseBody
	public Map<String, Object> checkCompanySettleDate(HttpServletRequest request, ItemCost itemCost)
			throws ManagerException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("success", true);

		Map<String, Object> companySettleParams = new HashMap<String, Object>();
		companySettleParams.put("companyNo", itemCost.getCompanyNo());
		List<CompanySettlePeriod> settlePeriods = companySettlePeriodManager.findByBiz(null, companySettleParams);
		if (!CollectionUtils.isEmpty(settlePeriods)) {
			// 判断财务关账日
			int year = Integer.valueOf(itemCost.getYear());
			int month = Integer.valueOf(itemCost.getMonth());
			if (settlePeriods.get(0).getAccountSettleTime().after(DateUtil.getFirstDayOfMonth(year, month))) {
				params.put("success", false);
				params.put("message", "公司的财务结账日已关账！");
			}
		} else {
			params.put("success", false);
			params.put("message", "公司的结账日未维护！");
		}
		return params;
	}

	/**
	 * 判断公司品牌是否已关账
	 * 
	 * @param request
	 * @param itemCost
	 * @return
	 */
	@RequestMapping(value = "/check_brand_unit_settle_date")
	@ResponseBody
	public Map<String, Object> checkBrandUnitSettleDate(HttpServletRequest request, ItemCost itemCost) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("success", true);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", itemCost.getCompanyNo());
			params.put("brandNo", itemCost.getBrandUnitNo());
			List<CompanyBrandSettlePeriod> list = companyBrandSettlePeriodManager.findByBiz(null, params);
			if (!CollectionUtils.isEmpty(list)) {
				// 判断财务关账日
				int year = Integer.valueOf(itemCost.getYear());
				int month = Integer.valueOf(itemCost.getMonth());
				if (list.get(0).getAccountSettleTime().after(DateUtil.getFirstDayOfMonth(year, month))) {
					obj.put("success", false);
					obj.put("message", "公司" + itemCost.getBrandUnitName() + "品牌部财务结账日已关账！");
				}
			} else {
				obj.put("success", false);
				obj.put("message", "公司品牌部结账日未维护！");
			}
		} catch (ManagerException e) {
			obj.put("success", false);
			obj.put("message", "公司品牌部结账日查询失败！");
		}
		return obj;
	}

	/**
	 * 判断是否可以生成
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/check_can_generate")
	@ResponseBody
	public Map<String, Object> checkGenerateCondition(HttpServletRequest request, ItemCost itemCost)
			throws ManagerException {

		Map<String, Object> params = new HashMap<String, Object>();
		boolean result = itemCostManager.checkGMSClosingDate(itemCost);
		params.put("success", result);
		return params;
	}

	/**
	 * 校验选择的商品和品牌是否匹配
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/conflict_item_brand")
	@ResponseBody
	public Map<String, Object> checkConflictItemBrand(HttpServletRequest request, ItemCost itemCost)
			throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			if (StringUtils.isNotEmpty(itemCost.getItemNos())) {
				List<String> itemNos = Arrays.asList(itemCost.getItemNos().split(","));

				Map<String, Object> brandParam = new HashMap<>();
				brandParam.put("brandUnitNo", itemCost.getBrandUnitNo());
				List<Brand> brands = brandManager.findByBiz(null, brandParam);

				List<String> brandNos = new ArrayList<String>();
				for (Brand brand : brands) {
					brandNos.add(brand.getBrandNo());
				}

				Map<String, Object> itemMap = new HashMap<String, Object>();
				List<Item> items = null;
				// 默认校验通过
				params.put("success", true);

				for (String itemNo : itemNos) {
					itemMap.put("itemNo", itemNo);
					items = itemManager.findByBiz(null, itemMap);
					if (brandNos.contains(items.get(0).getBrandNo())) {
						continue;
					} else {
						params.put("success", false);
						break;
					}
				}
			} else {
				params.put("success", true);
			}
			return params;
		} catch (ManagerException e) {
			throw new ManagerException(e);
		}
	}

	@RequestMapping(value = "/update_bill_cost")
	@ResponseBody
	public Map<String, Object> updateBillCost(HttpServletRequest request, Model model) throws ManagerException, InterruptedException {

		String async = request.getParameter("async");
		String companyNo = request.getParameter("companyNo");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String assignType = request.getParameter("assignType");
		String brandUnitNo = request.getParameter("brandUnitNos");
		Date start = DateUtil.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Date end = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Company company = companyManager.getCompany(companyNo);
		
		if (StringUtils.isEmpty(async) || "2".equals(assignType)) {
			
			if ("1".equals(assignType)) {
				// 分配单位成本
				itemCostManager.assignBillItemCost(company, brandUnitNo, start, end);
				
			} else if ("2".equals(assignType)) {
				// 分配总部、地区成本
				itemCostManager.assignBillHQCost(company, brandUnitNo, start, end);
				
				itemCostManager.assignBillRegionCost(company, brandUnitNo, start, end);
			}
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("status", 1); 
			return resultMap;
		} else {
			 AssignBillItemCostTask task =  new AssignBillItemCostTask(company, brandUnitNo, start, end);
			 Map<String, Object> resultMap = task.start(); 
			 return resultMap;
		}		
	}

	@RequestMapping(value = "/audit_list.json")
	@ResponseBody
	public Map<String, Object> auditList(HttpServletRequest req, Model model) throws ManagerException {
		// 新方案查询生成成本结果查询
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		PeriodBalanceAudit periodBalanceAudit = new PeriodBalanceAudit();
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
		periodBalanceAudit.setCreateUser(loginUser.getUsername());
		List<String> strList = new ArrayList<String>();
		List<Company> companys = companyManager.findByBiz(null, null);
		for (Company company : companys) {
			if (Authorization.getCurrentZone().equals(company.getZoneNo())) {
				strList.add(company.getCompanyNo());
			}
		}
		periodBalanceAudit.setCompanyNos(strList);
		int total = periodBalanceAuditManager.countPeriodBalanceManagerAuditByPage(periodBalanceAudit);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<PeriodBalanceAudit> list = periodBalanceAuditManager.findPeriodBalanceManagerAuditByPage(page,
				periodBalanceAudit);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	@RequestMapping(value = "/query_item")
	@ResponseBody
	public void runSelfOperation(HttpServletRequest req) {
		String type = req.getParameter("type");
		String queryCondition = req.getParameter("sql");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("queryCondition", queryCondition);
		params.put("status", type);

		itemCostManager.runSql(params, type);
	}

	private String getResquestStr(HttpServletRequest req, String requestStr) {
		return StringUtils.isEmpty(req.getParameter(requestStr)) ? "" : req.getParameter(requestStr);
	}

	private String getRequestExportStr(Map<String, Object> params, String field) {
		return params.get(field) == null ? "" : params.get(field).toString();
	}

}