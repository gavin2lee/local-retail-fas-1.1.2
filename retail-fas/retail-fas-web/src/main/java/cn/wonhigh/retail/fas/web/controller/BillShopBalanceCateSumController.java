package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceCateSumManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;

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
@RequestMapping("/bill_shop_balance_cate_sum")
public class BillShopBalanceCateSumController extends BaseCrudController<BillShopBalanceCateSum> {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

	@Resource
    private BillShopBalanceCateSumManager billShopBalanceCateSumManager;
    
    @Resource
    private BillShopBalanceManager billShopBalanceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_cate_sum/",billShopBalanceCateSumManager);
    }
    
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billShopBalanceCateSumManager.findCount(params);
		SimplePage page = null;
		if(params.get("print")!=null && params.get("print").equals("1")){
			page = new SimplePage(pageNo, (int) total, (int) total);
		}else{
			page = new SimplePage(pageNo, pageSize, (int) total);
		}
		List<BillShopBalanceCateSum> list = this.billShopBalanceCateSumManager.findByPage(page, sortColumn, 
				sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// 组装footer
		if(list != null && list.size() >= 0) {
			BillShopBalanceCateSumFooterDto footerDto = billShopBalanceCateSumManager.getFootDto(params);
			BillShopBalanceCateSum billShopBalanceCateSum = new BillShopBalanceCateSum();
			billShopBalanceCateSum.setCategoryName("合计");
			// 复制属性
			try {
				AnnotationReflectUtil.copyProperties(billShopBalanceCateSum, footerDto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
			
			// 获取商场开票金额
//			String balanceNo = req.getParameter("balanceNo");
//			if(StringUtils.isNotEmpty(balanceNo)) {
//				Map<String, Object> balanceParams = new HashMap<String, Object>();
//				balanceParams.put("balanceNo", balanceNo);
//				List<BillShopBalance> lstBillShopBalance = billShopBalanceManager.selectBlanceList(page, null, null, balanceParams);//.findByBiz(null, balanceParams);
//				if(lstBillShopBalance != null && lstBillShopBalance.size() > 0 
//						&& lstBillShopBalance.get(0).getMallBillingAmount().compareTo(BigDecimal.ZERO) != 0) {
//					BigDecimal mallBillingAmount = lstBillShopBalance.get(0).getMallBillingAmount();
//					BigDecimal saleAmount = lstBillShopBalance.get(0).getSystemSalesAmount();
//					// 设置页脚开票金额的合计
//					billShopBalanceCateSum.setBillingAmount(mallBillingAmount);
//					BigDecimal amount = BigDecimal.ZERO;
//					for(int i = 0; i < list.size(); i++) {
//						BillShopBalanceCateSum bill = list.get(i);
//						bill.setId(list.get(i).getId());
//						if(i < list.size() - 1) {
//							if(saleAmount.doubleValue() > 0){
//							   BigDecimal billAmount = BigDecimalUtil.format(BigDecimalUtil.multi(mallBillingAmount, 
//									BigDecimalUtil.divisionScale(bill.getSaleAmount(),10,saleAmount)),
//									BigDecimalUtil.POINT_4_PATTERN);
//									amount = BigDecimalUtil.format(BigDecimalUtil.add(amount, billAmount), BigDecimalUtil.POINT_4_PATTERN);
//									bill.setBillingAmount(billAmount);
//							}
//						} else {
//							bill.setBillingAmount(BigDecimalUtil.subtract(mallBillingAmount, amount));
//						}
//						billShopBalanceCateSumManager.modifyById(bill);	
//					}
//					
//				}
//			}
			List<BillShopBalanceCateSum> billShopBalanceCateSumTotal = new ArrayList<BillShopBalanceCateSum>(1);
			billShopBalanceCateSumTotal.add(billShopBalanceCateSum);
			obj.put("footer", billShopBalanceCateSumTotal);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
    @RequestMapping(value = "/salesCategory3list.json")  
	@ResponseBody
	public Map<String, Object> querySalesCategory3List(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billShopBalanceCateSumManager.findCount(params);
		SimplePage page = null;
		if(params.get("print")!=null && params.get("print").equals("1")){
			page = new SimplePage(pageNo, (int) total, (int) total);
		}else{
			page = new SimplePage(pageNo, pageSize, (int) total);
		}
		List<BillShopBalanceCateSum> list = this.billShopBalanceCateSumManager.findBrandSalesCategory3(page, sortColumn, 
				sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// 组装footer
		if(list != null && list.size() >= 0) {
			BillShopBalanceCateSumFooterDto footerDto = billShopBalanceCateSumManager.getFootDto(params);
			BillShopBalanceCateSum billShopBalanceCateSum = new BillShopBalanceCateSum();
			billShopBalanceCateSum.setCategoryName("合计");
			// 复制属性
			try {
				AnnotationReflectUtil.copyProperties(billShopBalanceCateSum, footerDto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
			List<BillShopBalanceCateSum> billShopBalanceCateSumTotal = new ArrayList<BillShopBalanceCateSum>(1);
			billShopBalanceCateSumTotal.add(billShopBalanceCateSum);
			obj.put("footer", billShopBalanceCateSumTotal);
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
}