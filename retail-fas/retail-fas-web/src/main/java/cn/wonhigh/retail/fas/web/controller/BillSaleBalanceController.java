package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
@RequestMapping("/bill_sale_balance")
public class BillSaleBalanceController extends BaseCrudController<BillSaleBalance> {
	
    @Resource
    private BillSaleBalanceManager billSaleBalanceManager;
    
    @Resource
    private BillShopBalanceManager billShopBalanceManager;
    
    @Resource
    private OrderMainManager orderMainManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_sale_balance/",billSaleBalanceManager);
    }
    
	/**
	 * 转到查询结算单信息
	 * @return
	 */
	@RequestMapping("/tosearchSaleOrderBill")
	public ModelAndView tosearchSaleOrderBill(HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("base_setting/bill_balance/searchSaleOrderBill");
		return view;
	}
    
    /**
     * 通过结算单号查询明细数据(开票申请模块的按明细显示页签有使用)
     * @param req HttpServletRequest
     * @param model Model
     * @return Map<String, Object>
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/list_by_balanceNo")
	@ResponseBody
	public Map<String, Object> queryListByBalanceNo(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> obj = new HashMap<String, Object>();
    	String balanceNo = req.getParameter("balanceNos");
    	String balanceType = req.getParameter("balanceType");
    	if(StringUtils.isEmpty(balanceNo) || StringUtils.isEmpty(balanceType)) {
    		obj.put("total", 0);
    		obj.put("rows", new ArrayList<BillSaleBalance>());
    		return obj;
    	}
    	
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	
    	List<BillSaleBalance> list = new ArrayList<BillSaleBalance>();
    	
    	int total = 0;
    	
    	//商场结算单明细查询(通过接口获取pos那边商场结算单信息)
    	if(Integer.parseInt(balanceType) == BalanceTypeEnums.AREA_MALL.getTypeNo()){
    		
        	Map<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put("balanceNos", Arrays.asList(balanceNo.split(",")));
        	List<BillShopBalance> shopBalanceList = billShopBalanceManager.findByBiz(null, paramMap);
    		
    		if(null != shopBalanceList && shopBalanceList.size() > 0){
    			List<ItemSaleDtlDto> dtlList = new ArrayList<ItemSaleDtlDto>();
    			Map<String, Object> params = new HashMap<String, Object>();
    			params.put("pageSize", pageSize);
        		params.put("pageNumber", pageNo);
        		List<Integer> businessTypeList = new ArrayList<Integer>();
    			businessTypeList.add(0);
    			businessTypeList.add(1);
    			businessTypeList.add(2);
    			businessTypeList.add(6);
    			params.put("businessTypeList",businessTypeList);
    			 
				List<Integer> statusList = new ArrayList<Integer>();
				statusList.add(30);
				statusList.add(41);
				params.put("statusList",statusList);
        		SimplePage page = null;
    			for (BillShopBalance billShopBalance : shopBalanceList) {
            		params.put("shopNo", billShopBalance.getShopNo());
            		params.put("startDate", billShopBalance.getBalanceStartDate());
            		params.put("endDate", billShopBalance.getBalanceEndDate());
            		params.put("balanceNo", billShopBalance.getBalanceNo());
            		total = orderMainManager.findOrderDetailCountByOrderNo(params);
            		page = new SimplePage(pageNo, pageSize, total);
            		dtlList = orderMainManager.findOrderDetailByOrderNo(page, params);
				}
        		
        		obj.put("total", total);
        		obj.put("rows", dtlList);
    		}else{
        		obj.put("total", total);
        		obj.put("rows", new ArrayList<ItemSaleDtlDto>());
    		}
    		
//    	}else if(Integer.parseInt(balanceType) == BalanceTypeEnums.AREA_SALEORDER.getTypeNo()){
//    		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
//    		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
//    		Map<String, Object> params = builderParams(req, model);
//    		params.put("originalBillNos", Arrays.asList(balanceNo.split(",")));
//    		params.put("balanceNos", "");
//    		params.put("balanceType", "");
//    		total = this.billSaleBalanceManager.findCount(params);
//    		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
//    		list = this.billSaleBalanceManager.findByPage(page, sortColumn, sortOrder, params);
//    		
//    		if(CollectionUtils.isNotEmpty(list)){
//    			for (int i = 0; i < list.size(); i++) {
//					list.get(i).setSendAmount(list.get(i).getSendQty() == null ? 
//					new BigDecimal(0) : new BigDecimal(list.get(i).getSendQty()).multiply(list.get(i).getCost()));
//				}
//    		}
//    		obj.put("total", total);
//    		obj.put("rows", list);
    	}else{
    		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("balanceNos", Arrays.asList(balanceNo.split(",")));
    		total = this.billSaleBalanceManager.findCount(params);
    		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
    		list = this.billSaleBalanceManager.findByPage(page, sortColumn, sortOrder, params);
    		
    		if(CollectionUtils.isNotEmpty(list)){
    			for (int i = 0; i < list.size(); i++) {
    				if(list.get(i).getBillType().equals(BillTypeEnums.RETURNOWN.getRequestId())){
    					list.get(i).setSendQty(list.get(i).getSendQty() == null ? 0 : -list.get(i).getSendQty());
    					list.get(i).setCost(list.get(i).getCost().multiply(new BigDecimal(-1)));
    					list.get(i).setSendAmount(list.get(i).getSendQty() == null ? 
    							new BigDecimal(0) : new BigDecimal(-list.get(i).getSendQty()).multiply(list.get(i).getCost()));
    				}else{
    					list.get(i).setSendAmount(list.get(i).getSendQty() == null ? 
    							new BigDecimal(0) : new BigDecimal(list.get(i).getSendQty()).multiply(list.get(i).getCost()));
    				}
				}
    		}
    		obj.put("total", total);
    		obj.put("rows", list);
    	}
		return obj;
	}
    
    /**
     * 查询批发订单
     * @param req HttpServletRequest
     * @return Map<String, Object>
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_sale_order")
	@ResponseBody
    public Map<String, Object> querySaleOrder(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		if(params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("billType", BillTypeEnums.SALEORDER.getRequestId());
		String bizType = params.get("bizType") == null ? "" : params.get("bizType").toString();
		if(StringUtils.isNotEmpty(bizType)) {
			params.put("bizType", "," + bizType + ",");
		}
		int total = billSaleBalanceManager.findSaleOrderCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SaleOrderDto> list = billSaleBalanceManager.findSaleOrderByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
    /**
     * 查询批发订单
     * @param req HttpServletRequest
     * @return Map<String, Object>
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_sale_order_detail")
	@ResponseBody
    public Map<String, Object> querySaleOrderDetail(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		if(params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("billType", BillTypeEnums.SALEORDER.getRequestId());
		String billNo = params.get("billNo") == null ? "" : params.get("billNo").toString();
		if(StringUtils.isNotEmpty(billNo)) {
			params.put("billNo", "," + billNo + ",");
		}
		int total = billSaleBalanceManager.selectSaleOrderCounts(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillSaleBalance> list = billSaleBalanceManager.selectSaleOrder(page, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
    /**
     * 获取地区团购订单信息
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/querySaleOrderBill")
	@ResponseBody
    public Map<String, Object> querySaleOrderBill(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String companyNo = StringUtils.isEmpty(req.getParameter("companyNo")) ? "" : String.valueOf(req.getParameter("companyNo"));
		Map<String, Object> params = new HashMap<String, Object>();
		
		//String billType = BillTypeEnums.SALEOUTS.getRequestId()+","+BillTypeEnums.LOSS.getRequestId()+","+BillTypeEnums.CLAIM.getRequestId()+","+BillTypeEnums.SALEOUTS.getRequestId();
		//String bizType = BalanceTypeEnums.AREA_SALEORDER.getTypeNo()+","+BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()+","+BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()+","+BalanceTypeEnums.HQ_WHOLESALE.getTypeNo();
		//params.put("billType", Arrays.asList(BillTypeEnums.SALEORDER.getRequestId()));
		//params.put("bizType", BalanceTypeEnums.AREA_SALEORDER.getTypeNo());
		List<BillSaleBalance> saleBalance = new ArrayList<BillSaleBalance>();
/*		BillSaleBalance saleBalanceDtl = new BillSaleBalance();
		saleBalanceDtl.setBillType(BillTypeEnums.SALEOUTS.getRequestId());
		saleBalanceDtl.setBizType(BalanceTypeEnums.AREA_SALEORDER.getTypeNo());
		saleBalance.add(saleBalanceDtl);*/
		BillSaleBalance saleBalanceDtl1 = new BillSaleBalance();
		saleBalanceDtl1.setBillType(BillTypeEnums.LOSS.getRequestId());
		//saleBalanceDtl1.setBizType(BizTypeEnums.HQ_WHOLESALE.getTypeNo());
		saleBalanceDtl1.setBizType(null);
		saleBalance.add(saleBalanceDtl1);
		BillSaleBalance saleBalanceDtl2 = new BillSaleBalance();
		saleBalanceDtl2.setBillType(BillTypeEnums.CLAIM.getRequestId());
		saleBalanceDtl2.setBizType(BizTypeEnums.CLAIM.getStatus());
		saleBalance.add(saleBalanceDtl2);
		BillSaleBalance saleBalanceDtl3 = new BillSaleBalance();
		saleBalanceDtl3.setBillType(BillTypeEnums.SALEOUTS.getRequestId());
		saleBalanceDtl3.setBizType(3);
		saleBalance.add(saleBalanceDtl3);
		
		BillSaleBalance saleBalanceDtl4 = new BillSaleBalance();
		saleBalanceDtl4.setBillType(BillTypeEnums.CLAIM.getRequestId());
		saleBalanceDtl4.setBizType(BizTypeEnums.FIRST_ORDER_DIFF.getStatus());
		saleBalance.add(saleBalanceDtl4);
		
		List<BillSaleBalance> saleOrderNoList = new ArrayList<BillSaleBalance>();
		
		if(CollectionUtils.isNotEmpty(saleBalance)){
			for (int i = 0; i < saleBalance.size(); i++) {
				params.put("billType", saleBalance.get(i).getBillType());
				params.put("bizType", saleBalance.get(i).getBizType());
				params.put("salerNo", companyNo);
				saleOrderNoList.addAll(billSaleBalanceManager.selectBillSaleBalanceByNo(params));
			}
		}
		
		List<BillSaleBalance> list = new ArrayList<BillSaleBalance>();
		
		List<BillSaleBalance> infoList = new ArrayList<BillSaleBalance>();
		
		if(CollectionUtils.isNotEmpty(saleOrderNoList)){
			for (BillSaleBalance billSaleBalance : saleOrderNoList) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("originalBillNo", billSaleBalance.getOriginalBillNo());
				param.put("salerNo", companyNo);
				list = billSaleBalanceManager.findByBiz(null, param);
				
				BigDecimal allAmount = new BigDecimal(0);
				BillSaleBalance dtl = new BillSaleBalance();
				if(CollectionUtils.isNotEmpty(list)){
					for (int i = 0; i < list.size(); i++) {
						allAmount = allAmount.add(list.get(i).getCost());
					}
				}
				
				dtl.setCost(allAmount);
				dtl.setBuyerName(billSaleBalance.getBuyerName());
				dtl.setBuyerNo(billSaleBalance.getBuyerNo());
				dtl.setSalerName(billSaleBalance.getSalerName());
				dtl.setSalerNo(billSaleBalance.getSalerNo());
				dtl.setOriginalBillNo(billSaleBalance.getOriginalBillNo());
				infoList.add(dtl);
			}
		}
		
		int total = infoList.size();
		List<BillSaleBalance> totalList = new ArrayList<BillSaleBalance>();
		if(null != infoList && infoList.size() > 0){
			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
				totalList.add(infoList.get(i));
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", totalList);
		return obj;
    }
    
    /**
     * 查询批发订单-2015 02 02
     * @param req HttpServletRequest
     * @return Map<String, Object>
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/select_sale_order")
	@ResponseBody
    public Map<String, Object> selectSaleOrder(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billSaleBalanceManager.selectSaleOrderCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SaleOrderDto> list = billSaleBalanceManager.selectSaleOrderByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
    @RequestMapping({"/list.json"})
    @ResponseBody
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
      int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
      int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
      String sortColumn = (StringUtils.isEmpty(req.getParameter("sort"))) ? "" : String.valueOf(req.getParameter("sort"));
      String sortOrder = (StringUtils.isEmpty(req.getParameter("order"))) ? "" : String.valueOf(req.getParameter("order"));

      Map<String, Object> params = builderParams(req, model);
      int total = billSaleBalanceManager.findCount(params);
      SimplePage page = new SimplePage(pageNo, pageSize, total);
      List<BillSaleBalance> list = billSaleBalanceManager.findByPage(page, sortColumn, sortOrder, params);
      List<Object> footerList = billSaleBalanceManager.selectEnterFooter(params);
      Map<String, Object> obj = new HashMap<String, Object>();
      obj.put("total", Integer.valueOf(total));
      obj.put("rows", billSaleBalanceManager.setExtendsBillSaleBalanceProperties(list));
      obj.put("footer",footerList);
      return obj;
    }
    
    @ResponseBody
    @RequestMapping("/brandCategoryDeduction.json")
	public Map<String,Object> selectbrandCategoryDeductionList(HttpServletRequest req, Model model) throws ManagerException {
    	
    	Map<String, Object> obj = new HashMap<String, Object>();
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		Integer total = billSaleBalanceManager.selectBrandCategoryDeductionCount(params);
		SimplePage page =null;
		if(params.get("print")!=null && params.get("print").equals("1")){
			page = new SimplePage(pageNo, (int) total, (int) total);
		}else{
			page = new SimplePage(pageNo, pageSize, (int) total);
		}
		List<BillSaleBalance> list = billSaleBalanceManager.selectBrandCategoryDeductionByPage(page, sortColumn, sortOrder, params);
		List<Object> footerList = billSaleBalanceManager.selectBrandCategoryDeductionFooter(params);
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}
    
}