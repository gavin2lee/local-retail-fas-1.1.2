/**  
*   
* 项目名称：retail-fas-web  
* 类名称：AreaOtherStockInEntryBalanceController  
* 类描述：地区其他入库结算单
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.AreaOtherInBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.AreaOtherOutBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums.OperateModule;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.AreaOtherStockInEntryManager;
import cn.wonhigh.retail.fas.manager.HqOtherStockOutBalanceManager;
import cn.wonhigh.retail.fas.web.utils.HqOtherStockOutExportUtils;
import cn.wonhigh.retail.fas.web.utils.HqInsteadOfBuyExportUtils;
import cn.wonhigh.retail.fas.web.utils.OperateLogHelper;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/area_other_stock_in_balance")
@ModuleVerify("30270209")
public class AreaOtherStockInEntryBalanceController extends BaseCrudController<BillBalance>{
	@Resource
    private HqOtherStockOutBalanceManager hqOtherStockOutBalanceManager;
	@Resource
	private AreaOtherStockInEntryManager areaOtherStockInEntryManager;
    @Resource
    private OperateLogHelper operateLogHelper;
    @Override
    public CrudInfo init() {
        return new CrudInfo("area_other_stock_in_balance/",hqOtherStockOutBalanceManager);
    }
    
    /**
	 * 单据明细
	 * @return
	 */
	@RequestMapping(value = "/balance_bill")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String billNoMenu = req.getParameter("billNoMenu");
		if(StringUtils.isNotBlank(billNoMenu)){
			mav.addObject("billNoMenu", billNoMenu);
		}
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("hq_other_out_balance/area_payable/list");
		return mav;
	}
    
    /**
     * 单据列表
     * @return
     */
    @RequestMapping(value = "/list_tabMain")
    public String mainTab(){
    	return "hq_other_out_balance/area_payable/list_tabMain";
    }
    
    /**
	 * 请款信息tab页
	 * @return
	 */
	@RequestMapping(value = "/receivableInfo")
	public String toPaymentInfoList() {
		return "hq_other_out_balance/area_payable/payment_info";
	}
    
	/**
     * 出库明细TAB列表
     * @return
     */
    @RequestMapping(value = "/transferOutTab")
    public String toTransferOutList(){
    	return "hq_other_out_balance/area_payable/send_out_dtl";
    }
    
    /**
     * 采购发票信息TAB列表
     * @return
     */
    @RequestMapping(value = "/invoiceInfo")
    public String toInvoiceInfoList(){
    	return "hq_other_out_balance/area_payable/invoice_info";
    }
	
	/**
	 * 查询结算单
	 */
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		Map<String, Object> obj = super.queryList(req, model);
		//根据balanceType查询结算单合计
		String balanceType = StringUtils.isEmpty(req.getParameter("balanceType")) ? "": String.valueOf(req.getParameter("balanceType"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceType", balanceType);
		List<BillBalance> totalList=hqOtherStockOutBalanceManager.findTotalRow(params);
		if(totalList.size()>=1){
			obj.put("footer", totalList);
		}
		return obj;
	}

	/**
	 * 查询出库明细
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/transferOutList")
	@ResponseBody
	public Map<String, Object> getbillTransferDtl(HttpServletRequest req,Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1: Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10: Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "": String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "": String.valueOf(req.getParameter("order"));
		String balanceNo = StringUtils.isEmpty(req.getParameter("balanceNo")) ? "": String.valueOf(req.getParameter("balanceNo"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceNo", balanceNo);//结算单编号
		int total = this.areaOtherStockInEntryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillSaleBalance> list = this.areaOtherStockInEntryManager.findByPage(page,sortColumn, sortOrder, params);
		List<BillSaleBalance> totalList=areaOtherStockInEntryManager.findTotalRow(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		if (totalList.size() >0) {
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 批量业务、财务、打回操作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/batch_audit")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BatchAuditConfirm(HttpServletRequest req) throws Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		String idLists = req.getParameter("idList");
		String[] ids=idLists.split(",");
		String operStatus = req.getParameter("status");
		Map<String, Object> map = new HashMap<String, Object>();
		String operStatusName = AreaOtherInBalanceStatusEnums.getTypeName(Integer.parseInt(operStatus));
		map.put("statusName", operStatusName);
		map.put("status", operStatus);
		map.put("auditor", loginUser.getUsername());
		map.put("auditTime", DateUtil.getCurrentDateTime());
		//修改结算单状态
		hqOtherStockOutBalanceManager.modifyBillStatus(ids,map);
		//保存操作日志
		operateLogHelper.addVerifyPost(req,idLists,OperateModule.JSD.getModuleNo(),Integer.parseInt(operStatus),operStatusName,operStatusName);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 单个业务、财务、打回操作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public ResponseEntity<BillBalance> AuditConfirm(HttpServletRequest req,@RequestParam("billNo") String[] billNo,@RequestParam("operStatus")String OperStatus) throws Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		Map<String, Object> map = new HashMap<String, Object>();
		String OperStatusName = AreaOtherOutBalanceStatusEnums.getTypeName(Integer.parseInt(OperStatus));
		map.put("statusName", OperStatusName);
		map.put("status", OperStatus);
		map.put("auditor", loginUser.getUsername());
		map.put("auditTime", DateUtil.getCurrentDateTime());
		//修改结算单状态
		hqOtherStockOutBalanceManager.modifyBillStatus(billNo,map);
		String no=billNo[0];
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("billNo", no);
		//查询结算单
		BillBalance balance=new BillBalance();
		List<BillBalance> list=hqOtherStockOutBalanceManager.findByBiz(null, params);
		balance=list.get(0);
		Integer statusNo=balance.getStatus();
		balance.setStatusName(AreaOtherOutBalanceStatusEnums.getTypeName(statusNo));
		//保存操作日志
		operateLogHelper.addVerifyPost(req, OperateModule.JSD.getModuleNo(),OperStatusName,OperStatusName);
		return new ResponseEntity<BillBalance>(balance, HttpStatus.OK);
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
		String formData = req.getParameter("formData");
		String brandNames=req.getParameter("brandName");
//		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
//		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		String balanceNo = StringUtils.isEmpty(req.getParameter("balanceNo")) ? "": String.valueOf(req.getParameter("balanceNo"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceNo", balanceNo);//结算单编号
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1: Integer.parseInt(req.getParameter("page"));
//		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10: Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "": String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "": String.valueOf(req.getParameter("order"));
		int total = this.areaOtherStockInEntryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillSaleBalance> dataList = this.areaOtherStockInEntryManager.findByPage(page,sortColumn, sortOrder, params);
		HqOtherStockOutExportUtils.doExport(formData, fileName, brandNames, exportColumns, dataList, response,0);
	}
	
	/**
	 * 列表导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/list_export")
	public void listExport(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.hqOtherStockOutBalanceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillSaleBalance> dataList = this.hqOtherStockOutBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		HqOtherStockOutExportUtils.doExport(null, fileName, null, exportColumns, dataList, response, 1);
	}
	
	/**
	 * 明细导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/dtl_export")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dtlExport(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		String billNo = req.getParameter("billNos");
		String[] billNos=billNo.split(",");
		Map<String, Object> params = builderParams(req, model);
		List<Map> dataList=new ArrayList<Map>();
		for(String no:billNos){
			BillBalance billBalance=new BillBalance();
			Map<String, Object> map = new HashMap<String, Object>();
			params.clear();
			params.put("billNo", no);//结算单编号
			billBalance=(BillBalance) hqOtherStockOutBalanceManager.findByBiz(model, params).get(0);
			params.clear();
			params.put("balanceNo", no);
			int total = this.areaOtherStockInEntryManager.findCount(params);
			SimplePage page = new SimplePage(0, total, (int) total);
			List dtlList = this.areaOtherStockInEntryManager.findByPage(page,"", "", params);
			List<Map> dtlMapList = HqInsteadOfBuyExportUtils.getDataList(dtlList);
			map.put("bill", billBalance);
			map.put("dtlList", dtlMapList);
			dataList.add(map);
			
		}
		HqOtherStockOutExportUtils.doFasExport(fileName, exportColumns, dataList,"应付", response);
	}
	
}
