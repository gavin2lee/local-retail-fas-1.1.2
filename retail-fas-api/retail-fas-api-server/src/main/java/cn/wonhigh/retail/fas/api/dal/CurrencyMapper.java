package cn.wonhigh.retail.fas.api.dal;

import java.util.List;

import cn.wonhigh.retail.fas.api.model.Currency;

public interface CurrencyMapper {
	
	List<Currency> findAllCurrency() throws Exception;
	
}
