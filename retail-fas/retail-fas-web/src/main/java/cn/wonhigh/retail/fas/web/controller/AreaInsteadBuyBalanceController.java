/**
 * title:AreaInsteadOfBuyBalanceController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:总部代采地区查询
 * auther:user
 * date:2015-4-3 下午3:17:51
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

import cn.wonhigh.retail.fas.common.enums.HqInsteadBuyBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums.OperateModule;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.AreaPrivatePurchaseBalanceManager;
import cn.wonhigh.retail.fas.manager.HqInsteadOfBuyBalanceManager;
import cn.wonhigh.retail.fas.manager.HqInsteadOfBuyManager;
import cn.wonhigh.retail.fas.web.utils.HqInsteadOfBuyExportUtils;
import cn.wonhigh.retail.fas.web.utils.OperateLogHelper;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/area_insteadOf_buy_balance")
@ModuleVerify("30161003")
public class AreaInsteadBuyBalanceController extends BaseCrudController<BillBalance>{
	@Resource
	private HqInsteadOfBuyManager hqInsteadOfBuyManager;
	@Resource
	private AreaPrivatePurchaseBalanceManager areaPrivatePurchaseBalanceManager;
	@Resource
	private HqInsteadOfBuyBalanceManager hqInsteadOfBuyBalanceManager;
	@Resource
	private OperateLogHelper operateLogHelper;

	@Override
	public CrudInfo init() {
		return new CrudInfo("area_insteadOf_buy_balance/", areaPrivatePurchaseBalanceManager);
	}
	
	/**
	 * 单据明细
	 * 方法改造,便于页面超链接跳转传参
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
		mav.setViewName("hq_insteadBuy_balance/area_query/list");
		return mav;
	}

	/**
	 * 单据列表
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public String mainTab() {
		return "hq_insteadBuy_balance/area_query/list_tabMain";
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
		String balanceType = StringUtils.isEmpty(req.getParameter("balanceType")) ? "" : String.valueOf(req.getParameter("balanceType"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceType", balanceType);
		List<BillBalance> totalList = hqInsteadOfBuyBalanceManager.findTotalRow(params);
		if (totalList.size() >0) {
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 *  批量业务、财务、打回操作
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
		String operStatusName = HqInsteadBuyBalanceStatusEnums.getTypeName(Integer.parseInt(operStatus));
		map.put("statusName", operStatusName);
		map.put("status", operStatus);
		map.put("auditor", loginUser.getUsername());
		map.put("auditTime", DateUtil.getCurrentDateTime());
		hqInsteadOfBuyBalanceManager.modifyBillStatus(ids,map);
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
	public ResponseEntity<BillBalance> AuditConfirm(HttpServletRequest req,@RequestParam("billNo") String[] billNo,@RequestParam("operStatus")String operStatus) throws Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		Map<String, Object> map = new HashMap<String, Object>();
		String operstatusName = HqInsteadBuyBalanceStatusEnums.getTypeName(Integer.parseInt(operStatus));
		map.put("statusName", operstatusName);
		map.put("status", operStatus);
		map.put("auditor", loginUser.getUsername());
		map.put("auditTime", DateUtil.getCurrentDateTime());
		hqInsteadOfBuyBalanceManager.modifyBillStatus(billNo,map);
		operateLogHelper.addVerifyPost(req, OperateModule.JSD.getModuleNo(),operstatusName,operstatusName);
		String no=billNo[0];
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("billNo", no);
		BillBalance balance=new BillBalance();
		List<BillBalance> list=hqInsteadOfBuyBalanceManager.findByBiz(null, params);
		balance=list.get(0);
		Integer statusNo=balance.getStatus();
		balance.setStatusName(HqInsteadBuyBalanceStatusEnums.getTypeName(statusNo));
		return new ResponseEntity<BillBalance>(balance, HttpStatus.OK);
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
		int total = this.hqInsteadOfBuyBalanceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillSaleBalance> dataList = this.hqInsteadOfBuyBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		HqInsteadOfBuyExportUtils.doExport(null,fileName, exportColumns, dataList, response,1);
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
			billBalance=(BillBalance) hqInsteadOfBuyBalanceManager.findByBiz(model, params).get(0);
			params.clear();
			params.put("balanceNo", no);
			int total = this.hqInsteadOfBuyManager.findCount(params);
			SimplePage page = new SimplePage(0, total, (int) total);
			List dtlList = this.hqInsteadOfBuyManager.findByPage(page,"", "", params);
			List<Map> dtlMapList = HqInsteadOfBuyExportUtils.getDataList(dtlList);
			map.put("bill", billBalance);
			map.put("dtlList", dtlMapList);
			dataList.add(map);
			
		}
		HqInsteadOfBuyExportUtils.doFasExport(fileName, exportColumns, dataList, response);
	}
}
