package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
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

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PayTermTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.model.PayTermSupplier;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.OtherDeductionManager;
import cn.wonhigh.retail.fas.manager.PayRelationshipManager;
import cn.wonhigh.retail.fas.manager.PayTermSupplierManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 其他扣项 
 * @author wang.m1
 * @date  2014-11-21 14:30:24
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
@RequestMapping("/other_deduction")
public class OtherDeductionController extends BaseCrudController<OtherDeduction> {
	
	protected static final XLogger logger = XLoggerFactory.getXLogger(OtherDeductionController.class);
	
    @Resource
    private OtherDeductionManager otherDeductionManager;

    @Resource
    private CommonUtilController commonUtilController;
    
    @Resource
    private FinancialAccountManager financialAccountManager;
    
    @Resource
    private PayTermSupplierManager payTermSupplierManager;
    
    @Resource
    private BillSaleBalanceManager billSaleBalanceManager;
    
    @Resource
    private PayRelationshipManager payRelationshipManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("other_deduction/",otherDeductionManager);
    }

    @RequestMapping(method = RequestMethod.GET ,value ="/return_list")
    public ModelAndView returnList(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("parentId", req.getParameter("parentId"));
		mav.addObject("salerNo", req.getParameter("salerNo"));
		mav.addObject("buyerNo", req.getParameter("buyerNo"));
		mav.addObject("billType", String.valueOf(BillTypeEnums.SALEOUT.getRequestId()));
		mav.addObject("balanceType", String.valueOf(BalanceTypeEnums.OTHER_DEDUCTION.getTypeNo()));
		mav.addObject("balanceNo", req.getParameter("balanceNo"));
		mav.setViewName("other_deduction/return_list");
		return mav;
    }
    
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/save_return_list")
	@ResponseBody
	public  Map<String, Object> saveReturnList(HttpServletRequest req, Model model) throws Exception {
		String parentId = req.getParameter("parentId");
		String checkRows = req.getParameter("checkedRows");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(parentId)){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(checkRows, new TypeReference<List<Map>>() {});
			List<Object> lstItem =convertListWithTypeReference(mapper, list, BillBuyBalance.class);
			SystemUser user = CurrentUser.getCurrentUser(req);
			otherDeductionManager.saveReturnList(user.getUsername(), parentId, lstItem);
			map.put("success", true);
			return map;
		}
		map.put("success", false);
		return map;
		
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String brandUnitNo = params.get("brandUnitNo")==null ? "" : params.get("brandUnitNo").toString();
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Integer balanceType = params.get("balanceType")==null ? 0 : Integer.valueOf(params.get("balanceType").toString());
		String areaBuyerNo = params.get("multiAreaBuyerNo")==null ? "" : params.get("multiAreaBuyerNo").toString();
		//多个品牌部
		if(brandUnitNo != null && brandUnitNo.indexOf(",") != -1) {
			//批发扣项调整页面 传参格式  BL,MP,
			if(brandUnitNo.lastIndexOf(',')==brandUnitNo.length()-1){
				brandUnitNo = brandUnitNo.substring(0,brandUnitNo.length()-2);
			}
			params.put("multiBrandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			params.put("brandUnitNo", "");
		}
		if(areaBuyerNo != ""){
			 String[] areaBuyerNoArray = FasUtil.parseInQueryCondition(areaBuyerNo).split(",");
			 params.put("areaBuyerNoArray", areaBuyerNoArray);
		}
		if(balanceType == BalanceTypeEnums.AREA_WHOLESALE.getTypeNo()){
			String companyNos = null;
			if(isHq.equals("true")){
				companyNos = financialAccountManager.findLeadRoleCompanyNos();
			}else{
				companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
			}
			if (StringUtils.isNotEmpty(companyNos)) {
				String queryCondition = params.get("queryCondition")==null ? "" : params.get("queryCondition").toString();
				params.put("queryCondition", queryCondition + " AND SALER_NO IN (" + companyNos + ")");
			}
		}
		int total = this.otherDeductionManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<OtherDeduction> list = this.otherDeductionManager.findByPage(page, sortColumn, sortOrder, params);
		List<OtherDeduction> footerList = this.otherDeductionManager.findFooter(params);
		Map<String, String> currencyInfo = this.getCurrencyInfo();
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", otherDeductionManager.setOtherDedutionProperies(list,currencyInfo));
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

	@RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(@RequestParam("balanceType")String balanceType, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.AREA_AMONG.getTypeNo()))) {
			mav.addObject("balanceType", balanceType);
			mav.setViewName("other_deduction/deduction_list");//转到地区间其他扣项
		}else if(StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.AREA_BUY.getTypeNo()))){
			mav.addObject("balanceType", balanceType);
			mav.setViewName("other_deduction/deduct");//转到地区自购其他扣项
		}else if(StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo()))){
			mav.addObject("balanceType", balanceType);
			mav.setViewName("other_deduction/deduct");//转到总部代采其他扣项
		}else if(StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.AREA_WHOLESALE.getTypeNo()))){
			String isHq = request.getParameter("isHq");
			if(StringUtils.isNotBlank(isHq)){
				mav.addObject("isHq", isHq);
			}
			mav.setViewName("other_deduction/wholesale_list");//转到地区批发其他扣项
		}else if(StringUtils.isNotBlank(balanceType)
				&& (Integer.parseInt(balanceType) == (BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()))){
			mav.addObject("balanceType", balanceType);
			mav.setViewName("other_deduction/area_deduction_list");//转到总部地区其他扣项
		}else if(StringUtils.isNotBlank(balanceType) 
				&& Integer.parseInt(balanceType) == (BalanceTypeEnums.PE_SUPPLIER.getTypeNo())){
			mav.addObject("balanceType", balanceType);
			mav.setViewName("other_deduction/sport_list");		//转到体总其他扣项
		}else {
			mav.addObject("balanceType", balanceType);
			mav.setViewName("other_deduction/list");
		}
		//用于区分GA03 和 GA13结算单跳转
		String atoa = request.getParameter("atoa");
		if(StringUtils.isNotBlank(atoa)) {
			mav.addObject("atoa", atoa);
		}
		return mav;
    }
    
    /***
     * 获取到期日
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value ="/getDueDate.json")
    @ResponseBody
    public Map<String, Object> getDueDate(HttpServletRequest req, Model model) throws ManagerException, Exception {
    	
    	Date dueDate = null;
    	Map<String, Object> result = new HashMap<String, Object>();
    	Map<String, Object> params = builderParams(req, model);
    	String salerNo = params.get("salerNo")==null ? "" : params.get("salerNo").toString();
    	String buyerNo = params.get("buyerNo")==null ? "" : params.get("buyerNo").toString();    	
    	String areaBuyerNo = params.get("areaBuyerNo")==null ? "" : params.get("areaBuyerNo").toString();
    	String dueDateStr = params.get("dueDate")==null ? "" : params.get("dueDate").toString();
    	Date deductionDate = params.get("deductionDate")==null ? null : DateUtil.getdate(params.get("deductionDate").toString());
    	SimplePage page = null;
    	if(dueDateStr.equals("")) {
    		//获取地区公司当月第一笔到货单的到期日
    		params.clear();
    		if(StringUtils.isNotBlank(areaBuyerNo)) {
    			Date date = new Date();
    			page = new SimplePage(0, 1, 1);
    			params.put("billType", "1301");
    			params.put("saleNo", salerNo);
    			params.put("buyerNos", FasUtil.parseInCondition(areaBuyerNo).split(","));
    			params.put("sendDateStart", DateUtil.formatDate(DateUtil.getFirstDayOfMonth(date)));
    			params.put("sendDateEnd", DateUtil.formatDate(DateUtil.addDate(DateUtil.getLastDayOfMonth(date), -1)));
    			List<BillSaleBalance> saleList = billSaleBalanceManager.findByPage(page, "send_date", "asc", params);
    			if(saleList != null && saleList.size() > 0){
    				params.clear();
    				params.put("businessBillNo", saleList.get(0).getBillNo());
    				List<PayRelationship> shipList = payRelationshipManager.findByBiz(null, params);
    				if(shipList != null && shipList.size() > 0) {
    					dueDate = shipList.get(0).getDueDate();
    				}
    			}
    		}else {	
    			//根据付款条款计算到期日    			
//    			FIXED_DATE(1, "每月固定日"), 
//    			SEND_DATE(2,"发货日XX天"), 
//    			INVOICE_DATE(3, "发票日XX天"), 
//    			MONTHLY_DATE(4, "月结XX天"), 
//    			SUPPLIER_SEND_DATE(5, "供应商发货日XX天"); 
    			params.put("companyNo", salerNo);
    			params.put("supplierNo", buyerNo);
    			page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
    			List<PayTermSupplier> dataList = payTermSupplierManager.findByPage(page, "", "", params);
    			if(dataList != null && dataList.size()>0) {
    				PayTermSupplier	termSupplier = dataList.get(0);
    				String termType = termSupplier.getTermType();
    				if(PayTermTypeEnums.FIXED_DATE.getTypeNo().toString().equals(termType)) {
    					dueDate = DateUtil.getdate1(DateUtil.format(new Date(), "yyyy-MM-") + termSupplier.getFixedDay().toString());
    				}else if(PayTermTypeEnums.SEND_DATE.getTypeNo().toString().equals(termType)
    						|| PayTermTypeEnums.SUPPLIER_SEND_DATE.getTypeNo().toString().equals(termType)) {
    					//发货日与供应商发货日条款 ，使用扣项的扣项日期计算到货日 
    					dueDate = DateUtil.addDate(deductionDate, termSupplier.getDays());
    				}else if(PayTermTypeEnums.MONTHLY_DATE.getTypeNo().toString().equals(termType)) {
    					dueDate = DateUtil.addDate(DateUtil.getFirstDayOfMonth(new Date()), termSupplier.getDays());
    				}
    			}
    		}
    	}
    	result.put("dueDate", dueDate);
    	return result;
    }

	/**
	 * 导入
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/doImport")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws Exception{
		String[] fieldNames= new String[]{"salerNo","buyerNo","brandNo","deductionDate","dueDate","deductionAmount","deductionName","categoryNo","areaBuyerNo","remark"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "buyerNo", "buyerName","总部公司", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "salerNo", "salerName","供应商",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "deductionDate", "","日期", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "deductionAmount", "","扣项金额", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "deductionName", "","扣项名称", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "dueDate", "","到期日", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "areaBuyerNo", "areaBuyerName","地区公司", false));

		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), OtherDeduction.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, OtherDeduction.class);
		OtherDeduction deduction = null;
		Date currDate = new Date();
		String username = Authorization.getUser().getUsername();
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				deduction = (OtherDeduction) resultVo.getValidateObj();
				deduction.setFineAmount(deduction.getDeductionAmount());
				deduction.setBalanceType(BalanceTypeEnums.PE_SUPPLIER.getTypeNo());
				deduction.setCreateTime(currDate);
				deduction.setCreateUser(username);
				otherDeductionManager.add(deduction);
			}
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	
	}
	
	/**
	 * 导入
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/doShoesImport")
	@ResponseBody
	public Map<String, Object>  doShoesImport(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws Exception{
		String[] fieldNames= new String[]{"buyerNo","salerNo","deductionDate","brandNo","categoryNo","deductionAmount","deductionCategory","remark"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "buyerNo", "buyerName","公司", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "salerNo", "salerName","供应商",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "deductionDate", "","日期", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "deductionAmount", "","其他扣项", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "deductionCategory", "","性别", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "remark", "","备注", false));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), OtherDeduction.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, OtherDeduction.class);
		OtherDeduction deduction = null;
		Date currDate = new Date();
		String username = Authorization.getUser().getUsername();
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				deduction = (OtherDeduction) resultVo.getValidateObj();
				if(deduction.getDeductionCategory().equals("男")){
					deduction.setDeductionCategory("20141017000029");
				}else if(deduction.getDeductionCategory().equals("女")){
					deduction.setDeductionCategory("20141017000030");
				}else{
					deduction.setDeductionCategory("20141017000034");
				}
				deduction.setFineAmount(deduction.getDeductionAmount());
				deduction.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
				deduction.setCreateTime(currDate);
				deduction.setCreateUser(username);
				otherDeductionManager.add(deduction);
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
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Integer balanceType = params.get("balanceType")==null ? 0 : Integer.valueOf(params.get("balanceType").toString());
		String areaBuyerNo = params.get("multiAreaBuyerNo")==null ? "" : params.get("multiAreaBuyerNo").toString();
		if(areaBuyerNo != ""){
			 String[] areaBuyerNoArray = FasUtil.parseInQueryCondition(areaBuyerNo).split(",");
			 params.put("areaBuyerNoArray", areaBuyerNoArray);
		}
		if(balanceType == BalanceTypeEnums.AREA_WHOLESALE.getTypeNo()){
			String companyNos = null;
			if(isHq.equals("true")){
				companyNos = financialAccountManager.findLeadRoleCompanyNos();
			}else{
				companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
			}
			if (StringUtils.isNotEmpty(companyNos)) {
				String queryCondition = params.get("queryCondition")==null ? "" : params.get("queryCondition").toString();
				params.put("queryCondition", queryCondition + " AND SALER_NO IN (" + companyNos + ")");
			}
		}
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE); // 导出全部
		List<OtherDeduction> dataList = otherDeductionManager.findByPage(page, "", "", params);
		Map<String, String> currencyInfo = this.getCurrencyInfo();
		dataList = otherDeductionManager.setOtherDedutionProperies(dataList, currencyInfo);
		List<LookupVo> lstCurrency = commonUtilController.getCurrency();
		List<LookupVo> lstCategory = commonUtilController.getCategory();
		List<LookupVo> lstGender = commonUtilController.getGender();
		for (OtherDeduction obj : dataList) {
			for (LookupVo vo : lstCurrency) {
				if(vo.getCode().equals(obj.getCurrencyNo())){
					obj.setCurrencyNo(vo.getName());
					break;
				}
			}
			for (LookupVo vo : lstCategory) {
				if(vo.getCode().equals(obj.getCategoryNo())){
					obj.setCategoryNo(vo.getName());
					break;
				}
			}
			for (LookupVo vo : lstGender) {
				if(vo.getCode().equals(obj.getDeductionCategory())){
					obj.setDeductionCategory(vo.getName());
					break;
				}
			}
		}
		List<OtherDeduction> footerList = this.otherDeductionManager.findFooter(params);
		dataList.addAll(footerList);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
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
	
	/**
	 * 分配扣项金额
	 * @param req
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ManagerException
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@RequestMapping(value = "/distribute_deduction")
	public ResponseEntity<Map<String, Boolean>> distributeDeduction(HttpServletRequest req) throws JsonParseException,
			JsonMappingException, IOException, ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
		Map resultMap = new HashMap();
		Map<CommonOperatorEnum, List<OtherDeduction>> params = null;
		JsonUtil<OtherDeduction> util = new JsonUtil<OtherDeduction>();
		try {
			params = util.convertToMap(req, OtherDeduction.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("false", Boolean.valueOf(false));
			return new ResponseEntity(resultMap, HttpStatus.OK);
		}
		List<OtherDeduction> otherDeductionList = params.get(CommonOperatorEnum.UPDATED);
		
		Boolean result = otherDeductionManager.distributeDeduction(otherDeductionList);
		if (result) {
			flag.put("success", result);
		} else {
			flag.put("false", result);
		}
		
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}	

}