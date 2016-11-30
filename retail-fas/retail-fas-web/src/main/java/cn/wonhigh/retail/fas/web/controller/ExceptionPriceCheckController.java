package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.ExceptionPriceCheckDto;
import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;
import cn.wonhigh.retail.fas.common.model.ExceptionPriceBill;
import cn.wonhigh.retail.fas.common.model.ExceptionPriceCheck;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.ExceptionPriceBillManager;
import cn.wonhigh.retail.fas.manager.ExceptionPriceCheckManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 异常价格检查及更新
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
@RequestMapping("/exception_price_check")
@ModuleVerify("30120033")
public class ExceptionPriceCheckController extends BaseCrudController<ExceptionPriceBill> {
	
    @Resource
    private ExceptionPriceCheckManager exceptionPriceCheckManager;
    
    @Resource
    private ExceptionPriceBillManager exceptionPriceBillManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("exception_price_check/",exceptionPriceBillManager);
    }
    
    @RequestMapping("/fas_bill_check")
	public String listBillInvoiceApply() {
		return "exception_price_check/fas_bill_check";
	}
    
    @RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView listTab(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String checkType = req.getParameter("checkType");
		if(StringUtils.isNotBlank(checkType)){
			mav.addObject("checkType", checkType);
		}
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("exception_price_check/list");
		return mav;
	}
    
	@RequestMapping("/find_exceptionPrice")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> findExceptionPrice(HttpServletRequest req, Model model) throws ManagerException {
		 
		Map<String,Boolean> resultMap = new HashMap<String,Boolean>();
		
		Map<String,Object> params = builderParams(req, model);
		params.put("shardingFlag", constructShardingFlag(params));
		//调用GMS接口，检查出异常单据
		boolean result = exceptionPriceCheckManager.checkExceptionPrice(params); 
		
		resultMap.put("success", result);
		return new ResponseEntity<Map<String, Boolean>>(resultMap,HttpStatus.OK);
	}
	
	@RequestMapping("/query_exception_price")
	@ResponseBody
	public Map<String,Object> queryExceptionPriceBill(HttpServletRequest req, Model model) throws ManagerException{
		
		Map<String,Object> params = builderParams(req, model);
		String flag = req.getParameter("flag")==null ? "" : req.getParameter("flag").toString();
		if(StringUtils.isBlank(flag)){
			params.put("shardingFlag", constructShardingFlag(params));
		}
		//调用GMS接口，查询出异常单据列表
		Map<String,Object> obj = exceptionPriceCheckManager.queryPriceExceptionBillList(params);
		return obj;
	}
	
	private String constructShardingFlag(Map<String,Object> params){
//		List<String> zones = Authorization.getAccessData(DataAccessEnum.ZONE);
		String shardingFlag="";
		String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
		if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){// 集团总部
			shardingFlag = "0_Z";
		}else{
			String companyNo = params.get("companyNo").toString();
			shardingFlag = organTypeNo+"_" + companyNo.substring(0, 1);
		}
		
//		if(zones!=null&&zones.size()>0&&CurrentUser.getCurrentUser()!=null){
//			String organTypeNo = CurrentUser.getCurrentUser().getOrganTypeNo();
//			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){// 集团总部
//				shardingFlagIn += "'0_Z'";
//			}else{
//				for (String zone : zones) {
//					shardingFlagIn+="'"+organTypeNo+"_"+zone+"',";
//				}
//				shardingFlagIn=shardingFlagIn.substring(0,shardingFlagIn.length()-1);
//			}
//		}
		return shardingFlag;
	}
	
	@RequestMapping("/update_exception_price")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> updateExceptionPrice(HttpServletRequest req, Model model) throws ManagerException{
		
		Map<String,Object> params = builderParams(req, model);
		Map<String,Boolean> obj = new HashMap<String,Boolean>();
		//调用GMS接口，更新异常单据
		boolean flag = exceptionPriceCheckManager.updatePriceExceptionBillList(params);
		obj.put("success", flag);
		
		return new ResponseEntity<Map<String,Boolean>>(obj,HttpStatus.OK);
	}
	
	/**
	 * 查询fas没有维护价格商品
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/query_exceptionPrice_local")
	@ResponseBody
	public Map<String,Object> queryExceptionPriceBillList(HttpServletRequest req, Model model) throws ManagerException{
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String billBalanceType = StringUtils.isEmpty(req.getParameter("billBalanceType")) ? "" : req.getParameter("billBalanceType");
		Map<String,Object> params = builderParams(req, model);
		
		if (null != params.get("brandNos") && StringUtils.isNotBlank(params.get("brandNos").toString())) {
			params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
		}
		if (null != params.get("companyNos") && StringUtils.isNotBlank(params.get("companyNos").toString())) {
			params.put("companyNos", FasUtil.formatInQueryCondition(params.get("companyNos").toString()));
		}
		params.put("startDate", DateUtil.parseToDate(req.getParameter("sendDateStart"), "yyyy-MM-dd"));
		params.put("endDate", DateUtil.parseToDate(req.getParameter("sendDateEnd"), "yyyy-MM-dd"));
		
		int total = 0;
		List<ExceptionPriceCheckDto> list = new ArrayList<ExceptionPriceCheckDto>();
		if ("1".equals(billBalanceType)) {
			//地区价异常
			total = exceptionPriceBillManager.findRegionPriceExceptionsCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = exceptionPriceBillManager.findRegionPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		}else if ("2".equals(billBalanceType)) {
			//采购价异常
			total = exceptionPriceBillManager.findPurchasePriceExceptionsCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = exceptionPriceBillManager.findPurchasePriceExceptionsByPage(page, sortColumn, sortOrder, params);
		}else if ("3".equals(billBalanceType)) {
			//牌价异常
			total = exceptionPriceBillManager.findTagPriceExceptionsCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = exceptionPriceBillManager.findTagPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		}else {
			total = exceptionPriceBillManager.findFasAllPriceExceptionsCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = exceptionPriceBillManager.findFasAllPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		}
//		Map<String,Object> obj = exceptionPriceCheckManager.queryExceptionPriceBillList(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);									
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * fas异常价格更新
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/doBatchUpdate_exceptionPrice_local")
	@ResponseBody
	public ResponseEntity<Map<String,Boolean>> doBatchUpdateExceptionPriceBill(HttpServletRequest req, Model model)
		throws ManagerException {
		Map<String,Boolean> resultMap = new HashMap<String,Boolean>();
		if(ShardingUtil.isShoes()){
			Map<String,Object> params = builderParams(req, model);
			String billBalanceType = StringUtils.isEmpty(req.getParameter("billBalanceType")) ? "" : req.getParameter("billBalanceType");
			if (null != params.get("brandNos") && StringUtils.isNotBlank(params.get("brandNos").toString())) {
				params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
			}
			if (null != params.get("companyNos") && StringUtils.isNotBlank(params.get("companyNos").toString())) {
				params.put("companyNos", FasUtil.formatInQueryCondition(params.get("companyNos").toString()));
			}
			params.put("startDate", DateUtil.parseToDate(req.getParameter("sendDateStart"), "yyyy-MM-dd"));
			params.put("endDate", DateUtil.parseToDate(req.getParameter("sendDateEnd"), "yyyy-MM-dd"));
			if ("1".equals(billBalanceType)) {
				//地区价更新，bill_buy_balance, 1301,1304,1333,1372
				//关联更新，bill_sale_balance, 1301,1371
				exceptionPriceBillManager.updateBuyBillRegionPrice(params);
				
				//有差异处理的单据，bill_sale_balance, 1301,1371
				exceptionPriceBillManager.updateSaleBillRegionPrice(params);
			} else if ("2".equals(billBalanceType)) {
				//采购价更新,bill_buy_balance, 1301,1304,1333, 关联更新的bill_sale_balance的1301单据
				exceptionPriceBillManager.updateBuyBillPurchasePrice(params);
			} 
		}
//		exceptionPriceBillManager.batchUpdatePriceSchedule();
		resultMap.put("success", true);
		return new ResponseEntity<Map<String,Boolean>>(resultMap,HttpStatus.OK);
	}
	
	
	/**
	 * 导出gms价格异常检查
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_gms_export")
	public void exportGMS(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		params.put("exports", "yes");
		params.put("shardingFlag", constructShardingFlag(params));
		
		Map<String,Object> obj = exceptionPriceCheckManager.queryPriceExceptionBillList(params);

		ExportUtils.doExport(fileName, exportColumns, (List<ExceptionPriceCheck>)obj.get("rows"), response);
	}
	
	
	/**
	 * 导出fas价格异常检查
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_fas_export")
	public void exportFAS(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		String billBalanceType = params.get("billBalanceType").toString();
		if (null != params.get("brandNos") && StringUtils.isNotBlank(params.get("brandNos").toString())) {
			params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
		}
		if (null != params.get("companyNos") && StringUtils.isNotBlank(params.get("companyNos").toString())) {
			params.put("companyNos", FasUtil.formatInQueryCondition(params.get("companyNos").toString()));
		}
		params.put("startDate", DateUtil.parseToDate(req.getParameter("sendDateStart"), "yyyy-MM-dd"));
		params.put("endDate", DateUtil.parseToDate(req.getParameter("sendDateEnd"), "yyyy-MM-dd"));
		int total = 0;
    	SimplePage page = null;
    	List<ExceptionPriceCheckDto> list = null;
    	
		if ("1".equals(billBalanceType)) {
			//地区价异常
			total = exceptionPriceBillManager.findRegionPriceExceptionsCount(params);
			page = new SimplePage(1, total, (int) total);
			list = exceptionPriceBillManager.findRegionPriceExceptionsByPage(page, orderByField, orderBy, params);
		}else if ("2".equals(billBalanceType)) {
			//采购价异常
			total = exceptionPriceBillManager.findPurchasePriceExceptionsCount(params);
			page = new SimplePage(1, total, (int) total);
			list = exceptionPriceBillManager.findPurchasePriceExceptionsByPage(page, orderByField, orderBy, params);
		}else if ("3".equals(billBalanceType)) {
			//牌价异常
			total = exceptionPriceBillManager.findTagPriceExceptionsCount(params);
			page = new SimplePage(1, total, (int) total);
			list = exceptionPriceBillManager.findTagPriceExceptionsByPage(page, orderByField, orderBy, params);
		}
		
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}
	
}