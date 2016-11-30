package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.CurrencyMapper;
import cn.wonhigh.retail.fas.api.model.Currency;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("currencyServiceImpl")
public class CurrencyServiceImpl implements CurrencyService {

	@Resource
	private CurrencyMapper currencyMapper ;
	
	private Logger log = Logger.getLogger(getClass());
	
	@Override
	public List<Currency> findAllCurrency() throws ServiceException {
		// TODO Auto-generated method stub
		
		try {
			return currencyMapper.findAllCurrency();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("系统错误-->获取货币类型失败");
			throw new ServiceException("获取货币类型失败",e);
		}
	}

}
