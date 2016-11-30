package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceBrand;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.manager.BillShopBalanceBrandManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-10-21 15:01:41
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
@RequestMapping("/bill_shop_balance_brand")
public class BillShopBalanceBrandController extends BaseCrudController<BillShopBalanceBrand> {
    @Resource
    private BillShopBalanceBrandManager billShopBalanceBrandManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_brand/",billShopBalanceBrandManager);
    }
    
    @RequestMapping("/list")
 	public String list() {
 		return "mallshop_balance/shopbalance_brand";
 	}
    
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String balanceNo = params.get("balanceNo") == null ? null : params.get("balanceNo").toString();
//		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
//		List<String> shopNoList = new ArrayList<String>();
//		if(shopNo != null && !"".equals(shopNo)){
//			if(shopNo.contains(",")){
//				String[] shopNos = shopNo.split(",");
//				for (String shopNoTemp : shopNos) {
//					shopNoList.add(shopNoTemp);
//				}
//			}else{
//				shopNoList.add(shopNo);
//			}
//			params.put("shopNos",shopNoList );
//			params.put("shopNo", null);
//		}else{
//			params.put("shopNos",null);
//		}
		params.put("balanceNo", balanceNo);
		int total = this.billShopBalanceBrandManager.findCount(params);
		SimplePage page =null;
		if(params.get("print")!=null && params.get("print").equals("1")){
			page = new SimplePage(pageNo, (int) total, (int) total);
		}else{
			page = new SimplePage(pageNo, pageSize, (int) total);
		}
		List<BillShopBalanceBrand> list = this.billShopBalanceBrandManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		
		BillShopBalanceBrand sumDtlDto = billShopBalanceBrandManager.getSumBalanceBrand(params);
    	
    	if (null != sumDtlDto) {
    		// 组装footer
    		BillShopBalanceBrand shopBalanceBrand = new BillShopBalanceBrand();
    		shopBalanceBrand.setBrandName("合计");
    		shopBalanceBrand.setMallNumber(sumDtlDto.getMallNumber());
    		shopBalanceBrand.setSalesAmount(sumDtlDto.getSalesAmount());
    		shopBalanceBrand.setMallBillingAmount(sumDtlDto.getMallBillingAmount());
    		shopBalanceBrand.setSystemBillingAmount(sumDtlDto.getSystemBillingAmount());
    		shopBalanceBrand.setDeductAllamount(sumDtlDto.getDeductAllamount());
    		shopBalanceBrand.setDeductDiffAmount(sumDtlDto.getDeductDiffAmount());
    		shopBalanceBrand.setDiffAmount(sumDtlDto.getDiffAmount());
    		shopBalanceBrand.setActualAmount(sumDtlDto.getActualAmount());
    		shopBalanceBrand.setReportDiffAmount(sumDtlDto.getReportDiffAmount());
    		shopBalanceBrand.setPrepaymentAmount(sumDtlDto.getPrepaymentAmount());
    		shopBalanceBrand.setUsedPrepaymentAmount(sumDtlDto.getUsedPrepaymentAmount());
    		shopBalanceBrand.setBalanceDiffAmount(sumDtlDto.getBalanceDiffAmount());
    		shopBalanceBrand.setDeductDiffAmount(sumDtlDto.getDeductDiffAmount());
    		shopBalanceBrand.setBalanceDeductAmount(sumDtlDto.getBalanceDeductAmount());
    		
    		List<BillShopBalanceBrand> billShopBalanceBrandSumTotal = new ArrayList<BillShopBalanceBrand>();
    		
    		billShopBalanceBrandSumTotal.add(shopBalanceBrand);
    		
    		obj.put("footer", billShopBalanceBrandSumTotal);
		}
		return obj;
    }
}