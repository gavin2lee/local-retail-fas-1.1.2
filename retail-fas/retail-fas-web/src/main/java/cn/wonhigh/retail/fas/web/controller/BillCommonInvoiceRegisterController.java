package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialCategoryDtl;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceApplyManager;
import cn.wonhigh.retail.fas.manager.BillCommonInvoiceRegisterManager;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.FinancialCategoryDtlManager;
import cn.wonhigh.retail.fas.manager.InvoiceInfoManager;
import cn.wonhigh.retail.fas.manager.ShopSaleDetailManager;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderInvoiceParameterDto;
import cn.wonhigh.retail.oc.common.api.dto.OrderAndReturnExMainDtlDto;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
@RequestMapping("/bill_balance_invoice_register")
public class BillCommonInvoiceRegisterController extends BaseController<BillCommonInvoiceRegister> {
    @Resource
    private BillCommonInvoiceRegisterManager billCommonInvoiceRegisterManager;
    @Resource
    private BillBalanceInvoiceApplyManager billBalanceInvoiceApplyManager;
    @Resource
    private ShopSaleDetailManager shopSaleDetailManager;
    
    @Resource
	private CompanyManager companyManager;
	
	@Resource
	private BrandManager brandManager;

	@Resource
	private FinancialCategoryDtlManager financialCategoryDtlManager;
	
	@Resource
	private InvoiceInfoManager invoiceInfoManager;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_common_invoice_register/",billCommonInvoiceRegisterManager);
    }
    
//    @RequestMapping("/list")
//   	public String list() {
//   		return "bill_balance_invoice_register/bill_common_register_dtl";
//   	}
    
	@RequestMapping("/bill_common_invoice_register")
	public String bill_backsectionSplit() {
		return "bill_balance_invoice_register/bill_common_invoice_register1";
	}

	// 新增页面
	@RequestMapping(method = RequestMethod.GET ,value = "/listInvoiceRegister")
	public ModelAndView listInvoiceRegister(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHQ");
		String billNoMenu = req.getParameter("billNoMenu");
    	mav.setViewName("bill_balance_invoice_register/bill_common_register"); 	
    	if("true".equals(isHq)){
    		mav.addObject("isHQ", "true");
    	}
    	if(StringUtils.isNotBlank(billNoMenu)) {
    		mav.addObject("billNoMenu", billNoMenu);
    	}
    	return mav;
	}

	// 基本信息tab页
	@RequestMapping("/bill_common_register_dtl")
	public String toRegisterDtl() {
		return "bill_balance_invoice_register/bill_common_register_dtl";
	}

	// 源单信息tab页
	@RequestMapping("/bill_common_balance_source")
	public String toBalaceSource() {
		return "bill_balance_invoice_register/bill_common_balance_source";
	}
	
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(@RequestParam("balanceType")String balanceType, HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	int iBalanceType = Integer.parseInt(balanceType);
    	mav.setViewName("bill_balance_invoice_register/bill_common_register_dtl"); 	
    	mav.addObject("balanceType", iBalanceType);    	
    	return mav;
    }
    
    /**
	 * 删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/deleteInvoiceRegister")
	@ResponseBody
	public Integer remove(@RequestParam("idList")String strIds) throws Exception{
		String[] ids = strIds.split(";");
		return  billCommonInvoiceRegisterManager.remove(ids);
	}
	
	/**
	 * 进行批量到票确认 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/InvoiceOk")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> updateConfirm(HttpServletRequest req)throws Exception{
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<BillCommonInvoiceRegister>> params = this.convertToMap(req);
		//获取单据编码
		List<BillCommonInvoiceRegister> billCommonInvoiceRegisterList=new ArrayList<BillCommonInvoiceRegister>();
		for (Entry<CommonOperatorEnum, List<BillCommonInvoiceRegister>> param : params.entrySet()) {
			if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
				billCommonInvoiceRegisterList = params.get(CommonOperatorEnum.UPDATED);
			}
		}
		
		SystemUser user = CurrentUser.getCurrentUser(req);//获取登录用户
		if(params.size()>0){
			billCommonInvoiceRegisterManager.updateConfirm(billCommonInvoiceRegisterList, user.getUsername());
		}
		
		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/listAll.json")
	@ResponseBody
	public  Map<String, Object> queryAllCustomer(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billCommonInvoiceRegisterManager.selectAllCustomerCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<LookupVo> list = this.billCommonInvoiceRegisterManager.findByAllCustomer(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	@Override
	protected List<BillCommonInvoiceRegister> queryExportData(Map<String, Object> params) throws ManagerException {
		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
		String queryCondition = "AND balance_type in(2,7,14,99) ";
   		if(StringUtils.isBlank(isHq)){
   			queryCondition = "AND balance_type in(5,7,8,10,11,99) ";
   		}
   		params.put("queryCondition", queryCondition);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<BillCommonInvoiceRegister> registerList = billCommonInvoiceRegisterManager.getByInvoice(page, "", "", params);
		return registerList;
	}

	/**
	 * 通过发票登记号获取发票登记信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getInvoiceRegister")
	@ResponseBody
	public Map<String, Object> getInvoiceRegister(HttpServletRequest req, Model model) throws ManagerException {
		String billNo = req.getParameter("billNo");
		String applyBillNo = req.getParameter("applyBillNo");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(billNo) && StringUtils.isEmpty(applyBillNo)) {
			obj.put("total", 0);
			obj.put("rows", new ArrayList<BillBalanceInvoiceInfo>(1));
		}else if(StringUtils.isNotEmpty(billNo)){
			obj = super.queryList(req, model);
			//return super.queryList(req, model);
		}else if(StringUtils.isEmpty(billNo) && StringUtils.isNotEmpty(applyBillNo)){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("billNo", applyBillNo);
			List<BillBalanceInvoiceApply> list = billBalanceInvoiceApplyManager.findByBiz(null, paramMap);
			
			if(null != list && list.size() > 0){
				billNo = list.get(0).getInvoiceRegisterNo();
				if(StringUtils.isEmpty(billNo)) {
					obj.put("total", 0);
					obj.put("rows", new ArrayList<BillBalanceInvoiceInfo>(1));
				} else {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("status", 1);
					params.put("billNo", billNo);
					int total = this.billCommonInvoiceRegisterManager.findCount(params);
					SimplePage page = new SimplePage(pageNo, pageSize, total);
					List<BillCommonInvoiceRegister> registerList = this.billCommonInvoiceRegisterManager.findByPage(page, sortColumn, sortOrder, params);
					obj.put("total", total);
					obj.put("rows", registerList);
				}
			}
		}
		return obj;
	}

	/**
	 * 根据源单类型区分是打开查询开票申请信息页面，还是打开查询原订单（pos销售明细）信息页面
	 * @param balanceType
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/search_invoice_apply_or_order")
	public ModelAndView searchInvoiceApplyOrOrderInfo(@RequestParam("balanceType") String balanceType) {
		ModelAndView mav = new ModelAndView();
		String url = "bill_balance_invoice_register/search_invoice_apply";
		if (StringUtils.isNotBlank(balanceType)) {
			int iBalanceType = Integer.parseInt(balanceType);
			mav.addObject("invoiceType", iBalanceType);
			if ((BalanceTypeEnums.AREA_MEMBERS_PURCHASE.getTypeNo() == iBalanceType)
					|| (BalanceTypeEnums.ALONE_SHOP.getTypeNo() == iBalanceType)) {//地区员购结算、独立店铺结算 需要查询订单信息，其他类型查询开票申请信息
				url = "bill_balance_invoice_register/search_order_info";
			}
		}
		mav.setViewName(url);
		return mav;
	}

	@RequestMapping(value = "/register_list")
	@ResponseBody
	public Map<String, Object> findInvoiceRegisterList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
		// 管理城市
   		String organNo = params.get("organNo") == null ? "" : params.get("organNo").toString();
   		List<String> organNoList = new ArrayList<String>();
   		if (StringUtils.isNotBlank(organNo)) {
   			if (organNo.contains(",")) {
   				String[] organNos = organNo.split(",");
   				for (String organNoTemp : organNos) {
   					organNoList.add(organNoTemp);
   				}
   			} else {
   				organNoList.add(organNo);
   			}

   			params.put("organNos", organNoList);
   			params.put("organNo", null);
   		}
   		
   		String queryCondition = "AND reg.balance_type in(2,14,99) ";
   		if(StringUtils.isBlank(isHq)){
   			queryCondition = "AND reg.balance_type in(5,7,8,10,11,99) ";
   		}
   		params.put("queryCondition", queryCondition);
   		//int total = billCommonInvoiceRegisterManager.findCount(params);
   		int total=billCommonInvoiceRegisterManager.getByInvoiceCount(params);
   		List<BillCommonInvoiceRegister> list = new ArrayList<BillCommonInvoiceRegister>();
   		if(total > 0 ){
   			SimplePage page = new SimplePage(pageNo, pageSize, total);
   			//list = billCommonInvoiceRegisterManager.findByPage(page, sortOrder, sortColumn, params);
   			list = this.billCommonInvoiceRegisterManager.getByInvoice(page, sortOrder, sortColumn, params);
   		}
//		if (!CollectionUtils.isEmpty(list)) {
//			for (BillCommonInvoiceRegister billCommonInvoiceRegister : list) {
//				//此方法处理源单据号问题
//				getSourceNoByBalanceType(billCommonInvoiceRegister);
//			}
//		}
		maps.put("total", total);
		maps.put("rows", list);
		return maps;
	}
	
	
	/**
	 * 新增
	 * @throws Exception 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "/addBillCommonInvoiceRegister")
	public ResponseEntity<BillCommonInvoiceRegister> addBillCommonInvoiceRegister(HttpServletRequest req, BillCommonInvoiceRegister type) throws  Exception {
		/*************************/
		List<OcOrderInvoiceParameterDto> orderDtlDtoList = null;
		if(StringUtils.isNotBlank(req.getParameter("checkedRows")) && req.getParameter("checkedRows") != null){
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("rawtypes")
			List<Map> list = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<Object> lstItem = convertListWithTypeReference(mapper, list, OrderAndReturnExMainDtlDto.class);
			orderDtlDtoList = new ArrayList<OcOrderInvoiceParameterDto>();
			for (Object object : lstItem) {
				OcOrderInvoiceParameterDto ocOrderInvoiceParameterDto = new OcOrderInvoiceParameterDto();
				OrderAndReturnExMainDtlDto dto = (OrderAndReturnExMainDtlDto)object;
				ocOrderInvoiceParameterDto.setOrderNo(dto.getOrderNo());
				ocOrderInvoiceParameterDto.setOrderBillType(dto.getOrderBillType());
				orderDtlDtoList.add(ocOrderInvoiceParameterDto);
			}
		}
		billCommonInvoiceRegisterManager.addBillCommonInvoiceRegister(type, orderDtlDtoList);
		
		String billNo = type.getBillNo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		List<BillCommonInvoiceRegister> invoiceRegisterList = billCommonInvoiceRegisterManager.findByBiz(null, params);
		BillCommonInvoiceRegister invoiceRegister = null;
		if(!CollectionUtils.isEmpty(invoiceRegisterList)){
			invoiceRegister = invoiceRegisterList.get(0);
			//此方法处理源单据号问题
			String sourceNo = findSourceByInvoiceNo(invoiceRegister.getBillNo());
			invoiceRegister.setSourceNo(sourceNo);
		}
		return new ResponseEntity<BillCommonInvoiceRegister>(invoiceRegister, HttpStatus.OK);
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
	 * 修改
	 */
	@Override
	@RequestMapping(value = "/put")
	public ResponseEntity<BillCommonInvoiceRegister> moditfy(BillCommonInvoiceRegister type) throws ManagerException {
		super.moditfy(type);
		BillCommonInvoiceRegister invoiceRegister = super.get(type).getBody();
		if(null != invoiceRegister){
			//此方法处理源单据号问题
			String sourceNo = findSourceByInvoiceNo(invoiceRegister.getBillNo());
			invoiceRegister.setSourceNo(sourceNo);
		}
		return new ResponseEntity<BillCommonInvoiceRegister>(invoiceRegister, HttpStatus.OK);
	}
	
	@Override
	@RequestMapping(value = "/get")
	/**
	 * 查询明细
	 */
	public ResponseEntity<BillCommonInvoiceRegister> get(BillCommonInvoiceRegister modelType) throws ManagerException {
		BillCommonInvoiceRegister billCommonInvoiceRegister = super.get(modelType).getBody();
		if(null != billCommonInvoiceRegister){
			//此方法处理源单据号问题
			String sourceNo = findSourceByInvoiceNo(billCommonInvoiceRegister.getBillNo());
			billCommonInvoiceRegister.setSourceNo(sourceNo);
		}
		return new ResponseEntity<BillCommonInvoiceRegister>(billCommonInvoiceRegister, HttpStatus.OK);
	}
	
	/**
	 * 根据发票编号查询开票申请号
	 * @param invoiceNo
	 * @return
	 * @throws ManagerException
	 */
	private String findSourceByInvoiceNo(String invoiceNo) throws ManagerException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("invoiceRegisterNo", invoiceNo);
		List<BillBalanceInvoiceApply> list = billBalanceInvoiceApplyManager.findByBiz(null, params);
		String invoiceNos = "";
		if(!CollectionUtils.isEmpty(list)){
			int index = 0;
			for (BillBalanceInvoiceApply billBalanceInvoiceApply : list) {
				invoiceNos += billBalanceInvoiceApply.getBillNo();
				if(index < (list.size() -1)){
					invoiceNos += ",";
				}
				index ++;
			}
		}
		return invoiceNos;
	}

	/**
	 * 根据结算单号查发票信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/query_balance_invoice")
	@ResponseBody
	public Map<String, Object> queryListByBalanceNo(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int total = 0;
		List<BillCommonInvoiceRegister>	lstItem = new ArrayList<BillCommonInvoiceRegister>();
		if(StringUtils.isNotBlank(req.getParameter("balanceNo"))){
			int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
			int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
			String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
			String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceNo", req.getParameter("balanceNo"));
			total = this.billCommonInvoiceRegisterManager.selectCountByBalanceNo(params);
			if(total > 0){
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				lstItem = this.billCommonInvoiceRegisterManager.selectListByBalanceNo(page, sortColumn, sortOrder, params);
			}
		}
		resultMap.put("total", total);
		resultMap.put("rows", lstItem);
		return resultMap;
	}
	
	/**
	 * 查询独立店铺没有登记发票的销售明细
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/query_alone_saler_detail")
	@ResponseBody
	public Map<String,Object> queryAloneSalerDetail(HttpServletRequest req, Model model)throws ManagerException {
		
		Map<String, Object> params = builderParams(req, model);
		String invoiceNoFlag = req.getParameter("invoiceNoFlag") == null ?"0":req.getParameter("invoiceNoFlag");
		params.put("invoiceNoFlag", invoiceNoFlag);
//		List<ItemSaleDtlDto> itemSaleDtlDtos = null;
		params.put("pageSize", 999999999);
		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = shopSaleDetailManager.queryShopSaleDetailListRemote(params);
		
		if(orderAndReturnExMainDtlDtoList == null || orderAndReturnExMainDtlDtoList.getResult() == null ||  orderAndReturnExMainDtlDtoList.getResult().size() < 1){
			return null;
		}
		List<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainList = orderAndReturnExMainDtlDtoList.getResult();
//		itemSaleDtlDtos = shopSaleDetailManager.converDateToViewData(OrderAndReturnExMainList);
		Map<String, POSOrderAndReturnExMainDtlDto> listMap = new HashMap<String, POSOrderAndReturnExMainDtlDto>();
		for (POSOrderAndReturnExMainDtlDto dto : orderAndReturnExMainList) {
			listMap.put(dto.getOrderNo(), dto);
		}
		orderAndReturnExMainList.clear();
		orderAndReturnExMainList.addAll(listMap.values());
		
		List<POSOrderAndReturnExMainDtlDto> showList = new ArrayList<POSOrderAndReturnExMainDtlDto>();
		int total = orderAndReturnExMainList.size();
		if(total > 0){
			int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
			int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
				showList.add(orderAndReturnExMainList.get(i));
			}
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", showList);
		return obj;
	}

	/**
	 * 开票申请列表页，直接生成发票
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addInoviceRegisters", method = RequestMethod.POST)
	@ResponseBody
	public List<BillCommonInvoiceRegister> addInoviceRegisters(BillCommonInvoiceRegister model, HttpServletRequest request) {
		SystemUser user = CurrentUser.getCurrentUser(request);
		List<BillCommonInvoiceRegister> returnList = new ArrayList<BillCommonInvoiceRegister>();
		try {
			//把选中的销售明细进行处理，获取订单号及订单类型(0-正常 1-换货)信息,生成发票回写发票号到pos 时,需要这两个信息
			if(StringUtils.isNotBlank(request.getParameter("checkedRows")) && request.getParameter("checkedRows") != null){
				ObjectMapper mapper = new ObjectMapper();
				@SuppressWarnings("rawtypes")
				List<Map> list = mapper.readValue(request.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
				List<Object> lstItem = convertListWithTypeReference(mapper, list, BillBalanceInvoiceApply.class);
				returnList = billCommonInvoiceRegisterManager.addInoviceRegisters(lstItem,user.getUsername());
			}
		} catch(Exception e) {
			BillCommonInvoiceRegister invoiceRegister = new BillCommonInvoiceRegister();
			invoiceRegister.setRemark("系统异常，发票登记生成失败,请联系管理员。");
			returnList.add(invoiceRegister);
		}
		return returnList;
	}
	
	@RequestMapping("/show_error_info")
	public String showErrorInfo() {
		return "bill_balance_invoice_register/show_error_info";
	}
	
//	 /**
//     * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
//     * @param req
//     * @param model
//     * @return
//     * @throws ManagerException
//     */
//    @RequestMapping(value = "/getBillCommonRegister")
//	@ResponseBody
//	public  Map<String, Object> getBillCommonRegister(HttpServletRequest req, Model model) throws ManagerException {
//		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
//		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
//		Map<String, Object> params = builderParams(req, model);
//		params.put("balanceType", BalanceTypeEnums.AREA_SALEORDER.getTypeNo());
//		int total = this.billCommonInvoiceRegisterManager.findInvoiceregisterCountForPayment(params);
//		SimplePage page = new SimplePage(pageNo, pageSize, total);
//		List<BillCommonInvoiceRegister> list = billCommonInvoiceRegisterManager.findInvoiceRegisterForPayment(page, params);
//		Map<String, Object> obj = new HashMap<String, Object>();
//		obj.put("total", total);
//		obj.put("rows", list);
//		return obj;
//	}
    
    @RequestMapping(value = "/do_import")
	@ResponseBody
	public 	Map<String, Object> doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
    	SystemUser user = CurrentUser.getCurrentUser(req);
    	String[] fieldNames= new String[]{"sourceNo","salerNo","buyerNo","invoiceCode","invoiceNo","invoiceDate","invoiceAmount",
    			"taxRate","express","deliveryNo","deliveryDate","confirmUser","confirmTime","remark"};
    	List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), BillCommonRegisterDtl.class, fieldNames);
    	List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
    	lstValidate.add(new ValidateVo(-1, "sourceNo", "","开票申请号", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "salerNo", "","公司编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "buyerNo", "","客户编码",true));
		lstValidate.add(new ValidateVo(-1, "invoiceCode", "","发票编码",true));
		lstValidate.add(new ValidateVo(-1, "invoiceNo", "","发票号",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "invoiceDate", "","发票日期",false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "invoiceAmount", "","发票金额",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "taxRate", "","税率",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌名称",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.FINANCIAL_CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类名称",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "qty", "","数量",true));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "price", "","价格",false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "deliveryDate", "","快递日期",false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "confirmTime", "","确认日期",false));
		List<ValidateResultVo>  listValidate = doValidate(lstItem, lstValidate, BillCommonRegisterDtl.class);
		List<BillCommonRegisterDtl> dtlList = new ArrayList<BillCommonRegisterDtl>();
		boolean flag = true;
		for (Object object : listValidate) {
			ValidateResultVo vo = (ValidateResultVo)object;
			if(vo.getPass() == YesNoEnum.YES.getValue()){
				BillCommonRegisterDtl billCommonRegisterDtl = (BillCommonRegisterDtl)vo.getValidateObj();
				dtlList.add(billCommonRegisterDtl);
			}else{
				flag = false;
			}
		}
		//导入的数据只要有一笔不合法，则不执行保存操作
		if(flag && !CollectionUtils.isEmpty(dtlList)){
			billCommonInvoiceRegisterManager.doImportInvoiceRegsiter(dtlList,user.getUsername());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", flag);
		map.put("data", listValidate);
		return map;
	}
	
    
    @RequestMapping(value = "/do_detail_import")
   	@ResponseBody
   	public 	Map<String, Object> doDetailImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
//       	SystemUser user = CurrentUser.getCurrentUser(req);
       	String[] fieldNames= new String[]{"invoiceCode","invoiceNo","invoiceDate","invoiceAmount",
       			"taxRate","qty","estimatedAmount","express","deliveryNo","deliveryDate","confirmUser","confirmTime","remark"};
       	List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), BillCommonRegisterDtl.class, fieldNames);
       	List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
   		lstValidate.add(new ValidateVo(-1, "invoiceCode", "","发票编码",true));
   		lstValidate.add(new ValidateVo(-1, "invoiceNo", "","发票号",true));
   		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "invoiceDate", "","发票日期",false));
   		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "invoiceAmount", "","发票金额",true));
   		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "qty", "","数量",true));
   		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "estimatedAmount", "","预估成本",false));
//   		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌名称",false));
//   		lstValidate.add(new ValidateVo(ValidateTypeEnums.FINANCIAL_CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类名称",false));
//   		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "price", "","价格",false));
   		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "deliveryDate", "","快递日期",false));
   		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "confirmTime", "","确认日期",false));
   		List<ValidateResultVo>  listValidate = doValidateDetail(lstItem, lstValidate, BillCommonRegisterDtl.class);
   		for (Object object : listValidate) {
   			ValidateResultVo vo = (ValidateResultVo)object;
   			if(vo.getPass() == YesNoEnum.YES.getValue()){
   				BillCommonRegisterDtl billCommonRegisterDtl = (BillCommonRegisterDtl)vo.getValidateObj();
   				
   				if(null == billCommonRegisterDtl.getTaxRate()){
					billCommonRegisterDtl.setTaxRate(new BigDecimal(0.17));
				}
				// 计算不含税金额 ＝ 金额 / 1+0.17
				if(null == billCommonRegisterDtl.getNoTaxAmount()){
					BigDecimal noTaxAmount = billCommonRegisterDtl.getInvoiceAmount().divide(billCommonRegisterDtl.getTaxRate().add(new BigDecimal(1)),2,BigDecimal.ROUND_HALF_UP) ;
					billCommonRegisterDtl.setNoTaxAmount(noTaxAmount);
				}
				// 税额 ＝ 金额 － 不含税金额
				if(null == billCommonRegisterDtl.getTaxAmount()){
					BigDecimal taxAmount = billCommonRegisterDtl.getInvoiceAmount().subtract(
							billCommonRegisterDtl.getInvoiceAmount().divide(billCommonRegisterDtl.getTaxRate().add(new BigDecimal(1)),2,BigDecimal.ROUND_HALF_UP)) ;
					billCommonRegisterDtl.setTaxAmount(taxAmount);
				}
//				if(null == billCommonRegisterDtl.getPrice()){
//					double price = billCommonRegisterDtl.getInvoiceAmount().doubleValue() / billCommonRegisterDtl.getQty();
//					billCommonRegisterDtl.setPrice(new BigDecimal(price));
//				}
   			}
   		}
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("success", true);
   		map.put("rows", listValidate);
   		return map;
   	}
    
    /**
	 * 导入校验
	 * @param fileName
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	private List<ValidateResultVo> doValidate(List<Object> lstItem, List<ValidateVo> lstValidate, Class clazz) throws Exception {
		List<ValidateResultVo> resultList = new ArrayList<ValidateResultVo>();
		if(!CollectionUtils.isEmpty(lstItem) && !CollectionUtils.isEmpty(lstValidate) ){
			String sourceNo = "", salerNo = "", buyerNo = "";
			for (int i = 0,iSize = lstItem.size(); i<iSize; i++) {
				ValidateResultVo resultVo = new ValidateResultVo();
				Object obj = lstItem.get(i);
				for (ValidateVo validate : lstValidate) {
					this.validate(validate, obj, clazz, resultVo);
				}
				String errorInfo = "";
				BillCommonRegisterDtl billCommonRegisterDtl = (BillCommonRegisterDtl)obj;
				// 每笔记录都需要验证此申请是否可以登记发票（状态是否为确认；是否已经登记过发票）
				BillBalanceInvoiceApply billBalanceInoviceApply = new BillBalanceInvoiceApply();
				billBalanceInoviceApply.setBillNo(billCommonRegisterDtl.getSourceNo());
				BillBalanceInvoiceApply inoviceApply = billBalanceInvoiceApplyManager.findById(billBalanceInoviceApply);
				if(null != inoviceApply){
					if(1 == inoviceApply.getStatus()){ // 开票申请单据状态：1＝制单；2＝确认
						errorInfo = errorInfo.concat("必须为确认状态：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
					}
					if(StringUtils.isNotBlank(inoviceApply.getInvoiceRegisterNo())){
						errorInfo = errorInfo.concat("已登记过发票：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
					}
				} else {
					errorInfo = errorInfo.concat("开票申请号不存：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
				}
				// 如果开票申请号一样，表示此申请号导入的是多笔明细，需要验证多笔明细的公司及客户是否一致
				if(StringUtils.isNotBlank(billCommonRegisterDtl.getSourceNo()) && sourceNo.equals(billCommonRegisterDtl.getSourceNo())){
					if(StringUtils.isNotBlank(billCommonRegisterDtl.getSalerNo()) && !salerNo.equals(billCommonRegisterDtl.getSalerNo())){
						errorInfo = errorInfo.concat("同一单开票申请公司必须一致：").concat(billCommonRegisterDtl.getSalerNo()).concat(";");
					}
					if(StringUtils.isNotBlank(billCommonRegisterDtl.getBuyerNo()) && !buyerNo.equals(billCommonRegisterDtl.getBuyerNo())){
						errorInfo = errorInfo.concat("同一单开票申请客户必须一致：").concat(billCommonRegisterDtl.getBuyerNo()).concat(";");
					}
				}else{
					sourceNo = billCommonRegisterDtl.getSourceNo();
					salerNo = billCommonRegisterDtl.getSalerNo();
					buyerNo = billCommonRegisterDtl.getBuyerNo();
					if(null != inoviceApply){
						if(!salerNo.equals(inoviceApply.getSalerNo())){
							errorInfo = errorInfo.concat("公司编码与开票申请不一致：").concat(salerNo).concat(";");
						}
						if(!buyerNo.equals(inoviceApply.getBuyerNo())){
							errorInfo = errorInfo.concat("客户编码与开票申请不一致：").concat(buyerNo).concat(";");
						}
					}
				}
				
				String errorCode = resultVo.getErrorCode();
				String emptyCode = resultVo.getEmptyCode();
				if(StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(emptyCode) ){
					resultVo.setPass(YesNoEnum.NO.getValue());
					if(StringUtils.isNotBlank(errorCode)){
						errorInfo = errorInfo.concat("无效编码：").concat(errorCode.substring(0, errorCode.length()-1).concat(";"));
					}
					if(StringUtils.isNotBlank(emptyCode)){
						errorInfo = errorInfo.concat("必填字段：").concat(emptyCode.substring(0, emptyCode.length()-1));
					}
				}
				if(StringUtils.isBlank(errorInfo)){
					resultVo.setPass(YesNoEnum.YES.getValue());
				}else{
					resultVo.setErrorInfo(errorInfo);
				}
				resultVo.setIndex(i+3);
				resultVo.setValidateObj(obj);
				resultList.add(resultVo);
			}
		}
		return resultList;
	}
	
	
	 /**
		 * 导入校验
		 * @param fileName
		 * @param request
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		@SuppressWarnings("rawtypes")
		private List<ValidateResultVo> doValidateDetail(List<Object> lstItem, List<ValidateVo> lstValidate, Class clazz) throws Exception {
			List<ValidateResultVo> resultList = new ArrayList<ValidateResultVo>();
			if(!CollectionUtils.isEmpty(lstItem) && !CollectionUtils.isEmpty(lstValidate) ){
				for (int i = 0,iSize = lstItem.size(); i<iSize; i++) {
					ValidateResultVo resultVo = new ValidateResultVo();
					Object obj = lstItem.get(i);
					for (ValidateVo validate : lstValidate) {
						this.validate(validate, obj, clazz, resultVo);
					}
					String errorInfo = "";
					String errorCode = resultVo.getErrorCode();
					String emptyCode = resultVo.getEmptyCode();
					if(StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(emptyCode) ){
						resultVo.setPass(YesNoEnum.NO.getValue());
						if(StringUtils.isNotBlank(errorCode)){
							errorInfo = errorInfo.concat("无效编码：").concat(errorCode.substring(0, errorCode.length()-1).concat(";"));
						}
						if(StringUtils.isNotBlank(emptyCode)){
							errorInfo = errorInfo.concat("必填字段：").concat(emptyCode.substring(0, emptyCode.length()-1));
						}
					}
					if(StringUtils.isBlank(errorInfo)){
						resultVo.setPass(YesNoEnum.YES.getValue());
					}else{
						resultVo.setErrorInfo(errorInfo);
					}
					resultVo.setIndex(i+3);
					resultVo.setValidateObj(obj);
					resultList.add(resultVo);
				}
			}
			return resultList;
		}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void validate(ValidateVo validate, Object obj , Class clazz, ValidateResultVo result)throws Exception{
		if(StringUtils.isBlank(validate.getPropertyName())){
			return ;
		}
		String propertyName = validate.getPropertyName();
		String cnPropertyName = validate.getCnPropertyName();
		Method getNoMethod = null;
		Method setNameMethod = null;
		if(StringUtils.isNotBlank(propertyName)){
			getNoMethod =  clazz.getDeclaredMethod("get".concat(propertyName.substring(0,1).toUpperCase()).concat(propertyName.substring(1)));
		}
		if(StringUtils.isNotBlank(cnPropertyName)){
			 setNameMethod =  clazz.getDeclaredMethod("set".concat(cnPropertyName.substring(0,1).toUpperCase()).concat(cnPropertyName.substring(1)),String.class);
		}
		String errorCode = result.getErrorCode();
		String emptyCode = result.getEmptyCode();
		Object validateObj = getNoMethod.invoke(obj);
		String validateNo = String.valueOf(validateObj);
		if(null!= validateObj && StringUtils.isNotBlank(validateNo)){
			Map<String, Object> params = new HashMap<String, Object>();
			if(validate.getValidateType() == ValidateTypeEnums.COMPANY.getTypeNo()){
				params.put("companyNo", validateNo);
				List<Company> list =  companyManager.findByBiz(null, params);
				if(list.size() < 0){
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.SUPPLIER.getTypeNo()){
				params.put("clientNo", validateNo);
				
				List<InvoiceInfo> list =  invoiceInfoManager.findByBiz(null,params);
				if(list == null || list.size() < 0){
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.BRAND.getTypeNo()){
				params.put("brandNo", validateNo);
				List<Brand> list =  brandManager.findByBiz(null, params);
				if(list.size() > 0 && null != setNameMethod){
					setNameMethod.invoke(obj, list.get(0).getName());
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.FINANCIAL_CATEGORY.getTypeNo()){
				params.put("categoryNo", validateNo);
				List<FinancialCategoryDtl> list =  financialCategoryDtlManager.findByBiz(null, params);
				if(list.size() > 0 && null != setNameMethod){
					setNameMethod.invoke(obj, list.get(0).getName());
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.NUMBER.getTypeNo()){
				if(!(validateObj instanceof Number)){
					errorCode = errorCode.concat(String.valueOf(validateObj)).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.DATE.getTypeNo()){
				if(!(validateObj instanceof Date)){
					errorCode = errorCode.concat(String.valueOf(validateObj)).concat(",");
				}
			}
		}else{
			if(validate.isEmptyValidate()){
				emptyCode = emptyCode.concat(validate.getPropertyDesc()).concat(",");
			}
		}
		result.setErrorCode(errorCode);
		result.setEmptyCode(emptyCode);
	}
}