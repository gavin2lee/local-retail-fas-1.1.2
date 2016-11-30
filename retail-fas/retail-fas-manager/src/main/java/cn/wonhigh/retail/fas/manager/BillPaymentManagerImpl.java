package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.BillPayment;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.ExchangeRateSetup;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.service.BillPaymentService;
import cn.wonhigh.retail.fas.service.CurrencyManagementService;
import cn.wonhigh.retail.fas.service.ExchangeRateSetupService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
@Service("billPaymentManager")
class BillPaymentManagerImpl extends BaseCrudManagerImpl implements BillPaymentManager {
	private Logger logger = Logger.getLogger(BillPaymentManagerImpl.class);
    @Resource
    private BillPaymentService billPaymentService;
    
    @Resource
    private CurrencyManagementService currencyManagementService;
    
    @Resource
    private ExchangeRateSetupService exchangeRateSetupService;

    @Override
    public BaseCrudService init() {
        return billPaymentService;
    }

	@Override
	public int generateByInvoice(List<BillInvoice> oList,
			String loginName) throws ManagerException {
		try {
			return billPaymentService.generateByInvoice(oList, loginName);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int verify(BillPayment obj) throws ManagerException {
		try {
			return billPaymentService.verify(obj);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillPayment> selectFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return billPaymentService.selectFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillPayment> setTargetCurencyPropertites(List<BillPayment> list,Map<String, String> currencyInfo) throws ManagerException {
		boolean flag = ShardingUtil.isBLK();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "1");
			CurrencyManagement targetCurrency = currencyManagementService.findDefaultCurrency();
			for(BillPayment billPayment:list){
				billPayment.setCurrencyName(currencyInfo.get(billPayment.getCurrency()));
				if(flag && null != targetCurrency){
					StringBuffer queryCondition = new StringBuffer();
					billPayment.setTargetCurrencyNo(targetCurrency.getCurrencyCode());//本位币
					billPayment.setTargetCurrencyName(targetCurrency.getCurrencyName());
					queryCondition.append(" and source_currency = '" + billPayment.getCurrency() + "'");
					queryCondition.append(" and target_currency = '" + billPayment.getTargetCurrencyNo() + "'");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					queryCondition.append(" and effective_date <= '" + date + "'");
					queryCondition.append(" and company_no = '" + billPayment.getBuyerNo() + "'");
					params.put("queryCondition", new String(queryCondition));
					BigDecimal conversionFactor = BigDecimal.valueOf(0d);
					if(billPayment.getCurrency() != null && billPayment.getCurrency().equals(targetCurrency.getCurrencyCode())){
						conversionFactor = BigDecimal.valueOf(1d);
					}else{
						List<ExchangeRateSetup> exchangeRateSetupList = exchangeRateSetupService.findByBiz(null, params);
						if(exchangeRateSetupList.size()>0){
							conversionFactor = exchangeRateSetupList.get(0).getConversionFactor();
						}
					}
					
					billPayment.setConversionFactor(conversionFactor);//汇率
					billPayment.setTargetCurrencyAmount(billPayment.getAmount().multiply(conversionFactor));//本位币金额
				}
			}
		} catch (ServiceException e) {
			logger.error("设置本位币、汇率、本位币金额属性出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
		return list;
	}
}