package cn.wonhigh.retail.fas.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.SelfShopBankInfoMapper;
import cn.wonhigh.retail.fas.api.model.ShopBankAccountModel;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("selfShopBankInfoServiceImpl")
public class SelfShopBankInfoServiceImpl implements SelfShopBankInfoService {

	@Resource
	private SelfShopBankInfoMapper selfShopBankInfoMapper;
	
	@Override
	public ShopBankAccountModel getShopBankInfo(String shopNo)
			throws ServiceException {
		// TODO Auto-generated method stub
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("shopNo", shopNo);
		
		return selfShopBankInfoMapper.getShopBankInfo(maps);
	}

	@Override
	public List<ShopBankAccountModel> getShopDepositAccountInfo(String shopNo)
			throws ServiceException {
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("shopNo", shopNo);
		
		return selfShopBankInfoMapper.getShopDepositAccountInfo(maps);
	}

	@Override
	public List<ShopBankAccountModel> getShopTerminalAccountInfo(String shopNo)
			throws ServiceException {
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("shopNo", shopNo);
		
		return selfShopBankInfoMapper.getShopTerminalAccountInfo(maps);
	}

}
