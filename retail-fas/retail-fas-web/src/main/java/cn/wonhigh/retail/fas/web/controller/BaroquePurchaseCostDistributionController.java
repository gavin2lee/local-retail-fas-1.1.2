package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.manager.BaroquePurchaseCostDistributionManager;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceAdditionalManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/baroque_purchase_cost_distribution")
@ModuleVerify("34000005")
public class BaroquePurchaseCostDistributionController extends BaseController<BillBuyBalanceAdditionDto> {

	@Resource
	private BaroquePurchaseCostDistributionManager baroquePurchaseCostDistributionManager;

	@Resource
	private BillBuyBalanceAdditionalManager billBuyBalanceAdditionalManager;

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SupplierRateSetController.class);

	@Override
	public CrudInfo init() {
		return new CrudInfo("baroque_bill_balance/", baroquePurchaseCostDistributionManager);
	}

	@RequestMapping(value = "/cost_distribution")
	public String costDistribution() {
		return "baroque_bill_balance/cost_distribution";
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
		setParams(req, params);

		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			int total = this.billBuyBalanceAdditionalManager.findGroupBaroqueDistributeCostBillCount(params);//
			// TODO
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillBuyBalanceAdditionDto> list = this.billBuyBalanceAdditionalManager.findBaroqueDistributeCostBill(
					page, sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 新增/修改
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Boolean>
	 */
	@RequestMapping(value = "/distribute_cost")
	@ResponseBody
	public Map<String, Object> distributeCost(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String originalNos = this.getOriginalNos(request);
			BigDecimal cost = this.getPurchaseFee(request);
			if (cost == BigDecimal.ZERO) {
				map.put("success", true);
				map.put("mgr", "分配费用获取失败!");
				return map;
			}
			this.baroquePurchaseCostDistributionManager.distributePurchaseCost(originalNos, cost);
			map.put("success", true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO
		}
		return map;
	}

	private BigDecimal getPurchaseFee(HttpServletRequest request) {
		if (request.getParameter("fee") != null) {
			return new BigDecimal(request.getParameter("fee"));
		}
		return BigDecimal.ZERO;
	}

	String getOriginalNos(HttpServletRequest request) throws Exception {
		String jsonString = StringUtils.isEmpty(request.getParameter("originalNos")) ? "" : request
				.getParameter("originalNos");

		if (StringUtils.isNotEmpty(jsonString)) {
			String[] strArr = jsonString.split(",");
			if (strArr != null & strArr.length > 0) {
				String originalNos = "(";
				for (String s : strArr) {
					originalNos += "'" + s + "',";
				}
				originalNos = originalNos.substring(0, originalNos.length() - 1);
				originalNos += ")";
				return originalNos;
			}
		}
		return null;
	}

	private void setParams(HttpServletRequest req, Map<String, Object> params) {

	}
}
