package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BLKItemCostManager;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CompanyBrandSettlePeriodManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.CompanySettlePeriodManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.manager.PeriodBalanceAuditManager;
import cn.wonhigh.retail.fas.manager.domain.AssignBillItemCostTask;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/blk_item_cost")
@ModuleVerify("30120301")
public class BLKItemCostController extends BaseController<ItemCost> {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BLKItemCostController.class);

	@Resource
	private BLKItemCostManager blkItemCostManager;
	
	@Resource
	private ItemCostManager itemCostManager;
	
	@Resource
	private CompanyManager companyManager;
	
	@Resource
	private BrandManager brandManager;
	
	@Resource
	private ItemManager itemManager;
	
	@Resource
	private CompanyBrandSettlePeriodManager companyBrandSettlePeriodManager;
	
	@Resource
	private CompanySettlePeriodManager companySettlePeriodManager;
	
	@Resource
	private PeriodBalanceAuditManager periodBalanceAuditManager;
	
	@Override
	protected CrudInfo init() {
		return new CrudInfo("item_cost/", blkItemCostManager);
	}
	
	@RequestMapping({"/list"})
    public String list() {
    	return "item_cost/blk_list";
    }
	
	@RequestMapping(value = "/item_cost_list.json")
	@ResponseBody
	public Map<String, Object> queryBLKItemCostList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		// 显示零金额
		String zeroAmount = StringUtils.isEmpty(req.getParameter("zeroAmount")) ? "" : req.getParameter("zeroAmount");
		// 显示与地区价不一致
		String regionCostUnmatch = StringUtils.isEmpty(req.getParameter("regionCostUnmatch")) ? "" : req
				.getParameter("regionCostUnmatch");
		// 必填参数
		String companyNo = StringUtils.isEmpty(req.getParameter("companyNo")) ? "" : req.getParameter("companyNo");
		String year = StringUtils.isEmpty(req.getParameter("year")) ? "" : req.getParameter("year");
		String month = StringUtils.isEmpty(req.getParameter("month")) ? "" : req.getParameter("month");

		Map<String, Object> params = builderParams(req, model);
		if (StringUtils.isEmpty(companyNo) || StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
			throw new ManagerException("查询条件设置错误，公司、年份、月份为必填");
		}
		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("brandNos", FasUtil.formatInQueryCondition(req.getParameter("brandNos")));
		}
		
		StringBuffer sb = new StringBuffer();
		if (zeroAmount.equals("true")) {
			sb.append(" AND ic.unit_cost = 0");
		}
		// 按照小计方式查询
		if (regionCostUnmatch.equals("true")) {
			sb.append(" AND ic.unit_cost != r.region_cost ");
		} 
		params.put("queryCondition", sb);
		
		int total = blkItemCostManager.findBLKItemCostCount(params);
		List<ItemCost> list = new ArrayList<ItemCost>();
		if(total > 0 ){
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = blkItemCostManager.findBLKItemCostList(page, null, null, params);
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	protected List<ItemCost> queryExportData(Map<String, Object> params) throws ManagerException {
		// 显示零金额
		String zeroAmount = params.get("zeroAmount") == null ? "" : String.valueOf(params.get("zeroAmount"));
		// 显示与地区价不一致
		String regionCostUnmatch = params.get("regionCostUnmatch") == null ? "" : String.valueOf(params.get("regionCostUnmatch"));
		String brandNos = params.get("brandNos") == null ?"":String.valueOf(params.get("brandNos"));
		if (StringUtils.isNotEmpty(brandNos)) {
			params.put("brandNos", FasUtil.formatInQueryCondition(brandNos));
		}
		
		StringBuffer sb = new StringBuffer();
		if (zeroAmount.equals("true")) {
			sb.append(" AND ic.unit_cost = 0");
		}
		// 按照小计方式查询
		if (regionCostUnmatch.equals("true")) {
			sb.append(" AND ic.unit_cost != r.region_cost ");
		} 
		params.put("queryCondition", sb);
		
		int total = blkItemCostManager.findBLKItemCostCount(params);
		List<ItemCost> list = new ArrayList<ItemCost>();
		if(total > 0 ){
			SimplePage page = new SimplePage(1, total, (int) total);
			list = blkItemCostManager.findBLKItemCostList(page, null, null, params);
		}
		
		return list;
	}

	@RequestMapping(value = "/conflict_item_brand")
	@ResponseBody
	public Map<String, Object> checkConflictItemBrand(HttpServletRequest request, ItemCost itemCost)
			throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			if (StringUtils.isNotEmpty(itemCost.getItemNos())) {
				List<String> styleNos = Arrays.asList(itemCost.getStyleNos().split(","));

				Map<String, Object> brandParam = new HashMap<>();
				brandParam.put("brandUnitNo", itemCost.getBrandUnitNo());
				List<Brand> brands = brandManager.findByBiz(null, brandParam);

				List<String> brandNos = new ArrayList<String>();
				for (Brand brand : brands) {
					brandNos.add(brand.getBrandNo());
				}

				Map<String, Object> styleMap = new HashMap<String, Object>();
				List<Item> items = null;
				// 默认校验通过
				params.put("success", true);

				for (String styleNo : styleNos) {
					styleMap.put("queryCondition", " AND style_no = '" + styleNo +"'");
					
					items = itemManager.findByBiz(null, styleMap);
					for (Item item : items) {
						if(brandNos.contains(item.getBrandNo())){
							continue;
						}else{
							params.put("success", false);
							break;
						}
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
	
	@RequestMapping(value = "/check_generate_condition")
	@ResponseBody
	public Map<String, Object> checkHasZeroAmountBill(HttpServletRequest request, ItemCost itemCost) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("success", true);
			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
			itemCost.setCreateUser(loginUser.getUsername());
			itemCost.setCreateTime(DateUtil.getCurrentDateTime());
			
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("companyNo", itemCost.getCompanyNo());
			obj.put("year", itemCost.getYear());
			obj.put("month", itemCost.getMonth());
			obj.put("brandUnitNo", itemCost.getBrandUnitNo());
			obj.put("status", String.valueOf(0));
			List<PeriodBalanceAudit> existsAudits = periodBalanceAuditManager.findByBiz(null, obj);
			String[] tips = {"成本正在生成中，请耐心等待！","操作过于频繁！",
					"后台正在生成成本，请稍后再试!","请勿重新生成成本！"};//响应提示
			
			if (!CollectionUtils.isEmpty(existsAudits)) {
				params.put("success", false);
				params.put("message", tips[new Random().nextInt(4)]);
				return params;
			}
			
			blkItemCostManager.generateBLKItemCost(itemCost);//巴洛克
		}  catch (Exception e) {
			params.put("success", false);
			params.put("message", "生成成本异常。");
			LOGGER.error(e.getMessage());
		}
    	
    	return params;
    }
	
	@RequestMapping(value = "/check_generate_condition_self")
	@ResponseBody
	public Map<String, Object> genearateUnitCostForSelf(HttpServletRequest request, ItemCost itemCost) throws ManagerException{
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("success", true);
			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
			itemCost.setCreateUser(loginUser.getUsername());
			itemCost.setCreateTime(DateUtil.getCurrentDateTime());
			
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("companyNo", itemCost.getCompanyNo());
			obj.put("year", itemCost.getYear());
			obj.put("month", itemCost.getMonth());
			obj.put("brandUnitNo", itemCost.getBrandUnitNo());
			obj.put("status", String.valueOf(0));
			List<PeriodBalanceAudit> existsAudits = periodBalanceAuditManager.findByBiz(null, obj);
			String[] tips = {"成本正在生成中，请耐心等待！","操作过于频繁！",
					"后台正在生成成本，请稍后再试!","请勿重新生成成本！"};//响应提示
			
			if (!CollectionUtils.isEmpty(existsAudits)) {
				params.put("success", false);
				params.put("message", tips[new Random().nextInt(4)]);
				return params;
			}
			
			blkItemCostManager.generateBLKItemCost(itemCost);//巴洛克
		}  catch (Exception e) {
			params.put("success", false);
			params.put("message", "生成成本异常。");
			LOGGER.error(e.getMessage());
		}
    	
    	return params;
	}
	
	@RequestMapping(value = "/audit_list.json")
	@ResponseBody
	public Map<String, Object> auditList(HttpServletRequest req, Model model) throws ManagerException {
		//新方案查询生成成本结果查询
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		PeriodBalanceAudit periodBalanceAudit = new PeriodBalanceAudit();
    	SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
    	periodBalanceAudit.setCreateUser(loginUser.getUsername());
    	List<String> strList = new ArrayList<String>();
    	List<Company> companys = companyManager.findByBiz(null, null);
    	for (Company company : companys) {
    		if(Authorization.getCurrentZone().equals(company.getZoneNo())){
    			strList.add(company.getCompanyNo());
    		}
		}
    	periodBalanceAudit.setCompanyNos(strList);
    	int total = periodBalanceAuditManager.countPeriodBalanceManagerAuditByPage(periodBalanceAudit);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<PeriodBalanceAudit> list = periodBalanceAuditManager.findPeriodBalanceManagerAuditByPage(page, periodBalanceAudit);
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/update_bill_cost")
	@ResponseBody
	public Map<String, Object> updateBillCost(HttpServletRequest request, Model model) throws ManagerException {
		String companyNo = request.getParameter("companyNo");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String assignType = request.getParameter("assignType");
		String brandUnitNo = request.getParameter("brandUnitNos");
		Date start = DateUtil.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Date end = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));

		Company company = companyManager.getCompany(companyNo);
		
		AssignBillItemCostTask task =  new AssignBillItemCostTask(company, brandUnitNo, start, end);
		Map<String, Object> resultMap = task.start(); 
		return resultMap;
		
//		if ("1".equals(assignType)) {
//			// 分配单位成本
//			itemCostManager.assignBillItemCost(company, brandUnitNo,start, end);
//		} else if ("2".equals(assignType)) {
//			// 分配总部、地区成本
//			itemCostManager.assignBillHQCost(company, brandUnitNo,start, end);
//			itemCostManager.assignBillRegionCost(company, brandUnitNo,start, end);
//		}  
//		Map<String, Object> map = new HashMap<>();
//		map.put("success", true);
//		return map;
	}
	
	@RequestMapping(value = "/genearate_zero_item_cost")
	@ResponseBody
	public Map<String, Object> gennearateZeroItemCost(HttpServletRequest req) throws Exception {
		Map<String, Object> params = new HashMap<String,Object>();
    	params.put("success", true);
    	
		String checkedList = StringUtils.isEmpty(req.getParameter("checked")) ? "":req.getParameter("checked");
		ObjectMapper mapper = new ObjectMapper();
		List<ItemCost> list = new ArrayList<ItemCost>();
		if(StringUtils.isNotEmpty(checkedList)){
			@SuppressWarnings("rawtypes")
			List<Map> l = mapper.readValue(checkedList, new TypeReference<List<Map>>(){});
			list = convertListWithTypeReference(mapper, l, req);
		}
		
		//按照品牌部进行分组
		Map<String, ItemCost> map = new HashMap<String, ItemCost>();
		for (ItemCost itemCost : list) {
			String brandUnitNo = itemCost.getBrandUnitNo();
			if(map.containsKey(brandUnitNo)){
				ItemCost cost = map.get(brandUnitNo);
				String brandUnitNo1 = cost.getBrandNo();
				cost.setBrandUnitNo(brandUnitNo1+","+brandUnitNo);
			}else{
				map.put(itemCost.getBrandUnitNo(), itemCost);
			}
		}
		
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
		
		try {
			//按照品牌部不同分别生成成本
			for(String key : map.keySet()){
				ItemCost itemCost = map.get(key);
				itemCost.setCreateUser(loginUser.getUsername());
				itemCost.setCreateTime(DateUtil.getCurrentDateTime());
				itemCostManager.generateBLKItemCost(itemCost);
			}
		} catch (Exception e) {
			params.put("success", false);
			params.put("message", "生成成本异常。");
			LOGGER.error(e.getMessage());
		}
		return params;
	}
}
