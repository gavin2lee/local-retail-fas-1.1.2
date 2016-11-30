package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.api.service.CostAccountingApi;
import cn.wonhigh.retail.fas.common.model.BillInvCostAdjust;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BillInvCostAdjustManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.gms.common.constans.Constant;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 库存成本调整单
 * 
 * @author wang.sm
 * @date 2016-03-22 09:30:55
 * @version 1.0.3
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/bill_inv_cost_adjust")
@ModuleVerify("30120002")
public class BillInvCostAdjustController extends BaseController<BillInvCostAdjust> {
    @Resource
    private BillInvCostAdjustManager billInvCostAdjustManager;
    
    @Resource
    private ItemCostManager itemCostManager;
    
    @Resource
    private CostAccountingApi costAccountingApi;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_inv_cost_adjust/",billInvCostAdjustManager);
    }
    
    @RequestMapping("/list_tabMain")
	public String listTabMain() throws ManagerException {
		return "bill_inv_cost_adjust/list_tabMain";
	}
    
    /**
	 * 获取开关控制
	 * 
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("get_controller_flag")
	public Map<String, String> getControllerFlag() throws ManagerException {
		return billInvCostAdjustManager.getControllerFlag();
	}

	/**
	 * 新增单据（库存成本调整单）
	 * 
	 * @param req
	 * @param billReceipt
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/post2")
	public ResponseEntity<BillInvCostAdjust> add(HttpServletRequest req, BillInvCostAdjust model)
			throws Exception {
		SystemUser currentUser = CurrentUser.getCurrentUser();
		// 如果单据编号不为空，则修改，否则就新增
		if (StringUtils.isNotBlank(model.getBillNo())) {
			billInvCostAdjustManager.modifyById(model);
		} else {
			model.setCreateUser(currentUser.getUsername());
			model.setShardingFlag(currentUser.getOrganTypeNo() + "_" + model.getCompanyNo().substring(0, 1));
			billInvCostAdjustManager.addFetchId(model);
			// //绑定页面数据
		}
		model = billInvCostAdjustManager.findById(model);// 页面底部要展示
		return new ResponseEntity<BillInvCostAdjust>(model, HttpStatus.OK);
	}

	@RequestMapping("/batch_delete")
	@ResponseBody
	public Map<String, Boolean> batchDel(HttpServletRequest request) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String deletedList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request
					.getParameter("deleted");
			List<BillInvCostAdjust> list = convertListWithTypeReference(deletedList, request, BillInvCostAdjust.class);
			billInvCostAdjustManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param valueList
	 *            待转换的json格式数据
	 * @param request
	 *            HttpServletRequest
	 * @param entityClass
	 *            转换后的class
	 * @return List<ModelType>
	 * @throws JsonParseException
	 *             异常
	 * @throws JsonMappingException
	 *             异常
	 * @throws JsonGenerationException
	 *             异常
	 * @throws IOException
	 *             异常
	 */
	private List<BillInvCostAdjust> convertListWithTypeReference(String valueList, HttpServletRequest request,
			Class<BillInvCostAdjust> entityClass) throws JsonParseException, JsonMappingException,
			JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(valueList, new TypeReference<List<Map>>() {
		});
		List<BillInvCostAdjust> tl = new ArrayList<BillInvCostAdjust>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillInvCostAdjust type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			tl.add(type);
		}
		return tl;
	}

	/**
	 * 查询单位成本
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 返回查询到的单位成本对象
	 * @throws ManagerException
	 *             异常
	 */
	@ResponseBody
	@RequestMapping("/find_item_cost")
	public ItemCost findItemCost(HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = this.buildQueryParams(request);
		if (params == null || params.size() < 1) {
			return null;
		}
		List<ItemCost> list = itemCostManager.findByBiz(null, params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/check_company_settle_date")
	public Map<String, Object> checkCompanySettleDate(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		Map<String, Object> params = builderParams(req, model);
		
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String year = params.get("year") == null ? null : params.get("year").toString();
		String month = params.get("month") == null ? null : params.get("month").toString();
		
		if (StringUtils.isEmpty(companyNo) || StringUtils.isEmpty(year) ||StringUtils.isEmpty(month) ) {
			resultMap.put("success", false);
			resultMap.put("message", "公司、年月参数错误，无法判断关账日期");
		}
		try {
			CompanySettlePeriod companySettlePeriod = costAccountingApi.findBalanceDate(companyNo);
			if (null == companySettlePeriod) {
				resultMap.put("success", false);
				resultMap.put("message", "获取FAS系统的公司关账日期失败");
			}else {
				if (createDateByYearMonth(year,month,0).before(companySettlePeriod.getAccountSettleTime())) {
					resultMap.put("success", false);
					resultMap.put("message", "公司当前年月已经关账");
				}
			}
		} catch (RpcException e) {
			resultMap.put("success", false);
			resultMap.put("message", "获取FAS系统的公司关账日期失败");
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		
		return resultMap;
	}
	
	private Date createDateByYearMonth(String year, String month, int maxOrMinDayFlag) {
		if(StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		int day = 1;
		if(maxOrMinDayFlag == 1) {
			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		calendar.set(Calendar.DATE, day);
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 查询本期结存
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 返回查询到的本期结存对象
	 * @throws ManagerException
	 *             异常
	 */
	@ResponseBody
	@RequestMapping("/find_period_balance")
	public PeriodBalance findPeriodBalance(HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = this.buildQueryParams(request);
		if (params == null || params.size() < 1) {
			return null;
		}
		PeriodBalance periodBalance = billInvCostAdjustManager.findPeriodBalance(params);
		return periodBalance;
	}

	/**
	 * 组装查询条件(查询的是上期期末数量和上期成本)
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 返回组装的查询条件
	 */
	private Map<String, Object> buildQueryParams(HttpServletRequest request) {
		String itemNo = request.getParameter("itemNo");
		String companyNo = request.getParameter("companyNo");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		params.put("companyNo", companyNo);
		params.put("year", year);
		params.put("month", month);
		params.put("shardingFlag", getShardingFlagByCompanyNo(companyNo));
		
		return params;
	}

	private String getShardingFlagByCompanyNo(String companyNo){
		String shardingFlag="";
		String zoneNo="";
		if(StringUtils.isNotBlank(companyNo)){
			zoneNo=companyNo.substring(0,1);
		}
		if(StringUtils.isNotBlank(zoneNo)){
			if(CurrentUser.getCurrentUser()==null){
				return shardingFlag;
			}
			String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){// 集团总部
				shardingFlag += "0_Z";
			}else{
				shardingFlag += organTypeNo+"_"+zoneNo;
			}
		}
		return shardingFlag;
	}

	/**
	 * 审核单据
	 * 
	 * @param request
	 * @param verifyInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/confirm")
	public ResponseEntity<BillInvCostAdjust> verify(HttpServletRequest request,
			@ModelAttribute("model") BillInvCostAdjust model) throws Exception {
		model = billInvCostAdjustManager.confirm(model);
		return new ResponseEntity<BillInvCostAdjust>(model, HttpStatus.OK);
	}

	/**
	 * 转到查询结算公司
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/forward_search_company")
	public ModelAndView forwardSearchCompany(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("bill/invCostAdjust/company");
		return view;
	}

	/**
	 * 查询结算公司数据
	 * 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/list_company.json")
	@ResponseBody
	public Map<String, Object> queryCompanyList(HttpServletRequest request, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
				.getParameter("page"));
		int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request
				.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request
				.getParameter("order"));
		Map<String, Object> params = builderParams(request, model);
		int total = this.billInvCostAdjustManager.findCompanyCount(params);
		List<Company> list = null;
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.billInvCostAdjustManager.findCompanyByPage(page, sortColumn, sortOrder, params);

		} else {
			list = new ArrayList<>();
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@Override
	public Map<String, Object> builderParams(HttpServletRequest req, Model model) {
		String zone=Authorization.getCurrentZone();
		Boolean isNeedshardingFlag = false;//先不按分区查看 ,fas挂菜单 TODO
		if(isNeedshardingFlag&&StringUtils.isBlank(zone)){
			isNeedshardingFlag=false;//如果取不到大区 则不需要加分库字段查询（暂时方案）TODO 
			if(System.getProperty("env")!=null && System.getProperty("env").equals("dev")){
				Authorization.setCurrentZone(Constant.HQ_ZONE_NO);
			}else{
				logger.error("session已超时！取不到当前选择的大区！请刷新页面或重新登录！");
			}
		}
		
		Map<String, Object> retParams = super.builderParams(req, model);
		//处理undefined
		for (String key : retParams.keySet()) {
			if(retParams.get(key)!=null&&retParams.get(key).equals("undefined")){
				retParams.put(key, "");
			}
		}
		//-1时会全部 gms.data.js datasource 定义了-1为全部 status
		//bizTypeCondition gms.data.bizTypeData 
		String[] fields = { "bizTypeCondition", "status", "billType", "billTypeCondition", 
				"statusBox","refBillType","yearCondition","monthCondition","checkTypeCondition","billTypeList","targetSystem","action"};
		for (String field : fields) {
			if (retParams.get(field) != null && retParams.get(field).equals("-1")) {
				retParams.put(field, null);
			}
		}
		if (retParams.containsKey("currentZoneNo")) {
			if(zone!=null){
				retParams.put("currentZoneNo", "'"+zone+"'");
			}
		}
		
		//等线上环境已有记录的分库字段值已经插入
		//单据查询列表+分库条件
		if(isNeedshardingFlag&&!retParams.containsKey("shardingFlag")){
			if(req.getAttribute("shardingFlag")!=null){
				retParams.put("shardingFlag", req.getAttribute("shardingFlag"));
			}else{
				String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
				if(StringUtils.isBlank(organTypeNo)){
					organTypeNo="0";//集团总部 默认0
				}
				retParams.put("shardingFlag", organTypeNo+"_"+zone);
			}
		}
		return retParams;
	}

    
}