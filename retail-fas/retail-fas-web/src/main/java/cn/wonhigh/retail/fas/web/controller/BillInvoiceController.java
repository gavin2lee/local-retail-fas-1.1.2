package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.BillInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillBalanceManager;
import cn.wonhigh.retail.fas.manager.BillInvoiceManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

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
@RequestMapping("/bill_invoice")
public class BillInvoiceController extends BaseCrudController<BillInvoice> {
    @Resource
    private BillInvoiceManager billInvoiceManager;
    
    @Resource
    private CommonUtilController commonUtilController;
    
    @Resource
    private BillBalanceManager billBalanceManager;
    
	@Resource
	private FinancialAccountManager financialAccountManager;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_invoice/",billInvoiceManager);
    }
    @RequestMapping("/list_tabMain")
    public String listTab(){
    	return "/bill_invoice/list_tabMain";
    }
    
    @RequestMapping("/to_ref")
    public String refTab(){
    	return "/bill_invoice/list_ref";
    }
	
    @RequestMapping("/to_dtl")
    public String dtlTab(){
    	return "/bill_invoice/list_dtl";
    }
    
    /**
     * 新增
     */
	@RequestMapping(value = "/post")
	public ResponseEntity<BillInvoice> add(BillInvoice obj) throws ManagerException {
		super.add(obj);
		return super.get(obj);
	}
	
	
    /**
     * 修改
     */
	@RequestMapping(value = "/put")
	public ResponseEntity<BillInvoice> moditfy(BillInvoice obj) throws ManagerException {
		super.moditfy(obj);
		return super.get(obj);
	}
	
    /**
     * 审核
     */
	@ResponseBody
	@RequestMapping(value = "/verify")
	public BillInvoice verify(BillInvoice obj) throws ManagerException {
		if(StringUtils.isBlank(obj.getBillNo()) || obj.getStatus() == null){
			return null;
		}
		billInvoiceManager.verify(obj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", obj.getBillNo());
		List<BillInvoice> lstItem = billInvoiceManager.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(lstItem)){
			return lstItem.get(0);
		}
		return null;
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
		mav.setViewName("bill_invoice/list_bak");
		return mav;
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
				params.put("queryCondition", "AND (invoice_no IS NULL OR invoice_no = '') AND ((balance_type IN (16,17) AND status IN (1,5)) OR (balance_type =1 AND status IN (2,5) ) OR (balance_type = 11 AND status IN (4,6) )) ");
			}else{
				params.put("queryCondition", "AND (invoice_no IS NULL OR invoice_no = '') AND ((balance_type IN (6,13) AND status  IN (2,5) ) OR (balance_type IN (2,5,14) AND status IN (4,6) )) ");
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
     * 源单信息
     */
	@RequestMapping(value = "/ref_list")
	@ResponseBody
	public  Map<String, Object>  refInfo(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = new HashMap<String, Object>();
		int total = 0 ;
		List<BillBalance> list = new ArrayList<BillBalance>();
		if(StringUtils.isNotBlank(req.getParameter("billNo"))){
			params.put("multiBillNo", FasUtil.formatInQueryCondition(req.getParameter("billNo")));
			total = billBalanceManager.findCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = billBalanceManager.findByPage(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String[] fieldNames= new String[]{"invoiceNo","invoiceCode","invoiceDate","invoiceAmount","taxRate","qty","categoryNo"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "invoiceNo", "","发票号", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "invoiceCode", "","发票编码",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "invoiceDate", "","发票日期", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "invoiceAmount", "","发票金额", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "taxRate", "","税率", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "qty", "","数量", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.FINANCIAL_CATEGORY.getTypeNo(), "categoryNo", "categoryName","财务大类编码", false));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), BillInvoiceDtl.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, BillInvoiceDtl.class);
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				BillInvoiceDtl dtl = (BillInvoiceDtl)resultVo.getValidateObj();
				BigDecimal invoiceAmount = dtl.getInvoiceAmount();
				BigDecimal taxRate = dtl.getTaxRate();
				dtl.setNoTaxAmount(invoiceAmount.divide(taxRate.add(new BigDecimal(1)), 8 ,BigDecimal.ROUND_HALF_UP));
				dtl.setTaxAmount(invoiceAmount.subtract(dtl.getNoTaxAmount()));	
				dtl.setPrice(invoiceAmount.divide(new BigDecimal(dtl.getQty()), 2 ,BigDecimal.ROUND_HALF_UP));
			}
			
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
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
		List dataList = billInvoiceManager.findByPage(page, "", "", params);
		Map<String, String> currencyInfo = this.getCurrencyInfo();
		dataList = billInvoiceManager.setTargetCurencyPropertites(dataList,currencyInfo);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	/**
	 * 根据结算单获取采购发票信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/query_balance_invoice")
	@ResponseBody
	public Map<String, Object> queryListByBalanceNo(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<BillInvoice> lstInvoice = new ArrayList<BillInvoice>();
		if(StringUtils.isNotBlank(req.getParameter("balanceNo"))){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", req.getParameter("balanceNo"));
			List<BillBalance> lstBalance = billBalanceManager.findByBiz(null, params);
			if(!CollectionUtils.isEmpty(lstBalance)){
				if(StringUtils.isNotBlank(lstBalance.get(0).getInvoiceNo())){
					params.put("billNo", lstBalance.get(0).getInvoiceNo());
					lstInvoice = billInvoiceManager.findByBiz(null, params);
				}
			}
		}
		resultMap.put("total", 1);
		resultMap.put("rows", lstInvoice);
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
		int total = this.billInvoiceManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillInvoice> list = new ArrayList<BillInvoice>();
		List<BillInvoice> footerList = new ArrayList<BillInvoice>();
		if(total > 0){
			list = billInvoiceManager.findByPage(page, sortColumn, sortOrder, params);
			Map<String, String> currencyInfo = this.getCurrencyInfo();
			list = billInvoiceManager.setTargetCurencyPropertites(list,currencyInfo);
			footerList = billInvoiceManager.selectFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}
	private Map<String, String> getCurrencyInfo() throws ManagerException {
		Map<String, String> params = new HashMap<String, String>();
    	List<LookupVo> voList =commonUtilController.getTSCurrency();
    	for(LookupVo vo:voList){
    		params.put(vo.getCode(), vo.getName());
    	}
		return params;
	}
	
}