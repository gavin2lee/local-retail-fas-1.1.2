package cn.wonhigh.retail.fas.api.service;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.OrderInfoMapper;
import cn.wonhigh.retail.fas.api.dto.OrderInfoDto;

@Service("orderInfoServiceImpl")
public class OrderInfoServiceImpl implements OrderInfoService {
	
	@Resource
	private OrderInfoMapper orderInfoMapper;

	@Override
	public Integer getStatus(OrderInfoDto orderInfoDto) {
		return orderInfoMapper.getStatus(orderInfoDto);
	}

	
	/**
	 * 根据订单号查询POS内购订单是否已做了财务确认
	 * @param orderInfoDto
	 * @return 确认状态
	 */
	@Override
	public Integer findInnerBuyConfrigStatus(@Param("orderInfoDto")OrderInfoDto orderInfoDto) {
		return orderInfoMapper.findInnerBuyConfrigStatus(orderInfoDto);
	}
}
