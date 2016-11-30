/**
 * title:ApplyForPaymentController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:体总请款单
 * auther:user
 * date:2016-4-20 上午10:44:05
 */
package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.TsAskPaymentStatusEnums;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BillAskPaymentManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.PhysicalBrandManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;


@Controller
@RequestMapping("/apply_for_payment")
@ModuleVerify("40001013")
public class ApplyForPaymentController extends BaseCrudController<BillAskPayment> {
	private Logger logger = Logger.getLogger(ApplyForPaymentController.class);

	@Resource
	private PhysicalBrandManager physicalBrandManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Resource
	private BillAskPaymentManager BillAskPaymentManager;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("top_sports/apply_for_payment/", physicalBrandManager);
	}
	
	/**
	 * 单据列表
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public String mainTab() {
		return "top_sports/apply_for_payment/list_tabMain";
	}
	
	/**
	 * 单据明细tab页
	 * @return
	 */
	@RequestMapping(value = "/billDtlInfo")
	public String toTransferOutList() {
		return "top_sports/apply_for_payment/billDtl";
	}
	
	/**
	 * 结算单明细tab页
	 * @return
	 */
	@RequestMapping(value = "/balanceInfo")
	public String toItemDataInfo() {
		return "top_sports/apply_for_payment/balanceDtl";
	}
	
	/**
	 * 单据状态
	 * @return
	 */
	@RequestMapping("/get_bill_status")
	@ResponseBody
	public List<Map<String, String>> getAuditStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		TsAskPaymentStatusEnums[] enumData = TsAskPaymentStatusEnums.values();
		for (TsAskPaymentStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getValue().toString());
			map.put("statusName", x.getText());
			list.add(map);
		}
		return list;
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
		int total = this.BillAskPaymentManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillAskPayment> list = this.BillAskPaymentManager.findByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 根据结算单号查询请款单明细
	 * @param req
	 * @param model
	 * @return
	 */
    @RequestMapping("/getBillDtl")
	@ResponseBody
	public List<BillAskPaymentDtl> getBillAskPaymentDtls(HttpServletRequest req, BillAskPayment model){
		List<BillAskPaymentDtl> list=physicalBrandManager.findBillAskPaymentDtl(model);
		return list;
	}
	
	/**
	 * 保存主表信息
	 * @param req
	 * @param bill
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/save_bill")
	@ResponseBody
	public Map<String,Object> saveInfo(HttpServletRequest req,BillAskPayment bill) throws Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		Map<String,Object> params=new HashMap<>();
		if(bill!=null){
			bill.setCreateUser(loginUser.getUsername());
			bill.setCreateTime(DateUtil.getCurrentDateTime());
			bill.setUpdateTime(DateUtil.getCurrentDateTime());
			bill=BillAskPaymentManager.addMainForm(bill);
		}
		if(bill!=null){
			params.put("success", true);
			params.put("bill", bill);
		}else{
			params.put("success", false);
		}
		return params;
	}
	
	/**
	 * 修改
	 * @param req
	 * @param bill
	 * @return
	 * @throws ManagerException
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/update_bill")
	@ResponseBody
	public Map<String,Object> updateInfo(HttpServletRequest req,BillAskPayment bill) throws ManagerException, ServiceException {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		Map<String,Object> params=new HashMap<>();
		int i=0;
		if(bill!=null){
			bill.setUpdateUser(loginUser.getUsername());
			try {
				bill.setUpdateTime(DateUtil.getCurrentDateTime());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			i=BillAskPaymentManager.modifyById(bill);
		}
		if(i>0){
			params.put("success", true);
			params.put("bill", bill);
		}else{
			params.put("success", false);
		}
		return params;
	}
	
	/**
	 * 单个审核/反审核操作
	 * @return
	 * @throws ManagerException 
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public ResponseEntity<BillAskPayment> auditConfirm(HttpServletRequest req,@RequestParam("billNo") String[] billNo,@RequestParam("status")String status) throws ManagerException {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditor", loginUser.getUsername());
		try {
			map.put("auditTime", DateUtil.getCurrentDateTime());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		map.put("status", status);
		boolean flag=false;
		if(billNo!=null){
		  	int i=physicalBrandManager.barchAuditBills(billNo,map);
		  	if(i>0){
		  		flag=true;
		  	}
		}
		BillAskPayment bill=null;
		if(flag){
			String no=billNo[0];
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("billNo", no);
			bill=new BillAskPayment();
			List<BillAskPayment> list=BillAskPaymentManager.findByBiz(null, params);
			bill=list.get(0);
			Integer statusNo=bill.getStatus();
			bill.setStatusName(TsAskPaymentStatusEnums.getTypeName(statusNo));
		}
		return new ResponseEntity<BillAskPayment>(bill, HttpStatus.OK);
	}
	
	/**
	 * 批量审核
	 * @param billNos
	 * @return
	 */
	@RequestMapping("/batch_audit")
	@ResponseBody
	public boolean batchAudit(HttpServletRequest req,@Param("billNos")String[] billNos,@RequestParam("status")String status){
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("auditor", loginUser.getUsername());
		params.put("status", status);
		try {
			params.put("auditTime", DateUtil.getCurrentDateTime());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		boolean flag=false;
		if(billNos!=null){
		  	int i=physicalBrandManager.barchAuditBills(billNos,params);
		  	if(i>0){
		  		flag=true;
		  	}
		}
		return flag;
	}
	
	/**
	 * 删除请款单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete_bill")
	@ResponseBody
	public Integer removeBill(@RequestParam("idList")String[] ids) throws Exception{
		return  physicalBrandManager.barchDelBills(ids);
	}
	
	/**
	 * 批量删除
	 * @param billNos
	 * @return
	 */
	@RequestMapping("/batch_del")
	@ResponseBody
	public boolean batchDel(@Param("billNos")String[] billNos){
		boolean flag=false;
		if(billNos!=null){
		  	int i=physicalBrandManager.barchDelBills(billNos);
		  	if(i>0){
		  		flag=true;
		  	}
		}
		return flag;
	}
	
 }
