package cn.wonhigh.retail.fas.api.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleControlApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleOutControlDto;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.api.service.WholesaleControlApi;
import cn.wonhigh.retail.fas.api.service.WholesaleControlApiService;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.common.exception.RpcException;

@Service("wholesaleControlApiManagerImpl")
public class WholesaleControlApiManagerImpl implements WholesaleControlApi {

	@Resource
	private WholesaleControlApiService wholesaleControlApiService;
	
	private Logger log = Logger.getLogger(getClass());

	// 初始版本
	@Override
	public WholesaleControlApiDto getCheckMessage(String billNo)
			throws RpcException {
		try {
			return wholesaleControlApiService.getCheckMessage(billNo);
		} catch (RpcException e) {
			log.debug("获取批发预警信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}
	
	@Override
	public String getOrderGoodsCheckMessage(String billNo) throws RpcException {
		try {
			return wholesaleControlApiService.getOrderGoodsCheckMessage(billNo);
		} catch (RpcException e) {
			log.debug("获取批发订货预警信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}
	
	@Override
	public String getOrderGoodsCheckMessage(String billNo, Integer itemOrderType) throws RpcException {
		try {
			return wholesaleControlApiService.getOrderGoodsCheckMessage(billNo, itemOrderType);
		} catch (RpcException e) {
			log.debug("获取批发订货预警信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

	@Override
	public String getSendGoodsCheckMessage(String billNo, String orderUnitNo, 
			String customerNo, BigDecimal amount) throws RpcException {
		try {
			return wholesaleControlApiService.getSendGoodsCheckMessage(billNo, orderUnitNo, customerNo, amount);
		} catch (RpcException e) {
			log.debug("获取批发出库预警信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

	@Override
	public String getDeliveryReleaseMessage(String billNo, String orderUnitNo,
			List<Map<String, Object>> list) throws RpcException {
		try {
			return wholesaleControlApiService.getDeliveryReleaseMessage(billNo, orderUnitNo, list);
		} catch (RpcException e) {
			log.debug("获取批发通知单预警信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

	@Override
	public ApiMessage validateWholesaleOut(WholesaleOutControlDto wholesaleOutControlDto) throws RpcException {
		try {
			return wholesaleControlApiService.validateWholesaleOut(wholesaleOutControlDto);
		} catch (RpcException e) {
			log.debug("获取批发出库验证信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

	@Override
	public Boolean findCustomerReceRel(String companyNo, String customerNo) throws RpcException {
		try {
			return wholesaleControlApiService.findCustomerReceRel(companyNo, customerNo);
		} catch (RpcException e) {
			log.debug("查询客户-收款条款信息失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

	@Override
	public Integer updateOrderStatus(String orderNo, Integer status) throws RpcException {
		try {
			return wholesaleControlApiService.updateOrderStatus(orderNo, status);
		} catch (RpcException e) {
			log.debug("更新订单状态失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

	@Override
	public String changeWholesaleCost(List<BillBalanceApiDto> lstBill) throws RpcException {
		try {
			return wholesaleControlApiService.changeWholesaleCost(lstBill);
		} catch (RpcException e) {
			log.debug("更新订单状态失败");
			throw new RpcException("财务辅助dubbo服务",e.getMessage());
		}
	}

}
