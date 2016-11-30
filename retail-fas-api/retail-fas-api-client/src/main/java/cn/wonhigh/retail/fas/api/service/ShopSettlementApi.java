package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Date;

import cn.wonhigh.retail.fas.api.dto.MallDeductionSettingDto;
import cn.wonhigh.retail.fas.api.dto.ShopBalanceDateDto;
import cn.wonhigh.retail.fas.api.dto.ShopBankInfoDto;
import cn.wonhigh.retail.fas.api.dto.ShopCardInfoDto;
import cn.wonhigh.retail.fas.api.model.ShopBankInfoDtlModel;

import com.yougou.logistics.base.common.exception.RpcException;

public interface ShopSettlementApi {
	
	/**
	 * 获取商场门店结算期设置
	 * @return 所有商场门店结算期设置
	 * @throws RpcException
	 */
	public ShopBalanceDateDto getAllShopBalanceDateInfo(String shopNo) throws RpcException ;
	
	/**
	 * 获取商场扣费名目设置
	 * @return 所有名目设置
	 * @throws RpcException
	 */
	public List<MallDeductionSettingDto> getAllMallDeductionSettingInfo(String shopNo) throws RpcException ;
	
	/**
	 * get the terminalNo and bankCardNo by shopNo
	 * @param shopNo : the no of the shop
	 * @return 
	 * @throws RpcException
	 */
	public ShopCardInfoDto getShopBankInfo(String shopNo) throws RpcException ;
	
	/**
	 * get the terminal and depositAccount by shopNo
	 * @param shopNo : the no of the shop
	 * @return 
	 * @throws RpcException
	 */
	public ShopBankInfoDto getShopBankInfoDtl(String shopNo) throws RpcException ;
	
	/**
	 * 获取商场门店结算期   已结算 最大结算月 的终止日期
	 * @return 
	 * @throws RpcException
	 */
	public Date getShopBalanceEndDate(String shopNo) throws RpcException ;
	
	/**
	 * 获取内购结算期  已开票   最大结算月 的终止日期
	 * @return 
	 * @throws RpcException
	 */
	public Date getInsidePurchaseBalanceEndDate(String shopNo) throws RpcException ;
}
