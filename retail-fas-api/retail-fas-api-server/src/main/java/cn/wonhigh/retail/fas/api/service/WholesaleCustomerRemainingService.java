package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.dto.WholesaleCustomerRemainingDto;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface WholesaleCustomerRemainingService {
	
	List<WholesaleCustomerRemainingDto> selectRemainingSumByParams(Map<String, Object> params)throws ServiceException;
	
	Integer updateCustomerRemainingSum(WholesaleCustomerRemainingDto remainingDto)throws ServiceException;
	
	Integer insertCustomerRemainingDtl(WholesaleCustomerRemainingDto remainingDto)throws ServiceException;

}
