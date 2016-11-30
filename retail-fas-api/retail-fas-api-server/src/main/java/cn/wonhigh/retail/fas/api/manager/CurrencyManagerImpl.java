package cn.wonhigh.retail.fas.api.manager;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dto.CurrencyDto;
import cn.wonhigh.retail.fas.api.model.Currency;
import cn.wonhigh.retail.fas.api.service.CurrencyApi;
import cn.wonhigh.retail.fas.api.service.CurrencyServiceImpl;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("currencyManagerImpl")
public class CurrencyManagerImpl implements CurrencyApi {

	@Resource
	private CurrencyServiceImpl currencyServiceImpl;
	
	private Logger log = Logger.getLogger(getClass());
	
	@Override
	public List<CurrencyDto> findAllCurrency() throws RpcException {
		// TODO Auto-generated method stub
		List<CurrencyDto> currencyDtoList = null;
		try {
			List<Currency> currencyList = currencyServiceImpl.findAllCurrency();
			
			if(currencyList != null && currencyList.size() > 0){
				currencyDtoList = new ArrayList<CurrencyDto>();
				for(Currency currency : currencyList){
					CurrencyDto currencyDto = new CurrencyDto();
					BeanUtils.copyProperties(currency, currencyDto);
					
					currencyDtoList.add(currencyDto);
				}
			}
			return currencyDtoList;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			log.debug("获取货币类别失败");
			throw new RpcException("财务辅助dubbo服务","获取货币类别失败",e);
		}
	}

}
