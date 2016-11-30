package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.manager.BaroquePurchaseRegionCostManager;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceAdditionalManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.AreaAmongBalanceExportUtils;

import com.alibaba.dubbo.rpc.Result;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@ModuleVerify("34000006")
@RequestMapping("/baroque_purchase_region_cost")
public class BaroquePurchaseRegionCostController extends BaseController<BillBuyBalanceAdditionDto> {

	@Resource
	private BaroquePurchaseRegionCostManager baroquePurchaseRegionCostManager;

	@Resource
	private BillBuyBalanceAdditionalManager billBuyBalanceAdditionalManager;

	@Resource
	private FinancialAccountManager financialAccountManager;

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SupplierRateSetController.class);

	@Override
	protected CrudInfo init() {
		return new CrudInfo("baroque_bill_balance/", baroquePurchaseRegionCostManager);
	}

	@RequestMapping(value = "/region_cost")
	public String regionCost() {
		return "baroque_bill_balance/region_cost";
	}

	/**
	 * 
	 */
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		// 选单操作
		Map<String, Object> params = builderParams(req, model);
		// this.setParams(req, params);

		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			String companyNos = financialAccountManager.findLeadRoleCompanyNos();
			params.put("HQCompanyNo", companyNos);
			int total = this.billBuyBalanceAdditionalManager.findBaroqueRegionCostBillCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillBuyBalanceAdditionDto> list = this.billBuyBalanceAdditionalManager.findBaroqueRegionCostBill(page,
					sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/sumList")
	@ResponseBody
	public Map<String, Object> querySumList(HttpServletRequest req, Model model) throws ManagerException {

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		// 选单操作
		Map<String, Object> params = builderParams(req, model);

		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			String companyNos = financialAccountManager.findLeadRoleCompanyNos();
			params.put("HQCompanyNo", companyNos);
			int total = this.billBuyBalanceAdditionalManager.findGroupBaroqueRegionCostBillCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillBuyBalanceAdditionDto> list = this.billBuyBalanceAdditionalManager.findGroupBaroqueRegionCostBill(
					page, sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return obj;
	}

	@RequestMapping(value = "/updateRegionCost")
	@ResponseBody
	public Map<String, Object> updateRegionCost(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			String ids = request.getParameter("ids");
			if (!StringUtils.isEmpty(ids)) {
				try {
					Map<String,Integer> result = baroquePurchaseRegionCostManager.updateRegionCost(ids);
					map.put("success", true);
					Integer total = result.get("total");
					Integer pass = result.get("pass");
					map.put("mgr",String.format("成功更新 %s笔数据,由于关账日限制过滤 %s 笔数据",total-pass,pass));
				} catch (Exception e) {
					throw new ManagerException(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			map.put("success", false);
		}
		return map;
	}
	private BigDecimal getDecimalParam(HttpServletRequest req,String key) throws ManagerException{
		BigDecimal val = BigDecimal.ZERO;
		try {
			val = new BigDecimal(req.getParameter(key));
		} catch(Exception e) {
			throw new ManagerException("更新输入数值有问题",e);
		}
		return val;
	}
	
	@RequestMapping(value = "/updateBuyAdl")
	@ResponseBody
	public Map<String, Object> updateBillBuyBalanceAddtional(HttpServletRequest req, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BigDecimal exchange = this.getDecimalParam(req, "exchangeRate");
			BigDecimal tariffRate = this.getDecimalParam(req, "tariffRate");
			BigDecimal vatRate = this.getDecimalParam(req, "vatRate");
			String id = req.getParameter("aId");
			baroquePurchaseRegionCostManager.updateBuyAdl(exchange, tariffRate, vatRate, id);
			map.put("success", true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			map.put("mgr", e.getMessage());
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 列表明细导出
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void listExport(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		params.put("HQCompanyNo", companyNos);
		int total = this.billBuyBalanceAdditionalManager.findBaroqueRegionCostBillCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalanceAdditionDto> dataList = this.billBuyBalanceAdditionalManager.findBaroqueRegionCostBill(page,
				sortColumn, sortOrder, params);
		AreaAmongBalanceExportUtils.doExport(null, fileName, null, exportColumns, dataList, response, 1);
	}

	/**
	 * 列表汇总导出
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sum_export")
	public void sumExport(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		params.put("HQCompanyNo", companyNos);
		int total = this.billBuyBalanceAdditionalManager.findGroupBaroqueRegionCostBillCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalanceAdditionDto> list = this.billBuyBalanceAdditionalManager.findGroupBaroqueRegionCostBill(
				page, sortColumn, sortOrder, params);
		AreaAmongBalanceExportUtils.doExport(null, fileName, null, exportColumns, list, response, 1);
	}
}
