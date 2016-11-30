package cn.wonhigh.retail.fas.web.controller;

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

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceSource;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceApplyManager;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceSourceManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
@RequestMapping("/bill_balance_invoice_source")
public class BillBalanceInvoiceSourceController extends BaseCrudController<BillBalanceInvoiceSource> {
	
    @Resource
    private BillBalanceInvoiceSourceManager billBalanceInvoiceSourceManager;
    
	@Resource
	private BillBalanceInvoiceApplyManager billBalanceInvoiceApplyManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_balance_invoice_source/",billBalanceInvoiceSourceManager);
    }
    
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		String billNo = req.getParameter("billNo");
		if(StringUtils.isEmpty(billNo)) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("total", 0);
			obj.put("rows", new ArrayList<BillBalanceInvoiceSource>(1));
			return obj;
		}
		return super.queryList(req, model);
	}
    
//    /**
//     * 获取团购源单信息
//     * @param req
//     * @param model
//     * @return
//     * @throws ManagerException
//     */
//	@RequestMapping(value = "/orderList.json")
//	@ResponseBody
//	public  Map<String, Object> orderList(HttpServletRequest req, Model model) throws ManagerException {
//		String billNo = StringUtils.isEmpty(req.getParameter("billNo")) ? "" : String.valueOf(req.getParameter("billNo"));
//		
//		String billNoStr = "";
//		
//		List<BillBalanceInvoiceSource> list = new ArrayList<BillBalanceInvoiceSource>();
//		
//		Map<String, Object> obj = new HashMap<String, Object>();
//		
//		int total = 0;
//		
//		if(StringUtils.isEmpty(billNo)){
//			obj.put("rows", list);
//			obj.put("total", total);
//		}
//		
//		if(StringUtils.isNotEmpty(billNo)){
//			billNoStr = billBalanceInvoiceApplyManager.getOrderBillNo(billNo, String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()), "");
//			
//			BillBalanceInvoiceApply apply = new BillBalanceInvoiceApply();
//			apply.setBillNo(billNo);
//			apply = billBalanceInvoiceApplyManager.findById(apply);
//			
//			if(null != apply){
//				if(billNoStr.length() > 0){
//					String[] billNos = billNoStr.split(",");
//					for (String string : billNos) {
//						BillBalanceInvoiceSource source = new BillBalanceInvoiceSource();
//						source.setBalanceNo(string);
//						source.setBillNo(billNo);
//						source.setAmount(apply.getAmount());
//						source.setBalanceType(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo());
//						list.add(source);
//					}
//				}
//			}
//			total = list.size();
//			obj.put("total", total);
//			obj.put("rows", list);
//		}
//		
//		return obj;
//	}
}