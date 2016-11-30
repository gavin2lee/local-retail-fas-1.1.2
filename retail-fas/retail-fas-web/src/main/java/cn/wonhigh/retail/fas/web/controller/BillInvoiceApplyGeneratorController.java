package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.utils.JsonUtils;
import cn.wonhigh.retail.fas.common.enums.AreaAmongBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.AreaOtherOutBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.AreaOtherStockOutBalanceManager;
import cn.wonhigh.retail.fas.manager.BillBalanceManager;
import cn.wonhigh.retail.fas.manager.BillInvoiceApplyGeneratorManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@RequestMapping("/bill_invoice_apply_generate")
@ModuleVerify("30300300")
public class BillInvoiceApplyGeneratorController extends BaseCrudController<BalanceInvoiceApplyGenerator> {
	
	@Resource
    private BillInvoiceApplyGeneratorManager billInvoiceApplyGeneratorManager;
	
    @Resource
    private BillBalanceManager billBalanceManager;

    @Resource
	private BillShopBalanceManager billShopBalanceManager;

    @Resource
    private AreaOtherStockOutBalanceManager areaOtherStockOutBalanceManager;
    
    @Resource
    private OrderMainManager orderMainManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_invoice_apply_generate/",billInvoiceApplyGeneratorManager);
    }
    
    @RequestMapping("/bill_invoice_apply")
	public String listBillInvoiceApply() {
		return "bill_invoice_apply_generate/bill_invoice_apply";
	}
    
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		String companyNo=(String) params.get("companyNo");
		int total = 0;
		SimplePage page = null;
		List<BalanceInvoiceApplyGenerator> list = null;
		if (String.valueOf(BalanceTypeEnums.AREA_MALL.getTypeNo()).equals((String)params.get("balanceType"))) {
			// 移除balanceType参数，防止在查询是出现数据遗异常（商场结算单表中有banalce_type字段）
//			if(params != null && params.size() > 0 && params.get("balanceType") != null) {
//				params.remove("balanceType");
//			}
			params.put("status", 2);
			params.put("balanceType", 1);//正式的，非预估的
			params.put("startDate", params.get("balanceStartDate"));
			params.put("endDate", params.get("balanceEndDate"));
			total = this.billShopBalanceManager.findCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillShopBalance> shopBalances = this.billShopBalanceManager.findByPage(page, sortColumn, sortOrder, params);
			list = convertShopBalancesToApplyGenerators(shopBalances);
		} else if (String.valueOf(BalanceTypeEnums.AREA_OTHER.getTypeNo()).equals((String)params.get("balanceType"))) {
			//地区其他出库结算，不做品牌权限控制
			params.put("status", 4);
			params.put("salerNo", companyNo);
			total = this.areaOtherStockOutBalanceManager.findCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillBalance> billBalances = this.areaOtherStockOutBalanceManager.findByPage(page, sortColumn, sortOrder, params);
			list = convertBalancesToApplyGenerators(billBalances);
		} else {
			//地区间结算，地区批发结算
			params.put("status", 4);
			params.put("salerNo", companyNo);
			total = this.billBalanceManager.findCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillBalance> billBalances = this.billBalanceManager.findByPage(page, sortColumn, sortOrder, params);
			list = convertBalancesToApplyGenerators(billBalances);
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
	@RequestMapping(value = "/query_bill.json")
	@ResponseBody
    public Map<String, Object> queryBillList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		
		List<BalanceInvoiceApplyGenerator> list = new ArrayList<BalanceInvoiceApplyGenerator>();
		
   		int total = 0 ;
   		//设置查询条件
   		setCommonParams(params);
		total = orderMainManager.findApplyGeneratorDetailCount(params);
		if(total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = orderMainManager.findApplyGeneratorDetail(page, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 设置单据开票tab 页的查询条件
	 * @param params
	 */
	private void setCommonParams(Map<String, Object> params){
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

   			params.put("organNoList", organNoList);
   			params.put("organNo", null);
   		}
   		
		String billBalanceType = params.get("billBalanceType") == null ? "" : params.get("billBalanceType").toString();
		if(StringUtils.isNotBlank(billBalanceType)){
			if(billBalanceType.contains(",")){
				String[] businessTypes = billBalanceType.split(",");
				String posSql = "";//pos 的业务类型条件
				String gmsSql = "";//gms 的单据类型条件
				int queryType = 0; //查询类型区分：1＝查询GMS单据，2＝只查询POS单据，3＝GMS、POS 单据连表查询
				for (String typeStr : businessTypes) {
					if("1".equals(typeStr)){ //索赔单
						if(StringUtils.isNotBlank(gmsSql)){
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat("( bsb.bill_type = 1355 AND bsb.biz_type IN (8,10)) ");//,10
					}else if("2".equals(typeStr)){ //报废单
						if(StringUtils.isNotBlank(gmsSql)){
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat("(bsb.bill_type = 1342)");
					}else if("3".equals(typeStr)){ //客残销售单
						if(StringUtils.isNotBlank(gmsSql)){
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat("(bsb.bill_type = 1335 AND bsb.biz_type = 2) OR (bsb.bill_type = 1355 AND bsb.biz_type = 26)");
					}else if("5".equals(typeStr)){ //团购出库单
						if(StringUtils.isNotBlank(gmsSql)){
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat("(bsb.bill_type = 1335 AND bsb.biz_type = 23)");
					}else if("6".equals(typeStr)){ //团购退货单
						if(StringUtils.isNotBlank(gmsSql)){
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat("(bsb.bill_type = 1335 AND bsb.biz_type = 24)");
					}else if("7".equals(typeStr)){ //领用出库
						if(StringUtils.isNotBlank(gmsSql)){
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat("(bsb.bill_type = 1335 AND bsb.biz_type = 13)");
					}else{//POS内购单
						posSql = "3" ;
					}
				}
				
				if(StringUtils.isBlank(posSql) && StringUtils.isNotBlank(gmsSql)){ //如果pos 的查询条件为空，则表示没有选择门店及内购类型，那只需要查询gms单据即可
					queryType = 1 ;
				} else{ // 否则则调用连表（pos 及 gms）的查询；这里不存只查询pos 单据，
					queryType = 3 ;
				}
				params.put("queryCondition", gmsSql);
				params.put("queryType", queryType);
				
			}else{//如果只单选POS内购单的话会执行这里的else 条件
				String queryCondition = "";
				int queryType = 0; //查询类型区分：1＝查询GMS单据，2＝只查询POS单据，3＝GMS、POS 单据连表查询
				if("1".equals(billBalanceType)){//索赔单
					queryCondition = "( bsb.bill_type = 1355 AND bsb.biz_type IN (8,10)) ";//,10
					queryType = 1 ;//表示只查询GMS 单据
				}else if("2".equals(billBalanceType)){ //报废单
					queryCondition = "(bsb.bill_type = 1342) ";
					queryType = 1 ;//表示只查询GMS 单据
				}else if("3".equals(billBalanceType)){ //客残销售单
					queryCondition = "(bsb.bill_type = 1335 AND bsb.biz_type = 2) OR (bsb.bill_type = 1355 AND bsb.biz_type = 26)";
					queryType = 1 ;//表示只查询GMS 单据
				}else if("5".equals(billBalanceType)){ //团购出库单
					queryCondition = "(bsb.bill_type = 1335 AND bsb.biz_type = 23)";
					queryType = 1 ;//表示只查询GMS 单据
				}else if("6".equals(billBalanceType)){ //团购退货单
					queryCondition = "(bsb.bill_type = 1335 AND bsb.biz_type = 24)";
					queryType = 1 ;//表示只查询GMS 单据
				}else if("7".equals(billBalanceType)){ //领用出库
					queryCondition = "(bsb.bill_type = 1335 AND bsb.biz_type = 13)";
					queryType = 1 ;//表示只查询GMS 单据
				}else{//其他
					queryType = 2 ;//表示只查询POS 单据
				}
				params.put("queryCondition", queryCondition);
				params.put("queryType", queryType);
			}
		}else{
			params.put("queryType", 3);//表示GMS、POS 单据合并查询
		}
	}
    
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/check_balance_amount")
	public ResponseEntity<Map<String, Object>> checkBalanceAmount(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException,
			ManagerException {
		Map<String, Object> validResult = new HashMap<String, Object>();

		String checkedRows = StringUtils.isEmpty(req.getParameter("checkedRows")) ? "" : req.getParameter("checkedRows");
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(checkedRows, new TypeReference<List<Map>>(){});
		List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators = convertListWithTypeReference(mapper,list);
		validResult.put("success", true);
		
		Map<String, Object> checkMap = new HashMap<String, Object>();
		for (BalanceInvoiceApplyGenerator validApplyGenerator : balanceInvoiceApplyGenerators) {
			//价格为零的校验
//			if (null == validApplyGenerator.getAmount() || validApplyGenerator.getAmount().compareTo(BigDecimal.ZERO) == 0) {
//				validResult.put("success", false);
//				validResult.put("message", "结算单编号【" + validApplyGenerator.getBillNo() + "】的开票金额为0.");
//				break;
//			}
			//状态是否已经变更
			if (BalanceTypeEnums.AREA_MALL.getTypeNo() == validApplyGenerator.getBalanceType()) {
				checkMap.put("balanceNo", validApplyGenerator.getBillNo());
				
				List<BillShopBalance> shopBalances = billShopBalanceManager.findByBiz(null, checkMap);
				if (!CollectionUtils.isEmpty(shopBalances)) {
					if (2 != shopBalances.get(0).getStatus()) {
						validResult.put("success", false);
						validResult.put("message", "结算单编号【" + validApplyGenerator.getBillNo() + "】的状态不正确.");
						break;
					}
				}else {
					validResult.put("success", false);
					validResult.put("message", "结算单编号【" + validApplyGenerator.getBillNo() + "】已经不存在.");
					break;
				}
			}else {
				checkMap.put("billNo", validApplyGenerator.getBillNo());

				List<BillBalance> billBalances = billBalanceManager.findByBiz(null, checkMap);
				if (!CollectionUtils.isEmpty(billBalances)) {
					if (4 != billBalances.get(0).getStatus()) {
						validResult.put("success", false);
						validResult.put("message", "结算单编号【" + validApplyGenerator.getBillNo() + "】的状态不正确.");
						break;
					}
				}else {
					validResult.put("success", false);
					validResult.put("message", "结算单编号【" + validApplyGenerator.getBillNo() + "】已经不存在.");
					break;
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(validResult, HttpStatus.OK);
	}
	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/save_balance_invoice")
	public ResponseEntity<Map<String, Object>> saveBalanceInvoice(HttpServletRequest req) throws Exception {
    	
//		Map<String, Boolean> flag = new HashMap<String, Boolean>();

		String checkedRows = StringUtils.isEmpty(req.getParameter("checkedRows")) ? "" : req.getParameter("checkedRows");
		
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(checkedRows, new TypeReference<List<Map>>(){});
		List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators = convertListWithTypeReference(mapper,list);
		
		this.setInvoiceApplyBaseInfos(balanceInvoiceApplyGenerators,req);
		
		Map<String, Object> flag = this.billInvoiceApplyGeneratorManager.generateBalanceInvoiceApply(balanceInvoiceApplyGenerators);
		
//		flag.put("success", true);
		return new ResponseEntity<Map<String, Object>>(flag, HttpStatus.OK);
	}
	
    private void setInvoiceApplyBaseInfos(List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators, HttpServletRequest req) throws Exception {
    	SystemUser loginUser = CurrentUser.getCurrentUser(req);
		for (BalanceInvoiceApplyGenerator balanceInvoiceApplyGenerator : balanceInvoiceApplyGenerators) {
			balanceInvoiceApplyGenerator.setCreateUser(loginUser.getUsername());
			balanceInvoiceApplyGenerator.setCreateTime(DateUtil.getCurrentDateTime());
		}
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<BalanceInvoiceApplyGenerator> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<BalanceInvoiceApplyGenerator> entityClass = (Class<BalanceInvoiceApplyGenerator>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<BalanceInvoiceApplyGenerator> tl=new ArrayList<BalanceInvoiceApplyGenerator>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BalanceInvoiceApplyGenerator type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
    
    @RequestMapping(value = "/save_bill_invoice")
    @ResponseBody
	public ResponseEntity<Map<String, Object>> saveBillInvoice(HttpServletRequest req, Model model) throws JsonParseException, JsonMappingException, IOException,
			ManagerException {
    	
		Map<String,Object> params = builderParams(req, model);
		SystemUser loginUser = CurrentUser.getCurrentUser(req);
		String checkedRows = StringUtils.isEmpty(params.get("checkedRows").toString()) ? "" : params.get("checkedRows").toString();
		Object buyerNo = params.get("buyerNo");
		Object buyerName = params.get("buyerName");
		String invoiceInfoStatus = params.get("invoiceInfoStatus") == null?"":params.get("invoiceInfoStatus").toString();
		List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGeneratorList = null;
		if(StringUtils.isNotEmpty(checkedRows)) {
			balanceInvoiceApplyGeneratorList = new ArrayList<BalanceInvoiceApplyGenerator>();
			
			String[] str = checkedRows.split("&");
			for(String s : str){
				BalanceInvoiceApplyGenerator balanceInvoiceApplyGenerator = JsonUtils.fromJson(s,BalanceInvoiceApplyGenerator.class);
				balanceInvoiceApplyGenerator.setBuyerName(buyerName.toString());
				balanceInvoiceApplyGenerator.setBuyerNo(buyerNo.toString());
				balanceInvoiceApplyGeneratorList.add(balanceInvoiceApplyGenerator);
			}
		}
		
		Map<String, Object> flag = billInvoiceApplyGeneratorManager.addInvoiceApplyMainInfo(balanceInvoiceApplyGeneratorList,loginUser.getUsername(),invoiceInfoStatus);
		
		return new ResponseEntity<Map<String, Object>>(flag, HttpStatus.OK);
	}
	
    private List<BalanceInvoiceApplyGenerator> convertShopBalancesToApplyGenerators(List<BillShopBalance> shopBalances) {
    	
    	List<BalanceInvoiceApplyGenerator> retApplyGenerators = new ArrayList<BalanceInvoiceApplyGenerator>();
		
		if (CollectionUtils.isEmpty(shopBalances)) {
			return retApplyGenerators;
		}
		
		for (BillShopBalance shopBalance : shopBalances) {
			
			BalanceInvoiceApplyGenerator applyGenerator = convertShopBalanceToApplyGenerator(shopBalance);
			retApplyGenerators.add(applyGenerator);
		}
		
		return retApplyGenerators;
	}
    
    private BalanceInvoiceApplyGenerator convertShopBalanceToApplyGenerator(BillShopBalance shopBalance) {
		BalanceInvoiceApplyGenerator applyGenerator = new BalanceInvoiceApplyGenerator();
		applyGenerator.setBillNo(shopBalance.getBalanceNo());
		applyGenerator.setBalanceType(BalanceTypeEnums.AREA_MALL.getTypeNo());
		applyGenerator.setBalanceTypeName(BalanceTypeEnums.AREA_MALL.getTypeName());
		applyGenerator.setStatus(shopBalance.getStatus());
		applyGenerator.setStatusName(shopBalance.getBillStatusName());
		applyGenerator.setBalanceDate(shopBalance.getBillDate());
		applyGenerator.setSalerNo(shopBalance.getCompanyNo());
		applyGenerator.setSalerName(shopBalance.getCompanyName());
		applyGenerator.setBuyerNo(shopBalance.getShopNo());
		applyGenerator.setBuyerName(shopBalance.getShortName());
		applyGenerator.setShopNo(shopBalance.getShopNo());
		applyGenerator.setShopName(shopBalance.getShortName());
		applyGenerator.setBalanceMonth(shopBalance.getMonth());
		applyGenerator.setBalanceStartDate(shopBalance.getBalanceStartDate());
		applyGenerator.setBalanceEndDate(shopBalance.getBalanceEndDate());
		applyGenerator.setBrandNo(shopBalance.getBrandNo());
		applyGenerator.setBrandName(shopBalance.getBrandName());
//		applyGenerator.setBrandUnitNo(shopBalance.getBrandUnitNo());
//		applyGenerator.setBrandUnitName(shopBalance.getBrandUnitName());
		applyGenerator.setCategoryNo(shopBalance.getCategoryNo());
		applyGenerator.setCategoryName(shopBalance.getCategoryName());
		
		applyGenerator.setAmount(shopBalance.getMallBillingAmount());//商场开票金额
		
		applyGenerator.setCreateUser(shopBalance.getCreateUser());
		applyGenerator.setCreateTime(shopBalance.getCreateTime());
		
		/****************** wang.yj ************************/
		applyGenerator.setOrganNo(shopBalance.getOrganNo());
		applyGenerator.setOrganName(shopBalance.getOrganName());
		return applyGenerator;
	}
    
	private List<BalanceInvoiceApplyGenerator> convertBalancesToApplyGenerators(List<BillBalance> billBalances) {
		
		List<BalanceInvoiceApplyGenerator> retApplyGenerators = new ArrayList<BalanceInvoiceApplyGenerator>();
		
		if (CollectionUtils.isEmpty(billBalances)) {
			return retApplyGenerators;
		}
		
		for (BillBalance billBalance : billBalances) {
			
			BalanceInvoiceApplyGenerator applyGenerator = convertBalanceToApplyGenerator(billBalance);
			retApplyGenerators.add(applyGenerator);
		}
		
		return retApplyGenerators;
	}

	private BalanceInvoiceApplyGenerator convertBalanceToApplyGenerator(BillBalance billBalance) {
		BalanceInvoiceApplyGenerator applyGenerator = new BalanceInvoiceApplyGenerator();
		applyGenerator.setBillNo(billBalance.getBillNo());
		applyGenerator.setBalanceType(billBalance.getBalanceType());
		applyGenerator.setBalanceTypeName(BalanceTypeEnums.getTypeNameByNo(billBalance.getBalanceType()));
		applyGenerator.setStatus(billBalance.getStatus());
		if (BalanceTypeEnums.AREA_OTHER.getTypeNo() == applyGenerator.getBalanceType()) {
			applyGenerator.setStatusName(AreaOtherOutBalanceStatusEnums.getTypeName(billBalance.getStatus()));
		} else if (BalanceTypeEnums.AREA_AMONG.getTypeNo() == applyGenerator.getBalanceType()) {
			applyGenerator.setStatusName(AreaAmongBalanceStatusEnums.getTypeName(billBalance.getStatus()));
		} else {
			applyGenerator.setStatusName(billBalance.getStatusName());
		}
		applyGenerator.setBuyerNo(billBalance.getBuyerNo());
		applyGenerator.setBuyerName(billBalance.getBuyerName());
		applyGenerator.setSalerNo(billBalance.getSalerNo());
		applyGenerator.setSalerName(billBalance.getSalerName());
		applyGenerator.setBalanceDate(billBalance.getBalanceDate());
		applyGenerator.setBalanceStartDate(billBalance.getBalanceStartDate());
		applyGenerator.setBalanceEndDate(billBalance.getBalanceEndDate());
		applyGenerator.setBrandNo(billBalance.getBrandNo());
		applyGenerator.setBrandName(billBalance.getBrandName());
		applyGenerator.setBrandUnitNo(billBalance.getBrandUnitNo());
		applyGenerator.setBrandUnitName(billBalance.getBrandUnitName());
		applyGenerator.setCategoryNo(billBalance.getCategoryNo());
		applyGenerator.setCategoryName(billBalance.getCategoryName());
		
		//开票金额
		applyGenerator.setAmount(billBalance.getBalanceAmount());
		//其他扣项金额
		applyGenerator.setDeductionAmount(billBalance.getDeductionAmount());
		
		applyGenerator.setCreateUser(billBalance.getCreateUser());
		applyGenerator.setCreateTime(billBalance.getCreateTime());
		applyGenerator.setOrganNo(billBalance.getOrganNoFrom());
		applyGenerator.setOrganName(billBalance.getOrganNameFrom());
		SimpleDateFormat format = new SimpleDateFormat("YYYYMM");
		if(null != billBalance.getBalanceEndDate()){
			applyGenerator.setBalanceMonth(format.format(billBalance.getBalanceEndDate()));
		}
		
		return applyGenerator;
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
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		//设置查询条件
   		setCommonParams(params);
   		List<BalanceInvoiceApplyGenerator> list = orderMainManager.findApplyGeneratorDetail(page, params);
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}
}