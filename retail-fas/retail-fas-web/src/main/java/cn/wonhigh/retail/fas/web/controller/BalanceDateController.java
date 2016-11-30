package cn.wonhigh.retail.fas.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BalanceDate;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.manager.BalanceDateManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-27 16:17:53
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
@RequestMapping("/balance_date")
public class BalanceDateController extends BaseCrudController<BalanceDate> {
    @Resource
    private BalanceDateManager balanceDateManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("balance_date/",balanceDateManager);
    }
    
    @RequestMapping("/list_payable")
    public String listPayable(){
    	return "/balance_date/list_payable";
    }
    
    @RequestMapping("/list_receivable")
    public String listReceivable(){
    	return "/balance_date/list_receivable";
    }
    
	@RequestMapping(value = "/getBalanceDate")
	@ResponseBody
	public Map<String, String> get(BillBalance obj) throws ManagerException {
		Map<String, String> map = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int iBalanceType = obj.getBalanceType();
		String companyNo = "";
		String supplierNo ="";
		String customerNo ="";
		if(iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()){
			companyNo = obj.getBuyerNo();
			supplierNo = obj.getSalerNo();
		}else{
			companyNo = obj.getSalerNo();
			customerNo = obj.getBuyerNo();
		}
		if(StringUtils.isNotBlank(companyNo) && (StringUtils.isNotBlank(supplierNo) ||
				StringUtils.isNotBlank(customerNo))){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			params.put("supplierNo", supplierNo);
			params.put("customerNo", customerNo);
			params.put("balanceType", iBalanceType);
			List<BalanceDate> lstItem = balanceDateManager.findByBiz(null, params);
			if(lstItem.size() == 0){
				params.clear();
				params.put("queryCondition", "AND (supplier_no IS NULL OR supplier_no = '') AND (customer_no IS NULL OR customer_no = '')");
				params.put("companyNo", companyNo);
				params.put("balanceType", iBalanceType);
				lstItem = balanceDateManager.findByBiz(null, params);
			}
			if(lstItem.size() > 0){
				map = new HashMap<String, String>();
				String date = lstItem.get(0).getBalanceDate();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date));
				Date balanceEndDate = cal.getTime();
				int currMonth = cal.get(Calendar.MONTH);
				int preMonth = currMonth - 1;
				cal.set(Calendar.MONTH, preMonth);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date) + 1);
				Date balanceStartDate = cal.getTime();
				map.put("balanceStartDate", sdf.format(balanceStartDate));
				map.put("balanceEndDate", sdf.format(balanceEndDate));
			}
		}
		return map;
	}
	
}