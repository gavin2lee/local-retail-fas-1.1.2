package cn.wonhigh.retail.fas.api.dal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.common.model.MallDeductionSetting;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

public interface ShopSettlementMapper {
	
	public List<ShopBalanceDate> getAllShopBalanceDateInfo(Map<String,Object> maps);
	
	public List<MallDeductionSetting> getAllMallDeductionSettingInfo(String shopNo);
	
	/**
	 * 获取商场门店结算期   已结算 最大结算月 的终止日期
	 * @return 
	 * @throws RpcException
	 */
	public Date getShopBalanceEndDate(String shopNo);
	
	/**
	 * 获取内购结算期  已开票   最大结算月 的终止日期
	 * @return 
	 * @throws RpcException
	 */
	public Date getInsidePurchaseBalanceEndDate(String shopNo);
	
}
