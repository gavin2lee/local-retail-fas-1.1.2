package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceProSumManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@RequestMapping("/bill_shop_balance_pro_sum")
public class BillShopBalanceProSumController extends BaseCrudController<BillShopBalanceProSum> {
	
    @Resource
    private BillShopBalanceProSumManager billShopBalanceProSumManager;
    
    @Resource
    private BillShopBalanceManager billShopBalanceManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_pro_sum/",billShopBalanceProSumManager);
    }
        
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billShopBalanceProSumManager.findCount(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(total == 0) {
			obj.put("total", total);
			obj.put("rows", new ArrayList<BillShopBalanceProSum>(0));
			return obj;
		}
		SimplePage page = null;
		if(params.get("print")!=null && params.get("print").equals("1")){
			page = new SimplePage(pageNo, (int) total, (int) total);
		}else{
			page = new SimplePage(pageNo, pageSize, (int) total);
		}
		List<BillShopBalanceProSum> list = billShopBalanceProSumManager.findByPage(page, sortColumn, sortOrder, params);
		
		obj.put("total", total);
		obj.put("rows", list);
		
		// 组装footer
		if(list != null && list.size() > 0) {
			List<BillShopBalanceProSum> billShopBalanceProSumTag = billShopBalanceProSumManager.getSumAmount(params);
			BillShopBalanceProSum billShopBalanceProSum = new BillShopBalanceProSum();
			// 获取商场开票金额
						String balanceNo = req.getParameter("balanceNo");
						if(StringUtils.isNotEmpty(balanceNo)) {
							Map<String, Object> balanceParams = new HashMap<String, Object>();
							balanceParams.put("balanceNo", balanceNo);
							List<BillShopBalance> lstBillShopBalance = billShopBalanceManager.selectBlanceList(page, null, null, balanceParams);//.findByBiz(null, balanceParams);
							if(lstBillShopBalance != null && lstBillShopBalance.size() > 0 
									&& lstBillShopBalance.get(0).getMallBillingAmount().compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal mallBillingAmount = lstBillShopBalance.get(0).getMallBillingAmount();
								BigDecimal saleAmount = lstBillShopBalance.get(0).getSystemSalesAmount();
								// 设置页脚开票金额的合计
								billShopBalanceProSum.setBillingAmount(mallBillingAmount);
								BigDecimal amount = BigDecimal.ZERO;
								for(int i = 0; i < list.size(); i++) {
									BillShopBalanceProSum bill = list.get(i);
									bill.setId(list.get(i).getId());
									if(i < list.size() - 1) {
										if(saleAmount.doubleValue() > 0){
										   BigDecimal billAmount = BigDecimalUtil.format(BigDecimalUtil.multi(mallBillingAmount, 
												BigDecimalUtil.divisionScale(bill.getSaleAmount(),10,saleAmount)),
												BigDecimalUtil.POINT_4_PATTERN);
												amount = BigDecimalUtil.format(BigDecimalUtil.add(amount, billAmount), BigDecimalUtil.POINT_4_PATTERN);
												bill.setBillingAmount(billAmount);
										}
									} else {
										bill.setBillingAmount(BigDecimalUtil.subtract(mallBillingAmount, amount));
									}
									billShopBalanceProSumManager.modifyById(bill);	
								}
							}
						}
				if(billShopBalanceProSumTag != null && billShopBalanceProSumTag.size() > 0) {
					for(BillShopBalanceProSum shopBalanceProSum : billShopBalanceProSumTag) {
		//				BillShopBalanceProSum billShopBalanceProSum = new BillShopBalanceProSum();
						billShopBalanceProSum.setProNo("合计");
						billShopBalanceProSum.setDeductAmount(shopBalanceProSum.getDeductAmount());
						billShopBalanceProSum.setSaleAmount(shopBalanceProSum.getSaleAmount());
						billShopBalanceProSum.setSystemBillingAmount(shopBalanceProSum.getSystemBillingAmount());
						billShopBalanceProSum.setBillingAmount(shopBalanceProSum.getBillingAmount());
						List<BillShopBalanceProSum> billShopBalanceProSumTotal = new ArrayList<BillShopBalanceProSum>(1);
						billShopBalanceProSumTotal.add(billShopBalanceProSum);
						obj.put("footer", billShopBalanceProSumTotal);
					}
			}
		}
		return obj;
	}
}