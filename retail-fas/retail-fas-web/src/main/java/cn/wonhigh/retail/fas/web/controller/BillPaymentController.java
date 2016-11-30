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

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.BillPayment;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.manager.BillInvoiceManager;
import cn.wonhigh.retail.fas.manager.BillPaymentManager;
import cn.wonhigh.retail.fas.manager.CurrencyManagementManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
@RequestMapping("/bill_payment")
public class BillPaymentController extends BaseCrudController<BillPayment> {
    @Resource
    private BillPaymentManager billPaymentManager;

    @Resource
    private BillInvoiceController billInvoiceController;
    
	@Resource
	private FinancialAccountManager financialAccountManager;
	
    @Resource
    private BillInvoiceManager billInvoiceManager;
    
    @Resource
    private CurrencyManagementManager currencyManagementManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_payment/",billPaymentManager);
    }
    
    @RequestMapping("/list_tabMain")
    public String listTab(){
    	return "/bill_payment/list_tabMain";
    }
    
    @RequestMapping("/to_ref")
    public String refTab(){
    	return "/bill_payment/list_ref";
    }
	
    @RequestMapping("/to_dtl")
    public String dtlTab(){
    	return "/bill_payment/list_dtl";
    }
    
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHQ");
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
		if(("true").equals(isHq)){
				mav.addObject("queryCondition", "AND buyer_no IN ("+companyNos +")");
				mav.addObject("fliterCompany", "notGroupLeadRole");
				mav.addObject("isHQ", "true");
		}else{
			if(StringUtils.isNotBlank(companyNos)){
				mav.addObject("queryCondition", "AND buyer_no NOT IN ("+companyNos +")");
			}
			mav.addObject("fliterCompany", "groupLeadRole");
		}
		mav.setViewName("bill_payment/list_bak");
		return mav;
    }
    
    /**
     * 新增
     */
	@RequestMapping(value = "/post")
	public ResponseEntity<BillPayment> add(BillPayment obj) throws ManagerException {
		super.add(obj);
		return super.get(obj);
	}
	
	
    /**
     * 修改
     */
	@RequestMapping(value = "/put")
	public ResponseEntity<BillPayment> moditfy(BillPayment obj) throws ManagerException {
		super.moditfy(obj);
		return super.get(obj);
	}
	
    /**
     * 审核
     */
	@ResponseBody
	@RequestMapping(value = "/verify")
	public BillPayment verify(HttpServletRequest req, BillPayment obj) throws ManagerException {
		if(StringUtils.isBlank(obj.getBillNo()) || obj.getStatus() == null){
			return null;
		}
		billPaymentManager.verify(obj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", obj.getBillNo());
		List<BillPayment> lstItem = billPaymentManager.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(lstItem)){
			return lstItem.get(0);
		}
		return null;
	}
	
	/**
	 * 根据发票生成付款单
	 * @param req
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ManagerException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/generate_by_invoice")
	public ResponseEntity<Integer> generateByInvoice(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException,
		ManagerException{
		int iCount = 0 ;
		String checkList = StringUtils.isEmpty(req.getParameter("checkRows")) ? "" : req.getParameter("checkRows");
		String loginName = CurrentUser.getCurrentUser(req).getUsername();
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(checkList)) {
			List<Map> list = mapper.readValue(checkList, new TypeReference<List<Map>>(){});
			List<BillInvoice> oList=(List<BillInvoice>)convertListWithTypeReference(mapper,list);
			iCount = billPaymentManager.generateByInvoice(oList, loginName);
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
	private List<BillInvoice> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		List<BillInvoice> tl=new ArrayList<BillInvoice>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillInvoice type=mapper.readValue(mapper.writeValueAsString(list.get(i)),BillInvoice.class);
			tl.add(type);
		}
		return tl;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = billPaymentManager.findByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billPaymentManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillPayment> list = new ArrayList<BillPayment>();
		List<BillPayment> footerList = new ArrayList<BillPayment>();
		if(total > 0){
			list = billPaymentManager.findByPage(page, sortColumn, sortOrder, params);
			Map<String, String> currencyInfo = this.getCurrencyInfo();
			list = billPaymentManager.setTargetCurencyPropertites(list,currencyInfo);
			footerList = billPaymentManager.selectFooter(params);
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
     * 源单信息
     */
	@RequestMapping(value = "/ref_list")
	@ResponseBody
	public  Map<String, Object>  refInfo(HttpServletRequest req, Model model) throws ManagerException {
		return billInvoiceController.queryList(req, model);
	}
	
    /**
     * 查询关联单据
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
			String companyNos=financialAccountManager.findLeadRoleCompanyNos();
			if(StringUtils.isNotBlank(isHQ) && "true".equals(isHQ)){
				params.put("queryCondition", "AND status in (1,2) AND buyer_no in ("+companyNos +")");
			}else{
				params.put("queryCondition", "AND status in (1,2) AND buyer_no not in ("+companyNos +")");
			}
			total = billInvoiceManager.findCount(params);
			if(total > 0){
				SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
				list = billInvoiceManager.findByPage(page, sortColumn, sortOrder, params);
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}	
}