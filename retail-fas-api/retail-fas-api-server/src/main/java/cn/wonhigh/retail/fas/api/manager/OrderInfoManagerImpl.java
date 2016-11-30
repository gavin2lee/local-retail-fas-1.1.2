package cn.wonhigh.retail.fas.api.manager;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dto.OrderInfoDto;
import cn.wonhigh.retail.fas.api.service.OrderInfoService;
import cn.wonhigh.retail.fas.api.service.OrderInfoApi;

@Service("orderInfoManagerImplApi")
public class OrderInfoManagerImpl implements OrderInfoApi {
	private Logger logger = Logger.getLogger(OrderInfoManagerImpl.class);
	@Resource
	private OrderInfoService orderInfoService;

	@Override
	public Integer getStatus(OrderInfoDto orderInfoDto) {
		if (StringUtils.isEmpty(orderInfoDto.getBillNo()) || orderInfoDto.getBillType() == null) {
			logger.error("单据编号为空或者单据类型为空!");
			return null;
		}
		Integer status = null;
		if(orderInfoDto.getBillType() == 99){//类型为99时,查询的是POS内购订单的确认状态
			status = orderInfoService.findInnerBuyConfrigStatus(orderInfoDto);
			if(null == status){
				status = 0;
			}
		}else{
			status = orderInfoService.getStatus(orderInfoDto);
		}
		return status;
	}

}
