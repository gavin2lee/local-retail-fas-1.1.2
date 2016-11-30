/**
 * title:PriceCheckAndUpdateController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:地区价检查和更新
 * auther:user
 * date:2016-8-2 下午2:51:45
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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.PriceCheckAndUpdateManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("area_price_check")
@ModuleVerify("30120018")
public class PriceCheckAndUpdateController extends BaseCrudController<PriceCheckAndUpdateDto>{

	@Resource
	private PriceCheckAndUpdateManager priceCheckAndUpdateManager;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("price_checkAndUpdate/area_price/", priceCheckAndUpdateManager);
	}
	
	/**
	 * 设置参数
	 * @param params
	 */
	public void setParams(Map<String, Object> params){
		String brandUnitNos = "";
		if (params.get("brandUnitNo") != null) {
			brandUnitNos = FasUtil.formatInQueryCondition(params.get("brandUnitNo").toString());
		}
    	String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("hqCompanyData",companyNos);
		params.put("brandUnitNo", brandUnitNos);
    }
	
	/**
	 * 总部、地区出库单据检查
	 */
	@RequestMapping(value = "/sale_list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String exceptionType = StringUtils.isEmpty(req.getParameter("exceptionType")) ? "" : String.valueOf(req.getParameter("exceptionType"));
		Map<String, Object> params = builderParams(req, model);
		setParams(params);
		
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		String companyNo = params.get("saleCompanyNo").toString();
		
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = 0;
		List<PriceCheckAndUpdateDto> list = new ArrayList<PriceCheckAndUpdateDto>();
		if(exceptionType.equals("1") && hqCompanyNos.contains(companyNo)){
			total = this.priceCheckAndUpdateManager.findHqSaleBillCostIsDiffCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.priceCheckAndUpdateManager.findHqSaleBillCostIsDiffByPage(page, sortColumn, sortOrder, params);
		}else if(exceptionType.equals("1") && regionCompanyNos.contains(companyNo)){
			total = this.priceCheckAndUpdateManager.findAreaSaleBillCostIsDiffCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.priceCheckAndUpdateManager.findAreaSaleBillCostIsDiffByPage(page, sortColumn, sortOrder, params);
		}else if(exceptionType.equals("0") && hqCompanyNos.contains(companyNo)){
			total = this.priceCheckAndUpdateManager.findHqSaleBillCostIsNullCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.priceCheckAndUpdateManager.findHqSaleBillCostIsNullByPage(page, sortColumn, sortOrder, params);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 总部、地区入库单据检查
	 */
	@RequestMapping(value = "/buy_list.json")
	@ResponseBody
	public Map<String, Object> querySumList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String exceptionType = StringUtils.isEmpty(req.getParameter("exceptionType")) ? "" : String.valueOf(req.getParameter("exceptionType"));
		Map<String, Object> params = builderParams(req, model);
		setParams(params);
		
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		String companyNo = params.get("saleCompanyNo").toString();
		
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = 0;
		List<PriceCheckAndUpdateDto> list = new ArrayList<PriceCheckAndUpdateDto>();
		if(exceptionType.equals("1") && regionCompanyNos.contains(companyNo)){
			total = this.priceCheckAndUpdateManager.findAreaBuyBillCostIsDiffCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.priceCheckAndUpdateManager.findAreaBuyBillCostIsDiffByPage(page, sortColumn, sortOrder, params);
		}else if(exceptionType.equals("1") && hqCompanyNos.contains(companyNo)){
			total = this.priceCheckAndUpdateManager.findHqBuyBillCostIsDiffCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.priceCheckAndUpdateManager.findHqBuyBillCostIsDiffByPage(page, sortColumn, sortOrder, params);
		}else if(exceptionType.equals("2") && regionCompanyNos.contains(companyNo)){
			total = this.priceCheckAndUpdateManager.findAreaBuyDiffOutBillCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.priceCheckAndUpdateManager.findAreaBuyDiffOutBillByPage(page, sortColumn, sortOrder, params);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 更新出库单据单价
	 * @param req
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/salePrice_update")
	@ResponseBody
	public Integer saleCostUpdate(HttpServletRequest req, Model model) throws ServiceException, ManagerException{
		Map<String, Object> params = builderParams(req, model);
		setParams(params);
		int total = 0;
		int count = priceCheckAndUpdateManager.modifySaleBillCost(params);
		total += count;
		return total;
	}
	
	/**
	 * 更新入库单据单价
	 * @param req
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/buyPrice_update")
	@ResponseBody
	public Integer buyCostUpdate(HttpServletRequest req, Model model) throws ServiceException, ManagerException{
		Map<String, Object> params = builderParams(req, model);
		setParams(params);
		int total = 0;
		int count = priceCheckAndUpdateManager.modifyBuyBillCost(params);
		total += count;
		return total;
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
//		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String exceptionType = StringUtils.isEmpty(req.getParameter("exceptionType")) ? "" : String.valueOf(req.getParameter("exceptionType"));
		String flag = StringUtils.isEmpty(req.getParameter("flag")) ? "" : String.valueOf(req.getParameter("flag"));

		Map<String, Object> params = builderParams(req, model);
		setParams(params);
		
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		String companyNo=params.get("saleCompanyNo").toString();
		
		List<PriceCheckAndUpdateDto> list = new ArrayList<PriceCheckAndUpdateDto>();
		if(flag.equals("saleGrid")){
			if(exceptionType.equals("1") && hqCompanyNos.contains(companyNo)){
				list = this.priceCheckAndUpdateManager.findHqSaleBillCostIsDiffByPage(null, sortColumn, sortOrder, params);
			}else if(exceptionType.equals("1") && regionCompanyNos.contains(companyNo)){
				list = this.priceCheckAndUpdateManager.findAreaSaleBillCostIsDiffByPage(null, sortColumn, sortOrder, params);
			}else if(exceptionType.equals("0") && hqCompanyNos.contains(companyNo)){
				list = this.priceCheckAndUpdateManager.findHqSaleBillCostIsNullByPage(null, sortColumn, sortOrder, params);
			}
		}else if(flag.equals("buyGrid")){
			if (exceptionType.equals("1") && regionCompanyNos.contains(companyNo)) {
				list = this.priceCheckAndUpdateManager.findAreaBuyBillCostIsDiffByPage(null, sortColumn, sortOrder, params);
			}else if(exceptionType.equals("1") && hqCompanyNos.contains(companyNo)){
				list = this.priceCheckAndUpdateManager.findHqBuyBillCostIsDiffByPage(null, sortColumn, sortOrder, params);
			}else if(exceptionType.equals("2") && regionCompanyNos.contains(companyNo)){
				list = this.priceCheckAndUpdateManager.findAreaBuyDiffOutBillByPage(null, sortColumn, sortOrder, params);
			}
		}
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}
	
}
