/**
 * title:HqOtherEntryController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:总部其他入库明细表
 * auther:user
 * date:2015-5-8 上午9:53:44
 */
package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.HqOtherEntryManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
@Controller
@RequestMapping("/hq_other_stock_in_dtl")
@ModuleVerify("30170001")
public class HqOtherEntryController extends BaseCrudController<BillBuyBalance> {
	@Resource
    private FinancialAccountManager financialAccountManager;
	@Resource
	private HqOtherEntryManager hqOtherEntryManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("hq_other_stock_in_dtl/", hqOtherEntryManager);
	}
	
	/**
	 * 转到总部其他入库明细表
	 * @return
	 */
	@RequestMapping(value = "/other_in")
	public String toTansferOut() {
		return "area_other_out_balance/other_in_list";
	}
	
	/**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(HttpServletRequest req,Map<String, Object> map){
		String brandUnitNos = "";
		if (map.get("brandUnitNoLike") != null) {
			brandUnitNos = FasUtil.formatInQueryCondition(map.get("brandUnitNoLike").toString());
		}
		String brandNos = "";
		if (map.get("brandNoLike") != null) {
			brandNos = FasUtil.formatInQueryCondition(map.get("brandNoLike").toString());
		}
		String categoryNos="";
		if (map.get("oneLevelCategoryNo") != null) {
			categoryNos = FasUtil.formatInQueryCondition(map.get("oneLevelCategoryNo").toString());
		}
		map.put("brandUnitNoLike", brandUnitNos);
		map.put("brandNoLike", brandNos);
		map.put("oneLevelCategoryNo", categoryNos);
		return map;
	}
	
	/**
	 * 总部其他入库明细查询
	 */
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
		setParams(req,params);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.BUYER_NO IN (" + companyNos + ") AND B.SALER_NO not in (" + companyNos
					+ ")");
		}
		int total = this.hqOtherEntryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillSaleBalance> list = this.hqOtherEntryManager.findByPage(page, sortColumn, sortOrder, params);
		List<BillSaleBalance> totalList=hqOtherEntryManager.findTotalRow(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		if (totalList.size() > 0) {
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		if(StringUtils.isNotEmpty(companyNos)){
			params.put("queryCondition", "AND B.BUYER_NO IN ("+companyNos +") AND B.SALER_NO not in ("+companyNos +")");
		}
		int total = this.hqOtherEntryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillSaleBalance> dataList = this.hqOtherEntryManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
