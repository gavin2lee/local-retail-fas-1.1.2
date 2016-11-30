package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import scala.Array;

import cn.wonhigh.retail.fas.common.dto.BillSaleBalanceDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillBalanceHQManager;
import cn.wonhigh.retail.fas.manager.BillBalanceZoneManager;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.gms.common.enums.BillTypeEnums;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 解冻退货金额
 * 
 * @author
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/unfrozen_amount")
@ModuleVerify("30160023")
public class UnfrozenAmountController extends BaseCrudController<BillBalance> {

	private Logger logger = Logger.getLogger(UnfrozenAmountController.class);

	@Resource
	private BillBalanceZoneManager billBalanceZoneManager;

	@Resource
	private BillSaleBalanceManager billSaleBalanceManager;

	@Resource
	private BillBalanceHQManager billBalanceHQManager;

	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Resource
	private CommonUtilController commonUtilController;

	@Override
	public CrudInfo init() {
		return new CrudInfo("unfrozen_amount/", billBalanceZoneManager);
	}
	
    /**
     * 进入解冻明细页面
     * @param request HttpServletRequest
     * @return viewName
     */
    @RequestMapping("/listBillDtl")
    public ModelAndView listBillDtl(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
		String billNoMenu = request.getParameter("billNoMenu");
		if(StringUtils.isNotBlank(billNoMenu)){
			mav.addObject("billNoMenu", billNoMenu);
		}
		String isHq = request.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("unfrozen_amount/list_bill_dtl");
		return mav;
    }

    @RequestMapping(value = "/enter_list.json")
	@ResponseBody
	public Map<String, Object> queryEnterList(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<Object> list = new ArrayList<Object>();
		List<Object> footerList = new ArrayList<Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req
				.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		params.put("queryCondition", "and send_date >= '2016-07-01'");
		setQueryCondition(params);
		params.put("billType",BillTypeEnums.SALEOUT.getRequestId());
		params.put("multiBizType", "("+ BizTypeEnums.CUSTOMER_RETURN.getStatus() +","+ BizTypeEnums.WHOLESALE_RECALL.getStatus() +")");
		
		total = billBalanceHQManager.selectEnterCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = billBalanceHQManager.selectEnterList(page, sortColumn, sortOrder, params);
			footerList = billBalanceHQManager.selectEnterFooter(params);
		}
		map.put("total", total);
		map.put("rows", billBalanceZoneManager.setExtendsBillSaleBalanceProperties(list));
		map.put("footer", footerList);
		return map;
	}

	/**
	 * 设置查询条件
	 * @param params
	 */
	private Map<String, Object> setQueryCondition(Map<String, Object> params) {
		if (null != params.get("balanceType")) {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			String queryCondition = params.get("queryCondition") == null ? "" : String.valueOf(params
					.get("queryCondition"));
			queryCondition = queryCondition.concat(BalanceTypeEnums.getQueryConditionByNo(iBalanceType));
			String companyNos = financialAccountManager.findLeadRoleCompanyNos();
			if (iBalanceType == BalanceTypeEnums.AREA_WHOLESALE.getTypeNo()) {
				String isHq = StringUtils.isEmpty(params.get("isHq") + "") ? "" : String.valueOf(params.get("isHq"));
				if (!isHq.equals("true")) {
					companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
				}
				if (StringUtils.isNotEmpty(companyNos)) {
					queryCondition = queryCondition.concat(" AND SALER_NO IN (" + companyNos + ")");
				}
			} else {
				if (iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {
					queryCondition = queryCondition.concat(" AND buyer_no IN (" + companyNos + ")");
				} else if (iBalanceType == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()) {
					queryCondition = queryCondition.concat(" AND saler_no IN (" + companyNos
							+ ") AND buyer_no NOT IN (" + companyNos + ")");
				} else if (iBalanceType == BalanceTypeEnums.HQ_OTHER.getTypeNo()) {
					queryCondition = queryCondition.concat(" AND buyer_no IN (" + companyNos
							+ ")  AND saler_no NOT IN (" + companyNos + ")");
				}
				params.put("zoneCompanyNo", companyNos);
			}
			params.put("queryCondition", queryCondition);
		}
		if (null != params.get("multiBrandNo")) {
			params.put("multiBrandNo", FasUtil.formatInQueryCondition(params.get("multiBrandNo").toString()));
		}
		return params;
	}

	/**
	 * 解冻退货金额
	 * @param req
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ManagerException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/unfrozen")
	public ResponseEntity<Map<String, Boolean>> unfrozenAmount(HttpServletRequest req) throws JsonParseException,
			JsonMappingException, IOException, ManagerException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map resultMap = new HashMap();
		Map<CommonOperatorEnum, List<BillSaleBalanceDto>> params = null;
		JsonUtil<BillSaleBalanceDto> util = new JsonUtil<BillSaleBalanceDto>();
		try {
			params = util.convertToMap(req, BillSaleBalanceDto.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("false", Boolean.valueOf(false));
			return new ResponseEntity(resultMap, HttpStatus.OK);
		}
		List<BillSaleBalanceDto> billList = params.get(CommonOperatorEnum.UPDATED);
		List<BillSaleBalance> list = new ArrayList<BillSaleBalance>();
		for (BillSaleBalanceDto billSaleBalanceDto : billList) {
			BillSaleBalance billSaleBalance = new  BillSaleBalance();
			PropertyUtils.copyProperties(billSaleBalance, billSaleBalanceDto);
			billSaleBalance.setUnfrozenQty(Math.abs(billSaleBalance.getSendQty()) - billSaleBalance.getReceiveQty());
			billSaleBalance.setReceiveQty(Math.abs(billSaleBalance.getSendQty()));
			billSaleBalance.setStatus(GmsBillStatusEnums.UNFROZEN.getStatus());
			list.add(billSaleBalance);
		}
        
		Boolean result = billBalanceZoneManager.unfrozenAmount(list);
		if (result) {
			flag.put("success", result);
		} else {
			flag.put("false", result);
		}

		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}

	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export_sale_dtl")
	public void exportSaleDtl(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		List<Map> dataMapList = ExportUtils.getDataList(this.getDataList(req, model));
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}

	/**
	 * 获取导出数据
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private List getDataList(HttpServletRequest req, Model model) throws Exception {
		List dataList = null;
		List footerList = null;
		int pageNumber = Integer
				.parseInt(req.getParameter("pageNumber") == null ? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null ? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		params.put("billType", BillTypeEnums.SALEOUT.getRequestId());
		params.put("multiBizType", "("+ BizTypeEnums.CUSTOMER_RETURN.getStatus() +","+ BizTypeEnums.WHOLESALE_RECALL.getStatus() +")");
		params.put("queryCondition", "and send_date >= '2016-07-01'");
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		dataList = billBalanceHQManager.selectEnterList(page, "", "", setQueryCondition(params));
		List<BillSaleBalance> retList = billBalanceZoneManager.setExtendsBillSaleBalanceProperties(dataList);
		for (BillSaleBalance saleBalance : retList) {
			int bizType = saleBalance.getBizType();
			if (bizType == BizTypeEnums.WHOLESALE.getStatus()) {
				saleBalance.setBizTypeName("批发销售");
			} else if (bizType == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus()) {
				saleBalance.setBizTypeName("批发销售(店出)");
			} else if (bizType == BizTypeEnums.WHOLESALE_RETURN.getStatus()) {
				saleBalance.setBizTypeName("过季退货");
			} else if (bizType == BizTypeEnums.CUSTOMER_RETURN.getStatus()) {
				saleBalance.setBizTypeName("客残退货");
			}else if (bizType == BizTypeEnums.WHOLESALE_RECALL.getStatus()) {
				saleBalance.setBizTypeName("召回退货");
			}
		}
		footerList = billBalanceHQManager.selectEnterFooter(params);
		if (!CollectionUtils.isEmpty(footerList) && !CollectionUtils.isEmpty(dataList)) {
			dataList.add(footerList.get(0));
		}
		return dataList;
	}

	/**
	 * 导入解冻退货单金额
	 * @param request
	 * @param modelType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object> doImport(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();	 
		Class<BillSaleBalance> entityClass = (Class<BillSaleBalance>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		// 忽略在excel有字段填写的值
		List<String> ignoreProperties = Lists.newArrayList();
		for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
			ignoreProperties.add(entry.getKey());
		}
		//导入解冻的明细
		List<BillSaleBalance> unfrozenList = ExcelImporterUtils.importSheet(file.getInputStream(), BillSaleBalance.class,entityClass.getSimpleName());
		if (null == unfrozenList || unfrozenList.size() == 0) {
			map.put("success", false);
			map.put("data", unfrozenList);
			return map;
		}
	    List<String> originalBillNos = new ArrayList<String>();
		List<Object> list = new ArrayList<Object>();
		for (Object object : unfrozenList) {
			list.add(object);
		}
		for (BillSaleBalance bill : unfrozenList) {
			originalBillNos.add(bill.getBillNo());    
		}
		//根据导入的单号信息查询出所有冻结明细
		params.put("originalBillNos", originalBillNos);
		params.put("billType", BillTypeEnums.SALEOUTS.getRequestId());
		params.put("multiBizType", "(30,43)");
		params.put("status", GmsBillStatusEnums.FROZEN.getStatus());
		List<BillSaleBalance> frozenList = billSaleBalanceManager.findByBiz(null, params);
        //解冻明细结果集
		List<BillSaleBalance> unfrozenTempList = new ArrayList<BillSaleBalance>(); 
		//校验集
		List<ValidateResultVo> listValidate = commonUtilController.doValidate(list, lstValidate, BillSaleBalance.class);
		
		String isHq = request.getParameter("isHq");
		String companyNos = null;
		String group = "";
		if(null != isHq && isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
			group = "总部公司";
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
			group = "地区公司";
		}
		String companys = "#"+companyNos+"#";
		// 数据校验
		for (ValidateResultVo resultVo : listValidate) {
			getDataValidators(resultVo, unfrozenTempList, unfrozenList,frozenList, companys, group);
		}	
		for (ValidateResultVo resultVo : listValidate) {
			if (resultVo.getPass()  == YesNoEnum.NO.getValue()) {
				for (ValidateResultVo resultV : listValidate) {
					resultV.setPass(YesNoEnum.NO.getValue());
				}
				map.put("success", true);
				map.put("data", listValidate);
				return map;
			}
		}
		billBalanceZoneManager.unfrozenAmount(unfrozenTempList);
		map.put("success", true);
		map.put("data", listValidate);
		return map;

	}

	/**
	 * 校验导入的数据
	 * @param resultVo
	 * @param unfrozenTempList
	 * @param unfrozenList
	 * @param frozenList
	 * @param companyList
	 * @param group
	 */

	private void getDataValidators(ValidateResultVo resultVo, List<BillSaleBalance> unfrozenTempList, List<BillSaleBalance> unfrozenList, List<BillSaleBalance> frozenList, String companyList, String group){
		if (resultVo.getPass() == YesNoEnum.NO.getValue()) {
			return;
		}
		try {
			BillSaleBalance billSaleBalance = (BillSaleBalance) resultVo.getValidateObj();  
			StringBuffer errorMessage = new StringBuffer();
			if (null == billSaleBalance.getReturnNo() || null == billSaleBalance.getBillNo() || null == billSaleBalance.getReceiveAmount()
					|| null == billSaleBalance.getItemCode() || null == billSaleBalance.getUnfrozenQty()) {
				errorMessage.append("导入有空值");
				resultVo.setPass(YesNoEnum.NO.getValue());
				resultVo.setErrorInfo(errorMessage.toString());
				return;
			}
			int flag = 0;
			//检查单号和商品编码相同的数据
			for (BillSaleBalance billSaleBalance2 : unfrozenList) {
				if (billSaleBalance.getBillNo().equals(billSaleBalance2.getBillNo()) 
						&& billSaleBalance.getItemCode().equals(billSaleBalance2.getItemCode())) {
					flag++;
				}
			}
			if (flag > 1) {
				errorMessage.append("存在单号和商品编码相同的数据");
			}
			//检查退货单
			BillSaleBalance billSaleBalanceT = null;
			boolean isExist = false;
			for (BillSaleBalance bill : frozenList) {
				if (bill.getBillNo().equals(billSaleBalance.getBillNo()) && bill.getItemCode().equals(billSaleBalance.getItemCode())) {
					billSaleBalanceT = bill;
					isExist=true;
					break;
				}
			}
			if (!isExist) {
				if (StringUtils.isNotEmpty(errorMessage.toString())) {
					errorMessage.append("&");
				}
				errorMessage.append("退货单不存在或者已经解冻");
				resultVo.setPass(YesNoEnum.NO.getValue());
				resultVo.setErrorInfo(errorMessage.toString());
				return;
			} 
			
			String salerNo = billSaleBalanceT.getSalerNo();
			if(companyList.split(salerNo).length <= 1) {
				if (StringUtils.isNotEmpty(errorMessage.toString())) {
					errorMessage.append("&");
				}
				errorMessage.append("查询出退货单的结算公司不是").append(group);
			}
			
			int frozen = Math.abs(billSaleBalanceT.getSendQty());//冻结数量
			int unfrozen = Math.abs(billSaleBalanceT.getReceiveQty());//已解冻数量
			int beFrozen = Math.abs(billSaleBalance.getUnfrozenQty());//要解冻数量
			//解冻数量+要解冻的数量小于冻结数量
			if ((unfrozen + beFrozen) <= frozen ) {
				billSaleBalanceT.setReceiveQty(unfrozen + beFrozen);
				billSaleBalanceT.setUnfrozenQty(beFrozen);
				if ((unfrozen + beFrozen) == frozen) {
					billSaleBalanceT.setStatus(GmsBillStatusEnums.UNFROZEN.getStatus());
				}
			}else{
				if (StringUtils.isNotEmpty(errorMessage.toString())) {
					errorMessage.append("&");
				}
				errorMessage.append("已解冻数量+要解冻的数量大于冻结数量");
			}
			if (StringUtils.isNotEmpty(errorMessage.toString())) {
				resultVo.setPass(YesNoEnum.NO.getValue());
				resultVo.setErrorInfo(errorMessage.toString());
			}else{
				billSaleBalanceT.setReturnNo(billSaleBalance.getReturnNo());
				unfrozenTempList.add(billSaleBalanceT);
			}
		} catch (Exception e) {
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("请检查数据是否有效性)");
		}
	}	

//	/**
//	 * 判断保存数据中是否存在相同退货单号数据
//	 * @param list
//	 * @return
//	 */
//	private String validateMultipleBillNo(List<BillSaleBalance> list) {
//		boolean istrue = false;
//		String errorStr = "";
//		for (int i = 0; i < list.size() - 1; i++) {
//			BillSaleBalance billOne = list.get(i);
//			for (int j = i + 1; j < list.size(); j++) {
//				BillSaleBalance billTwo = list.get(j);
//				if (billOne.getOrderNo().equals(billTwo.getOrderNo())
//						|| billOne.getItemNo().equals(billTwo.getItemNo())) {
//					istrue = true;
//					break;
//				}
//			}
//			if (istrue) {
//				break;
//			}
//		}
//		if (istrue) {
//			errorStr = "保存数据中不能存在退货单号相同的多条数据。";
//		}
//		return errorStr;
//	}
	
}
