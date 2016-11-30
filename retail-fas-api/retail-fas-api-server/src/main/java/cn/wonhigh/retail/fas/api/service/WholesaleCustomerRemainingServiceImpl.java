package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingMapper;
import cn.wonhigh.retail.fas.api.dto.WholesaleCustomerRemainingDto;

import com.yougou.logistics.base.common.exception.ServiceException;

public class WholesaleCustomerRemainingServiceImpl implements WholesaleCustomerRemainingService {
	
	@Resource
	private WholesaleCustomerRemainingMapper wholesaleCustomerRemainingMapper;
	
	@Override
	public List<WholesaleCustomerRemainingDto> selectRemainingSumByParams(Map<String,Object> params) throws ServiceException {
		return wholesaleCustomerRemainingMapper.selectRemainingSumByParams(params);
	}

	@Override
	public Integer updateCustomerRemainingSum(WholesaleCustomerRemainingDto remainingDto) throws ServiceException {
		return wholesaleCustomerRemainingMapper.updateCustomerRemainingSum(remainingDto);
	}

	@Override
	public Integer insertCustomerRemainingDtl(WholesaleCustomerRemainingDto remainingDto) throws ServiceException {
		return wholesaleCustomerRemainingMapper.insertCustomerRemainingDtl(remainingDto);
	}

}
