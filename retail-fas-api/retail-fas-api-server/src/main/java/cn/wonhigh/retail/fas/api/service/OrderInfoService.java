package cn.wonhigh.retail.fas.api.service;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.dto.OrderInfoDto;

public interface OrderInfoService {
	
	/**
	 * 提供给POS 接口查询当前销售订单状态
	 * @param orderInfoDto订单实体
	 * @return (0-未确认 1-已确认)
	 */
	public Integer getStatus(OrderInfoDto orderInfoDto);
	
	/**
	 * 根据订单号查询POS内购订单是否已做了财务确认
	 * @param orderInfoDto
	 * @return 确认状态
	 */
	public Integer findInnerBuyConfrigStatus(@Param("orderInfoDto")OrderInfoDto orderInfoDto) ;
}
