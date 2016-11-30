/**
 * title:SalesStorageInquireController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:代销商品销存查询
 * auther:user
 * date:2016-4-8 上午10:27:25
 */
package cn.wonhigh.retail.fas.web.controller;

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

import cn.wonhigh.retail.fas.common.dto.SalesStorageInquireDto;
import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.PhysicalBrandManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/sales_storage_inquire")
@ModuleVerify("40001012")
public class SalesStorageInquireController extends BaseCrudController<SalesStorageInquireDto> {
	@Resource
	private PhysicalBrandManager physicalBrandManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("top_sports/sales_storage_inquire/", physicalBrandManager);
	}
	
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		//查询承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND BUYER_NO IN ("+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.physicalBrandManager.findSalesStorageInquireCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SettledBillsDto> list = this.physicalBrandManager.findSalesStorageInquireByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/sum_list.json")
	@ResponseBody
	public Map<String, Object> querySumList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		//查询承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND BUYER_NO IN ("+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.physicalBrandManager.findSalesStorageSumCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SettledBillsDto> list = this.physicalBrandManager.findSalesStorageSumByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
}
