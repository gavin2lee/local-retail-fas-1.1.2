package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.OrderPayway;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface OrderPaywayManager extends BaseCrudManager {
	/**
	 * 店铺终端信息管理费率修改后修改财务人员未确认的销售订单的手续费
	 * @param list
	 * @throws ServiceException
	 */
	public void updatePoundage(List<SelfShopTerminalAccount> list) throws ServiceException;
	
	/**
	 * 店铺终端信息管理费率删除后修改财务人员未确认的销售订单的手续费
	 * @param list
	 * @throws ServiceException
	 */
	public void updatePoundageForDel(List<SelfShopTerminalAccount> list) throws ServiceException;
	
	/**
	 * 查询全部的支付方式
	 * @return
	 */
	public List<OrderPayway> queryAllPayWays();
}