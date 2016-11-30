package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CreditCardCheck;
import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.CreditCardCheckService;
import cn.wonhigh.retail.fas.service.OrderDtlService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
@Service("creditCardCheckManager")
class CreditCardCheckManagerImpl extends BaseCrudManagerImpl implements CreditCardCheckManager {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(CreditCardCheckManagerImpl.class);
	
    @Resource
    private CreditCardCheckService creditCardCheckService;
    
    @Resource
    private ShopService shopService;
    
    @Override
    public BaseCrudService init() {
        return creditCardCheckService;
    }
    
    public List<CreditCardCheck> getShopSaleDetail(String terminalNumber,String outDate) throws ManagerException{
    	try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("outDate", outDate);
			List<String> terminalNumberList = new ArrayList<String>();
			if(terminalNumber.indexOf(",")!=0){
				String[] terminal = terminalNumber.split(",");
				for (String string : terminal) {
					terminalNumberList.add(string);
				}
			}else{
				terminalNumberList.add(terminalNumber);
			}
			
			List<CreditCardCheck> list = creditCardCheckService.getShopSaleDetail(params);
			List<CreditCardCheck> result = new ArrayList<CreditCardCheck>();
			Map<String, Object> map = new HashMap<String,Object>();
			for (CreditCardCheck creditCardCheck : list) {
				String terminalNo = creditCardCheck.getTerminalNumber();
				for (String str : terminalNumberList) {
					if(terminalNo.indexOf(str)!=-1 && !result.contains(creditCardCheck)){
						map.put("shopNo", creditCardCheck.getShopNo());
						Shop shop = shopService.selectSubsiInfo(map);
						creditCardCheck.setMallNo(shop.getMallNo());
						creditCardCheck.setMallName(shop.getMallName());
						result.add(creditCardCheck);
					}
				}
			}
			return result;
		} catch (ServiceException e) {
			throw new ManagerException();
		}
    }

	@Override
	public CreditCardCheck findShopSaleDetailCount(Map<String, Object> params) throws ManagerException {
		try {
			return creditCardCheckService.findCreditCardCheckCount(params);
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<CreditCardCheck> findShopSaleDetailList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ManagerException {
		try {
			return creditCardCheckService.findCreditCardCheckList(page, orderBy, orderByField, params);
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage(), "查询银联刷卡核对明细异常！");
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<CreditCardCheck> setCreditCardCheckProperties(List<CreditCardCheck> list,String companyNo) throws ManagerException {
		for (CreditCardCheck creditCardCheck : list) {
			//根据店铺+终端号+交易日期查询银联刷卡交易明细的金额
			BigDecimal onlineIncomeAmount = this.getOnlieIncomeAmount(creditCardCheck);
			creditCardCheck.setOnlineIncomeAmount(onlineIncomeAmount);
			creditCardCheck.setCreditCardDiff(onlineIncomeAmount.subtract(creditCardCheck.getIncomeAmount()));
		}
		return list;
	}

	private BigDecimal getOnlieIncomeAmount(CreditCardCheck creditCardCheck) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if(creditCardCheck.getTerminalNumber()!=null && creditCardCheck.getOutDate()!=null){
				params.put("terminalNumber", Arrays.asList(creditCardCheck.getTerminalNumber().split(",")));
				params.put("outDate", DateUtil.formatDateByFormat(creditCardCheck.getOutDate(), "yyyy-MM-dd"));
				return creditCardCheckService.getOnlieIncomeAmount(params);
			}
		} catch (ServiceException e) {
			throw new ManagerException("查询银联交易明细记录出错!", e);
		}
		return BigDecimal.valueOf(0d);
	}
    
}