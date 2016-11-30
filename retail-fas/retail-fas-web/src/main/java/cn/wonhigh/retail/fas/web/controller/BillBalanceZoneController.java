package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BillBalanceHQManager;
import cn.wonhigh.retail.fas.manager.BillBalanceZoneManager;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 地区批发结算
 * 
 * @author yang.y
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/bill_balance_zone")
@ModuleVerify("30160013")
public class BillBalanceZoneController extends BaseCrudController<BillBalance> {

	private Logger logger = Logger.getLogger(BillBalanceZoneController.class);

	@Resource
	private BillBalanceZoneManager billBalanceZoneManager;

	@Resource
	private BillSaleBalanceManager billSaleBalanceManager;

	@Resource
	private BillBalanceHQManager billBalanceHQManager;

	@Resource
	private FinancialAccountManager financialAccountManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_balance_zone/", billBalanceZoneManager);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listTab")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if (StringUtils.isNotBlank(isHq)) {
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("bill_balance_zone/balance_zone_tab");
		return mav;
	}

	@RequestMapping("/balance_dtl")
	public String forwardBalanceDtl() {
		return "bill_balance_zone/balance_dtl";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/to_custom_create_balance")
	public ModelAndView toCustomCreateBalance(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if (StringUtils.isNotBlank(isHq)) {
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("bill_balance_zone/create_balance");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/to_balance_adjust")
	public ModelAndView toBalanceAdjust(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if (StringUtils.isNotBlank(isHq)) {
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("bill_balance_zone/balance_adjust");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String billNoMenu = req.getParameter("billNoMenu");
		if (StringUtils.isNotBlank(billNoMenu)) {
			mav.addObject("billNoMenu", billNoMenu);
		}
		String warnPostUrl = req.getParameter("warnPostUrl");
		if (StringUtils.isNotBlank(warnPostUrl)) {
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		String isHq = req.getParameter("isHq");
		if (StringUtils.isNotBlank(isHq)) {
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("bill_balance_zone/list");
		return mav;
	}

	@RequestMapping(value = "/list.json")
	@ResponseBody
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<Object> list = new ArrayList<Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req
				.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = StringUtils.isEmpty(params.get("isHq") + "") ? "" : String.valueOf(params.get("isHq"));
		String companyNos = null;
		if (isHq.equals("true")) {
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		} else {
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", " AND SALER_NO IN (" + companyNos + ")");
		}
		if (params.get("organNoFromCondition") != null && !("").equals(params.get("organNoFromCondition"))) {
			params.put("organNoFromCondition", (params.get("organNoFromCondition") + "").split(","));
		}
		if (params.get("multiBuyerNo") != null && !"".equals(params.get("multiBuyerNo"))) {
			params.put("buyerNoArray", String.valueOf(params.get("multiBuyerNo")).split(","));
		}
		total = billBalanceZoneManager.findCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = billBalanceZoneManager.findByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	@RequestMapping(value = "/enter_list.json")
	@ResponseBody
	public Map<String, Object> queryEnterList(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<Object> list = new ArrayList<Object>();
		List<Object> footerList = new ArrayList<Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req
				.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setQueryCondition(params);
		total = billBalanceHQManager.selectEnterCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = billBalanceHQManager.selectEnterList(page, sortColumn, sortOrder, params);
			footerList = billBalanceHQManager.selectEnterFooter(params);
		}
		map.put("total", total);
		map.put("rows", billBalanceZoneManager.setExtendsBillSaleBalanceProperties(list));
		map.put("footer", footerList);
		return map;
	}

	/**
	 * 自定义结算单
	 * 
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@ResponseBody
	@RequestMapping(value = "/custom_create_balance")
	public BillBalance customCreateBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if (StringUtils.isNotBlank(req.getParameter("checkedRows"))) {
			obj.setBalanceStartDate(obj.getBalanceStartDate1());
			obj.setBalanceEndDate(obj.getBalanceEndDate1());
			ObjectMapper mapper = new ObjectMapper();
			List<Map> itemList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {
			});
			List<Object> lstItem = null;
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == obj.getBalanceType().intValue()) {
				lstItem = convertListWithTypeReference(mapper, itemList, BillBuyBalance.class);
			} else {
				lstItem = convertListWithTypeReference(mapper, itemList, BillSaleBalance.class);
			}
			if (lstItem.size() > 0) {
				String organNoFroms = req.getParameter("multiOrganNoFrom");
				if (StringUtils.isNotBlank(organNoFroms)) {
					obj.setOrganNoFrom(FasUtil.parseInQueryCondition(organNoFroms));
				}
				SystemUser currUser = CurrentUser.getCurrentUser(req);
				obj.setCreateUser(currUser.getUsername());
				obj.setCreateTime(new Date());
				obj = billBalanceZoneManager.createBalance(obj, lstItem);
				return obj;
			}

		}
		return null;
	}

	/**
	 * 结算调整
	 * 
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@ResponseBody
	@RequestMapping(value = "/balance_adjust")
	public BillBalance balanceAdjust(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if (StringUtils.isNotBlank(req.getParameter("checkedRows"))) {
			ObjectMapper mapper = new ObjectMapper();
			List<Map> deductionList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {
			});
			List<Object> lstItem = convertListWithTypeReference(mapper, deductionList, OtherDeduction.class);
			SystemUser currUser = CurrentUser.getCurrentUser(req);
			obj.setUpdateUser(currUser.getUsername());
			obj.setUpdateTime(new Date());
			obj = billBalanceZoneManager.balanceAdjust(obj, lstItem);
			return obj;
		}
		return null;
	}

	/**
	 * 设置结算查询条件
	 * 
	 * @param params
	 */
	private Map<String, Object> setQueryCondition(Map<String, Object> params) {
		if (null != params.get("balanceType")) {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			String queryCondition = params.get("queryCondition") == null ? "" : String.valueOf(params
					.get("queryCondition"));
			queryCondition = queryCondition.concat(BalanceTypeEnums.getQueryConditionByNo(iBalanceType));
			String companyNos = financialAccountManager.findLeadRoleCompanyNos();
			if (iBalanceType == BalanceTypeEnums.AREA_WHOLESALE.getTypeNo()) {
				String isHq = StringUtils.isEmpty(params.get("isHq") + "") ? "" : String.valueOf(params.get("isHq"));
				if (!isHq.equals("true")) {
					companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
				}
				if (StringUtils.isNotEmpty(companyNos)) {
					queryCondition = queryCondition.concat(" AND SALER_NO IN (" + companyNos + ")");
				}
			} else {
				if (iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {
					queryCondition = queryCondition.concat(" AND buyer_no IN (" + companyNos + ")");
				} else if (iBalanceType == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()) {
					queryCondition = queryCondition.concat(" AND saler_no IN (" + companyNos
							+ ") AND buyer_no NOT IN (" + companyNos + ")");
				} else if (iBalanceType == BalanceTypeEnums.HQ_OTHER.getTypeNo()) {
					queryCondition = queryCondition.concat(" AND buyer_no IN (" + companyNos
							+ ")  AND saler_no NOT IN (" + companyNos + ")");
				}
				params.put("zoneCompanyNo", companyNos);
			}
			params.put("queryCondition", queryCondition);
		}
		if (null != params.get("multiBrandNo")) {
			params.put("multiBrandNo", FasUtil.formatInQueryCondition(params.get("multiBrandNo").toString()));
		}
		return params;
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	private List<Object> convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List<Object> tl = new ArrayList<Object>(list.size());
		if (!CollectionUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}

	/**
	 * 进入销售出库明细列表页面
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return viewName
	 */
	@RequestMapping("/listBillDtl")
	public ModelAndView listBillDtl(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String billNoMenu = request.getParameter("billNoMenu");
		if (StringUtils.isNotBlank(billNoMenu)) {
			mav.addObject("billNoMenu", billNoMenu);
		}
		String isHq = request.getParameter("isHq");
		if (StringUtils.isNotBlank(isHq)) {
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("bill_balance_zone/list_bill_dtl");
		return mav;
	}

	@RequestMapping("/deductionTab")
	public ModelAndView forwardDeductionTab(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_zone/deduction_tab");
		return mav;
	}

	@RequestMapping("/invoiceTab")
	public ModelAndView forwardInvoiceTab(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_zone/invoice_tab");
		return mav;
	}

	@RequestMapping("/brandCategoryDeductionTab")
	public ModelAndView forwardBrandCategroyDeductionTab(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_zone/brandCategoryDeduction_tab");
		return mav;
	}

	@RequestMapping("/selectAmount")
	@ResponseBody
	public Map<String, BigDecimal> selectAmount(HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", request.getParameter("salerNo"));
		params.put("customerNo", request.getParameter("buyerNo"));
		Map<String, BigDecimal> result = billBalanceZoneManager.selectAmount(params);
		return result;
	}

	/**
	 * 新增
	 * 
	 * @param model
	 *            待新增的实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return BillBalance
	 * @throws ManagerException 
	 */
	@RequestMapping(value = "/addBill", method = RequestMethod.POST)
	@ResponseBody
	public BillBalance addBill(@ModelAttribute("model") BillBalance model, HttpServletRequest request) throws ManagerException {

		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		model.setCreateUser(loginUser.getUsername());
		model.setCreateTime(new Date());
		model.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());
		model = billBalanceZoneManager.addBill(model);
		return model;
	}

	/**
	 * 修改
	 * 
	 * @param model
	 *            待修改的实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return BillBalance
	 */
	@RequestMapping("/updateBill")
	@ResponseBody
	public BillBalance updateBill(@ModelAttribute("model") BillBalance model, HttpServletRequest request) {
		try {
			SystemUser loginUser = CurrentUser.getCurrentUser(request);
			model.setUpdateUser(loginUser.getUsername());
			model.setUpdateTime(new Date());
			model.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());
			return billBalanceZoneManager.updateBill(model);
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}
		return model;
	}

	/**
	 * 批量新增
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Boolean>
	 */
	@RequestMapping(value = "/batchAddBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchAddBill(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> params = this.initBatchAddBillParams(request);
			if (params == null || params.size() == 0) {
				map.put("success", false);
				return map;
			}
			int iCount = billBalanceZoneManager.batchAddBill(params);
			map.put("count", iCount);
			map.put("success", true);
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 组装批量生成结算单的参数
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Object>
	 */
	private Map<String, Object> initBatchAddBillParams(HttpServletRequest request) {
		// 品牌部生成方式
		String tempBrandUnitNos = "";
		String brandUnitFlag = request.getParameter("brandUnitFlag");
		String salerNo = request.getParameter("batchSalerNo");
		String buyerNo = request.getParameter("batchBuyerNo");
		String brandUnitNo = request.getParameter("batchBrandUnitNo");
		String balanceStartDate = request.getParameter("balanceStartDate");
		String balanceEndDate = request.getParameter("balanceEndDate");
		String organNoFrom = request.getParameter("organNoFrom");
		String organNameFrom = request.getParameter("organNameFrom");
		// 校验需要的数据是否有值
		if (StringUtils.isEmpty(salerNo) // || StringUtils.isEmpty(buyerNo)
				|| StringUtils.isEmpty(balanceStartDate) || StringUtils.isEmpty(balanceEndDate)) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("salerNo", "," + salerNo + ",");
		params.put("buyerNo", "," + buyerNo + ",");
		params.put("balanceStartDate", balanceStartDate);
		params.put("balanceEndDate", balanceEndDate);
		params.put("brandUnitFlag", brandUnitFlag);
		params.put("organNoFrom", organNoFrom);
		params.put("organNameFrom", organNameFrom);
		if (StringUtils.isNotEmpty(brandUnitNo)) {
			brandUnitNo = "," + brandUnitNo + ",";
			tempBrandUnitNos = FasUtil.formatInQueryCondition(brandUnitNo);
		}
		params.put("brandUnitNo", brandUnitNo);
		params.put("tempBrandUnitNos", tempBrandUnitNos);
		// 批发/团购出库单
		params.put("billType", BillTypeEnums.SALEOUTS.getRequestId());
		// 团购销售
		params.put("bizType", BizTypeEnums.WHOLESALE.getStatus());
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		params.put("loginName", loginUser.getUsername());
		return params;
	}

	/**
	 * 校验是否可删除
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Boolean
	 */
	@RequestMapping("/checkDel")
	@ResponseBody
	public Boolean checkDel(HttpServletRequest request) {
		return true;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Boolean>
	 */
	@RequestMapping("/delBill")
	@ResponseBody
	public Map<String, Boolean> batchDelete(HttpServletRequest request) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String deletedList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request
					.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {
			});
			List<BillBalance> oList = convertListWithTypeReference(mapper, list);
			billBalanceZoneManager.delete(oList);
			map.put("success", true);
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			map.put("success", false);
		}
		return map;
	}

	private List<BillBalance> convertListWithTypeReference(ObjectMapper mapper, List<Map> list)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		Class<BillBalance> entityClass = (Class<BillBalance>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		List<BillBalance> tl = new ArrayList<BillBalance>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillBalance type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			tl.add(type);
		}
		return tl;
	}

	/**
	 * 业务确认/打回操作
	 * 
	 * @param model
	 *            待确认的实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return BillBalance
	 */
	@RequestMapping(value = "/confirm")
	@ResponseBody
	public BillBalance confirm(HttpServletRequest request) {
		try {
			SystemUser loginUser = CurrentUser.getCurrentUser(request);
			String confirmList = StringUtils.isEmpty(request.getParameter("confirm")) ? "" : request
					.getParameter("confirm");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(confirmList, new TypeReference<List<Map>>() {
			});
			List<BillBalance> lstBill = convertListWithTypeReference(mapper, list);
			if (lstBill != null && lstBill.size() > 0) {
				for (BillBalance model : lstBill) {
					model.setAuditor(loginUser.getUsername());
					model.setAuditTime(new Date());
				}
				return billBalanceZoneManager.confirm(lstBill);
			}
			return null;
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 导出
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export_sale_dtl")
	public void exportSaleDtl(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		List<Map> dataMapList = ExportUtils.getDataList(this.getDataList(req, model));
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}

	/**
	 * 获取导出数据
	 * 
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getDataList(HttpServletRequest req, Model model) throws Exception {
		List dataList = null;
		List footerList = null;
		int pageNumber = Integer
				.parseInt(req.getParameter("pageNumber") == null ? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null ? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		dataList = billBalanceHQManager.selectEnterList(page, "", "", setQueryCondition(params));
		List<BillSaleBalance> retList = billBalanceZoneManager.setExtendsBillSaleBalanceProperties(dataList);
		for (BillSaleBalance saleBalance : retList) {
			int bizType = saleBalance.getBizType();
			if (bizType == BizTypeEnums.WHOLESALE.getStatus()) {
				saleBalance.setBizTypeName("批发销售");
			} else if (bizType == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus()) {
				saleBalance.setBizTypeName("批发销售(店出)");
			} else if (bizType == BizTypeEnums.WHOLESALE_RETURN.getStatus()) {
				saleBalance.setBizTypeName("过季退货");
			} else if (bizType == BizTypeEnums.CUSTOMER_RETURN.getStatus()) {
				saleBalance.setBizTypeName("客残退货");
			}
		}
		footerList = billBalanceHQManager.selectEnterFooter(params);
		if (!CollectionUtils.isEmpty(footerList) && !CollectionUtils.isEmpty(dataList)) {
			dataList.add(footerList.get(0));
		}
		return dataList;
	}

	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = StringUtils.isEmpty(params.get("isHq") + "") ? "" : String.valueOf(params.get("isHq"));
		String companyNos = null;
		if (isHq.equals("true")) {
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		} else {
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", " AND SALER_NO IN (" + companyNos + ")");
		}
		if (params.get("multiBuyerNo") != null && !"".equals(params.get("multiBuyerNo"))) {
			params.put("buyerNoArray", String.valueOf(params.get("multiBuyerNo")).split(","));
		}
		List<Object> lstItem = billBalanceZoneManager.findByBiz(null, params);
		List<Map> dataMapList = ExportUtils.getDataList(lstItem);
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}

}
