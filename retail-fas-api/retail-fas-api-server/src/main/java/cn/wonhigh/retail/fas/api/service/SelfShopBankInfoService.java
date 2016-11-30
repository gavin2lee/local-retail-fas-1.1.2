package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import cn.wonhigh.retail.fas.api.model.ShopBankAccountModel;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface SelfShopBankInfoService {
	
	public ShopBankAccountModel getShopBankInfo(String shopNo) throws ServiceException;
	
	public List<ShopBankAccountModel> getShopDepositAccountInfo(String shopNo) throws ServiceException;
	
	public List<ShopBankAccountModel> getShopTerminalAccountInfo(String shopNo) throws ServiceException;
	
}
