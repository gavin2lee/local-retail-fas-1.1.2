package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.PaidTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillPrePaymentNt;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillPrePaymentNtManager;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 预收款缴款通知单
 * @author admin
 * @date  2014-09-22 12:14:38
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
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/bill_pre_payment_nt")
@ModuleVerify("30160006") 
public class BillPrePaymentNtController extends BaseController<BillPrePaymentNt> {
    
	@Resource
    private BillPrePaymentNtManager billPrePaymentNtManager;

	@Resource
    private BillSaleBalanceManager billSaleBalanceManager;
	
    @Resource
    private CommonUtilController commonUtilController;
    
    @Resource
    private FinancialAccountManager financialAccountManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_pre_payment_nt/",billPrePaymentNtManager);
    }
    
    @RequestMapping("/bill_tab")
    public String billTab() {
    	return "bill_pre_payment_nt/bill_tab";
    }
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String isHq = req.getParameter("isHq");
		if(StringUtils.isNotBlank(isHq)){
			mav.addObject("isHq", isHq);
		}
		mav.setViewName("bill_pre_payment_nt/list");
		return mav;
	}
    
	/**
	 * 转到查询发票信息
	 * @return
	 */
	@RequestMapping("/toSearchPrePaymentNt")
	public ModelAndView toSearchPrePaymentNt(HttpServletRequest req) {
		String buyerNo = StringUtils.isEmpty(req.getParameter("buyerNo")) ? "" : String.valueOf(req.getParameter("buyerNo"));
		String salerNo = StringUtils.isEmpty(req.getParameter("salerNo")) ? "" : String.valueOf(req.getParameter("salerNo"));
		ModelAndView view=new ModelAndView();
		view.setViewName("base_setting/pre_payment_nt/searchPrePaymentNt");
		view.addObject("buyerNo", buyerNo);
		view.addObject("salerNo", salerNo);
		return view;
	}
	
	/**
	 * 转到查询团购订单信息
	 * @return
	 */
	@RequestMapping("/toSearchBillOrder")
	public ModelAndView toSearchBillOrder(HttpServletRequest req) {
		String buyerNo = StringUtils.isEmpty(req.getParameter("buyerNo")) ? "" : String.valueOf(req.getParameter("buyerNo"));
		String salerNo = StringUtils.isEmpty(req.getParameter("salerNo")) ? "" : String.valueOf(req.getParameter("salerNo"));
		ModelAndView view=new ModelAndView();
		view.addObject("buyerNo", buyerNo);
		view.addObject("salerNo", salerNo);
		view.setViewName("base_setting/bill_order/searchBillOrder");
		return view;
	}
	
	/**
	 * 
	 */
	@RequestMapping("/calcPrePaymentTotal")
	@ResponseBody
	public BigDecimal calcPrePaymentTotal(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> params = builderParams(req, model);
		BillPrePaymentNt billPrePaymentNt = billPrePaymentNtManager.calcPrePaymentTotal(params);
		return billPrePaymentNt.getPaidAmount();
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String isHq = StringUtils.isEmpty(req.getParameter("isHq")) ? "" : String.valueOf(req.getParameter("isHq"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND COMPANY_NO IN (" + companyNos + ")");
		}
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		int total = billPrePaymentNtManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<Object> list = billPrePaymentNtManager.findByPage(page, sortColumn, sortOrder, params);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
    
    /**
     * 校验数据
     * @param request HttpServletRequest
     * @return  Map<String, Object>
     * @throws Exception  异常
     */
    @RequestMapping(value="/validate_data", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validateData(HttpServletRequest request)
    		throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	String id = request.getParameter("id");
    	String billNo = request.getParameter("billNo");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		List<BillPrePaymentNt> list = billPrePaymentNtManager.findByBiz(null, params);
		if(list != null && list.size() != 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "单据编码不能重复");
			return result;
		}
		result.put("success", true);
		return result;
    }
    
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String companyNos = null;
		String isHq = req.getParameter("isHq");
		String[] fieldNames= new String[]{"companyNo","customerNo","paidType","saleOrderNo","paidAmount","paidDate"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "companyNo", "companyName", "公司编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.CUSTOMER.getTypeNo(), "customerNo", "customerName","客户编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "paidType", "","收款类型", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "paidAmount", "","实收金额", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "paidDate", "","收款日期", true));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), BillPrePaymentNt.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, BillPrePaymentNt.class);
		if("true".equals(isHq)){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else {
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				BillPrePaymentNt dtl = (BillPrePaymentNt)resultVo.getValidateObj();
				if( StringUtils.isNotEmpty(companyNos) && companyNos.indexOf("'"+dtl.getCompanyNo()+"'")==-1) {
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo("没有该公司权限!");
				}else {
					SystemUser currUser = CurrentUser.getCurrentUser(req);
					dtl.setCreateUser(currUser.getUsername());
					dtl.setAuditStatus(0);
					dtl.setCreateTime(new Date());
					if(dtl.getPaidType().intValue() == PaidTypeEnums.ORDER.getTypeNo()){
						params.put("salerNo", dtl.getCompanyNo());
						params.put("buyerNo", dtl.getCustomerNo());
						params.put("billNo", dtl.getSaleOrderNo());
						SimplePage page = new SimplePage(1, 1, 1); 
						List<SaleOrderDto> lst = billSaleBalanceManager.selectSaleOrderByPage(page, "", "", params);
						if(lst.size() > 0){
							dtl.setTermName(lst.get(0).getTermName());
							dtl.setOrderAmount(lst.get(0).getAmount());
							billPrePaymentNtManager.add(dtl);
						}else{
							resultVo.setPass(YesNoEnum.NO.getValue());
							resultVo.setErrorInfo("批发订单编号无效!");
						}
					}else{
						dtl.setSaleOrderNo(null);
						billPrePaymentNtManager.add(dtl);
					}
				}
			}
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	}
	
    /**
	 * 审核
	 * @param request HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException 异常
	 */
	@RequestMapping("/do_audit")
	@ResponseBody
	public Boolean doAduit(HttpServletRequest request) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {});
			List<BillPrePaymentNt> oList = convertListWithTypeReference(mapper, list, request);
			int auditVal = StringUtils.isEmpty(request.getParameter("auditVal")) 
					? FasAduitStatusEnum.ADUIT_STATUS.getValue() 
					: Integer.parseInt(request.getParameter("auditVal"));
			if(oList != null && oList.size() > 0) {
				SystemUser systemUser = CurrentUser.getCurrentUser(request);
				for(BillPrePaymentNt model : oList) {
					model.setAuditStatus(auditVal);
					model.setAuditor(systemUser.getUsername());
					model.setAuditTime(new Date());
				}
				billPrePaymentNtManager.doAudit(oList);
			}
			return true;
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	protected List<BillPrePaymentNt> queryExportData(Map<String, Object> params) throws ManagerException {
		int total = billPrePaymentNtManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		String isHq = StringUtils.isEmpty(params.get("isHq")+"") ? "" : String.valueOf(params.get("isHq"));
		String companyNos = null;
		if(isHq.equals("true")){
			companyNos = financialAccountManager.findLeadRoleCompanyNos();
		}else{
			companyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		}
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND COMPANY_NO IN (" + companyNos + ")");
		}
		if(params.get("multiCustomerNo")!=null && !"".equals(params.get("multiCustomerNo"))){
			params.put("customerNoArray", String.valueOf(params.get("multiCustomerNo")).split(","));
		}
		List<BillPrePaymentNt> list = billPrePaymentNtManager.findByPage(page, orderByField, orderBy, params);
		return list;
	}
}