/**
 * title:AreaAmongStorageController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:地区间-调拨入库明细表
 * auther:user
 * date:2015-5-6 下午4:48:17
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
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.AreaAmongStorageManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/area_among_storage")
public class AreaAmongStorageController extends BaseCrudController<BillBuyBalance>{
	@Resource
	private AreaAmongStorageManager areaAmongStorageManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Override
	public CrudInfo init() {
		return new CrudInfo("area_among_storage/", areaAmongStorageManager);
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
	 * 明细查询
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
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();	//过滤承担总部职能的结算公司
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND SALER_NO NOT IN (" + companyNos + ") AND BUYER_NO not in ("
					+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.areaAmongStorageManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBuyBalance> list = this.areaAmongStorageManager.findByPage(page, sortColumn, sortOrder, params);
		List<BillBuyBalance> totalList=this.areaAmongStorageManager.findTotalRow(params);
		obj.put("total", total);
		obj.put("rows", list);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 明细导出
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
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();//过滤承担总部职能的结算公司
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND SALER_NO NOT IN ("+companyNos +") AND BUYER_NO not in ("+companyNos +")");
		}
		int total = this.areaAmongStorageManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalance> dataList = this.areaAmongStorageManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
