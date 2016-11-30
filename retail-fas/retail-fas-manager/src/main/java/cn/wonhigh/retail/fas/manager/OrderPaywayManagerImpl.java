package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillStatus;
import cn.wonhigh.retail.fas.common.model.OrderPayway;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.service.BillStatusService;
import cn.wonhigh.retail.fas.service.OrderPaywayService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途
 * 
 * @author Administrator
 * @date 2015-03-12 10:10:28
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("orderPaywayManager")
class OrderPaywayManagerImpl extends BaseCrudManagerImpl implements
		OrderPaywayManager {
	@Resource
	private OrderPaywayService orderPaywayService;
	@Resource
	private BillStatusService billStatusService;

	@Override
	public BaseCrudService init() {
		return orderPaywayService;
	}

	/**
	 * 店铺终端信息管理费率新增修改后修改财务人员未确认的销售订单的手续费
	 * 
	 * @param list
	 * @throws ServiceException
	 */
	public void updatePoundage(List<SelfShopTerminalAccount> list)
			throws ServiceException {
		Map<CommonOperatorEnum, List<OrderPayway>> map = new HashMap<CommonOperatorEnum, List<OrderPayway>>();
		List<OrderPayway> updateList = new ArrayList<OrderPayway>();
		for (SelfShopTerminalAccount selfShopTerminalAccount : list) {
			List<OrderPayway> temp = orderPaywayService
					.findByShopNo(selfShopTerminalAccount.getShopNo());
			for (OrderPayway orderPayway : temp) {
				BillStatus object = billStatusService.findByBillNo(
						orderPayway.getOrderNo(), orderPayway.getPayName());
				if (null != object && object.getStatus() == 1) {
					continue;// 跳过已确认的单
				}
				if (orderPayway.getPayName().equals(
						selfShopTerminalAccount.getCreditCardType())
						&& orderPayway.getPoundage() != null
						&& orderPayway.getAmount() != null
						&& StringUtils.isNotEmpty(selfShopTerminalAccount
								.getCreditCardRate().toString())) {
					orderPayway.setPoundage(orderPayway.getAmount().multiply(
							selfShopTerminalAccount.getCreditCardRate()));
					orderPayway.setRate(selfShopTerminalAccount.getCreditCardRate());
					updateList.add(orderPayway);
				}
			}
		}
		map.put(CommonOperatorEnum.UPDATED, updateList);
		if (updateList.size() > 0) {
			orderPaywayService.save(map);
		}
	}

	/**
	 * 店铺终端信息管理费率删除后修改财务人员未确认的销售订单的手续费
	 * 
	 * @param list
	 * @throws ServiceException
	 */
	public void updatePoundageForDel(List<SelfShopTerminalAccount> list)
			throws ServiceException {
		Map<CommonOperatorEnum, List<OrderPayway>> map = new HashMap<CommonOperatorEnum, List<OrderPayway>>();
		List<OrderPayway> updateList = new ArrayList<OrderPayway>();
		for (SelfShopTerminalAccount selfShopTerminalAccount : list) {
			List<OrderPayway> temp = orderPaywayService
					.findByShopNo(selfShopTerminalAccount.getShopNo());
			for (OrderPayway orderPayway : temp) {
				BillStatus object = billStatusService.findByBillNo(
						orderPayway.getOrderNo(), orderPayway.getPayName());
				if (null != object && object.getStatus() == 1) {
					continue;// 跳过已确认的单
				}
				orderPayway.setPoundage(null);
				orderPayway.setRate(null);
				updateList.add(orderPayway);
			}
		}
		map.put(CommonOperatorEnum.UPDATED, updateList);
		if (updateList.size() > 0) {
			orderPaywayService.save(map);
		}
	}

	@Override
	public List<OrderPayway> queryAllPayWays() {
		return orderPaywayService.queryAllPayWays();
	}
}