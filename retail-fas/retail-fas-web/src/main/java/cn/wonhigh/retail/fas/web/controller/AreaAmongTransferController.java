/**  
*   
* 项目名称：retail-fas-web  
* 类名称：AreaAmongController  
* 类描述：地区间-调拨出库明细表
* 创建人：ouyang.zm  
* 创建时间：2014-10-8 上午11:56:12  
* @version       
*/ 
package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.AreaAmongTransferManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
@Controller
@RequestMapping("/area_among_transfer")
@ModuleVerify("30133006")
public class AreaAmongTransferController extends BaseCrudController<BillSaleBalance> {
	@Resource
	private AreaAmongTransferManager areaAmongTransferManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Override
	public CrudInfo init() {
		return new CrudInfo("area_among_transfer/", areaAmongTransferManager);
	}
	
	/**
	 * 转到跨区调拨明细表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/transfer")
	public ModelAndView toTansferOut(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String flag = req.getParameter("atoa");
		String zoneflag = req.getParameter("senda");
		if (StringUtils.isNotBlank(flag)) {
			if (flag.equals("ys") && zoneflag.equals("no")) {//地区间应收
				mav.addObject("aToA", "ys");
				mav.setViewName("/area_among_balance/receivable/transfer_cargo_list");
			} else if (flag.equals("yf")) {//地区间应付
				mav.addObject("aToA", "yf");
				mav.setViewName("/area_among_balance/payable/transfer_storage_list");
			} else if (flag.equals("ys") && zoneflag.equals("yes")) {//森达
				mav.addObject("aToA", "ys");
				mav.setViewName("/area_among_balance/senda/transfer_cargo_list");
			}
		}
		return mav;
	}
	
	/**
	 * 转到收发汇总表
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public ModelAndView areaAmongListTabMain(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String flag = req.getParameter("atoa");
		if (StringUtils.isNotBlank(flag)) {
			if (flag.equals("ys")) {//地区间应收
				mav.addObject("RToS", "ys");
				mav.setViewName("area_among_balance/receivable/transfer_summary_list");
			} else if (flag.equals("yf")) {//地区间应付
				mav.addObject("RToS", "yf");
				mav.setViewName("area_among_balance/payable/transfer_summary_list");
			}
			
		}
		return mav;
	}
	
	/**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(HttpServletRequest req,Map<String, Object> map){
		String brandUnitNos = "";
		if (map.get("brandUnitNo") != null) {
			brandUnitNos = FasUtil.formatInQueryCondition(map.get("brandUnitNo").toString());
		}
		String brandNos = "";
		if (map.get("brandNo") != null) {
			brandNos = FasUtil.formatInQueryCondition(map.get("brandNo").toString());
		}
		String categoryNos="";
		if (map.get("oneLevelCategoryNo") != null) {
			categoryNos = FasUtil.formatInQueryCondition(map.get("oneLevelCategoryNo").toString());
		}
		map.put("brandUnitNo", brandUnitNos);
		map.put("brandNo", brandNos);
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
		//选单操作
		String balanceFlag = StringUtils.isEmpty(req.getParameter("balanceNoFlag")) ? "" : String.valueOf(req.getParameter("balanceNoFlag"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		params.put("balanceNoFlag", balanceFlag);
		//过滤承担总部职能的结算公司,买卖双方都是地区公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.SALER_NO NOT IN (" + companyNos + ") AND B.BUYER_NO not in ("
					+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.areaAmongTransferManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillSaleBalance> list = this.areaAmongTransferManager.findByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		List<TotalDto> totalList = areaAmongTransferManager.findTotalRow(params);//查询合计行
		if (totalList.size() >= 0) {
			BillSaleBalance totalRow = new BillSaleBalance();
			totalRow.setZoneName("合计");
			totalRow.setSendQty(totalList.get(0).getTotalOutQty());
			totalRow.setSendAmount(totalList.get(0).getTotalOutAmount()); 
			totalRow.setPurchaseAmount(totalList.get(0).getTotalPurchaseAmount());
			List<BillSaleBalance> footList = new ArrayList<BillSaleBalance>();
			footList.add(totalRow);
			obj.put("footer", footList);
		}
		return obj;
	}
	
	/**
	 * 收发汇总查询
	 */
	@RequestMapping(value = "/summary_list.json")
	@ResponseBody
	public Map<String, Object> queryGatherList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		//过滤承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND C.SALER_NO NOT IN (" + companyNos + ") AND C.BUYER_NO not in ("
					+ companyNos + ")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.areaAmongTransferManager.findSummaryCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillSaleBalance> list = this.areaAmongTransferManager.findSummaryByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		List<TotalDto> totalList = areaAmongTransferManager.findSummaryTotalRow(params);//查询合计行
		if (totalList.size() >= 0) {
			BillSaleBalance totalRow = new BillSaleBalance();
			totalRow.setSalerName("合计");
			totalRow.setSendQty(totalList.get(0).getTotalOutQty());
			totalRow.setSendAmount(totalList.get(0).getTotalOutAmount()); 
			totalRow.setReceiveQty(totalList.get(0).getTotalEntryQty());
			totalRow.setReceiveAmount(totalList.get(0).getTotalEntryAmount());
			List<BillSaleBalance> footList = new ArrayList<BillSaleBalance>();
			footList.add(totalRow);
			obj.put("footer", footList);
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
//		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
//		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
//		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		params.put("subTotal", "yes");//小计参数
		setParams(req,params);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();	//过滤承担总部职能的结算公司
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.SALER_NO NOT IN ("+companyNos +") AND B.BUYER_NO not in ("+companyNos +")");
		}
		int total = this.areaAmongTransferManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillSaleBalance> dataList = this.areaAmongTransferManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	/**
	 * 收发汇总导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export_summary")
	public void exportSummary(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();	//过滤承担总部职能的结算公司
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND C.SALER_NO NOT IN (" + companyNos + ") AND C.BUYER_NO not in ("
					+ companyNos + ")");
		}
		int total = this.areaAmongTransferManager.findSummaryCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillSaleBalance> dataList = this.areaAmongTransferManager.findSummaryByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
