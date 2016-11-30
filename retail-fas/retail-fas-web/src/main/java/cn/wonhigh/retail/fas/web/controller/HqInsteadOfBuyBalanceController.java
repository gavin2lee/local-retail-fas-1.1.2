/**  
* 项目名称：retail-fas-web  
* 类名称：HqInsteadOfBuyBalanceController  
* 类描述：总部代采结算
* 创建人：ouyang.zm  
* 创建时间：2015-1-22 下午5:22:14  
* @version       
*/ 
package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.HqInsteadBuyBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums.OperateModule;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
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
@RequestMapping("/hq_insteadOf_buy_balance")
@ModuleVerify("30520015")
public class HqInsteadOfBuyBalanceController extends BaseCrudController<BillBalance>{
	@Resource
	private HqInsteadOfBuyManager hqInsteadOfBuyManager;
	@Resource
	private HqInsteadOfBuyBalanceManager hqInsteadOfBuyBalanceManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Resource
	private OperateLogHelper operateLogHelper;

	@Override
	public CrudInfo init() {
		return new CrudInfo("hq_insteadOf_buy_balance/", hqInsteadOfBuyBalanceManager);
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
		mav.setViewName("hq_insteadBuy_balance/hq_confirm/list");
		return mav;
	}

	/**
	 * 单据列表
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public String mainTab() {
		return "hq_insteadBuy_balance/hq_confirm/list_tabMain";
	}

	/**
	 * 入库明细tab页
	 * @return
	 */
	@RequestMapping(value = "/transferInTab")
	public String toStorageList() {
		return "hq_insteadBuy_balance/hq_confirm/storage_dtl";
	}
	
	/**
	 * 退残明细tab页
	 * @return
	 */
	@RequestMapping(value = "/returnInfo")
	public String toReturnList() {
		return "hq_insteadBuy_balance/hq_confirm/return_dtl";
	}

	/**
	 * 请款款信息tab页
	 * @return
	 */
	@RequestMapping(value = "/paymentInfo")
	public String toPaymentInfoList() {
		return "hq_insteadBuy_balance/hq_confirm/payment_info";

	}

	/**
	 * 其他扣项tab页
	 * @return
	 */
	@RequestMapping(value = "/otherDeduction")
	public String toOtherDeduction() {
		return "hq_insteadBuy_balance/hq_confirm/deduction_info";

	}

	/**
	 * 发票信息tab页
	 * @return
	 */
	@RequestMapping(value = "/invoiceInfo")
	public String toInvoiceInfoList() {
		return "hq_insteadBuy_balance/hq_confirm/invoice_info";

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
	 * 判断能否生成结算单
	 * @throws ManagerException 
	 * @throws Exception 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("check_data")
	@ResponseBody
	public Map<String,Object> checkBillData(HttpServletRequest req,BillBalance formData) throws ManagerException, JsonParseException, JsonMappingException, Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("salerNoIn", FasUtil.formatInQueryCondition(formData.getSalerNo()));
		String areaCompanys=financialAccountManager.findNotLeadRoleCompanyNos();
		if(StringUtils.isBlank(formData.getBuyerNo())){
			param.put("buyerNoIn", FasUtil.splitToInStr(areaCompanys));
		}else{
			param.put("buyerNoIn", FasUtil.formatInQueryCondition(formData.getBuyerNo()));
		}
		param.put("sendDateStart", formData.getBalanceStartDate());
		param.put("sendDateEnd", formData.getBalanceEndDate());
		param.put("brandUnitNoIn", FasUtil.formatInQueryCondition(formData.getBrandUnitNo()));
		param.put("balanceFlag", "0");
		param.put("zeroFlag", "true");
		int count = hqInsteadOfBuyManager.findCount(param);//查询单价为0的数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		return map;
	}
	
    /**
     * 生成结算单
     * @throws Exception 
     * @throws IOException 
     * @throws JsonMappingException 
     */
	@RequestMapping(value = "/save_bill")
	@ResponseBody
	public ResponseEntity<BillBalance> add(HttpServletRequest req,BillBalance billBalance)throws JsonMappingException, IOException, Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		billBalance.setCreateUser(loginUser.getUsername());
		billBalance =hqInsteadOfBuyBalanceManager.addHqBalanceBill(billBalance);
		if(null != billBalance){
			operateLogHelper.addVerifyPost(req, billBalance.getBillNo(), OperateModule.JSD.getModuleNo(), new Integer(0),
					"制单", "制单");//添加日志
			billBalance = hqInsteadOfBuyBalanceManager.findById(billBalance);
		}
		return new ResponseEntity<BillBalance>(billBalance, HttpStatus.OK);
	}
	
	/**
	 * 跳转到选单关联界面
	 */
	@RequestMapping("/to_select_bill")
	public ModelAndView toSelectBill(HttpServletRequest req,Model model) {
		String params = StringUtils.isEmpty(req.getParameter("params")) ? "" : String.valueOf(req.getParameter("params"));
		ModelAndView view=new ModelAndView();
		view.setViewName("hq_insteadBuy_balance/hq_confirm//select_bill");
		view.addObject("params", params);
		return view;
	}
	
	/**
     * 生成结算单(选单)
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/select_save_bill")
	@ResponseBody
	public Map<String,Object> addByCreate(HttpServletRequest req,BillBalance billBalance)throws Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		billBalance.setCreateUser(loginUser.getUsername());
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (StringUtils.isNotBlank(req.getParameter("checkedRows"))) {
			List<Map> outList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<Object> lstItem = convertListWithTypeReference(mapper, outList, BillBuyBalance.class);
			resultMap = hqInsteadOfBuyBalanceManager.addHqBalanceBillBySelect(lstItem, billBalance);
		}
		BillBalance bill=(BillBalance) resultMap.get("bill"); 
		if (bill != null) {//添加日志
			operateLogHelper.addVerifyPost(req, bill.getBillNo(), OperateModule.JSD.getModuleNo(), new Integer(0),
					"制单", "制单");
		}
		return resultMap;
	}
	
	/**
	 * 扣项调整
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deduction_adjust")
	@ResponseBody
	public ResponseEntity<BillBalance> deductionAdjust(HttpServletRequest req,BillBalance billBalance)throws Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		billBalance.setCreateUser(loginUser.getUsername());
		ObjectMapper mapper = new ObjectMapper();
		BillBalance b = new BillBalance();
		if (StringUtils.isNotBlank(req.getParameter("checkedRows"))) {
			List<Map> outList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<Object> lstItem = convertListWithTypeReference(mapper, outList, OtherDeduction.class);
			b = hqInsteadOfBuyBalanceManager.modifyHqDeducation(lstItem, billBalance);
			super.moditfy(b);
		}
		if (null != b) {
			b = hqInsteadOfBuyBalanceManager.findById(b);
		}
		return new ResponseEntity<BillBalance>(b, HttpStatus.OK);
	}
	
	/**
     * 批量生成结算单
	 * @throws Exception 
     */
	@RequestMapping(value = "/batch_save_bill")
	@ResponseBody
	public Map<String,Object> batchSave(HttpServletRequest req,BillBalance fromPage)throws ManagerException, Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户
		String salerNo = fromPage.getSalerNo();
		String buyerNo = fromPage.getBuyerNo();
		BillBalance balance = new BillBalance();
		balance.setCreateUser(loginUser.getUsername());
		balance.setSalerNo(salerNo); //卖方
		balance.setBuyerNo(buyerNo);//买方
		balance.setBalanceStartDate(fromPage.getBalanceStartDate());
		balance.setBalanceEndDate(fromPage.getBalanceEndDate());
		balance.setBalanceDate(fromPage.getBalanceDate());
		balance.setBrandUnitNo(fromPage.getBrandUnitNo()); //品牌部
		balance.setBrandUnitName(fromPage.getBrandUnitName());
		Map<String,Object>	resultMap = hqInsteadOfBuyBalanceManager.addHqBalanceBillByBatch(balance);
		if(resultMap.get("flag").toString().equals("true")){
			String dataNo = resultMap.get("billNos").toString()
					.substring(1, resultMap.get("billNos").toString().length() - 1);
			operateLogHelper.addVerifyPost(req, dataNo,OperateModule.JSD.getModuleNo(), new Integer(0),
					"制单", "制单");
		}
		return resultMap;
	}
	
	/**
	 * 修改结算单
	 * @throws Exception 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 */
	@RequestMapping(value = "/update_bill")
	@ResponseBody
	public ResponseEntity<BillBalance> moditfy(HttpServletRequest req,BillBalance billBalance)
			throws JsonMappingException, IOException, Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);//获取登录用户
		billBalance.setUpdateUser(loginUser.getUsername());
		billBalance.setUpdateTime(DateUtil.getCurrentDateTime());
		hqInsteadOfBuyBalanceManager.modifyById(billBalance);
		if(null != billBalance){
			billBalance = hqInsteadOfBuyBalanceManager.findById(billBalance);
		}
		return new ResponseEntity<BillBalance>(billBalance, HttpStatus.OK);
	}
	
	/**
	 * 删除结算单
	 * @return
	 * @throws ManagerException  
	 */
	@RequestMapping(value = "/delete_bill")
	@ResponseBody
	public Integer removeBillBalance(@RequestParam("idList")String[] ids) 
			throws ManagerException {
		return hqInsteadOfBuyBalanceManager.deleteBalanceBill(ids);
	}
	
	/**
	 * 查询入库明细
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/transferInList")
	@ResponseBody
	public Map<String, Object> getBillDtl(HttpServletRequest req,Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1: Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10: Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "": String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "": String.valueOf(req.getParameter("order"));
		String balanceNo = StringUtils.isEmpty(req.getParameter("balanceNo")) ? "": String.valueOf(req.getParameter("balanceNo"));
		String billType = StringUtils.isEmpty(req.getParameter("billType")) ? "": String.valueOf(req.getParameter("billType"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceNo", balanceNo);//结算单编号
		params.put("billType", billType);//单据类型
		int total = this.hqInsteadOfBuyManager.findCount(params);
		SimplePage page =null;
		if(params.get("print")!=null && params.get("print").equals("1")){
			page = new SimplePage(pageNo, (int) total, (int) total);
		}else{
			page = new SimplePage(pageNo, pageSize, (int) total);
		}
		List<BillBuyBalance> list = this.hqInsteadOfBuyManager.findByPage(page,sortColumn, sortOrder, params);
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
		String balanceNo = StringUtils.isEmpty(req.getParameter("balanceNo")) ? "": String.valueOf(req.getParameter("balanceNo"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceNo", balanceNo);//结算单编号
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1: Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "": String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "": String.valueOf(req.getParameter("order"));
		int total = this.hqInsteadOfBuyManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalance> dataList = this.hqInsteadOfBuyManager.findByPage(page,sortColumn, sortOrder, params);
		HqInsteadOfBuyExportUtils.doExport(formData,fileName, exportColumns, dataList, response,0);
	}
	
	/**
	 * 导出数据的校验(制单和打回状态的不能导出)
	 * @return
	 * @throws ManagerException 
	 */
	@RequestMapping(value="/export_check")
	@ResponseBody
	public Map<String, Object> exportCheck(HttpServletRequest req,Model model) throws ManagerException{
		String status = StringUtils.isEmpty(req.getParameter("status")) ? "" : String.valueOf(req.getParameter("status"));
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> map = builderParams(req, model);
		if (StringUtils.isNotBlank(status) && (!status.equals("0") || !status.equals("99"))) {
			params.put("queryCondition", "AND B.STATUS ="+ status);
			map.put("statusFlag", status);
		}else{
			params.put("queryCondition", "AND B.STATUS IN ('0','99')");
			map.put("statusFlag", "0");
		}
		int count=hqInsteadOfBuyBalanceManager.findCount(params);
		Map<String, Object> msg = new HashMap<>();
		if (count > 0 && (map.get("statusFlag").equals("0") || map.get("statusFlag").equals("99"))) {
			msg.put("msg", false);
		} else {
			msg.put("msg", true);
		}
		return msg;
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
	
	/**
	 * @param req
	 * @return
	 * @throws Exception
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, List<Object>> getRowsToMap(HttpServletRequest req) throws Exception, JsonMappingException, IOException{
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotBlank(req.getParameter("entry"))) {
			List<Map> entryList = mapper.readValue(req.getParameter("entry"), new TypeReference<List<Map>>() {
			});
			map.put("entry", convertListWithTypeReference(mapper, entryList, BillBuyBalance.class));
		}
		if (StringUtils.isNotBlank(req.getParameter("deduction"))) {
			List<Map> deductionList = mapper.readValue(req.getParameter("deduction"), new TypeReference<List<Map>>() {
			});
			map.put("deduction", convertListWithTypeReference(mapper, deductionList, OtherDeduction.class));
		}
		return map;
	} 
	
	/**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
}
