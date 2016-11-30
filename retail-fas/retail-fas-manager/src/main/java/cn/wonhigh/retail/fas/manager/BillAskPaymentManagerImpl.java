package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.ExchangeRateSetup;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.service.BillAskPaymentService;
import cn.wonhigh.retail.fas.service.CurrencyManagementService;
import cn.wonhigh.retail.fas.service.ExchangeRateSetupService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
@Service("billAskPaymentManager")
class BillAskPaymentManagerImpl extends BaseCrudManagerImpl implements BillAskPaymentManager {
	private Logger logger = Logger.getLogger(BillAskPaymentManagerImpl.class);
    @Resource
    private BillAskPaymentService billAskPaymentService;
    
    @Resource
    private CurrencyManagementService currencyManagementService;
    
    @Resource
    private ExchangeRateSetupService exchangeRateSetupService;

    @Override
    public BaseCrudService init() {
        return billAskPaymentService;
    }

	@Override
	public int generateBillBybalance(List<BillBalance> list, String loginName) throws ManagerException {
		try {
			return billAskPaymentService.generateBillBybalance(list, loginName);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int verify(BillAskPayment obj) throws ManagerException {
		try {
			return billAskPaymentService.verify(obj);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BillAskPayment> selectFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return billAskPaymentService.selectFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BillAskPayment> setTargetCurencyPropertites(List<BillAskPayment> list,Map<String, String> currencyInfo) throws ManagerException {
		boolean flag = ShardingUtil.isBLK();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "1");
			CurrencyManagement targetCurrency = currencyManagementService.findDefaultCurrency();
			for(BillAskPayment billAskPayment:list){
				billAskPayment.setCurrencyName(currencyInfo.get(billAskPayment.getCurrencyNo()));
				if(flag && null != targetCurrency){
					StringBuffer queryCondition = new StringBuffer();
					billAskPayment.setTargetCurrencyNo(targetCurrency.getCurrencyCode());//本位币
					billAskPayment.setTargetCurrencyName(targetCurrency.getCurrencyName());
					queryCondition.append(" and source_currency = '" + billAskPayment.getCurrencyNo() + "'");
					queryCondition.append(" and target_currency = '" + billAskPayment.getTargetCurrencyNo() + "'");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					queryCondition.append(" and effective_date <= '" + date + "'");
					queryCondition.append(" and company_no = '" + billAskPayment.getBuyerNo() + "'");
					params.put("queryCondition", new String(queryCondition));
					BigDecimal conversionFactor = BigDecimal.valueOf(0d);
					if(billAskPayment.getCurrencyNo() != null && billAskPayment.getCurrencyNo().equals(targetCurrency.getCurrencyCode())){
						conversionFactor = BigDecimal.valueOf(1d);
					}else{
						List<ExchangeRateSetup> exchangeRateSetupList = exchangeRateSetupService.findByBiz(null, params);
						if(exchangeRateSetupList.size()>0){
							conversionFactor = exchangeRateSetupList.get(0).getConversionFactor();
						}
					}
					
					billAskPayment.setConversionFactor(conversionFactor);//汇率
					billAskPayment.setTargetCurrencyAmount(billAskPayment.getAllAmount().multiply(conversionFactor));//本位币金额

				}
			}
		} catch (ServiceException e) {
			logger.error("设置本位币、汇率、本位币金额属性出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
		return list;
	}
	
	@Override
	public BillAskPayment addMainForm(BillAskPayment bill) throws ServiceException {
		return billAskPaymentService.addMainForm(bill);
	}
}