package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.manager.BillSalesSumManager;
import cn.wonhigh.retail.fas.manager.BillSalesSumManagerImpl;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-11 15:51:08
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
@RequestMapping("/bill_sales_sum")
@ModuleVerify("30510402")
public class BillSalesSumController extends BaseController<BillSalesSum> {
	@Resource
	private BillSalesSumManager billSalesSumManager;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
 
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_sales_sum/",billSalesSumManager);
	}
	
	@RequestMapping(method = RequestMethod.GET ,value = "/list")
    public ModelAndView listTab(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
		String senda = req.getParameter("senda");
		if(StringUtils.isNotBlank(senda)){
			mav.addObject("senda", senda);
		}
		mav.setViewName("report/sales_sum_list");
		return mav;
    }
	
	/**
	 * 店铺结算期检查
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/checkShopBalanceDate")
	@ResponseBody
	public Map<String, Object> checkShopBalanceDate(HttpServletRequest req, Model model) throws ManagerException {
		
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<BillSalesSum> list = billSalesSumManager.checkShopBalanceDate(params);
		
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
//		String text = req.getParameter("retailType");
		Map<String, Object> obj = new HashMap<String, Object>();
		
		//格式化参数
		params = BillSalesSumManagerImpl.formatParams(params);
		
		int totalPos = 0;
		int totalGms = 0;
		int totalOther = 0;
		int total = 0;
		BillSalesSum posSum = null;
		BillSalesSum gmsSum = null;
		BillSalesSum otherSum = null;
		
		List<BillSalesSum> list = new ArrayList<BillSalesSum>();
		
		//查询门店统计
		if((Boolean) params.get("isPos")) {
			posSum = this.billSalesSumManager.selectSalesSumPosCount(params,null);
			totalPos = posSum.getCount();
		}
		else {
			totalPos = 0;
		}
		//查地区统计
		if((Boolean) params.get("isGms")) {
			params.put("leadRoleCompanyNos", financialAccountManager.findLeadRoleCompanyNos());
			gmsSum = this.billSalesSumManager.selectSalesSumGmsCount(params,null);
			totalGms = gmsSum.getCount();
		}
		else {
			totalGms = 0;
		}
		//查询内购统计
		if((Boolean) params.get("isOther")) {
			otherSum = this.billSalesSumManager.selectSalesSumOtherCount(params,null);
			totalOther = otherSum.getCount();
		}
		else {
			totalOther = 0;
		}
		
		total = totalPos + totalGms + totalOther;
		
		//查询起始下标
		long startRowNum = (pageNo - 1) * pageSize;
		
		//是否查询门店的数据
		if(list.size() < pageSize && totalPos > 0 
				&& startRowNum < totalPos) {
			params.put("startRowNum", Long.valueOf(startRowNum));
			params.put("selectCount", Integer.valueOf(pageSize));
			List<BillSalesSum> listPos = this.billSalesSumManager.selectSalesSumPos(null, sortColumn, sortOrder, params,null);
			list.addAll(listPos);
			startRowNum += listPos.size();
		}
		
		//是否查询地区的数据
		if(list.size() < pageSize && totalGms > 0
				&& startRowNum >= totalPos && startRowNum < totalPos + totalGms) {
			params.put("startRowNum", Long.valueOf(startRowNum - totalPos));
			params.put("selectCount", Integer.valueOf(pageSize - list.size()));
			List<BillSalesSum> listGms = this.billSalesSumManager.selectSalesSumGms(null, sortColumn, sortOrder, params,null);
			list.addAll(listGms);
			startRowNum += listGms.size();
		}
		
		//是否查询内购的数据
		if(list.size() < pageSize && totalOther > 0
				&& startRowNum >= totalPos + totalGms && startRowNum < totalPos + totalGms + totalOther){
			params.put("startRowNum", Long.valueOf(startRowNum - totalPos - totalGms));
			params.put("selectCount", Integer.valueOf(pageSize - list.size()));
			List<BillSalesSum> listOther = this.billSalesSumManager.selectSalesSumOther(null, sortColumn, sortOrder, params,null);
			list.addAll(listOther);
			startRowNum += listOther.size();
		}
		
		//字段处理
		list = BillSalesSumManagerImpl.areaTailor(list);
		
		BillSalesSum totalSum = null;
		List<BillSalesSum> listSum = new ArrayList<BillSalesSum>();
		
		//物料是否显示成本
		if(params.get("isCost") != null && "false".equals(params.get("isCost").toString())) {
			list = BillSalesSumManagerImpl.isShowCost(list);
		}
		
		//按大类统计
		BillSalesSum categoryTemp = null;
		Map<String, BillSalesSum> categorySumMap = new LinkedHashMap<String, BillSalesSum>();
		for(BillSalesSum billSalesSum : list) {
			
			if(null == categorySumMap.get(billSalesSum.getCategoryName())) {
				categoryTemp = new BillSalesSum();
				BillSalesSumManagerImpl.initBillSalesSum(categoryTemp);
				categoryTemp.setZoneName("合计：");
				categoryTemp.setCategoryName(billSalesSum.getCategoryName());
				categorySumMap.put(billSalesSum.getCategoryName(), categoryTemp);
			}
			
			BillSalesSumManagerImpl.sumToBillSalesSum(categorySumMap.get(billSalesSum.getCategoryName()), billSalesSum);
			
		}
		
		//是否显示小计
		List<BillSalesSum> countlist = new ArrayList<BillSalesSum>();
		if(params.get("countType") != null && !"".equals(params.get("countType").toString())) {
			//小计
			list = BillSalesSumManagerImpl.littleCount(list, Integer.valueOf(params.get("countType").toString()), countlist);
		}
		
		//总计
		if(total > 0) {
			totalSum = new BillSalesSum();
			BillSalesSumManagerImpl.initBillSalesSum(totalSum);
			totalSum.setBalanceQty(null);
			totalSum.setBalanceQtyAmount(null);
			totalSum.setBalanceQtyRegionAmount(null);
			totalSum.setBalanceQtyHeadquarterAmount(null);
			totalSum.setBiPeriodPredictiondeductions(null);
			totalSum.setBiPeriodMalldeductions(null);
			totalSum.setTmPredictiondeductions(null);
			totalSum.setZoneName("总计：");
			if(null != posSum && posSum.getCount() > 0) {
				BillSalesSumManagerImpl.sumToBillSalesSum(totalSum, posSum);
			}
			if(null != gmsSum && gmsSum.getCount() > 0) {
				BillSalesSumManagerImpl.sumToBillSalesSum(totalSum, gmsSum);
			}
			if(null != otherSum && otherSum.getCount() > 0) {
				BillSalesSumManagerImpl.sumToBillSalesSum(totalSum, otherSum);
			}
		}
		
		//添加按大类汇总至底部
		for(Iterator<String> it = categorySumMap.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			list.add(categorySumMap.get(key));
		}
		listSum.add(totalSum);
		
		obj.put("total", total);
		//是否仅显示小计
		if(params.get("onlyCount") != null && "true".equals(params.get("onlyCount").toString())) {
			obj.put("rows", countlist);
		}
		else {
			obj.put("rows", list);
		}
		obj.put("footer", listSum);
		return obj;
	}
	
	protected List<BillSalesSum> queryExportData(Map<String, Object> params) throws ManagerException {
		
		String sortColumn = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String sortOrder = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		
		//格式化参数
		params = BillSalesSumManagerImpl.formatParams(params);
		
		List<BillSalesSum> list = new ArrayList<BillSalesSum>();
		
		if((Boolean) params.get("isPos")) {
			List<BillSalesSum> listPos = this.billSalesSumManager.selectSalesSumPos(null, sortColumn, sortOrder, params,null);
			list.addAll(listPos);
		}
		if((Boolean) params.get("isGms")) {
			params.put("leadRoleCompanyNos", financialAccountManager.findLeadRoleCompanyNos());
			List<BillSalesSum> listGms = this.billSalesSumManager.selectSalesSumGms(null, sortColumn, sortOrder, params,null);
			list.addAll(listGms);
		}
		if((Boolean) params.get("isOther")) {
			List<BillSalesSum> listOther = this.billSalesSumManager.selectSalesSumOther(null, sortColumn, sortOrder, params,null);
			list.addAll(listOther);
		}
		
		//字段处理
		list = BillSalesSumManagerImpl.areaTailor(list);
		
		//物料是否显示成本
		if(params.get("isCost") != null && "false".equals(params.get("isCost").toString())) {
			list = BillSalesSumManagerImpl.isShowCost(list);
		}
		
		//按大类统计
		BillSalesSum categoryTemp = null;
		Map<String, BillSalesSum> categorySumMap = new LinkedHashMap<String, BillSalesSum>();
		for(BillSalesSum billSalesSum : list) {
			
			if(null == categorySumMap.get(billSalesSum.getCategoryNo())) {
				categoryTemp = new BillSalesSum();
				BillSalesSumManagerImpl.initBillSalesSum(categoryTemp);
				categoryTemp.setZoneName("合计：");
				categoryTemp.setCategoryName(billSalesSum.getCategoryName());
				categorySumMap.put(billSalesSum.getCategoryNo(), categoryTemp);
			}
			
			BillSalesSumManagerImpl.sumToBillSalesSum(categorySumMap.get(billSalesSum.getCategoryNo()), billSalesSum);
			
		}
		
		//总计
		BillSalesSum rowSum = null;
		if(list.size() > 0) {
			rowSum = new BillSalesSum();
			BillSalesSumManagerImpl.initBillSalesSum(rowSum);
			rowSum.setZoneName("总计：");
			for(BillSalesSum billSalesSum : list) {
				BillSalesSumManagerImpl.sumToBillSalesSum(rowSum, billSalesSum);
			}
		}
		
		//是否显示小计
		List<BillSalesSum> countlist = new ArrayList<BillSalesSum>();
		if(params.get("countType") != null && !"".equals(params.get("countType").toString())) {
			//小计
			list = BillSalesSumManagerImpl.littleCount(list, Integer.valueOf(params.get("countType").toString()), countlist);
		}
		
		//添加按大类汇总至底部
		for(Iterator<String> it = categorySumMap.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			list.add(categorySumMap.get(key));
		}
		
		//是否仅显示小计
		if(params.get("onlyCount") != null && "true".equals(params.get("onlyCount").toString())) {
			list = countlist;
		}
		
		//添加合计至底部
		if(rowSum != null) {
			list.add(rowSum);
		}
		
		return list;
	}
	
	/**
	 * 内购结算期检查
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/checkInnerBuyBalanceDate")
	@ResponseBody
	public Map<String, Object> checkInnerBuyBalanceDate(HttpServletRequest req, Model model) throws ManagerException {
		
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<BillSalesSum> list = billSalesSumManager.checkInnerBuyBalanceDate(params);
		
		obj.put("rows", list);
		return obj;
	}
	
	
}