package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.model.ShopBankAccountModel;

public interface SelfShopBankInfoMapper {
	
	public ShopBankAccountModel getShopBankInfo(Map<String,Object> maps);
	
	/**
	 * 获取店铺终端账号
	 * @param maps
	 * @return
	 */
	public List<ShopBankAccountModel> getShopTerminalAccountInfo(Map<String, Object> maps);
	
	/**
	 * 获取店铺存现账号
	 * @param maps
	 * @return
	 */
	public List<ShopBankAccountModel> getShopDepositAccountInfo (Map<String, Object> maps);
}
