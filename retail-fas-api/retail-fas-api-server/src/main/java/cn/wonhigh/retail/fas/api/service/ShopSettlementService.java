package cn.wonhigh.retail.fas.api.service;

import java.util.Date;
import java.util.List;

import cn.wonhigh.retail.fas.common.model.MallDeductionSetting;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

public interface ShopSettlementService {
	
	public List<ShopBalanceDate> getAllShopBalanceDateInfo(String shopNo) throws ServiceException;
	
	public List<MallDeductionSetting> getAllMallDeductionSettingInfo(String shopNo) throws ServiceException;
	
	/**
	 * 获取商场门店结算期   已结算 最大结算月 的终止日期
	 * @return 
	 * @throws RpcException
	 */
	public Date getShopBalanceEndDate(String shopNo) throws ServiceException ;
	
	/**
	 * 获取内购结算期  已开票   最大结算月 的终止日期
	 * @return 
	 * @throws RpcException
	 */
	public Date getInsidePurchaseBalanceEndDate(String shopNo) throws ServiceException ;
}
