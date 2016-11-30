package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import cn.wonhigh.retail.fas.api.model.Currency;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface CurrencyService {
	
	public List<Currency> findAllCurrency() throws ServiceException;
	
}
