package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import cn.wonhigh.retail.fas.api.dto.CurrencyDto;

import com.yougou.logistics.base.common.exception.RpcException;

public interface CurrencyApi {
	
	/**
	 * Get All the currencies that status of the currency is allowed .
	 * @return
	 * @throws RpcException
	 */
	List<CurrencyDto> findAllCurrency() throws RpcException ;
}
