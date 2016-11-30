package cn.wonhigh.retail.fas.api.service;

import cn.wonhigh.retail.fas.api.dto.OrderInfoDto;

public interface OrderInfoApi {
	/**
	 * 提供给POS 接口查询当前销售订单状态
	 * @param orderInfoDto-订单实体
	 * @return (0-未确认 1-已确认)
	 */
	public Integer getStatus(OrderInfoDto orderInfoDto);
}
