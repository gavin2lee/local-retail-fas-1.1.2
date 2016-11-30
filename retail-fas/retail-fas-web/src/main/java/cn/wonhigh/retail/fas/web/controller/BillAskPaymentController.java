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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.SettleTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BillAskPaymentDtlManager;
import cn.wonhigh.retail.fas.manager.BillAskPaymentManager;
import cn.wonhigh.retail.fas.manager.BillBalanceManager;
import cn.wonhigh.retail.fas.manager.CurrencyManagementManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/bill_ask_payment")
public class BillAskPaymentController extends BaseCrudController<BillAskPayment> {
    @Resource
    private BillAskPaymentManager billAskPaymentManager;
   
    @Resource
    private BillAskPaymentDtlManager billAskPaymentdtlManager;
    
    @Resource
    private BillBalanceController billBalanceController;
    
	@Resource
	private FinancialAccountManager financialAccountManager;
	
    @Resource
    private BillBalanceManager billBalanceManager;
    
    @Resource
    private CurrencyManagementManager currencyManagementManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_ask_payment/",billAskPaymentManager);
    }
    
    @RequestMapping("/to_ref")
    public String refTab(){
    	return "/bill_ask_payment/list_ref";
    }
	
    @RequestMapping("/to_dtl")
    public String dtlTab(){
    	return "/bill_ask_payment/list_dtl";
    }
    
    @RequestMapping("/list_tabMain")
    public String listTab(){
    	return "/bill_ask_payment/list_tabMain";
    }
    
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
		String isHQ = req.getParameter("isHQ");
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
    	String billNoMenu = req.getParameter("billNoMenu");
    	if(StringUtils.isNotBlank(billNoMenu)){
    		mav.addObject("billNoMenu", billNoMenu);
    	}
    	String warnPostUrl = req.getParameter("warnPostUrl");
    	if(StringUtils.isNotBlank(warnPostUrl)){
    		mav.addObject("warnPostUrl", warnPostUrl);
    	}
		//过滤承担总部职能的结算公司
		if(("true").equals(isHQ)){
				mav.addObject("queryCondition", "AND buyer_no IN ("+companyNos +")");
				mav.addObject("fliterCompany", "notGroupLeadRole");
				mav.addObject("isHQ", "true");
		}else{
			if(StringUtils.isNotBlank(companyNos)){
				mav.addObject("queryCondition", "AND buyer_no NOT IN ("+companyNos +")");
			}
			mav.addObject("fliterCompany", "groupLeadRole");
		}
		mav.setViewName("bill_ask_payment/list_bak");
		return mav;
    }
    	
    /**
     * 源单信息
     */
	@RequestMapping(value = "/ref_list")
	@ResponseBody
	public  Map<String, Object>  refInfo(HttpServletRequest req, Model model) throws ManagerException {
		return billBalanceController.queryList(req, model);
	}
	
    /**
     * 新增
     */
	@RequestMapping(value = "/post")
	public ResponseEntity<BillAskPayment> add(BillAskPayment obj) throws ManagerException {
		billAskPaymentManager.add(obj);
		obj = billAskPaymentManager.findById(obj);
		return new ResponseEntity<BillAskPayment>(obj, HttpStatus.OK);
	}
	
    /**
     * 修改
     */
	@RequestMapping(value = "/put")
	public ResponseEntity<BillAskPayment> moditfy(BillAskPayment obj) throws ManagerException {
		billAskPaymentManager.modifyById(obj);
		obj = billAskPaymentManager.findById(obj);
		return new ResponseEntity<BillAskPayment>(obj, HttpStatus.OK);
	}
	
    /**
     * 审核
     */
	@ResponseBody
	@RequestMapping(value = "/verify")
	public BillAskPayment  verify(HttpServletRequest req, BillAskPayment obj) throws ManagerException {
		if(StringUtils.isBlank(obj.getBillNo()) || obj.getStatus() == null){
			return null;
		}
		billAskPaymentManager.verify(obj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", obj.getBillNo());
		List<BillAskPayment> lstItem = billAskPaymentManager.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(lstItem)){
			return lstItem.get(0);
		}
		return null;
	}
	
	/**
	 * 根据结算单生成请款单
	 * @param req
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ManagerException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/generate_bill_by_balance")
	public ResponseEntity<Integer> batchGenerateBybalance(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException,
		ManagerException{
		int iCount = 0 ;
		String checkList = StringUtils.isEmpty(req.getParameter("checkRows")) ? "" : req.getParameter("checkRows");
		String loginName = CurrentUser.getCurrentUser().getUsername();
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(checkList)) {
			List<Map> list = mapper.readValue(checkList, new TypeReference<List<Map>>(){});
			List<BillBalance> oList=(List<BillBalance>)convertListWithTypeReference(mapper,list);
			iCount = billAskPaymentManager.generateBillBybalance(oList, loginName);
		}
		return new ResponseEntity<Integer>(iCount, HttpStatus.OK);
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
	@SuppressWarnings("rawtypes")
	private List<BillBalance> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		List<BillBalance> tl=new ArrayList<BillBalance>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillBalance type=mapper.readValue(mapper.writeValueAsString(list.get(i)),BillBalance.class);
			tl.add(type);
		}
		return tl;
	}

    /**
     * 查询结算单
     */
	@RequestMapping(value = "/search_ref")
	@ResponseBody
	public  Map<String, Object>  searchRef(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = 0 ;
		List<BillBalance> list = new ArrayList<BillBalance>();
		if(StringUtils.isNotBlank(req.getParameter("salerNo")) && StringUtils.isNotBlank(req.getParameter("buyerNo"))){
			String isHQ = req.getParameter("isHQ");
			if(StringUtils.isNotBlank(isHQ) && "true".equals(isHQ)){
				params.put("queryCondition", "AND (ask_payment_no IS NULL OR ask_payment_no = '') AND ((balance_type = 17 AND status = 1) OR (balance_type = 1 AND status = 2) OR (balance_type = 11 AND status IN (4,6)))");
			}else{
				params.put("queryCondition", "AND (ask_payment_no IS NULL OR ask_payment_no = '') AND ((balance_type IN (6,13) AND status = 2 ) OR (balance_type IN (2,5,14) AND status IN (4,6) ))");
			}
			total = billBalanceManager.findCount(params);
			if(total > 0){
				SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
				list = billBalanceManager.findByPage(page, sortColumn, sortOrder, params);
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = billAskPaymentManager.findByPage(page, "", "", params);
		Map<String, String> currencyInfo = this.getCurrencyInfo();
		dataList = billAskPaymentManager.setTargetCurencyPropertites(dataList,currencyInfo);
		List footerList = billAskPaymentManager.selectFooter(params);
		dataList.addAll(footerList);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}

	/**
	 * 根据结算单号 查请款信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/query_balance_ask_payment")
	@ResponseBody
	public Map<String, Object> queryListByBalanceNo(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<BillAskPayment> lstAskPayment = new ArrayList<BillAskPayment>();
		if(StringUtils.isNotBlank(req.getParameter("balanceNo"))){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceNo", req.getParameter("balanceNo"));
			lstAskPayment = billAskPaymentManager.findByBiz(null, params);
		}
		resultMap.put("total", 1);
		resultMap.put("rows", lstAskPayment);
		return resultMap;
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billAskPaymentManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillAskPayment> list = new ArrayList<BillAskPayment>();
		List<BillAskPayment> footerList = new ArrayList<BillAskPayment>();
		if(total > 0){
			list = billAskPaymentManager.findByPage(page, sortColumn, sortOrder, params);
			Map<String, String> currencyInfo = this.getCurrencyInfo();
			list = billAskPaymentManager.setTargetCurencyPropertites(list,currencyInfo);
			footerList = billAskPaymentManager.selectFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}
	
	private Map<String, String> getCurrencyInfo() throws ManagerException {
		Map<String, String> params = new HashMap<String, String>();
		List<CurrencyManagement> list = currencyManagementManager.findByBiz(null, null);
    	for(CurrencyManagement currencyManagement:list){
    		params.put(currencyManagement.getCurrencyCode(), currencyManagement.getCurrencyName());
    	}
		return params;
	}

	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_bill")
	public void exportBill(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String billNo = req.getParameter("billNo");
		String fileName = req.getParameter("fileName");
		String exportFields = req.getParameter("exportFields");
		String exportColumns = req.getParameter("exportColumns");
		if(StringUtils.isNotBlank(billNo)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billNo);
			List<Object> lstItem = billAskPaymentManager.findByBiz(null, params);
			if(lstItem.size() > 0){
				Map<String, String> currencyInfo = this.getCurrencyInfo();
				BillAskPayment obj = (BillAskPayment)lstItem.get(0);
				obj.setCurrencyNo(currencyInfo.get(obj.getCurrencyNo()));
				List<Object> dtlList = billAskPaymentdtlManager.findByBiz(null, params);
				for (Object object : dtlList) {
					BillAskPaymentDtl dtl = (BillAskPaymentDtl)object;
					dtl.setSettleMethodNo(SettleTypeEnums.getNameByNo(Integer.parseInt(dtl.getSettleMethodNo())));
				}
				List<Map> columnList =  ExportUtils.getColumnList(exportColumns);
				List<Map> dataMapList = ExportUtils.getDataList(dtlList);
				List<Map> fieldList =  ExportUtils.getColumnList(exportFields);
				List<Map> mainMapList = ExportUtils.getDataList(lstItem);
				ExportUtils.ExportBillData(fileName, fieldList, mainMapList, columnList, dataMapList, response);
			}
		}
	}
	
}