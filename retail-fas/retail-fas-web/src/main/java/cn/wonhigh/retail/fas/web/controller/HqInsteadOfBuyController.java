/**  
* 项目名称：retail-fas-web  
* 类名称：HqInsteadOfBuyController  
* 类描述：总部代采明细表
* 创建人：ouyang.zm  
* 创建时间：2015-1-22 下午4:40:36  
* @version       
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.HqInsteadOfBuyManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/hq_insteadOf_buy")
public class HqInsteadOfBuyController extends BaseCrudController<BillBuyBalance>{
	@Resource
	private   HqInsteadOfBuyManager hqInsteadOfBuyManager;
	@Resource
	private   FinancialAccountManager financialAccountManager;
	@Override
	protected CrudInfo init() {
		return new CrudInfo("hq_insteadOf_buy/", hqInsteadOfBuyManager);
	}

	/**
	 * 采购明细表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET ,value ="/out_list")
    public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String flag = req.getParameter("sign");
		if (StringUtils.isNotBlank(flag)) {
			mav.addObject("sign", flag);
		}
		mav.setViewName("hq_insteadBuy_balance/instead_out_list");
		return mav;
    }
	
	/**
	 * 采购入库明细表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET ,value ="/entry_list")
    public ModelAndView enrtyList(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String flag = req.getParameter("sign");
		if (StringUtils.isNotBlank(flag)) {
			mav.addObject("sign", flag);
		}
		mav.setViewName("hq_insteadBuy_balance/instead_entry_list");
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
		if (map.get("brandUnitNoLike") != null) {
			brandUnitNos = FasUtil.formatInQueryCondition(map.get("brandUnitNoLike").toString());
		}
		String brandNos = "";
		if (map.get("brandNoLike") != null) {
			brandNos = FasUtil.formatInQueryCondition(map.get("brandNoLike").toString());
		}
		map.put("brandUnitNoLike", brandUnitNos);
		map.put("brandNoLike", brandNos);
		return map;
	}
	
	/**
	 * 采购明细表查询
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
		//设置选单操作传递参数
		String balanceFlag = StringUtils.isEmpty(req.getParameter("balanceNoFlag")) ? "" : String.valueOf(req.getParameter("balanceNoFlag"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		params.put("balanceNoFlag", balanceFlag);
		//过滤承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.buyer_no NOT IN (" + companyNos + ")");
		}
		int total = this.hqInsteadOfBuyManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBuyBalance> list = this.hqInsteadOfBuyManager.findByPage(page, sortColumn, sortOrder, params);
		List<BillBuyBalance> totalList=this.hqInsteadOfBuyManager.findTotalRow(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 采购明细表导出
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
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.buyer_no NOT IN (" + companyNos + ")");
		}
		int total = this.hqInsteadOfBuyManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalance> dataList = this.hqInsteadOfBuyManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	/**
	 * 采购入库明细表查询
	 */
	@RequestMapping(value = "/entry_list.json")
	@ResponseBody
	public Map<String, Object> queryEntryList(HttpServletRequest req, Model model)
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
			params.put("queryCondition", "AND B.BUYER_NO NOT IN (" + companyNos + ") AND B.SALER_NO NOT IN ("+companyNos+")");
		}
		int total = this.hqInsteadOfBuyManager.findEntryCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBuyBalance> list = this.hqInsteadOfBuyManager.findEntryByPage(page, sortColumn, sortOrder, params);
		List<BillBuyBalance> totalList=this.hqInsteadOfBuyManager.findEntryTotalRow(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 采购入库明细表导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/entry_export")
	public void exportData(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.BUYER_NO NOT IN (" + companyNos + ") AND B.SALER_NO NOT IN ("+companyNos+")");
		}
		int total = this.hqInsteadOfBuyManager.findEntryCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalance> dataList = this.hqInsteadOfBuyManager.findEntryByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
